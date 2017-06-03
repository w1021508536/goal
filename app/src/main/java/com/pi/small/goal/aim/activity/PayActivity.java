package com.pi.small.goal.aim.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pingplusplus.android.Pingpp;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class PayActivity extends AppCompatActivity {

    @InjectView(R.id.left_image)
    ImageView left_image;
    @InjectView(R.id.right_image)
    ImageView right_image;
    @InjectView(R.id.balance_image)
    ImageView balance_image;
    @InjectView(R.id.balance_right_image)
    ImageView balance_right_image;
    @InjectView(R.id.wechat_image)
    ImageView wechat_image;
    @InjectView(R.id.wechat_right_image)
    ImageView wechat_right_image;
    @InjectView(R.id.balance_layout)
    RelativeLayout balance_layout;
    @InjectView(R.id.wechat_layout)
    RelativeLayout wechat_layout;
    @InjectView(R.id.alipay_image)
    ImageView alipay_image;
    @InjectView(R.id.alipay_right_image)
    ImageView alipay_right_image;
    @InjectView(R.id.alipay_layout)
    RelativeLayout alipay_layout;
    @InjectView(R.id.union_image)
    ImageView union_image;
    @InjectView(R.id.union_right_image)
    ImageView union_right_image;
    @InjectView(R.id.card_text)
    TextView card_text;
    @InjectView(R.id.union_layout)
    RelativeLayout union_layout;
    @InjectView(R.id.hook_image)
    ImageView hook_image;
    @InjectView(R.id.hook_layout)
    LinearLayout hook_layout;
    @InjectView(R.id.pay_text)
    TextView pay_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.inject(this);


        init();
    }


    @OnClick({R.id.left_image, R.id.right_image, R.id.balance_layout, R.id.wechat_layout, R.id.alipay_layout, R.id.union_layout, R.id.hook_layout, R.id.pay_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.left_image:

                break;
            case R.id.right_image:

                break;
            case R.id.balance_layout:

                break;
            case R.id.wechat_layout:
                Pingpp.createPayment(PayActivity.this, "");
                break;
            case R.id.alipay_layout:

                break;
            case R.id.union_layout:

                break;
            case R.id.hook_layout:

                break;
            case R.id.pay_text:

                break;
        }
    }

    private void init() {
        union_layout.setVisibility(View.GONE);
    }


    private void GetPayData(){

    }


}
