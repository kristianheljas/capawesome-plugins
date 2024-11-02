import type { ListenerCallback, PluginListenerHandle } from '@capacitor/core';
import type { BluetoothLowEnergyPlugin, ConnectOptions, CreateBondOptions, DisconnectOptions, DiscoverServiceOptions, GetConnectedDevicesResult, GetServicesOptions, GetServicesResult, IsBondedOptions, IsBondedResult, IsEnabledResult, PermissionStatus, ReadCharacteristicOptions, ReadCharacteristicResult, ReadDescriptorOptions, ReadDescriptorResult, ReadRssiOptions, ReadRssiResult, RequestConnectionPriorityOptions, RequestMtuOptions, StartCharacteristicNotificationsOptions, StartForegroundServiceOptions, StartScanOptions, StopCharacteristicNotificationsOptions, WriteCharacteristicOptions, WriteDescriptorOptions } from './definitions';
export declare class BluetoothLowEnergyClient implements BluetoothLowEnergyPlugin {
    private readonly plugin;
    private readonly queue;
    constructor(plugin: BluetoothLowEnergyPlugin);
    connect(options: ConnectOptions): Promise<void>;
    createBond(options: CreateBondOptions): Promise<void>;
    disconnect(options: DisconnectOptions): Promise<void>;
    discoverServices(options: DiscoverServiceOptions): Promise<void>;
    getConnectedDevices(): Promise<GetConnectedDevicesResult>;
    getServices(options: GetServicesOptions): Promise<GetServicesResult>;
    initialize(): Promise<void>;
    isBonded(options: IsBondedOptions): Promise<IsBondedResult>;
    isEnabled(): Promise<IsEnabledResult>;
    openAppSettings(): Promise<void>;
    openBluetoothSettings(): Promise<void>;
    openLocationSettings(): Promise<void>;
    readCharacteristic(options: ReadCharacteristicOptions): Promise<ReadCharacteristicResult>;
    readDescriptor(options: ReadDescriptorOptions): Promise<ReadDescriptorResult>;
    readRssi(options: ReadRssiOptions): Promise<ReadRssiResult>;
    requestConnectionPriority(options: RequestConnectionPriorityOptions): Promise<void>;
    requestMtu(options: RequestMtuOptions): Promise<void>;
    startCharacteristicNotifications(options: StartCharacteristicNotificationsOptions): Promise<void>;
    startScan(options: StartScanOptions): Promise<void>;
    startForegroundService(options: StartForegroundServiceOptions): Promise<void>;
    stopCharacteristicNotifications(options: StopCharacteristicNotificationsOptions): Promise<void>;
    stopForegroundService(): Promise<void>;
    stopScan(): Promise<void>;
    writeCharacteristic(options: WriteCharacteristicOptions): Promise<void>;
    writeDescriptor(options: WriteDescriptorOptions): Promise<void>;
    checkPermissions(): Promise<PermissionStatus>;
    requestPermissions(): Promise<PermissionStatus>;
    addListener(eventName: string, listenerFunc: ListenerCallback): Promise<PluginListenerHandle>;
    removeAllListeners(): Promise<void>;
    private timeout;
    private createTimeoutException;
}