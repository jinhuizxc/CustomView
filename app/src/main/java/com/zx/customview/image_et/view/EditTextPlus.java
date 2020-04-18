package com.zx.customview.image_et.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;

import com.zx.customview.image_et.util.Util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class EditTextPlus extends AppCompatEditText {

    private static final String TAG = "EditTextPlus";
    /**
     * 最大输入字符
     */
    public static final int MAX_LENGTH = 2000;
    /**
     * 一张图片所占的字符长度
     */
    public static final int IMAGE_LENGTH = 2;
    /**
     * 占位符
     */
    private String placeholder = "&";
    /**
     * 最大添加图片数量
     */
    private int maxImageCount = 8;

    private Context mContext;

    private String submitContent = "";
    private boolean insertImage = false;

    private float startY;
    private float startX;
    private float selectionStart;

    // 图片列表
    private List<String> imageLists = new ArrayList<>();


    private OnInsertImageListener onInsertImageListener;
    private OnDeleteImageListener onDeleteImageListener;

    public void setOnDeleteImageListener(OnDeleteImageListener onDeleteImageListener) {
        this.onDeleteImageListener = onDeleteImageListener;
    }

    public void setOnInsertImageListener(OnInsertImageListener onInsertImageListener) {
        this.onInsertImageListener = onInsertImageListener;
    }

    public EditTextPlus(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public EditTextPlus(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public EditTextPlus(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    public void init() {
        // 设置位置
        setGravity(Gravity.TOP);
        addTextChangedListener(watcher);
    }


    private String tempString;
    /**
     * 插入的文本进行监听
     */
    private TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            tempString = s.toString();
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            insertImage = false;
            //如果小于就是删除操作
            //如果小于就是删除操作
            if (s.length() < tempString.length()) {
                String deletString = tempString.substring(start, start + before);
                if (imageLists != null && imageLists.size() > 0) {
                    for (int i = 0; i < imageLists.size(); i++) {
                        //如果删除的内容中包含这张图片 那么就把图片集合中的对应的图片删除
                        if (deletString.toString().indexOf(imageLists.get(i)) != -1) {
                            imageLists.remove(i);
                            if (onDeleteImageListener != null) {
                                onDeleteImageListener.onDeleteImage();
                            }
                        }
                    }
                }
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            invalidate();   // TODO 作用
            requestLayout();
            StringBuffer stringBuffer = new StringBuffer(getText().toString());
            for (int i = 0; i < imageLists.size(); i++) {
                if (stringBuffer.indexOf(imageLists.get(i)) != -1) {
                    int index = stringBuffer.indexOf(imageLists.get(i));
                    stringBuffer.delete(index - 10, index + imageLists.get(i).length() + 3);
                    stringBuffer.insert(index - 10, placeholder);
                }
            }

            if (stringBuffer.toString().indexOf(placeholder) == 0) {
                stringBuffer.insert(0, " ");
            }

            submitContent = stringBuffer.toString();
            Log.d(TAG, "submitContent: " + submitContent);
        }
    };

    /**
     * 重写dispatchTouchEvent是为了解决上下滑动时光标跳跃的问题
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getRawX();
                startY = event.getRawY();
                selectionStart = getSelectionStart();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                float endY = event.getRawY();
                float endX = event.getRawX();

                if (Math.abs(endY - startY) > 10 || Math.abs(endX - startX) > 10) {
                    return true;
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }

    /**
     * 编辑插入的内容
     *
     * @param picPath
     * @return
     */
    private CharSequence getDrawableStr(String picPath) {
        String str = "<img src=\"" + picPath + "\">";
        Bitmap bitmap = createImageThumbnail(picPath);
        SpannableString spannableString = new SpannableString(str);
        // 定义插入图片
        Drawable drawable = new BitmapDrawable(bitmap);
        // 屏幕宽度
        float screenWidth = Util.getScene(Util.SCENE_WIDTH);  // 屏幕宽度的1/3;
        float width = drawable.getIntrinsicWidth();
        float height = drawable.getIntrinsicHeight();
        if (width > screenWidth) {
            // 如果图片宽度大于屏幕宽度
            width = width - 20;
            height = height - 20;
        } else {
            // 进行比例
            float scale = (screenWidth) / width;
            width *= scale;
            height *= scale;
        }

        //设置图片的宽高
        drawable.setBounds(2, 0, (int) width, (int) height);
        //ALIGN_BOTTOM 调整图片距离字有一定的间隙
        VerticalCenterImageSpan span = new VerticalCenterImageSpan(drawable, 1);
        //SPAN_INCLUSIVE_EXCLUSIVE 会导致删除后面的文字消失
        spannableString.setSpan(span, 0, spannableString.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

         /*
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE：前后都不包括，即在指定范围的前面和后面插入新字符都不会应用新样式
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE：前面不包括，后面包括。即仅在范围字符的后面插入新字符时会应用新样式
        Spannable.SPAN_INCLUSIVE_EXCLUSIVE：前面包括，后面不包括。
        Spannable.SPAN_INCLUSIVE_INCLUSIVE：前后都包括。
         */
        return spannableString;
    }

    ;


    private class VerticalCenterImageSpan extends ImageSpan {

        public VerticalCenterImageSpan(@NonNull Drawable drawable, int verticalAlignment) {
            super(drawable, verticalAlignment);
        }

        @Override
        public void draw(@NonNull Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, @NonNull Paint paint) {
//            super.draw(canvas, text, start, end, x, top, y, bottom, paint);
            Drawable b = getDrawable();
            canvas.save();

            int transY = bottom - b.getBounds().bottom;
            if (mVerticalAlignment == ALIGN_BASELINE) {
                transY -= paint.getFontMetricsInt().descent;
            } else if (mVerticalAlignment == ALIGN_BOTTOM) {

            } else {
                transY += paint.getFontMetricsInt().descent * 2;
            }

            canvas.translate(x, transY);
            b.draw(canvas);
            canvas.restore();
        }
    }

    /**
     * 将图片进行压缩
     *
     * @param picPath
     * @return
     */
    private Bitmap createImageThumbnail(String picPath) {
        Bitmap bitmap;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inTempStorage = new byte[500 * 1024];
        // 默认是Bitmap.Config.ARGB_8888
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        options.inSampleSize = 2;
        bitmap = BitmapFactory.decodeFile(picPath, options);
        return bitmap;
    }

    /**
     * 添加图片集合
     *
     * @param list 图片的路径
     */
    public void addImage(List<String> list) {
        if (getTextContent().length() + IMAGE_LENGTH > MAX_LENGTH){
            Toast.makeText(mContext, "输入的内容超过最大限制", Toast.LENGTH_SHORT).show();
            return;
        }

        Editable editable = getText();
        for (int i = 0; i < list.size(); i++) {
            if (getImage().size() >= maxImageCount) {
                Toast.makeText(mContext, "图片超过最大数量", Toast.LENGTH_SHORT).show();
                return;
            }

            // 开始插入图片
            if (list.get(i) != null && !TextUtils.isEmpty(list.get(i))){
                if (!TextUtils.isEmpty(getText().toString()) && !insertImage) {
                    //如果第一张就是图片不用换行
                    editable.insert(getSelectionStart(), "\n");
                }else if (getSelectionStart() < getText().length()) {
                    //当从中间插入时
                    editable.insert(getSelectionStart(), "\n");
                }

                CharSequence sequence = getDrawableStr(list.get(i));
                if (sequence != null){
                    imageLists.add(list.get(i));
                    editable.insert(getSelectionStart(), sequence);
                    editable.insert(getSelectionStart(), "\n");
                    insertImage = true;
                }
            }else {
                Toast.makeText(mContext, "图片路径为空", Toast.LENGTH_SHORT).show();
            }
        }

        //让光标始终在最后
        this.setSelection(getText().toString().length());
        if (onInsertImageListener != null){
            onInsertImageListener.insertImage();
        }
    }

    /**
     * 获取插入的图片列表
     *
     * @return
     */
    public List<String> getImage() {
        List<String> picPaths = new ArrayList<>();
        // 获取文本内容
        String content = this.getText().toString();
        for (int i = 0; i < imageLists.size(); i++) {
            if (content.indexOf(imageLists.get(i)) != -1) {
                picPaths.add(imageLists.get(i));
            }
        }
        return picPaths;
    }

    /**
     * 是否是图片
     * @param content
     * @return
     */
    public boolean isImage(String content){
        for (int i = 0; i < imageLists.size(); i++) {
            if (content.indexOf(imageLists.get(i)) != -1) {
                return true;
            }
        }
        return true;
    }

    /**
     * 获取去除image后的文字内容
     *
     * @return
     */
    public String getTextContent(){
        return submitContent;
    }


    public interface OnInsertImageListener {
        /**
         * 插入图片时的监听
         */
        void insertImage();
    }

    public interface OnDeleteImageListener {
        /**
         * 删除图片的监听
         */
        void onDeleteImage();
    }

}
