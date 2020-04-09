package com.zx.flowlayout;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class TagAdapter<T> {

    // 选中的标签数据
    private List<T> mTagData;
    private OnDataSetChangedListener onDataSetChangeListener;

    private HashSet<Integer> mCheckedPosList = new HashSet<Integer>();

    // 支持列表
    public TagAdapter(List<T> mTagData) {
        this.mTagData = mTagData;
    }

    // 将数组类型的数据转换为链表
    public TagAdapter(T[] data){
        mTagData = new ArrayList<>(Arrays.asList(data));
    }

    public void setOnDataSetChangeListener(OnDataSetChangedListener onDataSetChangeListener) {
        this.onDataSetChangeListener = onDataSetChangeListener;
    }

    public void removeSelectedItem(int selectItem) {
        mTagData.remove(selectItem - 1);
        notifyDataChanged();
    }

    // 数据监听回调
    public interface OnDataSetChangedListener{
        void onDataSetChanged();
    }

    public void setSelectedList(int... poses) {
        Set<Integer> set = new HashSet<>();
        for (int pos : poses) {
            set.add(pos);
        }
        setSelectedList(set);
    }

    public void setSelectedList(Set<Integer> set) {
        mCheckedPosList.clear();
        if (set != null) {
            mCheckedPosList.addAll(set);
        }
        notifyDataChanged();
    }

    HashSet<Integer> getPreCheckedList() {
        return mCheckedPosList;
    }

    /**
     * 获取数据
     * @return
     */
    public List<T> getTagData() {
        return mTagData;
    }

    /**
     * 获取某个position的数据
     * @param position
     * @return
     */
    public T getItem(int position){
        return mTagData.get(position);
    }

    public int getCount(){
        return mTagData == null ? 0 : mTagData.size();
    }

    /**
     * 重置数据
     * @param data
     */
    public void resetData(List<T> data){
        this.mTagData = data;
        notifyDataChanged();
    }

    public void remove(int position){
        mTagData.remove(position);
    }

    public void add(T s){
        mTagData.add(s);
    }

    public void addAll(List<T> data){
        mTagData.addAll(data);
        notifyDataChanged();
    }

    public void notifyDataChanged() {
        if (onDataSetChangeListener != null)
            onDataSetChangeListener.onDataSetChanged();
    }

    public void onSelected(int position, View view){
        Log.d("zhy","onSelected " + position);
    }

    public void unSelected(int position, View view){
        Log.d("zhy","unSelected " + position);
    }

    public boolean setSelected(int position, T t) {
        return false;
    }

    // 定义抽象方法
    // 获取View
    public abstract View getView(FlowLayout parent, int position, T s);

    // 获取标签view
    public abstract View getLabelView(FlowLayout parent);
    // 获取输入view
    public abstract View getInputView(FlowLayout parent);


}
