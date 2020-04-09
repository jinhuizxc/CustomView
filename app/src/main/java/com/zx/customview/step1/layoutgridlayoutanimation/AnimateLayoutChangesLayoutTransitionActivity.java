package com.zx.customview.step1.layoutgridlayoutanimation;

import android.animation.Animator;
import android.animation.Keyframe;
import android.animation.LayoutTransition;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.zx.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 * <p>
 * 前篇给大家讲了LayoutAnimation的知识，LayoutAnimation虽能实现ViewGroup的进入动画，
 * 但只能在创建时有效。在创建后，再往里添加控件就不会再有动画。在API 11后，
 * 又添加了两个能实现在创建后添加控件仍能应用动画的方法，
 * 分别是android:animateLayoutChanges属性和LayoutTransition类。这篇文章就来简单说一下他们的用法。
 * 由于他们的API 等级必须>=11，API等级稍高，且存在较多问题，并不建议读者使用，本篇只讲解具体用法，不做深究.
 * <p>
 * android:animateLayoutChanges属性
 * 在API 11之后，Android为了支持ViewGroup类控件，
 * 在添加和移除其中控件时自动添加动画，为我们提供了一个非常简单的属性：android:animateLayoutChanges=[true/false],所有派生自ViewGroup的控件都具有此属性，
 * 只要在XML中添加上这个属性，就能实现添加/删除其中控件时，带有默认动画了。
 * 我们来看下这次的效果图：
 * <p>
 * 布局代码很简单，两个按钮，最底部是一个LinearLayout做为动态添加btn的container，
 * 注意，我们给它添加了android:animateLayoutChanges="true"也就是说，它内部的控件在添加和删除时，是会带有默认动画。
 */


/**
 * LayoutTransaction
 * <p>
 * 上面虽然在ViewGroup类控件XML中仅添加一行android:animateLayoutChanges=[true]即可实现内部控件添加删除时都加上动画效果。但却只能使用默认动画效果，而无法自定义动画。
 * 为了能让我们自定义动画，谷歌在API 11时，同时为我们引入了一个类LayoutTransaction。
 * 要使用LayoutTransaction是非常容易的，只需要三步：
 * <p>
 * 第一步：创建实例
 * LayoutTransaction transitioner = new LayoutTransition();
 * <p>
 * 第二步：创建动画并设置
 * ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotation", 0f, 90f, 0f);
 * transitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
 * <p>
 * 第三步：将LayoutTransaction设置进ViewGroup
 * linearLayout.setLayoutTransition(mTransitioner);
 * <p>
 * 其中第三步中，在API 11之后，所有派生自ViewGroup类，比如LinearLayout，FrameLayout，RelativeLayout等，都具有一个专门用来设置LayoutTransition的方法:
 * public void setLayoutTransition(LayoutTransition transition)
 * <p>
 * 在第二步中，transitioner.setAnimator设置动画的函数声明为：
 * public void setAnimator(int transitionType, Animator animator)
 * 其中
 * 第一个参数int transitionType：表示当前应用动画的对象范围，取值有：
 * <p>
 * APPEARING —— 元素在容器中出现时所定义的动画。
 * DISAPPEARING —— 元素在容器中消失时所定义的动画。
 * CHANGE_APPEARING —— 由于容器中要显现一个新的元素，其它需要变化的元素所应用的动画
 * CHANGE_DISAPPEARING —— 当容器中某个元素消失，其它需要变化的元素所应用的动画
 * <p>
 * 这几个具体的意义，我们后面会具体来讲。
 * 第二个参数Animator animator：表示当前所选范围的控件所使用的动画。
 * <p>
 * LayoutTransition.APPEARING与LayoutTransition.DISAPPEARING示例
 * 大家可以看到，在新增一个btn时，这个新增的btn会有一个绕Y轴旋转360度的动画。这个就是LayoutTransition.APPEARING所对应的当一个控件出现时所对应的动画。
 * 当我们从容器中移除一个控件时，这个被移除的控件会绕Z轴旋转90度后，再消失。这个就是LayoutTransition.DISAPPEARING在一个控件被移除时,此被移除的控件所对应的动画。
 * 这样大家就理解了，LayoutTransition.APPEARING和LayoutTransition.DISAPPEARING的意义。下面我们就来看看代码吧。
 * 这个示例也是建立在上个android:animateLayoutChanges属性所对应示例的基础上的，所以框架部分是一样的，仅列出代码，不再多讲，只讲关键部分
 * <p>
 * <p>
 * 布局代码与上面一样，但唯一不同的是在LinearLayout中没有android:animateLayoutChanges="true"
 */


