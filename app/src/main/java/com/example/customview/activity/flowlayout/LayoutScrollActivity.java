package com.example.customview.activity.flowlayout;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.customview.R;
import com.example.flowlayout.FlowLayoutScrollView;
import com.example.flowlayout.adapter.FlowLayoutAdapter;

import java.util.ArrayList;
import java.util.List;

public class LayoutScrollActivity extends AppCompatActivity {

    private FlowLayoutAdapter<String> adapter;

    private FlowLayoutScrollView flowLayoutScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layout_scroll);

        List<String> list = getList();

        adapter = new FlowLayoutAdapter<String>(list) {
            @Override
            public void bindData(ViewHolder holder, int position, String bean) {
                holder.setText(R.id.tv, bean);
            }

            @Override
            public int getItemLayoutId(int position, String data) {
                return R.layout.item_fl_layout;
            }

            @Override
            public void onItemClick(int position, String bean) {

            }
        };

        flowLayoutScrollView = findViewById(R.id.flsv);

        flowLayoutScrollView.setAdapter(adapter);



    }

    private List<String> getList() {
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
        return list;
    }
}
