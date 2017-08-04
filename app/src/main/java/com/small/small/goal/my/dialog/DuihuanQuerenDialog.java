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
 * 描述  奖品兑换确认的dialog
 */
public class DuihuanQuerenDialog extends AlertDialog {
    private final Context context;
    private  String title;
    private TextView tv_money;
    private DialogClickListener listener;

    public DuihuanQuerenDialog(Context context, String title) {
        super(context);
        this.context = context;
        this.title = title;
    }

    public void setMoney(String title) {
        if (tv_money != null) {
            tv_money.setText(title);
        }
        this.title=title;

    }


    public void setOnClick(DialogClickListener listener) {
        this.listener = listener;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_duihuan_queren);

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

        TextView btn_ok = (TextView) window.findViewById(R.id.tv_ok);
        TextView btn_cancel = (TextView) window.findViewById(R.id.tv_cancel);
        tv_money = (TextView) window.findViewById(R.id.tv_money);
        tv_money.setText(title);
        window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null) {
                    listener.onClick("");
                }
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
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