public class AnimateLayoutChangesLayoutTransitionActivity extends AppCompatActivity {

    private static final String TAG = AnimateLayoutChangesLayoutTransitionActivity.class.getSimpleName();
    @BindView(R.id.add_btn)
    Button addBtn;
    @BindView(R.id.remove_btn)
    Button removeBtn;
    @BindView(R.id.layoutTransitionGroup)
    LinearLayout layoutTransitionGroup;

    private int i = 0;
    private LayoutTransition mTransitioner;

    /**
     * 不能点太快,不然会报openGL内存溢出错误，我发现的是按钮变形，不可恢复的
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animatelayoutchangeslayouttransition);
        ButterKnife.bind(this);

        /**
         *
         * LayoutTransition.APPEARING与LayoutTransition.DISAPPEARING示例
         *
         *
         * 同样是在点击“添加控件”按钮时，向LinearLayout中动态添加一个控件，
         * 在点击“移除控件”按钮时，将LinearLayout中第一个控件给移除。
         但是非常注意的是我们的LayoutTransition是在OnCreate中设置的，
         也就是说是在LinearLayout创建时就给它定义好控件的入场动画和出场动画的，定义代码如下
         */
//        mTransitioner = new LayoutTransition();
//        //入场动画:view在这个容器中消失时触发的动画
//        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 0f, 360f,0f);
//        mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);
//
//        //出场动画:view显示时的动画
//        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotation", 0f, 90f, 0f);
//        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);
//
//        layoutTransitionGroup.setLayoutTransition(mTransitioner);

        /**
         * LayoutTransition.CHANGE_APPEARING与LayoutTransition.CHANGE_DISAPPEARING
         * 在这个效果图中，在添加控件时，除了被添加控件本身的入场动画以外，其它需要移动位置的控件，在移动位置时，也被添加上了动画（left点位移动画），这些除了被添加控件以外的其它需要移动位置的控件组合，所对应的动画就是LayoutTransition.CHANGE_APPEARING
         同样，在移除一个控件时，因为移除了一个控件，而其它所有需要改变位置的控件组合所对应的动画就是LayoutTransition.CHANGE_DISAPPEARING，这里LayoutTransition.CHANGE_DISAPPEARING所对应的动画是
         《 Animation动画详解(八)——PropertyValuesHolder与Keyframe》的响铃效果。
         （1）、LayoutTransition.CHANGE_APPEARING实现
         我们这里先看看LayoutTransition.CHANGE_APPEARING所对应的完整代码

         入场动画((LayoutTransition.APPEARING)和出场动画(LayoutTransition.DISAPPEARING)我们已经讲过了，下面我们主要看看入场时，其它控件位移动画的部分：
         这里有几点注意事项：
         1、LayoutTransition.CHANGE_APPEARING和LayoutTransition.CHANGE_DISAPPEARING必须使用PropertyValuesHolder所构造的动画才会有效果，不然无效！
         也就是说使用ObjectAnimator构造的动画，在这里是不会有效果的！
         2、在构造PropertyValuesHolder动画时，”left”、”top”属性的变动是必写的。如果不需要变动，则直接写为：
         3、在构造PropertyValuesHolder时，所使用的ofInt,ofFloat中的参数值，第一个值和最后一个值必须相同，不然此属性所对应的的动画将被放弃，在此属性值上将不会有效果；
         比如，这里ofInt(“left”,0,100,0)第一个值和最后一个值都是0，所以这里会有效果的，如果我们改为ofInt(“left”,0,100);那么由于首尾值不一致，则将被视为无效参数，将不会有效果！
         4、在构造PropertyValuesHolder时，所使用的ofInt,ofFloat中，如果所有参数值都相同，也将不会有动画效果。
         比如：
         *
         */

