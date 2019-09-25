package com.example.customview.step1.customevaluator;

import android.animation.TypeEvaluator;

/**
 * Created by jinhui on 2018/1/5.
 * Email:1004260403@qq.com
 * 补充：IntEvaluator中方法：
 * public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
 * int startInt = startValue;
 * return (int)(startInt + fraction * (endValue - startInt));
 * }
 * <p>
 * 当前的值 = 100 + （400 - 100）* 显示进度  与上面返回值相同，计算方式一样的
 * 自定义Evalutor
 * <p>
 * 在实现TypeEvaluator，我们给它指定它的返回是Integer类型，
 * 这样我们就可以在ofInt()中使用这个Evaluator了。再说一遍原因：只有定义动画时的数值类型与Evalutor的返回值类型一样时，才能使用这个Evalutor；很显然ofInt()定义的数值类型是Integer而我们定义的MyEvaluator，它的返回值类型也是Integer；所以我们定义的MyEvaluator可以给ofInt（）来用。同理，如果我们把实现的TypeEvaluator填充为为Float类型，
 * 那么这个Evalutor也就只能给FloatEvalutor用了。
 * 我们在IntEvaluator的基础上修改了下，让它返回值时增加了200；
 * 所以当我们定义的区间是ofInt(0,400)时，它的实际返回值区间应该是(200,600)
 */


public class MyEvaluator implements TypeEvaluator<Integer> {

    // 我们在IntEvaluator的基础上修改了下，让它返回值时增加了200；
    // 所以当我们定义的区间是ofInt(0,400)时，它的实际返回值区间应该是(200,600)
    @Override
    public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
        int startInt = startValue;
        return (int) (200 + startInt + fraction * (endValue - startInt));
    }
}
