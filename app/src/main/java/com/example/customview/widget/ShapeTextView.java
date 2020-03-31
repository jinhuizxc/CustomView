package com.example.customview.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.example.customview.R;

/**
 * Created by TinyHung@outlook.com
 * 2019/5/10
 * 自行指定背景圆角、颜色的BUTTON
 */

public class ShapeTextView extends AppCompatTextView implements View.OnTouchListener {

    //圆角、边框
    private int mRadius, mStroke;
    //背景颜色
    private int mBackGroundColor = Color.parseColor("#00000000")
            //背景按下颜色
            , mBackGroundSelectedColor = Color.parseColor("#00000000")
            //边框颜色
            , mStrokeColor = Color.parseColor("#00000000");

    public ShapeTextView(@NonNull Context context) {
        this(context, null);
    }

    public ShapeTextView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeTextView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.setOnTouchListener(this);
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ShapeTextView);
            mRadius = typedArray.getDimensionPixelSize(R.styleable.ShapeTextView_shapeRadius, 0);
            mStroke = typedArray.getDimensionPixelSize(R.styleable.ShapeTextView_shapeStrokeWidth, 0);
            mStrokeColor = typedArray.getColor(R.styleable.ShapeTextView_shapeStrokeColor,
                    ContextCompat.getColor(getContext(), android.R.color.transparent));
            mBackGroundColor = typedArray.getColor(R.styleable.ShapeTextView_shapeBackgroundColor,
                    ContextCompat.getColor(getContext(), R.color.colorAccent));
            mBackGroundSelectedColor = typedArray.getColor(R.styleable.ShapeTextView_shapeBackgroundSelectorColor,
                    ContextCompat.getColor(getContext(), R.color.colorPrimaryDark));
            typedArray.recycle();
        }
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setCornerRadius(mRadius);
        gradientDrawable.setStroke(mStroke, mStrokeColor);
        gradientDrawable.setColor(mBackGroundColor);
        this.setBackground(gradientDrawable);
        setClickable(true);
    }


    public void setRadius(int radius) {
        mRadius = radius;
        GradientDrawable gradientDrawable = (GradientDrawable) getBackground();
        if (null != gradientDrawable) {
            gradientDrawable.setCornerRadius(mRadius);
        }
    }

    public void setBackGroundColor(int color) {
        this.mBackGroundColor = color;
        GradientDrawable gradientDrawable = (GradientDrawable) getBackground();
        if (null != gradientDrawable) {
            gradientDrawable.setColor(mBackGroundColor);
        }
    }

    public void setBackGroundSelectedColor(int color) {
        this.mBackGroundSelectedColor = color;
    }

    public void setStroke(int stroke) {
        mStroke = stroke;
        GradientDrawable gradientDrawable = (GradientDrawable) getBackground();
        if (null != gradientDrawable) {
            gradientDrawable.setStroke(mStroke, mStrokeColor);
        }
    }

    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
        GradientDrawable gradientDrawable = (GradientDrawable) getBackground();
        if (null != gradientDrawable) {
            gradientDrawable.setStroke(mStroke, mStrokeColor);
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (event.getAction()) {
            //用户手指按下，使用按下Color
            case MotionEvent.ACTION_DOWN:
                GradientDrawable gradientDrawable = (GradientDrawable) getBackground();
                if (null != gradientDrawable) {
                    gradientDrawable.setColor(mBackGroundSelectedColor);
                }
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            //用户松手，使用默认背景Color
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                GradientDrawable background = (GradientDrawable) getBackground();
                if (null != background) {
                    background.setColor(mBackGroundColor);
                }
                break;
        }
        return super.onTouchEvent(event);
    }
}
