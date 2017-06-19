package com.pi.small.goal.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.aim.activity.PayDetailActivity;
import com.pi.small.goal.utils.BalancePayActivity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.ThirdUtils;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pingplusplus.android.Pingpp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.pi.small.goal.R.id.alipay_right_image;
import static com.pi.small.goal.R.id.balance_right_image;
import static com.pi.small.goal.R.id.hook_image;
import static com.pi.small.goal.R.id.money_text;
import static com.pi.small.goal.R.id.union_layout;
import static com.pi.small.goal.R.id.wechat_right_image;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/19 9:58
 * 修改：
 * 描述：成为代理商的支付
 **/
public class ExtensionPayActivity extends BaseActivity {

    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.right_image)
    ImageView rightImage;
    @InjectView(money_text)
    TextView moneyText;
    @InjectView(R.id.balance_image)
    ImageView balanceImage;
    @InjectView(balance_right_image)
    ImageView balanceRightImage;
    @InjectView(R.id.balance_layout)
    RelativeLayout balanceLayout;
    @InjectView(R.id.wechat_image)
    ImageView wechatImage;
    @InjectView(wechat_right_image)
    ImageView wechatRightImage;
    @InjectView(R.id.wechat_layout)
    RelativeLayout wechatLayout;
    @InjectView(R.id.alipay_image)
    ImageView alipayImage;
    @InjectView(alipay_right_image)
    ImageView alipayRightImage;
    @InjectView(R.id.alipay_layout)
    RelativeLayout alipayLayout;
    @InjectView(R.id.union_image)
    ImageView unionImage;
    @InjectView(R.id.union_right_image)
    ImageView unionRightImage;
    @InjectView(R.id.card_text)
    TextView cardText;
    @InjectView(union_layout)
    RelativeLayout unionLayout;
    @InjectView(hook_image)
    ImageView hookImage;
    @InjectView(R.id.hook_layout)
    LinearLayout hookLayout;
    @InjectView(R.id.pay_text)
    TextView payText;
    private String content;
    private String aimId;
    private String money = "";
    private String img1;
    private String img2;
    private String img3;
    private String channel;//支付方式  0: 余额  1：微信 wx   2:alipay

    private Boolean isHook = true;
    public static IWXAPI wx_api;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月dd日");

    private String password;
    private String linkId;
    private ImageView[] imageViews;
    private String[] payTypes;
    private int[] clickIds;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_extension_pay);

        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
        money = getIntent().getStringExtra("money");

        moneyText.setText(money + "");

        content = getIntent().getStringExtra("content");
        aimId = getIntent().getStringExtra("aimId");

        img1 = getIntent().getStringExtra("img1");
        img2 = getIntent().getStringExtra("img2");
        img3 = getIntent().getStringExtra("img3");
        channel = "balance";

        wx_api = WXAPIFactory.createWXAPI(this, ThirdUtils.WX_APP_ID, true);
        wx_api.registerApp(ThirdUtils.WX_APP_ID);
    }


    @OnClick({R.id.left_image, R.id.right_image, R.id.balance_layout, R.id.wechat_layout, R.id.alipay_layout, union_layout, R.id.hook_layout, R.id.pay_text})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {

            case R.id.left_image:
                Utils.showToast(this, "支付取消");
                setResult(Code.FailCode, intent);
                finish();
                break;
            case R.id.right_image:

                break;
            case R.id.balance_layout:
            case R.id.wechat_layout:
            case R.id.alipay_layout:
            case R.id.union_layout:

                for (int y = 0; y < imageViews.length; y++) {
                    imageViews[y].setImageResource(R.mipmap.icon_hook_off);
                }

                for (int i = 0; i < clickIds.length; i++) {
                    if (view.getId() == clickIds[i]) {
                        channel = payTypes[i];
                        imageViews[i].setImageResource(R.mipmap.icon_hook_on);
                        break;
                    }
                }

                break;
            case R.id.hook_layout:
                if (isHook) {
                    isHook = false;
                    hookImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                } else {
                    isHook = true;
                    hookImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                }
                break;
            case R.id.pay_text:
                if (isHook) {
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
                        //   DynamicAim();
                        Utils.showToast(this, "准备支付了");
                    }
                } else {
                    Utils.showToast(this, "请仔细阅读相关协议");
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (data == null) return;
        //   System.out.print("===============" + data.getExtras().getString("pay_result"));
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
                    intent.setClass(this, PayDetailActivity.class);
                    intent.putExtra("money", money);
                    intent.putExtra("card", "");
                    intent.putExtra("channel", channel);
                    startActivityForResult(intent, Code.Pay);

                } else if (result.equals("fail")) {
                    Utils.showToast(this, "支付失败");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    System.out.println("===============" + errorMsg + "========" + extraMsg);
                    finish();
                } else if (result.equals("cancel")) {
                    Utils.showToast(this, "取消支付");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    finish();
                } else if (result.equals("invalid")) {
                    Utils.showToast(this, "支付插件未安装");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    finish();
                } else if (result.equals("unknown")) {
                    Utils.showToast(this, "应用进程异常");
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
                //     DynamicAim();
            } else {
                Utils.showToast(this, "取消输入支付密码");
            }
        }
    }


