package com.pi.small.goal;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.telephony.TelephonyManager;

import org.xutils.x;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2017/5/22.
 */

public class MyApplication extends Application {

    private TelephonyManager TelephonyMgr;
    public static String deviceId;

    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        RongIM.init(this);

        TelephonyMgr = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
        deviceId = TelephonyMgr.getDeviceId();
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
