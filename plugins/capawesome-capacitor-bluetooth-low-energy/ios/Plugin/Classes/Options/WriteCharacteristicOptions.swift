import Foundation
import CoreBluetooth
import Capacitor

@objc public class WriteCharacteristicOptions: NSObject {
    private var characteristicId: String
    private var deviceId: String
    private var serviceId: String
    private var type: CBCharacteristicWriteType
    private var value: Data

    init(characteristicId: String, deviceId: String, serviceId: String, type: String?, value: [UInt8]) {
        self.characteristicId = characteristicId
        self.deviceId = deviceId
        self.serviceId = serviceId
        self.type = WriteCharacteristicOptions.getTypeFromString(type: type)
        self.value = BluetoothLowEnergyHelper.convertJsonArrayToData(values: value)
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

    func getType() -> CBCharacteristicWriteType {
        return type
    }

    func getValue() -> Data {
        return value
    }

    private static func getTypeFromString(type: String?) -> CBCharacteristicWriteType {
        switch type {
        case "withoutResponse":
            return CBCharacteristicWriteType.withoutResponse
        default:
            return CBCharacteristicWriteType.withResponse
        }
    }
}
