package com.small.small.goal.my.mall.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.dialog.DuihuanQuerenDialog;
import com.small.small.goal.my.dialog.GoSetAddressDialog;
import com.small.small.goal.my.mall.entity.LuckEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Code;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.utils.dialog.DialogClickListener;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/11 11:06
 * 修改：
 * 描述：商品的详情
 **/
public class ShopingMoreActivity extends BaseActivity {


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
    @InjectView(R.id.img_top_big)
    ImageView imgTopBig;
    @InjectView(R.id.tv_shopName)
    TextView tvShopName;
    @InjectView(R.id.tv_jindoujia)
    TextView tvJindoujia;
    @InjectView(R.id.tv_money)
    TextView tvMoney;
    @InjectView(R.id.tv_surplus)
    TextView tvSurplus;
    @InjectView(R.id.tv_content)
    TextView tvContent;
    @InjectView(R.id.tv_myMoney)
    TextView tvMyMoney;
    @InjectView(R.id.tv_duihuan_ok)
    TextView tvDuihuanOk;
    @InjectView(R.id.ll_bottom)
    LinearLayout llBottom;
    private DuihuanQuerenDialog duihuanQuerenDialog;
    private GoSetAddressDialog gosetDialog;
    private LuckEntity luckEntity;

    ImageOptions imageOptions;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_shopping_more);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_CENTER)
                .setLoadingDrawableId(R.mipmap.background_load)
                .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
                .setFailureDrawableId(R.mipmap.background_fail)
                .build();


        init();
    }

    public void init() {
        nameTextInclude.setText("详情");
//        rightImageInclude.setImageResource(R.mipmap.icon_menu);
        rightImageInclude.setVisibility(View.GONE);
        luckEntity = (LuckEntity) getIntent().getSerializableExtra(KeyCode.INTENT_FLAG);
        duihuanQuerenDialog = new DuihuanQuerenDialog(this, luckEntity.getPrice() + "");
        gosetDialog = new GoSetAddressDialog(this, "");
        tvMyMoney.setText("金豆金额：" + CacheUtil.getInstance().getUserInfo().getAccount().getBean());
        setData();
    }

    private void setData() {

        if (luckEntity == null) return;

        if (luckEntity.getImg()!=null){
            x.image().bind(imgTopBig,Utils.GetPhotoPath(luckEntity.getImg()),imageOptions);
        }

//        Picasso.with(this).load(Utils.GetPhotoPath(luckEntity.getImg())).into(imgTopBig);
        tvContent.setText(luckEntity.getContent());
        tvShopName.setText(luckEntity.getName());
        tvMoney.setText(luckEntity.getPrice() + "");
        tvSurplus.setText("剩余：" + luckEntity.getStores() + "个");

        tvDuihuanOk.setOnClickListener(this);
        leftImageInclude.setOnClickListener(this);
        duihuanQuerenDialog.setOnClick(new DialogClickListener() {
            @Override
            public void onClick(String str) {

                duihuanShop();

            }
        });
        gosetDialog.setOnTvOKClickListener(new DialogClickListener() {
            @Override
            public void onClick(String str) {
                startActivity(new Intent(ShopingMoreActivity.this, AddAddressAcivity.class));
                gosetDialog.dismiss();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    /**
     * 兑换商品
     * create  wjz
     **/
    private void duihuanShop() {

        if (luckEntity == null) return;
        RequestParams requestParams = new RequestParams(Url.Url + "/buy");
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("goodsId", luckEntity.getId() + "");

//        x.http().post(requestParams, new Callback.CommonCallback<String>() {
//            @Override
//            public void onSuccess(String result) {
//                System.out.println("=========兑换=========" + result);
//
//                if (!Utils.callOk(result,ShopingMoreActivity.this)) return;
//
//                CacheUtil.getInstance().getUserInfo().getAccount().setBean(String.valueOf((int) (Double.valueOf(CacheUtil.getInstance().getUserInfo().getAccount().getBean()) - luckEntity.getPrice())));
//                tvMyMoney.setText("金豆金额：" + CacheUtil.getInstance().getUserInfo().getAccount().getBean());
//                View toastView = LayoutInflater.from(ShopingMoreActivity.this).inflate(R.layout.dialog_duihuan_success, null);
//                TextView tv_myMoney = (TextView) toastView.findViewById(R.id.tv_my_money);
//                tv_myMoney.setText(CacheUtil.getInstance().getUserInfo().getAccount().getBean() + "");
//
//                Toast toast = new Toast(ShopingMoreActivity.this);
//                toast.setGravity(Gravity.CENTER, 0, 0);
//                toast.setDuration(Toast.LENGTH_SHORT);
//                toast.setView(toastView);
//                toast.show();
//            }
//
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                System.out.println("=========兑换===ex======" + ex.getMessage());
//            }
//
//            @Override
//            public void onCancelled(CancelledException cex) {
//
//            }
//
//            @Override
//            public void onFinished() {
//
//            }
//        });
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("=========兑换=========" + result);

                if (!Utils.callOk(result, ShopingMoreActivity.this)) return;

                CacheUtil.getInstance().getUserInfo().getAccount().setBean(String.valueOf((int) (Double.valueOf(CacheUtil.getInstance().getUserInfo().getAccount().getBean()) - luckEntity.getPrice())));
                tvMyMoney.setText("金豆金额：" + CacheUtil.getInstance().getUserInfo().getAccount().getBean());
                View toastView = LayoutInflater.from(ShopingMoreActivity.this).inflate(R.layout.dialog_duihuan_success, null);
                TextView tv_myMoney = (TextView) toastView.findViewById(R.id.tv_my_money);
                tv_myMoney.setText(CacheUtil.getInstance().getUserInfo().getAccount().getBean() + "");

                luckEntity.setStores(luckEntity.getStores() - 1);
                tvSurplus.setText("剩余：" + (luckEntity.getStores()) + "个");

                Toast toast = new Toast(ShopingMoreActivity.this);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.setDuration(Toast.LENGTH_SHORT);
                toast.setView(toastView);
                toast.show();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=========兑换===ex======" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_duihuan_ok:

                getDefultAddress();
                break;
            case R.id.left_image_include:
                Intent intent = new Intent();
                intent.putExtra("number", luckEntity.getStores() + "");
                setResult(Code.COMMODITY, intent);
                finish();
                break;
        }
    }

    private void getDefultAddress() {

        RequestParams requestParams = new RequestParams(Url.Url + Url.Address);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=======地址==========" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        duihuanQuerenDialog.show();
                    } else if (code.equals("100000")) {
                        gosetDialog.show();
                    } else {
                        Utils.showToast(ShopingMoreActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=======地址=====ex=====" + ex.getMessage());
            }

            @Override
            public void onFinished() {

            }
        });

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) { //按下的如果是BACK，同时没有重复
            Intent intent = new Intent();
            intent.putExtra("number", luckEntity.getStores() + "");
            setResult(Code.COMMODITY, intent);
            finish();
            return true;
        }

        return super.onKeyDown(keyCode, event);

    }

}
