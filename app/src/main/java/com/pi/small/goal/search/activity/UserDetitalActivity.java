package com.pi.small.goal.search.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.MyListView;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.DynamicEntity;
import com.pi.small.goal.weight.PinchImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class UserDetitalActivity extends AppCompatActivity {

    @InjectView(R.id.head_layout)
    LinearLayout headLayout;
    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.more_image)
    ImageView moreImage;
    @InjectView(R.id.chat_image)
    ImageView chatImage;
    @InjectView(R.id.data_list)
    MyListView dataList;
    @InjectView(R.id.pinch_image)
    PinchImageView pinchImage;
    @InjectView(R.id.image_layout)
    RelativeLayout imageLayout;
    @InjectView(R.id.head_image)
    CircleImageView headImage;
    @InjectView(R.id.nick_text)
    TextView nickText;
    @InjectView(R.id.brief_text)
    TextView briefText;
    @InjectView(R.id.aims_text)
    TextView aimsText;
    @InjectView(R.id.be_follows_text)
    TextView beFollowsText;
    @InjectView(R.id.follows_text)
    TextView followsText;

    private String userId;

    private String id;
    private String nick;
    private String avatar;
    private String brief;
    private String city;
    private String sex;
    private String status;
    private String aim;
    private String login;
    private String follow;
    private String beFollowed;

    private List<DynamicEntity> dynamicEntityList;
    private DynamicEntity dynamicEntity;

    private int page = 1;

    private HotAdapter hotAdapter;

    private int width;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detital);
        ButterKnife.inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        width = (getWindowManager().getDefaultDisplay().getWidth() - 130);

        userId = getIntent().getStringExtra("userId");

        dynamicEntityList = new ArrayList<DynamicEntity>();

        hotAdapter = new HotAdapter(this);
//        dataList.setMode(PullToRefreshBase.Mode.BOTH);
        dataList.setAdapter(hotAdapter);

        GetUserData();
    }

    @OnClick({R.id.left_image, R.id.more_image, R.id.chat_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.more_image:
                GetMore(view);
                break;
            case R.id.chat_image:
                RongIM.getInstance().setCurrentUserInfo(new UserInfo("xmb_user_" + userId, nick, Uri.parse(Utils.GetPhotoPath(avatar))));
                RongIM.getInstance().setMessageAttachedUserInfo(true);

                RongIM.getInstance().setCurrentUserInfo(new UserInfo(Utils.UserSharedPreferences(this).getString("RY_Id", ""), Utils.UserSharedPreferences(this).getString("nick", ""), Uri.parse("http://www.ghost64.com/qqtupian/zixunImg/local/2016/11/22/14798003915289.jpg")));
                RongIM.getInstance().setMessageAttachedUserInfo(true);

                RongIM.getInstance().startPrivateChat(this, "xmb_user_" + userId, nick);

                break;
        }
    }

    private void GetUserData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.UserBase);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("token", Utils.GetToken(this));
        requestParams.addBodyParameter("deviceId", MyApplication.deviceId);

        System.out.println("=============uid==========" + userId);
        requestParams.addBodyParameter("uid", userId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("========GetUserData========" + result);
                try {
                    if (new JSONObject(result).getString("code").equals("0")) {

                        id = new JSONObject(result).getJSONObject("result").getString("id");
                        nick = new JSONObject(result).getJSONObject("result").getString("nick");
                        avatar = new JSONObject(result).getJSONObject("result").getString("avatar");
                        brief = new JSONObject(result).getJSONObject("result").getString("brief");
                        city = new JSONObject(result).getJSONObject("result").getString("city");
                        sex = new JSONObject(result).getJSONObject("result").getString("sex");
                        status = new JSONObject(result).getJSONObject("result").getString("status");
                        aim = new JSONObject(result).getJSONObject("result").getString("aim");
                        login = new JSONObject(result).getJSONObject("result").getString("login");
                        follow = new JSONObject(result).getJSONObject("result").getString("follow");
                        beFollowed = new JSONObject(result).getJSONObject("result").getString("beFollowed");

                        if (!avatar.equals("")) {
                            Picasso.with(UserDetitalActivity.this).load(Utils.GetPhotoPath(avatar)).into(headImage);
                        } else {
                            headImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_head));
                        }
                        beFollowsText.setText(beFollowed);
                        followsText.setText(follow);
                        aimsText.setText(aim);
                        nickText.setText(nick);
                        briefText.setText(brief);

                        GetData();
                    } else {
                        Utils.showToast(UserDetitalActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.print("========ex========" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void GetData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.AimDynamicUser);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("uid", userId);
        requestParams.addBodyParameter("r", "30");
        requestParams.addBodyParameter("p", page + "");
        XUtil.get(requestParams, UserDetitalActivity.this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("========GetData========" + result);
                try {
                    if (new JSONObject(result).getString("code").equals("0")) {

                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            dynamicEntity = new DynamicEntity();

                            dynamicEntity.setId(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("id"));
                            dynamicEntity.setAimId(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("aimId"));
                            dynamicEntity.setUserId(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("userId"));
                            dynamicEntity.setContent(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("content"));
                            dynamicEntity.setCity(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("city"));
                            dynamicEntity.setMoney(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("money"));
                            dynamicEntity.setImg3(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("img3"));
                            dynamicEntity.setImg2(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("img2"));
                            dynamicEntity.setImg1(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("img1"));
                            dynamicEntity.setVideo(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("video"));
                            dynamicEntity.setCreateTime(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("createTime"));
                            dynamicEntity.setUpdateTime(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("updateTime"));
                            dynamicEntity.setProvince(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("province"));
                            dynamicEntity.setIsPaid(jsonArray.getJSONObject(i).getJSONObject("dynamic").getString("isPaid"));

                            dynamicEntityList.add(dynamicEntity);
                        }

                        hotAdapter.notifyDataSetChanged();
                    } else {
                        Utils.showToast(UserDetitalActivity.this, new JSONObject(result).getString("msg"));
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

    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        headLayout.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
        ;
    }

    public class HotAdapter extends BaseAdapter {

        private Context context;


        private ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.background_load)
                .setFailureDrawableId(R.mipmap.background_fail)
                .build();

        public HotAdapter(Context context) {
            this.context = context;

        }

        @Override
        public int getCount() {
            return dynamicEntityList.size();
        }

        @Override
        public Object getItem(int position) {
            return dynamicEntityList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder viewHolder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_user_detital, parent, false);
                viewHolder = new ViewHolder(convertView);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.nameText.setText(nick);

            if (dynamicEntityList.get(position).getContent().equals("")) {
                viewHolder.contentText.setVisibility(View.GONE);
            } else {
                viewHolder.contentText.setVisibility(View.VISIBLE);
                viewHolder.contentText.setText(dynamicEntityList.get(position).getContent());
            }
            viewHolder.timeText.setText(Utils.GetTime(Long.valueOf(dynamicEntityList.get(position).getUpdateTime())));

//        x.image().bind(viewHolder.head_image, Utils.GetPhotoPath(dataList.get(position).getAvatar()), imageOptions);

            if (!avatar.equals("")) {
                Picasso.with(context).load(Utils.GetPhotoPath(avatar)).into(viewHolder.headImage);
            } else {
                viewHolder.headImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_head));
            }

            viewHolder.headImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(UserDetitalActivity.this, UserDetitalActivity.class);
                    intent.putExtra("uid", dynamicEntityList.get(position).getUserId());
                    startActivity(intent);
                }
            });

