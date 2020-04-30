package com.zx.customview.widgets.fold;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ActivityUtils;
import com.zx.customview.R;
import com.zx.customview.widgets.fold.two.SampleTwoActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 收集折叠组件, 尽可能理解其思路;
 */
public class FoldActivity extends AppCompatActivity {

    @BindView(R.id.btn_one)
    Button btnOne;
    @BindView(R.id.btn_two)
    Button btnTwo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fold2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.btn_one, R.id.btn_two})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_one:
                ActivityUtils.startActivity(SampleOneActivity.class);
                break;
            case R.id.btn_two:
                ActivityUtils.startActivity(SampleTwoActivity.class);
                break;
        }
    }
}
