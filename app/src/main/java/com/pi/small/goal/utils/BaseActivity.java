package com.pi.small.goal.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;

import org.xutils.http.RequestParams;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/5/31 17:12
 * 修改：
 * 描述：
 **/
public class BaseActivity extends AppCompatActivity implements View.OnClickListener {

    public View view;
    public ImageView leftImageInclude;
    public SharedPreferences sp;
    public RequestParams requestParams;
    public MyApplication app;

    public PullToRefreshListView plv;
    public ImageView img_empty;
    public TextView tv_empty;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
        initWeight();
    }

    /**
     * 设置点击事件等
     * create  wjz
     **/
    public void initWeight() {
        if (leftImageInclude != null) {
            leftImageInclude.setOnClickListener(this);
        }
    }

    /**
     * 初始化數據
     * create  wjz
     **/

    public void initData() {

        app = (MyApplication) getApplication();
        app.addActivity(this);
        sp = Utils.UserSharedPreferences(this);
        requestParams = new RequestParams();

        view = findViewById(R.id.view);

        plv = (PullToRefreshListView) findViewById(R.id.plv);
        img_empty = (ImageView) findViewById(R.id.img_empty);
        tv_empty = (TextView) findViewById(R.id.tv_empty);

        if (view == null)
            return;
        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            view.setVisibility(View.GONE);
        }
        leftImageInclude = (ImageView) findViewById(R.id.left_image_include);
        if (!isNetworkConnected() && plv != null) {
            plv.setVisibility(View.GONE);
            if (img_empty != null)
                img_empty.setImageResource(R.mipmap.bg_net_wrong);
            if (tv_empty != null)
                tv_empty.setText("没 有 网 络 连 接 ~");
        } else
            getData();
    }

    /**
     * 获取网络数据
     * create  wjz
     **/

    public void getData() {
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.left_image_include:
                finish();
                break;
        }
    }

    /**
     * 检测网络是否可用
     *
     * @return
     */
    public boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnectedOrConnecting();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (XUtil.loadingDialog != null) {
//            System.out.println("========not===null=====destroy====");
//            XUtil.loadingDialog.dismiss();
//        }
    }
}
