package io.capawesome.capacitorjs.plugins.bluetoothle;

import android.Manifest;
import androidx.annotation.NonNull;
import com.getcapacitor.JSArray;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.events.CharacteristicChangedEvent;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.events.DeviceDisconnectedEvent;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.events.DeviceScannedEvent;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.ConnectOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.CreateBondOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.DisconnectOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.DiscoverServicesOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.GetServicesOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.IsBondedOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.ReadCharacteristicOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.ReadDescriptorOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.ReadRssiOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.RequestConnectionPriorityOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.RequestMtuOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.StartCharacteristicNotificationsOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.StartForegroundServiceOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.StopCharacteristicNotificationsOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.WriteCharacteristicOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.WriteDescriptorOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.GetConnectedDevicesResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.GetServicesResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.IsBondedResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.IsEnabledResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.ReadCharacteristicResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.ReadDescriptorResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.ReadRssiResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.NonEmptyResultCallback;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONException;

@CapacitorPlugin(
    name = "BluetoothLowEnergy",
    permissions = {
        @Permission(strings = { Manifest.permission.ACCESS_FINE_LOCATION }, alias = BluetoothLowEnergyPlugin.PERMISSION_LOCATION),
        @Permission(strings = { Manifest.permission.BLUETOOTH_CONNECT }, alias = BluetoothLowEnergyPlugin.PERMISSION_BLUETOOTH_CONNECT),
        @Permission(strings = { Manifest.permission.BLUETOOTH_SCAN }, alias = BluetoothLowEnergyPlugin.PERMISSION_BLUETOOTH_SCAN),
        @Permission(strings = { Manifest.permission.POST_NOTIFICATIONS }, alias = BluetoothLowEnergyPlugin.PERMISSION_NOTIFICATIONS)
    }
)
public class BluetoothLowEnergyPlugin extends Plugin {

    public static final String ERROR_BODY_MISSING = "body must be provided.";
    public static final String ERROR_BOND_FAILED = "Failed to bond.";
    public static final String ERROR_CHARACTERISTIC_ID_MISSING = "characteristicId must be provided.";
    public static final String ERROR_CHARACTERISTIC_NOT_FOUND = "Characteristic not found.";
    public static final String ERROR_CONNECTION_PRIORITY_MISSING = "connectionPriority must be provided.";
    public static final String ERROR_CONNECTION_PRIORITY_INVALID = "connectionPriority is invalid.";
    public static final String ERROR_DESCRIPTOR_ID_MISSING = "descriptorId must be provided.";
    public static final String ERROR_DESCRIPTOR_NOT_FOUND = "Descriptor not found.";
    public static final String ERROR_DEVICE_ID_MISSING = "deviceId must be provided.";
    public static final String ERROR_DEVICE_NOT_CONNECTED = "Device is not connected.";
    public static final String ERROR_ID_MISSING = "id must be provided.";
    public static final String ERROR_MTU_MISSING = "mtu must be provided.";
    public static final String ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT = "Permission not granted for bluetoothConnect.";
    public static final String ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_SCAN = "Permission not granted for bluetoothScan.";
    public static final String ERROR_READ_CHARACTERISTIC_FAILED = "Failed to read characteristic.";
    public static final String ERROR_READ_DESCRIPTOR_FAILED = "Failed to read descriptor.";
    public static final String ERROR_READ_RSSI_FAILED = "Failed to read RSSI.";
    public static final String ERROR_REQUEST_CONNECTION_PRIORITY_FAILED = "Failed to request connection priority.";
    public static final String ERROR_REQUEST_MTU_FAILED = "Failed to request MTU.";
    public static final String ERROR_SERVICE_ID_MISSING = "serviceId must be provided.";
    public static final String ERROR_SERVICE_NOT_READY = "The bluetooth service not ready.";
    public static final String ERROR_SERVICE_NOT_FOUND = "Service not found.";
    public static final String ERROR_SMALL_ICON_MISSING = "smallIcon must be provided.";
    public static final String ERROR_TITLE_MISSING = "title must be provided.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String ERROR_VALUE_MISSING = "value must be provided.";
    public static final String ERROR_WRITE_CHARACTERISTIC_FAILED = "Failed to write characteristic.";
    public static final String ERROR_WRITE_DESCRIPTOR_FAILED = "Failed to write descriptor.";
    public static final String EVENT_CHARACTERISTIC_CHANGED = "characteristicChanged";
    public static final String EVENT_DEVICE_DISCONNECTED = "deviceDisconnected";
    public static final String EVENT_DEVICE_SCANNED = "deviceScanned";
    public static final String PERMISSION_BLUETOOTH_CONNECT = "bluetoothConnect";
    public static final String PERMISSION_BLUETOOTH_SCAN = "bluetoothScan";
    public static final String PERMISSION_LOCATION = "location";
    public static final String PERMISSION_NOTIFICATIONS = "notifications";
    public static final String TAG = "BluetoothLowEnergyPlugin";

