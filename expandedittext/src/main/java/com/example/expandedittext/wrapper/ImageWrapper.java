package com.example.expandedittext.wrapper;

import com.example.expandedittext.ExpandEditText;

/**
 * 定义图片文字的包裹规则
 */
public abstract class ImageWrapper {

    public abstract String getPattern();

    public abstract String getImageWrapper(String string);

    public abstract void parse(ExpandEditText expandEditTextView, String text);

}
