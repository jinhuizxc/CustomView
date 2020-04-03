package com.example.customview.expand_edittext;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.R;
import com.example.customview.expand_edittext.tools.ActivityTools;
import com.example.customview.expand_edittext.tools.FileTools;
import com.example.customview.expand_edittext.tools.ImageTools;
import com.example.customview.expand_edittext.tools.ToastTools;
import com.example.expandedittext.ExpandEditText;
import com.example.expandedittext.entity.ImageEntity;
import com.example.expandedittext.listener.OnExpandImageClickListener;
import com.example.expandedittext.utils.InputMethodUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * edittext 图文混排
 */
public class ExpandEditTextActivity extends AppCompatActivity implements OnExpandImageClickListener {

    @BindView(R.id.id_edittext)
    ExpandEditText expandEditTextView;
    @BindView(R.id.id_choosebutton)
    Button idChoosebutton;
    @BindView(R.id.id_clearbutton)
    Button idClearbutton;
    @BindView(R.id.id_parsebutton)
    Button idParsebutton;

    private static final int REQUEST_CODE = 1;

    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            FileTools.chooseFiles(ExpandEditTextActivity.this, REQUEST_CODE);
            return false;
        }
    });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_edit_text);
        ButterKnife.bind(this);

        init();
    }

    private void init() {
        expandEditTextView.bind(this)
                .setOnExpandImageClickListener(this)
                .setHintText("说些什么吧...");
    }

    @OnClick({R.id.id_choosebutton, R.id.id_clearbutton, R.id.id_parsebutton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_choosebutton:
                InputMethodUtils.close(this);
                new Timer().schedule(new TimerTask() {
                    @Override
                    public void run() {
                        mHandler.sendEmptyMessage(0x123);
                    }
                }, 50);
                break;
            case R.id.id_clearbutton:
                expandEditTextView.clear();
                expandEditTextView.createEditEntity(0);
                break;
            case R.id.id_parsebutton:
                ActivityTools.toActivity(ExpandEditTextActivity.this, ParseActivity.class);
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {  // 取消
            return;
        }
        switch (requestCode) {
            case REQUEST_CODE:
                Uri uri = intent.getData();
                String path = ImageTools.getPathFromUri(ExpandEditTextActivity.this, uri);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                expandEditTextView.insertBitmap(bitmap, path);
                break;
        }
    }


    @Override
    public void onImageClick(View view, ImageEntity imageEntity) {
        ToastTools.show(this, "url: " + imageEntity.getText());
    }
}
