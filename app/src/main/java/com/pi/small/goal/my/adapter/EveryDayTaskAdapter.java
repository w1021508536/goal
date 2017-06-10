package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.EveryTaskAdapterEntity;
import com.pi.small.goal.utils.CacheUtil;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/7 18:21
 * 修改：
 * 描述：
 **/
public class EveryDayTaskAdapter extends BaseAdapter {

    private final Context context;
    private List<EveryTaskAdapterEntity> data;

    public EveryDayTaskAdapter(Context context) {
        this.context = context;
    }

    public void setData(List<EveryTaskAdapterEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<EveryTaskAdapterEntity> data) {
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
        titleViewHolder titleViewHolder;
        contentViewHolder contentViewHolder;
        if (getItemViewType(position) == RedAdapter.TYPE_TITLE) {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_everydaytitle_task, null);
                titleViewHolder = new titleViewHolder(convertView);
                convertView.setTag(titleViewHolder);
            } else {
                titleViewHolder = (titleViewHolder) convertView.getTag();
            }
            titleViewHolder.tvTitle.setText(data.get(position).getTitleName());

        } else {

            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_everydaycontent_task, null);
                contentViewHolder = new contentViewHolder(convertView);
                convertView.setTag(contentViewHolder);
            } else {
                contentViewHolder = (contentViewHolder) convertView.getTag();
            }

            EveryTaskAdapterEntity info = data.get(position);
            contentViewHolder.tvTitle.setText(info.getName());
            contentViewHolder.tvGoto.setClickable(true);
            if (info.getName().equals("每日签到")) {

                if (CacheUtil.getInstance().isSignFlag()) {
                    contentViewHolder.tvGoto.setBackgroundResource(R.drawable.background_oval_gray_gray);
                    contentViewHolder.tvGoto.setClickable(false);
                }

            }
        }


        //  if(Utils.photoEmpty(everyTaskEntity.get)){}

        return convertView;
    }


    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }


    static class contentViewHolder {
        @InjectView(R.id.img_icon)
        ImageView imgIcon;
        @InjectView(R.id.tv_title)
        TextView tvTitle;
        @InjectView(R.id.tv_goto)
        TextView tvGoto;

        contentViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class titleViewHolder {
        @InjectView(R.id.tv_title)
        TextView tvTitle;

        titleViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
