package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import android.bluetooth.BluetoothGattCharacteristic;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.JSArray;
import io.capawesome.capacitorjs.plugins.bluetoothle.BluetoothLowEnergyHelper;
import org.json.JSONException;

public class WriteCharacteristicOptions {

    @NonNull
    private String characteristicId;

    @NonNull
    private String deviceId;

    @NonNull
    private String serviceId;

    @NonNull
    private int type;

    @NonNull
    private byte[] value;

    public WriteCharacteristicOptions(
        @NonNull String characteristicId,
        @NonNull String deviceId,
        @NonNull String serviceId,
        @Nullable String type,
        @NonNull JSArray value
    ) throws JSONException {
        this.characteristicId = characteristicId;
        this.deviceId = deviceId;
        this.serviceId = serviceId;
        this.value = BluetoothLowEnergyHelper.convertJsonArrayToByteArray(value);
    }

    @NonNull
    public String getCharacteristicId() {
        return characteristicId;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }

    @NonNull
    public String getServiceId() {
        return serviceId;
    }

    public int getType() {
        return type;
    }

    @NonNull
    public byte[] getValue() {
        return value;
    }

    private int getTypeFromString(String type) {
        switch (type) {
            case "withoutResponse":
                return BluetoothGattCharacteristic.WRITE_TYPE_NO_RESPONSE;
            default:
                return BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT;
        }
    }
}
