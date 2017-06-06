package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.activity.TargetMoreActivity;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/2 13:38
 * 修改：
 * 描述：历史目标
 **/
public class TargetOldAdapter extends BaseAdapter {

    private final Context context;

    public TargetOldAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 5;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_target_old, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
//        if (!"".equals(followEntry.getAvatar())) {
//            Picasso.with(context).load(Utils.GetPhotoPath(followEntry.getAvatar())).into(vh.iconItem);
//        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, TargetMoreActivity.class));
            }
        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_bg_item)
        ImageView imgBgItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv_money_item)
        TextView tvMoneyItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
