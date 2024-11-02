import Foundation
import Capacitor

@objc public class WriteDescriptorOptions: NSObject {
    private var characteristicId: String
    private var descriptorId: String
    private var deviceId: String
    private var serviceId: String
    private var value: Data

    init(characteristicId: String, descriptorId: String, deviceId: String, serviceId: String, value: [UInt8]) {
        self.characteristicId = characteristicId
        self.descriptorId = descriptorId
        self.deviceId = deviceId
        self.serviceId = serviceId
        self.value = BluetoothLowEnergyHelper.convertJsonArrayToData(values: value)
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

    func getValue() -> Data {
        return value
    }
}
