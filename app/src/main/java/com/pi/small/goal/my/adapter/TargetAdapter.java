package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.pi.small.goal.R;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 21:17
 * 描述： 我的目标的adapter
 * 修改：
 **/
public class TargetAdapter extends BaseAdapter {

    private final Context context;

    public TargetAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 10;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(context).inflate(R.layout.item_target,null);
        }
        return convertView;
    }
}
