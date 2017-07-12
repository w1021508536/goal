package com.small.small.goal.weight;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.gongwen.marqueen.MarqueeFactory;
import com.small.small.goal.R;
import com.small.small.goal.my.activity.ExtensionActivity;

public class NoticeMF extends MarqueeFactory<TextView, ExtensionActivity.notice> {
    private LayoutInflater inflater;

    public NoticeMF(Context mContext) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public TextView generateMarqueeItemView(ExtensionActivity.notice data) {
        TextView mView = (TextView) inflater.inflate(R.layout.notice_item, null);

        SpannableStringBuilder builder = new SpannableStringBuilder(data.getNoticeStr());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, 4, 4 + data.getNameLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mView.setText(builder);

        return mView;
    }

}