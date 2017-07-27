package com.small.small.goal.my.guess.elevenchoosefive.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.guess.LotteryExplainActivity;
import com.small.small.goal.my.guess.elevenchoosefive.adapter.ChooseOvalAdapter;
import com.small.small.goal.my.guess.elevenchoosefive.entity.ChooseOvalEntity;
import com.small.small.goal.my.guess.elevenchoosefive.entity.HlvEntity;
import com.small.small.goal.my.guess.elevenchoosefive.entity.NewsResultEntity;
import com.small.small.goal.my.guess.elevenchoosefive.entity.OldResult;
import com.small.small.goal.my.guess.elevenchoosefive.entity.WinDaletouNumberEntity;
import com.small.small.goal.my.guess.fastthree.FastThreeActivity;
import com.small.small.goal.my.guess.note.activity.LotteryNoteActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Combine;
import com.small.small.goal.utils.DateUtil;
import com.small.small.goal.utils.GameUtils;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.dialog.LotteryTopPopuwindow;
import com.small.small.goal.weight.MyGridView;

import org.xutils.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/28 15:52
 * 修改：
 * 描述：11选5的首页
 **/
public class ChooseMainActivity extends BaseActivity {

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
    @InjectView(R.id.banner)
    ImageView banner;
    @InjectView(R.id.tal)
    TabLayout tal;
    @InjectView(R.id.tv_expect)
    TextView tvExpect;
    @InjectView(R.id.tv_expectTime)
    TextView tvExpectTime;
    @InjectView(R.id.lv)
    ListView lv;
    @InjectView(R.id.tv_oldWin)
    TextView tvOldWin;
    @InjectView(R.id.tv_chooseNumber)
    TextView tvChooseNumber;
    @InjectView(R.id.tv_missNums)
    TextView tvMissNums;
    @InjectView(R.id.mgv)
    MyGridView mgv;
    @InjectView(R.id.mgv2)
    MyGridView mgv2;
    @InjectView(R.id.mgv3)
    MyGridView mgv3;
    @InjectView(R.id.content_text)
    TextView contentText;
    @InjectView(R.id.tv_winDou)
    TextView tvWinDou;
    @InjectView(R.id.tv_random)
    TextView tvRandom;
    @InjectView(R.id.tv_touzhu)
    TextView tvTouzhu;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;
    //    private HlvAdapter hlvAdapter;
    private ChooseOvalAdapter mgvAdapter;
    private int min = 1;   //至少选择的
    private LotteryTopPopuwindow popupWindow;
    private ChooseOvalAdapter mgvAdapter2;
    private ChooseOvalAdapter mgvAdapter3;
    private Timer timer;
    private NewsResultEntity newsResultEntity;
    private long newMinute;
    private long second;
    private List<HlvEntity> talData;
    //    private List<ChooseOvalEntity> mgvSelectedData3;   //当前选中的集合
//    private List<ChooseOvalEntity> mgvSelectedData2;
//    private List<ChooseOvalEntity> mgvSelectedData1;

    int position;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_choose_main);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (mgvAdapter != null) {
            mgvAdapter.releaseData();
        }
        if (mgvAdapter2 != null) {
            mgvAdapter2.releaseData();
        }
        if (mgvAdapter3 != null) {
            mgvAdapter3.releaseData();
        }
        getData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mgvAdapter != null) {
            mgvAdapter.releaseData();
        }
        if (mgvAdapter2 != null) {
            mgvAdapter2.releaseData();
        }
        if (mgvAdapter3 != null) {
            mgvAdapter3.releaseData();
        }
