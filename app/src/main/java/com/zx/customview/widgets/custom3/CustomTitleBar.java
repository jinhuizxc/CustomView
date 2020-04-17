package com.zx.customview.widgets.custom3;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.provider.CalendarContract;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.customview.R;

/**
 * 自定义组合控件
 */
public class CustomTitleBar extends RelativeLayout {

    private Button titleBarLeftBtn;
    private Button titleBarRightBtn;
    private TextView titleBarTitle;

    public CustomTitleBar(Context context) {
        super(context);
    }

    public CustomTitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.custom_title_bar, this,false);
        titleBarLeftBtn = (Button) findViewById(R.id.title_bar_left);
        titleBarRightBtn = (Button) findViewById(R.id.title_bar_right);
        titleBarTitle = (TextView) findViewById(R.id.title_bar_title);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.CustomTitleBar);

        // 获取属性
        if (typedArray != null){
            //处理titleBar背景色
            int titleBarBackgroundColor = typedArray.getResourceId(R.styleable.CustomTitleBar_title_background_color, Color.GREEN);
            setBackgroundResource(titleBarBackgroundColor);
            //先处理左边按钮
            //获取是否要显示左边按钮
            boolean leftButtonVisible = typedArray.getBoolean(R.styleable.CustomTitleBar_left_button_visible, true);
            if (leftButtonVisible){
                titleBarLeftBtn.setVisibility(VISIBLE);
            }else {
                titleBarLeftBtn.setVisibility(INVISIBLE);
            }

            //设置左边按钮的文字
            String leftButtonText = typedArray.getString(R.styleable.CustomTitleBar_left_button_text);
            if (!TextUtils.isEmpty(leftButtonText)) {
                titleBarLeftBtn.setText(leftButtonText);
                //设置左边按钮文字颜色
                int leftButtonTextColor = typedArray.getColor(R.styleable.CustomTitleBar_left_button_text_color, Color.WHITE);
                titleBarLeftBtn.setTextColor(leftButtonTextColor);
            }

            // 设置左边图片icon 这里是二选一 要么只能是文字 要么只能是图片
            int leftButtonDrawable = typedArray.getResourceId(R.styleable.CustomTitleBar_left_button_drawable, R.drawable.icon_back);
            if (leftButtonDrawable != -1){
                //设置到哪个控件的位置
                titleBarLeftBtn.setCompoundDrawablesWithIntrinsicBounds(leftButtonDrawable, 0, 0, 0);
            }

            //处理标题
            //先获取标题是否要显示图片icon
            int titleTextDrawable = typedArray.getResourceId(R.styleable.CustomTitleBar_title_text_drawable, -1);
            if (titleTextDrawable != -1) {
                titleBarTitle.setBackgroundResource(titleTextDrawable);
            } else {
                //如果不是图片标题 则获取文字标题
                String titleText = typedArray.getString(R.styleable.CustomTitleBar_title_text1);
                if (!TextUtils.isEmpty(titleText)) {
                    titleBarTitle.setText(titleText);
                }

                //获取标题显示颜色
                int titleTextColor = typedArray.getColor(R.styleable.CustomTitleBar_title_text_color, Color.WHITE);
                titleBarTitle.setTextColor(titleTextColor);

                //先处理右边按钮
                //获取是否要显示右边按钮
                boolean rightButtonVisible = typedArray.getBoolean(R.styleable.CustomTitleBar_right_button_visible, true);
                if (rightButtonVisible) {
                    titleBarRightBtn.setVisibility(View.VISIBLE);
                } else {
                    titleBarRightBtn.setVisibility(View.INVISIBLE);
                }

                //设置右边按钮的文字
                String rightButtonText = typedArray.getString(R.styleable.CustomTitleBar_right_button_text);
                if (!TextUtils.isEmpty(rightButtonText)) {
                    titleBarRightBtn.setText(rightButtonText);
                    // 设置右边按钮文字颜色
                    int rightButtonTextColor = typedArray.getColor(R.styleable.CustomTitleBar_right_button_text_color, Color.WHITE);
                    titleBarRightBtn.setTextColor(rightButtonTextColor);
                }
                //设置右边图片icon 这里是二选一 要么只能是文字 要么只能是图片
                int rightButtonDrawable = typedArray.getResourceId(R.styleable.CustomTitleBar_right_button_drawable, -1);


            }
        }

        typedArray.recycle();
    }
}
