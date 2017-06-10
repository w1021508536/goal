package com.pi.small.goal.my.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import cn.aigestudio.datepicker.cons.DPMode;
import cn.aigestudio.datepicker.utils.ScheduleUtil;
import cn.aigestudio.datepicker.views.DatePicker;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/8 16:35
 * 修改：
 * 描述：
 **/
public class MonthDialog extends AlertDialog {


    private Context context;
    private OnDialogOkListener listener;
    private String date;

    public interface OnDialogOkListener {
        void getSelectTime(String time);
    }

    public MonthDialog(Context context, OnDialogOkListener listener) {
        super(context);
        this.context = context;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_month);


        initDialog();


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


        TextView tv_cancel = (TextView) window.findViewById(R.id.tv_cancel_dialog);
        TextView tv_ok = (TextView) window.findViewById(R.id.tv_ok_dialog);
        DatePicker dp = (DatePicker) window.findViewById(R.id.dp_dialog);

        Map<Integer, String> map = new HashMap<>();
        for (int i = 1; i <= 31; i++) {
            if (i % 3 == 0) {
                map.put(i, ScheduleUtil.JIABAN);
            } else {
                map.put(i, "");
            }
        }
        dp.setCalendar(map);

        dp.setDate(2017, 6);
        dp.setMode(DPMode.SINGLE);

        dp.setOnDatePickedListener(new DatePicker.OnDatePickedListener() {
            @Override
            public void onDatePicked(String date) {
                MonthDialog.this.date = date;
                Utils.showToast(context, date);
            }
        });

        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listener != null) {
                    listener.getSelectTime(date + " 08:00:00");
                }
                dismiss();
            }
        });


    }


}
