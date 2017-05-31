package com.pi.small.goal.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.pi.small.goal.MainActivity;
import com.pi.small.goal.R;
import com.pi.small.goal.login.LoginActivity;
import com.pi.small.goal.utils.Utils;


public class WelcomeActivity extends AppCompatActivity {


    private SharedPreferences sharedPreferences;

    private String nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sharedPreferences = Utils.UserSharedPreferences(WelcomeActivity.this);
        nick = sharedPreferences.getString("nick", "");
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

}
