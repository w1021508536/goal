package com.small.small.goal.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.small.small.goal.MainActivity;
import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

public class LoginCodeActivity extends BaseActivity implements View.OnClickListener {

    private ImageView left_image;
    private EditText phone_edit;
    private EditText code_edit;
    private TextView code_text;
    private TextView login_text;
    private LinearLayout whole_layout;

    private TelephonyManager TelephonyMgr;
    private String deviceId;
    private TimeCount time;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String id;
    private String nick;
    private String avatar;
    private String brief;
    private String wechatId;
    private String qqId;
    private String mobile;
    private String city;
    private String createTime;
    private String updateTime;
    private String token;
    private String imtoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_code);

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();

        TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();

        time = new TimeCount(60000, 1000);

        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        phone_edit = (EditText) findViewById(R.id.phone_edit);
        code_edit = (EditText) findViewById(R.id.code_edit);
        code_text = (TextView) findViewById(R.id.code_text);
        login_text = (TextView) findViewById(R.id.login_text);
        whole_layout = (LinearLayout) findViewById(R.id.whole_layout);

        left_image.setOnClickListener(this);
        login_text.setOnClickListener(this);
        code_text.setOnClickListener(this);

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
            case R.id.login_text:
                if (phone_edit.getText().toString().trim().length() != 11) {
                    Toast.makeText(this, "请填写正确手机号码", Toast.LENGTH_SHORT).show();
                } else {
                    if (code_edit.getText().toString().trim().length() != 6) {
                        Toast.makeText(this, "请填写正确验证码", Toast.LENGTH_SHORT).show();
                    } else {
                        Login();
                    }

                }
                break;
            case R.id.code_text:
                if (phone_edit.getText().toString().trim().length() != 11) {
                    Utils.showToast(this, "请填写正确手机号码");
                } else {
                    GetCode();
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
                System.out.println("==========code=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Utils.showToast(LoginCodeActivity.this, "发送成功");
                    } else {
                        String msg = new JSONObject(result).getString("msg");
                        Utils.showToast(LoginCodeActivity.this, msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Utils.showToast(LoginCodeActivity.this, ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void Login() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Login);
        requestParams.addBodyParameter("loginWay", "mobile");
        requestParams.addBodyParameter("mobile", phone_edit.getText().toString().trim());
        requestParams.addBodyParameter("deviceId", deviceId);
        requestParams.addBodyParameter("verifyCode", code_edit.getText().toString().trim());
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=======qq====result====" + result);

                try {
                    String code = new JSONObject(result).getString("code");

                    if (code.equals("0")) {
                        Utils.putUser(result, LoginCodeActivity.this);

                        Intent intent = new Intent();
                        intent.setClass(LoginCodeActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        LoginActivity.instance.finish();

                    } else {
                        String msg = new JSONObject(result).getString("msg");
                        Utils.showToast(LoginCodeActivity.this, msg);

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
