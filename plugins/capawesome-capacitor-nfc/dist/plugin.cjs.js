'use strict';

Object.defineProperty(exports, '__esModule', { value: true });

var core = require('@capacitor/core');

/**
 * @since 0.0.1
 */
exports.TypeNameFormat = void 0;
(function (TypeNameFormat) {
    /**
     * An empty record with no type or payload.
     *
     * @since 0.0.1
     */
    TypeNameFormat[TypeNameFormat["Empty"] = 0] = "Empty";
    /**
     * A predefined type defined in the RTD specification of the NFC Forum.
     *
     * @since 0.0.1
     */
    TypeNameFormat[TypeNameFormat["WellKnown"] = 1] = "WellKnown";
    /**
     * An Internet media type as defined in RFC 2046.
     *
     * @since 0.0.1
     */
    TypeNameFormat[TypeNameFormat["MimeMedia"] = 2] = "MimeMedia";
    /**
     * A URI as defined in RFC 3986.
     *
     * @since 0.0.1
     */
    TypeNameFormat[TypeNameFormat["AbsoluteUri"] = 3] = "AbsoluteUri";
    /**
     * A user-defined value that is based on the rules of the
     * NFC Forum Record Type Definition specification.
     *
     * @since 0.0.1
     */
    TypeNameFormat[TypeNameFormat["External"] = 4] = "External";
    /**
     * Type is unknown.
     *
     * @since 0.0.1
     */
    TypeNameFormat[TypeNameFormat["Unknown"] = 5] = "Unknown";
    /**
     * Indicates the payload is an intermediate or final chunk
     * of a chunked NDEF Record.
     *
     * @since 0.0.1
     */
    TypeNameFormat[TypeNameFormat["Unchanged"] = 6] = "Unchanged";
})(exports.TypeNameFormat || (exports.TypeNameFormat = {}));
/**
 * @since 0.0.1
 */
exports.RecordTypeDefinition = void 0;
(function (RecordTypeDefinition) {
    /**
     * @since 0.0.1
     */
    RecordTypeDefinition["AndroidApp"] = "android.com:pkg";
    /**
     * @since 0.0.1
     */
    RecordTypeDefinition["AlternativeCarrier"] = "ac";
    /**
     * @since 0.0.1
     */
    RecordTypeDefinition["HandoverCarrier"] = "Hc";
    /**
     * @since 0.0.1
     */
    RecordTypeDefinition["HandoverRequest"] = "Hr";
    /**
     * @since 0.0.1
     */
    RecordTypeDefinition["HandoverSelect"] = "Hs";
    /**
     * @since 0.0.1
     */
    RecordTypeDefinition["SmartPoster"] = "Sp";
    /**
     * @since 0.0.1
     */
    RecordTypeDefinition["Text"] = "T";
    /**
     * @since 0.0.1
     */
    RecordTypeDefinition["Uri"] = "U";
})(exports.RecordTypeDefinition || (exports.RecordTypeDefinition = {}));
/**
 * @since 0.0.1
 */
