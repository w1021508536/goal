package com.pi.small.goal.my.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 15:45
 * 修改：
 * 描述： 轨迹的适配器
 **/
public class TrajectoryAdapter extends BaseAdapter {


    private final Context context;

    public TrajectoryAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 3;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trajectory, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        if (position == 0) {
            vh.viewLineTop.setVisibility(View.INVISIBLE);
        }
        vh.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RelativeLayout rl_image = (RelativeLayout) ((Activity) context).findViewById(R.id.rl_image);
                rl_image.setVisibility(View.VISIBLE);
            }
        });

        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.view_line_top)
        View viewLineTop;
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.tv_title_item)
        TextView tvTitleItem;
        @InjectView(R.id.img_item)
        ImageView imgItem;
        @InjectView(R.id.rl_money_item)
        LinearLayout rlMoneyItem;
        @InjectView(R.id.tv_city_item)
        TextView tvCityItem;
        @InjectView(R.id.rl_operation_item)
        LinearLayout rlOperationItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
