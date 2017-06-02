package com.pi.small.goal.my.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 15:04
 * 描述： 修改个人信息
 * 修改：
 **/
public class RenameActivity extends BaseActivity {

    public static final String KEY_TYPE = "type";
    public static final int TYPE_NICK = 0;
    public static final int TYPE_CONTENT = 1;


    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.etv_name_user)
    EditText etvNameUser;
    private int type;
    private SharedPreferences sp;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_rename);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        tvOkInclude.setVisibility(View.VISIBLE);
        rightImageInclude.setVisibility(View.GONE);
        sp = Utils.UserSharedPreferences(this);

        type = getIntent().getIntExtra(KEY_TYPE, 0);


        if (type == 0) {
            nameTextInclude.setText(getResources().getString(R.string.title_updata_name));
            etvNameUser.setText(sp.getString("nick", ""));
        } else {
            nameTextInclude.setText(getResources().getString(R.string.title_updata_content));
            etvNameUser.setText(sp.getString("brief", ""));
        }

        etvNameUser.setSelection(etvNameUser.getText().length());

    }

    @Override
    public void initWeight() {
        super.initWeight();

        tvOkInclude.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_ok_include:
                updata();
                break;
        }
    }

    //errorCode: 405, msg: , result: {"timestamp":1496303943142,"status":405,"error":"Method Not Allowed","exception":"org.springframework.web.HttpRequestMethodNotSupportedException","message":"Request method 'GET' not supported","path":"/user"}
    private void updata() {
        if (etvNameUser.getText().toString().length() == 0) {
            return;
        }
        RequestParams requestParams = new RequestParams(Url.Url + "/user");
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        if (type == TYPE_NICK) {
            requestParams.addBodyParameter("nick", etvNameUser.getText().toString());
        } else {
            requestParams.addBodyParameter("brief", etvNameUser.getText().toString());   //简介
        }
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (callOk(result)) {
                    Utils.showToast(RenameActivity.this, getResources().getString(R.string.updata_ok));
                    SharedPreferences.Editor editor = sp.edit();
                    if (type == TYPE_NICK) {
                        editor.putString("nick", etvNameUser.getText().toString());
                    } else {
                        editor.putString("brief", etvNameUser.getText().toString());
                    }
                    editor.commit();
                    finish();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    public static boolean callOk(String jsonString) {
        String code = "";
        try {
            code = new JSONObject(jsonString).getString("code");

            if (code.equals("0"))
                return true;
            else return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }
}
