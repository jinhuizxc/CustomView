package com.example.flowlayout.adapter;

import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.blankj.utilcode.util.StringUtils;

import org.w3c.dom.Text;

/**
 * 数据类的viewholder
 */
public class ViewHolder {

    private View itemView;
    private SparseArray<View> sparseArray;

    // itemViewd的构造并初始化sparseArray
    public ViewHolder(View itemView) {
        this.itemView = itemView;
        sparseArray = new SparseArray<>();
    }

    /**
     * 从sparseArray获取view，如果view为null，
     * 从itemView里面找到当前view, 并放到sparseArray中
     * <p>
     * 为了让getView支持更多的view比如TextView, EditView等，
     * 需要传入一个泛型T,具有通用性:
     *
     * @param viewId
     * @return
     */
    public <T extends View> T getView(int viewId) {
        // 从
        View view = sparseArray.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            sparseArray.put(viewId, view);
        }
        return (T) view;
    }

    public ViewHolder setVisible(int viewId) {
        getView(viewId).setVisibility(View.VISIBLE);
        return this;
    }

    public ViewHolder setInVisible(int viewId) {
        getView(viewId).setVisibility(View.INVISIBLE);
        return this;
    }

    public void setViewGone(int viewId) {
        getView(viewId).setVisibility(View.GONE);
    }

    public void setViewVisible(int viewId) {
        getView(viewId).setVisibility(View.VISIBLE);
    }

    public void setText(int viewId, String text) {
        TextView textView = getView(viewId);
        textView.setText(isNotNULL(text));

    }

    private String isNotNULL(Object object) {
        return object == null ? "" : object.toString();
    }

    // 获取TextView的内容
    public String getTvText(int viewId){
        TextView textView = getView(viewId);
        return textView.getText().toString().trim();
    }

    // 获取EditText的内容
    public String getEdText(int viewId){
        EditText editText = getView(viewId);
        return editText.getText().toString().trim();
    }

    public void setBackgroundResource(int viewId, int resId){
        View view = getView(viewId);
        view.setBackgroundResource(resId);
    }

    public void setImageBitmap(int viewId, Bitmap bitmap) {
        ImageView imageView = getView(viewId);
        imageView.setImageBitmap(bitmap);
    }

    public void setImageResource(int viewId, int resID) {
        ImageView imageView = getView(viewId);
        imageView.setImageResource(resID);
    }

    public void setProgress(int viewId, int progress) {
        ProgressBar progressBar = getView(viewId);
        progressBar.setProgress(progress);
    }

    public void setOnClickListener(int viewId, View.OnClickListener listener){
        getView(viewId).setOnClickListener(listener);
    }

    public void setOnLongClickListener(int viewId, View.OnLongClickListener listener){
        getView(viewId).setOnLongClickListener(listener);
    }


}
