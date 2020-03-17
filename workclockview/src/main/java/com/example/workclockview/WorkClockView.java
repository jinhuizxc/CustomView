package com.example.workclockview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 文字时钟：
 * 分析下涉及到的元素及样式表现
 *     「圆中信息」圆中心的数字时间 + 数字日期 + 文字星期几，始终为白色
 *     「时圈」一圈文字小时，一点、二点.. 十二点，当前点数为白色，其它为白色 + 透明度，如图中十点就是白色。
 *     「分圈」一圈文字分钟，一分、二分.. 五十九分，六十分显示为空，同理，当前分钟为白色，其它白色 + 透明度。
 *     「秒圈」一圈文字秒，一秒、二秒.. 五十九秒，六十秒显示为空，也是同理。
 *
 *     然后分析下动画效果：
 *     每秒钟「秒圈」走一下，这一下的旋转角度为 360°/60=6°，并且走这一下的时候有个线性旋转过去的动画效果。
 *     每分钟「分圈」走一下，旋转角度和动画效果跟「秒圈」相同。
 *     每小时「时圈」走一下，旋转角度为 360°/12=30°，动画效果同上。
 *
 */
public class WorkClockView extends View {

    public WorkClockView(Context context) {
        this(context, null);
    }

    public WorkClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WorkClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {

    }
}
