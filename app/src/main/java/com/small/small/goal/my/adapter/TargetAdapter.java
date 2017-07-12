package com.small.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.DonutProgress;
import com.small.small.goal.R;
import com.small.small.goal.my.entry.CollectEntity;
import com.small.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 21:17
 * 描述： 我的目标的adapter
 * 修改：
 **/
public class TargetAdapter extends BaseAdapter {

    private final Context context;
    private List<CollectEntity> data;

    public TargetAdapter(Context context) {
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

    public List<CollectEntity> getData() {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_target, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
            vh.progressItem.setFinishedStrokeWidth(8);
            vh.progressItem.setUnfinishedStrokeWidth(8);
            vh.progressItem.setTextColor(context.getResources().getColor(R.color.chat_top));
            vh.progressItem.setFinishedStrokeColor(context.getResources().getColor(R.color.chat_top));
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final CollectEntity collectEntity = data.get(position);
        vh.tvNameItem.setText(collectEntity.getName());
        vh.tvMoneyItem.setText("已转入 " + collectEntity.getMoney() + "/" + ((int) collectEntity.getBudget()) + "元");
        vh.progressItem.setProgress((float) (collectEntity.getMoney() * 10 / collectEntity.getBudget()));
        vh.imgBgItem.setImageResource(R.drawable.image4);

        if (!"".equals(collectEntity.getImg())) {
            Picasso.with(context).load(Utils.GetPhotoPath(collectEntity.getImg())).into(vh.imgBgItem);
        }

//        convertView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, AimMoreActivity.class);
//                intent.putExtra(AimMoreActivity.KEY_AIMID, collectEntity.getId() + "");
//                context.startActivity(intent);
//            }
//        });

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.img_bg_item)
        ImageView imgBgItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv_money_item)
        TextView tvMoneyItem;
        @InjectView(R.id.progress_item)
        DonutProgress progressItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
