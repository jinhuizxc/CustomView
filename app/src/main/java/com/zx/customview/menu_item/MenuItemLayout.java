package com.zx.customview.menu_item;

import android.content.Context;
import android.content.res.TypedArray;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zx.customview.R;

/**
 * Android-自定义控件开发
 *  https://www.jianshu.com/p/6572ebed0d99
 */
public class MenuItemLayout extends FrameLayout {

    private Context mContext;
    // 布局view
    private View mView;
    // 标题文本
    private TextView tvTitleText;
    // 提示文本
    private TextView tvHintText;
    //
    private ImageView redHintImg;
    // 标题左边的图片
    private ImageView iconImg;
    private int iconImgId;

    private String titleText;
    private String hintText;
    private String iconImgUri;
    private String jumpUrl;


    // 分割线样式
    public static final int N0_LINE = 0;
    public static final int DIVIDE_LINE = 1;
    public static final int DIVIDE_AREA = 2;
    public int divideLineStyle = N0_LINE;
    private boolean isShoeRedHintImg = false;

    public OnClickListener onClickListener;


    public void setViewOnClickListener(OnClickListener onClickListener) {
        this.onClickListener = onClickListener;
        mView.setOnClickListener(onClickListener);  // 设置view点击事件
    }

    public MenuItemLayout(@NonNull Context context) {
        this(context, null);
    }

    public MenuItemLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MenuItemLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

//    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
//    public MenuItemLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
//        super(context, attrs, defStyleAttr, defStyleRes);
//    }

    private void init(Context context, AttributeSet attrs) {
        mContext = context;
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mView = inflater.inflate(R.layout.item_menu_layout, this, true);

        tvTitleText = mView.findViewById(R.id.menu_item_text);
        tvHintText = mView.findViewById(R.id.menu_item_text_hint);
        iconImg = mView.findViewById(R.id.menu_item_icon_img);
        redHintImg = mView.findViewById(R.id.menu_item_red_hint);

        TypedArray typedArray = mContext.obtainStyledAttributes(attrs, R.styleable.MenuItemLayout);
        // 设置数据
        setTitleText(typedArray.getString(R.styleable.MenuItemLayout_title_text));
        setHintText(typedArray.getString(R.styleable.MenuItemLayout_hint_text));
        setIconImgId(typedArray.getResourceId(R.styleable.MenuItemLayout_icon_reference, 10000));
        setJumpUrl(typedArray.getString(R.styleable.MenuItemLayout_jump_url));
        divideLineStyle = typedArray.getInt(R.styleable.MenuItemLayout_divide_line_style, N0_LINE);
        setDivideLine(divideLineStyle);
        typedArray.recycle();


    }

    /**
     * 默认没有线条
     * @param bootomLineStyle
     */
    public void setDivideLine(int bootomLineStyle) {
        View lineView = findViewById(R.id.divide_line_view);
        View areaView = findViewById(R.id.divide_area_view);
        lineView.setVisibility(GONE);
        areaView.setVisibility(GONE);
        if (bootomLineStyle == DIVIDE_LINE){
            lineView.setVisibility(VISIBLE);
        }else if (bootomLineStyle == DIVIDE_AREA){
            areaView.setVisibility(VISIBLE);
        }

    }

    private void setJumpUrl(String url) {
        if (jumpUrl != null) {
            this.jumpUrl = url;
        }
    }

    public void setIconImgId(int id) {
        if (iconImgId != 10000) {
            this.iconImgId = id;
            iconImg.setImageResource(iconImgId);
        }
    }

    public ImageView getIconImg() {
        return iconImg;
    }

    public void setHintText(String text) {
        if (text != null) {
            this.hintText = text;
            tvHintText.setText(hintText);
        }
    }

    public String getHintText() {
        return hintText;
    }

    public void setTitleText(String titleText) {
        if (titleText != null) {
            this.titleText = titleText;
            tvTitleText.setText(titleText);
        }
    }

    public String getTitleText() {
        return titleText;
    }
}
