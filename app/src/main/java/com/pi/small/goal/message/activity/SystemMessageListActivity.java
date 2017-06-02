package com.pi.small.goal.message.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.message.adapter.SystemMessageListAdapter;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SystemMessageListActivity extends BaseActivity  {

    private ImageView left_image;
    private ListView system_list;

    private SystemMessageListAdapter systemListAdapter;

    private List<Map<String, String>> dataList;
    private Map<String, String> map;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_message_list);

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();

        token = sharedPreferences.getString("token", "");

        dataList = new ArrayList<Map<String, String>>();

        systemListAdapter = new SystemMessageListAdapter(this, dataList);
        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        system_list = (ListView) findViewById(R.id.system_list);

        system_list.setAdapter(systemListAdapter);

        left_image.setOnClickListener(this);

        system_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!dataList.get(position).get("url").equals("")) {
                    Intent intent = new Intent();
                    intent.setClass(SystemMessageListActivity.this, SystemMessageDataWebActivity.class);
                    intent.putExtra("url", dataList.get(position).get("url"));
                    startActivity(intent);
                } else {

                }
            }
        });


        GetSystemListData();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
        }

    }


    private void GetSystemListData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.SystemMessageList);
        requestParams.addHeader("token", token);
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        JSONArray contentObject = new JSONObject(result).getJSONArray("result");

                        System.out.println("=============GetSystemListData============" + result);

                        for (int i = 0; i < contentObject.length(); i++) {

                            map = new HashMap<String, String>();
                            JSONObject jsonObject = new JSONObject(contentObject.getJSONObject(i).getString("content"));
                            map.put("title", jsonObject.optString("title"));
                            map.put("brief", jsonObject.optString("brief"));
                            map.put("content", jsonObject.optString("content"));
                            map.put("img", jsonObject.optString("img"));
                            map.put("url", jsonObject.optString("url"));
                            map.put("msgType", contentObject.getJSONObject(i).optString("msgType"));
                            map.put("createTime", contentObject.getJSONObject(i).optString("createTime"));

                            dataList.add(map);
                        }
                        systemListAdapter.notifyDataSetChanged();

                    } else {
                        Utils.showToast(SystemMessageListActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
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


}
