package com.pi.small.goal.message.activity;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class RemarkFriendActivity extends AppCompatActivity implements View.OnClickListener {


    private ImageView left_image;
    private TextView right_text;

    private EditText remark_edit;

    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remark_friend);
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
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
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
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }
}
