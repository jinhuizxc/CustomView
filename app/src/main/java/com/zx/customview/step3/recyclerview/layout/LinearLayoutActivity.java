package com.zx.customview.step3.recyclerview.layout;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.zx.customview.R;
import com.zx.customview.step3.recyclerview.adapter.RecyclerAdapter;
import com.zx.customview.step3.recyclerview.itemdecoration.LinearItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2019/1/11.
 * <p>
 * 线性布局
 */
public class LinearLayoutActivity extends AppCompatActivity {

    @BindView(R.id.linear_recycler_view)
    RecyclerView recyclerView;

    private ArrayList<String> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linearlayout);
        ButterKnife.bind(this);

        generateDatas();

        //线性布局
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        // 添加分割线
        //1.1 引入ItemDecoration
//        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
//        recyclerView.addItemDecoration(dividerItemDecoration);

        //添加分隔线
        recyclerView.addItemDecoration(new LinearItemDecoration(this));

        RecyclerAdapter adapter = new RecyclerAdapter(this, mDatas);
        recyclerView.setAdapter(adapter);

    }


    private void generateDatas() {
        for (int i = 0; i < 200; i++) {
            mDatas.add("第 " + i + " 个item");
        }
    }

}
