package com.pi.small.goal.my.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.TrajectoryAdapter;
import com.pi.small.goal.my.entry.DynamicEntity;
import com.pi.small.goal.my.entry.TargetHeadEntity;
import com.pi.small.goal.search.activity.SupportMoneyActivity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;
import com.pi.small.goal.utils.EditTextHeightUtil;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.weight.PinchImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 14:50
 * 修改：
 * 描述： 目标详情
 **/
public class AimMoreActivity extends BaseActivity {


    @InjectView(R.id.lv_targetMore)
    ListView lvTargetMore;
    @InjectView(R.id.srfl_targetMore)
    SwipeRefreshLayout srflTargetMore;
    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.collect_image_include)
    ImageView collectImageInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.tl_top)
    LinearLayout tlTop;
    @InjectView(R.id.pimg)
    PinchImageView pimg;
    @InjectView(R.id.rl_image)
    RelativeLayout rlImage;
    @InjectView(R.id.etv_targetMore)
    EditText etvTargetMore;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;
    @InjectView(R.id.rl_top)
    RelativeLayout rlTop;
    private int allDy;
    private View header;
    private TrajectoryAdapter adapter;
    private String aimId = "";
    private String userId = "";

    public static final String KEY_AIMID = "aimId";
    private DonutProgress progress;            //圆形的百分比加载框
    private boolean myAim;
    private String commentId;
    private TargetHeadEntity targetHeadEntity;      //头部视图布局的数据

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_target_more);
        ButterKnife.inject(this);
        EditTextHeightUtil.assistActivity(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        nameTextInclude.setText("目标详情");

        adapter = new TrajectoryAdapter(this);
        lvTargetMore.setAdapter(adapter);
        aimId = getIntent().getStringExtra(KEY_AIMID);

//        plvTargetMore.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
//        AbsListView.LayoutParams layoutParams = new AbsListView.LayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
//        View header = getLayoutInflater().inflate(R.layout.view_head_target, plvTargetMore, false);
//        header.setLayoutParams(layoutParams);
//        ListView lv = plvTargetMore.getRefreshableView();
//        lv.addHeaderView(header);

        setHeadView();

        super.initData();
    }

    private void setHeadView() {
        header = getLayoutInflater().inflate(R.layout.view_head_target, null);
        lvTargetMore.addHeaderView(header);
        srflTargetMore.setColorSchemeColors(getResources().getColor(R.color.chat_top));
        progress = (DonutProgress) header.findViewById(R.id.progress);
        progress.setFinishedStrokeWidth(12);
        progress.setUnfinishedStrokeWidth(12);
        progress.setTextColor(getResources().getColor(R.color.white));
        progress.setFinishedStrokeColor(getResources().getColor(R.color.chat_top));
        progress.setUnfinishedStrokeColor(getResources().getColor(R.color.progress_unfinish_color));
        progress.setProgress(16);
    }

    @Override
    public void getData() {
        super.getData();
        requestParams.setUri(Url.Url + "/aim");
        requestParams.addBodyParameter("aimId", aimId);
        requestParams.addBodyParameter("userId", userId);

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!RenameActivity.callOk(result)) return;

                try {
                    JSONObject jsonObj = (JSONObject) ((JSONObject) new JSONObject(result).get("result"));

                    Gson gson = new Gson();
                    targetHeadEntity = gson.fromJson(jsonObj.toString(), TargetHeadEntity.class);

                    setHeadViewData(targetHeadEntity);
                } catch (JSONException e) {


                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
        getTargetDynamic();

    }

    private void getTargetDynamic() {

        RequestParams requestParams = new RequestParams();
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.setUri(Url.Url + "/aim/dynamic");
        requestParams.addBodyParameter("aimId", aimId);
        //      requestParams.addBodyParameter("userId", userId);

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (RenameActivity.callOk(result)) {
                }

                try {
                    String jsonData = new JSONObject(result).getString("result");
                    Gson gson = new Gson();
                    List<DynamicEntity> data = gson.fromJson(jsonData, new TypeToken<List<DynamicEntity>>() {
                    }.getType());

                    if (new JSONObject(result).getString("pageNum").equals("1")) {
                        adapter.setData(data);
                    } else {
                        adapter.addData(data);
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

    /**
     * 解析json并绑定视图上
     * create  wjz
     **/

    private void setHeadViewData(TargetHeadEntity targetHeadEntity) {

        if (header == null) return;
        TextView tv_budget = (TextView) header.findViewById(R.id.tv_budget_head);   //预算额度
        TextView tv_money = (TextView) header.findViewById(R.id.tv_money_head);   //已存金额
        TextView tv_cycle = (TextView) header.findViewById(R.id.tv_cycle_head);   //周期
        TextView tv_supports = (TextView) header.findViewById(R.id.tv_goodNums_targetMOre);   //周期
        LinearLayout ll_images = (LinearLayout) header.findViewById(R.id.ll_imags_head);
        RelativeLayout rl_supports = (RelativeLayout) header.findViewById(R.id.rl_goodPeople_targetMore);

        if (targetHeadEntity.getSupports().size() == 0) {
            rl_supports.setVisibility(View.GONE);
        }

//            JSONObject aimJsonObj = (JSONObject) jsonObj.get("aim");
//            JSONObject userJsonObj = (JSONObject) jsonObj.get("user");
//            int userId = (int) userJsonObj.get("id");
        if ((targetHeadEntity.getUser().getId() + "").equals(sp.getString(KeyCode.USER_ID, "26"))) {
            myAim = true;
            collectImageInclude.setImageResource(R.mipmap.goals_setting_btn);
            adapter.setOperationShowFlag(false);
        } else {
            myAim = false;
            if (targetHeadEntity.getHaveCollect() == 1) {
                collectImageInclude.setImageResource(R.mipmap.collection_btn_pressed);
            } else {
                collectImageInclude.setImageResource(R.mipmap.collection_btn);
            }
        }

        tv_budget.setText(targetHeadEntity.getAim().getBudget() + "");
        tv_money.setText(targetHeadEntity.getAim().getMoney() + "");
        tv_cycle.setText(targetHeadEntity.getAim().getCycle() + "");
        String percentOne = Utils.getPercentOne((float) targetHeadEntity.getAim().getMoney() / targetHeadEntity.getAim().getBudget());

        progress.setProgress(Float.parseFloat(percentOne));

        List<TargetHeadEntity.SupportsBean> supports = targetHeadEntity.getSupports();
        CacheUtil.getInstance().setSupportEntityList(supports);

        tv_supports.setText(supports.size() + "助力");
        for (int i = 0; i < 5; i++) {   //最多显示5个助力的人的头像
            if (supports.size() > i) {
                CircleImageView circleImageView = new CircleImageView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                layoutParams.setMargins(Utils.dip2px(this, 5), Utils.dip2px(this, 10), Utils.dip2px(this, 5), Utils.dip2px(this, 10));
                circleImageView.setLayoutParams(layoutParams);
                ll_images.addView(circleImageView);
                circleImageView.setImageResource(R.mipmap.icon_user);

                TargetHeadEntity.SupportsBean supportsBean = supports.get(i);
                if (Utils.photoEmpty(supportsBean.getAvatar())) {
                    Picasso.with(this).load(supportsBean.getAvatar()).into(circleImageView);
                }
            }
        }


        rl_supports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AimMoreActivity.this, SupportActivity.class));
            }
        });
    }

    @Override
    public void initWeight() {
        super.initWeight();
        rlImage.setOnClickListener(this);
        pimg.setOnClickListener(this);
        collectImageInclude.setOnClickListener(this);
        srflTargetMore.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srflTargetMore.setRefreshing(false);
            }
        });

        rlTop.setOnClickListener(this);

        lvTargetMore.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount > 0) {
                    // 滚动到最后一行了
                }

                //顶部渐变色
                if (adapter == null)
                    return;
                int heigh = header.getHeight();
                if (heigh < getScrollY()) {

                    //Color.argb(255,0,178,238)
                    tlTop.setBackgroundColor(Color.argb(255, 239, 120, 52));
                } else {
                    Log.i("scl", getScrollY() + "");
                    float a = 255.0f / (float) heigh;
                    a = a * (float) getScrollY();
                    if (a > 255)
                        a = 255;
                    else if (a < 0)
                        a = 0;

                    tlTop.setBackgroundColor(Color.argb((int) a, 239, 120, 52));
                }
            }

        });

        adapter.setOnMyAdapterClickListener(new TrajectoryAdapter.myAdapterClickListener() {

            @Override
            public void great(String id) {
                goGreat(id);
            }

            @Override
            public void comment(String id) {
                commentId = id;
                etvTargetMore.setVisibility(View.VISIBLE);
                rlTop.setVisibility(View.VISIBLE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }

            @Override
            public void help(String id, String nick, String avatar) {

                Intent intent = new Intent(AimMoreActivity.this, SupportMoneyActivity.class);
                intent.putExtra("dynamicId", id);
                intent.putExtra("aimId", aimId);
                intent.putExtra("nick", nick);
                intent.putExtra("avatar", avatar);
                startActivity(intent);

            }


        });

        etvTargetMore.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                switch (actionId) {
                    case EditorInfo.IME_ACTION_DONE:

                        rlTop.setVisibility(View.GONE);
                        etvTargetMore.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        comment(commentId, etvTargetMore.getText().toString());

                        break;
                }

                return true;
            }
        });


    }


    /**
     * 点赞
     * create  wjz
     **/
    private void goGreat(String id) {

        RequestParams requestParams = Utils.getRequestParams(this);

        requestParams.setUri(Url.Url + "/vote/vote");
        requestParams.addBodyParameter("objectId", id);

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 去评论
     * create  wjz
     **/
    private void comment(String id, String content) {

        RequestParams requestParams = Utils.getRequestParams(this);

        requestParams.setUri(Url.Url + "/aim/dynamic/comment");
        requestParams.addBodyParameter("dynamicId", id);
        requestParams.addBodyParameter("content", content);

        XUtil.put(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!Utils.callOk(result)) return;

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    public int getScrollY() {
        View c = lvTargetMore.getChildAt(0);
        if (c == null) {
            return 0;
        }
        int firstVisiblePosition = lvTargetMore.getFirstVisiblePosition();
        int top = c.getTop();
        return -top + firstVisiblePosition * c.getHeight();
    }

    private int listViewScorllY(ListView lv) {
        View view = lv.getChildAt(0);
        if (view == null) {
            return 0;
        }
        int firstVisiblePosition = lv.getFirstVisiblePosition();
        int top = view.getTop();
        return -top + firstVisiblePosition * view.getHeight();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_image:
                rlImage.setVisibility(View.GONE);
                break;
            case R.id.pimg:
                rlImage.setVisibility(View.GONE);
                break;
            case R.id.collect_image_include:
                if (targetHeadEntity == null) return;
                if (myAim) {

                } else {

                    if (targetHeadEntity.getHaveCollect() == 0) {
                        collectAim(aimId, "1");
                    } else {
                        collectAim(aimId, "0");
                    }

                }
                break;
            case R.id.rl_top:
                rlTop.setVisibility(View.GONE);
                etvTargetMore.setVisibility(View.GONE);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                break;
        }
    }

    private void collectAim(String aimId, String status) {
        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/aim/collect");
        requestParams.addBodyParameter("aimId", aimId + "");
        requestParams.addBodyParameter("status", status);
        if (status.equals("1")) {
            collectImageInclude.setImageResource(R.mipmap.collection_btn_pressed);
        } else {
            collectImageInclude.setImageResource(R.mipmap.collection_btn);
        }

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!RenameActivity.callOk(result)) return;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Utils.showToast(AimMoreActivity.this, (String) jsonObject.get("msg"));
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (rlImage.getVisibility() == View.VISIBLE) {
                    rlImage.setVisibility(View.GONE);
                    return false;
                } else {
                    break;
                }
        }

        return super.onKeyDown(keyCode, event);
    }
}
