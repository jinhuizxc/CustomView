package com.zx.customview.widget.fold;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.expandview.LogUtils;
import com.zx.customview.R;
import com.zx.customview.activity.fold.TextExpandActivity;
import com.zx.customview.activity.fold.TextExpandActivity2;
import com.zx.customview.activity.fold.TextExpandActivity3;

/**
 *
 * 自定义折叠布局，自定义折叠和展开布局，
 * 在不用改变原控件的基础上，就可以实现折叠展开功能，入侵性极低。可以设置折叠和展开的监听事件，可以支持支持常见的文本折叠，流失布局标签折叠，或者RecyclerView折叠等功能。十分方便，思路也比较容易理解，代码不超过300行……
 * https://github.com/yangchong211/YCExpandView
 *
 * 折叠组件，项目需要的效果需要进行部分修改;
 */
public class FoldActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tv1;
    private TextView tv2;
    private TextView tv3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold);

        LogUtils.setIsLog(true);
        initView();
        initListener();
    }

    private void initView() {
        tv1 = findViewById(R.id.tv_1);
        tv2 = findViewById(R.id.tv_2);
        tv3 = findViewById(R.id.tv_3);
    }

    private void initListener() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
        tv3.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_1:
                ActivityUtils.startActivity(TextExpandActivity.class);
                break;
            case R.id.tv_2:
                startActivity(new Intent(this, TextExpandActivity2.class));
                break;
            case R.id.tv_3:
                startActivity(new Intent(this, TextExpandActivity3.class));
                break;
            default:
                break;
        }
    }
}
