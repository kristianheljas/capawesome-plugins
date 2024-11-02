package io.capawesome.capacitorjs.plugins.bluetoothle.classes.results;

import android.bluetooth.BluetoothGattCharacteristic;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.bluetoothle.BluetoothLowEnergyHelper;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.Result;
import org.json.JSONArray;

public class ReadCharacteristicResult implements Result {

    private BluetoothGattCharacteristic characteristic;

    public ReadCharacteristicResult(BluetoothGattCharacteristic characteristic) {
        this.characteristic = characteristic;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("value", BluetoothLowEnergyHelper.convertBytesToJsonArray(characteristic.getValue()));
        return result;
    }
}
