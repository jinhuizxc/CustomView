package com.zx.customview.viewandgroup.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.zx.customview.R;
import com.zx.customview.viewandgroup.view.XiaoMiStep;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class XiaoMiSetpActivity extends AppCompatActivity {

    @BindView(R.id.xiaoMiStep)
    XiaoMiStep xiaoMiStep;
    @BindView(R.id.btn_reset)
    Button btnReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiao_mi_setp);
        ButterKnife.bind(this);

        xiaoMiStep.setMyFootNum(4500);

    }

    @OnClick({R.id.btn_reset})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_reset:
                xiaoMiStep.reSet();
                break;
        }
    }
}
