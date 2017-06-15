package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.dialog.HuiFuDialog;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.xutils.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.pi.small.goal.my.activity.BindPhoneActivity.TYPE_BIND;
import static com.pi.small.goal.my.activity.BindPhoneActivity.TYPE_FORGETPASS;
import static com.pi.small.goal.my.activity.BindPhoneActivity.TYPE_SETPASS;
import static com.pi.small.goal.my.activity.UpdataPassActivity.FLAG_FORGET_PASS;
import static com.pi.small.goal.my.activity.UpdataPassActivity.FLAG_SET_PASS;
import static com.pi.small.goal.my.activity.UpdataPassActivity.KEY_FLAG;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/7 16:55
 * 修改：
 * 描述：绑定手机的验证码页面
 **/
public class BindPhoneNextActivity extends BaseActivity {


    public static final String KEY_PHONE = "phone";
    @InjectView(R.id.tv_phoneNumber)
    TextView tvPhoneNumber;
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
    @InjectView(R.id.textView2)
    TextView textView2;
    @InjectView(R.id.etv_code)
    EditText etvCode;
    @InjectView(R.id.tv_time)
    TextView tvTime;
    @InjectView(R.id.next)
    TextView next;
    private String phone;
    private HuiFuDialog dialog;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_bind_phone_next);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();

        type = getIntent().getIntExtra(BindPhoneActivity.KEY_TYPE_UPDATA, BindPhoneActivity.TYPE_BIND);

        nameTextInclude.setText("绑定手机");
        rightImageInclude.setVisibility(View.GONE);
        dialog = new HuiFuDialog(this, "你的手机号已绑定成功");
//        phone = getIntent().getStringExtra(KEY_PHONE);

        if (type == TYPE_BIND) {
            phone = getIntent().getStringExtra(KEY_PHONE);
            tvPhoneNumber.setText("请输入" + phone + "的验证码");
        } else if (type == BindPhoneActivity.TYPE_UPDATA) {
            nameTextInclude.setText("更换绑定手机");
            //      dialog = new HuiFuDialog(this, "你的手机号已更换成功");
        } else if (type == BindPhoneActivity.TYPE_UNBIND) {
            phone = CacheUtil.getInstance().getUserInfo().getUser().getMobile();
            tvPhoneNumber.setText("请输入" + phone + "的验证码");
            phone = sp.getString(KeyCode.USER_MOBILE, "");
            nameTextInclude.setText("解绑手机");
            dialog = new HuiFuDialog(this, "你的手机号已解绑成功");
        } else if (type == BindPhoneActivity.TYPE_SETPASS) {
            //     phone = sp.getString(KeyCode.USER_MOBILE, "");

            phone = getIntent().getStringExtra(KEY_PHONE);
            phone = sp.getString(KeyCode.USER_MOBILE, "");
            tvPhoneNumber.setText("请输入" + phone + "的验证码");

            nameTextInclude.setText("短信验证");
        } else if (type == TYPE_FORGETPASS) {
            phone = sp.getString(KeyCode.USER_MOBILE, "");
            tvPhoneNumber.setText("请输入" + phone + "的验证码");
            nameTextInclude.setText("短信验证");
        }


        TimeCount time = new TimeCount(60000, 1000);
        time.start();

        getCode();

    }

    @Override
    public void initWeight() {
        super.initWeight();
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.next:

                if (etvCode.getText().length() != 6) {
                    return;
                }
                if (type == BindPhoneActivity.TYPE_UNBIND) {

                    unBind(phone, etvCode.getText().toString());
                } else if (type == BindPhoneActivity.TYPE_SETPASS || type == TYPE_FORGETPASS) {
                    verificationCode();
                } else
                    bindPhone(phone, etvCode.getText().toString());
                break;
        }
    }

    private void unBind(String phone, String code) {
        RequestParams params = new RequestParams(Url.Url + "/user/unbind");

        params.addHeader("token", Utils.GetToken(this));
        params.addHeader("deviceId", MyApplication.deviceId);
        params.addBodyParameter("bindWay", "mobile");
        params.addBodyParameter("mobile", phone);
        params.addBodyParameter("verifyCode", code);

        XUtil.post(params, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (Utils.callOk(result)) {
                    //       startActivity(new Intent(BindPhoneNextActivity.this, BindPhoneActivity.class));
                    dialog.show();
                    CacheUtil.getInstance().getUserInfo().getUser().setMobile("");
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

    public void verificationCode() {
        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/verifycode/verify");
        requestParams.addBodyParameter("mobile", phone);
        requestParams.addBodyParameter("code", etvCode.getText().toString());

        if (type == TYPE_FORGETPASS) {
            CacheUtil.getInstance().setForgetPassCode(etvCode.getText().toString());
        }

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (Utils.callOk(result)) {
                    if (type == TYPE_SETPASS) {

                        Intent intent = new Intent(BindPhoneNextActivity.this, UpdataPassActivity.class);
                        intent.putExtra(KEY_FLAG, FLAG_SET_PASS);
                        startActivity(intent);
                        Utils.showToast(BindPhoneNextActivity.this, Utils.getMsg(result));

                    } else {
                        Intent intent = new Intent(BindPhoneNextActivity.this, UpdataPassActivity.class);
                        intent.putExtra(KEY_FLAG, FLAG_FORGET_PASS);
                        startActivity(intent);
                        Utils.showToast(BindPhoneNextActivity.this, Utils.getMsg(result));
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


    /**
     * 获取验证码
     * create  wjz
     **/
    private void getCode() {

        requestParams.setUri(Url.Url + "/sms/verifycode/send");
        requestParams.addBodyParameter("mobile", phone);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("==========getCode=========" + result);
                if (!Utils.callOk(result))
                    Utils.showToast(BindPhoneNextActivity.this, Utils.getMsg(result));
                return;


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    /**
     * 60秒验证等待
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tvTime.setText("重发");
            tvTime.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            tvTime.setClickable(false);
            tvTime.setText(millisUntilFinished / 1000 + "秒后重发");
        }
    }

    /**
     * 绑定手机
     * create  wjz
     **/
    private void bindPhone(final String phone, String code) {
        RequestParams params = new RequestParams(Url.Url + "/user/bind");

        params.addHeader("token", Utils.GetToken(this));
        params.addHeader("deviceId", MyApplication.deviceId);
        params.addBodyParameter("bindWay", "mobile");
        params.addBodyParameter("openid", phone);
        params.addBodyParameter("verifyCode", code);

        XUtil.post(params, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!Utils.callOk(result)) return;

//                UerEntity userInfo = CacheUtil.getInstance().getUserInfo();
//                userInfo.getUser().setWechatId(code);
                CacheUtil.getInstance().getUserInfo().getUser().setMobile(phone);
                dialog.show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

}
