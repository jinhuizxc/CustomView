package com.example.customview.step1.layoutgridlayoutanimation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

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
 * GridLayoutAnimation的XML实现——gridLayoutAnimation
 * 这部分将给大家讲解有关gridview给内部子控件添加创建动画的内容。本部分的效果图如下
 *
 *
 * 第一：gridview中各个元素的出场顺序为从上往下，从左往右。
 * 第二：gridLayoutAnimation仅在gridview第一次创建时各个元素才会有出场动画，在创建成功以后，再向其中添加数据就不会再有动画。这一点与layoutAnimation相同。
 * 下面来看看这个实例的实现过程：
 * （1）、首先是gride_animation.xml
 * 这里没有设置android:direction属性，采用默认值：left_to_right|top_to_bottom；
 * （2）、程序布局main.xml
 * （3）、代码处理
 */


/**
 * gridLayoutAnimation标签各属性详解
 * （1）、directionPriority
 * directionPriority指gridview动画优先级，取值有row,column,none.意义分别为行优先，列优先，和无优先级（同时进行）。
 还以上面的例子为例，我们使用direction的默认值即left_to_right|top_to_bottom，将android:directionPriority分别改变为row,column,none，看它的效果如何。
 android:directionPriority=”row”
 从效果图中可以看出，在优先级改为行以后，gridview中各个item的出场顺序就变为一行一行的出现了。
 android:directionPriority=”column”
 从效果图中可以看出，在优先级改为列以后，gridview中各个item的出场顺序就改为一列一列的出现了。
 android:directionPriority=”none”
 从效果图中可以看出，在优先给改为None以后，gridview中各个item的出场顺序就是行，列一起进行了。
 在知道优先级字段的作用以后，我们来看看android:direction字段的意义
 * （2）、direction
 * direction表示gridview的各个item的动画方向，取值如下，可以通过“|”连接多个属性值。
 取值有四个：
 - left_to_right：列，从左向右开始动画
 - right_to_left ：列，从右向左开始动画
 - top_to_bottom：行，从上向下开始动画
 - bottom_to_top：行，从下向上开始动画
 为了更好的突显效果，我们将android:directionPriority设置为none即行列一起进行动画。
 android:direction=”left_to_right”从左向右开始动画
 从效果图中，很明显可以看出，入场顺序是从左向右的，由于上下的入场顺序没有指定，默认是从上向下入场
 android:direction=”right_to_left”从右向左开始动画
 很明显可以看出，各个item是从右向左入场的，同样由于上下的入场顺序没有指定，默认是从上向下入场
 android:direction=”top_to_bottom”从上向下开始动画
 从效果图中可以看出，各个item是从上向下入场的。由于左右入场顺序没有指定，所以默认是从左向右入场。
 android:direction=”bottom_to_top”从下向上开始动画
 从效果图中可以看出，各个item是从下向上入场的。同样由于左右入场顺序没有指定，所以默认是从左向右入场。
 组合：android:direction=”bottom_to_top|right_to_left”
 前面我们说过，可以通过”|”将多个属性值连接起来，我们这里尝试一下纵向从底向上入场，横向从右向左入场。
 从效果图中明显可以看出，我们实现了纵向从底向上入场，横向从右向左入场的效果。
 到这里，有关directionPriority和direction各个取值的意义已经讲解完了，下面我们就来看看如何通过代码来实现GridLayoutAnimation。
 *
 */

public class GridAnimationActivity extends AppCompatActivity {

    @BindView(R.id.add_data)
    Button addData;
    @BindView(R.id.grid)
    GridView grid;
    private GridAdapter mGrideAdapter;
    private List<String> mDatas = new ArrayList<>();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridanimation);
        ButterKnife.bind(this);

        mDatas.addAll(getData());
        mGrideAdapter = new GridAdapter();
        grid.setAdapter(mGrideAdapter);

    }

    /**
     * 按钮点击响应
     */
    @OnClick(R.id.add_data)
    public void onViewClicked() {
        addData();
    }

    private List<String> getData() {
        List<String> data = new ArrayList<String>();
        for (int i = 1;i<35;i++){
            data.add("DATA "+i);
        }
        return data;
    }

    public void addData(){
        mDatas.addAll(mDatas);
        mGrideAdapter.notifyDataSetChanged();
    }

    public class GridAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return mDatas.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView textView = new TextView(GridAnimationActivity.this);
            textView.setText(mDatas.get(position));
            textView.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
            return textView;
        }


    }
}
