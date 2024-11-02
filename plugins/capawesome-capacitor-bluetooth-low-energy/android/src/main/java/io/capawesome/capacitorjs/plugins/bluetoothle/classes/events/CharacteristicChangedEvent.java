package io.capawesome.capacitorjs.plugins.bluetoothle.classes.events;

import android.bluetooth.BluetoothGattCharacteristic;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.bluetoothle.BluetoothLowEnergyHelper;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.Result;

public class CharacteristicChangedEvent implements Result {

    private BluetoothGattCharacteristic characteristic;

    @NonNull
    private String deviceId;

    public CharacteristicChangedEvent(BluetoothGattCharacteristic characteristic, @NonNull String deviceId) {
        this.characteristic = characteristic;
        this.deviceId = deviceId;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("characteristicId", characteristic.getUuid().toString());
        result.put("deviceId", deviceId);
        result.put("serviceId", characteristic.getService().getUuid().toString());
        result.put("value", BluetoothLowEnergyHelper.convertBytesToJsonArray(characteristic.getValue()));
        return result;
    }
}
