package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;

public class StartCharacteristicNotificationsOptions {

    @NonNull
    private String characteristicId;

    @NonNull
    private String deviceId;

    @NonNull
    private String serviceId;

    public StartCharacteristicNotificationsOptions(@NonNull String characteristicId, @NonNull String deviceId, @NonNull String serviceId) {
        this.characteristicId = characteristicId;
        this.deviceId = deviceId;
        this.serviceId = serviceId;
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
}
