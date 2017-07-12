package com.small.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.small.small.goal.my.activity.BindPhoneActivity.KEY_TYPE_UPDATA;
import static com.small.small.goal.my.activity.BindPhoneActivity.TYPE_UNBIND;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/8 14:32
 * 修改：
 * 描述： 更换手机号
 **/
public class UpdataPhoneActivity extends BaseActivity {

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
    @InjectView(R.id.tv_phone_updataPhone)
    TextView tvPhoneUpdataPhone;
    @InjectView(R.id.next)
    TextView next;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.updata_phone);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();

        String mobile = CacheUtil.getInstance().getUserInfo().getUser().getMobile();
        tvPhoneUpdataPhone.setText(mobile);
        nameTextInclude.setText("解绑手机");
        rightImageInclude.setVisibility(View.GONE);
    }

    @Override
    public void initWeight() {
        super.initWeight();
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.next:
//                Intent intent = new Intent(this, BindPhoneActivity.class);
//                intent.putExtra(BindPhoneActivity.KEY_TYPE_UPDATA, BindPhoneActivity.TYPE_UPDATA);
//                startActivity(intent);
                Intent intent = new Intent(this, BindPhoneNextActivity.class);
                intent.putExtra(KEY_TYPE_UPDATA, TYPE_UNBIND);
                //   intent.putExtra(BindPhoneNextActivity.KEY_PHONE, etvPhone.getText().toString());
                startActivity(intent);
                finish();
                break;
        }
    }
}
