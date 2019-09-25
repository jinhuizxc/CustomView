package com.example.customview.step1.interpolator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
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
 * Created by jinhui on 2017/12/29.
 * Email:1004260403@qq.com
 * <p>
 * 插值器学习篇
 * 自定义控件三部曲之动画篇（二）——Interpolator插值器
 * Interpolator属性是Animation类的一个XML属性，所以alpha、scale、rotate、translate、set都会继承得到这个属性。Interpolator被译为插值器，其实我不大能从字面上理解为什么会这样译，其实他是一个指定动画如何变化的东东，跟PS里的动作有点类似：随便拿来一张图片，应用一个动作，图片就会指定变化。
 * Interpolator的系统值有下面几个：
 * 意义如下：
 * <p>
 * AccelerateDecelerateInterpolator   在动画开始与结束的地方速率改变比较慢，在中间的时候加速
 * AccelerateInterpolator                     在动画开始的地方速率改变比较慢，然后开始加速
 * AnticipateInterpolator                      开始的时候向后然后向前甩
 * AnticipateOvershootInterpolator     开始的时候向后然后向前甩一定值后返回最后的值
 * BounceInterpolator                          动画结束的时候弹起
 * CycleInterpolator                             动画循环播放特定的次数，速率改变沿着正弦曲线
 * DecelerateInterpolator                    在动画开始的地方快然后慢
 * LinearInterpolator                            以常量速率改变
 * OvershootInterpolator                      向前甩一定值后再回到原来位置
 */

/**
 * 那么rotate标签、alpha标签、translate标签，
 * 以及set标签的用法只要插入对应的插值就好，不做演示，
 * 效果实际开发时因需自己设置啦。
 */
public class InterpolatorActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.button)
    Button button;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.button4)
    Button button4;
    @BindView(R.id.button5)
    Button button5;
    @BindView(R.id.button6)
    Button button6;
    @BindView(R.id.button7)
    Button button7;
    @BindView(R.id.button8)
    Button button8;
    @BindView(R.id.button9)
    Button button9;
    Animation accelerate_decelerate, accelerate, decelerate, anticipate,
            anticipateOvershoot, bounce, cycle, linear, overshoot;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interpolator);
        ButterKnife.bind(this);

        // scale标签
        accelerate_decelerate = AnimationUtils.loadAnimation(this, R.anim.interpolator_accelerate_decelerate);
        accelerate = AnimationUtils.loadAnimation(this, R.anim.interpolator_accelerate);
        decelerate = AnimationUtils.loadAnimation(this, R.anim.interpolator_decelerate);
        anticipate = AnimationUtils.loadAnimation(this, R.anim.interpolator_anticipate);
        anticipateOvershoot = AnimationUtils.loadAnimation(this, R.anim.interpolator_anticipateovershoot);
        bounce = AnimationUtils.loadAnimation(this, R.anim.interpolator_bounce);
        cycle = AnimationUtils.loadAnimation(this, R.anim.interpolator_cycle);
        linear = AnimationUtils.loadAnimation(this, R.anim.interpolator_linear);
        overshoot = AnimationUtils.loadAnimation(this, R.anim.interpolator_overshoot);
        // rotate标签
        Animation accelerate_decelerate_rotate = AnimationUtils.loadAnimation(this, R.anim.interpolator_accelerate_decelerate_rotate);
        // alpha标签
        Animation accelerate_decelerate_alpha = AnimationUtils.loadAnimation(this, R.anim.interpolator_accelerate_decelerate_alpha);
        // translate标签
        Animation accelerate_decelerate_translate = AnimationUtils.loadAnimation(this, R.anim.interpolator_accelerate_decelerate_translate);
    }

    @OnClick({R.id.button, R.id.button2, R.id.button3, R.id.button4, R.id.button5, R.id.button6, R.id.button7, R.id.button8, R.id.button9})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button:
                // 在动画开始与介绍的地方速率改变比较慢，在中间的时候加速
                tv.startAnimation(accelerate_decelerate);
                break;
            case R.id.button2:
                // 在动画开始的地方速率改变比较慢，然后开始加速
                tv.startAnimation(accelerate);
                break;
            case R.id.button3:
                // 在动画开始的地方快然后慢
                tv.startAnimation(decelerate);
                break;
            case R.id.button4:
                // 开始的时候向后然后向前甩
                tv.startAnimation(anticipate);
                break;
            case R.id.button5:
                // 开始的时候向后然后向前甩一定值后返回最后的值
                tv.startAnimation(anticipateOvershoot);
                break;
            case R.id.button6:
                // 动画结束的时候弹起
                tv.startAnimation(bounce);
                break;
            case R.id.button7:
                // 动画循环播放特定的次数，速率改变沿着正弦曲线
                tv.startAnimation(cycle);
                break;
            case R.id.button8:
                // 以常量速率改变
                tv.startAnimation(linear);
                break;
            case R.id.button9:
                //  向前甩一定值后再回到原来位置
                tv.startAnimation(overshoot);
                break;
        }
    }
}
