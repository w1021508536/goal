package com.pi.small.goal.search.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class SupportMoneyActivity extends BaseActivity {

    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.right_image)
    ImageView rightImage;
    @InjectView(R.id.head_layout)
    RelativeLayout headLayout;
    @InjectView(R.id.head_image)
    CircleImageView headImage;
    @InjectView(R.id.save_text)
    TextView saveText;
    @InjectView(R.id.money_edit)
    EditText moneyEdit;
    @InjectView(R.id.hook_image)
    ImageView hookImage;
    @InjectView(R.id.hook_layout)
    LinearLayout hookLayout;
    @InjectView(R.id.nick_text)
    TextView nickText;

    private String aimId;
    private String dynamicId;
    private String nick;
    private String avatar;
    private String money;

    private Boolean isRead = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support_money);
        ButterKnife.inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        dynamicId = getIntent().getStringExtra("dynamicId");
        aimId = getIntent().getStringExtra("aimId");
        nick = getIntent().getStringExtra("nick");
        avatar = getIntent().getStringExtra("avatar");

        init();
    }

    private void init() {

        nickText.setText(nick);
        if (!Utils.GetPhotoPath(avatar).equals("")) {
            Picasso.with(this).load(Utils.GetPhotoPath(avatar)).into(headImage);
        }
        moneyEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().equals("0")) {
                    moneyEdit.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }


    @OnClick({R.id.left_image, R.id.save_text, R.id.hook_layout})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.left_image:
                Utils.showToast(SupportMoneyActivity.this, "支付取消");
                setResult(Code.FailCode, intent);
                finish();
                break;
            case R.id.save_text:
                if (moneyEdit.getText().toString().trim().equals("")) {
                    Utils.showToast(this, "金额不能为空");
                } else {
                    if (!isRead) {
                        Utils.showToast(this, "请仔细阅读相关协议");
                    } else {
                        money = moneyEdit.getText().toString().trim();
                        intent.setClass(this, SupportPayActivity.class);
                        intent.putExtra("aimId", aimId);
                        intent.putExtra("money", money);
                        intent.putExtra("dynamicId", dynamicId);
                        startActivityForResult(intent, Code.SupportAim);

                    }
                }
                break;
            case R.id.hook_layout:
                if (isRead) {
                    isRead = false;
                    hookImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_off));
                } else {
                    isRead = true;
                    hookImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_hook_on));
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


    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        headLayout.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
        ;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Utils.showToast(SupportMoneyActivity.this, "支付取消");
            Intent intent = new Intent();
            setResult(Code.FailCode, intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }
}
