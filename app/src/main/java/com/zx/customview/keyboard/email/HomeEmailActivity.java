package com.zx.customview.keyboard.email;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.ToastUtils;
import com.zx.customview.R;
import com.zx.customview.app.App;
import com.zx.customview.keyboard.email.view.InputConflictView;
import com.zx.customview.keyboard.way1.statusbar.StatusBarCompat;
import com.zx.expandedittext.ExpandEditText;
import com.zx.expandedittext.entity.ImageEntity;
import com.zx.expandedittext.listener.OnExpandImageClickListener;
import com.zx.flowlayout.FlowLayout;
import com.zx.flowlayout.TagAdapter;
import com.zx.flowlayout.TagFlowLayout;

import java.util.Iterator;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 采用方式1看下项目展示效果
 * <p>
 * 周四前看效果！
 */
public class HomeEmailActivity extends AppCompatActivity
        implements OnExpandImageClickListener,
        ExpandEditText.OnTouchListener {


    @BindView(R.id.ll_back)
    LinearLayout llBack;
    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.flow_layout)
    TagFlowLayout flowLayout;
    @BindView(R.id.ib_add)
    ImageButton ibAdd;
    @BindView(R.id.ib_close)
    ImageButton ibClose;
    @BindView(R.id.expand_text)
    ExpandEditText expandEditText;
    @BindView(R.id.scrollView)
    ScrollView scrollView;
    @BindView(R.id.et_subject)
    EditText etSubject;
    @BindView(R.id.keyboard_layout)
    InputConflictView keyboardLayout;

    // 收件人
    private String[] mValues = new String[]{};
    // 选中的元素
    private Set<Integer> selectPosSet;
    private boolean isBottomLayoutShow = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StatusBarCompat.setStatusBarColor(this, getResources().getColor(R.color.colorPrimary), 40);
        setContentView(R.layout.activity_home_email);
        ButterKnife.bind(this);

        initFlowLayout();

        initData();
        initListener();

        // 图文混排
        expandEditText.bind(this)
                .setOnExpandImageClickListener(this)
                .setTouchListener(this)
                .setHintText("说些什么吧~");


        initFocus();
    }


    /**
     * 获取焦点
     */
    private void initFocus() {
        // 获取焦点弹起键盘
        etSubject.requestFocus();


    }

    @SuppressLint("ClickableViewAccessibility")
    private void initFlowLayout() {
        // 设置是否需要添加标签,默认添加
        flowLayout.setAttachLabel(true);
        flowLayout.setAdapter(tagAdapter);
        flowLayout.getEditText().requestFocus();

        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 将选中的item进行移除
                Iterator<Integer> iterator = selectPosSet.iterator();
                if (iterator.hasNext()) {
                    Integer selectItem = iterator.next();
                    tagAdapter.removeSelectedItem(selectItem);
                    // 判断状态
//                    int count = tagAdapter.getCount();
//                    if (count > 0){
//                        ibAdd.setVisibility(View.VISIBLE);
//                    }
                }

            }
        });

        flowLayout.getEditText().setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                keyboardLayout.showKeyboard4();
                return false;
            }
        });

        /*flowLayout.getEditText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibAdd.setVisibility(View.VISIBLE);
                ibClose.setVisibility(View.GONE);
            }
        });*/

        // 选中监听
        flowLayout.setOnSelectListener(new TagFlowLayout.OnSelectListener() {
            @Override
            public void onSelected(Set<Integer> select) {
                ToastUtils.showShort(select.size());
                selectPosSet = select;
            }
        });

        // 点击tag监听
        flowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
//                ibAdd.setVisibility(View.GONE);
//                ibClose.setVisibility(View.VISIBLE);
                return true;
            }
        });

    }

    private TagAdapter tagAdapter = new TagAdapter<String>(mValues) {

        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView textView = (TextView) LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.item_flowlayout, parent, false);
            textView.setText(s);
            return textView;
        }

        @Override
        public View getLabelView(FlowLayout parent) {
            //如果设置flowLayout.setAttachLabel(false);该标签将不显示
            TextView tv = (TextView) LayoutInflater.from(getApplicationContext())
                    .inflate(R.layout.item_label, parent, false);
            tv.setText("收件人：");
            return tv;
        }

        @Override
        public View getInputView(FlowLayout parent) {
            return LayoutInflater.from(getApplicationContext()).inflate(R.layout.item_edit, parent, false);
        }
    };

    private void initData() {


    }


    /**
     * Android 输入框弹起时顶起布局(与ScrollView一起使用)
     */
    private void initListener() {
        // scrollView1为布局文件中ScrollView的id
        // 监听键盘弹起或关闭(ScrollView)
        scrollView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(final View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > 100)) {
//                    Toast.makeText(getApplicationContext(), "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
//                    scrollView.fullScroll(ScrollView.FOCUS_DOWN);(会导致输入框失去焦点)
                    // 获得屏幕
                    View decorView = getWindow().getDecorView();
                    scrollToBottom(decorView, v);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > 100)) {
//                    Toast.makeText(getApplicationContext(), "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // 点击监听
        etSubject.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ;
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    // 显示键盘，隐藏panelview
                    keyboardLayout.showKeyboard4();
                }
                return false;
            }
        });


    }


    /**
     * 让ScrollView滚动到底部
     *
     * @param decorView
     * @param scroll
     */
    public static void scrollToBottom(final View decorView, final View scroll) {

        Handler mHandler = new Handler();
        mHandler.post(new Runnable() {
            public void run() {
                if (scroll == null || decorView == null) {
                    return;
                }
                int offset = decorView.getMeasuredHeight() - scroll.getHeight();
                if (offset < 0) {
                    offset = 0;
                }
                scroll.scrollTo(0, offset);
            }
        });
    }


    @OnClick({R.id.ll_back, R.id.bt_send, R.id.ib_add})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_add:
                tagAdapter.add("测试");
                tagAdapter.notifyDataChanged();
                break;
            case R.id.ll_back:
                this.finish();
                break;
            case R.id.bt_send:
                break;
        }
    }

    @Override
    public void onImageClick(View view, ImageEntity imageEntity) {

    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        // 获取焦点
        keyboardLayout.showKeyboard4();
        return false;
    }
}
