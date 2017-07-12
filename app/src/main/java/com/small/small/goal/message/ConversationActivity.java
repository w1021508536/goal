package com.small.small.goal.message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.message.activity.FriendSetActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Utils;

public class ConversationActivity extends BaseActivity implements View.OnClickListener {

    private ImageView left_image;
    private ImageView right_image;
    private TextView name_text;
    private View view;
    private String name;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private String RY_userId;
    private MyApplication myApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);



        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();

        RY_userId = getIntent().getData().getQueryParameter("targetId");//单聊   userId
        name = getIntent().getData().getQueryParameter("title");// 昵称

        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        right_image = (ImageView) findViewById(R.id.right_image);
        name_text = (TextView) findViewById(R.id.name_text);
        view = findViewById(R.id.view);
        if (name != null) {
            name_text.setText(name);
        }

        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            view.setVisibility(View.GONE);
        }

        left_image.setOnClickListener(this);
        right_image.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.right_image:
                Intent intent = new Intent();
                intent.setClass(this, FriendSetActivity.class);
                intent.putExtra("RY_userId", RY_userId);
                startActivity(intent);

                break;
        }

    }
}
