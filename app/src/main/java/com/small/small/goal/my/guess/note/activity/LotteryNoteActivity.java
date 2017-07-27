package com.small.small.goal.my.guess.note.activity;

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
import com.small.small.goal.my.guess.note.adapter.LotteryAdapter;
import com.small.small.goal.my.guess.note.empty.LotteryEmpty;
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

public class LotteryNoteActivity extends BaseActivity {

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
    @InjectView(R.id.lottery_list)
    PullToRefreshListView lotteryList;
    @InjectView(R.id.ll_top_include)
    LinearLayout llTopInclude;
    @InjectView(R.id.img_empty)
    ImageView imgEmpty;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;

    private List<LotteryEmpty> lotteryEmptyList;

    private LotteryEmpty lotteryEmpty;

    private LotteryAdapter lotteryAdapter;

    public static LotteryNoteActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_lottery_note);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        init();
    }

    private void init() {

        instance = this;

        nameTextInclude.setText("投注记录");
        rightImageInclude.setVisibility(View.GONE);

        lotteryEmptyList = new ArrayList<>();
        lotteryAdapter = new LotteryAdapter(this, lotteryEmptyList);
        lotteryList.setAdapter(lotteryAdapter);

        lotteryList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        lotteryList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(LotteryNoteActivity.this, LotteryDetailsActivity.class);
                intent.putExtra("lotteryEmpty", lotteryEmptyList.get(position - 1));
                startActivity(intent);

            }
        });
        lotteryList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                GetLottery();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {

            }
        });

        if (this != null) {
            if (Utils.isNetworkConnected(this)) {
                nullLayout.setClickable(false);
                nullLayout.setVisibility(View.GONE);
//                page = 1;
                GetLottery();
            } else {
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
            }
        }
    }

    private void GetLottery() {

        RequestParams requestParams = new RequestParams(Url.Url + Url.LotteryRecord);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("======彩票列表===========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        lotteryEmptyList.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            lotteryEmpty = new LotteryEmpty();

                            lotteryEmpty.setId(jsonArray.getJSONObject(i).getString("id"));
                            lotteryEmpty.setUserId(jsonArray.getJSONObject(i).getString("userId"));
                            lotteryEmpty.setContent(jsonArray.getJSONObject(i).getString("content"));
                            lotteryEmpty.setNumber(jsonArray.getJSONObject(i).getString("number"));
                            lotteryEmpty.setMultifold(jsonArray.getJSONObject(i).getString("multifold"));
                            lotteryEmpty.setBean(jsonArray.getJSONObject(i).getString("bean"));
                            lotteryEmpty.setCreateTime(jsonArray.getJSONObject(i).getString("createTime"));
                            lotteryEmpty.setUpdateTime(jsonArray.getJSONObject(i).getString("updateTime"));
                            lotteryEmpty.setIsWin(jsonArray.getJSONObject(i).getString("isWin"));
                            lotteryEmpty.setIsDraw(jsonArray.getJSONObject(i).getString("isDraw"));
                            lotteryEmpty.setIsTake(jsonArray.getJSONObject(i).getString("isTake"));
                            lotteryEmpty.setTid(jsonArray.getJSONObject(i).getString("tid"));
                            lotteryEmpty.setName(jsonArray.getJSONObject(i).getString("name"));
                            lotteryEmpty.setExpect(jsonArray.getJSONObject(i).getString("expect"));
                            lotteryEmpty.setCode(jsonArray.getJSONObject(i).getString("code"));
                            lotteryEmpty.setType(jsonArray.getJSONObject(i).getString("type"));
                            lotteryEmpty.setOpenCode(jsonArray.getJSONObject(i).getString("openCode"));
                            lotteryEmpty.setReward(jsonArray.getJSONObject(i).getString("reward"));
                            lotteryEmpty.setOpenTime(jsonArray.getJSONObject(i).getString("openTime"));

                            lotteryEmptyList.add(lotteryEmpty);
                        }
                        lotteryAdapter.notifyDataSetChanged();
                    } else if (code.equals("100000")) {
                        if (lotteryEmptyList.size() == 0) {
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
                lotteryList.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                lotteryList.onRefreshComplete();
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
            case R.id.null_layout:
                if (Utils.isNetworkConnected(this)) {

                    nullLayout.setClickable(false);
                    nullLayout.setVisibility(View.GONE);
                    GetLottery();
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
}
