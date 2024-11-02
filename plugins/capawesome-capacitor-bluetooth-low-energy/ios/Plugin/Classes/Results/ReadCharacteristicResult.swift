import Foundation
import CoreBluetooth
import Capacitor

@objc public class ReadCharacteristicResult: NSObject, Result {
    let characteristic: CBCharacteristic

    init(characteristic: CBCharacteristic) {
        self.characteristic = characteristic
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["value"] = BluetoothLowEnergyHelper.convertDataToJsonArray(data: characteristic.value)
        return result as AnyObject
    }
}
