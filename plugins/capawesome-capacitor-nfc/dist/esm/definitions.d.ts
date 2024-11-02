import type { PermissionState, PluginListenerHandle } from '@capacitor/core';
export interface NfcPlugin {
    /**
     * Start a scan session.
     * Only one session can be active at a time.
     *
     * Stop the session as soon as you are done using `stopScanSession(...)`.
     *
     * On iOS, this will trigger the NFC Reader Session alert.
     *
     * @since 0.0.1
     */
    startScanSession(options?: StartScanSessionOptions): Promise<void>;
    /**
     * Stop the active scan session.
     *
     * @since 0.0.1
     */
    stopScanSession(options?: StopScanSessionOptions): Promise<void>;
    /**
     * Write to a NFC tag.
     *
     * This method must be called from within a `nfcTagScanned` handler.
     *
     * @since 0.0.1
     */
    write(options: WriteOptions): Promise<void>;
    /**
     * Make a NFC tag readonly.
     *
     * This method must be called from within a `nfcTagScanned` handler.
     *
     * **Attention:** This is permanent and can not be undone.
     *
     * @since 0.0.1
     */
    makeReadOnly(): Promise<void>;
    /**
     * Erase the NFC tag by writing an empty NDEF message.
     *
     * This method must be called from within a `nfcTagScanned` handler.
     *
     * @since 0.3.0
     */
    erase(): Promise<void>;
    /**
     * Format the NFC tag as NDEF and write an empty NDEF message.
     *
     * This method must be called from within a `nfcTagScanned` handler.
     *
     * Only available on Android.
     *
     * @since 0.3.0
     */
    format(): Promise<void>;
    /**
     * Send raw command to the tag and receive the response.
     *
     * This method must be called from within a `nfcTagScanned` handler.
     *
     * On **Android**, the tag must be connected with `connect()` first.
     *
     * ⚠️ **Experimental:** This method could not be tested extensively yet.
     * Please let us know if you discover any issues!
     *
     * ⚠️ **Attention**: A bad command can damage the tag forever.
     * Please read the Android and iOS documentation linked below first.
     *
     * More information on how to use this method on **Android**: https://bit.ly/3ywSkvT
     *
     * More information on how to use this method on **iOS** with...
     * - ISO 15693-3: https://apple.co/3Lliaum
     * - FeliCa: https://apple.co/3LpvRs6
     *
     * Only available on Android and iOS.
     *
     * @since 0.3.0
     */
    transceive(options: TransceiveOptions): Promise<TransceiveResult>;
    /**
     * Connect to the tag and enable I/O operations.
     *
     * This method must be called from within a `nfcTagScanned` handler.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    connect(options: ConnectOptions): Promise<void>;
    /**
     * Close the connection to the tag.
     *
     * This method must be called from within a `nfcTagScanned` handler.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    close(): Promise<void>;
    /**
     * Returns whether or not NFC is supported.
     *
     * @since 0.0.1
     */
    isSupported(): Promise<IsSupportedResult>;
    /**
     * Returns whether or not NFC is enabled.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    isEnabled(): Promise<IsEnabledResult>;
    /**
     * Opens the NFC device settings so that the user can enable or disable NFC.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    openSettings(): Promise<void>;
    /**
     * Returns information regarding Nfc antennas on the device such as their relative positioning on the device.
     *
     * Only available on Android.
     *
     * @since 6.1.0
     */
    getAntennaInfo(): Promise<GetAntennaInfoResult>;
    /**
     * Set a custom message, which is displayed in the NFC Reader Session alert.
     *
     * Only available on iOS.
     *
     * @since 6.2.0
     * @see https://developer.apple.com/documentation/corenfc/nfcreadersession/2919987-alertmessage
     */
    setAlertMessage(options: SetAlertMessageOptions): Promise<void>;
    /**
     * Check permission for reading and writing NFC tags.
     *
     * This method is only needed on Web.
     * On Android and iOS, `granted` is always returned.
     *
     * @since 0.0.1
     */
    checkPermissions(): Promise<PermissionResult>;
    /**
     * Request permission for reading and writing NFC tags.
     *
     * This method is only needed on Web.
     * On Android and iOS, `granted` is always returned.
     *
     * @since 0.0.1
     */
    requestPermissions(): Promise<PermissionResult>;
    /**
     * Called when a new NFC tag is scanned.
     *
     * @since 0.0.1
     */
    addListener(eventName: 'nfcTagScanned', listenerFunc: (event: NfcTagScannedEvent) => void): Promise<PluginListenerHandle>;
    /**
     * Called when the scan session was canceled.
     *
     * Only available on iOS.
     *
     * @since 0.0.1
     */
    addListener(eventName: 'scanSessionCanceled', listenerFunc: () => void): Promise<PluginListenerHandle>;
    /**
     * Called when an error occurs during the scan session.
     *
     * @since 0.0.1
     */
    addListener(eventName: 'scanSessionError', listenerFunc: (event: ScanSessionErrorEvent) => void): Promise<PluginListenerHandle>;
    /**
     * Remove all listeners for this plugin.
     *
     * @since 0.0.1
     */
    removeAllListeners(): Promise<void>;
}
/**
 * @since 0.0.1
 */
