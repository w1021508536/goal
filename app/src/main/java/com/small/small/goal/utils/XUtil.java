package com.small.small.goal.utils;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.View;

import com.small.small.goal.R;
import com.small.small.goal.my.dialog.LoadingDialog;

import org.xutils.common.Callback;
import org.xutils.http.HttpMethod;
import org.xutils.http.RequestParams;
import org.xutils.x;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/3 11:24
 * 修改：
 * 描述：对x.http 的二次封装，方便显示加载框和出错页
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

        final LoadingDialog loadingDialog = new LoadingDialog(context, "");

        if (((Activity) context).isDestroyed()) {

        } else
            loadingDialog.show();

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //     Utils.showToast(context, Utils.getMsg(result));
                loadingDialog.dismiss();
                xCallBackLinstener.onSuccess(result);

                if (requestParams.getUri().equals(Url.Url + "/vote/vote")) {
                    CacheUtil.getInstance().getMap().put(KeyCode.AIM_VOTE, true);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();
                xCallBackLinstener.onError(ex, isOnCallback);
             //   Log.v("TAg", ex.getMessage());

                if (context instanceof BaseActivity) {                          //访问出错展示的出错图

                    final BaseActivity activity = (BaseActivity) context;
                    if (activity.tv_empty != null) {
                        activity.tv_empty.setText("出 错！点 击 重 新 尝 试 ~");
                    }
                    if (activity.plv != null) {
                        activity.plv.setVisibility(View.GONE);
                    }

                    if (activity.img_empty != null) {
                        activity.img_empty.setImageResource(R.mipmap.bg_wrong);
                        activity.img_empty.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.getData();
                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(CancelledException cex) {
                loadingDialog.dismiss();
            }

            @Override
            public void onFinished() {
//                loadingDialog.dismiss();
                xCallBackLinstener.onFinished();
            }
        });

    }

    /**
     * x的gett方法，context用于显示和隐藏加载框
     * create  wjz
     **/

    public static <T> void get(RequestParams requestParams, final Context context, final XCallBackLinstener xCallBackLinstener) {

        final LoadingDialog loadingDialog = new LoadingDialog(context, "");

        if (((Activity) context).isDestroyed()) {

        } else
            loadingDialog.show();

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //        Utils.showToast(context, Utils.getMsg(result));
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();
                xCallBackLinstener.onSuccess(result);
                Log.v("TAg", Utils.getMsg(result));
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();
                xCallBackLinstener.onError(ex, isOnCallback);
                Log.v("TAg", ex.getMessage());

                if (context instanceof BaseActivity) {                          //访问出错展示的出错图

                    final BaseActivity activity = (BaseActivity) context;
                    if (activity.tv_empty != null) {
                        activity.tv_empty.setText("出 错！点 击 重 新 尝 试 ~");
                    }

                    if (activity.plv != null) {
                        activity.plv.setVisibility(View.GONE);
                    }

                    if (activity.img_empty != null) {
                        activity.img_empty.setImageResource(R.mipmap.bg_wrong);
                        activity.img_empty.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                activity.getData();
                            }
                        });
                    }
                }
//                Log.v("TAg", ex.getMessage());
            }

            @Override
            public void onCancelled(CancelledException cex) {
                System.out.println("========not===null===get==cancelled====");
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();
            }

            @Override
            public void onFinished() {
                xCallBackLinstener.onFinished();
//                loadingDialog.dismiss();
            }
        });

    }

//x.http().request(HttpMethod.PUT, requestParams, new Callback.CommonCallback<String>() {


    public static <T> void put(final RequestParams requestParams, final Context context, final XCallBackLinstener xCallBackLinstener) {
        final LoadingDialog loadingDialog = new LoadingDialog(context, "");

        if (((Activity) context).isDestroyed()) {

        } else
            loadingDialog.show();


        x.http().request(HttpMethod.PUT, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //        Utils.showToast(context, Utils.getMsg(result));
                loadingDialog.dismiss();
                xCallBackLinstener.onSuccess(result);

                if (requestParams.getUri().equals(Url.Url + "/aim/dynamic/comment")) {
                    CacheUtil.getInstance().getMap().put(KeyCode.AIM_COMMENT, true);
                } else if (requestParams.getUri().equals(Url.Url + "/aim/support")) {
                    CacheUtil.getInstance().getMap().put(KeyCode.AIM_SUPPORT, true);
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                loadingDialog.dismiss();
                xCallBackLinstener.onError(ex, isOnCallback);
            }

            @Override
            public void onCancelled(CancelledException cex) {
                loadingDialog.dismiss();
            }

            @Override
            public void onFinished() {
                xCallBackLinstener.onFinished();
//                loadingDialog.dismiss();
            }
        });

    }


}
