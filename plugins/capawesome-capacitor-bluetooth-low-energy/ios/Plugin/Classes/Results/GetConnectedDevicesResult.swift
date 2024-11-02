import Foundation
import CoreBluetooth
import Capacitor

@objc public class GetConnectedDevicesResult: NSObject, Result {
    let peripherals: [CBPeripheral]

    init(peripherals: [CBPeripheral]) {
        self.peripherals = peripherals
    }

    public func toJSObject() -> AnyObject {
        var devicesResult = JSArray()
        for peripheral in peripherals {
            var deviceResult = JSObject()
            deviceResult["id"] = peripheral.identifier.uuidString
            deviceResult["name"] = peripheral.name
            devicesResult.append(deviceResult)
        }

        var result = JSObject()
        result["devices"] = devicesResult
        return result as AnyObject
    }
}
