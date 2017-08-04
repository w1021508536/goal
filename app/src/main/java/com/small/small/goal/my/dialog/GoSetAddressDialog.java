package com.small.small.goal.my.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.small.small.goal.R;
import com.small.small.goal.utils.dialog.DialogClickListener;


/**
 * 作者 王金壮
 * 说明
 * 创建时间 2016/7/1
 * 公司名称 xmb
 * 描述   去设置地址的dialog
 */
public class GoSetAddressDialog extends AlertDialog {
    private final Context context;
    private final String title;
    private DialogClickListener onTvOKClickListener;


    public GoSetAddressDialog(Context context, String title) {
        super(context);
        this.context = context;
        this.title = title;

    }

    public void setOnTvOKClickListener(DialogClickListener onTvOKClickListener) {
        this.onTvOKClickListener = onTvOKClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_go_setaddress);

        initDialog();


//        lianxi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(Intent.ACTION_DIAL);
//                intent.setData(Uri.parse("tel:" + phones));
//                context.startActivity(intent);
//            }
//        });
    }

    private void initDialog() {
        Window window = this.getWindow();
        //    window.setWindowAnimations(R.style.dialog_anim2);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager wm = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params.width = wm.getDefaultDisplay().getWidth();
        window.setAttributes(params);
  //      this.setCancelable(false);

        //    tv_content.setText(title);
        TextView tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onTvOKClickListener.onClick("");

            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:

                return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
