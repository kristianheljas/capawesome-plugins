import { WebPlugin } from '@capacitor/core';
import type { ConnectOptions, GetAvailableNetworksResult, GetIpAddressResult, GetRssiResult, GetSsidResult, IsEnabledResult, PermissionStatus, RequestPermissionsOptions, WifiPlugin } from './definitions';
export declare class WifiWeb extends WebPlugin implements WifiPlugin {
    connect(_options: ConnectOptions): Promise<void>;
    disconnect(): Promise<void>;
    getAvailableNetworks(): Promise<GetAvailableNetworksResult>;
    getIpAddress(): Promise<GetIpAddressResult>;
    getRssi(): Promise<GetRssiResult>;
    getSsid(): Promise<GetSsidResult>;
    isEnabled(): Promise<IsEnabledResult>;
    startScan(): Promise<void>;
    checkPermissions(): Promise<PermissionStatus>;
    requestPermissions(_options?: RequestPermissionsOptions | undefined): Promise<PermissionStatus>;
    private createUnimplementedException;
}
