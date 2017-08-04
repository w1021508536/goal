package com.small.small.goal.my.mall.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.gold.GoldListActivity;
import com.small.small.goal.my.mall.adapter.HuafeiAdapter;
import com.small.small.goal.my.mall.adapter.LiuliangAdapter;
import com.small.small.goal.my.mall.adapter.QbAdapter;
import com.small.small.goal.my.mall.adapter.RecordAdapter;
import com.small.small.goal.my.mall.entity.HuafeiGosnEntity;
import com.small.small.goal.my.mall.entity.LiuliangGsonEntity;
import com.small.small.goal.my.mall.entity.QbGsonEntity;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/11 9:10
 * 修改：
 * 描述：记录的fragment
 **/
public class RecordFragment extends Fragment implements View.OnClickListener {

    @InjectView(R.id.lv)
    PullToRefreshListView lv;
    public final static int TYPE_HUAFEI = 0;
    public final static int TYPE_LIULIANG = 1;
    public final static int TYPE_QB = 2;
    @InjectView(R.id.img_empty)
    ImageView imgEmpty;
    @InjectView(R.id.tv_empty)
    TextView tvEmpty;
    @InjectView(R.id.null_layout)
    RelativeLayout nullLayout;
    private RecordAdapter adapter;
    private int page = 1;
    private boolean addFlag = true;
    private String startTime;
    private int type;

    public static RecordFragment newInstance(int type) {

        Bundle args = new Bundle();
        args.putInt(KeyCode.INTENT_FLAG, type);
        RecordFragment fragment = new RecordFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void setTime(String startTime) {
        this.startTime = startTime;
        initData();
        page = 1;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_record, null);
        ButterKnife.inject(this, view);
        type = getArguments().getInt(KeyCode.INTENT_FLAG, TYPE_HUAFEI);
        initData();
        initListener();
        return view;
    }

    private void initListener() {
        lv.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                page++;
                switch (type) {
                    case TYPE_HUAFEI:
                        getHuafeiData();
                        break;
                    case TYPE_LIULIANG:
                        getLiuliangData();
                        break;
                    case TYPE_QB:
                        getQbData();
                        break;
                }
            }
        });
    }

    private void initData() {
        switch (type) {
            case TYPE_HUAFEI:
                if (this != null) {
                    if (Utils.isNetworkConnected(getActivity())) {
                        nullLayout.setClickable(false);
                        nullLayout.setVisibility(View.GONE);
                        page = 1;
                        getHuafeiData();
                        adapter = new HuafeiAdapter(getActivity());
                        lv.setAdapter(adapter);
                    } else {
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                        tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                    }
                }

                break;
            case TYPE_LIULIANG:
                if (this != null) {
                    if (Utils.isNetworkConnected(getActivity())) {
                        nullLayout.setClickable(false);
                        nullLayout.setVisibility(View.GONE);
                        page = 1;
                        getLiuliangData();
                        adapter = new LiuliangAdapter(getActivity());
                        lv.setAdapter(adapter);
                    } else {
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                        tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                    }
                }

                break;
            case TYPE_QB:
                if (this != null) {
                    if (Utils.isNetworkConnected(getActivity())) {
                        nullLayout.setClickable(false);
                        nullLayout.setVisibility(View.GONE);
                        page = 1;
                        getQbData();
                        adapter = new QbAdapter(getActivity());
                        lv.setAdapter(adapter);
                    } else {
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                        tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                    }
                }

                break;
        }


        nullLayout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.null_layout:
                switch (type) {
                    case TYPE_HUAFEI:
                        if (this != null) {
                            if (Utils.isNetworkConnected(getActivity())) {
                                nullLayout.setClickable(false);
                                nullLayout.setVisibility(View.GONE);
                                page = 1;
                                getHuafeiData();
                                adapter = new HuafeiAdapter(getActivity());
                                lv.setAdapter(adapter);
                            } else {
                                Utils.showToast(getActivity(), "请检查网络是否连接");
                                nullLayout.setClickable(true);
                                nullLayout.setVisibility(View.VISIBLE);
                                imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                                tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                            }
                        }

                        break;
                    case TYPE_LIULIANG:
                        if (this != null) {
                            if (Utils.isNetworkConnected(getActivity())) {
                                nullLayout.setClickable(false);
                                nullLayout.setVisibility(View.GONE);
                                page = 1;
                                getLiuliangData();
                                adapter = new LiuliangAdapter(getActivity());
                                lv.setAdapter(adapter);
                            } else {
                                Utils.showToast(getActivity(), "请检查网络是否连接");
                                nullLayout.setClickable(true);
                                nullLayout.setVisibility(View.VISIBLE);
                                imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                                tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                            }
                        }

                        break;
                    case TYPE_QB:
                        if (this != null) {
                            if (Utils.isNetworkConnected(getActivity())) {
                                nullLayout.setClickable(false);
                                nullLayout.setVisibility(View.GONE);
                                page = 1;
                                getQbData();
                                adapter = new QbAdapter(getActivity());
                                lv.setAdapter(adapter);
                            } else {
                                Utils.showToast(getActivity(), "请检查网络是否连接");
                                nullLayout.setClickable(true);
                                nullLayout.setVisibility(View.VISIBLE);
                                imgEmpty.setImageResource(R.mipmap.bg_net_wrong);
                                tvEmpty.setText("网 络 异 常! 请 点 击 刷 新");
                            }
                        }

                        break;
                }
                break;
        }
    }

    /**
     * 获取的列表
     * create  wjz
     **/
    private void getQbData() {

        if (!addFlag) return;


        RequestParams requestParams = new RequestParams(Url.Url + "/charge/qq/record");
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);

