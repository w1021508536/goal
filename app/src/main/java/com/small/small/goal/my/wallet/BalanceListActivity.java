package com.small.small.goal.my.wallet;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.gold.GoldEmpty;
import com.small.small.goal.my.gold.GoldListAdapter;
import com.small.small.goal.my.wallet.adapter.BalanceAdapter;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class BalanceListActivity extends BaseActivity {


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
    @InjectView(R.id.ll_top_include)
    LinearLayout llTopInclude;
    @InjectView(R.id.plv)
    PullToRefreshListView plv;
    @InjectView(R.id.img_empty)
    ImageView imgEmpty;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;
    private BalanceAdapter goldListAdapter;

    private List<GoldEmpty> goldEmptyList;
    private GoldEmpty goldEmpty;

    private String startTime;
    private String endTime;


    private int total;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_balance_list);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        goldEmptyList = new ArrayList<>();
        startTime = "";
        endTime = "";
        init();
    }

    private void init() {

        nameTextInclude.setText("余额明细");
        rightImageInclude.setVisibility(View.GONE);
        llTopInclude.setBackgroundResource(R.color.gray_head);
        //        plv.setMode(PullToRefreshBase.Mode.BOTH);
        plv.setMode(PullToRefreshBase.Mode.BOTH);

        goldListAdapter = new BalanceAdapter(this, goldEmptyList);
        plv.setAdapter(goldListAdapter);


        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                startTime = "";
                endTime = "";
                page = 1;
                GetData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (page * 20 >= total) {
                    plv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Utils.showToast(BalanceListActivity.this, "没有更多数据了");
                            plv.onRefreshComplete();
                        }
                    }, 1000);
                } else {
                    page = page + 1;
                    GetData();
                }
            }
        });

        if (this != null) {
            if (Utils.isNetworkConnected(this)) {
                nullLayout.setClickable(false);
                nullLayout.setVisibility(View.GONE);
                page = 1;
                GetData();
            } else {
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
            }
        }
    }

    @OnClick({R.id.left_image_include, R.id.null_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.null_layout:
                if (Utils.isNetworkConnected(this)) {

                    nullLayout.setClickable(false);
                    nullLayout.setVisibility(View.GONE);
                    GetData();
                } else {
                    Utils.showToast(this, "请检查网络是否连接");
                    nullLayout.setClickable(true);
                    nullLayout.setVisibility(View.VISIBLE);
                    imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                    tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                }

                break;
        }
    }


    private void GetData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.AccountRecord);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("startTime", startTime);
        requestParams.addBodyParameter("endTime", endTime);
        requestParams.addBodyParameter("accountType", "1");
        requestParams.addBodyParameter("p", page + "");
        requestParams.addBodyParameter("r", "20");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("===============" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        if (page == 1)
                            goldEmptyList.clear();
                        total = Integer.valueOf(new JSONObject(result).getString("total"));

                        if (total > 20) {
                            plv.setMode(PullToRefreshBase.Mode.BOTH);
                        } else {
                            plv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                        }

                        for (int i = 0; i < jsonArray.length(); i++) {
                            goldEmpty = new GoldEmpty();
                            goldEmpty.setId(jsonArray.getJSONObject(i).optString("accountLogId"));
                            goldEmpty.setUserId(jsonArray.getJSONObject(i).optString("userId"));
                            goldEmpty.setAccountType(jsonArray.getJSONObject(i).optString("accountType"));
                            goldEmpty.setOperateType(jsonArray.getJSONObject(i).optString("operateType"));
                            goldEmpty.setRemark(jsonArray.getJSONObject(i).optString("remark"));
                            goldEmpty.setAmount(jsonArray.getJSONObject(i).optString("amount"));
                            goldEmpty.setLinkId(jsonArray.getJSONObject(i).optString("linkId"));
                            goldEmpty.setCreateTime(jsonArray.getJSONObject(i).optString("createTime"));
                            goldEmpty.setAccount(jsonArray.getJSONObject(i).optString("account"));
                            goldEmptyList.add(goldEmpty);
                        }
                        goldListAdapter.notifyDataSetChanged();
                    } else if (code.equals("100000")) {
                        if (page == 1) {
                            goldEmptyList.clear();
                        }
                        if (goldEmptyList.size() == 0) {
                            nullLayout.setClickable(false);
                            nullLayout.setVisibility(View.VISIBLE);
                            imgEmpty.setImageResource(R.mipmap.bg_null_data);
                            tvEmpty.setText("暂 时 没 有 任 何 数 据 ~");
                        }
                    } else {
                        Utils.showToast(BalanceListActivity.this, new JSONObject(result).getString("msg"));
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_wrong);
                        tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                plv.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=========ex======" + ex.getMessage());

                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                plv.onRefreshComplete();
            }

            @Override
            public void onFinished() {

            }
        });
    }


}
