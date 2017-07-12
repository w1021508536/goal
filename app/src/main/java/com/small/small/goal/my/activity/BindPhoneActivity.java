package com.small.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/7 16:49
 * 修改：
 * 描述：绑定手机号
 **/
public class BindPhoneActivity extends BaseActivity {


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
    @InjectView(R.id.etv_phone)
    EditText etvPhone;
    @InjectView(R.id.next)
    TextView next;

    public static final String KEY_TYPE_UPDATA = "updata";
    public static final int TYPE_BIND = 0;
    public static final int TYPE_UPDATA = 1;
    public static final int TYPE_UNBIND = 2;
    public static final int TYPE_SETPASS = 3;
    public static final int TYPE_FORGETPASS = 4;
    @InjectView(R.id.tv_hint_phone)
    TextView tvHintPhone;
    private int type;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_bindphone);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        type = getIntent().getIntExtra(KEY_TYPE_UPDATA, TYPE_BIND);

        nameTextInclude.setText("绑定手机");
        rightImageInclude.setVisibility(View.GONE);
        if (type == TYPE_UPDATA) {
            nameTextInclude.setText("更换绑定手机");
            tvHintPhone.setText("新手机号");
        }
    }

    @Override
    public void initWeight() {
        super.initWeight();
        next.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        Intent intent;
        switch (v.getId()) {
            case R.id.next:

                if (etvPhone.getText().toString().equals("") || etvPhone.getText().toString().length() != 11)
                    return;
                intent = new Intent(this, BindPhoneNextActivity.class);
                intent.putExtra(KEY_TYPE_UPDATA, type);
                intent.putExtra(BindPhoneNextActivity.KEY_PHONE, etvPhone.getText().toString());
                startActivity(intent);
                break;
        }
    }
}
