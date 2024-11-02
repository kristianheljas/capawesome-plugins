import Foundation
import CoreBluetooth

public class BluetoothLowEnergyHelper {
    public static func convertAnyToJsonArray(data: Any?) -> [UInt8] {
        if let str = data as? String {
            return [UInt8](Data(str.utf8))
        } else if let int = data as? Int {
            return [UInt8(int)]
        } else if let double = data as? Double {
            return [UInt8(double)]
        } else if let bool = data as? Bool {
            return [UInt8(bool ? 1 : 0)]
        } else if let array = data as? [Any] {
            return array.flatMap { convertAnyToJsonArray(data: $0) }
        } else {
            return []
        }
    }

    public static func convertCBUUIDToString(uuid: CBUUID) -> String {
        var uuidString = uuid.uuidString

        // If the original UUID String is already in 128-bit format, just return it
        if uuidString.count == 36 {
            return uuidString.lowercased()
        }

        // Convert 16-bit UUID to 128-bit
        uuidString = String(String(uuidString.reversed()).padding(toLength: 4, withPad: "0", startingAt: 0).reversed()).lowercased()
        return "0000\(uuidString)-0000-1000-8000-00805f9b34fb"
    }

    public static func convertDataToJsonArray(data: Data?) -> [UInt8] {
        if let data = data {
            return [UInt8](data)
        } else {
            return []
        }
    }

    public static func convertJsonArrayToData(values: [UInt8]) -> Data {
        return Data(values.map { UInt8($0) })
    }
}
