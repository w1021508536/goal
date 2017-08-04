package com.small.small.goal.weight;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.small.small.goal.R;


/**
 * 自定义switch
 *
 * @author fancyy
 */
public class SwitchView extends LinearLayout {
    private ImageView maskImage;              // 开关遮盖图片
    private boolean open;                     // 开关当前状态
    private boolean isAninFinish = true;      // 动画是否结束
    private float x;                          // 记录ACTION_DOWN时候的横坐标
    private boolean isChangedByTouch = false; // 是否在一次事件中已经切换过状态
    private OnSwitchChangeListener switchChangeListener; // 监控开关状态

    public interface OnSwitchChangeListener {
        void onSwitchChanged(boolean open);
    }

    public SwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SwitchView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setBackgroundResource(R.mipmap.btn_off);
        maskImage = new ImageView(getContext());
        maskImage.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        maskImage.setImageResource(R.mipmap.btn_round);
        addView(maskImage);
    }

    public boolean getSwitchStatus() {
        return open;
    }

    public void setSwitchStatus(boolean isOpen) {
        this.open = isOpen;
        if (isOpen) {
            setGravity(Gravity.RIGHT);
            setBackgroundResource(R.mipmap.btn_on);
        } else {
            setGravity(Gravity.LEFT);
            setBackgroundResource(R.mipmap.btn_off);
        }
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    setBackgroundResource(R.mipmap.btn_off);
                    break;
                case 2:
                    setBackgroundResource(R.mipmap.btn_on);
                    break;
            }
            return false;
        }
    });


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                x = event.getX();
                break;
            }
            case MotionEvent.ACTION_MOVE: {
                if (event.getX() - x > 5 && !open) { // 向右
                    changeStatus();
                } else if (event.getX() - x < -5 && open) { // 向左
                    changeStatus();
                }
                break;
            }
            case MotionEvent.ACTION_UP: {
                if (Math.abs(event.getX() - x) <= 5) {
                    changeStatus();
                }
                isChangedByTouch = false;
                break;
            }
            case MotionEvent.ACTION_CANCEL: {
                isChangedByTouch = false;
                break;
            }
        }
        return true;
    }

    private void changeStatus() {
        if (isAninFinish && !isChangedByTouch) {
            isChangedByTouch = true;
            open = !open;
            isAninFinish = false;
            if (switchChangeListener != null) {
                switchChangeListener.onSwitchChanged(open);
            }
            changeOpenStatusWithAnim(open);
        }
    }

    private void changeOpenStatusWithAnim(boolean open) {
        if (open) {
            // 左到右
            Animation leftToRight = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                    Animation.ABSOLUTE, getWidth() - maskImage.getWidth(),
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0);
            leftToRight.setDuration(300);
            leftToRight.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    maskImage.clearAnimation();
                    setGravity(Gravity.RIGHT);
                    isAninFinish = true;

                    handler.sendEmptyMessageAtTime(2,200);
                }
            });
            maskImage.startAnimation(leftToRight);


        } else {
            // 右到左
            Animation rightToLeft = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0,
                    Animation.ABSOLUTE, maskImage.getWidth() - getWidth(),
                    Animation.RELATIVE_TO_SELF, 0,
                    Animation.RELATIVE_TO_SELF, 0);
            rightToLeft.setDuration(300);
            rightToLeft.setAnimationListener(new AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                }

                @Override
                public void onAnimationRepeat(Animation animation) {
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    maskImage.clearAnimation();
                    setGravity(Gravity.LEFT);
                    isAninFinish = true;
                    handler.sendEmptyMessageAtTime(1,200);
                }
            });
            maskImage.startAnimation(rightToLeft);


        }
    }

    public OnSwitchChangeListener getSwitchChangeListener() {
        return switchChangeListener;
    }

    public void setOnSwitchChangeListener(OnSwitchChangeListener switchChangeListener) {
        this.switchChangeListener = switchChangeListener;
    }

}
