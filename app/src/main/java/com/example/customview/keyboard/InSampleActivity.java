package com.example.customview.keyboard;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.customview.R;
import com.example.customview.keyboard.example.HomeEmailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 键盘冲突
 * https://blog.csdn.net/qq_38875767/article/details/90201524
 */
public class InSampleActivity extends AppCompatActivity {

    @BindView(R.id.btn_way1)
    Button btnWay1;
    @BindView(R.id.btn_way2)
    Button btnWay2;
    @BindView(R.id.btn_edit_email)
    Button btnEditEmail;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_in);
        ButterKnife.bind(this);


    }


    @OnClick({R.id.btn_way1, R.id.btn_way2, R.id.btn_edit_email})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_way1:
                break;
            case R.id.btn_way2:
                break;
            case R.id.btn_edit_email:
                ActivityUtils.startActivity(HomeEmailActivity.class);
                break;
        }
    }
}
