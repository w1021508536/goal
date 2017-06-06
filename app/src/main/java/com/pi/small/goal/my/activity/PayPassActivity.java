package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 8:57
 * 修改：
 * 描述：支付密码
 **/
public class PayPassActivity extends BaseActivity {


    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.rl_updatapass_pass)
    RelativeLayout rlUpdatapassPass;
    @InjectView(R.id.rl_forgetPass_pass)
    RelativeLayout rlForgetPassPass;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_paypass);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("支付密码");
        rightImageInclude.setVisibility(View.GONE);
    }

    @Override
    public void initWeight() {
        super.initWeight();
        rlUpdatapassPass.setOnClickListener(this);
        rlForgetPassPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_updatapass_pass:
                startActivity(new Intent(this, UpdataPassActivity.class));
                break;
            case R.id.rl_forgetPass_pass:
                startActivity(new Intent(this, ForgetActivity.class));
                break;
        }
    }
}
