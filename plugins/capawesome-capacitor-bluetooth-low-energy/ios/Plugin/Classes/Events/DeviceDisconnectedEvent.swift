import Foundation
import CoreBluetooth
import Capacitor

@objc public class DeviceDisconnectedEvent: NSObject, Result {
    private var peripheral: CBPeripheral

    init(peripheral: CBPeripheral) {
        self.peripheral = peripheral
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["id"] = peripheral.identifier.uuidString
        result["name"] = peripheral.name
        return result as AnyObject
    }
}
