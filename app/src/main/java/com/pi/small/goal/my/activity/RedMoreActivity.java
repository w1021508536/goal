package com.pi.small.goal.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.RedAdapter;
import com.pi.small.goal.my.adapter.RedMoreAdapter;
import com.pi.small.goal.my.entry.RedMoreAdapterEntry;
import com.pi.small.goal.utils.BaseActivity;
import java.util.ArrayList;
import java.util.List;
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

        List<RedMoreAdapterEntry> data = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            if (i == 0 || i == 6) {
                data.add(new RedMoreAdapterEntry(0, 0, 0, 0, 0, 0, RedAdapter.TYPE_TITLE));
            } else {
                data.add(new RedMoreAdapterEntry(0, 0, 0, 0, 0, 0, RedAdapter.TYPE_CONTENT));
            }
        }
        RedMoreAdapter adapter = new RedMoreAdapter(this);
        adapter.setData(data);
        plv.setAdapter(adapter);
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
