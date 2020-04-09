package com.zx.workclockview;

import android.app.WallpaperManager;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 用Kotlin实现抖音爆红的文字时钟，征服产品小姐姐
 * https://mp.weixin.qq.com/s/Z2baRq2LBjxsFiC3VKNfNw
 */
public class WorkClockActivity extends AppCompatActivity {


    Button btnSet;
    Button btnClear;
    WorkClockView stageTextClock;
    private Timer timer = new Timer();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_clock);

        initViews();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        stageTextClock.doInvalidate();
                    }
                });
            }
        }, 0, 1000);

    }

    private void initViews() {
        btnSet = findViewById(R.id.btnSet);
        btnClear = findViewById(R.id.btnClear);
        stageTextClock = findViewById(R.id.wk_clockView);

        /**
         * 设置壁纸，API至少是16
         */
        btnSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             setWallPaper();
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearWallPaper();
            }
        });
    }

    private void setWallPaper() {
        Intent intent = new Intent();
        intent.setAction(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER);
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                new ComponentName(this, TextClockWallpaperService.class));
        startActivity(intent);
    }

    /**
     * 还原壁纸
     */
    private void clearWallPaper() {
        try {
            WallpaperManager.getInstance(this).clear();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
