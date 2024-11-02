package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;

public class ReadDescriptorOptions {

    @NonNull
    private String characteristicId;

    @NonNull
    private String descriptorId;

    @NonNull
    private String deviceId;

    @NonNull
    private String serviceId;

    public ReadDescriptorOptions(
        @NonNull String characteristicId,
        @NonNull String descriptorId,
        @NonNull String deviceId,
        @NonNull String serviceId
    ) {
        this.characteristicId = characteristicId;
        this.descriptorId = descriptorId;
        this.deviceId = deviceId;
        this.serviceId = serviceId;
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
}
