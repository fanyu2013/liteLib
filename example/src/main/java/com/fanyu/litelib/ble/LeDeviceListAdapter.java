package com.fanyu.litelib.ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fanyu.litelib.R;
import com.fanyu.litelibrary.lite;

import java.util.ArrayList;

/**
 * Created by fanyu on 16/6/23.
 *
 */
public class LeDeviceListAdapter extends BaseAdapter {
    private LayoutInflater inflater;

    public LeDeviceListAdapter(Context context) {
        inflater = LayoutInflater.from(context);
    }

    public BluetoothDevice getDevice(int position) {
        return lite.ble().scannedDevices().getbLeDevices().get(position);
    }

    @Override
    public int getCount() {
        return lite.ble().scannedDevices().getbLeDevices().size();
    }

    @Override
    public Object getItem(int position) {
        return lite.ble().scannedDevices().getbLeDevices().get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(
                    R.layout.item_le_device_list_lv, null);
            holder = new ViewHolder();
            holder.deviceName = (TextView) convertView.findViewById(R.id.device_name);
            holder.deviceAddress = (TextView) convertView.findViewById(R.id.device_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        BluetoothDevice device = lite.ble().scannedDevices().getbLeDevices().get(position);
        final String deviceName = device.getName();

        if (deviceName != null && deviceName.length() > 0){
            holder.deviceName.setText(deviceName);
            holder.deviceAddress.setText(device.getAddress());
        }else{
            holder.deviceName.setText(R.string.unknown_device);
            if (device.getAddress()!=null && device.getAddress().length()>0){
                holder.deviceAddress.setText(device.getAddress());
            }else {
                holder.deviceAddress.setText("Unkown address");
            }
        }

        return convertView;
    }

    static class ViewHolder {
        public TextView deviceName, deviceAddress;
    }
}
