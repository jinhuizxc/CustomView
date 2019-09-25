package com.example.customview.step1.xml;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/22.
 * Email:1004260403@qq.com
 * <p>
 * 上篇给大家讲了有关AnimatorSet的代码实现方法，这
 * 篇我们就分别来看看如何利用xml来实现ValueAnimator、ObjectAnimator和AnimatorSet;
 * <p>
 * 联合动画的XML实现
 * 在xml中对应animator总共有三个标签，分别是
 * <animator />:对应ValueAnimator
 * <objectAnimator />:对应ObjectAnimator
 * <set />:对应AnimatorSet
 * <p>
 * <p>
 * 总结
 * 最后总结一下，所有animator标签及取值范围如下：
 * <set
 * android:ordering=["together" | "sequentially"]>
 * <p>
 * <objectAnimator
 * android:propertyName="string"
 * android:duration="int"
 * android:valueFrom="float | int | color"
 * android:valueTo="float | int | color"
 * android:startOffset="int"
 * android:repeatCount="int"
 * android:repeatMode=["repeat" | "reverse"]
 * android:valueType=["intType" | "floatType"]/>
 * <p>
 * <animator
 * android:duration="int"
 * android:valueFrom="float | int | color"
 * android:valueTo="float | int | color"
 * android:startOffset="int"
 * android:repeatCount="int"
 * android:repeatMode=["repeat" | "reverse"]
 * android:valueType=["intType" | "floatType"]/>
 * <p>
 * <set>
 * ...
 * </set>
 * </set>
 * <p>
 *
 */

public class xml_Value_ObjectAnimator_AnimatorSet extends AppCompatActivity {

