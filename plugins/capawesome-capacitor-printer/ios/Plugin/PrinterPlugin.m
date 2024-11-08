#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(PrinterPlugin, "Printer",
           CAP_PLUGIN_METHOD(printPdf, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(printHtml, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(printWebView, CAPPluginReturnPromise);
)
