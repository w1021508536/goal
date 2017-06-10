package com.pi.small.goal.my.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.img_delete)
    ImageView imgDelete;
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
            etvNameUser.setText(sp.getString(KeyCode.USER_NICK, ""));
            etvNameUser.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});
        } else {
            nameTextInclude.setText(getResources().getString(R.string.title_updata_content));
            etvNameUser.setText(sp.getString(KeyCode.USER_BRIEF, ""));
            etvNameUser.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)});
        }

        etvNameUser.setSelection(etvNameUser.getText().length());
        if (etvNameUser.getText().length() < 1) {
            imgDelete.setVisibility(View.GONE);
        }

    }

    @Override
    public void initWeight() {
        super.initWeight();

        tvOkInclude.setOnClickListener(this);

        etvNameUser.addTextChangedListener(new myTextWatch());
        imgDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_ok_include:
                updata();
                break;
            case R.id.img_delete:
                etvNameUser.setText("");
                break;
        }
    }

    //errorCode: 405, msg: , result: {"timestamp":1496303943142,"status":405,"error":"Method Not Allowed","exception":"org.springframework.web.HttpRequestMethodNotSupportedException","message":"Request method 'GET' not supported","path":"/user"}
    private void updata() {
        if (etvNameUser.getText().toString().length() == 0) {
            return;
        }
        requestParams.setUri(Url.Url + "/user");
        requestParams.addHeader(KeyCode.USER_TOKEN, sp.getString(KeyCode.USER_TOKEN, ""));
        requestParams.addHeader(KeyCode.USER_DEVICEID, MyApplication.deviceId);
        if (type == TYPE_NICK) {
            requestParams.addBodyParameter(KeyCode.USER_NICK, etvNameUser.getText().toString());
        } else {
            requestParams.addBodyParameter(KeyCode.USER_BRIEF, etvNameUser.getText().toString());   //简介
        }
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (callOk(result)) {
                    Utils.showToast(RenameActivity.this, getResources().getString(R.string.updata_ok));
                    SharedPreferences.Editor editor = sp.edit();
                    if (type == TYPE_NICK) {
                        editor.putString(KeyCode.USER_NICK, etvNameUser.getText().toString());
                    } else {
                        editor.putString(KeyCode.USER_BRIEF, etvNameUser.getText().toString());
                    }
                    editor.commit();
                    finish();
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

    class myTextWatch implements TextWatcher {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s.length() > 0) {
                imgDelete.setVisibility(View.VISIBLE);
            } else imgDelete.setVisibility(View.GONE);
        }
    }

    /**
     * 返回的json是否是成功的
     * create  wjz
     **/
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

    /**
     * 返回的json是否是成功的
     * create  wjz
     **/
    public static String getMsg(String jsonString) {
        String msg = "";
        try {
            msg = new JSONObject(jsonString).getString("msg");

            if (msg.equals("0"))
                return msg;
            else return msg;
        } catch (JSONException e) {
            e.printStackTrace();
            return msg;
        }
    }
}
