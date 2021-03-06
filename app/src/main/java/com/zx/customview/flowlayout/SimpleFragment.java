package com.zx.customview.flowlayout;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.customview.R;
import com.zx.flowlayout.FlowLayout;
import com.zx.flowlayout.HyTagFlowLayout;
import com.zx.flowlayout.TagAdapter;


/**
 * Created by zhy on 15/9/10.
 */
public class SimpleFragment extends Fragment {

    private String[] mVals = new String[]
            {"Hello", "Android", "Weclome Hi ", "Button", "TextView", "Hello",
                    "Android", "Weclome", "Button ImageView", "TextView", "Helloworld",
                    "Android", "Weclome Hello", "Button Text", "TextView"};

    private HyTagFlowLayout mFlowLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_test, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final LayoutInflater mInflater = LayoutInflater.from(getActivity());
        mFlowLayout = (HyTagFlowLayout) view.findViewById(R.id.id_flowlayout);
        mFlowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "FlowLayout Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        mFlowLayout.setAdapter(new TagAdapter<String>(mVals) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                TextView tv = (TextView) mInflater.inflate(R.layout.tv,
                        mFlowLayout, false);
                tv.setText(s);
                return tv;
            }

            @Override
            public View getLabelView(FlowLayout parent) {
                return null;
            }

            @Override
            public View getInputView(FlowLayout parent) {
                return null;
            }

        });
    }
}
