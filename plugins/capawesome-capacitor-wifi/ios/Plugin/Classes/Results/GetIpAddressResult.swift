import Foundation
import CoreBluetooth
import Capacitor

@objc public class GetIpAddressResult: NSObject, Result {
    private let ipAddress: String

    init(ipAddress: String) {
        self.ipAddress = ipAddress
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["address"] = ipAddress
        return result as AnyObject
    }
}
