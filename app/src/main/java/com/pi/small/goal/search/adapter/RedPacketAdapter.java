package com.pi.small.goal.search.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.message.adapter.FriendsMessageListAdapter;
import com.pi.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import org.xutils.image.ImageOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JS on 2017-06-08.
 */

public class RedPacketAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> dataList;


    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");

    public RedPacketAdapter(Context context, List<Map<String, String>> dataList) {
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_red_packet, parent, false);

            viewHolder.name_text = (TextView) convertView.findViewById(R.id.name_text);
            viewHolder.head_image = (CircleImageView) convertView.findViewById(R.id.head_image);
            viewHolder.right_text = (TextView) convertView.findViewById(R.id.right_text);
            viewHolder.time_text = (TextView) convertView.findViewById(R.id.time_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name_text.setText(dataList.get(position).get("nick"));


        if (!dataList.get(position).get("avatar").equals("")) {
            Picasso.with(context).load(Utils.GetPhotoPath(dataList.get(position).get("avatar"))).into(viewHolder.head_image);
        } else {
            viewHolder.head_image.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_head));
        }
        viewHolder.time_text.setText(simpleDateFormat.format(new Date(Long.valueOf(dataList.get(position).get("createTime")))));
        viewHolder.right_text.setText(dataList.get(position).get("money") + "元");
        return convertView;
    }


    private class ViewHolder {

        private TextView name_text;
        private CircleImageView head_image;
        private TextView time_text;
        private TextView right_text;

    }
}
