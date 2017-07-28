package com.small.small.goal.message.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.message.adapter.AddFriendAdapter;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddFriendActivity extends BaseActivity {

    private ImageView left_image;
    private ImageView delete_image;
    private EditText search_edit;
    private ListView friends_list;

    private ImageView imgEmpty;
    private TextView tvEmpty;
    private RelativeLayout nullLayout;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String token;

    private AddFriendAdapter addFriendAdapter;


    private List<Map<String, String>> dataList;
    private Map<String, String> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_friend);
        super.onCreate(savedInstanceState);

        dataList = new ArrayList<Map<String, String>>();

        addFriendAdapter = new AddFriendAdapter(this, dataList);
        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);

        search_edit = (EditText) findViewById(R.id.search_edit);
        delete_image = (ImageView) findViewById(R.id.delete_image);
        friends_list = (ListView) findViewById(R.id.friends_list);
        imgEmpty = (ImageView) findViewById(R.id.img_empty);
        tvEmpty = (TextView) findViewById(R.id.tv_empty);
        nullLayout = (RelativeLayout) findViewById(R.id.null_layout);

        friends_list.setAdapter(addFriendAdapter);

        left_image.setOnClickListener(this);
        delete_image.setOnClickListener(this);
        nullLayout.setOnClickListener(this);
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                if (search_edit.getText().toString().length() > 0) {
                    delete_image.setVisibility(View.VISIBLE);
                } else {
                    delete_image.setVisibility(View.GONE);
                }

            }
        });

        search_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (!search_edit.getText().toString().trim().equals("")) {
                        if (Utils.isNetworkConnected(AddFriendActivity.this)) {
                            nullLayout.setClickable(false);
                            nullLayout.setVisibility(View.GONE);
                            SearchUser();
                        } else {
                            nullLayout.setClickable(true);
                            nullLayout.setVisibility(View.VISIBLE);
                            imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                            tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                        }

                    }
                }

                return false;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.delete_image:
                search_edit.setText("");
                break;
            case R.id.null_layout:
                if (!search_edit.getText().toString().trim().equals("")) {
                    if (Utils.isNetworkConnected(AddFriendActivity.this)) {
                        nullLayout.setClickable(false);
                        nullLayout.setVisibility(View.GONE);
                        SearchUser();
                    } else {
                        Utils.showToast(AddFriendActivity.this, "请检查网络是否连接");
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                        tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                    }
                }
                break;
        }

    }

    private void SearchUser() {

        RequestParams requestParams = new RequestParams(Url.Url + Url.SearchUser);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("nick", search_edit.getText().toString().trim());
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    dataList.clear();
                    if (code.equals("0")) {
                        JSONArray array = new JSONObject(result).getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {
                            map = new HashMap<String, String>();
//                            {"id":27,"nick":"123","avatar":"","brief":"","city":"","sex":0}
                            map.put("id", array.getJSONObject(i).getString("id"));
                            map.put("nick", array.getJSONObject(i).getString("nick"));
                            map.put("avatar", array.getJSONObject(i).getString("avatar"));
                            map.put("brief", array.getJSONObject(i).getString("brief"));
                            map.put("city", array.getJSONObject(i).getString("city"));
                            map.put("sex", array.getJSONObject(i).getString("sex"));
                            map.put("friend", IsFriend(array.getJSONObject(i).getString("nick")));

                            dataList.add(map);
                        }
                        addFriendAdapter.notifyDataSetChanged();
                    } else if (code.equals("100000")) {
                        nullLayout.setClickable(false);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_null_search);
                        tvEmpty.setText("没 有 搜 索 到 用 户");
                    } else {
//                        Utils.showToast(SearchKeyActivity.this, new JSONObject(result).getString("msg"));
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_wrong);
                        tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
            }

            @Override
            public void onFinished() {

            }
        });
    }


    //0  是好友 1 不是好友
    private String IsFriend(String name) {
        if (name.equals(Utils.UserSharedPreferences(this).getString("nick",""))){
            return "0";
        }else {
            return "1";
        }
    }
}
