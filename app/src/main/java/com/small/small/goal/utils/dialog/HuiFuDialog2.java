package com.small.small.goal.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.small.small.goal.R;

/**
 * Created by JS on 2017-07-24.
 */

public class HuiFuDialog2 extends AlertDialog {
    private final Context context;
    private final String title;
    private final onTvOKClickListener onTvOKClickListener;

    //String title;

    public interface onTvOKClickListener {
        void onClick();
    }

    public HuiFuDialog2(Context context, String title, onTvOKClickListener onTvOKClickListener) {
        super(context);
        this.context = context;
        this.title = title;
        this.onTvOKClickListener = onTvOKClickListener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pass_ok);

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
        this.setCancelable(false);

        TextView tv_content = (TextView) window.findViewById(R.id.text_content);
        //    tv_content.setText(title);
        TextView tv_ok = (TextView) findViewById(R.id.tv_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                onTvOKClickListener.onClick();

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
