package com.small.small.goal.my.guess.fastthree;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.small.small.goal.my.guess.fastthree.empty.FastThreeEmpty;
import com.small.small.goal.my.guess.fastthree.fragment.SumFragment;
import com.small.small.goal.my.guess.fastthree.fragment.ThreeNotSameFragment;
import com.small.small.goal.my.guess.fastthree.fragment.ThreeSameFragment;
import com.small.small.goal.my.guess.fastthree.fragment.TwoNotSameFragment;
import com.small.small.goal.my.guess.fastthree.fragment.TwoSameFragment;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.EditTextHeightUtil;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

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

public class FastThreePayActivity extends BaseActivity {


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
    @InjectView(R.id.add_choose_layout)
    LinearLayout addChooseLayout;
    @InjectView(R.id.add_random_layout)
    LinearLayout addRandomLayout;
    @InjectView(R.id.top_layout)
    LinearLayout topLayout;
    @InjectView(R.id.tv_zhui_hint)
    TextView tvZhuiHint;
    @InjectView(R.id.tv_zhuiDelete)
    TextView tvZhuiDelete;
    @InjectView(R.id.etv_zhuiNums)
    EditText etvZhuiNums;
    @InjectView(R.id.tv_zhuiAdd)
    TextView tvZhuiAdd;
    @InjectView(R.id.tv_qi_hint)
    TextView tvQiHint;
    @InjectView(R.id.tv_tou_hint)
    TextView tvTouHint;
    @InjectView(R.id.tv_touDelete)
    TextView tvTouDelete;
    @InjectView(R.id.etv_touNums)
    EditText etvTouNums;
    @InjectView(R.id.tv_touAdd)
    TextView tvTouAdd;
    @InjectView(R.id.tv_bei_hint)
    TextView tvBeiHint;
    @InjectView(R.id.next_text)
    TextView nextText;
    @InjectView(R.id.bottom_layout)
    LinearLayout bottomLayout;
    @InjectView(R.id.list)
    ListView list;
    @InjectView(R.id.bean_text)
    TextView beanText;
    private FastThreePayAdapter fastThreePayAdapter;


    private List<FastThreeEmpty> fastThreeEmptyList;

    private FastThreeEmpty getFastThreeEmpty;
    FastThreeEmpty fastThreeEmpty1;
    FastThreeEmpty fastThreeEmpty2;
    private int money = 0;

    private int notes = 1;
    private int status = 0;

    private String content;

    private String expect;
    private String openTime;


    private UerEntity.AccountBean account;

    EditTextHeightUtil editTextHeightUtil;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("HH:mm");

    PopupWindow popupWindow;
    SpannableStringBuilder spannable;

    TextView bean_text;
    private String chongzhi_money = "";
    private String bean_pay = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_fast_three_pay);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

