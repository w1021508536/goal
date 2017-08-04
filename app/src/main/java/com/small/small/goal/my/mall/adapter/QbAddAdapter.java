package com.small.small.goal.my.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.mall.activity.RechargeQbActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/14 15:06
 * 修改：
 * 描述：
 **/
public class QbAddAdapter extends RecordAdapter {


    private OnLLClickListener listener;

    public QbAddAdapter(Context context) {
        super(context);
    }

    public interface OnLLClickListener {
        void onClick(RechargeQbActivity.QbEntity entity);
    }

    public void setOnLLClickListener(OnLLClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void setData(List data) {
        super.setData(data);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_phone_addmoney, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final RechargeQbActivity.QbEntity ent = (RechargeQbActivity.QbEntity) data.get(position);
        vh.tvDuihuan.setText(ent.getPrice() + "Q币");
        vh.tvMoney.setText((ent.getPrice() * 120) + "");


        if (ent.isType()) {
            vh.tvDuihuan.setTextColor(context.getResources().getColor(R.color.white));
            vh.tvMoney.setTextColor(context.getResources().getColor(R.color.text_white_hui));
            vh.ll1.setBackgroundResource(R.drawable.bg_oval_red_5);
        } else {
            vh.tvDuihuan.setTextColor(context.getResources().getColor(R.color.text_white_hui));
            vh.tvMoney.setTextColor(context.getResources().getColor(R.color.white));
            vh.ll1.setBackgroundResource(R.drawable.bg_oval_gray_null_5);
        }

        vh.ll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                for (int i = 0; i < data.size(); i++) {
                    if (i == position)
                        ent.setType(!ent.isType());
                    else {
                        ((RechargeQbActivity.QbEntity) data.get(i)).setType(false);
                    }
                }
                if (listener != null) {
                    listener.onClick(ent);
                }
                notifyDataSetChanged();

            }
        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.tv_duihuan)
        TextView tvDuihuan;
        @InjectView(R.id.tv_money)
        TextView tvMoney;
        @InjectView(R.id.ll_1)
        RelativeLayout ll1;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
