package com.small.small.goal.search.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.aim.activity.PayDetailActivity;
import com.small.small.goal.my.activity.PayPassActivity;
import com.small.small.goal.utils.BalancePayActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.ThirdUtils;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.pingplusplus.android.Pingpp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SupportPayActivity extends BaseActivity {


    @InjectView(R.id.left_image)
    ImageView left_image;
    @InjectView(R.id.right_image)
    ImageView right_image;
    @InjectView(R.id.money_text)
    TextView money_text;
    @InjectView(R.id.balance_image)
    ImageView balance_image;
    @InjectView(R.id.balance_right_image)
    ImageView balance_right_image;
    @InjectView(R.id.balance_layout)
    RelativeLayout balance_layout;
    @InjectView(R.id.wechat_image)
    ImageView wechat_image;
    @InjectView(R.id.wechat_right_image)
    ImageView wechat_right_image;
    @InjectView(R.id.wechat_layout)
    RelativeLayout wechat_layout;
    @InjectView(R.id.alipay_image)
    ImageView alipay_image;
    @InjectView(R.id.alipay_right_image)
    ImageView alipay_right_image;
    @InjectView(R.id.alipay_layout)
    RelativeLayout alipay_layout;
    @InjectView(R.id.union_image)
    ImageView union_image;
    @InjectView(R.id.union_right_image)
    ImageView union_right_image;
    @InjectView(R.id.card_text)
    TextView card_text;
    @InjectView(R.id.union_layout)
    RelativeLayout union_layout;
    @InjectView(R.id.pay_text)
    TextView pay_text;

    private String dynamicId;
    private String aimId;
    private String money;
    private String channel;//支付方式  0: 余额  1：微信 wx   2:alipay

    public static IWXAPI wx_api;

    private String password;

    private String linkId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_support_pay);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);


        money = getIntent().getStringExtra("money");
        dynamicId = getIntent().getStringExtra("dynamicId");
        aimId = getIntent().getStringExtra("aimId");

        channel = "balance";

        wx_api = WXAPIFactory.createWXAPI(this, ThirdUtils.WX_APP_ID, true);
        wx_api.registerApp(ThirdUtils.WX_APP_ID);

        union_layout.setVisibility(View.GONE);