    @BindView(R.id.bt_valueAnimator)
    Button btValueAnimator;
    @BindView(R.id.bt_objectAnimator)
    Button btObjectAnimator;
    @BindView(R.id.bt_colorAnimator)
    Button btColorAnimator;
    @BindView(R.id.bt_setAnimator)
    Button btSetAnimator;
    @BindView(R.id.tv_1)
    TextView tv1;
    @BindView(R.id.menu)
    Button menu;
    @BindView(R.id.item1)
    Button item1;
    @BindView(R.id.item2)
    Button item2;
    @BindView(R.id.item3)
    Button item3;
    @BindView(R.id.item4)
    Button item4;
    @BindView(R.id.item5)
    Button item5;
    private boolean mIsMenuOpen = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xml_set);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_valueAnimator, R.id.bt_objectAnimator, R.id.bt_colorAnimator, R.id.bt_setAnimator,
            R.id.menu, R.id.item1, R.id.item2, R.id.item3, R.id.item4, R.id.item5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_valueAnimator:
                valueAnimator();
                break;
            case R.id.bt_objectAnimator:
                objectAnimator();
                break;
            case R.id.bt_colorAnimator:
                colorAnimator();
                break;
            case R.id.bt_setAnimator:
                setAnimator();
                break;
            case R.id.menu:
                doAnimatorSet();
                break;
            case R.id.item1:
                Toast.makeText(this, "你点击了item1" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.item2:
                Toast.makeText(this, "你点击了item2" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.item3:
                Toast.makeText(this, "你点击了item3" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.item4:
                Toast.makeText(this, "你点击了item4" , Toast.LENGTH_SHORT).show();
                break;
            case R.id.item5:
                Toast.makeText(this, "你点击了item5" , Toast.LENGTH_SHORT).show();
                break;
        }
    }

    /**
     * 开篇示例——AnimatorSet应用
     * 我们先来分析下这个效果，在用户点击按钮时，把菜单弹出来；弹出来的时候，动画一点从小变到大，一边透明度从0变到1.关键问题是，怎么样实现各个菜单以当前点击按钮为圆心排列在圆形上；
     * 在开始写代码之前，我们先讲讲，如何根据圆半径来定位每个图片的位置，先看下图：
     *
     * 在上面的图中，我们可以清晰的看到，假如当前菜单与Y轴的夹角是a度，那么这个菜单所移动的X轴距离为radius * sin(a);Y轴的移动距离为radius * cos(a);
     这是非常简单的三角函数的计算。想必这块大家理解起来是没有问题的。
     那么第一个问题来了，这个夹角a是多少度呢？
     很显然，这里所有的菜单的夹角之和是90度。我们总共有五个菜单项，把90度夹角做了4等分。所以夹角a的度数为90/4 = 22;所以这五个菜单，第一个菜单的夹角是0度，第二个菜单的夹角是22度，第三个菜单的夹角是22*2度，第四个夹角是22*3度，第五个的夹角是22*4度.
     我们假设index表示当前菜单的位置索引，从0开始，即第一个菜单的索引是0，第二个菜单的索引是1，第三个菜单的索引是2……，而当前的菜单与y轴的夹角恰好占了22度的index份；所以当前菜单与Y轴的夹角为22 * index;这个公式非常重要，大家在这里一定要理解，下面代码中会用到。
     第二个问题来了，如何求对应角度的sin,cos值
     想必很多同学都知道，JAVA中有一个Math类，它其中有四个函数:
     /**
     * 求对应弧度的正弦值
     *double sin(double d)
    /**
     * 求对应弧度的余弦值
     *double cos(double d)
    /**
     * 求对应弧度的正切值
     *double tan(double d)
     * 这里要非常注意的是，这三个函数的输入参数不是度数，而是对应的度数的弧度值！
     角度与其对应的弧度值对应关系如下：

     在Math中有两种方法可以得到弧度值：
     第一种方法：在Math中，Math.PI不仅代表圆周率π，也代表180度角所对应的弧度值。所以Math.sin(Math.PI)就表示180度的正弦值，Math.sin(Math.PI/2)就表示90度的正弦值。
     第二种方法：根据度数获得弧度值
     在Math中也提供了一个方法
     /**
     * Math中根据度数得到弧度值的函数
     * double toRadians(double angdeg)
     * 这个函数就是Math中根据度数得到弧度值的函数，参数angdeg指度数，返回值是对应的弧度值。
     * 所以比如我们要求22度对应的弧度值就是Math.toRadians(22)；所以如果我们要求22度所对应的正弦值就是Math.sin(Math.toRadians(22))
     * 在讲了如何根据半径求得每个菜单项的位置之后，我们来看看示例工程的代码。
     *
     */
    /**
     * ——————————————————存在问题,但是自己在做的时候没有问题，不管了—-——————————————————————————
     * 到这里，这个路径动画就讲解完了，但如果多使用几次就会发现，这里的动画存在问题：当菜单收回以后，再点击菜单展开时所在的位置，仍然会响应点击事件：
     * 问题在于，在将菜单缩回时：

     set.playTogether(
     …………
     ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
     ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f));

     在setScaleX\setScaleY动画时，由于我们将控件缩小到0，但是动画在控件被缩小到0以后，
     对它所做的属性动画，并不会实际改变控件的位置，这就像视图动画一样，虽然动画把控件移走了，
     但是响应点击事件的位置仍是在原来位置。这是Android的一个bug；
     所以，为了解决这个问题，可以有两种解决方案；
     方案一：
     第一个方案比较简单，在缩小时，既然在缩小到0以后，存在bug，那我们只需要不把控件缩小到0就可以了：
     set.playTogether(
     ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
     ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
     ObjectAnimator.ofFloat(view, "scaleX", 1f, 0.1f),
     ObjectAnimator.ofFloat(view, "scaleY", 1f, 0.1f),
     ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));
     这里缩小到0.1大小，视觉上看不出来，也正好规避了缩小到0以后不会实际改变控件位置的问题。

     方案二：
     方案一是在缩小时做补救，而另一个方案而是监听动画状态，在动画结束时，将控件放大即可。
     set.addListener(new Animator.AnimatorListener() {
    @Override
    public void onAnimationEnd(Animator animation) {
    view.setScaleX(1.0f);
    view.setScaleY(1.0f);
    }
    //其它监听函数只重写，不实现，代码省略
    …………
    });
     */


    private void doAnimatorSet() {
        if (!mIsMenuOpen) {
            mIsMenuOpen = true;
            item1.setClickable(true);
            item2.setClickable(true);
            item3.setClickable(true);
            item4.setClickable(true);
            item5.setClickable(true);
            doAnimateOpen(item1, 0, 5, 300);
            doAnimateOpen(item2, 1, 5, 300);
            doAnimateOpen(item3, 2, 5, 300);
            doAnimateOpen(item4, 3, 5, 300);
            doAnimateOpen(item5, 4, 5, 300);
        } else {
            mIsMenuOpen = false;
            item1.setClickable(false);
            item2.setClickable(false);
            item3.setClickable(false);
            item4.setClickable(false);
            item5.setClickable(false);
            doAnimateClose(item1, 0, 5, 300);
            doAnimateClose(item2, 1, 5, 300);
            doAnimateClose(item3, 2, 5, 300);
            doAnimateClose(item4, 3, 5, 300);
            doAnimateClose(item5, 4, 5, 300);
        }
    }

    /**
     * doAnimateClose()——收回菜单
     * @param view
     * @param index
     * @param total
     * @param radius
     */
    /**
     * 这段代码是很容易理解的，但我在这里求degree的时候，换了一种方法：
     * double degree = Math.PI * index / ((total - 1) * 2);
     * 其实这句代码与上面的double degree = Math.toRadians(90)/(total - 1) * index;
     * 是同一个意思。还记得，我们在讲原理的时候，
     * 提到过Math.PI不仅表示圆周率，也表示180度所对应的弧度。
     所以Math.toRadians(90)就等于Math.PI/2，这样，这两个公式就是完全一样的了。
     */
    private void doAnimateClose(View view, int index, int total,
                                int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        // Math.PI不仅表示圆周率，也表示180度所对应的弧度
        double degree = Math.PI * index / ((total - 1) * 2);
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));
        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", translationX, 0),
                ObjectAnimator.ofFloat(view, "translationY", translationY, 0),
                ObjectAnimator.ofFloat(view, "scaleX", 1f, 0f),
                ObjectAnimator.ofFloat(view, "scaleY", 1f, 0f),
                ObjectAnimator.ofFloat(view, "alpha", 1f, 0f));

        set.setDuration(500).start();
    }

    /**
     * doAnimateOpen()——弹出菜单
     * @param view
     * @param index
     * @param total
     * @param radius
     */
    /**
     * 这里构造的动画是利用translationX和translationY将控件移动到指定位置。
     * 同时，scaleX、scaleY、alpha都从0变到1；最关键的部分是如何得到translationX和translationY的值。
     * 在这部分的开篇，我们首先讲了，如何讲了
     * translationX = radius * sin(a)
     * translationY = radius * cos(a)
     * 我们来看看在代码中如何去做的：
     double degree = Math.toRadians(90)/(total - 1) * index;
     int translationX = -(int) (radius * Math.sin(degree));
     int translationY = -(int) (radius * Math.cos(degree));
     首先，是求得两个菜单的夹角，即公式里的a值。Math.toRadians(90)/(total - 1)表示90度被分成了total-1份，其中每一份的弧度值；
     我们前面讲过，假设每一份的弧度值是22度，那么当前菜单与Y轴的夹角就是22 * index度。这里类似，当前菜单与y轴的弧度值就是Math.toRadians(90)/(total - 1) * index
     在求得夹角以后，直接利用translationX = radius * sin(a)就可以得到x轴的移动距离，但又因为菜单是向左移动了translationX距离。所以根据坐标系向下为正，向右为正的原则。这里的移动距离translationX应该是负值。我们需要的translationY，因为是向上移动，所以也是负值：

     int translationX = -(int) (radius * Math.sin(degree));
     int translationY = -(int) (radius * Math.cos(degree));

     在理解了弹出的部分之后，收回的代码就好理解了
     */
    private void doAnimateOpen(View view, int index, int total, int radius) {
        if (view.getVisibility() != View.VISIBLE) {
            view.setVisibility(View.VISIBLE);
        }
        // Math.toRadians(90)/(total - 1)表示90度被分成了total-1份，
        // 其中每一份的弧度值；
        double degree = Math.toRadians(90)/(total - 1) * index;
        int translationX = -(int) (radius * Math.sin(degree));
        int translationY = -(int) (radius * Math.cos(degree));

        AnimatorSet set = new AnimatorSet();
        //包含平移、缩放和透明度动画
        set.playTogether(
                ObjectAnimator.ofFloat(view, "translationX", 0, translationX),
                ObjectAnimator.ofFloat(view, "translationY", 0, translationY),
                ObjectAnimator.ofFloat(view, "scaleX", 0f, 1f),
                ObjectAnimator.ofFloat(view, "scaleY", 0f, 1f),
                ObjectAnimator.ofFloat(view, "alpha", 0f, 1));
        //动画周期为500ms
        set.setDuration(500).start();
    }

    /**
     * set
     * （1）字段意义及使用方法
     * 这个是AnimatorSet所对应的标签。它只有一个属性：
     * <set
     * android:ordering=["together" | "sequentially"]>
     * android:ordering：表示动画开始顺序。together表示同时开始动画，sequentially表示逐个开始动画；
     * 加载方式为：
     * 同样是通过loadAnimator加载动画，然后将其强转为AnimatorSet；
     * 这里有两个objectAnimator动画，一个改变值x坐标，一个改变值y坐标；取值分别为0-400和0-300；
     * 然后在代码中加载：
     */
    private void setAnimator() {
        AnimatorSet set = (AnimatorSet) AnimatorInflater.loadAnimator(this,
                R.animator.set_animator);
        set.setTarget(tv1);
        set.start();
    }

    /**
     * 使用color属性示例
     * 这里我们就演示一下如何使用android:valueFrom、android:valueTo的color属性用法，
     * 我们建立一个objectAnimator的动画文件：
     * 设置属性名为BackgroundColor，即对应的set函数为setBackgroundColor(int color);
     * android:valueFrom和android:valueTo的取值都为颜色值，即#开头的八位数值；即ARGB值；
     * 使用方法不变：
     * 从效果图中可以看到，虽然实现了颜色变化，但会一直闪；
     * 所以直接利用xml实现的动画效果并不怎么好，所以如果想要实现颜色变化，还是利用代码来实现吧。
     * 前面的文章中，我们已经讲过如何利用ValueAnimator和ObjectAnimator来实现颜色过渡和原理了。大家可以翻看下。
     */
    private void colorAnimator() {
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,
                R.animator.color_animator);
        animator.setTarget(tv1);
        animator.start();
    }

    /**
     * objectAnimator
     * （1）字段意义及使用方法
     * <p>
     * 同样，我们先来看看它的所有标签的意义：
     * <p>
     * <objectAnimator
     * android:propertyName="string"
     * android:duration="int"
     * android:valueFrom="float | int | color"
     * android:valueTo="float | int | color"
     * android:startOffset="int"
     * android:repeatCount="int"
     * android:repeatMode=["repeat" | "reverse"]
     * android:valueType=["intType" | "floatType"]
     * android:interpolator=["@android:interpolator/XXX"]/>
     * 意义：
     * - android:propertyName：对应属性名，即ObjectAnimator所需要操作的属性名。
     * 其它字段的意义与animator的意义与取值是一样的，下面再重新列举一下。
     * - android:duration:每次动画播放的时长
     * - android:valueFrom:初始动化值；取值范围为float,int和color；
     * - android:valueTo：动画结束值；取值范围同样是float,int和color这三种类型的值；
     * - android:startOffset：动画激活延时；对应代码中的startDelay(long delay)函数；
     * - android:repeatCount：动画重复次数
     * - android:repeatMode：动画重复模式，取值为repeat和reverse；repeat表示正序重播，reverse表示倒序重播
     * - android:valueType：表示参数值类型，取值为intType和floatType；与android:valueFrom、android:valueTo相对应。如果这里的取值为intType，那么android:valueFrom、android:valueTo的值也就要对应的是int类型的数值。如果这里的数值是floatType，那么android:valueFrom、android:valueTo的值也要对应的设置为float类型的值。非常注意的是，如果android:valueFrom、android:valueTo的值设置为color类型的值，那么不需要设置这个参数；
     * - android:interpolator:设置加速器；
     * <p>
     * 下面我们就看看如何使用：
     * <p>
     * 同样是使用loadAnimator加载对应的xml动画。然后使用animator.setTarget(mTv1);绑定上动画目标。
     * 因为在xml中，没有设置目标的参数，所以我们必须通过代码将目标控件与动画绑定。
     * （2）、使用示例
     * 在这个xml中，我们定义了更改属性为TranslationY，即改变纵坐标；时长为2000毫秒。从0变到400；使用的插值器是加速插值器，对应的值类型为float类型。
     * 有些同学可能会问，为什么是float类型，因为setTranslationY函数的参数是float类型的，声明如下：
     * public void setTranslationY(float translationY)
     * 最后是设置重复次数和重复模式。将动画激活延时设置为2000毫秒；
     * 然后是加载动画：
     */
    private void objectAnimator() {
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this,
                R.animator.object_animator);
        animator.setTarget(tv1);
        animator.start();
    }


    /**
     * animator
     * (1)、animator所有字段及意义
     * <p>
     * 下面是完整的animator所有的字段及取值范围：
     * <p>
     * <animator
     * android:duration="int"
     * android:valueFrom="float | int | color"
     * android:valueTo="float | int | color"
     * android:startOffset="int"
     * android:repeatCount="int"
     * android:repeatMode=["repeat" | "reverse"]
     * android:valueType=["intType" | "floatType"]
     * android:interpolator=["@android:interpolator/XXX"]/>
     * <p>
     * android:duration:每次动画播放的时长
     * android:valueFrom:初始动化值；取值范围为float,int和color，如果取值为float对应的值样式应该为89.0，取值为Int时，对应的值样式为：89;当取值为clolor时，对应的值样式为 #333333;
     * android:valueTo：动画结束值；取值范围同样是float,int和color这三种类型的值；
     * android:startOffset：动画激活延时；对应代码中的startDelay(long delay)函数；
     * android:repeatCount：动画重复次数
     * android:repeatMode：动画重复模式，取值为repeat和reverse；repeat表示正序重播，reverse表示倒序重播
     * android:valueType：表示参数值类型，取值为intType和floatType；与android:valueFrom、android:valueTo相对应。如果这里的取值为intType，那么android:valueFrom、android:valueTo的值也就要对应的是int类型的数值。如果这里的数值是floatType，那么android:valueFrom、android:valueTo的值也要对应的设置为float类型的值。非常注意的是，如果android:valueFrom、android:valueTo的值设置为color类型的值，那么不需要设置这个参数；
     * android:interpolator:设置加速器；有关系统加速器所对应的xml值对照表如下：
     * （2）、将xml加载到程序中
     * 在定义了一个xml后，我们需要将其加载到程序中，使用的方法如下：
     * <p>
     * ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(MyActivity.this,R.animator.animator);
     * valueAnimator.start();
     * 通过loadAnimator将animator动画的xml文件，加载进来，根据类型进行强转。
     */
    /**
     * 在效果图中可以看到，我们生成了一个动画，动态了改变了当前控件的坐标位置。
     * 我们先在res/animator文件夹下生成一个动画的xml文件：
     * 在这里，我们将valueType设置为intType，所以对应的android:valueFrom、android:valueTo都必须是int类型的值；插值器使用bounce回弹插值器
     * 由于我们xml中根属性是<animator/>所以它对应的是ValueAnimator，所以在加载后，将其强转为valueAnimator；然后对其添加控件监听。在监听时，
     * 动态改变当前textview的位置。有关这些代码就不再细讲了，如果看到前面的文章，这段代码应该是无比熟悉的。
     * 最后的效果就是开头时所演示的效果。
     * 源码在文章底部给出
     * 有关android:valueFrom、android:valueTo取值为color属性时的用法，这里就不讲了，
     * 因为在xml中使用color属性，我也不会用；尝试了下，不成功，也不想尝试了，没什么太大意义，下面我们会讲如何在objectanimator中使用color属性；
     */
    private void valueAnimator() {
        ValueAnimator valueAnimator = (ValueAnimator) AnimatorInflater.loadAnimator(this,
                R.animator.animator);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int offset = (int) animation.getAnimatedValue();
                tv1.layout(offset, offset, tv1.getWidth() + offset, tv1.getHeight() + offset);
            }
        });
        valueAnimator.start();
    }

}
