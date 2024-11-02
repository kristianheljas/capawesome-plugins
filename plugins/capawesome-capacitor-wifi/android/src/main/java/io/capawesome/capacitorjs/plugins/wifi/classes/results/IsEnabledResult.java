package io.capawesome.capacitorjs.plugins.wifi.classes.results;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.wifi.interfaces.Result;

public class IsEnabledResult implements Result {

    private boolean isEnabled;

    public IsEnabledResult(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("enabled", isEnabled);
        return result;
    }
}
