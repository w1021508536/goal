package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.DynamicEntity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/6 8:45
 * 修改：
 * 描述： 评论的adapter
 **/
public class CommentAdapter extends BaseAdapter {

    private final Context context;
    private final List<DynamicEntity.CommentsBean> comments;
    private boolean showFlag = false;

    public CommentAdapter(Context context, List<DynamicEntity.CommentsBean> comments) {
        this.context = context;
        this.comments = comments;
    }

    public void setShowMore(boolean showFlag) {
        this.showFlag = showFlag;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {

        if(showFlag){
            return comments.size();
        }else {
            if(comments.size()>2){
                return 2;
            }else {
                return comments.size();
            }
        }
    }

    @Override
    public Object getItem(int position) {
        return comments.get(position);
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

        DynamicEntity.CommentsBean commentsBean = comments.get(position);

        if (showFlag) {
            vh.tvUserSupport2Item.setText(commentsBean.getNick() + ": ");
            vh.tvContentSupport2Item.setText(commentsBean.getContent());
        } else {
            if (position < 2) {
                vh.tvUserSupport2Item.setText(commentsBean.getNick() + ": ");
                vh.tvContentSupport2Item.setText(commentsBean.getContent());
            }
        }
        return convertView;
    }


    static class ViewHolder {
        @InjectView(R.id.tv_userSupport2_item)
        TextView tvUserSupport2Item;
        @InjectView(R.id.tv_contentSupport2_item)
        TextView tvContentSupport2Item;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
