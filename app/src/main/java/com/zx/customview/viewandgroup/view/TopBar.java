package com.zx.customview.viewandgroup.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zx.customview.R;

/**
 * 自定义组合控件
 *
 * custom:mCenterTitleColor="@android:color/white"
 *             custom:mCenterTitleSize="5sp"
 *             custom:mLeftBackground="@mipmap/back_icon"
 *             custom:mRightTitle="编辑"
 *             custom:mRightTitleColor="@android:color/white"
 *             custom:mRightTitleSize="5sp"
 *
 */
public class TopBar extends RelativeLayout {

    private String mCenterTitle;
    private float mCenterTitleSize;
    private int mCenterColor;
    // 左控件的属性值，即我们在atts.xml文件中定义的属性
    private Drawable mLeftBackGround;

    //右边TextView的属性值
    private String mRightText;
    private int mRightTextColor;
    private float mRightTextSize;

    //控件
    private ImageView iv_leftImage;
    private TextView tv_centerText;
    private TextView tv_rightText;

    // 布局属性，用来控制组件元素在ViewGroup中的位置
    private LayoutParams mLeftParams, mCenterTitleParams, mRightParams;


    public TopBar(Context context) {
        this(context, null);
    }

    public TopBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TopBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        inits(context, attrs);
    }

    private void inits(Context context, AttributeSet attrs) {
        //设置背景色
        setBackgroundColor(0xFF190D31);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TopBar);
        //中间title
        mCenterTitle = typedArray.getString(R.styleable.TopBar_mCenterTitle);
        mCenterColor = typedArray.getColor(R.styleable.TopBar_mCenterTitleColor, 0);
        mCenterTitleSize = typedArray.getDimension(R.styleable.TopBar_mCenterTitleSize, 0);
        //左侧图片
        mLeftBackGround = typedArray.getDrawable(R.styleable.TopBar_mLeftBackground);
        //右边Title
        mRightText = typedArray.getString(R.styleable.TopBar_mRightTitle);
        mRightTextColor = typedArray.getColor(R.styleable.TopBar_mRightTitleColor, 0);
        mRightTextSize = typedArray.getDimension(R.styleable.TopBar_mRightTitleSize, 0);
        // 获取完TypedArray的值后，一般要调用
        // recycle()方法来避免重新创建的时候的错误
        typedArray.recycle();

        // 动态加载布局
        iv_leftImage = new ImageView(context);
        tv_centerText = new TextView(context);
        tv_rightText = new TextView(context);
        // 为创建的组件元素赋值
        iv_leftImage.setBackground(mLeftBackGround);

        tv_centerText.setText(mCenterTitle);
        tv_centerText.setTextSize(mCenterTitleSize);
        tv_centerText.setTextColor(mCenterColor);

        tv_rightText.setText(mRightText);
        tv_rightText.setTextSize(mRightTextSize);
        tv_rightText.setTextColor(mRightTextColor);

        //设置组件的位置
        mLeftParams = new LayoutParams(60, 60);
        // 设置左居中
        mLeftParams.addRule(ALIGN_PARENT_LEFT, TRUE);
        mLeftParams.addRule(CENTER_VERTICAL, TRUE);
        addView(iv_leftImage, mLeftParams);
        mCenterTitleParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mCenterTitleParams.addRule(CENTER_IN_PARENT, TRUE);
        addView(tv_centerText, mCenterTitleParams);

        mRightParams = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        mRightParams.addRule(ALIGN_PARENT_RIGHT, TRUE);
        mRightParams.addRule(CENTER_VERTICAL, TRUE);
        // 设置右边距30
        mRightParams.setMargins(0, 0, 30, 0);
        addView(tv_rightText, mRightParams);

        //从xml布局中获取

        iv_leftImage.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topBarClickListener != null){
                    topBarClickListener.leftClick();
                }
            }
        });

        tv_rightText.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (topBarClickListener != null){
                    topBarClickListener.rightClick();
                }
            }
        });



    }

    // 设置点击事件
    public interface TopBarClickListener{

        void leftClick();

        void rightClick();
    }

    private TopBarClickListener topBarClickListener;

    public void setTopBarClickListener(TopBarClickListener topBarClickListener) {
        this.topBarClickListener = topBarClickListener;
    }
}
