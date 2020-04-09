package com.zx.customview.step3.recyclerview.itemdecoration;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zx.customview.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义控件三部曲视图篇（五）——RecyclerView系列之二ItemDecoration
 * https://blog.csdn.net/harvic880925/article/details/82959754
 *
 * 1.1 引入ItemDecoration
 *
 * 在上一篇中，我们讲解了RecyclerView的基本使用方法，但有个问题：为什么Item之间没有分割线呢？其实，给RecyclerView添加分割线也非常简单，只需要添加上一句话：
 *  DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.HORIZONTAL);
 *         recyclerView.addItemDecoration(dividerItemDecoration);
 *
 * Decoration是装饰的意思，ItemDecoration就是Item的装饰。
 * 系统只给我们提供了一个现成的Decoration类就是刚才使用的DividerItemDecoration，
 * 如果我们想实现其它的装饰效果，就需要自定义了。下面这些漂亮的效果都可以使用自定义ItemDecoration来实现
 *
 * 1. 首先，给整个Activity添加上一个红色的背景色：
 * 2. 之后，给每个Item添加上默认的背景色白色，这样有白色的地方就不会透出背景色的红色了，而没有白色的地方就会露出红色：
 * 3. 在这里，我们将item上方面所撑开的距离硬编码为1px; 最后，将LinearItemDecoration添加进RecyclerView:
 *
 * 可以看到每个Item的上方都出现了一条红线。尤其从第一个Item可以看出来。
 * 同样的，如果我们改为底部1px，左侧50px,右侧100px:
 *
 */
public class RvItemDecorationActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_itemdecortation);
        ButterKnife.bind(this);

        // 初始化数据
        generateDatas();
        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        // 如果是横向滚动，后面的数值表示的是几行，如果是竖向滚动，后面的数值表示的是几列
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

//        DividerItemDecoration dividerItemDecoration =
//                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);

        // 自定义LinearItemDecoration
        //添加分隔线
//        recyclerView.addItemDecoration(new LinearItemDecoration());

        // 左侧添加图片
        recyclerView.addItemDecoration(new LinearItemDecoration(this));


        RvItemDecorationAdapter adapter = new RvItemDecorationAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);

    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }

}
