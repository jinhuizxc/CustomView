package com.example.customview.alipay;

import android.graphics.Color;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.appbar.AppBarLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;

import com.example.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 支付宝首页
 * https://mp.weixin.qq.com/s/GegMt7GDBCFVoUgFQWG3Sw
 */
public class AliPayHomeActivity extends AppCompatActivity
        implements AppBarLayout.OnOffsetChangedListener {


    @BindView(R.id.app_bar_layout)
    AppBarLayout appBarLayout;
    @BindView(R.id.include_toolbar_open)
    View mToolbarOpenLayout;
    @BindView(R.id.include_toolbar_close)
    View mToolbarCloseLayout;
    @BindView(R.id.view_open_bg_view)
    View mToolbarOpenBgView;
    @BindView(R.id.view_toolbar_bg_close)
    View mToolbarCloseBgView;
    @BindView(R.id.content_bg_view)
    View contentBgView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alipay_home);
        ButterKnife.bind(this);

        appBarLayout.addOnOffsetChangedListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appBarLayout.removeOnOffsetChangedListener(this);
    }

    @Override
    public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
        //垂直方向偏移量
        int offset = Math.abs(verticalOffset);
        //最大偏移距离
        int scrollRange = appBarLayout.getTotalScrollRange();
        //当滑动没超过一半，展开状态下toolbar显示内容，根据收缩位置，改变透明值
        if (offset <= scrollRange / 2){
            mToolbarOpenLayout.setVisibility(View.VISIBLE);
            mToolbarCloseLayout.setVisibility(View.GONE);
            //根据偏移百分比 计算透明值
            float scale2 = (float) offset / (scrollRange / 2);
            int alpha2 = (int) (255 * scale2);
            mToolbarOpenBgView.setBackgroundColor(Color.argb(alpha2, 25, 131, 209));
        }else {
            mToolbarOpenLayout.setVisibility(View.GONE);
            mToolbarCloseLayout.setVisibility(View.VISIBLE);
            float scale3 = (float) (scrollRange  - offset) / (scrollRange / 2);
            int alpha3 = (int) (255 * scale3);
            mToolbarCloseBgView.setBackgroundColor(Color.argb(alpha3, 25, 131, 209));
        }
        //根据偏移百分比计算扫一扫布局的透明度值
        float scale = (float) offset / scrollRange;
        int alpha = (int) (255 * scale);
        contentBgView.setBackgroundColor(Color.argb(alpha, 25, 131, 209));
    }
}
