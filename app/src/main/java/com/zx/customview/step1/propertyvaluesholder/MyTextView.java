package com.zx.customview.step1.propertyvaluesholder;

import android.content.Context;
import androidx.appcompat.widget.AppCompatTextView;
import android.util.AttributeSet;

/**
 * Created by jinhui on 2018/1/22.
 * Email:1004260403@qq.com
 */

public class MyTextView extends AppCompatTextView {

    public MyTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    // 与 OfPropertyValuesHolderActivity 对应CharText
    public void setCharText(Character character){
        setText(String.valueOf(character));
    }
}
