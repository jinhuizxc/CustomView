package com.example.customview.step3.recyclerview.adapter;

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
 * 实现Adapter
 * 与listView一样，同样需要一个Adapter来将数据和Item视图绑定起来，
 * 但不同的的是RecyclerView的Adapter需要派生自RecyclerView.Adapter<RecyclerView.ViewHolder>
 * <p>
 * 当我们写一个Adapter的类派生自RecyclerView.Adapter<RecyclerView.ViewHolder>时,最简单的形式是这样的:
 * <p>
 * <p>
 * getRandomHeight()函数，得到一个0-300之间的一个数值。然后在onBindViewHolder中，将这个数值设置给TextView，作为TextVIew的高度。
 * <p>
 * 首先，可以看到，这里已经实现了瀑布流效果，但是每当滚动到顶部时，所有的Item会跳动一下，重新布局，这是为什么呢？
 * 这是因为在每次拉到顶部的时候，所有的Item会重新执行一次onBindViewHolder函数，因为我们Item的高度就是在这个函数中随机生成的，所以在拉到顶部时，每个Item的高度就会重新生成，造成的结果就是看起来跳了一下，重新布局了。
 * 所以，要解决这个问题也比较简单，那就是用一个数组，在Adapter初始化的时候，就把每个Item的高度生成好，然后在onBindViewHolder中直接取出即可。
 * 所以，我们可以先申请一个数组，并且在Adapter初始化时，保存每个Item的高度
 * <p>
 * 加载不同类型的ITEM:
 * 我们假设每十个常规Item添加一个组item，所以这里定义了一个常量M_SECTION_ITEM_NUM ，
 * 它的值设定为10，每十个元素返回一个组item的类型数值。为了标识组类型和常规item类型，这里定义一个枚举类型，
 * 在其中定义两个值。而ITEM_TYPE.ITEM_TYPE_SECTION.ordinal()的ordinal()函数，则是返回当前枚举值的位置索引。
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private ArrayList<String> mDatas;

    // 为每一个子项生成一个高度
    private ArrayList<Integer> mHeights = new ArrayList<>();

    public RecyclerAdapter(Context context, ArrayList<String> datas) {
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
            return new NormalHolder(inflater.inflate(R.layout.item_layout, parent, false));
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
