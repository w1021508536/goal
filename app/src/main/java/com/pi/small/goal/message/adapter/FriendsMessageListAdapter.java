package com.pi.small.goal.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.message.activity.FriendsMessageListActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/5/26.
 */

public class FriendsMessageListAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> dataList;

    private int rightWidth;

    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.FIT_XY)
            .setLoadingDrawableId(R.mipmap.ic_launcher)
            .setFailureDrawableId(R.mipmap.icon_message_system)
            .build();


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
            viewHolder.head_image = (ImageView) convertView.findViewById(R.id.head_image);
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
//        viewHolder.content_text.setText(dataList.get(position).get("sendUserBrief"));
        x.image().bind(viewHolder.head_image, Url.PhotoUrl + "/" + dataList.get(position).get("avatar"), imageOptions);

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
//                RequestParams requestParams = new RequestParams(Url.Url + Url.FriendAgree);
//                requestParams.addHeader("token", Utils.GetToken(context));
//                requestParams.addHeader("deviceId", MyApplication.deviceId);
//                requestParams.addBodyParameter("uid", dataList.get(position).get("sendUserId"));
//                x.http().post(requestParams, new Callback.CommonCallback<String>() {
//                    @Override
//                    public void onSuccess(String result) {
//                        try {
//                            String code = new JSONObject(result).getString("code");
//                            if (code.equals("0")) {
//
//                                FriendsMessageListActivity.UpData();
//
//                            } else {
//                                Utils.showToast(context, new JSONObject(result).getString("msg"));
//                            }
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable ex, boolean isOnCallback) {
//                        Utils.showToast(context, ex.getMessage());
//                    }
//
//                    @Override
//                    public void onCancelled(CancelledException cex) {
//
//                    }
//
//                    @Override
//                    public void onFinished() {
//
//                    }
//                });
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
        private ImageView head_image;
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
