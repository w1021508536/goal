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
import com.pi.small.goal.my.adapter.TransferMoreAdapter;
import com.pi.small.goal.my.dialog.MonthDialog;
import com.pi.small.goal.my.entry.TransferMoreEntity;
import com.pi.small.goal.my.entry.TransferMoreGsonEntity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.KeyCode;
import com.pi.small.goal.utils.TimeUtils;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.xutils.http.RequestParams;

import java.sql.Timestamp;
import java.text.ParseException;
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
 * 时间： 2017/6/16 16:15
 * 修改：
 * 描述： 期权转让明细
 **/
public class TransferMoreActivity extends BaseActivity implements MonthDialog.OnDialogOkListener {

    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.tv_ok_include)
    TextView tvOkInclude;
    @InjectView(R.id.plv)
    PullToRefreshListView plv;
    private String startTime = "";
    private String endTime = "";
    private TransferMoreAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_transfer_more);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("转让明细");
        rightImageInclude.setVisibility(View.GONE);

        MonthDialog monthDialog = new MonthDialog(this, this);
        adapter = new TransferMoreAdapter(this, monthDialog);
        plv.setAdapter(adapter);
    }

    @Override
    public void initWeight() {
        super.initWeight();

    }

    @Override
    public void getData() {
        super.getData();
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/option/transfer/log");
        requestParams.addBodyParameter("startTime", startTime);
        requestParams.addBodyParameter("endTime", endTime);

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!Utils.callOk(result)) return;

                Gson gson = new Gson();
                List<TransferMoreGsonEntity> data = gson.fromJson(Utils.getResult(result), new TypeToken<List<TransferMoreGsonEntity>>() {
                }.getType());

                List<TransferMoreEntity> transferMoreEntities = setTimeData(data);

                adapter.setData(transferMoreEntities);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
    }

    /**
     * 将gson解析的TransferMoreGsonEntity数据组装成TransferMoreEntity  加了标题部分
     * create  wjz
     **/
    private List<TransferMoreEntity> setTimeData(List<TransferMoreGsonEntity> data) {

        Map<String, List<TransferMoreGsonEntity>> timeData = new HashMap<>();
//先将数据根据时间归为几个集合
        for (TransferMoreGsonEntity one : data) {

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
                List<TransferMoreGsonEntity> oneDatas = new ArrayList<>();
                oneDatas.add(one);
                timeData.put(time, oneDatas);
            }
        }
//在将数据取出来，将总收入和总支出算出来
        Set<String> titles = timeData.keySet();
        List<TransferMoreEntity> adapterData = new ArrayList<>();
        for (String title : titles) {
            int titlePosition = adapterData.size();
            adapterData.add(new TransferMoreEntity(RedAdapter.TYPE_TITLE, title, 0, 0));
            List<TransferMoreGsonEntity> redMoreEntities = timeData.get(title);
            float addMoney = 0;
            float deleteMoney = 0;
            for (TransferMoreGsonEntity one : redMoreEntities) {
                //  (int redPacketRecordId, int userId, int money, int packetId, long createTime, int type, int fromUserId, int titleType)
                adapterData.add(new TransferMoreEntity(one.getId(), one.getAmount(), one.getFromUserId(), one.getToUserId(), one.getCreateTime(), RedAdapter.TYPE_CONTENT));
                if (one.getToUserId() == Integer.valueOf(sp.getString(KeyCode.USER_ID, "0"))) {
                    addMoney += one.getAmount();
                } else {
                    deleteMoney += one.getAmount();
                }
            }
            adapterData.get(titlePosition).setAddMoney(addMoney);
            adapterData.get(titlePosition).setDeleteMoney(deleteMoney);
        }
        return adapterData;
    }

    /**
     * 月份日历的点击月份的回掉
     * create  wjz
     **/

    @Override
    public void getSelectTime(String time) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Date date = null;
        try {
            date = df.parse(time);
            Timestamp monthStartTime = TimeUtils.getMonthStartTime(date);
            Timestamp monthEndTime = TimeUtils.getMonthEndTime(date);

            startTime = df.format(monthStartTime);
            endTime = df.format(monthEndTime);
            // startTime = time;
            getData();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