//            if (dynamicEntityList.get(position).getUserId().equals(Utils.UserSharedPreferences(context).getString("id", ""))) {
//                viewHolder.attentionText.setVisibility(View.GONE);
//            } else {
//                viewHolder.attentionText.setVisibility(View.VISIBLE);
//            }
            System.out.println("=============dynamicEntityList.get(position).getIsFollow()=========" + dynamicEntityList.get(position).getIsFollow());
//            if (dynamicEntityList.get(position).getIsFollow().equals("0")) {
//                viewHolder.attentionText.setBackgroundDrawable(UserDetitalActivity.this.getResources().getDrawable(R.drawable.background_white_yellow_corner));
//                viewHolder.attentionText.setTextColor(UserDetitalActivity.this.getResources().getColor(R.color.yellow_light));
//                viewHolder.attentionText.setText("关注");
//
//            } else {
//                viewHolder.attentionText.setBackgroundDrawable(UserDetitalActivity.this.getResources().getDrawable(R.drawable.background_white_gray_corner));
//                viewHolder.attentionText.setTextColor(UserDetitalActivity.this.getResources().getColor(R.color.gray_heavy));
//                viewHolder.attentionText.setText("已关注");
//
//            }


//            if (Integer.valueOf(dynamicEntityList.get(position).getHaveRedPacket()) > 0) {
//                viewHolder.moneyImage.setVisibility(View.VISIBLE);
//            } else {
//                viewHolder.moneyImage.setVisibility(View.GONE);
//            }
            viewHolder.moneyImage.setVisibility(View.GONE);
            viewHolder.moneyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent().setClass(context, RedHaveActivity.class);
                    intent.putExtra("dynamicId", dynamicEntityList.get(position).getId());
                    context.startActivity(intent);
                }
            });

            viewHolder.moneyText.setText(dynamicEntityList.get(position).getMoney());


            if (dynamicEntityList.get(position).getImg1().equals("") && dynamicEntityList.get(position).getImg2().equals("") && dynamicEntityList.get(position).getImg3().equals("")) {
                viewHolder.imageLayout.setVisibility(View.GONE);
            } else {
                viewHolder.imageLayout.setVisibility(View.VISIBLE);
                List<String> imageList = new ArrayList<String>();
                if (!dynamicEntityList.get(position).getImg1().equals("")) {
                    imageList.add(dynamicEntityList.get(position).getImg1());
                }
                if (!dynamicEntityList.get(position).getImg2().equals("")) {
                    imageList.add(dynamicEntityList.get(position).getImg2());
                }
                if (!dynamicEntityList.get(position).getImg3().equals("")) {
                    imageList.add(dynamicEntityList.get(position).getImg3());
                }

                if (imageList.size() == 1) {
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.GONE);
                    viewHolder.image3.setVisibility(View.GONE);
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(imageList.get(0)), imageOptions);
                    ViewGroup.LayoutParams layoutParams1 = viewHolder.image1.getLayoutParams();
                    layoutParams1.height = width / 2;
                    layoutParams1.width = width;
                    viewHolder.image1.setLayoutParams(layoutParams1);

                } else if (imageList.size() == 2) {
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.VISIBLE);
                    viewHolder.image3.setVisibility(View.GONE);
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(imageList.get(0)), imageOptions);
                    x.image().bind(viewHolder.image2, Utils.GetPhotoPath(imageList.get(1)), imageOptions);


                    ViewGroup.LayoutParams layoutParams1 = viewHolder.image1.getLayoutParams();
                    layoutParams1.height = width / 2;
                    layoutParams1.width = width / 2;
                    ViewGroup.LayoutParams layoutParams2 = viewHolder.image2.getLayoutParams();
                    layoutParams2.height = width / 2;
                    layoutParams2.width = width / 2;

                    viewHolder.image1.setLayoutParams(layoutParams1);
                    viewHolder.image2.setLayoutParams(layoutParams2);

                } else if (imageList.size() == 3) {
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.VISIBLE);
                    viewHolder.image3.setVisibility(View.VISIBLE);
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg1()), imageOptions);
                    x.image().bind(viewHolder.image2, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg2()), imageOptions);
                    x.image().bind(viewHolder.image3, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg3()), imageOptions);

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
                    imageLayout.setVisibility(View.VISIBLE);
                    x.image().bind(pinchImage, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg1()), imageOptions);
                }
            });
            viewHolder.image2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageLayout.setVisibility(View.VISIBLE);
                    x.image().bind(pinchImage, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg2()), imageOptions);
                }
            });
            viewHolder.image3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imageLayout.setVisibility(View.VISIBLE);
                    x.image().bind(pinchImage, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg3()), imageOptions);
                }
            });


            return convertView;
        }


        class ViewHolder {
            @InjectView(R.id.head_image)
            CircleImageView headImage;
            @InjectView(R.id.name_text)
            TextView nameText;
            @InjectView(R.id.time_text)
            TextView timeText;
            @InjectView(R.id.attention_text)
            TextView attentionText;
            @InjectView(R.id.content_text)
            TextView contentText;

            @InjectView(R.id.image_layout)
            LinearLayout imageLayout;
            @InjectView(R.id.image3)
            ImageView image3;
            @InjectView(R.id.image2)
            ImageView image2;
            @InjectView(R.id.image1)
            ImageView image1;

            @InjectView(R.id.money_text)
            TextView moneyText;
            @InjectView(R.id.money_image)
            ImageView moneyImage;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }


    private void GetMore(View v) {
        View windowView = LayoutInflater.from(UserDetitalActivity.this).inflate(
                R.layout.window_search_report, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, true);

        final TextView report_text = (TextView) windowView.findViewById(R.id.report_text);
        TextView cancel_text = (TextView) windowView.findViewById(R.id.cancel_text);

        cancel_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        report_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                report_text.setClickable(false);
                RequestParams requestParams = new RequestParams(Url.Url + Url.ReportUser);
                requestParams.addHeader("token", Utils.GetToken(UserDetitalActivity.this));
                requestParams.addHeader("deviceId", MyApplication.deviceId);
                requestParams.addBodyParameter("uid", userId);
                x.http().request(HttpMethod.PUT, requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            String code = new JSONObject(result).getString("code");
                            if (code.equals("0")) {
                                Utils.showToast(UserDetitalActivity.this, "举报成功");
                                report_text.setClickable(true);
                                popupWindow.dismiss();
                            } else {
                                Utils.showToast(UserDetitalActivity.this, new JSONObject(result).getString("msg"));
                                report_text.setClickable(true);
                                popupWindow.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Utils.showToast(UserDetitalActivity.this, ex.getMessage());
                        report_text.setClickable(true);
                        popupWindow.dismiss();
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
        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);


    }

}
