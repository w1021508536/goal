package com.small.small.goal.my.mall.activity;

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
import com.small.small.goal.my.dialog.MonthDialog;
import com.small.small.goal.my.mall.adapter.FragmentAdapter;
import com.small.small.goal.my.mall.fragment.RecordFragment;
import com.small.small.goal.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/11 8:49
 * 修改：
 * 描述： 话费充值记录
 **/
public class PhoneRecordActivity extends BaseActivity {

    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.tl)
    TabLayout tl;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.ll_top_include)
    LinearLayout llTopInclude;
    @InjectView(R.id.vp)
    ViewPager vp;
    private MonthDialog monthDialog;
    private int selectPosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_phone_record);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        final List<Fragment> fragments = new ArrayList<>();
        fragments.add(RecordFragment.newInstance(RecordFragment.TYPE_HUAFEI));
        fragments.add(RecordFragment.newInstance(RecordFragment.TYPE_LIULIANG));
        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), fragments);
        vp.setAdapter(adapter);
        tl.setupWithViewPager(vp);

        tl.getTabAt(0).setText("    话费    ");
        tl.getTabAt(1).setText("    流量    ");

        rightImageInclude.setOnClickListener(this);
        monthDialog = new MonthDialog(this, new MonthDialog.OnDialogOkListener() {
            @Override
            public void getSelectTime(String time) {
                ((RecordFragment) fragments.get(selectPosition)).setTime(time);
            }
        });
        tl.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectPosition = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_image_include:
                monthDialog.show();
                break;
        }
    }
}
