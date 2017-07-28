package com.small.small.goal.search.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.activity.AimMoreActivity;
import com.small.small.goal.search.adapter.CommentAdapter;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.MyListView;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.entity.CommentEntity;
import com.small.small.goal.utils.entity.DynamicEntity;
import com.small.small.goal.weight.PinchImageView;
import com.squareup.picasso.Picasso;

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
import de.hdodenhof.circleimageview.CircleImageView;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.UserInfo;

public class UserDetitalActivity extends BaseActivity {

    private ImageView leftImage;
    private ImageView moreImage;
    private ImageView chatImage;
    private RelativeLayout topLayout;
    private PinchImageView pinchImage;
    private RelativeLayout imageLayout;
    private PullToRefreshListView pullToRefreshListView;
    private RelativeLayout nullLayout;
    private ImageView imgEmpty;
    private TextView tvEmpty;

    private ListView listView;

    private CircleImageView head_image;
    private TextView nick_text;
    private TextView brief_text;
    private TextView attention_text;
    private TextView aims_text;
    private TextView be_follows_text;
    private TextView follows_text;


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
    private List<CommentEntity> commentEntityList;
    private CommentEntity commentEntity;
    private DynamicEntity dynamicEntity;

    private HotAdapter hotAdapter;

    private int width;
    private List<Map<String, String>> followList = new ArrayList<Map<String, String>>();
    private Parcelable state;
    private int currentPosition;

    private boolean isDown = false;
    private int page = 1;

    private int total;
    public static String KEY_USERID = "userId";

    private SharedPreferences utilsSharedPreferences;
    private SharedPreferences.Editor utilsEditor;

    private int isFollow = 0;

