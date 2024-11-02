import Foundation
import Capacitor
import CoreBluetooth

@objc public class BluetoothLowEnergy: NSObject {
    private let plugin: BluetoothLowEnergyPlugin

    private var cbCentralManager: CBCentralManager?
    private var connectedPeripherals: [String: CBPeripheral] = [:]
    private var discoveredCharacteristicsNTimes: [String: Int] = [:]
    private var discoveredDescriptorsNTimes: [String: Int] = [:]
    private var discoveredPeripherals: [String: CBPeripheral] = [:]
    private var totalNumberOfCharacteristics: [String: Int] = [:]
    private var totalNumberOfServices: [String: Int] = [:]
    private var savedConnectCallbacks: [String: ((Error?) -> Void)] = [:]
    private var savedDisconnectCallbacks: [String: ((Error?) -> Void)] = [:]
    private var savedDiscoverServicesCallbacks: [String: ((Error?) -> Void)] = [:]
    private var savedInitializeCallback: ((Error?) -> Void)?
    private var savedReadCharacteristicCallbacks: [String: ((ReadCharacteristicResult?, Error?) -> Void)] = [:]
    private var savedReadDescriptorCallbacks: [String: ((ReadDescriptorResult?, Error?) -> Void)] = [:]
    private var savedReadRssiCallbacks: [String: ((ReadRssiResult?, Error?) -> Void)] = [:]
    private var savedWriteCharacteristicCallbacks: [String: ((Error?) -> Void)] = [:]
    private var savedWriteDescriptorCallbacks: [String: ((Error?) -> Void)] = [:]

    init(plugin: BluetoothLowEnergyPlugin) {
        self.plugin = plugin
        super.init()
    }

    @objc public func connect(_ options: ConnectOptions, completion: @escaping (_ error: Error?) -> Void) {
        let deviceId = options.getDeviceId()

        if let cbCentralManager = cbCentralManager {
            if let peripheral = retrievePeripheral(cbCentralManager, deviceId: deviceId) {
                savedConnectCallbacks[deviceId] = completion
                cbCentralManager.connect(peripheral)
            } else if let peripheral = discoveredPeripherals[deviceId] {
                savedConnectCallbacks[deviceId] = completion
                cbCentralManager.connect(peripheral)
            } else {
                completion(CustomError.deviceNotDiscovered)
            }
        } else {
            completion(CustomError.notInitialized)
        }
    }

    @objc public func disconnect(_ options: DisconnectOptions, completion: @escaping (_ error: Error?) -> Void) {
        let deviceId = options.getDeviceId()

        if let cbCentralManager = cbCentralManager {
            if let peripheral = connectedPeripherals[deviceId] {
                savedDisconnectCallbacks[deviceId] = completion
                cbCentralManager.cancelPeripheralConnection(peripheral)
            } else {
                completion(CustomError.deviceNotConnected)
            }
        } else {
            completion(CustomError.notInitialized)
        }
    }

    @objc public func discoverServices(_ options: DiscoverServicesOptions, completion: @escaping (_ error: Error?) -> Void) {
        let deviceId = options.getDeviceId()

        if let _ = cbCentralManager {
            if let peripheral = connectedPeripherals[deviceId] {
                savedDiscoverServicesCallbacks[deviceId] = completion
                peripheral.discoverServices(nil)
            } else {
                completion(CustomError.deviceNotConnected)
            }
        } else {
            completion(CustomError.notInitialized)
        }
    }

    @objc public func getConnectedDevices(completion: @escaping (_ result: GetConnectedDevicesResult?, _ error: Error?) -> Void) {
        if let _ = cbCentralManager {
            // let peripherals = cbCentralManager.retrieveConnectedPeripherals(withServices: serviceIds)
            let peripherals = connectedPeripherals.values.map { $0 }
            let result = GetConnectedDevicesResult(peripherals: peripherals)
            completion(result, nil)
        } else {
            completion(nil, CustomError.notInitialized)
        }
    }

    @objc public func getServices(_ options: GetServicesOptions, completion: @escaping (_ result: GetServicesResult?, _ error: Error?) -> Void) {
        let deviceId = options.getDeviceId()

        if let _ = cbCentralManager {
            if let peripheral = connectedPeripherals[deviceId] {
                let result = GetServicesResult(services: (peripheral.services ?? []))
                completion(result, nil)
            } else {
                completion(nil, CustomError.deviceNotConnected)
            }
        } else {
            completion(nil, CustomError.notInitialized)
        }
    }

