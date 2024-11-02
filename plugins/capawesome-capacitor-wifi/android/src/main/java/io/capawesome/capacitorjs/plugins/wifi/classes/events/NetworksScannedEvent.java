package io.capawesome.capacitorjs.plugins.wifi.classes.events;

import android.net.wifi.ScanResult;
import android.os.Build;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.wifi.interfaces.Result;
import java.util.List;

public class NetworksScannedEvent implements Result {

    private List<ScanResult> results;

    public NetworksScannedEvent(List<ScanResult> results) {
        this.results = results;
    }

    public JSObject toJSObject() {
        JSArray networksResult = new JSArray();
        for (ScanResult result : results) {
            JSObject networkResult = new JSObject();
            networkResult.put("rssi", result.level);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                JSArray securityTypesResult = new JSArray();
                for (int securityType : result.getSecurityTypes()) {
                    securityTypesResult.put(securityType);
                }
                networkResult.put("securityTypes", securityTypesResult);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                networkResult.put("ssid", result.getWifiSsid());
            } else {
                networkResult.put("ssid", result.SSID);
            }
            networksResult.put(networkResult);
        }

        JSObject result = new JSObject();
        result.put("networks", networksResult);
        return result;
    }
}