    private View head_view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_user_detital);
        super.onCreate(savedInstanceState);

        utilsSharedPreferences = Utils.UtilsSharedPreferences(this);
        utilsEditor = utilsSharedPreferences.edit();

        width = (getWindowManager().getDefaultDisplay().getWidth() - 130);

        userId = getIntent().getStringExtra("userId");

        dynamicEntityList = new ArrayList<DynamicEntity>();

        init();


    }

    private void init() {
        leftImage = (ImageView) findViewById(R.id.left_image);
        moreImage = (ImageView) findViewById(R.id.more_image);
        chatImage = (ImageView) findViewById(R.id.chat_image);
        topLayout = (RelativeLayout) findViewById(R.id.top_layout);
        pinchImage = (PinchImageView) findViewById(R.id.pinch_image);
        imageLayout = (RelativeLayout) findViewById(R.id.image_layout);
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.data_list);
        nullLayout = (RelativeLayout) findViewById(R.id.null_layout);
        imgEmpty = (ImageView) findViewById(R.id.img_empty);
        tvEmpty = (TextView) findViewById(R.id.tv_empty);

        head_view = LayoutInflater.from(this).inflate(R.layout.head_user_view, null);
        listView = pullToRefreshListView.getRefreshableView();


        head_image = (CircleImageView) head_view.findViewById(R.id.head_image);
        nick_text = (TextView) head_view.findViewById(R.id.nick_text);
        brief_text = (TextView) head_view.findViewById(R.id.brief_text);
        attention_text = (TextView) head_view.findViewById(R.id.attention_text);
        aims_text = (TextView) head_view.findViewById(R.id.aims_text);
        be_follows_text = (TextView) head_view.findViewById(R.id.be_follows_text);
        follows_text = (TextView) head_view.findViewById(R.id.follows_text);


        hotAdapter = new HotAdapter(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }


        pullToRefreshListView.setAdapter(hotAdapter);
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                isDown = true;
                page = 1;
                GetHotData(page + "", "10");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                isDown = false;

                if (page * 10 >= total) {
                    pullToRefreshListView.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Utils.showToast(UserDetitalActivity.this, "没有更多数据了");
                            pullToRefreshListView.onRefreshComplete();
                        }
                    }, 1000);
                } else {
                    page = page + 1;
                    GetHotData(page + "", "10");
                }
            }
        });

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                //顶部渐变色
                if (hotAdapter == null)
                    return;
                int heigh = head_view.getHeight();
                if (heigh < getScrollY()) {

                    //Color.argb(255,0,178,238)
                    leftImage.setImageResource(R.mipmap.icon_arrow_white_left);
                    moreImage.setImageResource(R.mipmap.more_btn_white);
                    chatImage.setImageResource(R.mipmap.chat_btn_white);
                    topLayout.setBackgroundColor(Color.argb(255, 239, 120, 52));
                } else {
                    float a = 255.0f / (float) heigh;
                    a = a * (float) getScrollY();
                    if (a > 255)
                        a = 255;
                    else if (a < 0)
                        a = 0;

                    if (a >= 200) {
                        leftImage.setImageResource(R.mipmap.icon_arrow_white_left);
                        moreImage.setImageResource(R.mipmap.more_btn_white);
                        chatImage.setImageResource(R.mipmap.chat_btn_white);
                    } else {
                        leftImage.setImageResource(R.mipmap.icon_arrow_yellow_left);
                        moreImage.setImageResource(R.mipmap.more_btn);
                        chatImage.setImageResource(R.mipmap.chat_btn);
                    }
                    topLayout.setBackgroundColor(Color.argb((int) a, 239, 120, 52));
                }

            }
        });


        if (!Utils.UtilsSharedPreferences(this).getString("followList", "").equals("")) {
            followList = Utils.GetFollowList(Utils.UtilsSharedPreferences(this).getString("followList", ""));
        }

        for (int i = 0; i < followList.size(); i++) {
            if (userId.equals(followList.get(i).get("followUserId"))) {
                isFollow = 1;
            }
        }

        if (userId.equals(Utils.UserSharedPreferences(this).getString("id", ""))) {
            attention_text.setVisibility(View.INVISIBLE);
        } else {
            attention_text.setVisibility(View.VISIBLE);
        }
        if (isFollow == 1) {
            attention_text.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gray_corner_45));
            attention_text.setText("已关注");
        } else {
            attention_text.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_yellow_corner));
            attention_text.setText("关注");
        }

        if (userId.equals(Utils.UserSharedPreferences(this).getString("id", ""))) {
            chatImage.setVisibility(View.GONE);
            moreImage.setVisibility(View.GONE);
        } else {
            chatImage.setVisibility(View.VISIBLE);
            moreImage.setVisibility(View.VISIBLE);
        }


        leftImage.setOnClickListener(this);
        moreImage.setOnClickListener(this);
        chatImage.setOnClickListener(this);
        attention_text.setOnClickListener(this);
        nullLayout.setOnClickListener(this);
        if (Utils.isNetworkConnected(this)) {
            GetUserData();
        } else {
            nullLayout.setClickable(true);
            nullLayout.setVisibility(View.VISIBLE);
            imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
            tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");

        }

    }

    public int getScrollY() {
        try {
            View c = listView.getChildAt(0);
            if (c == null) {
                return 0;
            }
            int firstVisiblePosition = listView.getFirstVisiblePosition() - 1;
            int top = c.getTop();
            return -top + firstVisiblePosition * c.getHeight();
        } catch (Exception e) {
        }
        return 0;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.more_image:
                GetMore(v);
                break;
            case R.id.chat_image:
                RongIM.getInstance().setCurrentUserInfo(new UserInfo("xmb_user_" + userId, nick, Uri.parse(Utils.GetPhotoPath(avatar))));
                RongIM.getInstance().setMessageAttachedUserInfo(true);

                if (Utils.UserSharedPreferences(this).getString("avatar", "").equals("")) {
                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(Utils.UserSharedPreferences(this).getString("RY_Id", ""), Utils.UserSharedPreferences(this).getString("nick", ""), Uri.parse("")));
                } else {
                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(Utils.UserSharedPreferences(this).getString("RY_Id", ""), Utils.UserSharedPreferences(this).getString("nick", ""), Uri.parse(Utils.GetPhotoPath(Utils.UserSharedPreferences(this).getString("avatar", "")))));
                }
                RongIM.getInstance().setMessageAttachedUserInfo(true);

                RongIM.getInstance().startPrivateChat(this, "xmb_user_" + userId, nick);

                break;
            case R.id.attention_text:
                Attention();
                break;
            case R.id.null_layout:
                if (Utils.isNetworkConnected(this)) {
                    GetUserData();
                } else {
                    Utils.showToast(UserDetitalActivity.this, "请检查网络是否连接");
                    nullLayout.setClickable(true);
                    nullLayout.setVisibility(View.VISIBLE);
                    imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                    tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");

                }
                break;

        }
    }


    private void Attention() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Follow);
        requestParams.addHeader("token", Utils.GetToken(UserDetitalActivity.this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("followUserId", userId);
        XUtil.post(requestParams, UserDetitalActivity.this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    if (new JSONObject(result).getString("code").equals("0")) {
                        //1 是已关注 0 是未关注
                        if (isFollow == 1) {
                            isFollow = 0;
                            beFollowed = String.valueOf(Integer.valueOf(beFollowed) - 1);
                            be_follows_text.setText(beFollowed);

                            attention_text.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_yellow_corner));
                            attention_text.setText("关注");
                            for (int i = 0; i < followList.size(); i++) {
                                if (userId.equals(followList.get(i).get("followUserId"))) {
                                    followList.remove(i);
                                }
                            }
                            utilsEditor.putString("followList", Utils.changeFollowToJson(followList));
                            utilsEditor.commit();


                        } else {
                            isFollow = 1;
                            beFollowed = String.valueOf(Integer.valueOf(beFollowed) + 1);
                            be_follows_text.setText(beFollowed);
                            attention_text.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_gray_corner_45));
                            attention_text.setText("已关注");
                            Map<String, String> map = new HashMap<String, String>();
                            map.put("followId", new JSONObject(result).getJSONObject("result").optString("followId"));
                            map.put("userId", new JSONObject(result).getJSONObject("result").optString("userId"));
                            map.put("followUserId", new JSONObject(result).getJSONObject("result").optString("followUserId"));
                            map.put("nick", nick);
                            map.put("avatar", avatar);
                            map.put("status", new JSONObject(result).getJSONObject("result").optString("status"));
                            followList.add(map);
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

    private void GetUserData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.UserBase);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("token", Utils.GetToken(this));
        requestParams.addBodyParameter("deviceId", MyApplication.deviceId);

        requestParams.addBodyParameter("uid", userId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

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
                            Picasso.with(UserDetitalActivity.this).load(Utils.GetPhotoPath(avatar)).into(head_image);
                        } else {
                            head_image.setImageDrawable(getResources().getDrawable(R.mipmap.icon_head));
                        }
                        be_follows_text.setText(beFollowed);
                        follows_text.setText(follow);
                        aims_text.setText(aim);
                        nick_text.setText(nick);
                        brief_text.setText(brief);

                        listView.addHeaderView(head_view);
                        GetHotData(page + "", "10");
                    } else {
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

    private void GetHotData(String page, String r) {
        RequestParams requestParams = new RequestParams(Url.Url + Url.AimDynamicUser);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("p", page);
        requestParams.addBodyParameter("r", r);
        requestParams.addBodyParameter("uid", userId);

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        total = Integer.valueOf(new JSONObject(result).getString("total"));
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        if (isDown) {
                            dynamicEntityList.clear();
                        }
                        if (!Utils.UtilsSharedPreferences(UserDetitalActivity.this).getString("followList", "").equals("")) {
                            followList = Utils.GetFollowList(Utils.UtilsSharedPreferences(UserDetitalActivity.this).getString("followList", ""));
                        } else {
                            followList.clear();
                        }
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject dynamicObject = jsonArray.getJSONObject(j).getJSONObject("dynamic");
                            JSONArray commentsArray = jsonArray.getJSONObject(j).getJSONArray("comments");

                            commentEntityList = new ArrayList<CommentEntity>();
                            dynamicEntity = new DynamicEntity();

                            dynamicEntity.setId(dynamicObject.optString("id"));
                            dynamicEntity.setAimId(dynamicObject.optString("aimId"));
                            dynamicEntity.setUserId(dynamicObject.getString("userId"));
                            dynamicEntity.setNick(dynamicObject.optString("nick"));
                            dynamicEntity.setAvatar(dynamicObject.optString("avatar"));
                            dynamicEntity.setContent(dynamicObject.optString("content"));
                            dynamicEntity.setCity(dynamicObject.optString("city"));
                            dynamicEntity.setMoney(dynamicObject.optString("money"));

                            dynamicEntity.setImg1(dynamicObject.optString("img1"));
                            dynamicEntity.setImg2(dynamicObject.optString("img2"));
                            dynamicEntity.setImg3(dynamicObject.optString("img3"));
                            dynamicEntity.setVideo(dynamicObject.optString("video"));

                            dynamicEntity.setCreateTime(dynamicObject.optString("createTime"));
                            dynamicEntity.setUpdateTime(dynamicObject.optString("updateTime"));
                            dynamicEntity.setProvince(dynamicObject.optString("province"));
                            dynamicEntity.setIsPaid(dynamicObject.optString("isPaid"));


                            dynamicEntity.setSupports(jsonArray.getJSONObject(j).optString("supports"));
                            dynamicEntity.setHaveRedPacket(jsonArray.getJSONObject(j).optString("haveRedPacket"));
                            dynamicEntity.setVotes(jsonArray.getJSONObject(j).optString("votes"));
                            dynamicEntity.setHaveVote(jsonArray.getJSONObject(j).optString("haveVote"));

                            for (int i = 0; i < commentsArray.length(); i++) {
                                commentEntity = new CommentEntity();
                                commentEntity.setCommentId(commentsArray.getJSONObject(i).optString("commentId"));
                                commentEntity.setUserId(commentsArray.getJSONObject(i).optString("userId"));
                                commentEntity.setPid(commentsArray.getJSONObject(i).optString("pid"));
                                commentEntity.setContent(commentsArray.getJSONObject(i).optString("content"));
                                commentEntity.setAimId(commentsArray.getJSONObject(i).optString("aimId"));
                                commentEntity.setDynamicId(commentsArray.getJSONObject(i).optString("dynamicId"));
                                commentEntity.setCreateTime(commentsArray.getJSONObject(i).optString("createTime"));
                                commentEntity.setNick(commentsArray.getJSONObject(i).optString("nick"));
                                commentEntity.setAvatar(commentsArray.getJSONObject(i).optString("avatar"));

                                commentEntityList.add(commentEntity);
                            }
                            dynamicEntity.setCommentList(commentEntityList);
                            dynamicEntityList.add(dynamicEntity);
                        }
                        if (dynamicEntityList.size() < 1) {
                            nullLayout.setVisibility(View.VISIBLE);
                            nullLayout.setClickable(false);
                            imgEmpty.setImageResource(R.mipmap.icon_dynamic_null);
                            tvEmpty.setText("没 有 任 何 用 户 动 态 ~");

                        } else {
                            nullLayout.setClickable(false);
                            nullLayout.setVisibility(View.GONE);

                        }
                        hotAdapter.notifyDataSetChanged();
                    } else if (code.equals("100000")) {
                        if (isDown) {
                            dynamicEntityList.clear();
                        }
                        if (dynamicEntityList.size() < 1) {
                            nullLayout.setVisibility(View.VISIBLE);
                            nullLayout.setClickable(false);
                            imgEmpty.setImageResource(R.mipmap.icon_dynamic_null);
                            tvEmpty.setText("没 有 任 何 用 户 动 态 ~");
                        } else {
                            nullLayout.setClickable(false);
                            nullLayout.setVisibility(View.GONE);

                        }

                    } else {
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_wrong);
                        tvEmpty.setText("出 错! 点 击 重 新 尝 试");
