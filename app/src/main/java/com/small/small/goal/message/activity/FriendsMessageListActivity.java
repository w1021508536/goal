package com.small.small.goal.message.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.message.adapter.FriendsMessageListAdapter;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.SwipeListView;
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

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class FriendsMessageListActivity extends BaseActivity {


    @InjectView(R.id.left_image)
    ImageView left_image;
    @InjectView(R.id.right_text)
    TextView right_text;
    @InjectView(R.id.friends_list)
    SwipeListView friends_list;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;
    @InjectView(R.id.img_empty)
    ImageView imgEmpty;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;


    private List<Map<String, String>> dataList;
    private Map<String, String> map;

    private FriendsMessageListAdapter friendsMessageListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_friends_message_list);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);


        init();
    }


    private void init() {
        dataList = new ArrayList<Map<String, String>>();
        friendsMessageListAdapter = new FriendsMessageListAdapter(this, dataList, friends_list.getRightViewWidth());
        friends_list.setAdapter(friendsMessageListAdapter);

        friendsMessageListAdapter.setOnRightItemClickListener(new FriendsMessageListAdapter.onRightItemClickListener() {

            @Override
            public void onRightItemClick(View v, final int position) {
                RequestParams requestParams = new RequestParams(Url.Url + Url.FriendsMessageListDelete);
                requestParams.addHeader("token", Utils.GetToken(FriendsMessageListActivity.this));
                requestParams.addHeader("deviceId", MyApplication.deviceId);
                requestParams.addBodyParameter("msgId", dataList.get(position).get("id"));
                XUtil.post(requestParams, FriendsMessageListActivity.this, new XUtil.XCallBackLinstener() {
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
                requestParams.addBodyParameter("msgId", dataList.get(position).get("id"));
                requestParams.addBodyParameter("uid", dataList.get(position).get("sendUserId"));
                XUtil.get(requestParams, FriendsMessageListActivity.this, new XUtil.XCallBackLinstener() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            System.out.println("========同意==============" + result);
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
                requestParams.addBodyParameter("msgId", dataList.get(position).get("id"));
                XUtil.get(requestParams, FriendsMessageListActivity.this, new XUtil.XCallBackLinstener() {
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

                    }

                    @Override
                    public void onFinished() {

                    }
                });

            }
        });
        if (Utils.isNetworkConnected(FriendsMessageListActivity.this)) {
            nullLayout.setClickable(false);
            nullLayout.setVisibility(View.GONE);
            GetFriendsMessageList();
        } else {
            nullLayout.setClickable(true);
            nullLayout.setVisibility(View.VISIBLE);
            imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
            tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
        }
    }


    @OnClick({R.id.left_image, R.id.right_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.right_text:
                if (dataList.size() > 0) {
                    ClearFriendsList();
                }
                break;
            case R.id.left_image:
                finish();
                break;
            case R.id.null_layout:
                if (Utils.isNetworkConnected(FriendsMessageListActivity.this)) {
                    nullLayout.setClickable(false);
                    nullLayout.setVisibility(View.GONE);
                    GetFriendsMessageList();
                } else {
                    Utils.showToast(FriendsMessageListActivity.this, "请检查网络是否连接");
                    nullLayout.setClickable(true);
                    nullLayout.setVisibility(View.VISIBLE);
                    imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                    tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                }
                break;
        }
    }

    private void GetFriendsMessageList() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.FriendsMessageList);
        requestParams.addHeader("token", Utils.GetToken(FriendsMessageListActivity.this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=======FriendsMessageList========" + result);
                try {

                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        dataList.clear();
                        JSONArray contentObject = new JSONObject(result).getJSONArray("result");
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

                        if (dataList.size() > 0) {
                            nullLayout.setVisibility(View.GONE);

                        } else {
                            nullLayout.setClickable(false);
                            nullLayout.setVisibility(View.VISIBLE);
                            imgEmpty.setImageResource(R.mipmap.bg_null_info);
                            tvEmpty.setText("暂 无 任 何 消 息 通 知 ~");
                        }
                        friendsMessageListAdapter.notifyDataSetChanged();

                    } else if (code.equals("100000")) {
                        if (dataList.size() < 1) {
                            nullLayout.setClickable(false);
                            nullLayout.setVisibility(View.VISIBLE);
                            imgEmpty.setImageResource(R.mipmap.bg_null_info);
                            tvEmpty.setText("暂 无 任 何 消 息 通 知 ~");
                        }

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


    private void ClearFriendsList() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.FriendsMessageListClear);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
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
            public void onFinished() {

            }
        });

    }


}
