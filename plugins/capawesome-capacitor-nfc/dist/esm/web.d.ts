import { WebPlugin } from '@capacitor/core';
import type { ConnectOptions, GetAntennaInfoResult, IsEnabledResult, IsSupportedResult, NfcPlugin, PermissionResult, TransceiveOptions, TransceiveResult, WriteOptions } from './definitions';
export declare class NfcWeb extends WebPlugin implements NfcPlugin {
    static readonly nfcTagScannedEvent = "nfcTagScanned";
    static readonly scanSessionErrorEvent = "scanSessionError";
    private readonly TAG;
    private readonly _isSupported;
    private readonly nfcUtils;
    private ndefReader;
    private abortController;
    startScanSession(): Promise<void>;
    stopScanSession(): Promise<void>;
    write(options: WriteOptions): Promise<void>;
    makeReadOnly(): Promise<void>;
    erase(): Promise<void>;
    format(): Promise<void>;
    transceive(_options: TransceiveOptions): Promise<TransceiveResult>;
    connect(_options: ConnectOptions): Promise<void>;
    close(): Promise<void>;
    isSupported(): Promise<IsSupportedResult>;
    isEnabled(): Promise<IsEnabledResult>;
    openSettings(): Promise<void>;
    getAntennaInfo(): Promise<GetAntennaInfoResult>;
    setAlertMessage(_options: {
        message: string;
    }): Promise<void>;
    checkPermissions(): Promise<PermissionResult>;
    requestPermissions(): Promise<PermissionResult>;
    private handleNfcTagScannedEvent;
    private handleScanSessionErrorEvent;
    private createNfcTagResult;
    private createNdefMessageResult;
    private createNdefRecordResultFromNativeNDEFRecord;
    private createNativeNDEFRecordInitFromNdefRecord;
    private mapNativeRecordTypeToTypeNameFormat;
    private convertStringToBytes;
    private convertBytesToString;
    private convertHexToBytes;
    private createUnavailableException;
    private createSessionMissingException;
}