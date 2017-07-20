package com.small.small.goal.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.xutils.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/18 13:33
 * 修改：
 * 描述：微信提现
 **/
public class ToMoneyWeChatActivity extends BaseActivity {
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
    @InjectView(R.id.etv_money)
    EditText etvMoney;
    @InjectView(R.id.img_delete)
    ImageView imgDelete;
    @InjectView(R.id.tv_moneyHint)
    TextView tvMoneyHint;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv)
    TextView tv;
    @InjectView(R.id.pay_text)
    TextView payText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_wechat_tomoney);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        tvMoney.setText(CacheUtil.getInstance().getUserInfo().getAccount().getBalance() + "");
        imgDelete.setVisibility(View.GONE);
        rightImageInclude.setVisibility(View.GONE);
        nameTextInclude.setText("提现");
    }

    @Override
    public void initWeight() {
        super.initWeight();
        etvMoney.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0 && Double.valueOf(s.toString()) > CacheUtil.getInstance().getUserInfo().getAccount().getBalance()) {
                    etvMoney.setText(tvMoney.getText().toString());
                    etvMoney.setSelection(tvMoney.getText().toString().length());
                }
                if (s.length() > 0) {
                    imgDelete.setVisibility(View.VISIBLE);
                } else {
                    imgDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        imgDelete.setOnClickListener(this);
        payText.setOnClickListener(this);
        tv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.img_delete:
                etvMoney.setText("");
                break;
            case R.id.pay_text:
                tixian();
                break;
            case R.id.tv:
                etvMoney.setText(tvMoney.getText().toString());
                etvMoney.setSelection(etvMoney.getText().toString().length());
                break;
        }
    }

    private void tixian() {
        if (etvMoney.getText().toString().equals("")) return;
        if ("".equals(CacheUtil.getInstance().getUserInfo().getUser().getWechatId())) {
            Utils.showToast(this, "暂未绑定微信");
            return;
        }
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/account/cash");
        requestParams.addBodyParameter("accountType", "2");
        requestParams.addBodyParameter("amount", etvMoney.getText().toString());
        requestParams.addBodyParameter("account", "");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (Utils.callOk(result)) {
                    Utils.showToast(ToMoneyWeChatActivity.this, "提交成功");
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

}
