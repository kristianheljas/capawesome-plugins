import Foundation
import Capacitor

@objc public class ReadRssiOptions: NSObject {
    private var deviceId: String

    init(deviceId: String) {
        self.deviceId = deviceId
    }

    func getDeviceId() -> String {
        return deviceId
    }
}
