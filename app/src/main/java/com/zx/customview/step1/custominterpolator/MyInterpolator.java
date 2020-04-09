package com.zx.customview.step1.custominterpolator;

import android.animation.TimeInterpolator;

/**
 * Created by jinhui on 2018/1/5.
 * Email:1004260403@qq.com
 *
 * 自定义加速器的学习
 *
 * LinearInterpolator实现了Interpolator接口；
 * 而Interpolator接口则直接继承自TimeInterpolator，而且并没有添加任何其它的方法。
 * 这里是TimeInterpolator的代码，它里面只有一个函数float getInterpolation(float input);我们来讲讲这个函数是干什么的。
 参数input:input参数是一个float类型，它取值范围是0到1，表示当前动画的进度，取0时表示动画刚开始，取1时表示动画结束，取0.5时表示动画中间的位置，其它类推。
 返回值：表示当前实际想要显示的进度。取值可以超过1也可以小于0，超过1表示已经超过目标值，小于0表示小于开始位置。
 对于input参数，它表示的是当前动画的进度，匀速增加的。什么叫动画的进度，动画的进度就是动画在时间上的进度，与我们的任何设置无关，随着时间的增长，动画的进度自然的增加，从0到1；input参数相当于时间的概念，我们通过setDuration()指定了动画的时长，在这个时间范围内，动画进度肯定是一点点增加的；就相当于我们播放一首歌，这首歌的进度是从0到1是一样的。
 而返回值则表示动画的数值进度，它的对应的数值范围是我们通过ofInt(),ofFloat()来指定的，这个返回值就表示当前时间所对应的数值的进度。
 */

public class MyInterpolator implements TimeInterpolator {



    @Override
    public float getInterpolation(float input) {
        return 1- input;
    }
}
