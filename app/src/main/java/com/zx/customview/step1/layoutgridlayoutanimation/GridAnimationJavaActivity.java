package com.zx.customview.step1.layoutgridlayoutanimation;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.GridLayoutAnimationController;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.zx.customview.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 * <p>
 * GridLayoutAnimation的代码实现——GridLayoutAnimationController
 * 我们知道gridLayoutAnimation标签在代码中对应GridLayoutAnimationController类，它的构造方法如下：
 * public GridLayoutAnimationController(Animation animation)
 * public GridLayoutAnimationController(Animation animation, float columnDelay, float rowDelay)
 * 其中animation对应标签属性中的android:animation
 * columnDelay对应标签属性中的android:columnDelay，取值为float类型
 * rowDelay对应标签属性中的android:rowDelay，取值为float类型
 * 然后是GridLayoutAnimationController的几个函数：
 * /**
 * 设置列动画开始延迟
 * public void setColumnDelay(float columnDelay)
 * <p>
 * 设置行动画开始延迟
 * public void setRowDelay(float rowDelay)
 * <p>
 * 设置gridview动画的入场方向。取值有：DIRECTION_BOTTOM_TO_TOP、DIRECTION_TOP_TO_BOTTOM、DIRECTION_LEFT_TO_RIGHT、DIRECTION_RIGHT_TO_LEFT
 * public void setDirection(int direction)
 * <p>
 * 动画开始优先级，取值有PRIORITY_COLUMN、PRIORITY_NONE、PRIORITY_ROW
 * public void setDirectionPriority(int directionPriority)
 */

public class GridAnimationJavaActivity extends AppCompatActivity {

    @BindView(R.id.grid)
    GridView grid;
    private GridAdapter mGrideAdapter;
    private List<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gridanimationjava);
        ButterKnife.bind(this);

        mDatas.addAll(getData());
        mGrideAdapter = new GridAdapter();
        grid.setAdapter(mGrideAdapter);

        /**
         * 这部分理解起来难度也不大，无外乎就是构造一个GridLayoutAnimationController，
         * 然后通过它的各个set函数把各个属性值设置进去。
         */
        Animation animation = AnimationUtils.loadAnimation(GridAnimationJavaActivity.this,R.anim.slide_in_left);
        GridLayoutAnimationController controller = new GridLayoutAnimationController(animation);
        controller.setColumnDelay(0.75f);
        controller.setRowDelay(0.5f);
        controller.setDirection(GridLayoutAnimationController.DIRECTION_BOTTOM_TO_TOP| GridLayoutAnimationController.DIRECTION_LEFT_TO_RIGHT);
        controller.setDirectionPriority(GridLayoutAnimationController.PRIORITY_NONE);
        grid.setLayoutAnimation(controller);
        grid.startLayoutAnimation();
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
        public View getView(int position, View convertView, ViewGroup parent) {
            TextView i = new TextView(GridAnimationJavaActivity.this);
            i.setText(mDatas.get(position));
            i.setLayoutParams(new GridView.LayoutParams(GridView.LayoutParams.WRAP_CONTENT, GridView.LayoutParams.WRAP_CONTENT));
            return i;
        }

        public final int getCount() {
            return mDatas.size();
        }

        public final Object getItem(int position) {
            return null;
        }

        public final long getItemId(int position) {
            return position;
        }
    }
}
