package com.pi.small.goal.aim.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

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

    private Boolean isRead = false;

    private String content;
    private String aimId;
    private String money;
    private String img1;
    private String img2;
    private String img3;

    private String video = "";

    private int budget;
    private int haveMoney;
    private int MaxMoney;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_money);
        ButterKnife.inject(this);

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();

        content = sharedPreferences.getString("content", "");
        aimId = sharedPreferences.getString("aimId", "");

        img1 = sharedPreferences.getString("img1", "");
        img2 = sharedPreferences.getString("img2", "");
        img3 = sharedPreferences.getString("img3", "");

        budget = Integer.valueOf(sharedPreferences.getString("budget", "0"));
        haveMoney = Integer.valueOf(sharedPreferences.getString("money", "0"));


        init();

    }

    @OnClick({R.id.left_image, R.id.right_image, R.id.save_text, R.id.hook_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
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
                            DynamicAim();
                        }
                    }
                }
                DynamicAim();
                break;
            case R.id.hook_layout:
                if (isRead) {
                    isRead = false;
                    hook_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
                } else {
                    hook_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                    isRead = true;
                }
                break;
        }
    }


    private void init() {

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


    private void DynamicAim() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.AimDynamic);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("content", content);
        requestParams.addBodyParameter("aimId", aimId);
        requestParams.addBodyParameter("money", money);
        requestParams.addBodyParameter("img1", img1);
        requestParams.addBodyParameter("img2", img2);
        requestParams.addBodyParameter("img3", img3);
        requestParams.addBodyParameter("video", "");
        x.http().request(HttpMethod.PUT, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("==============AimDynamic=========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {


                    } else {
                        Utils.showToast(SaveMoneyActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (ex.getMessage() != null) {
                    Utils.showToast(SaveMoneyActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });


    }
}
