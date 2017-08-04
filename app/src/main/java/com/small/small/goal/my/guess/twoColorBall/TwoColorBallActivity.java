package com.small.small.goal.my.guess.twoColorBall;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.guess.LotteryExplainActivity;
import com.small.small.goal.my.guess.elevenchoosefive.entity.WinDaletouNumberEntity;
import com.small.small.goal.my.guess.note.activity.LotteryNoteActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.dialog.LotteryTopPopuwindow;
import com.small.small.goal.weight.MyGridView;
import com.small.small.goal.weight.Notice;
import com.small.small.goal.weight.NoticeMF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/27 16:24
 * 修改：
 * 描述： 天天大乐透
 **/
public class TwoColorBallActivity extends BaseActivity {

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
    @InjectView(R.id.ll_top_include)
    LinearLayout llTopInclude;
    @InjectView(R.id.img_banner)
    ImageView imgBanner;
    @InjectView(R.id.img_guangbo)
    ImageView imgGuangbo;
    @InjectView(R.id.marqueeView)
    MarqueeView marqueeView;
    @InjectView(R.id.tv_expect)
    TextView tvExpect;
    @InjectView(R.id.lv)
    ListView lv;
    @InjectView(R.id.tv_oldWin)
    TextView tvOldWin;
    @InjectView(R.id.mgv_red_dayPlay)
    MyGridView mgvRedDayPlay;
    @InjectView(R.id.mgv_blue_dayPlay)
    MyGridView mgvBlueDayPlay;
    @InjectView(R.id.tv_random_dayPlay)
    TextView tvRandomDayPlay;
    @InjectView(R.id.tv_touzhu_dayPlay)
    TextView tvTouzhuDayPlay;
    private OvalAdapter redAdapter;
    private OvalAdapter blueAdapter;

    private List<WinDaletouNumberEntity> data;
    private WinDaletouNumberEntity winDaletouNumberEntity;
    private LvAdapter lvAdapter;

    private int intent_flag;
    private int redMax;
    private int blueMax;
    private int redMin;
    private int blueMin;
    private LotteryTopPopuwindow popupWindow;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("mm:ss");

    private String expect = "2017089";
    private String openCode;
    private String openTime;
    private String openTimestamp;
    private String expireTime;
    private String code;
    private String now;

