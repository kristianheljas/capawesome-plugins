#import <Foundation/Foundation.h>
#import <Capacitor/Capacitor.h>

// Define the plugin using the CAP_PLUGIN Macro, and
// each method the plugin supports using the CAP_PLUGIN_METHOD macro.
CAP_PLUGIN(BluetoothLowEnergyPlugin, "BluetoothLowEnergy",
           CAP_PLUGIN_METHOD(connect, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(createBond, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(disconnect, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(discoverServices, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getConnectedDevices, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(getServices, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(initialize, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isBonded, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(isEnabled, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(openAppSettings, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(openBluetoothSettings, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(openLocationSettings, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(readCharacteristic, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(readDescriptor, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(readRssi, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(requestConnectionPriority, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(requestMtu, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(startCharacteristicNotifications, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(startForegroundService, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(startScan, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(stopCharacteristicNotifications, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(stopForegroundService, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(stopScan, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(writeCharacteristic, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(writeDescriptor, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(checkPermissions, CAPPluginReturnPromise);
           CAP_PLUGIN_METHOD(requestPermissions, CAPPluginReturnPromise);
)
