package com.example.customview.autosize_txt;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.RelativeLayout;
import android.widget.SeekBar;

import com.example.customview.R;
import com.example.customview.utils.PixelUtil;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 *
 */
public class AutoSizeTxtActivity extends AppCompatActivity {

    @BindView(R.id.autoSizeView)
    AutoSizeTextView autoSizeView;
    @BindView(R.id.seekBar)
    SeekBar seekBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_size_txt);
        ButterKnife.bind(this);

        seekBar.setMax(100);
        seekBar.setProgress(25);
        // 测试数据
//        autoSizeView.setText("我是测试数据我是测试数据我是测试数据我是测试数据我是测试数据我是测试数据")
        autoSizeView.setText("我是测试数据").setMaxTextSize(50);

        initListener();
    }

    private void initListener() {
        //使用SeekBar模拟文本控件宽度变化（即：容器大小变化）
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) autoSizeView.getLayoutParams();
                layoutParams.height = PixelUtil.dp2px(AutoSizeTxtActivity.this, 50);
                // 计算进度：容器宽度/进度条宽度 = 进度/100
                layoutParams.width = progress * seekBar.getWidth() / 100;
                autoSizeView.setLayoutParams(layoutParams);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


}
