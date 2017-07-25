package com.small.small.goal.my.guess.note.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.guess.note.empty.SportEmpty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JS on 2017-07-10.
 */

public class SportAdapter extends BaseAdapter {
    private Context context;
    private List<SportEmpty> sportEmptyList;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public SportAdapter(Context context, List<SportEmpty> sportEmptyList) {
        this.context = context;
        this.sportEmptyList = sportEmptyList;
    }

    @Override
    public int getCount() {
        return sportEmptyList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_sport_bet, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.beanText.setText("投注金豆:" + sportEmptyList.get(position).getBean() + "金豆");
        viewHolder.timeText.setText(simpleDateFormat.format(new Date(Long.valueOf(sportEmptyList.get(position).getCreateTime()))));
        if (sportEmptyList.get(position).getType().equals("1")) {
            viewHolder.statusText.setText("趣味足球");
            viewHolder.statusText.setBackgroundResource(R.drawable.background_green_corner_60);
        } else {
            viewHolder.statusText.setText("趣味篮球");
            viewHolder.statusText.setBackgroundResource(R.drawable.background_yellow_corner_60);
        }

        if (sportEmptyList.get(position).getIsDraw().equals("0")) {
            viewHolder.openStatusText.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.openStatusText.setText("等待开奖");
        } else {
            if (sportEmptyList.get(position).getIsWin().equals("0")) {
                viewHolder.openStatusText.setTextColor(context.getResources().getColor(R.color.black));
                viewHolder.openStatusText.setText("未中奖");
            } else if (sportEmptyList.get(position).getIsWin().equals("1")) {
                viewHolder.openStatusText.setTextColor(context.getResources().getColor(R.color.bg_red));
                viewHolder.openStatusText.setText("猜中+" + sportEmptyList.get(position).getReward() + "金豆");
            }
        }
        viewHolder.nameText.setText(sportEmptyList.get(position).getHomeTeam() + "VS" + sportEmptyList.get(position).getVisitTeam());

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.name_text)
        TextView nameText;
        @InjectView(R.id.status_text)
        TextView statusText;
        @InjectView(R.id.bean_text)
        TextView beanText;
        @InjectView(R.id.right)
        ImageView right;
        @InjectView(R.id.open_status_text)
        TextView openStatusText;
        @InjectView(R.id.time_text)
        TextView timeText;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

