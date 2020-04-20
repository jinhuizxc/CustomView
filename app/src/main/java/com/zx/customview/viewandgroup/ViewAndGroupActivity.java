package com.zx.customview.viewandgroup;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zx.customview.R;
import com.zx.customview.viewandgroup.activity.BezierActivity;
import com.zx.customview.viewandgroup.activity.CanvasOperationActivity;
import com.zx.customview.viewandgroup.activity.CustomViewActivity;
import com.zx.customview.viewandgroup.activity.CustomViewBasicActivity;
import com.zx.customview.viewandgroup.activity.DrawActivity;
import com.zx.customview.viewandgroup.activity.DrawPathActivity;
import com.zx.customview.viewandgroup.activity.ViewMeasureActivity;
import com.zx.customview.viewandgroup.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class ViewAndGroupActivity extends AppCompatActivity {

    @BindView(R.id.activity_main)
    LinearLayout mActivityMain;
    @BindView(R.id.recycleView)
    RecyclerView mRecycleView;

    private List<String> list = new ArrayList<String>();
    private MainAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_and_group);
        ButterKnife.bind(this);

        initList();

        initAdapter();

    }

    private void initAdapter() {

        mAdapter = new MainAdapter(list, this);
        mRecycleView.setLayoutManager(new LinearLayoutManager(this));
        mRecycleView.setAdapter(mAdapter);

        mAdapter.setOnClickListener(new MainAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position){
                    case 0:
                        ActivityUtils.startActivity(ViewMeasureActivity.class);
                        break;
                    case 1:
                        ActivityUtils.startActivity(DrawActivity.class);
                        break;
                    case 2:
                        ActivityUtils.startActivity(CanvasOperationActivity.class);
                        break;
                    case 3:
                        ActivityUtils.startActivity(DrawPathActivity.class);
                        break;
                    case 4:
                        ActivityUtils.startActivity(BezierActivity.class);
                        break;
                    case 5:
                        //自定义View基础
                        ActivityUtils.startActivity(CustomViewBasicActivity.class);
                        break;
                    case 6:
                        ActivityUtils.startActivity(CustomViewActivity.class);
                        break;
                    default:
                        break;
                }
            }
        });

    }

    private void initList() {
        list.add("View的测量");
        list.add("View的绘制");
        list.add("Canvas操作");
        list.add("Path的绘制");
        list.add("贝赛尔曲线");
        list.add("自定义View基础");
        list.add("自定义View举例");
        list.add("仿小米运动计步");
        list.add("自定义ViewGroup");
        list.add("CircleProgress");
    }
}
