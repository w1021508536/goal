package com.small.small.goal.my.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.small.small.goal.R;
import com.small.small.goal.my.adapter.TrajectoryAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 15:43
 * 修改：
 * 描述：目标轨迹的fragment
 **/
public class TargetTrajectoryFragment extends Fragment {

    @InjectView(R.id.plv_trajectory)
    PullToRefreshListView plvTrajectory;

    public static TargetTrajectoryFragment newInstance() {

        Bundle args = new Bundle();

        TargetTrajectoryFragment fragment = new TargetTrajectoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_trajectory, null);

        ButterKnife.inject(this, view);
        initData();
        return view;
    }

    private void initData() {
        TrajectoryAdapter adapter = new TrajectoryAdapter(getContext());
        plvTrajectory.setAdapter(adapter);
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