//                        Utils.showToast(UserDetitalActivity.this, new JSONObject(result).getString("msg"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                if (!ex.getMessage().equals("")) {
//                    Utils.showToast(UserDetitalActivity.this, ex.getMessage());
//                }
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
            }

            @Override
            public void onFinished() {
                pullToRefreshListView.onRefreshComplete();
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
        topLayout.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
        head_view.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
//        topLayout.setLayoutParams();;
    }

    public class HotAdapter extends BaseAdapter {

        private Context context;


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

                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_hot, parent, false);
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
            if (!dynamicEntityList.get(position).getUpdateTime().equals("")) {
                viewHolder.timeText.setText(Utils.GetTime(Long.valueOf(dynamicEntityList.get(position).getUpdateTime())));
            }

            if (!avatar.equals("")) {
                Picasso.with(context).load(Utils.GetPhotoPath(avatar)).into(viewHolder.headImage);
            } else {
                viewHolder.headImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_head));
            }


            if (dynamicEntityList.get(position).getUserId().equals(Utils.UserSharedPreferences(context).getString("id", ""))) {
                viewHolder.moreLayout.setVisibility(View.GONE);
            } else {
                viewHolder.moreLayout.setVisibility(View.VISIBLE);
            }
            viewHolder.attentionText.setVisibility(View.GONE);
            viewHolder.moreImage.setVisibility(View.GONE);

            if (Integer.valueOf(dynamicEntityList.get(position).getHaveRedPacket()) > 0) {
                viewHolder.moneyImage.setVisibility(View.VISIBLE);
            } else {
                viewHolder.moneyImage.setVisibility(View.GONE);
            }
