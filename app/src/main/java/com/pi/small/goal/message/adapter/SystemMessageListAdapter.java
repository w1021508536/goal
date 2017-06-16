package com.pi.small.goal.message.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.Url;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

/**
 * Created by Administrator on 2017/5/25.
 */

public class SystemMessageListAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> dataList;

    private ImageOptions imageOptions;

    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");
    private SimpleDateFormat simpleDateFormat2 = new SimpleDateFormat("yyyy-MM-dd");

    public SystemMessageListAdapter(Context context, List<Map<String, String>> dataList) {
        this.context = context;
        this.dataList = dataList;

        imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.FIT_XY)
                .setLoadingDrawableId(R.mipmap.background_load)
                .setFailureDrawableId(R.mipmap.background_fail)
                .build();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_system, parent, false);
            viewHolder.time_text = (TextView) convertView.findViewById(R.id.time_text);
            viewHolder.title_text = (TextView) convertView.findViewById(R.id.title_text);
            viewHolder.date_text = (TextView) convertView.findViewById(R.id.date_text);
            viewHolder.brief_text = (TextView) convertView.findViewById(R.id.brief_text);
            viewHolder.item_layout = (LinearLayout) convertView.findViewById(R.id.item_layout);
            viewHolder.item_image = (ImageView) convertView.findViewById(R.id.item_image);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.title_text.setText(dataList.get(position).get("title"));
        viewHolder.brief_text.setText(dataList.get(position).get("brief"));

        viewHolder.time_text.setText(simpleDateFormat2.format(new Date(Long.valueOf(dataList.get(position).get("createTime")))));
//        viewHolder.date_text.setText(simpleDateFormat2.format(new Date(Long.valueOf(dataList.get(position).get("createTime") ))));
        if (!dataList.get(position).get("url").equals("")) {
            viewHolder.item_image.setVisibility(View.VISIBLE);
            x.image().bind(viewHolder.item_image, Url.PhotoUrl + "/" + dataList.get(position).get("img"), imageOptions);
        } else {
            viewHolder.item_image.setVisibility(View.GONE);
        }


        return convertView;
    }


    private class ViewHolder {

        private TextView time_text;
        private LinearLayout item_layout;
        private TextView title_text;
        private TextView date_text;
        private ImageView item_image;
        private TextView brief_text;


    }
}
