/**
 * Copyright (c) 2022 Robin Genz
 */
import Foundation
import Capacitor

@objc(NfcPlugin)
public class NfcPlugin: CAPPlugin {
    public let tag = "Nfc"
    public let nfcTagScannedEvent = "nfcTagScanned"
    public let scanSessionCanceledEvent = "scanSessionCanceled"
    public let scanSessionErrorEvent = "scanSessionError"
    public let errorNoTagDetected = "No NFC tag was detected."
    public let errorNdefNotSupported = "The NFC tag does not support NDEF tag technology."
    public let errorIosNotSupported = "This iOS version is not supported."
    public let errorTechTypeUnsupported = "The NFC tag does not support the selected techType."
    public let errorTechTypeMissing = "techType must be provided."
    public let errorDataMissing = "data must be provided."
    public let errorMessageMissing = "message must be provided."
    public let errorDataInvalid = "data must be a valid value."
    public let errorRequestFlagsMissing = "requestFlags must be provided."
    public let errorCommandCodeMissing = "commandCode must be provided."
    public let errorIos14Required = "This method is only available on iOS 14+"
    public let errorBlockRangeMissing = "Data does not contain enough information for block range"
    private var _implementation: Any?

    @available(iOS 13.0, *)
    fileprivate var implementation: Nfc? {
        // swiftlint:disable:next force_cast
        return _implementation as! Nfc?
    }

    override public func load() {
        if #available(iOS 13.0, *) {
            _implementation = Nfc(plugin: self)
        } else {
            print("\(tag): \(errorIosNotSupported)")
        }
    }

    @objc override public func checkPermissions(_ call: CAPPluginCall) {
        call.resolve([
            "nfc": "granted"
        ])
    }

    @objc override public func requestPermissions(_ call: CAPPluginCall) {
        call.resolve([
            "nfc": "granted"
        ])
    }

    @objc func startScanSession(_ call: CAPPluginCall) {
        if #available(iOS 13.0, *) {
            let alertMessage = call.getString("alertMessage", "")
            let pollingOptions = call.getArray("pollingOptions", String.self) ?? ["iso14443", "iso15693"]

            implementation?.startScanSession(pollingOption: NfcHelper.createPollingOptionFromStringArray(values: pollingOptions),
                                             alertMessage: alertMessage)
            call.resolve()
        } else {
            call.reject(errorIosNotSupported)
        }
    }

    @objc func stopScanSession(_ call: CAPPluginCall) {
        if #available(iOS 13.0, *) {
            let errorMessage = call.getString("errorMessage")

            implementation?.stopScanSession(errorMessage: errorMessage)
            call.resolve()
        } else {
            call.reject(errorIosNotSupported)
        }
    }

    @objc func write(_ call: CAPPluginCall) {
        if #available(iOS 13.0, *) {
            let jsonMessage = call.getObject("message", JSObject())
            let message = NfcHelper.createNdefMessageFromJsonObject(json: jsonMessage)

            implementation?.write(message: message, completion: { errorMessage in
                if let errorMessage = errorMessage {
                    call.reject(errorMessage)
                    return
                }
                call.resolve()
            })
        } else {
            call.reject(errorIosNotSupported)
        }
    }

    @objc func makeReadOnly(_ call: CAPPluginCall) {
        if #available(iOS 13.0, *) {
            implementation?.makeReadOnly(completion: { errorMessage in
                if let errorMessage = errorMessage {
                    call.reject(errorMessage)
                    return
                }
                call.resolve()
            })
        } else {
            call.reject(errorIosNotSupported)
        }
    }

    @objc func erase(_ call: CAPPluginCall) {
        if #available(iOS 13.0, *) {
            implementation?.erase(completion: { errorMessage in
                if let errorMessage = errorMessage {
                    call.reject(errorMessage)
                    return
                }
                call.resolve()
            })
        } else {
            call.reject(errorIosNotSupported)
        }
    }

    @objc func format(_ call: CAPPluginCall) {
        call.unimplemented()
    }

    @objc func transceive(_ call: CAPPluginCall) {
        if #available(iOS 13.0, *) {
            guard let techType = call.getString("techType") else {
                call.reject(errorTechTypeMissing)
                return
            }

            implementation?.transceive(techType: techType, call: call, completion: { response, errorMessage in
                if let errorMessage = errorMessage {
                    call.reject(errorMessage)
                    return
                }
                call.resolve([
                    "response": response ?? []
                ])
            })
        } else {
            call.reject(errorIosNotSupported)
        }
    }

    @objc func connect(_ call: CAPPluginCall) {
        call.unimplemented()
    }

    @objc func close(_ call: CAPPluginCall) {
        call.unimplemented()
    }

    @objc func isSupported(_ call: CAPPluginCall) {
        if #available(iOS 13.0, *) {
            let isSupported = implementation?.isSupported()
            call.resolve([
                "isSupported": isSupported ?? false
            ])
        } else {
            call.reject(errorIosNotSupported)
        }
    }

    @objc func isEnabled(_ call: CAPPluginCall) {
        call.unimplemented()
    }

    @objc func openSettings(_ call: CAPPluginCall) {
        call.unimplemented()
    }

    @objc func getAntennaInfo(_ call: CAPPluginCall) {
        call.unimplemented()
    }

    @objc func setAlertMessage(_ call: CAPPluginCall) {
        if #available(iOS 13.0, *) {
            guard let message = call.getString("message") else {
                call.reject(errorMessageMissing)
                return
            }

            implementation?.setAlertMessage(message)
            call.resolve()
        } else {
            call.reject(errorIosNotSupported)
        }
    }

    func notifyNfcTagScannedListener(nfcTag: JSObject) {
        var result = JSObject()
        result["nfcTag"] = nfcTag
        notifyListeners(nfcTagScannedEvent, data: result, retainUntilConsumed: true)
    }

    func notifyScanSessionCanceledListener() {
        notifyListeners(scanSessionCanceledEvent, data: nil)
    }

    func notifyScanSessionErrorListener(message: String) {
        var result = JSObject()
        result["message"] = message
        notifyListeners(scanSessionErrorEvent, data: result)
    }
}
