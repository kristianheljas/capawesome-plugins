package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;

public class RequestMtuOptions {

    @NonNull
    private String deviceId;

    @NonNull
    private int mtu;

    public RequestMtuOptions(@NonNull String deviceId, @NonNull int mtu) {
        this.deviceId = deviceId;
        this.mtu = mtu;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }

    @NonNull
    public int getMtu() {
        return mtu;
    }
}
