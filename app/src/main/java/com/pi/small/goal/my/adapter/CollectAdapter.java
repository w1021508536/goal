package com.pi.small.goal.my.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.my.activity.RenameActivity;
import com.pi.small.goal.my.activity.AimMoreActivity;
import com.pi.small.goal.my.entry.CollectEntity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/2 17:38
 * 修改：
 * 描述：我的收藏的adapter
 **/
public class CollectAdapter extends BaseAdapter {

    private final Context context;
    private List<CollectEntity> data;

    public CollectAdapter(Context context) {
        this.context = context;
        this.data = new ArrayList<>();
    }

    public void setData(List<CollectEntity> data) {
        this.data = data;
        notifyDataSetChanged();
    }

    public void addData(List<CollectEntity> data) {
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
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_collect, null);
            vh = new ViewHolder(convertView);
            convertView.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }
        final CollectEntity collectEntity = data.get(position);

        vh.tvTitleItem.setText(collectEntity.getName());
        vh.tvBudgetNumItem.setText(((int) collectEntity.getBudget()) + "");   //预算
        vh.tvSupportNumItem.setText(collectEntity.getSupport() + "");  //支持人数

        if (Utils.photoEmpty(collectEntity.getAvatar())) {
            Picasso.with(context).load(collectEntity.getAvatar()).into(vh.iconItem);
        }
        vh.tvNameItem.setText(collectEntity.getNick());
        if (Utils.photoEmpty(collectEntity.getImg())) {
            Picasso.with(context).load(Utils.GetPhotoPath(collectEntity.getImg())).into(vh.imgCityItem);
        }

        //      vh.tvDayNumItem.setText(collectEntity.getCycle() + "");
        Date date = new Date();
        long l = date.getTime() - collectEntity.getCreateTime();
        long timeMinute = l / 60000;  //分钟
        long timeHour = timeMinute / 60;
        long timeDay = timeHour / 24;

        vh.tvDayNumItem.setText((collectEntity.getCycle() * 30 - timeDay) + "");

//

//        vh.tvTimeItem.setText(Utils.GetTime(collectEntity.getCreateTime()));


        float v = (float) (collectEntity.getMoney() / collectEntity.getBudget());

        DecimalFormat df = new DecimalFormat("#.0");

        //通过设置权重来改变横线块占比长度
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) vh.imgProgressItem.getLayoutParams();
        String format = df.format(v * 100);
        if (v < 0.01) {
            vh.tvProgressItem.setText("0" + format + "%");
        } else {
            vh.tvProgressItem.setText(format + "%");
        }

        if (v < 0.01) {
            lp.weight = 0.01f;
        } else if (v == 1) {
            lp.weight = 1;
            vh.viewWidth.setVisibility(View.GONE);
        } else {
            float v1 = v / ((1 - v));
            lp.weight = v1;
        }
        //   vh.viewWidth.setVisibility(View.GONE);
        vh.imgProgressItem.setLayoutParams(lp);
        // vh.viewWidth.setLayoutParams(new LinearLayout.LayoutParams(0,0,1-lp.width));
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AimMoreActivity.class);
                intent.putExtra(AimMoreActivity.KEY_AIMID, collectEntity.getId() + "");
                context.startActivity(intent);
            }
        });
        vh.imgCollectItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                collectAim(collectEntity.getId(), "0", position);
            }
        });
        return convertView;
    }

    private void collectAim(int aimId, String status, final int position) {
        RequestParams requestParams = Utils.getRequestParams(context);
        requestParams.setUri(Url.Url + "/aim/collect");
        requestParams.addBodyParameter("aimId", aimId + "");
        requestParams.addBodyParameter("status", status);
        XUtil.post(requestParams, context, new XUtil.XCallBackLinstener() {
            @Override
            public void onSuccess(String result) {
                if (!RenameActivity.callOk(result)) return;
                data.remove(position);
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    Utils.showToast(context, (String) jsonObject.get("msg"));
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


    static class ViewHolder {
        @InjectView(R.id.line)
        View line;
        @InjectView(R.id.icon_item)
        CircleImageView iconItem;
        @InjectView(R.id.tv_name_item)
        TextView tvNameItem;
        @InjectView(R.id.tv_time_item)
        TextView tvTimeItem;
        @InjectView(R.id.img_collect_item)
        ImageView imgCollectItem;
        @InjectView(R.id.img_city_item)
        ImageView imgCityItem;
        @InjectView(R.id.tv_title_item)
        TextView tvTitleItem;
        @InjectView(R.id.img_progress_item)
        ImageView imgProgressItem;
        @InjectView(R.id.tv_progress_item)
        TextView tvProgressItem;
        @InjectView(R.id.view_width)
        View viewWidth;
        @InjectView(R.id.ll_progress_item)
        LinearLayout llProgressItem;
        @InjectView(R.id.tv_budget_num_item)
        TextView tvBudgetNumItem;
        @InjectView(R.id.tv_support_num_item)
        TextView tvSupportNumItem;
        @InjectView(R.id.tv_day_num_item)
        TextView tvDayNumItem;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}