//        requestParams.addBodyParameter("p", page + "");
//        requestParams.addBodyParameter("n", "20");
        XUtil.get(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("============QQ=====" + result);

                List<QbGsonEntity> data = new ArrayList<QbGsonEntity>();

                try {
                    String code = new JSONObject(result).getString("code");

                    if (code.equals("0")) {

                        data = Utils.fromJsonList(Utils.getResultStr(result), QbGsonEntity.class);

                        if (page == 1)
                            (adapter).setData(setQbData(data));
                        else
                            adapter.addData(setQbData(data));
                        if (data.size() > 0) {
                            addFlag = true;
                        } else {
                            addFlag = false;
                        }
                    } else if (code.equals("100000")) {
                        if (page == 1) {
                            data.clear();
                        }
                        if (data.size() == 0) {
                            data.clear();
                            adapter.setData(setQbData(data));
                            nullLayout.setClickable(false);
                            nullLayout.setVisibility(View.VISIBLE);
                            imgEmpty.setImageResource(R.mipmap.bg_null_data);
                            tvEmpty.setText("暂 时 没 有 任 何 数 据 ~");
                        }
                    } else {
                        data.clear();
                        adapter.setData(setQbData(data));
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_wrong);
                        tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("============QQ==EX===" + ex.getMessage());
                List<QbGsonEntity> data = new ArrayList<QbGsonEntity>();
                adapter.setData(setQbData(data));
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 获取话费的列表
     * create  wjz
     **/
    private void getHuafeiData() {

        if (!addFlag) return;

        RequestParams requestParams = new RequestParams(Url.Url + "/charge/phone/record");
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);

//        requestParams.addBodyParameter("p", page + "");
//        requestParams.addBodyParameter("n", "20");
        XUtil.get(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("============话费=====" + result);
                List<HuafeiGosnEntity> data = new ArrayList<HuafeiGosnEntity>();
                try {
                    String code = new JSONObject(result).getString("code");

                    if (code.equals("0")) {

                        data = Utils.fromJsonList(Utils.getResultStr(result), HuafeiGosnEntity.class);

                        if (page == 1)
                            (adapter).setData(setHuafeiTimeData(data));
                        else
                            adapter.addData(setHuafeiTimeData(data));
                        if (data.size() > 0) {
                            addFlag = true;
                        } else {
                            addFlag = false;
                        }
                    } else if (code.equals("100000")) {
                        if (page == 1) {
                            data.clear();
                        }
                        if (data.size() == 0) {
                            data.clear();
                            adapter.setData(setHuafeiTimeData(data));
                            nullLayout.setClickable(false);
                            nullLayout.setVisibility(View.VISIBLE);
                            imgEmpty.setImageResource(R.mipmap.bg_null_data);
                            tvEmpty.setText("暂 时 没 有 任 何 数 据 ~");
                        }
                    } else {
                        data.clear();
                        adapter.setData(setHuafeiTimeData(data));
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_wrong);
                        tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("============话费===ex==" + ex.getMessage());
                List<HuafeiGosnEntity> data = new ArrayList<HuafeiGosnEntity>();
                adapter.setData(setHuafeiTimeData(data));
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void getLiuliangData() {

        if (!addFlag) return;

        RequestParams requestParams = new RequestParams(Url.Url + "/charge/flow/record");
        requestParams.addHeader("token", Utils.GetToken(getActivity()));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
//        requestParams.addBodyParameter("p", page + "");
//        requestParams.addBodyParameter("n", "20");
        XUtil.get(requestParams, getActivity(), new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                System.out.println("============流量=====" + result);
                List<LiuliangGsonEntity> data = new ArrayList<LiuliangGsonEntity>();

                try {
                    String code = new JSONObject(result).getString("code");

                    if (code.equals("0")) {

                        data = Utils.fromJsonList(Utils.getResultStr(result), LiuliangGsonEntity.class);

                        if (page == 1)
                            (adapter).setData(setLiuliangTimeData(data));
                        else
                            adapter.addData(setLiuliangTimeData(data));
                        if (data.size() > 0) {
                            addFlag = true;
                        } else {
                            addFlag = false;
                        }
                    } else if (code.equals("100000")) {
                        if (page == 1) {
                            data.clear();
                        }
                        if (data.size() == 0) {
                            data.clear();
                            adapter.setData(setLiuliangTimeData(data));
                            nullLayout.setClickable(false);
                            nullLayout.setVisibility(View.VISIBLE);
                            imgEmpty.setImageResource(R.mipmap.bg_null_data);
                            tvEmpty.setText("暂 时 没 有 任 何 数 据 ~");
                        }
                    } else {
                        data.clear();
                        adapter.setData(setLiuliangTimeData(data));
                        Utils.showToast(getActivity(), new JSONObject(result).getString("msg"));
                        nullLayout.setClickable(true);
                        nullLayout.setVisibility(View.VISIBLE);
                        imgEmpty.setImageResource(R.mipmap.bg_wrong);
                        tvEmpty.setText("出 错! 点 击 重 新 尝 试");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                System.out.println("============流量===ex==" + ex.getMessage());
                List<LiuliangGsonEntity> data = new ArrayList<LiuliangGsonEntity>();
                adapter.setData(setLiuliangTimeData(data));
                nullLayout.setClickable(true);
                nullLayout.setVisibility(View.VISIBLE);
                imgEmpty.setImageResource(R.mipmap.bg_wrong);
                tvEmpty.setText("出 错! 点 击 重 新 尝 试");
            }

            @Override
            public void onFinished() {

            }
        });
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    /**
     * 加了标题部分
     * create  wjz
     **/
    private List<HuafeiGosnEntity> setHuafeiTimeData(List<HuafeiGosnEntity> data) {

        Map<String, List<HuafeiGosnEntity>> timeData = new HashMap<>();
//先将数据根据时间归为几个集合
        for (HuafeiGosnEntity one : data) {

            Date date = new Date(Long.valueOf(one.getCreateTime()));
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");//设置日期格式
            String time =df.format(date);
            //    String[] times = format.split("-");
            Set<String> strings = timeData.keySet();
            boolean havaTimeOk = false;
            for (String str : strings) {
                if (str.equals(time)) {
                    timeData.get(time).add(one);
                    havaTimeOk = true;
                    break;
                }
            }
            if (!havaTimeOk) {
                List<HuafeiGosnEntity> oneDatas = new ArrayList<>();
                oneDatas.add(one);
                timeData.put(time, oneDatas);
            }
        }
//在将数据取出来，根据时间归类
        Set<String> titles = timeData.keySet();
        List<HuafeiGosnEntity> adapterData = new ArrayList<>();
        for (String title : titles) {
            HuafeiGosnEntity huafeiGosnEntity = new HuafeiGosnEntity();
            huafeiGosnEntity.setTitle(title);
            huafeiGosnEntity.setType(HuafeiAdapter.ITEM_TYPE_TITLE);
            adapterData.add(huafeiGosnEntity);
            adapterData.addAll(timeData.get(title));
        }
        try {
            if (((HuafeiGosnEntity) adapter.getData().get(0)).getCreateTime().split(" ").equals(adapterData.get(1).getCreateTime().split(" ")[0])) {
                adapterData.remove(0);
            }
        } catch (Exception e) {
        }
        return adapterData;
    }

    /**
     * 加了标题部分
     * create  wjz
     **/
    private List<LiuliangGsonEntity> setLiuliangTimeData(List<LiuliangGsonEntity> data) {

        Map<String, List<LiuliangGsonEntity>> timeData = new HashMap<>();
//先将数据根据时间归为几个集合
        for (LiuliangGsonEntity one : data) {

            Date date = new Date(Long.valueOf(one.getCreateTime()));
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");//设置日期格式
            String time =df.format(date);
            //    String[] times = format.split("-");
            Set<String> strings = timeData.keySet();
            boolean havaTimeOk = false;
            for (String str : strings) {
                if (str.equals(time)) {
                    timeData.get(time).add(one);
                    havaTimeOk = true;
                    break;
                }
            }
            if (!havaTimeOk) {
                List<LiuliangGsonEntity> oneDatas = new ArrayList<>();
                oneDatas.add(one);
                timeData.put(time, oneDatas);
            }
        }
//在将根据时间归类
        Set<String> titles = timeData.keySet();
        List<LiuliangGsonEntity> adapterData = new ArrayList<>();
        for (String title : titles) {
            LiuliangGsonEntity huafeiGosnEntity = new LiuliangGsonEntity();
            huafeiGosnEntity.setTitle(title);
            huafeiGosnEntity.setType(HuafeiAdapter.ITEM_TYPE_TITLE);
            adapterData.add(huafeiGosnEntity);
            adapterData.addAll(timeData.get(title));
        }
        try {
            if (((LiuliangGsonEntity) adapter.getData().get(0)).getCreateTime().split(" ").equals(adapterData.get(1).getCreateTime().split(" ")[0])) {
                adapterData.remove(0);
            }
        } catch (Exception e) {
        }
        return adapterData;
    }

    /**
     * 加了标题部分
     * create  wjz
     **/
    private List<QbGsonEntity> setQbData(List<QbGsonEntity> data) {

        Map<String, List<QbGsonEntity>> timeData = new HashMap<>();
//先将数据根据时间归为几个集合
        for (QbGsonEntity one : data) {

            Date date = new Date(Long.valueOf(one.getCreateTime()));
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");//设置日期格式
            String time =df.format(date);
            //    String[] times = format.split("-");
            Set<String> strings = timeData.keySet();
            boolean havaTimeOk = false;
            for (String str : strings) {
                if (str.equals(time)) {
                    timeData.get(time).add(one);
                    havaTimeOk = true;
                    break;
                }
            }
            if (!havaTimeOk) {
                List<QbGsonEntity> oneDatas = new ArrayList<>();
                oneDatas.add(one);
                timeData.put(time, oneDatas);
            }
        }
//在将数据取出来，将总收入和总支出算出来
        Set<String> titles = timeData.keySet();
        List<QbGsonEntity> adapterData = new ArrayList<>();
        for (String title : titles) {
            QbGsonEntity huafeiGosnEntity = new QbGsonEntity();
            huafeiGosnEntity.setTitle(title);
            huafeiGosnEntity.setType(HuafeiAdapter.ITEM_TYPE_TITLE);
            adapterData.add(huafeiGosnEntity);
            adapterData.addAll(timeData.get(title));
        }
        try {
            if (((QbGsonEntity) adapter.getData().get(0)).getCreateTime().split(" ").equals(adapterData.get(1).getCreateTime().split(" ")[0])) {
                adapterData.remove(0);
            }
        } catch (Exception e) {
        }
        return adapterData;
    }


}
