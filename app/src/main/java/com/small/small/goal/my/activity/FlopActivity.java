package com.small.small.goal.my.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 17:56
 * 修改：
 * 描述： 翻牌
 **/
public class FlopActivity extends BaseActivity {

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
    @InjectView(R.id.img1_card_flop)
    ImageView img1CardFlop;
    @InjectView(R.id.tv1_card_item)
    TextView tv1CardItem;
    @InjectView(R.id.imageView1)
    ImageView imageView1;
    @InjectView(R.id.img2_card_flop)
    ImageView img2CardFlop;
    @InjectView(R.id.tv2_card_item)
    TextView tv2CardItem;
    @InjectView(R.id.imageView2)
    ImageView imageView2;
    @InjectView(R.id.img3_card_flop)
    ImageView img3CardFlop;
    @InjectView(R.id.tv3_card_item)
    TextView tv3CardItem;
    @InjectView(R.id.imageView3)
    ImageView imageView3;
    @InjectView(R.id.img4_card_flop)
    ImageView img4CardFlop;
    @InjectView(R.id.tv4_card_item)
    TextView tv4CardItem;
    @InjectView(R.id.imageView4)
    ImageView imageView4;
    @InjectView(R.id.img5_card_flop)
    ImageView img5CardFlop;
    @InjectView(R.id.tv5_card_item)
    TextView tv5CardItem;
    @InjectView(R.id.imageView5)
    ImageView imageView5;
    @InjectView(R.id.img6_card_flop)
    ImageView img6CardFlop;
    @InjectView(R.id.tv6_card_item)
    TextView tv6CardItem;
    @InjectView(R.id.imageView6)
    ImageView imageView6;
    @InjectView(R.id.rl1_card_flop)
    RelativeLayout rl1CardFlop;
    @InjectView(R.id.rl2_card_flop)
    RelativeLayout rl2CardFlop;
    @InjectView(R.id.rl3_card_flop)
    RelativeLayout rl3CardFlop;
    @InjectView(R.id.rl4_card_flop)
    RelativeLayout rl4CardFlop;
    @InjectView(R.id.rl5_card_flop)
    RelativeLayout rl5CardFlop;
    @InjectView(R.id.rl6_card_flop)
    RelativeLayout rl6CardFlop;
    @InjectView(R.id.tv_flowNums_flow)
    TextView tvFlowNumsFlow;
    private int exp = 0;
    private TextView[] textViews;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_flop);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("翻牌");
        rightImageInclude.setVisibility(View.GONE);
        tvOkInclude.setText("规则");
        tvOkInclude.setVisibility(View.VISIBLE);

        textViews = new TextView[]{tv1CardItem, tv2CardItem, tv3CardItem, tv4CardItem, tv5CardItem, tv6CardItem};
    }

    @Override
    public void initWeight() {
        super.initWeight();
        rl1CardFlop.setOnClickListener(this);
        rl2CardFlop.setOnClickListener(this);
        rl3CardFlop.setOnClickListener(this);
        rl4CardFlop.setOnClickListener(this);
        rl5CardFlop.setOnClickListener(this);
        rl6CardFlop.setOnClickListener(this);
    }

    @Override
    public void getData() {
        super.getData();
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/game/card");
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!RenameActivity.callOk(result)) return;

                try {
                    JSONObject jsob = new JSONObject(result);
                    exp = jsob.getInt("result");
                    for (int i = 0; i < textViews.length; i++) {
                        textViews[i].setText(exp + "点经验");
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
            case R.id.rl1_card_flop:
                if (tvFlowNumsFlow.getText().toString().equals("0") || imageView1.getVisibility() == View.INVISIBLE)
                    return;
                tvFlowNumsFlow.setText((Integer.valueOf(tvFlowNumsFlow.getText().toString()) - 1) + "");
                imageView1.setVisibility(View.GONE);
                flopDialog();
                break;
            case R.id.rl2_card_flop:
                if (tvFlowNumsFlow.getText().toString().equals("0") || imageView2.getVisibility() == View.INVISIBLE)
                    return;
                tvFlowNumsFlow.setText((Integer.valueOf(tvFlowNumsFlow.getText().toString()) - 1) + "");
                imageView2.setVisibility(View.GONE);
                flopDialog();
                break;
            case R.id.rl3_card_flop:
                if (tvFlowNumsFlow.getText().toString().equals("0") || imageView3.getVisibility() == View.INVISIBLE)
                    return;
                tvFlowNumsFlow.setText((Integer.valueOf(tvFlowNumsFlow.getText().toString()) - 1) + "");
                imageView3.setVisibility(View.GONE);
                flopDialog();
                break;
            case R.id.rl4_card_flop:
                if (tvFlowNumsFlow.getText().toString().equals("0") || imageView4.getVisibility() == View.INVISIBLE)
                    return;
                tvFlowNumsFlow.setText((Integer.valueOf(tvFlowNumsFlow.getText().toString()) - 1) + "");
                imageView4.setVisibility(View.GONE);
                flopDialog();
                break;
            case R.id.rl5_card_flop:
                if (tvFlowNumsFlow.getText().toString().equals("0") || imageView5.getVisibility() == View.INVISIBLE)
                    return;
                tvFlowNumsFlow.setText((Integer.valueOf(tvFlowNumsFlow.getText().toString()) - 1) + "");
                imageView5.setVisibility(View.GONE);
                flopDialog();
                break;
            case R.id.rl6_card_flop:
                if (tvFlowNumsFlow.getText().toString().equals("0") || imageView6.getVisibility() == View.INVISIBLE)
                    return;
                tvFlowNumsFlow.setText((Integer.valueOf(tvFlowNumsFlow.getText().toString()) - 1) + "");
                imageView6.setVisibility(View.GONE);
                flopDialog();
                break;
        }
    }

    /**
     * 显示翻牌成功的dialog
     * create  wjz
     **/
    private void flopDialog() {

        final AlertDialog deleteCacheDialog = new AlertDialog.Builder(this).create();
        deleteCacheDialog.show();
        deleteCacheDialog.setContentView(R.layout.dialog_flop);
        Window window = deleteCacheDialog.getWindow();
        //            window.setContentView(R.layout.dialog_bugger);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager wm = (WindowManager) this.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params.width = wm.getDefaultDisplay().getWidth();
        window.setAttributes(params);
        //      deleteCacheDialog.setCancelable(false);


        TextView tv_ok = (TextView) window.findViewById(R.id.tv_flowOK);
        TextView tv_exp = (TextView) window.findViewById(R.id.tv_exp);
        tv_exp.setText(exp + "");
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteCacheDialog.dismiss();
            }
        });

    }
}
