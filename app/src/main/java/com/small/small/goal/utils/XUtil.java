package com.small.small.goal.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;

import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.login.LoginActivity;
import com.small.small.goal.my.dialog.LoadingDialog;

import org.json.JSONException;
import org.json.JSONObject;
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
        final MyApplication app = (MyApplication) context.getApplicationContext();
        final SharedPreferences sp = Utils.UserSharedPreferences(context);
        if (((Activity) context).isDestroyed()) {

        } else
            loadingDialog.show();

        x.http().post(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //     Utils.showToast(context, Utils.getMsg(result));
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();
                try {
                    if (new JSONObject(result).getString("code").equals("100001") && new JSONObject(result).getString("msg").equals("无效的token")) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        app.exit();
                        Utils.showToast(context, "账号登录过期，请重新登录");
                        context.startActivity(new Intent(context, LoginActivity.class));
                    } else {
                        xCallBackLinstener.onSuccess(result);
                        if (requestParams.getUri().equals(Url.Url + "/vote/vote")) {
                            CacheUtil.getInstance().getMap().put(KeyCode.AIM_VOTE, true);

                            for (int i = 0; i < CacheUtil.getInstance().getEveryTaskGsonEntityList().size(); i++) {
                                if (CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getUserTaskId() == 6) {

                                    if (CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getFinishTimes() < 10) {
                                        CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).setFinishTimes(CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getFinishTimes() + 1);
                                    }

                                }
                            }


                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();
                xCallBackLinstener.onError(ex, isOnCallback);
                //   Log.v("TAg", ex.getMessage());

                Utils.showToast(context, "网络异常，请稍后再试");

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
        final MyApplication app = (MyApplication) context.getApplicationContext();
        final SharedPreferences sp = Utils.UserSharedPreferences(context);
        if (((Activity) context).isDestroyed()) {

        } else
            loadingDialog.show();

        x.http().get(requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                //        Utils.showToast(context, Utils.getMsg(result));
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();
                try {
                    if (new JSONObject(result).getString("code").equals("100001") && new JSONObject(result).getString("msg").equals("无效的token")) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        app.exit();

                        Utils.showToast(context, "账号登录过期，请重新登录");
                        context.startActivity(new Intent(context, LoginActivity.class));
                    } else {
                        xCallBackLinstener.onSuccess(result);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();
                xCallBackLinstener.onError(ex, isOnCallback);

                Utils.showToast(context, "网络异常，请稍后再试");

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
        final MyApplication app = (MyApplication) context.getApplicationContext();
        final SharedPreferences sp = Utils.UserSharedPreferences(context);
        if (((Activity) context).isDestroyed()) {

        } else
            loadingDialog.show();


        x.http().request(HttpMethod.PUT, requestParams, new Callback.CommonCallback<String>() {
            @Override
            public void onSuccess(String result) {
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();

                try {
                    if (new JSONObject(result).getString("code").equals("100001") && new JSONObject(result).getString("msg").equals("无效的token")) {
                        SharedPreferences.Editor editor = sp.edit();
                        editor.clear();
                        editor.commit();
                        app.exit();
                        Utils.showToast(context, "账号登录过期，请重新登录");
                        context.startActivity(new Intent(context, LoginActivity.class));
                    } else {
                        xCallBackLinstener.onSuccess(result);
                        if (requestParams.getUri().equals(Url.Url + "/aim/dynamic/comment")) {
                            CacheUtil.getInstance().getMap().put(KeyCode.AIM_COMMENT, true);

                            for (int i = 0; i < CacheUtil.getInstance().getEveryTaskGsonEntityList().size(); i++) {
                                if (CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getUserTaskId() == 7) {

                                    if (CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getFinishTimes() < 5) {
                                        CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).setFinishTimes(CacheUtil.getInstance().getEveryTaskGsonEntityList().get(i).getFinishTimes() + 1);
                                    }

                                }
                            }

                        } else if (requestParams.getUri().equals(Url.Url + "/aim/support")) {
                            CacheUtil.getInstance().getMap().put(KeyCode.AIM_SUPPORT, true);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                if (loadingDialog.isShowing())
                    loadingDialog.dismiss();
                xCallBackLinstener.onError(ex, isOnCallback);

                Utils.showToast(context, "网络异常，请稍后再试");

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
