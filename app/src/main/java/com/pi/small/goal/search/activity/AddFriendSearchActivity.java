package com.pi.small.goal.search.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

public class AddFriendSearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_friend_search);
        super.onCreate(savedInstanceState);

        init();
    }

    private void init() {
        GetUserRecommend();

    }


    private void GetUserRecommend() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.UserRecommend);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("token", Utils.GetToken(this));
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=====GetUserRecommend=======" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                    } else {
                        Utils.showToast(AddFriendSearchActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=====ex=======" + ex.getMessage());
                Utils.showToast(AddFriendSearchActivity.this, ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }
}
