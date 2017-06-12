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
import com.pi.small.goal.my.activity.AimMoreActivity;
import com.pi.small.goal.my.entry.AimOldEntity;

import java.util.ArrayList;
import java.util.List;

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
    private List<AimOldEntity> data;

    public TargetOldAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<AimOldEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<AimOldEntity> data) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_target_old, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
//        if (!"".equals(followEntry.getAvatar())) {
//            Picasso.with(context).load(Utils.GetPhotoPath(followEntry.getAvatar())).into(vh.iconItem);
//        }


        final AimOldEntity aimOldEntity = data.get(position);
        vh.tvNameItem.setText(aimOldEntity.getName());

        switch (aimOldEntity.getStatus()) {              //1：进行中  2：已完成 3：已结束
            case 1:
                vh.tvMoneyItem.setText("已结束" + aimOldEntity.getMoney() + "/" + aimOldEntity.getBudget() + "元");
                break;
            case 2:
                vh.tvMoneyItem.setText("已完成" + aimOldEntity.getMoney() + "/" + aimOldEntity.getBudget() + "元");
                break;
            case 3:
                vh.tvMoneyItem.setText("已结束" + aimOldEntity.getMoney() + "/" + aimOldEntity.getBudget() + "元");
                break;
            default:
                vh.tvMoneyItem.setText("已结束" + aimOldEntity.getMoney() + "/" + aimOldEntity.getBudget() + "元");
                break;

        }


        if (aimOldEntity.getMoney() >= aimOldEntity.getBudget()) {
            vh.imgOkItem.setImageResource(R.mipmap.icon_goal_finished);
        } else {
            vh.imgOkItem.setImageResource(R.mipmap.icon_goal_unfinished);
        }

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AimMoreActivity.class);
                intent.putExtra(AimMoreActivity.KEY_AIMID, aimOldEntity.getId()+"");
                context.startActivity(intent);
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
        @InjectView(R.id.img_ok_item)
        ImageView imgOkItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
