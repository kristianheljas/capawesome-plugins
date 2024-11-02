package io.capawesome.capacitorjs.plugins.wifi.classes.results;

import android.net.wifi.WifiInfo;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.wifi.interfaces.Result;

public class GetSsidResult implements Result {

    private String ssid;

    public GetSsidResult(@NonNull WifiInfo wifiInfo) {
        this.ssid = wifiInfo.getSSID().substring(1, wifiInfo.getSSID().length() - 1);
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("ssid", ssid);
        return result;
    }
}
