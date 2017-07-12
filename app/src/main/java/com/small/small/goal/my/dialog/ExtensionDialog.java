package com.small.small.goal.my.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.small.small.goal.R;


/**
 * 作者 王金壮
 * 说明
 * 创建时间 2016/7/1
 * 公司名称 xmb
 * 描述
 */
public class ExtensionDialog extends AlertDialog {
    private final Context context;
    private onClickGoListener listener;

    //String title;


    public ExtensionDialog(Context context) {
        super(context);
        this.context = context;
    }

    public interface onClickGoListener {
        void onclick();
    }

    public void setOnClickGoListener(onClickGoListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_hint);

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

        TextView tv_money = (TextView) window.findViewById(R.id.tv_haveMoney);
        final TextView tv_go = (TextView) window.findViewById(R.id.tv_go);

        String s = tv_money.getText().toString();
        int i = s.indexOf("6");
        SpannableStringBuilder builder = new SpannableStringBuilder(tv_money.getText().toString());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, i, i + 3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv_money.setText(builder);

        tv_go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onclick();
                }
            }
        });
    }

}
