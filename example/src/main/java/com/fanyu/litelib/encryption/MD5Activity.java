package com.fanyu.litelib.encryption;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.fanyu.litelib.R;
import com.fanyu.litelibrary.util.Md5Util_Abs;

public class MD5Activity extends AppCompatActivity {

    private EditText et_input;
    private TextView tv_result;
    private Button btn1,btn2,btn3,btn4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_md5);
        setupView();
        setupListener();
    }

    private void setupView() {
        et_input = (EditText) findViewById(R.id.et_input);
        tv_result = (TextView) findViewById(R.id.tv_result);
        btn1 = (Button) findViewById(R.id.btn1);
        btn2 = (Button) findViewById(R.id.btn2);
        btn3 = (Button) findViewById(R.id.btn3);
        btn4 = (Button) findViewById(R.id.btn4);
    }

    private void setupListener() {
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result.setText(Md5Util_Abs.parseStrToMd5L32(et_input.getText().toString()));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result.setText(Md5Util_Abs.parseStrToMd5U32(et_input.getText().toString()));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result.setText(Md5Util_Abs.parseStrToMd5L16(et_input.getText().toString()));
            }
        });
        btn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_result.setText(Md5Util_Abs.parseStrToMd5U16(et_input.getText().toString()));
            }
        });
    }
}
