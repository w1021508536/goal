package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/5/31 17:48
 * 修改：
 * 描述：个人中心
 **/
public class UserInfoActivity extends BaseActivity {

    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_username_user)
    TextView tvUsernameUser;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.icon_user)
    CircleImageView iconUser;
    @InjectView(R.id.rl_icon_user)
    RelativeLayout rlIconUser;
    @InjectView(R.id.rl_username_user)
    RelativeLayout rlUsernameUser;
    @InjectView(R.id.rl_content_user)
    RelativeLayout rlContentUser;
    @InjectView(R.id.rl_denji_user)
    RelativeLayout rlDenjiUser;
    @InjectView(R.id.rl_wx_user)
    RelativeLayout rlWxUser;
    @InjectView(R.id.rl_phone_user)
    RelativeLayout rlPhoneUser;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.tv_brief_user)
    TextView tvBriefUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override

    public void initData() {

        nameTextInclude.setText(getResources().getString(R.string.title_user_activity));

        view = findViewById(R.id.view);
        super.initData();
        //  x.image().bind(sp.getString("avatar", ""), iconUser);
    }

    @Override
    public void initWeight() {
        super.initWeight();
        leftImageInclude.setOnClickListener(this);
        rlUsernameUser.setOnClickListener(this);
        rlContentUser.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.rl_content_user:
                intent = new Intent(this, RenameActivity.class);
                intent.putExtra(RenameActivity.KEY_TYPE, RenameActivity.TYPE_CONTENT);
                startActivity(intent);
                break;
            case R.id.rl_username_user:
                intent = new Intent(this, RenameActivity.class);
                intent.putExtra(RenameActivity.KEY_TYPE, RenameActivity.TYPE_NICK);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = Utils.UserSharedPreferences(this);
        tvUsernameUser.setText(sp.getString("nick", ""));
        if (sp.getString("avatar", "").length() != 0) {
            Picasso.with(this).load(sp.getString("avatar", "")).into(iconUser);
        }
        String brief = sp.getString("brief", "");
        tvBriefUser.setText(sp.getString("brief", ""));
    }
}
