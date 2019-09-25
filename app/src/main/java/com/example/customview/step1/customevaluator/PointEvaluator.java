package com.example.customview.step1.customevaluator;

import android.animation.TypeEvaluator;

import com.example.customview.step1.model.Point;

/**
 * Created by jinhui on 2018/1/5.
 * Email:1004260403@qq.com
 */

public class PointEvaluator implements TypeEvaluator<Point> {
    @Override
    public Point evaluate(float fraction, Point startValue, Point endValue) {
        int start = startValue.getRadius();
        int end  = endValue.getRadius();
        int curValue = (int)(start + fraction * (end - start));
        return new Point(curValue);
    }
}