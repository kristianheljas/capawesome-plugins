package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;

public class CreateBondOptions {

    @NonNull
    private String deviceId;

    public CreateBondOptions(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }
}
