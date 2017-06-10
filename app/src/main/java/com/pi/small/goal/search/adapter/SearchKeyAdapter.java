package com.pi.small.goal.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.entity.AimEntity;

import java.util.List;

/**
 * Created by JS on 2017-06-10.
 */

public class SearchKeyAdapter extends BaseAdapter {

    private List<AimEntity> dataList;
    private Context context;


    public SearchKeyAdapter(Context context, List<AimEntity> dataList) {
        this.dataList = dataList;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_search_key, parent, false);
        } else {

        }

        return convertView;
    }

    private class ViewHolder {
        ImageView aim_iamge;
        TextView content_text;
    }
}
