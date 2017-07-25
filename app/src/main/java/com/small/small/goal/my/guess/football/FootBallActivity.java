package com.small.small.goal.my.guess.football;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.guess.football.adapter.FootBallPagerAdapter;
import com.small.small.goal.my.guess.football.fragment.HotFragment;
import com.small.small.goal.my.guess.football.fragment.MatchFragment;
import com.small.small.goal.my.guess.note.activity.SportNoteActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.dialog.AddgoldDialog;
import com.small.small.goal.utils.dialog.DialogClickListener;
import com.small.small.goal.weight.IndexViewPager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FootBallActivity extends FragmentActivity {



    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    @InjectView(R.id.ll_top_include)
    LinearLayout llTopInclude;
    @InjectView(R.id.sanjiao)
    ImageView sanjiao;
    @InjectView(R.id.bottom_left_layout)
    LinearLayout bottomLeftLayout;
    @InjectView(R.id.cai_text)
    TextView caiText;
    @InjectView(R.id.own_money_text)
    TextView ownMoneyText;
    @InjectView(R.id.own_money_layout)
    LinearLayout ownMoneyLayout;
    @InjectView(R.id.choose_dou_layout)
    LinearLayout chooseDouLayout;
    @InjectView(R.id.ok_text)
    TextView okText;
    @InjectView(R.id.viewPager)
    IndexViewPager viewPager;

    static TextView douText;
    static TextView chooseNumberText;
    static LinearLayout bottomLayout;
    static TextView chooseDouText;
    private HotFragment hotFragment;
    private MatchFragment matchFragment;

    private List<String> tabList;
    private List<Fragment> fragmentList;

    private FootBallPagerAdapter footBallPagerAdapter;


    private int choose;// 0  进入  豪门盛宴  1 //  进入  全部赛事

    private static int base;
    private int number = 0;

    private int ownMoney = 888;

    public static List<Map<String, String>> chooseList;
    static Map<String, String> map;

    private PopupWindow popupWindow;
    private View window_view_right;

    private AddgoldDialog addgoldDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_foot_ball);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
        base = 100;
        choose = getIntent().getIntExtra("choose", 0);

        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            if (view != null)
                view.setVisibility(View.GONE);
            if (llTopInclude != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) llTopInclude.getLayoutParams();
                layoutParams.height = Utils.dip2px(this, 40);
                llTopInclude.setLayoutParams(layoutParams);
            }
        }

        tabList = new ArrayList<>();
        fragmentList = new ArrayList<>();
        chooseList = new ArrayList<>();

        bottomLayout = (LinearLayout) findViewById(R.id.bottom_layout);
        chooseNumberText = (TextView) findViewById(R.id.choose_number_text);
        douText = (TextView) findViewById(R.id.dou_text);
        chooseDouText = (TextView) findViewById(R.id.choose_dou_text);
        addgoldDialog = new AddgoldDialog(this);
        init();
    }

    private void init() {
        rightImageInclude.setImageResource(R.mipmap.icon_menu);
        llTopInclude.setBackgroundResource(R.mipmap.title_nav_bg_bule);
        hotFragment = new HotFragment();
        matchFragment = new MatchFragment();

        fragmentList.add(hotFragment);
        fragmentList.add(matchFragment);

        tabList.add("豪门盛宴");
        tabList.add("全部赛事");


        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(0)));
        tabLayout.addTab(tabLayout.newTab().setText(tabList.get(1)));

        footBallPagerAdapter = new FootBallPagerAdapter(getSupportFragmentManager(), fragmentList, tabList);

        viewPager.setAdapter(footBallPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);


        chooseDouText.setText("投" + base + "豆");

        addgoldDialog.setOnListener(new DialogClickListener() {
            @Override
            public void onClick(String str) {
                base = Integer.valueOf(str);
                chooseDouText.setText("投" + base + "豆");
            }
        });

