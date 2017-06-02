package com.pi.small.goal.my.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.FollowAdapter;
import com.pi.small.goal.my.entry.FollowEntry;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.GosnUtil;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

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
        getData();
    }

    //{"msg":"success","code":0,
// "result":[{"followId":1,"userId":26,"followUserId":31,"nick":"张洋","avatar":"https://wx.qlogo.cn/mmopen/SK4ycmXqictWLtQbUyjGw4o4yzlcY5AEZevEib7zkIJqwuiamTibn4cImYk3Tb0fJOqv92ykvlObc2j0gu6Nv3az2VtBniavZHOiay/0"},
// {"followId":2,"userId":26,"followUserId":8,"nick":"ee","avatar":"jpg"},
// {"followId":3,"userId":26,"followUserId":9,"nick":"ff","avatar":"jpg"}],"pageNum":0,"pageSize":0,"pageTotal":0,"total":0}
    private void getData() {
        SharedPreferences sp = Utils.UserSharedPreferences(this);
        RequestParams requestParams = new RequestParams(Url.Url + "/user/followed");
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("userId", "26");
        x.http().post(requestParams, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    List<FollowEntry> data = GosnUtil.parseJsonArrayWithGson(jsonObject.get("result").toString(), FollowEntry.class);
                    adapter.setData(data);

                } catch (JSONException e) {
                }
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {

            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });

    }

    @Override
    public void initWeight() {
        super.initWeight();
    }


}