//    private void init() {
//        union_layout.setVisibility(View.GONE);
//        money_text.setText(money + ".00");
//        balance_right_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
//
//        long currentTime = System.currentTimeMillis();
//        profit_text.setText("预计" + simpleDateFormat.format(new Date(currentTime + 86400000)) + "产生收益," + simpleDateFormat.format(new Date(currentTime + 172800000)) + "收益到账");
//
//    }

    @Override
    public void initData() {
        super.initData();

        channel = "balance";

        imageViews = new ImageView[]{balanceRightImage, wechatRightImage, alipayRightImage, unionRightImage};
        payTypes = new String[]{"balance", "wx", "alipay", "balance"};
        clickIds = new int[]{R.id.balance_layout, R.id.wechat_layout, R.id.alipay_layout, R.id.union_layout};


    }

    private void DynamicAim() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.AimDynamic);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("content", content);
        requestParams.addBodyParameter("aimId", aimId);
        requestParams.addBodyParameter("money", money);
        requestParams.addBodyParameter("img1", img1);
        requestParams.addBodyParameter("img2", img2);
        requestParams.addBodyParameter("img3", img3);
        requestParams.addBodyParameter("video", "");
        requestParams.addBodyParameter("channel", channel);
        XUtil.put(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("==============AimDynamic=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        if (channel.equals("balance")) {
                            linkId = new JSONObject(result).getJSONObject("result").getJSONObject("dynamic").getString("id");
                            BalancePay();
                        } else {
                            String json = new JSONObject(result).getJSONObject("result").getString("charge");
                            System.out.println("==============AimDynamic=====json====" + json);
                            Pingpp.createPayment(ExtensionPayActivity.this, json);
                        }

                    } else {
                        Utils.showToast(ExtensionPayActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex.getMessage() != null) {
                    Utils.showToast(ExtensionPayActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void BalancePay() {

        RequestParams requestParams = new RequestParams(Url.Url + Url.PayBalance);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("linkId", linkId);
        requestParams.addBodyParameter("amount", money);
        requestParams.addBodyParameter("password", password);
        requestParams.addBodyParameter("type", "2");

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("==============AimDynamic=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Intent intent = new Intent();
                        intent.setClass(ExtensionPayActivity.this, PayDetailActivity.class);
                        intent.putExtra("money", money);
                        intent.putExtra("card", "");
                        intent.putExtra("channel", channel);
                        startActivityForResult(intent, Code.Pay);

                    } else {
                        Utils.showToast(ExtensionPayActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex.getMessage() != null) {
                    Utils.showToast(ExtensionPayActivity.this, ex.getMessage());
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
            Utils.showToast(this, "支付取消");
            Intent intent = new Intent();
            setResult(Code.FailCode, intent);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}