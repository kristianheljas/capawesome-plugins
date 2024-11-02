package io.capawesome.capacitorjs.plugins.bluetoothle.classes.results;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothDevice;
import android.content.pm.PackageManager;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.Result;
import java.util.List;

public class GetConnectedDevicesResult implements Result {

    private List<BluetoothDevice> devices;

    public GetConnectedDevicesResult(List<BluetoothDevice> devices) {
        this.devices = devices;
    }

    @SuppressLint("MissingPermission")
    @NonNull
    public JSObject toJSObject() {
        JSArray devicesResult = new JSArray();
        for (BluetoothDevice device : devices) {
            JSObject deviceResult = new JSObject();
            deviceResult.put("id", device.getAddress());
            deviceResult.put("name", device.getName());
            devicesResult.put(deviceResult);
        }

        JSObject result = new JSObject();
        result.put("devices", devicesResult);
        return result;
    }
}
