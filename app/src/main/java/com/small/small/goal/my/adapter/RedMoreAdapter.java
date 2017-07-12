package com.small.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.my.dialog.MonthDialog;
import com.small.small.goal.my.entry.RedMoreAdapterEntry;
import com.small.small.goal.utils.Utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 13:19
 * 描述：
 * 修改：红包明细的适配器
 **/
public class RedMoreAdapter extends BaseAdapter {

    private final MonthDialog dialog;
    private Context context;
    private List<RedMoreAdapterEntry> data;

    public RedMoreAdapter(Context context, MonthDialog dialog) {
        this.context = context;
        data = new ArrayList<>();
        this.dialog = dialog;
    }

    public void setData(List<RedMoreAdapterEntry> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<RedMoreAdapterEntry> data) {

        if (data.size() == 0) return;
        RedMoreAdapterEntry entry1 = data.get(0);
        for (RedMoreAdapterEntry entry : this.data) {

            if (entry.getTitleType() == RedAdapter.TYPE_TITLE) {
                if (entry.getTitle().equals(entry1.getTitle())) {
                    entry.setAddMoney(entry.getAddMoney() + entry1.getAddMoney());
                    data.remove(0);
                }
            } else {

            }
        }
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
        TitleViewHolder titleViewHolder = null;
        ContentViewHolder contentViewHolder;
        if (getItemViewType(position) == RedAdapter.TYPE_TITLE) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_title_redmore, null);
                titleViewHolder = new TitleViewHolder(convertView);
                convertView.setTag(titleViewHolder);
            } else {
                titleViewHolder = (TitleViewHolder) convertView.getTag();
            }
            RedMoreAdapterEntry redMoreAdapterEntry = data.get(position);
            titleViewHolder.tvTimeItem.setText(redMoreAdapterEntry.getTitle());
            //  titleViewHolder.tvMoneyItem.setText("支出 ¥" + redMoreAdapterEntry.getDeletteMoney() + "  收入 ¥" + redMoreAdapterEntry.getAddMoney());
            titleViewHolder.tvMoneyItem.setText("收入 ¥" + Utils.getPercentTwoStr(redMoreAdapterEntry.getAddMoney()));
            if (position == 0 && titleViewHolder != null) {
                titleViewHolder.imgMonthItem.setVisibility(View.VISIBLE);
            }
            titleViewHolder.imgMonthItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (dialog != null && !dialog.isShowing()) {
                        dialog.show();
                    }

                }
            });
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_content_redmore, null);
                contentViewHolder = new ContentViewHolder(convertView);
                convertView.setTag(contentViewHolder);
            } else {
                contentViewHolder = (ContentViewHolder) convertView.getTag();
            }
            RedMoreAdapterEntry redMoreAdapterEntry = data.get(position);
            switch (redMoreAdapterEntry.getType()) {//type: 1:助力红包  2:感谢红包 3:收益红包
                case 1:   //
                    contentViewHolder.tvNameItem.setText("助力红包—来自");
                    contentViewHolder.tvUserName.setText(redMoreAdapterEntry.getFromUserNick());
                    break;
                case 2:   //
                    contentViewHolder.tvNameItem.setText("感谢红包—来自");
                    contentViewHolder.tvUserName.setText(redMoreAdapterEntry.getFromUserNick());
                    break;
                case 3:  //
                    contentViewHolder.tvNameItem.setText("收益红包");
                    contentViewHolder.tvUserName.setText("");
                    break;

            }
            contentViewHolder.tvMoneyItem.setText("+" + redMoreAdapterEntry.getMoney());

            String timeDate = getTimeDate(redMoreAdapterEntry.getCreateTime());
            contentViewHolder.tvTimeItem.setText(timeDate);
            //  contentViewHolder.tvTimeItem.setText((collectEntity.getCycle() - timeDay) + "");
        }

        return convertView;
    }

    /**
     * 获取时间格式  *分钟前。。
     * create  wjz
     **/
    public static String getTimeDate(long createTime) {
        Date date = new Date();
        long l = date.getTime() - createTime;
        long timeMinute = l / 60000;  //分钟
        long timeHour = timeMinute / 60;
        long timeDay = timeHour / 24;
        if (timeMinute < 60)
            return timeMinute + "分钟前";
        else if (timeHour < 24)
            return timeHour + "小时前";
        else return timeDay + "天前";
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getTitleType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    static class TitleViewHolder {
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.tv_money_item)
        TextView tvMoneyItem;
        @InjectView(R.id.img_month_item)
        ImageView imgMonthItem;

        TitleViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    class ContentViewHolder {
        @InjectView(R.id.img_item)
        ImageView imgItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.tv_money_item)
        TextView tvMoneyItem;
        @InjectView(R.id.tv_userName_item)
        TextView tvUserName;

        ContentViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
