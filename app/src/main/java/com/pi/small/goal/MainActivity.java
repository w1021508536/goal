package com.pi.small.goal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.pi.small.goal.aim.AimFragment;
import com.pi.small.goal.aim.activity.AddAimActivity;
import com.pi.small.goal.aim.activity.SaveMoneyActivity;
import com.pi.small.goal.message.MessageFragment;
import com.pi.small.goal.message.activity.FriendsListActivity;
import com.pi.small.goal.my.MyFragment;
import com.pi.small.goal.search.SearchFragment;
import com.pi.small.goal.utils.Code;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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

    private SharedPreferences userSharedPreferences;
    private SharedPreferences.Editor userEditor;
    private String imtoken;

    private SharedPreferences utilsSharedPreferences;
    private SharedPreferences.Editor utilsEditor;

    private String lastTime = "";

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

    private View currentView;
    final public static int REQUEST_CODE_ASK_CALL_PHONE = 123;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener;

    public AMapLocationClientOption mLocationOption;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        setContentView(R.layout.activity_main);
        userSharedPreferences = Utils.UserSharedPreferences(this);
        userEditor = userSharedPreferences.edit();
        imtoken = userSharedPreferences.getString("imtoken", "");

        utilsSharedPreferences = Utils.UtilsSharedPreferences(this);
        utilsEditor = utilsSharedPreferences.edit();
        lastTime = utilsSharedPreferences.getString("lastTime", "");
        fragmentManager = getSupportFragmentManager();
        super.onCreate(savedInstanceState);
        initData();
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        currentView = parent;
        return super.onCreateView(parent, name, context, attrs);
    }

    public void initData() {
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

        if (!imtoken.equals("")) {

            connect(imtoken);
//            GetFriendsListData();
            GetFollowListData();

//            if (lastTime.equals("")) {
//                GetRed();
//            } else {
//                if (simpleDateFormat.format(new Date(Long.valueOf(lastTime))).equals(simpleDateFormat.format(new Date(System.currentTimeMillis())))) {
//
//                } else {
//                    GetRed();
//                }
//            }

        }

        setTabSelection(0);

        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        mLocationClient = new AMapLocationClient(this);
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(600000);
        //设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为1000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//        //启动定位
//        mLocationClient.startLocation();

        mLocationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {


                if (aMapLocation != null) {
                    //解析定位结果
                    if (aMapLocation.getErrorCode() == 0) {
                        //定位成功回调信息，设置相关消息
                        aMapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                        aMapLocation.getLatitude();//获取纬度
                        aMapLocation.getLongitude();//获取经度
                        aMapLocation.getAccuracy();//获取精度信息


                        userEditor.putString("latitude", String.valueOf(aMapLocation.getLatitude()));
                        userEditor.putString("longitude", String.valueOf(aMapLocation.getLongitude()));
                        userEditor.putString("city", aMapLocation.getCity());
                        userEditor.commit();

                    } else {
                        //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                        Log.e("AmapError", "location Error, ErrCode:"
                                + aMapLocation.getErrorCode() + ", errInfo:"
                                + aMapLocation.getErrorInfo());
                    }
                }
            }
        };
//        //初始化定位
//        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        //启动定位
        mLocationClient.startLocation();

        if (Build.VERSION.SDK_INT >= 23) {

            System.out.println("=============23=================");
            int checkCallPhonePermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
            if (checkCallPhonePermission != PackageManager.PERMISSION_GRANTED) {
                System.out.println("=============23=========22========");
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_ASK_CALL_PHONE);
                return;
            } else {
                System.out.println("=============23=====1111============");
                mLocationClient.startLocation();
            }
        } else {
            System.out.println("=============!23=================");
            mLocationClient.startLocation();
        }



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
        aim_image.setImageResource(R.mipmap.icon_tab_aim_off);

        search_text.setTextColor(color);
        search_image.setImageResource(R.mipmap.icon_tab_search_off);


        my_text.setTextColor(color);
        my_image.setImageResource(R.mipmap.icon_tab_my_off);


        message_text.setTextColor(color);
        message_image.setImageResource(R.mipmap.icon_tab_message_off);
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
                aim_image.setImageResource(R.mipmap.icon_tab_aim_on);
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
                search_image.setImageResource(R.mipmap.icon_tab_search_on);
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
                message_image.setImageResource(R.mipmap.icon_tab_message_on);
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
                my_image.setImageResource(R.mipmap.icon_tab_my_on);
                if (myFragment == null) {
                    myFragment = new MyFragment();
                    transaction.add(R.id.framelayout, myFragment);
                } else {
                    transaction.show(myFragment);
                }
                break;

        }

//      if (index==3)
//        view.setVisibility(View.GONE);
//        else
//            view.setVisibility(View.VISIBLE);
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

                    userEditor.putString("RY_Id", userid);
                    userEditor.commit();
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

    private void GetFriendsListData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.FriendList);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                System.out.println("=============GetFriendsListData==========" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        utilsEditor.putString("friendsList", result);
                        utilsEditor.commit();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void GetRed() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.RedGet);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        GetRedWindow();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void GetRedWindow() {

        View windowView = LayoutInflater.from(this).inflate(
                R.layout.window_red_get, null);
        final PopupWindow popupWindow = new PopupWindow(windowView,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT, false);

        popupWindow.setAnimationStyle(R.style.MyDialogStyle);
        popupWindow.setTouchable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(false);

        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框
        // 我觉得这里是API的一个bug
        popupWindow.setBackgroundDrawable(getResources().getDrawable(R.drawable.background_empty));
        // 设置好参数之后再show
        popupWindow.showAtLocation(currentView, Gravity.CENTER, 0, 0);

    }


    private void GetFollowListData() {
        RequestParams requestParams = new RequestParams(Url.Url + Url.FollowedList);
        requestParams.addHeader("token", Utils.GetToken(this));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {

                System.out.println("==========GetFollowListData===========" + result);

                try {
                    String code = new JSONObject(result).getString("code");
                    if (code.equals("0")) {
                        utilsEditor.putString("followList", new JSONObject(result).getString("result"));
                        utilsEditor.commit();
                    }
//                    else if(code.equals("100000")){
//                        List<Map<String, String>> followList = new ArrayList<Map<String, String>>();
//                        Utils.UtilsSharedPreferences(MainActivity.this).edit().putString("followList", Utils.changeFollowToJson(followList));
//                        Utils.UtilsSharedPreferences(MainActivity.this).edit().commit();
//                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_CALL_PHONE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // Permission Granted
                    System.out.println("=============6666=================");
                    mLocationClient.startLocation();
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "您禁止了定位权限", Toast.LENGTH_SHORT)
                            .show();
                }
                break;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
