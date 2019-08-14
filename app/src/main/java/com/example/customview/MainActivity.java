package com.example.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.customview.qqmenu.QQMenuActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 仿新版手机QQ底部动态按钮
 * https://www.jianshu.com/p/fcbd86d2b73a
 *
 * Android自定义控件：带你掌握一款多特效的智能loadingView
 * https://www.jianshu.com/p/63c209041e22
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_01)
    Button bt01;
    @BindView(R.id.bt_02)
    Button bt02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_01, R.id.bt_02})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_01:
                ActivityUtils.startActivity(QQMenuActivity.class);
                break;
            case R.id.bt_02:
                break;
        }
    }
}
