package com.pi.small.goal.my.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.pi.small.goal.R;


/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/5/28
 * 描述：
 * 修改：
 **/
public class LoadingDialog extends ProgressDialog {
    TextView tv;
    String content;
    Context context;

    public LoadingDialog(Context context, String content) {
        super(context, R.style.loading_dialog);
        this.content = content;
        this.context = context;
        this.setCanceledOnTouchOutside(false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);

        tv = (TextView) findViewById(R.id.text);
        ImageView img_loading = (ImageView) findViewById(R.id.img_loading);

        AnimationDrawable animationDrawable = (AnimationDrawable) img_loading.getDrawable();
        animationDrawable.start();

        tv.setText(content);
        this.setMessage(content);
//        this.setContentView(R.layout.dialog_loading);
//        ImageView img = (ImageView) findViewById(R.id.img_zhendonghua);
//        AnimationDrawable animationDrawable = (AnimationDrawable) img.getBackground();
//        animationDrawable.start();
    }
}
