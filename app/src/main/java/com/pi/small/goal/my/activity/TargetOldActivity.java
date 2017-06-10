package com.pi.small.goal.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.TargetOldAdapter;
import com.pi.small.goal.my.entry.AimOldEntity;
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
 * 时间： 2017/6/2 13:31
 * 修改：
 * 描述：历史目标
 **/
public class TargetOldActivity extends BaseActivity {

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
    @InjectView(R.id.plv_target_old)
    PullToRefreshListView plvTargetOld;

    private int page = 1;
    private TargetOldAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_target_old);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("历史目标");
        rightImageInclude.setVisibility(View.GONE);

        adapter = new TargetOldAdapter(this);

//        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty_nodata, null);
//        plvTargetOld.setEmptyView(emptyView);
    }

    @Override
    public void getData() {
        super.getData();

        requestParams.setUri(Url.Url + "/aim/history");
        requestParams.addBodyParameter("userId", sp.getString(KeyCode.USER_ID, "26"));
        requestParams.addBodyParameter("p", page + "");


        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!RenameActivity.callOk(result) || Utils.getMsg(result).equals("no data")) {
//                    View emptyView = LayoutInflater.from(TargetActivity.this).inflate(R.layout.view_empty_nodata, null);
//                    plvTarget.setEmptyView(emptyView);
                    plvTargetOld.setVisibility(View.GONE);
                    return;
                }

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String jsonData = jsonObject.get("result").toString();
                    Gson gson = new Gson();
                    List<AimOldEntity> data = gson.fromJson(jsonData, new TypeToken<List<AimOldEntity>>() {
                    }.getType());
                    if (Integer.valueOf(jsonObject.get("pageNum").toString()) != 0) {
                        adapter.addData(data);

                    } else {
                        adapter.setData(data);
                        plvTargetOld.setAdapter(adapter);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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
    public void initWeight() {
        super.initWeight();
    }
}
