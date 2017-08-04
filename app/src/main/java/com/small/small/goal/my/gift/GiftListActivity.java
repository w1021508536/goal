package com.small.small.goal.my.gift;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.gold.GoldListActivity;
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

public class GiftListActivity extends BaseActivity {


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
    private List<GiftEmpty> giftEmptyList;
    private GiftEmpty giftEmpty;

    private GiftListAdapter giftListAdapter;

    private int total;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gift_list);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        giftEmptyList = new ArrayList<>();

        init();
    }

    private void init() {
        nameTextInclude.setText("我的奖品");
        rightImageInclude.setVisibility(View.GONE);

        //        plv.setMode(PullToRefreshBase.Mode.BOTH);
        plv.setMode(PullToRefreshBase.Mode.BOTH);
        giftListAdapter = new GiftListAdapter(this, giftEmptyList);
        plv.setAdapter(giftListAdapter);

        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                page = 1;
                GetData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                if (page * 20 >= total) {
                    plv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Utils.showToast(GiftListActivity.this, "没有更多数据了");
                            plv.onRefreshComplete();
                        }
                    }, 1000);
                } else {
                    page = page + 1;
                    GetData();
                }
            }
        });

        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(GiftListActivity.this, GiftDetailsActivity.class);
                intent.putExtra("giftEmpty", giftEmptyList.get(position - 1));
                startActivity(intent);
            }
        });

//        GetData();

        if (this != null) {
            if (Utils.isNetworkConnected(this)) {
                nullLayout.setClickable(false);
                nullLayout.setVisibility(View.GONE);
                page = 1;
                GetData();
            } else {
                giftEmptyList.clear();
                giftListAdapter.notifyDataSetChanged();
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
            }
        }
    }


    private void GetData() {

        RequestParams requestParams = new RequestParams(Url.Url + Url.Order);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("p", page + "");
        requestParams.addBodyParameter("r", "20");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("========result==========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");

                        if (page == 1)
                            giftEmptyList.clear();
                        total = Integer.valueOf(new JSONObject(result).getString("total"));

                        for (int i = 0; i < jsonArray.length(); i++) {

                            giftEmpty = new GiftEmpty();

                            giftEmpty.setOrderId(jsonArray.getJSONObject(i).optString("orderId"));
                            giftEmpty.setOrderNo(jsonArray.getJSONObject(i).optString("orderNo"));
                            giftEmpty.setUserId(jsonArray.getJSONObject(i).optString("userId"));
                            giftEmpty.setCreateTime(jsonArray.getJSONObject(i).optString("createTime"));
                            giftEmpty.setPayTime(jsonArray.getJSONObject(i).optString("payTime"));
                            giftEmpty.setDeliverTime(jsonArray.getJSONObject(i).optString("deliverTime"));
                            giftEmpty.setConfirmTime(jsonArray.getJSONObject(i).optString("confirmTime"));
                            giftEmpty.setIsPaid(jsonArray.getJSONObject(i).optString("isPaid"));
                            giftEmpty.setStatus(jsonArray.getJSONObject(i).optString("status"));
                            giftEmpty.setType(jsonArray.getJSONObject(i).optString("type"));
                            giftEmpty.setRefundTime(jsonArray.getJSONObject(i).optString("refundTime"));
                            giftEmpty.setAmount(jsonArray.getJSONObject(i).optString("amount"));
                            giftEmpty.setTotal(jsonArray.getJSONObject(i).optString("total"));
                            giftEmpty.setChannel(jsonArray.getJSONObject(i).optString("channel"));
                            giftEmpty.setIsComment(jsonArray.getJSONObject(i).optString("isComment"));
                            giftEmpty.setConsignee(jsonArray.getJSONObject(i).optString("consignee"));
                            giftEmpty.setMobile(jsonArray.getJSONObject(i).optString("mobile"));
                            giftEmpty.setProvince(jsonArray.getJSONObject(i).optString("province"));
                            giftEmpty.setCity(jsonArray.getJSONObject(i).optString("city"));
                            giftEmpty.setDistrict(jsonArray.getJSONObject(i).optString("district"));
                            giftEmpty.setAddress(jsonArray.getJSONObject(i).optString("address"));
                            giftEmpty.setExpressNo(jsonArray.getJSONObject(i).optString("expressNo"));
                            giftEmpty.setExpressName(jsonArray.getJSONObject(i).optString("expressName"));

                            giftEmpty.setId(jsonArray.getJSONObject(i).getJSONArray("goods").getJSONObject(0).optString("id"));
                            giftEmpty.setGoodsId(jsonArray.getJSONObject(i).getJSONArray("goods").getJSONObject(0).optString("goodsId"));
                            giftEmpty.setGoodsName(jsonArray.getJSONObject(i).getJSONArray("goods").getJSONObject(0).optString("goodsName"));
                            giftEmpty.setGoodsPrice(jsonArray.getJSONObject(i).getJSONArray("goods").getJSONObject(0).optString("goodsPrice"));
                            giftEmpty.setNumber(jsonArray.getJSONObject(i).getJSONArray("goods").getJSONObject(0).optString("number"));
                            giftEmpty.setGoodOrderNo(jsonArray.getJSONObject(i).getJSONArray("goods").getJSONObject(0).optString("orderNo"));
                            giftEmpty.setGoodsType(jsonArray.getJSONObject(i).getJSONArray("goods").getJSONObject(0).optString("goodsType"));
                            giftEmpty.setImg(jsonArray.getJSONObject(i).getJSONArray("goods").getJSONObject(0).optString("img"));
                            giftEmptyList.add(giftEmpty);
                        }
                        giftListAdapter.notifyDataSetChanged();
                    } else if (code.equals("100000")) {
                        if (page == 1) {
                            giftEmptyList.clear();
                        }
                        if (giftEmptyList.size() == 0) {
                            giftEmptyList.clear();
                            giftListAdapter.notifyDataSetChanged();
                            nullLayout.setClickable(false);
                            nullLayout.setVisibility(View.VISIBLE);
                            imgEmpty.setImageResource(R.mipmap.bg_null_data);
                            tvEmpty.setText("暂 时 没 有 任 何 数 据 ~");
                        }
                    } else {
                        giftEmptyList.clear();
                        giftListAdapter.notifyDataSetChanged();
                        Utils.showToast(GiftListActivity.this, new JSONObject(result).getString("msg"));
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
                giftEmptyList.clear();
                giftListAdapter.notifyDataSetChanged();
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

    @OnClick({R.id.left_image_include})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
        }
    }

}
