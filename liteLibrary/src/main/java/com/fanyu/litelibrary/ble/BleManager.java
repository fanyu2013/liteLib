package com.fanyu.litelibrary.ble;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.Handler;
import android.support.annotation.RequiresPermission;

import com.fanyu.litelibrary.ble.entity.BleConnection;
import com.fanyu.litelibrary.ble.entity.ScannedDevices;
import com.fanyu.litelibrary.ble.event.BlueToothOpenEvent;
import com.fanyu.litelibrary.ble.event.ConnectionNewEvent;
import com.fanyu.litelibrary.lite;
import com.fanyu.litelibrary.util.LogUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * Created by fanyu on 17/1/22.
 */
public class BleManager implements BleManager_I {

    private static final Object lock = new Object();
    private static volatile BleManager instance;
    private final static String TAG = "我的蓝牙-BleManager ";
    private Handler mHandler;

    private BluetoothAdapter mBluetoothAdapter;
    public static final int ENABLE_BLUETOOTH = 1;
    private BluetoothAdapter.LeScanCallback mLeScanCallback = null;
    private boolean isScanning;
    private ScannedDevices scannedDevices = new ScannedDevices();

    //connection
    List<BleConnection> connections = new ArrayList<>();

    @RequiresPermission(Manifest.permission.BLUETOOTH)
    private BleManager() {
        mHandler = new Handler();
        setScanCallback();
    }

    public boolean isScanning() {
        return isScanning;
    }

    @TargetApi(18)
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    private void setScanCallback() {
        if (mLeScanCallback!=null)return;
        mLeScanCallback =
                new BluetoothAdapter.LeScanCallback() {
                    @Override
                    @RequiresPermission(Manifest.permission.BLUETOOTH)
                    public void onLeScan(final BluetoothDevice device, int rssi, byte[] scanRecord) {
                        scannedDevices.add(device);
                    }
                };
    }

    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public static void registerInstance() {
        if (instance == null) {
            synchronized (lock) {
                if (instance == null) {
                    instance = new BleManager();
                }
            }
        }
        lite.Is.setBleManager_i(instance);
    }

    @Override
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public void enableBluetooth(Activity activity) {
        if (mBluetoothAdapter==null){
            mBluetoothAdapter = BleUtil.getBleAdapter(activity);
        }
        //开启蓝牙
        // 如果本地蓝牙没有开启，则开启
        if (mBluetoothAdapter==null)return;
        if (!mBluetoothAdapter.isEnabled()) {
            // 我们通过startActivityForResult()方法发起的Intent将会在onActivityResult()回调方法中获取用户的选择，比如用户单击了Yes开启，
            // 那么将会收到RESULT_OK的结果，
            // 如果RESULT_CANCELED则代表用户不愿意开启蓝牙
            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            activity.startActivityForResult(mIntent, ENABLE_BLUETOOTH);
            // 用enable()方法来开启，无需询问用户(实惠无声息的开启蓝牙设备),这时就需要用到android.permission.BLUETOOTH_ADMIN权限。
            // mBluetoothAdapter.enable();
            // mBluetoothAdapter.disable();//关闭蓝牙
        }else {
            EventBus.getDefault().post(new BlueToothOpenEvent());
        }
    }

    @Override
    @TargetApi(18)
    @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
    public void scan(boolean enable) {
        if (enable) {
            LogUtil.i(TAG,"开始扫描");
            // Stops scanning after a pre-defined scan period.
            mHandler.postDelayed(new Runnable() {

                @RequiresPermission(Manifest.permission.BLUETOOTH_ADMIN)
                public void run() {
                    isScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, 100000);
            isScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        } else {
            LogUtil.i(TAG,"停止扫描");
            isScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }

    @Override
    public ScannedDevices scannedDevices() {
        return scannedDevices;
    }

    @Override
    public BluetoothAdapter bluetoothAdapter() {
        return mBluetoothAdapter;
    }

    @Override
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public boolean connect(BluetoothDevice device) {
        BleConnection connection;
        if (BleConnection.contains(device,connections)){
            connection = BleConnection.getConnection(device,connections);
        }else {
            connection = new BleConnection(device);
            connections.add(connection);
            EventBus.getDefault().post(new ConnectionNewEvent(device));
        }
        return connection.connect();
    }

    @Override
    @RequiresPermission(Manifest.permission.BLUETOOTH)
    public BleConnection createConnection(BluetoothDevice device) {
        BleConnection connection;
        if (BleConnection.contains(device,connections)){
            connection = BleConnection.getConnection(device,connections);
        }else {
            connection = new BleConnection(device);
            connections.add(connection);
            EventBus.getDefault().post(new ConnectionNewEvent(device));
        }
        return connection;
    }

    @Override
    public List<BleConnection> connections() {
        return connections;
    }
}
