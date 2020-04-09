package com.zx.customview;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zx.customview.step1.Step1Activity;
import com.zx.customview.step2.Step2Activity;
import com.zx.customview.step3.Step3Activity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/4.
 * Email:1004260403@qq.com
 */

public class CustomStudyActivity extends AppCompatActivity {


    @BindView(R.id.bt_step1)
    Button btStep1;
    @BindView(R.id.bt_step2)
    Button btStep2;
    @BindView(R.id.bt_step3)
    Button btStep3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        ButterKnife.bind(this);

    }


    @OnClick({R.id.bt_step1, R.id.bt_step2, R.id.bt_step3})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_step1:
                startActivity(new Intent(this, Step1Activity.class));
                break;
            case R.id.bt_step2:
                startActivity(new Intent(this, Step2Activity.class));
                break;
            case R.id.bt_step3:
                startActivity(new Intent(this, Step3Activity.class));
                break;
        }
    }
}
