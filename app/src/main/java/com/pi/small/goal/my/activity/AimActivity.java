package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.TargetAdapter;
import com.pi.small.goal.my.entry.CollectEntity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 21:11
 * 描述： 我的目标
 * 修改：
 **/
public class AimActivity extends BaseActivity {

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
    @InjectView(R.id.plv_target)
    PullToRefreshListView plvTarget;
    private TargetAdapter adapter;

    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_target);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("我的目标");
        rightImageInclude.setImageResource(R.mipmap.icon_aim_history2x);

        adapter = new TargetAdapter(this);
        plvTarget.setAdapter(adapter);

    }

    @Override
    public void initWeight() {
        super.initWeight();
        rightImageInclude.setOnClickListener(this);
        plvTarget.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                page = 1;
                getData();
            }
        });
        plvTarget.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                page++;
                getData();
            }
        });
    }

    @Override
    public void getData() {
        super.getData();
        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/aim");
        requestParams.addBodyParameter("userId", sp.getString(KeyCode.USER_ID, "26"));
        requestParams.addBodyParameter("p", page + "");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!RenameActivity.callOk(result) || Utils.getMsg(result).equals("no data")) {
//                    View emptyView = LayoutInflater.from(AimActivity.this).inflate(R.layout.view_empty_nodata, null);
//                    plvTarget.setEmptyView(emptyView);
                    plvTarget.setVisibility(View.GONE);
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String jsonData = jsonObject.get("result").toString();
                    Gson gson = new Gson();
                    List<CollectEntity> data = gson.fromJson(jsonData, new TypeToken<List<CollectEntity>>() {
                    }.getType());
                    if (Integer.valueOf(jsonObject.get("pageNum").toString()) != 0) {
                        adapter.addData(data);

                    } else {
                        adapter.setData(data);

                    }

                } catch (JSONException e) {
                }
                plvTarget.onRefreshComplete();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_image_include:
                startActivity(new Intent(this, AimOldActivity.class));
                break;
        }
    }
}
