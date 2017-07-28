package com.small.small.goal.wxapi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.telephony.TelephonyManager;

import com.small.small.goal.MainActivity;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.login.LoginActivity;
import com.small.small.goal.my.activity.UserInfoActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.ThirdUtils;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.weixin.view.WXCallbackActivity;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;


public class WXEntryActivity extends WXCallbackActivity implements IWXAPIEventHandler {

    private IWXAPI wx_api;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private TelephonyManager TelephonyMgr;
    private String deviceId;

    private String access_token;
    private String openid_wx;
    private String nick_wx;
    private String avatar_wx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();

        TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();

        wx_api = WXAPIFactory.createWXAPI(this, ThirdUtils.WX_APP_ID, true);
        wx_api.registerApp(ThirdUtils.WX_APP_ID);
        wx_api.handleIntent(getIntent(), this);


    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
        wx_api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
//        ProgressBar.stop();
        switch (req.getType()) {
            case ConstantsAPI.COMMAND_GETMESSAGE_FROM_WX:
                break;
            case ConstantsAPI.COMMAND_SHOWMESSAGE_FROM_WX:
                break;
        }
    }


    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {

        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if (ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX == resp.getType()) {
                    super.onResp(resp);
                    break;
                }
                String code = ((SendAuth.Resp) resp).code;
                GetData(code, ((SendAuth.Resp) resp).state);
//                GetWXData(code);
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
//                ProgressBar.stop();
                finish();
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
//                ProgressBar.stop();
                finish();
                break;
        }
    }


    /**
     * 绑定微信
     * create  wjz
     **/
    private void bindWx(final String code) {
        RequestParams params = new RequestParams(Url.Url + "/user/bind");

        params.addHeader("token", Utils.GetToken(this));
        params.addHeader("deviceId", MyApplication.deviceId);
        params.addBodyParameter("bindWay", "wechat");
        params.addBodyParameter("openid", code);
        params.addBodyParameter("verifyCode", "");

        XUtil.post(params, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!Utils.callOk(result, WXEntryActivity.this)) {

                    Utils.showToast(WXEntryActivity.this, Utils.getMsg(result));
                    finish();
                    return;
                }

//                UerEntity userInfo = CacheUtil.getInstance().getUserInfo();
//                userInfo.getUser().setWechatId(code);
                CacheUtil.getInstance().getUserInfo().getUser().setWechatId(code);
                finish();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void GetData(final String code, final String state) {
        RequestParams params = new RequestParams("https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + ThirdUtils.WX_APP_ID + "&secret=" + ThirdUtils.WX_APP_SECRET + "&code=" + code + "&grant_type=authorization_code");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    access_token = new JSONObject(result).getString("access_token");
                    openid_wx = new JSONObject(result).getString("openid");

                    if (state.equals(UserInfoActivity.BIND_WX)) {
                        bindWx(openid_wx);
                        return;
                    }
                    GetUserInfo(access_token, openid_wx);
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

    private void GetUserInfo(String token, String openid) {
        RequestParams params = new RequestParams("https://api.weixin.qq.com/sns/userinfo?access_token=" + token + "&openid=" + openid + "");
        x.http().get(params, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    avatar_wx = new JSONObject(result).getString("headimgurl");
                    nick_wx = new JSONObject(result).getString("nickname");
                    wxLogin();
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

    private void wxLogin() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Login);
        requestParams.addBodyParameter("loginWay", "wechat");
        requestParams.addBodyParameter("openId", openid_wx);
        requestParams.addBodyParameter("nick", nick_wx);
        requestParams.addBodyParameter("avatar", avatar_wx);
        requestParams.addBodyParameter("deviceId", deviceId);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                try {
                    String code = new JSONObject(result).getString("code");

                    if (code.equals("0")) {

                        Utils.putUser(result, WXEntryActivity.this);

                        Intent intent = new Intent();
                        intent.setClass(WXEntryActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                        LoginActivity.instance.finish();

                    } else {
                        String msg = new JSONObject(result).getString("msg");
                        Utils.showToast(WXEntryActivity.this, msg);

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


}
