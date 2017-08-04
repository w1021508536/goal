package com.small.small.goal.my.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.mall.entity.HuafeiGosnEntity;
import com.small.small.goal.utils.Utils;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/11 9:14
 * 修改：
 * 描述：充值记录的适配器
 **/
public class HuafeiAdapter extends RecordAdapter {


    public final static int ITEM_TYPE_TITLE = 0;
    public final static int ITEM_TYPE_CONTENT = 1;
    //   private List<HuafeiGosnEntity> data;

    public HuafeiAdapter(Context context) {
        super(context);
    }

    @Override
    public void addData(List data) {
        super.addData(data);
    }

    @Override
    public void setData(List data) {
        super.setData(data);

    }

    //   public void setData(List<LiuliangGsonEntity> data) {
//        this.data = data;
//    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TitleViewHolder titleViewHolder;
        ContentViewHolder contentViewHolder;
        if (getItemViewType(position) == ITEM_TYPE_TITLE) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_title_record, null);
                titleViewHolder = new TitleViewHolder(convertView);
                convertView.setTag(titleViewHolder);
            } else {
                titleViewHolder = (TitleViewHolder) convertView.getTag();
            }
            titleViewHolder.tvTitle.setText(((HuafeiGosnEntity) data.get(position)).getTitle());
        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_content_record, null);
                contentViewHolder = new ContentViewHolder(convertView);
                convertView.setTag(contentViewHolder);
                contentViewHolder.imgContent.setBackgroundResource(R.mipmap.icon_bg_phonecost);
            } else {
                contentViewHolder = (ContentViewHolder) convertView.getTag();
            }
            HuafeiGosnEntity huafeiGosnEntity = (HuafeiGosnEntity) data.get(position);
            contentViewHolder.tvTitle.setText("话费充值" + huafeiGosnEntity.getAmount() + "元");
            contentViewHolder.tvTime.setText(Utils.GetTime(Long.valueOf(huafeiGosnEntity.getCreateTime())));
            contentViewHolder.tvMoney.setText("-" + huafeiGosnEntity.getBean());
            contentViewHolder.imgContent.setText(huafeiGosnEntity.getAmount() + "元");

        }

        //super.getView(position, convertView, parent)
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return ((HuafeiGosnEntity) data.get(position)).getType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }


    static class ContentViewHolder {
        @InjectView(R.id.img_content)
        TextView imgContent;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_time)
        TextView tvTime;
        @InjectView(R.id.tv_money)
        TextView tvMoney;

        ContentViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class TitleViewHolder {
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        TitleViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}