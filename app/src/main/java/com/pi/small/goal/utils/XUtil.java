package com.pi.small.goal.utils;

import android.content.Context;
import android.util.Log;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
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

    public static void post(final RequestParams requestParams, final Context context, final XCallBackLinstener xCallBackLinstener) {

//      final LoadingDialog  loadingDialog = new LoadingDialog(context, "");
//
//        loadingDialog.show();

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Utils.showToast(context, Utils.getMsg(result));
                xCallBackLinstener.onSuccess(result);

                if (requestParams.getUri().equals(Url.Url + "/vote/vote")) {
                    CacheUtil.getInstance().getMap().put(KeyCode.AIM_VOTE, true);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                xCallBackLinstener.onError(ex, isOnCallback);
                //        loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                xCallBackLinstener.onFinished();
                //      loadingDialog.dismiss();
            }
        });

    }

    /**
     * x的gett方法，context用于显示和隐藏加载框
     * create  wjz
     **/

    public static <T> void get(RequestParams requestParams, final Context context, final XCallBackLinstener xCallBackLinstener) {
//        final LoadingDialog   loadingDialog = new LoadingDialog(context, "");
//
//        loadingDialog.show();

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Utils.showToast(context, Utils.getMsg(result));
                xCallBackLinstener.onSuccess(result);
                Log.v("TAg", Utils.getMsg(result));
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                xCallBackLinstener.onError(ex, isOnCallback);
                Log.v("TAg", ex.getMessage());
                //      loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {
            }

            @Override
            public void onFinished() {
                xCallBackLinstener.onFinished();
                //    loadingDialog.dismiss();
            }
        });

    }

//x.http().request(HttpMethod.PUT, requestParams, new Callback.CommonCallback<String>() {


    public static <T> void put(final RequestParams requestParams, final Context context, final XCallBackLinstener xCallBackLinstener) {
//        final LoadingDialog   loadingDialog = new LoadingDialog(context, "");
//
//        loadingDialog.show();


        x.http().request(HttpMethod.PUT, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Utils.showToast(context, Utils.getMsg(result));
                xCallBackLinstener.onSuccess(result);

                if (requestParams.getUri().equals(Url.Url + "/aim/dynamic/comment")) {
                    CacheUtil.getInstance().getMap().put(KeyCode.AIM_COMMENT, true);
                } else if (requestParams.getUri().equals(Url.Url + "/aim/dynamic")) {
                    CacheUtil.getInstance().getMap().put(KeyCode.AIM_SUPPORT, true);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                xCallBackLinstener.onError(ex, isOnCallback);
                //   loadingDialog.dismiss();
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
                xCallBackLinstener.onFinished();
                //      loadingDialog.dismiss();
            }
        });

    }
}
