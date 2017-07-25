package com.small.small.goal.utils.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;


import com.small.small.goal.R;
import com.small.small.goal.weight.PickerView;

import java.util.ArrayList;
import java.util.List;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/4 13:30
 * 修改：
 * 描述：
 **/
public class AddgoldDialog extends AlertDialog {

    private final Context context;
    private DialogClickListener listener;
    String select = "600";

    public AddgoldDialog(Context context) {
        super(context);
        this.context = context;
    }

    public void setOnListener(DialogClickListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_add_gold);
        initDialog(context);
    }

    private void initDialog(Context context) {
        Window window = this.getWindow();
        //    window.setWindowAnimations(R.style.dialog_anim2);
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        WindowManager wm = (WindowManager) context.getApplicationContext()
                .getSystemService(Context.WINDOW_SERVICE);
        params.width = wm.getDefaultDisplay().getWidth();
        window.setAttributes(params);

        final PickerView pv = (PickerView) findViewById(R.id.pv);
        TextView tv_ok = (TextView) findViewById(R.id.tv_ok);
        List<String> goldData = new ArrayList<>();
        for (int i = 100; i <= 1000; i += 100) {
            goldData.add(i + "");
        }
        pv.setData(goldData);
        pv.setOnSelectListener(new PickerView.onSelectListener() {
            @Override
            public void onSelect(View view, String text) {
                select = text;
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (listener != null)
                    listener.onClick(select);
            }
        });
    }
}
