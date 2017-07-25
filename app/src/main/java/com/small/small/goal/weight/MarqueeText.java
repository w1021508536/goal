package com.small.small.goal.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by JS on 2017-06-30.
 */

public class MarqueeText extends android.support.v7.widget.AppCompatTextView {
    public MarqueeText(Context context) {
        super(context);
    }

    public MarqueeText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MarqueeText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    //返回textview是否处在选中的状态
    //而只有选中的textview才能够实现跑马灯效果
    @Override
    public boolean isFocused() {
        return true;
    }
}
