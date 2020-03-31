package com.example.customview.step1.layoutgridlayoutanimation;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.example.customview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 * <p>
 * LayoutAnimation的xml实现——layoutAnimation标签
 * 这部分，我们就来看看layoutAnimation标签的用法，要使用layoutAnimation只需要两步：
 * 第一：定义一个layoutAnimation的animation文件，如：(anim/layout_animation.xml)
 * 有关它的具体意义，我们后面会讲。
 第二步：在viewGroup类型的控件中，添加android:layoutAnimation=”@anim/layout_animation”，如：

 从效果图中，可以看出两点：
 - listview中各个item从左至右滑入位置
 - 动画仅在第一次创建时有用，后期加入的数据，将不会再有动画（这个问题最后再讲）

 这里添加的layoutAnimation，与上面的layout_animation.xml文件一样：
 */

public class LayoutAnimationActivity extends AppCompatActivity {


    private static final String TAG = "LayoutAnimationActivity";
    @BindView(R.id.addlist)
    Button addlist;
    @BindView(R.id.listview)
    ListView listview;

    private ArrayAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutanimation);
        ButterKnife.bind(this);
        /**
         * 给listview添加layoutAimation
         * layoutAnimation只会在布局第一次出现时,加载其中item时使用动画,在后面就不会再有动画了
         * 如果想让每个item进入时,都有动画,可以在构造Adapter时在getView中,对每个convertview添加动画
         */
        mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, getData());
        listview.setAdapter(mAdapter);
    }

    @OnClick(R.id.addlist)
    public void onViewClicked() {
        mAdapter.addAll(getData());
        /**
         * 通过代码来实现layouAnimation,到main.xml中打开对应的代码
         */
        //代码设置通过加载XML动画设置文件来创建一个Animation对象；
//        Animation animation= AnimationUtils.loadAnimation(this,R.anim.slide_in_left);   //得到一个LayoutAnimationController对象；
//        LayoutAnimationController controller = new LayoutAnimationController(animation);   //设置控件显示的顺序；
//        controller.setOrder(LayoutAnimationController.ORDER_REVERSE);   //设置控件显示间隔时间；
//        controller.setDelay(0.3f);   //为ListView设置LayoutAnimationController属性；
//        mListView.setLayoutAnimation(controller);
//        mListView.startLayoutAnimation();
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
