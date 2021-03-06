package com.small.small.goal.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.small.small.goal.MainActivity;
import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

public class LoginPasswordActivity extends BaseActivity implements View.OnClickListener {

    private ImageView left_image;
    private EditText phone_edit;
    private EditText password_edit;
    private TextView login_text;
    private TextView forget_text;
    private LinearLayout whole_layout;


    private TelephonyManager TelephonyMgr;
    private String deviceId;

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
        setContentView(R.layout.activity_login_password);

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();

        TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();

        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        phone_edit = (EditText) findViewById(R.id.phone_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        login_text = (TextView) findViewById(R.id.login_text);
        forget_text = (TextView) findViewById(R.id.forget_text);
        whole_layout = (LinearLayout) findViewById(R.id.whole_layout);

        left_image.setOnClickListener(this);
        forget_text.setOnClickListener(this);
        login_text.setOnClickListener(this);

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
                    Utils.showToast(this, "请填写正确手机号码");
                } else {
                    if (password_edit.getText().toString().trim().length() == 0) {
                        Utils.showToast(this, "密码不可为空");
                    } else {
                        Login();
                    }

                }
                break;
            case R.id.forget_text:
                Intent intent = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intent);

                break;
        }
    }

    private void Login() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Login);
        requestParams.addBodyParameter("loginWay", "mobile");
        requestParams.addBodyParameter("mobile", phone_edit.getText().toString().trim());
        requestParams.addBodyParameter("password", password_edit.getText().toString().trim());
        requestParams.addBodyParameter("deviceId", deviceId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                try {
                    String code = new JSONObject(result).getString("code");

                    if (code.equals("0")) {

                        Utils.putUser(result, LoginPasswordActivity.this);

                        Intent intent = new Intent();
                        intent.setClass(LoginPasswordActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        LoginActivity.instance.finish();

                    } else {
                        String msg = new JSONObject(result).getString("msg");
                        Utils.showToast(LoginPasswordActivity.this, msg);

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
