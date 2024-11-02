import Foundation
import Capacitor

@objc public class ReadDescriptorOptions: NSObject {
    private var characteristicId: String
    private var descriptorId: String
    private var deviceId: String
    private var serviceId: String

    init(characteristicId: String, descriptorId: String, deviceId: String, serviceId: String) {
        self.characteristicId = characteristicId
        self.descriptorId = descriptorId
        self.deviceId = deviceId
        self.serviceId = serviceId
    }

    func getCharacteristicId() -> String {
        return characteristicId
    }

    func getDescriptorId() -> String {
        return descriptorId
    }

    func getDeviceId() -> String {
        return deviceId
    }

    func getServiceId() -> String {
        return serviceId
    }
}
