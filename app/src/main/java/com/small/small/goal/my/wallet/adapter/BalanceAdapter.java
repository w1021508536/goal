package com.small.small.goal.my.wallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.my.gold.GoldEmpty;
import com.small.small.goal.my.gold.GoldListAdapter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import me.nereo.multi_image_selector.bean.Image;

/**
 * Created by JS on 2017-08-02.
 */

public class BalanceAdapter extends BaseAdapter {

    private Context context;
    private List<GoldEmpty> guessEmptyList;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM");
    private SimpleDateFormat simpleDateFormat3 = new SimpleDateFormat("MM-dd");

    public BalanceAdapter(Context context, List<GoldEmpty> guessEmptyList) {
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

        viewHolder.moneyImage.setVisibility(View.GONE);

        if (Double.valueOf(guessEmptyList.get(position).getAmount()) > 0) {
            viewHolder.moneyText.setText("+" + guessEmptyList.get(position).getAmount());
            viewHolder.moneyText.setTextColor(context.getResources().getColor(R.color.kaijiang));
        } else {
            viewHolder.moneyText.setText(guessEmptyList.get(position).getAmount());
            viewHolder.moneyText.setTextColor(context.getResources().getColor(R.color.white));
        }
        viewHolder.monthText.setText(simpleDateFormat2.format(new Date(Long.valueOf(guessEmptyList.get(position).getCreateTime()))));
        viewHolder.timeText.setText(simpleDateFormat1.format(new Date(Long.valueOf(guessEmptyList.get(position).getCreateTime()))) + " " + simpleDateFormat3.format(new Date(Long.valueOf(guessEmptyList.get(position).getCreateTime()))));

        viewHolder.nameText.setText(guessEmptyList.get(position).getRemark());
        if (position != 0) {
            String now = simpleDateFormat2.format(new Date(Long.valueOf(guessEmptyList.get(position).getCreateTime())));
            String last = simpleDateFormat2.format(new Date(Long.valueOf(guessEmptyList.get(position - 1).getCreateTime())));
            if (now.equals(last)) {
                viewHolder.monthLayout.setVisibility(View.GONE);
            } else {
                viewHolder.monthLayout.setVisibility(View.VISIBLE);
            }
        } else {
            viewHolder.monthLayout.setVisibility(View.VISIBLE);
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
        @InjectView(R.id.money_image)
        ImageView moneyImage;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
