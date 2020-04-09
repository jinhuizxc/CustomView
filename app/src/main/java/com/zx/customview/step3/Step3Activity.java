package com.zx.customview.step3;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.blankj.utilcode.util.ActivityUtils;
import com.blankj.utilcode.util.ToastUtils;
import com.zx.customview.R;
import com.zx.customview.step3.recyclerview.itemdecoration.RvItemDecorationActivity;
import com.zx.customview.step3.recyclerview.layoutmanager.RvLayoutManagerActivity;
import com.zx.customview.step3.flowLayout.AFlowLayoutActivity;
import com.zx.customview.step3.recyclerview.RvActivity;
import com.zx.customview.step3.waterfalllayout.WaterFallLayoutActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 * <p>
 * 自定义视图篇
 *
 * 《自定义控件三部曲视图篇（一）——测量与布局》
 * https://blog.csdn.net/harvic880925/article/details/47029169
 */

public class Step3Activity extends AppCompatActivity {

    @BindView(R.id.bt_flowLayout)
    Button btFlowLayout;
    @BindView(R.id.bt_waterFallLayout)
    Button btWaterFallLayout;
    @BindView(R.id.btn_rv)
    Button btnRv;
    @BindView(R.id.btn_itemDecoration)
    Button btnItemDecoration;
    @BindView(R.id.btn_layoutManager)
    Button btnLayoutManager;
    @BindView(R.id.btn_recycle)
    Button btnRecycle;
    @BindView(R.id.btn_layout)
    Button btnLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step3);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_flowLayout, R.id.bt_waterFallLayout, R.id.btn_rv,
            R.id.btn_itemDecoration, R.id.btn_layoutManager, R.id.btn_recycle, R.id.btn_layout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_layout:
                ActivityUtils.startActivity(AFlowLayoutActivity.class);
                break;
            case R.id.bt_flowLayout:
                // startActivity                  : 启动 Activity
                ActivityUtils.startActivity(AFlowLayoutActivity.class);
                break;
            case R.id.bt_waterFallLayout:
                ActivityUtils.startActivity(WaterFallLayoutActivity.class);
                break;
            case R.id.btn_rv:
                ActivityUtils.startActivity(RvActivity.class);
                break;
            case R.id.btn_recycle:
                ToastUtils.showShort("todo");
                break;
            case R.id.btn_itemDecoration:
                ActivityUtils.startActivity(RvItemDecorationActivity.class);
                break;
            case R.id.btn_layoutManager:
                ActivityUtils.startActivity(RvLayoutManagerActivity.class);
                break;
        }
    }
}
