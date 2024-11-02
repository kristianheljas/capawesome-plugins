import Foundation
import CoreBluetooth
import Capacitor

@objc public class StartScanOptions: NSObject {
    private var serviceIds: [CBUUID]?

    init(serviceIds: [String]?) {
        if let serviceIds = serviceIds {
            self.serviceIds = serviceIds.map { CBUUID(string: $0) }
        } else {
            self.serviceIds = nil
        }
    }

    func getServiceIds() -> [CBUUID]? {
        return serviceIds
    }
}
