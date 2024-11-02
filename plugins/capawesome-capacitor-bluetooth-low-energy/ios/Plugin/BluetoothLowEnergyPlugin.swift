import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(BluetoothLowEnergyPlugin)
public class BluetoothLowEnergyPlugin: CAPPlugin {
    public let eventCharacteristicChanged = "characteristicChanged"
    public let eventDeviceDisconnected = "deviceDisconnected"
    public let eventDeviceScanned = "deviceScanned"
    public let tag = "BluetoothLowEnergy"

    private var implementation: BluetoothLowEnergy?

    override public func load() {
        implementation = BluetoothLowEnergy(plugin: self)
    }

    @objc func connect(_ call: CAPPluginCall) {
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }

        let options = ConnectOptions(deviceId: deviceId)

        implementation?.connect(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func createBond(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc func disconnect(_ call: CAPPluginCall) {
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }

        let options = DisconnectOptions(deviceId: deviceId)

        implementation?.disconnect(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func discoverServices(_ call: CAPPluginCall) {
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }

        let options = DiscoverServicesOptions(deviceId: deviceId)

        implementation?.discoverServices(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func getConnectedDevices(_ call: CAPPluginCall) {
        implementation?.getConnectedDevices(completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func getServices(_ call: CAPPluginCall) {
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }

        let options = GetServicesOptions(deviceId: deviceId)

        implementation?.getServices(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func initialize(_ call: CAPPluginCall) {
        implementation?.initialize(completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func isBonded(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc func isEnabled(_ call: CAPPluginCall) {
        implementation?.isEnabled(completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func openAppSettings(_ call: CAPPluginCall) {
        implementation?.openAppSettings(completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func openBluetoothSettings(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc func openLocationSettings(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc func readCharacteristic(_ call: CAPPluginCall) {
        guard let characteristicId = call.getString("characteristicId") else {
            call.reject(CustomError.characteristicIdMissing.localizedDescription)
            return
        }
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }
        guard let serviceId = call.getString("serviceId") else {
            call.reject(CustomError.serviceIdMissing.localizedDescription)
            return
        }

        let options = ReadCharacteristicOptions(characteristicId: characteristicId, deviceId: deviceId, serviceId: serviceId)

        implementation?.readCharacteristic(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func readDescriptor(_ call: CAPPluginCall) {
        guard let characteristicId = call.getString("characteristicId") else {
            call.reject(CustomError.characteristicIdMissing.localizedDescription)
            return
        }
        guard let descriptorId = call.getString("descriptorId") else {
            call.reject(CustomError.descriptorIdMissing.localizedDescription)
            return
        }
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }
        guard let serviceId = call.getString("serviceId") else {
            call.reject(CustomError.serviceIdMissing.localizedDescription)
            return
        }

        let options = ReadDescriptorOptions(characteristicId: characteristicId, descriptorId: descriptorId, deviceId: deviceId, serviceId: serviceId)

        implementation?.readDescriptor(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func readRssi(_ call: CAPPluginCall) {
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }

        let options = ReadRssiOptions(deviceId: deviceId)

        implementation?.readRssi(options, completion: { result, error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            if let result = result?.toJSObject() as? JSObject {
                call.resolve(result)
            }
        })
    }

    @objc func requestConnectionPriority(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc func requestMtu(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc func startCharacteristicNotifications(_ call: CAPPluginCall) {
        guard let characteristicId = call.getString("characteristicId") else {
            call.reject(CustomError.characteristicIdMissing.localizedDescription)
            return
        }
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }
        guard let serviceId = call.getString("serviceId") else {
            call.reject(CustomError.serviceIdMissing.localizedDescription)
            return
        }

        let options = StartCharacteristicNotificationsOptions(characteristicId: characteristicId, deviceId: deviceId, serviceId: serviceId)

        implementation?.startCharacteristicNotifications(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func startForegroundService(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc func startScan(_ call: CAPPluginCall) {
        let serviceIds = call.getArray("serviceIds") as? [String]

        let options = StartScanOptions(serviceIds: serviceIds)

        implementation?.startScan(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func stopCharacteristicNotifications(_ call: CAPPluginCall) {
        guard let characteristicId = call.getString("characteristicId") else {
            call.reject(CustomError.characteristicIdMissing.localizedDescription)
            return
        }
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }
        guard let serviceId = call.getString("serviceId") else {
            call.reject(CustomError.serviceIdMissing.localizedDescription)
            return
        }

        let options = StopCharacteristicNotificationsOptions(characteristicId: characteristicId, deviceId: deviceId, serviceId: serviceId)

        implementation?.stopCharacteristicNotifications(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func stopForegroundService(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
    }

    @objc func stopScan(_ call: CAPPluginCall) {
        implementation?.stopScan(completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func writeCharacteristic(_ call: CAPPluginCall) {
        guard let characteristicId = call.getString("characteristicId") else {
            call.reject(CustomError.characteristicIdMissing.localizedDescription)
            return
        }
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }
        guard let serviceId = call.getString("serviceId") else {
            call.reject(CustomError.serviceIdMissing.localizedDescription)
            return
        }
        let type = call.getString("type")
        guard let value = call.getArray("value") as? [UInt8] else {
            call.reject(CustomError.valueMissing.localizedDescription)
            return
        }

        let options = WriteCharacteristicOptions(characteristicId: characteristicId, deviceId: deviceId, serviceId: serviceId, type: type, value: value)

        implementation?.writeCharacteristic(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func writeDescriptor(_ call: CAPPluginCall) {
        guard let characteristicId = call.getString("characteristicId") else {
            call.reject(CustomError.characteristicIdMissing.localizedDescription)
            return
        }
        guard let descriptorId = call.getString("descriptorId") else {
            call.reject(CustomError.descriptorIdMissing.localizedDescription)
            return
        }
        guard let deviceId = call.getString("deviceId") else {
            call.reject(CustomError.deviceIdMissing.localizedDescription)
            return
        }
        guard let serviceId = call.getString("serviceId") else {
            call.reject(CustomError.serviceIdMissing.localizedDescription)
            return
        }
        guard let value = call.getArray("value") as? [UInt8] else {
            call.reject(CustomError.valueMissing.localizedDescription)
            return
        }

        let options = WriteDescriptorOptions(characteristicId: characteristicId, descriptorId: descriptorId, deviceId: deviceId, serviceId: serviceId, value: value)

        implementation?.writeDescriptor(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
        // implementation?.checkPermissions(completion: { permission in
        //     call.resolve([
        //         "bluetooth": permission
        //     ])
        // })
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        call.unimplemented("Not implemented on iOS.")
        // implementation?.requestPermissions(completion: { granted, error in
        //     if let error = error {
        //         CAPLog.print("[", self.tag, "] ", error)
        //         call.reject(error.localizedDescription)
        //         return
        //     }
        //     call.resolve(["receive": granted ? "granted" : "denied"])
        // })
    }

    func notifyCharacteristicChangedListener(event: CharacteristicChangedEvent) {
        if let event = event.toJSObject() as? JSObject {
            notifyListeners(eventCharacteristicChanged, data: event)
        }
    }

    func notifyDeviceDisconnectedListener(event: DeviceDisconnectedEvent) {
        if let event = event.toJSObject() as? JSObject {
            notifyListeners(eventDeviceDisconnected, data: event)
        }
    }

    func notifyDeviceScannedListener(event: DeviceScannedEvent) {
        if let event = event.toJSObject() as? JSObject {
            notifyListeners(eventDeviceScanned, data: event)
        }
    }
}
