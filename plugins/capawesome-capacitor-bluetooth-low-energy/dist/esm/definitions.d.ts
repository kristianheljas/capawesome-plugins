import type { PermissionState, PluginListenerHandle } from '@capacitor/core';
export declare type BluetoothLowEnergyPermissionType = 'bluetooth' | 'bluetoothConnect' | 'bluetoothScan' | 'location' | 'notifications';
export interface BluetoothLowEnergyPluginPermission {
    permissions: BluetoothLowEnergyPermissionType[];
}
export interface BluetoothLowEnergyPlugin {
    /**
     * Connect to a BLE device.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    connect(options: ConnectOptions): Promise<void>;
    /**
     * Create a bond with the BLE device.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    createBond(options: CreateBondOptions): Promise<void>;
    /**
     * Disconnect from the BLE device.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    disconnect(options: DisconnectOptions): Promise<void>;
    /**
     * Discover services provided by the device.
     *
     * On **iOS**, this operation may take up to 30 seconds.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    discoverServices(options: DiscoverServiceOptions): Promise<void>;
    /**
     * Get a list of connected devices.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    getConnectedDevices(): Promise<GetConnectedDevicesResult>;
    /**
     * Get a list of services provided by the device.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    getServices(options: GetServicesOptions): Promise<GetServicesResult>;
    /**
     * Initialize the plugin. This method must be called before any other method.
     *
     * On **iOS**, this will prompt the user for Bluetooth permissions.
     *
     * Only available on iOS.
     *
     * @since 6.0.0
     */
    initialize(): Promise<void>;
    /**
     * Check if the device is bonded.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    isBonded(options: IsBondedOptions): Promise<IsBondedResult>;
    /**
     * Check if Bluetooth is enabled.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    isEnabled(): Promise<IsEnabledResult>;
    /**
     * Open the Bluetooth settings on the device.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    openAppSettings(): Promise<void>;
    /**
     * Open the Bluetooth settings on the device.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    openBluetoothSettings(): Promise<void>;
    /**
     * Open the location settings on the device.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    openLocationSettings(): Promise<void>;
    /**
     * Read the value of a characteristic.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    readCharacteristic(options: ReadCharacteristicOptions): Promise<ReadCharacteristicResult>;
    /**
     * Read the value of a descriptor.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    readDescriptor(options: ReadDescriptorOptions): Promise<ReadDescriptorResult>;
    /**
     * Read the RSSI value of the device.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    readRssi(options: ReadRssiOptions): Promise<ReadRssiResult>;
    /**
     * Request a connection priority.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    requestConnectionPriority(options: RequestConnectionPriorityOptions): Promise<void>;
    /**
     * Request an MTU size.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    requestMtu(options: RequestMtuOptions): Promise<void>;
    /**
     * Start listening for characteristic value changes. This will emit the `characteristicChanged` event when a value changes.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    startCharacteristicNotifications(options: StartCharacteristicNotificationsOptions): Promise<void>;
    /**
     * Start the foreground service.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    startForegroundService(options: StartForegroundServiceOptions): Promise<void>;
    /**
     * Start scanning for BLE devices. This will emit the `deviceScanned` event when a device is found.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    startScan(options?: StartScanOptions): Promise<void>;
    /**
     * Stop listening for characteristic value changes.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    stopCharacteristicNotifications(options: StopCharacteristicNotificationsOptions): Promise<void>;
    /**
     * Stop the foreground service.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    stopForegroundService(): Promise<void>;
    /**
     * Stop scanning for BLE devices.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    stopScan(): Promise<void>;
    /**
     * Write a value to a characteristic.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    writeCharacteristic(options: WriteCharacteristicOptions): Promise<void>;
    /**
     * Write a value to a descriptor.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    writeDescriptor(options: WriteDescriptorOptions): Promise<void>;
    /**
     * Check permissions for the plugin.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    checkPermissions(): Promise<PermissionStatus>;
    /**
     * Request permissions for the plugin.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    requestPermissions(permissions?: BluetoothLowEnergyPluginPermission): Promise<PermissionStatus>;
    /**
     * Called when a characteristic value changes.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    addListener(eventName: 'characteristicChanged', listenerFunc: (event: CharacteristicChangedEvent) => void): Promise<PluginListenerHandle>;
    /**
     * Called when a device is disconnected.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    addListener(eventName: 'deviceDisconnected', listenerFunc: (event: DeviceDisconnectedEvent) => void): Promise<PluginListenerHandle>;
    /**
     * Called when an error occurs during the scan session.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    addListener(eventName: 'deviceScanned', listenerFunc: (event: DeviceScannedEvent) => void): Promise<PluginListenerHandle>;
    /**
     * Remove all listeners for this plugin.
     *
     * @since 6.0.0
     */
    removeAllListeners(): Promise<void>;
}
/**
 * @since 6.0.0
 */
