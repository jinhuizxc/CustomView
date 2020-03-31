package com.example.richeditor;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.Nullable;
import android.text.Editable;
import android.text.Spannable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.richeditor.utils.DialogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义的富文本
 */
public class FuncEditView extends LinearLayout implements View.OnClickListener {

    private int color[] = {R.color.tp_black, R.color.tp_red, R.color.tp_orange,
            R.color.tp_green, R.color.tp_blues, R.color.tp_purple};
    private int color_[] = {0xFF000000, 0xFFed2e2e, 0xFFed9a2e,
            0xFF42d153, 0xFF2da4e8, 0xFFd02de8};
    private Activity mContext;
    private EditText funcEdit;
    private ImageView mBold, mLean, mUnderLine, mImage, mLight, mDelete;
    private GridView mGridView;

    private int startPosition;
    private int startCount;


    private int currentColorPosition = 0;

    private boolean isLongClick = false; //用户是否进行长按

    private boolean isBold = false; //是否加粗
    private boolean isClickBold = false;

    private boolean isLean = false; //是否倾斜
    private boolean isClickLean = false;

    private boolean isUnderLine = false; //是否下划线
    private boolean isClickUnderLine = false;


    private boolean isLight = false;  //是否高亮
    private boolean isClickLight = false;

    private boolean isDelete = false; //是否删除线
    private boolean isClickDelete = false;

    private List<Integer> imgPosition = new ArrayList<>();  //插入图片位置
    private List<String> imgUrl = new ArrayList<>(); //插入图片路径
    private Editable content;

    public byte IMG_CODE = 127;
    public byte CAPTURE_CODE = 126;



    public FuncEditView(Context context) {
        this(context, null);
    }

