package com.example.customview.activity.email;

import android.os.PersistableBundle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.customview.R;
import com.zxt.flowlayout.FlowLayout;
import com.zxt.flowlayout.TagAdapter;
import com.zxt.flowlayout.TagFlowLayout;

import java.util.ArrayList;

/**
 * # android流式布局，单选，带edittext，支持软键盘的输入新增和删减删除
 * https://blog.csdn.net/qugengting/article/details/81324030?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task
 */
public class EmailToActivity extends AppCompatActivity {

    private String[] mValues = new String[]{
            "a@qq.com", "b@qq.com",
            "c@126.com", "d@hotmail.com",
            "e@foxmail.com"};

    private static final String KEY_DATA_STORE = "key_data";
    private LayoutInflater inflater;
    private TagFlowLayout flowLayout;

    private ImageButton imgaddBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_email_to);

        inflater = LayoutInflater.from(this);
        flowLayout = findViewById(R.id.flowlayout);
        // 设置是否需要添加标签,默认添加
        flowLayout.setAttachLabel(true);
        flowLayout.setAdapter(tagAdapter);

        imgaddBtn = findViewById(R.id.ib_add);
        imgaddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tagAdapter.add("1004260403@qq.com");
                tagAdapter.add("张三");
                tagAdapter.add("李四");
                tagAdapter.notifyDataChanged();
            }
        });

    }

    private TagAdapter tagAdapter = new TagAdapter<String>(mValues) {
        @Override
        public View getView(FlowLayout parent, int position, String s) {
            TextView textView = (TextView) inflater.inflate(R.layout.item_flowlayout, parent, false);
            textView.setText(s);
            return textView;
        }

        @Override
        public View getLabelView(FlowLayout parent) {
            //如果设置flowLayout.setAttachLabel(false);该标签将不显示
            TextView tv = (TextView) inflater.inflate(R.layout.item_label, parent, false);
            tv.setText("收件人：");
            return tv;
        }

        @Override
        public View getInputView(FlowLayout parent) {
            return inflater.inflate(R.layout.item_edit, parent, false);
        }
    };


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        outState.putStringArrayList(KEY_DATA_STORE, (ArrayList<String>) tagAdapter.getTagData());
        super.onSaveInstanceState(outState, outPersistentState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        ArrayList<String> data = savedInstanceState.getStringArrayList(KEY_DATA_STORE);
        tagAdapter.resetData(data);
    }
}
