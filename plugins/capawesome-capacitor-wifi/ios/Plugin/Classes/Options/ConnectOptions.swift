import Foundation
import Capacitor

@objc public class ConnectOptions: NSObject {
    private var ssid: String
    private var password: String?

    init(ssid: String, password: String?) {
        self.ssid = ssid
        self.password = password
    }

    func getSsid() -> String {
        return ssid
    }

    func getPassword() -> String? {
        return password
    }
}
