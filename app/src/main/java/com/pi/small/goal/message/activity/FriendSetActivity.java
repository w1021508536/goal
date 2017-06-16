package com.pi.small.goal.message.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class FriendSetActivity extends BaseActivity {

    private ImageView left_image;

    private RelativeLayout remark_layout;
    private RelativeLayout clear_layout;
    private RelativeLayout report_layout;
    private RelativeLayout black_layout;
    private Switch black_switch;


    private String RY_userId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_friend_set);
        super.onCreate(savedInstanceState);

        RY_userId = getIntent().getStringExtra("RY_userId");
        userId = RY_userId.substring(RY_userId.length() - 2, RY_userId.length());
        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);

        remark_layout = (RelativeLayout) findViewById(R.id.remark_layout);
        clear_layout = (RelativeLayout) findViewById(R.id.clear_layout);
        report_layout = (RelativeLayout) findViewById(R.id.report_layout);
        black_layout = (RelativeLayout) findViewById(R.id.black_layout);
        black_switch = (Switch) findViewById(R.id.black_switch);


        left_image.setOnClickListener(this);
        clear_layout.setOnClickListener(this);
        black_switch.setOnClickListener(this);
        report_layout.setOnClickListener(this);
        remark_layout.setOnClickListener(this);


        black_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {
                // TODO Auto-generated method stub
                if (isChecked) {
                    AddBlack();
                    System.out.println("==========true==============");
                } else {
                    RemoveBlack();
                    System.out.println("==========false==============");
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.remark_layout:
                Intent intent = new Intent();
                intent.setClass(this, RemarkFriendActivity.class);
                intent.putExtra("userId", userId);
                startActivity(intent);

                break;
            case R.id.report_layout:
                Report();
                break;
            case R.id.black_switch:

                break;
            case R.id.clear_layout:
                ClearConversation();
                break;

        }
    }

    private void AddBlack() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.FriendBlackAdd);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("friendId", userId);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("==========拉黑========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Utils.showToast(FriendSetActivity.this, "拉黑成功");
                    } else {
                        Utils.showToast(FriendSetActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Utils.showToast(FriendSetActivity.this, ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void RemoveBlack() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.FriendBlackDelete);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("friendId", userId);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("==========移除拉黑========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Utils.showToast(FriendSetActivity.this, "移除成功");
                    } else {
                        Utils.showToast(FriendSetActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Utils.showToast(FriendSetActivity.this, ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void Report() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.ReportUser);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("uid", userId);
        XUtil.put(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Utils.showToast(FriendSetActivity.this, "投诉成功");
                    } else {
                        Utils.showToast(FriendSetActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Utils.showToast(FriendSetActivity.this, ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void ClearConversation() {

        RongIM.getInstance().clearMessages(Conversation.ConversationType.PRIVATE, RY_userId, new RongIMClient.ResultCallback<Boolean>() {
            @Override
            public void onSuccess(Boolean aBoolean) {

                if (aBoolean) {
                    Utils.showToast(FriendSetActivity.this, "清理成功");
                } else {
                    Utils.showToast(FriendSetActivity.this, "清理失败");
                }

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Utils.showToast(FriendSetActivity.this, errorCode.getMessage());
            }
        });

    }
}
