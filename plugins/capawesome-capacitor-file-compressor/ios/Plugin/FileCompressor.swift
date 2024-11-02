import Foundation
import UIKit

@objc public class FileCompressor: NSObject {
    private let plugin: FileCompressorPlugin
    private var interactionController: UIDocumentInteractionController?

    init(plugin: FileCompressorPlugin) {
        self.plugin = plugin
    }

    @objc public func compressImage(url: URL, mimeType: String, quality: Float, completion: @escaping (String) -> Void) {
        let outputFileName = String(Int(NSDate().timeIntervalSince1970 * 1000.0)) + "." + FileCompressorHelper.mapMimeTypeToExtension(mimeType)
        let outputFilePath = NSTemporaryDirectory() + outputFileName
        let outputFileUrl = URL.init(fileURLWithPath: outputFilePath)
        let image = UIImage.init(contentsOfFile: url.path)
        let data = image?.jpegData(compressionQuality: CGFloat(quality))
        try? data?.write(to: outputFileUrl)
        completion(outputFileUrl.absoluteString)
    }

    @objc public func getFileUrlByPath(_ path: String) -> URL? {
        guard let url = URL.init(string: path) else {
            return nil
        }
        if FileManager.default.fileExists(atPath: url.path) {
            return url
        } else {
            return nil
        }
    }
}
