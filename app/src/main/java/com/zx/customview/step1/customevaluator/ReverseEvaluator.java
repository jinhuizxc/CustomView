package com.zx.customview.step1.customevaluator;

import android.animation.TypeEvaluator;

/**
 * Created by jinhui on 2018/1/5.
 * Email:1004260403@qq.com
 *
 * 在加速器中，我们可以通过自定义加速器的返回的数值进度来改变返回数值的位置。比如上面我们实现的倒序动画
 * 在Evaluator中，我们又可以通过改变进度值所对应的具体数字来改变数值的位置。
 * 所以，结论来了：
 * 我们可以通过重写加速器改变数值进度来改变数值位置，也可以通过改变Evaluator中进度所对应的数值来改变数值位置。
 *
 * 实现倒序输出实例
 *
 */

public class ReverseEvaluator implements TypeEvaluator<Integer> {
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (endValue - fraction * (endValue - startInt));
    }
}