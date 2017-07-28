package com.small.small.goal;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;
import android.telephony.TelephonyManager;

import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.ThirdUtils;
import com.small.small.goal.utils.Utils;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MyApplication extends MultiDexApplication {

    private TelephonyManager TelephonyMgr;
    public static String deviceId;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private List<Activity> activityList;

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public void exit() {
        for (Activity activity : activityList) {
            activity.finish();
        }
        CacheUtil.getInstance().setClear();
    }

    public List<Activity> getActivityList() {
        return activityList;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        RongIM.init(this);

        sharedPreferences = Utils.UserSharedPreferences(this);
        editor = sharedPreferences.edit();
        activityList = new ArrayList<>();

        TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();

        PlatformConfig.setWeixin(ThirdUtils.WX_APP_ID, ThirdUtils.WX_APP_SECRET);
        Config.DEBUG = true;
        PlatformConfig.setQQZone(ThirdUtils.QQ_APP_ID, ThirdUtils.QQ_APP_KEY);
        Config.DEBUG = true;
        UMShareAPI.get(this);

    }

    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;


            }
        }
        return null;
    }
}
