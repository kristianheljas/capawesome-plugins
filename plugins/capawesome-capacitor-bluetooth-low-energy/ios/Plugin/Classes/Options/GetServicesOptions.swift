import Foundation
import Capacitor

@objc public class GetServicesOptions: NSObject {
    private var deviceId: String

    init(deviceId: String) {
        self.deviceId = deviceId
    }

    func getDeviceId() -> String {
        return deviceId
    }
}
