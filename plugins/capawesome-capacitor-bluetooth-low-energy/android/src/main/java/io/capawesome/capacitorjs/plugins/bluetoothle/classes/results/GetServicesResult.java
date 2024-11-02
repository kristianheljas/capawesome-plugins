package io.capawesome.capacitorjs.plugins.bluetoothle.classes.results;

import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import io.capawesome.capacitorjs.plugins.bluetoothle.BluetoothLowEnergyHelper;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.Result;
import java.util.List;
import org.json.JSONArray;

public class GetServicesResult implements Result {

    private List<BluetoothGattService> services;

    public GetServicesResult(List<BluetoothGattService> services) {
        this.services = services;
    }

    @NonNull
    public JSObject toJSObject() {
        JSArray servicesResult = new JSArray();
        for (BluetoothGattService service : services) {
            JSArray characteristicsResult = new JSArray();
            for (BluetoothGattCharacteristic characteristic : service.getCharacteristics()) {
                JSArray descriptorsResult = new JSArray();
                for (BluetoothGattDescriptor descriptor : characteristic.getDescriptors()) {
                    JSObject descriptorResult = new JSObject();
                    descriptorResult.put("id", descriptor.getUuid().toString());
                    descriptorsResult.put(descriptorResult);
                }

                JSObject characteristicResult = new JSObject();
                characteristicResult.put("id", characteristic.getUuid().toString());
                characteristicResult.put("descriptors", descriptorsResult);
                characteristicResult.put("properties", createCharacteristicPropertiesResult(characteristic));
                characteristicsResult.put(characteristicResult);
            }

            JSObject serviceResult = new JSObject();
            serviceResult.put("id", service.getUuid().toString());
            serviceResult.put("characteristics", characteristicsResult);
            servicesResult.put(serviceResult);
        }

        JSObject result = new JSObject();
        result.put("services", servicesResult);
        return result;
    }

    @NonNull
    private JSObject createCharacteristicPropertiesResult(BluetoothGattCharacteristic characteristic) {
        JSObject properties = new JSObject();
        properties.put("broadcast", (characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_BROADCAST) > 0);
        properties.put("read", (characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_READ) > 0);
        properties.put(
            "writeWithoutResponse",
            (characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_WRITE_NO_RESPONSE) > 0
        );
        properties.put("write", (characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_WRITE) > 0);
        properties.put("notify", (characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0);
        properties.put("indicate", (characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) > 0);
        properties.put(
            "authenticatedSignedWrites",
            (characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_SIGNED_WRITE) > 0
        );
        properties.put("extendedProperties", (characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_EXTENDED_PROPS) > 0);
        return properties;
    }
}
