package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.dialog.MonthDialog;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.weight.CurveMuchChartView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DistributionActivity extends BaseActivity implements MonthDialog.OnDialogOkListener {

    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.right_text)
    TextView rightText;
    @InjectView(R.id.curve_view)
    CurveMuchChartView curveView;

    @InjectView(R.id.time_image)
    ImageView timeImage;

    MonthDialog dialog;

    private String chooseTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_distribution);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        dialog = new MonthDialog(this, this);

        initCurve();
    }

    @OnClick({R.id.left_image, R.id.right_text, R.id.time_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.right_text:
                Intent intent = new Intent(DistributionActivity.this, DistributionMemberActivity.class);
                startActivity(intent);
                break;
            case R.id.time_image:
                dialog.show();
                break;
        }
    }




    private void initCurve() {


        List<List<Float>> curveList = new ArrayList<>();
        List<Float> dataList = new ArrayList<>();      //线条的每个点的方位（y）;
        List<Float> dataList2 = new ArrayList<>();
        List<Float> dataList3 = new ArrayList<>();

        List<Integer> colors = new ArrayList<>();
        List<String> dataX = new ArrayList<>();
        List<Float> dataY = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            dataY.add((float) i);
        }
        for (int i = 0; i < 5; i++) {
            dataX.add(i + "");
        }
        dataList.add((float) 1.2);
        dataList.add((float) 5.2);
        dataList.add((float) 3.2);
        dataList.add((float) 2);
        dataList.add((float) 4);
        dataList.add((float) 0.1);

        dataList2.add((float) 3.2);
        dataList2.add((float) 2.2);
        dataList2.add((float) 5.2);
        dataList2.add((float) 2.8);
        dataList2.add((float) 3.6);
        dataList2.add((float) 2.1);

        dataList3.add((float) 5.2);
        dataList3.add((float) 8.2);
        dataList3.add((float) 4.2);
        dataList3.add((float) 1.5);
        dataList3.add((float) 3);
        dataList3.add((float) 1);

        curveList.add(dataList);
        curveList.add(dataList2);
        curveList.add(dataList3);


        colors.add(Color.parseColor("#906EE1A6"));
        colors.add(Color.parseColor("#90FFDA64"));
        colors.add(Color.parseColor("#9005D5A3"));


        curveView.setData(curveList);               //传入线条的数据
        curveView.setColors(colors);           //线条的颜色   需要和线条的数量对应
        curveView.setDateX(dataX);             //X 轴坐标的集合
        curveView.setDateY(dataY);             //Y 轴坐标的集合
    }

    @Override
    public void getSelectTime(String time) {
        chooseTime = time;
    }

}
