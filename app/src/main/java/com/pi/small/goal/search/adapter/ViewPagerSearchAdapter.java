package com.pi.small.goal.search.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.List;

/**
 * Created by JS on 2017-06-14.
 */

public class ViewPagerSearchAdapter extends PagerAdapter {
    private List<View> viewList;
    private Context context;

    public ViewPagerSearchAdapter(Context context, List<View> viewList) {
        this.context = context;
        this.viewList = viewList;
    }

    public void setViewList(List<View> viewList) {
        this.viewList = viewList;
        notifyDataSetChanged();
    }


    public void addData(ImageView view) {
        this.viewList.add(view);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return viewList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(viewList.get(position));

        return viewList.get(position);
    }
}