package com.example.customview.step1.layoutgridlayoutanimation;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 * <p>
 * 前几篇给大家讲述了如何针对某一个控件应用动画，这篇将给大家讲解如何给容器中的控件应用统一动画。即在容器中控件出现时，
 * 不必为每个控件添加进入动画，可以在容器中为其添加统一的进入和退出动画。
 * 从上面的示例动画也可以看出，listview中的数据在进入时就加入了统一动画,下面我们就来看看这些是怎么来实现的吧。
 * 这篇我们将讲述有关普通viewGroup添加进入统一动画的LayoutAnimation和针对grideView添加进入动画的gridLayoutAnimation；
 * LayoutAnimation和gridLayoutAnimation在API 1中就有的函数。所有大家不必担心他们的所能使用的api等级；也正因为他们是在API 1中就引入了，
 * 所以他们也只能使用animtion来做动画，而不能使用animator。
 */

public class LayoutGridLayoutAnimationActivity extends AppCompatActivity {

    @BindView(R.id.bt_LayoutAnimation)
    Button btLayoutAnimation;
    @BindView(R.id.bt_GridAnimation)
    Button btGridAnimation;
    @BindView(R.id.bt_LayoutAnimation_java)
    Button btLayoutAnimationJava;
    @BindView(R.id.bt_bt_GridAnimation_java)
    Button btBtGridAnimationJava;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layoutgridlayoutanimation);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_LayoutAnimation, R.id.bt_GridAnimation,
            R.id.bt_LayoutAnimation_java, R.id.bt_bt_GridAnimation_java})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_LayoutAnimation:
                startActivity(new Intent(this, LayoutAnimationActivity.class));
                break;
            case R.id.bt_GridAnimation:
                startActivity(new Intent(this, GridAnimationActivity.class));
                break;
            case R.id.bt_LayoutAnimation_java:
                startActivity(new Intent(this, LayoutAnimationJavaActivity.class));
                break;
            case R.id.bt_bt_GridAnimation_java:
                startActivity(new Intent(this, GridAnimationJavaActivity.class));
                break;
        }
    }

}
