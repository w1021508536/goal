package com.pi.small.goal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.pi.small.goal.MyApplication;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static SharedPreferences UtilsSharedPreferences(Context context) {
        SharedPreferences mySharedPreferences = context.getSharedPreferences("utils", Context.MODE_PRIVATE);
        return mySharedPreferences;
    }


    private static Toast mToast = null;


    public static void showToast(Context context, String text) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        mToast.setText(text);
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.setDuration(Toast.LENGTH_SHORT);
        mToast.show();
    }


    public static String GetToken(Context context) {
        SharedPreferences sharedPreferences = Utils.UserSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String token = sharedPreferences.getString("token", "");

        return token;
    }

    public static String GetOneStringForSp(Context context, String key) {
        SharedPreferences sharedPreferences = Utils.UserSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String str = sharedPreferences.getString(key, "");

        return str;
    }

    public static String GetPhotoPath(String path) {
        String photoPath = "";
        if (path.equals("")) {
            return "";
        } else if (path.indexOf("http") == -1) {
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

    // listview 重新测量；
    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();

        if (listAdapter == null) {
            // pre-condition
            return;
        }
        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static RequestParams getRequestParams(Context context) {
        RequestParams requestParams = new RequestParams();
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

    public static void putUser(String result, Context context) throws JSONException {
        JSONObject userObject = new JSONObject(result).getJSONObject("result").getJSONObject("user");
        String id = userObject.getString("id");
        String nick = userObject.getString("nick");
        String avatar = userObject.optString("avatar");
        String brief = userObject.optString("brief");
        String wechatId = userObject.optString("wechatId");
        String qqId = userObject.optString("qqId");
        String mobile = userObject.optString("mobile");
        String city = userObject.optString("city");
        String createTime = userObject.optString("createTime");
        String updateTime = userObject.optString("updateTime");
        String token = new JSONObject(result).getJSONObject("result").optString("token");
        String imtoken = new JSONObject(result).getJSONObject("result").optString("imtoken");
        int payPassword = new JSONObject(result).getJSONObject("result").optInt("payPassword");

        SharedPreferences sharedPreferences = UserSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("id", id);
        editor.putString("nick", nick);
        editor.putString("avatar", avatar);
        editor.putString("brief", brief);
        editor.putString("wechatId", wechatId);
        editor.putString("qqId", qqId);
        editor.putString("mobile", mobile);
        editor.putString("city", city);
        editor.putString("createTime", createTime);
        editor.putString("updateTime", updateTime);
        editor.putString("token", token);
        editor.putString("imtoken", imtoken);
        editor.putInt("payPassword", payPassword);
        editor.commit();
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


    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }


    public static String changeFollowToJson(List<Map<String, String>> dataList) {  //把一个集合转换成json格式的字符串
        // "followId":2,"userId":26,"followUserId":8,"nick":"ee","avatar":"jpg"
        JSONArray jsonArray = new JSONArray();
        JSONObject object;
        String str = "";
        for (int i = 0; i < dataList.size(); i++) {  //遍历上面初始化的集合数据，把数据加入JSONObject里面
            object = new JSONObject();//一个user对象，使用一个JSONObject对象来装
            //从集合取出数据，放入JSONObject里面 JSONObject对象和map差不多用法,以键和值形式存储数据
            try {
                object.put("followId", dataList.get(i).get("followId"));
                object.put("userId", dataList.get(i).get("userId"));
                object.put("followUserId", dataList.get(i).get("followUserId"));
                object.put("nick", dataList.get(i).get("nick"));
                object.put("avatar", dataList.get(i).get("avatar"));
                object.put("status", dataList.get(i).get("status"));
                jsonArray.put(object); //把JSONObject对象装入jsonArray数组里面
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        str = jsonArray.toString();

        return str;
    }

    public static List<Map<String, String>> GetFollowList(String string) {
        List<Map<String, String>> dataList = new ArrayList<Map<String, String>>();
        Map<String, String> map;
        if (!string.equals("")) {
            try {
                JSONArray jsonArray = new JSONArray(string);
                for (int i = 0; i < jsonArray.length(); i++) {
                    map = new HashMap<String, String>();
                    map.put("followId", jsonArray.getJSONObject(i).optString("followId"));
                    map.put("userId", jsonArray.getJSONObject(i).optString("userId"));
                    map.put("followUserId", jsonArray.getJSONObject(i).optString("followUserId"));
                    map.put("nick", jsonArray.getJSONObject(i).optString("nick"));
                    map.put("avatar", jsonArray.getJSONObject(i).optString("avatar"));
                    map.put("status", jsonArray.getJSONObject(i).optString("avatar"));
                    dataList.add(map);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return dataList;

    }

//    public static String changeFriendsToJson(List<Map<String, String>> dataList) {  //把一个集合转换成json格式的字符串
//        // "followId":2,"userId":26,"followUserId":8,"nick":"ee","avatar":"jpg"
//        JSONArray jsonArray = new JSONArray();
//        JSONObject object;
//        String str = "";
//        for (int i = 0; i < dataList.size(); i++) {  //遍历上面初始化的集合数据，把数据加入JSONObject里面
//            object = new JSONObject();//一个user对象，使用一个JSONObject对象来装
//            //从集合取出数据，放入JSONObject里面 JSONObject对象和map差不多用法,以键和值形式存储数据
//            try {
//                object.put("followId", dataList.get(i).get("followId"));
//                object.put("userId", dataList.get(i).get("userId"));
//                object.put("followUserId", dataList.get(i).get("followUserId"));
//                object.put("nick", dataList.get(i).get("nick"));
//                object.put("avatar", dataList.get(i).get("avatar"));
//                jsonArray.put(object); //把JSONObject对象装入jsonArray数组里面
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//
//        str = jsonArray.toString();
//
//        return str;
//    }

    /**
     * 获取图片旋转角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 压缩图片
     *
     * @param bitmap   源图片
     * @param width    想要的宽度
     * @param height   想要的高度
     * @param isAdjust 是否自动调整尺寸, true图片就不会拉伸，false严格按照你的尺寸压缩
     * @return Bitmap
     */
    public static Bitmap reduce(Bitmap bitmap, int width, int height, String path, boolean isAdjust) {
        // 如果想要的宽度和高度都比源图片小，就不压缩了，直接返回原图
        if (bitmap.getWidth() < width && bitmap.getHeight() < height) {
            return bitmap;
        }
        if (width == 0 && height == 0) {
            width = bitmap.getWidth();
            height = bitmap.getHeight();
        }

        // 根据想要的尺寸精确计算压缩比例, 方法详解：public BigDecimal divide(BigDecimal divisor, int scale, int
        // roundingMode);
        // scale表示要保留的小数位, roundingMode表示如何处理多余的小数位，BigDecimal.ROUND_DOWN表示自动舍弃
        float sx = new BigDecimal(width).divide(new BigDecimal(bitmap.getWidth()), 4, BigDecimal
                .ROUND_DOWN).floatValue();
        float sy = new BigDecimal(height).divide(new BigDecimal(bitmap.getHeight()), 4,
                BigDecimal.ROUND_DOWN).floatValue();
        if (isAdjust) {// 如果想自动调整比例，不至于图片会拉伸
            sx = (sx < sy ? sx : sy);
            sy = sx;// 哪个比例小一点，就用哪个比例
        }
        Matrix matrix = new Matrix();
        matrix.postScale(sx, sy);// 调用api中的方法进行压缩，就大功告成了
        matrix.postRotate(readPictureDegree(path)); /*翻转90度*/
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix,
                true);
    }
}
