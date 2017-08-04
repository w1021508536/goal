package com.small.small.goal.my.mall.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/11 9:14
 * 修改：
 * 描述：充值记录的适配器   可做自己的baseadapter
 **/
public class RecordAdapter<T> extends BaseAdapter {

    public final Context context;

    public final static int ITEM_TYPE_TITLE = 0;
    public final static int ITEM_TYPE_CONTENT = 1;
    public List<T> data;

    public RecordAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<T> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<T> getData() {
        return data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView;
    }


}
