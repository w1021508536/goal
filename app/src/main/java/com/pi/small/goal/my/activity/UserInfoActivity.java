package com.pi.small.goal.my.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_user);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }
    @Override

    public void initData() {

        nameTextInclude.setText("个人资料");
        SharedPreferences sp = Utils.UserSharedPreferences(this);
        tvUsernameUser.setText(sp.getString("nick", ""));
        view=findViewById(R.id.view);
        super.initData();
    }

    @Override
    public void initWeight() {
        super.initWeight();
        leftImageInclude.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.left_image_include:
                finish();
                break;
        }
    }
}
