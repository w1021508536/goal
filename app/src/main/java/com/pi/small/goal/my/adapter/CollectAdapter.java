package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.CollectEntity;
import com.pi.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/2 17:38
 * 修改：
 * 描述：我的收藏的adapter
 **/
public class CollectAdapter extends BaseAdapter {

    private final Context context;
    private List<CollectEntity> data;

    public CollectAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<CollectEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<CollectEntity> data) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_collect, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        CollectEntity collectEntity = data.get(position);

        vh.tvTitleItem.setText(collectEntity.getName());
        vh.tvBudgetNumItem.setText(((int) collectEntity.getBudget()) + "");   //预算
        vh.tvSupportNumItem.setText(collectEntity.getSupport() + "");  //支持人数

        Date date = new Date();
        long l = date.getTime() - collectEntity.getCreateTime();
        long timeMinute = l / 60000;  //分钟
        long timeHour = timeMinute / 60;
        long timeDay = timeHour / 24;
        if (timeMinute < 60)
            vh.tvTimeItem.setText(timeMinute + "分钟前");
        else if (timeHour < 24)
            vh.tvTimeItem.setText(timeHour + "小时前");
        else vh.tvTimeItem.setText(timeDay + "天前");
        vh.tvDayNumItem.setText((collectEntity.getCycle() - timeDay) + "");

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss ");
        Date curDate = new Date(collectEntity.getCreateTime());//获取当前时间
        String str = formatter.format(curDate);
        Log.v("TAG", str);

        float v = collectEntity.getMoney() / collectEntity.getBudget();
        //通过设置权重来改变横线块占比长度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.imgProgressItem.getLayoutParams();
        vh.tvProgressItem.setText(v + "%");
        if (v == 0) {
            lp.weight = 0.01f;
        } else if (v == 1) {
            lp.weight = 1;
            vh.viewWidth.setVisibility(View.GONE);
        } else {
            float v1 = v / ((1 - v));
            lp.weight = v1;
        }
        //   vh.viewWidth.setVisibility(View.GONE);
        vh.imgProgressItem.setLayoutParams(lp);
        // vh.viewWidth.setLayoutParams(new LinearLayout.LayoutParams(0,0,1-lp.width));

        if (!"".equals(collectEntity.getImg())) {
            Picasso.with(context).load(Utils.GetPhotoPath(collectEntity.getImg())).into(vh.imgCityItem);
        }
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.line)
        View line;
        @InjectView(R.id.icon_item)
        CircleImageView iconItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.img_city_item)
        ImageView imgCityItem;
        @InjectView(R.id.tv_title_item)
        TextView tvTitleItem;
        @InjectView(R.id.img_progress_item)
        ImageView imgProgressItem;
        @InjectView(R.id.tv_progress_item)
        TextView tvProgressItem;
        @InjectView(R.id.view_width)
        View viewWidth;
        @InjectView(R.id.ll_progress_item)
        LinearLayout llProgressItem;
        @InjectView(R.id.tv_budget_num_item)
        TextView tvBudgetNumItem;
        @InjectView(R.id.tv_support_num_item)
        TextView tvSupportNumItem;
        @InjectView(R.id.tv_day_num_item)
        TextView tvDayNumItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
