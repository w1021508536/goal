package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.RedMoreAdapterEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 13:19
 * 描述：
 * 修改：
 **/
public class RedMoreAdapter extends BaseAdapter {

    private Context context;
    private List<RedMoreAdapterEntry> data;

    public RedMoreAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<RedMoreAdapterEntry> data) {
        this.data = data;
        notifyDataSetChanged();
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
        TitleViewHolder titleViewHolder = null;
        ContentViewHolder contentViewHolder;
        if (getItemViewType(position) == RedAdapter.TYPE_TITLE) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_title_redmore, null);
                titleViewHolder = new TitleViewHolder(convertView);
                convertView.setTag(titleViewHolder);
            } else {
                titleViewHolder = (TitleViewHolder) convertView.getTag();
            }

        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_content_redmore, null);
                contentViewHolder = new ContentViewHolder(convertView);
                convertView.setTag(contentViewHolder);
            } else {
                contentViewHolder = (ContentViewHolder) convertView.getTag();
            }
        }
        if (position == 0 && titleViewHolder != null) {
            titleViewHolder.imgMonthItem.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getTitleType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    static class TitleViewHolder {
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.tv_money_item)
        TextView tvMoneyItem;
        @InjectView(R.id.img_month_item)
        ImageView imgMonthItem;

        TitleViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    class ContentViewHolder {
        @InjectView(R.id.img_item)
        ImageView imgItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.tv_money_item)
        TextView tvMoneyItem;

        ContentViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
