package com.fanyu.litelibrary.ble.entity;

import android.Manifest;
import android.bluetooth.BluetoothDevice;
import android.support.annotation.RequiresPermission;

import com.fanyu.litelibrary.ble.event.FindDeviceEvent;
import com.fanyu.litelibrary.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 *
 * Created by fanyu on 17/2/4.
 */
public class ScannedDevices {

    private ArrayList<BluetoothDevice> bLeDevices = new ArrayList<BluetoothDevice>();

    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public void add(BluetoothDevice device) {
        if (!bLeDevices.contains(device)) {
            bLeDevices.add(device);
            LogUtil.i("我的蓝牙-ScannedDevices-->发现设备",device.getName()+"---"+device.getAddress()+
                    "---- 共"+bLeDevices.size()+"个");
            EventBus.getDefault().post(new FindDeviceEvent(device));
        }
    }

    public void clear() {
        LogUtil.i("我的蓝牙-ScannedDevices-->清除设备","bLeDevices.clear()"+
                "---- 共"+bLeDevices.size()+"个");
        this.bLeDevices.clear();
    }

    public ArrayList<BluetoothDevice> getbLeDevices() {
        return bLeDevices;
    }
}
