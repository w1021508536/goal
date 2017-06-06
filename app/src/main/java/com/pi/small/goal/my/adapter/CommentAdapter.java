package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/6 8:45
 * 修改：
 * 描述： 评论的adapter
 **/
public class CommentAdapter extends BaseAdapter {

    private final Context context;

    public CommentAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder vh;

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_comment, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    static class ViewHolder {
        @InjectView(R.id.icon_item)
        CircleImageView iconItem;
        @InjectView(R.id.tv_userNmae_item)
        TextView tvUserNmaeItem;
        @InjectView(R.id.rl_item)
        RelativeLayout rlItem;
        @InjectView(R.id.tv_support_item)
        TextView tvSupportItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