        mTransitioner = new LayoutTransition();
        //入场动画:view在这个容器中消失时触发的动画
        ObjectAnimator animIn = ObjectAnimator.ofFloat(null, "rotationY", 0f, 360f,0f);
        mTransitioner.setAnimator(LayoutTransition.APPEARING, animIn);

        //出场动画:view显示时的动画
        ObjectAnimator animOut = ObjectAnimator.ofFloat(null, "rotation", 0f, 90f, 0f);
        mTransitioner.setAnimator(LayoutTransition.DISAPPEARING, animOut);


        /**
         * LayoutTransition.CHANGE_APPEARING动画
         */
        PropertyValuesHolder pvhLeft = PropertyValuesHolder.ofInt("left",0,100,0);
        PropertyValuesHolder pvhTop = PropertyValuesHolder.ofInt("top",1,1);
        //必须第一个值与最后一值相同才会有效果,不然没有效果
        PropertyValuesHolder pvhScaleX = PropertyValuesHolder.ofFloat("ScaleX",1f,9f,1f);
        Animator changeAppearAnimator
                = ObjectAnimator.ofPropertyValuesHolder(layoutTransitionGroup, pvhLeft,pvhTop,pvhScaleX);
        mTransitioner.setAnimator(LayoutTransition.CHANGE_APPEARING,changeAppearAnimator);


        /**
         * LayoutTransition.CHANGE_DISAPPEARING动画
         */
        PropertyValuesHolder outLeft = PropertyValuesHolder.ofInt("left",0,0);
        PropertyValuesHolder outTop = PropertyValuesHolder.ofInt("top",0,0);

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
        PropertyValuesHolder mPropertyValuesHolder = PropertyValuesHolder.ofKeyframe("rotation",frame0,frame1,frame2,frame3,frame4,frame5,frame6,frame7,frame8,frame9,frame10);

        ObjectAnimator mObjectAnimatorChangeDisAppearing = ObjectAnimator.ofPropertyValuesHolder(this, outLeft,outTop,mPropertyValuesHolder);
        mTransitioner.setAnimator(LayoutTransition.CHANGE_DISAPPEARING, mObjectAnimatorChangeDisAppearing);

        //设置单个item间的动画间隔
        mTransitioner.setStagger(LayoutTransition.CHANGE_APPEARING, 30);


        mTransitioner.addTransitionListener(new LayoutTransition.TransitionListener() {
            @Override
            public void startTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {

                Log.d("qijian","start:"+"transitionType:"+transitionType +"count:"+container.getChildCount() + "view:"+view.getClass().getName());
            }

            @Override
            public void endTransition(LayoutTransition transition, ViewGroup container, View view, int transitionType) {
                Log.d("qijian","end:"+"transitionType:"+transitionType +"count:"+container.getChildCount() + "view:"+view.getClass().getName());
            }
        });

        layoutTransitionGroup.setLayoutTransition(mTransitioner);

    }

    /**
     * 代码很简单就不再细讲了。
     * 由上面的效果图可见，我们只需要在viewGroup的XML中添加一行代码android:animateLayoutChanges=[true]即可实现内部控件添加删除时都加上动画效果。
     * 下面我们来做下对比，如果把上面LinearLayout中的android:animateLayoutChanges=[true]给去掉的效果是怎样的？大家来看下原始添加控件是怎样的，就知道默认动画效果是什么了。
     * 在没加android:animateLayoutChanges=true时：
     * 可见，在添加和删除控件时是没有任何动画的。经过对比就可知道，
     * 默认的进入动画就是向下部控件下移，然后新添控件透明度从0到1显示出来。
     * 默认的退出动画是控件透明度从1变到0消失，下部控件上移。
     *
     * @param view
     */
    @OnClick({R.id.add_btn, R.id.remove_btn})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_btn:
                addButtonView();
                break;
            case R.id.remove_btn:
                removeButtonView();
                break;
        }
    }


    /**
     * 移除按钮
     */
    private void removeButtonView() {
        if (i > 0) {
            layoutTransitionGroup.removeViewAt(0);
            i--;
        } else {
            i = 0;
        }

    }

    /**
     * 添加按钮
     */
    private void addButtonView() {
        i++;
        Button button = new Button(this);
        button.setText("button" + i);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        button.setLayoutParams(params);
        layoutTransitionGroup.addView(button, 0);
    }
}
