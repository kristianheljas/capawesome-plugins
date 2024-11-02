package io.capawesome.capacitorjs.plugins.bluetoothle.classes.results;

import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.Result;

public class IsBondedResult implements Result {

    private boolean isBonded;

    public IsBondedResult(boolean isBonded) {
        this.isBonded = isBonded;
    }

    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("bonded", isBonded);
        return result;
    }
}
