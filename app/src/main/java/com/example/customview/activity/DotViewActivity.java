package com.example.customview.activity;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.R;
import com.example.dotview.DotTextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 支持对任意 View 添加未读消息红点
 * https://github.com/hongtaoStudio/DotView
 */
public class DotViewActivity extends AppCompatActivity {

    @BindView(R.id.tv_dot)
    DotTextView tvDot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_view);
        ButterKnife.bind(this);

        tvDot.setIsShow(false);

    }
}
