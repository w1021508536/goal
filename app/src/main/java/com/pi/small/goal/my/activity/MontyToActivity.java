package com.pi.small.goal.my.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.aim.activity.PayDetailActivity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.ThirdUtils;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pingplusplus.android.Pingpp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/6 20:59
 * 修改：
 * 描述：充值
 **/
public class MontyToActivity extends BaseActivity {


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
    @InjectView(R.id.union_image)
    ImageView unionImage;
    @InjectView(R.id.union_right_image)
    ImageView unionRightImage;
    @InjectView(R.id.card_text)
    TextView cardText;
    @InjectView(R.id.union_layout)
    RelativeLayout unionLayout;
    @InjectView(R.id.pay_text)
    TextView payText;
    @InjectView(R.id.hook_image)
    ImageView hookImage;
    @InjectView(R.id.hook_layout)
    LinearLayout hookLayout;
    @InjectView(R.id.etv_money_money)
    EditText etvMoneyMoney;
    private String content;
    private String aimId;
    private String money;
    private String img1;
    private String img2;
    private String img3;
    private String channel;//支付方式  0: 余额  1：微信 wx   2:alipay

    private Boolean isHook = false;
    public static IWXAPI wx_api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_montyto);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        wx_api = WXAPIFactory.createWXAPI(this, ThirdUtils.WX_APP_ID, true);
        wx_api.registerApp(ThirdUtils.WX_APP_ID);

        nameTextInclude.setText("充值");
        rightImageInclude.setVisibility(View.GONE);
    }

    @Override
    public void getData() {
        super.getData();
    }

    @Override
    public void initWeight() {
        super.initWeight();
        wechatLayout.setOnClickListener(this);
        alipayLayout.setOnClickListener(this);
        unionLayout.setOnClickListener(this);
        hookLayout.setOnClickListener(this);
        payText.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.wechat_layout:

                boolean isHaveWeixin = wx_api.isWXAppInstalled()
                        && wx_api.isWXAppSupportAPI();
                if (isHaveWeixin) {
                    wechatRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    alipayRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    channel = "wx";
                    wechatRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                } else {
                    Utils.showToast(MontyToActivity.this, "请先安装微信应用");
                }

                break;
            case R.id.alipay_layout:
                System.out.println("==========AlipayGphone==========" + Utils.isAppInstalled(this, "com.eg.android.AlipayGphone"));
                if (Utils.isAppInstalled(this, "com.eg.android.AlipayGphone")) {
                    wechatRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    alipayRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    channel = "alipay";
                    alipayRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                } else {
                    wechatRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    alipayRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    channel = "alipay_wap";
                    alipayRightImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                }
                break;
            case R.id.union_layout:

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
                DynamicAim();
                break;
        }
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        System.out.print("===============" + data.getExtras().getString("pay_result"));
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
                    intent.setClass(MontyToActivity.this, PayDetailActivity.class);
                    intent.putExtra("money", money);
                    intent.putExtra("card", "");
                    intent.putExtra("channel", channel);
                    startActivityForResult(intent, Code.Pay);

                } else if (result.equals("fail")) {
                    Utils.showToast(MontyToActivity.this, "支付失败");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    System.out.println("===============" + errorMsg + "========" + extraMsg);
                    finish();
                } else if (result.equals("cancel")) {
                    Utils.showToast(MontyToActivity.this, "取消支付");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    finish();
                } else if (result.equals("invalid")) {
                    Utils.showToast(MontyToActivity.this, "支付插件未安装");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    finish();
                } else if (result.equals("unknown")) {
                    Utils.showToast(MontyToActivity.this, "应用进程异常");
                    Intent intent = new Intent();
                    setResult(Code.FailCode, intent);
                    finish();
                }

            }
        } else if (requestCode == Code.Pay) {
            Intent intent = new Intent();
            setResult(Code.SupportAim, intent);
            finish();


        }
    }

    private void DynamicAim() {


        System.out.println("====================" + aimId + "===" + content + "-==" + money + "==" + channel);

        money = etvMoneyMoney.getText().toString();
        if (money.equals("0") || money.equals("")) {
            return;
        }
        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/charge");


        requestParams.addBodyParameter("channel", channel);
        requestParams.addBodyParameter("amount", money);
//        requestParams.addBodyParameter("aimId", aimId);
//        requestParams.addBodyParameter("money", money);
//        requestParams.addBodyParameter("img1", img1);
//        requestParams.addBodyParameter("img2", img2);
//        requestParams.addBodyParameter("img3", img3);
//        requestParams.addBodyParameter("video", "");
//        requestParams.addBodyParameter("channel", channel);

        x.http().request(HttpMethod.PUT, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("==============AimDynamic=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        String json = new JSONObject(result).getJSONObject("result").getString("charge");

                        System.out.println("==============AimDynamic=====json====" + json);
                        Pingpp.createPayment(MontyToActivity.this, json);
                    } else {
                        Utils.showToast(MontyToActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex.getMessage() != null) {
                    Utils.showToast(MontyToActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
