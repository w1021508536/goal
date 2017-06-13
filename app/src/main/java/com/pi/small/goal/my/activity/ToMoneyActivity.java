package com.pi.small.goal.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/6 22:11
 * 修改：
 * 描述：提现
 **/
public class ToMoneyActivity extends BaseActivity {


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
    @InjectView(R.id.etv_money_money)
    EditText etvMoneyMoney;
    @InjectView(R.id.pay_text)
    TextView payText;
    @InjectView(R.id.tv_moneyHint_toMoney)
    TextView tvMoneyHintToMoney;
    @InjectView(R.id.tv_toMoney_toMoney)
    TextView tvToMoneyToMoney;
    @InjectView(R.id.tv_money_toMOney)
    TextView tvMoneyToMOney;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_tomoney);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("提现");
        rightImageInclude.setVisibility(View.GONE);
        tvMoneyHintToMoney.setText("可提现金额");
        tvMoneyToMOney.setText(CacheUtil.getInstance().getUserInfo().getAccount().getBalance() + "");
        tvToMoneyToMoney.setOnClickListener(this);
    }

    @Override
    public void initWeight() {
        super.initWeight();
        payText.setOnClickListener(this);
        etvMoneyMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    if (Double.valueOf(s.toString()) > Double.valueOf(tvMoneyToMOney.getText().toString())) {
                        etvMoneyMoney.setText(tvMoneyToMOney.getText().toString());
                        etvMoneyMoney.setSelection(etvMoneyMoney.getText().toString().length());
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_toMoney_toMoney:
                etvMoneyMoney.setText(CacheUtil.getInstance().getUserInfo().getAccount().getBalance() + "");
                etvMoneyMoney.setSelection(etvMoneyMoney.getText().toString().length());
                break;
        }
    }
}
