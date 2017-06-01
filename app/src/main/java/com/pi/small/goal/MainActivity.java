package com.pi.small.goal;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.aim.AimFragment;
import com.pi.small.goal.message.MessageFragment;
import com.pi.small.goal.my.MyFragment;
import com.pi.small.goal.search.SearchFragment;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Utils;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private LinearLayout aim_layout;
    private ImageView aim_image;
    private TextView aim_text;

    private LinearLayout search_layout;
    private ImageView search_image;
    private TextView search_text;

    private LinearLayout message_layout;
    private ImageView message_image;
    private TextView message_text;

    private LinearLayout my_layout;
    private ImageView my_image;
    private TextView my_text;

    private RelativeLayout message_top_layout;


    private FragmentManager fragmentManager;


    private AimFragment aimFragment;
    private MessageFragment messageFragment;
    private SearchFragment searchFragment;
    private MyFragment myFragment;

    private FrameLayout frameLayout;
    private int currentTabIndex = -1;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String imtoken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();
        imtoken = sharedPreferences.getString("imtoken", "");
        fragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
    }

@Override
    public void initData() {

view=findViewById(R.id.view);
        aim_layout = (LinearLayout) findViewById(R.id.aim_layout);
        aim_image = (ImageView) findViewById(R.id.aim_image);
        aim_text = (TextView) findViewById(R.id.aim_text);

        search_layout = (LinearLayout) findViewById(R.id.search_layout);
        search_image = (ImageView) findViewById(R.id.search_image);
        search_text = (TextView) findViewById(R.id.search_text);

        message_layout = (LinearLayout) findViewById(R.id.message_layout);
        message_image = (ImageView) findViewById(R.id.message_image);
        message_text = (TextView) findViewById(R.id.message_text);

        my_layout = (LinearLayout) findViewById(R.id.my_layout);
        my_image = (ImageView) findViewById(R.id.my_image);
        my_text = (TextView) findViewById(R.id.my_text);

        message_top_layout = (RelativeLayout) findViewById(R.id.message_top_layout);

        aim_layout.setOnClickListener(this);
        search_layout.setOnClickListener(this);
        message_layout.setOnClickListener(this);
        my_layout.setOnClickListener(this);

        System.out.println("=========imtoken===========" + imtoken);
        if (!imtoken.equals("")) {

            connect(imtoken);
        }

        setTabSelection(0);
super.initData();

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.aim_layout:

                if (currentTabIndex != 0) {
                    setTabSelection(0);
                }
                break;
            case R.id.search_layout:
                if (currentTabIndex != 1) {


                    setTabSelection(1);
                }
                break;
            case R.id.message_layout:
                if (currentTabIndex != 2) {
//                    RongIM.getInstance().setCurrentUserInfo(new UserInfo(sharedPreferences.getString("RY_Id", ""), sharedPreferences.getString("nick", ""), Uri.parse("http://www.ghost64.com/qqtupian/zixunImg/local/2016/11/22/14798003915289.jpg")));
//                    RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
//                        @Override
//                        public UserInfo getUserInfo(String s) {
//
//                            return new UserInfo(sharedPreferences.getString("RY_Id", ""), sharedPreferences.getString("nick", ""), Uri.parse("http://www.ghost64.com/qqtupian/zixunImg/local/2016/11/22/14798003915289.jpg"));
//                        }
//                    }, false);
//                    RongIM.getInstance().setMessageAttachedUserInfo(true);
                    setTabSelection(2);
                }
                break;
            case R.id.my_layout:
                if (currentTabIndex != 3) {
                    setTabSelection(3);
                }

                break;
        }
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     *
     * @param transaction 用于对Fragment执行操作的事务
     */
    private void hideFragments(FragmentTransaction transaction) {

        if (searchFragment != null) {
            transaction.hide(searchFragment);
        }
        if (aimFragment != null) {
            transaction.hide(aimFragment);
        }
        if (myFragment != null) {
            transaction.hide(myFragment);
        }
        if (messageFragment != null) {
            transaction.hide(messageFragment);
        }
    }


    /**
     * 清除掉所有的选中状态。
     */
    private void clearSelection() {

        message_top_layout.setVisibility(View.GONE);

        int color = getResources().getColor(R.color.text_main_off);
        aim_text.setTextColor(color);
//      aim_image.setImageResource(R.mipmap.center);

        search_text.setTextColor(color);
//      search_image.setImageResource(R.mipmap.search);


        my_text.setTextColor(color);
//      my_image.setImageResource(R.mipmap.my);


        message_text.setTextColor(color);
//      message_image.setImageResource(R.mipmap.message);
    }

    private void setTabSelection(int index) {
        // 每次选中之前先清楚掉上次的选中状态
        clearSelection();
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                currentTabIndex = 0;
                aim_text.setTextColor(getResources().getColor(R.color.text_main_on));
//                aim_image.setImageResource(R.mipmap.center_2);
                if (aimFragment == null) {
                    // 如果CenterFragment为空，则创建一个并添加到界面上
                    aimFragment = new AimFragment();
                    transaction.add(R.id.framelayout, aimFragment);
                } else {
                    // 如果CenterFragment不为空，则直接将它显示出来
                    transaction.show(aimFragment);
                }
                break;
            case 1:
                currentTabIndex = 1;
                search_text.setTextColor(getResources().getColor(R.color.text_main_on));
//                search_image.setImageResource(R.mipmap.search_2);
                if (searchFragment == null) {
                    searchFragment = new SearchFragment();
                    transaction.add(R.id.framelayout, searchFragment);
                } else {
                    transaction.show(searchFragment);
                }
                break;
            case 2:
                currentTabIndex = 2;
                message_text.setTextColor(getResources().getColor(R.color.text_main_on));
//                message_image.setImageResource(R.mipmap.message_2);
//                message_top_layout.setVisibility(View.VISIBLE);
                if (messageFragment == null) {
                    messageFragment = new MessageFragment();
                    Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                            .appendPath("conversationlist")
                            .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false") //设置私聊会话，该会话聚合显示
                            .build();

                    messageFragment.setUri(uri);  //设置 ConverssationListFragment 的显示属性
                    transaction.add(R.id.framelayout, messageFragment);
                } else {
                    transaction.show(messageFragment);
                }
                break;
            case 3:
                currentTabIndex = 3;
                my_text.setTextColor(getResources().getColor(R.color.text_main_on));
//                my_image.setImageResource(R.mipmap.my_2);
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    transaction.add(R.id.framelayout, myFragment);
                } else {
                    transaction.show(myFragment);
                }
                break;

        }

      if (index==3)
        view.setVisibility(View.GONE);
        else
            view.setVisibility(View.VISIBLE);
        transaction.commitAllowingStateLoss();

    }


    //    /**
//     * <p>连接服务器，在整个应用程序全局，只需要调用一次，需在 {@link #init(Context)} 之后调用。</p>
//     * <p>如果调用此接口遇到连接失败，SDK 会自动启动重连机制进行最多10次重连，分别是1, 2, 4, 8, 16, 32, 64, 128, 256, 512秒后。
//     * 在这之后如果仍没有连接成功，还会在当检测到设备网络状态变化时再次进行重连。</p>
//     *
//     * @param token    从服务端获取的用户身份令牌（Token）。
//     * @param  callback 连接回调。
//     * @return RongIM  客户端核心类的实例。
//     */
    private void connect(String token) {
        System.out.println("=========token===========" + token);
        if (getApplicationInfo().packageName.equals(MyApplication.getCurProcessName(getApplicationContext()))) {

            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
                 *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
                 */
                @Override
                public void onTokenIncorrect() {

                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token 对应的用户 id
                 */
                @Override
                public void onSuccess(String userid) {
                    System.out.println("====================" + userid);
                    Log.d("LoginActivity", "--onSuccess" + userid);
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                    finish();

                    editor.putString("RY_Id", userid);
                    editor.commit();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {
                    System.out.println("====================" + errorCode.getMessage());
                }
            });
        }
    }
}
