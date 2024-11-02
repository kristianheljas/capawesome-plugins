package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;

public class GetServicesOptions {

    @NonNull
    private String deviceId;

    public GetServicesOptions(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }
}
