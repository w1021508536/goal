package com.small.small.goal.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.utils.entity.CommentEntity;

import java.util.List;

/**
 * Created by JS on 2017-06-09.
 */

public class CommentAdapter extends BaseAdapter {

    private Context context;
    private List<CommentEntity> dataList;

    private int number;

    public CommentAdapter(Context context, List<CommentEntity> dataList, int number) {
        this.context = context;
        this.dataList = dataList;
        this.number = number;
    }


    @Override
    public int getCount() {
        return number;
    }

    public void SetNumber(int number) {
        this.number = number;
        notifyDataSetChanged();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_comment, parent, false);

            viewHolder.nick_text = (TextView) convertView.findViewById(R.id.nick_text);
            viewHolder.content_text = (TextView) convertView.findViewById(R.id.content_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.nick_text.setText(dataList.get(position).getNick() + "：");

        viewHolder.content_text.setText(dataList.get(position).getContent());
        return convertView;
    }


    private class ViewHolder {

        private TextView nick_text;
        private TextView content_text;

    }
}