//        EditTextHeightUtil.assistActivity(this);
        editTextHeightUtil = new EditTextHeightUtil(this, 1);

        expect = getIntent().getExtras().getString("expect", "");
        openTime = getIntent().getExtras().getString("openTime", "");
        getFastThreeEmpty = (FastThreeEmpty) getIntent().getSerializableExtra("fastThreeEmpty");

        if (fastThreeEmptyList == null) {
            fastThreeEmptyList = new ArrayList<>();
        }
        if (fastThreePayAdapter == null) {
            fastThreePayAdapter = new FastThreePayAdapter(this);
            list.setAdapter(fastThreePayAdapter);
        }


        if (getFastThreeEmpty != null) {

            if (getFastThreeEmpty.getStatus() == 12) {

                fastThreeEmpty1 = new FastThreeEmpty();
                fastThreeEmpty2 = new FastThreeEmpty();
                fastThreeEmpty1.setStatus(10);
                fastThreeEmpty1.setThreeSingleList(getFastThreeEmpty.getThreeSingleList());
                fastThreeEmpty2.setStatus(11);
                fastThreeEmpty2.setThreeEvenList(getFastThreeEmpty.getThreeEvenList());
                fastThreeEmptyList.add(fastThreeEmpty1);
                fastThreeEmptyList.add(fastThreeEmpty2);

            } else if (getFastThreeEmpty.getStatus() == 22) {
                fastThreeEmpty1 = new FastThreeEmpty();
                fastThreeEmpty2 = new FastThreeEmpty();
                fastThreeEmpty1.setStatus(20);
                fastThreeEmpty1.setTwoSingleList(getFastThreeEmpty.getTwoSingleList());
                fastThreeEmpty1.setTwoSameList(getFastThreeEmpty.getTwoSameList());
                fastThreeEmpty2.setStatus(21);
                fastThreeEmpty2.setTwoCheckList(getFastThreeEmpty.getTwoCheckList());
                fastThreeEmptyList.add(fastThreeEmpty1);
                fastThreeEmptyList.add(fastThreeEmpty2);
            } else if (getFastThreeEmpty.getStatus() == 32) {
                fastThreeEmpty1 = new FastThreeEmpty();
                fastThreeEmpty2 = new FastThreeEmpty();
                fastThreeEmpty1.setStatus(30);
                fastThreeEmpty1.setThreeNotList(getFastThreeEmpty.getThreeNotList());
                fastThreeEmpty2.setStatus(31);
                fastThreeEmpty2.setThreeNotEvenList(getFastThreeEmpty.getThreeNotEvenList());
                fastThreeEmptyList.add(fastThreeEmpty1);
                fastThreeEmptyList.add(fastThreeEmpty2);

            } else {
                fastThreeEmptyList.add(getFastThreeEmpty);
            }
            status = fastThreeEmptyList.get(fastThreeEmptyList.size() - 1).getStatus();
            fastThreePayAdapter.notifyDataSetChanged();
        }

        SetMoney();

        account = CacheUtil.getInstance().getUserInfo().getAccount();

        etvTouNums.setText(notes + "");
        nameTextInclude.setText("新快3");
        rightImageInclude.setVisibility(View.GONE);

        beanText.setText("我的金豆：" + account.getBean());
        etvTouNums.addTextChangedListener(new MyTextWatch());
    }

    private void SetMoney() {
        money = 0;
        for (int i = 0; i < fastThreeEmptyList.size(); i++) {

            if (fastThreeEmptyList.get(i).getStatus() == 40) {
                money = (int) (money + Utils.combination(2, fastThreeEmptyList.get(i).getTwoNotList().size()) * 20);
            } else if (fastThreeEmptyList.get(i).getStatus() == 30) {
                money = (int) (money + Utils.combination(3, fastThreeEmptyList.get(i).getThreeNotList().size()) * 20);
            } else if (fastThreeEmptyList.get(i).getStatus() == 31) {
                money = money + 20;
            } else if (fastThreeEmptyList.get(i).getStatus() == 20) {
                money = money + fastThreeEmptyList.get(i).getTwoSameList().size() * fastThreeEmptyList.get(i).getTwoSingleList().size() * 20;
            } else if (fastThreeEmptyList.get(i).getStatus() == 21) {
                money = money + 20 * fastThreeEmptyList.get(i).getTwoCheckList().size();
            } else if (fastThreeEmptyList.get(i).getStatus() == 10) {
                money = money + fastThreeEmptyList.get(i).getThreeSingleList().size() * 20;
            } else if (fastThreeEmptyList.get(i).getStatus() == 11) {
                money = money + 20;
            } else if (fastThreeEmptyList.get(i).getStatus() == 0) {
                money = money + fastThreeEmptyList.get(i).getSumList().size() * 20;
            }
        }
        nextText.setText("立即投注 " + money * notes + "金豆");
    }

    @OnClick({R.id.next_text, R.id.left_image_include, R.id.add_choose_layout, R.id.add_random_layout, R.id.tv_zhuiDelete, R.id.tv_zhuiAdd, R.id.tv_touDelete, R.id.tv_touAdd})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.add_choose_layout:
                Intent intent = new Intent();
                intent.setClass(this, FastThreeActivity.class);
                intent.putExtra("status", "1");
