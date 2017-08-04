package com.small.small.goal.my.transfer.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.SpannableStringBuilder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.small.small.goal.MainActivity;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.entry.NoticeEntity;
import com.small.small.goal.my.gold.GoldEmpty;
import com.small.small.goal.my.guess.twoColorBall.TwoColorBallActivity;
import com.small.small.goal.my.transfer.adapter.TransferAdapter;
import com.small.small.goal.my.transfer.adapter.TransferMoreAdapter;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.weight.MarqueeTextView;
import com.small.small.goal.weight.Notice;
import com.small.small.goal.weight.NoticeMF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/5/31 17:45
 * 修改：
 * 描述：期权转让
 **/
public class TransferActivity extends BaseActivity {


    PopupWindow popupWindow;
    SpannableStringBuilder spannable;
    @InjectView(R.id.pullto)
    PullToRefreshListView pullto;
    @InjectView(R.id.img_empty)
    ImageView imgEmpty;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.top_layout)
    RelativeLayout topLayout;


    TextView tvMySurplusTransfer;
    TextView transferText;
    TextView tvGoZhuanquTransfer;
    TextView tvTransferTransfer;
    LinearLayout llBottom;
    RelativeLayout llTop;
    TextView tvScollTransfer;
    LinearLayout llNotice;
    MarqueeView marqueeView;


    private View head_view;
    private ListView listView;


    private int page = 1;

    private int total;

    private List<GoldEmpty> goldEmptyList;
    private GoldEmpty goldEmpty;

    private TransferAdapter goldListAdapter;

    private String startTime;
    private String endTime;

    private List<Notice> noticeList;
    private MarqueeFactory<TextView, Notice> marqueeFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_transfer);
        ButterKnife.inject(this);

        super.onCreate(savedInstanceState);

        init();
    }

    public void init() {
        //   initBfcv();

        noticeList = new ArrayList<>();

        goldEmptyList = new ArrayList<>();
        startTime = "";
        endTime = "";
        goldListAdapter = new TransferAdapter(this, goldEmptyList);
        nameTextInclude.setText("期权");
        head_view = LayoutInflater.from(this).inflate(R.layout.view_transfer_head, null);
        listView = pullto.getRefreshableView();

        tvMySurplusTransfer = (TextView) head_view.findViewById(R.id.tv_mySurplus_transfer);
        transferText = (TextView) head_view.findViewById(R.id.transfer_text);
        tvGoZhuanquTransfer = (TextView) head_view.findViewById(R.id.tv_goZhuanqu_transfer);
        tvTransferTransfer = (TextView) head_view.findViewById(R.id.tv_transfer_transfer);
        llBottom = (LinearLayout) head_view.findViewById(R.id.ll_bottom);
        llTop = (RelativeLayout) head_view.findViewById(R.id.ll_top);
        tvScollTransfer = (TextView) head_view.findViewById(R.id.tv_scoll_transfer);
        llNotice = (LinearLayout) head_view.findViewById(R.id.ll_notice);
        marqueeView = (MarqueeView) head_view.findViewById(R.id.marqueeView);

        listView.addHeaderView(head_view);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        marqueeFactory = new NoticeMF(this, 0);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();

        pullto.setAdapter(goldListAdapter);
        pullto.setMode(PullToRefreshBase.Mode.BOTH);
        pullto.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                page = 1;
                GetData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {

                if (page * 10 >= total) {
                    pullto.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Utils.showToast(TransferActivity.this, "没有更多数据了");
                            pullto.onRefreshComplete();
                        }
                    }, 1000);
                } else {
                    page = page + 1;
                    GetData();
                }
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //顶部渐变色
                if (goldListAdapter == null)
                    return;
                int heigh = head_view.getHeight();

                if (heigh < getScrollY()) {

                    //Color.argb(255,0,178,238)
                    topLayout.setBackgroundColor(Color.argb(255, 255, 95, 12));
                } else {
                    float a = 255.0f / (float) heigh;
                    a = a * (float) getScrollY();
                    if (a > 255)
                        a = 255;
                    else if (a < 0)
                        a = 0;

                    topLayout.setBackgroundColor(Color.argb((int) a, 255, 95, 12));
                }

            }
        });
        tvTransferTransfer.setOnClickListener(this);
        tvGoZhuanquTransfer.setOnClickListener(this);
        rightImageInclude.setOnClickListener(this);
        leftImageInclude.setOnClickListener(this);

        if (Utils.isNetworkConnected(this)) {
            GetData();
            getNotice();
        } else {
            nullLayout.setClickable(true);
            nullLayout.setVisibility(View.VISIBLE);
            imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
            tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");

        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.tv_transfer_transfer:
                startActivity(new Intent(this, TransferNextActivity.class));
                break;
            case R.id.tv_goZhuanqu_transfer:
                CacheUtil.getInstance().setTaskQiQuan(true);
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.right_image_include:
                MoneyWindow(v);
                break;
        }
    }

    private void GetData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.AccountRecord);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("startTime", startTime);
        requestParams.addBodyParameter("endTime", endTime);
        requestParams.addBodyParameter("accountType", "3");
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
                            pullto.setMode(PullToRefreshBase.Mode.BOTH);
                        } else {
                            pullto.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
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

                        System.out.println("====================" + goldEmptyList.size());

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
                        Utils.showToast(TransferActivity.this, new JSONObject(result).getString("msg"));
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_wrong);
                        tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
                pullto.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=========ex======" + ex.getMessage());

                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                pullto.onRefreshComplete();
            }

            @Override
            public void onFinished() {

            }
        });
    }


    //弹出框
    private void MoneyWindow(final View view) {

        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_transfer_explain, null);
        popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
        windowView.setOnClickListener(new View.OnClickListener() {
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


    /**
     * 公告
     * create  wjz
     **/
    private void getNotice() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.NoticeList);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("type", "4");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            noticeList.add(new Notice(jsonArray.getJSONObject(i).getString("content"), 0));
                        }
                        llNotice.setVisibility(View.VISIBLE);
                        marqueeFactory.setData(noticeList);

                    } else {
                        if (!code.equals("100000"))
                            Utils.showToast(TransferActivity.this, new JSONObject(result).getString("msg"));
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
    protected void onResume() {
        super.onResume();

        double option = CacheUtil.getInstance().getUserInfo().getAccount().getOption();

        tvMySurplusTransfer.setText(option + "");
        if ((option + "").length() > 6) {
            int i = 600 / (option + "").length();

            tvMySurplusTransfer.setTextSize(i);

        }
        transferText.setText("可转让额" + String.valueOf(option).substring(0, String.valueOf(option).indexOf(".")));
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        topLayout.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
        head_view.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
//        topLayout.setLayoutParams();;
    }

    public int getScrollY() {
        try {
            View c = listView.getChildAt(0);
            if (c == null) {
                return 0;
            }
            int firstVisiblePosition = listView.getFirstVisiblePosition() - 1;
            int top = c.getTop();
            return -top + firstVisiblePosition * c.getHeight();
        } catch (Exception e) {
        }
        return 0;
    }


}
