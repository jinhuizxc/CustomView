package com.example.customview.activity.flowlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.customview.R;
import com.example.customview.activity.email.EmailTextAreaActivity;
import com.example.customview.activity.email.EmailToActivity;
import com.example.customview.flowlayout.CategoryActivity;
import com.example.test.TestActivity;

public class SampleActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample);

        findViewById(R.id.btn_test).setOnClickListener(this);
        findViewById(R.id.btn_tv).setOnClickListener(this);
        findViewById(R.id.btn_tv_scroll).setOnClickListener(this);
        findViewById(R.id.btn_layout_scroll).setOnClickListener(this);
        findViewById(R.id.btn_layout__multi_scroll).setOnClickListener(this);
        findViewById(R.id.btn_select).setOnClickListener(this);
        findViewById(R.id.btn_email_to).setOnClickListener(this);
        findViewById(R.id.btn_hyman).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_test) {
            ActivityUtils.startActivity(TestActivity.class);
        } else if (id == R.id.btn_tv) {
            ActivityUtils.startActivity(TvActivity.class);
        } else if (id == R.id.btn_tv_scroll) {
            ActivityUtils.startActivity(TvScrollActivity.class);
        } else if (id == R.id.btn_layout_scroll) {
            ActivityUtils.startActivity(LayoutScrollActivity.class);
        } else if (id == R.id.btn_layout__multi_scroll) {
            ActivityUtils.startActivity(LayoutMultiScrollActivity.class);
        }else if (id == R.id.btn_select){
            ActivityUtils.startActivity(EmailTextAreaActivity.class);
        }else if (id == R.id.btn_email_to){
            ActivityUtils.startActivity(EmailToActivity.class);
        }else if (id == R.id.btn_hyman){
            ActivityUtils.startActivity(CategoryActivity.class);
        }
    }
}
