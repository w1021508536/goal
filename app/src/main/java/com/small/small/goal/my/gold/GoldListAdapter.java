package com.small.small.goal.my.gold;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.small.small.goal.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JS on 2017-07-11.
 */

public class GoldListAdapter extends BaseAdapter {

    private Context context;
    private List<GoldEmpty> guessEmptyList;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("MM-dd");

    public GoldListAdapter(Context context, List<GoldEmpty> guessEmptyList) {
        this.context = context;
        this.guessEmptyList = guessEmptyList;
    }

    @Override
    public int getCount() {
        return guessEmptyList.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_gold_mine, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        if (Double.valueOf(guessEmptyList.get(position).getAmount()) > 0) {
            viewHolder.moneyText.setText(guessEmptyList.get(position).getAmount().substring(0, guessEmptyList.get(position).getAmount().indexOf(".")));
            viewHolder.moneyText.setTextColor(context.getResources().getColor(R.color.kaijiang));
        } else {
            viewHolder.moneyText.setText(guessEmptyList.get(position).getAmount().substring(0, guessEmptyList.get(position).getAmount().indexOf(".")));
            viewHolder.moneyText.setTextColor(context.getResources().getColor(R.color.white));
        }
        viewHolder.monthText.setText(simpleDateFormat2.format(new Date(Long.valueOf(guessEmptyList.get(position).getCreateTime()))));
        viewHolder.timeText.setText(simpleDateFormat1.format(new Date(Long.valueOf(guessEmptyList.get(position).getCreateTime()))) + " " + simpleDateFormat3.format(new Date(Long.valueOf(guessEmptyList.get(position).getCreateTime()))));


        if (position != 0) {
            String now = new Date(Long.valueOf(guessEmptyList.get(position).getCreateTime())) + "";
            String last = new Date(Long.valueOf(guessEmptyList.get(position - 1).getCreateTime())) + "";
            if (now.substring(0, 8).equals(last.substring(0, 8))) {
                viewHolder.monthLayout.setVisibility(View.GONE);
            } else {
                viewHolder.monthLayout.setVisibility(View.VISIBLE);
            }
        }

        if (guessEmptyList.get(position).getOperateType().equals("41")) {
            viewHolder.nameText.setText("充值金豆");
        } else if (guessEmptyList.get(position).getOperateType().equals("421")) {
            viewHolder.nameText.setText("投注足球");
        } else if (guessEmptyList.get(position).getOperateType().equals("422")) {
            viewHolder.nameText.setText("投注篮球");
        } else if (guessEmptyList.get(position).getOperateType().equals("423")) {
            viewHolder.nameText.setText("投注娱乐快报");
        } else if (guessEmptyList.get(position).getOperateType().equals("424")) {
            viewHolder.nameText.setText("投注彩票");
        } else if (guessEmptyList.get(position).getOperateType().equals("431")) {
            viewHolder.nameText.setText("足球派奖");
        } else if (guessEmptyList.get(position).getOperateType().equals("432")) {
            viewHolder.nameText.setText("篮球派奖");
        } else if (guessEmptyList.get(position).getOperateType().equals("433")) {
            viewHolder.nameText.setText("娱乐快报派奖");
        } else if (guessEmptyList.get(position).getOperateType().equals("434")) {
            viewHolder.nameText.setText("彩票派奖");
        } else if (guessEmptyList.get(position).getOperateType().equals("441")) {
            viewHolder.nameText.setText("兑换礼品");
        } else if (guessEmptyList.get(position).getOperateType().equals("442")) {
            viewHolder.nameText.setText("话费充值");
        } else if (guessEmptyList.get(position).getOperateType().equals("443")) {
            viewHolder.nameText.setText("流量充值");
        } else if (guessEmptyList.get(position).getOperateType().equals("444")) {
            viewHolder.nameText.setText("Q币充值");
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.month_text)
        TextView monthText;
        @InjectView(R.id.month_layout)
        RelativeLayout monthLayout;
        @InjectView(R.id.name_text)
        TextView nameText;
        @InjectView(R.id.time_text)
        TextView timeText;
        @InjectView(R.id.money_text)
        TextView moneyText;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}