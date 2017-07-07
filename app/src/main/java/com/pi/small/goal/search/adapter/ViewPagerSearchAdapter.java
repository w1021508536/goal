package com.pi.small.goal.search.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.pi.small.goal.R;
import com.pi.small.goal.search.activity.AdDetailsActivity;
import com.pi.small.goal.utils.Url;
import com.pi.small.goal.utils.Utils;

import org.xutils.common.Callback;
import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by JS on 2017-06-14.
 */

public class ViewPagerSearchAdapter extends PagerAdapter {
    private List<Map<String, String>> adList;
    private Context context;
    List<View> mViewList = new ArrayList<View>();

    private ImageOptions imageOptions = new ImageOptions.Builder()
            .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
            .setFailureDrawableId(R.mipmap.banner)
            .setLoadingDrawableId(R.mipmap.banner)
            .setPlaceholderScaleType(ImageView.ScaleType.CENTER_CROP)
            .build();

    public ViewPagerSearchAdapter(Context context, List<Map<String, String>> adList) {
        this.context = context;
        this.adList = adList;
    }


    @Override
    public int getCount() {
        return adList.size();
    }

    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        return arg0 == arg1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
        mViewList.add(view);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View itemView = null;
        ViewHolder viewHolder = null;

        if (mViewList.isEmpty()) {
            itemView = LayoutInflater.from(context).inflate(
                    R.layout.item_view_pager_hot, null);
            viewHolder= new ViewHolder();
            viewHolder.banner_image = (ImageView) itemView.findViewById(R.id.banner_image);
            itemView.setTag(viewHolder);
        } else {
            itemView = mViewList.remove(0);
            viewHolder = (ViewHolder) itemView.getTag();
        }
//        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100);
//        itemView.setLayoutParams(params);
        if (!adList.get(position).get("img").equals("")) {
            x.image().bind(viewHolder.banner_image, Utils.GetPhotoPath(adList.get(position).get("img")) + Url.SMALL_PHOTO_URL_600, imageOptions, new Callback.CommonCallback<Drawable>() {
                @Override
                public void onSuccess(Drawable result) {
                    result.setCallback(null);
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
        } else {
            viewHolder.banner_image.setImageDrawable(context.getResources().getDrawable(R.mipmap.banner));
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(context, AdDetailsActivity.class);
                intent.putExtra("url",adList.get(position).get("url"));
                intent.putExtra("title",adList.get(position).get("title"));
                context.startActivity(intent);
            }
        });
        container.addView(itemView);

        return itemView;
    }

    private class ViewHolder {
        ImageView banner_image;
    }


}
