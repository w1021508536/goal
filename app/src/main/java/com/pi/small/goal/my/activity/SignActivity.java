package com.pi.small.goal.my.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 17:06
 * 修改：
 * 描述：   签到也面
 **/
public class SignActivity extends BaseActivity {


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
    @InjectView(R.id.tv_signHint_sign)
    TextView tvSignHintSign;
    @InjectView(R.id.tv_signDays_sign)
    TextView tvSignDaysSign;
    @InjectView(R.id.tv_sign_hintDay_sign)
    TextView tvSignHintDaySign;
    @InjectView(R.id.img_day1_sign)
    ImageView imgDay1Sign;
    @InjectView(R.id.tv_day1_sign)
    TextView tvDay1Sign;
    @InjectView(R.id.img_day2_sign)
    ImageView imgDay2Sign;
    @InjectView(R.id.tv_day2_sign)
    TextView tvDay2Sign;
    @InjectView(R.id.img_day3_sign)
    ImageView imgDay3Sign;
    @InjectView(R.id.tv_day3_sign)
    TextView tvDay3Sign;
    @InjectView(R.id.img_day4_sign)
    ImageView imgDay4Sign;
    @InjectView(R.id.tv_day4_sign)
    TextView tvDay4Sign;
    @InjectView(R.id.img_day5_sign)
    ImageView imgDay5Sign;
    @InjectView(R.id.tv_day5_sign)
    TextView tvDay5Sign;
    @InjectView(R.id.img_day6_sign)
    ImageView imgDay6Sign;
    @InjectView(R.id.tv_day6_sign)
    TextView tvDay6Sign;
    @InjectView(R.id.imageView7)
    ImageView imageView7;
    @InjectView(R.id.tv_sing_sign)
    Button tvSingSign;
    @InjectView(R.id.tv_hint_sign)
    TextView tvHintSign;
    private long signTime;
    private int days;
    private View[] tvViews;
    private TextView thiDayImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_sign);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        tvViews = new TextView[]{tvDay1Sign, tvDay2Sign, tvDay3Sign, tvDay4Sign, tvDay5Sign, tvDay6Sign,};
        nameTextInclude.setText("签到");
        tvOkInclude.setText("规则");
        tvOkInclude.setVisibility(View.VISIBLE);
        rightImageInclude.setVisibility(View.GONE);

        if (CacheUtil.getInstance().isSignFlag()) {
            tvSingSign.setText("今日已签到");
            tvSingSign.setBackgroundResource(R.mipmap.btn_signed);
        }

        super.initData();
    }


    /**
     * 获取签到天数
     * create  wjz
     **/
    @Override
    public void getData() {
        super.getData();
        requestParams.setUri(Url.Url + "/signin");
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
//{"msg":"success","code":0,"result":{"signInId":4,"lastSignInTime":1496714247000,"userId":48,"days":2,"drew":0,"rewardType":1,"exp":6},"pageNum":0,"pageSize":0,"pageTotal":0,"total":0}
                if (!RenameActivity.callOk(result)) {
                    tvSignHintSign.setText("您尚未签到");
                    tvSignDaysSign.setVisibility(View.GONE);
                    tvSignHintDaySign.setVisibility(View.GONE);
                    tvViews[0].setBackgroundResource(R.drawable.background_yuan_orange);
                    return;
                }

                try {

                    signTime = (long) ((JSONObject) new JSONObject(result).get("result")).get("lastSignInTime");
                    days = (int) ((JSONObject) new JSONObject(result).get("result")).get("days");
                    tvSignDaysSign.setText(days + "");

                    Date date = new Date(signTime);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
                    Date nowDate = new Date();

                    if (!formatter.format(date).equals(formatter.format(nowDate))) {
                        days = 0;
                    }

                    if (days == 0) {
                        tvSignHintSign.setText("您尚未签到");
                        tvSignDaysSign.setVisibility(View.GONE);
                        tvSignHintDaySign.setVisibility(View.GONE);
                    }

//                    if (formatter.format(date).equals(formatter.format(nowDate)))
//                        return;

                    for (int i = 0; i < tvViews.length; i++) {

                        if (i < days) {
                            tvViews[i].setVisibility(View.INVISIBLE);
                        }
                        if (i == days && days < tvViews.length && !formatter.format(date).equals(formatter.format(nowDate))) {
                            tvViews[i].setBackgroundResource(R.drawable.background_yuan_orange);
                            thiDayImageView = (TextView) tvViews[i];
                        }
                    }

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

    @Override
    public void initWeight() {
        super.initWeight();
        tvSingSign.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_sing_sign:

                sign();

                break;
        }
    }

    /**
     * 签到的操作
     * create  wjz
     **/
    private void sign() {
        Date date = new Date(signTime);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
        Date nowDate = new Date();
        if (formatter.format(date).equals(formatter.format(nowDate))) {
            return;
        }
        requestParams.setUri(Url.Url + "/signin");
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {

            @Override
            public void onSuccess(String result) {

                if (!RenameActivity.callOk(result)) return;

                try {
                    JSONObject obj = (JSONObject) new JSONObject(result).get("result");
                    int exp = (int) obj.get("exp");
                    signOkDialog(exp);
                    tvSingSign.setText("今日已签到");
                    tvSingSign.setBackgroundResource(R.mipmap.btn_signed);
                    CacheUtil.getInstance().setSignFlag(true);
                    CacheUtil.getInstance().getUserInfo().getTaskInfo().setFinishTaskCount(CacheUtil.getInstance().getUserInfo().getTaskInfo().getFinishTaskCount() + 1);
                } catch (JSONException e) {
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
        signTime = new Date().getTime();
    }


    /**
     * 显示签到成功的dialog
     * create  wjz
     **/
    private void signOkDialog(int exp) {

        final AlertDialog deleteCacheDialog = new AlertDialog.Builder(this).create();
        deleteCacheDialog.show();
        deleteCacheDialog.setContentView(R.layout.dialog_sing_ok);
        Window window = deleteCacheDialog.getWindow();
        //            window.setContentView(R.layout.dialog_bugger);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager wm = (WindowManager) this.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params.width = wm.getDefaultDisplay().getWidth();
        window.setAttributes(params);
        //      deleteCacheDialog.setCancelable(false);


        TextView tv_exp = (TextView) window.findViewById(R.id.tv_addExp_dialog);
        TextView tv_ok = (TextView) window.findViewById(R.id.tv_sigeOK);
        tv_exp.setText("+" + exp + "点经验");

        tvSignDaysSign.setText((Integer.valueOf(tvSignDaysSign.getText().toString()) + 1) + "");
        tvSignDaysSign.setVisibility(View.VISIBLE);
        tvSignHintDaySign.setVisibility(View.VISIBLE);
        tvSignHintSign.setText("恭喜！您已连续签到");
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (thiDayImageView != null)
                    thiDayImageView.setVisibility(View.INVISIBLE);
                deleteCacheDialog.dismiss();
                startActivity(new Intent(SignActivity.this, FlopActivity.class));
            }
        });
    }
}