    @objc public func initialize(completion: @escaping (_ error: Error?) -> Void) {
        cbCentralManager = CBCentralManager(delegate: self, queue: nil)
        savedInitializeCallback = completion
    }

    @objc public func isEnabled(completion: @escaping (_ result: IsEnabledResult?, _ error: Error?) -> Void) {
        var enabled = false
        if let cbCentralManager = cbCentralManager {
            enabled = cbCentralManager.state == .poweredOn
        }
        let result = IsEnabledResult(enabled: enabled)
        completion(result, nil)
    }

    @objc public func openAppSettings(completion: @escaping (_ error: Error?) -> Void) {
        guard let url = URL(string: UIApplication.openSettingsURLString) else {
            completion(CustomError.operationFailed)
            return
        }
        DispatchQueue.main.async {
            if UIApplication.shared.canOpenURL(url) {
                UIApplication.shared.open(url, options: [:], completionHandler: { (success) in
                    if success == true {
                        completion(nil)
                    } else {
                        completion(CustomError.operationFailed)
                    }
                })
            } else {
                completion(CustomError.operationFailed)
            }
        }
    }

    @objc public func readCharacteristic(_ options: ReadCharacteristicOptions, completion: @escaping (_ result: ReadCharacteristicResult?, _ error: Error?) -> Void) {
        let characteristicId = options.getCharacteristicId()
        let deviceId = options.getDeviceId()
        let serviceId = options.getServiceId()

        if let _ = cbCentralManager {
            if let peripheral = connectedPeripherals[deviceId] {
                let serviceUUID = CBUUID(string: serviceId)
                let characteristicUUID = CBUUID(string: characteristicId)
                let service = peripheral.services?.first { $0.uuid == serviceUUID }
                let characteristic = service?.characteristics?.first { $0.uuid == characteristicUUID }
                if let characteristic = characteristic {
                    let key = createCallbackKey(peripheral, characteristic)
                    savedReadCharacteristicCallbacks[key] = completion
                    peripheral.readValue(for: characteristic)
                } else {
                    completion(nil, CustomError.characteristicNotFound)
                }
            } else {
                completion(nil, CustomError.deviceNotConnected)
            }
        } else {
            completion(nil, CustomError.notInitialized)
        }
    }

    @objc public func readDescriptor(_ options: ReadDescriptorOptions, completion: @escaping (_ result: ReadDescriptorResult?, _ error: Error?) -> Void) {
        let descriptorId = options.getDescriptorId()
        let deviceId = options.getDeviceId()
        let characteristicId = options.getCharacteristicId()
        let serviceId = options.getServiceId()

        if let _ = cbCentralManager {
            if let peripheral = connectedPeripherals[deviceId] {
                let serviceUUID = CBUUID(string: serviceId)
                let characteristicUUID = CBUUID(string: characteristicId)
                let descriptorUUID = CBUUID(string: descriptorId)
                let service = peripheral.services?.first { $0.uuid == serviceUUID }
                let characteristic = service?.characteristics?.first { $0.uuid == characteristicUUID }
                let descriptor = characteristic?.descriptors?.first { $0.uuid == descriptorUUID }
                if let descriptor = descriptor {
                    let key = createCallbackKey(peripheral, descriptor)
                    savedReadDescriptorCallbacks[key] = completion
                    peripheral.readValue(for: descriptor)
                } else {
                    completion(nil, CustomError.descriptorNotFound)
                }
            } else {
                completion(nil, CustomError.deviceNotConnected)
            }
        } else {
            completion(nil, CustomError.notInitialized)
        }
    }

    @objc public func readRssi(_ options: ReadRssiOptions, completion: @escaping (_ result: ReadRssiResult?, _ error: Error?) -> Void) {
        let deviceId = options.getDeviceId()

        if let _ = cbCentralManager {
            if let peripheral = connectedPeripherals[deviceId] {
                savedReadRssiCallbacks[deviceId] = completion
                peripheral.readRSSI()
            } else {
                completion(nil, CustomError.deviceNotConnected)
            }
        } else {
            completion(nil, CustomError.notInitialized)
        }
    }

    @objc public func startCharacteristicNotifications(_ options: StartCharacteristicNotificationsOptions, completion: @escaping (_ error: Error?) -> Void) {
        let characteristicId = options.getCharacteristicId()
        let deviceId = options.getDeviceId()
        let serviceId = options.getServiceId()

        if let _ = cbCentralManager {
            if let peripheral = connectedPeripherals[deviceId] {
                let serviceUUID = CBUUID(string: serviceId)
                let characteristicUUID = CBUUID(string: characteristicId)
                let service = peripheral.services?.first { $0.uuid == serviceUUID }
                let characteristic = service?.characteristics?.first { $0.uuid == characteristicUUID }
                if let characteristic = characteristic {
                    peripheral.setNotifyValue(true, for: characteristic)
                    completion(nil)
                } else {
                    completion(CustomError.characteristicNotFound)
                }
            } else {
                completion(CustomError.deviceNotConnected)
            }
        } else {
            completion(CustomError.notInitialized)
        }
    }

