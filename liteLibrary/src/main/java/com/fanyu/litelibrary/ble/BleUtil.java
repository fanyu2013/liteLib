package com.fanyu.litelibrary.ble;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.widget.Toast;

/**
 *
 * Created by fanyu on 16/6/22.
 */
public class BleUtil {

    public static void getPermission(Activity activity) {
        //请求权限
        ActivityCompat.requestPermissions(activity,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},
                1);
        //判断是否需要 向用户解释，为什么要申请该权限
        ActivityCompat.shouldShowRequestPermissionRationale(activity,
                Manifest.permission.READ_CONTACTS);
    }

    @TargetApi(18)
    public static BluetoothAdapter getBleAdapter(Activity activity) {

        // Use this check to determine whether BLE is supported on the device.  Then you can
        // selectively disable BLE-related features.
        if (!activity.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE)) {
            Toast.makeText(activity, "您的手机不支持BLE！" , Toast.LENGTH_SHORT).show();
            activity.finish();
        }

        final BluetoothManager bluetoothManager =
                (BluetoothManager) activity.getSystemService(Context.BLUETOOTH_SERVICE);
        BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(activity, "您的手机不支持蓝牙！" , Toast.LENGTH_SHORT).show();
            activity.finish();
            return null;
        }

        return mBluetoothAdapter;
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    public static void enableBluetooth(Activity activity, BluetoothAdapter mBluetoothAdapter
            , int requestCode) {
        // enable Bluetooth.
        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(enableBtIntent, requestCode);
        }
    }
}
