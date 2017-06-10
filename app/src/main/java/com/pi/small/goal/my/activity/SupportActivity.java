package com.pi.small.goal.my.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.SupportAdapter;
import com.pi.small.goal.my.entry.TargetHeadEntity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.CacheUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/7 15:33
 * 修改：
 * 描述：助力的人的
 **/
public class SupportActivity extends BaseActivity {

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
    @InjectView(R.id.lv_support)
    ListView lvSupport;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_support);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("助力");
        rightImageInclude.setVisibility(View.GONE);

        List<TargetHeadEntity.SupportsBean> supportEntityList = CacheUtil.getInstance().getSupportEntityList();

        SupportAdapter adapter = new SupportAdapter(this);
        adapter.setData(supportEntityList);
        lvSupport.setAdapter(adapter);

    }


}