    @objc public func startScan(_ options: StartScanOptions, completion: @escaping (_ error: Error?) -> Void) {
        let serviceIds = options.getServiceIds()

        if let cbCentralManager = cbCentralManager {
            discoveredPeripherals = [:]
            cbCentralManager.scanForPeripherals(withServices: serviceIds, options: nil)
            completion(nil)
        } else {
            completion(CustomError.notInitialized)
        }
    }

    @objc public func stopCharacteristicNotifications(_ options: StopCharacteristicNotificationsOptions, completion: @escaping (_ error: Error?) -> Void) {
        let characteristicId = options.getCharacteristicId()
        let deviceId = options.getDeviceId()
        let serviceId = options.getServiceId()

        if let _ = cbCentralManager {
            if let peripheral = connectedPeripherals[deviceId] {
                let serviceUUID = CBUUID(string: serviceId)
                let characteristicUUID = CBUUID(string: characteristicId)
                let service = peripheral.services?.first { $0.uuid == serviceUUID }
                let characteristic = service?.characteristics?.first { $0.uuid == characteristicUUID }
                if let characteristic = characteristic {
                    peripheral.setNotifyValue(false, for: characteristic)
                    completion(nil)
                } else {
                    completion(CustomError.characteristicNotFound)
                }
            } else {
                completion(CustomError.deviceNotConnected)
            }
        } else {
            completion(CustomError.notInitialized)
        }
    }

    @objc public func stopScan(completion: @escaping (_ error: Error?) -> Void) {
        if let cbCentralManager = cbCentralManager {
            cbCentralManager.stopScan()
            completion(nil)
        } else {
            completion(CustomError.notInitialized)
        }
    }

    @objc public func writeCharacteristic(_ options: WriteCharacteristicOptions, completion: @escaping (_ error: Error?) -> Void) {
        let characteristicId = options.getCharacteristicId()
        let deviceId = options.getDeviceId()
        let serviceId = options.getServiceId()
        let type = options.getType()
        let value = options.getValue()

        if let _ = cbCentralManager {
            if let peripheral = connectedPeripherals[deviceId] {
                let serviceUUID = CBUUID(string: serviceId)
                let characteristicUUID = CBUUID(string: characteristicId)
                let service = peripheral.services?.first { $0.uuid == serviceUUID }
                let characteristic = service?.characteristics?.first { $0.uuid == characteristicUUID }
                if let characteristic = characteristic {
                    let key = createCallbackKey(peripheral, characteristic)
                    savedWriteCharacteristicCallbacks[key] = completion
                    peripheral.writeValue(value, for: characteristic, type: type)
                } else {
                    completion(CustomError.characteristicNotFound)
                }
            } else {
                completion(CustomError.deviceNotConnected)
            }
        } else {
            completion(CustomError.notInitialized)
        }
    }

    @objc public func writeDescriptor(_ options: WriteDescriptorOptions, completion: @escaping (_ error: Error?) -> Void) {
        let descriptorId = options.getDescriptorId()
        let deviceId = options.getDeviceId()
        let characteristicId = options.getCharacteristicId()
        let serviceId = options.getServiceId()
        let value = options.getValue()

        if let _ = cbCentralManager {
            if let peripheral = connectedPeripherals[deviceId] {
                let serviceUUID = CBUUID(string: serviceId)
                let characteristicUUID = CBUUID(string: characteristicId)
                let descriptorUUID = CBUUID(string: descriptorId)
                let service = peripheral.services?.first { $0.uuid == serviceUUID }
                let characteristic = service?.characteristics?.first { $0.uuid == characteristicUUID }
                let descriptor = characteristic?.descriptors?.first { $0.uuid == descriptorUUID }
                if let descriptor = descriptor {
                    let key = createCallbackKey(peripheral, descriptor)
                    savedWriteDescriptorCallbacks[key] = completion
                    peripheral.writeValue(value, for: descriptor)
                } else {
                    completion(CustomError.descriptorNotFound)
                }
            } else {
                completion(CustomError.deviceNotConnected)
            }
        } else {
            completion(CustomError.notInitialized)
        }
    }

