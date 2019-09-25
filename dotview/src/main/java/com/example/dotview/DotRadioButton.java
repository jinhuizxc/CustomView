package com.example.dotview;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatRadioButton;
import android.util.AttributeSet;

public class DotRadioButton extends AppCompatRadioButton implements IDot{

    private DotView mDotView;

    public DotRadioButton(Context context) {
        super(context);
    }

    public DotRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        mDotView = new DotView(this, context, attrs);
    }

    public DotRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Drawable[] drawables = getCompoundDrawables();
        int width = drawables[1].getIntrinsicWidth() + w;
        mDotView.onSizeChanged(width, h);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mDotView.draw(canvas);
    }

    @Override
    public void setTipsCount(int tipsCount) {
        mDotView.setTipsCount(tipsCount);
    }

    @Override
    public int getTipsCount() {
        return mDotView.getTipsCount();
    }

    @Override
    public void setIsShow(boolean isShowDot) {
        mDotView.setIsShow(isShowDot);
    }

    @Override
    public void setColor(int color) {
        mDotView.setColor(color);
    }

}
