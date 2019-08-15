package com.rorbin.customview.qqmenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.rorbin.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class QQMenuActivity extends AppCompatActivity {

    @BindView(R.id.qqMenu)
    QQMenu qqMenu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qqmenu);
        ButterKnife.bind(this);

        qqMenu.setImages(R.drawable.skin_tab_icon_conversation_normal,
                R.drawable.skin_tab_icon_conversation_selected,
                R.drawable.rvq, R.drawable.rvr);

        qqMenu.setOnMenuClickListener(new QQMenu.OnMenuClickListener() {
            @Override
            public void onOnItemClick(View view) {
                ToastUtils.showShort("click = " + qqMenu.isHasClick());
            }
        });
    }
}