export interface CharacteristicChangedEvent {
    /**
     * The UUID of the characteristic.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    characteristicId: string;
    /**
     * The address of the device.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The UUID of the service.
     *
     * @since 6.0.0
     * @example "0000180A-0000-1000-8000-00805F9B34FB"
     */
    serviceId?: string;
    /**
     * The changed value bytes of the characteristic.
     *
     * @since 6.0.0
     * @example [0, 1, 2, 3]
     */
    value: number[];
}
/**
 * @since 6.0.0
 */
export interface DeviceDisconnectedEvent {
    /**
     * The address of the disconnected device.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The name of the disconnected device.
     *
     * @since 6.0.0
     * @example "My Device"
     */
    name?: string;
}
/**
 * @since 6.0.0
 */
export interface ConnectOptions {
    /**
     * The address of the device to connect to.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The timeout for the connect operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 10000
     * @default 10000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface CreateBondOptions {
    /**
     * The address of the device to create a bond with.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The timeout for the create bond operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 10000
     * @default 10000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface DisconnectOptions {
    /**
     * The address of the device to disconnect from.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The timeout for the disconnect operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     * @default 5000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface DiscoverServiceOptions {
    /**
     * The address of the device to discover services for.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The timeout for the discover services operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 20000
     * @default 20000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface GetServicesOptions {
    /**
     * The address of the device to get the services for.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The timeout for the get services operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     * @default 5000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface GetServicesResult {
    /**
     * An array of services provided by the device.
     *
     * @since 6.0.0
     */
    services: Service[];
}
/**
 * @since 6.0.0
 */
export interface GetConnectedDevicesResult {
    /**
     * An array of connected devices.
     *
     * @since 6.0.0
     */
    devices: Device[];
}
/**
 * @since 6.0.0
 */
export interface IsBondedOptions {
    /**
     * The address of the device to check if it is bonded.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The timeout for the is bonded operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface IsBondedResult {
    /**
     * Whether or not the device is bonded.
     *
     * @since 6.0.0
     * @example true
     */
    bonded: boolean;
}
/**
 * @since 6.0.0
 */
export interface IsEnabledResult {
    /**
     * Whether or not Bluetooth is enabled.
     *
     * @since 6.0.0
     * @example true
     */
    enabled: boolean;
}
/**
 * @since 6.0.0
 */
export interface ReadCharacteristicOptions {
    /**
     * The UUID of the characteristic to read.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    characteristicId: string;
    /**
     * The address of the device to read the characteristic from.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The UUID of the service to read the characteristic from.
     *
     * @since 6.0.0
     * @example "0000180A-0000-1000-8000-00805F9B34FB"
     */
    serviceId: string;
    /**
     * The timeout for the read operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     * @default 5000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface ReadCharacteristicResult {
    /**
     * The value bytes of the characteristic.
     *
     * @since 6.0.0
     * @example [0, 1, 2, 3]
     */
    value: number[];
}
/**
 * @since 6.0.0
 */
