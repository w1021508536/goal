package com.small.small.goal.utils.dialog;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.small.small.goal.R;


/**
 * 公司：小目标
 * 创建者： 王金壮
 * 时间： 2017/7/1 10:12
 * 修改：
 * 描述：彩票的右上角的popuwindow
 **/
public class LotteryTopPopuwindow extends PopupWindow {

    private setOnclickListener listener;

    public LotteryTopPopuwindow(Context context) {
        super(context);
        initPop(context);
    }

    public interface setOnclickListener {
        void onGuizeClick();        //规则

        void onJiluClick();        //投注记录

        void onFollowClick();

        void onShareClick();

    }

    public void setListener(setOnclickListener listener) {

        this.listener = listener;
    }

    /**
     * 初始化popuwindow
     * create  wjz
     **/
    private void initPop(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.pop_day_play, null);
        setContentView(view);
        setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new BitmapDrawable());
        setOutsideTouchable(true);//不能在没有焦点的时候使用


        TextView tv_guize = (TextView) view.findViewById(R.id.tv_guize);
        TextView tv_jilu = (TextView) view.findViewById(R.id.tv_jilu);
        TextView tv_follow = (TextView) view.findViewById(R.id.tv_follow);
        TextView tv_share = (TextView) view.findViewById(R.id.tv_share);

        tv_guize.setOnClickListener(new Myclick(tv_guize));
        tv_jilu.setOnClickListener(new Myclick(tv_jilu));
        tv_follow.setOnClickListener(new Myclick(tv_follow));
        tv_share.setOnClickListener(new Myclick(tv_share));

    }

    class Myclick implements View.OnClickListener {

        private final View v;

        public Myclick(View v) {
            this.v = v;
        }

        @Override
        public void onClick(View v) {
            if (listener == null) return;
            switch (this.v.getId()) {
                case R.id.tv_guize:
                    listener.onGuizeClick();
                    break;
                case R.id.tv_jilu:
                    listener.onJiluClick();
                    break;
                case R.id.tv_follow:
                    listener.onFollowClick();
                    break;
                case R.id.tv_share:
                    listener.onShareClick();
                    break;
            }
        }
    }

}
