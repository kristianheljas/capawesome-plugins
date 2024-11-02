import Foundation
import SSZipArchive
import Capacitor

@objc public class Zip: NSObject {
    private let plugin: ZipPlugin

    init(plugin: ZipPlugin) {
        self.plugin = plugin
    }

    @objc public func unzip(source: String, destination: String, password: String?, completion: @escaping (Bool) -> Void) {
        var source = source
        if source.contains("file://") {
            source = source.replacingOccurrences(of: "file://", with: "")
        }
        var destination = destination
        if destination.contains("file://") {
            destination = destination.replacingOccurrences(of: "file://", with: "")
        }

        DispatchQueue.global(qos: .userInitiated).async {
            SSZipArchive.unzipFile(atPath: source, toDestination: destination, overwrite: false, password: password, progressHandler: {(_, _, _, _) in
                // No op
            }, completionHandler: { (_, succeeded, _) in
                completion(succeeded)
            })
        }
    }

    @objc public func zip(source: String, destination: String, password: String?, completion: @escaping (Bool) -> Void) {
        var source = source
        if source.contains("file://") {
            source = source.replacingOccurrences(of: "file://", with: "")
        }
        let sourceURL = URL(fileURLWithPath: source)
        var destination = destination
        if destination.contains("file://") {
            destination = destination.replacingOccurrences(of: "file://", with: "")
        }

        DispatchQueue.global(qos: .userInitiated).async {
            var successful = false
            if sourceURL.isDirectory {
                successful = SSZipArchive.createZipFile(atPath: destination, withContentsOfDirectory: source, withPassword: password)
            } else {
                successful = SSZipArchive.createZipFile(atPath: destination, withFilesAtPaths: [source], withPassword: password)
            }
            completion(successful)
        }
    }
}

extension URL {
    var isDirectory: Bool {
        (try? resourceValues(forKeys: [.isDirectoryKey]))?.isDirectory == true
    }
}
