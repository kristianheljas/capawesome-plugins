package io.capawesome.capacitorjs.plugins.wifi.classes.results;

import android.net.wifi.WifiInfo;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.wifi.interfaces.Result;

public class GetRssiResult implements Result {

    private int rssi;

    public GetRssiResult(@NonNull WifiInfo wifiInfo) {
        this.rssi = wifiInfo.getRssi();
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("rssi", rssi);
        return result;
    }
}
