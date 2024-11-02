package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;

public class DiscoverServicesOptions {

    @NonNull
    private String deviceId;

    public DiscoverServicesOptions(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }
}
