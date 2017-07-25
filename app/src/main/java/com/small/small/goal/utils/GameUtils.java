package com.small.small.goal.utils;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.ArrayList;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/28 8:47
 * 修改：
 * 描述：零散工具类
 **/
public class GameUtils {


    /**
     * 计算阶乘数，即n! = n * (n-1) * ... * 2 * 1
     *
     * @param n
     * @return
     */
    private static long factorial(int n) {
        return (n > 1) ? n * factorial(n - 1) : 1;
    }


    /**
     * 计算组合数，即C(m, n) = n!/((n-m)! * m!)
     *
     * @param n
     * @param m
     * @return
     */
    public static long combination(int m, int n) {
        return (n >= m) ? factorial(n) / factorial(n - m) / factorial(m) : 0;
    }

    /**
     * 生成随机数
     * create  wjz
     **/
    public static int[] randomJ(int min, int max, int nums) {

        int[] intRet = new int[nums];
        int intRd = 0; //存放随机数
        int count = 0; //记录生成的随机数个数
        int flag = 0; //是否已经生成过标志
        while (count < intRet.length) {
            intRd = min + (int) (Math.random() * max);
            for (int i = 0; i < count; i++) {
                if (intRet[i] == intRd) {
                    flag = 1;
                    break;
                } else {
                    flag = 0;
                }
            }
            if (flag == 0) {
                intRet[count] = intRd;
                count++;
            }
        }
        return intRet;
    }

    /**
     * 生成可以重复的随机数
     * create  wjz
     **/
    public static int[] randomJRepeat(int min, int max, int nums) {

        int[] intRet = new int[nums];
        int intRd = 0; //存放随机数
        int count = 0; //记录生成的随机数个数
        while (count < intRet.length) {
            intRd = min + (int) (Math.random() * max);
            intRet[count] = intRd;
            count++;
        }
        return intRet;
    }

    /**
     * 计算排列数，即A(n, m) = n!/(n-m)!
     *
     * @param n
     * @param m
     * @return
     */
    public static long arrangement(int n, int m) {
        return (n >= m) ? factorial(n) / factorial(n - m) : 0;
    }


    public static boolean CallResultOK(String jsonString) {

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

    /**
     * 获取json里面result下的数据
     * create  wjz
     **/
    public static String getResultStr(String jsonString) {
        String result = "";
        try {
            result = new JSONObject(jsonString).getString("result");


            return result;
        } catch (JSONException e) {
            e.printStackTrace();
            return result;
        }
    }

    public static Gson gson = new Gson();

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }

    /**
     * 转成list
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> ArrayList<T> fromJsonList(String gsonString, Class<T> cls) {
        ArrayList<T> mList = new ArrayList<T>();
        JsonArray array = new JsonParser().parse(gsonString).getAsJsonArray();
        for (final JsonElement elem : array) {
            mList.add(gson.fromJson(elem, cls));
        }
        return mList;
    }

    public static String getNUmberForString(String str) {
        str = str.trim();
        String str2 = "";
        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                }
            }
        }
        return str2;
    }
}