export interface ReadDescriptorOptions {
    /**
     * The UUID of the characteristic that the descriptor belongs to.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    characteristicId: string;
    /**
     * The UUID of the descriptor to read.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    descriptorId: string;
    /**
     * The address of the device to read the descriptor from.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The UUID of the service that the descriptor belongs to.
     *
     * @since 6.0.0
     * @example "0000180A-0000-1000-8000-00805F9B34FB"
     */
    serviceId: string;
    /**
     * The timeout for the read operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     * @default 5000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface ReadDescriptorResult {
    /**
     * The value bytes of the descriptor.
     *
     * @since 6.0.0
     * @example [0, 1, 2, 3]
     */
    value: number[];
}
/**
 * @since 6.0.0
 */
export interface ReadRssiOptions {
    /**
     * The address of the device to read the RSSI for.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The timeout for the read RSSI operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     * @default 5000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface RequestConnectionPriorityOptions {
    /**
     * The address of the device to request the connection priority for.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The connection priority to request.
     *
     * @since 6.0.0
     */
    connectionPriority: ConnectionPriority;
    /**
     * The timeout for the request connection priority operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface RequestMtuOptions {
    /**
     * The address of the device to request the MTU size for.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The mtu size to request.
     *
     * @since 6.0.0
     */
    mtu: number;
    /**
     * The timeout for the request MTU operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface ReadRssiResult {
    /**
     * The RSSI value.
     *
     * @since 6.0.0
     * @example -50
     */
    rssi: number;
}
/**
 * @since 6.0.0
 */
export interface StartCharacteristicNotificationsOptions {
    /**
     * The UUID of the characteristic to start notifications for.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    characteristicId: string;
    /**
     * The address of the device to start notifications for.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The UUID of the service to start notifications for.
     *
     * @since 6.0.0
     * @example "0000180A-0000-1000-8000-00805F9B34FB"
     */
    serviceId: string;
    /**
     * The timeout for the start notifications operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     * @default 5000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface StartForegroundServiceOptions {
    /**
     * The body of the notification, shown below the title.
     *
     * @since 6.0.0
     * @example "This is the body of the notification"
     */
    body: string;
    /**
     * The notification identifier.
     *
     * @since 6.0.0
     * @example 123
     */
    id: number;
    /**
     * The status bar icon for the notification.
     *
     * Icons should be placed in your app's `res/drawable` folder. The value for
     * this option should be the drawable resource ID, which is the filename
     * without an extension.
     *
     * @since 6.0.0
     * @example "ic_stat_icon_config_sample"
     */
    smallIcon: string;
    /**
     * The title of the notification.
     *
     * @since 6.0.0
     * @example "This is the title of the notification"
     */
    title: string;
}
/**
 * @since 6.0.0
 */
export interface StartScanOptions {
    /**
     * Find devices with services that match any of the provided UUIDs.
     *
     * Only available on iOS.
     *
     * @since 6.0.0
     * @example ["0000180A-0000-1000-8000-00805F9B34FB"]
     */
    serviceIds?: string[];
}
/**
 * @since 6.0.0
 */
export interface StopCharacteristicNotificationsOptions {
    /**
     * The UUID of the characteristic to stop notifications for.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    characteristicId: string;
    /**
     * The address of the device to stop notifications for.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The UUID of the service to stop notifications for.
     *
     * @since 6.0.0
     * @example "0000180A-0000-1000-8000-00805F9B34FB"
     */
    serviceId: string;
    /**
     * The timeout for the stop notifications operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     * @default 5000
     */
    timeout?: number;
}
/**
 * @since 6.0.0
 */
