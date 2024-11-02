/**
 * @since 0.0.1
 */
export var TypeNameFormat;
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
})(TypeNameFormat || (TypeNameFormat = {}));
/**
 * @since 0.0.1
 */
export var RecordTypeDefinition;
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
})(RecordTypeDefinition || (RecordTypeDefinition = {}));
/**
 * @since 0.0.1
 */
export var NfcTagTechType;
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
})(NfcTagTechType || (NfcTagTechType = {}));
/**
 * @since 0.0.1
 */
export var NfcTagType;
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
})(NfcTagType || (NfcTagType = {}));
/**
 * @since 0.2.0
 */
export var PollingOption;
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
})(PollingOption || (PollingOption = {}));
/**
 * @since 0.3.0
 */
export var Iso15693RequestFlag;
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
})(Iso15693RequestFlag || (Iso15693RequestFlag = {}));
/**
 * @since 0.3.1
 *
 * URI identifier codes as defined in the NFC Forum URI Record Type Definition.
 */
export var UriIdentifierCode;
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
})(UriIdentifierCode || (UriIdentifierCode = {}));
//# sourceMappingURL=definitions.js.map