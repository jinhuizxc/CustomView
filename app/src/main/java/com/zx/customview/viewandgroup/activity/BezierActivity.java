package com.zx.customview.viewandgroup.activity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.zx.customview.R;
import com.zx.customview.viewandgroup.view.BezierThree;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BezierActivity extends AppCompatActivity {

    @BindView(R.id.bezierThree)
    BezierThree bezierThree;
    @BindView(R.id.control1)
    RadioButton control1;
    @BindView(R.id.control2)
    RadioButton control2;
    @BindView(R.id.radiogroup)
    RadioGroup radiogroup;
    @BindView(R.id.activity_bazier_two)
    LinearLayout activityBazierTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bazier);
        ButterKnife.bind(this);

        radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.control1:
                        bezierThree.setMode(true);
                        break;
                    case R.id.control2:
                        bezierThree.setMode(false);
                        break;
                }
            }
        });
    }
}
