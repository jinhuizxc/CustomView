package com.zx.richeditor;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.zx.richeditor.utils.BitmapUtil;
import com.zx.richeditor.utils.DialogUtil;
import com.zx.richeditor.utils.UpLoadPicSaveUtil;
import com.zx.richeditor.utils.UriUtil;
import com.zx.richeditor.webview.RichEditorWebView;

import java.io.IOException;

public class RichEditorWebViewActivity extends AppCompatActivity implements View.OnClickListener {

    private static int screenWidth;
    private static int screenHeight;

    private int color[] = {R.color.tp_black, R.color.tp_red, R.color.tp_orange,
            R.color.tp_green, R.color.tp_blues, R.color.tp_purple};
    private int color_[] = {0xFF000000, 0xFFed2e2e, 0xFFed9a2e,
            0xFF42d153, 0xFF2da4e8, 0xFFd02de8};

    private RichEditorWebView richEditor;

    private boolean isClickBold = false;
    private boolean isClickLean = false;
    private boolean isClickUnderLine = false;
    private boolean isClickDelete = false;
    private boolean isClickListUl = false;
    private boolean isClickListOl = false;
    private boolean isClickSize = false;

    public byte IMG_CODE = 127;
    public byte CAPTURE_CODE = 126;
    private String content = null;

    private ImageView tBold, tLean, tUnderline, tImage, tLight, tDelete, tListUl, tListOl, tSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取屏幕信息
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels;
        setContentView(R.layout.activity_rich_editor);

        initViews();
        initListener();

