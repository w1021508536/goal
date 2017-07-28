package com.small.small.goal.login;

import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

public class ForgetPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView left_image;
    private RelativeLayout whole_layout;
    private EditText phone_edit;
    private TextView code_text;
    private EditText code_edit;

    private EditText password_edit;
    private EditText password_next_edit;

    private TextView modify_text;

    private TelephonyManager TelephonyMgr;
    private String deviceId;


    private TimeCount time;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();

        time = new TimeCount(60000, 1000);
        init();
    }

    private void init() {

        left_image = (ImageView) findViewById(R.id.left_image);
        whole_layout = (RelativeLayout) findViewById(R.id.whole_layout);
        code_text = (TextView) findViewById(R.id.code_text);
        phone_edit = (EditText) findViewById(R.id.phone_edit);
        code_edit = (EditText) findViewById(R.id.code_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        password_next_edit = (EditText) findViewById(R.id.password_next_edit);

        modify_text = (TextView) findViewById(R.id.modify_text);


        code_text.setClickable(true);

        left_image.setOnClickListener(this);
        code_text.setOnClickListener(this);
        modify_text.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.code_text:
                if (phone_edit.getText().toString().trim().length() != 11) {
                    Toast.makeText(this, "请填写正确手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    GetCode();
                }

                break;
            case R.id.modify_text:

                if (phone_edit.getText().toString().trim().length() != 11) {
                    Toast.makeText(this, "请填写正确手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    if (code_edit.getText().toString().trim().length() != 6) {
                        Toast.makeText(this, "请填写正确验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        if (password_edit.getText().toString().trim().length() < 8) {
                            Toast.makeText(this, "输入密码过短", Toast.LENGTH_SHORT).show();
                        } else {
                            if (!password_edit.getText().toString().trim().equals(password_next_edit.getText().toString().trim())) {
                                Toast.makeText(this, "两次输入密码不同", Toast.LENGTH_SHORT).show();
                            } else {
                                ModifyPassword();
                            }
                        }
                    }

                }


                break;
        }
    }

    private void GetCode() {
        time.start();
        RequestParams requestParams = new RequestParams(Url.Url + Url.GetCode);
        requestParams.addHeader("deviceId", deviceId);
        requestParams.addBodyParameter("mobile", phone_edit.getText().toString().trim());

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Utils.showToast(ForgetPasswordActivity.this, "发送成功");
                    } else {
                        String msg = new JSONObject(result).getString("msg");
                        Utils.showToast(ForgetPasswordActivity.this, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Utils.showToast(ForgetPasswordActivity.this, ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void ModifyPassword() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.ForgetPassword);
        requestParams.addBodyParameter("mobile", phone_edit.getText().toString().trim());
        requestParams.addBodyParameter("password", password_edit.getText().toString().trim());
        requestParams.addBodyParameter("verifyCode", code_edit.getText().toString().trim());
        requestParams.addBodyParameter("deviceId", deviceId);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");

                    if (code.equals("0")) {
                        Utils.showToast(ForgetPasswordActivity.this, "修改密码成功");
                        finish();

                    } else {
                        String msg = new JSONObject(result).getString("msg");
                        Utils.showToast(ForgetPasswordActivity.this, msg);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("")) {
                    Utils.showToast(ForgetPasswordActivity.this, ex.getMessage());
                }
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
            code_text.setText("重新验证");
            code_text.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            code_text.setClickable(false);
            code_text.setText(millisUntilFinished / 1000 + "秒");
        }
    }

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        whole_layout.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
    }
}