//        viewPager.setCurrentItem(choose);
//        tabLayout.getTabAt(choose).select();
    }


    @OnClick({R.id.left_image_include, R.id.right_image_include, R.id.own_money_layout, R.id.choose_dou_layout, R.id.ok_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.right_image_include:

                initPop();
                break;
            case R.id.choose_dou_layout:
                addgoldDialog.show();
                break;
            case R.id.own_money_layout:

                break;
            case R.id.ok_text:
                Betting(view);
                break;
        }
    }


    private void initPop() {
        if (window_view_right == null) {
            window_view_right = LayoutInflater.from(this).inflate(R.layout.window_football_top_right, null);
            popupWindow = new PopupWindow(window_view_right, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            TextView help_text = (TextView) window_view_right.findViewById(R.id.help_text);
            TextView guess_text = (TextView) window_view_right.findViewById(R.id.guess_text);
            help_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                }
            });
            guess_text.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(FootBallActivity.this, SportNoteActivity.class);
                    startActivity(intent);
                    popupWindow.dismiss();
                }
            });

            popupWindow.setBackgroundDrawable(new BitmapDrawable());
            popupWindow.setOutsideTouchable(true);//不能在没有焦点的时候使用
            popupWindow.showAsDropDown(rightImageInclude, 0, -Utils.dip2px(this, 0));
        } else {
            popupWindow.showAsDropDown(rightImageInclude, 0, -Utils.dip2px(this, 0));
        }

    }

    public static void ChangeNotesInfo(String id, String status, String name) {
//        for (int i = 0; i < chooseList.size(); i++) {
//            if (chooseList.get(i).get("id").equals(id)) {
//                chooseList.remove(i);
//            }
//        }

//        chooseList.clear();

        if (!status.equals("-1")) {
            map = new HashMap<>();
            map.put("id", id);
            map.put("status", status);
            map.put("name", name);
            chooseList.add(map);
        }
        if (chooseList.size() > 0) {
            bottomLayout.setVisibility(View.VISIBLE);
            chooseDouText.setText("投" + base + "豆");
            chooseNumberText.setText(chooseList.size() + "场");
            douText.setText((int) (chooseList.size() * base * 1.25) + "豆");
        } else {
            base = 100;
            bottomLayout.setVisibility(View.GONE);
        }
    }

    private void Betting(final View v) {

//
//        content	string	是	投注内容:胜、负	胜
//        number	string	是	注数，默认1	1
//        multifold	string	是	倍投	3
//        bean	string	是	金豆	300
//        matchId	string	否	赛事id	2
//        type	string	是	1：足球 2：篮球 3：娱乐 4： 彩票	1
//        name	string	是	名称：英超、双色球	英超
//        expect	string	是	场次	2场
//        code	string	否	彩票代码	ssq
//        rule	string	否	彩票玩法


        RequestParams requestParams = new RequestParams(Url.Url + Url.Wager);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("type", "1");
        requestParams.addBodyParameter("bean", base + "");
        requestParams.addBodyParameter("number", "1");
        requestParams.addBodyParameter("code", "");
        requestParams.addBodyParameter("rule", "");
        requestParams.addBodyParameter("multifold", "1");
        requestParams.addBodyParameter("expect", "20170810");
        requestParams.addBodyParameter("matchId", chooseList.get(0).get("id"));
        requestParams.addBodyParameter("name", chooseList.get(0).get("name"));

        if (chooseList.get(0).get("status").equals("0")) {
            requestParams.addBodyParameter("content", "胜");
        } else if (chooseList.get(0).get("status").equals("1")) {
            requestParams.addBodyParameter("content", "平");
        } else {
            requestParams.addBodyParameter("content", "负");
        }

        XUtil.post(requestParams, FootBallActivity.this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=======================" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        PutWindow(v);
                    } else {
                        Utils.showToast(FootBallActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("===========ex============" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    //弹出框
    private void PutWindow(View view) {

        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_lottery_pay_success, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);

        TextView content_text = (TextView) windowView.findViewById(R.id.content_text);
        TextView next_text = (TextView) windowView.findViewById(R.id.next_text);
        next_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(false);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }
}
