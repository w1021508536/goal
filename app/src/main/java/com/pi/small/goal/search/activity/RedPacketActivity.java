package com.pi.small.goal.search.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.search.adapter.RedPacketAdapter;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.MyListView;
import com.pi.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class RedPacketActivity extends BaseActivity {

    @InjectView(R.id.head_layout)
    RelativeLayout headLayout;
    @InjectView(R.id.head_image)
    CircleImageView headImage;
    @InjectView(R.id.nick_text)
    TextView nickText;
    @InjectView(R.id.money_text)
    TextView moneyText;
    @InjectView(R.id.red_list)
    MyListView redList;
    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.right_image)
    ImageView rightImage;
    @InjectView(R.id.layout)
    RelativeLayout layout;
    @InjectView(R.id.scrollView)
    ScrollView scrollView;
    @InjectView(R.id.view)
    ImageView view;
    @InjectView(R.id.money_layout)
    LinearLayout moneyLayout;
    @InjectView(R.id.text)
    TextView text;
    private RedPacketAdapter redPacketAdapter;

    private List<Map<String, String>> dataList;
    private Map<String, String> map;

    private String json;

    private String money;
    private String nick;
    private String avatar;

    private String isHave;  //0 抢到红包  1  没有红包，只显示列表

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_red_packet);
        ButterKnife.inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }

        json = getIntent().getExtras().getString("json", "");
        nick = getIntent().getExtras().getString("nick", "");
        avatar = getIntent().getExtras().getString("avatar", "");
        isHave = getIntent().getExtras().getString("isHave", "");
        dataList = new ArrayList<Map<String, String>>();

        redPacketAdapter = new RedPacketAdapter(this, dataList);
        redList.setAdapter(redPacketAdapter);
        SetData();
    }

    @OnClick(R.id.left_image)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
        }
    }

    private void SetData() {

        if (isHave.equals("1")) {
            moneyLayout.setVisibility(View.GONE);
//            text.setVisibility(View.GONE);
            if (!json.equals("")) {

                try {


                    JSONArray jsonArray = new JSONObject(json).getJSONArray("result");
                    System.out.println("==========jsonArray===============" + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        map = new HashMap<String, String>();
                        map.put("nick", jsonArray.getJSONObject(i).getString("nick"));
                        map.put("avatar", jsonArray.getJSONObject(i).getString("avatar"));
                        map.put("redPacketRecordId", jsonArray.getJSONObject(i).getString("redPacketRecordId"));
                        map.put("userId", jsonArray.getJSONObject(i).getString("userId"));
                        map.put("money", jsonArray.getJSONObject(i).getString("money"));
                        map.put("packetId", jsonArray.getJSONObject(i).getString("packetId"));
                        map.put("type", jsonArray.getJSONObject(i).getString("type"));
                        map.put("fromUserId", jsonArray.getJSONObject(i).getString("fromUserId"));
                        map.put("createTime", jsonArray.getJSONObject(i).getString("createTime"));
                        map.put("fromUserNick", jsonArray.getJSONObject(i).getString("fromUserNick"));
                        map.put("fromUserAvatar", jsonArray.getJSONObject(i).getString("fromUserAvatar"));
                        dataList.add(map);

                    }
                    redPacketAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            moneyText.setText(money);
        } else {
            if (!json.equals("")) {
                try {
                    JSONObject jsonObject = new JSONObject(json).getJSONObject("result");

                    money = jsonObject.getJSONObject("redPacket").getString("money");

                    JSONArray jsonArray = jsonObject.getJSONArray("records");

                    System.out.println("==========jsonArray===============" + jsonArray.length());
                    for (int i = 0; i < jsonArray.length(); i++) {
//                redPacketRecordId": 121, "userId": 33, "money": 1.06, "
//                packetId": 11, "createTime": 1496382687000, "type": 1, "nick": "Doris%F0%9F%90%BF", "avatar": "jpg"

//                    redPacketRecordId":86,"userId":43,"money":1.12,"packetId":13,"createTime":1496909627000," +
//                            ""type":1,"fromUserId":26,"fromUserNick":"44","fromUserAvatar":""}
                        map = new HashMap<String, String>();
                        map.put("nick", jsonArray.getJSONObject(i).getString("nick"));
                        map.put("avatar", jsonArray.getJSONObject(i).getString("avatar"));
                        map.put("redPacketRecordId", jsonArray.getJSONObject(i).getString("redPacketRecordId"));
                        map.put("userId", jsonArray.getJSONObject(i).getString("userId"));
                        map.put("money", jsonArray.getJSONObject(i).getString("money"));
                        map.put("packetId", jsonArray.getJSONObject(i).getString("packetId"));
                        map.put("type", jsonArray.getJSONObject(i).getString("type"));
                        map.put("fromUserId", jsonArray.getJSONObject(i).getString("fromUserId"));
                        map.put("createTime", jsonArray.getJSONObject(i).getString("createTime"));
                        map.put("fromUserNick", jsonArray.getJSONObject(i).getString("fromUserNick"));
                        map.put("fromUserAvatar", jsonArray.getJSONObject(i).getString("fromUserAvatar"));
                        dataList.add(map);

                    }
                    redPacketAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            moneyText.setText(money);
        }


        redList.setFocusable(false);
        redList.setFocusableInTouchMode(false);
        nickText.setText(nick);

        if (!avatar.equals("")) {
            Picasso.with(this).load(Utils.GetPhotoPath(avatar)).into(headImage);
        } else {
            headImage.setImageDrawable(getResources().getDrawable(R.mipmap.icon_head));
        }

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
        scrollView.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
        view.setVisibility(View.VISIBLE);
        ViewGroup.LayoutParams params = view.getLayoutParams();
        params.height = Utils.getStatusBarHeight(this);
        view.setLayoutParams(params);
    }
}
