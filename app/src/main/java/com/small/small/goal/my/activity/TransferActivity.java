package com.small.small.goal.my.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.small.small.goal.R;
import com.small.small.goal.my.entry.NoticeEntity;
import com.small.small.goal.my.entry.TransferShapeEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.weight.BaseFundChartView;

import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/5/31 17:45
 * 修改：
 * 描述：期权转让
 **/
public class TransferActivity extends BaseActivity {


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
    @InjectView(R.id.ll_top)
    LinearLayout llTop;
    @InjectView(R.id.ll_notice)
    LinearLayout llNotice;
    @InjectView(R.id.bfcv)
    BaseFundChartView bfcv;
    @InjectView(R.id.tv_question)
    TextView tvQuestion;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;
    @InjectView(R.id.tv_goZhuanqu_transfer)
    TextView tvGoZhuanquTransfer;
    @InjectView(R.id.tv_transfer_transfer)
    TextView tvTransferTransfer;
    @InjectView(R.id.tv_mySurplus_transfer)
    TextView tvMySurplusTransfer;
    @InjectView(R.id.tv_scoll_transfer)
    TextView tvScollTransfer;
    @InjectView(R.id.tv_hint)
    TextView tvHint;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_transfer);
        ButterKnife.inject(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        Drawable drawable = getResources().getDrawable(R.mipmap.icon_question);
        drawable.setBounds(0, 0, Utils.dip2px(this, 10), Utils.dip2px(this, 10));
        tvQuestion.setCompoundDrawables(drawable, null, null, null);
        //   initBfcv();
        nameTextInclude.setText("期权转让");
        tvOkInclude.setVisibility(View.VISIBLE);
        tvOkInclude.setText("转让明细");
        rightImageInclude.setVisibility(View.GONE);
    }


    @Override
    public void initWeight() {
        super.initWeight();
        tvTransferTransfer.setOnClickListener(this);
        tvOkInclude.setOnClickListener(this);
        tvGoZhuanquTransfer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_transfer_transfer:
                startActivity(new Intent(this, TransferNextActivity.class));
                break;
            case R.id.tv_goZhuanqu_transfer:
                startActivity(new Intent(this, MontyToActivity.class));
                break;
            case R.id.tv_ok_include:
                startActivity(new Intent(this, TransferMoreActivity.class));
                break;
        }
    }

    @Override
    public void getData() {
        super.getData();

        getDiagram();
    }


    @Override
    protected void onResume() {
        super.onResume();

        double option = CacheUtil.getInstance().getUserInfo().getAccount().getOption();

        tvMySurplusTransfer.setText(option + "");
        if ((option + "").length() > 6) {
            int i = 600 / (option + "").length();

            tvMySurplusTransfer.setTextSize(i);

        }
    }

    /**
     * 获取公告
     * create  wjz
     **/
    private void getNotice() {

        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/notice/list");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!Utils.callOk(result,TransferActivity.this)) return;
                Gson gson = new Gson();
                List<NoticeEntity> data = gson.fromJson(Utils.getResultStr(result), new TypeToken<List<NoticeEntity>>() {
                }.getType());

                String noticeStr = "";
                for (NoticeEntity entity : data) {
                    noticeStr += entity.getTitle();
                }

                tvScollTransfer.setText(noticeStr);

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
     * 获取曲线图的
     * create  wjz
     **/
    private void getDiagram() {
        requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/option/price");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!Utils.callOk(result,TransferActivity.this)) return;
                //{"msg":"success","code":0,"result":[{"id":1,"createTime":1496302252000,"price":10.00},{"id":2,"createTime":1496388657000,"price":11.00},{"id":3,"createTime":1496475064000,"price":16.00},{"id":4,"createTime":1496561476000,"price":100.00},{"id":5,"createTime":1496647880000,"price":60.00},{"id":6,"createTime":1496734284000,"price":1.00},{"id":7,"createTime":1496820688000,"price":50.00}],"pageNum":1,"pageSize":9,"pageTotal":1,"total":7}

                Gson gson = new Gson();
                String arrStr = Utils.getResultStr(result);
                List<TransferShapeEntity> data = gson.fromJson(arrStr, new TypeToken<List<TransferShapeEntity>>() {
                }.getType());
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式

                for (TransferShapeEntity entity : data) {
                    String createTime = entity.getCreateTime();
                    long time = Long.valueOf(createTime);
                    entity.setCreateTime(df.format(new Date(time)));
                }
                setBfcv(data);
                getNotice();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void setBfcv(List<TransferShapeEntity> data) {
        if (data.size() == 0) return;
        List<List<Float>> lineData = new ArrayList<>();
        List<Float> oneLineData = new ArrayList<>();
        List<String> dataX = new ArrayList<>();
        List<Float> dataY = new ArrayList<>();
        List<Integer> colors = new ArrayList<>();
        double maxPrice = 0;
        for (TransferShapeEntity entity : data) {
            oneLineData.add((float) entity.getPrice());
            dataX.add(entity.getCreateTime());
            if (maxPrice < entity.getPrice()) {
                maxPrice = entity.getPrice();
            }
        }

        lineData.add(oneLineData);

        if (maxPrice % 10 > 0) {
            double v = maxPrice - maxPrice % 10 + 10;
        }
        double v =  (maxPrice / 10.0);
        for (double i = 0; i <= maxPrice; i += v) {
            dataY.add((float) i);
        }

        colors.add(R.color.colorAccent);
        bfcv.setColors(colors);           //线条的颜色   需要和线条的数量对应

        bfcv.setDateX(dataX);
        bfcv.setDateY(dataY);
        bfcv.setDrawText(oneLineData.get(oneLineData.size() - 1) + "");
        bfcv.setData(lineData);
    }

    private void initBfcv() {
        List<List<Float>> data = new ArrayList<>();
        List<Float> adta2 = new ArrayList<>();      //线条的每个点的方位（y）;

        List<Integer> colors = new ArrayList<>();
        List<String> dataX = new ArrayList<>();
        List<Float> dataY = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataX.add(i + "");
            dataY.add((float) i);
        }
        float number = new Random().nextInt(8) + 1;

        //   adta2.add(number);
        adta2.add((float) 1.2);
        adta2.add((float) 3);
        adta2.add((float) 1);
        adta2.add((float) 6.3);
        adta2.add((float) 4.2);
        adta2.add((float) 9.0);
        adta2.add((float) 5.0);
        adta2.add((float) 5.0);
        adta2.add((float) 5.0);
        adta2.add((float) 5.0);
        data.add(adta2);

        colors.add(R.color.colorAccent);
        bfcv.setData(data);               //传入线条的数据
        bfcv.setColors(colors);           //线条的颜色   需要和线条的数量对应
        bfcv.setDateX(dataX);             //X 轴坐标的集合
        bfcv.setDateY(dataY);             //Y 轴坐标的集合
    }
}
