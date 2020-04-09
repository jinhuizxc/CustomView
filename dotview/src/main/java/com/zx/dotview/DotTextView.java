package com.zx.dotview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;


public class DotTextView extends AppCompatTextView implements IDot{

    private DotView mDotView;

    private boolean mIsFollowText;

    public DotTextView(Context context) {
        super(context);
    }

    public DotTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mDotView = new DotView(this, context, attrs);

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.DotTextView);
        mIsFollowText = array.getBoolean(R.styleable.DotTextView_dot_is_follow_text, false);
        array.recycle();

    }

    public DotTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int textWidth = (int) (getPaint().measureText(getText().toString()) + getPaddingLeft() + getPaddingRight());
        int width = mIsFollowText ? textWidth * 2 : w;
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
    public void setIsShow(boolean isShow) {
        mDotView.setIsShow(isShow);
    }

    @Override
    public void setColor(int color) {
        mDotView.setColor(color);
    }
}
