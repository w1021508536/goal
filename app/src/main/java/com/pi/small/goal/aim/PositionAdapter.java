package com.pi.small.goal.aim;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/31.
 */

public class PositionAdapter extends BaseAdapter {

    private List<Map<String, String>> dataList;
    private Context context;


    public PositionAdapter(Context context, List<Map<String, String>> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_position, parent, false);
            viewHolder.title_text = (TextView) convertView.findViewById(R.id.title_text);
            viewHolder.check_image = (ImageView) convertView.findViewById(R.id.check_image);
            viewHolder.snippet_text = (TextView) convertView.findViewById(R.id.snippet_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title_text.setText(dataList.get(position).get("title"));
        viewHolder.snippet_text.setText(dataList.get(position).get("snippet"));


        return convertView;
    }


    private class ViewHolder {
        private TextView title_text;
        private TextView snippet_text;
        private ImageView check_image;
    }
}
