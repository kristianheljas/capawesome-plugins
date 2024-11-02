#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(NfcPlugin, "Nfc",
           CAP_PLUGIN_METHOD(startScanSession, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(stopScanSession, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(write, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(makeReadOnly, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(erase, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(format, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(transceive, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(connect, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(close, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isSupported, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isEnabled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(openSettings, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getAntennaInfo, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(setAlertMessage, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(checkPermissions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(requestPermissions, CAPPluginReturnPromise);
)
