package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.pi.small.goal.my.activity.UpdataPassActivity.FLAG_NEW;
import static com.pi.small.goal.my.activity.UpdataPassActivity.KEY_FLAG;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 11:24
 * 修改：
 * 描述：  忘记密码的下一页  输入银行卡信息页面
 **/
public class ForgetNextActivity extends BaseActivity {

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
    @InjectView(R.id.tv_next_forget)
    TextView tvNextForget;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.forget_next);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("忘记密码");
        rightImageInclude.setVisibility(View.GONE);
        tvNextForget.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_next_forget:
                Intent intent = new Intent(this, UpdataPassActivity.class);
                intent.putExtra(KEY_FLAG, FLAG_NEW);
                startActivity(intent);
                startActivity(intent);
                break;
        }
    }
}
