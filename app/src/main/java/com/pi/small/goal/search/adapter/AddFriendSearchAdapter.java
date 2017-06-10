package com.pi.small.goal.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.search.activity.UserDetitalActivity;
import com.pi.small.goal.utils.MyListView;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.UserSearchEntity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JS on 2017-06-10.
 */

public class AddFriendSearchAdapter extends BaseAdapter {

    private List<UserSearchEntity> dataList;
    private Context context;

    public AddFriendSearchAdapter(List<UserSearchEntity> dataList, Context context) {
        this.dataList = dataList;
        this.context = context;
    }


    @Override
    public int getCount() {
        return dataList.size();
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder viewHolder;
        if (convertView == null) {

            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_add_friend_search, parent, false);
            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        if (!dataList.get(position).getAvatar().equals("")) {
            Picasso.with(context).load(Utils.GetPhotoPath(dataList.get(position).getAvatar())).into(viewHolder.headImage);
        } else {
            viewHolder.headImage.setImageDrawable(context.getResources().getDrawable(R.mipmap.icon_head));
        }

        viewHolder.headImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(context, UserDetitalActivity.class);
                intent.putExtra("userId", dataList.get(position).getId());
                context.startActivity(intent);
            }
        });

        viewHolder.nameText.setText(dataList.get(position).getNick());
        viewHolder.briefText.setText(dataList.get(position).getBrief());
        viewHolder.attentionText.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_white_yellow_corner));
        viewHolder.attentionText.setTextColor(context.getResources().getColor(R.color.yellow_light));
        viewHolder.attentionText.setText("关注");

        viewHolder.attentionText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams requestParams = new RequestParams(Url.Url + Url.Follow);
                requestParams.addHeader("token", Utils.GetToken(context));
                requestParams.addHeader("deviceId", MyApplication.deviceId);
                requestParams.addBodyParameter("followUserId", dataList.get(position).getId());
                XUtil.post(requestParams, context, new XUtil.XCallBackLinstener() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("==========attention==============" + result);
                        try {
                            if (new JSONObject(result).getString("code").equals("0")) {
                                if (dataList.get(position).getIsFollowed().equals("1")) {
                                    dataList.get(position).setIsFollowed("0");
                                    viewHolder.attentionText.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_white_gray_corner));
                                    viewHolder.attentionText.setTextColor(context.getResources().getColor(R.color.gray_heavy));
                                    viewHolder.attentionText.setText("已关注");
                                } else {
                                    dataList.get(position).setIsFollowed("1");
                                    viewHolder.attentionText.setBackgroundDrawable(context.getResources().getDrawable(R.drawable.background_white_yellow_corner));
                                    viewHolder.attentionText.setTextColor(context.getResources().getColor(R.color.yellow_light));
                                    viewHolder.attentionText.setText("关注");
                                }

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });

        if (dataList.get(position).getAimEntityList().size() != 0) {
            if (dataList.get(position).getAimEntityList().size() > 5) {
                viewHolder.aimAdapter = new AimAdapter(dataList.get(position).getAimEntityList(), context, 5);
                viewHolder.commentList.setAdapter(viewHolder.aimAdapter);
            } else {
                viewHolder.aimAdapter = new AimAdapter(dataList.get(position).getAimEntityList(), context, dataList.get(position).getAimEntityList().size());
                viewHolder.commentList.setAdapter(viewHolder.aimAdapter);
            }
        }


        return convertView;
    }

    class ViewHolder {
        @InjectView(R.id.head_image)
        CircleImageView headImage;
        @InjectView(R.id.name_text)
        TextView nameText;
        @InjectView(R.id.brief_text)
        TextView briefText;
        @InjectView(R.id.attention_text)
        TextView attentionText;

        @InjectView(R.id.comment_list)
        MyListView commentList;

        private AimAdapter aimAdapter;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
