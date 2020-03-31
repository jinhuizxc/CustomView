package com.example.customview.inputconflict.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.customview.R;

import java.util.List;

public class MyAdapter extends BaseAdapter {

    private List<String> mDatas;
    private Context mContext;

    public MyAdapter(Context mContext, List<String> mDatas) {
        this.mDatas = mDatas;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null){
            convertView = View.inflate(mContext, R.layout.item_chat, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);

            viewHolder.tv = convertView.findViewById(R.id.send_content_tv);

        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        // 设置数据
        viewHolder.tv.setText(mDatas.get(position));

        return convertView;
    }

    public void setDataList(List<String> list){
        mDatas.clear();
        mDatas.addAll(list);
        notifyDataSetChanged();
    }

    public void addData(String string){
        mDatas.add(string);
        notifyDataSetChanged();
    }

    private class ViewHolder{
        TextView tv;
    }
}
