package com.small.small.goal.my.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.small.small.goal.R;
import com.small.small.goal.my.adapter.CollectAdapter;
import com.small.small.goal.my.entry.CollectEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/2 17:24
 * 修改：
 * 描述：我的收藏
 **/
public class CollectActivity extends BaseActivity {

    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.tv_cancel_collect)
    TextView tvCancelCollect;
    @InjectView(R.id.etv_seach_collect)
    EditText etvSeachCollect;
    private CollectAdapter adapter;

    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_collect);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        adapter = new CollectAdapter(this);
        plv.setAdapter(adapter);
        nameTextInclude.setText("我的收藏");
        rightImageInclude.setVisibility(View.GONE);

        Drawable drawable = getResources().getDrawable(R.mipmap.index_iocn_search);
        drawable.setBounds(0, 0, Utils.dip2px(this, 14), Utils.dip2px(this, 14));
        etvSeachCollect.setCompoundDrawables(drawable, null, null, null);

    }

    @Override
    public void initWeight() {
        super.initWeight();
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                page = 1;
                getData();
            }
        });
    }

    @Override
    public void getData() {
        super.getData();
//{"msg":"success","code":0,"result":[{"id":5,"name":"我的第二个目标","budget":1000,"money":0,"cycle":6,"current":0,"userId":11,"province":"山东","city":"青岛","brief":"实现梦想","position":"卓越","longitude":0.000000,"latitude":0.000000,"support":0,"createTime":1494974232000,"status":1,"img":""}],"pageNum":0,"pageSize":0,"pageTotal":0,"total":0}
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/aim/collect");
        requestParams.addBodyParameter("userId", sp.getString(KeyCode.USER_ID, ""));
        requestParams.addBodyParameter("p", page + "");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                JSONObject jsonObject = null;
                if ((!RenameActivity.callOk(result) || Utils.getMsg(result).equals(KeyCode.NO_DATA)) && page == 1) {
//                    View emptyView = LayoutInflater.from(AimActivity.this).inflate(R.layout.view_empty_nodata, null);
//                    plvTarget.setEmptyView(emptyView);
                    plv.setVisibility(View.GONE);
                    return;
                }
                try {
                    jsonObject = new JSONObject(result);
                    String jsonData = jsonObject.get("result").toString();
                    Gson gson = new Gson();
                    List<CollectEntity> data = gson.fromJson(jsonData, new TypeToken<List<CollectEntity>>() {
                    }.getType());
                    adapter.setData(data);
                    plv.onRefreshComplete();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                plv.onRefreshComplete();
            }

            @Override
            public void onFinished() {
                plv.onRefreshComplete();
            }
        });
    }
}
