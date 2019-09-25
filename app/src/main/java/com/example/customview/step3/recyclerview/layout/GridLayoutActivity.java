package com.example.customview.step3.recyclerview.layout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

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
public class GridLayoutActivity extends AppCompatActivity {

    @BindView(R.id.linear_recycler_view)
    RecyclerView recyclerView;

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linearlayout);
        ButterKnife.bind(this);

        generateDatas();

        //如果是横向滚动，后面的数值表示的是几行，如果是竖向滚动，后面的数值表示的是几列
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 5);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        RecyclerAdapter adapter = new RecyclerAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);

    }


    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }
}
