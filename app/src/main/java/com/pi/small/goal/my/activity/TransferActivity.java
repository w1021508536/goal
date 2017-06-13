package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.weight.BaseFundChartView;

import java.util.ArrayList;
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
        initBfcv();
        nameTextInclude.setText("期权转让");
        tvOkInclude.setVisibility(View.VISIBLE);
        tvOkInclude.setText("转让明细");
        rightImageInclude.setVisibility(View.GONE);
    }


    @Override
    public void initWeight() {
        super.initWeight();
        tvTransferTransfer.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_transfer_transfer:
                startActivity(new Intent(this, TransferNextActivity.class));
                break;
            case R.id.tv_goZhuanqu_transfer:
                break;
        }
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
