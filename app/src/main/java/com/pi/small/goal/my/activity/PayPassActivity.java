package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.pi.small.goal.my.activity.BindPhoneActivity.KEY_TYPE_UPDATA;
import static com.pi.small.goal.my.activity.BindPhoneNextActivity.KEY_PHONE;

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
    @InjectView(R.id.rl_setPass_pass)
    RelativeLayout rlSetPassPass;

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
    protected void onResume() {
        super.onResume();
        SharedPreferences sp = Utils.UserSharedPreferences(this);
        int payPassword = sp.getInt(KeyCode.USER_PAY_PASS, 0);
        if (payPassword == 0) {
            rlUpdatapassPass.setVisibility(View.GONE);
            rlForgetPassPass.setVisibility(View.GONE);
            rlSetPassPass.setVisibility(View.VISIBLE);
        } else {
            rlSetPassPass.setVisibility(View.GONE);
            rlUpdatapassPass.setVisibility(View.VISIBLE);
            rlForgetPassPass.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void initWeight() {
        super.initWeight();
        rlUpdatapassPass.setOnClickListener(this);
        rlForgetPassPass.setOnClickListener(this);
        rlSetPassPass.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_updatapass_pass:
                startActivity(new Intent(this, UpdataPassActivity.class));
                break;
            case R.id.rl_forgetPass_pass:
                //       startActivity(new Intent(this, ForgetActivity.class));

                Intent intent = new Intent(this, BindPhoneNextActivity.class);
                intent.putExtra(KEY_TYPE_UPDATA, BindPhoneActivity.TYPE_FORGETPASS);
                intent.putExtra(KEY_PHONE, Utils.GetOneStringForSp(this, KeyCode.USER_MOBILE));
                startActivity(intent);

                break;
            case R.id.rl_setPass_pass:
                if (CacheUtil.getInstance().getUserInfo().getUser().getMobile().equals("")) {
                    Utils.showToast(this, "请先绑定手机");
                    return;
                }
                intent = new Intent(this, BindPhoneNextActivity.class);
                intent.putExtra(KEY_TYPE_UPDATA, BindPhoneActivity.TYPE_SETPASS);
                intent.putExtra(KEY_PHONE, Utils.GetOneStringForSp(this, KeyCode.USER_MOBILE));
                startActivity(intent);
                break;
        }
    }
}
