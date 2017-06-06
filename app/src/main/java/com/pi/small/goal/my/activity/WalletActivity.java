package com.pi.small.goal.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/3 8:56
 * 修改：
 * 描述： 我的钱包
 **/
public class WalletActivity extends BaseActivity {
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.img_balance_wallet)
    ImageView imgBalanceWallet;
    @InjectView(R.id.rl_balance_wallet)
    RelativeLayout rlBalanceWallet;
    @InjectView(R.id.img_bank_wallet)
    ImageView imgBankWallet;
    @InjectView(R.id.rl_bank_wallet)
    RelativeLayout rlBankWallet;
    @InjectView(R.id.img_moneyTo_wallet)
    ImageView imgMoneyToWallet;
    @InjectView(R.id.rl_moneyTo_wallet)
    RelativeLayout rlMoneyToWallet;
    @InjectView(R.id.img_toMoney_wallet)
    ImageView imgToMoneyWallet;
    @InjectView(R.id.rl_toMoney_wallet)
    RelativeLayout rlToMoneyWallet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_wallet);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("我的钱包");
        rightImageInclude.setVisibility(View.GONE);
    }

    @Override
    public void initWeight() {
        super.initWeight();
        //    rlBalanceWallet.setOnClickListener(this);
        rlBankWallet.setOnClickListener(this);
        rlMoneyToWallet.setOnClickListener(this);
        rlToMoneyWallet.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_moneyTo_wallet:    //充值

                break;
            case R.id.rl_toMoney_wallet:    //提现
                break;
        }
    }
}