export interface WriteCharacteristicOptions {
    /**
     * The UUID of the characteristic to write.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    characteristicId: string;
    /**
     * The address of the device to write the characteristic to.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The UUID of the service to write the characteristic to.
     *
     * @since 6.0.0
     * @example "0000180A-0000-1000-8000-00805F9B34FB"
     */
    serviceId: string;
    /**
     * The timeout for the write operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     * @default 5000
     */
    timeout?: number;
    /**
     * The type of write operation.
     *
     * @since 6.1.0
     * @default 'default'
     * @example 'withoutResponse'
     */
    type?: 'default' | 'withoutResponse';
    /**
     * The value bytes to write to the characteristic.
     *
     * @since 6.0.0
     * @example [0, 1, 2, 3]
     */
    value: number[];
}
/**
 * @since 6.0.0
 */
export interface WriteDescriptorOptions {
    /**
     * The UUID of the characteristic that the descriptor belongs to.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    characteristicId: string;
    /**
     * The UUID of the descriptor.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    descriptorId: string;
    /**
     * The address of the device that the descriptor belongs to.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    deviceId: string;
    /**
     * The UUID of the service that the descriptor belongs to.
     *
     * @since 6.0.0
     * @example "0000180A-0000-1000-8000-00805F9B34FB"
     */
    serviceId: string;
    /**
     * The timeout for the write operation in milliseconds.
     *
     * If the operation takes longer than this value, the promise will be rejected.
     *
     * @since 6.0.0
     * @example 5000
     * @default 5000
     */
    timeout?: number;
    /**
     * The value bytes of the descriptor.
     *
     * @since 6.0.0
     * @example [0, 1, 2, 3]
     */
    value: number[];
}
/**
 * @since 6.0.0
 */
export interface Characteristic {
    /**
     * The UUID of the characteristic.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    id: string;
    /**
     * The descriptors of the characteristic.
     *
     * @since 6.0.0
     */
    descriptors: Descriptor[];
    /**
     * The properties of the characteristic.
     *
     * @since 6.0.0
     */
    properties: CharacteristicProperties;
}
/**
 * @since 6.0.0
 */
export interface CharacteristicProperties {
    /**
     * Whether or not the characteristic can be broadcast.
     *
     * @since 6.0.0
     */
    broadcast: boolean;
    /**
     * Whether or not the characteristic can be read.
     *
     * @since 6.0.0
     */
    read: boolean;
    /**
     * Whether or not the characteristic can be written without response.
     *
     * @since 6.0.0
     */
    writeWithoutResponse: boolean;
    /**
     * Whether or not the characteristic can be written.
     *
     * @since 6.0.0
     */
    write: boolean;
    /**
     * Whether or not the characteristic supports notifications.
     *
     * @since 6.0.0
     */
    notify: boolean;
    /**
     * Whether or not the characteristic supports indications.
     *
     * @since 6.0.0
     */
    indicate: boolean;
    /**
     * Whether or not the characteristic supports signed writes.
     *
     * @since 6.0.0
     */
    authenticatedSignedWrites: boolean;
    /**
     * Whether or not the characteristic supports extended properties.
     *
     * @since 6.0.0
     */
    extendedProperties: boolean;
    /**
     * Whether or not the characteristic supports reliable writes.
     *
     * @since 6.0.0
     */
    notifyEncryptionRequired: boolean;
    /**
     * Whether or not the characteristic supports writable auxiliaries.
     *
     * @since 6.0.0
     */
    indicateEncryptionRequired: boolean;
}
/**
 * @since 6.0.0
 */
export interface Descriptor {
    /**
     * The UUID of the descriptor.
     *
     * @since 6.0.0
     * @example "00002A29-0000-1000-8000-00805F9B34FB"
     */
    id: string;
}
/**
 * @since 6.0.0
 */
export interface Device {
    /**
     * The UUID of the connected device.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    id: string;
    /**
     * The name of the connected device.
     *
     * @since 6.0.0
     * @example "My Device"
     */
    name?: string;
}
/**
 * @since 6.0.0
 */
