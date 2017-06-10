package com.pi.small.goal.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.pi.small.goal.MyApplication;
import com.pi.small.goal.R;
import com.pi.small.goal.search.activity.AddFriendSearchActivity;
import com.pi.small.goal.search.activity.UserDetitalActivity;
import com.pi.small.goal.utils.BaseActivity;
import com.pi.small.goal.utils.MyListView;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;
import com.pi.small.goal.utils.XUtil;
import com.pi.small.goal.utils.entity.AimEntity;
import com.pi.small.goal.utils.entity.UserSearchEntity;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by JS on 2017-06-10.
 */

public class AimAdapter extends BaseAdapter {

    private List<AimEntity> dataList;
    private Context context;
    private int number;

    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setLoadingDrawableId(R.mipmap.background_load)
            .setFailureDrawableId(R.mipmap.background_fail)
            .build();
    public AimAdapter(List<AimEntity> dataList, Context context, int number) {
        this.dataList = dataList;
        this.context = context;
        this.number = number;
    }


    @Override
    public int getCount() {
        return number;
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

            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_aim, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.contentText.setText(dataList.get(position).getName());
        x.image().bind(viewHolder.image1, Utils.GetPhotoPath(dataList.get(position).getImg()), imageOptions);
        ViewGroup.LayoutParams layoutParams1 = viewHolder.image1.getLayoutParams();
        layoutParams1.height = AddFriendSearchActivity.width / 2;
        layoutParams1.width = AddFriendSearchActivity.width;
        viewHolder.image1.setLayoutParams(layoutParams1);

        return convertView;
    }

     class ViewHolder {
        @InjectView(R.id.content_text)
        TextView contentText;

        @InjectView(R.id.image_layout)
        LinearLayout imageLayout;
        @InjectView(R.id.image1)
        ImageView image1;

        ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }
}

