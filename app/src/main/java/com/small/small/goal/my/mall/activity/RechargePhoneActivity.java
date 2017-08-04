package com.small.small.goal.my.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.my.mall.adapter.FragmentAdapter;
import com.small.small.goal.my.mall.fragment.PhoneAddmoneyFragment;
import com.small.small.goal.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/7 14:08
 * 修改：
 * 描述：手机充值
 **/
public class RechargePhoneActivity extends BaseActivity {


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
    @InjectView(R.id.ll_top_include)
    LinearLayout llTopInclude;
    @InjectView(R.id.tl)
    TabLayout tl;
    @InjectView(R.id.vp)
    ViewPager vp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_recharge_phone);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("手机充值");
        rightImageInclude.setVisibility(View.GONE);
        tvOkInclude.setText("充值记录");
        tvOkInclude.setVisibility(View.VISIBLE);

        List<Fragment> data = new ArrayList<>();
        data.add(PhoneAddmoneyFragment.newInstance(PhoneAddmoneyFragment.TYPE_HUAFEI));
        data.add(PhoneAddmoneyFragment.newInstance(PhoneAddmoneyFragment.TYPE_LIULIANG));
        vp.setAdapter(new FragmentAdapter(getSupportFragmentManager(), data));
        tl.setupWithViewPager(vp);
        tl.getTabAt(0).setText("      话费充值      ");
        tl.getTabAt(1).setText("      流量充值      ");

        llTopInclude.setBackgroundResource(R.color.bg_include);

        tvOkInclude.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_ok_include:
                startActivity(new Intent(this, PhoneRecordActivity.class));
                break;
        }
    }
}
