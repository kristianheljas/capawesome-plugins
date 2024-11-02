package io.capawesome.capacitorjs.plugins.bluetoothle.classes.events;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.Result;

public class DeviceScannedEvent implements Result {

    @NonNull
    private BluetoothDevice device;

    private int rssi;

    public DeviceScannedEvent(BluetoothDevice device, int rssi) {
        this.device = device;
        this.rssi = rssi;
    }

    @SuppressLint("MissingPermission")
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("id", device.getAddress());
        result.put("name", device.getName());
        result.put("rssi", rssi);
        return result;
    }
}
