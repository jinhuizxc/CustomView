package com.example.customview.step3.recyclerview;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.customview.R;
import com.example.customview.step3.recyclerview.layout.GridLayoutActivity;
import com.example.customview.step3.recyclerview.layout.LinearLayoutActivity;
import com.example.customview.step3.recyclerview.layout.StaggeredGridLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2019/1/10.
 * <p>
 * 自定义控件三部曲视图篇（四）——RecyclerView系列之一简单使用
 * https://blog.csdn.net/harvic880925/article/details/82656394
 * <p>
 * LinearLayoutManager就是传统的ListView的功能，上下滚动或者左右滚动。
 * 而GridLayoutManager则是网格摆放，
 * 而StaggeredGridLayoutMnager则是瀑布流摆放。
 * <p>
 * 其中WearableLinearLayoutManager用于在穿戴设备上使用，比如智能手表等，所以我们这里不讨论它
 *
 * LayoutManager
 * GridLayoutManager
 * spanCount:如果是竖向滚动，则表示当前划分为几列；如果是横向滚动，则表示当前划分为几行。
 *
 * StaggeredGridLayoutMnager
 * 其中 public StaggeredGridLayoutManager(int spanCount, int orientation)
 *
 * spanCount:同样表示行数或列数，如果是竖向滚动，则表示当前划分为几列；如果是横向滚动，则表示当前划分为几行。
 * orientation:表示滚动方向，取值有：StaggeredGridLayoutManager.HORIZONTAL和StaggeredGridLayoutManager.VERTICAL
 *
 *
 */
public class RvActivity extends AppCompatActivity {

    @BindView(R.id.bt_linearLayout)
    Button btLinearLayout;
    @BindView(R.id.bt_gridLayout)
    Button btGridLayout;
    @BindView(R.id.bt_staggerGridLayout)
    Button btStaggerGridLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_01);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.bt_linearLayout, R.id.bt_gridLayout, R.id.bt_staggerGridLayout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_linearLayout:
                ActivityUtils.startActivity(LinearLayoutActivity.class);
                break;
            case R.id.bt_gridLayout:
                ActivityUtils.startActivity(GridLayoutActivity.class);
                break;
            case R.id.bt_staggerGridLayout:
                ActivityUtils.startActivity(StaggeredGridLayoutActivity.class);
                break;
        }
    }
}
