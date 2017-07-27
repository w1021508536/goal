package com.small.small.goal.my.guess.fastthree.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.guess.fastthree.empty.FastThreeSumEmpty;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JS on 2017-07-03.
 */

public class SumAdapter extends BaseAdapter {

    private Context context;
    private List<FastThreeSumEmpty> dataList;
    private String status;

    public SumAdapter(Context context, List<FastThreeSumEmpty> dataList, String status) {
        this.context = context;
        this.dataList = dataList;
        this.status = status;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    public void changeItem(int number, int status) {
        dataList.get(number).setStatus(status);
        notifyDataSetChanged();
    }


    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_fast_three_sum, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.numberText.setText(dataList.get(position).getNumber());
        viewHolder.moneyText.setText(dataList.get(position).getMoney());

        if (status.equals("1")) {
            viewHolder.moneyText.setVisibility(View.GONE);
            viewHolder.numberText.setTextSize(18);

        }

        if (dataList.get(position).getStatus() == 0) {
            viewHolder.numberText.setTextColor(context.getResources().getColor(R.color.bg_red));
            viewHolder.moneyText.setTextColor(context.getResources().getColor(R.color.text_gray_light));
            convertView.setBackgroundResource(R.drawable.background_white_gray_corner_5);
        } else {
            viewHolder.numberText.setTextColor(context.getResources().getColor(R.color.white));
            viewHolder.moneyText.setTextColor(context.getResources().getColor(R.color.white));
            convertView.setBackgroundResource(R.drawable.background_red_gray_corner_5);
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.number_text)
        TextView numberText;
        @InjectView(R.id.money_text)
        TextView moneyText;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
