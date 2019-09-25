package com.example.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.customview.activity.DotViewActivity;
import com.example.customview.badgeview.BadgeViewActivity;
import com.example.customview.qqmenu.QQMenuActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * TODO 如果可以一天一个自定义view
 * 仿新版手机QQ底部动态按钮
 * https://www.jianshu.com/p/fcbd86d2b73a
 * <p>
 * Android自定义控件：带你掌握一款多特效的智能loadingView
 * https://www.jianshu.com/p/63c209041e22
 * <p>
 * <p>
 * 消息红点
 * Android自定义小红点BadgeView
 * https://www.jianshu.com/p/2cc34a055bfe
 * <p>
 * 支持自由定制外观、拖拽消除的MaterialDesign风格Android BadgeView
 * https://github.com/qstumn/BadgeView
 *
 * Android自定义控件三部曲文章索引
 * https://blog.csdn.net/harvic880925/article/details/50995268
 *
 * https://github.com/vipulasri/Timeline-View
 * 他是通过自定义View来实现的，大家也可以尝试通过RecyclerView的ItemDecoration来实现出来。
 *
 *
 * 将此前写的资源整理下，整合资源;
 *
 */

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.bt_01)
    Button bt01;
    @BindView(R.id.bt_02)
    Button bt02;
    @BindView(R.id.bt_badge)
    Button btBadge;
    @BindView(R.id.btn_custom_study)
    Button btnCustomStudy;
    @BindView(R.id.btn_dot)
    Button btnDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_01, R.id.bt_02, R.id.bt_badge, R.id.btn_custom_study,
    R.id.btn_dot})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_01:
                ActivityUtils.startActivity(QQMenuActivity.class);
                break;
            case R.id.bt_02:
                ToastUtils.showShort("TODO");
                break;
            case R.id.bt_badge:
                ActivityUtils.startActivity(BadgeViewActivity.class);
                break;
            case R.id.btn_custom_study:
                ActivityUtils.startActivity(CustomStudyActivity.class);
                break;
            case R.id.btn_dot:
                ActivityUtils.startActivity(DotViewActivity.class);
                break;
        }
    }
}
