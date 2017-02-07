package com.fanyu.litelib;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.fanyu.litelib.ble.BLEActivity;
import com.fanyu.litelib.util.MD5Activity;
import com.fanyu.litelib.view.DeleteEditTextActivity;

public class MainActivity extends AppCompatActivity {
    public final String TAG = this.getClass().getSimpleName();
    private Button btn_md5,btn_delete_et,btn_ble;

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
        btn_ble = (Button) findViewById(R.id.btn_ble);
    }

    private void setupListener() {
        btn_ble.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, BLEActivity.class);
                startActivity(intent);
            }
        });
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