export interface StartScanSessionOptions {
    /**
     * A custom message, which is displayed in the NFC Reader Session alert.
     *
     * Only available on iOS.
     *
     * @since 0.0.1
     * @example 'Hold your iPhone near the NFC tag.'
     * @see https://developer.apple.com/documentation/corenfc/nfcreadersession/2919987-alertmessage
     */
    alertMessage?: string;
    /**
     * The NFC tag technologies to filter on in this session.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    techTypes?: NfcTagTechType[];
    /**
     * Mime types to filter on in this session.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    mimeTypes?: string[];
    /**
     * Type of tags to detect during a polling sequence.
     *
     * Only available on iOS.
     *
     * @default [PollingOption.iso14443, PollingOption.iso15693]
     * @since 0.2.0
     */
    pollingOptions?: PollingOption[];
}
/**
 * @since 0.0.1
 */
export interface StopScanSessionOptions {
    /**
     * A custom error message, which is displayed in the NFC Reader Session alert.
     *
     * Only available on iOS.
     *
     * @since 0.0.1
     */
    errorMessage?: string;
}
/**
 * @since 0.0.1
 */
export interface WriteOptions {
    /**
     * The NDEF message to write.
     *
     * @since 0.0.1
     */
    message: NdefMessage;
}
/**
 * @since 0.3.0
 */
export interface TransceiveOptions {
    /**
     * The NFC tag technology to connect with.
     *
     * Only available on iOS.
     *
     * @since 0.3.0
     */
    techType?: NfcTagTechType;
    /**
     * Bytes to send.
     *
     * @since 0.3.0
     */
    data: number[];
    /**
     * The request flags for the NFC tag technology type `NfcV` (ISO 15693-3).
     *
     * Only available on iOS 14+
     *
     * @since 0.3.0
     */
    iso15693RequestFlags?: Iso15693RequestFlag[];
    /**
     * The custom command code defined by the IC manufacturer for the NFC tag
     * technology type `NfcV` (ISO 15693-3).
     * Valid range is 0xA0 to 0xDF inclusively, 0x23 is also supported.
     *
     * Only available on iOS 14+
     *
     * @since 0.3.0
     */
    iso15693CommandCode?: number;
}
/**
 * @since 0.3.0
 */
export interface TransceiveResult {
    /**
     * Bytes received in response.
     *
     * @since 0.3.0
     */
    response: number[];
}
/**
 * @since 6.0.0
 */
export interface ConnectOptions {
    /**
     * The NFC tag technology to connect with.
     *
     * Only available on Android.
     *
     * @since 6.0.0
     */
    techType: NfcTagTechType;
}
/**
 * @since 0.0.1
 */
export interface NfcTagScannedEvent {
    /**
     * The scanned NFC tag.
     *
     * @since 0.0.1
     */
    nfcTag: NfcTag;
}
/**
 * @since 0.0.1
 */
export interface ScanSessionErrorEvent {
    /**
     * The error message.
     *
     * @since 0.0.1
     */
    message: string;
}
/**
 * @since 0.0.1
 */
