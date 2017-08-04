package com.small.small.goal.my.guess.twoColorBall;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.util.ArrayMap;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.dialog.HuiFuDialog;
import com.small.small.goal.my.entry.UerEntity;
import com.small.small.goal.my.gold.GoldPayActivity;
import com.small.small.goal.my.guess.elevenchoosefive.activity.ChooseAddMoneyActivity;
import com.small.small.goal.my.guess.elevenchoosefive.activity.ChooseMainActivity;
import com.small.small.goal.my.guess.elevenchoosefive.entity.ChooseOvalEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.Combine;
import com.small.small.goal.utils.EditTextHeightUtil;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.xutils.http.RequestParams;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/27 17:38
 * 修改：
 * 描述： 选完 下注多少的界面
 **/
public class PlayAddMoneyActivity extends BaseActivity {


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
    @InjectView(R.id.rl_iAdd)
    RelativeLayout rlIAdd;
    @InjectView(R.id.rl_jAdd)
    RelativeLayout rlJAdd;
    @InjectView(R.id.lv)
    ListView lv;
    @InjectView(R.id.tv_myDou)
    TextView tvMyDou;
    @InjectView(R.id.tv_zhui_hint)
    TextView tvZhuiHint;
    @InjectView(R.id.img_zhuiDelete)
    ImageView imgZhuiDelete;
    @InjectView(R.id.etv_zhuiNums)
    EditText etvZhuiNums;
    @InjectView(R.id.img_zhuiAdd)
    ImageView imgZhuiAdd;
    @InjectView(R.id.tv_qi_hint)
    TextView tvQiHint;
    @InjectView(R.id.tv_tou_hint)
    TextView tvTouHint;
    @InjectView(R.id.img_touDelete)
    ImageView imgTouDelete;
    @InjectView(R.id.etv_touNums)
    EditText etvTouNums;
    @InjectView(R.id.img_touAdd)
    ImageView imgTouAdd;
    @InjectView(R.id.tv_bei_hint)
    TextView tvBeiHint;
    @InjectView(R.id.tv_go)
    TextView tvGo;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;
    private List<Map<String, List<OvalEntity>>> data;
    private MyAdapter myAdapter;
    private int minRed;
    private int minBlue;
    private int zhuNums;
    private int maxRed;
    private int maxBlue;

    private String expect = "0";

    TextView bean_text;
    EditTextHeightUtil editTextHeightUtil;
    private UerEntity.AccountBean account;
    PopupWindow popupWindow;

    SpannableStringBuilder spannable;

    private String chongzhi_money = "";
    private String bean_pay = "";

