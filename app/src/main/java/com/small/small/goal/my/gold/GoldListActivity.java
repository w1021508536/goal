package com.small.small.goal.my.gold;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.dialog.MonthDialog;
import com.small.small.goal.my.entry.UerEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Code;
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

public class GoldListActivity extends BaseActivity implements MonthDialog.OnDialogOkListener {


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
    @InjectView(R.id.plv)
    PullToRefreshListView plv;
    @InjectView(R.id.head)
    LinearLayout head;
    @InjectView(R.id.next_text)
    TextView nextText;

    TextView bean_text;
    @InjectView(R.id.img_empty)
    ImageView imgEmpty;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;

    private GoldListAdapter goldListAdapter;

    private List<GoldEmpty> goldEmptyList;
    private GoldEmpty goldEmpty;

    private String startTime;
    private String endTime;

    MonthDialog dialog;

    private UerEntity.AccountBean account;

    PopupWindow popupWindow;
    SpannableStringBuilder spannable;


    private String money = "";
    private String bean_pay = "";
    private int total;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gold_list);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        dialog = new MonthDialog(this, this);
        goldEmptyList = new ArrayList<>();
        startTime = "";
        endTime = "";
        account = CacheUtil.getInstance().getUserInfo().getAccount();
        init();
    }

    private void init() {

        nameTextInclude.setText("金豆明细");
        rightImageInclude.setImageResource(R.mipmap.icon_fitter);

        //        plv.setMode(PullToRefreshBase.Mode.BOTH);
        plv.setMode(PullToRefreshBase.Mode.BOTH);

        goldListAdapter = new GoldListAdapter(this, goldEmptyList);
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
                            Utils.showToast(GoldListActivity.this, "没有更多数据了");
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

    @OnClick({R.id.left_image_include, R.id.right_image_include, R.id.next_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.right_image_include:
                dialog.show();
                break;
            case R.id.next_text:
                MoneyWindow(view);
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
        requestParams.addBodyParameter("p", page + "");
        requestParams.addBodyParameter("r", "20");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("========金豆记录========" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        if (page == 1)
                            goldEmptyList.clear();
                        total = Integer.valueOf(new JSONObject(result).getString("total"));
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
//                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
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
                System.out.println("========金豆记录==ex======" + ex.getMessage());


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

    @Override
    public void getSelectTime(String time) {


//        startTime = time.substring(0, time.length() - 9) + " 00:00:01";
        endTime = time.substring(0, time.length() - 9) + " 23:59:59";
        page = 1;
        GetData();
    }

    //弹出框
    private void MoneyWindow(final View view) {

        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_money_gold, null);
        popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);

        RelativeLayout content_layout = (RelativeLayout) windowView.findViewById(R.id.content_layout);
        TextView recharge1_text = (TextView) windowView.findViewById(R.id.recharge1_text);
        TextView recharge2_text = (TextView) windowView.findViewById(R.id.recharge2_text);
        TextView recharge3_text = (TextView) windowView.findViewById(R.id.recharge3_text);
        TextView recharge4_text = (TextView) windowView.findViewById(R.id.recharge4_text);
        TextView recharge5_text = (TextView) windowView.findViewById(R.id.recharge5_text);
        TextView recharge6_text = (TextView) windowView.findViewById(R.id.recharge6_text);
        ImageView ribbon_image = (ImageView) windowView.findViewById(R.id.ribbon_image);
        bean_text = (TextView) windowView.findViewById(R.id.bean_text);

        bean_text.setText(account.getBean());

        recharge1_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeWindow(view, 1);
            }
        });
        recharge2_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeWindow(view, 2);
            }
        });
        recharge3_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeWindow(view, 3);
            }
        });
        recharge4_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeWindow(view, 4);
            }
        });
        recharge5_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeWindow(view, 5);
            }
        });
        recharge6_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeWindow(view, 6);
            }
        });
        windowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        content_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        ribbon_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);


        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    private void RechargeWindow(View view, int status) {
        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_recharge_gold, null);
        final PopupWindow popupWindow_recharge = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
        TextView content_text = (TextView) windowView.findViewById(R.id.content_text);
        TextView number_text = (TextView) windowView.findViewById(R.id.number_text);
        ImageView number_image = (ImageView) windowView.findViewById(R.id.number_image);
        TextView ok_text = (TextView) windowView.findViewById(R.id.ok_text);
        TextView no_text = (TextView) windowView.findViewById(R.id.no_text);
        String content = null;
        if (status == 1) {
            content = "本次充值您将花费1元";
            number_text.setText("100金豆");
            money = "1";
            bean_pay = "100";
            number_image.setImageResource(R.mipmap.icon_gold_bean_1);
        } else if (status == 2) {
            content = "本次充值您将花费6元";
            number_text.setText("600金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_2);
            money = "6";
            bean_pay = "600";
        } else if (status == 3) {
            content = "本次充值您将花费10元";
            number_text.setText("1000金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_3);
            money = "10";
            bean_pay = "1000";
        } else if (status == 4) {
            content = "本次充值您将花费30元";
            number_text.setText("3000金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_4);
            money = "30";
            bean_pay = "3000";
        } else if (status == 5) {
            content = "本次充值您将花费50元";
            number_text.setText("5000金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_4);
            money = "50";
            bean_pay = "5000";
        } else if (status == 6) {
            content = "本次充值您将花费100元";
            number_text.setText("10000金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_4);
            money = "100";
            bean_pay = "10000";
        }
        spannable = new SpannableStringBuilder(content);
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_heavy)), 8, content.length() - 1
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        content_text.setText(spannable);

        ok_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChongZhi();
                popupWindow_recharge.dismiss();
            }
        });
        no_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow_recharge.dismiss();
            }
        });
        popupWindow_recharge.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow_recharge.setTouchable(true);
        popupWindow_recharge.setOutsideTouchable(false);
        popupWindow_recharge.setFocusable(true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow_recharge.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow_recharge.showAtLocation(view, Gravity.CENTER, 0, 0);
    }


    private void ChongZhi() {
        Intent intent = new Intent(this, GoldPayActivity.class);

        intent.putExtra("money", money);
        startActivityForResult(intent, Code.SupportAim);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Code.SupportAim) {
            account.setBean(Long.valueOf(account.getBean()) + Long.valueOf(bean_pay) + "");
            bean_text.setText(account.getBean());
        } else if (resultCode == Code.FailCode) {

        }
    }

}
