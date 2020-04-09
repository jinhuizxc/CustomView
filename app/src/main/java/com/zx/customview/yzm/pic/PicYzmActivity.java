package com.zx.customview.yzm.pic;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.zx.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PicYzmActivity extends AppCompatActivity {

    @BindView(R.id.pic_yzm)
    CustomView picYzm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pic_yzm);
        ButterKnife.bind(this);

    }

    @OnClick(R.id.pic_yzm)
    public void onViewClicked() {
        String yzmCode = picYzm.createCode();
        ToastUtils.showShort("生成验证码: " + yzmCode);
    }
}
