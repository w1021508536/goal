package com.small.small.goal.my.guess.elevenchoosefive.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.small.small.goal.my.entry.UerEntity;
import com.small.small.goal.my.gold.GoldPayActivity;
import com.small.small.goal.my.guess.elevenchoosefive.entity.ChooseOvalEntity;
import com.small.small.goal.my.guess.fastthree.FastThreeActivity;
import com.small.small.goal.my.guess.fastthree.FastThreePayActivity;
import com.small.small.goal.my.guess.fastthree.empty.FastThreeEmpty;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.Combine;
import com.small.small.goal.utils.EditTextHeightUtil;
import com.small.small.goal.utils.GameUtils;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.dialog.HuiFuDialog2;

import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class ChooseAddMoneyActivity extends BaseActivity {


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
    private List<Map<Integer, List<ChooseOvalEntity>>> data;
    private MyAdapter myAdapter;
    private int zhuNums;
    private HuiFuDialog2 dialog;
    private int min;
    public static final int MAX = 11;
    private String[] types;
    private int[] codes;            //彩票的代码
    //    private int intent_flag;
//    private int maxRed;
//    private int maxBlue;

    EditTextHeightUtil editTextHeightUtil;

    private UerEntity.AccountBean account;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");

    private String expect = "";
    private String openTime;

    PopupWindow popupWindow;
    SpannableStringBuilder spannable;

    TextView bean_text;
    private String chongzhi_money = "";
    private String bean_pay = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_play_addmoney);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