//        if (mgvSelectedData1 != null) {
//            mgvSelectedData1.clear();
//            mgvSelectedData2.clear();
//            mgvSelectedData3.clear();
//        }
    }

    @Override
    public void initData() {
        super.initData();
        position = 0;
        setDrawableImg(R.mipmap.down);
        rightImageInclude.setImageResource(R.mipmap.icon_lottery_menu);
        nameTextInclude.setText("11选5");
        initTal();
        initMgv();
        initWinlv();
        popupWindow = new LotteryTopPopuwindow(this);
        popupWindow.setListener(new LotteryTopPopuwindow.setOnclickListener() {
            @Override
            public void onGuizeClick() {
                Intent intent = new Intent(ChooseMainActivity.this, LotteryExplainActivity.class);
                intent.putExtra("url", Url.UrlLottery + "sd11x5");
                startActivity(intent);
            }

            @Override
            public void onJiluClick() {
                startActivity(new Intent(ChooseMainActivity.this, LotteryNoteActivity.class));
                popupWindow.dismiss();
            }

            @Override
            public void onFollowClick() {

            }

            @Override
            public void onShareClick() {

            }
        });

        tvTouzhu.setOnClickListener(this);
        tvTouzhu.setClickable(false);
        tvRandom.setOnClickListener(this);
        tvOldWin.setOnClickListener(this);
        rightImageInclude.setOnClickListener(this);
    }


    @Override
    public void getData() {
        super.getData();
        getNewsResult();
    }

    /**
     * 获取最新开奖
     * create  wjz
     **/
    private void getNewsResult() {
        RequestParams requestParams = new RequestParams(Url.Url + "/lottery");
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("code", "sd11x5");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                Log.v("TAG", result);
                if (!GameUtils.CallResultOK(result)) return;
                Gson gson = new Gson();
                List<NewsResultEntity> data = gson.fromJson(GameUtils.getResultStr(result), new TypeToken<List<NewsResultEntity>>() {
                }.getType());
                if (data != null && data.size() > 0) {

                    try {
                        newsResultEntity = data.get(0);
                        long expireTimeLong = DateUtil.stringToLong(newsResultEntity.getOpenTime(), "yyyy-MM-dd HH:mm:ss");
                        long l = new Date().getTime() - expireTimeLong;
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
                        Log.v("TAG", "time    " + df.format(new Date()));// new Date()为获取当前系统时间
                        long minute = l / 60000;
                        newMinute = 10 - minute % 10;
                        second = 60 - l % 60000 / 1000;
                        Log.v("TAG", "minute---- " + minute + " new----- " + newMinute + "second----- " + second);
                        setNewResult(newsResultEntity.getExpect(), minute, second);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
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

    private void setNewResult(String expect, long minute, long second) {
        tvExpect.setText((Integer.valueOf(expect) + 2) + "");
        //    tvExpectTime.setText();

        timer = new Timer(true);
        timer.schedule(task, 0, 1000); //延时1000ms后执行，1000ms执行一次
    }


    TimerTask task = new TimerTask() {


        public void run() {
            Message message = new Message();
            message.what = 1;
            handler.sendMessage(message);

        }
    };

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            if (second == 0) {
                second = 59;
                newMinute--;
            } else {
                second--;
            }

            tvExpectTime.setText((newMinute >= 10 ? newMinute : "0" + newMinute) + ":" + (second >= 10 ? second : "0" + second));
            tvExpect.setText((Long.valueOf(newsResultEntity.getExpect()) + 1) + "");
            //   tvScoll.setText("距离第" + newsResultEntity.getExpect() + "期截止" + (newMinute >= 10 ? newMinute : "0" + newMinute) + ":" + (second >= 10 ? second : "0" + second));
            return false;
        }
    });


    /**
     * 初始化三个gridview
     * create  wjz
     **/
    private void initMgv() {
        mgvAdapter = new ChooseOvalAdapter(this, ChooseOvalAdapter.ELEVEN_CHOOSE);
        mgvAdapter.setMin(1);
        mgv.setAdapter(mgvAdapter);

        final List<ChooseOvalEntity> mgvData = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {

            int[] ints = Utils.randomJ(1, 30, 1);
            mgvData.add(new ChooseOvalEntity(i > 9 ? i + "" : "0" + i, false, ints[0]));

        }
        mgvAdapter.setData(mgvData);


        mgvAdapter2 = new ChooseOvalAdapter(this, ChooseOvalAdapter.ELEVEN_CHOOSE);
        mgvAdapter2.setMin(1);
        mgv2.setAdapter(mgvAdapter2);
        final List<ChooseOvalEntity> mgvData2 = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {

            int[] ints = Utils.randomJ(1, 30, 1);
            mgvData2.add(new ChooseOvalEntity(i > 9 ? i + "" : "0" + i, false, ints[0]));

        }
        mgvAdapter2.setData(mgvData2);

        mgvAdapter3 = new ChooseOvalAdapter(this, ChooseOvalAdapter.ELEVEN_CHOOSE);
        mgvAdapter3.setMin(1);
        mgv3.setAdapter(mgvAdapter3);
        final List<ChooseOvalEntity> mgvData3 = new ArrayList<>();
        for (int i = 1; i <= 11; i++) {

            int[] ints = Utils.randomJ(1, 30, 1);
            mgvData3.add(new ChooseOvalEntity(i > 9 ? i + "" : "0" + i, false, ints[0]));

        }
        mgvAdapter3.setData(mgvData3);


        mgvAdapter.setOnOvalClickListener(new ChooseOvalAdapter.onOvalClickListener() {
            @Override
            public void onclick(int flag) {
                for (ChooseOvalEntity one : mgvData) {
                    for (ChooseOvalEntity two : mgvData2) {
                        if (one.getContent().equals(two.getContent())) {
                            if (one.isSelected())
                                two.setSelected(false);
                        }
//                        if (two.isSelected() && one.getContent().equals("01")) {
//                            mgvSelectedData2.add(two);
//                        }
                    }
                    for (ChooseOvalEntity three : mgvData3) {
                        if (one.getContent().equals(three.getContent())) {
                            if (one.isSelected()) three.setSelected(false);
                        }
//                        if (three.isSelected() && one.getContent().equals("01")) {
//                            mgvSelectedData3.add(three);
//                        }
                    }
//                    if (one.isSelected())
//                        mgvSelectedData1.add(one);
                }
                mgvAdapter2.notifyDataSetChanged();
                mgvAdapter3.notifyDataSetChanged();
                setTouZhu(flag);
            }
        });
        mgvAdapter2.setOnOvalClickListener(new ChooseOvalAdapter.onOvalClickListener() {
            @Override
            public void onclick(int flag) {

                for (ChooseOvalEntity two : mgvData2) {
                    for (ChooseOvalEntity one : mgvData) {
                        if (one.getContent().equals(two.getContent())) {
                            if (two.isSelected()) one.setSelected(false);
                        }

                    }
                    for (ChooseOvalEntity three : mgvData3) {
                        if (two.getContent().equals(three.getContent())) {
                            if (two.isSelected())
                                three.setSelected(false);
                        }

                    }
                }
                mgvAdapter.notifyDataSetChanged();
                mgvAdapter3.notifyDataSetChanged();
                setTouZhu(flag);
            }
        });
        mgvAdapter3.setOnOvalClickListener(new ChooseOvalAdapter.onOvalClickListener() {
            @Override
            public void onclick(int flag) {
                for (ChooseOvalEntity three : mgvData3) {
                    for (ChooseOvalEntity two : mgvData2) {
                        if (three.getContent().equals(two.getContent())) {
                            if (three.isSelected())
                                two.setSelected(false);
                        }

                    }
                    for (ChooseOvalEntity one : mgvData) {
                        if (one.getContent().equals(three.getContent())) {
                            if (three.isSelected()) one.setSelected(false);
                        }
                    }
                }
                mgvAdapter2.notifyDataSetChanged();
                mgvAdapter.notifyDataSetChanged();
                setTouZhu(flag);
            }
        });

    }

    private void setTouZhu(int flag) {
        int d1 = 0;
        int d2 = 0;
        int d3 = 0;
        for (ChooseOvalEntity entity : mgvAdapter.getData()) {
            if (entity.isSelected()) d1++;
        }

        for (ChooseOvalEntity entity : mgvAdapter2.getData()) {
            if (entity.isSelected()) d2++;
        }

        for (ChooseOvalEntity entity : mgvAdapter3.getData()) {
            if (entity.isSelected()) d3++;
        }

        if (flag == ChooseOvalAdapter.ELEVEN_CHOOSE_ZHI_2) {
            if (d1 > 0 && d2 > 0) {
                tvTouzhu.setText("您选择了" + (d1 * d2) + "注");
                tvTouzhu.setClickable(true);
            } else {
                tvTouzhu.setText("您选择了0注");
                tvTouzhu.setClickable(false);
            }
        } else {
            if (d1 > 0 && d2 > 0 && d3 > 0) {

                tvTouzhu.setText("您选择了" + (d1 * d2 * d3) + "注");
                tvTouzhu.setClickable(true);
            } else {
                tvTouzhu.setText("您选择了0注");
                tvTouzhu.setClickable(false);
            }
        }
    }

    /**
     * 初始化tablayout
     * create  wjz
     **/
    private void initTal() {
        talData = new ArrayList<>();
        talData.add(new HlvEntity("        前一        ", true, 1, 190));
        talData.add(new HlvEntity("        任二        ", false, 2, 90));
        talData.add(new HlvEntity("        任三        ", false, 3, 280));
        talData.add(new HlvEntity("        任四        ", false, 4, 1140));
        talData.add(new HlvEntity("        任五        ", false, 5, 7900));
        talData.add(new HlvEntity("        任六        ", false, 6, 1300));
        talData.add(new HlvEntity("        任七        ", false, 7, 380));
        talData.add(new HlvEntity("        任八        ", false, 8, 135));
        talData.add(new HlvEntity("        前二直选        ", false, 9, 1900));
        talData.add(new HlvEntity("        前二组选        ", false, 10, 950));
        talData.add(new HlvEntity("        前三直选        ", false, 11, 17100));
        talData.add(new HlvEntity("        前三组选        ", false, 12, 2850));

        WindowManager windowManager = getWindowManager();
        Display windowDisplay = windowManager.getDefaultDisplay();
        for (HlvEntity entity : talData) {
            tal.addTab(tal.newTab().setText(entity.getContent()));
        }
        for (int i = 0; i < tal.getChildCount(); i++) {
            tal.getChildAt(i).setLayoutParams(new FrameLayout.LayoutParams((int) (windowDisplay.getWidth() / 3), LinearLayout.LayoutParams.FILL_PARENT));
        }

        tal.setTabMode(TabLayout.MODE_SCROLLABLE);
        //tab居中显示
        tal.setTabGravity(TabLayout.GRAVITY_CENTER);
        //tab的字体选择器,默认黑色,选择时红色
        tal.setTabTextColors(Color.WHITE, Color.RED);
        //tab的下划线颜色,默认是粉红色
        tal.setSelectedTabIndicatorColor(Color.RED);
//tablayout 设置分割线和高度
        LinearLayout linearLayout = (LinearLayout) tal.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerPadding(Utils.dip2px(this, 10));
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this,
                R.drawable.tablayout_line));
        tal.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                if (position != tab.getPosition()) {

                    position = tab.getPosition();
                    mgvAdapter.setFlag(ChooseOvalAdapter.ELEVEN_CHOOSE);
                    mgvAdapter2.setFlag(ChooseOvalAdapter.ELEVEN_CHOOSE);
                    mgvAdapter3.setFlag(ChooseOvalAdapter.ELEVEN_CHOOSE);
                    mgv2.setVisibility(View.GONE);
                    mgv3.setVisibility(View.GONE);
                    if (position == 8) {
                        mgv2.setVisibility(View.VISIBLE);
                        mgvAdapter.setFlag(ChooseOvalAdapter.ELEVEN_CHOOSE_ZHI_2);
                        mgvAdapter2.setFlag(ChooseOvalAdapter.ELEVEN_CHOOSE_ZHI_2);
                        mgvAdapter3.setFlag(ChooseOvalAdapter.ELEVEN_CHOOSE_ZHI_2);
                    } else if (position == 10) {
                        mgv3.setVisibility(View.VISIBLE);
                        mgv2.setVisibility(View.VISIBLE);
                        mgvAdapter.setFlag(ChooseOvalAdapter.ELEVEN_CHOOSE_ZHI_3);
                        mgvAdapter2.setFlag(ChooseOvalAdapter.ELEVEN_CHOOSE_ZHI_3);
                        mgvAdapter3.setFlag(ChooseOvalAdapter.ELEVEN_CHOOSE_ZHI_3);
                    }

                    if (position == 0) {
                        contentText.setText("至少选1个号码，猜中第1个开奖号码，");

                    } else if (position == 1) {
                        contentText.setText("至少选2个号码，任意猜中2个号码，");
                    } else if (position == 2) {
                        contentText.setText("至少选3个号码，任意猜中3个号码，");
                    } else if (position == 3) {
                        contentText.setText("至少选4个号码，任意猜中4个号码，");
                    } else if (position == 4) {
                        contentText.setText("至少选5个号码，全部猜中5个号码，");
                    } else if (position == 5) {
                        contentText.setText("至少选6个号码，选号包含5个开奖号，");
                    } else if (position == 6) {
                        contentText.setText("至少选7个号码，选号包含5个开奖号，");
                    } else if (position == 7) {
                        contentText.setText("至少选8个号码，选号包含5个开奖号，");
                    } else if (position == 8) {
                        contentText.setText("每位至少选1个号码，按位猜中前2个号码，");
                    } else if (position == 9) {
                        contentText.setText("至少选2个号码，猜中前2个号码，");
                    } else if (position == 10) {
                        contentText.setText("每位至少选1个号码，按位猜中前3个号码，");
                    } else if (position == 11) {
                        contentText.setText("至少选3个号码，猜中前3个号码，");
                    }


                    HlvEntity hlvEntity = talData.get(position);
                    talData.get(position).setSelected(true);
                    //     hlvAdapter.notifyDataSetChanged();
                    min = hlvEntity.getMin();

                    tvTouzhu.setClickable(false);
                    tvWinDou.setText(hlvEntity.getWinNums() + "");
                    tvTouzhu.setText("至少选" + hlvEntity.getMin() + "个号码");
                    mgvAdapter.setMin(hlvEntity.getMin());

                    int selected = 0;
                    for (ChooseOvalEntity one : mgvAdapter.getData()) {
                        if (one.isSelected()) {
                            selected++;
                        }
                    }
                    int selected2 = 0;
                    for (ChooseOvalEntity one : mgvAdapter2.getData()) {
                        if (one.isSelected()) {
                            selected2++;
                        }
                    }
                    int selected3 = 0;
                    for (ChooseOvalEntity one : mgvAdapter3.getData()) {
                        if (one.isSelected()) {
                            selected3++;
                        }
                    }

                    switch (hlvEntity.getMin()) {
                        case 1:
                        case 2:
                        case 3:
                        case 4:
                        case 5:
                        case 6:
                        case 7:
                        case 8:
                            if (selected >= hlvEntity.getMin()) {
                                tvTouzhu.setText("共" + Combine.getNumber(hlvEntity.getMin(), selected) + "注，下一步");
                                tvTouzhu.setClickable(true);
                            }
                            break;
                        case 9:
                            if (selected > 0 && selected2 > 0) {
                                tvTouzhu.setText("共" + (selected * selected2) + "注，下一步");
                                tvTouzhu.setClickable(true);
                            } else {
                                tvTouzhu.setText("每位至少选1个号码");
                                tvTouzhu.setClickable(false);
                            }
                            break;
                        case 10:
                            if (selected >= 2) {
                                tvTouzhu.setText("共" + Combine.getNumber(2, selected) + "注，下一步");
                                tvTouzhu.setClickable(true);
                            } else {
                                tvTouzhu.setText("每位至少选2个号码");
                                tvTouzhu.setClickable(false);
                            }
                            break;
                        case 11:
                            if (selected > 0 && selected2 > 0 && selected3 > 0) {
                                tvTouzhu.setText("共" + (selected * selected2 * selected3) + "注，下一步");
                                tvTouzhu.setClickable(true);
                            } else {
                                tvTouzhu.setText("每位至少选1个号码");
                                tvTouzhu.setClickable(false);
                            }
                            break;
                        case 12:
                            if (selected >= 3) {
                                tvTouzhu.setText("共" + Combine.getNumber(3, selected) + "注，下一步");
                                tvTouzhu.setClickable(true);
                            } else {
                                tvTouzhu.setText("每位至少选3个号码");
                                tvTouzhu.setClickable(false);
                            }
                            break;
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    /**
     * 初始化历史开奖
     * create  wjz
     **/
    private void initWinlv() {


        RequestParams requestParams = new RequestParams(Url.Url + "/lottery/newest");
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("code", "sd11x5");

        final List<Integer> blues = new ArrayList<>();
        final List<WinDaletouNumberEntity> oldWinData = new ArrayList<>();

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                Log.v("TAG", result);
                if (!GameUtils.CallResultOK(result)) return;
                Gson gson = new Gson();
                List<OldResult> data = gson.fromJson(GameUtils.getResultStr(result), new TypeToken<List<OldResult>>() {
                }.getType());

                List<Integer> reds = new ArrayList<>();
                for (int i = 0; i < data.size(); i++) {
                    OldResult oldResult = data.get(i);
                    String[] split = oldResult.getOpenCode().split(",");
                    reds = new ArrayList<Integer>();
                    for (int y = 0; y < split.length; y++) {
                        reds.add(Integer.valueOf(split[y]));
                    }
                    oldWinData.add(new WinDaletouNumberEntity(oldResult.getExpect(), reds, blues));
                }
                lv.setAdapter(new LvAdapter(oldWinData, ChooseMainActivity.this));
                Utils.setListViewHeightBasedOnChildren(lv);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });


//        for (int i = 1; i < 6; i++) {
//            reds.clear();
//            int[] winArray = PlayAddMoneyActivity.randomJ(1, 11, 5);
//
//            for (int y = 0; y < winArray.length; y++) {
//                reds.add(winArray[y]);
//            }
//
//            oldWinData.add(new WinDaletouNumberEntity(i + "", reds, blues));
//        }

    }


    @Override
    public void onClick(View v) {
        List<ChooseOvalEntity> data;
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_random:

                setRandom();

                break;
            case R.id.tv_touzhu:
                addEleven(mgvAdapter.getData());
                Intent intent = new Intent(this, ChooseAddMoneyActivity.class);
                intent.putExtra(KeyCode.INTENT_MIN, min);
                intent.putExtra("expect", newsResultEntity.getExpect());
                intent.putExtra("openTime", newsResultEntity.getOpenTime());
                startActivity(intent);
                finish();
                break;
            case R.id.tv_oldWin:
                if (lv.getVisibility() == View.VISIBLE) {
                    tvOldWin.setText("点击查看历史开奖");
                    lv.setVisibility(View.GONE);
                    setDrawableImg(R.mipmap.down);
                } else {
                    lv.setVisibility(View.VISIBLE);
                    tvOldWin.setText("点击收起");
                    setDrawableImg(R.mipmap.up);
                }
                break;
            case R.id.right_image_include:

                popupWindow.showAsDropDown(rightImageInclude, 0, -Utils.dip2px(this, 20));
                break;
        }
    }

    private void setRandom() {
        List<ChooseOvalEntity> data;


        if (min == 9) {
            int[] ints = Utils.randomJ(1, 11, 2);
            mgvAdapter.releaseData();
            mgvAdapter.getData().get(ints[0] - 1).setSelected(true);
            mgvAdapter.notifyDataSetChanged();

            mgvAdapter2.releaseData();
            mgvAdapter2.getData().get(ints[1] - 1).setSelected(true);
            mgvAdapter2.notifyDataSetChanged();

//            mgvSelectedData1.add(mgvAdapter.getData().get(ints[0] - 1));
//            mgvSelectedData2.add(mgvAdapter.getData().get(ints[1] - 1));

            tvTouzhu.setText("共1注，下一步");
            tvTouzhu.setClickable(true);
        } else if (min == 11) {
            int[] ints = Utils.randomJ(1, 11, 3);
            mgvAdapter.releaseData();
            mgvAdapter.getData().get(ints[0] - 1).setSelected(true);
            mgvAdapter.notifyDataSetChanged();

            mgvAdapter2.releaseData();
            mgvAdapter2.getData().get(ints[1] - 1).setSelected(true);
            mgvAdapter2.notifyDataSetChanged();

            mgvAdapter3.releaseData();
            mgvAdapter3.getData().get(ints[2] - 1).setSelected(true);
            mgvAdapter3.notifyDataSetChanged();

//            mgvSelectedData1.add(mgvAdapter.getData().get(ints[0] - 1));
//            mgvSelectedData2.add(mgvAdapter.getData().get(ints[1] - 1));
//            mgvSelectedData3.add(mgvAdapter.getData().get(ints[2] - 1));

            tvTouzhu.setText("共1注，下一步");
            tvTouzhu.setClickable(true);
        } else {
            int[] ints;
            if (min == 10) {
                ints = Utils.randomJ(1, 11, 2);
            } else if (min == 12) {
                ints = Utils.randomJ(1, 11, 3);
            } else {
                ints = Utils.randomJ(1, 11, min);
            }


            data = mgvAdapter.getData();
            mgvAdapter.releaseData();
            for (int i = 0; i < ints.length; i++) {
                data.get(ints[i] - 1).setSelected(true);
            }
            mgvAdapter.notifyDataSetChanged();
            tvTouzhu.setText("共1注，下一步");
            tvTouzhu.setClickable(true);
        }
    }

    /**
     * 设置textview的drawableLeft等图片的大小
     * create  wjz
     **/
    private void setDrawableImg(int mipRes) {
        Drawable drawable = getResources().getDrawable(mipRes);
/// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, Utils.dip2px(this, 12), Utils.dip2px(this, 5));
        tvOldWin.setCompoundDrawables(null, null, drawable, null);
    }

    /**
     * 将选择的数字 添加到单例缓存中
     * create  wjz
     **/

    public void addEleven(List<ChooseOvalEntity> data) {
        Map<Integer, List<ChooseOvalEntity>> map = new HashMap<>();
        if (mgvAdapter.getFlag() == ChooseOvalAdapter.ELEVEN_CHOOSE) {
            List<ChooseOvalEntity> selectedData = new ArrayList<>();
            for (ChooseOvalEntity one : data) {
                if (one.isSelected()) {
                    selectedData.add(one);
                }
            }
            map.put(min, selectedData);
        } else if (mgvAdapter.getFlag() == ChooseOvalAdapter.ELEVEN_CHOOSE_ZHI_2) {
            List<ChooseOvalEntity> selectedData = new ArrayList<>();

            List<ChooseOvalEntity> data1 = new ArrayList<>();
            List<ChooseOvalEntity> data2 = new ArrayList<>();
            for (ChooseOvalEntity entity : mgvAdapter.getData()) {
                if (entity.isSelected()) data1.add(entity);
            }
            for (ChooseOvalEntity entity : mgvAdapter2.getData()) {
                if (entity.isSelected()) data2.add(entity);
            }
            selectedData.addAll(data1);
            selectedData.add(new ChooseOvalEntity(",", false, 0));
            selectedData.addAll(data2);
            map.put(min, selectedData);
        } else {
            List<ChooseOvalEntity> selectedData = new ArrayList<>();

            List<ChooseOvalEntity> data1 = new ArrayList<>();
            List<ChooseOvalEntity> data2 = new ArrayList<>();
            List<ChooseOvalEntity> data3 = new ArrayList<>();
            for (ChooseOvalEntity entity : mgvAdapter.getData()) {
                if (entity.isSelected()) data1.add(entity);
            }
            for (ChooseOvalEntity entity : mgvAdapter2.getData()) {
                if (entity.isSelected()) data2.add(entity);
            }
            for (ChooseOvalEntity entity : mgvAdapter3.getData()) {
                if (entity.isSelected()) data3.add(entity);
            }

            selectedData.addAll(data1);
            selectedData.add(new ChooseOvalEntity(",", false, 0));
            selectedData.addAll(data2);
            selectedData.add(new ChooseOvalEntity(",", false, 0));
            selectedData.addAll(data3);
            map.put(min, selectedData);
        }
        CacheUtil.getInstance().addElevenChooseFive(map);
    }

    /**
     * 页面里面的开奖记录的listview 的适配器
     * create  wjz
     **/
    public static class LvAdapter extends BaseAdapter {

        private final List<WinDaletouNumberEntity> data;
        private final Context context;

        public LvAdapter(List<WinDaletouNumberEntity> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return this.data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder vh;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_win_nums, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            WinDaletouNumberEntity info = data.get(position);
            List<Integer> red = info.getReds();
            List<Integer> blue = info.getBlues();

            vh.tvQiNumsItem.setText(info.getQiNums() + "期");


            String str = "";
            for (Integer i : red) {
                if (i >= 10)
                    str += i + " ";
                else
                    str += "0" + i + " ";
            }
            for (int i = 0; i < blue.size(); i++) {
                Integer y = blue.get(i);
                if (y >= 10)
                    str += y;
                else
                    str += "0" + y;
                if (i == blue.size() - 1) {

                } else {
                    str += " ";
                }
            }
            SpannableStringBuilder builder = new SpannableStringBuilder(str);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            ForegroundColorSpan blueSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.blue));
            builder.setSpan(redSpan, 0, red.size() * 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(blueSpan, red.size() * 3, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            vh.tvSelectedItem.setText(builder);

            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.tv_qiNums_item)
            TextView tvQiNumsItem;
            @InjectView(R.id.tv_selected_item)
            TextView tvSelectedItem;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
