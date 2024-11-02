import Foundation

public enum CustomError: Error {
    case bluetoothOff
    case bluetoothUnauthorized
    case bluetoothUnsupported
    case characteristicIdMissing
    case characteristicNotFound
    case descriptorIdMissing
    case descriptorNotFound
    case deviceIdMissing
    case deviceNotConnected
    case deviceNotDiscovered
    case notInitialized
    case operationFailed
    case serviceIdMissing
    case valueMissing
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .bluetoothOff:
            return NSLocalizedString("Bluetooth is powered off.", comment: "bluetoothOff")
        case .bluetoothUnauthorized:
            return NSLocalizedString("Bluetooth is unauthorized.", comment: "bluetoothUnauthorized")
        case .bluetoothUnsupported:
            return NSLocalizedString("Bluetooth is unsupported.", comment: "bluetoothUnsupported")
        case .characteristicIdMissing:
            return NSLocalizedString("characteristicId must be provided.", comment: "characteristicIdMissing")
        case .characteristicNotFound:
            return NSLocalizedString("Characteristic not found.", comment: "characteristicNotFound")
        case .descriptorIdMissing:
            return NSLocalizedString("descriptorId must be provided.", comment: "descriptorIdMissing")
        case .descriptorNotFound:
            return NSLocalizedString("Descriptor not found.", comment: "descriptorNotFound")
        case .deviceIdMissing:
            return NSLocalizedString("deviceId must be provided.", comment: "deviceIdMissing")
        case .deviceNotConnected:
            return NSLocalizedString("Device is not connected.", comment: "deviceNotConnected")
        case .deviceNotDiscovered:
            return NSLocalizedString("Device was not discovered.", comment: "deviceNotDiscovered")
        case .notInitialized:
            return NSLocalizedString("Plugin is not initialized.", comment: "notInitialized")
        case .operationFailed:
            return NSLocalizedString("Operation failed.", comment: "operationFailed")
        case .serviceIdMissing:
            return NSLocalizedString("serviceId must be provided.", comment: "serviceIdMissing")
        case .valueMissing:
            return NSLocalizedString("value must be provided.", comment: "valueMissing")
        }
    }
}
