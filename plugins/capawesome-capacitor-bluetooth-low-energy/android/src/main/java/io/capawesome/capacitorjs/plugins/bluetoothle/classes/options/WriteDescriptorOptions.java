package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import io.capawesome.capacitorjs.plugins.bluetoothle.BluetoothLowEnergyHelper;
import org.json.JSONException;

public class WriteDescriptorOptions {

    @NonNull
    private String characteristicId;

    @NonNull
    private String descriptorId;

    @NonNull
    private String deviceId;

    @NonNull
    private String serviceId;

    @NonNull
    private byte[] value;

    public WriteDescriptorOptions(
        @NonNull String characteristicId,
        @NonNull String descriptorId,
        @NonNull String deviceId,
        @NonNull String serviceId,
        @NonNull JSArray value
    ) throws JSONException {
        this.characteristicId = characteristicId;
        this.descriptorId = descriptorId;
        this.deviceId = deviceId;
        this.serviceId = serviceId;
        this.value = BluetoothLowEnergyHelper.convertJsonArrayToByteArray(value);
    }

    @NonNull
    public String getCharacteristicId() {
        return characteristicId;
    }

    @NonNull
    public String getDescriptorId() {
        return descriptorId;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }

    @NonNull
    public String getServiceId() {
        return serviceId;
    }

    public byte[] getValue() {
        return value;
    }
}
