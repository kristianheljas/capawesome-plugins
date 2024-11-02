package io.capawesome.capacitorjs.plugins.bluetoothle.classes.results;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.bluetoothle.BluetoothLowEnergyHelper;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.Result;

public class ReadDescriptorResult implements Result {

    private BluetoothGattDescriptor descriptor;

    public ReadDescriptorResult(BluetoothGattDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("value", BluetoothLowEnergyHelper.convertBytesToJsonArray(descriptor.getValue()));
        return result;
    }
}
