package com.small.small.goal.search.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.message.activity.AddFriendActivity;
import com.small.small.goal.my.activity.AimMoreActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.MyListView;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.entity.AimEntity;
import com.small.small.goal.utils.entity.UserSearchEntity;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.utils.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

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

    private SharedPreferences utilsSharedPreferences;
    private SharedPreferences.Editor utilsEditor;
    private List<Map<String, String>> followList;
    public static int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_add_friend_search);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
        utilsSharedPreferences = Utils.UtilsSharedPreferences(AddFriendSearchActivity.this);
        utilsEditor = utilsSharedPreferences.edit();

        width = (getWindowManager().getDefaultDisplay().getWidth() - 130);
        followList = new ArrayList<Map<String, String>>();

        userSearchEntityList = new ArrayList<UserSearchEntity>();
        if (!Utils.UtilsSharedPreferences(this).getString("followList", "").equals("")) {
            followList = Utils.GetFollowList(Utils.UtilsSharedPreferences(this).getString("followList", ""));
        } else {
            followList.clear();
        }
        init();
    }

    private void init() {
        if (Utils.isNetworkConnected(this))
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
                            JSONArray aimsArray = jsonArray.getJSONObject(i).optJSONArray("aims");
                            aimEntityList = new ArrayList<AimEntity>();
                            for (int j = 0; j < aimsArray.length(); j++) {
                                aimEntity = new AimEntity();
                                aimEntity.setId(aimsArray.getJSONObject(j).optString("id"));
                                aimEntity.setName(aimsArray.getJSONObject(j).getString("name"));
                                aimEntity.setBudget(aimsArray.getJSONObject(j).getString("budget"));
                                aimEntity.setMoney(aimsArray.getJSONObject(j).getString("money"));
                                aimEntity.setCycle(aimsArray.getJSONObject(j).getString("cycle"));
                                aimEntity.setCurrent(aimsArray.getJSONObject(j).getString("current"));
                                aimEntity.setUserId(aimsArray.getJSONObject(j).getString("userId"));
                                aimEntity.setProvince(aimsArray.getJSONObject(j).getString("province"));
                                aimEntity.setCity(aimsArray.getJSONObject(j).getString("city"));
                                aimEntity.setBrief(aimsArray.getJSONObject(j).getString("brief"));
                                aimEntity.setPosition(aimsArray.getJSONObject(j).getString("position"));
                                aimEntity.setLongitude(aimsArray.getJSONObject(j).getString("longitude"));
                                aimEntity.setLatitude(aimsArray.getJSONObject(j).getString("latitude"));
                                aimEntity.setSupport(aimsArray.getJSONObject(j).getString("support"));
                                aimEntity.setCreateTime(aimsArray.getJSONObject(j).getString("createTime"));
                                aimEntity.setStatus(aimsArray.getJSONObject(j).getString("status"));
                                aimEntity.setImg(aimsArray.getJSONObject(j).getString("img"));

                                aimEntityList.add(aimEntity);
                            }

                            userSearchEntity.setAimEntityList(aimEntityList);
                            userSearchEntityList.add(userSearchEntity);

                        }

                        addFriendSearchAdapter = new AddFriendSearchAdapter(AddFriendSearchActivity.this);
                        userList.setAdapter(addFriendSearchAdapter);
                    } else {
                        Utils.showToast(AddFriendSearchActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
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
                if (Build.VERSION.SDK_INT >= 23) {
                    int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS);
                    if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 101);
                        return;
                    } else {
                        int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                        if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 102);
                            return;
                        } else {
                            int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS);
                            if (checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 103);
                                return;
                            } else {
                                intent.setClass(this, InviteActivity.class);
                                startActivity(intent);
                            }
                        }
                    }
                } else {
                    intent.setClass(this, InviteActivity.class);
                    startActivity(intent);
                }

                break;
            case R.id.wx_layout:
                share(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.qq_layout:
                share(SHARE_MEDIA.QQ);
                break;
            case R.id.wb_layout:
                break;
        }
    }

    private void share(SHARE_MEDIA platform) {

        UMImage image = new UMImage(this, R.mipmap.about_us_logo);//网络图片
        new ShareAction(AddFriendSearchActivity.this).setPlatform(platform)
                .withText("小目标哦")
                .withMedia(image)
                .setCallback(umShareListener)
                .share();
    }


    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            //  Log.d("plat","platform"+platform);

            Toast.makeText(AddFriendSearchActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(AddFriendSearchActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(AddFriendSearchActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 101:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int checkCallPhonePermission2 = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS);
                    if (checkCallPhonePermission2 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS}, 102);
                        return;
                    } else {
                        int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS);
                        if (checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 103);
                            return;
                        } else {
                            Intent intent = new Intent();
                            intent.setClass(this, InviteActivity.class);
                            startActivity(intent);
                        }
                    }
                } else {
                    Utils.showToast(AddFriendSearchActivity.this, "您禁止了短信权限");
                }
                break;
            case 102:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    int checkCallPhonePermission3 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_CONTACTS);
                    if (checkCallPhonePermission3 != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_CONTACTS}, 103);
                        return;
                    } else {
                        Intent intent = new Intent();
                        intent.setClass(this, InviteActivity.class);
                        startActivity(intent);
                    }
                } else {
                    Utils.showToast(AddFriendSearchActivity.this, "您禁止了读取权限");
                }
                break;
            case 103:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent();
                    intent.setClass(this, InviteActivity.class);
                    startActivity(intent);
                } else {
                    Utils.showToast(AddFriendSearchActivity.this, "您禁止了写入权限");
                }
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }


    class AddFriendSearchAdapter extends BaseAdapter {

        private Context context;


        List<Map<String, String>> imageList;

        public AddFriendSearchAdapter(Context context) {
            this.context = context;
        }


        @Override
        public int getCount() {
            return userSearchEntityList.size();
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_add_friend_search, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (!userSearchEntityList.get(position).getAvatar().equals("")) {
                Picasso.with(context).load(Utils.GetPhotoPath(userSearchEntityList.get(position).getAvatar())).into(viewHolder.headImage);
            } else {
                viewHolder.headImage.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_head));
            }

            viewHolder.headImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(context, UserDetitalActivity.class);
                    intent.putExtra("userId", userSearchEntityList.get(position).getId());
                    context.startActivity(intent);
                }
            });

            viewHolder.nameText.setText(userSearchEntityList.get(position).getNick());
            viewHolder.briefText.setText(userSearchEntityList.get(position).getBrief());
            viewHolder.attentionText.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_white_yellow_corner));
            viewHolder.attentionText.setTextColor(context.getResources().getColor(R.color.yellow_light));
            viewHolder.attentionText.setText("关注");

            viewHolder.attentionText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams requestParams = new RequestParams(Url.Url + Url.Follow);
                    requestParams.addHeader("token", Utils.GetToken(context));
                    requestParams.addHeader("deviceId", MyApplication.deviceId);
                    requestParams.addBodyParameter("followUserId", userSearchEntityList.get(position).getId());
                    XUtil.post(requestParams, context, new XUtil.XCallBackLinstener() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                if (new JSONObject(result).getString("code").equals("0")) {
                                    if (userSearchEntityList.get(position).getIsFollowed().equals("0")) {
                                        userSearchEntityList.get(position).setIsFollowed("1");
                                        viewHolder.attentionText.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_white_gray_corner));
                                        viewHolder.attentionText.setTextColor(context.getResources().getColor(R.color.gray_heavy));
                                        viewHolder.attentionText.setText("已关注");
                                        Map<String, String> map = new HashMap<String, String>();

                                        map.put("followId", new JSONObject(result).getJSONObject("result").optString("followId"));
                                        map.put("userId", new JSONObject(result).getJSONObject("result").optString("userId"));
                                        map.put("followUserId", new JSONObject(result).getJSONObject("result").optString("followUserId"));
                                        map.put("nick", userSearchEntityList.get(position).getNick());
                                        map.put("avatar", userSearchEntityList.get(position).getAvatar());
                                        followList.add(map);

                                        utilsEditor.putString("followList", Utils.changeFollowToJson(followList));
                                        utilsEditor.commit();
                                    } else {
                                        userSearchEntityList.get(position).setIsFollowed("0");
                                        viewHolder.attentionText.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_white_yellow_corner));
                                        viewHolder.attentionText.setTextColor(context.getResources().getColor(R.color.yellow_light));
                                        viewHolder.attentionText.setText("关注");
                                        for (int i = 0; i < followList.size(); i++) {
                                            if (userSearchEntityList.get(position).getId().equals(followList.get(i).get("followUserId"))) {
                                                followList.remove(i);
                                            }
                                        }
                                        utilsEditor.putString("followList", Utils.changeFollowToJson(followList));
                                        utilsEditor.commit();
                                    }

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

            imageList = new ArrayList<Map<String, String>>();
            if (userSearchEntityList.get(position).getAimEntityList().size() < 1) {
                viewHolder.imageLayout.setVisibility(View.GONE);
            } else {
                viewHolder.imageLayout.setVisibility(View.VISIBLE);
                for (int i = 0; i < userSearchEntityList.get(position).getAimEntityList().size(); i++) {
                    if (imageList.size() < 3) {
                        if (!userSearchEntityList.get(position).getAimEntityList().get(i).getImg().equals("")) {
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("img", userSearchEntityList.get(position).getAimEntityList().get(i).getImg());
                            map.put("aimId", userSearchEntityList.get(position).getAimEntityList().get(i).getId());
                            imageList.add(map);
                        }
                    }
                }
                if (imageList.size() == 1) {

                    ImageOptions imageOptions = new ImageOptions.Builder()
                            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setLoadingDrawableId(R.mipmap.background_load)
                            .setSize(width / 2, width)
                            .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setFailureDrawableId(R.mipmap.background_fail)
                            .build();

                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.GONE);
                    viewHolder.image3.setVisibility(View.GONE);
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(imageList.get(0).get("img")) + Url.SMALL_PHOTO_URL2, imageOptions);
                    ViewGroup.LayoutParams layoutParams1 = viewHolder.image1.getLayoutParams();
                    layoutParams1.height = width / 2;
                    layoutParams1.width = width;
                    viewHolder.image1.setLayoutParams(layoutParams1);

                } else if (imageList.size() == 2) {

                    ImageOptions imageOptions = new ImageOptions.Builder()
                            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setLoadingDrawableId(R.mipmap.background_load)
                            .setSize(width / 2, width / 2)
                            .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setFailureDrawableId(R.mipmap.background_fail)
                            .build();

                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.VISIBLE);
                    viewHolder.image3.setVisibility(View.GONE);
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(imageList.get(0).get("img")) + Url.SMALL_PHOTO_URL, imageOptions);
                    x.image().bind(viewHolder.image2, Utils.GetPhotoPath(imageList.get(1).get("img")) + Url.SMALL_PHOTO_URL, imageOptions);


                    ViewGroup.LayoutParams layoutParams1 = viewHolder.image1.getLayoutParams();
                    layoutParams1.height = width / 2;
                    layoutParams1.width = width / 2;
                    ViewGroup.LayoutParams layoutParams2 = viewHolder.image2.getLayoutParams();
                    layoutParams2.height = width / 2;
                    layoutParams2.width = width / 2;

                    viewHolder.image1.setLayoutParams(layoutParams1);
                    viewHolder.image2.setLayoutParams(layoutParams2);

                } else if (imageList.size() == 3) {

                    ImageOptions imageOptions = new ImageOptions.Builder()
                            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setLoadingDrawableId(R.mipmap.background_load)
                            .setSize(width / 3, width / 3)
                            .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                            .setFailureDrawableId(R.mipmap.background_fail)
                            .build();

                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.VISIBLE);
                    viewHolder.image3.setVisibility(View.VISIBLE);
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(imageList.get(0).get("img")) + Url.SMALL_PHOTO_URL, imageOptions);
                    x.image().bind(viewHolder.image2, Utils.GetPhotoPath(imageList.get(1).get("img")) + Url.SMALL_PHOTO_URL, imageOptions);
                    x.image().bind(viewHolder.image3, Utils.GetPhotoPath(imageList.get(2).get("img")) + Url.SMALL_PHOTO_URL, imageOptions);

                    ViewGroup.LayoutParams layoutParams1 = viewHolder.image1.getLayoutParams();
                    layoutParams1.height = width / 3;
                    layoutParams1.width = width / 3;
                    ViewGroup.LayoutParams layoutParams2 = viewHolder.image2.getLayoutParams();
                    layoutParams2.height = width / 3;
                    layoutParams2.width = width / 3;
                    ViewGroup.LayoutParams layoutParams3 = viewHolder.image3.getLayoutParams();
                    layoutParams3.height = width / 3;
                    layoutParams3.width = width / 3;

                    viewHolder.image1.setLayoutParams(layoutParams1);
                    viewHolder.image2.setLayoutParams(layoutParams2);
                    viewHolder.image3.setLayoutParams(layoutParams3);
                }
            }
            viewHolder.image1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddFriendSearchActivity.this, AimMoreActivity.class);
                    intent.putExtra(AimMoreActivity.KEY_AIMID, imageList.get(0).get("aimId"));
                    startActivity(intent);
                }
            });
            viewHolder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddFriendSearchActivity.this, AimMoreActivity.class);
                    intent.putExtra(AimMoreActivity.KEY_AIMID, imageList.get(1).get("aimId"));
                    startActivity(intent);
                }
            });

            viewHolder.image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(AddFriendSearchActivity.this, AimMoreActivity.class);
                    intent.putExtra(AimMoreActivity.KEY_AIMID, imageList.get(2).get("aimId"));
                    startActivity(intent);
                }
            });
            if (userSearchEntityList.get(position).getAimEntityList().get(0).getCity().equals("")) {
                viewHolder.cityLayout.setVisibility(View.GONE);
            } else {
                viewHolder.cityLayout.setVisibility(View.VISIBLE);
                viewHolder.cityText.setText(userSearchEntityList.get(position).getAimEntityList().get(0).getCity());
                viewHolder.provinceText.setText(userSearchEntityList.get(position).getAimEntityList().get(0).getProvince());
            }

            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.head_image)
            CircleImageView headImage;
            @InjectView(R.id.name_text)
            TextView nameText;
            @InjectView(R.id.brief_text)
            TextView briefText;
            @InjectView(R.id.attention_text)
            TextView attentionText;

            @InjectView(R.id.image_layout)
            LinearLayout imageLayout;
            @InjectView(R.id.image3)
            ImageView image3;
            @InjectView(R.id.image2)
            ImageView image2;
            @InjectView(R.id.image1)
            ImageView image1;
            @InjectView(R.id.city_text)
            TextView cityText;
            @InjectView(R.id.city_layout)
            LinearLayout cityLayout;
            @InjectView(R.id.province_text)
            TextView provinceText;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
