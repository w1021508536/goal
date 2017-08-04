package com.small.small.goal.my.mall.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.my.dialog.GoSetAddressDialog;
import com.small.small.goal.my.mall.activity.AddAddressAcivity;
import com.small.small.goal.my.mall.entity.LuckEntity;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.dialog.DialogClickListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/7 10:18
 * 修改：
 * 描述：
 **/
public class LuckAdapter extends BaseAdapter {

    private Context context;
    private List<LuckEntity> data;
    private final GoSetAddressDialog dialog;

    public LuckAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
        dialog = new GoSetAddressDialog(context, "");
    }

    public void setData(List<LuckEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<LuckEntity> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
    }

    public List<LuckEntity> getData() {
        return data;
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
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_luck_grid, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
            dialog.setOnTvOKClickListener(new DialogClickListener() {
                @Override
                public void onClick(String str) {
                    context.startActivity(new Intent(context, AddAddressAcivity.class));
                }
            });
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        LuckEntity luckEntity = data.get(position);
        if (luckEntity.getId() == 0) {
            vh.imgHot.setVisibility(View.INVISIBLE);
            vh.ll.setVisibility(View.INVISIBLE);
            return convertView;
        }

        if (luckEntity.getIsHot() == 1) {
            vh.imgHot.setImageResource(R.mipmap.title_hot);
        } else if (luckEntity.getIsNew() == 1) {
            vh.imgHot.setImageResource(R.mipmap.title_new);
        } else {
            vh.imgHot.setVisibility(View.GONE);
        }

        Picasso.with(context).load(Utils.GetPhotoPath(luckEntity.getImg())).into(vh.imgShop);
        vh.tvMoney.setText((int)luckEntity.getPrice() + "个");
        vh.tvSurplus.setText("剩余：" + luckEntity.getStores() + "个");
        vh.tvShopName.setText(luckEntity.getName());


//        if (position % 2 == 1) {
//            vh.viewLine.setVisibility(View.GONE);
//        }else {
//            vh.viewLine.setVisibility(View.VISIBLE);
//        }

//        vh.tvOk.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //    context.startActivity(new Intent(context, AddAddressAcivity.class));
//                dialog.show();
//            }
//        });
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.img_hot)
        ImageView imgHot;
        @InjectView(R.id.img_shop)
        ImageView imgShop;
        @InjectView(R.id.tv_shopName)
        TextView tvShopName;
        @InjectView(R.id.tv_money)
        TextView tvMoney;
        @InjectView(R.id.tv_surplus)
        TextView tvSurplus;
        @InjectView(R.id.tv_ok)
        TextView tvOk;
        @InjectView(R.id.ll)
        LinearLayout ll;
        @InjectView(R.id.view_line)
        View viewLine;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
