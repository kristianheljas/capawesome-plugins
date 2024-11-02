import Foundation

public class FileCompressorHelper {
    public static func mapMimeTypeToExtension(_ mimeType: String) -> String {
        switch mimeType {
        case "image/jpeg":
            return "jpeg"
        default:
            return "jpeg"
        }
    }
}
