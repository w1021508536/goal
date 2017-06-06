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

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/5/31 18:43
 * 修改：
 * 描述：任务中心
 **/
public class TaskActivity extends BaseActivity {

    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.tv_gotoSign_task)
    TextView tvGotoSignTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_task);
        ButterKnife.inject(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        view = findViewById(R.id.view);
        super.initData();
        nameTextInclude.setText("我的任务");
        rightImageInclude.setVisibility(View.VISIBLE);
        rightImageInclude.setImageResource(R.mipmap.qa_icon);
    }

    @Override
    public void initWeight() {
        super.initWeight();
        leftImageInclude.setOnClickListener(this);
        tvGotoSignTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.tv_gotoSign_task:
                startActivity(new Intent(this, SignActivity.class));
                break;
        }
    }
}
