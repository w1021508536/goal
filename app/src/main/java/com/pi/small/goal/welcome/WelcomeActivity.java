package com.pi.small.goal.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.google.gson.Gson;
import com.pi.small.goal.MainActivity;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.login.LoginActivity;
import com.pi.small.goal.my.activity.RenameActivity;
import com.pi.small.goal.my.entry.UerEntity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;


public class WelcomeActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;

    private String nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sharedPreferences = Utils.UserSharedPreferences(WelcomeActivity.this);
        nick = sharedPreferences.getString("nick", "");
  //      getData();
        init();
    }

    private void init() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (nick.equals("")) {
                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, LoginActivity.class);
                    startActivity(intent);
                    finish();
                } else {

//                    long token_time = Long.valueOf(createTime) * 1000;
//                    long currentTime = System.currentTimeMillis();
//                    if (token_time - currentTime > 0) {
//                        Intent intent = new Intent();
//                        intent.setClass(WelcomeActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Utils.showToast(WelcomeActivity.this, "登录已过期，请重新登录");
//                        Intent intent = new Intent();
//                        intent.setClass(WelcomeActivity.this, LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }


                    Intent intent = new Intent();
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                }


            }
        }, 2000);
    }

    private void getData() {
        RequestParams requestParams = new RequestParams();
        SharedPreferences sp = Utils.UserSharedPreferences(this);
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.setUri(Url.Url + "/user/my");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!RenameActivity.callOk(result)) return;
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    UerEntity userInfo = gson.fromJson(jsonObject.get("result").toString(), UerEntity.class);

                    CacheUtil.getInstance().setUserInfo(userInfo);
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
}
