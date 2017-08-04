package com.small.small.goal.my.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.dialog.DuihuanQuerenDialog;
import com.small.small.goal.my.mall.adapter.QbAddAdapter;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.dialog.DialogClickListener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/7 17:24
 * 修改：
 * 描述：Q币充值
 **/
public class RechargeQbActivity extends BaseActivity {


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
    @InjectView(R.id.etv_phone)
    EditText etvPhone;
    @InjectView(R.id.ll_top)
    LinearLayout llTop;
    @InjectView(R.id.gv)
    GridView gv;
    @InjectView(R.id.tv_duihuan_ok)
    TextView tvDuihuanOk;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;
    @InjectView(R.id.tv_myMoney)
    TextView tvMyMoney;
    private DuihuanQuerenDialog duihuanQuerenDialog;
    private int clickPosition = -1;
    private int oldlickPosition = -2;
    private QbAddAdapter qbAdapter;
    private QbEntity selectEntity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_recharge_qb);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        //  lls = new LinearLayout[]{ll1, ll2, ll3, ll4};
        duihuanQuerenDialog = new DuihuanQuerenDialog(this, "2000");
        nameTextInclude.setText("Q币充值");
        rightImageInclude.setVisibility(View.GONE);
        tvOkInclude.setText("充值记录");
        tvOkInclude.setVisibility(View.VISIBLE);
        llTopInclude.setBackgroundResource(R.color.bg_include);

        qbAdapter = new QbAddAdapter(this);
        gv.setAdapter(qbAdapter);

        tvDuihuanOk.setOnClickListener(this);
        tvOkInclude.setOnClickListener(this);
        leftImageInclude.setOnClickListener(this);

        duihuanQuerenDialog.setOnClick(new DialogClickListener() {
            @Override
            public void onClick(String str) {

                recordQb();

            }
        });
        qbAdapter.setOnLLClickListener(new QbAddAdapter.OnLLClickListener() {
            @Override
            public void onClick(QbEntity entity) {
                RechargeQbActivity.this.selectEntity = entity;
                if (entity.isType()) {
                    llBottom.setVisibility(View.VISIBLE);
                } else {
                    llBottom.setVisibility(View.GONE);
                }

                duihuanQuerenDialog.setMoney((entity.getPrice() * 120) + "");
            }
        });
    }

    @Override
    public void getData() {
        super.getData();
        RequestParams requestParams = new RequestParams(Url.Url + "/charge/qqproducts");
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                ArrayList<QbEntity> qbEntities = Utils.fromJsonList(Utils.getResultStr(result), QbEntity.class);

                for (int i = qbEntities.size() - 1; i >= 0; i--) {

                    if (!qbEntities.get(i).getName().startsWith("Q币"))
                        qbEntities.remove(i);
                }

                qbAdapter.setData(qbEntities);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });

    }

    private void recordQb() {
        RequestParams requestParams = new RequestParams(Url.Url + "/charge/qq");
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("qq", etvPhone.getText().toString());
        requestParams.addBodyParameter("proId", selectEntity.getProId() + "");
        requestParams.addBodyParameter("num", "1");

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("============充值QQ=========" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        CacheUtil.getInstance().getUserInfo().getAccount().setBean(String.valueOf(Integer.valueOf(CacheUtil.getInstance().getUserInfo().getAccount().getBean()) - selectEntity.getPrice() * 120));
                        tvMyMoney.setText("金豆金额：" + CacheUtil.getInstance().getUserInfo().getAccount().getBean());
                        showMyToast();
                    } else {
                        Utils.showToast(RechargeQbActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("============充值QQ======ex===" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 显示兑换成功的toast
     * create  wjz
     **/
    private void showMyToast() {
        Toast toast = Toast.makeText(this,
                "兑换成功", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);

        LinearLayout toastView = (LinearLayout) toast.getView();
        TextView tv = (TextView) toastView.getChildAt(0);
        tv.setTextSize(Utils.dip2px(this, 8));
        ImageView imageCodeProject = new ImageView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(Utils.dip2px(this, 40), Utils.dip2px(this, 10), Utils.dip2px(this, 40), Utils.dip2px(this, 10));
        imageCodeProject.setLayoutParams(layoutParams);
        imageCodeProject.setImageResource(R.mipmap.icon_success_green);
        toastView.addView(imageCodeProject, 0);
        toast.show();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_duihuan_ok:
                if (!etvPhone.getText().toString().equals("")) {
                    duihuanQuerenDialog.show();
                } else {
                    Utils.showToast(RechargeQbActivity.this, "请填写正确的qq号");
                }
                break;
            case R.id.tv_ok_include:
                startActivity(new Intent(this, QbRecordActivity.class));
                break;
            case R.id.left_image_include:
                finish();
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        tvMyMoney.setText("金豆金额：" + CacheUtil.getInstance().getUserInfo().getAccount().getBean());
    }

//
//    private void setChildViewColor() {
//        for (int i = 0; i < lls.length; i++) {
//            LinearLayout ll = lls[i];
//            ((TextView) ((LinearLayout) ll.getChildAt(0)).getChildAt(0)).setTextColor(getResources().getColor(R.color.text_white_hui));
//            ((TextView) ((LinearLayout) ll.getChildAt(0)).getChildAt(1)).setTextColor(getResources().getColor(R.color.white));
//            ll.setBackgroundResource(R.drawable.bg_oval_gray_null_5);
//        }
//        if (oldlickPosition != clickPosition) {
//            LinearLayout ll = lls[clickPosition];
//            ((TextView) ((LinearLayout) ll.getChildAt(0)).getChildAt(0)).setTextColor(getResources().getColor(R.color.white));
//            ((TextView) ((LinearLayout) ll.getChildAt(0)).getChildAt(1)).setTextColor(getResources().getColor(R.color.text_white_hui));
//            ll.setBackgroundResource(R.drawable.bg_oval_red_5);
//            oldlickPosition = clickPosition;
//            llBottom.setVisibility(View.VISIBLE);
//        } else {
//            oldlickPosition = -2;
//            llBottom.setVisibility(View.GONE);
//        }
//        duihuanQuerenDialog.setMoney(GameUtils.getNUmberForString(((TextView) ((LinearLayout) lls[clickPosition].getChildAt(0)).getChildAt(1)).getText().toString()));
//    }

    public class QbEntity {


        /**
         * proId : 50001
         * name : Q币1元直充
         * price : 1
         * unit : 元
         * max : 500
         */

        private int proId;
        private String name;
        private int price;
        private String unit;
        private int max;
        private boolean type = false;

        public QbEntity(int proId, String name, int price, String unit, int max, boolean type) {
            this.proId = proId;
            this.name = name;
            this.price = price;
            this.unit = unit;
            this.max = max;
            this.type = type;
        }

        public QbEntity(int proId, String name, int price, String unit, int max) {
            this.proId = proId;
            this.name = name;
            this.price = price;
            this.unit = unit;
            this.max = max;
        }

        public int getProId() {
            return proId;
        }

        public void setProId(int proId) {
            this.proId = proId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public String getUnit() {
            return unit;
        }

        public void setUnit(String unit) {
            this.unit = unit;
        }

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public boolean isType() {
            return type;
        }

        public void setType(boolean type) {
            this.type = type;
        }
    }


}
