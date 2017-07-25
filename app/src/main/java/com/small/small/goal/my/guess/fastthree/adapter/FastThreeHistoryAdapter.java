package com.small.small.goal.my.guess.fastthree.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.guess.fastthree.ThirdDHistoryEmpty;

import java.util.List;

/**
 * Created by JS on 2017-07-04.
 */

public class FastThreeHistoryAdapter extends BaseAdapter {

    private Context context;
    private List<ThirdDHistoryEmpty> dataList;

    private int number = 0;
    private int status;

    public FastThreeHistoryAdapter(Context context, List<ThirdDHistoryEmpty> dataList, int status) {
        this.context = context;
        this.dataList = dataList;
        this.status = status;
        number = dataList.size();
    }

    @Override
    public int getCount() {
        return number;
    }

    public void SetNumber(int number) {
        this.number = number;
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
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_third_d_history, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.time_text = (TextView) convertView.findViewById(R.id.time_text);
            viewHolder.hundred_text = (TextView) convertView.findViewById(R.id.hundred_text);
            viewHolder.other_text = (TextView) convertView.findViewById(R.id.other_text);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String numberString = "";

        viewHolder.time_text.setText(dataList.get(position).getTime());

        for (int i = 0; i < dataList.get(position).getNumber_list().size(); i++) {
            numberString = numberString + "  " + dataList.get(position).getNumber_list().get(i);
        }
        viewHolder.hundred_text.setText(numberString);


        if (status == 0) {
            viewHolder.other_text.setVisibility(View.VISIBLE);
            String other = "  ";
            int all = 0;
            for (int i = 0; i < dataList.get(position).getNumber_list().size(); i++) {
                all = all + Integer.valueOf(dataList.get(position).getNumber_list().get(i));
            }
            other = other + all;
            if (all < 11) {
                other = other + "  " + "小";
            } else {
                other = other + "  " + "大";
            }
            if (all % 2 == 0) {
                other = other + "  " + "双";
            } else {
                other = other + "  " + "单";
            }
            viewHolder.other_text.setText(other);
        } else {
            viewHolder.other_text.setVisibility(View.VISIBLE);
            String other = "  ";
            int number1 = Integer.valueOf(dataList.get(position).getNumber_list().get(0));
            int number2 = Integer.valueOf(dataList.get(position).getNumber_list().get(1));
            int number3 = Integer.valueOf(dataList.get(position).getNumber_list().get(2));

            if (number1 == number2 && number1 == number3) {
                viewHolder.other_text.setText("三同号");
            } else if (number1 != number2 && number1 != number3 && number2 != number3) {
                if (number1 + 1 == number2 && number2 + 1 == number3) {
                    viewHolder.other_text.setText("三连号");
                } else {
                    viewHolder.other_text.setText("三不同号");
                }

            } else {
                viewHolder.other_text.setText("二同号");
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView time_text;
        TextView hundred_text;
        TextView other_text;
    }
}

