import Foundation
import Capacitor
import CoreBluetooth

@objc public class CharacteristicChangedEvent: NSObject, Result {
    private var characteristic: CBCharacteristic
    private var peripheral: CBPeripheral

    init(characteristic: CBCharacteristic, peripheral: CBPeripheral) {
        self.characteristic = characteristic
        self.peripheral = peripheral
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["characteristicId"] = BluetoothLowEnergyHelper.convertCBUUIDToString(uuid: characteristic.uuid)
        result["deviceId"] = peripheral.identifier.uuidString
        if let service = characteristic.service {
            result["serviceId"] = BluetoothLowEnergyHelper.convertCBUUIDToString(uuid: service.uuid)
        }
        result["value"] = BluetoothLowEnergyHelper.convertDataToJsonArray(data: characteristic.value ?? Data())
        return result as AnyObject
    }
}
