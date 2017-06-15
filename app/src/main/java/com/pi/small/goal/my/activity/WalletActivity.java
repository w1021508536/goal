package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.UerEntity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

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
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.tv_money_wallet)
    TextView tvMoneyWallet;
    @InjectView(R.id.tv_walletMoney_walllet)
    TextView tvWalletMoneyWalllet;

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
                startActivity(new Intent(this, MontyToActivity.class));
                break;
            case R.id.rl_toMoney_wallet:    //提现
                startActivity(new Intent(this, ToMoneyActivity.class));
                break;
        }
    }

    private void setData() {
        UerEntity userInfo = CacheUtil.getInstance().getUserInfo();
        tvMoneyWallet.setText((userInfo.getAccount().getBalance() + userInfo.getAccount().getAim()) + "");
        tvWalletMoneyWalllet.setText(userInfo.getAccount().getBalance() + "元");
    }

    @Override
    public void getData() {
        RequestParams requestParams = new RequestParams();
        SharedPreferences sp = Utils.UserSharedPreferences(this);
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.setUri(Url.Url + "/user/my");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!RenameActivity.callOk(result)) return;
                Gson gson = new Gson();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    UerEntity userInfo = gson.fromJson(jsonObject.get("result").toString(), UerEntity.class);

                    CacheUtil.getInstance().setUserInfo(userInfo);
                    setData();
                } catch (JSONException e) {
                    e.printStackTrace();
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
