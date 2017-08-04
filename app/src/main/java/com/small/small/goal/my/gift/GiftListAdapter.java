package com.small.small.goal.my.gift;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.utils.Utils;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by JS on 2017-07-12.
 */

public class GiftListAdapter extends BaseAdapter {

    private Context context;
    private List<GiftEmpty> giftEmptyList;

    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.mipmap.icon_gold_bean_1)
            .setFailureDrawableId(R.mipmap.icon_gold_bean_2)
            .build();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public GiftListAdapter(Context context, List<GiftEmpty> giftEmptyList) {
        this.context = context;
        this.giftEmptyList = giftEmptyList;
    }

    @Override
    public int getCount() {
        return giftEmptyList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_gift_mine, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.moneyText.setText(giftEmptyList.get(position).getGoodsPrice().substring(0, giftEmptyList.get(position).getGoodsPrice().indexOf(".")));
        viewHolder.nameText.setText(giftEmptyList.get(position).getGoodsName());
        if (!giftEmptyList.get(position).getCreateTime().equals(""))
            viewHolder.timeText.setText(simpleDateFormat.format(new Date(Long.valueOf(giftEmptyList.get(position).getCreateTime()))));

        x.image().bind(viewHolder.image, Utils.GetPhotoPath(giftEmptyList.get(position).getImg()), imageOptions);

        if (giftEmptyList.get(position).getStatus().equals("0")) {
            viewHolder.statusText.setText("未支付");
            viewHolder.statusText.setTextColor(context.getResources().getColor(R.color.text_gray_light));
            viewHolder.statusText.setBackground(null);
        } else if (giftEmptyList.get(position).getStatus().equals("1")) {
            viewHolder.statusText.setText("未发货");
            viewHolder.statusText.setTextColor(context.getResources().getColor(R.color.text_gray_light));
            viewHolder.statusText.setBackground(null);
        } else if (giftEmptyList.get(position).getStatus().equals("2")) {
            viewHolder.statusText.setText("已发货");
            viewHolder.statusText.setTextColor(context.getResources().getColor(R.color.text_gray_light));
            viewHolder.statusText.setBackground(null);
        } else if (giftEmptyList.get(position).getStatus().equals("3")) {
//            if (giftEmptyList.get(position).getIsComment().equals("0")) {
//                viewHolder.statusText.setText("去晒图");
//                viewHolder.statusText.setTextColor(context.getResources().getColor(R.color.red));
//                viewHolder.statusText.setBackgroundResource(R.drawable.background_null_red_corner_60);
//            } else {
            viewHolder.statusText.setText("已收货");
            viewHolder.statusText.setTextColor(context.getResources().getColor(R.color.text_gray_light));
            viewHolder.statusText.setBackground(null);
//            }

        }


//        viewHolder.statusText.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                if (giftEmptyList.get(position).getStatus().equals("3") && giftEmptyList.get(position).getIsComment().equals("0")) {
//                    Intent intent = new Intent(context, PutShareActivity.class);
//                    intent.putExtra("orderNo", giftEmptyList.get(position).getOrderNo());
//                    context.startActivity(intent);
//                }
//
//
//            }
//        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.image)
        ImageView image;
        @InjectView(R.id.money_text)
        TextView moneyText;
        @InjectView(R.id.name_text)
        TextView nameText;
        @InjectView(R.id.status_text)
        TextView statusText;
        @InjectView(R.id.time_text)
        TextView timeText;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
