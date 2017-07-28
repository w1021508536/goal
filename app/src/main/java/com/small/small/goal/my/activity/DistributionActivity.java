package com.small.small.goal.my.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.dialog.MonthDialog;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.weight.CurveMuchChartView;

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

    @InjectView(R.id.img_empty)
    ImageView imgEmpty;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;
    @InjectView(R.id.commission_text)
    TextView commissionText;
    @InjectView(R.id.deposit_text)
    TextView depositText;
    @InjectView(R.id.yesterday_deposit)
    TextView yesterdayDeposit;
    @InjectView(R.id.option_text)
    TextView optionText;
    @InjectView(R.id.yesterday_option_text)
    TextView yesterdayOptionText;

    MonthDialog dialog;

    private String startTime = "";
    private String endTime = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_distribution);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        dialog = new MonthDialog(this, this);

        GetData();
//        initCurve();
    }

    @OnClick({R.id.left_image, R.id.right_text, R.id.time_image, R.id.null_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.right_text:

                GetCode();
                break;
            case R.id.time_image:
                dialog.show();
                break;
            case R.id.null_layout:

                break;
        }
    }

    //判断是否为代理商
    private void GetCode() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.Agent);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("uid", Utils.UserSharedPreferences(this).getString("id", ""));

        requestParams.addBodyParameter("p", "1");
        requestParams.addBodyParameter("r", "20");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Intent intent = new Intent(DistributionActivity.this, DistributionMemberActivity.class);
                        intent.putExtra("result", result);
                        startActivity(intent);
                    } else if (code.equals("1500001")) {
                        Utils.showToast(DistributionActivity.this, new JSONObject(result).getString("msg"));
                        startActivity(new Intent(DistributionActivity.this, ExtensionActivity.class));
                        finish();
                    } else if (code.equals("100000")) {
                        Intent intent = new Intent(DistributionActivity.this, DistributionMemberActivity.class);
                        intent.putExtra("result", result);
                        startActivity(intent);
                    } else {
                        Utils.showToast(DistributionActivity.this, new JSONObject(result).getString("msg"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("") && ex.getMessage() != null) {
                    Utils.showToast(DistributionActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onFinished() {
                return;

            }
        });
    }


    private void GetData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.AgentDistribution);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("startTime", startTime);
        requestParams.addBodyParameter("endTime", endTime);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        commissionText.setText(new JSONObject(result).getJSONObject("result").getString("commission"));
                        depositText.setText(new JSONObject(result).getJSONObject("result").getString("deposit"));
                        yesterdayDeposit.setText(new JSONObject(result).getJSONObject("result").getString("yesterdayDeposit"));
                        optionText.setText(new JSONObject(result).getJSONObject("result").getString("option"));
                        yesterdayOptionText.setText(new JSONObject(result).getJSONObject("result").getString("yesterdayOption"));
                    } else {
                        Utils.showToast(DistributionActivity.this, new JSONObject(result).getString("msg"));
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("") && ex.getMessage() != null) {
                    Utils.showToast(DistributionActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onFinished() {
                return;

            }
        });
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

        startTime = time.substring(0, time.length() - 9) + " 00:00:01";
        endTime = time.substring(0, time.length() - 9) + " 23:59:59";

        GetData();
    }


}
