#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(WifiPlugin, "Wifi",
           CAP_PLUGIN_METHOD(connect, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(disconnect, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getAvailableNetworks, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getIpAddress, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getRssi, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getScanResult, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getSsid, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isEnabled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(startScan, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(checkPermissions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(requestPermissions, CAPPluginReturnPromise);
)
