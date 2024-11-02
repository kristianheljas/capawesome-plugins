import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';
import { RecordTypeDefinition, TypeNameFormat } from './definitions';
import { NfcUtils } from './utils';
export class NfcWeb extends WebPlugin {
    constructor() {
        super(...arguments);
        this.TAG = 'NfcPlugin';
        this._isSupported = 'NDEFReader' in window;
        this.nfcUtils = new NfcUtils();
    }
    async startScanSession() {
        if (!this._isSupported) {
            throw this.createUnavailableException();
        }
        this.ndefReader = new NDEFReader();
        this.abortController = new AbortController();
        await this.ndefReader.scan({ signal: this.abortController.signal });
        this.ndefReader.onreading = (event) => this.handleNfcTagScannedEvent(event);
        this.ndefReader.onreadingerror = () => {
            this.handleScanSessionErrorEvent();
        };
    }
    async stopScanSession() {
        var _a;
        if (!this._isSupported) {
            throw this.createUnavailableException();
        }
        (_a = this.abortController) === null || _a === void 0 ? void 0 : _a.abort();
        this.ndefReader = undefined;
        this.abortController = undefined;
    }
    async write(options) {
        if (!this._isSupported) {
            throw this.createUnavailableException();
        }
        if (!this.ndefReader || !this.abortController) {
            throw this.createSessionMissingException();
        }
        const message = {
            records: options.message.records.map((record) => this.createNativeNDEFRecordInitFromNdefRecord(record)),
        };
        await this.ndefReader.write(message, { signal: this.abortController.signal, overwrite: true });
    }
    async makeReadOnly() {
        if (!this._isSupported || !('makeReadOnly' in NDEFReader.prototype)) {
            throw this.createUnavailableException();
        }
        if (!this.ndefReader || !this.abortController) {
            throw this.createSessionMissingException();
        }
        await this.ndefReader.makeReadOnly({ signal: this.abortController.signal });
    }
    async erase() {
        await this.write({ message: { records: [{ tnf: TypeNameFormat.Empty }] } });
    }
    async format() {
        throw this.createUnavailableException();
    }
    async transceive(_options) {
        throw this.createUnavailableException();
    }
    async connect(_options) {
        throw this.createUnavailableException();
    }
    async close() {
        throw this.createUnavailableException();
    }
    async isSupported() {
        return {
            isSupported: this._isSupported,
        };
    }
    async isEnabled() {
        throw this.createUnavailableException();
    }
    async openSettings() {
        throw this.createUnavailableException();
    }
    async getAntennaInfo() {
        throw this.createUnavailableException();
    }
    async setAlertMessage(_options) {
        throw this.createUnavailableException();
    }
    async checkPermissions() {
        if (!this._isSupported) {
            throw this.createUnavailableException();
        }
        const permissionName = 'nfc';
        const nfcPermissionStatus = await navigator.permissions.query({ name: permissionName });
        return { nfc: nfcPermissionStatus.state };
    }
    async requestPermissions() {
        if (!this._isSupported) {
            throw this.createUnavailableException();
        }
        try {
            const ndefReader = new NDEFReader();
            const abortController = new AbortController();
            await ndefReader.scan({ signal: abortController.signal });
            abortController.abort();
        }
        catch (error) {
            console.error(`${this.TAG} requestPermissions error.`, error);
        }
        return this.checkPermissions();
    }
    handleNfcTagScannedEvent(event) {
        const result = {
            nfcTag: this.createNfcTagResult(event),
        };
        this.notifyListeners(NfcWeb.nfcTagScannedEvent, result);
    }
    handleScanSessionErrorEvent() {
        const result = {
            message: 'An unknown error has occurred.',
        };
        this.notifyListeners(NfcWeb.scanSessionErrorEvent, result);
    }
    createNfcTagResult(event) {
        const serialNumber = event.serialNumber ? event.serialNumber.split(':').join('') : undefined;
        const id = serialNumber ? this.convertHexToBytes(serialNumber) : undefined;
        const nfcTag = {
            id,
            message: this.createNdefMessageResult(event.message),
        };
        return nfcTag;
    }
    createNdefMessageResult(message) {
        const records = message.records.map((record) => this.createNdefRecordResultFromNativeNDEFRecord(record));
        const result = {
            records,
        };
        return result;
    }
    createNdefRecordResultFromNativeNDEFRecord(record) {
        const id = record.id ? this.convertStringToBytes(record.id) : undefined;
        const data = record.data ? Array.from(new Uint8Array(record.data.buffer)) : undefined;
        const language = record.lang ? record.lang : undefined;
        const tnf = this.mapNativeRecordTypeToTypeNameFormat(record.recordType);
        let payload = undefined;
        let type = undefined;
        switch (record.recordType) {
            case 'empty':
                return this.nfcUtils.createNdefEmptyRecord().record;
            case 'text':
                return this.nfcUtils.createNdefTextRecord({
                    id,
                    text: data || [],
                    language,
                }).record;
            case 'url':
                return this.nfcUtils.createNdefUriRecord({
                    id,
                    uri: data || [],
                }).record;
            case 'smart-poster':
                type = this.convertStringToBytes(RecordTypeDefinition.SmartPoster);
                payload = data;
                break;
            case 'mime':
                return this.nfcUtils.createNdefMimeMediaRecord({
                    id,
                    mimeType: record.mediaType || '',
                    mimeData: data || [],
                }).record;
            case 'absolute-url':
                return this.nfcUtils.createNdefAbsoluteUriRecord({
                    id,
                    uri: data || [],
                }).record;
            case 'unknown':
                type = undefined;
                payload = data;
                break;
            default: {
                if (record.recordType.startsWith(':')) {
                    type = this.convertStringToBytes(record.recordType.substring(1));
                }
                else {
                    type = this.convertStringToBytes(record.recordType);
                }
                payload = data;
            }
        }
        const result = {
            id,
            payload,
            tnf,
            type,
        };
        return result;
    }
    createNativeNDEFRecordInitFromNdefRecord(record) {
        const id = record.id ? this.convertBytesToString(record.id) : undefined;
        const type = record.type ? this.convertBytesToString(record.type) : undefined;
        const payload = record.payload ? new Uint8Array(record.payload) : undefined;
        let data;
        let recordType;
        let mediaType;
        let lang;
        switch (record.tnf) {
            case TypeNameFormat.Empty:
                recordType = 'empty';
                break;
            case TypeNameFormat.WellKnown: {
                if (type === RecordTypeDefinition.Text) {
                    recordType = 'text';
                    lang = this.nfcUtils.getLanguageFromNdefTextRecord({ record }).language;
                    const text = this.nfcUtils.getTextFromNdefTextRecord({ record }).text;
                    const textAsBytes = text ? this.convertStringToBytes(text) : [];
                    data = new Uint8Array(textAsBytes);
                }
                else {
                    if (type === RecordTypeDefinition.Uri) {
                        recordType = 'url';
                    }
                    else if (type === RecordTypeDefinition.SmartPoster) {
                        recordType = 'smart-poster';
                    }
                    else {
                        recordType = type ? ':' + type : 'unknown';
                    }
                    data = payload;
                }
                break;
            }
            case TypeNameFormat.MimeMedia: {
                recordType = 'mime';
                mediaType = type;
                data = payload;
                break;
            }
            case TypeNameFormat.AbsoluteUri: {
                recordType = 'absolute-url';
                const textEncoder = new TextEncoder();
                const uint8Array = textEncoder.encode(type);
                data = new DataView(uint8Array);
                break;
            }
            case TypeNameFormat.External: {
                recordType = type || 'unknown';
                data = payload;
                break;
            }
            default:
                recordType = 'unknown';
        }
        const result = {
            id,
            data,
            recordType,
            mediaType,
            lang,
        };
        return result;
    }
    mapNativeRecordTypeToTypeNameFormat(recordType) {
        switch (recordType) {
            case 'empty':
                return TypeNameFormat.Empty;
            case 'text':
            case 'url':
            case 'smart-poster':
                return TypeNameFormat.WellKnown;
            case 'mime':
                return TypeNameFormat.MimeMedia;
            case 'absolute-url':
                return TypeNameFormat.AbsoluteUri;
            case 'unknown':
                return TypeNameFormat.Unknown;
            default: {
                if (recordType.startsWith(':')) {
                    return TypeNameFormat.WellKnown;
                }
                return TypeNameFormat.External;
            }
        }
    }
    convertStringToBytes(text) {
        return this.nfcUtils.convertStringToBytes({
            text,
        }).bytes;
    }
    convertBytesToString(bytes) {
        return this.nfcUtils.convertBytesToString({
            bytes,
        }).text;
    }
    convertHexToBytes(hex) {
        if (hex.length % 2 !== 0) {
            throw new Error(NfcUtils.errorInvalidHexString);
        }
        return this.nfcUtils.convertHexToBytes({
            hex,
            separator: '',
            start: '',
        }).bytes;
    }
    createUnavailableException() {
        return new CapacitorException('This NFC plugin method is not available on this platform.', ExceptionCode.Unavailable);
    }
    createSessionMissingException() {
        return new CapacitorException('There is currently no NFC session.');
    }
}
NfcWeb.nfcTagScannedEvent = 'nfcTagScanned';
NfcWeb.scanSessionErrorEvent = 'scanSessionError';
//# sourceMappingURL=web.js.map