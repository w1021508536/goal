package com.pi.small.goal.my.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.pi.small.goal.R;
import com.pi.small.goal.my.adapter.CommentAdapter;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/6 8:43
 * 修改：
 * 描述： 评论的fragment
 **/
public class CommentFragment extends Fragment {


    @InjectView(R.id.plv_comment)
    PullToRefreshListView plvComment;

    public static CommentFragment newInstance() {

        Bundle args = new Bundle();

        CommentFragment fragment = new CommentFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_comment, null);
        ButterKnife.inject(this,view);
        initData();
        return view;
    }

    private void initData() {

        CommentAdapter adapter = new CommentAdapter(getContext());
        plvComment.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }
}
