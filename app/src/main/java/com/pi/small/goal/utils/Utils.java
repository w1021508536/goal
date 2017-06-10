package com.pi.small.goal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.Gravity;
import android.widget.Toast;

import com.pi.small.goal.MyApplication;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        //  if ("".equals(path)) return "";
        if (path.indexOf("http") == -1) {
            photoPath = Url.PhotoUrl + "/" + path;
        } else {
            photoPath = path;
        }
        return photoPath;

    }

    /**
     * 返回的json是否是成功的
     * create  wjz
     **/
    public static boolean callOk(String jsonString) {
        String code = "";
        try {
            code = new JSONObject(jsonString).getString("code");

            if (code.equals("0"))
                return true;
            else return false;
        } catch (JSONException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 返回的json是否是成功的
     * create  wjz
     **/
    public static String getResult(String jsonString) {
        String result = "";
        try {
            result = new JSONObject(jsonString).getString("result");


            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return result;
        }
    }

    /**
     * 返回的json是否是成功的
     * create  wjz
     **/
    public static boolean photoEmpty(String photo) {
        if (photo.equals("") || photo.equals("jpg")) {
            return false;
        } else
            return true;
    }

    /**
     * 获取返回的json的msg
     * create  wjz
     **/
    public static String getMsg(String jsonString) {
        String msg = "";
        try {
            msg = new JSONObject(jsonString).getString("msg");

            if (msg.equals("0"))
                return msg;
            else return msg;
        } catch (JSONException e) {
            e.printStackTrace();
            return msg;
        }
    }

    public static RequestParams getRequestParams(Context context) {
        RequestParams requestParams = new RequestParams();
        requestParams.setUri(Url.Url + "/redpacket");
        requestParams.addHeader("token", Utils.UserSharedPreferences(context).getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        return requestParams;
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


    public static String GetTime(Long time) {
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
        } else if (distanceTime >= third_day) {
            time_string = simpleDateFormat2.format(new Date(time));
        }
        return time_string;
    }

    public static String GetTime2(Long time) {
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
        } else if (distanceTime >= day) {
            time_string = String.valueOf(distanceTime / day) + "天前";
        }
        return time_string;
    }

    /**
     * 小格式化，小数点后省略一位
     * create  wjz
     **/

    public static String getPercentOne(float v) {
        DecimalFormat df = new DecimalFormat("#.0");
        if (v < 1) {
            return "0" + df.format(v);
        } else {
            return df.format(v);
        }
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
