package com.pi.small.goal.search.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.pi.small.goal.R;
import com.pi.small.goal.utils.Utils;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class UserDetitalActivity extends AppCompatActivity {

    @InjectView(R.id.head_layout)
    LinearLayout headLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detital);
        ButterKnife.inject(this);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);
        }
    }


    private void setTranslucentStatus(boolean on) {
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
        headLayout.setPadding(0, Utils.getStatusBarHeight(this), 0, 0);
        ;
    }
}
