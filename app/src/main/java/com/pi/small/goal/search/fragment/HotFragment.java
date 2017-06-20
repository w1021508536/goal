package com.pi.small.goal.search.fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
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
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.activity.AimMoreActivity;
import com.pi.small.goal.search.activity.RedHaveActivity;
import com.pi.small.goal.search.activity.SupportMoneyActivity;
import com.pi.small.goal.search.activity.UserDetitalActivity;
import com.pi.small.goal.search.adapter.CommentAdapter;
import com.pi.small.goal.search.adapter.ViewPagerSearchAdapter;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.MyListView;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.WrapContentHeightViewPager;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.CommentEntity;
import com.pi.small.goal.utils.entity.DynamicEntity;
import com.pi.small.goal.weight.PinchImageView;
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
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class HotFragment extends Fragment {


    @InjectView(R.id.hot_list)
    PullToRefreshListView hotList;
    @InjectView(R.id.pinch_image)
    PinchImageView pinchImage;
    @InjectView(R.id.image_layout)
    RelativeLayout imageLayout;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;

    private ListView listView;

    private List<DynamicEntity> dynamicEntityList;
    private List<CommentEntity> commentEntityList;
    private CommentEntity commentEntity;
    private DynamicEntity dynamicEntity;

    private HotAdapter hotAdapter;

    private View currentView;

    private int width;
    private List<Map<String, String>> followList = new ArrayList<Map<String, String>>();
    private Parcelable state;
    private int currentPosition;

    private boolean isDown = false;
    private int page = 1;

    private int total;

    private SharedPreferences utilsSharedPreferences;
    private SharedPreferences.Editor utilsEditor;

    private int position = 0;

    private List<View> viewList;
    private View itemView;
    private ImageView[] imageViews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        currentView = inflater.inflate(R.layout.fragment_hot, container, false);
        ButterKnife.inject(this, currentView);
        return currentView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        width = (getActivity().getWindowManager().getDefaultDisplay().getWidth() - 130);
        utilsSharedPreferences = Utils.UtilsSharedPreferences(getActivity());
        utilsEditor = utilsSharedPreferences.edit();
        dynamicEntityList = new ArrayList<DynamicEntity>();
        hotAdapter = new HotAdapter(getActivity());
        viewList = new ArrayList<View>();
        hotList.setAdapter(hotAdapter);
        init();
    }


    private void init() {

//        itemView = LayoutInflater.from(getActivity()).inflate(
//                R.layout.view_viewpager_hot, null);

        hotList.setMode(PullToRefreshBase.Mode.BOTH);
        hotList.setAdapter(hotAdapter);

        /**
         * 实现 接口  OnRefreshListener2<ListView>  以便与监听  滚动条到顶部和到底部
         */
        hotList.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //   new GetDownDataTask().execute();
                isDown = true;
                page = 1;
                GetHotData(page + "", "10");
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //        new GetUpDataTask().execute();
                isDown = false;

                if (page * 10 >= total) {

                } else {
                    page = page + 1;
                    GetHotData(page + "", "10");
                }
            }
        });

        GetHotData(page + "", "10");
        AddHeadView();
    }


    private void AddHeadView() {

        View view = LayoutInflater.from(getActivity()).inflate(
                R.layout.view_viewpager_hot, null);
        for (int i = 0; i < 3; i++) {
            itemView = LayoutInflater.from(getActivity()).inflate(
                    R.layout.item_view_pager_hot, null);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
            itemView.setLayoutParams(params);
            ImageView banner_image = (ImageView) itemView.findViewById(R.id.banner_image);
            banner_image.setImageDrawable(getActivity().getResources().getDrawable(R.mipmap.banner));
            viewList.add(itemView);
        }


        WrapContentHeightViewPager viewPager = (WrapContentHeightViewPager) view.findViewById(R.id.view_pager);

        LinearLayout viewGroup = (LinearLayout) view.findViewById(R.id.viewGroup);
        setPoint(viewGroup);
        ViewPagerSearchAdapter viewPagerSearchAdapter = new ViewPagerSearchAdapter(getActivity(), viewList);
        viewPager.setAdapter(viewPagerSearchAdapter);
        listView = hotList.getRefreshableView();
        listView.addHeaderView(view);
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
    }

    private void setPoint(LinearLayout viewGroup) {
        imageViews = new ImageView[viewList.size()];
        for (int i = 0; i < viewList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            //设置小圆点imageview的参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(10, 10);
            params.setMargins(10, 0, 10, 0);
            imageView.setLayoutParams(params);//创建一个宽高均为20 的布局
            //将小圆点layout添加到数组中
            imageViews[i] = imageView;

            //默认选中的是第一张图片，此时第一个小圆点是选中状态，其他不是
            if (i == 0) {
                imageViews[i].setBackgroundResource(R.mipmap.icon_dian_yellow);
            } else {
                imageViews[i].setBackgroundResource(R.mipmap.icon_dian_white);
            }

            //将imageviews添加到小圆点视图组
            viewGroup.addView(imageViews[i]);
        }
    }

    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            // TODO Auto-generated method stub

        }

        @Override
        public void onPageSelected(int position) {
            // TODO Auto-generated method stub
            position = position;
            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position].setBackgroundResource(R.mipmap.icon_dian_yellow);
                //不是当前选中的page，其小圆点设置为未选中的状态
                if (position != i) {
                    imageViews[i].setBackgroundResource(R.mipmap.icon_dian_white);
                }
            }

        }
    }

    /**
     * 下拉刷新
     */
    private class GetDownDataTask extends AsyncTask<Void, Void, List<DynamicEntity>> {

        //子线程请求数据
        @Override
        protected List<DynamicEntity> doInBackground(Void... params) {
            isDown = true;
            page = 1;
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GetHotData(page + "", "10");
            return dynamicEntityList;
        }

        //主线程更新UI
        @Override
        protected void onPostExecute(List<DynamicEntity> result) {

//            hotAdapter.notifyDataSetChanged();
            //通知RefreshListView 我们已经更新完成
            hotList.onRefreshComplete();

            super.onPostExecute(result);
        }
    }

    /**
     * 模拟网络加载数据的   异步请求类
     * 上拉加载
     */
    private class GetUpDataTask extends AsyncTask<Void, Void, List<DynamicEntity>> {

        //子线程请求数据
        @Override
        protected List<DynamicEntity> doInBackground(Void... params) {
            isDown = false;

            if (page * 10 >= total) {

            } else {
                page = page + 1;
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                GetHotData(page + "", "10");
            }


            return dynamicEntityList;
        }

        //主线程更新UI
        @Override
        protected void onPostExecute(List<DynamicEntity> result) {
//            hotAdapter.notifyDataSetChanged();
            hotList.onRefreshComplete();
            if (page * 10 >= total) {
                Utils.showToast(getActivity(), "没有更多数据了");
            }
            super.onPostExecute(result);
        }
    }


    private void GetHotData(String page, String r) {
//        state = hotList.getRefreshableView().onSaveInstanceState();
        RequestParams requestParams = new RequestParams(Url.Url + Url.HotSearch);
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("p", page);
        requestParams.addBodyParameter("r", r);
        XUtil.get(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=======GetHotData=============" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        total = Integer.valueOf(new JSONObject(result).getString("total"));
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        if (isDown) {
                            dynamicEntityList.clear();
                        }
                        if (!Utils.UtilsSharedPreferences(getActivity()).getString("followList", "").equals("")) {
                            followList = Utils.GetFollowList(Utils.UtilsSharedPreferences(getActivity()).getString("followList", ""));
                        } else {
                            followList.clear();
                        }
                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject dynamicObject = jsonArray.getJSONObject(j).getJSONObject("dynamic");
                            JSONArray commentsArray = jsonArray.getJSONObject(j).getJSONArray("comments");

                            commentEntityList = new ArrayList<CommentEntity>();
                            dynamicEntity = new DynamicEntity();
//
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

                            System.out.println("=============followJson======" + Utils.UtilsSharedPreferences(getActivity()).getString("followList", ""));
                            dynamicEntity.setIsFollow("0");
                            for (int i = 0; i < followList.size(); i++) {
                                if (dynamicEntity.getUserId().equals(followList.get(i).get("followUserId"))) {
                                    dynamicEntity.setIsFollow("1");
                                }
                            }

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

                        hotAdapter.notifyDataSetChanged();
//                        hotList.getRefreshableView().onRestoreInstanceState(state);
                    } else if (code.equals("100000")) {
                        if (isDown) {
                            dynamicEntityList.clear();
                        }
                        if (dynamicEntityList.size() == 0) {
                            nullLayout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("")) {
                    Utils.showToast(getActivity(), ex.getMessage());
                }
                hotList.onRefreshComplete();
            }

            @Override
            public void onFinished() {
                hotList.onRefreshComplete();
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Code.SupportAim) {
            PutRedWindow();
        } else if (resultCode == Code.FailCode) {
            System.out.println("==========支付失败============");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //弹出框
    private void PutRedWindow() {
        final View windowView = LayoutInflater.from(getActivity()).inflate(
                R.layout.window_thanks_red, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);

        ImageView imageView = (ImageView) windowView.findViewById(R.id.image);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams requestParams = new RequestParams(Url.Url + Url.RedpacketThanksDraw);
                requestParams.addHeader("token", Utils.GetToken(getActivity()));
                requestParams.addHeader("deviceId", MyApplication.deviceId);
                XUtil.post(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
                    @Override
                    public void onSuccess(String result) {
                        Utils.showToast(getActivity(), result);
                        System.out.println("=======感谢红包=========" + result);
                        try {
                            String code = new JSONObject(result).getString("code");
                            if (code.equals("0")) {
                                String money = new JSONObject(result).getJSONObject("result").optString("money");
                                if (money.equals("")) {
                                    Utils.showToast(getActivity(), "恭喜您获取红包");
                                } else {
                                    Utils.showToast(getActivity(), "恭喜您获取红包" + money + "元");
                                }
                            } else if (code.equals("100000")) {
                                Utils.showToast(getActivity(), getResources().getString(R.string.rad_null));
                            } else {
                                Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                            }
                            popupWindow.dismiss();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        popupWindow.dismiss();
                        System.out.println("========ex.getMessage()========" + ex.getMessage());
                        Utils.showToast(getActivity(), ex.getMessage());
                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });


//        windowView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popupWindow.dismiss();
//            }
//        });
        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(false);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow.showAtLocation(currentView, Gravity.CENTER, 0, 0);

    }

    @OnClick({R.id.pinch_image, R.id.image_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.pinch_image:
                imageLayout.setVisibility(View.GONE);
                break;
            case R.id.image_layout:
                imageLayout.setVisibility(View.GONE);
                break;
        }
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

                convertView = LayoutInflater.from(context).inflate(R.layout.item_list_hot, parent, false);
                viewHolder = new ViewHolder(convertView);

                convertView.setTag(viewHolder);

            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }

            viewHolder.nameText.setText(dynamicEntityList.get(position).getNick());

            if (dynamicEntityList.get(position).getContent().equals("")) {
                viewHolder.contentText.setVisibility(View.GONE);
            } else {
                viewHolder.contentText.setVisibility(View.VISIBLE);
                viewHolder.contentText.setText(dynamicEntityList.get(position).getContent());
            }
            viewHolder.timeText.setText(Utils.GetTime(Long.valueOf(dynamicEntityList.get(position).getUpdateTime())));

            if (!dynamicEntityList.get(position).getAvatar().equals("")) {
                Picasso.with(context).load(Utils.GetPhotoPath(dynamicEntityList.get(position).getAvatar())).into(viewHolder.headImage);
            } else {
                viewHolder.headImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_head));
            }

            viewHolder.headImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setClass(getActivity(), UserDetitalActivity.class);
                    intent.putExtra("userId", dynamicEntityList.get(position).getUserId());
                    startActivity(intent);
                }
            });

            if (dynamicEntityList.get(position).getUserId().equals(Utils.UserSharedPreferences(context).getString("id", ""))) {
                viewHolder.attentionText.setVisibility(View.GONE);
                viewHolder.moreLayout.setVisibility(View.GONE);
            } else {
                viewHolder.attentionText.setVisibility(View.VISIBLE);
                viewHolder.moreLayout.setVisibility(View.VISIBLE);
            }
            System.out.println("=============dynamicEntityList.get(position).getIsFollow()=========" + dynamicEntityList.get(position).getIsFollow());
            if (dynamicEntityList.get(position).getIsFollow().equals("0")) {
                viewHolder.attentionText.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.background_white_yellow_corner));
                viewHolder.attentionText.setTextColor(getActivity().getResources().getColor(R.color.yellow_light));
                viewHolder.attentionText.setText("关注");

            } else {
                viewHolder.attentionText.setBackgroundDrawable(getActivity().getResources().getDrawable(R.drawable.background_white_gray_corner));
                viewHolder.attentionText.setTextColor(getActivity().getResources().getColor(R.color.gray_heavy));
                viewHolder.attentionText.setText("已关注");

            }
            viewHolder.attentionText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RequestParams requestParams = new RequestParams(Url.Url + Url.Follow);
                    requestParams.addHeader("token", Utils.GetToken(getActivity()));
                    requestParams.addHeader("deviceId", MyApplication.deviceId);
                    requestParams.addBodyParameter("followUserId", dynamicEntityList.get(position).getUserId());
                    XUtil.post(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                if (new JSONObject(result).getString("code").equals("0")) {
                                    //1 是已关注 0 是未关注
                                    if (dynamicEntityList.get(position).getIsFollow().equals("1")) {
                                        for (int i = 0; i < followList.size(); i++) {
                                            if (dynamicEntityList.get(position).getUserId().equals(followList.get(i).get("followUserId"))) {
                                                followList.remove(i);
                                            }
                                        }
                                        utilsEditor.putString("followList", Utils.changeFollowToJson(followList));
                                        utilsEditor.commit();
                                    } else {
                                        Map<String, String> map = new HashMap<String, String>();

                                        map.put("followId", new JSONObject(result).getJSONObject("result").optString("followId"));
                                        map.put("userId", new JSONObject(result).getJSONObject("result").optString("userId"));
                                        map.put("followUserId", new JSONObject(result).getJSONObject("result").optString("followUserId"));
                                        map.put("nick", dynamicEntityList.get(position).getNick());
                                        map.put("avatar", dynamicEntityList.get(position).getAvatar());
                                        map.put("status", new JSONObject(result).getJSONObject("result").optString("status"));
                                        followList.add(map);

                                        utilsEditor.putString("followList", Utils.changeFollowToJson(followList));
                                        utilsEditor.commit();

                                    }
                                    isDown = true;
                                    GetHotData("1", page * 10 + "");
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
                    System.out.println("==============dynamicEntityList.get(position).getId()=======" + dynamicEntityList.get(position).getId());
                    RequestParams requestParams = new RequestParams(Url.Url + Url.Vote);
                    requestParams.addHeader("token", Utils.GetToken(getActivity()));
                    requestParams.addHeader("deviceId", MyApplication.deviceId);
                    requestParams.addBodyParameter("objectId", dynamicEntityList.get(position).getId());

                    XUtil.post(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
                        @Override
                        public void onSuccess(String result) {
                            System.out.println("================点赞===========" + result);
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
                                    Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        @Override
                        public void onError(Throwable ex, boolean isOnCallback) {
                            if (!ex.getMessage().equals("")) {
                                Utils.showToast(getActivity(), ex.getMessage());
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
                    View windowView = LayoutInflater.from(getActivity()).inflate(
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
                                    requestParams.addHeader("token", Utils.GetToken(getActivity()));
                                    requestParams.addHeader("deviceId", MyApplication.deviceId);
                                    requestParams.addBodyParameter("dynamicId", dynamicEntityList.get(position).getId());
                                    requestParams.addBodyParameter("content", comment_edit.getText().toString().trim());
                                    XUtil.put(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
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

                                                    Utils.showToast(getActivity(), "评论成功");
                                                    popupWindow.dismiss();
                                                    notifyDataSetChanged();

                                                } else {
                                                    Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                                                    popupWindow.dismiss();
                                                }
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }

                                        }

                                        @Override
                                        public void onError(Throwable ex, boolean isOnCallback) {
                                            System.out.println("======================" + ex.getMessage());
                                            Utils.showToast(getActivity(), ex.getMessage());
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
                    Intent intent = new Intent(getActivity(), SupportMoneyActivity.class);
                    intent.putExtra("dynamicId", dynamicEntityList.get(position).getId());
                    intent.putExtra("aimId", dynamicEntityList.get(position).getAimId());
                    intent.putExtra("nick", dynamicEntityList.get(position).getNick());
                    intent.putExtra("avatar", dynamicEntityList.get(position).getAvatar());
                    startActivityForResult(intent, Code.SupportAim);

                }
            });

            viewHolder.moreImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetMore(position, v);
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
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.GONE);
                    viewHolder.image3.setVisibility(View.GONE);
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(imageList.get(0)+Url.SMALL_PHOTO_URL2), imageOptions);
                    ViewGroup.LayoutParams layoutParams1 = viewHolder.image1.getLayoutParams();
                    layoutParams1.height = width / 2;
                    layoutParams1.width = width;
                    viewHolder.image1.setLayoutParams(layoutParams1);

                } else if (imageList.size() == 2) {
                    viewHolder.image1.setVisibility(View.VISIBLE);
                    viewHolder.image2.setVisibility(View.VISIBLE);
                    viewHolder.image3.setVisibility(View.GONE);
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(imageList.get(0)+Url.SMALL_PHOTO_URL), imageOptions);
                    x.image().bind(viewHolder.image2, Utils.GetPhotoPath(imageList.get(1)+Url.SMALL_PHOTO_URL), imageOptions);


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
                    x.image().bind(viewHolder.image1, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg1())+Url.SMALL_PHOTO_URL, imageOptions);
                    x.image().bind(viewHolder.image2, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg2())+Url.SMALL_PHOTO_URL, imageOptions);
                    x.image().bind(viewHolder.image3, Utils.GetPhotoPath(dynamicEntityList.get(position).getImg3())+Url.SMALL_PHOTO_URL, imageOptions);
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
                viewHolder.commentAdapter = new CommentAdapter(getActivity(), dynamicEntityList.get(position).getCommentList(), 2);
                viewHolder.commentList.setAdapter(viewHolder.commentAdapter);
                viewHolder.commentMoreText.setVisibility(View.VISIBLE);
            } else {
                viewHolder.commentAdapter = new CommentAdapter(getActivity(), dynamicEntityList.get(position).getCommentList(), dynamicEntityList.get(position).getCommentList().size());
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
                    Intent intent = new Intent(getActivity(), AimMoreActivity.class);
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


    private void GetMore(final int position, View v) {
        View windowView = LayoutInflater.from(getActivity()).inflate(
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
                requestParams.addHeader("token", Utils.GetToken(getActivity()));
                requestParams.addHeader("deviceId", MyApplication.deviceId);
                requestParams.addBodyParameter("uid", dynamicEntityList.get(position).getUserId());
                XUtil.put(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
                    @Override
                    public void onSuccess(String result) {
                        try {
                            String code = new JSONObject(result).getString("code");
                            if (code.equals("0")) {
                                Utils.showToast(getActivity(), "举报成功");
                                report_text.setClickable(true);
                                popupWindow.dismiss();
                            } else {
                                Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                                report_text.setClickable(true);
                                popupWindow.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {
                        Utils.showToast(getActivity(), ex.getMessage());
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
