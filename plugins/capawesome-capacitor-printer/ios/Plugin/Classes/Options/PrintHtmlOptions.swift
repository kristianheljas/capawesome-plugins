import Foundation

@objc public class PrintHtmlOptions: PrintOptions {
    private var html: String

    init(name: String, html: String) {
        self.html = html
        super.init(name: name)
    }

    func getHtml() -> String {
        return html
    }
}
