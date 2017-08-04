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

public class NoticeMF extends MarqueeFactory<TextView, Notice> {
    private LayoutInflater inflater;
    private int status;
    public NoticeMF(Context mContext, int status) {
        super(mContext);
        inflater = LayoutInflater.from(mContext);
        this.status=status;
    }

    @Override
    public TextView generateMarqueeItemView(Notice data) {
        TextView mView = (TextView) inflater.inflate(R.layout.notice_item, null);

        if (status==0){
            mView.setTextColor(mContext.getResources().getColor(R.color.white));
        }else if (status==1){
            mView.setTextColor(mContext.getResources().getColor(R.color.text_hui));
        }

        SpannableStringBuilder builder = new SpannableStringBuilder(data.getNoticeStr());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        builder.setSpan(redSpan, 4, 4 + data.getNameLength(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mView.setText(builder);

        return mView;
    }

}