package com.zx.customview.yzm;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.don.verificationviewlibrary.VerificationView;
import com.zx.customview.R;

public class NumberYzmActivity extends AppCompatActivity {

    VerificationView vv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_yzm);

        vv= (VerificationView) findViewById(R.id.vv);
        Button btnReset= (Button) findViewById(R.id.btnReset);

        vv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.reset();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vv.reset();
                Log.i("MyLog","验证码是="+vv.getText());
            }
        });
    }
}
