package com.small.small.goal.my.mall.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.dialog.MonthDialog;
import com.small.small.goal.my.mall.fragment.RecordFragment;
import com.small.small.goal.utils.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/11 8:47
 * 修改：
 * 描述：qb充值记录
 **/
public class QbRecordActivity extends BaseActivity {

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
    private MonthDialog monthDialog;
    private RecordFragment fragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_qb_record);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("充值记录");
        llTopInclude.setBackgroundColor(getResources().getColor(R.color.bg_include));
        rightImageInclude.setImageResource(R.mipmap.icon_sale_date);
        rightImageInclude.setVisibility(View.GONE);
        fragment = RecordFragment.newInstance(RecordFragment.TYPE_QB);
        getSupportFragmentManager().beginTransaction().add(R.id.fl, fragment).commit();

        monthDialog = new MonthDialog(this, new MonthDialog.OnDialogOkListener() {
            @Override
            public void getSelectTime(String time) {
                if (fragment != null)
                    fragment.setTime(time);
            }
        });

        rightImageInclude.setOnClickListener(this);
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
