import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(ZipPlugin)
public class ZipPlugin: CAPPlugin {
    public static let tag = "Zip"

    public let errorSourceMissing = "path must be provided."
    public let errorDestinationMissing = "path must be provided."
    public let errorOperationFailed = "Operation failed."

    private var implementation: Zip?

    override public func load() {
        self.implementation = Zip(plugin: self)
    }

    @objc func unzip(_ call: CAPPluginCall) {
        guard let source = call.getString("source") else {
            call.reject(errorSourceMissing)
            return
        }
        guard let destination = call.getString("destination") else {
            call.reject(errorDestinationMissing)
            return
        }
        let password = call.getString("password")

        implementation?.unzip(source: source, destination: destination, password: password, completion: { successful in
            if successful == true {
                call.resolve()
            } else {
                call.reject(self.errorOperationFailed)
            }
        })
    }

    @objc func zip(_ call: CAPPluginCall) {
        guard let source = call.getString("source") else {
            call.reject(errorSourceMissing)
            return
        }
        guard let destination = call.getString("destination") else {
            call.reject(errorDestinationMissing)
            return
        }
        let password = call.getString("password")

        implementation?.zip(source: source, destination: destination, password: password, completion: { successful in
            if successful == true {
                call.resolve()
            } else {
                call.reject(self.errorOperationFailed)
            }
        })
    }
}