//            viewHolder.moneyImage.setVisibility(View.VISIBLE);
            viewHolder.moneyImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent().setClass(context, RedHaveActivity.class);
                    intent.putExtra("dynamicId", dynamicEntityList.get(position).getId());
                    context.startActivity(intent);
                }
            });

            viewHolder.voteNumberText.setText(dynamicEntityList.get(position).getVotes());
            viewHolder.supportNumberText.setText(dynamicEntityList.get(position).getSupports());
            if (dynamicEntityList.get(position).getHaveVote().equals("0")) {
                viewHolder.voteImage.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_vote_off));
            } else {
                viewHolder.voteImage.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_vote_on));
            }
            viewHolder.voteImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams requestParams = new RequestParams(Url.Url + Url.Vote);
                    requestParams.addHeader("token", Utils.GetToken(UserDetitalActivity.this));
                    requestParams.addHeader("deviceId", MyApplication.deviceId);
                    requestParams.addBodyParameter("objectId", dynamicEntityList.get(position).getId());

                    XUtil.post(requestParams, UserDetitalActivity.this, new XUtil.XCallBackLinstener() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                String code = new JSONObject(result).getString("code");
                                if (code.equals("0")) {
                                    if (dynamicEntityList.get(position).getHaveVote().equals("0")) {
                                        viewHolder.voteImage.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_vote_on));
                                        dynamicEntityList.get(position).setHaveVote("1");
                                        dynamicEntityList.get(position).setVotes(String.valueOf(Integer.valueOf(dynamicEntityList.get(position).getVotes()) + 1));
                                        viewHolder.voteNumberText.setText(dynamicEntityList.get(position).getVotes());
                                    } else {
                                        viewHolder.voteImage.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_vote_off));
                                        dynamicEntityList.get(position).setHaveVote("0");
                                        dynamicEntityList.get(position).setVotes(String.valueOf(Integer.valueOf(dynamicEntityList.get(position).getVotes()) - 1));
                                        viewHolder.voteNumberText.setText(dynamicEntityList.get(position).getVotes());
                                    }

                                } else {
                                    Utils.showToast(UserDetitalActivity.this, new JSONObject(result).getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            if (!ex.getMessage().equals("")) {
                                Utils.showToast(UserDetitalActivity.this, ex.getMessage());
                            }
                        }

                        @Override
                        public void onFinished() {

                        }
                    });

                }
            });

            viewHolder.moneyText.setText(dynamicEntityList.get(position).getMoney());
            viewHolder.commentImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View windowView = LayoutInflater.from(UserDetitalActivity.this).inflate(
                            R.layout.window_comments, null);
                    final PopupWindow popupWindow = new PopupWindow(windowView,
                            LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, true);
                    final EditText comment_edit = (EditText) windowView.findViewById(R.id.comment_edit);
                    comment_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                        @Override
                        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                            if (actionId == EditorInfo.IME_ACTION_SEND) {
                                if (!comment_edit.getText().toString().trim().equals("")) {
                                    RequestParams requestParams = new RequestParams(Url.Url + Url.AimDynamicComment);
                                    requestParams.addHeader("token", Utils.GetToken(UserDetitalActivity.this));
                                    requestParams.addHeader("deviceId", MyApplication.deviceId);
                                    requestParams.addBodyParameter("dynamicId", dynamicEntityList.get(position).getId());
                                    requestParams.addBodyParameter("content", comment_edit.getText().toString().trim());
                                    XUtil.put(requestParams, UserDetitalActivity.this, new XUtil.XCallBackLinstener() {
                                        @Override
                                        public void onSuccess(String result) {
                                            try {
                                                String code = new JSONObject(result).getString("code");
                                                if (code.equals("0")) {

                                                    commentEntity = new CommentEntity();
                                                    commentEntity.setCommentId(new JSONObject(result).getJSONObject("result").getString("commentId"));
                                                    commentEntity.setPid(new JSONObject(result).getJSONObject("result").getString("pid"));
                                                    commentEntity.setUserId(new JSONObject(result).getJSONObject("result").getString("userId"));
                                                    commentEntity.setContent(new JSONObject(result).getJSONObject("result").getString("content"));
                                                    commentEntity.setAimId(new JSONObject(result).getJSONObject("result").getString("aimId"));
                                                    commentEntity.setDynamicId(new JSONObject(result).getJSONObject("result").getString("dynamicId"));
                                                    commentEntity.setCreateTime(new JSONObject(result).getJSONObject("result").getString("createTime"));
                                                    commentEntity.setNick(new JSONObject(result).getJSONObject("result").getString("nick"));
                                                    commentEntity.setAvatar(new JSONObject(result).getJSONObject("result").getString("avatar"));

                                                    dynamicEntityList.get(position).getCommentList().add(0, commentEntity);

                                                    Utils.showToast(UserDetitalActivity.this, "评论成功");
                                                    popupWindow.dismiss();
                                                    notifyDataSetChanged();

                                                } else {
                                                    Utils.showToast(UserDetitalActivity.this, new JSONObject(result).getString("msg"));
                                                    popupWindow.dismiss();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void onError(Throwable ex, boolean isOnCallback) {
                                            Utils.showToast(UserDetitalActivity.this, ex.getMessage());
                                        }

                                        @Override
                                        public void onFinished() {

                                        }
                                    });
                                }


                            }
                            return false;
                        }
                    });

                    popupWindow.setAnimationStyle(R.style.MyDialogStyle);
                    popupWindow.setTouchable(true);
                    popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
                    popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

                    // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
                    // 我觉得这里是API的一个bug
                    popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
                    // 设置好参数之后再show
                    popupWindow.showAtLocation(v, Gravity.BOTTOM, 0, 0);
                }
            });

            viewHolder.payImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPosition = position;
                    Intent intent = new Intent(UserDetitalActivity.this, SupportMoneyActivity.class);
                    intent.putExtra("dynamicId", dynamicEntityList.get(position).getId());
                    intent.putExtra("aimId", dynamicEntityList.get(position).getAimId());
                    intent.putExtra("nick", dynamicEntityList.get(position).getNick());
                    intent.putExtra("avatar", dynamicEntityList.get(position).getAvatar());
                    startActivityForResult(intent, Code.SupportAim);

                }
            });


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
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(imageList.get(0)) + Url.SMALL_PHOTO_URL2, imageOptions);
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
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(imageList.get(0)) + Url.SMALL_PHOTO_URL, imageOptions);
                    x.image().bind(viewHolder.image2, Utils.GetPhotoPath(imageList.get(1)) + Url.SMALL_PHOTO_URL, imageOptions);


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
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg1()) + Url.SMALL_PHOTO_URL, imageOptions);
                    x.image().bind(viewHolder.image2, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg2()) + Url.SMALL_PHOTO_URL, imageOptions);
                    x.image().bind(viewHolder.image3, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg3()) + Url.SMALL_PHOTO_URL, imageOptions);

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

