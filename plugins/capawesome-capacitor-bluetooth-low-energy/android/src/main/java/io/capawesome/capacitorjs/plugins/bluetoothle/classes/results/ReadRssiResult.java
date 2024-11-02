package io.capawesome.capacitorjs.plugins.bluetoothle.classes.results;

import androidx.annotation.NonNull;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.Result;

public class ReadRssiResult implements Result {

    private Integer rssi;

    public ReadRssiResult(@NonNull Integer rssi) {
        this.rssi = rssi;
    }

    @NonNull
    public JSObject toJSObject() {
        JSObject result = new JSObject();
        result.put("rssi", rssi);
        return result;
    }
}