    private List<Notice> noticeList;
    private MarqueeFactory<TextView, Notice> marqueeFactory;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_two_color_ball);
        ButterKnife.inject(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        initData();
    }

    @Override
    public void initData() {
        super.initData();

        noticeList = new ArrayList<>();

        marqueeFactory = new NoticeMF(this, 0);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.setInterval(5000);
        marqueeView.startFlipping();


        rightImageInclude.setImageResource(R.mipmap.icon_lottery_menu);
        nameTextInclude.setText("双色球");
        imgBanner.setImageResource(R.mipmap.banner_twocolor);
        redMax = 33;
        blueMax = 16;
        redMin = 6;
        blueMin = 1;
        tvTouzhuDayPlay.setText("至少选择" + redMin + "个红球+" + blueMin + "个蓝球");

        initMgv();
        initOldwinLv();
        popupWindow = new LotteryTopPopuwindow(this);

        tvTouzhuDayPlay.setOnClickListener(this);
        tvTouzhuDayPlay.setClickable(false);
        tvRandomDayPlay.setOnClickListener(this);
        rightImageInclude.setOnClickListener(this);
        tvOldWin.setOnClickListener(this);
        popupWindow.setListener(new LotteryTopPopuwindow.setOnclickListener() {
            @Override
            public void onGuizeClick() {
                Intent intent = new Intent(TwoColorBallActivity.this, LotteryExplainActivity.class);
                intent.putExtra("url", Url.UrlLottery + "ssq");
                startActivity(intent);
            }

            @Override
            public void onJiluClick() {
                startActivity(new Intent(TwoColorBallActivity.this, LotteryNoteActivity.class));
                popupWindow.dismiss();
            }

            @Override
            public void onFollowClick() {

            }

            @Override
            public void onShareClick() {

            }
        });
    }

    /**
     * 初始化历史开奖的lv
     * create  wjz
     **/
    private void initOldwinLv() {
        List<Integer> reds = new ArrayList<>();
        List<Integer> blues = new ArrayList<>();

        data = new ArrayList<>();
//        lvAdapter = new LvAdapter(data, this);
//        for (int i = 17066; i < 17071; i++) {
//            int[] redArray = PlayAddMoneyActivity.randomJ(1, redMax, redMin);
//            int[] blueArray = PlayAddMoneyActivity.randomJ(1, blueMax, blueMin);
//            reds.clear();
//            blues.clear();
//
//            for (int y = 0; y < redArray.length; y++) {
//                reds.add(redArray[y]);
//            }
//            for (int z = 0; z < blueArray.length; z++) {
//                blues.add(blueArray[z]);
//            }
//
//            data.add(new WinDaletouNumberEntity(i + "", reds, blues));
//        }
//        lv.setAdapter(lvAdapter);
//        Utils.setListViewHeightBasedOnChildren(lv);
    }

    /**
     * 初始化红球和篮球的选择gridview
     * create  wjz
     **/
    private void initMgv() {
        List<OvalEntity> redData = new ArrayList<>();
        List<OvalEntity> blueData = new ArrayList<>();
        for (int i = 1; i <= redMax; i++) {
            if (i < 10)
                redData.add(new OvalEntity(0, "0" + i, false));
            else
                redData.add(new OvalEntity(0, i + "", false));
        }
        for (int i = 1; i <= blueMax; i++) {
            if (i < 10)
                blueData.add(new OvalEntity(1, "0" + i, false));
            else
                blueData.add(new OvalEntity(1, i + "", false));
        }

        redAdapter = new OvalAdapter(this, intent_flag);
        redAdapter.setData(redData);
        mgvRedDayPlay.setAdapter(redAdapter);

        blueAdapter = new OvalAdapter(this, intent_flag);
        blueAdapter.setData(blueData);
        mgvBlueDayPlay.setAdapter(blueAdapter);

        getNewsResult();
        getNotice();
    }

    /**
     * 获取最新开奖
     * create  wjz
     **/
    private void getNewsResult() {
        RequestParams requestParams = new RequestParams(Url.Url + "/lottery");
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("code", "ssq");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("===========双色球上一期=========" + result);
                try {
                    if (new JSONObject(result).getString("code").equals("0")) {
                        expect = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("expect");
                        openCode = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("openCode");
                        openTime = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("openTime");
                        openTimestamp = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("openTimestamp");
                        expireTime = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("expireTime");
                        code = new JSONObject(result).getJSONArray("result").getJSONObject(0).getString("code");
                        now = new JSONObject(result).optString("now");

                        tvExpect.setText((Long.valueOf(expect) + 1) + "");


                    } else {
                        Utils.showToast(TwoColorBallActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("===========双色球上一期====ex=====" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 公告
     * create  wjz
     **/
    private void getNotice() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.NoticeList);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("type", "3");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("===========公告=========" + result);
                try {
                    if (new JSONObject(result).getString("code").equals("0")) {

                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            noticeList.add(new Notice(jsonArray.getJSONObject(i).getString("content"), 0));
                        }
                        marqueeFactory.setData(noticeList);

                    } else {
                        Utils.showToast(TwoColorBallActivity.this, new JSONObject(result).getString("msg"));
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_touzhu_dayPlay:    //跳转到下一页面
                Intent intent = new Intent(this, PlayAddMoneyActivity.class);
                intent.putExtra(KeyCode.INTENT_FLAG, intent_flag);
                startActivity(intent);

                Map<String, List<OvalEntity>> map = new HashMap<>();
                List<OvalEntity> selectedShuangseqiuRedData = new ArrayList<>();
                List<OvalEntity> selectedShuangseqiuBlueData = new ArrayList<>();
                selectedShuangseqiuRedData.addAll(CacheUtil.getInstance().getSelectedShuangseqiuRedData());
                selectedShuangseqiuBlueData.addAll(CacheUtil.getInstance().getSelectedShuangseqiuBlueData());
                map.put(KeyCode.MAP_RED, selectedShuangseqiuRedData);
                map.put(KeyCode.MAP_BLUE, selectedShuangseqiuBlueData);
                CacheUtil.getInstance().addSelectedShuangseqiuData(map);

                finish();

                break;
            case R.id.tv_random_dayPlay:             //随机一注
                int[] reds = PlayAddMoneyActivity.randomJ(1, redMax, redMin);
                int[] blues = PlayAddMoneyActivity.randomJ(1, blueMax, blueMin);

                redAdapter.releaseData();
                blueAdapter.releaseData();
                List<OvalEntity> selectedRed = new ArrayList<>();
                List<OvalEntity> selectedBlue = new ArrayList<>();

                for (int i = 0; i < reds.length; i++) {
                    redAdapter.getData().get(reds[i] - 1).setSelected(true);
                    selectedRed.add(new OvalEntity(0, reds[i] >= 10 ? reds[i] + "" : "0" + reds[i], true));
                }
                for (int i = 0; i < blues.length; i++) {
                    blueAdapter.getData().get(blues[i] - 1).setSelected(true);
                    selectedBlue.add(new OvalEntity(0, blues[i] >= 10 ? blues[i] + "" : "0" + blues[i], true));
                }
                redAdapter.notifyDataSetChanged();
                blueAdapter.notifyDataSetChanged();
                CacheUtil.getInstance().setSelectedShuangseqiuRedData(selectedRed);
                CacheUtil.getInstance().setSelectedShuangseqiuBlueData(selectedBlue);

                tvTouzhuDayPlay.setText("您已选择1注");
                tvTouzhuDayPlay.setClickable(true);

                break;
            case R.id.tv_oldWin:
                if (lv.getVisibility() == View.VISIBLE) {
                    tvOldWin.setText("点击查看历史开奖");
                    lv.setVisibility(View.GONE);
                    setDrawableImg(R.mipmap.down);

                } else {
                    if (data.size() > 0) {
                        lv.setVisibility(View.VISIBLE);
                        tvOldWin.setText("点击收起");
                        setDrawableImg(R.mipmap.up);
                    } else
                        GetHistory();

                }
                break;
            case R.id.right_image_include:
                popupWindow.showAsDropDown(rightImageInclude, 0, -Utils.dip2px(this, 20));
                break;
        }
    }

    private void setDrawableImg(int mipRes) {
        Drawable drawable = getResources().getDrawable(mipRes);
/// 这一步必须要做,否则不会显示.
        drawable.setBounds(0, 0, Utils.dip2px(this, 12), Utils.dip2px(this, 5));
        tvOldWin.setCompoundDrawables(null, null, drawable, null);
    }

    private void GetHistory() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.LotteryNewest);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("code", "ssq");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("===============" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        data.clear();
                        JSONArray array = new JSONObject(result).getJSONArray("result");
                        for (int i = 0; i < array.length(); i++) {

//                            array.getJSONObject(i).getString("expect");
                            String openCode = array.getJSONObject(i).getString("openCode");
                            List<Integer> blues = new ArrayList<>();
                            List<Integer> reds = new ArrayList<>();
                            blues.add(Integer.valueOf(openCode.substring(openCode.indexOf("+") + 1, openCode.length())));
                            List<String> stringList = new ArrayList<String>();
                            String red = openCode.substring(0, openCode.indexOf("+"));
                            String redString = "";
                            red.substring(0, 2);
                            red.substring(3, 5);
                            red.substring(6, 8);
                            red.substring(9, 11);
                            red.substring(12, 14);
                            red.substring(15, red.length());
                            reds.add(Integer.valueOf(red.substring(0, 2)));
                            reds.add(Integer.valueOf(red.substring(3, 5)));
                            reds.add(Integer.valueOf(red.substring(6, 8)));
                            reds.add(Integer.valueOf(red.substring(9, 11)));
                            reds.add(Integer.valueOf(red.substring(12, 14)));
                            reds.add(Integer.valueOf(red.substring(15, red.length())));
                            for (int j = 0; j < reds.size(); j++) {
                                System.out.println("========red========" + reds.get(j));
                            }
                            System.out.println("========blues========" + blues.get(0));

                            winDaletouNumberEntity = new WinDaletouNumberEntity(array.getJSONObject(i).getString("expect"), reds, blues);
                            data.add(winDaletouNumberEntity);
                        }

                        lvAdapter = new LvAdapter(data, TwoColorBallActivity.this);
                        lv.setAdapter(lvAdapter);
                        Utils.setListViewHeightBasedOnChildren(lv);


                        lv.setVisibility(View.VISIBLE);
                        tvOldWin.setText("点击收起");
                        setDrawableImg(R.mipmap.up);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("======ex=========" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        if (redAdapter != null)
            redAdapter.releaseData();
        if (blueAdapter != null)
            blueAdapter.releaseData();
        tvTouzhuDayPlay.setClickable(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 页面里面的开奖记录的listview 的适配器
     * create  wjz
     **/
    public static class LvAdapter extends BaseAdapter {

        private final List<WinDaletouNumberEntity> data;
        private final Context context;

        public LvAdapter(List<WinDaletouNumberEntity> data, Context context) {
            this.data = data;
            this.context = context;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return this.data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder vh;

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_win_nums, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            WinDaletouNumberEntity info = data.get(position);
            List<Integer> red = info.getReds();
            List<Integer> blue = info.getBlues();

            vh.tvQiNumsItem.setText(info.getQiNums() + "期");


            String str = "";
            for (Integer i : red) {
                if (i >= 10)
                    str += i + " ";
                else
                    str += "0" + i + " ";
            }
            for (int i = 0; i < blue.size(); i++) {
                Integer y = blue.get(i);
                if (y >= 10)
                    str += y;
                else
                    str += "0" + y;
                if (i == blue.size() - 1) {

                } else {
                    str += " ";
                }
            }
            SpannableStringBuilder builder = new SpannableStringBuilder(str);
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            ForegroundColorSpan blueSpan = new ForegroundColorSpan(context.getResources().getColor(R.color.blue));
            builder.setSpan(redSpan, 0, red.size() * 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(blueSpan, red.size() * 3, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            vh.tvSelectedItem.setText(builder);

            return convertView;
        }

        class ViewHolder {
            @InjectView(R.id.tv_qiNums_item)
            TextView tvQiNumsItem;
            @InjectView(R.id.tv_selected_item)
            TextView tvSelectedItem;

            ViewHolder(View view) {
                ButterKnife.inject(this, view);
            }
        }
    }
}
