package com.rorbin.customview.qqmenu;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

public class QQMenu extends FrameLayout {

    private View childView1, childView2;
    private float childView1X, childView1Y, childView2X, childView2Y;
    private float centerX, centerY;

    private boolean hasClick = false;
    private int image_unselect, image_select;
    private int image_unselect_face, image_select_face;

    private OnMenuClickListener onMenuClickListener;

    public QQMenu(Context context) {
        super(context);
    }

    public QQMenu(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public QQMenu(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView() {
        childView1 = new ImageView(getContext());
        childView2 = new ImageView(getContext());
        addView(childView1);
        addView(childView2);
    }


    public void setHasClick(boolean hasClick) {
        this.hasClick = hasClick;
        refreshDrawable();
    }

    public boolean isHasClick() {
        return hasClick;
    }

    public void setOnMenuClickListener(OnMenuClickListener onMenuClickListener) {
        this.onMenuClickListener = onMenuClickListener;
    }

    public void setImages(@DrawableRes int normal, @DrawableRes int select) {
        this.image_unselect = normal;
        this.image_select = select;
    }

    public void setImages(@DrawableRes int normal, @DrawableRes int select, @DrawableRes int unselect_face, @DrawableRes int select_face) {
        this.image_unselect = normal;
        this.image_select = select;
        this.image_unselect_face = unselect_face;
        this.image_select_face = select_face;
        refreshDrawable();
    }

    private void refreshDrawable() {
        if (hasClick){
            if (image_select != 0)
                childView1.setBackgroundResource(image_select);
            if (image_select_face != 0)
                childView2.setBackgroundResource(image_select_face);
        }else {
            if (image_unselect != 0)
                childView1.setBackgroundResource(image_unselect);
            if (image_unselect_face != 0)
                childView2.setBackgroundResource(image_unselect_face);
        }
    }

    @Override
    public void onWindowFocusChanged(boolean hasWindowFocus) {
        super.onWindowFocusChanged(hasWindowFocus);
        centerX = getWidth() / 5;
        centerY = getHeight() / 5;
    }

    /**
     *  java.lang.NullPointerException: Attempt to invoke virtual method 'float android.view.View.getX()' on a null object reference
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        childView1 = getChildAt(0);
        childView2 = getChildAt(1);
        childView1X = childView1.getX();
        childView1Y = childView1.getY();
        childView2X = childView2.getX();
        childView2Y = childView2.getY();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int action = event.getAction();
        switch (action){
            case MotionEvent.ACTION_MOVE:
                move(x, y);
                return true;
            case MotionEvent.ACTION_UP:
                restorePosition();
                if (isContain(this, event.getRawX(), event.getRawY())) {
                    setHasClick(!hasClick);
                    if (onMenuClickListener != null) {
                        onMenuClickListener.onOnItemClick(this);
                    }
                }
                return true;
        }
//        return super.onTouchEvent(event);
        return true;
    }

    private boolean isContain(View view, float x, float y) {
        int[] point = new int[2];
        view.getLocationOnScreen(point);
        return x >= point[0] && x <= (point[0] + view.getWidth()) && y >= point[1] && y <= (point[1] + view.getHeight());
    }


    private void move(float x, float y) {
        if (y + centerY < -12 * centerY) {
            y = -12 * centerY - centerY;
        } else if (y - centerY > 12 * centerY) {
            y = 12 * centerY + centerY;
        }
        if (x + centerX < -12 * centerX) {
            x = -12 * centerX - centerX;
        } else if (x - centerX > 12 * centerX) {
            x = 12 * centerX + centerX;
        }
        childView1.setX(childView1X + (x - centerX) / 23);
        childView1.setY(childView1Y + (y - centerY) / 23);
        if (childView2 != null) {
            childView2.setX(childView2X + (x - centerX) / 10);
            childView2.setY(childView2Y + (y - centerY) / 10);
        }
    }

    private void restorePosition() {
        childView1.setX(childView1X);
        childView1.setY(childView1Y);
        childView2.setX(childView2X);
        childView2.setY(childView2Y);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public interface OnMenuClickListener {
        void onOnItemClick(View view);
    }

}