    // public func requestPermissions(completion: @escaping (_ granted: Bool, _ error: Error?) -> Void) {
    //     self.cbCentralManager = CBCentralManager(delegate: self, queue: nil)
    // }

    // public func checkPermissions() -> String {
    //     // Check bluetooth permissions
    //     let bluetoothStatus = CBCentralManager.authorization
    //     let permission: String
    //     switch bluetoothStatus {
    //     case .allowedAlways:
    //         permission = "granted"
    //     case .restricted, .denied:
    //         permission = "denied"
    //     case .notDetermined:
    //         permission = "prompt"
    //     @unknown default:
    //         permission = "prompt"
    //     }
    //     return permission
    // }

    private func createCallbackKey(_ peripheral: CBPeripheral, _ characteristic: CBCharacteristic) -> String {
        return peripheral.identifier.uuidString + "-" + characteristic.uuid.uuidString
    }

    private func createCallbackKey(_ peripheral: CBPeripheral, _ descriptor: CBDescriptor) -> String {
        return peripheral.identifier.uuidString + "-" + (descriptor.characteristic?.uuid.uuidString ?? "") + "-" + descriptor.uuid.uuidString
    }

    private func notifyCharacteristicChangedListener(characteristic: CBCharacteristic, peripheral: CBPeripheral) {
        let event = CharacteristicChangedEvent(characteristic: characteristic, peripheral: peripheral)
        plugin.notifyCharacteristicChangedListener(event: event)
    }

    private func notifyDeviceDisconnectedListener(peripheral: CBPeripheral) {
        let event = DeviceDisconnectedEvent(peripheral: peripheral)
        plugin.notifyDeviceDisconnectedListener(event: event)
    }

    private func notifyDeviceScannedListener(peripheral: CBPeripheral, rssi: NSNumber) {
        let event = DeviceScannedEvent(peripheral: peripheral, rssi: rssi)
        plugin.notifyDeviceScannedListener(event: event)
    }

    private func retrievePeripheral(_ cbCentralManager: CBCentralManager, deviceId: String) -> CBPeripheral? {
        let identifiers = [UUID(uuidString: deviceId)!]
        let peripherals = cbCentralManager.retrievePeripherals(withIdentifiers: identifiers)
        return peripherals.first
    }
}

extension BluetoothLowEnergy: CBCentralManagerDelegate {
    public func centralManagerDidUpdateState(_ central: CBCentralManager) {
        switch central.state {
        case .poweredOn:
            savedInitializeCallback?(nil)
        case .poweredOff:
            savedInitializeCallback?(CustomError.bluetoothOff)
        case .resetting:
            break
        case .unauthorized:
            savedInitializeCallback?(CustomError.bluetoothUnauthorized)
        case .unknown:
            break
        case .unsupported:
            savedInitializeCallback?(CustomError.bluetoothUnsupported)
        @unknown default:
            CAPLog.print("Bluetooth is unknown")
        }
    }

    public func centralManager(_ central: CBCentralManager, didDiscover peripheral: CBPeripheral, advertisementData: [String: Any], rssi RSSI: NSNumber) {
        let deviceId = peripheral.identifier.uuidString
        if discoveredPeripherals[deviceId] != nil {
            return
        }
        discoveredPeripherals[deviceId] = peripheral
        notifyDeviceScannedListener(peripheral: peripheral, rssi: RSSI)
    }

    public func centralManager(_ central: CBCentralManager, didConnect peripheral: CBPeripheral) {
        connectedPeripherals[peripheral.identifier.uuidString] = peripheral
        peripheral.delegate = self
        savedConnectCallbacks[peripheral.identifier.uuidString]?(nil)
    }

    public func centralManager(_ central: CBCentralManager, didFailToConnect peripheral: CBPeripheral, error: Error?) {
        savedConnectCallbacks[peripheral.identifier.uuidString]?(error)
    }

    public func centralManager(_ central: CBCentralManager, didDisconnectPeripheral peripheral: CBPeripheral, error: Error?) {
        connectedPeripherals.removeValue(forKey: peripheral.identifier.uuidString)
        savedDisconnectCallbacks[peripheral.identifier.uuidString]?(error)
        notifyDeviceDisconnectedListener(peripheral: peripheral)
    }

    public func centralManager(_ central: CBCentralManager, didUpdateValueFor characteristic: CBCharacteristic, error: Error?) {
    }

    public func centralManager(_ central: CBCentralManager, didDiscoverServices peripheral: CBPeripheral, error: Error?) {
    }

