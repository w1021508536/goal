package com.small.small.goal.message.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class RemarkFriendActivity extends BaseActivity {


    private ImageView left_image;
    private TextView right_text;

    private EditText remark_edit;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_remark_friend);
        super.onCreate(savedInstanceState);
        userId = getIntent().getStringExtra("userId");

        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        right_text = (TextView) findViewById(R.id.right_text);

        remark_edit = (EditText) findViewById(R.id.remark_edit);

        left_image.setOnClickListener(this);
        right_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.right_text:
                if (!remark_edit.getText().toString().trim().equals("")) {
                    KeepBlack();
                }
                break;
        }

    }

    private void KeepBlack() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.FriendEdit);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("friendId", userId);
        requestParams.addBodyParameter("remark", remark_edit.getText().toString().trim());
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("==========拉黑========" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Utils.showToast(RemarkFriendActivity.this, "修改成功");
                        RongIM.getInstance().setCurrentUserInfo(new UserInfo("xmb_user_" + userId, remark_edit.getText().toString().trim(), null));
                        RongIM.getInstance().setMessageAttachedUserInfo(true);
                        finish();
                    } else {
                        Utils.showToast(RemarkFriendActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Utils.showToast(RemarkFriendActivity.this, ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
