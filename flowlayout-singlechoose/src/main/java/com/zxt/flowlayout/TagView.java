package com.zxt.flowlayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Checkable;
import android.widget.FrameLayout;

/**
 * tag: 选中、未选中
 * 实现Checkable接口
 */
public class TagView extends FrameLayout implements Checkable {

    private boolean isChecked;

    // 选中状态
    private static final int[] CHECK_STATE = new int[]{android.R.attr.state_checked};

    public TagView(Context context) {
        super(context);
    }

    public TagView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public TagView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 获取tagView
     * @return
     */
    public View getTagView(){
        return getChildAt(0);
    }

    @Override
    protected int[] onCreateDrawableState(int extraSpace) {
//        return super.onCreateDrawableState(extraSpace);
        int[] states = super.onCreateDrawableState(extraSpace + 1);
        if (isChecked()){
            mergeDrawableStates(states, CHECK_STATE);
        }
        return states;
    }


    @Override
    public void setChecked(boolean checked) {
        if (this.isChecked != checked){
            this.isChecked = checked;
            /**
             * 调用此选项可强制视图更新其可绘制状态。
             * 这将导致在此视图上调用drawableStateChanged。
             * 对新状态感兴趣的视图应调用getDrawableState。
             */
            refreshDrawableState();
        }
    }

    /**
     * 视图当前选中状态
     * @return
     */
    @Override
    public boolean isChecked() {
        return isChecked;
    }

    /**
     * 将视图更改为其当前状态的倒数
     */
    @Override
    public void toggle() {
        setChecked(!isChecked);
    }
}
