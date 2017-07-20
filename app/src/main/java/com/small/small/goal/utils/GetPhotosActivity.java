package com.small.small.goal.utils;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.small.small.goal.R;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class GetPhotosActivity extends BaseActivity {

    @InjectView(R.id.left_image)
    ImageView leftImage;
    @InjectView(R.id.grid_view)
    GridView gridView;
    @InjectView(R.id.current_layout)
    LinearLayout currentLayout;

    private int width;

    private List<String> dataList;
    private PhotosAdapter photosAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_get_photos);
        ButterKnife.inject(this);
        super.onCreate(savedInstanceState);

        width = (getWindowManager().getDefaultDisplay().getWidth() - 130);
        dataList = new ArrayList<String>();
        photosAdapter = new PhotosAdapter(this);
        init();
    }

    private void init() {
//        http://img.smallaim.cn/aim/aim_1.jpg
        for (int i = 1; i < 35; i++) {
            dataList.add("aim/aim_" + i + ".jpg");
        }
        gridView.setAdapter(photosAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("path", dataList.get(position));
                setResult(Code.RESULT_OWM_CODE, intent);
                finish();
            }
        });
    }

    @OnClick(R.id.left_image)
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.left_image:
                Intent intent = new Intent();
                intent.putExtra("path", "");
                setResult(Code.RESULT_OWM_CODE, intent);
                finish();
                break;
        }
    }

    private class PhotosAdapter extends BaseAdapter {

        private Context context;
        private ImageOptions imageOptions = new ImageOptions.Builder()
                .setImageScaleType(ImageView.ScaleType.CENTER_CROP)
                .setLoadingDrawableId(R.mipmap.background_fail)
                .setFailureDrawableId(R.mipmap.background_fail)
                .build();

        private PhotosAdapter(Context context) {
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
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(context).inflate(R.layout.item_grid_photo, parent, false);
                viewHolder.image = (ImageView) convertView.findViewById(R.id.image);
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            ViewGroup.LayoutParams layoutParams1 = viewHolder.image.getLayoutParams();
            layoutParams1.height = width / 3;
            layoutParams1.width = width / 3;
            viewHolder.image.setLayoutParams(layoutParams1);

            x.image().bind(viewHolder.image, Utils.GetPhotoPath(dataList.get(position)) + "?x-oss-process=image/resize,m_lfit,h_300,w_300", imageOptions);

            return convertView;
        }


        private class ViewHolder {
            ImageView image;
        }
    }
}
