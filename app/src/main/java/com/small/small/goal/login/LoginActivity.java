package com.small.small.goal.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.small.small.goal.MainActivity;
import com.small.small.goal.R;
import com.small.small.goal.register.RegisterActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.ThirdUtils;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.tencent.connect.UserInfo;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private TextView right_text;

    private RelativeLayout whole_layout;

    private TextView phone_text;
    private TextView password_text;
    private LinearLayout wx_layout;
    private LinearLayout qq_layout;

    private TextView register_text;


    /* QQ */
    private Tencent mTencent;
    private UserInfo mQQInfo;
    private BaseUiListener qqListener;
    private String open_id_qq;
    private String access_token_qq;
    private String expires_in_qq;
    private String nick_qq;
    private String avatar_qq;


    /* 微信*/
    public static IWXAPI wx_api;


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

    public static LoginActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        instance = this;

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();

        TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();

        qqListener = new BaseUiListener();

        //微信
        wx_api = WXAPIFactory.createWXAPI(this, ThirdUtils.WX_APP_ID, true);
        wx_api.registerApp(ThirdUtils.WX_APP_ID);
        init();
    }

    private void init() {
        right_text = (TextView) findViewById(R.id.right_text);
        whole_layout = (RelativeLayout) findViewById(R.id.whole_layout);
        phone_text = (TextView) findViewById(R.id.phone_text);
        password_text = (TextView) findViewById(R.id.password_text);
        wx_layout = (LinearLayout) findViewById(R.id.wx_layout);
        qq_layout = (LinearLayout) findViewById(R.id.qq_layout);
        register_text = (TextView) findViewById(R.id.register_text);


        register_text.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);


        right_text.setOnClickListener(this);
        phone_text.setOnClickListener(this);
        password_text.setOnClickListener(this);
        qq_layout.setOnClickListener(this);
        wx_layout.setOnClickListener(this);
        register_text.setOnClickListener(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
    }

    @Override
    public void onClick(View v) {

        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.right_text:
                intent.setClass(this, MainActivity.class);
                startActivity(intent);
                break;
            case R.id.phone_text:
                intent.setClass(this, LoginCodeActivity.class);
                startActivity(intent);
                break;
            case R.id.password_text:
                intent.setClass(this, LoginPasswordActivity.class);
                startActivity(intent);
                break;
            case R.id.wx_layout:
                wxLogin();
                break;
            case R.id.qq_layout:
                //QQ
                mTencent = Tencent.createInstance(ThirdUtils.QQ_APP_ID, this);
                if (!mTencent.isSessionValid()) {
                    mTencent.login(LoginActivity.this, "all", qqListener);
                }
                break;
            case R.id.register_text:
                intent.setClass(this, RegisterActivity.class);
                startActivity(intent);
                break;

        }
    }

    private class BaseUiListener implements IUiListener {
        @Override
        public void onComplete(Object response) {
            doComplete(response);

        }

        protected void doComplete(Object values) {
            try {
                open_id_qq = new JSONObject(values.toString()).getString("openid");
                access_token_qq = new JSONObject(values.toString()).getString("access_token");
                expires_in_qq = new JSONObject(values.toString()).getString("expires_in");
                getQQUserInfo();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onError(UiError e) {
            Toast.makeText(LoginActivity.this, e.errorMessage, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel() {
        }
    }

    public void getQQUserInfo() {
        mTencent.setAccessToken(access_token_qq, expires_in_qq);
        mTencent.setOpenId(open_id_qq);
        UserInfo userInfo = new UserInfo(LoginActivity.this, mTencent.getQQToken());

        IUiListener iUiListener = new IUiListener() {
            @Override
            public void onComplete(Object o) {

                doComplete(o);
            }

            protected void doComplete(Object values) {
                try {
                    nick_qq = new JSONObject(values.toString()).getString("nickname");
                    avatar_qq = new JSONObject(values.toString()).getString("figureurl_qq_1");

                    qqLogin();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        };
        userInfo.getUserInfo(iUiListener);
    }

    private void qqLogin() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Login);
        requestParams.addBodyParameter("loginWay", "qq");
        requestParams.addBodyParameter("openId", open_id_qq);
        requestParams.addBodyParameter("nick", nick_qq);
        requestParams.addBodyParameter("avatar", avatar_qq);
        requestParams.addBodyParameter("deviceId", deviceId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {


                try {
                    String code = new JSONObject(result).getString("code");

                    if (code.equals("0")) {

                        Utils.putUser(result, LoginActivity.this);

                        Intent intent = new Intent();
                        intent.setClass(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        String msg = new JSONObject(result).getString("msg");
                        Utils.showToast(LoginActivity.this, msg);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Tencent.onActivityResultData(requestCode, resultCode, data, qqListener);
    }

    private void wxLogin() {
        boolean isHaveWeixin = wx_api.isWXAppInstalled()
                && wx_api.isWXAppSupportAPI();
        if (isHaveWeixin) {
            final SendAuth.Req req = new SendAuth.Req();
            req.scope = "snsapi_userinfo";
            req.state = "wechat_sdk_demo_test";
            wx_api.sendReq(req);
        } else {
            Utils.showToast(LoginActivity.this, "请先安装微信应用");
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
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
