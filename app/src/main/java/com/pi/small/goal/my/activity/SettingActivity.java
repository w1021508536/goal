package com.pi.small.goal.my.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.login.LoginActivity;
import com.pi.small.goal.utils.BaseActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/3 16:45
 * 修改：
 * 描述：  设置的actviity
 **/
public class SettingActivity extends BaseActivity {

    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.rl_idea_setting)
    RelativeLayout rlIdeaSetting;
    @InjectView(R.id.rl_empty_setting)
    RelativeLayout rlEmptySetting;
    @InjectView(R.id.rl_about_setting)
    RelativeLayout rlAboutSetting;
    @InjectView(R.id.tv_logout_setting)
    TextView tvLogoutSetting;
    @InjectView(R.id.rl_pass_setting)
    RelativeLayout rlPassSetting;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_setting);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("设置");
        rightImageInclude.setVisibility(View.GONE);

    }

    @Override
    public void initWeight() {
        super.initWeight();
        rlPassSetting.setOnClickListener(this);
        rlAboutSetting.setOnClickListener(this);
        rlEmptySetting.setOnClickListener(this);
        rlIdeaSetting.setOnClickListener(this);
        tvLogoutSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_logout_setting:
                SharedPreferences.Editor editor = sp.edit();
                editor.clear();
                editor.commit();
                app.exit();
                startActivity(new Intent(this, LoginActivity.class));
                break;
            case R.id.rl_pass_setting:
                startActivity(new Intent(this, PayPassActivity.class));
                break;
            case R.id.rl_idea_setting:
                startActivity(new Intent(this, IdeaBackActivity.class));
                break;
            case R.id.rl_about_setting:
                startActivity(new Intent(this, AboutUsActivity.class));
                break;
            case R.id.rl_empty_setting:
                showDeleteCacheDialog();
                break;
        }
    }

    /**
     * 显示清除缓存的dialog
     * create  wjz
     **/
    private void showDeleteCacheDialog() {

        final AlertDialog deleteCacheDialog = new AlertDialog.Builder(this).create();
        deleteCacheDialog.show();
        deleteCacheDialog.setContentView(R.layout.dialog_delete_cache);
        Window window = deleteCacheDialog.getWindow();
        //            window.setContentView(R.layout.dialog_bugger);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager wm = (WindowManager) this.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params.width = wm.getDefaultDisplay().getWidth();
        window.setAttributes(params);
        //       deleteCacheDialog.setCancelable(false);


        TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel_dialog);
        TextView tv_ok = (TextView) window.findViewById(R.id.tv_ok_dialog);
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCacheDialog.dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
