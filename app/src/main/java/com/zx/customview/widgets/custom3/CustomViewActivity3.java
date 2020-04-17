package com.zx.customview.widgets.custom3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.zx.customview.R;

/**
 * 使用自定义组合控件的好处？
 * 我们在项目开发中经常会遇见很多相似或者相同的布局，比如APP的标题栏，
 * 我们从三种方式实现标题栏来对比自定义组件带来的好处，毕竟好的东西还是以提高开发效率，降低开发成本为导向的。
 *
 * 1.）第一种方式：直接在每个xml布局中写相同的标题栏布局代码
 * 这种方式没有任何布局复用的概念，同时也让当前的布局变得臃肿难以维护，开发效率低下，
 * 而且这个还需要要求每个开发人员必须细心否则有可能会做出参差不齐的标题栏，所以这种方式是最不推荐使用的。
 *
 * 2.）第二种方式：使用include标签
 * 然后在需要的地方通过include标签实现引用
 * 通过上面的布局代码，我们可以使用上面这种方式确实实现了布局的复用，而且也避免了开发人员开发出参差不齐标题栏的问题，但是同时也引入了新的问题，比如更加降低了开发效率，加大了开发成本，问题就在我们该如何为每个布局文件定义标题栏？只有通过代码的方式来设置标题问题，左右按钮等其他的属性，导致布局属性和Activity代码耦合性比较高，所以这种方式也不是推荐的方式。
 *
 * 3.）第三种方式：通过自定义组合控件
 * 这里先不具体介绍如何实现一个自定义组合控件，这里先介绍一下自定义组合控件带来的好处。
 *
 *     提高布局文件开发效率
 *     降低布局文件维护成本
 *     降低布局文件和Activity代码耦合性
 *     容易扩展
 *     简单易用
 *
 */
public class CustomViewActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view3);
    }
}
