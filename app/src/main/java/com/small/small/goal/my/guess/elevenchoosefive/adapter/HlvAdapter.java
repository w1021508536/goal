package com.small.small.goal.my.guess.elevenchoosefive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.guess.elevenchoosefive.entity.HlvEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/28 16:22
 * 修改：
 * 描述：11选5的首页上的横向listview
 **/
public class HlvAdapter extends BaseAdapter {

    private final Context context;
    private final List<HlvEntity> data;

    public HlvAdapter(Context context, List<HlvEntity> data) {

        this.context = context;
        this.data = data;
    }

    public List<HlvEntity> getData() {
        return data;
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
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_hlv, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        HlvEntity hlvEntity = data.get(position);
        if (hlvEntity.isSelected()) {
            vh.imgBottom.setImageResource(View.VISIBLE);
            vh.tvContent.setTextColor(context.getResources().getColor(R.color.red));
        } else {

            vh.imgBottom.setVisibility(View.INVISIBLE);
            vh.tvContent.setTextColor(context.getResources().getColor(R.color.white));
        }
        vh.tvContent.setText(hlvEntity.getContent());

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_content)
        TextView tvContent;
        @InjectView(R.id.img_bottom)
        ImageView imgBottom;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
