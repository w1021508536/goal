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
import com.small.small.goal.my.guess.note.adapter.SportAdapter;
import com.small.small.goal.my.guess.note.empty.SportEmpty;
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

public class SportNoteActivity extends BaseActivity {

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
    @InjectView(R.id.guess_list)
    PullToRefreshListView guessList;
    @InjectView(R.id.ll_top_include)
    LinearLayout llTopInclude;
    @InjectView(R.id.img_empty)
    ImageView imgEmpty;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;


    private List<SportEmpty> sportEmptyList;
    private SportEmpty sportEmpty;
    private SportAdapter sportAdapter;

    public static SportNoteActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_sport_note);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);


        init();
    }

    private void init() {

        instance = this;

        nameTextInclude.setText("竞猜记录");
        rightImageInclude.setVisibility(View.GONE);


        sportEmptyList = new ArrayList<>();
        sportAdapter = new SportAdapter(this, sportEmptyList);
        guessList.setAdapter(sportAdapter);

        guessList.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

        guessList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SportNoteActivity.this, SportDetailsActivity.class);
                intent.putExtra("sportEmpty", sportEmptyList.get(position - 1));
                startActivity(intent);

            }
        });
        guessList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                GetGuess();
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
                GetGuess();
            } else {
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
            }
        }
    }

    private void GetGuess() {

        RequestParams requestParams = new RequestParams(Url.Url + Url.GuessRecord);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            sportEmpty = new SportEmpty();

                            sportEmpty.setType(jsonArray.getJSONObject(i).getString("type"));
                            sportEmpty.setVisitTeam(jsonArray.getJSONObject(i).getString("visitTeam"));
                            sportEmpty.setHomeTeam(jsonArray.getJSONObject(i).getString("homeTeam"));
                            sportEmpty.setReward(jsonArray.getJSONObject(i).getString("reward"));
                            sportEmpty.setOpenCode(jsonArray.getJSONObject(i).getString("openCode"));
                            sportEmpty.setId(jsonArray.getJSONObject(i).getString("id"));
                            sportEmpty.setUserId(jsonArray.getJSONObject(i).getString("userId"));
                            sportEmpty.setContent(jsonArray.getJSONObject(i).getString("content"));
                            sportEmpty.setNumber(jsonArray.getJSONObject(i).getString("number"));
                            sportEmpty.setMultifold(jsonArray.getJSONObject(i).getString("multifold"));
                            sportEmpty.setBean(jsonArray.getJSONObject(i).getString("bean"));
                            sportEmpty.setCreateTime(jsonArray.getJSONObject(i).getString("createTime"));
                            sportEmpty.setUpdateTime(jsonArray.getJSONObject(i).getString("updateTime"));
                            sportEmpty.setIsWin(jsonArray.getJSONObject(i).getString("isWin"));
                            sportEmpty.setIsDraw(jsonArray.getJSONObject(i).getString("isDraw"));
                            sportEmpty.setIsTake(jsonArray.getJSONObject(i).getString("isTake"));
                            sportEmpty.setMid(jsonArray.getJSONObject(i).getString("mid"));
                            sportEmpty.setLeague(jsonArray.getJSONObject(i).getString("league"));
                            sportEmpty.setExpect(jsonArray.getJSONObject(i).getString("expect"));

                            sportEmptyList.add(sportEmpty);
                        }

                        sportAdapter.notifyDataSetChanged();
                    } else if (code.equals("100000")) {
                        if (sportEmptyList.size() == 0) {
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
                guessList.onRefreshComplete();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                guessList.onRefreshComplete();
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
