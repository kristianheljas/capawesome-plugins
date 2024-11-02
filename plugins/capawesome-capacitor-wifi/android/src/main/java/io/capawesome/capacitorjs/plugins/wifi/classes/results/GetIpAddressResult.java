package io.capawesome.capacitorjs.plugins.wifi.classes.results;

import android.net.wifi.WifiInfo;
import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.wifi.WifiHelper;
import io.capawesome.capacitorjs.plugins.wifi.interfaces.Result;

public class GetIpAddressResult implements Result {

    private int ipAddress;

    public GetIpAddressResult(@NonNull WifiInfo wifiInfo) {
        this.ipAddress = wifiInfo.getIpAddress();
    }

    public JSObject toJSObject() {
        String ipAddressString = WifiHelper.convertIpAddressIntToString(ipAddress);

        JSObject result = new JSObject();
        result.put("address", ipAddressString);
        return result;
    }
}
