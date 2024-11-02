/**
 * Copyright (c) 2022 Robin Genz
 */
import Foundation
import Capacitor
import CoreNFC

@available(iOS 13.0, *)
@objc public class Nfc: NSObject {

    private let plugin: NfcPlugin
    private var session: NFCTagReaderSession?

    init(plugin: NfcPlugin) {
        self.plugin = plugin
    }

    public func startScanSession(pollingOption: NFCTagReaderSession.PollingOption, alertMessage: String?) {
        session = NFCTagReaderSession(pollingOption: pollingOption, delegate: self)
        session?.alertMessage = alertMessage ?? ""
        session?.begin()
    }

    public func stopScanSession(errorMessage: String?) {
        if let errorMessage = errorMessage {
            session?.invalidate(errorMessage: errorMessage)
        } else {
            session?.invalidate()
        }
    }

    public func write(message: NFCNDEFMessage, completion: @escaping (String?) -> Void) {
        guard let tag = session?.connectedTag else {
            completion(plugin.errorNoTagDetected)
            return
        }
        guard let ndefTag = NfcHelper.getNfcNdefTagFromNfcTag(tag: tag) else {
            completion(plugin.errorNdefNotSupported)
            return
        }
        ndefTag.writeNDEF(message, completionHandler: { error in
            if let error = error {
                completion(error.localizedDescription)
                return
            }
            completion(nil)
        })
    }

    public func makeReadOnly(completion: @escaping (String?) -> Void) {
        guard let tag = session?.connectedTag else {
            completion(plugin.errorNoTagDetected)
            return
        }
        guard let ndefTag = NfcHelper.getNfcNdefTagFromNfcTag(tag: tag) else {
            completion(plugin.errorNdefNotSupported)
            return
        }
        ndefTag.writeLock(completionHandler: { error in
            if let error = error {
                completion(error.localizedDescription)
                return
            }
            completion(nil)
        })
    }

    public func erase(completion: @escaping (String?) -> Void) {
        let ndefRecord = NfcHelper.createEmptyNdefRecord()
        let ndefMessage = NFCNDEFMessage(records: [ndefRecord])
        write(message: ndefMessage, completion: completion)
    }

