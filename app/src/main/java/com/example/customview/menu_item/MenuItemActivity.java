package com.example.customview.menu_item;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.example.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 * 1.自定义View：继承View
 * 2.基于现有组件：继承View的派生类
 * 3.组合的方式：自定义控件中包含了其他的组件
 */
public class MenuItemActivity extends AppCompatActivity {

    @BindView(R.id.menuItem1)
    MenuItemLayout menuItem1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_item);
        ButterKnife.bind(this);

        menuItem1.setTitleText("消息通知");
        menuItem1.setIconImgId(R.drawable.img2);
        menuItem1.setDivideLine(MenuItemLayout.DIVIDE_AREA);
        menuItem1.setHintText("暂无消息");
        menuItem1.setViewOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtils.showShort("View被点击了...");
            }
        });

    }
}