    private BluetoothLowEnergy implementation;

    @Override
    public void load() {
        try {
            implementation = new BluetoothLowEnergy(this);
        } catch (Exception exception) {
            Logger.error(BluetoothLowEnergyPlugin.TAG, exception.getMessage(), exception);
        }
    }

    @PluginMethod
    public void connect(PluginCall call) {
        try {
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }

            ConnectOptions options = new ConnectOptions(deviceId);

            implementation.connect(
                options,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        rejectCall(call, exception);
                    }
                }
            );
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void createBond(PluginCall call) {
        try {
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }

            CreateBondOptions options = new CreateBondOptions(deviceId);

            implementation.createBond(
                options,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        rejectCall(call, exception);
                    }
                }
            );
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void disconnect(PluginCall call) {
        try {
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }

            DisconnectOptions options = new DisconnectOptions(deviceId);

            implementation.disconnect(
                options,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        rejectCall(call, exception);
                    }
                }
            );
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void discoverServices(PluginCall call) {
        try {
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }

            DiscoverServicesOptions options = new DiscoverServicesOptions(deviceId);

            implementation.discoverServices(
                options,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        rejectCall(call, exception);
                    }
                }
            );
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getConnectedDevices(PluginCall call) {
        try {
            GetConnectedDevicesResult result = implementation.getConnectedDevices();
            call.resolve(result.toJSObject());
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getServices(PluginCall call) {
        try {
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }

            GetServicesOptions options = new GetServicesOptions(deviceId);

            GetServicesResult result = implementation.getServices(options);
            call.resolve(result.toJSObject());
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void initialize(PluginCall call) {
        call.reject("Not implemented on Android.");
    }

    @PluginMethod
    public void isBonded(PluginCall call) {
        try {
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }

            IsBondedOptions options = new IsBondedOptions(deviceId);

            IsBondedResult result = implementation.isBonded(options);
            call.resolve(result.toJSObject());
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        try {
            IsEnabledResult result = implementation.isEnabled();
            call.resolve(result.toJSObject());
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void openAppSettings(PluginCall call) {
        try {
            implementation.openAppSettings();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void openBluetoothSettings(PluginCall call) {
        try {
            implementation.openBluetoothSettings();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void openLocationSettings(PluginCall call) {
        try {
            implementation.openLocationSettings();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void readCharacteristic(PluginCall call) {
        try {
            String characteristicId = call.getString("characteristicId");
            if (characteristicId == null) {
                call.reject(ERROR_CHARACTERISTIC_ID_MISSING);
                return;
            }
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }
            String serviceId = call.getString("serviceId");
            if (serviceId == null) {
                call.reject(ERROR_SERVICE_ID_MISSING);
                return;
            }

            ReadCharacteristicOptions options = new ReadCharacteristicOptions(characteristicId, deviceId, serviceId);

            NonEmptyResultCallback callback = new NonEmptyResultCallback<ReadCharacteristicResult>() {
                @Override
                public void success(ReadCharacteristicResult result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.readCharacteristic(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void readDescriptor(PluginCall call) {
        try {
            String characteristicId = call.getString("characteristicId");
            if (characteristicId == null) {
                call.reject(ERROR_CHARACTERISTIC_ID_MISSING);
                return;
            }
            String descriptorId = call.getString("descriptorId");
            if (descriptorId == null) {
                call.reject(ERROR_DESCRIPTOR_ID_MISSING);
                return;
            }
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }
            String serviceId = call.getString("serviceId");
            if (serviceId == null) {
                call.reject(ERROR_SERVICE_ID_MISSING);
                return;
            }

            ReadDescriptorOptions options = new ReadDescriptorOptions(characteristicId, descriptorId, deviceId, serviceId);

            NonEmptyResultCallback callback = new NonEmptyResultCallback<ReadDescriptorResult>() {
                @Override
                public void success(ReadDescriptorResult result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.readDescriptor(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void readRssi(PluginCall call) {
        try {
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }

            ReadRssiOptions options = new ReadRssiOptions(deviceId);

            NonEmptyResultCallback callback = new NonEmptyResultCallback<ReadRssiResult>() {
                @Override
                public void success(ReadRssiResult result) {
                    call.resolve(result.toJSObject());
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.readRssi(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void requestConnectionPriority(PluginCall call) {
        try {
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }
            Integer connectionPriority = call.getInt("connectionPriority");
            if (connectionPriority == null) {
                call.reject(ERROR_CONNECTION_PRIORITY_MISSING);
                return;
            }
            if (connectionPriority < 0 || connectionPriority > 3) {
                call.reject(ERROR_CONNECTION_PRIORITY_INVALID);
                return;
            }

            RequestConnectionPriorityOptions options = new RequestConnectionPriorityOptions(deviceId, connectionPriority);

            implementation.requestConnectionPriority(
                options,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        rejectCall(call, exception);
                    }
                }
            );
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void requestMtu(PluginCall call) {
        try {
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }
            Integer mtu = call.getInt("mtu");
            if (mtu == null) {
                call.reject(ERROR_MTU_MISSING);
                return;
            }

            RequestMtuOptions options = new RequestMtuOptions(deviceId, mtu);

            implementation.requestMtu(
                options,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        rejectCall(call, exception);
                    }
                }
            );
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startCharacteristicNotifications(PluginCall call) {
        try {
            String characteristicId = call.getString("characteristicId");
            if (characteristicId == null) {
                call.reject(ERROR_CHARACTERISTIC_ID_MISSING);
                return;
            }
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }
            String serviceId = call.getString("serviceId");
            if (serviceId == null) {
                call.reject(ERROR_SERVICE_ID_MISSING);
                return;
            }

            StartCharacteristicNotificationsOptions options = new StartCharacteristicNotificationsOptions(
                characteristicId,
                deviceId,
                serviceId
            );

            implementation.startCharacteristicNotifications(
                options,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        rejectCall(call, exception);
                    }
                }
            );
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startForegroundService(PluginCall call) {
        try {
            String body = call.getString("body");
            if (body == null) {
                call.reject(ERROR_BODY_MISSING);
                return;
            }
            Integer id = call.getInt("id");
            if (id == null) {
                call.reject(ERROR_ID_MISSING);
                return;
            }
            String smallIcon = call.getString("smallIcon");
            if (smallIcon == null) {
                call.reject(ERROR_SMALL_ICON_MISSING);
                return;
            }
            String title = call.getString("title");
            if (title == null) {
                call.reject(ERROR_TITLE_MISSING);
                return;
            }

            StartForegroundServiceOptions options = new StartForegroundServiceOptions(body, id, smallIcon, title);

            implementation.startForegroundService(options);
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startScan(PluginCall call) {
        try {
            implementation.startScan();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopCharacteristicNotifications(PluginCall call) {
        try {
            String characteristicId = call.getString("characteristicId");
            if (characteristicId == null) {
                call.reject(ERROR_CHARACTERISTIC_ID_MISSING);
                return;
            }
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }
            String serviceId = call.getString("serviceId");
            if (serviceId == null) {
                call.reject(ERROR_SERVICE_ID_MISSING);
                return;
            }

            StopCharacteristicNotificationsOptions options = new StopCharacteristicNotificationsOptions(
                characteristicId,
                deviceId,
                serviceId
            );

            implementation.stopCharacteristicNotifications(
                options,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        rejectCall(call, exception);
                    }
                }
            );
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopForegroundService(PluginCall call) {
        try {
            implementation.stopForegroundService();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void stopScan(PluginCall call) {
        try {
            implementation.stopScan();
            call.resolve();
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void writeCharacteristic(PluginCall call) {
        try {
            String characteristicId = call.getString("characteristicId");
            if (characteristicId == null) {
                call.reject(ERROR_CHARACTERISTIC_ID_MISSING);
                return;
            }
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }
            String serviceId = call.getString("serviceId");
            if (serviceId == null) {
                call.reject(ERROR_SERVICE_ID_MISSING);
                return;
            }
            String type = call.getString("type");
            JSArray value = call.getArray("value");
            if (value == null) {
                call.reject(ERROR_VALUE_MISSING);
                return;
            }

            WriteCharacteristicOptions options = new WriteCharacteristicOptions(characteristicId, deviceId, serviceId, type, value);

            implementation.writeCharacteristic(
                options,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        rejectCall(call, exception);
                    }
                }
            );
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void writeDescriptor(PluginCall call) {
        try {
            String characteristicId = call.getString("characteristicId");
            if (characteristicId == null) {
                call.reject(ERROR_CHARACTERISTIC_ID_MISSING);
                return;
            }
            String descriptorId = call.getString("descriptorId");
            if (descriptorId == null) {
                call.reject(ERROR_DESCRIPTOR_ID_MISSING);
                return;
            }
            String deviceId = call.getString("deviceId");
            if (deviceId == null) {
                call.reject(ERROR_DEVICE_ID_MISSING);
                return;
            }
            String serviceId = call.getString("serviceId");
            if (serviceId == null) {
                call.reject(ERROR_SERVICE_ID_MISSING);
                return;
            }
            JSArray value = call.getArray("value");
            if (value == null) {
                call.reject(ERROR_VALUE_MISSING);
                return;
            }

            WriteDescriptorOptions options = new WriteDescriptorOptions(characteristicId, descriptorId, deviceId, serviceId, value);

            implementation.writeDescriptor(
                options,
                new EmptyCallback() {
                    @Override
                    public void success() {
                        call.resolve();
                    }

                    @Override
                    public void error(Exception exception) {
                        rejectCall(call, exception);
                    }
                }
            );
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @Override
    @PluginMethod
    public void checkPermissions(PluginCall call) {
        super.checkPermissions(call);
    }

    @Override
    @PluginMethod
    public void requestPermissions(PluginCall call) {
        List<String> permissionsList = new ArrayList<>();
        permissionsList.add(PERMISSION_BLUETOOTH_CONNECT);
        permissionsList.add(PERMISSION_BLUETOOTH_SCAN);
        permissionsList.add(PERMISSION_LOCATION);
        permissionsList.add(PERMISSION_NOTIFICATIONS);

        JSArray permissions = call.getArray("permissions");
        if (permissions != null) {
            try {
                permissionsList = permissions.toList();
            } catch (JSONException e) {}
        }

        requestPermissionForAliases(permissionsList.toArray(new String[0]), call, "permissionsCallback");
    }

    public void notifyCharacteristicChangedListener(@NonNull CharacteristicChangedEvent event) {
        notifyListeners(EVENT_CHARACTERISTIC_CHANGED, event.toJSObject());
    }

    public void notifyDeviceDisconnectedListener(@NonNull DeviceDisconnectedEvent event) {
        notifyListeners(EVENT_DEVICE_DISCONNECTED, event.toJSObject());
    }

    public void notifyDeviceScannedListener(@NonNull DeviceScannedEvent event) {
        notifyListeners(EVENT_DEVICE_SCANNED, event.toJSObject());
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }

    @PermissionCallback
    private void permissionsCallback(PluginCall call) {
        this.checkPermissions(call);
    }
}
