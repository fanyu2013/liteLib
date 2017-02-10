package com.fanyu.litelibrary.ble.entity;

import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCharacteristic;

/**
 *
 * Created by fanyu on 17/2/9.
 */
public interface BleConnectionCallback {
    void startToConnect();
    void connected();
    void disConnected();
    void onServicesDiscovered(BluetoothGatt gatt, int status);
    void onCharacteristicRead(BluetoothGatt gatt,
                              BluetoothGattCharacteristic characteristic,
                              int status);
    void onCharacteristicChanged(BluetoothGatt gatt,
                            BluetoothGattCharacteristic characteristic);
}
