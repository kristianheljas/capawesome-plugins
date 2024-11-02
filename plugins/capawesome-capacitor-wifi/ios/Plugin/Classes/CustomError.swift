import Foundation

public enum CustomError: Error {
    case ipAddressNotAvailable
    case locationPermissionDenied
    case ssidMissing
    case ssidNotAvailable
}

extension CustomError: LocalizedError {
    public var errorDescription: String? {
        switch self {
        case .ipAddressNotAvailable:
            return NSLocalizedString("The IP address is not available.", comment: "ipAddressNotAvailable")
        case .locationPermissionDenied:
            return NSLocalizedString("Location permission denied.", comment: "locationPermissionDenied")
        case .ssidMissing:
            return NSLocalizedString("SSID must be provided.", comment: "ssidMissing")
        case .ssidNotAvailable:
            return NSLocalizedString("The SSID is not available.", comment: "ssidNotAvailable")
        }
    }
}
