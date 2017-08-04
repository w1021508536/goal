package com.small.small.goal.my.activity;

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

import com.small.small.goal.R;
import com.small.small.goal.aim.activity.PayDetailActivity;
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

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

import static com.small.small.goal.R.id.union_layout;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/19 9:58
 * 修改：
 * 描述：成为代理商的支付
 **/
public class ExtensionPayActivity extends BaseActivity {


    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.money_text)
    TextView moneyText;
    @InjectView(R.id.balance_image)
    ImageView balanceImage;
    @InjectView(R.id.balance_right_image)
    ImageView balanceRightImage;
    @InjectView(R.id.balance_layout)
    RelativeLayout balanceLayout;
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
    @InjectView(R.id.union_image)
    ImageView unionImage;
    @InjectView(R.id.union_right_image)
    ImageView unionRightImage;
    @InjectView(R.id.card_text)
    TextView cardText;
    @InjectView(R.id.union_layout)
    RelativeLayout unionLayout;
    @InjectView(R.id.hook_image)
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

    @Override
    public void initData() {
        super.initData();

        channel = "balance";

        imageViews = new ImageView[]{balanceRightImage, wechatRightImage, alipayRightImage, unionRightImage};
        payTypes = new String[]{"balance", "wx", "alipay", "balance"};
        clickIds = new int[]{R.id.balance_layout, R.id.wechat_layout, R.id.alipay_layout, R.id.union_layout};

        nameTextInclude.setText("成为代理");
        rightImageInclude.setVisibility(View.GONE);

    }

    @OnClick({R.id.balance_layout, R.id.wechat_layout, R.id.alipay_layout, union_layout, R.id.hook_layout, R.id.pay_text})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
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
                        DynamicAim();
                        //  Utils.showToast(this, "准备支付了");
                    }
                } else {
                    Utils.showToast(this, "请仔细阅读相关协议");
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {


        if (data == null) return;
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
        } else if (requestCode == Code.BalancePay) {
            if (data.getStringExtra("password").length() == 6) {
                password = data.getStringExtra("password");
                DynamicAim();
            } else {
                Utils.showToast(this, "取消输入支付密码");
            }
        }
    }


    private void DynamicAim() {
        RequestParams requestParams = Utils.getRequestParams(this);

        requestParams.setUri(Url.Url + "/agent/buy");
        requestParams.addBodyParameter("code", "");
        requestParams.addBodyParameter("channel", channel);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {


                try {
                    if (Utils.callOk(result,ExtensionPayActivity.this)) {
                        if (channel.equals("balance")) {


                            JSONObject jsonObject = new JSONObject(result);
                            JSONObject orderInfo = (JSONObject) ((JSONObject) jsonObject.get("result")).get("orderInfo");
                            int orderId = orderInfo.getInt("orderId");

                            BalancePay(orderId);
                        } else {
                            String json = new JSONObject(result).getJSONObject("result").getString("charge");
                            Pingpp.createPayment(ExtensionPayActivity.this, json);
                        }

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

    private void BalancePay(int orderId) {


        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/pay/balance");
        requestParams.addBodyParameter("amount", money);
        requestParams.addBodyParameter("password", password);
        requestParams.addBodyParameter("linkId", orderId + "");
        requestParams.addBodyParameter("type", "3");

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                Utils.showToast(ExtensionPayActivity.this, Utils.getMsg(result));
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

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