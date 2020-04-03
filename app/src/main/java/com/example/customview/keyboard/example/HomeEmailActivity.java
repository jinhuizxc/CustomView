package com.example.customview.keyboard.example;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.R;
import com.example.customview.keyboard.way1.statusbar.StatusBarCompat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 采用方式1看下项目展示效果
 */
public class HomeEmailActivity extends AppCompatActivity {


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.bt_send)
    Button btSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), 40);
        setContentView(R.layout.activity_home_email);
        ButterKnife.bind(this);

        initData();
        initListener();
    }

    private void initData() {


    }

    private void initListener() {
        /*mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mImageButton.setVisibility(View.GONE);
//                    mSendTv.setVisibility(View.VISIBLE);
                } else {
                    mImageButton.setVisibility(View.VISIBLE);
//                    mSendTv.setVisibility(View.GONE);
                }
            }
        });*/

    }


    @OnClick({R.id.ll_back, R.id.bt_send})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.bt_send:
                break;
        }
    }
}
