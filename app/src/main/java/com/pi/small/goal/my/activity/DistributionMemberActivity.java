package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.DistributionMemberAdapter;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.MemberEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DistributionMemberActivity extends BaseActivity {

    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;
    @InjectView(R.id.member_list)
    PullToRefreshListView memberList;
    private List<MemberEntity> memberEntityList;
    private MemberEntity memberEntity;
    private DistributionMemberAdapter distributionMemberAdapter;

    private boolean isDown = false;
    private int intPage = 1;
    private int size = 20;
    private int total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_distribution_member);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        memberEntityList = new ArrayList<>();
        distributionMemberAdapter = new DistributionMemberAdapter(this, memberEntityList);

        init();
    }

    private void init() {

        memberList.setMode(PullToRefreshBase.Mode.BOTH);
        memberList.setAdapter(distributionMemberAdapter);

        /**
         * 实现 接口  OnRefreshListener2<ListView>  以便与监听  滚动条到顶部和到底部
         */
        memberList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                isDown = true;
                intPage = 1;
                GetMember();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                isDown = false;
                if (intPage * 10 >= total) {
                    memberList.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Utils.showToast(DistributionMemberActivity.this, "没有更多数据了");
                            memberList.onRefreshComplete();
                        }
                    }, 1000);
                } else {
                    intPage = intPage + 1;
                    GetMember();
                }
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        });
        GetMember();
    }

    @OnClick(R.id.left_image)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
        }
    }


    private void GetMember() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Agent);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
//        requestParams.addBodyParameter("uid", "");
        requestParams.addBodyParameter("uid", "26");
        requestParams.addBodyParameter("p", intPage + "");
        requestParams.addBodyParameter("r", size + "");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        total = Integer.valueOf(new JSONObject(result).getString("total"));
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        if (isDown){
                            memberEntityList.clear();
                        }
                        for (int i = 0; i < jsonArray.length(); i++) {
                            memberEntity = new MemberEntity();
                            memberEntity.setId(jsonArray.getJSONObject(i).getString("id"));
                            memberEntity.setUserId(jsonArray.getJSONObject(i).getString("userId"));
                            memberEntity.setPid(jsonArray.getJSONObject(i).getString("pid"));
                            memberEntity.setLevel(jsonArray.getJSONObject(i).getString("level"));
                            memberEntity.setSubCompanyId(jsonArray.getJSONObject(i).getString("subCompanyId"));
                            memberEntity.setVolume(jsonArray.getJSONObject(i).getString("volume"));
                            memberEntity.setNick(jsonArray.getJSONObject(i).getString("nick"));
                            memberEntity.setAvatar(jsonArray.getJSONObject(i).getString("avatar"));

                            memberEntityList.add(memberEntity);
                        }

                        if (memberEntityList.size() > 0) {
                            nullLayout.setVisibility(View.GONE);
                        } else {
                            nullLayout.setVisibility(View.VISIBLE);
                        }
                        distributionMemberAdapter.notifyDataSetChanged();
                        memberList.onRefreshComplete();
                    } else {
                        if (!code.equals("100000")) {
                            Utils.showToast(DistributionMemberActivity.this, new JSONObject(result).getString("msg"));
                        }
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
}
