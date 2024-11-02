import Foundation
import CoreBluetooth
import Capacitor

@objc public class GetSsidResult: NSObject, Result {
    private let ssid: String

    init(ssid: String) {
        self.ssid = ssid
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["ssid"] = ssid
        return result as AnyObject
    }
}
