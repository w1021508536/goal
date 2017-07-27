package com.small.small.goal.my.guess.football.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.guess.football.empty.FootBallMemberEmpty;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JS on 2017-06-30.
 */

public class TeamRightAdapter extends BaseAdapter {

    private List<FootBallMemberEmpty> dataList;
    private Context context;
    private int status = 0;//1 正式  2 替补
    public TeamRightAdapter(Context context, List<FootBallMemberEmpty> dataList ,int status) {
        this.context = context;
        this.dataList = dataList;
        this.status=status;
    }

    @Override
    public int getCount() {
        return dataList.size();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_football_right, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.nameText.setText(dataList.get(position).getPlayerName());
        viewHolder.numberText.setText(dataList.get(position).getJerseyNum());

//        1 => '守门员',2 => '后卫',3 => '中场',4 => '前锋',5 => '替补',
//                6 => '中锋',7 => '边后卫',8 => '中后卫',9 => '后腰',10 => '前腰',11 => '中前卫'
//                ,12 => '影子前锋',13 => '边锋',14 => '翼卫',
        if (status == 1) {
            viewHolder.statusText.setVisibility(View.VISIBLE);

            if (dataList.get(position).getPosition().equals("1")) {
                viewHolder.statusText.setText("门将");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_menjiang);
            } else if (dataList.get(position).getPosition().equals("2")) {
                viewHolder.statusText.setText("后卫");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_houwei);
            } else if (dataList.get(position).getPosition().equals("3")) {
                viewHolder.statusText.setText("中场");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_zhongchang);
            } else if (dataList.get(position).getPosition().equals("4")) {
                viewHolder.statusText.setText("前锋");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_qianfeng);
            } else if (dataList.get(position).getPosition().equals("6")) {
                viewHolder.statusText.setText("中锋");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_zhongchang);
            } else if (dataList.get(position).getPosition().equals("7")) {
                viewHolder.statusText.setText("边后卫");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_houwei);
            } else if (dataList.get(position).getPosition().equals("8")) {
                viewHolder.statusText.setText("中后卫");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_houwei);
            } else if (dataList.get(position).getPosition().equals("9")) {
                viewHolder.statusText.setText("后腰");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_houwei);
            } else if (dataList.get(position).getPosition().equals("10")) {
                viewHolder.statusText.setText("前腰");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_qianwei);
            } else if (dataList.get(position).getPosition().equals("11")) {
                viewHolder.statusText.setText("中前卫");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_qianwei);
            } else if (dataList.get(position).getPosition().equals("12")) {
                viewHolder.statusText.setText("影子前锋");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_qianfeng);
            } else if (dataList.get(position).getPosition().equals("13")) {
                viewHolder.statusText.setText("边锋");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_qianfeng);
            } else if (dataList.get(position).getPosition().equals("14")) {
                viewHolder.statusText.setText("翼卫");
                viewHolder.statusText.setBackgroundResource(R.drawable.background_qianfeng);
            }
        }else {
            viewHolder.statusText.setVisibility(View.GONE);
        }


        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.name_text)
        TextView nameText;
        @InjectView(R.id.number_text)
        TextView numberText;
        @InjectView(R.id.status_text)
        TextView statusText;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
