package com.small.small.goal.welcome;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.small.small.goal.MainActivity;
import com.small.small.goal.R;
import com.small.small.goal.login.LoginActivity;
import com.small.small.goal.utils.Utils;


public class WelcomeActivity extends AppCompatActivity {

    private RelativeLayout whole_layout;
    private SharedPreferences sharedPreferences;

    private String nick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        sharedPreferences = Utils.UserSharedPreferences(WelcomeActivity.this);
        nick = sharedPreferences.getString("nick", "");
        //      getData();

        whole_layout = (RelativeLayout) findViewById(R.id.whole_layout);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
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