//        EditTextHeightUtil.assistActivity(this);
        editTextHeightUtil = new EditTextHeightUtil(this, 1);
    }

    @Override
    public void initData() {
        super.initData();

        account = CacheUtil.getInstance().getUserInfo().getAccount();

        rightImageInclude.setVisibility(View.GONE);
        data = CacheUtil.getInstance().getElevenChooseFive();
        min = getIntent().getIntExtra(KeyCode.INTENT_MIN, 1);
        expect = getIntent().getExtras().getString("expect", "");
        openTime = getIntent().getExtras().getString("openTime", "");
        nameTextInclude.setText("11选5");

        myAdapter = new MyAdapter();
        lv.setAdapter(myAdapter);
        dialog = new HuiFuDialog2(this, "", new HuiFuDialog2.onTvOKClickListener() {
            @Override
            public void onClick() {
                startActivity(new Intent(ChooseAddMoneyActivity.this, ChooseMainActivity.class));
                finish();

            }
        });

        rlIAdd.setOnClickListener(this);
        rlJAdd.setOnClickListener(this);

        imgZhuiDelete.setOnClickListener(this);
        imgZhuiAdd.setOnClickListener(this);
        imgTouDelete.setOnClickListener(this);
        imgTouAdd.setOnClickListener(this);
        tvGo.setOnClickListener(this);


        etvTouNums.addTextChangedListener(new MyTextWatch(etvTouNums.getId()));
        etvZhuiNums.addTextChangedListener(new MyTextWatch(etvZhuiNums.getId()));


        types = new String[]{"", "前一", "任二", "任三", "任四", "任五", "任六", "任七", "任八", "前二直选", "前二组选", "前三直选", "前三组选"};
        codes = new int[]{0, 31, 22, 23, 24, 25, 26, 27, 28, 32, 302, 33, 303};
        setZhu();
        setTvGo();
    }

    private void setZhu() {
        zhuNums = 0;

        for (Map<Integer, List<ChooseOvalEntity>> one : data) {

            Set<Integer> integers = one.keySet();
            for (Integer i : integers) {
                List<ChooseOvalEntity> chooseOvalEntities = one.get(i);
                if (i == 9) {
                    int now = 1;
                    int qian = 0;
                    int last = 0;
                    for (ChooseOvalEntity entity : chooseOvalEntities) {

                        if (entity.getContent().equals(",")) {
                            now = 2;
                        } else {
                            if (now == 1) {
                                qian++;
                            } else {
                                last++;
                            }
                        }
                    }
                    zhuNums += qian * last;
                } else if (i == 11) {
                    int now = 1;
                    int qian = 0;
                    int last = 0;
                    int san = 0;
                    for (ChooseOvalEntity entity : chooseOvalEntities) {
                        if (entity.getContent().equals(",")) {
                            now++;
                        } else {
                            if (now == 1) {
                                qian++;
                            } else if (now == 2) {
                                last++;
                            } else if (now == 3) {
                                san++;
                            }
                        }
                    }

                    zhuNums += qian * last * san;
                } else if (i == 10) {
                    zhuNums += Combine.getNumber(2, chooseOvalEntities.size());
                } else if (i == 12) {
                    zhuNums += Combine.getNumber(3, chooseOvalEntities.size());
                } else {
                    zhuNums += Combine.getNumber(i, chooseOvalEntities.size());
                }
            }

        }
        tvMyDou.setText("我的金豆：" + account.getBean() + " 您选择了" + zhuNums + "注");


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_iAdd:
                startActivity(new Intent(this, ChooseMainActivity.class));
                finish();
                break;
            case R.id.rl_jAdd:
                int[] ints = randomJ(1, MAX, min);


                List<ChooseOvalEntity> addData = new ArrayList<>();

                for (int i = 0; i < ints.length; i++) {
                    int red = ints[i];
                    if (min == 9 || min == 11) {
                        if (i == ints.length - 1)
                            addData.add(new ChooseOvalEntity(red >= 10 ? red + "" : "0" + red + "", false, 0));
                        else {
                            addData.add(new ChooseOvalEntity(red >= 10 ? red + "" : "0" + red + "", false, 0));
                            addData.add(new ChooseOvalEntity(",", false, 0));
                        }
                    } else
                        addData.add(new ChooseOvalEntity(red >= 10 ? red + "" : "0" + red, false, 0));
                }

                Map<Integer, List<ChooseOvalEntity>> map = new HashMap<>();
                map.put(min, addData);
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
                if (etvTouNums.getText().toString().equals("1")) return;
                etvTouNums.setText((Integer.valueOf(etvTouNums.getText().toString()) - 1) + "");
                etvTouNums.setSelection(etvTouNums.getText().length());
                break;
            case R.id.img_touAdd:
                etvTouNums.setText((Integer.valueOf(etvTouNums.getText().toString()) + 1) + "");
                etvTouNums.setSelection(etvTouNums.getText().length());
                break;
            case R.id.tv_go:
                //          dialog.show();
                postTouZhu(v);
                break;
        }
    }

    /**
     * 彩票投注
     * create  wjz
     **/
    private void postTouZhu(final View v) {

        String context = "";
        for (Map<Integer, List<ChooseOvalEntity>> one : data) {
            Set<Integer> keys = one.keySet();

            for (Integer i : keys) {
                if (!one.get(i).get(0).getContent().equals(",")) {
                    context += codes[i] + ":";
                    List<ChooseOvalEntity> list = one.get(i);
                    for (int y = 0; y < list.size(); y++) {
                        if (list.get(y).getContent().equals(",")) {
                            context += "+";
                        } else if (y != list.size() - 1) {
                            if (list.get(y + 1).getContent().equals(",")) {
                                context += list.get(y).getContent();
                            } else {
                                context += list.get(y).getContent() + ",";
                            }

                        } else {
                            context += list.get(y).getContent() + ";";
                        }

                    }
                }
            }
        }
        System.out.println("===========context=======" + context);

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
        requestParams.addBodyParameter("name", "11选5");
        requestParams.addBodyParameter("expect", (Long.valueOf(expect) + 1) + "");
        requestParams.addBodyParameter("code", "sd11x5");
        requestParams.addBodyParameter("rule", "");

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                boolean b = GameUtils.CallResultOK(result);
//                Utils.showToast(ChooseAddMoneyActivity.this, GameUtils.getMsg(result));
//                if (b) {
//                    CacheUtil.getInstance().closeElevenChooseFive();
//                }

                if (b) {
                    account.setBean((Integer.valueOf(account.getBean()) - (tou * zhui * zhuNums * 20)) + "");
                    tvMyDou.setText("我的金豆：" + account.getBean() + "您选择了" + zhuNums + "注");
                    PutWindow(v);
                    CacheUtil.getInstance().closeElevenChooseFive();
                } else {
                    Utils.showToast(ChooseAddMoneyActivity.this, GameUtils.getMsg(result));
                    if (GameUtils.getMsg(result).equals("金豆不足")) {
                        MoneyWindow(v);
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

    //弹出框
    private void PutWindow(View view) {

        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_lottery_pay_success, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);

        TextView content_text = (TextView) windowView.findViewById(R.id.content_text);
        TextView next_text = (TextView) windowView.findViewById(R.id.next_text);
        try {
            Date date1 = simpleDateFormat.parse(openTime);
            Long time = date1.getTime() + 1000 * 60 * 10;
            ;
            content_text.setText("预计" + simpleDateFormat2.format(new Date(time)) + "开奖");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        next_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent(ChooseAddMoneyActivity.this, ChooseMainActivity.class);
                intent.putExtra("status", "");
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
            tvMyDou.setText("我的金豆：" + account.getBean() + " 您选择了" + zhuNums + "注");
        } else if (resultCode == Code.FailCode) {

        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * 生成随机数
     * create  wjz
     **/
    public static int[] randomJ(int min, int max, int nums) {

        switch (nums) {
            case 9:
            case 10:
                nums = 2;
                break;
            case 11:
            case 12:
                nums = 3;
                break;
        }


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
                        etvTouNums.setSelection(s.toString().length());
                        if (Integer.valueOf(s.toString()) > 2000) {
                            etvTouNums.setText(2000 + "");
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
        if ("".equals(etvTouNums.getText().toString())) return;
        if ("".equals(etvZhuiNums.getText().toString())) return;

        Integer tou = Integer.valueOf(etvTouNums.getText().toString());
        Integer zhui = Integer.valueOf(etvZhuiNums.getText().toString());

        tvGo.setText("立即投注  " + (tou * zhui * zhuNums * 20) + "金豆");

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
                convertView = LayoutInflater.from(ChooseAddMoneyActivity.this).inflate(R.layout.item_choose_addmoney, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            Map<Integer, List<ChooseOvalEntity>> map = data.get(position);
            List<ChooseOvalEntity> oneData = new ArrayList<>();
            for (Map.Entry<Integer, List<ChooseOvalEntity>> eny : map.entrySet()) {

                vh.tvType.setText(types[eny.getKey()]);
                oneData = map.get(eny.getKey());
            }

            if (oneData.size() > 0) {


                String str = "";
                for (int i = 0; i < oneData.size(); i++) {
                    ChooseOvalEntity entity = oneData.get(i);
                    str += i == oneData.size() - 1 ? entity.getContent() : entity.getContent() + " ";
                }

                vh.tvRed.setText(str);
            }

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
            @InjectView(R.id.tv_type)
            TextView tvType;
            @InjectView(R.id.tv_red)
            TextView tvRed;
            @InjectView(R.id.img_delete)
            ImageView imgDelete;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }

}
