package com.zx.customview.step1.animatorset;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.TextView;

import com.zx.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/22.
 * Email:1004260403@qq.com
 * <p>
 * AnimatorSet——playSequentially,playTogether>
 * 首先，AnimatorSet针对ValueAnimator和ObjectAnimator都是适用的，
 * 但一般而言，我们不会用到ValueAnimator的组合动画，所以我们这篇仅讲解ObjectAnimator下的组合动画实现。
 * 在AnimatorSet中直接给为我们提供了两个方法playSequentially和playTogether，
 * playSequentially表示所有动画依次播放，playTogether表示所有动画一起开始。
 */

public class AnimatorSetActivity extends AppCompatActivity {

    private static final String TAG = "AnimatorSetActivity";
    @BindView(R.id.btn_cancel)
    Button btnCancel;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.tv_2)
    TextView tv2;
    @BindView(R.id.btn_playSequentially)
    Button btnPlaySequentially;
    @BindView(R.id.btn_playTogether)
    Button btnPlayTogether;
    @BindView(R.id.btn_11)
    Button btn11;

    private AnimatorSet mAnimatorSet;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animationset);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_cancel, R.id.btn_playSequentially, R.id.btn_playTogether,
            R.id.btn_11, R.id.btn_forever, R.id.btn_free, R.id.btn_free2, R.id.btn_doListenerAnimation,
            R.id.btn_doAnimatorSetSetting, R.id.btn_dosetTarget, R.id.btn_setStartDelay})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_playSequentially:
                // playSequentially
                doPlaySequentiallyAnimator();
                break;
            case R.id.btn_playTogether:
                doPlayTogetherAnimator();
                break;
            case R.id.btn_11:
