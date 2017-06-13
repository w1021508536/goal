package com.pi.small.goal.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.MainActivity;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class LoginPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView left_image;
    private EditText phone_edit;
    private EditText password_edit;
    private TextView login_text;
    private TextView forget_text;


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


        left_image.setOnClickListener(this);
        forget_text.setOnClickListener(this);
        login_text.setOnClickListener(this);
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
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=======loginPAssword====" + result);

                try {
                    String code = new JSONObject(result).getString("code");

                    if (code.equals("0")) {

                    Utils.putUser(result,LoginPasswordActivity.this);

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
                System.out.println("=======ex.getMessage()====" + ex.getMessage());
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
