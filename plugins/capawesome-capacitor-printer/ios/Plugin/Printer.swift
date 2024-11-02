import Foundation
import WebKit
import UIKit

@objc public class Printer: NSObject {
    private let plugin: PrinterPlugin

    private var printHtmlCompletion: ((Error?) -> Void)?
    private var printHtmlOptions: PrintHtmlOptions?
    private var webView: WKWebView?

    init(plugin: PrinterPlugin) {
        self.plugin = plugin
    }

    @objc public func printPdf(_ options: PrintPdfOptions, completion: @escaping (Error?) -> Void) {
        let name = options.getName()
        let url = options.getUrl()

        DispatchQueue.main.async {
            let controller = UIPrintInteractionController.shared
            controller.delegate = self
            controller.printInfo?.jobName = name
            controller.printingItem = url
            controller.present(animated: true, completionHandler: { _, _, error in
                completion(error)
            })
        }
    }

    @objc public func printHtml(_ options: PrintHtmlOptions, completion: @escaping (Error?) -> Void) {
        let html = options.getHtml()

        printHtmlOptions = options
        printHtmlCompletion = completion

        DispatchQueue.main.async {
            guard let window = UIApplication.shared.delegate?.window else { return }
            let aWebView = WKWebView(frame: window!.bounds)
            window!.addSubview(aWebView)
            aWebView.navigationDelegate = self
            aWebView.isHidden = true
            aWebView.loadHTMLString(html, baseURL: nil)
            self.webView = aWebView
        }
    }

    @objc public func printWebView(_ options: PrintWebViewOptions, completion: @escaping (Error?) -> Void) {
        let name = options.getName()

        DispatchQueue.main.async {
            let controller = UIPrintInteractionController.shared
            controller.delegate = self
            controller.printInfo?.jobName = name
            controller.printFormatter = self.plugin.webView?.viewPrintFormatter()
            controller.present(animated: true, completionHandler: { _, _, error in
                completion(error)
            })
        }
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

extension Printer: UIPrintInteractionControllerDelegate {

}

extension Printer: WKNavigationDelegate {
    public func webView(_ webView: WKWebView, didFinish navigation: WKNavigation!) {
        let name = printHtmlOptions?.getName()

        let controller = UIPrintInteractionController.shared
        controller.delegate = self
        controller.printInfo?.jobName = name ?? ""
        controller.printFormatter = webView.viewPrintFormatter()
        controller.present(animated: true, completionHandler: { _, _, error in
            self.printHtmlCompletion?(error)
        })

        self.webView?.navigationDelegate = nil
        self.webView?.removeFromSuperview()
    }
}
