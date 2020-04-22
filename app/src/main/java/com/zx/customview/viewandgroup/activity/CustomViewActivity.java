package com.zx.customview.viewandgroup.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.zx.customview.R;
import com.zx.customview.viewandgroup.view.TopBar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CustomViewActivity extends AppCompatActivity {

    @BindView(R.id.topBar)
    TopBar topBar;
    @BindView(R.id.topBar1)
    TopBar topBar1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);
        ButterKnife.bind(this);

        topBar.setTopBarClickListener(new TopBar.TopBarClickListener() {
            @Override
            public void leftClick() {
                ToastUtils.showShort("click left");
            }

            @Override
            public void rightClick() {
                ToastUtils.showShort("click right");
            }
        });
    }

}
