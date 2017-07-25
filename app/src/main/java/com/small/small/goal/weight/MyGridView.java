package com.small.small.goal.weight;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/6/26 16:02
 * 修改：
 * 描述：
 **/
public class MyGridView extends GridView {

    public boolean hasScrollBar = true;

    public MyGridView(Context context) {
        this(context, null);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = heightMeasureSpec;
        if (hasScrollBar) {
            expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                    MeasureSpec.AT_MOST);
            super.onMeasure(widthMeasureSpec, expandSpec);// 注意这里,这里的意思是直接测量出GridView的高度
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }
}