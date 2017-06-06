package com.pi.small.goal.my.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司：百迅
 * 创建者： 王金壮
 * 时间： 2016/9/29.
 * 修改：
 * 描述：VpFragmentAdapter
 */

public class VpFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> frList;

    public VpFragmentAdapter(FragmentManager fm, List<Fragment> frList) {
        super(fm);
        this.frList = new ArrayList<>();
        this.frList = frList;
    }


    @Override
    public int getCount() {
        return frList.size();
    }


    @Override
    public Fragment getItem(int position) {
        return frList.get(position);
    }
}
