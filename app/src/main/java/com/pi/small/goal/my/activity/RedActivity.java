package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.RedAdapter;
import com.pi.small.goal.my.entry.RedGsonEntity;
import com.pi.small.goal.my.entry.WalletEntry;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 9:22
 * 修改：
 * 描述：我的红包
 **/
public class RedActivity extends BaseActivity {

    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.plv_wallet)
    PullToRefreshListView plvWallet;

    private int page = 1;
    private RedAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_red);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("我的红包");
        rightImageInclude.setImageResource(R.mipmap.list_btn);

        List<WalletEntry> data = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            if (i == 0 || i == 6) {
//                data.add(new WalletEntry(0));
//            } else {
//                data.add(new WalletEntry(1));
//            }
//        }
        adapter = new RedAdapter(this, data);
        plvWallet.setAdapter(adapter);

    }

    @Override
    public void getData() {
        super.getData();
        requestParams.setUri(Url.Url + "/redpacket/undraw");
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("userId", "26");
        requestParams.addBodyParameter("p", page + "");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
//[{"id":9,"aimId":17,"dynamicId":7,"money":10,"size":10,"remainMoney":10,"remainSize":10,"toUserId":48,"fromUserId":26,"createTime":1496717112000,"status":1}]
                if (!RenameActivity.callOk(result)) return;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String jsonData = jsonObject.get("result").toString();
                    Gson gson = new Gson();
                    Utils.showToast(RedActivity.this, (String) jsonObject.get("msg"));
                    List<RedGsonEntity> data = gson.fromJson(jsonData, new TypeToken<List<RedGsonEntity>>() {
                    }.getType());

                    List<WalletEntry> newData = new ArrayList<>();
                    for (RedGsonEntity one : data) {

                        newData.add(new WalletEntry(one.getId(), one.getAimId(), one.getDynamicId(), one.getMoney(), one.getSize(), one.getRemainMoney(), one.getRemainSize(), one.getToUserId(), one.getFromUserId(), one.getCreateTime(), one.getStatus(), RedAdapter.TYPE_CONTENT));
                    }
                    if (Integer.valueOf(jsonObject.get("pageNum").toString()) != 0) {
                        adapter.addData(newData);
                    } else
                        adapter.setData(newData);
                    plvWallet.onRefreshComplete();

                } catch (JSONException e) {
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
        rightImageInclude.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_image_include:
                startActivity(new Intent(this, RedMoreActivity.class));
                break;
        }
    }
}
