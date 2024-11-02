/**
 * Copyright (c) 2022 Robin Genz
 */
import Foundation
import Capacitor
import CoreNFC

@available(iOS 13.0, *)
public class NfcHelper {

    public static func createPollingOptionFromStringArray(values: [String]) -> NFCTagReaderSession.PollingOption {
        var pollingOption: NFCTagReaderSession.PollingOption = []
        if values.contains("iso14443") {
            pollingOption.insert(.iso14443)
        }
        if values.contains("iso15693") {
            pollingOption.insert(.iso15693)
        }
        if values.contains("iso18092") {
            pollingOption.insert(.iso18092)
        }
        return pollingOption
    }

    public static func createReadingResultForNdefTag(tag: NFCTag, status: NFCNDEFStatus?, capacity: Int?, message: NFCNDEFMessage?) -> JSObject {
        var result = JSObject()
        var techTypes: [String] = []

        if let status = status {
            result["isWritable"] = status == NFCNDEFStatus.readWrite
        }
        if let capacity = capacity {
            result["maxSize"] = capacity
        }
        if let message = message {
            result["message"] = NfcHelper.createJsonObjectFromNdefMessage(message: message)
        }

        if NfcHelper.getNfcNdefTagFromNfcTag(tag: tag) != nil {
            techTypes.append("NDEF")
        }

        if let felicaTag = NfcHelper.getNfcFelicaTagFromNfcTag(tag: tag) {
            techTypes.append("NFC_F")
            result["type"] = "NFC_FORUM_TYPE_3"
            result["manufacturer"] = convertDataToJsonArray(data: felicaTag.currentIDm)
            result["systemCode"] = convertDataToJsonArray(data: felicaTag.currentSystemCode)
        }

        if let iso7816Tag = NfcHelper.getNfcIso7816TagFromNfcTag(tag: tag) {
            result["id"] = [UInt8](iso7816Tag.identifier)
            if let applicationData = iso7816Tag.applicationData {
                result["applicationData"] = convertDataToJsonArray(data: applicationData)
            }
            if let historicalBytes = iso7816Tag.historicalBytes {
                result["historicalBytes"] = convertDataToJsonArray(data: historicalBytes)
            }
            techTypes.append("ISO_7816")
        }

        if let iso15693Tag = NfcHelper.getNfcIso15693TagFromNfcTag(tag: tag) {
            result["id"] = convertDataToJsonArray(data: iso15693Tag.identifier)
            techTypes.append("NFC_V")
        }

        if let mifareTag = NfcHelper.getNfcMifareTagFromNfcTag(tag: tag) {
            result["id"] = convertDataToJsonArray(data: mifareTag.identifier)
            if let historicalBytes = mifareTag.historicalBytes {
                result["historicalBytes"] = convertDataToJsonArray(data: historicalBytes)
            }
            switch mifareTag.mifareFamily {
            case .desfire:
                techTypes.append("MIFARE_DESFIRE")
                case .plus:
                techTypes.append("MIFARE_PLUS")
                case .ultralight:
                techTypes.append("MIFARE_ULTRALIGHT")
            case .unknown: break
            @unknown default: break
            }
        }

        result["techTypes"] = techTypes

        return result
    }

    public static func getNfcNdefTagFromNfcTag(tag: NFCTag) -> NFCNDEFTag? {
        switch tag {
        case .feliCa(let ndefTag):
            return ndefTag
        case .miFare(let ndefTag):
            return ndefTag
        case .iso7816(let ndefTag):
            return ndefTag
        case .iso15693(let ndefTag):
            return ndefTag
        @unknown default:
            return nil
        }
    }

    public static func getNfcFelicaTagFromNfcTag(tag: NFCTag) -> NFCFeliCaTag? {
        switch tag {
        case .feliCa(let felicaTag):
            return felicaTag
        case .miFare:
            return nil
        case .iso7816:
            return nil
        case .iso15693:
            return nil
        @unknown default:
            return nil
        }
    }

