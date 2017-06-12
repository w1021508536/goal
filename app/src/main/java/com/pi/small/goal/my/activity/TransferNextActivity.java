package com.pi.small.goal.my.activity;

import android.graphics.drawable.Drawable;
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
 * 时间： 2017/6/12 16:40
 * 修改：
 * 描述： 期权转让给别人
 **/
public class TransferNextActivity extends BaseActivity {


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
    @InjectView(R.id.img_icon)
    ImageView imgIcon;
    @InjectView(R.id.img_right)
    ImageView imgRight;
    @InjectView(R.id.tv_ok)
    TextView tvOk;
    @InjectView(R.id.tv_hint)
    TextView tvHint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_transfer_next);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_warn);
        drawable.setBounds(0, 0, Utils.dip2px(this, 10), Utils.dip2px(this, 10));
        tvHint.setCompoundDrawables(drawable, null, null, null);

        nameTextInclude.setText("转让");
        rightImageInclude.setVisibility(View.GONE);
    }
}