//                doPlayTogether2Animator();
                doPlaySequentially2Animator();
                break;
            case R.id.btn_forever:
                doInfiniteAnimation();
                break;
            case R.id.btn_free:
                doBuilderAnimation();
                break;
            case R.id.btn_free2:
                doBuilder2Animation();
                break;
            case R.id.btn_doListenerAnimation:
                mAnimatorSet = doListenerAnimation();
                break;
            case R.id.btn_cancel:
                if (null != mAnimatorSet) {
                    mAnimatorSet.cancel();
                }
                break;
            case R.id.btn_doAnimatorSetSetting:
                doAnimatorSetSetting();
                break;
            case R.id.btn_dosetTarget:
                doAnimatorSetTarget();
                break;
            case R.id.btn_setStartDelay:
                doStartDelay();
                break;
        }
    }

    /**
     * 设置延时开始动画时长
     * public void setStartDelay(long startDelay)
     * 上面我们讲了，当AnimatorSet所拥有的函数与单个动画所拥有的函数冲突时，就以AnimatorSet设置为准。但唯一的例外就是setStartDelay。
     setStartDelay函数不会覆盖单个动画的延时，而且仅针对性的延长AnimatorSet的激活时间，单个动画的所设置的setStartDelay仍对单个动画起作用。
     在这个动画中，我们首先给AnimatorSet设置了延时，所以AnimatorSet会在2000毫秒以后，才会执行start()函数。另外我们还给tv2设置了延时2000毫秒，所以在动画开始后，tv1会直接运动，但tv2要等2000毫秒以后，才会开始运动。
     这里要特别提醒大家注意一行代码：
     animatorSet.play(tv1TranslateY).with(tv2TranslateY);
     在这行代码中，我们play的是tv1！而且tv1是没有设置延时的！这里要非常注意一下，下面我们会深入的探讨这个问题。
     在这个效果图中可以看到在点击了start anim按钮以后，动画并没有立即开始，这是因为我们给AnimatorSet设置了延时；另外在AnimatorSet延时过了以后，可以看到tv1立刻开始动画，但此时tv2并没有任何动静。这是因为我们单独给tv2又设置了延时。
     所以从这里，我们可以得到一个结论：
     AnimatorSet的延时是仅针对性的延长AnimatorSet激活时间的，对单个动画的延时设置没有影响。
     */
    /**
     * 示例二：
     * 这个动画效果有没有感觉非常奇怪，这里的代码仅仅调换了play的顺序，却与上面的效果完全不一样！
     按说这里的效果应该与上个的效果是一样的才对，即在AnimatorSet被激活以后，tv1应该立即运行，等2000毫秒后tv2才开始运行。
     但这里的效果却是过了一段时间以后，tv1和tv2一起运行！
     这是因为：
     AnimatorSet真正激活延时 = AnimatorSet.startDelay+第一个动画.startDelay
     也就是说AnimatorSet被激活的真正延时等于它本身设置的setStartDelay(2000)延时再上第一个动画的延时；
     在真正的延时过了之后，动画被激活，这时相当于赛马场的每个跑道的门就打开了。每个动画就按照自己的动画处理来操作了，如果有延时就延时动画。但由于第一个动画的延时已经AnimatorSet被用掉了，所以第一个动画就直接运行。
     在这个例子中，由于只有tv1有延时，而在AnimatorSet被激活后，tv1的延时被AnimatorSet用掉了，所以tv1直接运行;而在AnimatorSet激活后，由于tv2没有设置延时，所以tv2直接运动。
     下面我们再举个例子，同样是上面的代码，我们如果给tv2加上延时会怎样：
     从效果图中也可以看到，由于AnimatorSet激活延时 = AnimatorSet.startDelay+第一个动画.startDelay；所以在4000毫秒后，动画被激活，tv2由于已经被用掉了延时，所以在激活后直接开始。但tv1则按照自己的设定，在动画激活后，延时2000毫秒后才开始动画；
     */

    /**
     * 经过上面的例子，我们可以得出以下结论：
     - AnimatorSet的延时是仅针对性的延长AnimatorSet激活时间的，对单个动画的延时设置没有影响。
     - AnimatorSet真正激活延时 = AnimatorSet.startDelay+第一个动画.startDelay
     - 在AnimatorSet激活之后，第一个动画绝对是会开始运行的，后面的动画则根据自己是否延时自行处理。
     */
    private void doStartDelay() {
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0, 400, 0);
        tv1TranslateY.setStartDelay(2000);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);
        tv2TranslateY.setStartDelay(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        // 上面我们提示大家注意动画顺序，上面的动画顺序是
//        animatorSet.play(tv1TranslateY).with(tv2TranslateY);
        animatorSet.play(tv2TranslateY).with(tv1TranslateY);
        animatorSet.setStartDelay(2000);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * setTarget(Object target)示例
     * 设置ObjectAnimator动画目标控件
     * public void setTarget(Object target)
     * 这里我们着重讲一下AnimatorSet的setTartget函数，这个函数是用来设置目标控件的，也就是说，只要通过AnimatorSet的setTartget函数设置了目标控件，那么单个动画中的目标控件都以AnimatorSet设置的为准
     我们来看个例子：
     在这段代码中，我们给tv1设置了改变背景色，给tv2设置了上下移动。
     但由于我们通过animatorSet.setTarget(mTv2);将各个动画的目标控件设置为mTv2，所以tv1将不会有任何动画，所有的动画都会发生在tv2上。
     */
    // 所以AnimatorSet.setTarget()的作用就是将动画的目标统一设置为当前控件，AnimatorSet中的所有动画都将作用在所设置的target控件上
    private void doAnimatorSetTarget() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(tv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(tv1BgAnimator,tv2TranslateY);
        animatorSet.setDuration(2000);
        animatorSet.setTarget(tv2);
        animatorSet.start();
    }

    /**
     * 通用函数逐个设置与AnimatorSet设置的区别
     * 在AnimatorSet中还有几个函数：
     *
     * 设置单次动画时长
     * public AnimatorSet setDuration(long duration);
     *
     * 设置加速器
     * public void setInterpolator(TimeInterpolator interpolator)
     *
     * 设置ObjectAnimator动画目标控件
     * public void setTarget(Object target)
     * 这几个函数好像比较诡异，因为在ObjectAnimator中也都有这几个函数。那在AnimatorSet中设置与在单个ObjectAnimator中设置有什么区别呢？
     * 区别就是：在AnimatorSet中设置以后，会覆盖单个ObjectAnimator中的设置；即如果AnimatorSet中没有设置，那么就以ObjectAnimator中的设置为准。如果AnimatorSet中设置以后，ObjectAnimator中的设置就会无效。
     *
     * 在第这个例子中，我们通过animatorSet.setDuration(2000);设置为所有动画单次运动时长为2000毫秒，虽然我们给tv1TranslateY设置了单次动画时长为tv1TranslateY.setDuration(500000000);但由于AnimatorSet设置了setDuration(2000)这个参数以后，单个动画的时长设置将无效。所以每个动画的时长为2000毫秒。
     但我们这里还分别给tv1和tv2设置了加速器，但并没有给AnimatorSet设置加速器，那么tv1,tv2将按各自加速器的表现形式做动画。
     同样，如果我们给AnimatorSet设置上了加速器，那么单个动画中所设置的加速器都将无效，以AnimatorSet中的加速器为准。
     效果图如下：
     从动画中也可以看到，这两个控件同时开始，同时结束，这说明他们两个的单次动画的时长是一样的。也就是以animatorSet.setDuration(2000)为准的2000毫秒。
     其次，这两个动画在运动过程中的表现形式是完全不一样的，这说明他们的加速器是不一样的。也就是在AnimatorSet没有统一设置的情况下，各自按各自的来。
     */
    private void doAnimatorSetSetting() {
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0, 400, 0);
        tv1TranslateY.setDuration(500000000);
        tv1TranslateY.setInterpolator(new BounceInterpolator());

        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);
        tv2TranslateY.setInterpolator(new AccelerateDecelerateInterpolator());


        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(tv2TranslateY).with(tv1TranslateY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * AnimatorSet监听器,
     * 在AnimatorSet中也可以添加监听器，对应的监听器为：
     * public static interface AnimatorListener {
     *
     * 当AnimatorSet开始时调用
     * void onAnimationStart(Animator animation);
     *
     * 当AnimatorSet结束时调用
     * void onAnimationEnd(Animator animation);
     *
     * 当AnimatorSet被取消时调用
     * void onAnimationCancel(Animator animation);
     *
     * 当AnimatorSet重复时调用，由于AnimatorSet没有设置repeat的函数，所以这个方法永远不会被调用
     * void onAnimationRepeat(Animator animation);
     * }
     *
     * 添加方法为：
     * public void addListener(AnimatorListener listener);
     *
     * 好像这个listenner和ValueAnimator的一模一样啊。不错，确实是一模一样，因为ValueAnimator和AnimatorSet都派生自Animator类，而AnimatorListener是Animator类中的函数。
     * 监听器的用法并不难，难点在于，我们AnimatorSet中的监听器，监听的AnimatorSet本身的动作，还是它内部的每个动画的动作？在AnimatorSet代码注释中我们已经提到，它监听的是AnimatorSet的过程，所以只有当AnimatorSet的状态发生变化时，才会被调用。
     * 我们来看个例子：
     * 额外添加一个Cancel按钮，在点击start按钮时，开始动画，在点击取消按钮时取消动画
     */

    /**
     * 这里着重注意两点：
     * 第一：将动画tv2TranslateY设置为无限循环
     * 第二：在animatorSet添加的Animator.AnimatorListener()中每个部分添加上log
     * 我们看一下对应的动画及Log
     * 从效果图和对应中的Log中也可以看到，虽然我们的tv2TranslateY动画在无限循环，但Log中没有打印出对应的repeat的日志，
     * 从日志中也可以看出，AnimatorSet的监听函数也只是用来监听AnimatorSet的状态的，与其中的动画无关；
     * <p>
     * 总结一下AnimatorSet的监听
     * 1、AnimatorSet的监听函数也只是用来监听AnimatorSet的状态的，与其中的动画无关；
     * 2、AnimatorSet中没有设置循环的函数，所以AnimatorSet监听器中永远无法运行到onAnimationRepeat()中！
     * 有关如何实现无限循环的问题，我们上面已经讲了，就不再赘述
     */
    private AnimatorSet doListenerAnimation() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(tv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0, 400, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);
        tv2TranslateY.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(tv1TranslateY).with(tv2TranslateY).after(tv1BgAnimator);
        //添加listener
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                Log.e(TAG, "animator start");
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                Log.e(TAG, "animator end");
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                Log.e(TAG, "animator cancel");
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                Log.e(TAG, "animator repeat");
            }
        });
        animatorSet.setDuration(2000);
        animatorSet.start();
        return animatorSet;
    }

    /**
     * 从上面的代码中，我们可以看到AnimatorSet.Builder是通过animatorSet.play(tv1BgAnimator)生成的，这是生成AnimatorSet.Builder对象的唯一途径！
     *
     * 调用AnimatorSet中的play方法是获取AnimatorSet.Builder对象的唯一途径
     * 表示要播放哪个动画public Builder play(Animator anim)
     *
     * AnimatorSet.Builder还有一些函数:
     * 和前面动画一起执行
     * public Builder with(Animator anim)
     *
     * 执行前面的动画后才执行该动画
     * public Builder before(Animator anim)
     *
     * 执行先执行这个动画再执行前面动画
     * public Builder after(Animator anim)
     *
     * 延迟n毫秒之后执行动画
     * public Builder after(long delay)
     *
     */
    /**
     * 下面我们就举个例子来看一下他们的用法,
     * 这里实现的效果是：在tv1颜色变化后，两个控件一同开始位移动画：
     */
    private void doBuilder2Animation() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(tv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0, 400, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(tv1TranslateY).with(tv2TranslateY).after(tv1BgAnimator);
        animatorSet.setDuration(2000);

        animatorSet.start();
    }

    /**
     * 概述
     * 上面我们讲了playTogether和playSequentially，分别能实现一起开始动画和逐个开始动画。但并不是非常自由的组合动画，比如我们有三个动画A,B,C我们想先播放C然后同时播放A和B。利用playTogether和playSequentially是没办法实现的，所以为了更方便的组合动画，谷歌的开发人员另外给我们提供一个类AnimatorSet.Builder;
     * 我们这里使用AnimatorSet.Builder实现下面这个效果：
     * 即两个控件一同开始动画
     * 首先是构造一个AnimatorSet对象。然后调用animatorSet.play(tv1BgAnimator)方法生成一个AnimatorSet.Builder对象，
     * 直接调用builder.with()就能实现两个控件同时做动画了，多么神奇，下面我们来看看这个AnimatorSet.Builder的定义！
     */
    private void doBuilderAnimation() {
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0, 400, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        AnimatorSet.Builder builder = animatorSet.play(tv1TranslateY);
        builder.with(tv2TranslateY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * 实现无限循环动画
     * 很多同学会一直纠结如何实现无限循环的组合动画，因为AnimatorSet中没有设置循环次数的函数！通过上面的讲解，我们也能知道是否无限循环主要是看动画本身，与门（playTogether）无关！
     * 下面我们就实现三个动画同时开始并无限循环的动画：
     */
    private void doInfiniteAnimation() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(tv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        tv1BgAnimator.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0, 400, 0);
        tv1TranslateY.setRepeatCount(ValueAnimator.INFINITE);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);
        tv2TranslateY.setRepeatCount(ValueAnimator.INFINITE);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(tv1BgAnimator, tv1TranslateY, tv2TranslateY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    // 下面2个动画总结：：
    // 第一：playTogether和playSequentially在激活动画后，控件的动画情况与它们无关，
    // 他们只负责定时激活控件动画。
    // 第二：playSequentially只有上一个控件做完动画以后，才会激活下一个控件的动画，
    // 如果上一控件的动画是无限循环，那下一个控件就别再指望能做动画了。

    /**
     * playSequentially,playTogether真正意义2
     * 同样是那三个动画，首先tv1BgAnimator设置了延时开始，
     * tv1TranslateY设置为无限循环；使用playSequentially来逐个播放这三个动画，
     * 首先是tv1BgAnimator：在开始之后，这个动画会延时2000毫秒再开始。结束之后，激活tv1TranslateY，这个动画会无限循环。无限循环也就是说它永远也不会结束。
     * 那么第三个动画tv2TranslateY也永远不会开始。下面来看看效果图：
     * 在效果图中也可以看出，textview1先是等了一段时间然后开始背景色变化，然后开始无限循环的上下运动。另一个textview永远也不会开始动画了。
     */
    private void doPlaySequentially2Animator() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(tv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        tv1BgAnimator.setStartDelay(2000);

        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0, 300, 0);
        tv1TranslateY.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(tv1BgAnimator, tv1TranslateY, tv2TranslateY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * playSequentially,playTogether真正意义
     * 在这个例子中，我们将tv1TranslateY开始延迟2000毫秒开始，并设为无限循环。tv2TranslateY设为开始延迟2000毫秒。
     * 而tv1BgAnimator则是没有任何设置，所以是默认直接开始。我们来看效果图：
     * 在效果图中可以看到，在点击按钮以后，先进行的是tv1的颜色变化，在颜色变化完以后，tv2的延时也刚好结束，此时两个textview开始位移变换。最后textview1的位移变换是无限循环的。
     * 所以从这个例子中也可以看到，playTogether只是负责在同一时间点把门拉开，拉开门以后，马跑不跑，那是它自己的事了，回不回来，门也管不着。
     * playSequentially也是一样，只是一个回来结束以后，才打开另一个的门。如果上一个一直没回来，那下一个也是永远不会开始的。
     */
    private void doPlayTogether2Animator() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(tv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);

        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0, 400, 0);
        tv1TranslateY.setStartDelay(2000);
        tv1TranslateY.setRepeatCount(ValueAnimator.INFINITE);

        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);
        tv2TranslateY.setStartDelay(2000);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(tv1BgAnimator, tv1TranslateY, tv2TranslateY);
        animatorSet.setDuration(2000);
        animatorSet.start();
    }

    /**
     * playTogether
     * playTogether表示将所有动画一起播放
     * 我们先来看看playTogether的声明：
     * public void playTogether(Animator... items);
     * public void playTogether(Collection<Animator> items);
     * 同样这里也是有两个构造函数，他们两个的意义是一样的，只是传入的参数不一样，第一个依然是传可变长参数列表，第二个则是需要传一个组装好的Collection<Animator>对象。
     */
    private void doPlayTogetherAnimator() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(tv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0, 400, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(tv1BgAnimator, tv1TranslateY, tv2TranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }

    /**
     * playSequentially,
     * playSequentially的声明
     * public void playSequentially(Animator... items);
     * public void playSequentially(List<Animator> items);
     * <p>
     * 这段代码也没什么难度，首先是初始化textview1，textview2和btn的对象，
     * 然后当点击按钮时执行doPlaySequentiallyAnimator()函数。
     * 下面我们来看看doPlaySequentiallyAnimator()的具体实现：
     * <p>
     * 这里首先构造了三个动画，针对textview1的是前两个tv1BgAnimator和tv1TranslateY:分别是改变当前动画背景和改变控件Y坐标位置;
     * 针对textview2则只是通过translationY来改变控件Y坐标位置。有关动画的创建方式，我这里就不再讲了，
     * 不理解的同学请参考上篇《Animation动画详解(七)——ObjectAnimator基本使用》
     * 然后是利用AnimatorSet的playSequentially函数将这三个动画组装起来，逐个播放。代码比较简单，就不再细讲。这篇我们就会在这个demo的基础上来讲解本篇所有的知识点。
     */
    private void doPlaySequentiallyAnimator() {
        ObjectAnimator tv1BgAnimator = ObjectAnimator.ofInt(tv1, "BackgroundColor", 0xffff00ff, 0xffffff00, 0xffff00ff);
        ObjectAnimator tv1TranslateY = ObjectAnimator.ofFloat(tv1, "translationY", 0, 300, 0);
        ObjectAnimator tv2TranslateY = ObjectAnimator.ofFloat(tv2, "translationY", 0, 400, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(tv1BgAnimator, tv1TranslateY, tv2TranslateY);
        animatorSet.setDuration(1000);
        animatorSet.start();
    }


}