//                startActivity(intent);
                startActivityForResult(intent, Code.THIRD_D_CODE);
                break;
            case R.id.add_random_layout:
                Random();
                break;
            case R.id.tv_zhuiDelete:
                break;
            case R.id.tv_zhuiAdd:
                break;
            case R.id.tv_touDelete:
                if (notes > 1) {
                    notes--;
                    etvTouNums.setText(notes + "");
                    nextText.setText("立即投注 " + money * notes + "金豆");
                }
                break;
            case R.id.tv_touAdd:
                notes++;
                etvTouNums.setText(notes + "");
                nextText.setText("立即投注 " + money * notes + "金豆");
                break;
            case R.id.next_text:

                Betting(view);
                break;
        }
    }

    private void Betting(final View v) {


        //        content	string	是	投注内容:胜、负	胜

        //        name	    string	是	名称：英超、双色球	英超
//        bean	    string	是	金豆	300
//        code	    string	否	彩票代码	ssq

        RequestParams requestParams = new RequestParams(Url.Url + Url.Wager);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("number", "1");
        requestParams.addBodyParameter("multifold", notes + "");
        requestParams.addBodyParameter("linkId", "");
        requestParams.addBodyParameter("type", "4");
        requestParams.addBodyParameter("expect", (Long.valueOf(expect) + 1) + "");
        requestParams.addBodyParameter("rule", "");

        requestParams.addBodyParameter("code", "jlk3");
        requestParams.addBodyParameter("name", "新快三");
        requestParams.addBodyParameter("bean", money * notes + "");

        content = "";

        for (int i = 0; i < fastThreeEmptyList.size(); i++) {
            if (fastThreeEmptyList.get(i).getStatus() == 0) {
                content = content + "10:";
                for (int j = 0; j < fastThreeEmptyList.get(i).getSumList().size(); j++) {
                    if (j != fastThreeEmptyList.get(i).getSumList().size() - 1) {
                        content = content + fastThreeEmptyList.get(i).getSumList().get(j) + ",";
                    } else {
                        content = content + fastThreeEmptyList.get(i).getSumList().get(j);
                    }
                }
            } else if (fastThreeEmptyList.get(i).getStatus() == 10) {
                content = content + "431:";

                for (int j = 0; j < fastThreeEmptyList.get(i).getThreeSingleList().size(); j++) {
                    if (j != fastThreeEmptyList.get(i).getThreeSingleList().size() - 1) {
                        content = content + fastThreeEmptyList.get(i).getThreeSingleList().get(j) + ",";
                    } else {
                        content = content + fastThreeEmptyList.get(i).getThreeSingleList().get(j);
                    }
                }
            } else if (fastThreeEmptyList.get(i).getStatus() == 11) {
                content = content + "436:1;";
            } else if (fastThreeEmptyList.get(i).getStatus() == 20) {
                content = content + "421:";
                for (int j = 0; j < fastThreeEmptyList.get(i).getTwoSameList().size(); j++) {

                    if (j == fastThreeEmptyList.get(i).getTwoSameList().size() - 1) {
                        content = content + fastThreeEmptyList.get(i).getTwoSameList().get(j);
                    } else {
                        content = content + fastThreeEmptyList.get(i).getTwoSameList().get(j) + ",";
                    }
                }
                content = content + "+";
                for (int j = 0; j < fastThreeEmptyList.get(i).getTwoSingleList().size(); j++) {
                    if (j == fastThreeEmptyList.get(i).getTwoSingleList().size() - 1) {
                        content = content + fastThreeEmptyList.get(i).getTwoSingleList().get(j);
                    } else {
                        content = content + fastThreeEmptyList.get(i).getTwoSingleList().get(j) + ",";
                    }
                }
            } else if (fastThreeEmptyList.get(i).getStatus() == 21) {

                content = content + "426:";
                for (int j = 0; j < fastThreeEmptyList.get(i).getTwoCheckList().size(); j++) {
                    if (j != fastThreeEmptyList.get(i).getTwoCheckList().size() - 1) {
                        content = content + fastThreeEmptyList.get(i).getTwoCheckList().get(j).substring(0, 2) + ",";
                    } else {
                        content = content + fastThreeEmptyList.get(i).getTwoCheckList().get(j).substring(0, 2);
                    }
                }

            } else if (fastThreeEmptyList.get(i).getStatus() == 30) {

                content = content + "430:";
                for (int j = 0; j < fastThreeEmptyList.get(i).getThreeNotList().size(); j++) {
                    if (j != fastThreeEmptyList.get(i).getThreeNotList().size() - 1) {
                        content = content + fastThreeEmptyList.get(i).getThreeNotList().get(j) + ",";
                    } else {
                        content = content + fastThreeEmptyList.get(i).getThreeNotList().get(j);
                    }
                }

            } else if (fastThreeEmptyList.get(i).getStatus() == 31) {
                content = content + "43:1;";

            } else if (fastThreeEmptyList.get(i).getStatus() == 40) {
                content = content + "420:";
                for (int j = 0; j < fastThreeEmptyList.get(i).getTwoNotList().size(); j++) {
                    if (j != fastThreeEmptyList.get(i).getTwoNotList().size() - 1) {
                        content = content + fastThreeEmptyList.get(i).getTwoNotList().get(j) + ",";
                    } else {
                        content = content + fastThreeEmptyList.get(i).getTwoNotList().get(j);
                    }
                }
            }

            if (i != fastThreeEmptyList.size() - 1) {
                content = content + ";";
            }
        }
        System.out.println("============content===========" + content);
        requestParams.addBodyParameter("content", content);
        XUtil.post(requestParams, FastThreePayActivity.this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=======================" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        account.setBean((Integer.valueOf(account.getBean()) - money * notes) + "");
                        beanText.setText("我的金豆：" + account.getBean());
                        PutWindow(v);
                    } else {

                        Utils.showToast(FastThreePayActivity.this, new JSONObject(result).getString("msg"));
                        if (new JSONObject(result).getString("msg").equals("金豆不足")) {
                            MoneyWindow(v);
                        }
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
        if (requestCode == Code.THIRD_D_CODE) {
            if (data != null) {
                getFastThreeEmpty = (FastThreeEmpty) data.getSerializableExtra("fastThreeEmpty");

                System.out.println("============getFastThreeEmpty================" + getFastThreeEmpty.getStatus());

                if (getFastThreeEmpty != null) {
                    if (getFastThreeEmpty.getStatus() == 12) {

                        fastThreeEmpty1 = new FastThreeEmpty();
                        fastThreeEmpty2 = new FastThreeEmpty();
                        fastThreeEmpty1.setStatus(10);
                        fastThreeEmpty1.setThreeSingleList(getFastThreeEmpty.getThreeSingleList());
                        fastThreeEmpty2.setStatus(11);
                        fastThreeEmpty2.setThreeEvenList(getFastThreeEmpty.getThreeEvenList());
                        fastThreeEmptyList.add(fastThreeEmpty1);
                        fastThreeEmptyList.add(fastThreeEmpty2);

                    } else if (getFastThreeEmpty.getStatus() == 22) {
                        fastThreeEmpty1 = new FastThreeEmpty();
                        fastThreeEmpty2 = new FastThreeEmpty();
                        fastThreeEmpty1.setStatus(20);
                        fastThreeEmpty1.setTwoSingleList(getFastThreeEmpty.getTwoSingleList());
                        fastThreeEmpty1.setTwoSameList(getFastThreeEmpty.getTwoSameList());
                        fastThreeEmpty2.setStatus(21);
                        fastThreeEmpty2.setTwoCheckList(getFastThreeEmpty.getTwoCheckList());
                        fastThreeEmptyList.add(fastThreeEmpty1);
                        fastThreeEmptyList.add(fastThreeEmpty2);
                    } else if (getFastThreeEmpty.getStatus() == 32) {
                        fastThreeEmpty1 = new FastThreeEmpty();
                        fastThreeEmpty2 = new FastThreeEmpty();
                        fastThreeEmpty1.setStatus(30);
                        fastThreeEmpty1.setThreeNotList(getFastThreeEmpty.getThreeNotList());
                        fastThreeEmpty2.setStatus(31);
                        fastThreeEmpty2.setThreeNotEvenList(getFastThreeEmpty.getThreeNotEvenList());
                        fastThreeEmptyList.add(fastThreeEmpty1);
                        fastThreeEmptyList.add(fastThreeEmpty2);

                    } else {
                        fastThreeEmptyList.add(getFastThreeEmpty);
                    }
                    status = fastThreeEmptyList.get(fastThreeEmptyList.size() - 1).getStatus();
                    fastThreePayAdapter.notifyDataSetChanged();
                }
                SetMoney();
            }

        } else if (requestCode == Code.SupportAim) {
            if (resultCode == Code.SupportAim) {
                account.setBean(Long.valueOf(account.getBean()) + Long.valueOf(bean_pay) + "");
                bean_text.setText(account.getBean());
                beanText.setText("我的金豆：" + account.getBean());
            } else if (resultCode == Code.FailCode) {

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    private void Random() {
        Random rand = new Random();
        FastThreeEmpty fastThreeEmpty = new FastThreeEmpty();

        List<String> dataList = new ArrayList<>();
        List<String> dataList2 = new ArrayList<>();
        if (status == 40) {
            int number = rand.nextInt(6);
            int number2 = rand.nextInt(6);
            if (number2 == number) {
                if (number2 > 4) {
                    number2 = rand.nextInt(4);
                } else {
                    number2 = number2 + 1;
                }
            }
            dataList.add(TwoNotSameFragment.dataList.get(number).getNumber());
            dataList.add(TwoNotSameFragment.dataList.get(number2).getNumber());
            fastThreeEmpty.setStatus(40);
            fastThreeEmpty.setTwoNotList(dataList);
        } else if (status == 30) {
            int[] ints = Utils.randomCommon(1, 6, 3);
            for (int i = 0; i < ints.length; i++) {
                dataList.add(ThreeNotSameFragment.dataList.get(ints[i]).getNumber());
            }
            fastThreeEmpty.setStatus(30);
            fastThreeEmpty.setThreeNotList(dataList);
        } else if (status == 31) {
            dataList.add("三连号通选");
            fastThreeEmpty.setStatus(31);
            fastThreeEmpty.setThreeNotEvenList(dataList);
        } else if (status == 20) {
            int number = rand.nextInt(6);
            int number2 = rand.nextInt(6);
            if (number2 == number) {
                if (number2 > 4) {
                    number2 = rand.nextInt(4);
                } else {
                    number2 = number2 + 1;
                }
            }
            dataList.add(TwoSameFragment.dataList.get(number).getNumber());
            dataList2.add(TwoSameFragment.fastList.get(number2).getNumber());
            fastThreeEmpty.setStatus(20);
            fastThreeEmpty.setTwoSameList(dataList);
            fastThreeEmpty.setTwoSingleList(dataList2);
        } else if (status == 21) {
            int number = rand.nextInt(6);
            dataList.add(TwoSameFragment.dataList.get(number).getNumber());
            fastThreeEmpty.setStatus(21);
            fastThreeEmpty.setTwoCheckList(dataList);

        } else if (status == 10) {
            int number = rand.nextInt(6);
            dataList.add(ThreeSameFragment.dataList.get(number).getNumber());
            fastThreeEmpty.setStatus(10);
            fastThreeEmpty.setThreeSingleList(dataList);

        } else if (status == 11) {
            dataList.add("三同号通选");
            fastThreeEmpty.setStatus(11);
            fastThreeEmpty.setThreeEvenList(dataList);

        } else if (status == 0) {
            int number = rand.nextInt(16);
            dataList.add(SumFragment.dataList.get(number).getNumber());
            fastThreeEmpty.setStatus(0);
            fastThreeEmpty.setSumList(dataList);
        }
        fastThreeEmptyList.add(fastThreeEmpty);
        fastThreePayAdapter.notifyDataSetChanged();

        SetMoney();
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
            content_text.setText("预计" + simpleDateFormat2.format(new Date(time)) + "开奖");
        } catch (ParseException e) {
            e.printStackTrace();
        }


        next_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Intent intent = new Intent(FastThreePayActivity.this, FastThreeActivity.class);
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

    class MyTextWatch implements TextWatcher {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (s.toString().length() > 0) {
                etvTouNums.setSelection(s.toString().length());
                if (Integer.valueOf(s.toString()) > 2000) {
                    notes = 2000;
                }
            }


        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.toString().length() > 0) {
                notes = Integer.valueOf(etvTouNums.getText().toString());
                etvTouNums.setText(notes + "");
                nextText.setText("立即投注 " + money * notes + "金豆");
            } else {
                notes = 0;
                nextText.setText("立即投注 " + money * notes + "金豆");
            }

        }
    }


    public class FastThreePayAdapter extends BaseAdapter {

        private Context context;

        public FastThreePayAdapter(Context context) {
            this.context = context;
        }

        @Override
        public int getCount() {
            return fastThreeEmptyList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_third_d_pay, parent, false);
                viewHolder = new ViewHolder(convertView);

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            if (fastThreeEmptyList.get(position).getStatus() == 40) {
                viewHolder.statusText.setText("二不同号");
                String number = "";
                for (int i = 0; i < fastThreeEmptyList.get(position).getTwoNotList().size(); i++) {
                    number = number + " " + fastThreeEmptyList.get(position).getTwoNotList().get(i) + " ";
                }
                viewHolder.numberText.setText(number);
                viewHolder.notesText.setText(Utils.combination(2, fastThreeEmptyList.get(position).getTwoNotList().size()) + "注");
            } else if (fastThreeEmptyList.get(position).getStatus() == 30) {
                viewHolder.statusText.setText("三不同号");
                String number = "";
                for (int i = 0; i < fastThreeEmptyList.get(position).getThreeNotList().size(); i++) {
                    number = number + " " + fastThreeEmptyList.get(position).getThreeNotList().get(i) + " ";
                }
                viewHolder.numberText.setText(number);
                viewHolder.notesText.setText(Utils.combination(3, fastThreeEmptyList.get(position).getThreeNotList().size()) + "注");
            } else if (fastThreeEmptyList.get(position).getStatus() == 31) {
                viewHolder.statusText.setText("三连号通选");
                String number = fastThreeEmptyList.get(position).getThreeNotEvenList().get(0);
                viewHolder.numberText.setText(number);
                viewHolder.notesText.setText(1 + "注");
            } else if (fastThreeEmptyList.get(position).getStatus() == 20) {
                viewHolder.statusText.setText("二同号单选");
                String number = "";
                for (int i = 0; i < fastThreeEmptyList.get(position).getTwoSameList().size(); i++) {
                    number = number + " " + fastThreeEmptyList.get(position).getTwoSameList().get(i) + " ";
                }
                number = number + "|";
                for (int i = 0; i < fastThreeEmptyList.get(position).getTwoSingleList().size(); i++) {
                    number = number + " " + fastThreeEmptyList.get(position).getTwoSingleList().get(i) + " ";
                }
                viewHolder.numberText.setText(number);
                viewHolder.notesText.setText(fastThreeEmptyList.get(position).getTwoSameList().size() * fastThreeEmptyList.get(position).getTwoSingleList().size() + "注");
            } else if (fastThreeEmptyList.get(position).getStatus() == 21) {
                viewHolder.statusText.setText("二同号复选");
                String number = "";
                for (int i = 0; i < fastThreeEmptyList.get(position).getTwoCheckList().size(); i++) {
                    number = number + " " + fastThreeEmptyList.get(position).getTwoCheckList().get(i) + " ";
                }
                viewHolder.numberText.setText(number);
                viewHolder.notesText.setText(fastThreeEmptyList.get(position).getTwoCheckList().size() + "注");
            } else if (fastThreeEmptyList.get(position).getStatus() == 10) {
                viewHolder.statusText.setText("三同号单选");
                String number = "";
                for (int i = 0; i < fastThreeEmptyList.get(position).getThreeSingleList().size(); i++) {
                    number = number + " " + fastThreeEmptyList.get(position).getThreeSingleList().get(i) + " ";
                }
                viewHolder.numberText.setText(number);
                viewHolder.notesText.setText(fastThreeEmptyList.get(position).getThreeSingleList().size() + "注");
            } else if (fastThreeEmptyList.get(position).getStatus() == 11) {
                viewHolder.statusText.setText("三同号通选");
                String number = fastThreeEmptyList.get(position).getThreeEvenList().get(0);
                viewHolder.numberText.setText(number);
                viewHolder.notesText.setText(1 + "注");
            } else if (fastThreeEmptyList.get(position).getStatus() == 0) {
                viewHolder.statusText.setText("和数");
                String number = "";
                for (int i = 0; i < fastThreeEmptyList.get(position).getSumList().size(); i++) {
                    number = number + " " + fastThreeEmptyList.get(position).getSumList().get(i) + " ";
                }
                viewHolder.numberText.setText(number);
                viewHolder.notesText.setText(fastThreeEmptyList.get(position).getSumList().size() + "注");
            }

            viewHolder.deleteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    fastThreeEmptyList.remove(position);
                    if (fastThreeEmptyList.size() != 0)
                        status = fastThreeEmptyList.get(fastThreeEmptyList.size() - 1).getStatus();
                    notifyDataSetChanged();
                    SetMoney();

                }
            });
            return convertView;
        }


        class ViewHolder {
            @InjectView(R.id.status_text)
            TextView statusText;
            @InjectView(R.id.left)
            LinearLayout left;
            @InjectView(R.id.number_text)
            TextView numberText;
            @InjectView(R.id.delete_image)
            ImageView deleteImage;
            @InjectView(R.id.notes_text)
            TextView notesText;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
