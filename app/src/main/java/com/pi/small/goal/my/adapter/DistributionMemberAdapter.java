package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.entity.MemberEntity;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JS on 2017-06-19.
 */

public class DistributionMemberAdapter extends BaseAdapter {

    private List<MemberEntity> memberEntityList;
    private Context context;

    public DistributionMemberAdapter(Context context, List<MemberEntity> memberEntityList) {
        this.context = context;
        this.memberEntityList = memberEntityList;
    }

    @Override
    public int getCount() {
        return memberEntityList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_member, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.head_image = (CircleImageView) convertView.findViewById(R.id.head_image);
            viewHolder.name_text = (TextView) convertView.findViewById(R.id.name_text);
            viewHolder.level_text = (TextView) convertView.findViewById(R.id.level_text);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.name_text.setText(memberEntityList.get(position).getNick());

        if (memberEntityList.get(position).getLevel().equals("1")) {
            viewHolder.level_text.setText("分公司");
            viewHolder.level_text.setBackgroundResource(R.drawable.background_level_senior);
        } else if (memberEntityList.get(position).getLevel().equals("2")) {
            viewHolder.level_text.setText("高级代理");
            viewHolder.level_text.setBackgroundResource(R.drawable.background_level_senior);
        } else if (memberEntityList.get(position).getLevel().equals("3")) {
            viewHolder.level_text.setText("普通代理");
            viewHolder.level_text.setBackgroundResource(R.drawable.background_level_ordinary);
        } else {
            viewHolder.level_text.setVisibility(View.GONE);
        }

        if (!memberEntityList.get(position).getAvatar().equals("")) {
            Picasso.with(context).load(Utils.GetPhotoPath(memberEntityList.get(position).getAvatar())).into(viewHolder.head_image);
        } else {
            viewHolder.head_image.setImageDrawable(context.getDrawable(R.mipmap.icon_head));
        }


        return convertView;
    }

    private class ViewHolder {
        CircleImageView head_image;
        TextView name_text;
        TextView level_text;
    }
}
