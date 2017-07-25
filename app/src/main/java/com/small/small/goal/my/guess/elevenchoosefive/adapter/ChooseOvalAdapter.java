package com.small.small.goal.my.guess.elevenchoosefive.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.my.guess.elevenchoosefive.entity.ChooseOvalEntity;
import com.small.small.goal.utils.BaseActivity;
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
 * 描述：11选5的小圆圈的适配器
 **/
public class ChooseOvalAdapter extends BaseAdapter {

    private final Context context;
    private int flag;   //区分是11选5  还是七乐彩
    public static final int ELEVEN_CHOOSE = 0;
    public static final int ELEVEN_CHOOSE_ZHI_2 = 2;
    public static final int ELEVEN_CHOOSE_ZHI_3 = 3;
    public static final int FLAG_SEVEN_HAPPAY = 1;
    public static final int FLAG_SEVEN_STAR = 4;
    private int min;

    private List<ChooseOvalEntity> data;
    private final TextView tv_touzhu;
    private boolean visibilyFlag;
    private int selectedNums = 0;
    private onOvalClickListener listener;

    public ChooseOvalAdapter(Context context, int flag) {
        this.context = context;
        this.data = new ArrayList<>();
        this.flag = flag;
        tv_touzhu = (TextView) ((BaseActivity) context).findViewById(R.id.tv_touzhu);
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getFlag() {
        return flag;
    }


    public interface onOvalClickListener {
        void onclick(int flag);
    }

    public void setOnOvalClickListener(onOvalClickListener listener) {
        this.listener = listener;
    }

    public void setMin(int min) {
        this.min = min;
    }

    public void setMissVisibily(boolean visibilyFlag) {
        this.visibilyFlag = visibilyFlag;
        notifyDataSetChanged();
    }

    public int getSelectedNums() {
        return selectedNums;
    }

    public void releaseData() {
        for (ChooseOvalEntity entity : data) {
            entity.setSelected(false);
        }
        notifyDataSetChanged();
    }

    public void setData(List<ChooseOvalEntity> data) {
        this.data = data;
    }

    public List<ChooseOvalEntity> getData() {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_choose_oval, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        ChooseOvalEntity ovalEntity = data.get(position);
        vh.tv.setText(data.get(position).getContent());
        if (ovalEntity.isSelected()) {
            vh.tv.setBackgroundResource(R.drawable.bg_yuan_red);
            vh.tv.setTextColor(context.getResources().getColor(R.color.white));
        } else {
            vh.tv.setTextColor(context.getResources().getColor(R.color.red));
            vh.tv.setBackgroundResource(R.drawable.bg_yuan_hui_null);
        }
        vh.tvMiss.setText(ovalEntity.getMissNums() + "");
        if (visibilyFlag) {
            vh.tvMiss.setVisibility(View.VISIBLE);
        } else {
            vh.tvMiss.setVisibility(View.GONE);
        }

        vh.tv.setOnClickListener(new myClick(vh, data, position));
        return convertView;
    }

    class myClick implements View.OnClickListener {

        private final ViewHolder vh;
        private final List<ChooseOvalEntity> data;
        private final int position;

        public myClick(ViewHolder vh, List<ChooseOvalEntity> data, int position) {
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


        int selectedNums = 0;

        List<ChooseOvalEntity> selectData = new ArrayList<>();

        for (ChooseOvalEntity entity : data) {
            if (entity.isSelected()) {
                selectData.add(entity);
                selectedNums++;
            }
        }
        this.selectedNums = selectedNums;

        if (flag == ELEVEN_CHOOSE_ZHI_2) {
            if (listener != null) listener.onclick(flag);

        } else if (flag == ELEVEN_CHOOSE_ZHI_3) {
            if (listener != null) listener.onclick(flag);
        } else if (flag == FLAG_SEVEN_STAR) {
            if (listener != null) {
                listener.onclick(flag);
            }
        } else {
            if (min == 10) min = 2;
            if (min == 12) min = 3;
            if (selectedNums >= min) {
//            Map<Integer, List<ChooseOvalEntity>> map = new HashMap<>();
//            map.put(min, selectData);
//            CacheUtil.getInstance().addElevenChooseFive(map);

                long combination = Combine.getNumber(min, selectedNums);

                tv_touzhu.setText("共" + combination + "注，下一步");
                tv_touzhu.setClickable(true);

            }
        }
    }


    static class ViewHolder {
        @InjectView(R.id.tv)
        TextView tv;
        @InjectView(R.id.tv_miss)
        TextView tvMiss;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
