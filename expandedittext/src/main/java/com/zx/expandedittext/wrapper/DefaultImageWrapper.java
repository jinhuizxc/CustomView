package com.zx.expandedittext.wrapper;

import android.util.Log;

import com.zx.expandedittext.ExpandEditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultImageWrapper extends ImageWrapper {
    @Override
    public String getPattern() {
        return "!\\[img\\]\\((S.*?)\\)";
    }

    @Override
    public String getImageWrapper(String string) {
        return "![img](" + string + ")";
    }

    @Override
    public void parse(ExpandEditText expandEditTextView, String text) {
        String nowStr = text;
        int preEnd = 0;
        String pattern = getPattern();
        Pattern p = Pattern.compile(pattern);
        Matcher matcher = p.matcher(text);
        while (matcher.find()){
            int start = matcher.start();
            String preString = nowStr.substring(preEnd, start);
            Log.i("prestring", "parse: " + preString);
            preEnd = matcher.end();

            String url = matcher.group(1);

            if (!preString.isEmpty()){
                expandEditTextView.parseTextEntity(preString);
            }

            expandEditTextView.parseImageEntity(url);
        }

        if (preEnd < text.length()) {
            String string = nowStr.substring(preEnd);
            expandEditTextView.parseTextEntity(string);
        }

    }
}
