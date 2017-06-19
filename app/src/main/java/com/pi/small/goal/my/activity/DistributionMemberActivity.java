package com.pi.small.goal.my.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.BaseActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class DistributionMemberActivity extends BaseActivity {

    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.member_list)
    ExpandableListView memberList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_distribution_member);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);
    }

    @OnClick(R.id.left_image)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                finish();
                break;
        }
    }
}
