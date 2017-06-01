package com.pi.small.goal.my.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.*;
import com.pi.small.goal.my.entry.WalletEntry;
import com.pi.small.goal.utils.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 9:22
 * 修改：
 * 描述：钱包明细
 **/
public class RedActivity extends BaseActivity {

    @InjectView(R.id.left_image_include)
    ImageView leftImageInclude;
    @InjectView(R.id.name_text_include)
    TextView nameTextInclude;
    @InjectView(R.id.right_image_include)
    ImageView rightImageInclude;
    @InjectView(R.id.plv_wallet)
    PullToRefreshListView plvWallet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_red);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initData() {
        super.initData();
        nameTextInclude.setText("我的钱包");
        rightImageInclude.setImageResource(R.mipmap.list_btn);

        List<WalletEntry> data = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            if (i == 0 || i == 6) {
                data.add(new WalletEntry(0));
            } else {
                data.add(new WalletEntry(1));
            }
        }
        RedAdapter adapter = new RedAdapter(this, data);
        plvWallet.setAdapter(adapter);
    }

    @Override
    public void initWeight() {
        super.initWeight();
        rightImageInclude.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.right_image_include:
                startActivity(new Intent(this, RedMoreActivity.class));
                break;
        }
    }
}
