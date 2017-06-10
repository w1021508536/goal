package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.TargetHeadEntity;
import com.pi.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/7 15:35
 * 修改：
 * 描述： 主力的人的  adapter
 **/
public class SupportAdapter extends BaseAdapter {

    private final Context context;
    private List<TargetHeadEntity.SupportsBean> data;

    public SupportAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<TargetHeadEntity.SupportsBean> data) {
        this.data = data;
        notifyDataSetChanged();
    }


    public void addData(List<TargetHeadEntity.SupportsBean> data) {
        this.data.addAll(data);
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
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_support, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        TargetHeadEntity.SupportsBean supportEntity = data.get(position);
        if (!"".equals(supportEntity.getAvatar()) && !"jpg".equals(supportEntity.getAvatar())) {
            Picasso.with(context).load(Utils.GetPhotoPath(supportEntity.getAvatar())).into(vh.iconItem);
        }
        vh.tvNameItem.setText(supportEntity.getNick());
        vh.tvMoneyItem.setText(supportEntity.getMoney() + "");
        vh.tvTimeItem.setText(Utils.GetTime(supportEntity.getCreateTime()));

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.icon_item)
        CircleImageView iconItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv1)
        TextView tv1;
        @InjectView(R.id.tv_money_item)
        TextView tvMoneyItem;
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
