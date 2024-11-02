import Foundation
import Capacitor

@objc(PrinterPlugin)
public class PrinterPlugin: CAPPlugin {
    public let tag = "FirebaseRemoteConfig"
    public let errorHtmlMissing = "html must be provided."
    public let errorPathMissing = "path must be provided."
    public let errorFileNotExist = "File does not exist."

    private var implementation: Printer?

    override public func load() {
        self.implementation = Printer(plugin: self)
    }

    @objc func printPdf(_ call: CAPPluginCall) {
        let name = call.getString("name") ?? "Document"
        guard let path = call.getString("path") else {
            call.reject(errorPathMissing)
            return
        }

        guard let url = implementation?.getFileUrlByPath(path) else {
            call.reject(errorFileNotExist)
            return
        }

        let options = PrintPdfOptions(name: name, url: url)

        implementation?.printPdf(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func printHtml(_ call: CAPPluginCall) {
        let name = call.getString("name") ?? "Document"
        guard let html = call.getString("html") else {
            call.reject(errorHtmlMissing)
            return
        }

        let options = PrintHtmlOptions(name: name, html: html)

        implementation?.printHtml(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }

    @objc func printWebView(_ call: CAPPluginCall) {
        let name = call.getString("name") ?? "Document"

        let options = PrintWebViewOptions(name: name)

        implementation?.printWebView(options, completion: { error in
            if let error = error {
                CAPLog.print("[", self.tag, "] ", error)
                call.reject(error.localizedDescription)
                return
            }
            call.resolve()
        })
    }
}
