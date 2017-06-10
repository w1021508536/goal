package com.pi.small.goal.my.adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.DynamicEntity;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.weight.PinchImageView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/5 15:45
 * 修改：
 * 描述： 轨迹的适配器
 **/
public class TrajectoryAdapter extends BaseAdapter {


    private final Context context;
    private boolean operationShowFlag;
    private List<DynamicEntity> data;
    private myAdapterClickListener listener;

    public TrajectoryAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();

    }

    //    dynamicId = getIntent().getStringExtra("dynamicId");
//    aimId = getIntent().getStringExtra("aimId");
//    nick = getIntent().getStringExtra("nick");
//    avatar = getIntent().getStringExtra("avatar");
    public interface myAdapterClickListener {
        void great(String id);  //点赞

        void comment(String id); //评论

        void help(String id, String nick, String avatar);    //助力
    }

    public void setOnMyAdapterClickListener(myAdapterClickListener listener) {
        this.listener = listener;
    }


    /**
     * 是否显示下面的点赞 评论 主力的图标  自己看自己的不显示
     * create  wjz
     **/

    public void setOperationShowFlag(boolean operationShowFlag) {
        this.operationShowFlag = operationShowFlag;
    }

    public void setData(List<DynamicEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<DynamicEntity> data) {
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
        final ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_trajectory, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
            if (!operationShowFlag) {
                vh.rlOperation.setVisibility(View.GONE);
            }
            Drawable drawable = context.getResources().getDrawable(R.mipmap.local_icon);
            drawable.setBounds(0, 0, Utils.dip2px(context, 11), Utils.dip2px(context, 13));
            vh.tvCityItem.setCompoundDrawables(drawable, null, null, null);

        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        final DynamicEntity dynamicEntity = data.get(position);

        String timeStr = Utils.GetTime2(dynamicEntity.getDynamic().getUpdateTime());
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(timeStr);
        String time = m.replaceAll("").trim();
        vh.tvTimeItem.setText(time);
        vh.tvTimeDayItem.setText(timeStr.replace(time, ""));
        if (dynamicEntity.getDynamic().getContent().equals("")) {
            vh.tvTitleItem.setVisibility(View.GONE);
        } else {
            vh.tvTitleItem.setVisibility(View.VISIBLE);
        }
        vh.tvTitleItem.setText(dynamicEntity.getDynamic().getContent());
        vh.tvAddMoneyItem.setText(dynamicEntity.getDynamic().getMoney() + "");
        vh.tvSupportsItem.setText(dynamicEntity.getVotes() + "赞" + "  " + dynamicEntity.getSupports() + "助力");

        if (position == 0) {
            vh.viewLineTop.setVisibility(View.INVISIBLE);
        }

        List<DynamicEntity.CommentsBean> comments = dynamicEntity.getComments();
        final CommentAdapter adapter = new CommentAdapter(context, comments);
        vh.lvItem.setAdapter(adapter);
        Utils.setListViewHeightBasedOnChildren(vh.lvItem);

//        if (comments.size() > 1) {
//            vh.tvUserSupport1Item.setText(comments.get(0).getNick() + ":");
//            vh.tvContentSupport1Item.setText(comments.get(0).getContent());
//            vh.llSuppor1Item.setVisibility(View.VISIBLE);
//        }
        if (comments.size() > 2) {
            vh.tvSupportsNumsItem.setText("查看全部" + comments.size() + "条评论");
//            vh.tvUserSupport2Item.setText(comments.get(1).getNick() + ": ");
//            vh.tvContentSupport2Item.setText(comments.get(1).getContent());
//            vh.llSupport2Item.setVisibility(View.VISIBLE);
            vh.tvSupportsNumsItem.setVisibility(View.VISIBLE);
        }

        if (data.get(position).getHaveVote() == 1) {
            vh.imgGreat.setImageResource(R.mipmap.icon_vote_on);
        } else {
            vh.imgGreat.setImageResource(R.mipmap.icon_vote_off);
        }

        vh.imgItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                RelativeLayout rl_image = (RelativeLayout) ((Activity) context).findViewById(R.id.rl_image);
                PinchImageView pimg = (PinchImageView) rl_image.getChildAt(0);
                String s = Utils.GetPhotoPath(dynamicEntity.getDynamic().getImg1());
                if (!Utils.photoEmpty(s)) {
                    Picasso.with(context).load(Utils.GetPhotoPath(s)).into(pimg);
                } else {
                    pimg.setImageResource(R.drawable.image2);
                }

                rl_image.setVisibility(View.VISIBLE);
            }
        });

        vh.tvSupportsNumsItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.setShowMore(true);
                Utils.setListViewHeightBasedOnChildren(vh.lvItem);
                vh.tvSupportsNumsItem.setVisibility(View.GONE);
            }
        });
        vh.imgCommit.setOnClickListener(new myClick(data, position, vh));
        vh.imgGreat.setOnClickListener(new myClick(data, position, vh));
        vh.imgHelp.setOnClickListener(new myClick(data, position, vh));

        return convertView;
    }

    class myClick implements View.OnClickListener {

        private final int position;
        private final ViewHolder viewHolder;
        private List<DynamicEntity> data;

        public myClick(List<DynamicEntity> data, int position, ViewHolder viewHolder) {
            this.data = data;
            this.viewHolder = viewHolder;
            this.position = position;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.img_great:
                    if (listener != null) {
                        listener.great(data.get(position).getDynamic().getId() + "");
                        if (data.get(position).getHaveVote() == 0) {
                            data.get(position).setHaveVote(1);
                            viewHolder.imgGreat.setImageResource(R.mipmap.icon_vote_on);
                            data.get(position).setVotes(data.get(position).getVotes() + 1);
                            notifyDataSetChanged();
                        } else {
                            data.get(position).setHaveVote(0);
                            viewHolder.imgGreat.setImageResource(R.mipmap.icon_vote_off);
                            data.get(position).setVotes(data.get(position).getVotes() - 1);
                            notifyDataSetChanged();
                        }

                    }
                    break;
                case R.id.img_commit:
                    if (listener != null) {
                        listener.comment(data.get(position).getDynamic().getId() + "");
                    }
                    break;
                case R.id.img_help:
                    if (listener != null) {
                        listener.help(data.get(position).getDynamic().getId() + "", data.get(position).getDynamic().getNick(), data.get(position).getDynamic().getAvatar());


                    }
                    break;
            }
        }
    }

    static class ViewHolder {
        @InjectView(R.id.view_line_top)
        View viewLineTop;
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.tv_timeDay_item)
        TextView tvTimeDayItem;
        @InjectView(R.id.tv_title_item)
        TextView tvTitleItem;
        @InjectView(R.id.img_item)
        ImageView imgItem;
        @InjectView(R.id.tv_addMoney_item)
        TextView tvAddMoneyItem;
        @InjectView(R.id.rl_money_item)
        LinearLayout rlMoneyItem;
        @InjectView(R.id.img_great)
        ImageView imgGreat;
        @InjectView(R.id.img_commit)
        ImageView imgCommit;
        @InjectView(R.id.img_help)
        ImageView imgHelp;
        @InjectView(R.id.rl_operation)
        RelativeLayout rlOperation;
        @InjectView(R.id.tv_supports_item)
        TextView tvSupportsItem;
        @InjectView(R.id.tv_city_item)
        TextView tvCityItem;
        @InjectView(R.id.lv_item)
        ListView lvItem;
        @InjectView(R.id.tv_supportsNums_item)
        TextView tvSupportsNumsItem;
        @InjectView(R.id.rl_operation_item)
        LinearLayout rlOperationItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
