package com.fanyu.litelib.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fanyu.litelib.R;
import com.fanyu.litelibrary.ble.entity.BleConnection;
import com.fanyu.litelibrary.ble.event.FindDeviceEvent;
import com.fanyu.litelibrary.lite;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ScanListActivity extends AppCompatActivity {

    private ListView lv_device;
    private LeDeviceListAdapter leDeviceListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        setContentView(R.layout.activity_scan_list);
        lv_device = (ListView) findViewById(R.id.lv_device);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lite.ble().scan(true);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDeviceFindEvent(FindDeviceEvent event) {
        if (leDeviceListAdapter == null){
            leDeviceListAdapter = new LeDeviceListAdapter(this);
            lv_device.setAdapter(leDeviceListAdapter);
            lv_device.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final BluetoothDevice device = leDeviceListAdapter.getDevice(position);
                    if (device == null) return;
                    lite.ble().connect(device);
//                    if (BleConnection.getConnection(device,lite.ble().connections())==null){
//                        lite.ble().connect(device);
//                    }else {
//                        BleConnection.getConnection(device,lite.ble().connections()).reConnect();
//                    }
//                    final Intent intent = new Intent(BluetoothListActivity.this, MainActivity.class);
//                    intent.putExtra(MainActivity.EXTRAS_DEVICE_NAME, device.getName());
//                    intent.putExtra(MainActivity.EXTRAS_DEVICE_ADDRESS, device.getAddress());
//                    blueTooth = blueTooth.close();
//                    startActivity(intent);
//                    finish();
                }
            });
        }
        leDeviceListAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
