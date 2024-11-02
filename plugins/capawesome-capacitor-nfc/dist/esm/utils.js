import { UriIdentifierCode, RecordTypeDefinition, TypeNameFormat } from './definitions';
export class NfcUtils {
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
        tnf = options.tnf || TypeNameFormat.Empty;
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
            tnf: TypeNameFormat.Empty,
            type: [],
        });
    }
    createNdefTextRecord(options) {
        const language = options.language || 'en';
        const payload = this.createTextPayload(options.text, language);
        return this.createNdefRecord({
            id: options.id,
            payload,
            tnf: TypeNameFormat.WellKnown,
            type: RecordTypeDefinition.Text,
        });
    }
    createNdefUriRecord(options) {
        const identifierCode = options.identifierCode || UriIdentifierCode.None;
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
            tnf: TypeNameFormat.WellKnown,
            type: RecordTypeDefinition.Uri,
        });
    }
    createNdefAbsoluteUriRecord(options) {
        return this.createNdefRecord({
            id: options.id,
            payload: undefined,
            tnf: TypeNameFormat.AbsoluteUri,
            type: options.uri,
        });
    }
    createNdefMimeMediaRecord(options) {
        return this.createNdefRecord({
            id: options.id,
            payload: options.mimeData,
            tnf: TypeNameFormat.MimeMedia,
            type: options.mimeType,
        });
    }
    createNdefExternalRecord(options) {
        return this.createNdefRecord({
            id: options.id,
            payload: options.payload,
            tnf: TypeNameFormat.External,
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
                type = RecordTypeDefinition.Text;
            }
            else if (bytes[0] === 85) {
                type = RecordTypeDefinition.Uri;
            }
        }
        if (bytes.length === 2) {
            if (bytes[0] === 72 && bytes[1] === 99) {
                type = RecordTypeDefinition.HandoverCarrier;
            }
            else if (bytes[0] === 72 && bytes[1] === 114) {
                type = RecordTypeDefinition.HandoverRequest;
            }
            else if (bytes[0] === 72 && bytes[1] === 115) {
                type = RecordTypeDefinition.HandoverSelect;
            }
            else if (bytes[0] === 97 && bytes[1] === 99) {
                type = RecordTypeDefinition.AlternativeCarrier;
            }
            else if (bytes[0] === 83 && bytes[1] === 112) {
                type = RecordTypeDefinition.SmartPoster;
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
//# sourceMappingURL=utils.js.map