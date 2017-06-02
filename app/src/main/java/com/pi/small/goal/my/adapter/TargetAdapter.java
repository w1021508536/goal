package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.pi.small.goal.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

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
        return 10;
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
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_target, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
            vh.progressItem.setFinishedStrokeWidth(8);
            vh.progressItem.setUnfinishedStrokeWidth(8);
            vh.progressItem.setTextColor(context.getResources().getColor(R.color.chat_top));
            vh.progressItem.setFinishedStrokeColor(context.getResources().getColor(R.color.chat_top));
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        vh.progressItem.setProgress(50);
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_bg_item)
        ImageView imgBgItem;
        @InjectView(R.id.progress_item)
        DonutProgress progressItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
