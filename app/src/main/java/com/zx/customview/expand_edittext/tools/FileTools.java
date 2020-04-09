package com.zx.customview.expand_edittext.tools;

import android.app.Activity;
import android.content.Intent;

import com.zx.customview.R;

public class FileTools {

    public static void chooseFiles(Activity context, int requestCode){
        Intent intent = new Intent();
        intent.setType("image/*");     // 选择图片
        intent.setAction(Intent.ACTION_GET_CONTENT);
        context.startActivityForResult(intent, requestCode);
        context.overridePendingTransition(R.anim.slide2_in, R.anim.slide2_out);

    }
}
