package com.small.small.goal.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/5/26.
 */

public class FriendsMessageListAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> dataList;

    private int rightWidth;

    private SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm");

    public FriendsMessageListAdapter(Context context, List<Map<String, String>> dataList, int rightWidth) {
        this.context = context;
        this.dataList = dataList;
        this.rightWidth = rightWidth;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_friends_message, parent, false);

            viewHolder.name_text = (TextView) convertView.findViewById(R.id.name_text);
            viewHolder.head_image = (CircleImageView) convertView.findViewById(R.id.head_image);
            viewHolder.content_text = (TextView) convertView.findViewById(R.id.content_text);
            viewHolder.refuse_text = (TextView) convertView.findViewById(R.id.refuse_text);
            viewHolder.agree_text = (TextView) convertView.findViewById(R.id.agree_text);

            viewHolder.right_text = (TextView) convertView.findViewById(R.id.right_text);
            viewHolder.agree_layout = (RelativeLayout) convertView.findViewById(R.id.agree_layout);
            viewHolder.left_layout = (RelativeLayout) convertView.findViewById(R.id.left_layout);
            viewHolder.right_layout = (RelativeLayout) convertView.findViewById(R.id.right_layout);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.name_text.setText(dataList.get(position).get("sendUserNick"));
//      viewHolder.content_text.setText(dataList.get(position).get("sendUserBrief"));

        if (!dataList.get(position).get("sendUserAvatar").equals("")) {
            Picasso.with(context).load(Utils.GetPhotoPath(dataList.get(position).get("sendUserAvatar"))).into(viewHolder.head_image);
        } else {
            viewHolder.head_image.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_head));
        }

        viewHolder.content_text.setText(simpleDateFormat1.format(new Date(Long.valueOf(dataList.get(position).get("createTime")))) + "申请加你为好友");

        if (dataList.get(position).get("status").equals("0")) {
            viewHolder.agree_layout.setVisibility(View.VISIBLE);
            viewHolder.right_text.setVisibility(View.GONE);

        } else if (dataList.get(position).get("status").equals("-1")) {
            viewHolder.agree_layout.setVisibility(View.GONE);
            viewHolder.right_text.setVisibility(View.VISIBLE);
            viewHolder.right_text.setText("已拒绝");

        } else if (dataList.get(position).get("status").equals("1")) {
            viewHolder.agree_layout.setVisibility(View.GONE);
            viewHolder.right_text.setVisibility(View.VISIBLE);
            viewHolder.right_text.setText("已同意");
        }

        viewHolder.refuse_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (refuseListener != null) {
                    refuseListener.onRefuseItemClick(v, position);

                }
            }
        });

        viewHolder.agree_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (agreeListener != null) {
                    agreeListener.onAgreeItemClick(v, position);
                }
            }
        });


        LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        viewHolder.left_layout.setLayoutParams(lp1);
        LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(rightWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        viewHolder.right_layout.setLayoutParams(lp2);


        viewHolder.right_layout.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightItemClick(v, position);

                }
            }
        });

        return convertView;
    }


    private class ViewHolder {

        private TextView name_text;
        private CircleImageView head_image;
        private TextView content_text;

        private TextView refuse_text;
        private TextView agree_text;

        private RelativeLayout agree_layout;
        private TextView right_text;

        private RelativeLayout left_layout;
        private RelativeLayout right_layout;


    }

    /**
     * 单击事件监听器
     */
    private onRightItemClickListener mListener = null;

    public void setOnRightItemClickListener(onRightItemClickListener listener) {
        mListener = listener;
    }

    public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }

    /**
     * 单击事件监听器
     */
    private onAgreeItemClickListener agreeListener = null;

    public void setOnAgreeItemClickListener(onAgreeItemClickListener listener) {
        agreeListener = listener;
    }

    public interface onAgreeItemClickListener {
        void onAgreeItemClick(View v, int position);
    }


    /**
     * 单击事件监听器
     */
    private onRefuseItemClickListener refuseListener = null;

    public void setOnRefuseItemClickListener(onRefuseItemClickListener listener) {
        refuseListener = listener;
    }

    public interface onRefuseItemClickListener {
        void onRefuseItemClick(View v, int position);
    }

}
