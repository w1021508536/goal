package com.small.small.goal.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.adapter.FollowAdapter;
import com.small.small.goal.my.entry.FollowEntry;
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
 * 时间： 2017/6/1 18:31
 * 描述：
 * 修改：我的关注
 **/
public class FollowActivity extends BaseActivity {

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
    private FollowAdapter adapter;
    private int page = 1;
    private boolean addFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_follow);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        adapter = new FollowAdapter(this);
        plv.setAdapter(adapter);
        nameTextInclude.setText("我的关注");
        rightImageInclude.setVisibility(View.GONE);
//        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty_nofollow, null);
//        plvCollect.setEmptyView(emptyView);
    }


    /**
     * 获取数据
     * create  wjz
     **/

    //{"msg":"success","code":0,
// "result":[{"followId":1,"userId":26,"followUserId":31,"nick":"张洋","avatar":"https://wx.qlogo.cn/mmopen/SK4ycmXqictWLtQbUyjGw4o4yzlcY5AEZevEib7zkIJqwuiamTibn4cImYk3Tb0fJOqv92ykvlObc2j0gu6Nv3az2VtBniavZHOiay/0"},
// {"followId":2,"userId":26,"followUserId":8,"nick":"ee","avatar":"jpg"},
// {"followId":3,"userId":26,"followUserId":9,"nick":"ff","avatar":"jpg"}],"pageNum":0,"pageSize":0,"pageTotal":0,"total":0}
    @Override
    public void getData() {
        super.getData();
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/user/followed");
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("userId", "");
        requestParams.addBodyParameter("p", page + "");

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if ((!RenameActivity.callOk(result) || Utils.getMsg(result).equals(KeyCode.NO_DATA)) && page == 1) {
//                    View emptyView = LayoutInflater.from(AimActivity.this).inflate(R.layout.view_empty_nodata, null);
//                    plvTarget.setEmptyView(emptyView);
                    plv.setVisibility(View.GONE);
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String jsonData = jsonObject.get("result").toString();
                    Gson gson = new Gson();
                    List<FollowEntry> data = gson.fromJson(jsonData, new TypeToken<List<FollowEntry>>() {
                    }.getType());
                    if (Integer.valueOf(jsonObject.get("pageNum").toString()) != 0) {
                        adapter.addData(data);
                    } else
                        adapter.setData(data);
                    if (data.size() >= 9) {
                        addFlag = true;
                    }

                    plv.onRefreshComplete();

                } catch (JSONException e) {
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
        plv.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (addFlag) {
                    page++;
                    getData();
                    addFlag = false;
                }
            }
        });

    }


}
