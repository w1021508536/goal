package com.pi.small.goal.search;


import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.search.activity.AddFriendSearchActivity;
import com.pi.small.goal.search.activity.SearchKeyActivity;
import com.pi.small.goal.search.fragment.AttentionFragment;
import com.pi.small.goal.search.fragment.CityFragment;
import com.pi.small.goal.search.fragment.HotFragment;
import com.pi.small.goal.utils.PagerSlidingTabStrip;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment {


    @InjectView(R.id.view)
    View view;
    @InjectView(R.id.left_image)
    ImageView left_image;
    @InjectView(R.id.right_image)
    ImageView right_image;
    @InjectView(R.id.view_pager)
    ViewPager view_pager;
    @InjectView(R.id.tabs)
    PagerSlidingTabStrip tabs;


    private List<Fragment> dataList;
    private AttentionFragment attentionFragment;
    private CityFragment cityFragment;
    private HotFragment hotFragment;

    private SearchViewPagerAdapter searchViewPagerAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        View topView = view.findViewById(R.id.view);

        int sysVersion = Integer.parseInt(Build.VERSION.SDK);
        if (sysVersion >= 19) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        } else {
            topView.setVisibility(View.GONE);
        }

        ButterKnife.inject(this, view);


        setTabsValue();

        dataList = new ArrayList<Fragment>();

        searchViewPagerAdapter = new SearchViewPagerAdapter(getChildFragmentManager());
        view_pager.setAdapter(searchViewPagerAdapter);
//        view_pager.set
        view_pager.setOffscreenPageLimit(2);
        tabs.setViewPager(view_pager);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.reset(this);
    }

    @OnClick({R.id.left_image, R.id.right_image})
    public void onViewClicked(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.left_image:
                intent.setClass(getActivity(), AddFriendSearchActivity.class);
                startActivity(intent);
                break;

            case R.id.right_image:
                intent.setClass(getActivity(), SearchKeyActivity.class);
                startActivity(intent);
                break;
        }
    }

    /**
     * 对PagerSlidingTabStrip的各项属性进行赋值。
     */
    private void setTabsValue() {
        // 设置Tab是自动填充满屏幕的
        tabs.setShouldExpand(true);
        // 设置Tab的分割线是透明的
        tabs.setDividerColor(Color.TRANSPARENT);
        // 设置Tab底部线的高度
//        tabs.setUnderlineHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 1, getResources().getDisplayMetrics()));
        tabs.setUnderlineHeight(0);
        // 设置Tab Indicator的高度
        tabs.setIndicatorHeight((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics()));

        // 设置Tab标题文字的大小
        tabs.setTextSize((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        // 设置Tab Indicator的颜色
        tabs.setIndicatorColor(Color.parseColor("#ffffff"));//#d83737   #d83737(绿)
//        // 设置选中Tab文字的颜色 (这是我自定义的一个方法)
//        tabs.setSelectedTextColor(Color.parseColor("#ffffff"));
        // 取消点击Tab时的背景色
        tabs.setTabBackground(0);

        tabs.setTextColor(getActivity().getResources().getColor(R.color.white));
        tabs.setDividerPadding(115);
    }


    private class SearchViewPagerAdapter extends FragmentPagerAdapter {


        private final String[] titles = {"热门", "关注", "同城"};

        public SearchViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    if (hotFragment == null) {
                        hotFragment = new HotFragment();
                    }
                    return hotFragment;
                case 1:
                    if (attentionFragment == null) {
                        attentionFragment = new AttentionFragment();
                    }
                    return attentionFragment;
                case 2:
                    if (cityFragment == null) {
                        cityFragment = new CityFragment();
                    }
                    return cityFragment;
                default:
                    return null;
            }
        }


        @Override
        public int getCount() {
            return titles.length;
        }
    }


//    @Override
//    public void onResume() {
//        GetFollowListData();
//        super.onResume();
//    }

}
