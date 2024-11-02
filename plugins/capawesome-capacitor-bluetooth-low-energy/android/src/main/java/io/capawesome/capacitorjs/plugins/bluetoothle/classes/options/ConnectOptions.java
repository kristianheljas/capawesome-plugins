package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;

public class ConnectOptions {

    @NonNull
    private String deviceId;

    public ConnectOptions(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }
}