package com.pi.small.goal.my.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

public class DistributionMemberNextActivity extends BaseActivity {
    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.member_list)
    PullToRefreshListView memberList;
    @InjectView(R.id.img_empty)
    ImageView imgEmpty;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;
    private List<MemberEntity> memberEntityList;
    private MemberEntity memberEntity;
    private DistributionMemberAdapter distributionMemberAdapter;

    private boolean isDown = false;
    private int intPage = 1;
    private int size = 20;
    private int total;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_distribution_member_next);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        uid = getIntent().getExtras().getString("uid", "26");

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
                            Utils.showToast(DistributionMemberNextActivity.this, "没有更多数据了");
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


        if (Utils.isNetworkConnected(DistributionMemberNextActivity.this)) {
            nullLayout.setClickable(false);
            nullLayout.setVisibility(View.GONE);
            GetMember();
        } else {
            nullLayout.setClickable(true);
            nullLayout.setVisibility(View.VISIBLE);
            imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
            tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
        }
    }

    @OnClick({R.id.left_image, R.id.null_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.null_layout:
                if (Utils.isNetworkConnected(DistributionMemberNextActivity.this)) {
                    nullLayout.setClickable(false);
                    nullLayout.setVisibility(View.GONE);
                    GetMember();
                } else {
                    Utils.showToast(DistributionMemberNextActivity.this, "请检查网络是否连接");
                    nullLayout.setClickable(true);
                    nullLayout.setVisibility(View.VISIBLE);
                    imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                    tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                }
                break;
        }
    }


    private void GetMember() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Agent);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("uid", uid);
        requestParams.addBodyParameter("p", intPage + "");
        requestParams.addBodyParameter("r", size + "");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                getResult(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getResult(String result) {
        if (!result.equals("")) {
            try {
                String code = new JSONObject(result).getString("code");
                if (code.equals("0")) {

                    total = Integer.valueOf(new JSONObject(result).getString("total"));
                    JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                    if (isDown) {
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
                        nullLayout.setClickable(false);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_null_data);
                        tvEmpty.setText("暂 时 没 有 下 级");
                    }
                    distributionMemberAdapter.notifyDataSetChanged();
                    memberList.onRefreshComplete();
                } else if (code.equals("100000")) {
                    if (memberEntityList.size() < 1) {
                        nullLayout.setClickable(false);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_null_data);
                        tvEmpty.setText("暂 时 没 有 下 级");
                    }

                } else {
                    nullLayout.setClickable(true);
                    nullLayout.setVisibility(View.VISIBLE);
                    imgEmpty.setImageResource(R.mipmap.bg_wrong);
                    tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
