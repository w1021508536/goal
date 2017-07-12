package com.small.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.small.small.goal.R;
import com.small.small.goal.my.adapter.TargetAdapter;
import com.small.small.goal.my.entry.CollectEntity;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Url;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.XUtil;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

import static com.small.small.goal.my.activity.AimOldActivity.KEY_IMG;
import static com.small.small.goal.my.activity.AimOldActivity.REQUEST_CHANGE_PHOTO;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 21:11
 * 描述： 我的目标
 * 修改：
 **/
public class AimActivity extends BaseActivity {

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
    private TargetAdapter adapter;

    private int page = 1;
    private int position;
    private boolean addFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_target);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("我的目标");
        rightImageInclude.setImageResource(R.mipmap.icon_aim_history2x);

        adapter = new TargetAdapter(this);
        plv.setAdapter(adapter);

    }

    @Override
    public void initWeight() {
        super.initWeight();
        rightImageInclude.setOnClickListener(this);
        plv.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ListView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ListView> pullToRefreshBase) {
                page = 1;
                getData();
            }
        });
        plv.setOnLastItemVisibleListener(new PullToRefreshBase.OnLastItemVisibleListener() {
            @Override
            public void onLastItemVisible() {
                if (!addFlag) return;
                page++;
                getData();
                addFlag=false;
            }
        });
    }

    @Override
    public void getData() {
        super.getData();
        RequestParams requestParams = Utils.getRequestParams(this);
        requestParams.setUri(Url.Url + "/aim");
        requestParams.addBodyParameter("userId", sp.getString(KeyCode.USER_ID, ""));
        requestParams.addBodyParameter("p", page + "");

        XUtil.get(requestParams, this, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if ((!RenameActivity.callOk(result) || Utils.getMsg(result).equals(KeyCode.NO_DATA)) && page == 1) {
//                    View emptyView = LayoutInflater.from(AimActivity.this).inflate(R.layout.view_empty_nodata, null);
//                    plvTarget.setEmptyView(emptyView);
                    plv.setVisibility(View.GONE);
                    return;
                }
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String jsonData = jsonObject.get("result").toString();
                    Gson gson = new Gson();
                    List<CollectEntity> data = gson.fromJson(jsonData, new TypeToken<List<CollectEntity>>() {
                    }.getType());
                    if (Integer.valueOf(jsonObject.get("pageNum").toString()) != 0) {
                        adapter.addData(data);

                    } else {
                        adapter.setData(data);
                    }
                    if (data.size() >= 9) {
                        addFlag = true;
                    }

                } catch (JSONException e) {
                }
                plv.onRefreshComplete();

            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });
        plv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                List<CollectEntity> data = adapter.getData();
                AimActivity.this.position = position - 1;
                Intent intent = new Intent(AimActivity.this, AimMoreActivity.class);
                intent.putExtra(AimMoreActivity.KEY_AIMID, data.get(position - 1).getId() + "");
                startActivityForResult(intent, REQUEST_CHANGE_PHOTO);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null) return;
        if (requestCode == REQUEST_CHANGE_PHOTO) {
            String photo = data.getStringExtra(KEY_IMG);
            adapter.getData().get(position).setImg(photo);
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_image_include:
                startActivity(new Intent(this, AimOldActivity.class));
                break;
        }
    }
}
