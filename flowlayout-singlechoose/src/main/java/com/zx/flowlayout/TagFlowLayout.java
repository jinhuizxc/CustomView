package com.zx.flowlayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class TagFlowLayout extends FlowLayout implements TagAdapter.OnDataSetChangedListener {

    private static final String TAG = "TagFlowLayout";

    private TagAdapter mTagAdapter;
    // 关联标签
    private boolean mAttachLabel = false;
    // 关联输入
    private boolean attachInput = true;
    // 选中的item
    private Set<Integer> mSelectedItem = new HashSet<>();
    private EditText editText;

    private int selectedIndex = -1;
    private boolean isSelect = false;

    private OnSelectListener mOnSelectListener;
    private OnTagClickListener mOnTagClickListener;

    // tag点击监听
    public interface OnTagClickListener {
        boolean onTagClick(View view, int position, FlowLayout parent);
    }

    public interface OnSelectListener {
        void onSelected(Set<Integer> selectPosSet);
    }

    public void setOnTagClickListener(OnTagClickListener mOnTagClickListener) {
        this.mOnTagClickListener = mOnTagClickListener;
    }

    public void setOnSelectListener(OnSelectListener mOnSelectListener) {
        this.mOnSelectListener = mOnSelectListener;
    }

    public TagFlowLayout(Context context) {
        this(context, null);
    }

    public TagFlowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagFlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.TagFlowLayout);
        ta.recycle();

    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            TagView tagView = (TagView) getChildAt(i);
            if (tagView.getVisibility() == GONE) {
                continue;
            }
            if (tagView.getTagView().getVisibility() == GONE) {
                tagView.setVisibility(GONE);
            }
        }
        // 执行测量
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    public void setAdapter(TagAdapter adapter) {
        mTagAdapter = adapter;
        mTagAdapter.setOnDataSetChangeListener(this);
        mSelectedItem.clear();
        changeAdapter();
    }

    /**
     * 设置是否显示标签，默认显示
     *
     * @param attachLabel 是否显示标签
     */
    public void setAttachLabel(boolean attachLabel) {
        this.mAttachLabel = attachLabel;
    }

    @Override
    public void onDataSetChanged() {
        mSelectedItem.clear();
        changeAdapter();
    }


    /**
     * TODO 这里开始不太懂了...
     */
    private void changeAdapter() {
        removeAllViews();
        TagAdapter adapter = mTagAdapter;
        TagView tagViewContainer;
        if (mAttachLabel) {
            View labelView = adapter.getLabelView(this);
            tagViewContainer = new TagView(getContext());
            labelView.setDuplicateParentStateEnabled(true);
            if (labelView.getLayoutParams() != null) {
                tagViewContainer.setLayoutParams(labelView.getLayoutParams());
            } else {
                MarginLayoutParams lp = new MarginLayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                lp.setMargins(
                        PixelUtil.dp2px(getContext(), 5),
                        PixelUtil.dp2px(getContext(), 5),
                        PixelUtil.dp2px(getContext(), 5),
                        PixelUtil.dp2px(getContext(), 5));
                tagViewContainer.setLayoutParams(lp);
            }
            LayoutParams lp = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            labelView.setLayoutParams(lp);
            tagViewContainer.addView(labelView);
            addView(tagViewContainer);
        }
        for (int i = 0; i < adapter.getCount(); i++) {
            View tagView = adapter.getView(this, i, adapter.getItem(i));

            tagViewContainer = new TagView(getContext());
            tagView.setDuplicateParentStateEnabled(true);
            if (tagView.getLayoutParams() != null) {
                tagViewContainer.setLayoutParams(tagView.getLayoutParams());
            } else {
                MarginLayoutParams lp = new MarginLayoutParams(
                        LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                lp.setMargins(PixelUtil.dp2px(getContext(), 5),
                        PixelUtil.dp2px(getContext(), 5),
                        PixelUtil.dp2px(getContext(), 5),
                        PixelUtil.dp2px(getContext(), 5));
                tagViewContainer.setLayoutParams(lp);
            }

            LayoutParams lp = new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            tagView.setLayoutParams(lp);
            tagViewContainer.addView(tagView);
            addView(tagViewContainer);

            tagView.setClickable(false);
            final TagView finalTagViewContainer = tagViewContainer;
            final int position = mAttachLabel ? i + 1 : i;
            tagViewContainer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    doSelect(finalTagViewContainer, position);
                    editText.setCursorVisible(false);
                }
            });
        }

        if (attachInput) {
            editText = (EditText) adapter.getInputView(this);
            tagViewContainer = new TagView(getContext());
            editText.setDuplicateParentStateEnabled(true);
            if (editText.getLayoutParams() != null) {
                tagViewContainer.setLayoutParams(editText.getLayoutParams());
            } else {
                MarginLayoutParams lp = new MarginLayoutParams(
                        LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                lp.setMargins(PixelUtil.dp2px(getContext(), 5),
                        PixelUtil.dp2px(getContext(), 5),
                        PixelUtil.dp2px(getContext(), 5),
                        PixelUtil.dp2px(getContext(), 5));
                tagViewContainer.setLayoutParams(lp);
            }
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            editText.setLayoutParams(lp);
            tagViewContainer.addView(editText);
            addView(tagViewContainer);

            // 文本输入监听
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s.toString().contains(",") || s.toString().contains("，") && s.length() > 1) {
                        String ss = s.toString().substring(0, s.length() - 1);
                        addItem(ss);
                        editText.setText("");
                        s.clear();
                        editText.requestFocus();
                    }
                }
            });

            // 删除键和手势抬起
            editText.setOnKeyListener(new OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    //执行了两次因为onkey事件包含了down和up事件，所以只需要加入其中一个即可
                    if (keyCode == KeyEvent.KEYCODE_DEL && event.getAction() == KeyEvent.ACTION_UP) {
                        if (mAttachLabel && getChildCount() <= 2) {
                            return false;
                        }
                        if (!mAttachLabel && getChildCount() <= 1) {
                            return false;
                        }
                        if (isSelect) {
                            removeItem(selectedIndex);
                            isSelect = false;
                            editText.requestFocus();
                            editText.setCursorVisible(false);
                        } else {
                            if (editText.length() == 0) {
                                doSelect((TagView) getChildAt(getChildCount() - 2), getChildCount() - 2);
                            }
                        }
                    }
                    return false;
                }
            });

            editText.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    editText.setCursorVisible(true);
                }
            });
        }
    }


    // 移除item
    private void removeItem(int position) {
        mTagAdapter.remove(mAttachLabel ? position - 1 : position);
        mSelectedItem.clear();
        changeAdapter();
    }

    /**
     * 添加item
     * @param s
     */
    private void addItem(String s) {
        mTagAdapter.add(s);
        mSelectedItem.clear();
        changeAdapter();
    }

    /**
     * 选择item
     * @param child
     * @param position
     */
    private void doSelect(TagView child, int position) {
        if (!child.isChecked()){
            if (mSelectedItem.size() == 1){
                Log.d(TAG, "doSelect: 1 === " + position);
                Iterator<Integer> iterator = mSelectedItem.iterator();
                Integer preIndex = iterator.next();
                TagView pre = (TagView) getChildAt(preIndex);
                setChildUnChecked(pre);
                setChildChecked(child);

                mSelectedItem.remove(preIndex);
                mSelectedItem.add(position);
            }else {
                // 第一个元素走这里
                Log.d(TAG, "doSelect: 2 === " + position);
                if (mSelectedItem.size() >= 1){
                    return;
                }
                setChildChecked(child);
                mSelectedItem.add(position);
            }
            if (attachInput){
                showInput(editText);
            }
            selectedIndex = position;
            isSelect = true;
        }else {
            // 取消选择的话走这里
            Log.d(TAG, "doSelect: 3 === " + position);
            setChildUnChecked(child);
            mSelectedItem.remove(position);
            isSelect = false;
        }
    }

    private void showInput(final EditText editText) {
        editText.requestFocus();
        new Handler().post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager)
                        getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
            }
        });
    }

    private void setChildChecked(TagView tagView) {
        tagView.setChecked(true);
    }

    private void setChildUnChecked(TagView tagView) {
        tagView.setChecked(false);
    }

    public EditText getEditText() {
        return editText;
    }
}