    public func transceive(techType: String, call: CAPPluginCall, completion: @escaping ([UInt8]?, String?) -> Void) {
        guard let tag = session?.connectedTag else {
            completion(nil, plugin.errorNoTagDetected)
            return
        }
        switch techType {
        case "ISO_7816":
            guard let iso7816Tag = NfcHelper.getNfcIso7816TagFromNfcTag(tag: tag) else {
                completion(nil, plugin.errorTechTypeUnsupported)
                return
            }
            transceive(iso7816Tag: iso7816Tag, call: call, completion: completion)
            return
        case "NFC_F":
            guard let felicaTag = NfcHelper.getNfcFelicaTagFromNfcTag(tag: tag) else {
                completion(nil, plugin.errorTechTypeUnsupported)
                return
            }
            transceive(felicaTag: felicaTag, call: call, completion: completion)
            return
        case "NFC_V":
            guard let iso15693Tag = NfcHelper.getNfcIso15693TagFromNfcTag(tag: tag) else {
                completion(nil, plugin.errorTechTypeUnsupported)
                return
            }
            if #available(iOS 14.0, *) {
                transceive(iso15693Tag: iso15693Tag, call: call, completion: completion)
            } else {
                completion(nil, plugin.errorIos14Required)
            }
            return
        case "MIFARE_DESFIRE", "MIFARE_PLUS", "MIFARE_ULTRALIGHT":
            guard let mifareTag = NfcHelper.getNfcMifareTagFromNfcTag(tag: tag) else {
                completion(nil, plugin.errorTechTypeUnsupported)
                return
            }
            transceive(mifareTag: mifareTag, call: call, completion: completion)
            return
        default:
            completion(nil, plugin.errorTechTypeUnsupported)
        }
    }

    public func isSupported() -> Bool {
        return NFCTagReaderSession.readingAvailable
    }

    public func setAlertMessage(_ message: String) {
        session?.alertMessage = message
    }

    public func transceive(mifareTag: NFCMiFareTag, call: CAPPluginCall, completion: @escaping ([UInt8]?, String?) -> Void) {
        guard let data = call.getArray("data") as? [UInt8] else {
            completion(nil, plugin.errorDataMissing)
            return
        }
        let commandPacket = NfcHelper.convertJsonArrayToData(values: data)
        mifareTag.sendMiFareCommand(commandPacket: commandPacket, completionHandler: { data, error in
            if let error = error {
                completion(nil, error.localizedDescription)
                return
            }
            let response = NfcHelper.convertDataToJsonArray(data: data)
            completion(response, nil)
        })
    }

    public func transceive(felicaTag: NFCFeliCaTag, call: CAPPluginCall, completion: @escaping ([UInt8]?, String?) -> Void) {
        guard let data = call.getArray("data") as? [UInt8] else {
            completion(nil, plugin.errorDataMissing)
            return
        }
        let commandPacket = NfcHelper.convertJsonArrayToData(values: data)
        felicaTag.sendFeliCaCommand(commandPacket: commandPacket, completionHandler: { data, error in
            if let error = error {
                completion(nil, error.localizedDescription)
                return
            }
            let response = NfcHelper.convertDataToJsonArray(data: data)
            completion(response, nil)
        })
    }

    public func transceive(iso7816Tag: NFCISO7816Tag, call: CAPPluginCall, completion: @escaping ([UInt8]?, String?) -> Void) {
        guard let data = call.getArray("data") as? [UInt8] else {
            completion(nil, plugin.errorDataMissing)
            return
        }
        let commandPacket = NfcHelper.convertJsonArrayToData(values: data)
        guard let apdu = NFCISO7816APDU(data: commandPacket) else {
            completion(nil, plugin.errorDataInvalid)
            return
        }
        iso7816Tag.sendCommand(apdu: apdu, completionHandler: { data, _, _, error in
            if let error = error {
                completion(nil, error.localizedDescription)
                return
            }
            let response = NfcHelper.convertDataToJsonArray(data: data)
            completion(response, nil)
        })
    }

    @available(iOS 14.0, *)
    public func transceive(iso15693Tag: NFCISO15693Tag, call: CAPPluginCall, completion: @escaping ([UInt8]?, String?) -> Void) {
        guard let iso15693RequestFlags = call.getArray("iso15693RequestFlags") as? [String] else {
            completion(nil, plugin.errorRequestFlagsMissing)
            return
        }
        let requestFlags = NfcHelper.convertJsonArrayToNfcIso15693RequestFlags(values: iso15693RequestFlags)
        guard let iso15693CommandCode = call.getInt("iso15693CommandCode") else {
            completion(nil, plugin.errorCommandCodeMissing)
            return
        }
        guard let data = call.getArray("data") as? [UInt8] else {
            completion(nil, plugin.errorDataMissing)
            return
        }

        if iso15693CommandCode == 0x23 {
            guard data.count >= 2 else {
                completion(nil, plugin.errorBlockRangeMissing)
                return
            }

            let startBlock = Int(data[0])
            let numberOfBlocks = Int(data[1])
            let blockRange = NSRange(location: startBlock, length: numberOfBlocks)

            iso15693Tag.readMultipleBlocks(requestFlags: requestFlags, blockRange: blockRange) { (blocks, error) in
                if let error = error {
                    completion(nil, error.localizedDescription)
                    return
                }
                let response = blocks.flatMap { Array($0) }
                completion(response, nil)
            }
        } else {
            let customRequestParameters = NfcHelper.convertJsonArrayToData(values: data)
            iso15693Tag.customCommand(requestFlags: requestFlags, customCommandCode: iso15693CommandCode, customRequestParameters: customRequestParameters) { data, error in
                if let error = error {
                    completion(nil, error.localizedDescription)
                    return
                }
                let response = NfcHelper.convertDataToJsonArray(data: data)
                completion(response, nil)
            }
        }
    }
}

@available(iOS 13.0, *)
extension Nfc: NFCTagReaderSessionDelegate {

    public func tagReaderSessionDidBecomeActive(_ session: NFCTagReaderSession) {
        // no op
    }

    public func tagReaderSession(_ session: NFCTagReaderSession, didInvalidateWithError error: Error) {
        let message = error.localizedDescription
        if message.contains("Session invalidated by user") {
            self.plugin.notifyScanSessionCanceledListener()
        } else {
            self.plugin.notifyScanSessionErrorListener(message: message)
        }
    }

    public func tagReaderSession(_ session: NFCTagReaderSession, didDetect tags: [NFCTag]) {
        let firstTag = tags.first!
        session.connect(to: firstTag) { error in
            guard error == nil else {
                session.invalidate(errorMessage: "Could not connect to tag.")
                return
            }

            var status: NFCNDEFStatus?
            var ndefMessage: NFCNDEFMessage?
            var capacity: Int?

            let group = DispatchGroup()
            if let ndefTag = NfcHelper.getNfcNdefTagFromNfcTag(tag: firstTag) {
                group.enter()
                ndefTag.readNDEF(completionHandler: { ndefMessageResult, _ in
                    ndefMessage = ndefMessageResult
                    group.leave()
                })
                group.enter()
                ndefTag.queryNDEFStatus(completionHandler: { statusResult, capacityResult, _ in
                    status = statusResult
                    capacity = capacityResult
                    group.leave()
                })
            }

            group.notify(queue: .main) {
                let nfcTagResult = NfcHelper.createReadingResultForNdefTag(tag: firstTag, status: status, capacity: capacity, message: ndefMessage)
                self.plugin.notifyNfcTagScannedListener(nfcTag: nfcTagResult)
                // slow down polling, because iOS can read tags very fast,
                // so moving the tag away sometimes results in corrupted reads
                DispatchQueue.main.asyncAfter(deadline: .now() + 0.5) {
                    session.restartPolling()
                }
            }
        }
    }
}
