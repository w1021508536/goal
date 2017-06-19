package com.pi.small.goal.message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.message.activity.FriendSetActivity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Utils;

import butterknife.ButterKnife;

public class ConversationActivity extends BaseActivity {

    private ImageView left_image;
    private ImageView right_image;
    private TextView name_text;

    private String name;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private String RY_userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_conversation);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

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

        if (name != null) {
            name_text.setText(name);
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
