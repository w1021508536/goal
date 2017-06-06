package com.pi.small.goal.my.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.TrajectoryAdapter;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.weight.PinchImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
public class TargetMoreActivity extends BaseActivity {


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
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.rl_image)
    RelativeLayout rlImage;
    @InjectView(R.id.tl_top)
    LinearLayout tlTop;
    @InjectView(R.id.pimg)
    PinchImageView pimg;
    private int allDy;
    private View header;
    private TrajectoryAdapter adapter;
    private String aimId = "";
    private String userId = "";

    public static final String KEY_AIMID = "aimId";
    private DonutProgress progress;            //圆形的百分比加载框

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_target_more);
        ButterKnife.inject(this);
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
        progress.setProgress(16);
    }

    @Override
    public void getData() {
        super.getData();
        requestParams.setUri(Url.Url + "/aim");
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("aimId", aimId);
        requestParams.addBodyParameter("userId", userId);

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!RenameActivity.callOk(result)) return;

                try {
                    JSONObject jsonObj = (JSONObject) ((JSONObject) new JSONObject(result).get("result"));
                    setData(jsonObj);
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

    }

    /**
     * 解析json并绑定视图上
     * create  wjz
     **/

    private void setData(JSONObject jsonObj) {

        if (header == null) return;
        TextView tv_budget = (TextView) header.findViewById(R.id.tv_budget_head);   //预算额度
        TextView tv_money = (TextView) header.findViewById(R.id.tv_money_head);   //已存金额
        TextView tv_cycle = (TextView) header.findViewById(R.id.tv_cycle_head);   //周期
        TextView tv_supports = (TextView) header.findViewById(R.id.tv_goodNums_targetMOre);   //周期
        LinearLayout ll_images = (LinearLayout) header.findViewById(R.id.ll_imags_head);
        try {
            JSONObject aimJsonObj = (JSONObject) jsonObj.get("aim");
            JSONArray SupportJsonAry = (JSONArray) jsonObj.getJSONArray("supports");

            //   SupportJsonAry.

            int money = (int) aimJsonObj.get("money");
            int budget = (int) aimJsonObj.get("budget");
            tv_budget.setText(budget + "");
            tv_money.setText(money + "");
            tv_cycle.setText(aimJsonObj.get("cycle") + "");
            progress.setProgress(((float) money) / budget);

            tv_supports.setText(SupportJsonAry.length() + "助力");
            for (int i = 0; i < 5; i++) {   //最多显示5个助力的人的头像
                if (SupportJsonAry.length() > i) {
                    CircleImageView circleImageView = new CircleImageView(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                    layoutParams.setMargins(Utils.dip2px(this, 5), Utils.dip2px(this, 5), Utils.dip2px(this, 5), Utils.dip2px(this, 5));
                    circleImageView.setLayoutParams(layoutParams);
                    ll_images.addView(circleImageView);
                    JSONObject one = (JSONObject) SupportJsonAry.opt(i);
                    Picasso.with(this).load(Utils.GetPhotoPath((String) one.get("avatar"))).into(circleImageView);
                }
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initWeight() {
        super.initWeight();
        rlImage.setOnClickListener(this);
        pimg.setOnClickListener(this);

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
        }
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
