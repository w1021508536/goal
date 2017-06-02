package com.pi.small.goal.utils;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.pi.small.goal.R;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/5/31 17:12
 * 修改：
 * 描述：
 **/
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public View view;
    public ImageView leftImageInclude;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initWeight();
    }

    /**
     * 设置点击事件等
     * create  wjz
     **/
    public void initWeight() {

    }

    /**
     * 初始化數據
     * create  wjz
     **/

    public void initData() {
        view = findViewById(R.id.view);
        if (view == null)
            return;
        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            view.setVisibility(View.GONE);
        }
        leftImageInclude = (ImageView) findViewById(R.id.left_image_include);
        if (leftImageInclude != null) {
            leftImageInclude.setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.left_image_include:
                finish();
                break;
        }

    }
}
