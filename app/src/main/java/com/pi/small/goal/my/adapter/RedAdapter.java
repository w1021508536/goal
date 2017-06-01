package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.WalletEntry;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 9:36
 * 修改：
 * 描述：  錢包明細的adapter
 **/
public class RedAdapter extends BaseAdapter {

    private final List<WalletEntry> data;
    private Context context;
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_CONTENT = 1;

    public RedAdapter(Context context, List<WalletEntry> data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;

        if (getItemViewType(position) == TYPE_TITLE) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_title_red, null);
            } else {
            }
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_content_red, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            vh.tvHaveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

        return convertView;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }


    static class ViewHolder {
        @InjectView(R.id.img_item)
        ImageView imgItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.tv_have_item)
        TextView tvHaveItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
