package com.small.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gongwen.marqueen.MarqueeFactory;
import com.gongwen.marqueen.MarqueeView;
import com.google.gson.Gson;
import com.small.small.goal.R;
import com.small.small.goal.my.dialog.ExtensionDialog;
import com.small.small.goal.my.entry.LastAgentEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;
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
import de.hdodenhof.circleimageview.CircleImageView;

import static com.small.small.goal.R.id.tv_extension;

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
    @InjectView(tv_extension)
    TextView tvExtension;
    @InjectView(R.id.tv_moneyHint1)
    TextView tvMoneyHint1;
    @InjectView(R.id.tvMoney)
    TextView tvMoney;
    @InjectView(R.id.tv_moneyHint2)
    TextView tvMoneyHint2;
    private ExtensionDialog dialog;
    private final List<notice> datas = new ArrayList<>();
    private MarqueeFactory<TextView, notice> marqueeFactory;
    private LastAgentEntity.ResultBean agent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_extension);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        dialog = new ExtensionDialog(this);


        marqueeFactory = new NoticeMF(this);
        marqueeView.setMarqueeFactory(marqueeFactory);
        marqueeView.startFlipping();

    }

    @Override
    public void initWeight() {
        super.initWeight();
        tvAgent.setOnClickListener(this);
        tvExtension.setOnClickListener(this);
        tvExtension.setClickable(false);
        dialog.setOnClickGoListener(new ExtensionDialog.onClickGoListener() {
            @Override
            public void onclick() {
                Intent intent = new Intent(ExtensionActivity.this, ExtensionPayActivity.class);
                intent.putExtra("money", "698");
                startActivity(intent);
                dialog.dismiss();
            }
        });
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
                if (Utils.callOk(result)) {
                    Gson gson = new Gson();
                    LastAgentEntity lastAgentEntity = gson.fromJson(result, LastAgentEntity.class);
                    agent = lastAgentEntity.getResult();

                    datas.add(new notice("恭喜用户" + agent.getNick() + "成为小目标第" + agent.getId() + "名代理商", agent.getNick().length()));
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
        RequestParams requestParams = Utils.getRequestParams(this);

        requestParams.setUri(Url.Url + "/agent");
        String string = sp.getString(KeyCode.USER_ID, "");
        //  requestParams.addBodyParameter("uid", sp.getString(KeyCode.USER_ID, ""));

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                //{"msg":"success","code":0,"result":{"id":4,"userId":48,"level":3,"subCompanyId":0,"volume":0},"pageNum":0,"pageSize":0,"pageTotal":0,"total":0}
                if (Utils.callOk(result)) {

                    Picasso.with(ExtensionActivity.this).load(Utils.GetPhotoPath(sp.getString(KeyCode.USER_AVATAR, ""))).into(imgIcon);
                    tvUserName.setText(sp.getString(KeyCode.USER_NICK, ""));

                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String id = Utils.GetOneStringForJson("id", Utils.getResultStr(result));
                        String level = Utils.GetOneStringForJson("level", Utils.getResultStr(result));
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
            case R.id.tv_agent:
//                View view = LayoutInflater.from(this).inflate(R.layout.dialog_hint, null);
//                new AlertDialog.Builder(this).setView(view)
//                        .setTitle("成为代理商")
//                        //   .setMessage("1.成为代理商需要支付698元;" + "\n" + "2.成为代理商后可以开展小目标分销业务，参与全民业绩分享;")
//                        .setPositiveButton("去支付", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                //     unBindWx();
//                                dialog.dismiss();
//                                Intent intent = new Intent(ExtensionActivity.this, ExtensionPayActivity.class);
//                                intent.putExtra("money", "698");
//                                startActivity(intent);
//                            }
//                        }).show();
                dialog.show();
                break;
            case tv_extension:
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
        UMWeb web = new UMWeb("http://m.test.smallaim.cn/agent/" + sp.getString(KeyCode.USER_ID, ""));
        web.setTitle("小目标");//标题
        web.setThumb(image);  //缩略图
        web.setDescription("哈哈哈哈哈");//描述
        new ShareAction(this).withText("hello").withMedia(web)
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

            Toast.makeText(ExtensionActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(ExtensionActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
            if (t != null) {
                Log.d("throw", "throw:" + t.getMessage());
            }
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(ExtensionActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
        }
    };

    public class notice {

        String noticeStr = "";
        int nameLength;

        public notice(String noticeStr, int nameLength) {
            this.noticeStr = noticeStr;
            this.nameLength = nameLength;
        }

        public String getNoticeStr() {
            return noticeStr;
        }

        public void setNoticeStr(String noticeStr) {
            this.noticeStr = noticeStr;
        }

        public int getNameLength() {
            return nameLength;
        }

        public void setNameLength(int nameLength) {
            this.nameLength = nameLength;
        }
    }
}
