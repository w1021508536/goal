package com.small.small.goal.my.guess.football.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by JS on 2017-06-26.
 *
 */

public class FootBallPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private List<String> tabList;


    public FootBallPagerAdapter(FragmentManager fm, List<Fragment> fragmentList, List<String> tabList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.tabList = tabList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return tabList.size();
    }


    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return tabList.get(position % tabList.size());
    }
}
