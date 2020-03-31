package com.example.customview.step1.baseanim;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import com.example.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 文章地址：
 * http://blog.csdn.net/harvic880925/article/details/39996643
 * <p>
 * <p>
 * 对动画的知识快忘了，弥补下！
 * Android的animation由四种类型组成：alpha、scale、translate、rotate，
 * <p>
 * 对应android官方文档地址：《Animation Resources》
 * <p>
 * alpha 渐变透明度动画效果
 * scale 渐变尺寸伸缩动画效果
 * translate 画面转换位置移动动画效果
 * rotate 画面转移旋转动画效果
 * <p>
 * scale标签——调节尺寸
 * scale标签是缩放动画，可以实现动态调控件尺寸的效果，有下面几个属性：
 * android:fromXScale    起始的X方向上相对自身的缩放比例，浮点值，比如1.0代表自身无变化，0.5代表起始时缩小一倍，2.0代表放大一倍；
 * android:toXScale        结尾的X方向上相对自身的缩放比例，浮点值；
 * android:fromYScale    起始的Y方向上相对自身的缩放比例，浮点值，
 * android:toYScale        结尾的Y方向上相对自身的缩放比例，浮点值；
 * android:pivotX            缩放起点X轴坐标，可以是数值、百分数、百分数p 三种样式，比如 50、50%、50%p，当为数值时，表示在当前View的左上角，即原点处加上50px，做为起始缩放点；如果是50%，表示在当前控件的左上角加上自己宽度的50%做为起始点；如果是50%p，那么就是表示在当前的左上角加上父控件宽度的50%做为起始点x轴坐标。（具体意义，后面会举例演示）
 * android:pivotY           缩放起点Y轴坐标，取值及意义跟android:pivotX一样。
 * <p>
 * <p>
 * （1）、pivotX取值数值时（50）
 * 这个控件，宽度和高度都是从0放大到1.4倍，起始点坐标在控件左上角（坐标原点），向x轴正方向和y轴正方向都加上50像素；
 * 根据pivotX,pivotY的意义，控件的左上角即为控件的坐标原点，这里的起始点是在控件的原点的基础上向X轴和Y轴各加上50px，做为起始点，
 * <p>
 * （2）、pivotX取值百分数时（50%）
 * 下面再看看当pivotX、pivotY取百分数的时候，起始点又在哪里？
 * 上面我们讲了，pivotX的值，当取50%时，表示在原点坐标的基础上加上的自己宽度的50%
 * <p>
 * （3）、pivotX取值50%p时
 * 前面说过，当取值在百分数后面加上一个字母p，就表示，取值的基数是父控件，即在原点的基础上增加的值是父标签的百分值。
 * <p>
 * 2、从Animation类继承的属性
 * Animation类是所有动画（scale、alpha、translate、rotate）的基类，这里以scale标签为例，
 * 讲解一下，Animation类所具有的属性及意义。关于Animation类的官方文档位置为：《Animation》
 * <p>
 * android:duration        动画持续时间，以毫秒为单位
 * android:fillAfter          如果设置为true，控件动画结束时，将保持动画最后时的状态
 * android:fillBefore       如果设置为true,控件动画结束时，还原到开始动画前的状态
 * android:fillEnabled    与android:fillBefore 效果相同，都是在动画结束时，将控件还原到初始化状态
 * android:repeatCount 重复次数
 * android:repeatMode	重复类型，有reverse和restart两个值，reverse表示倒序回放，restart表示重新放一遍，必须与repeatCount一起使用才能看到效果。因为这里的意义是重复的类型，即回放时的动作。
 * android:interpolator  设定插值器，其实就是指定的动作效果，比如弹跳效果等，不在这小节中讲解，后面会单独列出一单讲解。
 * <p>
 * 对于android:duration，就不再讲解了，就是动画的持续时长，以毫秒为单位，下面看看android:fillAfter和android:fillBefore
 */

public class BaseAnimActivity extends AppCompatActivity {

