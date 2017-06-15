package com.pi.small.goal.search.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
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

public class RedHaveActivity extends AppCompatActivity {


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


    private String dynamicId;

    private List<RedPacketEntity> displayList;
    private List<RedPacketEntity> redPacketEntityList;
    private RedPacketEntity redPacketEntity;
    private List<CircleImageView> circleImageViewList;//头像集合
    private List<RelativeLayout> relativeLayoutList;//item 整体集合
    private List<TextView> textViewList;//姓名集合
    private List<ImageView> radImageList;//红包图标集合

    private int page = 1;

    private int total;
    private int pageTotal;
    private boolean isChange = false;

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
                DrawRedPacket(redPacketEntityList.get(3).getId(), redPacketEntityList.get(3).getFromUserNick(), redPacketEntityList.get(3).getFromUserAvatar());
                break;
            case R.id.three_layout:
                DrawRedPacket(redPacketEntityList.get(2).getId(), redPacketEntityList.get(2).getFromUserNick(), redPacketEntityList.get(2).getFromUserAvatar());
                break;
            case R.id.six_layout:
                DrawRedPacket(redPacketEntityList.get(5).getId(), redPacketEntityList.get(5).getFromUserNick(), redPacketEntityList.get(5).getFromUserAvatar());
                break;
            case R.id.seven_layout:
                DrawRedPacket(redPacketEntityList.get(6).getId(), redPacketEntityList.get(6).getFromUserNick(), redPacketEntityList.get(6).getFromUserAvatar());
                break;
            case R.id.two_layout:
                DrawRedPacket(redPacketEntityList.get(1).getId(), redPacketEntityList.get(1).getFromUserNick(), redPacketEntityList.get(1).getFromUserAvatar());
                break;
            case R.id.one_layout:
                DrawRedPacket(redPacketEntityList.get(0).getId(), redPacketEntityList.get(0).getFromUserNick(), redPacketEntityList.get(0).getFromUserAvatar());
                break;
            case R.id.five_layout:
                DrawRedPacket(redPacketEntityList.get(4).getId(), redPacketEntityList.get(4).getFromUserNick(), redPacketEntityList.get(4).getFromUserAvatar());
                break;
            case R.id.eight_layout:
                DrawRedPacket(redPacketEntityList.get(7).getId(), redPacketEntityList.get(7).getFromUserNick(), redPacketEntityList.get(7).getFromUserAvatar());
                break;
        }
    }

    private void GetRedList() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.RedpacketDynamic);
        System.out.println("=========GetRedList======change=======" + page);
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

                System.out.println("=========GetRedList=============" + result);
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
                System.out.println("=========getMessage=============" + ex.getMessage());
                if (ex.getMessage() != null) {
                    Utils.showToast(RedHaveActivity.this, ex.getMessage());
                }
            }

            @Override
            public void onFinished() {

            }
        });

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
            System.out.println("===========textViewList============" + textViewList.size());
            textViewList.get(i).setText(displayList.get(i).getFromUserNick());
            if (!displayList.get(i).getFromUserAvatar().equals("")) {
                System.out.println("============displayList.get(i).getFromUserAvatar()==========" + displayList.get(i).getFromUserAvatar());
                Picasso.with(this).load(Utils.GetPhotoPath(displayList.get(i).getFromUserAvatar())).into(circleImageViewList.get(i));
            } else {
                circleImageViewList.get(i).setImageDrawable(getResources().getDrawable(R.mipmap.icon_head));
            }
            if (Integer.valueOf(displayList.get(i).getRemainSize()) > 0) {
                if (displayList.get(i).getDrew().equals("0")) {
                    radImageList.get(i).setImageDrawable(getResources().getDrawable(R.mipmap.icon_money_radar_on));
                } else {
                    radImageList.get(i).setImageDrawable(getResources().getDrawable(R.mipmap.icon_money_radar_off));
                }
            } else {
                radImageList.get(i).setImageDrawable(getResources().getDrawable(R.mipmap.icon_money_radar_off));
            }
        }
    }


    //抢红包
    private void DrawRedPacket(String packetId, final String nick, final String avatar) {
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
                        Intent intent = new Intent(RedHaveActivity.this, RedPacketActivity.class);
                        intent.putExtra("nick", nick);
                        intent.putExtra("avatar", avatar);
                        intent.putExtra("json", result);
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
