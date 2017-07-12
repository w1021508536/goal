package com.small.small.goal.register;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
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


import com.small.small.goal.MainActivity;
import com.small.small.goal.R;
import com.small.small.goal.login.LoginActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class RegisterActivity extends BaseActivity implements View.OnClickListener {

    private ImageView left_image;
    private RelativeLayout whole_layout;
    private TextView code_text;

    private EditText invitation_code_edit;
    private EditText phone_edit;
    private EditText code_edit;
    private EditText password_edit;
    private EditText password_next_edit;

    private TextView register_text;
    private TextView term_text;
    private TextView policy_text;

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
        setContentView(R.layout.activity_register);

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();

        TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();

        time = new TimeCount(60000, 1000);
        init();
    }

    private void init() {

        left_image = (ImageView) findViewById(R.id.left_image);
        whole_layout = (RelativeLayout) findViewById(R.id.whole_layout);
        code_text = (TextView) findViewById(R.id.code_text);
        invitation_code_edit = (EditText) findViewById(R.id.invitation_code_edit);
        phone_edit = (EditText) findViewById(R.id.phone_edit);
        code_edit = (EditText) findViewById(R.id.code_edit);
        password_edit = (EditText) findViewById(R.id.password_edit);
        password_next_edit = (EditText) findViewById(R.id.password_next_edit);

        register_text = (TextView) findViewById(R.id.register_text);

        term_text = (TextView) findViewById(R.id.term_text);
        policy_text = (TextView) findViewById(R.id.policy_text);

        code_text.setClickable(true);
        term_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        policy_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);

        left_image.setOnClickListener(this);
        term_text.setOnClickListener(this);
        policy_text.setOnClickListener(this);
        code_text.setOnClickListener(this);
        register_text.setOnClickListener(this);
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
            case R.id.register_text:

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
                                Register();
                            }
                        }
                    }

                }


                break;

            case R.id.term_text:

                break;
            case R.id.policy_text:

                break;
        }
    }

    private void GetCode() {
        time.start();
        RequestParams requestParams = new RequestParams(Url.Url + Url.GetCode);
        requestParams.addHeader("deviceId", deviceId);
        requestParams.addBodyParameter("mobile", phone_edit.getText().toString().trim());

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("==========code=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {


                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void Register() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Register);
        requestParams.addBodyParameter("mobile", phone_edit.getText().toString().trim());
        requestParams.addBodyParameter("password", password_edit.getText().toString().trim());
        requestParams.addBodyParameter("verifyCode", code_edit.getText().toString().trim());
        requestParams.addBodyParameter("deviceId", deviceId);
        x.http().request(HttpMethod.PUT, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("==========register=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");

                    if (code.equals("0")) {

                        JSONObject userObject = new JSONObject(result).getJSONObject("result").getJSONObject("user");
                        id = userObject.getString("id");
                        nick = userObject.getString("nick");
                        avatar = userObject.optString("avatar");
                        brief = userObject.optString("brief");
                        wechatId = userObject.optString("wechatId");
                        qqId = userObject.optString("qqId");
                        mobile = userObject.optString("mobile");
                        city = userObject.optString("city");
                        createTime = userObject.optString("createTime");
                        updateTime = userObject.optString("updateTime");
                        token = new JSONObject(result).getJSONObject("result").optString("token");
                        imtoken = new JSONObject(result).getJSONObject("result").optString("imtoken");


                        editor.putString("id", id);
                        editor.putString("nick", nick);
                        editor.putString("avatar", avatar);
                        editor.putString("brief", brief);
                        editor.putString("wechatId", wechatId);
                        editor.putString("qqId", qqId);
                        editor.putString("mobile", mobile);
                        editor.putString("city", city);
                        editor.putString("createTime", createTime);
                        editor.putString("updateTime", updateTime);
                        editor.putString("token", token);
                        editor.putString("imtoken", imtoken);

                        editor.commit();

                        Intent intent = new Intent();
                        intent.setClass(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        LoginActivity.instance.finish();

                    } else {
                        String msg = new JSONObject(result).getString("msg");
                        Utils.showToast(RegisterActivity.this, msg);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

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
