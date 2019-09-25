package com.example.customview.step3.recyclerview.itemdecoration;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.blankj.utilcode.util.ToastUtils;
import com.example.customview.R;

import java.util.ArrayList;

/**
 *
 */
public class RvItemDecorationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;

    // 为每一个子项生成一个高度
    private ArrayList<Integer> mHeights = new ArrayList<>();

    public RvItemDecorationAdapter(Context context, ArrayList<String> datas) {
        mContext = context;
        mDatas = datas;

        if (mDatas.size() > 0) {
            for (int i = 0; i < mDatas.size(); i++) {
                mHeights.add(getRandomHeight());
            }
        }
    }

    /**
     * 定义不同的item
     */
    public static enum ITEM_TYPE {
        ITEM_TYPE_SECTION,
        ITEM_TYPE_ITEM
    }

    private int M_SECTION_ITEM_NUM = 10;

    /**
     * 在自定义的Adapter中，我们可以通过getItemViewType函数来返回每个position所对应的类型
     *
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        if (position % M_SECTION_ITEM_NUM == 0) {
            return ITEM_TYPE.ITEM_TYPE_SECTION.ordinal();
        }
        return ITEM_TYPE.ITEM_TYPE_ITEM.ordinal();
    }

    /**
     * onCreateViewHolder:用于得到我们自定义的ViewHolder,在listView中,我们也会定义ViewHolder来承载视图中的元素.
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       /* LayoutInflater inflater = LayoutInflater.from(mContext);
        return new NormalHolder(inflater.inflate(R.layout.item_layout, parent, false));*/
        // 加载不同type;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == ITEM_TYPE.ITEM_TYPE_ITEM.ordinal()) {
            return new NormalHolder(inflater.inflate(R.layout.item_decoration_layout, parent, false));
        }
        return new SectionHolder(inflater.inflate(R.layout.item_select, parent, false));
    }

    /**
     * onBindViewHolder:是用于将指定位置的数据和视图绑定起来的
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
//        NormalHolder normalHolder = (NormalHolder) holder;
//        normalHolder.tv.setText(mDatas.get(position));
//
//       /* ViewGroup.LayoutParams lp = normalHolder.tv.getLayoutParams();
//        lp.height = getRandomHeight();
//        normalHolder.tv.setLayoutParams(lp);*/
//
//        ViewGroup.LayoutParams lp = normalHolder.tv.getLayoutParams();
//        lp.height = mHeights.get(position);
//        normalHolder.tv.setLayoutParams(lp);

        if (holder instanceof SectionHolder) {
            SectionHolder sectionHolder = (SectionHolder) holder;
            sectionHolder.tvItemSelect.setText("第 " + position / M_SECTION_ITEM_NUM + " 组");
        } else if (holder instanceof NormalHolder) {
            NormalHolder normalHolder = (NormalHolder) holder;
            normalHolder.tv.setText(mDatas.get(position));
        }

    }

    /**
     * 为tv生成自由高度
     *
     * @return
     */
    private int getRandomHeight() {
        int randomHeight;
        do {
            randomHeight = (int) (Math.random() * 300);
        } while (randomHeight == 0);
        return randomHeight;
    }


    /**
     * getItemCount:用于获取列表总共的item数
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    private class NormalHolder extends RecyclerView.ViewHolder {

        public TextView tv;

        public NormalHolder(View itemView) {
            super(itemView);

            tv = (TextView) itemView.findViewById(R.id.item_tv);
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ToastUtils.showShort(tv.getText());
                }
            });
        }
    }

    private class SectionHolder extends RecyclerView.ViewHolder {

        public TextView tvItemSelect;

        public SectionHolder(@NonNull View itemView) {
            super(itemView);
            tvItemSelect = (TextView) itemView.findViewById(R.id.tv_item_section);
        }
    }


}
