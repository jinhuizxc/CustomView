package com.example.customview.activity.flowlayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.blankj.utilcode.util.ToastUtils;
import com.example.customview.R;
import com.example.flowlayout.FlowLayout;
import com.example.flowlayout.adapter.FlowLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

public class TvActivity extends AppCompatActivity {

    private FlowLayoutAdapter<String> adapter;
    private FlowLayout flowLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tv);

        List<String> list = initData();

        adapter = new FlowLayoutAdapter<String>(list) {
            @Override
            public void bindData(ViewHolder viewHolder, int position, String s) {
                viewHolder.setText(R.id.tv, s);
            }

            @Override
            public int getItemLayoutId(int position, String data) {
                return R.layout.item_tv;
            }

            @Override
            public void onItemClick(int position, String bean) {
                ToastUtils.showShort(position + " , " + bean);
            }
        };

        flowLayout = findViewById(R.id.fl);
        flowLayout.setAdapter(adapter);



    }

    private List<String> initData() {
        List<String> list = new ArrayList<>();
        list.add("环境");
        list.add("环境");
        list.add("如果皇太后");
        list.add("人皇太后");
        list.add("环境");
        list.add("然后");
        list.add("环境");
        list.add("环境");
        list.add("然后钛合金");
        list.add("环境");
        list.add("任何人挺好");
        list.add("环境");
        list.add("发个黄庭坚");
        list.add("环境");
        list.add("分分然后");
        list.add("环境");
        list.add("环境");
        list.add("凤凰台和");
        list.add("环境");
        list.add("环境");
        list.add("环境");
        list.add("发个荣誉感");
        list.add("环境");
        list.add("复合肥");
        list.add("环境");
        list.add("发然后");
        list.add("环的风格让他很认同和境");
        list.add("的富贵华庭");
        list.add("的富");
        return list;
    }
}
