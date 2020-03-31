package com.example.customview.step3.recyclerview.layout;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.customview.R;
import com.example.customview.step3.recyclerview.adapter.RecyclerAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2019/1/11.
 *
 *  线性布局
 */
public class StaggeredGridLayoutActivity extends AppCompatActivity {

    @BindView(R.id.linear_recycler_view)
    RecyclerView recyclerView;

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linearlayout);
        ButterKnife.bind(this);

        generateDatas();

        /**
         * //瀑布流布局
         * 其中 public StaggeredGridLayoutManager(int spanCount, int orientation)
         * spanCount:同样表示行数或列数，如果是竖向滚动，则表示当前划分为几列；如果是横向滚动，则表示当前划分为几行。
         * orientation:表示滚动方向，取值有：StaggeredGridLayoutManager.HORIZONTAL和StaggeredGridLayoutManager.VERTICAL
         */
        StaggeredGridLayoutManager staggeredManager = new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(staggeredManager);

//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);

        RecyclerAdapter adapter = new RecyclerAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);

    }


    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}