    public static func getNfcMifareTagFromNfcTag(tag: NFCTag) -> NFCMiFareTag? {
        switch tag {
        case .feliCa:
            return nil
        case .miFare(let mifareTag):
            return mifareTag
        case .iso7816:
            return nil
        case .iso15693:
            return nil
        @unknown default:
            return nil
        }
    }

    public static func getNfcIso7816TagFromNfcTag(tag: NFCTag) -> NFCISO7816Tag? {
        switch tag {
        case .feliCa:
            return nil
        case .miFare:
            return nil
        case .iso7816(let iso7816Tag):
            return iso7816Tag
        case .iso15693:
            return nil
        @unknown default:
            return nil
        }
    }

    public static func getNfcIso15693TagFromNfcTag(tag: NFCTag) -> NFCISO15693Tag? {
        switch tag {
        case .feliCa:
            return nil
        case .miFare:
            return nil
        case .iso7816:
            return nil
        case .iso15693(let iso15693Tag):
            return iso15693Tag
        @unknown default:
            return nil
        }
    }

    public static func createNdefMessageFromJsonObject(json: JSObject) -> NFCNDEFMessage {
        let records = json["records"] as? [JSObject] ?? []
        return NFCNDEFMessage(records: records.map {
            var format: NFCTypeNameFormat = NFCTypeNameFormat.unknown
            if let tnfValue = $0["tnf"] as? Int {
                format = NFCTypeNameFormat(rawValue: UInt8(tnfValue)) ?? NFCTypeNameFormat.unknown
            }
            var type: Data = Data()
            if let typeValue = $0["type"] as? [UInt8] {
                type = convertJsonArrayToData(values: typeValue)
            }
            var identifier: Data = Data()
            if let idValue = $0["id"] as? [UInt8] {
                identifier = convertJsonArrayToData(values: idValue)
            }
            var payload: Data = Data()
            if let payloadValue = $0["payload"] as? [UInt8] {
                payload = convertJsonArrayToData(values: payloadValue)
            }
            return NFCNDEFPayload(
                format: format,
                type: type,
                identifier: identifier,
                payload: payload
            )
        })
    }

    public static func convertDataToJsonArray(data: Data) -> [UInt8] {
        return [UInt8](data)
    }

    public static func convertJsonArrayToData(values: [UInt8]) -> Data {
        return Data(values.map { UInt8($0) })
    }

    @available(iOS 14.0, *)
    public static func convertJsonArrayToNfcIso15693RequestFlags(values: [String]) -> NFCISO15693RequestFlag {
        var flags: NFCISO15693RequestFlag = []
        if values.contains("address") {
            flags.insert(.address)
        }
        if values.contains("commandSpecificBit8") {
            flags.insert(.commandSpecificBit8)
        }
        if values.contains("dualSubCarriers") {
            flags.insert(.dualSubCarriers)
        }
        if values.contains("highDataRate") {
            flags.insert(.highDataRate)
        }
        if values.contains("option") {
            flags.insert(.option)
        }
        if values.contains("protocolExtension") {
            flags.insert(.protocolExtension)
        }
        if values.contains("select") {
            flags.insert(.select)
        }
        return flags
    }

    public static func createEmptyNdefRecord() -> NFCNDEFPayload {
        return NFCNDEFPayload(
            format: NFCTypeNameFormat.empty,
            type: Data(),
            identifier: Data(),
            payload: Data()
        )
    }

    private static func createJsonObjectFromNdefMessage(message: NFCNDEFMessage) -> JSObject {
        let records = NfcHelper.createJsonArrayFromNdefRecords(records: message.records)

        var result = JSObject()
        result["records"] = records
        return result
    }

    private static func createJsonArrayFromNdefRecords(records: [NFCNDEFPayload]) -> [JSObject] {
        return records.map { NfcHelper.createJsonObjectFromNdefRecord(record: $0) }
    }

    private static func createJsonObjectFromNdefRecord(record: NFCNDEFPayload) -> JSObject {
        var result = JSObject()
        result["id"] = [UInt8](record.identifier)
        result["type"] = [UInt8](record.type)
        result["payload"] = [UInt8](record.payload)
        result["tnf"] = record.typeNameFormat.rawValue as JSValue
        return result
    }
}
