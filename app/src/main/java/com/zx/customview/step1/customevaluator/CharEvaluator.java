package com.zx.customview.step1.customevaluator;

import android.animation.TypeEvaluator;

/**
 * Created by jinhui on 2018/1/5.
 * Email:1004260403@qq.com
 *
 * 我们知道在ASCII码表中，每个字符都是有数字跟他一一对应的，字母A到字母Z之间的所有字母对应的数字区间为65到90；
 *而且在程序中，我们能通过数字强转成对应的字符。
 * 比如：
 *
 * 数字转字符:
 * char temp = (char)65;//得到的temp的值就是大写字母A
 * 字符转数字：
 * char temp = 'A';
 * int num = (int)temp;
 */

public class CharEvaluator implements TypeEvaluator<Character> {
    @Override
    public Character evaluate(float fraction, Character startValue, Character endValue) {
        int startInt  = (int)startValue;
        int endInt = (int)endValue;
        int curInt = (int)(startInt + fraction *(endInt - startInt));
        char result = (char)curInt;
        return result;
    }
}
