package com.example.customview.step1.animation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.TextView;

import com.example.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/5.
 * Email:1004260403@qq.com
 */

public class AnimationActivity extends AppCompatActivity {

    @BindView(R.id.tv)
    TextView tv;
    @BindView(R.id.bt_alpha_anim)
    Button btAlphaAnim;
    @BindView(R.id.bt_scale_anim)
    Button btScaleAnim;
    @BindView(R.id.bt_translate_anim)
    Button btTranslateAnim;
    @BindView(R.id.bt_rotate_anim)
    Button btRotateAnim;
    @BindView(R.id.bt_set_anim)
    Button btSetAnim;
    @BindView(R.id.bt_interpolator)
    Button btInterpolator;

    ScaleAnimation scaleAnim;
    AlphaAnimation alphaAnim;
    RotateAnimation rotateAnim;
    TranslateAnimation translateAnim;
    AnimationSet setAnim;

    ScaleAnimation interpolateScaleAnim;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_animation);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.bt_alpha_anim, R.id.bt_scale_anim, R.id.bt_translate_anim, R.id.bt_rotate_anim, R.id.bt_set_anim, R.id.bt_interpolator})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_alpha_anim:
                // AlphaAnimation
                alphaAnim = new AlphaAnimation(1.0f, 0.1f);
                alphaAnim.setDuration(3000);
                alphaAnim.setFillBefore(true);
                tv.startAnimation(alphaAnim);
                break;
            case R.id.bt_scale_anim:
                // ScaleAnimation
                scaleAnim = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnim.setDuration(700);
                tv.startAnimation(scaleAnim);
                break;
            case R.id.bt_translate_anim:
                // TranslateAnimation
                translateAnim = new TranslateAnimation(Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -80,
                        Animation.ABSOLUTE, 0, Animation.ABSOLUTE, -80);
                translateAnim.setDuration(2000);
                translateAnim.setFillBefore(true);
                tv.startAnimation(translateAnim);
                break;
            case R.id.bt_rotate_anim:
                // RotateAnimation
                rotateAnim = new RotateAnimation(0, -650, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnim.setDuration(3000);
                rotateAnim.setFillAfter(true);
                tv.startAnimation(rotateAnim);
                break;
            case R.id.bt_set_anim:
                // AnimationSet
                alphaAnim = new AlphaAnimation(1.0f, 0.1f);
                scaleAnim = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnim = new RotateAnimation(0, 720, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);

                setAnim = new AnimationSet(true);
                setAnim.addAnimation(alphaAnim);
                setAnim.addAnimation(scaleAnim);
                setAnim.addAnimation(rotateAnim);
                setAnim.setDuration(3000);
                setAnim.setFillAfter(true);
                tv.startAnimation(setAnim);
                break;
            case R.id.bt_interpolator:
                // Interpolator使用
                interpolateScaleAnim = new ScaleAnimation(0.0f, 1.4f, 0.0f, 1.4f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                // Interpolator使用——setInterpolator方法
                interpolateScaleAnim.setInterpolator(new BounceInterpolator());
                interpolateScaleAnim.setDuration(3000);
                tv.startAnimation(interpolateScaleAnim);
                break;
        }
    }
}
