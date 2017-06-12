package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.my.entry.WalletEntry;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;

import org.xutils.http.RequestParams;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/1 9:36
 * 修改：
 * 描述：  錢包明細的adapter
 **/
public class RedAdapter extends BaseAdapter {

    private List<WalletEntry> data;
    private Context context;
    public static final int TYPE_TITLE = 0;
    public static final int TYPE_CONTENT = 1;

    public RedAdapter(Context context, List<WalletEntry> data) {
        this.context = context;
        this.data = data;
    }

    public void setData(List<WalletEntry> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<WalletEntry> data) {
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
        final titleViewHolder titleViewHolder;
        if (getItemViewType(position) == TYPE_TITLE) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_title_red, null);
                titleViewHolder = new titleViewHolder(convertView);
                convertView.setTag(titleViewHolder);
            } else {
                titleViewHolder = (RedAdapter.titleViewHolder) convertView.getTag();
            }
            titleViewHolder.tvLingquItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    obtainRedAll();
                }
            });

        } else {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_content_red, null);
                vh = new ViewHolder(convertView);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }

            final WalletEntry walletEntry = data.get(position);
            vh.tvTimeItem.setText(RedMoreAdapter.getTimeDate(walletEntry.getCreateTime()));
            vh.tvNameItem.setText("助力红包—来自");
            vh.tvUserNameItem.setText(walletEntry.getFromUserNick());

            vh.tvHaveItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    obtainRed(position, walletEntry.getId() + "");

                }
            });
        }

        return convertView;
    }


    /**
     * 领取全部红包
     * create  wjz
     **/
    private void obtainRedAll() {


        RequestParams requestParams = new RequestParams();
        requestParams.setUri(Url.Url + "/redpacket/draw/all");
        SharedPreferences sp = Utils.UserSharedPreferences(context);
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);

        XUtil.post(requestParams, context, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!Utils.callOk(result)) return;
                data.removeAll(data);
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


    /**
     * 获取红包
     * create  wjz
     **/
    private void obtainRed(final int position, String packetId) {


        RequestParams requestParams = new RequestParams();
        requestParams.setUri(Url.Url + "/redpacket/draw");
        SharedPreferences sp = Utils.UserSharedPreferences(context);
        requestParams.addHeader("token", sp.getString("token", ""));
        requestParams.addHeader("deviceId", MyApplication.deviceId);
        requestParams.addBodyParameter("packetId", packetId);

        XUtil.post(requestParams, context, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {

                if (!Utils.callOk(result)) return;
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

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getType();
    }


    static class ViewHolder {
        @InjectView(R.id.img_item)
        ImageView imgItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv_userName_item)
        TextView tvUserNameItem;
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.tv_have_item)
        TextView tvHaveItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class titleViewHolder {
        @InjectView(R.id.tv_lingqu_item)
        TextView tvLingquItem;

        titleViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
