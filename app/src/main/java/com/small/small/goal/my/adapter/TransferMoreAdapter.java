package com.small.small.goal.my.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.my.dialog.MonthDialog;
import com.small.small.goal.my.entry.TransferMoreEntity;
import com.small.small.goal.utils.KeyCode;
import com.small.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

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
 * 修改：转让明细
 **/
public class TransferMoreAdapter extends BaseAdapter {

    private final MonthDialog dialog;
    private Context context;
    private List<TransferMoreEntity> data;
    private SharedPreferences sp;

    public TransferMoreAdapter(Context context, MonthDialog dialog) {
        this.context = context;
        data = new ArrayList<>();
        this.dialog = dialog;
    }

    public void setData(List<TransferMoreEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<TransferMoreEntity> data) {
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
            titleViewHolder.tvMoneyItem.setVisibility(View.GONE);
            TransferMoreEntity entity = data.get(position);
            titleViewHolder.tvTimeItem.setText(entity.getTitle());
            //   titleViewHolder.tvMoneyItem.setText("收入 ¥" + redMoreAdapterEntry.getAddMoney());
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

                sp = Utils.UserSharedPreferences(context);
                contentViewHolder.tvUserName.setTextColor(context.getResources().getColor(R.color.black));
            } else {
                contentViewHolder = (ContentViewHolder) convertView.getTag();
            }
            TransferMoreEntity redMoreAdapterEntry = data.get(position);


            String amount = String.valueOf(redMoreAdapterEntry.getAmount());

            if (redMoreAdapterEntry.getToUserId() == Integer.valueOf(sp.getString(KeyCode.USER_ID, "0"))) {
                contentViewHolder.tvNameItem.setText("转入-来自");

                if (redMoreAdapterEntry.getAmount() > 1) {
                    contentViewHolder.tvMoneyItem.setText("+" + amount.substring(0, amount.indexOf(".")));
                } else {
                    contentViewHolder.tvMoneyItem.setText("+" + redMoreAdapterEntry.getAmount());
                }
                contentViewHolder.tvUserName.setText(redMoreAdapterEntry.getFromUserNick());
                contentViewHolder.tvMoneyItem.setTextColor(context.getResources().getColor(R.color.red));
                if (!redMoreAdapterEntry.getToUserAvatar().equals("")) {
                    Picasso.with(context).load(Utils.GetPhotoPath(redMoreAdapterEntry.getFromUserAvatar())).into(contentViewHolder.imgItem);
                } else {
                    contentViewHolder.imgItem.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_head));
                }
            } else {
                contentViewHolder.tvNameItem.setText("转出-转给");
                if (redMoreAdapterEntry.getAmount() < 1) {
                    contentViewHolder.tvMoneyItem.setText("-" + redMoreAdapterEntry.getAmount());
                } else {
                    contentViewHolder.tvMoneyItem.setText("-" + amount.substring(0, amount.indexOf(".")));
                }

                contentViewHolder.tvUserName.setText(redMoreAdapterEntry.getToUserNick());
                contentViewHolder.tvMoneyItem.setTextColor(context.getResources().getColor(android.R.color.black));
                if (!redMoreAdapterEntry.getToUserAvatar().equals("")) {
                    Picasso.with(context).load(Utils.GetPhotoPath(redMoreAdapterEntry.getToUserAvatar())).into(contentViewHolder.imgItem);
                } else {
                    contentViewHolder.imgItem.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_head));
                }
            }


            //   contentViewHolder.tvMoneyItem.setText("+" + redMoreAdapterEntry.getAmount());

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
