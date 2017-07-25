package com.small.small.goal.my.guess.football.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.small.small.goal.R;

import java.util.List;

/**
 * Created by JS on 2017-06-26.
 */

public class MatchTypeListAdapter extends BaseAdapter {
    private List<String> horizontalList;
    private Context context;
    private int page = 0;

    public MatchTypeListAdapter(Context context, List<String> horizontalList) {
        this.horizontalList = horizontalList;
        this.context = context;
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public int getCount() {
        return horizontalList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_match_type, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.name_text = (TextView) convertView.findViewById(R.id.name_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (position == page) {
            viewHolder.name_text.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            viewHolder.name_text.setTextColor(context.getResources().getColor(R.color.text_gray_light));
        }

        viewHolder.name_text.setText(horizontalList.get(position));
        return convertView;
    }

    class ViewHolder {
        TextView name_text;
    }

}
