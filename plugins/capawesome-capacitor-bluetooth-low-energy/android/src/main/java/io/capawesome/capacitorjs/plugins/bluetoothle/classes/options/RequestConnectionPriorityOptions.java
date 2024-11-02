package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;

public class RequestConnectionPriorityOptions {

    @NonNull
    private String deviceId;

    @NonNull
    private int connectionPriority;

    public RequestConnectionPriorityOptions(@NonNull String deviceId, @NonNull int connectionPriority) {
        this.deviceId = deviceId;
        this.connectionPriority = connectionPriority;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }

    @NonNull
    public int getConnectionPriority() {
        return connectionPriority;
    }
}
