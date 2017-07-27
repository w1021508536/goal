package com.small.small.goal.my.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.guess.fastthree.FastThreeActivity;
import com.small.small.goal.my.guess.fastthree.FastThreePayActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.ParseException;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/6 22:11
 * 修改：
 * 描述：提现
 **/
public class ToMoneyActivity extends BaseActivity {


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
    @InjectView(R.id.wechat_image)
    ImageView wechatImage;
    @InjectView(R.id.wechat_right_image)
    ImageView wechatRightImage;
    @InjectView(R.id.wechat_layout)
    RelativeLayout wechatLayout;
    @InjectView(R.id.alipay_image)
    ImageView alipayImage;
    @InjectView(R.id.alipay_right_image)
    ImageView alipayRightImage;
    @InjectView(R.id.alipay_layout)
    RelativeLayout alipayLayout;
    @InjectView(R.id.pay_text)
    TextView payText;

    private String accountType = "2";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_tomoney);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("提现");
        rightImageInclude.setVisibility(View.GONE);
    }

    @Override
    public void initWeight() {
        super.initWeight();
    }


    @OnClick({R.id.left_image_include, R.id.wechat_layout, R.id.alipay_layout, R.id.pay_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.wechat_layout:
                wechatRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                alipayRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                accountType = "2";
                wechatRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                break;
            case R.id.alipay_layout:
                wechatRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                alipayRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                accountType = "1";
                alipayRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                break;
            case R.id.pay_text:

                if (accountType.equals("0")) {
                    Utils.showToast(this, "请选择提现方式");
                } else if (accountType.equals("1")) {
                    GetBudget(view);
                } else if (accountType.equals("2")) {
                    startActivity(new Intent(this, ToMoneyWeChatActivity.class));
                }
                break;
        }
    }

    private void GetBudget(final View view) {

        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_to_money, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        TextView cancel_text = (TextView) windowView.findViewById(R.id.cancel_text);
        TextView ok_text = (TextView) windowView.findViewById(R.id.ok_text);
        final EditText money_edit = (EditText) windowView.findViewById(R.id.money_edit);
        final EditText account_edit = (EditText) windowView.findViewById(R.id.account_edit);
        money_edit.setHint("最多可提现" + CacheUtil.getInstance().getUserInfo().getAccount().getBalance() + "元");

        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        ok_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!money_edit.getText().toString().trim().equals("") && !account_edit.getText().toString().trim().equals("")) {
                    RequestParams requestParams = new RequestParams(Url.Url + Url.AccountCash);
                    SharedPreferences sp = Utils.UserSharedPreferences(ToMoneyActivity.this);
                    requestParams.addHeader("deviceId", MyApplication.deviceId);
                    requestParams.addHeader("token", sp.getString("token", ""));
                    requestParams.addBodyParameter("accountType", accountType);
                    requestParams.addBodyParameter("amount", money_edit.getText().toString().trim());
                    requestParams.addBodyParameter("account", account_edit.getText().toString().trim());
                    XUtil.post(requestParams, ToMoneyActivity.this, new XUtil.XCallBackLinstener() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                if (new JSONObject(result).getString("code").equals("0")) {
                                    popupWindow.dismiss();
                                    PutWindow(view);
                                } else {
                                    Utils.showToast(ToMoneyActivity.this, new JSONObject(result).getString("msg"));
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
                } else {
                    if (money_edit.getText().toString().trim().equals("")) {
                        Utils.showToast(ToMoneyActivity.this, "金额不可为空");
                    } else {
                        Utils.showToast(ToMoneyActivity.this, "账号不可为空");
                    }
                }
            }
        });

        windowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });

        money_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {


                if (s.toString().length() > 0) {
                    money_edit.setSelection(s.toString().length());
                    if (s.toString().equals(".")) {
                        money_edit.setText("");
                    }

                    if (s.toString().substring(0, 1).equals("0")) {
                        if (s.toString().length() > 1) {
                            if (!s.toString().substring(1, 2).equals(".")) {
                                money_edit.setText(s.toString().substring(0, 1));
                            }
                        }
                    }

                    if (s.toString().substring(0, s.toString().length() - 1).indexOf(".") > -1) {
                        if (s.toString().length() > start) {
                            if (s.toString().substring(start, start + count).equals(".")) {
                                money_edit.setText(s.toString().substring(0, start));
                            }

                            if (s.toString().substring(s.toString().indexOf(".") + 1,
                                    s.toString().length()).length() > 2) {
                                money_edit.setText(s.toString().substring(0, start));
                            }

                        }

                    }

                    if (!s.toString().substring(s.toString().length() - 1, s.toString().length()).equals(".")) {
                        if (Double.valueOf(s.toString()) > CacheUtil.getInstance().getUserInfo().getAccount().getBalance()) {
                            money_edit.setText(CacheUtil.getInstance().getUserInfo().getAccount().getBalance() + "");
                        }
                    }

                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    private void PutWindow(View view) {

        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_lottery_pay_success, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);

        TextView success_text = (TextView) windowView.findViewById(R.id.success_text);
        TextView content_text = (TextView) windowView.findViewById(R.id.content_text);
        TextView next_text = (TextView) windowView.findViewById(R.id.next_text);
        next_text.setText("确定");
        content_text.setText("提现申请已提交，1-2个工作日到账");
        success_text.setVisibility(View.GONE);
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
