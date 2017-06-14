package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.activity.RenameActivity;
import com.pi.small.goal.my.entry.FollowEntry;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.squareup.picasso.Picasso;

import org.xutils.http.RequestParams;

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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_follow, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final FollowEntry followEntry = data.get(position);
        vh.tvNameItem.setText(followEntry.getNick());
        if (!"".equals(followEntry.getAvatar())) {
            Picasso.with(context).load(Utils.GetPhotoPath(followEntry.getAvatar())).into(vh.iconItem);
        } else {
            vh.iconItem.setImageResource(R.mipmap.icon_user);
        }

        vh.tvNoFollowItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                follow(followEntry.getFollowUserId(), position);
            }
        });

        return convertView;
    }

    /**
     * 关注or取消关注
     * create  wjz
     **/

    private void follow(int followUserId, final int position) {

        SharedPreferences sp = Utils.UserSharedPreferences(context);
        RequestParams requestParams = new RequestParams(Url.Url + "/user/follow");
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("followUserId", followUserId + "");
        //  requestParams.addBodyParameter("status", 0 + "");                //0 否 1 是


        XUtil.post(requestParams, context, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!RenameActivity.callOk(result)) return;
                data.remove(position);
                notifyDataSetChanged();
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {

            }

            @Override
            public void onFinished() {

            }
        });


    }


    static class ViewHolder {
        @InjectView(R.id.icon_item)
        CircleImageView iconItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv_noFollow_item)
        TextView tvNoFollowItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
