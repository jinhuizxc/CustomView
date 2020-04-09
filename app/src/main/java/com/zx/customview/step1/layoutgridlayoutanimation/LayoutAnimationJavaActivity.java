package com.zx.customview.step1.layoutgridlayoutanimation;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.zx.customview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 * <p>
 * LayoutAnimation的代码实现——LayoutAnimationController
 * 上面我们讲过了LayoutAnimation的xml实现方式，下面来看看LayoutAnimation的代码实现方式。
 * 首先，xml中layoutAnimation标签所对应的类为LayoutAnimationController；
 * 它有两个构造函数:
 * public LayoutAnimationController(Animation animation)
 * public LayoutAnimationController(Animation animation, float delay)
 * 很容易理解，animation对应标签中的android:animation属性，delay对应标签中的android:delay属性。
 * LayoutAnimationController的函数如下：
 * /**
 * 设置animation动画
 * public void setAnimation(Animation animation)
 * <p>
 * 设置单个item开始动画延时
 * public void setDelay(float delay)
 * <p>
 * 设置viewGroup中控件开始动画顺序，取值为ORDER_NORMAL、ORDER_REVERSE、ORDER_RANDOM
 * public void setOrder(int order)
 */

public class LayoutAnimationJavaActivity extends AppCompatActivity {

    @BindView(R.id.addlist)
    Button addlist;
    @BindView(R.id.listview)
    ListView listview;

    private ArrayAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutanimationjava);
        ButterKnife.bind(this);
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData());
        listview.setAdapter(mAdapter);

        /**
         * 布局与xml的实现方式一样，唯一不同的是Listview中没有定义android:layoutAnimation=”@anim/layout_animation”属性，
         * 因为所有有关LayoutAnimation的部分都是利用代码来实现的；
         * 代码设置通过加载XML动画设置文件来创建一个Animation对象；
         *
         * 这段代码中，在填充listview的代码都是与xml的实现方式相同的，关键是填充后，
         * 开始给listview设置LayoutAnimationController,代码如下：
         */

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);   //得到一个LayoutAnimationController对象；
        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
        controller.setDelay(0.3f);   //为ListView设置LayoutAnimationController属性；
        listview.setLayoutAnimation(controller);
        listview.startLayoutAnimation();
    }


    @OnClick(R.id.addlist)
    public void onViewClicked() {
        mAdapter.addAll(getData());
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        data.add("测试数据1");
        data.add("测试数据2");
        data.add("测试数据3");
        data.add("测试数据4");
        return data;
    }
}
