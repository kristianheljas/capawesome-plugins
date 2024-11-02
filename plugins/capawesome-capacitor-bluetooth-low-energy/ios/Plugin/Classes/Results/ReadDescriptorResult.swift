import Foundation
import CoreBluetooth
import Capacitor

@objc public class ReadDescriptorResult: NSObject, Result {
    let descriptor: CBDescriptor

    init(descriptor: CBDescriptor) {
        self.descriptor = descriptor
    }

    public func toJSObject() -> AnyObject {
        var result = JSObject()
        result["value"] = BluetoothLowEnergyHelper.convertAnyToJsonArray(data: descriptor.value)
        return result as AnyObject
    }
}