    @BindView(R.id.alpha)
    Button alpha;
    @BindView(R.id.scale)
    Button scale;
    @BindView(R.id.rotate)
    Button rotate;
    @BindView(R.id.trans)
    Button trans;
    @BindView(R.id.set)
    Button set;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.splash)
    Button splash;


    // 自定义控件三部曲之动画篇（二）——Interpolator插值器
    Animation scaleAnimation, alphaAnimation,
            rotateAnimation, transAnimation, setAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_baseanim);
        ButterKnife.bind(this);


        scaleAnimation = AnimationUtils.loadAnimation(this, R.anim.scaleanim);
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alphaanimation);
        rotateAnimation = AnimationUtils.loadAnimation(this, R.anim.rotateanimation);
        transAnimation = AnimationUtils.loadAnimation(this, R.anim.transanimation);
        setAnimation = AnimationUtils.loadAnimation(this, R.anim.setanimation);
    }


    @OnClick({R.id.alpha, R.id.scale, R.id.rotate, R.id.trans, R.id.set})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.alpha:
                /**
                 * alpha标签——调节透明度
                 *
                 * 1、自身属性
                 * android:fromAlpha   动画开始的透明度，从0.0 --1.0 ，0.0表示全透明，1.0表示完全不透明
                 * android:toAlpha       动画结束时的透明度，也是从0.0 --1.0 ，0.0表示全透明，1.0表示完全不透明
                 *
                 */
                tv.startAnimation(alphaAnimation);
                break;
            case R.id.scale:
                tv.startAnimation(scaleAnimation);
                break;
            case R.id.rotate:
                /**
                 * rotate标签——旋转
                 *
                 * 1、自身属性
                 * android:fromDegrees     开始旋转的角度位置，正值代表顺时针方向度数，负值代码逆时针方向度数
                 * android:toDegrees         结束时旋转到的角度位置，正值代表顺时针方向度数，负值代码逆时针方向度数
                 * android:pivotX               缩放起点X轴坐标，可以是数值、百分数、百分数p 三种样式，比如 50、50%、50%p，具体意义已在scale标签中讲述，这里就不再重讲
                 * android:pivotY               缩放起点Y轴坐标，可以是数值、百分数、百分数p 三种样式，比如 50、50%、50%p
                 */
                tv.startAnimation(rotateAnimation);
                break;
            case R.id.trans:
                /**
                 * translate标签 —— 平移
                 *
                 * 1、自身属性
                 * android:fromXDelta     起始点X轴坐标，可以是数值、百分数、百分数p 三种样式，比如 50、50%、50%p，具体意义已在scale标签中讲述，这里就不再重讲
                 * android:fromYDelta    起始点Y轴从标，可以是数值、百分数、百分数p 三种样式；
                 * android:toXDelta         结束点X轴坐标
                 * android:toYDelta        结束点Y轴坐标
                 */
                tv.startAnimation(transAnimation);
                break;
            case R.id.set:
                /**
                 * set标签——定义动作合集
                 * 前面我们讲解了各个标签动画的意义及用法，但他们都是独立对控件起作用，
                 * 假设我现在想上面的textView控件做一个动画——从小到大，旋转出场，而且透明度也要从0变成1
                 *
                 * 属性：

                 set标签自已是没有属性的，他的属性都是从Animation继承而来，但当它们用于Set标签时，就会对Set标签下的所有子控件都产生作用。

                 属性有：（从Animation类继承的属性）

                 android:duration        动画持续时间，以毫秒为单位
                 android:fillAfter          如果设置为true，控件动画结束时，将保持动画最后时的状态
                 android:fillBefore       如果设置为true,控件动画结束时，还原到开始动画前的状态
                 android:fillEnabled    与android:fillBefore 效果相同，都是在动画结束时，将控件还原到初始化状态
                 android:repeatCount 重复次数
                 android:repeatMode	重复类型，有reverse和restart两个值，reverse表示倒序回放，restart表示重新放一遍，必须与repeatCount一起使用才能看到效果。因为这里的意义是重复的类型，即回放时的动作。
                 android:interpolator  设定插值器，其实就是指定的动作效果，比如弹跳效果等，不在这小节中讲解，后面会单独列出一单讲解。
                 */
                tv.startAnimation(setAnimation);
                break;
        }
    }

    @OnClick(R.id.splash)
    public void onViewClicked() {
        Intent intent = new Intent();
        intent.setClass(BaseAnimActivity.this, SecondActivity.class);
        startActivity(intent);

        // 过渡界面，用于启动页
        overridePendingTransition(R.anim.alpha_in, R.anim.alpha_out);
    }
}
