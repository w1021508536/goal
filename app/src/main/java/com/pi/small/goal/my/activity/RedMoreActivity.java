package com.pi.small.goal.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.RedAdapter;
import com.pi.small.goal.my.adapter.RedMoreAdapter;
import com.pi.small.goal.my.entry.RedMoreAdapterEntry;
import com.pi.small.goal.my.entry.RedMoreEntity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;

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
public class RedMoreActivity extends BaseActivity {

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

        List<RedMoreAdapterEntry> data = new ArrayList<>();

//        for (int i = 0; i < 10; i++) {
//            if (i == 0 || i == 6) {
//                data.add(new RedMoreAdapterEntry(0, 0, 0, 0, 0, 0, RedAdapter.TYPE_TITLE));
//            } else {
//                data.add(new RedMoreAdapterEntry(0, 0, 0, 0, 0, 0, RedAdapter.TYPE_CONTENT));
//            }
//        }
        adapter = new RedMoreAdapter(this);
        plv.setAdapter(adapter);
    }

    @Override
    public void getData() {
        super.getData();
        requestParams.setUri(Url.Url + "/redpacket");
//        requestParams.addHeader("token", sp.getString("token", ""));
//        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addHeader("token", "12345");
        requestParams.addHeader("deviceId", "11");
        //  requestParams.addBodyParameter("userId", "26");

        requestParams.addBodyParameter("startTime", startTime);
        requestParams.addBodyParameter("endTime", endTime);
        requestParams.addBodyParameter("p", page + "");
        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
//{"msg":"no data","code":100000,"pageNum":0,"pageSize":0,"pageTotal":0,"total":0}
                //    if (!RenameActivity.callOk(result)) return;
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.get("msg").equals("no data")) {
                        List<RedMoreEntity> data = new ArrayList<RedMoreEntity>();
                        data.add(new RedMoreEntity(1, 11, 8, 1, 1491300885000l, 1, 0));
                        data.add(new RedMoreEntity(2, 11, 8, 2, 1491300978000l, 1, 0));
                        setTimeData(data);
                        return;
                    }
                    String jsonData = jsonObject.get("result").toString();
                    Gson gson = new Gson();
                    List<RedMoreEntity> data = gson.fromJson(jsonData, new TypeToken<List<RedMoreEntity>>() {
                    }.getType());
//                    if (Integer.valueOf(jsonObject.get("pageNum").toString()) != 0) {
//                        adapter.addData(data);
//                    } else
//                        adapter.setData(data);
                    plv.onRefreshComplete();

                } catch (JSONException e) {
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    private void setTimeData(List<RedMoreEntity> data) {

        Map<String, List<RedMoreEntity>> timeData = new HashMap<>();

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

        Set<String> titles = timeData.keySet();
        List<RedMoreAdapterEntry> adapterData = new ArrayList<>();
        for (String title : titles) {
            adapterData.add(new RedMoreAdapterEntry(RedAdapter.TYPE_TITLE, title));
            List<RedMoreEntity> redMoreEntities = timeData.get(title);
            for (RedMoreEntity one : redMoreEntities) {
                //  (int redPacketRecordId, int userId, int money, int packetId, long createTime, int type, int fromUserId, int titleType)
                adapterData.add(new RedMoreAdapterEntry(one.getRedPacketRecordId(), one.getUserId(), one.getMoney(), one.getPacketId(), one.getCreateTime(), one.getType(), one.getFromUserId(), RedAdapter.TYPE_CONTENT));
            }
        }

        adapter.setData(adapterData);

    }


    @Override
    public void initWeight() {
        super.initWeight();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
    }
}