//            viewHolder.image1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    imageLayout.setVisibility(View.VISIBLE);
//                    x.image().bind(pinchImage, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg1()), imageOptions);
//                }
//            });
//            viewHolder.image2.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    imageLayout.setVisibility(View.VISIBLE);
//                    x.image().bind(pinchImage, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg2()), imageOptions);
//                }
//            });
//            viewHolder.image3.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    imageLayout.setVisibility(View.VISIBLE);
//                    x.image().bind(pinchImage, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg3()), imageOptions);
//                }
//            });

            viewHolder.commentMoreText.setText("查看全部" + dynamicEntityList.get(position).getCommentList().size() + "条评论");
            if (dynamicEntityList.get(position).getCommentList().size() > 2) {
                viewHolder.commentAdapter = new CommentAdapter(UserDetitalActivity.this, dynamicEntityList.get(position).getCommentList(), 2);
                viewHolder.commentList.setAdapter(viewHolder.commentAdapter);
                viewHolder.commentMoreText.setVisibility(View.VISIBLE);
            } else {
                viewHolder.commentAdapter = new CommentAdapter(UserDetitalActivity.this, dynamicEntityList.get(position).getCommentList(), dynamicEntityList.get(position).getCommentList().size());
                viewHolder.commentList.setAdapter(viewHolder.commentAdapter);
                viewHolder.commentMoreText.setVisibility(View.GONE);
            }
            viewHolder.commentMoreText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    viewHolder.commentAdapter.SetNumber(dynamicEntityList.get(position).getCommentList().size());
                    viewHolder.commentMoreText.setVisibility(View.GONE);
                }
            });

            viewHolder.hot_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(UserDetitalActivity.this, AimMoreActivity.class);
                    intent.putExtra(AimMoreActivity.KEY_AIMID, dynamicEntityList.get(position).getAimId());
                    startActivity(intent);
                }
            });

            if (dynamicEntityList.get(position).getCity().equals("")) {
                viewHolder.cityLayout.setVisibility(View.GONE);
            } else {
                viewHolder.cityLayout.setVisibility(View.VISIBLE);
                viewHolder.cityText.setText(dynamicEntityList.get(position).getCity());
                viewHolder.provinceText.setText(dynamicEntityList.get(position).getProvince());
            }

            return convertView;
        }


        class ViewHolder {
            @InjectView(R.id.hot_layout)
            LinearLayout hot_layout;
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
            @InjectView(R.id.more_layout)
            RelativeLayout moreLayout;
            @InjectView(R.id.money_text)
            TextView moneyText;
            @InjectView(R.id.money_image)
            ImageView moneyImage;
            @InjectView(R.id.vote_image)
            ImageView voteImage;
            @InjectView(R.id.comment_image)
            ImageView commentImage;
            @InjectView(R.id.pay_image)
            ImageView payImage;
            @InjectView(R.id.more_image)
            ImageView moreImage;
            @InjectView(R.id.vote_number_text)
            TextView voteNumberText;
            @InjectView(R.id.support_number_text)
            TextView supportNumberText;
            @InjectView(R.id.comment_list)
            MyListView commentList;


            @InjectView(R.id.money_layout)
            RelativeLayout moneyLayout;
            @InjectView(R.id.icon_money_text)
            TextView iconMoneyText;
            @InjectView(R.id.icon_money_image)
            ImageView iconMoneyImage;

            @InjectView(R.id.comment_more_text)
            TextView commentMoreText;

            @InjectView(R.id.city_text)
            TextView cityText;
            @InjectView(R.id.city_layout)
            LinearLayout cityLayout;
            @InjectView(R.id.province_text)
            TextView provinceText;
            private CommentAdapter commentAdapter;

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
                XUtil.put(requestParams, UserDetitalActivity.this, new XUtil.XCallBackLinstener() {
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
