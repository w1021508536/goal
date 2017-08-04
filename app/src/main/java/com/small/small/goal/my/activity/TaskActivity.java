package com.small.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.small.small.goal.MainActivity;
import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;

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
    @InjectView(R.id.tv_goGreat_task)
    TextView tvGoGreatTask;
    @InjectView(R.id.tv_goShare_task)
    TextView tvGoShareTask;
    @InjectView(R.id.tv_goComment_task)
    TextView tvGoCommentTask;
    @InjectView(R.id.tv_invitationFriend_task)
    TextView tvInvitationFriendTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_task);
        ButterKnife.inject(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        view = findViewById(R.id.view);

        nameTextInclude.setText("我的任务");
        rightImageInclude.setVisibility(View.VISIBLE);
        rightImageInclude.setImageResource(R.mipmap.qa_icon);

        leftImageInclude.setOnClickListener(this);
        tvAddMoneyTask.setOnClickListener(this);
        tvGotoSignTask.setOnClickListener(this);
        tvFriendsTask.setOnClickListener(this);
        tvGoShareTask.setOnClickListener(this);
        tvGoGreatTask.setOnClickListener(this);
        tvGoCommentTask.setOnClickListener(this);
        tvInvitationFriendTask.setOnClickListener(this);      //邀请好友

        super.initData();
    }

    @Override
    public void getData() {
        super.getData();

        Map<String, Boolean> map = CacheUtil.getInstance().getMap();
        Set<String> strings = map.keySet();
        for (String s : strings) {

           if (s.equals(KeyCode.AIM_SHARE) && map.get(s)) {          //分享
                tvGoShareTask.setText("1/1");
                tvGoShareTask.setBackgroundResource(R.drawable.background_oval_gray_gray_big);
                tvGoShareTask.setClickable(false);
            } else if (s.equals(KeyCode.AIM_SUPPORT) && map.get(s)) {          //助力
                tvFriendsTask.setText("已完成");
                tvFriendsTask.setBackgroundResource(R.drawable.background_oval_gray_gray_big);
                tvFriendsTask.setClickable(false);
            }
        }

        for (int i = 0; i < CacheUtil.getInstance().getEveryTaskGsonEntityList().size(); i++) {
            if (CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getUserTaskId() == 6) {
                if (CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getFinishTimes() < 10) {
                    tvGoGreatTask.setText(CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getFinishTimes() + "/10");
                } else {
                    tvGoGreatTask.setText("10/10");
                    tvGoGreatTask.setBackgroundResource(R.drawable.background_oval_gray_gray_big);
                    tvGoGreatTask.setClickable(false);
                }
            } else if (CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getUserTaskId() == 7) {
                if (CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getFinishTimes() < 5) {
                    tvGoCommentTask.setText(CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getFinishTimes() + "/5");
                } else {
                    tvGoCommentTask.setText("5/5");
                    tvGoCommentTask.setBackgroundResource(R.drawable.background_oval_gray_gray_big);
                    tvGoCommentTask.setClickable(false);
                }
            }
        }


    }

    @Override
    public void initWeight() {
        super.initWeight();

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
                CacheUtil.getInstance().setTaskAddMoneyToMainFlag(true);
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_friends_task:
//                startActivity(new Intent(this, FriendsListActivity.class));
//                break;

            case R.id.tv_goGreat_task:
                CacheUtil.getInstance().setTaskToMainFlag(true);
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_goShare_task:
            case R.id.tv_goComment_task:
                CacheUtil.getInstance().setTaskToMainFlag(true);
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.tv_invitationFriend_task:
                share();
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


    }

    private void getSign() {
        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/signin");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!Utils.callOk(result, TaskActivity.this)) return;
                try {
                    long signTime = (long) ((JSONObject) new JSONObject(result).get("result")).get("lastSignInTime");
                    Date date = new Date(signTime);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
                    Date nowDate = new Date();
                    if (formatter.format(date).equals(formatter.format(nowDate))) {
                        CacheUtil.getInstance().setSignFlag(true);
                        CacheUtil.getInstance().getMap().put(KeyCode.AIM_SIGN, true);
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

    /**
     * 分享
     * create  wjz
     **/
    private void share() {

        new ShareAction(this).withText("hello")
                .setDisplayList(SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(TaskActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(TaskActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(TaskActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
}
