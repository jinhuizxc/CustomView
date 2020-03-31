package com.example.customview.animation_btn;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Button;

import com.blankj.utilcode.util.ToastUtils;
import com.example.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnimationBtnActivity extends AppCompatActivity {

    @BindView(R.id.animation_btn)
    AnimationButton animationBtn;
    @BindView(R.id.okView)
    OkView okView;
    @BindView(R.id.btn_ok)
    Button btnOk;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation_btn);
        ButterKnife.bind(this);

        animationBtn.setAnimationButtonListener(new AnimationButton.AnimationButtonListener() {
            @Override
            public void onClickListener() {
                animationBtn.start();
            }

            @Override
            public void animationFinish() {
                ToastUtils.showShort("动画执行完毕");
                animationBtn.reset();
            }
        });
    }

    @OnClick(R.id.btn_ok)
    public void onViewClicked() {
        okView.start();
    }
}
