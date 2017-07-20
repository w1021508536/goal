package com.small.small.goal.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.adapter.EveryDayTaskAdapter;
import com.small.small.goal.my.adapter.RedAdapter;
import com.small.small.goal.my.entry.EveryTaskAdapterEntity;
import com.small.small.goal.my.entry.EveryTaskGsonEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/5/31 18:43
 * 修改：
 * 描述：任务中心
 **/
public class TaskListActivity extends BaseActivity {


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
    @InjectView(R.id.lv_task)
    ListView lvTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_task_list);
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
        getSign();
    }

    @Override
    public void getData() {
        super.getData();

        RequestParams requestParams = new RequestParams();
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.setUri(Url.Url + "/signin");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!Utils.callOk(result,TaskListActivity.this)) return;
                try {
                    int status = (int) ((JSONObject) new JSONObject(result).get("result")).get("status");
                    if (status == 1) {
                        CacheUtil.getInstance().setSignFlag(true);
                        CacheUtil.getInstance().getMap().put(KeyCode.AIM_SIGN, true);
                    }
//                    if (CacheUtil.getInstance().isSignFlag()) {
//                        tvGotoSignTask.setBackgroundResource(R.drawable.background_oval_gray_gray);
//                        tvGotoSignTask.setClickable(false);
//                    }
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
        leftImageInclude.setOnClickListener(this);
//        tvGotoSignTask.setOnClickListener(this);
//        tvAddMoneyTask.setOnClickListener(this);
//        tvFriendsTask.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.left_image_include:
                finish();
                break;
//            case R.id.tv_gotoSign_task:
//                startActivity(new Intent(this, SignActivity.class));
//                break;
//            case R.id.tv_addMoney_task:
//                startActivity(new Intent(this, SupportAimActivity.class));
//                break;
//            case R.id.tv_friends_task:
//                startActivity(new Intent(this, FriendsListActivity.class));
//                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        if (CacheUtil.getInstance().isSignFlag()) {
//            tvGotoSignTask.setBackgroundResource(R.drawable.background_oval_gray_gray);
//            tvGotoSignTask.setClickable(false);
//        }


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
        requestParams.setUri(Url.Url + "/task");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!Utils.callOk(result,TaskListActivity.this)) return;
                Gson gson = new Gson();
                List<EveryTaskGsonEntity> data = gson.fromJson(Utils.getResultStr(result), new TypeToken<List<EveryTaskGsonEntity>>() {
                }.getType());

                List<EveryTaskAdapterEntity> newTaskData = new ArrayList<EveryTaskAdapterEntity>();
                //int taskType, int reward, int times, int status, long updateTime, String name, int upperLimit, int finish, int type
                List<EveryTaskAdapterEntity> everyTaskData = new ArrayList<EveryTaskAdapterEntity>();
                everyTaskData.add(new EveryTaskAdapterEntity(0, 0, "", 2, 0, 0, 0, 0, "每日签到", 0, 0, RedAdapter.TYPE_CONTENT));
                for (EveryTaskGsonEntity one : data) {

                    if (one.getTaskType() == 2) {
                        everyTaskData.add(new EveryTaskAdapterEntity(one.getUserTaskId(), one.getRewardType(), one.getAction(), one.getTaskType(), one.getReward(), one.getTimes(), one.getStatus(), one.getUpdateTime(), one.getName(), one.getUpperLimit(), one.getFinish(), RedAdapter.TYPE_CONTENT));
                    } else {
                        newTaskData.add(new EveryTaskAdapterEntity(one.getUserTaskId(), one.getRewardType(), one.getAction(), one.getTaskType(), one.getReward(), one.getTimes(), one.getStatus(), one.getUpdateTime(), one.getName(), one.getUpperLimit(), one.getFinish(), RedAdapter.TYPE_CONTENT));
                    }

                }
                List<EveryTaskAdapterEntity> adapterData = new ArrayList<EveryTaskAdapterEntity>();


                adapterData.add(new EveryTaskAdapterEntity(RedAdapter.TYPE_TITLE, "--日常任务--"));
                adapterData.addAll(everyTaskData);
                adapterData.add(new EveryTaskAdapterEntity(RedAdapter.TYPE_TITLE, "--新手任务--"));
                adapterData.addAll(newTaskData);
                EveryDayTaskAdapter adapter = new EveryDayTaskAdapter(TaskListActivity.this);
                adapter.setData(adapterData);
                lvTask.setAdapter(adapter);
                ImageView imageView = new ImageView(TaskListActivity.this);
                imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, Utils.dip2px(TaskListActivity.this, 150)));
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setImageResource(R.mipmap.icon_task_banner2x);
                lvTask.addHeaderView(imageView);

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
