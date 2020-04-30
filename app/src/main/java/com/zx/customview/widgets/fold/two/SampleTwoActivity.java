package com.zx.customview.widgets.fold.two;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.expandable.ExpandableTextView4List;
import com.zx.customview.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SampleTwoActivity extends AppCompatActivity {

    @BindView(R.id.btn_test1)
    Button btnTest1;
    @BindView(R.id.btn_test2)
    Button btnTest2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sample_two);
        ButterKnife.bind(this);

    }

    @OnClick({R.id.btn_test1, R.id.btn_test2})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_test1:
                ActivityUtils.startActivity(Test1Activity.class);
                break;
            case R.id.btn_test2:
                ActivityUtils.startActivity(Test2Activity.class);
                break;
        }
    }


}
