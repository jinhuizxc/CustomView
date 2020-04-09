package com.zx.customview.step3.recyclerview.layoutmanager;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * 1.1 自定义CustomLayoutManager
 * <p>
 * 生成一个类CustomLayoutManager，派生自LayoutManager:
 * <p>
 * 当我们派生自LayoutManager时，会强制让我们生成一个方法generateDefaultLayoutParams。
 * 这个方法就是RecyclerView Item的布局参数，换种说法，就是RecyclerView 子 item 的 LayoutParameters，若是想修改子Item的布局参数（比如：宽/高/margin/padding等等），那么可以在该方法内进行设置。
 * 一般来说，没什么特殊需求的话，则可以直接让子item自己决定自己的宽高即可（wrap_content）。
 * 所以，我们一般的写法是：
 * <p>
 * 页面显示空白：
 * 我们说过所有的Item的布局都是在LayoutManager中处理的，很明显，我们目前在CustomLayoutManager中并没有布局任何的Item。当然没有Item出现了。
 */
public class CustomLayoutManager extends RecyclerView.LayoutManager {
    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT,
                RecyclerView.LayoutParams.WRAP_CONTENT);
    }


    /**
     * 首先，我们通过measureChildWithMargins(view, 0, 0);函数测量这个view，
     * 并且通过getDecoratedMeasuredWidth(view)得到测量出来的宽度，
     * 需要注意的是通过getDecoratedMeasuredWidth(view)得到的是item+decoration的总宽度。如果你只想得到view的测量宽度，通过view.getMeasuredWidth()就可以得到了
     *
     * 然后通过layoutDecorated();函数将每个item摆放在对应的位置，每个Item的左右位置都是相同的，
     * 从左侧x=0开始摆放，只是y的点需要计算。所以这里有一个变量offsetY，用以累加当前Item之前所有item的高度。从而计算出当前item的位置。这个部分难度不大，就不再细讲了。
     *
     * 在此之后，我们再运行程序，会发现，现在item显示出来了：
     *
     *
     * @param recycler
     * @param state
     */
    private int mTotalHeight = 0;
    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        //定义竖直方向的偏移量
        int offsetY = 0;
        for (int i = 0; i < getItemCount(); i++) {
            // 第一：把所有的item所对应的view加进来：
            View view = recycler.getViewForPosition(i);
            addView(view);
            // 第二：把所有的Item摆放在它应在的位置：
            measureChildWithMargins(view, 0, 0);
            int width = getDecoratedMeasuredWidth(view);
            int height = getDecoratedMeasuredHeight(view);
            layoutDecorated(view, 0, offsetY, width, offsetY + height);
            offsetY += height;
        }

        //如果所有子View的高度和没有填满RecyclerView的高度，
        // 则将高度设置为RecyclerView的高度
        mTotalHeight = Math.max(offsetY, getVerticalSpace());

    }

    private int getVerticalSpace() {
        return getHeight() - getPaddingBottom() - getPaddingTop();
    }


    /**
     * 2.1 添加滚动效果
     * 但是，现在还不能滑动，如果我们要给它添加上滑动，需要修改两个地方：
     *
     * 首先，我们通过在canScrollVertically()中return true；使LayoutManager具有垂直滚动的功能。然后在scrollVerticallyBy中接收每次滚动的距离dy。
     * 如果你想使LayoutManager具有横向滚动的功能，可以通过在canScrollHorizontally()中return true;
     *
     * 这里需要注意的是,在scrollVerticallyBy中,dy表示手指在屏幕上每次滑动的位移.
     *
     *     当手指由下往上滑时,dy>0
     *     当手指由上往下滑时,dy<0
     *
     * 很明显,当手指向上滑动时,我们需要让所有子Item向上移动,向上移动明显是需要减去dy的.所以,大家经过测试也可以发现,让容器内的item移动-dy距离,才符合生活习惯.在LayoutManager中,我们可以通过public void offsetChildrenVertical(int dy)函数来移动RecycerView中的所有item.
     *
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    /**
     * 2.2 添加异常判断
     *
     * @param dy
     * @param recycler
     * @param state
     * @return
     */
    private int mSumDy = 0;
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler, RecyclerView.State state) {
//        return super.scrollVerticallyBy(dy, recycler, state);

//        // 平移容器内的item
//        offsetChildrenVertical(-dy);
//        return dy;

        /**
         *  (1)、判断到顶
         *  判断到顶相对比较容易，我们只需要把所有的dy相加，如果小于0，就表示已经到顶了。就不让它再移动就行，代码如下：
         *
         * (2)、判断到底
         * 判断到底的方法，其实就是我们需要知道所有item的总高度，用总高度减去最后一屏的高度，就是到底的时的偏移值，如果大于这个偏移值就说明超过底部了。
         *
         * 所以，我们首先需要得到所有item的总高度，我们知道在onLayoutChildren中会测量所有的item并且对每一个item布局，所以我们只需要在onLayoutChildren中将所有item的高度相加就可以得到所有Item的总高度了。
         *
         */
        int travel = dy;
        //如果滑动到最顶部
        if (mSumDy + dy < 0) {
            travel = -mSumDy;
        }else if (mSumDy + dy > mTotalHeight - getVerticalSpace()){
            //如果滑动到最底部
            travel = mTotalHeight - getVerticalSpace() - mSumDy;
        }
        mSumDy += travel;
        // 平移容器内的item
        offsetChildrenVertical(-travel);
        return dy;

    }
}
