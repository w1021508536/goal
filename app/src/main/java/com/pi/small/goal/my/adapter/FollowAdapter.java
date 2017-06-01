package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.FollowEntry;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 18:34
 * 描述： 我的关注de adapter
 * 修改：
 **/
public class FollowAdapter extends BaseAdapter {

    private Context context;
    private List<FollowEntry> data;

    public FollowAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void setData(List<FollowEntry> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<FollowEntry> data) {
        this.data.addAll(data);
        notifyDataSetChanged();
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_follow, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        FollowEntry followEntry = data.get(position);
        vh.tvNameItem.setText(followEntry.getNick());
        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.icon_item)
        CircleImageView iconItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv_follow_item)
        TextView tvFollowItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
