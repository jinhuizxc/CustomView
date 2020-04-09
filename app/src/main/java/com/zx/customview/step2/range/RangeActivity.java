package com.zx.customview.step2.range;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.zx.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/24.
 * Email:1004260403@qq.com
 * <p>
 * 自定义控件之绘图篇（三）：区域（Range）
 */

public class RangeActivity extends AppCompatActivity {

    @BindView(R.id.bt_set)
    Button btSet;
    @BindView(R.id.bt_other)
    Button btOther;
    @BindView(R.id.bt_no_set)
    Button btNoSet;
    @BindView(R.id.bt_SetPath)
    Button btSetPath;
    @BindView(R.id.bt_intersect)
    Button btIntersect;
    @BindView(R.id.bt_difference)
    Button btDifference;
    @BindView(R.id.bt_replace)
    Button btReplace;
    @BindView(R.id.bt_reverse_difference)
    Button btReverseDifference;
    @BindView(R.id.bt_union)
    Button btUnion;
    @BindView(R.id.bt_xor)
    Button btXor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_set, R.id.bt_other, R.id.bt_no_set,
            R.id.bt_SetPath, R.id.bt_intersect, R.id.bt_difference, R.id.bt_replace,
            R.id.bt_reverse_difference, R.id.bt_union, R.id.bt_xor})
    public void onViewClicked(View view) {
        RegionView.DrawMode drawMode = null;
        switch (view.getId()) {
            case R.id.bt_no_set:
                drawMode = RegionView.DrawMode.NoSet;
                break;
            case R.id.bt_set:
                drawMode = RegionView.DrawMode.Set;
                break;
            case R.id.bt_SetPath:
                drawMode = RegionView.DrawMode.SetPath;
                break;
            case R.id.bt_other:
                drawMode = RegionView.DrawMode.Other;
                break;
            case R.id.bt_intersect:
                drawMode = RegionView.DrawMode.Intersect;
                break;
            case R.id.bt_difference:
                drawMode = RegionView.DrawMode.Difference;
                break;
            case R.id.bt_replace:
                drawMode = RegionView.DrawMode.Replace;
                break;
            case R.id.bt_reverse_difference:
                drawMode = RegionView.DrawMode.Reverse_difference;
                break;
            case R.id.bt_union:
                drawMode = RegionView.DrawMode.Union;
                break;
            case R.id.bt_xor:
                drawMode = RegionView.DrawMode.xor;
                break;
        }

        if(drawMode == null){
            Toast.makeText(this, "drawMode == null", Toast.LENGTH_SHORT).show();
        }else if(view.getId() == R.id.bt_other) {
            Toast.makeText(this, "见代码注释", Toast.LENGTH_SHORT).show();
        }else {
            Intent intent = new Intent(this, CanvasRangeActivity.class);
            intent.putExtra("drawMode", drawMode.getValue());
            startActivity(intent);
        }


    }

}
