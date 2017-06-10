package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.aim.activity.SupportAimActivity;
import com.pi.small.goal.message.activity.FriendsListActivity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
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
 * 时间： 2017/5/31 18:43
 * 修改：
 * 描述：任务中心
 **/
public class TaskActivity extends BaseActivity {

    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.textView)
    TextView textView;
    @InjectView(R.id.tv_gotoSign_task)
    TextView tvGotoSignTask;
    @InjectView(R.id.tv_addMoney_task)
    TextView tvAddMoneyTask;
    @InjectView(R.id.tv_friends_task)
    TextView tvFriendsTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_task);
        ButterKnife.inject(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        view = findViewById(R.id.view);
        super.initData();
        nameTextInclude.setText("我的任务");
        rightImageInclude.setVisibility(View.VISIBLE);
        rightImageInclude.setImageResource(R.mipmap.qa_icon);
        //     getSign();
    }

    @Override
    public void getData() {
        super.getData();

        //  getSign();

//        requestParams.setUri(Url.Url + "/task");
//        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
//            @Override
//            public void onSuccess(String result) {
//
//
//                if (!Utils.callOk(result)) return;
//                Gson gson = new Gson();
//                List<EveryTaskGsonEntity> data = gson.fromJson(Utils.getResult(result), new TypeToken<List<EveryTaskGsonEntity>>() {
//                }.getType());
//
//
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });

    }

    @Override
    public void initWeight() {
        super.initWeight();
        leftImageInclude.setOnClickListener(this);
        tvGotoSignTask.setOnClickListener(this);
        tvAddMoneyTask.setOnClickListener(this);
        tvFriendsTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.tv_gotoSign_task:
                startActivity(new Intent(this, SignActivity.class));
                break;
            case R.id.tv_addMoney_task:
                startActivity(new Intent(this, SupportAimActivity.class));
                break;
            case R.id.tv_friends_task:
                startActivity(new Intent(this, FriendsListActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (CacheUtil.getInstance().isSignFlag()) {
            tvGotoSignTask.setText("已完成");
            tvGotoSignTask.setBackgroundResource(R.drawable.background_oval_gray_gray_big);
            tvGotoSignTask.setClickable(false);
        }


//        requestParams.setUri(Url.Url + "/task");
//        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
//            @Override
//            public void onSuccess(String result) {
//
//
//                if (!Utils.callOk(result)) return;
//                Gson gson = new Gson();
//                List<EveryTaskGsonEntity> data = gson.fromJson(Utils.getResult(result), new TypeToken<List<EveryTaskGsonEntity>>() {
//                }.getType());
//
//                for (EveryTaskGsonEntity one:data){
//
//                }
//
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });

    }

    private void getSign() {
        requestParams.setUri(Url.Url + "/signin");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!Utils.callOk(result)) return;
                try {
                    long signTime = (long) ((JSONObject) new JSONObject(result).get("result")).get("lastSignInTime");
                    Date date = new Date(signTime);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
                    Date nowDate = new Date();
                    if (formatter.format(date).equals(formatter.format(nowDate))) {
                        CacheUtil.getInstance().setSignFlag(true);
                    }
                    if (CacheUtil.getInstance().isSignFlag()) {
                        tvGotoSignTask.setText("已完成");
                        tvGotoSignTask.setBackgroundResource(R.drawable.background_oval_gray_gray_big);
                        tvGotoSignTask.setClickable(false);
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
}
