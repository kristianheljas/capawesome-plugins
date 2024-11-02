import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(FileCompressorPlugin)
public class FileCompressorPlugin: CAPPlugin {
    public let errorPathMissing = "path must be provided."
    public let errorMimeTypeNotSupported = "mimeType is not supported."
    public let errorFileNotExist = "File does not exist."

    private var implementation: FileCompressor?

    override public func load() {
        self.implementation = FileCompressor(plugin: self)
    }

    @objc func compressImage(_ call: CAPPluginCall) {
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }
        let mimeType = call.getString("mimeType") ?? "image/jpeg"
        if mimeType != "image/jpeg" {
            call.reject(errorMimeTypeNotSupported)
            return
        }
        let quality = call.getFloat("quality") ?? 0.6

        guard let url = implementation?.getFileUrlByPath(path) else {
            call.reject(errorFileNotExist)
            return
        }

        implementation?.compressImage(url: url, mimeType: mimeType, quality: quality, completion: { path in
            var result = JSObject()
            result["path"] = path
            call.resolve(result)
        })
    }
}
