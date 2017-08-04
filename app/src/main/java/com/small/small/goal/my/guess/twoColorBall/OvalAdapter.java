package com.small.small.goal.my.guess.twoColorBall;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.utils.BaseActivity;
import com.small.small.goal.utils.CacheUtil;
import com.small.small.goal.utils.Combine;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/27 16:48
 * 修改：
 * 描述：大乐透的小圆圈的适配器
 **/
public class OvalAdapter extends BaseAdapter {

    private final Context context;
    private final int flag;
    private final int redMin;
    private final int blueMIn;
    private List<OvalEntity> data;
    private final TextView tv_touzhu;


    public OvalAdapter(Context context, int flag) {
        this.context = context;
        this.data = new ArrayList<>();
        tv_touzhu = (TextView) ((BaseActivity) context).findViewById(R.id.tv_touzhu_dayPlay);
        this.flag = flag;

        redMin = 6;
        blueMIn = 1;
    }

    public void releaseData() {
        for (OvalEntity entity : data) {
            entity.setSelected(false);
        }
        notifyDataSetChanged();
        if (tv_touzhu != null) {
            tv_touzhu.setText("至少选择6个红球+1个蓝球");
        }
    }

    public void setData(List<OvalEntity> data) {
        this.data = data;
    }

    public List<OvalEntity> getData() {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_oval, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        OvalEntity ovalEntity = data.get(position);
        vh.tv.setText(data.get(position).getContent());
        if (ovalEntity.isSelected()) {
            if (ovalEntity.getFlag() == 0) {
                vh.tv.setBackgroundResource(R.drawable.bg_yuan_red);
            } else {
                vh.tv.setBackgroundResource(R.drawable.bg_yuan_blue);
            }
            vh.tv.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            if (ovalEntity.getFlag() == 0) {
                vh.tv.setTextColor(context.getResources().getColor(R.color.red));
            } else {
                vh.tv.setTextColor(context.getResources().getColor(R.color.blue));
            }
            vh.tv.setBackgroundResource(R.drawable.bg_yuan_hui_null);
        }

        vh.tv.setOnClickListener(new myClick(vh, data, position));
        return convertView;
    }

    class myClick implements View.OnClickListener {

        private final ViewHolder vh;
        private final List<OvalEntity> data;
        private final int position;

        public myClick(ViewHolder vh, List<OvalEntity> data, int position) {
            this.vh = vh;
            this.data = data;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.tv:
                    data.get(position).setSelected(!data.get(position).isSelected());
                    notifyDataSetChanged();
                    setParentTv();
                    break;
            }
        }
    }

    private void setParentTv() {

        int selectedRed = 0;
        int selectedBlue = 0;

        List<OvalEntity> selectRedData = new ArrayList<>();
        List<OvalEntity> selectBlueData = new ArrayList<>();


        for (OvalEntity entity : data) {
            if (entity.isSelected()) {
                if (entity.getFlag() == 0) {
                    selectRedData.add(entity);
                } else {
                    selectBlueData.add(entity);
                }
            }
        }
        if (selectBlueData.size() > 0) {
            CacheUtil.getInstance().setSelectedShuangseqiuBlueData(selectBlueData);
        }
        if (selectRedData.size() > 0) {
            CacheUtil.getInstance().setSelectedShuangseqiuRedData(selectRedData);
        }
        selectedBlue = CacheUtil.getInstance().getSelectedShuangseqiuBlueData().size();
        selectedRed = CacheUtil.getInstance().getSelectedShuangseqiuRedData().size();

        if (selectedBlue >= blueMIn && selectedRed >= redMin) {

            long redZhu = Combine.getNumber(redMin, selectedRed);
            long blueZhu = Combine.getNumber(blueMIn, selectedBlue);

            tv_touzhu.setText("您已选择" + (redZhu * blueZhu) + "注");
            tv_touzhu.setClickable(true);
        } else {
            tv_touzhu.setText("至少选择" + redMin + "个红球+" + blueMIn + "个蓝球");
        }

    }

    static class ViewHolder {
        @InjectView(R.id.tv)
        TextView tv;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
