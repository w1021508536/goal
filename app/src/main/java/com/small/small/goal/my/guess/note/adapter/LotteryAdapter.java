package com.small.small.goal.my.guess.note.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.guess.note.empty.LotteryEmpty;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JS on 2017-07-10.
 */

public class LotteryAdapter extends BaseAdapter {

    private Context context;
    private List<LotteryEmpty> lotteryEmptyList;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    public LotteryAdapter(Context context, List<LotteryEmpty> lotteryEmptyList) {
        this.context = context;
        this.lotteryEmptyList = lotteryEmptyList;
    }

    @Override
    public int getCount() {
        return lotteryEmptyList.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_lottery_bet, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.beanText.setText("支付" + lotteryEmptyList.get(position).getBean() + "金豆");
        viewHolder.timeText.setText(simpleDateFormat.format(new Date(Long.valueOf(lotteryEmptyList.get(position).getCreateTime()))));
        if (lotteryEmptyList.get(position).getTid().equals("8")) {
            viewHolder.statusText.setText("11选5");
            viewHolder.statusText.setBackgroundResource(R.drawable.background_yellow_corner_60);
        } else if (lotteryEmptyList.get(position).getTid().equals("9")) {
            viewHolder.statusText.setText("新快3");
            viewHolder.statusText.setBackgroundResource(R.drawable.background_green_corner_60);
        }
        if (lotteryEmptyList.get(position).getIsDraw().equals("0")) {
            viewHolder.openStatusText.setTextColor(context.getResources().getColor(R.color.black));
            viewHolder.openStatusText.setText("等待开奖");
        } else {
            if (lotteryEmptyList.get(position).getIsWin().equals("0")) {
                viewHolder.openStatusText.setTextColor(context.getResources().getColor(R.color.black));
                viewHolder.openStatusText.setText("未中奖");
            } else if (lotteryEmptyList.get(position).getIsWin().equals("1")) {
                viewHolder.openStatusText.setTextColor(context.getResources().getColor(R.color.bg_red));
                viewHolder.openStatusText.setText("中奖" + lotteryEmptyList.get(position).getReward() + "金豆");
            }
        }


        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.bean_text)
        TextView beanText;
        @InjectView(R.id.status_text)
        TextView statusText;
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
