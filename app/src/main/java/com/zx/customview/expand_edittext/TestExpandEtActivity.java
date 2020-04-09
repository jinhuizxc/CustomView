package com.zx.customview.expand_edittext;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.zx.customview.R;
import com.zx.customview.app.App;
import com.zx.customview.expand_edittext.tools.ActivityTools;
import com.zx.customview.expand_edittext.tools.FileTools;
import com.zx.customview.expand_edittext.tools.ImageTools;
import com.zx.expandedittext.ExpandEditText;
import com.zx.expandedittext.entity.ImageEntity;
import com.zx.expandedittext.listener.OnExpandImageClickListener;
import com.zx.expandedittext.utils.InputMethodUtils;


import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;

/**
 * 一个支持图文混排的EditText
 * https://github.com/nmyangmo/ExpandEditText
 */
public class TestExpandEtActivity extends AppCompatActivity implements OnExpandImageClickListener {

    @BindView(R.id.id_edittext)
    ExpandEditText expandEditText;
    @BindView(R.id.id_choosebutton)
    Button idChoosebutton;
    @BindView(R.id.id_clearbutton)
    Button idClearbutton;
    @BindView(R.id.id_parsebutton)
    Button idParsebutton;

    public static final int CODE_OPEN_IMAGE = 1;

    private static final String TAG = "TestExpandEtActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_expand_edittext);
        ButterKnife.bind(this);

        initView();
    }


    private void initView() {
        expandEditText.bind(this)
                .setOnExpandImageClickListener(this)
                .setHintText("说些什么吧~");
    }

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            FileTools.chooseFiles(TestExpandEtActivity.this, CODE_OPEN_IMAGE);
            return false;
        }
    });

    @OnClick({R.id.id_choosebutton, R.id.id_clearbutton, R.id.id_parsebutton})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.id_choosebutton:
                // 请求存储权限
                HiPermission.create(App.getApp())
                        .checkSinglePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                            @Override
                            public void onClose() {
                                Log.i(TAG, "onClose");
                                ToastUtils.showShort("They cancelled our request");
                            }

                            @Override
                            public void onFinish() {
                                ToastUtils.showShort("All permissions requested completed");
                            }

                            @Override
                            public void onDeny(String permission, int position) {
                                Log.i(TAG, "onDeny");
                            }

                            @Override
                            public void onGuarantee(String permission, int position) {
                                Log.i(TAG, "onGuarantee");
                                InputMethodUtils.close(TestExpandEtActivity.this);
                                new Timer().schedule(new TimerTask() {
                                    @Override
                                    public void run() {
                                        handler.sendEmptyMessage(0x123);
                                    }
                                }, 50);
                            }
                        });

                break;
            case R.id.id_clearbutton:
                expandEditText.clear();
                expandEditText.createEditEntity(0);
                break;
            case R.id.id_parsebutton:
                ActivityTools.toActivity(TestExpandEtActivity.this, ParseActivity.class);
                finish();
                break;
        }
    }

    @Override
    public void onImageClick(View view, ImageEntity imageEntity) {
        ToastUtils.showShort("url:" + imageEntity.getText());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {

        // 用户没有进行有效的设置操作，返回
        if (resultCode == RESULT_CANCELED) {// 取消
            return;
        }
        switch (requestCode) {
            case CODE_OPEN_IMAGE:
                Uri uri = intent.getData();
                String path = ImageTools.getPathFromUri(TestExpandEtActivity.this, uri);
                Bitmap bitmap = BitmapFactory.decodeFile(path);
                // 插入图片
                expandEditText.insertBitmap(bitmap, path);
                break;

        }
        super.onActivityResult(requestCode, resultCode, intent);
    }
}