    public FuncEditView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FuncEditView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init((Activity) context);
    }



    private void init(Activity context) {
        mContext = context;
        View view = inflate(context, R.layout.func_editview, this);
        funcEdit = (EditText) view.findViewById(R.id.func_edit);
        mBold = (ImageView) view.findViewById(R.id.btn_bold);
        mLean = (ImageView) findViewById(R.id.btn_lean);
        mUnderLine = (ImageView) findViewById(R.id.btn_underline);
        mImage = (ImageView) findViewById(R.id.btn_image);
        mLight = (ImageView) findViewById(R.id.btn_light);
        mDelete = (ImageView) findViewById(R.id.btn_delete);
        forEditText();
        setOnClick();
    }

    private void forEditText() {

        funcEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String s1 = s.toString().substring(start, start + count);
                if (s1.startsWith("<img src")) {  //输入的是图片
                    imgPosition.add(start);
                    String img_url = s1.substring(10, s1.length() - 4);
                    imgUrl.add(img_url);
                } else {
                    if (count > 1) {
                        for (int i = start; i < start + count; i++) {
                            onTextChanged(s, i, before, 1);
                        }
                    }
                }
                startPosition = start;
                startCount = count;
                /*if (before > 0) { //在删除时把所有的字体设置为零
                    isBold = false;
                    mBold.setTextColor(getResources().getColor(R.color.tp_black));
                    isClickBold = false;
                }*/
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (isBold) {  //加粗
                    for (int i = startPosition; i < startPosition + startCount; i++) {
                        s.setSpan(new StyleSpan(Typeface.BOLD), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }

                }
                if (isLean) {  //倾斜
                    for (int i = startPosition; i < startPosition + startCount; i++) {
                        s.setSpan(new StyleSpan(Typeface.ITALIC), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                if (isUnderLine) { //下划线
                    for (int i = startPosition; i < startPosition + startCount; i++) {
                        s.setSpan(new UnderlineSpan(), i, i + 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                if (isLight) { //高亮
                    for (int i = startPosition; i < startPosition + startCount; i++) {
                        s.setSpan(new ForegroundColorSpan(color_[currentColorPosition]), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }

                if (isDelete) {  //删除线
                    for (int i = startPosition; i < startPosition + startCount; i++) {
                        s.setSpan(new StrikethroughSpan(), i, i + 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                }
                content = s;
            }
        });

        funcEdit.setHighlightColor(getResources().getColor(R.color.tp_blue));  //设置选中背景颜色
        funcEdit.setOnLongClickListener(new OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                isLongClick = true;
                //隐藏软键盘
                InputMethodManager inputManager = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(mContext.getCurrentFocus().getWindowToken(), 0);
                return false;
            }
        });

    }

    private void setOnClick() {
        mBold.setOnClickListener(this);
        mLean.setOnClickListener(this);
        mUnderLine.setOnClickListener(this);
        mLight.setOnClickListener(this);
        mImage.setOnClickListener(this);
        mDelete.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btn_bold) {
            Log.e("123", funcEdit.getSelectionStart() + "@@@" + funcEdit.getSelectionEnd() + "ccc" + isLongClick);
            if (isClickBold) {
                mBold.setImageResource(R.mipmap.bold);
                isBold = false;
            } else {  //加粗
                mBold.setImageResource(R.mipmap.bold_);
                isBold = true;
            }
            isClickBold = !isClickBold;
            if (isLongClick) {
                isLongClick = false;
                content.setSpan(new StyleSpan(Typeface.BOLD), funcEdit.getSelectionStart(), funcEdit.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                funcEdit.setSelection(funcEdit.getSelectionEnd());
            }
        } else if (id == R.id.btn_lean) {
            if (isClickLean) {
                mLean.setImageResource(R.mipmap.lean);
                isLean = false;
            } else {  //倾斜
                mLean.setImageResource(R.mipmap.lean_);
                isLean = true;
            }
            isClickLean = !isClickLean;
            if (isLongClick) {
                isLongClick = false;
                content.setSpan(new StyleSpan(Typeface.ITALIC), funcEdit.getSelectionStart(), funcEdit.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                funcEdit.setSelection(funcEdit.getSelectionEnd());
            }
        } else if (id == R.id.btn_underline) {
            if (isClickUnderLine) {
                mUnderLine.setImageResource(R.mipmap.underline);
                isUnderLine = false;
            } else {  //下划线
                mUnderLine.setImageResource(R.mipmap.underline_);
                isUnderLine = true;
            }
            isClickUnderLine = !isClickUnderLine;
            if (isLongClick) {
                isLongClick = false;
                content.setSpan(new UnderlineSpan(), funcEdit.getSelectionStart(), funcEdit.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                funcEdit.setSelection(funcEdit.getSelectionEnd());
            }
        } else if (id == R.id.btn_light) {
            DialogUtil.setGravity(Gravity.CENTER);
            View view = DialogUtil.show(mContext, R.layout.edit_color);
            mGridView = (GridView) view.findViewById(R.id.gv_color);
            mGridView.setSelector(new ColorDrawable(Color.TRANSPARENT));
            mGridView.setAdapter(new ColorAdapter());
            mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    isLight = true;
                    currentColorPosition = position;
                    setLightColor();
                    DialogUtil.dismisss();
                }
            });
        } else if (id == R.id.btn_delete) {
            if (isClickDelete) {
                mDelete.setImageResource(R.mipmap.delete);
                isDelete = false;
            } else {  //删除线
                mDelete.setImageResource(R.mipmap.delete_);
                isDelete = true;
            }
            isClickDelete = !isClickDelete;
            if (isLongClick) {
                isLongClick = false;
                content.setSpan(new StrikethroughSpan(), funcEdit.getSelectionStart(), funcEdit.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                funcEdit.setSelection(funcEdit.getSelectionEnd());
            }
        } else if (id == R.id.btn_image) {
            repayState();
            chooseImage();
        }
    }

    private void setLightColor() {
        /*if (isClickLight) {
            mLight.setImageResource(R.mipmap.light);
            isLight = false;
        } else {  //高亮
            mLight.setImageResource(R.mipmap.light_);
            isLight = true;
        }
        isClickLight = !isClickLight;*/
        if (isLongClick) {
            isLongClick = false;
            content.setSpan(new ForegroundColorSpan(color_[currentColorPosition]), funcEdit.getSelectionStart(), funcEdit.getSelectionEnd(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            funcEdit.setSelection(funcEdit.getSelectionEnd());
        }
    }

    private void chooseImage() {
        CharSequence item[] = {"手机相册", "相机拍摄"};
        AlertDialog dialog = new AlertDialog.Builder(mContext).setTitle("选择图片").setItems(item, new DialogInterface.OnClickListener() {
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
        }).create();
        dialog.show();
    }

    private void searchImg(byte whichWay) {
        if (whichWay == IMG_CODE) {
            Intent intent = new Intent();
            /* 开启Pictures画面Type设定为image */
            intent.setType("image/*");
            /* 使用Intent.ACTION_GET_CONTENT这个Action */
            intent.setAction(Intent.ACTION_GET_CONTENT);
            /* 取得相片后返回本画面 */
            mContext.startActivityForResult(intent, whichWay);
        } else {
            Intent takephoto = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            mContext.startActivityForResult(takephoto, CAPTURE_CODE);
        }
    }

    /**
     * 当点击图片时还原
     */
    private void repayState() {
        isBold = false; //加粗
        isClickBold = false;
        mBold.setImageResource(R.mipmap.bold);

        isLean = false; //倾斜
        isClickLean = false;
        mLean.setImageResource(R.mipmap.lean);

        isUnderLine = false; //下划线
        isClickUnderLine = false;
        mUnderLine.setImageResource(R.mipmap.underline);

        isLight = false;  //高亮
        isClickLight = false;
        mLight.setImageResource(R.mipmap.light);

        isDelete = false; //删除线
        isClickDelete = false;
        mDelete.setImageResource(R.mipmap.delete);
    }


    private class ColorAdapter extends BaseAdapter {
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
            View view = LayoutInflater.from(mContext).inflate(R.layout.gv_item_color, parent, false);
            ImageView gvItem = (ImageView) view.findViewById(R.id.iv_item_color);
            gvItem.setImageResource(color[position]);
            return view;
        }
    }
}
