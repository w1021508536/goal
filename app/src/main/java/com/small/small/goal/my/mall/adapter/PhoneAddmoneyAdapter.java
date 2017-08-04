package com.small.small.goal.my.mall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.mall.fragment.PhoneAddmoneyFragment;

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
public class PhoneAddmoneyAdapter extends RecordAdapter {


    private OnLLClickListener listener;
    private int type;


    public PhoneAddmoneyAdapter(Context context, int type) {
        super(context);
        this.type = type;
    }

    public interface OnLLClickListener {
        void onClick(PhoneAddmoneyFragment.phoneAddmoney entity,int position);
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

        final PhoneAddmoneyFragment.phoneAddmoney ent = (PhoneAddmoneyFragment.phoneAddmoney) data.get(position);


        if (type == 1) {
            vh.tvDuihuan.setText(ent.getP());
            vh.tvMoney.setText((int) ent.getPrice() + "");
        } else {
//            vh.tvDuihuan.setText(ent.getP());
//            vh.tvMoney.setText(ent.getV() * 120 + "");
            vh.tvMoney.setText(ent.getBean() + "");

            if (ent.getV() >= 1024) {

                vh.tvDuihuan.setText((int) (ent.getV() / 1024) + "G");
            } else {
                vh.tvDuihuan.setText(ent.getV() + "M");
            }
        }


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
                        ((PhoneAddmoneyFragment.phoneAddmoney) data.get(i)).setType(false);
                    }
                }
                if (listener != null) {
                    listener.onClick(ent,position);
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
