package com.zx.customview.step1.layoutgridlayoutanimation.listviewitem;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.zx.customview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 * <p>
 * 前面两篇我们讲解了使用layoutAnimation和LayoutTransition实现ViewGroup中Item加载动画的方法，但他们都各自存在问题：
 * layoutAnimation虽然是API 1中就已经引入，但只能在动画初次创建时才能使用指定动画。控件创建以后，再往ViewGroup里加Item就不会再有动画。这显然是不合适的！
 * LayoutTransition能够实现无论何时往ViewGroup中添加控件都可以给其中控件使用动画。但最大的问题是，它的API等级是11。而且也没有兼容包可供我们使用这个函数。
 * 这样问题就来了，如果我们想在兼容API 8以上的机型，完成ListView中各个Item进入时都添加动画，这要怎么来做呢？
 * 今天我们要完成的效果图如下：
 * 从效果图中可以看到，当每个Item进入的时候，都添加了动画。前面我们说了layoutAnimation和LayoutTransition所存在的问题，那抛开这两个函数，我们要如何实现Item进入动画呢？
 别忘了，ListView在得到每个Item时会调用BaseAdapter的getView方法！getView中每一个convertView就是当前要显示的Item所对应的View，所以我们直接对convertView添加动画不就好了。
 上面的原理理解起来并不难，下面我们就看看如何实现的吧。

 */

public class ListViewItemActivity extends AppCompatActivity {

    @BindView(R.id.list)
    ListView list;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listviewitem);
        ButterKnife.bind(this);

        List<Drawable> drawables = new ArrayList<>();
        drawables.add(getResources().getDrawable(R.drawable.pic1));
        drawables.add(getResources().getDrawable(R.drawable.pic2));
        drawables.add(getResources().getDrawable(R.drawable.pic3));
        drawables.add(getResources().getDrawable(R.drawable.pic4));
        drawables.add(getResources().getDrawable(R.drawable.pic5));
        drawables.add(getResources().getDrawable(R.drawable.pic6));
        drawables.add(getResources().getDrawable(R.drawable.pic7));
        drawables.add(getResources().getDrawable(R.drawable.pic8));
        drawables.add(getResources().getDrawable(R.drawable.pic9));


        ListView listView = (ListView)findViewById(R.id.list);
        final ListAdapter adapter = new ListAdapter(this,listView,drawables,300);
        listView.setAdapter(adapter);
        /**
         * 我们知道本身的BaseAdapter并没有使用getItem(int position)函数，重写这个函数是为了让我们在BaseAdapter实例中，
         * 可以通过getItem来获取我们想要的实例(类似下面这样)：
         */
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //关键在这里哦
                Drawable drawable = (Drawable) adapter.getItem(position);
            }
        });
    }
}
