package com.pi.small.goal.message;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.message.activity.FriendSetActivity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class ConversationActivity extends BaseActivity  {

    private ImageView left_image;
    private ImageView right_image;
    private TextView name_text;

    private String name;
    private UserInfo userInfo;


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


    private String RY_userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();


        RY_userId = getIntent().getData().getQueryParameter("targetId");//单聊   userId
        name = getIntent().getData().getQueryParameter("title");// 昵称

//        userInfo = new UserInfo(sharedPreferences.getString("RY_Id", ""), sharedPreferences.getString("nick", ""), Uri.parse("http://www.ghost64.com/qqtupian/zixunImg/local/2016/11/22/14798003915289.jpg"));
//        userInfo.setName(sharedPreferences.getString("nick", ""));
//        userInfo.setUserId(sharedPreferences.getString("RY_Id", ""));
//        userInfo.setPortraitUri(Uri.parse(Url.PhotoUrl + "/" + sharedPreferences.getString("avatar", "")));

//        if (sharedPreferences.getString("avatar", "").equals("")) {
//            userInfo = new UserInfo(sharedPreferences.getString("RY_Id", ""), sharedPreferences.getString("nick", ""), null);
//        } else {
//            userInfo = new UserInfo(sharedPreferences.getString("RY_Id", ""), sharedPreferences.getString("nick", ""), Uri.parse(Url.PhotoUrl + "/" + sharedPreferences.getString("avatar", "")));
//        }
//        userInfo = new UserInfo(sharedPreferences.getString("RY_Id", ""), sharedPreferences.getString("nick", ""), Uri.parse("http://www.ghost64.com/qqtupian/zixunImg/local/2016/11/22/14798003915289.jpg"));
//
//        RongIM.getInstance().setCurrentUserInfo(userInfo);
//        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//            @Override
//            public UserInfo getUserInfo(String s) {
//
//                return userInfo;
//            }
//        }, false);


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