export interface NfcTag {
    /**
     * The ATQA/SENS_RES bytes of an NFC A tag.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    atqa?: number[];
    /**
     * The Application Data bytes from ATQB/SENSB_RES of an NFC B tag.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    applicationData?: number[];
    /**
     * The barcode bytes of an NfcBarcode tag.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    barcode?: number[];
    /**
     * Whether the NDEF tag can be made read-only or not.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    canMakeReadOnly?: boolean;
    /**
     * The DSF ID bytes of an NFC V tag.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    dsfId?: number[];
    /**
     * The higher layer response bytes of an ISO-DEP tag.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    hiLayerResponse?: number[];
    /**
     * The historical bytes of an ISO-DEP tag.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    historicalBytes?: number[];
    /**
     * The tag identifier (low level serial number)
     * which is used for anti-collision and identification.
     *
     * @since 0.0.1
     */
    id?: number[];
    /**
     * Whether the NDEF tag is writable or not.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    isWritable?: boolean;
    /**
     * The Manufacturer bytes of an NFC F tag.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    manufacturer?: number[];
    /**
     * The maximum NDEF message size in bytes.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    maxSize?: number;
    /**
     * The NDEF-formatted message.
     *
     * @since 0.0.1
     */
    message?: NdefMessage;
    /**
     * The Protocol Info bytes from ATQB/SENSB_RES of an NFC B tag.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    protocolInfo?: number[];
    /**
     * The Response Flag bytes of an NFC V tag.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    responseFlags?: number[];
    /**
     * The SAK/SEL_RES bytes of an NFC A tag.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    sak?: number[];
    /**
     * The System Code bytes of an NFC F tag.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    systemCode?: number[];
    /**
     * The technologies available in this tag.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    techTypes?: NfcTagTechType[];
    /**
     * The NDEF tag type.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    type?: NfcTagType;
}
/**
 * @since 0.0.1
 */
export interface NdefMessage {
    /**
     * The records of the NDEF message.
     *
     * @since 0.0.1
     */
    records: NdefRecord[];
}
/**
 * @since 0.0.1
 */
export interface NdefRecord {
    /**
     * The record identifier as byte array.
     *
     * @since 0.0.1
     */
    id?: number[];
    /**
     * The payload field data as byte array.
     *
     * @since 0.0.1
     */
    payload?: number[];
    /**
     * The record type name format which indicates the structure of
     * the value of the `type` field.
     *
     * @since 0.0.1
     */
    tnf: TypeNameFormat;
    /**
     * The type of the record payload.
     * This should be used in conjunction with the `tnf` field to determine
     * the payload format.
     *
     * @since 0.0.1
     */
    type?: number[];
}
/**
 * @since 0.0.1
 */
export declare enum TypeNameFormat {
    /**
     * An empty record with no type or payload.
     *
     * @since 0.0.1
     */
    Empty = 0,
    /**
     * A predefined type defined in the RTD specification of the NFC Forum.
     *
     * @since 0.0.1
     */
    WellKnown = 1,
    /**
     * An Internet media type as defined in RFC 2046.
     *
     * @since 0.0.1
     */
    MimeMedia = 2,
    /**
     * A URI as defined in RFC 3986.
     *
     * @since 0.0.1
     */
    AbsoluteUri = 3,
    /**
     * A user-defined value that is based on the rules of the
     * NFC Forum Record Type Definition specification.
     *
     * @since 0.0.1
     */
    External = 4,
    /**
     * Type is unknown.
     *
     * @since 0.0.1
     */
    Unknown = 5,
    /**
     * Indicates the payload is an intermediate or final chunk
     * of a chunked NDEF Record.
     *
     * @since 0.0.1
     */
    Unchanged = 6
}
/**
 * @since 0.0.1
 */
export declare enum RecordTypeDefinition {
    /**
     * @since 0.0.1
     */
    AndroidApp = "android.com:pkg",
    /**
     * @since 0.0.1
     */
    AlternativeCarrier = "ac",
    /**
     * @since 0.0.1
     */
    HandoverCarrier = "Hc",
    /**
     * @since 0.0.1
     */
    HandoverRequest = "Hr",
    /**
     * @since 0.0.1
     */
    HandoverSelect = "Hs",
    /**
     * @since 0.0.1
     */
    SmartPoster = "Sp",
    /**
     * @since 0.0.1
     */
    Text = "T",
    /**
     * @since 0.0.1
     */
    Uri = "U"
}
/**
 * @since 0.0.1
 */
export declare enum NfcTagTechType {
    /**
     * The NFC-A (ISO 14443-3A) tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcA = "NFC_A",
    /**
     * The NFC-B (ISO 14443-3B) tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcB = "NFC_B",
    /**
     * The NFC-F (JIS 6319-4) tag technology.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    NfcF = "NFC_F",
    /**
     * The NFC-V (ISO 15693) tag technology.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    NfcV = "NFC_V",
    /**
     * The ISO-DEP (ISO 14443-4) tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    IsoDep = "ISO_DEP",
    /**
     * The ISO 7816 tag technology.
     *
     * Only available on iOS.
     *
     * @since 5.1.0
     */
    Iso7816 = "ISO_7816",
    /**
     * The NDEF tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    Ndef = "NDEF",
    /**
     * The MIFARE Classic tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    MifareClassic = "MIFARE_CLASSIC",
    /**
     * The MIFARE Desfire tag technology.
     *
     * Only available on iOS.
     *
     * @since 5.1.0
     */
    MifareDesfire = "MIFARE_DESFIRE",
    /**
     * The MIFARE Plus tag technology.
     *
     * Only available on iOS.
     *
     * @since 5.1.0
     */
    MifarePlus = "MIFARE_PLUS",
    /**
     * The MIFARE Ultralight tag technology.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    MifareUltralight = "MIFARE_ULTRALIGHT",
    /**
     * The technology of a tag containing just a barcode.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcBarcode = "NFC_BARCODE",
    /**
     * The NDEF formatable tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NdefFormatable = "NDEF_FORMATABLE"
}
/**
 * @since 0.0.1
 */
export declare enum NfcTagType {
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcForumType1 = "NFC_FORUM_TYPE_1",
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcForumType2 = "NFC_FORUM_TYPE_2",
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    NfcForumType3 = "NFC_FORUM_TYPE_3",
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcForumType4 = "NFC_FORUM_TYPE_4",
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    MifareClassic = "MIFARE_CLASSIC",
    /**
     * @since 0.0.1
     * @deprecated Not supported on any platform.
     */
    MifareDesfire = "MIFARE_DESFIRE",
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    MifarePlus = "MIFARE_PLUS",
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    MifarePro = "MIFARE_PRO",
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    MifareUltralight = "MIFARE_ULTRALIGHT",
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    MifareUltralightC = "MIFARE_ULTRALIGHT_C"
}
/**
 * @since 0.0.1
 */
export interface IsSupportedResult {
    /**
     * @since 0.0.1
     */
    isSupported: boolean;
}
/**
 * @since 0.0.1
 */
export interface IsEnabledResult {
    /**
     * @since 0.0.1
     */
    isEnabled: boolean;
}
/**
 * @since 6.1.0
 */
export interface GetAntennaInfoResult {
    /**
     * The available NFC antennas on the device.
     *
     * @since 6.1.0
     */
    availableAntennas: Antenna[];
    /**
     * The height of the device in millimeters.
     *
     * @since 6.1.0
     * @example 200
     */
    deviceHeight: number;
    /**
     * The width of the device in millimeters.
     *
     * @since 6.1.0
     * @example 100
     */
    deviceWidth: number;
    /**
     * Whether or not the device is foldable.
     *
     * @since 6.1.0
     * @example true
     */
    isDeviceFoldable: boolean;
}
/**
 * @since 6.2.0
 */
export interface SetAlertMessageOptions {
    /**
     * The custom message, which is displayed in the NFC Reader Session alert.
     *
     * @since 6.2.0
     *
     * @example 'Hold your iPhone near the NFC tag.'
     */
    message: string;
}
/**
 * @since 6.1.0
 */
export interface Antenna {
    /**
     * The location of the NFC antenna on the X axis in millimeters.
     *
     * @since 6.1.0
     * @example 50
     */
    locationX: number;
    /**
     * The location of the NFC antenna on the Y axis in millimeters.
     *
     * @since 6.1.0
     * @example 100
     */
    locationY: number;
}
/**
 * @since 0.0.1
 */
export interface PermissionResult {
    /**
     * Permission state for reading and writing NFC tags.
     *
     * @since 0.0.1
     */
    nfc: PermissionState;
}
/**
 * @since 0.0.1
 */
export interface INfcUtils {
    /**
     * Convert a byte array to a hex string.
     *
     * @since 0.3.1
     */
    convertBytesToHex(options: ConvertBytesToHexOptions): {
        hex: string;
    };
    /**
     * Convert a byte array to a string.
     *
     * @since 0.0.1
     */
    convertBytesToString(options: ConvertBytesToStringOptions): {
        text: string;
    };
    /**
     * Convert a hex string to a byte array.
     *
     * @since 0.3.1
     */
    convertHexToBytes(options: ConvertHexToBytesOptions): {
        bytes: number[];
    };
    /**
     * Convert a hex string to a number.
     *
     * @since 0.3.1
     */
    convertHexToNumber(options: ConvertHexToNumberOptions): {
        number: number;
    };
    /**
     * Convert a number to a hex string.
     *
     * @since 0.3.1
     */
    convertNumberToHex(options: ConvertNumberToHexOptions): {
        hex: string;
    };
    /**
     * Convert a string to a byte array.
     *
     * @since 0.0.1
     */
    convertStringToBytes(options: ConvertStringToBytesOptions): {
        bytes: number[];
    };
    /**
     * Create a NDEF record.
     *
     * @since 0.0.1
     */
    createNdefRecord(options: CreateNdefRecordOptions): CreateNdefRecordResult;
    /**
     * Create an empty NDEF record.
     *
     * @since 0.0.1
     */
    createNdefEmptyRecord(): CreateNdefRecordResult;
    /**
     * Create a NDEF text record.
     *
     * @since 0.0.1
     */
    createNdefTextRecord(options: CreateNdefTextRecordOptions): CreateNdefRecordResult;
    /**
     * Create a NDEF URI record.
     *
     * @since 0.0.1
     */
    createNdefUriRecord(options: CreateNdefUriRecordOptions): CreateNdefRecordResult;
    /**
     * Create a NDEF absolute URI record.
     *
     * @since 0.0.1
     */
    createNdefAbsoluteUriRecord(options: CreateNdefAbsoluteUriRecordOptions): CreateNdefRecordResult;
    /**
     * Create a NDEF mime media record.
     *
     * @since 0.0.1
     */
    createNdefMimeMediaRecord(options: CreateNdefMimeMediaRecordOptions): CreateNdefRecordResult;
    /**
     * Create a NDEF external type record.
     *
     * @since 0.0.1
     */
    createNdefExternalRecord(options: CreateNdefExternalRecordOptions): CreateNdefRecordResult;
    /**
     * Get the identifier code from a NDEF URI record.
     *
     * This method assumes that the record has a valid URI identifier code.
     *
     * @since 0.3.1
     */
    getIdentifierCodeFromNdefUriRecord(options: GetIdentifierCodeFromNdefUriRecordOptions): {
        identifierCode: UriIdentifierCode | undefined;
    };
    /**
     * Get the language code from a NDEF text record.
     *
     * @since 0.0.1
     */
    getLanguageFromNdefTextRecord(options: GetLanguageFromNdefTextRecordOptions): {
        language: string | undefined;
    };
    /**
     * Get the text from a NDEF text record.
     *
     * @since 0.0.1
     */
    getTextFromNdefTextRecord(options: GetTextFromNdefTextRecordOptions): {
        text: string | undefined;
    };
    /**
     * Get the uri from a NDEF URI record.
     *
     * This method assumes that the record has a valid URI identifier code.
     *
     * @since 0.3.1
     */
    getUriFromNdefUriRecord(options: GetIdentifierCodeFromNdefUriRecordOptions): {
        uri: string | undefined;
    };
    /**
     * Map a byte array to a the corresponding NDEF record type.
     *
     * @since 0.0.1
     */
    mapBytesToRecordTypeDefinition(options: {
        bytes: number[];
    }): {
        type: RecordTypeDefinition | undefined;
    };
}
/**
 * @since 0.3.1
 */
export interface ConvertBytesToHexOptions {
    /**
     * The byte array to convert to a hex string.
     *
     * @since 0.3.1
     */
    bytes: number[];
    /**
     * The text to prepend to the hex string.
     *
     * @since 0.3.1
     * @default '0x'
     */
    start?: string;
    /**
     * The separator to use between each byte.
     *
     * @since 0.3.1
     * @default ''
     * @example ':'
     */
    separator?: string;
}
/**
 * @since 0.0.1
 */
export interface ConvertBytesToStringOptions {
    /**
     * The byte array to convert to a string.
     *
     * @since 0.0.1
     */
    bytes: number[];
}
/**
 * @since 0.3.1
 */
export interface ConvertHexToBytesOptions {
    /**
     * The hex string to convert to a byte array.
     *
     * @since 0.3.1
     */
    hex: string;
    /**
     * The text to remove from the beginning of the hex string.
     *
     * @since 0.3.1
     * @default '0x'
     */
    start?: string;
    /**
     * The separator which is used between each byte.
     *
     * @since 0.3.1
     * @default ''
     * @example ':'
     */
    separator?: string;
}
/**
 * @since 0.3.1
 */
export interface ConvertHexToNumberOptions {
    /**
     * The hex string to convert to a number array.
     *
     * @since 0.3.1
     */
    hex: string;
}
/**
 * @since 0.3.1
 */
export interface ConvertNumberToHexOptions {
    /**
     * The number to convert to a hex string.
     *
     * @since 0.3.1
     */
    number: number;
}
/**
 * @since 0.0.1
 */
export interface ConvertStringToBytesOptions {
    /**
     * The string to convert to a byte array.
     *
     * @since 0.0.1
     */
    text: string;
}
/**
 * @since 0.0.1
 */
export interface CreateNdefRecordOptions {
    /**
     * @since 0.0.1
     */
    id?: number[] | string;
    /**
     * @since 0.0.1
     */
    payload?: number[] | string;
    /**
     * @since 0.0.1
     */
    tnf?: TypeNameFormat;
    /**
     * @since 0.0.1
     */
    type?: number[] | string | RecordTypeDefinition;
}
/**
 * @since 0.0.1
 */
export interface CreateNdefRecordResult {
    /**
     * @since 0.0.1
     */
    record: NdefRecord;
}
/**
 * @since 0.0.1
 */
export interface CreateNdefTextRecordOptions {
    /**
     * @since 0.0.1
     */
    id?: number[] | string;
    /**
     * @since 0.0.1
     */
    text: number[] | string;
    /**
     * The ISO/IANA language identifier.
     *
     * @default en
     * @since 0.0.1
     */
    language?: number[] | string;
}
/**
 * @since 0.0.1
 */
export interface CreateNdefUriRecordOptions {
    /**
     * @since 0.0.1
     */
    id?: number[] | string;
    /**
     * @since 0.0.1
     * @example capacitorjs.com
     */
    uri: number[] | string;
    /**
     * The URI identifier code.
     *
     * @default UriIdentifierCode.None
     * @since 0.3.1
     */
    identifierCode?: UriIdentifierCode | number;
}
/**
 * @since 0.0.1
 */
export interface CreateNdefAbsoluteUriRecordOptions {
    /**
     * @since 0.0.1
     */
    id?: number[] | string;
    /**
     * @since 0.0.1
     */
    uri: number[] | string;
}
/**
 * @since 0.0.1
 */
export interface CreateNdefMimeMediaRecordOptions {
    /**
     * @since 0.0.1
     */
    id?: number[] | string;
    /**
     * A valid MIME type.
     *
     * @example application/json
     * @since 0.0.1
     */
    mimeType: number[] | string;
    /**
     * The MIME data as bytes or string.
     *
     * @since 0.0.1
     */
    mimeData?: number[] | string;
}
/**
 * @since 0.0.1
 */
export interface CreateNdefExternalRecordOptions {
    /**
     * @since 0.0.1
     */
    id?: number[] | string;
    /**
     * @since 0.0.1
     */
    payload?: number[] | string;
    /**
     * The domain-name of issuing organization.
     *
     * @example com.example
     * @since 0.0.1
     */
    domain: number[] | string;
    /**
     * The domain-specific type of data.
     *
     * @example externalType
     * @since 0.0.1
     */
    type: number[] | string;
}
/**
 * @since 0.3.1
 */
export interface GetIdentifierCodeFromNdefUriRecordOptions {
    /**
     * @since 0.3.1
     */
    record: NdefRecord;
}
/**
 * @since 0.0.1
 */
export interface GetLanguageFromNdefTextRecordOptions {
    /**
     * @since 0.0.1
     */
    record: NdefRecord;
}
/**
 * @since 0.0.1
 */
export interface GetTextFromNdefTextRecordOptions {
    /**
     * @since 0.0.1
     */
    record: NdefRecord;
}
/**
 * @since 0.2.0
 */
export declare enum PollingOption {
    /**
     * The option for detecting ISO 7816-compatible and MIFARE tags.
     *
     * @since 0.2.0
     */
    iso14443 = "iso14443",
    /**
     * The option for detecting ISO 15693 tags.
     *
     * @since 0.2.0
     */
    iso15693 = "iso15693",
    /**
     * The option for detecting FeliCa tags.
     *
     * @since 0.2.0
     */
    iso18092 = "iso18092"
}
/**
 * @since 0.3.0
 */
export declare enum Iso15693RequestFlag {
    /**
     * @since 0.3.0
     */
    address = "address",
    /**
     * @since 0.3.0
     */
    commandSpecificBit8 = "commandSpecificBit8",
    /**
     * @since 0.3.0
     */
    dualSubCarriers = "dualSubCarriers",
    /**
     * @since 0.3.0
     */
    highDataRate = "highDataRate",
    /**
     * @since 0.3.0
     */
    option = "option",
    /**
     * @since 0.3.0
     */
    protocolExtension = "protocolExtension",
    /**
     * @since 0.3.0
     */
    select = "select"
}
/**
 * @since 0.3.1
 *
 * URI identifier codes as defined in the NFC Forum URI Record Type Definition.
 */
export declare enum UriIdentifierCode {
    /**
     * No prepending is done.
     *
     * @since 0.3.1
     */
    None = 0,
    /**
     * `http://www.` is prepended to the URI.
     *
     * @since 0.3.1
     */
    HttpWww = 1,
    /**
     * `https://www.` is prepended to the URI.
     *
     * @since 0.3.1
     */
    HttpsWww = 2,
    /**
     * `http:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Http = 3,
    /**
     * `https:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Https = 4,
    /**
     * `tel:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Tel = 5,
    /**
     * `mailto:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Mailto = 6,
    /**
     * `ftp://anonymous:anonymous@` is prepended to the URI.
     *
     * @since 0.3.1
     */
    FtpAnonymous = 7,
    /**
     * `ftp://ftp.` is prepended to the URI.
     *
     * @since 0.3.1
     */
    FtpFtp = 8,
    /**
     * `ftps://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Ftps = 9,
    /**
     * `sftp://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Sftp = 10,
    /**
     * `smb://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Smb = 11,
    /**
     * `nfs://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Nfs = 12,
    /**
     * `ftp://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Ftp = 13,
    /**
     * `dav://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Dav = 14,
    /**
     * `news:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    News = 15,
    /**
     * `telnet://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Telnet = 16,
    /**
     * `imap:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Imap = 17,
    /**
     * `rtsp://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Rtsp = 18,
    /**
     * `urn:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Urn = 19,
    /**
     * `pop:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Pop = 20,
    /**
     * `sip:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Sip = 21,
    /**
     * `sips:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Sips = 22,
    /**
     * `tftp:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Tftp = 23,
    /**
     * `btspp://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Btspp = 24,
    /**
     * `btl2cap://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Btl2cap = 25,
    /**
     * `btgoep://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Btgoep = 26,
    /**
     * `tcpobex://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Tcpobex = 27,
    /**
     * `irdaobex://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    Irdaobex = 28,
    /**
     * `file://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    File = 29,
    /**
     * `urn:epc:id:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UrnEpcId = 30,
    /**
     * `urn:epc:tag:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UrnEpcTag = 31,
    /**
     * `urn:epc:pat:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UrnEpcPat = 32,
    /**
     * `urn:epc:raw:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UrnEpcRaw = 33,
    /**
     * `urn:epc:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UrnEpc = 34,
    /**
     * `urn:nfc:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UrnNfc = 35
}
