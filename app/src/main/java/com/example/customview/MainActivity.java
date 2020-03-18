package com.example.customview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.example.customview.activity.DotViewActivity;
import com.example.customview.alipay.AliPayHomeActivity;
import com.example.customview.animation_btn.AnimationBtnActivity;
import com.example.customview.autosize_txt.AutoSizeTxtActivity;
import com.example.customview.badgeview.BadgeViewActivity;
import com.example.customview.cloudmusic.CloudActivity;
import com.example.customview.menu_item.MenuItemActivity;
import com.example.customview.step1.animation.AnimationActivity;
import com.example.customview.yzm.pic.PicYzmActivity;
import com.example.customview.qqmenu.QQMenuActivity;
import com.example.richeditor.RichEditorWebViewActivity;
import com.example.workclockview.WorkClockActivity;

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
 * <p>
 * Android自定义控件三部曲文章索引
 * https://blog.csdn.net/harvic880925/article/details/50995268
 * <p>
 * https://github.com/vipulasri/Timeline-View
 * 他是通过自定义View来实现的，大家也可以尝试通过RecyclerView的ItemDecoration来实现出来。
 * <p>
 * <p>
 * 将此前写的资源整理下，整合资源;
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
    @BindView(R.id.btn_pic_yzm)
    Button btnPicYzm;
    @BindView(R.id.btn_music)
    Button btnMusic;
    @BindView(R.id.btn_auto_size_txt)
    Button btnAutoSizeTxt;
    @BindView(R.id.btn_menu_item)
    Button btnMenuItem;
    @BindView(R.id.btn_alipay_home)
    Button btnAliPay;
    @BindView(R.id.btn_animation)
    Button btnAnimation;
    @BindView(R.id.btn_workClock)
    Button btnWorkClock;
    @BindView(R.id.btn_editor)
    Button btnRichEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_01, R.id.bt_02, R.id.bt_badge,
            R.id.btn_custom_study, R.id.btn_dot, R.id.btn_pic_yzm,
            R.id.btn_music, R.id.btn_auto_size_txt, R.id.btn_menu_item,
            R.id.btn_alipay_home, R.id.btn_animation, R.id.btn_workClock,
    R.id.btn_editor})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_pic_yzm:
                ActivityUtils.startActivity(PicYzmActivity.class);
                break;
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
            case R.id.btn_music:
                ActivityUtils.startActivity(CloudActivity.class);
                break;
            case R.id.btn_auto_size_txt:
                ActivityUtils.startActivity(AutoSizeTxtActivity.class);
                break;
            case R.id.btn_menu_item:
                ActivityUtils.startActivity(MenuItemActivity.class);
                break;
            case R.id.btn_alipay_home:
                ActivityUtils.startActivity(AliPayHomeActivity.class);
                break;
            case R.id.btn_animation:
                ActivityUtils.startActivity(AnimationBtnActivity.class);
                break;
            case R.id.btn_workClock:
                ActivityUtils.startActivity(WorkClockActivity.class);
                break;
            case R.id.btn_editor:
                ActivityUtils.startActivity(RichEditorWebViewActivity.class);
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }
}
