package com.small.small.goal.my.guess.fastthree;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.guess.LotteryExplainActivity;
import com.small.small.goal.my.guess.fastthree.empty.FastThreeEmpty;
import com.small.small.goal.my.guess.fastthree.fragment.SumFragment;
import com.small.small.goal.my.guess.fastthree.fragment.ThreeNotSameFragment;
import com.small.small.goal.my.guess.fastthree.fragment.ThreeSameFragment;
import com.small.small.goal.my.guess.fastthree.fragment.TwoNotSameFragment;
import com.small.small.goal.my.guess.fastthree.fragment.TwoSameFragment;
import com.small.small.goal.my.guess.football.adapter.FootBallPagerAdapter;
import com.small.small.goal.my.guess.note.activity.LotteryNoteActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.dialog.LotteryTopPopuwindow;
import com.small.small.goal.weight.MyViewPager;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FastThreeActivity extends BaseActivity {


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
    @InjectView(R.id.explain_text)
    TextView explainText;
    @InjectView(R.id.explain2_text)
    TextView explain2Text;
    @InjectView(R.id.random_text)
    TextView randomText;
    @InjectView(R.id.bottom)
    LinearLayout bottom;
    @InjectView(R.id.tabLayout)
    TabLayout tabLayout;
    @InjectView(R.id.expect_text)
    TextView expectText;
    @InjectView(R.id.time_text)
    TextView timeText;
    @InjectView(R.id.vp_main)
    MyViewPager vpMain;

    public static TextView nextText;

    private List<String> typeList;
    private int currentPosition = 0;

    private List<Fragment> fragmentList;

    private String sumString = "猜中开奖号码相加之和，单注奖108～4560金豆";//和数
    private String twoNotString = "猜中开奖号码中不相同的2个号，单注奖88金豆";//二不同号
    private String twoString = "猜中3个号码(2个相同),单注奖1440金豆";//二同号
    private String twoString2 = "猜中相同的2个号,单注奖225金豆";//二同号2
    private String threeString = "猜中111～666中指定一个，单注奖4560金豆";//三同号
    private String threeString2 = "111～666中任意一个开出，单注奖680金豆";//三同号2
    private String threeNotString = "猜中3个不同号码，单注奖680金豆";//三不同号
    private String threeNotString2 = "三连号任意一个开出，单注奖130金豆";//三不同号2


    SpannableStringBuilder spannable;
    SpannableStringBuilder spannable2;
    private String status;

    public static FastThreeActivity instance;

    private LotteryTopPopuwindow popupWindow;

    private String expect;
    private String openCode;
    private String openTime;
    private String openTimestamp;
    private String expireTime;
    private String code;
    private String now;

    private TimeCount time;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm:ss");


    FootBallPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fast_three);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);
        instance = this;
        fragmentList = new ArrayList<>();
        typeList = new ArrayList<>();

        status = getIntent().getExtras().getString("status", "");

        vpMain.setOffscreenPageLimit(5);
        nameTextInclude.setText("新快3");
        rightImageInclude.setImageResource(R.mipmap.icon_lottery_menu);

        nextText = (TextView) findViewById(R.id.next_text);

        popupWindow = new LotteryTopPopuwindow(this);
        popupWindow.setListener(new LotteryTopPopuwindow.setOnclickListener() {
            @Override
            public void onGuizeClick() {
                Intent intent = new Intent(FastThreeActivity.this, LotteryExplainActivity.class);
                intent.putExtra("url", Url.UrlLottery + "jlk3");
                startActivity(intent);
            }

            @Override
            public void onJiluClick() {

                startActivity(new Intent(FastThreeActivity.this, LotteryNoteActivity.class));
                popupWindow.dismiss();
            }

            @Override
            public void onFollowClick() {

            }

            @Override
            public void onShareClick() {

            }
        });

        typeList.add("和数");
        typeList.add("三同号");
        typeList.add("二同号");
        typeList.add("三不同号");
        typeList.add("二不同号");


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                currentPosition = position;
                if (position == 0) {
                    spannable = new SpannableStringBuilder(sumString);
                    spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_heavy)), 14, sumString.length() - 2
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    explainText.setText(spannable);
                    explain2Text.setVisibility(View.GONE);
                    SumFragment.getFragment().ChangeNext();
                } else if (position == 1) {
                    spannable = new SpannableStringBuilder(threeString);
                    spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_heavy)), 18, threeString.length() - 2
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    explainText.setText(spannable);

                    spannable2 = new SpannableStringBuilder(threeString2);
                    spannable2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_heavy)), 18, threeString2.length() - 2
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    explain2Text.setText(spannable2);
                    explain2Text.setVisibility(View.VISIBLE);
                    ThreeSameFragment.getFragment().ChangeNext();
                } else if (position == 2) {
                    spannable = new SpannableStringBuilder(twoString);
                    spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_heavy)), 16, twoString.length() - 2
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    explainText.setText(spannable);

                    spannable2 = new SpannableStringBuilder(twoString2);
                    spannable2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_heavy)), 12, twoString2.length() - 2
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    explain2Text.setText(spannable2);
                    explain2Text.setVisibility(View.VISIBLE);
                    TwoSameFragment.getFragment().ChangeNext();

                } else if (position == 3) {
                    spannable = new SpannableStringBuilder(threeNotString);
                    spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_heavy)), 12, threeNotString.length() - 2
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    explainText.setText(spannable);

                    spannable2 = new SpannableStringBuilder(threeNotString2);
                    spannable2.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_heavy)), 13, threeNotString2.length() - 2
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    explain2Text.setText(spannable2);
                    explain2Text.setVisibility(View.VISIBLE);
                    ThreeNotSameFragment.getFragment().ChangeNext();
                } else if (position == 4) {
                    spannable = new SpannableStringBuilder(twoNotString);
                    spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_heavy)), 18, twoNotString.length() - 2
                            , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    explainText.setText(spannable);
                    explain2Text.setVisibility(View.GONE);
                    SumFragment.getFragment().ChangeNext();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        fragmentList.add(SumFragment.getFragment());
        fragmentList.add(ThreeSameFragment.getFragment());
        fragmentList.add(TwoSameFragment.getFragment());
        fragmentList.add(ThreeNotSameFragment.getFragment());
        fragmentList.add(TwoNotSameFragment.getFragment());

        for (int i = 0; i < typeList.size(); i++) {
            tabLayout.addTab(tabLayout.newTab().setText(typeList.get(i)));
        }

        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab居中显示
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        //tabLayout 设置分割线和高度
        LinearLayout linearLayout = (LinearLayout) tabLayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerPadding(Utils.dip2px(this, 10));
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tablayout_line));

        adapter = new FootBallPagerAdapter(getSupportFragmentManager(), fragmentList, typeList);
        vpMain.setAdapter(adapter);
        tabLayout.setupWithViewPager(vpMain);


        spannable = new SpannableStringBuilder(sumString);
        spannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.red_heavy)), 14, sumString.length() - 2
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        explainText.setText(spannable);
        nextText.setText("至少选择1个号码");

        GetNewData();
    }

    @OnClick({R.id.left_image_include, R.id.right_image_include, R.id.random_text, R.id.next_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.right_image_include:
                popupWindow.showAsDropDown(rightImageInclude, 0, -Utils.dip2px(this, 20));
                break;
            case R.id.random_text:
                GetRandomDirectly();
                break;
            case R.id.next_text:
                next();
                break;
        }
    }

    private void GetRandomDirectly() {
        Random rand = new Random();
        if (currentPosition == 0) {
            SumFragment.getFragment().SetRandom(rand.nextInt(16));
        } else if (currentPosition == 1) {
            ThreeSameFragment.getFragment().SetRandom(rand.nextInt(6));
        } else if (currentPosition == 2) {
            int number = rand.nextInt(6);
            int number2 = rand.nextInt(6);
            if (number2 == number) {
                if (number2 > 4) {
                    number2 = rand.nextInt(4);
                } else {
                    number2 = number2 + 1;
                }
            }
            TwoSameFragment.getFragment().SetRandom(number, number2);
        } else if (currentPosition == 3) {
            ThreeNotSameFragment.getFragment().SetRandom(Utils.randomCommon(1, 6, 3));
        } else if (currentPosition == 4) {
            int number = rand.nextInt(6);
            int number2 = rand.nextInt(6);
            if (number2 == number) {
                if (number2 > 4) {
                    number2 = rand.nextInt(4);
                } else {
                    number2 = number2 + 1;
                }
            }
            TwoNotSameFragment.getFragment().SetRandom(number, number2);
        }
    }


    private void GetNewData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Lottery);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("code", "jlk3");
        XUtil.get(requestParams, FastThreeActivity.this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("====GetNewData====" + result);
                try {
                    if (new JSONObject(result).getString("code").equals("0")) {
                        expect = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("expect");
                        openCode = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("openCode");
                        openTime = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("openTime");
                        openTimestamp = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("openTimestamp");
                        expireTime = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("expireTime");
                        code = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("code");
                        now = new JSONObject(result).optString("now");

                        expectText.setText("距离" + (Long.valueOf(expect) + 1) + "期截止");

                        try {
                            Date date1 = simpleDateFormat.parse(openTime);

                            if (now.equals("")) {
                                SetTime(date1.getTime() + 1000 * 60 * 10 - System.currentTimeMillis());
                                System.out.println("====now==1==" + now);
                            } else {
                                SetTime(date1.getTime() + 1000 * 60 * 10 - Long.valueOf(now));
                            }


                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                    } else {
                        Utils.showToast(FastThreeActivity.this, new JSONObject(result).getString("msg"));
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


    private void SetTime(long no) {
        time = new TimeCount(no, 1000);
        time.start();
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            timeText.setText("00:00");
            GetNewData();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
//            timeText.setText(millisUntilFinished / 1000 + "秒");
            simpleDateFormat2.format(new Date(millisUntilFinished));
            timeText.setText(simpleDateFormat2.format(new Date(millisUntilFinished)));
        }
    }

    private void next() {
        FastThreeEmpty fastThreeEmpty = new FastThreeEmpty();
        Intent intent = new Intent();
        intent.setClass(this, FastThreePayActivity.class);
        intent.putExtra("expect", expect);
        intent.putExtra("openTime", openTime);
        if (currentPosition == 0) {
            if (SumFragment.dataList_choose.size() > 0) {
                if (status.equals("")) {
                    fastThreeEmpty.setStatus(0);
                    fastThreeEmpty.setSumList(SumFragment.dataList_choose);
                    intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                    startActivity(intent);
                    finish();
                } else {
                    fastThreeEmpty.setStatus(0);
                    fastThreeEmpty.setSumList(SumFragment.dataList_choose);
                    intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                    setResult(Code.THIRD_D_CODE, intent);
                    finish();
                }
            }

        } else if (currentPosition == 1) {
            if (ThreeSameFragment.numberList.size() < 1 && ThreeSameFragment.isTongXuan == 0) {

            } else {

                if (ThreeSameFragment.numberList.size() > 0 && ThreeSameFragment.isTongXuan == 1) {
                    fastThreeEmpty.setStatus(12);
                    List<String> evenList = new ArrayList<>();
                    evenList.add(ThreeSameFragment.fastList.get(0).getNumber());
                    fastThreeEmpty.setThreeEvenList(evenList);
                    fastThreeEmpty.setThreeSingleList(ThreeSameFragment.numberList);
                } else if (ThreeSameFragment.isTongXuan == 0) {
                    fastThreeEmpty.setStatus(10);
                    fastThreeEmpty.setThreeSingleList(ThreeSameFragment.numberList);
                } else {
                    fastThreeEmpty.setStatus(11);
                    List<String> evenList = new ArrayList<>();
                    evenList.add(ThreeSameFragment.fastList.get(0).getNumber());
                    fastThreeEmpty.setThreeEvenList(evenList);
                }

                if (status.equals("")) {
                    intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                    startActivity(intent);
                    finish();
                } else {
                    intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                    setResult(Code.THIRD_D_CODE, intent);
                    finish();
                }
            }

        } else if (currentPosition == 2) {

            if ((TwoSameFragment.twoSingleList.size() > 0 && TwoSameFragment.twoSameList.size() > 0) || TwoSameFragment.twoCheckList.size() > 0) {

                if (TwoSameFragment.twoSingleList.size() > 0 && TwoSameFragment.twoSameList.size() > 0 && TwoSameFragment.twoCheckList.size() > 0) {
                    fastThreeEmpty.setStatus(22);
                    fastThreeEmpty.setTwoSingleList(TwoSameFragment.twoSingleList);
                    fastThreeEmpty.setTwoSameList(TwoSameFragment.twoSameList);
                    fastThreeEmpty.setTwoCheckList(TwoSameFragment.twoCheckList);
                    if (status.equals("")) {
                        intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                        startActivity(intent);
                        finish();
                    } else {
                        intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                        setResult(Code.THIRD_D_CODE, intent);
                        finish();
                    }
                } else if (TwoSameFragment.twoCheckList.size() <= 0) {
                    fastThreeEmpty.setStatus(20);
                    fastThreeEmpty.setTwoSingleList(TwoSameFragment.twoSingleList);
                    fastThreeEmpty.setTwoSameList(TwoSameFragment.twoSameList);
                    if (status.equals("")) {
                        intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                        startActivity(intent);
                        finish();
                    } else {
                        intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                        setResult(Code.THIRD_D_CODE, intent);
                        finish();
                    }
                } else {
                    if (TwoSameFragment.twoSingleList.size() <= 0 && TwoSameFragment.twoSameList.size() <= 0) {
                        fastThreeEmpty.setStatus(21);
                        fastThreeEmpty.setTwoCheckList(TwoSameFragment.twoCheckList);

                        if (status.equals("")) {
                            intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                            startActivity(intent);
                            finish();
                        } else {
                            intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                            setResult(Code.THIRD_D_CODE, intent);
                            finish();
                        }
                    } else {
                        Utils.showToast(this, "至少选择1个同号1个不同号");
                    }
                }


            }

        } else if (currentPosition == 3) {
            if (ThreeNotSameFragment.numberList.size() > 2 || ThreeNotSameFragment.isTongXuan) {
                if (ThreeNotSameFragment.numberList.size() > 2 && ThreeNotSameFragment.isTongXuan) {
                    fastThreeEmpty.setStatus(32);
                    fastThreeEmpty.setThreeNotList(ThreeNotSameFragment.numberList);
                    List<String> evenList = new ArrayList<>();
                    evenList.add(ThreeNotSameFragment.fastList.get(0).getNumber());
                    fastThreeEmpty.setThreeNotEvenList(evenList);

                    if (status.equals("")) {
                        intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                        startActivity(intent);
                        finish();
                    } else {
                        intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                        setResult(Code.THIRD_D_CODE, intent);
                        finish();
                    }
                } else if (ThreeNotSameFragment.isTongXuan) {

                    if (ThreeNotSameFragment.numberList.size() == 0) {
                        fastThreeEmpty.setStatus(31);
                        List<String> evenList = new ArrayList<>();
                        evenList.add(ThreeNotSameFragment.fastList.get(0).getNumber());
                        fastThreeEmpty.setThreeNotEvenList(evenList);

                        if (status.equals("")) {
                            intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                            startActivity(intent);
                            finish();
                        } else {
                            intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                            setResult(Code.THIRD_D_CODE, intent);
                            finish();
                        }
                    } else {
                        Utils.showToast(this, "至少选择3个不同号");
                    }

                } else {

                    fastThreeEmpty.setStatus(30);
                    fastThreeEmpty.setThreeNotList(ThreeNotSameFragment.numberList);
                    if (status.equals("")) {
                        intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                        startActivity(intent);
                        finish();
                    } else {
                        intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                        setResult(Code.THIRD_D_CODE, intent);
                        finish();
                    }
                }

            }
        } else if (currentPosition == 4) {
            if (TwoNotSameFragment.numberList.size() > 1) {
                fastThreeEmpty.setStatus(40);
                fastThreeEmpty.setTwoNotList(TwoNotSameFragment.numberList);

                if (status.equals("")) {
                    intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                    startActivity(intent);
                    finish();
                } else {
                    intent.putExtra("fastThreeEmpty", fastThreeEmpty);
                    setResult(Code.THIRD_D_CODE, intent);
                    finish();
                }
            }
        }


    }
}
