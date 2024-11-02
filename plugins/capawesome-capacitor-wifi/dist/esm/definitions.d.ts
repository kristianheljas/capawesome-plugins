import type { PermissionState, PluginListenerHandle } from '@capacitor/core';
export interface WifiPlugin {
    /**
     * Connect to a Wi-Fi network.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    connect(options: ConnectOptions): Promise<void>;
    /**
     * Disconnect from a Wi-Fi network.
     *
     * On **iOS**, you can only disconnect from networks that you connected to using the plugin.
     * This also removes the Wi-Fi network from the list of known networks.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    disconnect(options?: DisconnectOptions): Promise<void>;
    /**
     * Get a list of Wi-Fi networks found during the last scan.
     *
     * The returned networks are the most recently updated results, which may be from a previous scan
     * if your current scan has not completed or succeeded.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    getAvailableNetworks(): Promise<GetAvailableNetworksResult>;
    /**
     * Get the current IP address of the device.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    getIpAddress(): Promise<GetIpAddressResult>;
    /**
     * Get the received signal strength indicator (RSSI) of the current network in dBm.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    getRssi(): Promise<GetRssiResult>;
    /**
     * Get the service set identifier (SSID) of the current network.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    getSsid(): Promise<GetSsidResult>;
    /**
     * Check if Wi-Fi is enabled.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    isEnabled(): Promise<IsEnabledResult>;
    /**
     * Start a scan for Wi-Fi networks.
     *
     * This call may fail for any of the following reasons:
     * - Scan requests may be throttled because of too many scans in a short time.
     * - The device is idle and scanning is disabled.
     * - Wi-Fi hardware reports a scan failure.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    startScan(): Promise<void>;
    /**
     * Check permissions for the plugin.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    checkPermissions(): Promise<PermissionStatus>;
    /**
     * Request permissions for the plugin.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    requestPermissions(options?: RequestPermissionsOptions): Promise<PermissionStatus>;
    /**
     * Called when the scan results are available.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    addListener(eventName: 'networksScanned', listenerFunc: (event: NetworksScannedEvent) => void): Promise<PluginListenerHandle>;
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
export interface ConnectOptions {
    /**
     * The SSID of the network to connect to.
     *
     *
     */
    ssid: string;
    /**
     * The password of the network to connect to.
     *
     * @since 6.0.0
     */
    password?: string;
    /**
     * Whether or not the SSID is hidden.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     * @default false
     * @see https://developer.android.com/reference/android/net/wifi/WifiNetworkSpecifier.Builder#setIsHiddenSsid(boolean)
     */
    isHiddenSsid?: boolean;
}
/**
 * @since 6.0.0
 */
export interface DisconnectOptions {
    /**
     * The SSID of the network to disconnect from.
     * If not provided, the device will disconnect from the current network.
     *
     * Only available on iOS.
     *
     * @since 6.0.0
     */
    ssid?: string;
}
/**
 * @since 6.0.0
 */
export interface GetAvailableNetworksResult {
    /**
     * The list of Wi-Fi networks found during the last scan.
     *
     * @since 6.0.0
     */
    networks: Network[];
}
/**
 * @since 6.0.0
 */
export interface GetIpAddressResult {
    /**
     * The IP address of the device.
     *
     * @since 6.0.0
     */
    address: string;
}
/**
 * @since 6.0.0
 */
export interface GetRssiResult {
    /**
     * The received signal strength indicator (RSSI) of the current network in dBm.
     *
     * @since 6.0.0
     */
    rssi: number;
}
/**
 * @since 6.0.0
 */
export interface GetSsidResult {
    /**
     * The service set identifier (SSID) of the current network.
     *
     * On **iOS 14+**, the SSID can only be retrieved if the network was connected to using the plugin
     * or if the app has permission to access precise location.
     *
     * @since 6.0.0
     */
    ssid: string;
}
/**
 * @since 6.0.0
 */
export interface IsEnabledResult {
    /**
     * Whether or not Wi-Fi is enabled.
     *
     * @since 6.0.0
     */
    enabled: boolean;
}
/**
 * @since 6.0.0
 */
export interface RequestPermissionsOptions {
    /**
     * The permissions to request.
     *
     * @since 6.0.0
     * @default ["location"]
     */
    permissions?: PermissionType[];
}
/**
 * @since 6.0.0
 */
export interface PermissionStatus {
    /**
     * @since 6.0.0
     */
    location: PermissionState;
}
/**
 * @since 6.0.0
 */
export interface NetworksScannedEvent {
    /**
     * The list of Wi-Fi networks found during the scan.
     *
     * @since 6.0.0
     */
    networks: Network[];
}
/**
 * @since 6.0.0
 */
export interface Network {
    /**
     * The received signal strength indicator (RSSI) of the network in dBm.
     *
     * @since 6.1.0
     */
    rssi: number;
    /**
     * The service set identifier (SSID) of the network.
     *
     * Only available on Android (SDK 33+).
     *
     * @since 6.1.0
     */
    securityTypes?: NetworkSecurityType[];
    /**
     * The service set identifier (SSID) of the network.
     *
     * @since 6.0.0
     * @example "MyNetwork"
     */
    ssid: string;
}
/**
 * @since 6.0.0
 */
export declare type PermissionType = 'location';
/**
 * @since 6.1.0
 */
export declare enum NetworkSecurityType {
    /**
     * Unknown security type.
     *
     * @since 6.1.0
     */
    UNKNOWN = -1,
    /**
     * Open network.
     *
     * @since 6.1.0
     */
    OPEN = 0,
    /**
     * WEP network.
     *
     * @since 6.1.0
     */
    WEP = 1,
    /**
     * PSK network.
     *
     * @since 6.1.0
     */
    PSK = 2,
    /**
     * EAP network.
     *
     * @since 6.1.0
     */
    EAP = 3,
    /**
     * SAE network.
     *
     * @since 6.1.0
     */
    SAE = 4,
    /**
     * WPA3-Enterprise in 192-bit security network.
     *
     * @since 6.1.0
     */
    EAP_WPA3_ENTERPRISE_192_BIT = 5,
    /**
     * OWE network.
     *
     * @since 6.1.0
     */
    OWE = 6,
    /**
     * WAPI PSK network.
     *
     * @since 6.1.0
     */
    WAPI_PSK = 7,
    /**
     * WAPI Certificate network.
     *
     * @since 6.1.0
     */
    WAPI_CERT = 8,
    /**
     * WPA3-Enterprise network.
     *
     * @since 6.1.0
     */
    WPA3_ENTERPRISE = 9,
    /**
     * OSEN network.
     *
     * @since 6.1.0
     */
    OSEN = 10,
    /**
     * Passpoint R1/R2 network, where TKIP and WEP are not allowed.
     *
     * @since 6.1.0
     */
    PASSPOINT_R1_R2 = 11,
    /**
     * Passpoint R3 network, where TKIP and WEP are not allowed, and PMF must be set to Required.
     *
     * @since 6.1.0
     */
    PASSPOINT_R3 = 12,
    /**
     * Easy Connect (DPP) network.
     *
     * @since 6.1.0
     */
    DPP = 13
}
