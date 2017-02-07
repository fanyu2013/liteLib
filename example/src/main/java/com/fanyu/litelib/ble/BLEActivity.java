package com.fanyu.litelib.ble;

import android.Manifest;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.fanyu.litelib.R;
import com.fanyu.litelibrary.ble.event.BlueToothOpenEvent;
import com.fanyu.litelibrary.lite;
import com.fastaccess.permission.base.PermissionHelper;
import com.fastaccess.permission.base.callback.OnPermissionCallback;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BLEActivity extends AppCompatActivity implements OnPermissionCallback {
    Button btn_open_bt,btn_scan_bt,btn_scan_list_bt,btn_disconnect_bt;
    String[] permissionArray = new String[]{
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);
        EventBus.getDefault().register(this);
        setupView();
        setupListener();
        getPermission();
    }

    private void setupView() {
        btn_open_bt = (Button)findViewById(R.id.btn_open_bt);
        btn_scan_bt = (Button)findViewById(R.id.btn_scan_bt);
        btn_scan_list_bt = (Button)findViewById(R.id.btn_scan_list_bt);
        btn_disconnect_bt = (Button)findViewById(R.id.btn_disconnect_bt);
    }

    private void setupListener() {
        btn_disconnect_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0; i<lite.ble().connections().size();i++){
                    lite.ble().connections().get(i).disConnect();
                }
            }
        });
        btn_scan_list_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BLEActivity.this,ScanListActivity.class);
                startActivity(intent);
            }
        });

        btn_scan_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lite.ble().scan(true);
            }
        });

        btn_open_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lite.ble().enableBluetooth(BLEActivity.this);
            }
        });
    }

    private void getPermission() {
        if (Integer.valueOf(android.os.Build.VERSION.SDK) < 23) {
            Log.d("version= ", android.os.Build.VERSION.SDK);
            return;
        }
        PermissionHelper.getInstance(this)
                .setForceAccepting(true) // default is false. its here so you know that it exists.
                .request(permissionArray);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lite.ble().enableBluetooth(BLEActivity.this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        lite.ble().scannedDevices().clear();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onBlueToothOpenEvent(BlueToothOpenEvent event) {
        Toast.makeText(this,"您已开启蓝牙",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                EventBus.getDefault().post(new BlueToothOpenEvent());
            } else if (resultCode == RESULT_CANCELED) {
                finish();
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionHelper.getInstance(this).onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void onPermissionGranted(@NonNull String[] permissionName) {

    }

    @Override
    public void onPermissionDeclined(@NonNull String[] permissionName) {
        finish();
    }

    @Override
    public void onPermissionPreGranted(@NonNull String permissionsName) {

    }

    @Override
    public void onPermissionNeedExplanation(@NonNull String permissionName) {
        PermissionHelper.getInstance(this).requestAfterExplanation(permissionArray);
    }

    @Override
    public void onPermissionReallyDeclined(@NonNull String permissionName) {
        finish();
    }

    @Override
    public void onNoPermissionNeeded() {

    }
}
