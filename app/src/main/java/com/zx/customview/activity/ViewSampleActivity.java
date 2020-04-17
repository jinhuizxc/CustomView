package com.zx.customview.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.zx.customview.R;
import com.zx.customview.widgets.custom3.CustomViewActivity3;

import butterknife.BindView;

/**
 * Android自定义控件之自定义组合控件
 * https://www.cnblogs.com/whoislcj/p/5714760.html
 */
public class ViewSampleActivity extends AppCompatActivity {

    @BindView(R.id.btn_01)
    Button btn01;
    @BindView(R.id.btn_02)
    Button btn02;
    @BindView(R.id.btn_03)
    Button btn03;
    @BindView(R.id.btn_04)
    Button btn04;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.zx.viewgroup.R.layout.activity_view_sample);
        butterknife.ButterKnife.bind(this);


    }

    @butterknife.OnClick({R.id.btn_01, R.id.btn_02, R.id.btn_03, R.id.btn_04})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_01:
                break;
            case R.id.btn_02:
                break;
            case R.id.btn_03:
                // 组合
                ActivityUtils.startActivity(CustomViewActivity3.class);
                break;
            case R.id.btn_04:
                break;
        }
    }
}
