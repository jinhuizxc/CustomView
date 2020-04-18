package com.zx.customview.image_et;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.orhanobut.logger.Logger;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zhihu.matisse.listener.OnCheckedListener;
import com.zhihu.matisse.listener.OnSelectedListener;
import com.zx.customview.R;
import com.zx.customview.app.App;
import com.zx.customview.image_et.choose.GifSizeFilter;
import com.zx.customview.image_et.choose.Glide4Engine;
import com.zx.customview.image_et.view.EditTextPlus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 带插入图片功能的EditText
 * https://www.jianshu.com/p/25e027e4364e
 */
public class ImgEditTextActivity extends AppCompatActivity {

    @BindView(R.id.edit_text)
    EditTextPlus editText;
    @BindView(R.id.add_image)
    ImageView addImage;
    @BindView(R.id.get_image)
    Button getImage;
    @BindView(R.id.functionBar)
    RelativeLayout functionBar;

    // 选择相册请求码
    private static final int REQUEST_CODE_CHOOSE = 23;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img_edit_text);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.add_image, R.id.get_image})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.add_image:
                RxPermissions rxPermissions = new RxPermissions(this);
                rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        .subscribe(aBoolean -> {
                            if (aBoolean) {
                                gotoAlbum();
                            } else {
                                Toast.makeText(ImgEditTextActivity.this, R.string.permission_request_denied, Toast.LENGTH_LONG)
                                        .show();
                            }
                        }, Throwable::printStackTrace);

                break;
            case R.id.get_image:
                if (editText != null) {
                    Log.e("-----", "获取插入的图片集合:");
                    for (String path : editText.getImage()) {
                        Logger.d("获取插入的图片集合:" + path + "\n");
                    }
                }
                break;
        }
    }

    private void gotoAlbum() {
        Matisse.from(this)
                .choose(MimeType.ofImage(), false)
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, App.getFileProviderName()))
                .maxSelectable(9)
                .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(
                        getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
//                                            .imageEngine(new GlideEngine())  // for glide-V3
                .imageEngine(new Glide4Engine())    // for glide-V4
                .setOnSelectedListener(new OnSelectedListener() {
                    @Override
                    public void onSelected(
                            @NonNull List<Uri> uriList, @NonNull List<String> pathList) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("onSelected", "onSelected: pathList=" + pathList);

                    }
                })
                .originalEnable(true)
                .maxOriginalSize(10)
//                                            .autoHideToolbarOnSingleTap(true)
                .setOnCheckedListener(new OnCheckedListener() {
                    @Override
                    public void onCheck(boolean isChecked) {
                        // DO SOMETHING IMMEDIATELY HERE
                        Log.e("isChecked", "onCheck: isChecked=" + isChecked);
                    }
                })
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Logger.d("onActivityResult req = " + requestCode + ", " + "resultCode = " + resultCode);
        switch (requestCode) {
            case REQUEST_CODE_CHOOSE:
                if (resultCode == Activity.RESULT_OK) {
//                    Logger.d("选择相册返回结果: " + Matisse.obtainResult(data), Matisse.obtainPathResult(data));
                    try {
                        List<String> paths = Matisse.obtainPathResult(data);
                        Logger.d("选择相册返回结果 path : " + paths);
                        // TODO 将多张图片发送出去;
                        if (paths.size() > 1) {
                            // 多张图片
                            for (String path : paths) {
                                Logger.d("获取的图片路径 多张图片: " + path);
                                List<String> data1 = new ArrayList<>();
                                data1.add(path);
                                editText.addImage(data1);
                            }
                        } else {
                            Logger.d("获取的图片路径 多张图片: " + paths.get(0));
                            List<String> data1 = new ArrayList<>();
                            data1.add(paths.get(0));
                            editText.addImage(data1);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Logger.d("选择相册, 失败");
                }
                break;
            default:
                break;
        }
    }

}
