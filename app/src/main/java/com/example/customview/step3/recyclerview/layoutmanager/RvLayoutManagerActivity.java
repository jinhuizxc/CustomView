package com.example.customview.step3.recyclerview.layoutmanager;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;

import com.example.customview.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 自定义控件三部曲视图篇（六）——RecyclerView系列之三自定义LayoutManager
 * https://blog.csdn.net/harvic880925/article/details/84789602
 *
 * 1.1 自定义CustomLayoutManager
 *
 */
public class RvLayoutManagerActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_layout_manager);
        ButterKnife.bind(this);

        generateDatas();

        recyclerView.setLayoutManager(new CustomLayoutManager());

        RvLayoutManagerAdapter adapter = new RvLayoutManagerAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);

    }

    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }


}
