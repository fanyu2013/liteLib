package com.fanyu.litelibrary.ble.event;

import android.bluetooth.BluetoothDevice;

/**
 *
 * Created by fanyu on 17/2/4.
 */
public class FindDeviceEvent {
    public BluetoothDevice device;

    public FindDeviceEvent(BluetoothDevice device) {
        this.device = device;
    }
}
