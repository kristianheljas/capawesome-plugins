import Foundation
import CoreBluetooth
import Capacitor

@objc public class GetServicesResult: NSObject, Result {
    let services: [CBService]

    init(services: [CBService]) {
        self.services = services
    }

    public func toJSObject() -> AnyObject {
        var servicesResult = JSArray()
        for service in services {
            var characteristicsResult = JSArray()
            for characteristic in (service.characteristics ?? []) {
                var descriptorsResult = JSArray()
                for descriptor in (characteristic.descriptors ?? []) {
                    var descriptorResult = JSObject()
                    descriptorResult["id"] = BluetoothLowEnergyHelper.convertCBUUIDToString(uuid: descriptor.uuid)
                    descriptorsResult.append(descriptorResult)
                }

                var characteristicResult = JSObject()
                characteristicResult["id"] = BluetoothLowEnergyHelper.convertCBUUIDToString(uuid: characteristic.uuid)
                characteristicResult["descriptors"] = descriptorsResult
                characteristicResult["properties"] = createCharacteristicPropertiesResult(characteristic: characteristic)
                characteristicsResult.append(characteristicResult)
            }

            var serviceResult = JSObject()
            serviceResult["id"] = BluetoothLowEnergyHelper.convertCBUUIDToString(uuid: service.uuid)
            serviceResult["characteristics"] = characteristicsResult
            servicesResult.append(serviceResult)
        }

        var result = JSObject()
        result["services"] = servicesResult
        return result as AnyObject
    }

    private func createCharacteristicPropertiesResult(characteristic: CBCharacteristic) -> JSObject {
        var propertiesResult = JSObject()
        propertiesResult["broadcast"] = characteristic.properties.contains(.broadcast)
        propertiesResult["read"] = characteristic.properties.contains(.read)
        propertiesResult["writeWithoutResponse"] = characteristic.properties.contains(.writeWithoutResponse)
        propertiesResult["write"] = characteristic.properties.contains(.write)
        propertiesResult["notify"] = characteristic.properties.contains(.notify)
        propertiesResult["indicate"] = characteristic.properties.contains(.indicate)
        propertiesResult["authenticatedSignedWrites"] = characteristic.properties.contains(.authenticatedSignedWrites)
        propertiesResult["extendedProperties"] = characteristic.properties.contains(.extendedProperties)
        propertiesResult["notifyEncryptionRequired"] = characteristic.properties.contains(.notifyEncryptionRequired)
        propertiesResult["indicateEncryptionRequired"] = characteristic.properties.contains(.indicateEncryptionRequired)
        return propertiesResult
    }
}
