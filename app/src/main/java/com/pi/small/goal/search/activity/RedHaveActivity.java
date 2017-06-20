package com.pi.small.goal.search.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.RedPacketEntity;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class RedHaveActivity extends BaseActivity {


    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.right_image)
    ImageView rightImage;
    @InjectView(R.id.four_head_image)
    CircleImageView fourHeadImage;
    @InjectView(R.id.four_red_image)
    ImageView fourRedImage;
    @InjectView(R.id.four_text)
    TextView fourText;
    @InjectView(R.id.four_layout)
    RelativeLayout fourLayout;
    @InjectView(R.id.three_head_image)
    CircleImageView threeHeadImage;
    @InjectView(R.id.three_red_image)
    ImageView threeRedImage;
    @InjectView(R.id.three_text)
    TextView threeText;
    @InjectView(R.id.three_layout)
    RelativeLayout threeLayout;
    @InjectView(R.id.six_head_image)
    CircleImageView sixHeadImage;
    @InjectView(R.id.six_red_image)
    ImageView sixRedImage;
    @InjectView(R.id.six_text)
    TextView sixText;
    @InjectView(R.id.six_layout)
    RelativeLayout sixLayout;
    @InjectView(R.id.seven_head_image)
    CircleImageView sevenHeadImage;
    @InjectView(R.id.seven_red_image)
    ImageView sevenRedImage;
    @InjectView(R.id.seven_text)
    TextView sevenText;
    @InjectView(R.id.seven_layout)
    RelativeLayout sevenLayout;
    @InjectView(R.id.two_head_image)
    CircleImageView twoHeadImage;
    @InjectView(R.id.two_red_image)
    ImageView twoRedImage;
    @InjectView(R.id.two_text)
    TextView twoText;
    @InjectView(R.id.two_layout)
    RelativeLayout twoLayout;
    @InjectView(R.id.one_head_image)
    CircleImageView oneHeadImage;
    @InjectView(R.id.one_red_image)
    ImageView oneRedImage;
    @InjectView(R.id.one_text)
    TextView oneText;
    @InjectView(R.id.one_layout)
    RelativeLayout oneLayout;
    @InjectView(R.id.five_head_image)
    CircleImageView fiveHeadImage;
    @InjectView(R.id.five_red_image)
    ImageView fiveRedImage;
    @InjectView(R.id.five_text)
    TextView fiveText;
    @InjectView(R.id.five_layout)
    RelativeLayout fiveLayout;
    @InjectView(R.id.eight_head_image)
    CircleImageView eightHeadImage;
    @InjectView(R.id.eight_red_image)
    ImageView eightRedImage;
    @InjectView(R.id.eight_text)
    TextView eightText;
    @InjectView(R.id.eight_layout)
    RelativeLayout eightLayout;
    @InjectView(R.id.layout)
    RelativeLayout layout;
    @InjectView(R.id.change_image)
    ImageView changeImage;
    @InjectView(R.id.img_rotat)
    ImageView imgRotat;


    private String dynamicId;

    private List<RedPacketEntity> displayList;
    private List<RedPacketEntity> redPacketEntityList;
    private RedPacketEntity redPacketEntity;
    private List<CircleImageView> circleImageViewList;//头像集合
    private List<RelativeLayout> relativeLayoutList;//item 整体集合
    private List<TextView> textViewList;//姓名集合
    private List<ImageView> radImageList;//红包图标集合
    private List<Integer> isPointList;
    private int page = 1;

    private int total;
    private int pageTotal;
    private boolean isChange = false;
    private RotateAnimation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_have);
        ButterKnife.inject(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
        dynamicId = getIntent().getStringExtra("dynamicId");

        displayList = new ArrayList<RedPacketEntity>();
        redPacketEntityList = new ArrayList<RedPacketEntity>();
        circleImageViewList = new ArrayList<CircleImageView>();
        relativeLayoutList = new ArrayList<RelativeLayout>();
        isPointList = new ArrayList<Integer>();
        textViewList = new ArrayList<TextView>();
        radImageList = new ArrayList<ImageView>();

        circleImageViewList.add(oneHeadImage);
        circleImageViewList.add(twoHeadImage);
        circleImageViewList.add(threeHeadImage);
        circleImageViewList.add(fourHeadImage);
        circleImageViewList.add(fiveHeadImage);
        circleImageViewList.add(sixHeadImage);
        circleImageViewList.add(sevenHeadImage);
        circleImageViewList.add(eightHeadImage);

        relativeLayoutList.add(oneLayout);
        relativeLayoutList.add(twoLayout);
        relativeLayoutList.add(threeLayout);
        relativeLayoutList.add(fourLayout);
        relativeLayoutList.add(fiveLayout);
        relativeLayoutList.add(sixLayout);
        relativeLayoutList.add(sevenLayout);
        relativeLayoutList.add(eightLayout);

        textViewList.add(oneText);
        textViewList.add(twoText);
        textViewList.add(threeText);
        textViewList.add(fourText);
        textViewList.add(fiveText);
        textViewList.add(sixText);
        textViewList.add(sevenText);
        textViewList.add(eightText);

        radImageList.add(oneRedImage);
        radImageList.add(twoRedImage);
        radImageList.add(threeRedImage);
        radImageList.add(fourRedImage);
        radImageList.add(fiveRedImage);
        radImageList.add(sixRedImage);
        radImageList.add(sevenRedImage);
        radImageList.add(eightRedImage);


        init();
    }

    private void init() {
        GetRedList();

    }

    @OnClick({R.id.left_image, R.id.right_image, R.id.change_image, R.id.four_layout, R.id.three_layout, R.id.six_layout, R.id.seven_layout, R.id.two_layout, R.id.one_layout, R.id.five_layout, R.id.eight_layout})
    public void onViewClicked(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
            case R.id.change_image:
                if (isChange) {
                    if (page >= pageTotal) {
                        page = 1;
                    } else {
                        page = page + 1;
                    }
                    if (redPacketEntityList.size() < total) {
                        GetRedList();
                    } else {
                        SetDataList();
                    }
                }
                break;
            case R.id.right_image:
                break;
            case R.id.four_layout:
                if (isPointList.get(3) == 1) {
                    DrawRedPacket(3, redPacketEntityList.get(3).getId(), redPacketEntityList.get(3).getFromUserNick(), redPacketEntityList.get(3).getFromUserAvatar());
                } else {
                    GetRedPacketData(redPacketEntityList.get(3).getId(), redPacketEntityList.get(3).getFromUserNick(), redPacketEntityList.get(3).getFromUserAvatar());
                }
                break;
            case R.id.three_layout:
                if (isPointList.get(2) == 1) {
                    DrawRedPacket(2, redPacketEntityList.get(2).getId(), redPacketEntityList.get(2).getFromUserNick(), redPacketEntityList.get(2).getFromUserAvatar());
                } else {
                    GetRedPacketData(redPacketEntityList.get(2).getId(), redPacketEntityList.get(2).getFromUserNick(), redPacketEntityList.get(2).getFromUserAvatar());
                }
                break;
            case R.id.six_layout:
                if (isPointList.get(5) == 1) {
                    DrawRedPacket(5, redPacketEntityList.get(5).getId(), redPacketEntityList.get(5).getFromUserNick(), redPacketEntityList.get(5).getFromUserAvatar());
                } else {
                    GetRedPacketData(redPacketEntityList.get(5).getId(), redPacketEntityList.get(5).getFromUserNick(), redPacketEntityList.get(5).getFromUserAvatar());
                }
                break;
            case R.id.seven_layout:
                if (isPointList.get(6) == 1) {
                    DrawRedPacket(6, redPacketEntityList.get(6).getId(), redPacketEntityList.get(6).getFromUserNick(), redPacketEntityList.get(6).getFromUserAvatar());
                } else {
                    GetRedPacketData(redPacketEntityList.get(6).getId(), redPacketEntityList.get(6).getFromUserNick(), redPacketEntityList.get(6).getFromUserAvatar());
                }
                break;
            case R.id.two_layout:
                if (isPointList.get(1) == 1) {
                    DrawRedPacket(1, redPacketEntityList.get(1).getId(), redPacketEntityList.get(1).getFromUserNick(), redPacketEntityList.get(1).getFromUserAvatar());
                } else {
                    GetRedPacketData(redPacketEntityList.get(1).getId(), redPacketEntityList.get(1).getFromUserNick(), redPacketEntityList.get(1).getFromUserAvatar());
                }
                break;
            case R.id.one_layout:
                if (isPointList.get(0) == 1) {
                    DrawRedPacket(0, redPacketEntityList.get(0).getId(), redPacketEntityList.get(0).getFromUserNick(), redPacketEntityList.get(0).getFromUserAvatar());
                } else {
                    GetRedPacketData(redPacketEntityList.get(0).getId(), redPacketEntityList.get(0).getFromUserNick(), redPacketEntityList.get(0).getFromUserAvatar());
                }
                break;
            case R.id.five_layout:
                if (isPointList.get(4) == 1) {
                    DrawRedPacket(4, redPacketEntityList.get(4).getId(), redPacketEntityList.get(4).getFromUserNick(), redPacketEntityList.get(4).getFromUserAvatar());
                } else {
                    GetRedPacketData(redPacketEntityList.get(4).getId(), redPacketEntityList.get(4).getFromUserNick(), redPacketEntityList.get(4).getFromUserAvatar());
                }
                break;
            case R.id.eight_layout:
                if (isPointList.get(7) == 1) {
                    DrawRedPacket(7, redPacketEntityList.get(7).getId(), redPacketEntityList.get(7).getFromUserNick(), redPacketEntityList.get(7).getFromUserAvatar());
                } else {
                    GetRedPacketData(redPacketEntityList.get(7).getId(), redPacketEntityList.get(7).getFromUserNick(), redPacketEntityList.get(7).getFromUserAvatar());
                }
                break;
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            getRedListNext();

            return false;
        }
    });

    private void getRedListNext() {

        RequestParams requestParams = new RequestParams(Url.Url + Url.RedpacketDynamic);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("dynamicId", dynamicId);
        requestParams.addBodyParameter("p", page + "");
        requestParams.addBodyParameter("r", "8");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                //    id":12,"aimId":18,"dynamicId":64,"money":10.00,"size":10,"
//    remainMoney":10.00,"remainSize":10,"toUserId":27,"fromUserId":26,"
//    createTime":1496885913000,"status":1,"type":1,"fromUserNick":"44","fromUserAvatar":""}

//                System.out.println("=========GetRedList=============" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        JSONArray jsonArray = new JSONObject(result).getJSONArray("result");
                        total = Integer.valueOf(new JSONObject(result).getString("total"));
                        pageTotal = Integer.valueOf(new JSONObject(result).getString("pageTotal"));
                        for (int i = 0; i < jsonArray.length(); i++) {
                            redPacketEntity = new RedPacketEntity();
                            redPacketEntity.setId(jsonArray.getJSONObject(i).getString("id"));
                            redPacketEntity.setAimId(jsonArray.getJSONObject(i).getString("aimId"));
                            redPacketEntity.setDynamicId(jsonArray.getJSONObject(i).getString("dynamicId"));
                            redPacketEntity.setMoney(jsonArray.getJSONObject(i).getString("money"));
                            redPacketEntity.setSize(jsonArray.getJSONObject(i).getString("size"));

                            redPacketEntity.setRemainMoney(jsonArray.getJSONObject(i).getString("remainMoney"));
                            redPacketEntity.setRemainSize(jsonArray.getJSONObject(i).getString("remainSize"));
                            redPacketEntity.setToUserId(jsonArray.getJSONObject(i).getString("toUserId"));
                            redPacketEntity.setFromUserId(jsonArray.getJSONObject(i).getString("fromUserId"));
                            redPacketEntity.setCreateTime(jsonArray.getJSONObject(i).getString("createTime"));
                            redPacketEntity.setStatus(jsonArray.getJSONObject(i).getString("status"));
                            redPacketEntity.setType(jsonArray.getJSONObject(i).getString("type"));
                            redPacketEntity.setFromUserNick(jsonArray.getJSONObject(i).getString("fromUserNick"));
                            redPacketEntity.setFromUserAvatar(jsonArray.getJSONObject(i).getString("fromUserAvatar"));
                            redPacketEntity.setDrew(jsonArray.getJSONObject(i).getString("drew"));
                            redPacketEntityList.add(redPacketEntity);
                        }

                        SetDataList();
                    } else {
                        Utils.showToast(RedHaveActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
//                System.out.println("=========getMessage=============" + ex.getMessage());
                if (ex.getMessage() != null) {
                    Utils.showToast(RedHaveActivity.this, ex.getMessage());
                }
                animation.cancel();
                imgRotat.setVisibility(View.GONE);
            }

            @Override
            public void onFinished() {
                animation.cancel();
                imgRotat.setVisibility(View.GONE);
            }
        });

    }

    private void GetRedList() {

        animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(1000);//设置动画持续时间
/** 常用方法 */
        animation.setRepeatCount(10000);//设置重复次数
        animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//animation.setStartOffset(long startOffset);//执行前的等待时间

        imgRotat.setAnimation(animation);
        animation.start();

        handler.sendMessageDelayed(new Message(), 2000);

    }


    private void SetDataList() {
        if (pageTotal < 2) {
            isChange = false;
            changeImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_radar_change_off));
        } else {
            isChange = true;
            changeImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_radar_change_on));
        }

        displayList.clear();
        isPointList.clear();
        if (page * 8 > redPacketEntityList.size()) {
            for (int i = (page - 1) * 8; i < redPacketEntityList.size(); i++) {
                displayList.add(redPacketEntityList.get(i));
            }
        } else {
            for (int i = (page - 1) * 8; i < page * 8; i++) {
                displayList.add(redPacketEntityList.get(i));
            }
        }

        for (int i = 0; i < 8; i++) {
            relativeLayoutList.get(i).setVisibility(View.GONE);
        }
