package com.zx.customview.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.InputMethodManager;

import com.blankj.utilcode.util.ToastUtils;
import com.sendtion.xrichtext.RichTextEditor;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.internal.entity.CaptureStrategy;
import com.zx.customview.R;
import com.zx.customview.app.App;
import com.zx.customview.app.Constants;
import com.zx.customview.choose.Glide4Engine;
import com.zx.customview.utils.CommonUtil;
import com.zx.customview.utils.ImageUtils;
import com.zx.customview.utils.SDCardUtil;
import com.zx.customview.utils.ToastUtil;
import com.zx.expandedittext.utils.InputMethodUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import me.weyye.hipermission.HiPermission;
import me.weyye.hipermission.PermissionCallback;

public class NewActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_CHOOSE = 23;//定义请求码常量

    private ProgressDialog insertDialog;
    private RichTextEditor et_new_content;

    private int screenWidth;
    private int screenHeight;

    private Disposable disposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);

        initView();
    }

    private void initView() {

        insertDialog = new ProgressDialog(this);
        insertDialog.setMessage("正在插入图片...");
        insertDialog.setCanceledOnTouchOutside(false);

        et_new_content = findViewById(R.id.et_new_content);

        screenWidth = CommonUtil.getScreenWidth(this);
        screenHeight = CommonUtil.getScreenHeight(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_image:
                closeSoftKeyInput();//关闭软键盘
                callGallery();
                break;
            case R.id.action_new_save:
                /*try {
                    saveNoteData(false);
                } catch (Exception e) {
                    e.printStackTrace();
                }*/
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 调用图库选择
     */
    private void callGallery(){
//        //调用系统图库
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");// 相片类型
//        startActivityForResult(intent, 1);

       /* Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))//照片视频全部显示MimeType.allOf()
                .countable(true)//true:选中后显示数字;false:选中后显示对号
                .maxSelectable(3)//最大选择数量为9
                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))//图片显示表格的大小
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//图像选择和预览活动所需的方向
                .thumbnailScale(0.85f)//缩放比例
                .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                .imageEngine(new Glide4Engine())//图片加载方式，Glide4需要自定义实现
                .capture(true) //是否提供拍照功能，兼容7.0系统需要下面的配置
                //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                .captureStrategy(new CaptureStrategy(true,"com.sendtion.matisse.fileprovider"))//存储到哪里
                .forResult(REQUEST_CODE_CHOOSE);//请求码*/

        HiPermission.create(App.getApp())
                .checkSinglePermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, new PermissionCallback() {
                    @Override
                    public void onClose() {
                        Log.i("tag", "onClose");
                    }

                    @Override
                    public void onFinish() {
                        ToastUtils.showShort("All permissions requested completed");
                    }

                    @Override
                    public void onDeny(String permission, int position) {
                        Log.i("tag", "onDeny");
                    }

                    @Override
                    public void onGuarantee(String permission, int position) {
                        Log.i("tag", "onGuarantee");
                        InputMethodUtils.close(NewActivity.this);

                        Matisse.from(NewActivity.this)
                                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG, MimeType.GIF))//照片视频全部显示MimeType.allOf()
                                .countable(true)//true:选中后显示数字;false:选中后显示对号
                                .maxSelectable(3)//最大选择数量为9
                                //.addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                .gridExpectedSize(getResources().getDimensionPixelSize(R.dimen.grid_expected_size))//图片显示表格的大小
                                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)//图像选择和预览活动所需的方向
                                .thumbnailScale(0.85f)//缩放比例
                                .theme(R.style.Matisse_Zhihu)//主题  暗色主题 R.style.Matisse_Dracula
                                .imageEngine(new Glide4Engine())//图片加载方式，Glide4需要自定义实现
                                .capture(true) //是否提供拍照功能，兼容7.0系统需要下面的配置
                                //参数1 true表示拍照存储在共有目录，false表示存储在私有目录；参数2与 AndroidManifest中authorities值相同，用于适配7.0系统 必须设置
                                .captureStrategy(new CaptureStrategy(true, Constants.getFileProviderName()))//存储到哪里
                                .forResult(REQUEST_CODE_CHOOSE);//请求码

                    }
                });
    }

    /**
     * 关闭软键盘
     */
    private void closeSoftKeyInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        //boolean isOpen=imm.isActive();//isOpen若返回true，则表示输入法打开
        if (imm != null && imm.isActive() && getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
            //imm.hideSoftInputFromInputMethod();//据说无效
            //imm.hideSoftInputFromWindow(et_content.getWindowToken(), 0); //强制隐藏键盘
            //如果输入法在窗口上已经显示，则隐藏，反之则显示
            //imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (data != null) {
                if (requestCode == 1){
                    //处理调用系统图库
                } else if (requestCode == REQUEST_CODE_CHOOSE){
                    //异步方式插入图片
                    insertImagesSync(data);
                }
            }
        }
    }

    /**
     * 异步方式插入图片
     */
    private void insertImagesSync(final Intent data){
        insertDialog.show();

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) {
                try{
                    et_new_content.measure(0, 0);
                    List<Uri> mSelected = Matisse.obtainResult(data);
                    // 可以同时插入多张图片
                    for (Uri imageUri : mSelected) {
                        String imagePath = SDCardUtil.getFilePathFromUri(NewActivity.this,  imageUri);
                        //Log.e(TAG, "###path=" + imagePath);
                        Bitmap bitmap = ImageUtils.getSmallBitmap(imagePath, screenWidth, screenHeight);//压缩图片
                        //bitmap = BitmapFactory.decodeFile(imagePath);
                        imagePath = SDCardUtil.saveToSdCard(bitmap);
                        //Log.e(TAG, "###imagePath="+imagePath);
                        emitter.onNext(imagePath);
                    }

                    // 测试插入网络图片 http://pics.sc.chinaz.com/files/pic/pic9/201904/zzpic17414.jpg
                    //emitter.onNext("http://pics.sc.chinaz.com/files/pic/pic9/201903/zzpic16838.jpg");
                   /* emitter.onNext("http://b.zol-img.com.cn/sjbizhi/images/10/640x1136/1572123845476.jpg");
                    emitter.onNext("https://img.ivsky.com/img/tupian/pre/201903/24/richu_riluo-013.jpg");*/

                    emitter.onComplete();
                }catch (Exception e){
                    e.printStackTrace();
                    emitter.onError(e);
                }
            }
        })
                //.onBackpressureBuffer()
                .subscribeOn(Schedulers.io())//生产事件在io
                .observeOn(AndroidSchedulers.mainThread())//消费事件在UI线程
                .subscribe(new Observer<String>() {
                    @Override
                    public void onComplete() {
                        if (insertDialog != null && insertDialog.isShowing()) {
                            insertDialog.dismiss();
                        }
                        ToastUtils.showShort("图片插入成功");
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (insertDialog != null && insertDialog.isShowing()) {
                            insertDialog.dismiss();
                        }
                        ToastUtils.showShort("图片插入失败:"+e.getMessage());
                    }

                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable = d;
                    }

                    @Override
                    public void onNext(String imagePath) {
                        et_new_content.insertImage(imagePath);
                    }
                });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (disposable != null && disposable.isDisposed()){
            disposable.dispose();
        }
    }
}
