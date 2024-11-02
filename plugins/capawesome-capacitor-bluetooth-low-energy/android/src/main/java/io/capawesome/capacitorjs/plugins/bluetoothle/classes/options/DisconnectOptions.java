package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;

public class DisconnectOptions {

    @NonNull
    private String deviceId;

    public DisconnectOptions(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }
}
