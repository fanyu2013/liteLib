package com.fanyu.litelibrary.ble.event;

import android.bluetooth.BluetoothDevice;

/**
 *
 * Created by fanyu on 17/2/9.
 */
public class ConnectionNewEvent {
    public BluetoothDevice device;

    public ConnectionNewEvent(BluetoothDevice device) {
        this.device = device;
    }
}
