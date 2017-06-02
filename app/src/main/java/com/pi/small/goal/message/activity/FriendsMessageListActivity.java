package com.pi.small.goal.message.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.message.adapter.FriendsMessageListAdapter;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.SwipeListView;
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

public class FriendsMessageListActivity extends BaseActivity  {


    private ImageView left_image;
    private TextView right_text;

    private SwipeListView friends_list;


    private List<Map<String, String>> dataList;
    private Map<String, String> map;

    private FriendsMessageListAdapter friendsMessageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends_message_list);

        dataList = new ArrayList<Map<String, String>>();


        init();
    }

    private void init() {
        left_image = (ImageView) findViewById(R.id.left_image);
        right_text = (TextView) findViewById(R.id.right_text);
        friends_list = (SwipeListView) findViewById(R.id.friends_list);
        friendsMessageListAdapter = new FriendsMessageListAdapter(this, dataList, friends_list.getRightViewWidth());
        friends_list.setAdapter(friendsMessageListAdapter);

        left_image.setOnClickListener(this);
        right_text.setOnClickListener(this);

        friendsMessageListAdapter.setOnRightItemClickListener(new FriendsMessageListAdapter.onRightItemClickListener() {

            @Override
            public void onRightItemClick(View v, final int position) {
                RequestParams requestParams = new RequestParams(Url.Url + Url.FriendsMessageListDelete);
                requestParams.addHeader("token", Utils.GetToken(FriendsMessageListActivity.this));
                requestParams.addHeader("deviceId", MyApplication.deviceId);
                requestParams.addBodyParameter("msgId", dataList.get(position).get("id"));
                x.http().post(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            String code = new JSONObject(result).getString("code");
                            if (code.equals("0")) {
                                dataList.remove(position);
                                friendsMessageListAdapter.notifyDataSetChanged();
                            } else {
                                Utils.showToast(FriendsMessageListActivity.this, new JSONObject(result).getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Utils.showToast(FriendsMessageListActivity.this, ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });


            }
        });
        friendsMessageListAdapter.setOnAgreeItemClickListener(new FriendsMessageListAdapter.onAgreeItemClickListener() {

            @Override
            public void onAgreeItemClick(View v, final int position) {
                RequestParams requestParams = new RequestParams(Url.Url + Url.FriendAgree);
                requestParams.addHeader("token", Utils.GetToken(FriendsMessageListActivity.this));
                requestParams.addHeader("deviceId", MyApplication.deviceId);
                requestParams.addBodyParameter("uid", dataList.get(position).get("sendUserId"));
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            String code = new JSONObject(result).getString("code");
                            if (code.equals("0")) {
                                dataList.get(position).put("status", "1");
                                friendsMessageListAdapter.notifyDataSetChanged();
                            } else {
                                Utils.showToast(FriendsMessageListActivity.this, new JSONObject(result).getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Utils.showToast(FriendsMessageListActivity.this, ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });


            }
        });
        friendsMessageListAdapter.setOnRefuseItemClickListener(new FriendsMessageListAdapter.onRefuseItemClickListener() {

            @Override
            public void onRefuseItemClick(View v, final int position) {
                RequestParams requestParams = new RequestParams(Url.Url + Url.FriendRefuse);
                requestParams.addHeader("token", Utils.GetToken(FriendsMessageListActivity.this));
                requestParams.addHeader("deviceId", MyApplication.deviceId);
                requestParams.addBodyParameter("uid", dataList.get(position).get("sendUserId"));
                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            String code = new JSONObject(result).getString("code");
                            if (code.equals("0")) {
                                dataList.get(position).put("status", "-1");
                                friendsMessageListAdapter.notifyDataSetChanged();
                            } else {
                                Utils.showToast(FriendsMessageListActivity.this, new JSONObject(result).getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Utils.showToast(FriendsMessageListActivity.this, ex.getMessage());
                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });


            }
        });

        GetFriendsMessageList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_text:
                ClearFriendsList();
                break;
            case R.id.left_image:
                finish();
                break;
        }
    }

    private void GetFriendsMessageList() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.FriendsMessageList);
        requestParams.addHeader("token", Utils.GetToken(FriendsMessageListActivity.this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=======FriendsMessageList========" + result);
                try {

                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        dataList.clear();
                        JSONArray contentObject = new JSONObject(result).getJSONArray("result");

//                        {"id":18,"userId":43,"createTime":1495848554000,"msgType":2,
//                                "content":"{\"operate\":\"APPLY_ADD_FRIEND\",\"user\":{\"id\":43,\"nick\":\"13693361208\",\"avatar\":\"\"," +
//                                "\"brief\":\"\",\"city\":\"\",\"sex\":0}}","status":0,"isRead":1},

                        //status  0 未处理  1 已同意  -1 已拒绝
                        for (int i = 0; i < contentObject.length(); i++) {
                            map = new HashMap<String, String>();
                            JSONObject jsonObject = new JSONObject(contentObject.getJSONObject(i).getString("content"));
                            map.put("id", contentObject.getJSONObject(i).getString("id"));
                            map.put("userId", contentObject.getJSONObject(i).getString("userId"));
                            map.put("createTime", contentObject.getJSONObject(i).getString("createTime"));
                            map.put("msgType", contentObject.getJSONObject(i).getString("msgType"));
                            map.put("isRead", contentObject.getJSONObject(i).getString("isRead"));
                            map.put("status", contentObject.getJSONObject(i).getString("status"));
                            map.put("operate", jsonObject.getString("operate"));
                            map.put("sendUserId", jsonObject.getJSONObject("user").getString("id"));
                            map.put("sendUserNick", jsonObject.getJSONObject("user").getString("nick"));
                            map.put("sendUserAvatar", jsonObject.getJSONObject("user").getString("avatar"));
                            map.put("sendUserBrief", jsonObject.getJSONObject("user").getString("brief"));
                            map.put("sendUserCity", jsonObject.getJSONObject("user").getString("city"));
                            map.put("sendUserSex", jsonObject.getJSONObject("user").getString("sex"));

                            dataList.add(map);
                        }

                        friendsMessageListAdapter.notifyDataSetChanged();

                    } else {
                        Utils.showToast(FriendsMessageListActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=======FriendsMessageList=====ex===" + ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });

    }


    private void ClearFriendsList() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.FriendsMessageListClear);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        dataList.clear();
                        friendsMessageListAdapter.notifyDataSetChanged();
                        Utils.showToast(FriendsMessageListActivity.this, "清理成功");
                    } else {
                        Utils.showToast(FriendsMessageListActivity.this, new JSONObject(result).getString("msg"));
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