//        for (int i = 0; i < relativeLayoutList.size(); i++) {
//            relativeLayoutList.get(i).setVisibility(View.VISIBLE);
//        }
        for (int i = 0; i < displayList.size(); i++) {
            relativeLayoutList.get(i).setVisibility(View.VISIBLE);
        }
//
        for (int i = 0; i < displayList.size(); i++) {
//            relativeLayoutList.get(i).setVisibility(View.VISIBLE);
            textViewList.get(i).setText(displayList.get(i).getFromUserNick());
            if (!displayList.get(i).getFromUserAvatar().equals("")) {
                Picasso.with(this).load(Utils.GetPhotoPath(displayList.get(i).getFromUserAvatar())).into(circleImageViewList.get(i));
            } else {
                circleImageViewList.get(i).setImageDrawable(getResources().getDrawable(R.mipmap.icon_head));
            }
            if (Integer.valueOf(displayList.get(i).getRemainSize()) > 0) {
                if (displayList.get(i).getDrew().equals("0")) {
                    radImageList.get(i).setImageDrawable(getResources().getDrawable(R.mipmap.icon_money_radar_on));
                    isPointList.add(1);
                } else {
                    radImageList.get(i).setImageDrawable(getResources().getDrawable(R.mipmap.icon_money_radar_off));
                    isPointList.add(0);
                }
            } else {
                radImageList.get(i).setImageDrawable(getResources().getDrawable(R.mipmap.icon_money_radar_off));
                isPointList.add(0);

            }
        }
    }


    //抢红包
    private void DrawRedPacket(final int position, String packetId, final String nick, final String avatar) {
        RequestParams requestParams = new RequestParams(Url.Url + Url.RedpacketDraw);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("packetId", packetId);
        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=========DrawRedPacket=============" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        radImageList.get(position).setImageDrawable(getResources().getDrawable(R.mipmap.icon_money_radar_off));
                        isPointList.set(position, 0);
                        Intent intent = new Intent(RedHaveActivity.this, RedPacketActivity.class);
                        intent.putExtra("nick", nick);
                        intent.putExtra("avatar", avatar);
                        intent.putExtra("json", result);
                        intent.putExtra("isHave", "0");
                        startActivity(intent);
                    } else {
                        Utils.showToast(RedHaveActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (!ex.getMessage().equals("")) {
                    Utils.showToast(RedHaveActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }

    //获取指定领取红包记录
    private void GetRedPacketData(String packetId, final String nick, final String avatar) {

        RequestParams requestParams = new RequestParams(Url.Url + Url.RedpacketRecord);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("packetId", packetId);
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=========GetRedPacketData=============" + result);
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        Intent intent = new Intent(RedHaveActivity.this, RedPacketActivity.class);
                        intent.putExtra("nick", nick);
                        intent.putExtra("avatar", avatar);
                        intent.putExtra("json", result);
                        intent.putExtra("isHave", "1");
                        startActivity(intent);
                    } else {
                        Utils.showToast(RedHaveActivity.this, new JSONObject(result).getString("msg"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("=========GetRedPacketData===ex==========" + ex.getMessage());
                if (!ex.getMessage().equals("")) {
                    Utils.showToast(RedHaveActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onFinished() {

            }
        });
    }


    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        layout.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
        ;
    }
}
