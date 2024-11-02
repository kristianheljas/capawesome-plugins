import Foundation
import CoreBluetooth
import Capacitor

@objc public class ReadRssiResult: NSObject, Result {
    let rssi: NSNumber

    init(rssi: NSNumber) {
        self.rssi = rssi
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["rssi"] = rssi
        return result as AnyObject
    }
}
