import Foundation
import Capacitor
import SystemConfiguration.CaptiveNetwork
import NetworkExtension
import CoreLocation

@objc public class Wifi: NSObject {
    private let locationManager = CLLocationManager()
    private var locationPermissionCallbacks: [(_ status: CLAuthorizationStatus) -> Void] = []
    private let plugin: WifiPlugin

    init(plugin: WifiPlugin) {
        self.plugin = plugin
        super.init()
        self.locationManager.delegate = self
    }

    @objc public func connect(_ options: ConnectOptions, completion: @escaping (_ error: Error?) -> Void) {
        let ssid = options.getSsid()
        let password = options.getPassword()

        var configuration: NEHotspotConfiguration
        if let password = password {
            configuration = NEHotspotConfiguration(ssid: ssid, passphrase: password, isWEP: false)
        } else {
            configuration = NEHotspotConfiguration(ssid: ssid)
        }
        NEHotspotConfigurationManager.shared.apply(configuration) { error in
            if error?.localizedDescription == "already associated." {
                completion(nil)
                return
            }
            completion(error)
        }
    }

    @objc public func disconnect(_ options: DisconnectOptions, completion: @escaping (_ error: Error?) -> Void) {
        let ssid = options.getSsid()

        if let ssid = ssid {
            disconnect(ssid: ssid)
            completion(nil)
        } else {
            getSsid { ssid in
                if let ssid = ssid {
                    self.disconnect(ssid: ssid)
                    completion(nil)
                } else {
                    completion(CustomError.ssidNotAvailable)
                }
            }
        }
    }

    @objc public func getIpAddress(completion: @escaping (_ result: GetIpAddressResult?, _ error: Error?) -> Void) {
        let ipAddress = getIpAddress()
        if let ipAddress = ipAddress {
            let result = GetIpAddressResult(ipAddress: ipAddress)
            completion(result, nil)
        } else {
            completion(nil, CustomError.ipAddressNotAvailable)
        }
    }

    @objc public func getSsid(completion: @escaping (_ result: GetSsidResult?, _ error: Error?) -> Void) {
        getSsid { ssid in
            if let ssid = ssid {
                let result = GetSsidResult(ssid: ssid)
                completion(result, nil)
            } else {
                completion(nil, CustomError.ssidNotAvailable)
            }
        }
    }

    @objc public func checkLocationPermission() -> CLAuthorizationStatus {
        return CLLocationManager.authorizationStatus()
    }

    @objc public func requestLocationPermission(completion: @escaping (_ status: CLAuthorizationStatus) -> Void) {
        locationPermissionCallbacks.append(completion)
        self.locationManager.requestAlwaysAuthorization()
    }

    private func disconnect(ssid: String) {
        NEHotspotConfigurationManager.shared.removeConfiguration(forSSID: ssid)
    }

    private func getIpAddress() -> String? {
        var address: String?
        var ifaddr: UnsafeMutablePointer<ifaddrs>?
        if getifaddrs(&ifaddr) == 0 {
            var ptr = ifaddr
            while ptr != nil {
                defer { ptr = ptr?.pointee.ifa_next }
                let interface = ptr?.pointee
                let addrFamily = interface?.ifa_addr.pointee.sa_family
                if addrFamily == UInt8(AF_INET) {
                    if let name = interface?.ifa_name, String(cString: name) == "en0" {
                        var hostname = [CChar](repeating: 0, count: Int(NI_MAXHOST))
                        getnameinfo(interface?.ifa_addr, socklen_t((interface?.ifa_addr.pointee.sa_len)!), &hostname, socklen_t(hostname.count), nil, socklen_t(0), NI_NUMERICHOST)
                        address = String(cString: hostname)
                    }
                }
            }
            freeifaddrs(ifaddr)
        }
        return address
    }

    private func getSsid(_ completion: @escaping (_ ssid: String?) -> Void) {
        if #available(iOS 14.0, *) {
            NEHotspotNetwork.fetchCurrent { network in
                completion(network?.ssid)
            }
        } else {
            if let interfaces = CNCopySupportedInterfaces() as? [String] {
                for interface in interfaces {
                    if let info = CNCopyCurrentNetworkInfo(interface as CFString) as NSDictionary? {
                        completion(info[kCNNetworkInfoKeySSID as String] as? String)
                        return
                    }
                }
            }
            completion(nil)
        }
    }
}

extension Wifi: CLLocationManagerDelegate {
    public func locationManagerDidChangeAuthorization(_ manager: CLLocationManager) {
        let status = CLLocationManager.authorizationStatus()
        for callback in locationPermissionCallbacks {
            callback(status)
        }
    }
}