exports.NfcTagTechType = void 0;
(function (NfcTagTechType) {
    /**
     * The NFC-A (ISO 14443-3A) tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagTechType["NfcA"] = "NFC_A";
    /**
     * The NFC-B (ISO 14443-3B) tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagTechType["NfcB"] = "NFC_B";
    /**
     * The NFC-F (JIS 6319-4) tag technology.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    NfcTagTechType["NfcF"] = "NFC_F";
    /**
     * The NFC-V (ISO 15693) tag technology.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    NfcTagTechType["NfcV"] = "NFC_V";
    /**
     * The ISO-DEP (ISO 14443-4) tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagTechType["IsoDep"] = "ISO_DEP";
    /**
     * The ISO 7816 tag technology.
     *
     * Only available on iOS.
     *
     * @since 5.1.0
     */
    NfcTagTechType["Iso7816"] = "ISO_7816";
    /**
     * The NDEF tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagTechType["Ndef"] = "NDEF";
    /**
     * The MIFARE Classic tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagTechType["MifareClassic"] = "MIFARE_CLASSIC";
    /**
     * The MIFARE Desfire tag technology.
     *
     * Only available on iOS.
     *
     * @since 5.1.0
     */
    NfcTagTechType["MifareDesfire"] = "MIFARE_DESFIRE";
    /**
     * The MIFARE Plus tag technology.
     *
     * Only available on iOS.
     *
     * @since 5.1.0
     */
    NfcTagTechType["MifarePlus"] = "MIFARE_PLUS";
    /**
     * The MIFARE Ultralight tag technology.
     *
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    NfcTagTechType["MifareUltralight"] = "MIFARE_ULTRALIGHT";
    /**
     * The technology of a tag containing just a barcode.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagTechType["NfcBarcode"] = "NFC_BARCODE";
    /**
     * The NDEF formatable tag technology.
     *
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagTechType["NdefFormatable"] = "NDEF_FORMATABLE";
})(exports.NfcTagTechType || (exports.NfcTagTechType = {}));
/**
 * @since 0.0.1
 */
exports.NfcTagType = void 0;
(function (NfcTagType) {
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagType["NfcForumType1"] = "NFC_FORUM_TYPE_1";
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagType["NfcForumType2"] = "NFC_FORUM_TYPE_2";
    /**
     * Only available on Android and iOS.
     *
     * @since 0.0.1
     */
    NfcTagType["NfcForumType3"] = "NFC_FORUM_TYPE_3";
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagType["NfcForumType4"] = "NFC_FORUM_TYPE_4";
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagType["MifareClassic"] = "MIFARE_CLASSIC";
    /**
     * @since 0.0.1
     * @deprecated Not supported on any platform.
     */
    NfcTagType["MifareDesfire"] = "MIFARE_DESFIRE";
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagType["MifarePlus"] = "MIFARE_PLUS";
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagType["MifarePro"] = "MIFARE_PRO";
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagType["MifareUltralight"] = "MIFARE_ULTRALIGHT";
    /**
     * Only available on Android.
     *
     * @since 0.0.1
     */
    NfcTagType["MifareUltralightC"] = "MIFARE_ULTRALIGHT_C";
})(exports.NfcTagType || (exports.NfcTagType = {}));
/**
 * @since 0.2.0
 */
exports.PollingOption = void 0;
(function (PollingOption) {
    /**
     * The option for detecting ISO 7816-compatible and MIFARE tags.
     *
     * @since 0.2.0
     */
    PollingOption["iso14443"] = "iso14443";
    /**
     * The option for detecting ISO 15693 tags.
     *
     * @since 0.2.0
     */
    PollingOption["iso15693"] = "iso15693";
    /**
     * The option for detecting FeliCa tags.
     *
     * @since 0.2.0
     */
    PollingOption["iso18092"] = "iso18092";
})(exports.PollingOption || (exports.PollingOption = {}));
/**
 * @since 0.3.0
 */
exports.Iso15693RequestFlag = void 0;
(function (Iso15693RequestFlag) {
    /**
     * @since 0.3.0
     */
    Iso15693RequestFlag["address"] = "address";
    /**
     * @since 0.3.0
     */
    Iso15693RequestFlag["commandSpecificBit8"] = "commandSpecificBit8";
    /**
     * @since 0.3.0
     */
    Iso15693RequestFlag["dualSubCarriers"] = "dualSubCarriers";
    /**
     * @since 0.3.0
     */
    Iso15693RequestFlag["highDataRate"] = "highDataRate";
    /**
     * @since 0.3.0
     */
    Iso15693RequestFlag["option"] = "option";
    /**
     * @since 0.3.0
     */
    Iso15693RequestFlag["protocolExtension"] = "protocolExtension";
    /**
     * @since 0.3.0
     */
    Iso15693RequestFlag["select"] = "select";
})(exports.Iso15693RequestFlag || (exports.Iso15693RequestFlag = {}));
/**
 * @since 0.3.1
 *
 * URI identifier codes as defined in the NFC Forum URI Record Type Definition.
 */
exports.UriIdentifierCode = void 0;
(function (UriIdentifierCode) {
    /**
     * No prepending is done.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["None"] = 0] = "None";
    /**
     * `http://www.` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["HttpWww"] = 1] = "HttpWww";
    /**
     * `https://www.` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["HttpsWww"] = 2] = "HttpsWww";
    /**
     * `http:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Http"] = 3] = "Http";
    /**
     * `https:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Https"] = 4] = "Https";
    /**
     * `tel:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Tel"] = 5] = "Tel";
    /**
     * `mailto:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Mailto"] = 6] = "Mailto";
    /**
     * `ftp://anonymous:anonymous@` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["FtpAnonymous"] = 7] = "FtpAnonymous";
    /**
     * `ftp://ftp.` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["FtpFtp"] = 8] = "FtpFtp";
    /**
     * `ftps://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Ftps"] = 9] = "Ftps";
    /**
     * `sftp://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Sftp"] = 10] = "Sftp";
    /**
     * `smb://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Smb"] = 11] = "Smb";
    /**
     * `nfs://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Nfs"] = 12] = "Nfs";
    /**
     * `ftp://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Ftp"] = 13] = "Ftp";
    /**
     * `dav://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Dav"] = 14] = "Dav";
    /**
     * `news:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["News"] = 15] = "News";
    /**
     * `telnet://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Telnet"] = 16] = "Telnet";
    /**
     * `imap:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Imap"] = 17] = "Imap";
    /**
     * `rtsp://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Rtsp"] = 18] = "Rtsp";
    /**
     * `urn:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Urn"] = 19] = "Urn";
    /**
     * `pop:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Pop"] = 20] = "Pop";
    /**
     * `sip:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Sip"] = 21] = "Sip";
    /**
     * `sips:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Sips"] = 22] = "Sips";
    /**
     * `tftp:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Tftp"] = 23] = "Tftp";
    /**
     * `btspp://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Btspp"] = 24] = "Btspp";
    /**
     * `btl2cap://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Btl2cap"] = 25] = "Btl2cap";
    /**
     * `btgoep://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Btgoep"] = 26] = "Btgoep";
    /**
     * `tcpobex://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Tcpobex"] = 27] = "Tcpobex";
    /**
     * `irdaobex://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["Irdaobex"] = 28] = "Irdaobex";
    /**
     * `file://` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["File"] = 29] = "File";
    /**
     * `urn:epc:id:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["UrnEpcId"] = 30] = "UrnEpcId";
    /**
     * `urn:epc:tag:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["UrnEpcTag"] = 31] = "UrnEpcTag";
    /**
     * `urn:epc:pat:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["UrnEpcPat"] = 32] = "UrnEpcPat";
    /**
     * `urn:epc:raw:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["UrnEpcRaw"] = 33] = "UrnEpcRaw";
    /**
     * `urn:epc:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["UrnEpc"] = 34] = "UrnEpc";
    /**
     * `urn:nfc:` is prepended to the URI.
     *
     * @since 0.3.1
     */
    UriIdentifierCode[UriIdentifierCode["UrnNfc"] = 35] = "UrnNfc";
})(exports.UriIdentifierCode || (exports.UriIdentifierCode = {}));

class NfcUtils {
    convertBytesToHex(options) {
        const start = options.start === undefined ? '0x' : options.start;
        const separator = options.separator === undefined ? '' : options.separator;
        const hexArray = [];
        for (const byte of options.bytes) {
            const hexItem = ('0' + (byte & 0xff).toString(16)).slice(-2).toUpperCase();
            hexArray.push(hexItem);
        }
        const hex = start + hexArray.join(separator);
        return { hex };
    }
    convertBytesToString(options) {
        const bytes = options.bytes;
        const textDecoder = new TextDecoder();
        const uint8Array = new Uint8Array(bytes);
        const text = textDecoder.decode(uint8Array);
        return { text };
    }
    convertHexToBytes(options) {
        const start = options.start === undefined ? '0x' : options.start;
        const separator = options.separator === undefined ? '' : options.separator;
        let hex = options.hex.replace(start, '');
        hex = hex.split(separator).join('');
        const bytes = [];
        for (let i = 0; i < hex.length; i += 2) {
            bytes.push(parseInt(hex.substr(i, 2), 16));
        }
        return { bytes };
    }
    convertHexToNumber(options) {
        const hex = options.hex;
        const number = parseInt(hex, 16);
        return { number };
    }
    convertNumberToHex(options) {
        const number = options.number;
        const hex = number.toString(16);
        return { hex };
    }
    convertStringToBytes(options) {
        const text = options.text;
        const textEncoder = new TextEncoder();
        const uint8Array = textEncoder.encode(text);
        const bytes = Array.from(uint8Array);
        return { bytes };
    }
    createNdefRecord(options) {
        let { id, payload, tnf, type } = options;
        if (id === undefined) {
            id = [];
        }
        else if (typeof id === 'string') {
            id = this.convertStringToBytes({ text: id }).bytes;
        }
        if (payload === undefined) {
            payload = [];
        }
        else if (typeof payload === 'string') {
            payload = this.convertStringToBytes({ text: payload }).bytes;
        }
        tnf = options.tnf || exports.TypeNameFormat.Empty;
        if (type === undefined) {
            type = [];
        }
        else if (typeof type === 'string') {
            type = this.convertStringToBytes({ text: type }).bytes;
        }
        const record = {
            id,
            payload,
            tnf,
            type,
        };
        return { record };
    }
    createNdefEmptyRecord() {
        return this.createNdefRecord({
            id: [],
            payload: [],
            tnf: exports.TypeNameFormat.Empty,
            type: [],
        });
    }
    createNdefTextRecord(options) {
        const language = options.language || 'en';
        const payload = this.createTextPayload(options.text, language);
        return this.createNdefRecord({
            id: options.id,
            payload,
            tnf: exports.TypeNameFormat.WellKnown,
            type: exports.RecordTypeDefinition.Text,
        });
    }
    createNdefUriRecord(options) {
        const identifierCode = options.identifierCode || exports.UriIdentifierCode.None;
        let uriAsBytes;
        if (typeof options.uri === 'string') {
            uriAsBytes = this.convertStringToBytes({ text: options.uri }).bytes;
        }
        else {
            uriAsBytes = options.uri;
        }
        const payload = [identifierCode, ...uriAsBytes];
        return this.createNdefRecord({
            id: options.id,
            payload,
            tnf: exports.TypeNameFormat.WellKnown,
            type: exports.RecordTypeDefinition.Uri,
        });
    }
    createNdefAbsoluteUriRecord(options) {
        return this.createNdefRecord({
            id: options.id,
            payload: undefined,
            tnf: exports.TypeNameFormat.AbsoluteUri,
            type: options.uri,
        });
    }
    createNdefMimeMediaRecord(options) {
        return this.createNdefRecord({
            id: options.id,
            payload: options.mimeData,
            tnf: exports.TypeNameFormat.MimeMedia,
            type: options.mimeType,
        });
    }
    createNdefExternalRecord(options) {
        return this.createNdefRecord({
            id: options.id,
            payload: options.payload,
            tnf: exports.TypeNameFormat.External,
            type: `${options.domain}:${options.type}`,
        });
    }
    getIdentifierCodeFromNdefUriRecord(options) {
        const record = options.record;
        const firstByte = record.payload ? record.payload[0] : undefined;
        return { identifierCode: firstByte };
    }
    getLanguageFromNdefTextRecord(options) {
        const record = options.record;
        const payload = record.payload;
        if (!payload) {
            return { language: undefined };
        }
        const languageIdentifierLength = payload && payload.length > 0 ? payload[0] : 0;
        const language = this.convertBytesToString({
            bytes: payload,
        }).text.substring(1, languageIdentifierLength + 1);
        return { language };
    }
    getTextFromNdefTextRecord(options) {
        const record = options.record;
        const payload = record.payload;
        if (!payload) {
            return { text: undefined };
        }
        const languageIdentifierLength = payload && payload.length > 0 ? payload[0] : 0;
        const text = this.convertBytesToString({
            bytes: payload,
        }).text.substring(languageIdentifierLength + 1);
        return { text };
    }
    getUriFromNdefUriRecord(options) {
        const record = options.record;
        const payload = record.payload;
        if (!payload) {
            return { uri: undefined };
        }
        const uri = this.convertBytesToString({
            bytes: payload.slice(1),
        }).text;
        return { uri };
    }
    mapBytesToRecordTypeDefinition(options) {
        let type = undefined;
        const { bytes } = options;
        if (bytes.length === 1) {
            if (bytes[0] === 84) {
                type = exports.RecordTypeDefinition.Text;
            }
            else if (bytes[0] === 85) {
                type = exports.RecordTypeDefinition.Uri;
            }
        }
        if (bytes.length === 2) {
            if (bytes[0] === 72 && bytes[1] === 99) {
                type = exports.RecordTypeDefinition.HandoverCarrier;
            }
            else if (bytes[0] === 72 && bytes[1] === 114) {
                type = exports.RecordTypeDefinition.HandoverRequest;
            }
            else if (bytes[0] === 72 && bytes[1] === 115) {
                type = exports.RecordTypeDefinition.HandoverSelect;
            }
            else if (bytes[0] === 97 && bytes[1] === 99) {
                type = exports.RecordTypeDefinition.AlternativeCarrier;
            }
            else if (bytes[0] === 83 && bytes[1] === 112) {
                type = exports.RecordTypeDefinition.SmartPoster;
            }
        }
        return { type };
    }
    createTextPayload(text, language) {
        language = language || 'en';
        const textAsbytes = Array.isArray(text)
            ? text
            : this.convertStringToBytes({
                text: text,
            }).bytes;
        const languageAsbytes = Array.isArray(language)
            ? language
            : this.convertStringToBytes({
                text: language,
            }).bytes;
        const payload = [...languageAsbytes, ...textAsbytes];
        payload.unshift(language.length);
        return payload;
    }
}
NfcUtils.errorInvalidHexString = 'Cannot convert an invalid hex string.';

const Nfc = core.registerPlugin('Nfc', {
    web: () => Promise.resolve().then(function () { return web; }).then((m) => new m.NfcWeb()),
});

class NfcWeb extends core.WebPlugin {
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
        await this.write({ message: { records: [{ tnf: exports.TypeNameFormat.Empty }] } });
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
                type = this.convertStringToBytes(exports.RecordTypeDefinition.SmartPoster);
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
            case exports.TypeNameFormat.Empty:
                recordType = 'empty';
                break;
            case exports.TypeNameFormat.WellKnown: {
                if (type === exports.RecordTypeDefinition.Text) {
                    recordType = 'text';
                    lang = this.nfcUtils.getLanguageFromNdefTextRecord({ record }).language;
                    const text = this.nfcUtils.getTextFromNdefTextRecord({ record }).text;
                    const textAsBytes = text ? this.convertStringToBytes(text) : [];
                    data = new Uint8Array(textAsBytes);
                }
                else {
                    if (type === exports.RecordTypeDefinition.Uri) {
                        recordType = 'url';
                    }
                    else if (type === exports.RecordTypeDefinition.SmartPoster) {
                        recordType = 'smart-poster';
                    }
                    else {
                        recordType = type ? ':' + type : 'unknown';
                    }
                    data = payload;
                }
                break;
            }
            case exports.TypeNameFormat.MimeMedia: {
                recordType = 'mime';
                mediaType = type;
                data = payload;
                break;
            }
            case exports.TypeNameFormat.AbsoluteUri: {
                recordType = 'absolute-url';
                const textEncoder = new TextEncoder();
                const uint8Array = textEncoder.encode(type);
                data = new DataView(uint8Array);
                break;
            }
            case exports.TypeNameFormat.External: {
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
                return exports.TypeNameFormat.Empty;
            case 'text':
            case 'url':
            case 'smart-poster':
                return exports.TypeNameFormat.WellKnown;
            case 'mime':
                return exports.TypeNameFormat.MimeMedia;
            case 'absolute-url':
                return exports.TypeNameFormat.AbsoluteUri;
            case 'unknown':
                return exports.TypeNameFormat.Unknown;
            default: {
                if (recordType.startsWith(':')) {
                    return exports.TypeNameFormat.WellKnown;
                }
                return exports.TypeNameFormat.External;
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
        return new core.CapacitorException('This NFC plugin method is not available on this platform.', core.ExceptionCode.Unavailable);
    }
    createSessionMissingException() {
        return new core.CapacitorException('There is currently no NFC session.');
    }
}
NfcWeb.nfcTagScannedEvent = 'nfcTagScanned';
NfcWeb.scanSessionErrorEvent = 'scanSessionError';

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    NfcWeb: NfcWeb
});

exports.Nfc = Nfc;
exports.NfcUtils = NfcUtils;
//# sourceMappingURL=plugin.cjs.js.map
