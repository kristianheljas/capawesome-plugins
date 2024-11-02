import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(WifiPlugin)
public class WifiPlugin: CAPPlugin {
    public static let tag = "Wifi"
    public static let permissionLocation = "location"

    private var implementation: Wifi?

    override public func load() {
        self.implementation = Wifi(plugin: self)
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        var locationResult: String
        switch implementation?.checkLocationPermission() {
        case .notDetermined:
            locationResult = "prompt"
        case .none, .restricted, .denied:
            locationResult = "denied"
        case .authorizedAlways, .authorizedWhenInUse:
            locationResult = "granted"
        @unknown default:
            locationResult = "prompt"
        }

        var result = JSObject()
        result[WifiPlugin.permissionLocation] = locationResult
        call.resolve(result)
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        var permissions = call.getArray("permissions", String.self) ?? []
        if permissions.isEmpty {
            permissions = [WifiPlugin.permissionLocation]
        }

        let group = DispatchGroup()
        if permissions.contains(WifiPlugin.permissionLocation) {
            if implementation?.checkLocationPermission() == .notDetermined {
                group.enter()
                implementation?.requestLocationPermission { _ in
                    group.leave()
                }
            }
        }

        group.notify(queue: DispatchQueue.main) {
            self.checkPermissions(call)
        }
    }

    @objc public func connect(_ call: CAPPluginCall) {
        guard let ssid = call.getString("ssid") else {
            call.reject(CustomError.ssidMissing.localizedDescription)
            return
        }
        let password = call.getString("password")

        let options = ConnectOptions(ssid: ssid, password: password)

        implementation?.connect(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    @objc public func disconnect(_ call: CAPPluginCall) {
        let ssid = call.getString("ssid")

        let options = DisconnectOptions(ssid: ssid)

        implementation?.disconnect(options, completion: { error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, nil)
            }
        })
    }

    @objc public func getAvailableNetworks(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc public func getIpAddress(_ call: CAPPluginCall) {
        implementation?.getIpAddress(completion: { result, error in
            if let error = error {
                self.rejectCall(call, error)
            } else {
                self.resolveCall(call, result)
            }
        })
    }

    @objc public func getRssi(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc public func getScanResult(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc public func getSsid(_ call: CAPPluginCall) {
        switch implementation?.checkLocationPermission() {
        case .authorizedAlways, .authorizedWhenInUse:
            implementation?.getSsid(completion: { result, error in
                if let error = error {
                    self.rejectCall(call, error)
                } else {
                    self.resolveCall(call, result)
                }
            })
        case .notDetermined:
            implementation?.requestLocationPermission { _ in
                self.getSsid(call)
            }
        case .none, .restricted, .denied:
            self.rejectCall(call, CustomError.locationPermissionDenied)
        @unknown default:
            self.rejectCall(call, CustomError.locationPermissionDenied)
        }
    }

    @objc public func isEnabled(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc public func startScan(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    private func rejectCall(_ call: CAPPluginCall, _ error: Error) {
        CAPLog.print("[", WifiPlugin.tag, "] ", error)
        call.reject(error.localizedDescription)
    }

    private func resolveCall(_ call: CAPPluginCall, _ result: Result?) {
        if let result = result?.toJSObject() as? JSObject {
            call.resolve(result)
        } else {
            call.resolve()
        }
    }
}
