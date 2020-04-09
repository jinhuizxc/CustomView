package com.zx.customview.keyboard.one;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import com.zx.customview.R;

/**
 * Android之控件保持在软键盘上面
 * https://blog.csdn.net/i_do_can/article/details/52764813
 */
public class TestOneActivity extends AppCompatActivity {

    private View mContentView;
    private Button mBtnShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_one);

        mContentView = findViewById(R.id.contentView);
        mBtnShow = findViewById(R.id.btn);

        buttonBeyondKeyboardLayout(mContentView, mBtnShow);

    }

    private void buttonBeyondKeyboardLayout(final View root, final View button) {
        // 监听根布局的视图变化
        root.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        Rect rect = new Rect();
                        // 获取内容布局在窗体的可视区域
                        root.getWindowVisibleDisplayFrame(rect);
                        // 获取内容布局在窗体的不可视区域高度(被其他View遮挡的区域高度)
                        int rootInvisibleHeight = root.getHeight() - rect.bottom;
                        // 若不可视区域高度大于100，则键盘显示
                        if (rootInvisibleHeight > 100) {
                            int[] location = new int[2];
                            // 获取须顶上去的控件在窗体的坐标
                            button.getLocationInWindow(location);
                            // 计算内容滚动高度，使button在可见区域
                            int buttonHeight = (location[1]
                                    + button.getHeight()) - rect.bottom;
                            root.scrollTo(0, buttonHeight);
                        } else {
                            // 键盘隐藏
                            root.scrollTo(0, 0);
                        }
                    }
                });
    }

}
