package com.example.customview.step1.propertyvaluesholder;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.customview.R;
import com.example.customview.step1.customevaluator.CharEvaluator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/22.
 * Email:1004260403@qq.com
 */

/**
 * 前几篇给大家讲了ValueAnimator、ObjectAnimator的知识，讲解了它们ofInt(),ofFloat(),ofObject()函数的用法。
 * 细心的同学可能会注意到，ValueAnimator、ObjectAnimator除了这些创建Animator实例的方法以外，都还有一个方法：
 * <p>
 * valueAnimator的
 * public static ValueAnimator ofPropertyValuesHolder(PropertyValuesHolder... values)
 * ObjectAnimator的
 * public static ObjectAnimator ofPropertyValuesHolder(Object target,PropertyValuesHolder... values)
 * <p>
 * <p>
 * 由于ValueAnimator和ObjectAnimator都具有ofPropertyValuesHolder（）函数，使用方法也差不多，
 * 相比而言，ValueAnimator的使用机会不多，这里我们就只讲ObjectAnimator中ofPropertyValuesHolder（）的用法。
 */

public class OfPropertyValuesHolderActivity extends AppCompatActivity {

    @BindView(R.id.button1)
    Button button1;
    @BindView(R.id.button2)
    Button button2;
    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.button3)
    Button button3;
    @BindView(R.id.img)
    ImageView img;
    private MyTextView mMyTv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofpropertyvaluesholder);
        ButterKnife.bind(this);
        mMyTv = (MyTextView) findViewById(R.id.mytv);
    }

    /**
     * PropertyValuesHolder,
     * PropertyValuesHolder中有很多函数: 我们来看看创建实例的函数：
     * public static PropertyValuesHolder ofFloat(String propertyName, float... values)
     * public static PropertyValuesHolder ofInt(String propertyName, int... values)
     * public static PropertyValuesHolder ofObject(String propertyName, TypeEvaluator evaluator,Object... values)
     * public static PropertyValuesHolder ofKeyframe(String propertyName, Keyframe... values)
     * <p>
     * <p>
     * PropertyValuesHolder这个类的意义就是，它其中保存了动画过程中所需要操作的属性和对应的值。我们通过ofFloat(Object target, String propertyName, float… values)构造的动画，ofFloat()的内部实现其实就是将传进来的参数封装成PropertyValuesHolder实例来保存动画状态。
     * 在封装成PropertyValuesHolder实例以后，后期的各种操作也是以PropertyValuesHolder为主的。
     */

    @OnClick({R.id.button1, R.id.button2, R.id.button3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.button1:
                /**
                 * PropertyValuesHolder之ofFloat()、ofInt()
                 * propertyName：表示ObjectAnimator需要操作的属性名。即ObjectAnimator需要通过反射查找对应属性的setProperty()函数的那个property.
                 * values：属性所对应的参数，同样是可变长参数，可以指定多个，还记得我们在ObjectAnimator中讲过，如果只指定了一个，那么ObjectAnimator会通过查找getProperty()方法来获得初始值。
                 * 不理解的同学请参看《Animation动画详解(七)——ObjectAnimator基本使用》
                 *
                 * ObjectAnimator.ofPropertyValuesHolder()
                 * 其中：target：指需要执行动画的控件
                 * values：是一个可变长参数，可以传进去多个PropertyValuesHolder实例，由于每个PropertyValuesHolder实例都会针对一个属性做动画，
                 * 所以如果传进去多个PropertyValuesHolder实例，将会对控件的多个属性同时做动画操作。
                 */
                doOfAnim();
                break;
            case R.id.button2:
                /**
                 *  PropertyValuesHolder之ofObject()
                 *  ofObject的构造函数 public static PropertyValuesHolder ofObject(String propertyName, TypeEvaluator evaluator,Object... values)
                 *
                 *  propertyName:ObjectAnimator动画操作的属性名;
                 *  evaluator:Evaluator实例，Evaluator是将当前动画进度计算出当前值的类，可以使用系统自带的IntEvaluator、FloatEvaluator也可以自定义，有关Evaluator的知识，大家可以参考《Animation动画详解(五)——ValueAnimator高级进阶（一）》
                 *  values：可变长参数，表示操作动画属性的值
                 *  它的各个参数与ObjectAnimator.ofObject的类似,只是少了target参数而已
                 */

                doOfObjectAnim();
                break;
            case R.id.button3:
                /**
                 * 通过前面几篇的讲解，我们知道如果要控制动画速率的变化，我们可以通过自定义插值器，
                 * 也可以通过自定义Evaluator来实现。但如果真的让我们为了速率变化效果而自定义插值器或者Evaluator的话，
                 * 恐怕大部分同学会有一万头草泥马在眼前奔过，因为大部分的同学的数学知识已经还给老师了。
                 * 为了解决方便的控制动画速率的问题，谷歌为了我等屁民定义了一个KeyFrame的类，KeyFrame直译过来就是关键帧。
                 * 关键帧这个概念是从动画里学来的，我们知道视频里，一秒要播放24帧图片，对于制作flash动画的同学来讲，是不是每一帧都要画出来呢？当然不是了，如果每一帧都画出来，那估计做出来一个动画片都得要一年时间；比如我们要让一个球在30秒时间内，从（0,0）点运动到（300，200）点，那flash是怎么来做的呢，在flash中，我们只需要定义两个关键帧，在动画开始时定义一个，把球的位置放在(0,0)点；在30秒后，再定义一个关键帧，把球的位置放在（300，200）点。在动画 开始时，球初始在是（0，0）点，30秒时间内就adobe flash就会自动填充，把球平滑移动到第二个关键帧的位置（300，200）点；
                 * 通过上面分析flash动画的制作原理，我们知道，一个关键帧必须包含两个原素，第一时间点，第二位置。即这个关键帧是表示的是某个物体在哪个时间点应该在哪个位置上。
                 *
                 * 所以谷歌的KeyFrame也不例外，KeyFrame的生成方式为：
                 *    Keyframe kf0 = Keyframe.ofFloat(0, 0);
                 *    Keyframe kf1 = Keyframe.ofFloat(0.1f, -20f);
                 *    Keyframe kf2 = Keyframe.ofFloat(1f, 0);
                 *
                 * KeyFrame的ofInt函数的声明为：
                 * public static Keyframe ofFloat(float fraction, float value)
                 *  fraction：表示当前的显示进度，即从加速器中getInterpolation()函数的返回值；
                 *  value：表示当前应该在的位置
                 *  比如Keyframe.ofFloat(0, 0)表示动画进度为0时，动画所在的数值位置为0；Keyframe.ofFloat(0.25f, -20f)表示动画进度为25%时，动画所在的数值位置为-20；Keyframe.ofFloat(1f,0)表示动画结束时，动画所在的数值位置为0；
                 *
                 *  在理解了KeyFrame.ofFloat()的参数以后，我们来看看PropertyValuesHolder是如何使用KeyFrame对象的：
                 *   public static PropertyValuesHolder ofKeyframe(String propertyName, Keyframe... values)
                 *       propertyName：动画所要操作的属性名
                 values：Keyframe的列表，PropertyValuesHolder会根据每个Keyframe的设定，定时将指定的值输出给动画。

                 所以完整的KeyFrame的使用代码应该是这样的：
                 Keyframe frame0 = Keyframe.ofFloat(0f, 0);
                 Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
                 Keyframe frame2 = Keyframe.ofFloat(1, 0);
                 PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame2);
                 Animator animator = ObjectAnimator.ofPropertyValuesHolder(mImage,frameHolder);
                 animator.setDuration(1000);
                 animator.start();
                 *
                 */

//                doOfFloatAnim();
                // 示例1——没有插值器
//                donoInterpolatorAnim();
                // 示例2——使用插值器 
//                doInterpolatorAnim();
                // Keyframe之ofObject
//                doMyTvAnim();
                // 疑问：如果没有设置进度为0或者进度为1时的关键帧，展示是怎样的？
//                doAnswerAnim();
                doBellRingAnim();
                break;
        }
    }

    /**
     * 电话响铃效果
     * 这里的总共分为四步：
     * 第一步，实现左右震铃效果；
     * 这部分代码前面已经讲过，这里就不再赘述
     * 第二步，利用View类中的SetScaleX(float value)方法所对应的ScaleX属性，在动画过程中，将图片横向放大1.1倍：
     * 非常注意的是，在动画过程中放大1.1倍，在开始动画和动画结束时，都要还原状态，即原大小的1倍值：
     * 第三步，同样利用View类的SetScaleY(float value)方法，在动画过程中将图片纵向放大1.1倍。原理与scaleX相同，就不再细讲。
     * 第四步：生成ObjectAnimator实例：
     *
     *
     */
    private void doBellRingAnim() {
        /**
         * 左右震动效果
         */
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder1 = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4,
                frame5, frame6, frame7, frame8, frame9, frame10);


        /**
         * scaleX放大1.1倍
         */
        Keyframe scaleXframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleXframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleXframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleXframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleXframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleXframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleXframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleXframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleXframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleXframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleXframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder frameHolder2 = PropertyValuesHolder.ofKeyframe("ScaleX",scaleXframe0,scaleXframe1,scaleXframe2,scaleXframe3,scaleXframe4,
                scaleXframe5,scaleXframe6,scaleXframe7,scaleXframe8,scaleXframe9,scaleXframe10);


        /**
         * scaleY放大1.1倍
         */
        Keyframe scaleYframe0 = Keyframe.ofFloat(0f, 1);
        Keyframe scaleYframe1 = Keyframe.ofFloat(0.1f, 1.1f);
        Keyframe scaleYframe2 = Keyframe.ofFloat(0.2f, 1.1f);
        Keyframe scaleYframe3 = Keyframe.ofFloat(0.3f, 1.1f);
        Keyframe scaleYframe4 = Keyframe.ofFloat(0.4f, 1.1f);
        Keyframe scaleYframe5 = Keyframe.ofFloat(0.5f, 1.1f);
        Keyframe scaleYframe6 = Keyframe.ofFloat(0.6f, 1.1f);
        Keyframe scaleYframe7 = Keyframe.ofFloat(0.7f, 1.1f);
        Keyframe scaleYframe8 = Keyframe.ofFloat(0.8f, 1.1f);
        Keyframe scaleYframe9 = Keyframe.ofFloat(0.9f, 1.1f);
        Keyframe scaleYframe10 = Keyframe.ofFloat(1, 1);
        PropertyValuesHolder frameHolder3 = PropertyValuesHolder.ofKeyframe("ScaleY",scaleYframe0,scaleYframe1,scaleYframe2,scaleYframe3,scaleYframe4,
                scaleYframe5,scaleYframe6,scaleYframe7,scaleYframe8,scaleYframe9,scaleYframe10);

        /**
         * 构建动画
         */
        Animator animator = ObjectAnimator.ofPropertyValuesHolder(img, frameHolder1,frameHolder2,frameHolder3);
        animator.setDuration(1000);
        animator.start();
    }

    /**
     * PropertyValuesHolder之其它函数
     * PropertyValuesHolder除了上面的讲到的ofInt,ofFloat,ofObject,ofKeyframe以外，api 11的还有几个函数：
     *     /**
     * 设置动画的Evaluator
     * public void setEvaluator(TypeEvaluator evaluator)
     * 用于设置ofFloat所对应的动画值列表
     * public void setFloatValues(float... values)
     * 用于设置ofInt所对应的动画值列表
     * public void setIntValues(int... values)
     * 用于设置ofKeyframe所对应的动画值列表
     * public void setKeyframes(Keyframe... values)
     * 用于设置ofObject所对应的动画值列表
     * public void setObjectValues(Object... values)
     * 设置动画属性名
     * public void setPropertyName(String propertyName)
     *
     * 这些函数都比较好理解，setFloatValues(float… values)对应PropertyValuesHolder.ofFloat()，用于动态设置动画中的数值。setIntValues、setKeyframes、setObjectValues同理；
     * setPropertyName用于设置PropertyValuesHolder所需要操作的动画属性名;
     * 最重要的是setEvaluator(TypeEvaluator evaluator)
     * 设置动画的Evaluator
     * public void setEvaluator(TypeEvaluator evaluator)
     *
     *
     * 如果是利用PropertyValuesHolder.ofObject()来创建动画实例的话，我们是一定要显示调用 PropertyValuesHolder.setEvaluator()来设置Evaluator的。
     * 在上面的字母转换的例子中，我们已经用过这个函数了。这里也就没什么好讲的了。
    */


    private void doAnswerAnim() {
        // 这里有三个帧，在进度为0.5时，电话向右旋转100度，然后再转回来。
//        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
//        Keyframe frame1 = Keyframe.ofFloat(0.5f, 100f);
//        Keyframe frame2 = Keyframe.ofFloat(1,0);
//        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0,frame1,frame2);
//        Animator animator = ObjectAnimator.ofPropertyValuesHolder(img, frameHolder);
//        animator.setDuration(3000);
//        animator.start();

        // 去掉第0帧，将以第一帧为起始位置
        // 如果我们把第0帧去掉，只保留中间帧和结束帧，看结果会怎样?
        // 可以看到，动画是直接从中间帧frame1开始的，即当没有第0帧时，动画从最近的一个帧开始。
//        Keyframe frame1 = Keyframe.ofFloat(0.5f, 100f);
//        Keyframe frame2 = Keyframe.ofFloat(1,0);
//        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame1,frame2);
//        Animator animator = ObjectAnimator.ofPropertyValuesHolder(img, frameHolder);
//        animator.setDuration(3000);
//        animator.start();

        // 去掉结束帧，将最后一帧为结束帧
        // 如果我们把结束帧去掉，保留第0帧和中间帧，看结果会怎样?
        // 可以看到到最后停在最后位置不动了
//        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
//        Keyframe frame1 = Keyframe.ofFloat(0.5f, 100f);
//        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0,frame1);
//        Animator animator = ObjectAnimator.ofPropertyValuesHolder(img, frameHolder);
//        animator.setDuration(3000);
//        animator.start();

        // 只保留一个中间帧，会崩,
        // 异常如下：报错问题是数组越界，也就是说，至少要有两个帧才行。
//        Keyframe frame1 = Keyframe.ofFloat(0.5f, 100f);
//        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame1);
//        Animator animator = ObjectAnimator.ofPropertyValuesHolder(img, frameHolder);
//        animator.setDuration(3000);
//        animator.start();

        // 保留两个中间帧
        Keyframe frame1 = Keyframe.ofFloat(0.5f, 100f);
        Keyframe frame2 = Keyframe.ofFloat(0.7f,50f);
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame1,frame2);

        Animator animator = ObjectAnimator.ofPropertyValuesHolder(img, frameHolder);
        animator.setDuration(3000);
        animator.start();

        /**
         * 下面我们做出结论：
         * 如果去掉第0帧，将以第一个关键帧为起始位置
         * 如果去掉结束帧，将以最后一个关键帧为结束位置
         * 使用Keyframe来构建动画，至少要有两个或两个以上帧
         */


    }

    /**
     *  Keyframe之ofObject
     *  与ofInt,ofFloat一样，ofObject也有两个构造函数：
     *      public static Keyframe ofObject(float fraction)
     *      public static Keyframe ofObject(float fraction, Object value)
     *
     * 分析：
     * 在这个动画中，我们定义了三帧：
     * frame0表示在进度为0的时候，动画的字符是A；
     * frame1表示在进度在0.1的时候，动画的字符是L;
     * frame2表示在结束的时候，动画的字符是Z；
     * 利用关键帧创建PropertyValuesHolder后，一定要记得设置自定义的Evaluator:
     */

    private void doMyTvAnim() {
        Keyframe frame0 = Keyframe.ofObject(0f, new Character('A'));
        Keyframe frame1 = Keyframe.ofObject(0.1f, new Character('L'));
        Keyframe frame2 = Keyframe.ofObject(1,new Character('Z'));

        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("CharText",frame0,frame1,frame2);
        frameHolder.setEvaluator(new CharEvaluator());
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mMyTv,frameHolder);
        animator.setDuration(3000);
        animator.start();
    }

    private void doInterpolatorAnim() {
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.5f, 300f);
        Keyframe frame2 = Keyframe.ofFloat(1);
        frame2.setValue(0f);
        frame2.setInterpolator(new BounceInterpolator());
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame2);

        Animator animator = ObjectAnimator.ofPropertyValuesHolder(img,frameHolder);
        animator.setDuration(3000);
        animator.start();
    }

    /**
     * 在这段代码中，总共就只有三个关键帧，最后一个Keyframe的生成方法是利用:
     *     Keyframe frame2 = Keyframe.ofFloat(1);
     *     frame2.setValue(0f);
     *     对于Keyframe而言，fraction和value这两个参数是必须有的，所以无论用哪种方式实例化Keyframe都必须保证这两个值必须被初始化。 
     * 这里没有设置插值器，会使用默认的线性插值器（LinearInterpolator）
     */
    private void donoInterpolatorAnim() {
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.5f, 100f);
        Keyframe frame2 = Keyframe.ofFloat(1);
        frame2.setValue(0f);
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame2);

        Animator animator = ObjectAnimator.ofPropertyValuesHolder(img,frameHolder);
        animator.setDuration(3000);
        animator.start();
    }
    //  ——————————————————————-常用函数————————————————
    /**
     * 常用函数：
     * /**
     * 设置fraction参数，即Keyframe所对应的进度
     *  public void setFraction(float fraction)
     * 设置当前Keyframe所对应的值
     * public void setValue(Object value)
     * 设置Keyframe动作期间所对应的插值器
     * public void setInterpolator(TimeInterpolator interpolator)
     */


    /**
     * 第一步：生成Keyframe对象；
     * 第二步：利用PropertyValuesHolder.ofKeyframe()生成PropertyValuesHolder对象
     * 第三步：ObjectAnimator.ofPropertyValuesHolder()生成对应的Animator
     *
     * 分析：
     * 在这些keyframe中，首先指定在开始和结束时，旋转角度为0
     * 然后在过程中，让它左右旋转，比如在进度为0.2时，旋转到左边20度位置：
     * 然后在进度为0.3时，旋转到右边20度位置:以此类推。
     */
    private void doOfFloatAnim() {
        Keyframe frame0 = Keyframe.ofFloat(0f, 0);
        Keyframe frame1 = Keyframe.ofFloat(0.1f, -20f);
        Keyframe frame2 = Keyframe.ofFloat(0.2f, 20f);
        Keyframe frame3 = Keyframe.ofFloat(0.3f, -20f);
        Keyframe frame4 = Keyframe.ofFloat(0.4f, 20f);
        Keyframe frame5 = Keyframe.ofFloat(0.5f, -20f);
        Keyframe frame6 = Keyframe.ofFloat(0.6f, 20f);
        Keyframe frame7 = Keyframe.ofFloat(0.7f, -20f);
        Keyframe frame8 = Keyframe.ofFloat(0.8f, 20f);
        Keyframe frame9 = Keyframe.ofFloat(0.9f, -20f);
        Keyframe frame10 = Keyframe.ofFloat(1, 0);
        PropertyValuesHolder frameHolder = PropertyValuesHolder.ofKeyframe("rotation", frame0, frame1, frame2, frame3, frame4,
                frame5, frame6, frame7, frame8, frame9, frame10);

        Animator animator = ObjectAnimator.ofPropertyValuesHolder(img, frameHolder);
        animator.setDuration(1000);
        animator.start();
    }


    /**
     * 根据PropertyValuesHolder.ofObject生成一个PropertyValuesHolder实例，
     * 注意它的属性就是CharText，所对应的set函数就是setCharText,
     * 由于CharEvaluator的中间值是Character类型，所以CharText属性所对应的完整的函数声明为setCharText(Character character)；
     * 这也就是我们为什么要自定义一个MyTextView原因，就是因为TextView中没有setText(Character character)这样的函数。
     * 然后就是利用ObjectAnimator.ofPropertyValuesHolder生成ObjectAnimator实例了，
     * 最后就是对animator设置并start了，没什么难度，就不再讲了。
     */
    private void doOfObjectAnim() {
        PropertyValuesHolder charHolder = PropertyValuesHolder.ofObject("CharText", new CharEvaluator(), new Character('A'), new Character('Z'));
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(mMyTv, charHolder);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    /**
     * PropertyValuesHolder的ofFloat、ofInt来做动画的
     */
    private void doOfAnim() {
        PropertyValuesHolder rotationHolder = PropertyValuesHolder.ofFloat("Rotation", 60f, -60f, 40f, -40f, -20f, 20f, 10f, -10f, 0f);
        PropertyValuesHolder colorHolder = PropertyValuesHolder.ofInt("BackgroundColor", 0xffffffff, 0xffff00ff, 0xffffff00, 0xffffffff);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(tv, rotationHolder, colorHolder);
        animator.setDuration(3000);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }
}
