package io.capawesome.capacitorjs.plugins.wifi.classes.options;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class ConnectOptions {

    @NonNull
    private String ssid;

    @Nullable
    private String password;

    private boolean isHiddenSsid;

    public ConnectOptions(@NonNull String ssid, @Nullable String password, boolean isHiddenSsid) {
        this.ssid = ssid;
        this.password = password;
        this.isHiddenSsid = isHiddenSsid;
    }

    @NonNull
    public String getSsid() {
        return ssid;
    }

    @Nullable
    public String getPassword() {
        return password;
    }

    public boolean isHiddenSsid() {
        return isHiddenSsid;
    }
}
