package com.fanyu.litelib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.fanyu.litelib.encryption.MD5Activity;
import com.fanyu.litelib.ui.editText.DeleteEditTextActivity;
import com.fanyu.litelibrary.DeleteEditText;
import com.fanyu.litelibrary.util.TestUtil;

public class MainActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();
    private Button btn_md5;
    private Button btn_delete_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupView();
        setupListener();
    }

    private void setupView() {
        btn_md5 = (Button) findViewById(R.id.btn_md5);
        btn_delete_et = (Button) findViewById(R.id.btn_delete_et);
    }

    private void setupListener() {
        btn_delete_et.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DeleteEditTextActivity.class);
                startActivity(intent);
            }
        });
        btn_md5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MD5Activity.class);
                startActivity(intent);
            }
        });
    }
}
