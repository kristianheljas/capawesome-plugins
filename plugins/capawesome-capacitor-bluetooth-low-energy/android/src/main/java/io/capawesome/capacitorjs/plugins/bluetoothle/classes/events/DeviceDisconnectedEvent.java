package io.capawesome.capacitorjs.plugins.bluetoothle.classes.events;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.Result;

public class DeviceDisconnectedEvent implements Result {

    @NonNull
    private BluetoothDevice device;

    public DeviceDisconnectedEvent(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothDevice getDevice() {
        return device;
    }

    @SuppressLint("MissingPermission")
    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("id", device.getAddress());
        result.put("name", device.getName());
        return result;
    }
}
