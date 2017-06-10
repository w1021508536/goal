package com.pi.small.goal.utils;

import android.content.Context;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/3 11:24
 * 修改：
 * 描述：
 **/
public class XUtil {

    public interface XCallBackLinstener {
        void onSuccess(String result);

        void onError(Throwable ex, boolean isOnCallback);

        void onFinished();
    }

    /**
     * x的post方法，context用于显示和隐藏加载框
     * create  wjz
     **/

    public static void post(RequestParams requestParams, Context context, final XCallBackLinstener xCallBackLinstener) {


        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                xCallBackLinstener.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                xCallBackLinstener.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                xCallBackLinstener.onFinished();
            }
        });

    }

    /**
     * x的gett方法，context用于显示和隐藏加载框
     * create  wjz
     **/

    public static <T> void get(RequestParams requestParams, final Context context, final XCallBackLinstener xCallBackLinstener) {


        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Utils.showToast(context, Utils.getMsg(result));
                xCallBackLinstener.onSuccess(result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                xCallBackLinstener.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                xCallBackLinstener.onFinished();
            }
        });

    }

}
