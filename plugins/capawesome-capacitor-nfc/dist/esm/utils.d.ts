import type { ConvertBytesToHexOptions, ConvertBytesToStringOptions, ConvertHexToBytesOptions, ConvertHexToNumberOptions, ConvertNumberToHexOptions, ConvertStringToBytesOptions, CreateNdefAbsoluteUriRecordOptions, CreateNdefExternalRecordOptions, CreateNdefMimeMediaRecordOptions, CreateNdefRecordOptions, CreateNdefRecordResult, CreateNdefTextRecordOptions, CreateNdefUriRecordOptions, GetIdentifierCodeFromNdefUriRecordOptions, GetLanguageFromNdefTextRecordOptions, GetTextFromNdefTextRecordOptions, INfcUtils, NdefRecord } from './definitions';
import { UriIdentifierCode, RecordTypeDefinition } from './definitions';
export declare class NfcUtils implements INfcUtils {
    static readonly errorInvalidHexString = "Cannot convert an invalid hex string.";
    convertBytesToHex(options: ConvertBytesToHexOptions): {
        hex: string;
    };
    convertBytesToString(options: ConvertBytesToStringOptions): {
        text: string;
    };
    convertHexToBytes(options: ConvertHexToBytesOptions): {
        bytes: number[];
    };
    convertHexToNumber(options: ConvertHexToNumberOptions): {
        number: number;
    };
    convertNumberToHex(options: ConvertNumberToHexOptions): {
        hex: string;
    };
    convertStringToBytes(options: ConvertStringToBytesOptions): {
        bytes: number[];
    };
    createNdefRecord(options: CreateNdefRecordOptions): {
        record: NdefRecord;
    };
    createNdefEmptyRecord(): CreateNdefRecordResult;
    createNdefTextRecord(options: CreateNdefTextRecordOptions): CreateNdefRecordResult;
    createNdefUriRecord(options: CreateNdefUriRecordOptions): CreateNdefRecordResult;
    createNdefAbsoluteUriRecord(options: CreateNdefAbsoluteUriRecordOptions): CreateNdefRecordResult;
    createNdefMimeMediaRecord(options: CreateNdefMimeMediaRecordOptions): CreateNdefRecordResult;
    createNdefExternalRecord(options: CreateNdefExternalRecordOptions): CreateNdefRecordResult;
    getIdentifierCodeFromNdefUriRecord(options: GetIdentifierCodeFromNdefUriRecordOptions): {
        identifierCode: UriIdentifierCode | undefined;
    };
    getLanguageFromNdefTextRecord(options: GetLanguageFromNdefTextRecordOptions): {
        language: string | undefined;
    };
    getTextFromNdefTextRecord(options: GetTextFromNdefTextRecordOptions): {
        text: string | undefined;
    };
    getUriFromNdefUriRecord(options: GetIdentifierCodeFromNdefUriRecordOptions): {
        uri: string | undefined;
    };
    mapBytesToRecordTypeDefinition(options: {
        bytes: number[];
    }): {
        type: RecordTypeDefinition | undefined;
    };
    private createTextPayload;
}
