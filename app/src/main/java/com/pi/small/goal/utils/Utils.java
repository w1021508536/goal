package com.pi.small.goal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.Gravity;
import android.widget.Toast;

/**
 * Created by Administrator on 2017/5/23.
 */

public class Utils {

    /**
     * 建立用户本地存储
     *
     * @param context
     * @return
     */
    public static SharedPreferences UserSharedPreferences(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("user_info", Context.MODE_PRIVATE);
        return mySharedPreferences;
    }


    private static Toast mToast = null;


    public static void showToast(Context context, String text) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mToast.setText(text);
        mToast.setGravity(Gravity.BOTTOM, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }


    public static String GetToken(Context context) {
        SharedPreferences sharedPreferences = Utils.UserSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String token = sharedPreferences.getString("token", "");

        return token;
    }


    public static String GetPhotoPath(String path) {
        String photoPath = "";
        if (path.indexOf("http") == -1) {
            photoPath = Url.PhotoUrl + "/" + path;
        } else {
            photoPath = path;
        }
        return photoPath;

    }


    public static boolean isAppInstalled(Context context, String packageName) {
        final PackageManager packageManager = context.getPackageManager();
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        List<String> pName = new ArrayList<String>();
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                pName.add(pn);
            }
        }
        return pName.contains(packageName);
    }


    public static String GetTime( Long time) {
       SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        String time_string = "";
        long minute = 60000;
        long hour = 3600000;
        long day = 86400000;
        long third_day = 259200000;
        long distanceTime = System.currentTimeMillis() - time;

        if (distanceTime < minute) {
            time_string = "刚刚";
        } else if (distanceTime >= minute && distanceTime < hour) {
            time_string = String.valueOf(distanceTime / minute) + "分钟前";
        } else if (distanceTime >= hour && distanceTime < day) {
            time_string = String.valueOf(distanceTime / hour) + "小时前";
        } else if (distanceTime >= day && distanceTime < third_day) {
            time_string = String.valueOf(distanceTime / day) + "天前";
        } else if (distanceTime >= third_day ){
         time_string=   simpleDateFormat2.format(new Date(time));

        }


        return time_string;

    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}
