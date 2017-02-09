package com.fanyu.litelibrary.ble.entity;

import android.Manifest;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.support.annotation.RequiresPermission;
import android.util.Log;

import com.fanyu.litelibrary.ble.BlueToothUtil;
import com.fanyu.litelibrary.lite;
import com.fanyu.litelibrary.util.LogUtil;

import java.util.List;

/**
 *
 * Created by fanyu on 17/2/6.
 */
public class BleConnection {
    final static String TAG = "我的蓝牙-BleConnection ";
    private BluetoothDevice device;
    private BluetoothGattCallback mGattCallback = null;
    private BluetoothGatt mBluetoothGatt;
    private boolean isConnected = false;
    private BleConnectionCallback callback;

    public BleConnection(BluetoothDevice device) {
        this.device = device;
        setGattCallback();
    }

    public void setBleCallback(BleConnectionCallback callback) {
        this.callback = callback;
    }

    public BluetoothGatt getBluetoothGatt() {
        return mBluetoothGatt;
    }

    @TargetApi(18)
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    private void setGattCallback() {
        mGattCallback = new BluetoothGattCallback() {

            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    // Attempts to discover services after successful connection.
                    isConnected = true;
                    LogUtil.i(TAG,"连接成功,开始获取services");
                    callback.connected();
                    mBluetoothGatt.discoverServices();
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    isConnected = false;
                    LogUtil.i(TAG,"断开连接: "+device.getName()+" "+device.getAddress());
                    callback.disConnected();
                    lite.ble().scan(true);
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                callback.onServicesDiscovered(gatt,status);
                if (status == BluetoothGatt.GATT_SUCCESS) {
                    LogUtil.i(TAG, "serviceDiscover成功 = " + status);
                    if (!lite.isRelease()){
                        printServices(gatt);
                    }
                } else {
                    LogUtil.i(TAG, "serviceDiscover失败 = " + status);
                }
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt,
                                             BluetoothGattCharacteristic characteristic,
                                             int status) {
                callback.onCharacteristicRead(gatt,characteristic,status);
                LogUtil.i(TAG, "onCharacteristicRead");

            }

            @Override
            public void onCharacteristicChanged(BluetoothGatt gatt,
                                                BluetoothGattCharacteristic characteristic){
                callback.onCharacteristicChanged(gatt,characteristic);
                LogUtil.i(TAG,"onCharacteristicChanged");
            }

        };
    }

    @TargetApi(18)
    private void printServices(BluetoothGatt gatt) {
        List<BluetoothGattService> gattServices = gatt.getServices();
        if (gattServices == null) return;

        for (BluetoothGattService gattService : gattServices) {
            //-----Service的字段信息-----//
            int type = gattService.getType();
            LogUtil.i(TAG, "-->service type:" + BlueToothUtil.getServiceType(type));
            LogUtil.i(TAG, "-->includedServices size:" + gattService.getIncludedServices().size());
            LogUtil.i(TAG, "-->service uuid:" + gattService.getUuid());

            //-----Characteristics的字段信息-----//
            List<BluetoothGattCharacteristic> gattCharacteristics = gattService.getCharacteristics();
            for (final BluetoothGattCharacteristic gattCharacteristic : gattCharacteristics) {
                LogUtil.i(TAG, "---->char uuid:" + gattCharacteristic.getUuid());
                //打印
//                StringBuilder str = new StringBuilder();
//                short[] shoArray = new short[gattCharacteristic.getValue().length];
//                for (int i = 0; i<gattCharacteristic.getValue().length; i++){
//                    shoArray[i] = (short) (gattCharacteristic.getValue()[i] & 0xFF);
//                    str.append(shoArray[i]).append(",");
//                }
//                LogUtil.i(TAG, "---->char value:" + gattCharacteristic.getValue().toString());
                if (gattCharacteristic.getUuid().toString().equals("0000b81d-0000-1000-8000-00805f9b34fb")){
                    LogUtil.i("","");
                }

                int permission = gattCharacteristic.getPermissions();
                LogUtil.i(TAG, "---->char permission:" + BlueToothUtil.getCharPermission(permission));

                int property = gattCharacteristic.getProperties();
                LogUtil.i(TAG, "---->char property:" + BlueToothUtil.getCharPropertie(property));

                byte[] data = gattCharacteristic.getValue();
                if (data != null && data.length > 0) {
                    LogUtil.i(TAG, "---->char value:" + new String(data));
                }

                //UUID_KEY_DATA是可以跟蓝牙模块串口通信的Characteristic
//                if (gattCharacteristic.getUuid().toString().equals(Const.UUID_CHAR_READ)) {
//                    //测试读取当前Characteristic数据，会触发mOnDataAvailable.onCharacteristicRead()
////                    mHandler.postDelayed(new Runnable() {
////                        @Override
////                        public void run() {
////                            blueTooth.readCharacteristic(gattCharacteristic);
////                        }
////                    }, 500);
////                    blueTooth.readCharacteristic(gattCharacteristic);
//                    //接受Characteristic被写的通知,收到蓝牙模块的数据后会触发mOnDataAvailable.onCharacteristicWrite()
//                    Log.i(TAG, "notification = " +
//                            blueTooth.setCharacteristicNotification(gattCharacteristic, true));
//
//                    mNotifyCharacteristic = gattCharacteristic;
//                }

                //-----Descriptors的字段信息-----//
                List<BluetoothGattDescriptor> gattDescriptors = gattCharacteristic.getDescriptors();
                for (BluetoothGattDescriptor gattDescriptor : gattDescriptors) {
                    LogUtil.i(TAG, "-------->desc uuid:" + gattDescriptor.getUuid());
                    int descPermission = gattDescriptor.getPermissions();
                    LogUtil.i(TAG, "-------->desc permission:" + BlueToothUtil.getDescPermission(descPermission));
                    gattDescriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                    mBluetoothGatt.writeDescriptor(gattDescriptor);
                }
            }
        }
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    public void setDevice(BluetoothDevice device) {
        this.device = device;
    }

    public static boolean contains(BluetoothDevice device, List<BleConnection>connections){
        boolean contain = false;
        for (int i=0; i<connections.size(); i++){
            if (connections.get(i).getDevice().getAddress().equals(device.getAddress())){
                contain = true;
                break;
            }
        }
        return contain;
    }

    public static BleConnection getConnection(BluetoothDevice device, List<BleConnection>connections){
        for (int i=0; i<connections.size(); i++){
            if (connections.get(i).getDevice().getAddress().equals(device.getAddress())){
                return connections.get(i);
            }
        }
        return null;
    }

    public static BleConnection getConnection(String address, List<BleConnection>connections){
        for (int i=0; i<connections.size(); i++){
            if (connections.get(i).getDevice().getAddress().equals(address)){
                return connections.get(i);
            }
        }
        return null;
    }

    @TargetApi(18)
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public boolean connect(){
        if (lite.ble().bluetoothAdapter() == null || device==null ||  device.getAddress() == null) {
            LogUtil.i(TAG, "BluetoothAdapter没有初始化 或者 device为null");
            return false;
        }
        // 重连
        if (mBluetoothGatt != null) {
            LogUtil.i(TAG, "使用已存在的 mBluetoothGatt 进行重连");

            if (isConnected){
                mBluetoothGatt.disconnect();
                LogUtil.i(TAG,"断连接: mBluetoothGatt.disconnect();");
            }

            if (mBluetoothGatt.connect()) {
                LogUtil.i(TAG,"重连: mBluetoothGatt.connect()=true");
                return true;
            } else {
                LogUtil.i(TAG,"重连: mBluetoothGatt.connect()=false");
                return false;
            }
        }
        //第一次连接
        final BluetoothDevice device = lite.ble().bluetoothAdapter().getRemoteDevice(this.device.getAddress());
        if (device == null) {
            LogUtil.i(TAG, "没有找到设备,不能连接");
            return false;
        }
        // We want to directly connect to the device, so we are setting the autoConnect
        // parameter to false.
        mBluetoothGatt = device.connectGatt(lite.appContext(), false, mGattCallback);
        Log.i(TAG, "尝试建立新的连接: "+device.getName()+" "+device.getAddress());
        return true;
    }

    @TargetApi(18)
    public void disConnect(){
        if (mBluetoothGatt != null) {
            mBluetoothGatt.disconnect();
        }
    }
}


