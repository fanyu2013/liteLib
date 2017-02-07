package com.fanyu.litelibrary.ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import com.fanyu.litelibrary.ble.entity.BleConnection;
import com.fanyu.litelibrary.ble.entity.ScannedDevices;

import java.util.List;

/**
 *
 * Created by fanyu on 17/1/20.
 */
public interface BleManager_I {
    void enableBluetooth(Activity activity);
    void scan(boolean enable);
    ScannedDevices scannedDevices();
    BluetoothAdapter bluetoothAdapter();
    boolean connect(BluetoothDevice device);
    List<BleConnection> connections();
}
