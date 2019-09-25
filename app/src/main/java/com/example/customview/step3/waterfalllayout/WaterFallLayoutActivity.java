package com.example.customview.step3.waterfalllayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.customview.R;

import java.util.Random;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2019/1/10.
 *
 * 自定义控件三部曲视图篇（三）——瀑布流容器WaterFallLayout实现
 *  https://blog.csdn.net/harvic880925/article/details/69787359
 *
 */
public class WaterFallLayoutActivity extends AppCompatActivity {

    private static int IMG_COUNT = 5;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waterfall);

//        final WaterfallLayout waterfallLayout = ((WaterfallLayout)findViewById(R.id.waterfallLayout));

        final WaterfallLayoutImprove waterfallLayoutImprove = (WaterfallLayoutImprove) findViewById(R.id.waterfallLayout);

        findViewById(R.id.add_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addView(waterfallLayoutImprove);
            }
        });

    }

    public void addView(WaterfallLayoutImprove waterfallLayoutImprove) {
        Random random = new Random();
        Integer num = Math.abs(random.nextInt());
        WaterFallLayout.LayoutParams layoutParams = new WaterFallLayout.LayoutParams(
                WaterFallLayout.LayoutParams.WRAP_CONTENT,
                WaterFallLayout.LayoutParams.WRAP_CONTENT);
        ImageView imageView = new ImageView(this);
        if (num % IMG_COUNT == 0) {
            imageView.setImageResource(R.drawable.pic_1);
        } else if (num % IMG_COUNT == 1) {
            imageView.setImageResource(R.drawable.pic_2);
        } else if (num % IMG_COUNT == 2) {
            imageView.setImageResource(R.drawable.pic_3);
        } else if (num % IMG_COUNT == 3) {
            imageView.setImageResource(R.drawable.pic_4);
        } else if (num % IMG_COUNT == 4) {
            imageView.setImageResource(R.drawable.pic_5);
        }
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

        waterfallLayoutImprove.addView(imageView, layoutParams);

        waterfallLayoutImprove.setOnItemClickListener(
                new WaterfallLayoutImprove.OnItemClickListener() {
                    @Override
                    public void onItemClick(View v, int index) {
                        Toast.makeText(WaterFallLayoutActivity.this, "item=" + index, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}
