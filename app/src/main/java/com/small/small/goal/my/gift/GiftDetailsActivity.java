package com.small.small.goal.my.gift;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GiftDetailsActivity extends BaseActivity {


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
    @InjectView(R.id.top)
    RelativeLayout top;
    @InjectView(R.id.image)
    ImageView image;
    @InjectView(R.id.money_text)
    TextView moneyText;
    @InjectView(R.id.name_text)
    TextView nameText;
    @InjectView(R.id.status_text)
    TextView statusText;
    @InjectView(R.id.time_text)
    TextView timeText;
    @InjectView(R.id.user_name_text)
    TextView userNameText;
    @InjectView(R.id.phone_text)
    TextView phoneText;
    @InjectView(R.id.address_text)
    TextView addressText;
    @InjectView(R.id.order_no_text)
    TextView orderNoText;
    @InjectView(R.id.pay_time_text)
    TextView payTimeText;
    @InjectView(R.id.deliver_time_text)
    TextView deliverTimeText;
    @InjectView(R.id.express_name_text)
    TextView expressNameText;
    @InjectView(R.id.goods_order_no_text)
    TextView goodsOrderNoText;
    @InjectView(R.id.next_text)
    TextView nextText;
    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.mipmap.icon_gold_bean_1)
            .setFailureDrawableId(R.mipmap.icon_gold_bean_2)
            .build();

    private GiftEmpty giftEmpty;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_gift_details);
        super.onCreate(savedInstanceState);
        ButterKnife.inject(this);

        giftEmpty = (GiftEmpty) getIntent().getSerializableExtra("giftEmpty");

        init();
    }

    private void init() {
        nameTextInclude.setText("详情");
        rightImageInclude.setVisibility(View.GONE);


        if (giftEmpty.getStatus().equals("0")) {
            statusText.setText("未支付");
            nextText.setVisibility(View.GONE);
        } else if (giftEmpty.getStatus().equals("1")) {
            statusText.setText("未发货");
            nextText.setVisibility(View.GONE);
        } else if (giftEmpty.getStatus().equals("2")) {
            statusText.setText("已发货");
            nextText.setVisibility(View.VISIBLE);
            nextText.setText("确认收货");
        } else if (giftEmpty.getStatus().equals("3")) {
            statusText.setText("已收货");
//            if (giftEmpty.getIsComment().equals("0")) {
//                nextText.setVisibility(View.VISIBLE);
//                nextText.setText("去晒图");
//            } else {
//            nextText.setVisibility(View.GONE);
//            }

            nextText.setText("已收货");

        }
        x.image().bind(image, Utils.GetPhotoPath(giftEmpty.getImg()), imageOptions);
        nameText.setText(giftEmpty.getGoodsName());
        moneyText.setText(giftEmpty.getGoodsPrice().substring(0, giftEmpty.getGoodsPrice().indexOf(".")));


        userNameText.setText(giftEmpty.getConsignee());
        phoneText.setText(Utils.GetPhone(giftEmpty.getMobile()));
        addressText.setText(giftEmpty.getProvince() + giftEmpty.getCity() + giftEmpty.getDistrict() + giftEmpty.getAddress());
        orderNoText.setText(giftEmpty.getOrderNo());
        goodsOrderNoText.setText(giftEmpty.getExpressNo());
        expressNameText.setText(giftEmpty.getExpressName());
        if (!giftEmpty.getPayTime().equals("")) {
            timeText.setText(simpleDateFormat.format(new Date(Long.valueOf(giftEmpty.getPayTime()))));
            payTimeText.setText(simpleDateFormat.format(new Date(Long.valueOf(giftEmpty.getPayTime()))));
        }

        if (!giftEmpty.getDeliverTime().equals("")) {
            deliverTimeText.setText(simpleDateFormat.format(new Date(Long.valueOf(giftEmpty.getDeliverTime()))));
        }

    }

    @OnClick({R.id.left_image_include, R.id.next_text})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.next_text:
                if (giftEmpty.getStatus().equals("3")) {
//                    if (giftEmpty.getIsComment().equals("0")) {
//                        Intent intent = new Intent(this, PutShareActivity.class);
//                        startActivity(intent);
//                    }
                } else if (giftEmpty.getStatus().equals("2")) {

                    ShouHuo();
                }
                break;
        }
    }

    private void ShouHuo() {


        RequestParams requestParams = new RequestParams(Url.Url + Url.OrderConfirm);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("orderId", giftEmpty.getOrderId());

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    if (new JSONObject(result).getString("code").equals("0")) {
                        Toast toast = Toast.makeText(GiftDetailsActivity.this,
                                "收货成功", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);

                        LinearLayout toastView = (LinearLayout) toast.getView();
                        TextView tv = (TextView) toastView.getChildAt(0);
                        tv.setTextSize(Utils.dip2px(GiftDetailsActivity.this, 8));
                        ImageView imageCodeProject = new ImageView(GiftDetailsActivity.this);
                        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                        layoutParams.setMargins(Utils.dip2px(GiftDetailsActivity.this, 40), Utils.dip2px(GiftDetailsActivity.this, 10), Utils.dip2px(GiftDetailsActivity.this, 40), Utils.dip2px(GiftDetailsActivity.this, 10));
                        imageCodeProject.setLayoutParams(layoutParams);
                        imageCodeProject.setImageResource(R.mipmap.icon_success_green);
                        toastView.addView(imageCodeProject, 0);
                        toast.show();

                        nextText.setVisibility(View.VISIBLE);
                        nextText.setText("去晒图");
                        statusText.setText("已收货");
                        giftEmpty.setStatus("3");
                    } else {
                        Utils.showToast(GiftDetailsActivity.this, new JSONObject(result).getString("msg"));
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
}
