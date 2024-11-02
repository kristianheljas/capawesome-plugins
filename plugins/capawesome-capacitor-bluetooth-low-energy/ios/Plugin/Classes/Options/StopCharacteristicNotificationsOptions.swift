import Foundation
import Capacitor

@objc public class StopCharacteristicNotificationsOptions: NSObject {
    private var characteristicId: String
    private var deviceId: String
    private var serviceId: String

    init(characteristicId: String, deviceId: String, serviceId: String) {
        self.characteristicId = characteristicId
        self.deviceId = deviceId
        self.serviceId = serviceId
    }

    func getCharacteristicId() -> String {
        return characteristicId
    }

    func getDeviceId() -> String {
        return deviceId
    }

    func getServiceId() -> String {
        return serviceId
    }
}
