package com.small.small.goal.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.small.small.goal.MyApplication;
import com.small.small.goal.R;
import com.small.small.goal.my.adapter.RedAdapter;
import com.small.small.goal.my.adapter.RedMoreAdapter;
import com.small.small.goal.my.dialog.MonthDialog;
import com.small.small.goal.my.entry.RedMoreAdapterEntry;
import com.small.small.goal.my.entry.RedMoreEntity;
import com.small.small.goal.utils.BaseActivity;
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
 * 时间： 2017/6/1 11:53
 * 描述：红包明细
 * 修改：
 **/
public class RedMoreActivity extends BaseActivity implements MonthDialog.OnDialogOkListener {

    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.plv)
    PullToRefreshListView plv;

    private int page = 1;
    private String startTime = "";
    private String endTime = "";
    private RedMoreAdapter adapter;
    private boolean addFlag;


    private int total;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_red_more);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("红包明细");
        rightImageInclude.setVisibility(View.GONE);
//        rightImageInclude.setImageResource(R.mipmap.btn_filter);
        MonthDialog dialog = new MonthDialog(this, this);
        adapter = new RedMoreAdapter(this, dialog);

        plv.setAdapter(adapter);
//        View emptyView = LayoutInflater.from(this).inflate(R.layout.view_empty_nodata, null);
//        plv.setEmptyView(emptyView);

        leftImageInclude.setOnClickListener(this);
    }

    @Override
    public void getData() {
        super.getData();
        RequestParams requestParams = new RequestParams();
        requestParams.setUri(Url.Url + "/redpacket");
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("startTime", startTime);
        requestParams.addBodyParameter("endTime", endTime);
        requestParams.addBodyParameter("p", page + "");
        requestParams.addBodyParameter("r", "10");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
//{"msg":"no data","code":100000,"pageNum":0,"pageSize":0,"pageTotal":0,"total":0}

                System.out.println("===============result=========" + result);

                if ((!RenameActivity.callOk(result) || Utils.getMsg(result).equals(KeyCode.NO_DATA)) && page == 1) {
                    plv.setVisibility(View.GONE);
                    return;
                }
                try {

                    total = Integer.valueOf(new JSONObject(result).getString("total"));
                    if (total < 10) {
                        plv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
                    } else
                        plv.setMode(PullToRefreshBase.Mode.BOTH);
                    JSONObject jsonObject = new JSONObject(result);
                    String jsonData = jsonObject.get("result").toString();
                    Gson gson = new Gson();
                    List<RedMoreEntity> data = gson.fromJson(jsonData, new TypeToken<List<RedMoreEntity>>() {
                    }.getType());

                    List<RedMoreAdapterEntry> newData = setTimeData(data);
                    if (Integer.valueOf(jsonObject.get("pageNum").toString()) != 1) {
                        adapter.addData(newData);
                    } else
                        adapter.setData(newData);

                    if (newData.size() > 9) {
                        addFlag = true;
                    }

                } catch (JSONException e) {
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                plv.onRefreshComplete();
            }

            @Override
            public void onFinished() {
                plv.onRefreshComplete();
            }
        });
    }

    /**
     * 将redmoreentity数据组装成redmoreadapterentity  加了标题部分
     * create  wjz
     **/
    private List<RedMoreAdapterEntry> setTimeData(List<RedMoreEntity> data) {

        Map<String, List<RedMoreEntity>> timeData = new HashMap<>();
//先将数据根据时间归为几个集合
        for (RedMoreEntity one : data) {

            Date date = new Date(one.getCreateTime());
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月");//设置日期格式
            String time = df.format(date);// new Date()为获取当前系统时间
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
                List<RedMoreEntity> oneDatas = new ArrayList<>();
                oneDatas.add(one);
                timeData.put(time, oneDatas);
            }
        }
//在将数据取出来，将总收入和总支出算出来
        Set<String> titles = timeData.keySet();
        List<RedMoreAdapterEntry> adapterData = new ArrayList<>();
        for (String title : titles) {
            int titlePosition = adapterData.size();
            adapterData.add(new RedMoreAdapterEntry(RedAdapter.TYPE_TITLE, title, 0, 0));
            List<RedMoreEntity> redMoreEntities = timeData.get(title);
            float addMoney = 0;
            for (RedMoreEntity one : redMoreEntities) {
                //  (int redPacketRecordId, int userId, int money, int packetId, long createTime, int type, int fromUserId, int titleType)
                adapterData.add(new RedMoreAdapterEntry(one.getRedPacketRecordId(), one.getUserId(), one.getMoney(), one.getPacketId(), one.getCreateTime(), one.getType(), one.getFromUserId(), one.getFromUserNick(), one.getFromUserAvatar(), RedAdapter.TYPE_CONTENT));
                addMoney += one.getMoney();
            }
            adapterData.get(titlePosition).setAddMoney(addMoney);
        }
        return adapterData;
    }


    @Override
    public void initWeight() {
        super.initWeight();
        plv.setMode(PullToRefreshBase.Mode.BOTH);
//        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
//                page = 1;
//                getData();
//            }
//        });
//        plv.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
//            @Override
//            public void onLastItemVisible() {
//                if (startTime.equals("") && addFlag) {
//                    page++;
//                    getData();
//                    addFlag = false;
//                }
//            }
//        });

        /**
         * 实现 接口  OnRefreshListener2<ListView>  以便与监听  滚动条到顶部和到底部
         */
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //   new GetDownDataTask().execute();
                page = 1;
                getData();
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //        new GetUpDataTask().execute();

                if (page * 10 >= total) {
                    plv.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Utils.showToast(RedMoreActivity.this, "没有更多数据了");
                            plv.onRefreshComplete();
                        }
                    }, 1000);
                } else {

                    if (startTime.equals("") && addFlag) {
                        page++;
                        getData();
                        addFlag = false;
                    }
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.right_image_include:

                break;
            case R.id.left_image_include:
                finish();
                break;
        }
    }

    /**
     * 日历的dialog 确定之后
     * create  wjz
     **/

    @Override
    public void getSelectTime(String time) {

        startTime = time;
        page = 1;
        getData();

    }
}
