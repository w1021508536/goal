package com.small.small.goal.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.utils.Utils;
import com.small.small.goal.utils.entity.DynamicEntity;
import com.squareup.picasso.Picasso;

import org.xutils.image.ImageOptions;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

;

/**
 * Created by JS on 2017-06-06.
 */

public class HotAdapter extends BaseAdapter {

    private Context context;
    private List<DynamicEntity> dataList;

    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.mipmap.icon_head)
            .setFailureDrawableId(R.mipmap.icon_head)
            .build();

    public HotAdapter(Context context, List<DynamicEntity> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_hot, parent, false);
            viewHolder.head_image = (CircleImageView) convertView.findViewById(R.id.head_image);
            viewHolder.name_text = (TextView) convertView.findViewById(R.id.name_text);
            viewHolder.time_text = (TextView) convertView.findViewById(R.id.time_text);
            viewHolder.attention_text = (TextView) convertView.findViewById(R.id.attention_text);
            viewHolder.content_text = (TextView) convertView.findViewById(R.id.content_text);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name_text.setText(dataList.get(position).getNick());
        viewHolder.content_text.setText(dataList.get(position).getContent());
        viewHolder.time_text.setText(Utils.GetTime(Long.valueOf(dataList.get(position).getUpdateTime())));

        if (!Utils.GetPhotoPath(dataList.get(position).getAvatar()).equals("")) {
            Picasso.with(context).load(Utils.GetPhotoPath(dataList.get(position).getAvatar())).into(viewHolder.head_image);
        }
        return convertView;
    }

    private class ViewHolder {
        private CircleImageView head_image;
        private TextView name_text;
        private TextView attention_text;
        private TextView time_text;
        private TextView content_text;
    }
}