        // 设置编辑器
        richEditor.setEditorHeight(200);
        richEditor.setFontSize(18);
        richEditor.setEditorFontColor(Color.BLACK);
        richEditor.setPadding(10, 10, 10, 10);
        richEditor.setPlaceholder("请输入内容...");


    }

    private void initListener() {
        tBold.setOnClickListener(this);
        tLean.setOnClickListener(this);
        tUnderline.setOnClickListener(this);
        tImage.setOnClickListener(this);
        tLight.setOnClickListener(this);
        tDelete.setOnClickListener(this);
        tListUl.setOnClickListener(this);
        tListOl.setOnClickListener(this);
        tSize.setOnClickListener(this);
        richEditor.setOnTextChangeListener(new RichEditorWebView.OnTextChangeListener() {
            @Override
            public void onTextChange(String text) {
                if (TextUtils.isEmpty(text)) {
                    reset();
                }
            }
        });
    }


    private void initViews() {
        richEditor = (RichEditorWebView) findViewById(R.id.wv_editor);
        tBold = (ImageView) findViewById(R.id.test_bold);
        tLean = (ImageView) findViewById(R.id.test_lean);
        tUnderline = (ImageView) findViewById(R.id.test_underline);
        tImage = (ImageView) findViewById(R.id.test_image);
        tLight = (ImageView) findViewById(R.id.test_light);
        tDelete = (ImageView) findViewById(R.id.test_delete);
        tListUl = (ImageView) findViewById(R.id.test_list_ul);
        tListOl = (ImageView) findViewById(R.id.test_list_ol);
        tSize = (ImageView) findViewById(R.id.test_font_size);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.test_bold) {
            if (isClickBold) {
                tBold.setImageResource(R.mipmap.bold);
            } else {  //加粗
                tBold.setImageResource(R.mipmap.bold_);
            }
            isClickBold = !isClickBold;
            richEditor.setBold();
        } else if (id == R.id.test_lean) {
            if (isClickLean) {
                tLean.setImageResource(R.mipmap.lean);
            } else {  //倾斜
                tLean.setImageResource(R.mipmap.lean_);
            }
            isClickLean = !isClickLean;
            richEditor.setItalic();
        } else if (id == R.id.test_underline) {
            if (isClickUnderLine) {
                tUnderline.setImageResource(R.mipmap.underline);
            } else {  //下划线
                tUnderline.setImageResource(R.mipmap.underline_);
            }
            isClickUnderLine = !isClickUnderLine;
            richEditor.setUnderline();
        } else if (id == R.id.test_image) {
            if (null == content) {
                Toast.makeText(this, "请先输入文字", Toast.LENGTH_SHORT).show();
                return;
            }
            choosePhoto();
        } else if (id == R.id.test_light) {
            DialogUtil.setGravity(Gravity.CENTER);
            View view = DialogUtil.show(this, R.layout.edit_color);
            GridView mGridView = (GridView) view.findViewById(R.id.gv_color);
            mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            mGridView.setAdapter(new TColorAdapter());
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    richEditor.setTextColor(color_[position]);
                    DialogUtil.dismisss();
                }
            });
        } else if (id == R.id.test_font_size) {
            CharSequence item[] = {"ABC-12", "ABC-14", "ABC-16", "ABC-18（默认）", "ABC-20", "ABC-24"};
            android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).setTitle("选择字体").setItems(item, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            richEditor.setFontSize(1);
                            break;
                        case 1:
                            richEditor.setFontSize(2);
                            break;
                        case 2:
                            richEditor.setFontSize(3);
                            break;
                        case 3:
                            richEditor.setFontSize(4);
                            break;
                        case 4:
                            richEditor.setFontSize(5);
                            break;
                        case 5:
                            richEditor.setFontSize(6);
                            break;
                    }
                }
            }).create();
            dialog.show();
        } else if (id == R.id.test_delete) {
            if (isClickDelete) {
                tDelete.setImageResource(R.mipmap.delete);
            } else {  //删除线
                tDelete.setImageResource(R.mipmap.delete_);
            }
            isClickDelete = !isClickDelete;
            richEditor.setStrikeThrough();
        } else if (id == R.id.test_list_ul) {
            if (isClickListUl) {
                tListUl.setImageResource(R.mipmap.list_ul);
            } else {
                tListUl.setImageResource(R.mipmap.list_ul_);
            }
            isClickListUl = !isClickListUl;
            richEditor.setBullets();
        } else if (id == R.id.test_list_ol) {
            if (isClickListOl) {
                tListOl.setImageResource(R.mipmap.list_ol);
            } else {
                tListOl.setImageResource(R.mipmap.list_ol_);
            }
            isClickListOl = !isClickListOl;
            richEditor.setNumbers();
        }
    }

    private void choosePhoto() {
        CharSequence items[] = {"手机相册", "拍摄"};
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("选择图片")
                .setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:  //手机相册
                                searchImg(IMG_CODE);
                                break;
                            case 1:  //相机拍摄
                                searchImg(CAPTURE_CODE);
                                break;
                        }
                    }
                })
                .create();
        dialog.show();
    }

    private void searchImg(byte img_code) {
        if (img_code == IMG_CODE) {
            Intent intent = new Intent();
            /* 开启Pictures画面Type设定为image */
            intent.setType("image/*");
            /* 使用Intent.ACTION_GET_CONTENT这个Action */
            intent.setAction(Intent.ACTION_GET_CONTENT);
            /* 取得相片后返回本画面 */
            startActivityForResult(intent, img_code);
        } else {
            Intent takephoto = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takephoto, CAPTURE_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            String path = "";
            if (requestCode == CAPTURE_CODE) {  //拍照
                path = getCapturePath(data);
                Log.e("IMG", path + "!!!!!");
            } else {                            //相册
                Bitmap b = BitmapUtil.resizeBitMapImage(UriUtil.getAbsoluteFilePath(this, data.getData()), screenWidth / 10, screenHeight / 15);
                path = UpLoadPicSaveUtil.saveFile(this, b);
                Log.e("IMG", path + "@@@@");
            }
            richEditor.insertImage(path, "dachshund");
        }
    }

    /**
     * 获取裁剪路径
     *
     * @param data
     * @return
     */
    protected String getCapturePath(Intent data) {
        Bitmap bitmap = null;
        if (!data.hasExtra("data")) {
            Uri uri = data.getData();
            try {
                bitmap = BitmapUtil.getBitmapFromUri(this, uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Bundle bundle = data.getExtras();
            Bitmap b = (Bitmap) bundle.get("data");
            bitmap = BitmapUtil.compressImage(b);
        }
        return UpLoadPicSaveUtil.saveFile(this, bitmap);
    }


    //重置方法
    private void reset() {
        if (isClickBold) {
            isClickBold = false;
            tBold.setImageResource(R.mipmap.bold);
        }
        if (isClickLean) {
            isClickLean = false;
            tLean.setImageResource(R.mipmap.lean);
        }
        if (isClickUnderLine) {
            isClickUnderLine = false;
            tUnderline.setImageResource(R.mipmap.underline);
        }
        if (isClickDelete) {
            isClickDelete = false;
            tDelete.setImageResource(R.mipmap.delete);
        }
        if (isClickListUl) {
            isClickListUl = false;
            tListUl.setImageResource(R.mipmap.list_ul);
        }
        if (isClickListOl) {
            isClickListOl = false;
            tListOl.setImageResource(R.mipmap.list_ol);
        }
    }

    private class TColorAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return color.length;
        }

        @Override
        public Object getItem(int position) {
            return position;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            @SuppressLint("ViewHolder")
            View view = LayoutInflater.from(RichEditorWebViewActivity.this).inflate(R.layout.gv_item_color, parent, false);
            ImageView gvItem = (ImageView) view.findViewById(R.id.iv_item_color);
            gvItem.setImageResource(color[position]);
            return view;
        }
    }
}
