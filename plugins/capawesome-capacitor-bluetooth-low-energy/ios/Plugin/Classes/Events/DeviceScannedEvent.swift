import Foundation
import CoreBluetooth
import Capacitor

@objc public class DeviceScannedEvent: NSObject, Result {
    private var peripheral: CBPeripheral
    private var rssi: NSNumber

    init(peripheral: CBPeripheral, rssi: NSNumber) {
        self.peripheral = peripheral
        self.rssi = rssi
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["id"] = peripheral.identifier.uuidString
        result["name"] = peripheral.name
        result["rssi"] = rssi
        return result as AnyObject
    }
}
