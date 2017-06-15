package com.pi.small.goal.my.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pi.small.goal.R;


/**
 * 作者 王金壮
 * 说明
 * 创建时间 2016/7/1
 * 公司名称 百迅科技
 * 描述
 */
public class HuiFuDialog extends AlertDialog {
    private final Context context;
    private final String title;

    //String title;


    public HuiFuDialog(Context context, String title) {
        super(context);
        this.context = context;
        this.title = title;
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

        TextView tv_content = (TextView) window.findViewById(R.id.text_content);
        tv_content.setText(title);
    }

}
