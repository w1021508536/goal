package com.pi.small.goal.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/5/26.
 */

public class AddFriendAdapter extends BaseAdapter {
    private Context context;
    private List<Map<String, String>> dataList;

    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .setLoadingDrawableId(R.mipmap.ic_launcher)
            .setFailureDrawableId(R.mipmap.icon_message_system)
            .build();

    public AddFriendAdapter(Context context, List<Map<String, String>> dataList) {
        this.context = context;
        this.dataList = dataList;
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_friend, parent, false);
            viewHolder.head_image = (CircleImageView) convertView.findViewById(R.id.head_image);
            viewHolder.name_text = (TextView) convertView.findViewById(R.id.name_text);
            viewHolder.add_text = (TextView) convertView.findViewById(R.id.add_text);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        if (!dataList.get(position).get("avatar").equals("")) {
            Picasso.with(context).load(Utils.GetPhotoPath(dataList.get(position).get("avatar"))).into(viewHolder.head_image);
        } else {
            viewHolder.head_image.setImageDrawable(context.getDrawable(R.mipmap.icon_head));
        }
        viewHolder.name_text.setText(dataList.get(position).get("nick"));

        if (dataList.get(position).get("friend").equals("0")) {
            viewHolder.add_text.setVisibility(View.GONE);
        } else {
            viewHolder.add_text.setVisibility(View.VISIBLE);
        }
        viewHolder.add_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RequestParams requestParams = new RequestParams(Url.Url + Url.FriendApply);
                requestParams.addHeader("token", Utils.UserSharedPreferences(context).getString("token", ""));
                requestParams.addHeader("deviceId", MyApplication.deviceId);
                requestParams.addBodyParameter("token", Utils.UserSharedPreferences(context).getString("token", ""));
                requestParams.addBodyParameter("deviceId", MyApplication.deviceId);
                requestParams.addBodyParameter("uid", dataList.get(position).get("id"));

                x.http().get(requestParams, new Callback.CommonCallback<String>() {
                    @Override
                    public void onSuccess(String result) {
                        System.out.println("==========申请添加好友===========" + result);
                        try {
                            String code = new JSONObject(result).getString("code");
                            if (code.equals("0")) {
                                Utils.showToast(context, "申请添加好友成功");
                            } else {
                                Utils.showToast(context, new JSONObject(result).getString("msg"));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(Throwable ex, boolean isOnCallback) {

                    }

                    @Override
                    public void onCancelled(CancelledException cex) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
            }
        });

        return convertView;
    }


    private class ViewHolder {

        private CircleImageView head_image;
        private TextView name_text;
        private TextView add_text;


    }
}