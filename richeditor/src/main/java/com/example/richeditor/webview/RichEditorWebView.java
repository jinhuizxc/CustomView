package com.example.richeditor.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;

import com.example.richeditor.utils.Utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class RichEditorWebView extends WebView {

    /**
     * 定义枚举支持类型
     * 加粗, 斜体
     * 下划线
     * 段落
     * 订单列表
     * 代办订单列表
     */
    public enum Type {
        BOLD,
        ITALIC,
        SUBSCRIPT,
        SUPERSCRIPT,
        STRIKETHROUGH,
        UNDERLINE,
        H1,
        H2,
        H3,
        H4,
        H5,
        H6,
        ORDEREDLIST,
        UNORDEREDLIST,
        JUSTIFYCENTER,
        JUSTIFYFULL,
        JUSTUFYLEFT,
        JUSTIFYRIGHT,
    }

    private EditText editText;


    // 文本监听
    public interface OnTextChangeListener {
        void onTextChange(String text);

    }

    // 装饰状态监听器
    public interface OnDecorationStateListener {
        void onStateChangeListener(String text, List<Type> types);
    }

    // 初始化加载监听之后
    public interface AfterInitialLoadListener {
        void onAfterInitialLoad(boolean isReady);
    }

    // 初始化html
    private static final String SETUP_HTML = "file:///android_asset/editor.html";
    private static final String CALLBACK_SCHEME = "re-callback://";
    private static final String STATE_SCHEME = "re-state://";
    private boolean isReady = false;
    private String mContents;

    private RichEditorWebView.OnTextChangeListener mOnTextChangeListener;
    private RichEditorWebView.OnDecorationStateListener mOnDecorationStateListener;
    private RichEditorWebView.AfterInitialLoadListener mAfterInitialLoadListener;

    public void setOnTextChangeListener(OnTextChangeListener mTextChangeListener) {
        this.mOnTextChangeListener = mTextChangeListener;
    }

    public void setOnDecorationStateListener(OnDecorationStateListener mOnDecorationStateListener) {
        this.mOnDecorationStateListener = mOnDecorationStateListener;
    }

    public void setAfterInitialLoadListener(AfterInitialLoadListener mAfterInitialLoadListener) {
        this.mAfterInitialLoadListener = mAfterInitialLoadListener;
    }

    private void callback(String text){
        mContents = text.replaceFirst(CALLBACK_SCHEME, "");
        if (mOnTextChangeListener != null){
            mOnTextChangeListener.onTextChange(mContents);
        }
    }

    private void stateCheck(String text) {
        String state = text.replaceFirst(STATE_SCHEME, "").toUpperCase(Locale.ENGLISH);
        List<RichEditorWebView.Type> types = new ArrayList<>();
        for (RichEditorWebView.Type type : RichEditorWebView.Type.values()) {
            if (TextUtils.indexOf(state, type.name()) != -1) {
                types.add(type);
            }
        }

        if (mOnDecorationStateListener != null) {
            mOnDecorationStateListener.onStateChangeListener(state, types);
        }
    }

    public RichEditorWebView(Context context) {
        this(context, null);
    }

    public RichEditorWebView(Context context, AttributeSet attrs) {
        this(context, attrs, android.R.attr.webViewStyle);
    }

    @SuppressLint("SetJavaScriptEnabled")
    public RichEditorWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        // 设置垂直滚动条
        setVerticalScrollBarEnabled(false);
        // 设置水平滚动条
        setHorizontalScrollBarEnabled(false);
        // 设置支持JavaScript
        getSettings().setJavaScriptEnabled(true);
        // 设置Web Chrome客户端
        setWebChromeClient(new WebChromeClient());
        //创建webViewClient客户端
        setWebViewClient(createWebViewClient());
        //加载url
        loadUrl(SETUP_HTML);

        applyAttributeSet(context, attrs);
    }


    private WebViewClient createWebViewClient() {
        return new EditorWebViewClient();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public RichEditorWebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    protected class EditorWebViewClient extends WebViewClient {
        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            isReady = url.equalsIgnoreCase(SETUP_HTML);
            if(mAfterInitialLoadListener != null){
                mAfterInitialLoadListener.onAfterInitialLoad(isReady);
            }
        }

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            String decode;
            try {
                decode = URLDecoder.decode(url, "UTF-8");
            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
                return false;
            }

            if (TextUtils.indexOf(url, CALLBACK_SCHEME) == 0){
                callback(decode);
                return true;
            } else if (TextUtils.indexOf(url, STATE_SCHEME) == 0) {
                stateCheck(decode);
                return true;
            }
            return super.shouldOverrideUrlLoading(view, url);
        }
    }

    private void applyAttributeSet(Context context, AttributeSet attrs) {
        int[] attrsArray = new int[]{android.R.attr.gravity};
        TypedArray typedArray = context.obtainStyledAttributes(attrs, attrsArray);
        // 设置相应位置
        int gravity = typedArray.getInt(0, NO_ID);
        switch (gravity) {
            case Gravity.LEFT:
                exec("javascript:RE.setTextAlign(\"left\")");
                break;
            case Gravity.RIGHT:
                exec("javascript:RE.setTextAlign(\"right\")");
                break;
            case Gravity.TOP:
                exec("javascript:RE.setVerticalAlign(\"top\")");
                break;
            case Gravity.BOTTOM:
                exec("javascript:RE.setVerticalAlign(\"bottom\")");
                break;
            case Gravity.CENTER_VERTICAL:
                exec("javascript:RE.setVerticalAlign(\"middle\")");
                break;
            case Gravity.CENTER_HORIZONTAL:
                exec("javascript:RE.setTextAlign(\"center\")");
                break;
            case Gravity.CENTER:
                exec("javascript:RE.setVerticalAlign(\"middle\")");
                exec("javascript:RE.setTextAlign(\"center\")");
                break;
        }

        typedArray.recycle();
    }

    /**
     * 设置html
     *
     * @param contents
     */
    public void setHtml(String contents) {
        if (contents == null) {
            contents = "";
        }
        try {
            exec("javascript:RE.setHtml('" + URLEncoder.encode(contents, "UTF-8") + "');");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        mContents = contents;
    }

    public String getHtml() {
        return mContents;
    }

    public void setEditorFontColor(int color) {
        String hex = convertHexColorString(color);
        exec("javascript:RE.setBaseTextColor('" + hex + "');");
    }

    /**
     * 设置字体大小
     *
     * @param px
     */
    public void setEditorFontSize(int px) {
        exec("javascript:RE.setBaseFontSize('" + px + "px');");
    }

//    public void setPadding(int left, int top, int right, int bootom){
//
//    }

    /**
     * 设置内边距
     *
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        super.setPadding(left, top, right, bottom);
        exec("javascript:RE.setPadding('" + left + "px', '" + top + "px', '" + right + "px', '" + bottom
                + "px');");
    }

    @Override
    public void setPaddingRelative(int start, int top, int end, int bottom) {
        super.setPaddingRelative(start, top, end, bottom);
        setPadding(start, top, end, bottom);
    }

    /**
     * 设置背景颜色
     *
     * @param color
     */
    public void setEditorBackgroundColor(int color) {
        setBackgroundColor(color);
    }

    @Override
    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
    }

    @Override
    public void setBackgroundResource(int resid) {
        super.setBackgroundResource(resid);
        Bitmap bitmap = Utils.decodeResource(getContext(), resid);
        String base64 = Utils.toBase64(bitmap);
        // 将bitmap进行回收
        bitmap.recycle();
        exec("javascript:RE.setBackgroundImage('url(data:image/png;base64," + base64 + ")');");
    }

    @Override
    public void setBackground(Drawable background) {
        super.setBackground(background);
        Bitmap bitmap = Utils.toBitmap(background);
        String base64 = Utils.toBase64(bitmap);
        bitmap.recycle();
        exec("javascript:RE.setBackgroundImage('url(data:image/png;base64," + base64 + ")');");
    }

    /**
     * 设置背景
     * @param url
     */
    public void setBackground(String url){
        exec("javascript:RE.setBackgroundImage('url(" + url + ")');");
    }

    // 设置编辑器宽度 setWith('"+px+"px')
    public void setEditorWidth(int px){
        exec("javascript:RE.setWidth('" + px + "px');");
    }

    // 设置编辑器宽度
    public void setEditorHeight(int px){
        exec("javascript:RE.setHeight('" + px + "px');");
    }

    // 设置占位符
    public void setPlaceholder(String placeholder){
        exec("javascript:RE.setPlaceholder('" + placeholder + "');");
    }

    // 设置输入支持
    public void setInputEnabled(boolean inputEnabled){
        exec("javascript:RE.setInputEnabled(" + inputEnabled + ")");
    }

    // 加载css
    public void loadCSS(String cssFile){
        String jsCSSImport = "(function() {" +
                "    var head  = document.getElementsByTagName(\"head\")[0];" +
                "    var link  = document.createElement(\"link\");" +
                "    link.rel  = \"stylesheet\";" +
                "    link.type = \"text/css\";" +
                "    link.href = \"" + cssFile + "\";" +
                "    link.media = \"all\";" +
                "    head.appendChild(link);" +
                "}) ();";
        exec("javascript:" + jsCSSImport + "");
    }

    // 未做
    public void undo(){
        exec("javascript:RE.undo();");
    }

    // 准备
    public void redo(){
        exec("javascript:RE.redo();");
    }

    public void setBold(){
        exec("javascript:RE.setBold();");
    }

    public void setItalic() {
        exec("javascript:RE.setItalic();");
    }

    public void setSubscript() {
        exec("javascript:RE.setSubscript();");
    }

    public void setSuperscript() {
        exec("javascript:RE.setSuperscript();");
    }

    public void setStrikeThrough() {
        exec("javascript:RE.setStrikeThrough();");
    }

    public void setUnderline() {
        exec("javascript:RE.setUnderline();");
    }

    public void setTextColor(int color){
        exec("javascript:RE.prepareInsert();");
        String hex = convertHexColorString(color);
        exec("javascript:RE.setTextColor('" + hex + "');");
    }

    public void setTextBackgroundColor(int color) {
        exec("javascript:RE.prepareInsert();");
        String hex = convertHexColorString(color);
        exec("javascript:RE.setTextBackgroundColor('" + hex + "');");
    }

    // 设置字体大小
    public void setFontSize(int fontSize) {
        if (fontSize > 7 || fontSize < 1) {
            Log.e("RichEditor", "Font size should have a value between 1-7");
        }
        exec("javascript:RE.setFontSize('" + fontSize + "');");
    }

    public void removeFormat() {
        exec("javascript:RE.removeFormat();");
    }

    public void setHeading(int heading) {
        exec("javascript:RE.setHeading('" + heading + "');");
    }

    public void setIndent() {
        exec("javascript:RE.setIndent();");
    }

    public void setOutdent() {
        exec("javascript:RE.setOutdent();");
    }

    public void setAlignLeft() {
        exec("javascript:RE.setJustifyLeft();");
    }

    public void setAlignCenter() {
        exec("javascript:RE.setJustifyCenter();");
    }

    public void setAlignRight() {
        exec("javascript:RE.setJustifyRight();");
    }

    public void setBlockquote() {
        exec("javascript:RE.setBlockquote();");
    }

    public void setBullets() {
        exec("javascript:RE.setBullets();");
    }

    public void setNumbers() {
        exec("javascript:RE.setNumbers();");
    }

    // 插入图片
    public void insertImage(String url, String alt) {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertImage('" + url + "', '" + alt + "');");
    }

    // 插入链接
    public void insertLink(String href, String title) {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.insertLink('" + href + "', '" + title + "');");
    }

    public void insertTodo() {
        exec("javascript:RE.prepareInsert();");
        exec("javascript:RE.setTodo('" + Utils.getCurrentTime() + "');");
    }

    public void focusEditor() {
        requestFocus();
        exec("javascript:RE.focus();");
    }

    public void clearFocusEditor() {
        exec("javascript:RE.blurFocus();");
    }

    /**
     * 转换十六进制颜色字符串
     *
     * @param color
     * @return
     */
    private String convertHexColorString(int color) {
        return String.format("#%06X", (0xFFFFFF & color));
    }

    private void exec(final String trigger) {
        if (isReady){
            load(trigger);
        }else {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    exec(trigger);
                }
            }, 100);
        }
    }

    /**
     * API需要大于19
     * @param trigger
     */
    private void load(String trigger){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            evaluateJavascript(trigger, null);
        }else {
            loadUrl(trigger);
        }
    }

}