//        alipay_layout.setVisibility(View.GONE);
        money_text.setText(money + ".00");
        balance_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
    }

    @OnClick({R.id.left_image, R.id.right_image, R.id.balance_layout, R.id.wechat_layout, R.id.alipay_layout, R.id.union_layout, R.id.pay_text})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.left_image:
                Utils.showToast(SupportPayActivity.this, "支付取消");

                setResult(Code.FailCode, intent);
                finish();
                break;
            case R.id.right_image:

                break;
            case R.id.balance_layout:
                wechat_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                balance_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                alipay_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                channel = "balance";
                balance_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                break;
            case R.id.wechat_layout:

                boolean isHaveWeixin = wx_api.isWXAppInstalled()
                        && wx_api.isWXAppSupportAPI();
                if (isHaveWeixin) {
                    wechat_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    balance_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    alipay_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    channel = "wx";
                    wechat_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                } else {
                    Utils.showToast(SupportPayActivity.this, "请先安装微信应用");
                }

                break;
            case R.id.alipay_layout:
                System.out.println("==========AlipayGphone==========" + Utils.isAppInstalled(this, "com.eg.android.AlipayGphone"));
                if (Utils.isAppInstalled(this, "com.eg.android.AlipayGphone")) {
                    wechat_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    balance_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    alipay_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    channel = "alipay";
                    alipay_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                } else {
                    wechat_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    balance_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    alipay_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    channel = "alipay_wap";
                    alipay_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                }
                break;
            case R.id.union_layout:

                break;
            case R.id.pay_text:
                if (channel.equals("balance")) {
                    int payPassword = Utils.UserSharedPreferences(this).getInt("payPassword", 0);
                    if (payPassword == 0) {
                        Utils.showToast(this, getResources().getString(R.string.set_password));
                        startActivity(new Intent(this, PayPassActivity.class));
                    } else {
                        intent.setClass(this, BalancePayActivity.class);
                        startActivityForResult(intent, Code.BalancePay);
                    }
                } else {
                    DynamicAim();
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        //支付页面返回处理
        if (requestCode == Pingpp.REQUEST_CODE_PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                String result = data.getExtras().getString("pay_result");
            /* 处理返回值
             * "success" - 支付成功
             * "fail"    - 支付失败
             * "cancel"  - 取消支付
             * "invalid" - 支付插件未安装（一般是微信客户端未安装的情况）
             * "unknown" - app进程异常被杀死(一般是低内存状态下,app进程被杀死)
             */
                String errorMsg = data.getExtras().getString("error_msg"); // 错误信息
                String extraMsg = data.getExtras().getString("extra_msg"); // 错误信息
//                showMsg(result, errorMsg, extraMsg);
//                Utils.showToast(PayActivity.this, result);
                if (result.equals("success")) {
                    Intent intent = new Intent();
                    intent.setClass(SupportPayActivity.this, PayDetailActivity.class);
                    intent.putExtra("money", money);
                    intent.putExtra("card", "");
                    intent.putExtra("channel", channel);
                    startActivityForResult(intent, Code.Pay);

                } else if (result.equals("fail")) {
                    Utils.showToast(SupportPayActivity.this, "支付失败");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    System.out.println("===============" + errorMsg + "========" + extraMsg);
                    finish();
                } else if (result.equals("cancel")) {
                    Utils.showToast(SupportPayActivity.this, "取消支付");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    finish();
                } else if (result.equals("invalid")) {
                    Utils.showToast(SupportPayActivity.this, "支付插件未安装");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    finish();
                } else if (result.equals("unknown")) {
                    Utils.showToast(SupportPayActivity.this, "应用进程异常");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    finish();
                }

            }
        } else if (requestCode == Code.Pay) {
            Intent intent = new Intent();
            setResult(Code.SupportAim, intent);
            finish();
        } else if (requestCode == Code.BalancePay) {
            if (data.getStringExtra("password").length() == 6) {
                password = data.getStringExtra("password");
                DynamicAim();
            } else {
                Utils.showToast(SupportPayActivity.this, "取消输入支付密码");
            }
        }
    }


    private void BalancePay() {
        System.out.println("====================" + aimId + "===" + dynamicId + "-==" + money + "==" + channel);

        RequestParams requestParams = new RequestParams(Url.Url + Url.PayBalance);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("linkId", linkId);
        requestParams.addBodyParameter("amount", money);
        requestParams.addBodyParameter("password", password);
        requestParams.addBodyParameter("type", "1");

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("==============AimDynamic=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Intent intent = new Intent();
                        intent.setClass(SupportPayActivity.this, PayDetailActivity.class);
                        intent.putExtra("money", money);
                        intent.putExtra("card", "");
                        intent.putExtra("channel", channel);
                        startActivityForResult(intent, Code.Pay);
                    } else {
                        Utils.showToast(SupportPayActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex.getMessage() != null) {
                    Utils.showToast(SupportPayActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void DynamicAim() {
        System.out.println("====================" + aimId + "===" + dynamicId + "-==" + money + "==" + channel);

        RequestParams requestParams = new RequestParams(Url.Url + Url.AimSupport);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("aimId", aimId);
        requestParams.addBodyParameter("money", money);
        requestParams.addBodyParameter("dynamicId", dynamicId);
        requestParams.addBodyParameter("channel", channel);

        XUtil.put(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("==============AimDynamic=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        if (channel.equals("balance")) {
                            linkId = new JSONObject(result).getJSONObject("result").getJSONObject("support").getString("id");
                            BalancePay();
                        } else {
                            String json = new JSONObject(result).getJSONObject("result").getString("charge");
                            System.out.println("==============AimDynamic=====json====" + json);
                            Pingpp.createPayment(SupportPayActivity.this, json);
                        }

                    } else {
                        Utils.showToast(SupportPayActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex.getMessage() != null) {
                    Utils.showToast(SupportPayActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Utils.showToast(SupportPayActivity.this, "支付取消");
            Intent intent = new Intent();
            setResult(Code.FailCode, intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

}
