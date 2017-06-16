package com.pi.small.goal.aim.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class SaveMoneyActivity extends BaseActivity {


    @InjectView(R.id.left_image)
    ImageView left_image;
    @InjectView(R.id.right_image)
    ImageView right_image;
    @InjectView(R.id.save_text)
    TextView save_text;
    @InjectView(R.id.hook_image)
    ImageView hook_image;
    @InjectView(R.id.hook_layout)
    LinearLayout hook_layout;
    @InjectView(R.id.money_edit)
    EditText money_edit;

    private Boolean isRead = true;

    private String content;
    private String aimId;
    private String money;
    private String img1;
    private String img2;
    private String img3;

    private String video = "";

    private int budget;
    private Double haveMoney;
    private Double MaxMoney;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_save_money);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();

        content = getIntent().getStringExtra("content");
        aimId = getIntent().getStringExtra("aimId");

        img1 = getIntent().getStringExtra("img1");
        img2 = getIntent().getStringExtra("img2");
        img3 = getIntent().getStringExtra("img3");

        budget = Integer.valueOf(getIntent().getStringExtra("budget"));
        haveMoney = Double.valueOf(getIntent().getStringExtra("money"));


        init();

    }

    @OnClick({R.id.left_image, R.id.right_image, R.id.save_text, R.id.hook_layout})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.left_image:
                Utils.showToast(SaveMoneyActivity.this, "支付取消");
                setResult(Code.FailCode, intent);
                finish();
                break;
            case R.id.right_image:
                break;
            case R.id.save_text:
                if (money_edit.getText().toString().trim().equals("")) {
                    Utils.showToast(this, "金额不能为空");
                } else {
                    if (Integer.valueOf(money_edit.getText().toString().trim()) > MaxMoney) {
                        Utils.showToast(this, "输入金额不得超过" + MaxMoney);
                    } else {
                        if (!isRead) {
                            Utils.showToast(this, "请仔细阅读相关协议");
                        } else {

                            money = money_edit.getText().toString().trim();
                            intent.setClass(this, PayActivity.class);
                            intent.putExtra("aimId", aimId);
                            intent.putExtra("money", money);
                            intent.putExtra("img1", img1);
                            intent.putExtra("img2", img2);
                            intent.putExtra("img3", img3);
                            intent.putExtra("content", content);
                            startActivityForResult(intent, Code.SupportAim);

                        }
                    }
                }
                break;
            case R.id.hook_layout:
                if (isRead) {
                    isRead = false;
                    hook_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                } else {
                    isRead = true;
                    hook_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                }
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == Code.SupportAim) {
            Intent intent = new Intent();
            intent.putExtra("money", money);
            setResult(Code.SupportAim, intent);
            finish();

        } else if (resultCode == Code.FailCode) {
            Intent intent = new Intent();
            intent.putExtra("money", "0");
            setResult(Code.FailCode, intent);
            finish();

        }
    }


    private void init() {

        System.out.println("========================" + budget + "=====" + haveMoney);
        MaxMoney = budget - haveMoney;

        money_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("0")) {
                    money_edit.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Utils.showToast(SaveMoneyActivity.this, "支付取消");
            Intent intent = new Intent();
            setResult(Code.FailCode, intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }
}
