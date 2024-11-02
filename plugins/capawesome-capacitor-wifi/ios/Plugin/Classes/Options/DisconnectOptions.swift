import Foundation
import Capacitor

@objc public class DisconnectOptions: NSObject {
    private var ssid: String?

    init(ssid: String?) {
        self.ssid = ssid
    }

    func getSsid() -> String? {
        return ssid
    }
}
