package com.zx.customview.widget.loadingview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.RelativeLayout;

/**
 * loadingview
 *
 * 对于RelativeLayout容器
 */
public class LoadingView extends RelativeLayout {

    private CircleView mRightView;
    private CircleView mCenterView;
    private CircleView mLeftView;
    // 动画时间
    private long animator_duration = 500;


    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(context);
    }

    private void init(Context context){
        mLeftView = getCircleView(context);
        mRightView = getCircleView(context);
        mCenterView = getCircleView(context);

        mLeftView.exChangeColor(Color.RED);
        mCenterView.exChangeColor(Color.BLUE);
        mRightView.exChangeColor(Color.GREEN);

       addView(mLeftView);
       addView(mRightView);
       addView(mCenterView);

       post(new Runnable() {
           @Override
           public void run() {
               expandAnimation();
           }
       });
    }

    /**
     * 往外跑的动画
     */
    private void expandAnimation() {
        //左边的View往左走
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", 0, -dp2px(20));
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", 0, dp2px(20));

        AnimatorSet set = new AnimatorSet();
        set.playTogether(leftTranslationAnimator, rightTranslationAnimator);
        set.setDuration(animator_duration);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                inAnimation();
            }
        });
        set.start();
    }

    /**
     * 往里跑的动画
     */
    private void inAnimation() {
        ObjectAnimator leftTranslationAnimator = ObjectAnimator.ofFloat(mLeftView, "translationX", -dp2px(20), 0);
        ObjectAnimator rightTranslationAnimator = ObjectAnimator.ofFloat(mRightView, "translationX", dp2px(20), 0);

        AnimatorSet set = new AnimatorSet();
        set.playTogether(leftTranslationAnimator, rightTranslationAnimator);
        set.setDuration(animator_duration);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                //往里动画执行完毕.改变颜色.
                //改变颜色规则,左边的颜色给中间,中间给右边,右边给左边
                int leftColor = mLeftView.getColor();
                int centerColor = mCenterView.getColor();
                int rightColor = mRightView.getColor();

                mLeftView.exChangeColor(rightColor);
                mCenterView.exChangeColor(leftColor);
                mRightView.exChangeColor(centerColor);

                expandAnimation();
            }
        });
        set.start();

    }

    /**
     * 获取circleView
     *
     * @param context
     * @return
     */
    private CircleView getCircleView(Context context) {
        CircleView circleView = new CircleView(context);
        // 将circleview放在容器里
        LayoutParams params = new LayoutParams(dp2px(10), dp2px(10));
        params.addRule(CENTER_IN_PARENT);
        circleView.setLayoutParams(params);
        return circleView;
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
