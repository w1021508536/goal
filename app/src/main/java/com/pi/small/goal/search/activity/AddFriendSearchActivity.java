package com.pi.small.goal.search.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.message.activity.AddFriendActivity;
import com.pi.small.goal.search.adapter.AddFriendSearchAdapter;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.MyListView;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.AimEntity;
import com.pi.small.goal.utils.entity.UserSearchEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class AddFriendSearchActivity extends BaseActivity {

    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.right_image)
    ImageView rightImage;
    @InjectView(R.id.head_layout)
    RelativeLayout headLayout;
    @InjectView(R.id.search_edit)
    TextView searchEdit;
    @InjectView(R.id.search_layout)
    LinearLayout searchLayout;
    @InjectView(R.id.phone_layout)
    LinearLayout phoneLayout;
    @InjectView(R.id.wx_layout)
    LinearLayout wxLayout;
    @InjectView(R.id.qq_layout)
    LinearLayout qqLayout;
    @InjectView(R.id.wb_layout)
    LinearLayout wbLayout;
    @InjectView(R.id.user_list)
    MyListView userList;

    private List<UserSearchEntity> userSearchEntityList;
    private AddFriendSearchAdapter addFriendSearchAdapter;
    private UserSearchEntity userSearchEntity;
    private AimEntity aimEntity;
    private List<AimEntity> aimEntityList;

    public static int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_friend_search);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        width = (getWindowManager().getDefaultDisplay().getWidth() - 130);
        init();
    }

    private void init() {
        userSearchEntityList = new ArrayList<UserSearchEntity>();
        addFriendSearchAdapter = new AddFriendSearchAdapter(userSearchEntityList, this);
        userList.setAdapter(addFriendSearchAdapter);

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
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            userSearchEntity = new UserSearchEntity();
//                            "id":1,"nick":"aa","avatar":"jpg","brief":"","city":"",
//                                    "sex":0,"updateTime":1496979123000,"status":1,"aim":0,"login":0,"follow":0,"beFollowed":0
                            userSearchEntity.setId(jsonArray.getJSONObject(i).getJSONObject("user").getString("id"));
                            userSearchEntity.setNick(jsonArray.getJSONObject(i).getJSONObject("user").getString("nick"));
                            userSearchEntity.setAvatar(jsonArray.getJSONObject(i).getJSONObject("user").getString("avatar"));
                            userSearchEntity.setBrief(jsonArray.getJSONObject(i).getJSONObject("user").getString("brief"));
                            userSearchEntity.setCity(jsonArray.getJSONObject(i).getJSONObject("user").getString("city"));
                            userSearchEntity.setSex(jsonArray.getJSONObject(i).getJSONObject("user").getString("sex"));
                            userSearchEntity.setUpdateTime(jsonArray.getJSONObject(i).getJSONObject("user").getString("updateTime"));
                            userSearchEntity.setAim(jsonArray.getJSONObject(i).getJSONObject("user").getString("aim"));
                            userSearchEntity.setLogin(jsonArray.getJSONObject(i).getJSONObject("user").getString("login"));
                            userSearchEntity.setFollow(jsonArray.getJSONObject(i).getJSONObject("user").getString("follow"));
                            userSearchEntity.setBeFollowed(jsonArray.getJSONObject(i).getJSONObject("user").getString("beFollowed"));
                            userSearchEntity.setIsFollowed("0");
                            JSONArray aimsArray = jsonArray.getJSONObject(i).getJSONArray("aims");
                            aimEntityList = new ArrayList<AimEntity>();
                            for (int j = 0; j < aimsArray.length(); j++) {
                                aimEntity = new AimEntity();
                                aimEntity.setId(aimsArray.getJSONObject(i).getString("id"));
                                aimEntity.setName(aimsArray.getJSONObject(i).getString("name"));
                                aimEntity.setBudget(aimsArray.getJSONObject(i).getString("budget"));
                                aimEntity.setMoney(aimsArray.getJSONObject(i).getString("money"));
                                aimEntity.setCycle(aimsArray.getJSONObject(i).getString("cycle"));
                                aimEntity.setCurrent(aimsArray.getJSONObject(i).getString("current"));
                                aimEntity.setUserId(aimsArray.getJSONObject(i).getString("userId"));

                                aimEntity.setProvince(aimsArray.getJSONObject(i).getString("province"));
                                aimEntity.setCity(aimsArray.getJSONObject(i).getString("city"));
                                aimEntity.setBrief(aimsArray.getJSONObject(i).getString("brief"));
                                aimEntity.setPosition(aimsArray.getJSONObject(i).getString("position"));
                                aimEntity.setLongitude(aimsArray.getJSONObject(i).getString("longitude"));
                                aimEntity.setLatitude(aimsArray.getJSONObject(i).getString("latitude"));
                                aimEntity.setSupport(aimsArray.getJSONObject(i).getString("support"));
                                aimEntity.setCreateTime(aimsArray.getJSONObject(i).getString("createTime"));
                                aimEntity.setStatus(aimsArray.getJSONObject(i).getString("status"));
                                aimEntity.setImg(aimsArray.getJSONObject(i).getString("img"));

                                aimEntityList.add(aimEntity);
                            }
                            userSearchEntity.setAimEntityList(aimEntityList);
                            userSearchEntityList.add(userSearchEntity);
                            addFriendSearchAdapter.notifyDataSetChanged();
                        }


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

    @OnClick({R.id.left_image, R.id.search_layout, R.id.phone_layout, R.id.wx_layout, R.id.qq_layout, R.id.wb_layout})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.search_layout:
                intent.setClass(this, AddFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.phone_layout:
                intent.setClass(this, InviteActivity.class);
                startActivity(intent);
                break;
            case R.id.wx_layout:
                break;
            case R.id.qq_layout:
                break;
            case R.id.wb_layout:
                break;
        }
    }
}
