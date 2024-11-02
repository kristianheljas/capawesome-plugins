import Foundation

@objc public class PrintPdfOptions: PrintOptions {
    private var url: URL

    init(name: String, url: URL) {
        self.url = url
        super.init(name: name)
    }

    func getUrl() -> URL {
        return url
    }
}