    public func centralManager(_ central: CBCentralManager, didDiscoverCharacteristicsFor service: CBService, error: Error?) {
    }
}

extension BluetoothLowEnergy: CBPeripheralDelegate {
    public func peripheral(_ peripheral: CBPeripheral, didDiscoverServices error: Error?) {
        if let error = error {
            savedDiscoverServicesCallbacks[peripheral.identifier.uuidString]?(error)
        } else {
            if let services = peripheral.services {
                discoveredCharacteristicsNTimes[peripheral.identifier.uuidString] = 0
                totalNumberOfServices[peripheral.identifier.uuidString] = services.count
                for service in services {
                    peripheral.discoverCharacteristics(nil, for: service)
                }
            }
        }
    }

    public func peripheral(_ peripheral: CBPeripheral, didDiscoverCharacteristicsFor service: CBService, error: Error?) {
        if let error = error {
            savedDiscoverServicesCallbacks[peripheral.identifier.uuidString]?(error)
        } else {
            if let characteristics = service.characteristics {
                discoveredCharacteristicsNTimes[peripheral.identifier.uuidString] = (discoveredCharacteristicsNTimes[peripheral.identifier.uuidString] ?? 0) + 1
                totalNumberOfCharacteristics[peripheral.identifier.uuidString] = (totalNumberOfCharacteristics[peripheral.identifier.uuidString] ?? 0) + characteristics.count
                for characteristic in characteristics {
                    peripheral.discoverDescriptors(for: characteristic)
                }
            }
        }
    }

    public func peripheral(_ peripheral: CBPeripheral, didDiscoverDescriptorsFor characteristic: CBCharacteristic, error: Error?) {
        if let error = error {
            savedDiscoverServicesCallbacks[peripheral.identifier.uuidString]?(error)
        } else {
            discoveredDescriptorsNTimes[peripheral.identifier.uuidString] = (discoveredDescriptorsNTimes[peripheral.identifier.uuidString] ?? 0) + 1
            if let discoveredCharacteristicsNTimes = discoveredCharacteristicsNTimes[peripheral.identifier.uuidString],
               let totalNumberOfCharacteristics = totalNumberOfCharacteristics[peripheral.identifier.uuidString],
               let discoveredDescriptorsNTimes = discoveredDescriptorsNTimes[peripheral.identifier.uuidString],
               let totalNumberOfServices = totalNumberOfServices[peripheral.identifier.uuidString] {
                if discoveredCharacteristicsNTimes == totalNumberOfServices && discoveredDescriptorsNTimes == totalNumberOfCharacteristics {
                    savedDiscoverServicesCallbacks[peripheral.identifier.uuidString]?(nil)
                }
            }
        }
    }

    public func peripheral(_ peripheral: CBPeripheral, didReadRSSI RSSI: NSNumber, error: Error?) {
        if let completion = savedReadRssiCallbacks[peripheral.identifier.uuidString] {
            if let error = error {
                completion(nil, error)
            } else {
                let result = ReadRssiResult(rssi: RSSI)
                completion(result, nil)
            }
        }
    }

    public func peripheral(_ peripheral: CBPeripheral, didUpdateValueFor characteristic: CBCharacteristic, error: Error?) {
        let key = createCallbackKey(peripheral, characteristic)
        if let completion = savedReadCharacteristicCallbacks[key] {
            if let error = error {
                completion(nil, error)
            } else {
                let result = ReadCharacteristicResult(characteristic: characteristic)
                completion(result, nil)
            }
        } else {
            notifyCharacteristicChangedListener(characteristic: characteristic, peripheral: peripheral)
        }
    }

    public func peripheral(_ peripheral: CBPeripheral, didUpdateValueFor descriptor: CBDescriptor, error: Error?) {
        let key = createCallbackKey(peripheral, descriptor)
        if let completion = savedReadDescriptorCallbacks[key] {
            if let error = error {
                completion(nil, error)
            } else {
                let result = ReadDescriptorResult(descriptor: descriptor)
                completion(result, nil)
            }
        }
    }

    public func peripheral(_ peripheral: CBPeripheral, didWriteValueFor characteristic: CBCharacteristic, error: Error?) {
        let key = createCallbackKey(peripheral, characteristic)
        if let completion = savedWriteCharacteristicCallbacks[key] {
            completion(error)
        }
    }

    public func peripheral(_ peripheral: CBPeripheral, didWriteValueFor descriptor: CBDescriptor, error: Error?) {
        let key = createCallbackKey(peripheral, descriptor)
        if let completion = savedWriteDescriptorCallbacks[key] {
            completion(error)
        }
    }
}
