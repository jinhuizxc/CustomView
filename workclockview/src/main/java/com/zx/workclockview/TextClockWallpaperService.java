package com.zx.workclockview;

import android.graphics.Canvas;
import android.os.Handler;
import android.service.wallpaper.WallpaperService;
import android.util.Log;
import android.view.SurfaceHolder;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 文字时钟动态壁纸服务
 */
public class TextClockWallpaperService extends WallpaperService {


    @Override
    public Engine onCreateEngine() {
        return new MyEngine();
    }

    private class MyEngine extends Engine {
        WorkClockView mClockView = new WorkClockView(getBaseContext());
        private Handler handler;
        private Timer timer;
        private static final String TAG = "MyEngine";

        @Override
        public void onCreate(SurfaceHolder surfaceHolder) {
            super.onCreate(surfaceHolder);
            Log.d(TAG, "onCreate: ");
        }

        @Override
        public void onSurfaceCreated(SurfaceHolder holder) {
            super.onSurfaceCreated(holder);
            Log.d(TAG, "onSurfaceCreated: ");
        }

        @Override
        public void onSurfaceChanged(SurfaceHolder holder, int format, int width, int height) {
            super.onSurfaceChanged(holder, format, width, height);
            Log.d(TAG, "onSurfaceChanged: ");
        }

        @Override
        public void onSurfaceDestroyed(SurfaceHolder holder) {
            super.onSurfaceDestroyed(holder);
            Log.d(TAG, "onSurfaceDestroyed: ");
            stopClock();
        }

        @Override
        public void onDestroy() {
            super.onDestroy();
            Log.d(TAG, "onDestroy: ");
        }

        /**
         * Called to inform you of the wallpaper becoming visible or
         * hidden.  <em>It is very important that a wallpaper only use
         * CPU while it is visible.</em>.
         * <p>
         * 当壁纸显示或隐藏是会回调该方法。
         * 很重要的一点是，要只在壁纸显示的时候做绘制操作（占用CPU）。
         */
        @Override
        public void onVisibilityChanged(boolean visible) {
            super.onVisibilityChanged(visible);
            Log.d(TAG, "onVisibilityChanged: " + visible);
            if (visible) {
                startClock();
            } else {
                stopClock();
            }
        }


        /**
         * 停止绘制
         */
        private void stopClock() {
            timer.cancel();
            timer = null;
            mClockView.stopInvalidate();
        }

        private void startClock() {
            if (timer != null) return;
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            mClockView.doInvalidate();
                            if (timer != null && getSurfaceHolder() != null) {
                                Canvas canvas = getSurfaceHolder().lockCanvas();
                                mClockView.initWidthHeight((float) canvas.getWidth(), (float) canvas.getHeight());
                                mClockView.draw(canvas);
                                getSurfaceHolder().unlockCanvasAndPost(canvas);
                                Log.d("clock", "doInvalidate >>> 触发绘制");
                            }
                        }
                    });
                }
            }, 0, 1000);
        }

    }


}
