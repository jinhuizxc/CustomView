package com.zxt.flowlayout;

import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class TagAdapter {

    // 选中的标签数据
    private List<String> mTagData;
    private OnDataSetChangedListener onDataSetChangeListener;

    // 支持列表
    public TagAdapter(List<String> mTagData) {
        this.mTagData = mTagData;
    }

    // 将数组类型的数据转换为string链表
    public TagAdapter(String[] data){
        mTagData = new ArrayList<>(Arrays.asList(data));
    }

    /**
     * 获取数据
     * @return
     */
    public List<String> getTagData() {
        return mTagData;
    }

    /**
     * 获取某个position的数据
     * @param position
     * @return
     */
    public String getItem(int position){
        return mTagData.get(position);
    }

    public int getCount(){
        return mTagData == null ? 0 : mTagData.size();
    }

    /**
     * 重置数据
     * @param data
     */
    public void resetData(List<String> data){
        this.mTagData = data;
        notifyDataChanged();
    }

    public void setOnDataSetChangeListener(OnDataSetChangedListener onDataSetChangeListener) {
        this.onDataSetChangeListener = onDataSetChangeListener;
    }

    // 数据监听回调
    public interface OnDataSetChangedListener{
        void onDataSetChanged();
    }

    public void remove(int position){
        mTagData.remove(position);
    }

    public void add(String s){
        mTagData.add(s);
    }

    public void addAll(List<String> data){
        mTagData.addAll(data);
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        if (onDataSetChangeListener != null)
            onDataSetChangeListener.onDataSetChanged();
    }

    // 定义抽象方法
    // 获取View
    public abstract View getView(FlowLayout parent, int position, String s);

    // 获取标签view
    public abstract View getLabelView(FlowLayout parent);
    // 获取输入view
    public abstract View getInputView(FlowLayout parent);


}