    int tou;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_play_addmoney);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
        editTextHeightUtil = new EditTextHeightUtil(this, 1);
    }

    @Override
    public void initData() {
        super.initData();
        expect = getIntent().getExtras().getString("expect", "2017089");

        account = CacheUtil.getInstance().getUserInfo().getAccount();
        data = CacheUtil.getInstance().getSelectedShuangseqiuData();
        CacheUtil.getInstance().closeSelectedShuangseqiuRedData();
        CacheUtil.getInstance().closeSelectedShuangseqiuBlueData();
        nameTextInclude.setText("双色球");
        minRed = 6;
        minBlue = 1;
        maxRed = 33;
        maxBlue = 16;


        rightImageInclude.setVisibility(View.GONE);
        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);

        rlIAdd.setOnClickListener(this);
        rlJAdd.setOnClickListener(this);

        imgZhuiDelete.setOnClickListener(this);
        imgZhuiAdd.setOnClickListener(this);
        imgTouDelete.setOnClickListener(this);
        imgTouAdd.setOnClickListener(this);
        tvGo.setOnClickListener(this);


        etvTouNums.addTextChangedListener(new MyTextWatch(etvTouNums.getId()));
        etvZhuiNums.addTextChangedListener(new MyTextWatch(etvZhuiNums.getId()));

        setZhu();
        setTvGo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        data = CacheUtil.getInstance().getSelectedShuangseqiuData();
        CacheUtil.getInstance().closeSelectedShuangseqiuRedData();
        CacheUtil.getInstance().closeSelectedShuangseqiuBlueData();
    }

    private void setZhu() {
        zhuNums = 0;

        for (Map<String, List<OvalEntity>> one : data) {
            int red = one.get(KeyCode.MAP_RED).size();
            int blue = one.get(KeyCode.MAP_BLUE).size();
            long cRed = Combine.getNumber(minRed, red);
            long cBlue = Combine.getNumber(minBlue, blue);
            zhuNums += cBlue * cRed;
        }
        tvMyDou.setText("我的金豆：" + account.getBean());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_iAdd:
                startActivity(new Intent(this, TwoColorBallActivity.class));
                finish();
                break;
            case R.id.rl_jAdd:
                int[] reds = randomJ(1, maxRed, minRed);
                int[] blues = randomJ(1, maxBlue, minBlue);

                List<OvalEntity> blueList = new ArrayList<>();
                List<OvalEntity> redList = new ArrayList<>();
                for (int i = 0; i < reds.length; i++) {
                    int red = reds[i];
                    redList.add(new OvalEntity(0, red >= 10 ? red + "" : "0" + red, false));
                }
                for (int i = 0; i < blues.length; i++) {
                    int blue = blues[i];
                    blueList.add(new OvalEntity(1, blue >= 10 ? blue + "" : "0" + blue, false));
                }
                Map<String, List<OvalEntity>> map = new ArrayMap<>();
                map.put(KeyCode.MAP_RED, redList);
                map.put(KeyCode.MAP_BLUE, blueList);
                data.add(map);
                myAdapter.notifyDataSetChanged();
                setZhu();
                setTvGo();
                break;

            case R.id.img_zhuiDelete:
                if (etvZhuiNums.getText().toString().equals("1")) return;
                etvZhuiNums.setText((Integer.valueOf(etvZhuiNums.getText().toString()) - 1) + "");
                etvZhuiNums.setSelection(etvZhuiNums.getText().length());
                break;
            case R.id.img_zhuiAdd:
                etvZhuiNums.setText((Integer.valueOf(etvZhuiNums.getText().toString()) + 1) + "");
                etvZhuiNums.setSelection(etvZhuiNums.getText().length());
                break;
            case R.id.img_touDelete:
//                if (etvTouNums.getText().toString().equals("1")) return;
//                etvTouNums.setText((Integer.valueOf(etvTouNums.getText().toString()) - 1) + "");
//                etvTouNums.setSelection(etvTouNums.getText().length());

                if (etvTouNums.getText().toString().equals("")) {
                    etvTouNums.setText("1");
                    etvTouNums.setSelection(etvTouNums.getText().length());
                } else if (etvTouNums.getText().toString().equals("1")) {
//                    etvTouNums.setText("1");
//                    etvTouNums.setSelection(etvTouNums.getText().length());
                } else {
                    etvTouNums.setText((Integer.valueOf(etvTouNums.getText().toString()) - 1) + "");
                    etvTouNums.setSelection(etvTouNums.getText().length());
                }
                break;
            case R.id.img_touAdd:
//                etvTouNums.setText((Integer.valueOf(etvTouNums.getText().toString()) + 1) + "");
//                etvTouNums.setSelection(etvTouNums.getText().length());
                if (etvTouNums.getText().toString().equals("")) {
                    etvTouNums.setText("1");
                    etvTouNums.setSelection(etvTouNums.getText().length());
                } else {
                    etvTouNums.setText((Integer.valueOf(etvTouNums.getText().toString()) + 1) + "");
                    etvTouNums.setSelection(etvTouNums.getText().length());
                }
                break;
            case R.id.tv_go:
                if (data.size() > 0) {
                    postTouZhu(v);
                } else
                    Utils.showToast(this, "请添加投注");
                break;
        }
    }

    /**
     * 彩票投注
     * create  wjz
     **/
    private void postTouZhu(final View v) {


//        for (Map<Integer, List<ChooseOvalEntity>> one : data) {
//            Set<Integer> keys = one.keySet();
//
//            for (Integer i : keys) {
//                if (!one.get(i).get(0).getContent().equals(",")) {
//                    context += codes[i] + ":";
//                    List<ChooseOvalEntity> list = one.get(i);
//                    for (int y = 0; y < list.size(); y++) {
//                        if (list.get(y).getContent().equals(",")) {
//                            context += "+";
//                        } else if (y != list.size() - 1) {
//                            if (list.get(y + 1).getContent().equals(",")) {
//                                context += list.get(y).getContent();
//                            } else {
//                                context += list.get(y).getContent() + ",";
//                            }
//
//                        } else {
//                            context += list.get(y).getContent() + ";";
//                        }
//
//                    }
//                }
//            }
//        }
        String context = "";
        for (int i = 0; i < data.size(); i++) {
            context = context + "0:";
            Map<String, List<OvalEntity>> one = data.get(i);
            List<OvalEntity> red = one.get(KeyCode.MAP_RED);
            List<OvalEntity> blue = one.get(KeyCode.MAP_BLUE);
            for (int j = 0; j < red.size(); j++) {

                if (j == red.size() - 1) {
                    context = context + red.get(j).getContent() + "+";
                } else
                    context = context + red.get(j).getContent() + ",";
            }

            for (int j = 0; j < blue.size(); j++) {
                if (j == blue.size() - 1) {
                    context = context + blue.get(j).getContent();
                } else
                    context = context + blue.get(j).getContent() + ",";
            }

            if (i != data.size() - 1) {
                context = context + ";";
            }


        }

        System.out.println("===========context=========" + context);
        final Integer tou = Integer.valueOf(etvTouNums.getText().toString());
        final Integer zhui = Integer.valueOf(etvZhuiNums.getText().toString());
        RequestParams requestParams = new RequestParams(Url.Url + "/wager");
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("content", context);
        requestParams.addBodyParameter("number", "1");
        requestParams.addBodyParameter("multifold", tou + "");  //倍数
        requestParams.addBodyParameter("bean", (tou * zhui * zhuNums * 20) + "");          //金豆
        requestParams.addBodyParameter("linkId", "");
        requestParams.addBodyParameter("type", "4");
        requestParams.addBodyParameter("name", "双色球");
        requestParams.addBodyParameter("expect", (Long.valueOf(expect) + 1) + "");
        requestParams.addBodyParameter("code", "ssq");
        requestParams.addBodyParameter("rule", "");

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {


                System.out.println("============双色球============" + result);

                boolean b = Utils.callOk(result, PlayAddMoneyActivity.this);

                if (b) {
                    account.setBean((Integer.valueOf(account.getBean()) - (tou * zhui * zhuNums * 20)) + "");
                    tvMyDou.setText("我的金豆：" + account.getBean());
                    PutWindow(v);
                    CacheUtil.getInstance().closeSelectedShuangseqiuData();
                } else {
                    Utils.showToast(PlayAddMoneyActivity.this, Utils.getMsg(result));
                    if (Utils.getMsg(result).equals("金豆不足")) {
                        MoneyWindow(v);
                    }
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("========================" + ex.getMessage());
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

        content_text.setText("每周二、周四、周日21：15开奖");

        windowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                finish();
            }
        });

        next_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent(PlayAddMoneyActivity.this, TwoColorBallActivity.class);
                startActivity(intent);
                finish();
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
        TextView recharge7_text = (TextView) windowView.findViewById(R.id.recharge7_text);
        TextView recharge8_text = (TextView) windowView.findViewById(R.id.recharge8_text);
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
        recharge7_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeWindow(view, 7);
            }
        });
        recharge8_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RechargeWindow(view, 8);
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
            chongzhi_money = "1";
            bean_pay = "100";
            number_image.setImageResource(R.mipmap.icon_gold_bean_1);
        } else if (status == 2) {
            content = "本次充值您将花费6元";
            number_text.setText("600金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_2);
            chongzhi_money = "6";
            bean_pay = "600";
        } else if (status == 3) {
            content = "本次充值您将花费10元";
            number_text.setText("1000金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_3);
            chongzhi_money = "10";
            bean_pay = "1000";
        } else if (status == 4) {
            content = "本次充值您将花费30元";
            number_text.setText("3000金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_4);
            chongzhi_money = "30";
            bean_pay = "3000";
        } else if (status == 5) {
            content = "本次充值您将花费50元";
            number_text.setText("5000金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_4);
            chongzhi_money = "50";
            bean_pay = "5000";
        } else if (status == 6) {
            content = "本次充值您将花费100元";
            number_text.setText("10000金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_4);
            chongzhi_money = "100";
            bean_pay = "10000";
        } else if (status == 7) {
            content = "本次充值您将花费200元";
            number_text.setText("20000金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_4);
            chongzhi_money = "200";
            bean_pay = "20000";
        } else if (status == 8) {
            content = "本次充值您将花费500元";
            number_text.setText("50000金豆");
            number_image.setImageResource(R.mipmap.icon_gold_bean_4);
            chongzhi_money = "500";
            bean_pay = "50000";
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

        intent.putExtra("money", chongzhi_money);
        startActivityForResult(intent, Code.SupportAim);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Code.SupportAim) {
            account.setBean(Long.valueOf(account.getBean()) + Long.valueOf(bean_pay) + "");
            bean_text.setText(account.getBean());
            tvMyDou.setText("我的金豆：" + account.getBean());
        } else if (resultCode == Code.FailCode) {

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 生成随机数
     * create  wjz
     **/
    public static int[] randomJ(int min, int max, int nums) {

        int[] intRet = new int[nums];
        int intRd = 0; //存放随机数
        int count = 0; //记录生成的随机数个数
        int flag = 0; //是否已经生成过标志
        while (count < intRet.length) {
            intRd = min + (int) (Math.random() * max);
            for (int i = 0; i < count; i++) {
                if (intRet[i] == intRd) {
                    flag = 1;
                    break;
                } else {
                    flag = 0;
                }
            }
            if (flag == 0) {
                intRet[count] = intRd;
                count++;
            }
        }
        return intRet;
    }

    class MyTextWatch implements TextWatcher {

        private final int id;

        public MyTextWatch(int id) {
            this.id = id;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            switch (id) {
                case R.id.etv_touNums:
                    if (s.toString().length() > 0) {

                        if (s.toString().equals("0")) {
                            etvTouNums.setText("");
                        } else {
                            etvTouNums.setSelection(s.toString().length());
                            if (Integer.valueOf(s.toString()) > 2000) {
                                etvTouNums.setText(2000 + "");
                            }
                        }
                    }

                    break;
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (id) {
                case R.id.etv_touNums:
                    setTvGo();
                    break;
                case R.id.etv_zhuiNums:
                    setTvGo();
                    break;
            }
        }
    }

    private void setTvGo() {


        if ("".equals(etvTouNums.getText().toString())) {
            tou = 0;
        } else {
            tou = Integer.valueOf(etvTouNums.getText().toString());
        }

        tvGo.setText("立即投注  " + (tou * 1 * zhuNums * 20) + "金豆");


    }

    class MyAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder vh;
            if (convertView == null) {
                convertView = LayoutInflater.from(PlayAddMoneyActivity.this).inflate(R.layout.item_play_addmoney, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            Map<String, List<OvalEntity>> one = data.get(position);

            List<OvalEntity> red = one.get(KeyCode.MAP_RED);
            List<OvalEntity> blue = one.get(KeyCode.MAP_BLUE);

            vh.notesText.setText(Utils.combination(6, red.size()) * blue.size() + "注");
            String str = "";
            for (OvalEntity entity : red) {
                str += entity.getContent() + " ";
            }
            for (int i = 0; i < blue.size(); i++) {
                OvalEntity entity = blue.get(i);
                if (i == blue.size() - 1) {
                    str += entity.getContent();
                } else {
                    str += entity.getContent() + " ";
                }
            }


            SpannableStringBuilder builder = new SpannableStringBuilder(str);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            ForegroundColorSpan blueSpan = new ForegroundColorSpan(getResources().getColor(R.color.blue));
            builder.setSpan(redSpan, 0, red.size() * 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(blueSpan, red.size() * 3, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            vh.tvRed.setText(builder);

            vh.imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.remove(position);
                    notifyDataSetChanged();
                    setZhu();
                    setTvGo();
                }
            });

            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.tv_red)
            TextView tvRed;
            @InjectView(R.id.img_delete)
            ImageView imgDelete;
            @InjectView(R.id.notes_text)
            TextView notesText;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

}
