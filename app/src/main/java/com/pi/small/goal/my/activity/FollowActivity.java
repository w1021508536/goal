package com.pi.small.goal.my.activity;

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
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.FollowAdapter;
import com.pi.small.goal.my.entry.FollowEntry;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 18:31
 * 描述：
 * 修改：我的关注
 **/
public class FollowActivity extends BaseActivity {

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
    @InjectView(R.id.plv_collect)
    PullToRefreshListView plvCollect;
    private FollowAdapter adapter;
    private int page = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        setContentView(R.layout.activity_follow);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        adapter = new FollowAdapter(this);
        plvCollect.setAdapter(adapter);
        nameTextInclude.setText("我的关注");
        rightImageInclude.setVisibility(View.GONE);
        getData();
    }


    /**
     * 获取数据
     * create  wjz
     **/

    //{"msg":"success","code":0,
// "result":[{"followId":1,"userId":26,"followUserId":31,"nick":"张洋","avatar":"https://wx.qlogo.cn/mmopen/SK4ycmXqictWLtQbUyjGw4o4yzlcY5AEZevEib7zkIJqwuiamTibn4cImYk3Tb0fJOqv92ykvlObc2j0gu6Nv3az2VtBniavZHOiay/0"},
// {"followId":2,"userId":26,"followUserId":8,"nick":"ee","avatar":"jpg"},
// {"followId":3,"userId":26,"followUserId":9,"nick":"ff","avatar":"jpg"}],"pageNum":0,"pageSize":0,"pageTotal":0,"total":0}
    @Override
    public void getData() {

        requestParams.setUri(Url.Url + "/user/followed");
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("userId", "26");
        requestParams.addBodyParameter("p", page + "");

        XUtil.post(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String jsonData = jsonObject.get("result").toString();
                    Gson gson = new Gson();
                    List<FollowEntry> data = gson.fromJson(jsonData, new TypeToken<List<FollowEntry>>() {
                    }.getType());
                    if (Integer.valueOf(jsonObject.get("pageNum").toString()) != 0) {
                        adapter.addData(data);
                    } else
                        adapter.setData(data);
                    plvCollect.onRefreshComplete();

                } catch (JSONException e) {
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                plvCollect.onRefreshComplete();
            }

            @Override
            public void onFinished() {
                plvCollect.onRefreshComplete();
            }
        });
    }

    @Override
    public void initWeight() {
        super.initWeight();

        plvCollect.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                page = 1;
                getData();
            }
        });
        plvCollect.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                page++;
                getData();
            }
        });

    }


}
