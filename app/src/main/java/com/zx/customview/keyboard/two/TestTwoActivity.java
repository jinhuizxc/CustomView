package com.zx.customview.keyboard.two;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.zx.customview.R;

/**
 * android 实现按钮浮动在键盘上方
 * https://blog.csdn.net/weixin_41392105/article/details/105010624?fps=1&locationNum=2
 */
public class TestTwoActivity extends AppCompatActivity {


    Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_two);

        btn = findViewById(R.id.btn);

        initFloatBtn();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initFloatBtn();
            }
        });

    }

    private void initFloatBtn() {
        FloatBtnUtil floatBtnUtil = new FloatBtnUtil(this);
        RelativeLayout rl = this.findViewById(R.id.rl_Root);
        floatBtnUtil.setFloatView(rl, btn);
//        FrameLayout frameLayout = this.findViewById(R.id.contentView);
//        floatBtnUtil.setFloatView(frameLayout, btn);
    }


}