export interface Service {
    /**
     * The UUID of the service.
     *
     * @since 6.0.0
     * @example "0000180A-0000-1000-8000-00805F9B34FB"
     */
    id: string;
    /**
     * The characteristics of the service.
     *
     * @since 6.0.0
     */
    characteristics: Characteristic[];
}
/**
 * @since 6.0.0
 */
export interface PermissionStatus {
    /**
     * Permission state for using bluetooth.
     *
     * Only available on iOS.
     *
     * @since 6.0.0
     */
    bluetooth?: PermissionState;
    /**
     * Permission state for connecting to a BLE device.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    bluetoothConnect?: PermissionState;
    /**
     * Permission state for scanning for BLE devices.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    bluetoothScan?: PermissionState;
    /**
     * Permission state for using location services.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    location?: PermissionState;
    /**
     * Permission state for using notifications.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    notifications?: PermissionState;
}
export interface DeviceScannedEvent {
    /**
     * The address of the scanned device.
     *
     * @since 6.0.0
     * @example "00:00:00:00:00:00"
     */
    id: string;
    /**
     * The name of the scanned device.
     *
     * @since 6.0.0
     * @example "My Device"
     */
    name?: string;
    /**
     * The RSSI value of the scanned device.
     *
     * Only available on iOS.
     *
     * @since 6.0.0
     * @example -50
     */
    rssi?: number;
}
/**
 * @since 6.0.0
 */
export interface IBluetoothLowEnergyUtils {
    /**
     * Convert a byte array to a hex string.
     *
     * @since 6.0.0
     */
    convertBytesToHex(options: ConvertBytesToHexOptions): {
        hex: string;
    };
    /**
     * Convert a hex string to a byte array.
     *
     * @since 6.0.0
     */
    convertHexToBytes(options: ConvertHexToBytesOptions): {
        bytes: number[];
    };
}
/**
 * @since 6.0.0
 */
export interface ConvertBytesToHexOptions {
    /**
     * The byte array to convert to a hex string.
     *
     * @since 6.0.0
     */
    bytes: number[];
    /**
     * The text to prepend to the hex string.
     *
     * @since 6.0.0
     * @default '0x'
     */
    start?: string;
    /**
     * The separator to use between each byte.
     *
     * @since 6.0.0
     * @default ''
     * @example ':'
     */
    separator?: string;
}
/**
 * @since 6.0.0
 */
export interface ConvertHexToBytesOptions {
    /**
     * The hex string to convert to a byte array.
     *
     * @since 6.0.0
     */
    hex: string;
    /**
     * The text to remove from the beginning of the hex string.
     *
     * @since 6.0.0
     * @default '0x'
     */
    start?: string;
    /**
     * The separator which is used between each byte.
     *
     * @since 6.0.0
     * @default ''
     * @example ':'
     */
    separator?: string;
}
/**
 * @since 6.0.0
 */
export declare enum ConnectionPriority {
    /**
     * Balanced connection priority.
     *
     * @since 6.0.0
     * @see https://developer.android.com/reference/android/bluetooth/BluetoothGatt#CONNECTION_PRIORITY_BALANCED
     */
    BALANCED = 0,
    /**
     * High connection priority.
     *
     * @since 6.0.0
     * @see https://developer.android.com/reference/android/bluetooth/BluetoothGatt#CONNECTION_PRIORITY_HIGH
     */
    HIGH = 1,
    /**
     * Low power connection priority.
     *
     * @since 6.0.0
     * @see https://developer.android.com/reference/android/bluetooth/BluetoothGatt#CONNECTION_PRIORITY_LOW_POWER
     */
    LOW_POWER = 2,
    /**
     * Digital Car Key connection priority.
     *
     * @since 6.0.0
     * @see https://developer.android.com/reference/android/bluetooth/BluetoothGatt#CONNECTION_PRIORITY_DCK
     */
    PRIORITY_DCK = 3
}
