package com.small.small.goal.my.mall.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.gift.GiftListActivity;
import com.small.small.goal.my.mall.adapter.LuckAdapter;
import com.small.small.goal.my.mall.entity.LuckEntity;
import com.small.small.goal.my.transfer.activity.TransferActivity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.mzbanner.MZBannerView;
import com.small.small.goal.utils.mzbanner.holder.MZHolderCreator;
import com.small.small.goal.utils.mzbanner.holder.MZViewHolder;
import com.small.small.goal.weight.GridViewWithHeaderAndFooter;
import com.small.small.goal.weight.Notice;
import com.small.small.goal.weight.NoticeMF;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MallActivity extends BaseActivity {

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
    @InjectView(R.id.gv)
    GridViewWithHeaderAndFooter gv;
    @InjectView(R.id.view_line)
    View viewLine;
    @InjectView(R.id.ll_top_include)
    LinearLayout llTopInclude;

    LinearLayout llNotice;
    MarqueeView marqueeView;
    private LuckAdapter adapter;
    private MZBannerView luck_banner;
    private int page = 1;
    private boolean addFlag = true;


    private int choosePosition = -1;

    List<LuckEntity> dataList;
    private List<Notice> noticeList;
    private MarqueeFactory<TextView, Notice> marqueeFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_mall);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);


        init();
    }

    private void init() {
        noticeList = new ArrayList<>();
        dataList = new ArrayList<>();

        nameTextInclude.setText("金豆商城");
        rightImageInclude.setVisibility(View.GONE);
        tvOkInclude.setVisibility(View.VISIBLE);
        tvOkInclude.setText("兑奖记录");
        setHeaderView();
//        getFooterData();
        adapter = new LuckAdapter(this);
        gv.setAdapter(adapter);
        getShoppingData();
        getNotice();
        llTopInclude.setBackgroundResource(R.mipmap.title_nav_bg_1);
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (adapter.getData().get(position).getId() == 0) return;
                choosePosition = position;
                Intent intent = new Intent(MallActivity.this, ShopingMoreActivity.class);
                intent.putExtra(KeyCode.INTENT_FLAG, adapter.getData().get(position));
                startActivityForResult(intent, Code.COMMODITY);
            }
        });

        leftImageInclude.setOnClickListener(this);
        tvOkInclude.setOnClickListener(this);
    }

    /**
     * 公告
     * create  wjz
     **/
    private void getNotice() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.NoticeList);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("type", "4");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {

                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            noticeList.add(new Notice(jsonArray.getJSONObject(i).getString("content"), 0));
                        }
                        llNotice.setVisibility(View.VISIBLE);
                        marqueeFactory.setData(noticeList);

                    } else {
                        if (!code.equals("100000"))
                            Utils.showToast(MallActivity.this, new JSONObject(result).getString("msg"));
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
     * 获取商品信息
     * create  wjz
     **/
    private void getShoppingData() {
        if (!addFlag) return;

        RequestParams requestParams = new RequestParams(Url.Url + "/goods");
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);

        requestParams.addBodyParameter("p", page + "");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                dataList = Utils.fromJsonList(Utils.getResultStr(result), LuckEntity.class);
                if (dataList.size() % 2 == 1) {
                    //            public LuckEntity(int id, String name, String img, int type, double price, int status, String content, int stores, int sales, int isNew, int isHot, String createTime, String updateTime) {
                    dataList.add(new LuckEntity(0, "", "", 0, 0, 0, "", 0, 0, 0, 0, "", ""));
                }
                if (page == 1) {
                    adapter.setData(dataList);
                } else {
                    adapter.addData(dataList);
                }
                if (dataList.size() > 0) {
                    addFlag = true;
                } else {
                    addFlag = false;
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

    private void setHeaderView() {
        View header = LayoutInflater.from(this).inflate(R.layout.view_grid_header, null);
        RelativeLayout rl_phone = (RelativeLayout) header.findViewById(R.id.rl_phone);
        RelativeLayout rl_qb = (RelativeLayout) header.findViewById(R.id.rl_qb);
        llNotice = (LinearLayout) header.findViewById(R.id.ll_notice);
        marqueeView = (MarqueeView) header.findViewById(R.id.marqueeView);
        gv.addHeaderView(header);
        rl_phone.setOnClickListener(this);
        rl_qb.setOnClickListener(this);
        marqueeFactory = new NoticeMF(this, 0);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();
        luck_banner = (MZBannerView) header.findViewById(R.id.banner_top);
        List<Integer> images = new ArrayList<>();
        images.add(R.mipmap.banner_lottery_draw);
        luck_banner.setmIndicatorsBottom(Utils.dip2px(this, 10));
        luck_banner.setFocusable(true);
        luck_banner.setPages(images, new MZHolderCreator() {
            @Override
            public MZViewHolder createViewHolder() {
                return new BannerViewHolder();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.tv_more_footer:
                page++;
                getShoppingData();
                break;
            case R.id.rl_phone:
                startActivity(new Intent(this, RechargePhoneActivity.class));
                break;
            case R.id.rl_qb:
                startActivity(new Intent(this, RechargeQbActivity.class));
                break;
            case R.id.tv_ok_include:
                startActivity(new Intent(this, GiftListActivity.class));
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (luck_banner != null) {
            luck_banner.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (luck_banner != null) {
            luck_banner.pause();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            if (requestCode == Code.COMMODITY) {
                String luckEntity = data.getExtras().getString("number", "0");
                dataList.get(choosePosition).setStores(Integer.valueOf(luckEntity));
                adapter.setData(dataList);
            }
        }
    }

    public static class BannerViewHolder implements MZViewHolder<Integer> {
        private ImageView mImageView;

        @Override
        public View createView(Context context) {
            // 返回页面布局
            View view = LayoutInflater.from(context).inflate(R.layout.item_banner, null);
            mImageView = (ImageView) view.findViewById(R.id.img_banner);
            return view;
        }

        @Override
        public void onBind(Context context, int position, Integer data) {
            // 数据绑定
            mImageView.setImageResource(data);
        }
    }
}
