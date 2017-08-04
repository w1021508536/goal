package com.small.small.goal.my.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.google.gson.Gson;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.dialog.ExtensionDialog;
import com.small.small.goal.my.entry.LastAgentEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
import com.small.small.goal.weight.Notice;
import com.small.small.goal.weight.NoticeMF;
import com.squareup.picasso.Picasso;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/14 16:10
 * 修改：
 * 描述：推广码
 **/
public class ExtensionActivity extends BaseActivity {


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
    @InjectView(R.id.marqueeView)
    MarqueeView marqueeView;
    @InjectView(R.id.img_icon)
    CircleImageView imgIcon;
    @InjectView(R.id.tv_userName)
    TextView tvUserName;
    @InjectView(R.id.tv_agent)
    TextView tvAgent;
    @InjectView(R.id.tv_moneyHint1)
    TextView tvMoneyHint1;
    @InjectView(R.id.tvMoney)
    TextView tvMoney;
    @InjectView(R.id.tv_moneyHint2)
    TextView tvMoneyHint2;
    @InjectView(R.id.tv_extension)
    TextView tvExtension;
    //    private ExtensionDialog dialog;
    private final List<Notice> datas = new ArrayList<>();
    private MarqueeFactory<TextView, Notice> marqueeFactory;
    private LastAgentEntity agent;

    PopupWindow popupWindow;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_extension);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
//        dialog = new ExtensionDialog(this);


        marqueeFactory = new NoticeMF(this,1);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();

    }

    @Override
    public void initWeight() {
        super.initWeight();
        tvExtension.setClickable(false);
//        dialog.setOnClickGoListener(new ExtensionDialog.onClickGoListener() {
//            @Override
//            public void onclick() {
//                Intent intent = new Intent(ExtensionActivity.this, ExtensionPayActivity.class);
//                intent.putExtra("money", "698");
//                startActivity(intent);
//                dialog.dismiss();
//            }
//        });
    }

    @Override
    public void getData() {

        super.getData();

        getLast();

    }

    /**
     * 获取最后一个的代理
     * create  wjz
     **/
    private void getLast() {
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/agent/latest");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (Utils.callOk(result, ExtensionActivity.this)) {
                    Gson gson = new Gson();
                    agent = gson.fromJson(Utils.getResultStr(result), LastAgentEntity.class);
                    datas.add(new Notice("恭喜用户" + agent.getNick() + "成为小目标第" + agent.getId() + "名代理商", agent.getNick().length()));
                    marqueeFactory.setData(datas);

                }
                getAgent();
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
     * 获取代理身份
     * create  wjz
     **/
    private void getAgent() {
        RequestParams requestParams = new RequestParams(Url.Url + "/agent");
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                try {
                    String code = new JSONObject(result).getString("code");
                    if ("0".equals(code)) {
                        if (!sp.getString(KeyCode.USER_AVATAR, "").equals("")) {
                            Picasso.with(ExtensionActivity.this).load(Utils.GetPhotoPath(sp.getString(KeyCode.USER_AVATAR, ""))).into(imgIcon);
                        } else {
                            imgIcon.setImageResource(R.mipmap.icon_head);
                        }

                        tvUserName.setText(sp.getString(KeyCode.USER_NICK, ""));

                        try {
                            JSONObject jsonObject = new JSONObject(result);
                            String id = jsonObject.getJSONObject("result").getString("id");
                            String level = jsonObject.getJSONObject("result").getString("level");
                            if (level.equals("1") || level.equals("2") || level.equals("3")) {
                                tvExtension.setClickable(true);
                                tvAgent.setBackgroundResource(R.drawable.bg_gray_extension);
                                tvAgent.setClickable(false);
                            } else {
                                tvExtension.setBackgroundResource(R.drawable.bg_gray_extension);
                            }
                            tvMoney.setText(id);
                            tvExtension.setBackgroundResource(R.drawable.selector_blue_extension);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
                        tvUserName.setText("代理商推广码");
                        imgIcon.setVisibility(View.GONE);
                        tvMoneyHint1.setText("已有");

                        int id = agent.getId();
                        if (id < 10000) {
                            tvMoneyHint2.setText("名用户加入我们");
                            tvMoney.setText(agent.getId() + "");
                        } else {
                            double v = id / 10000.0;
                            tvMoney.setText(v + "");
                            tvMoneyHint2.setText("万用户加入我们");
                        }

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

                System.out.println("============ex===" + ex.getMessage());

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @OnClick({R.id.left_image_include, R.id.right_image_include, R.id.tv_agent, R.id.tv_extension})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image_include:
                finish();
                break;
            case R.id.right_image_include:
                MoneyWindow(view, 2);
                break;
            case R.id.tv_agent:
//                dialog.show();
                MoneyWindow(view, 1);
                break;
            case R.id.tv_extension:
                share();
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);//完成回调
    }

    /**
     * 分享
     * create  wjz
     **/
    private void share() {


        UMImage image = new UMImage(this, R.mipmap.about_us_logo);//网络图片
        UMWeb web = new UMWeb("http://m.smallaim.cn/agent/" + sp.getString(KeyCode.USER_ID, ""));
        web.setTitle("快来加入小目标吧");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("加入小目标，和我一起赚钱吧");//描述
        new ShareAction(this).withMedia(web)
                .setDisplayList(SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE)
                .setCallback(umShareListener).open();
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onStart(SHARE_MEDIA platform) {
            //分享开始的回调
        }

        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.d("plat", "platform" + platform);

            Toast.makeText(ExtensionActivity.this, " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ExtensionActivity.this, " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ExtensionActivity.this, " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    private void MoneyWindow(final View view, int number) {

        final View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_extension_explain, null);
        popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);
        TextView tv_go = (TextView) windowView.findViewById(R.id.tv_go);
        TextView tv_money = (TextView) windowView.findViewById(R.id.tv_haveMoney);

        String s = tv_money.getText().toString();
        int i = s.indexOf("6");
        SpannableStringBuilder builder = new SpannableStringBuilder(tv_money.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, i, i + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_money.setText(builder);

        if (number == 1) {
            tv_go.setVisibility(View.VISIBLE);
        } else {
            tv_go.setVisibility(View.GONE);
            windowView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    popupWindow.dismiss();
                }
            });
        }

        tv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExtensionActivity.this, ExtensionPayActivity.class);
                intent.putExtra("money", "698");
                startActivity(intent);
                popupWindow.dismiss();
            }
        });

        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(false);
        popupWindow.setFocusable(true);


        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }



}
