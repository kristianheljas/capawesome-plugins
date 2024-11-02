package io.capawesome.capacitorjs.plugins.wifi;

import android.Manifest;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import com.getcapacitor.PermissionState;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import com.getcapacitor.annotation.PermissionCallback;
import io.capawesome.capacitorjs.plugins.wifi.classes.events.NetworksScannedEvent;
import io.capawesome.capacitorjs.plugins.wifi.classes.options.ConnectOptions;
import io.capawesome.capacitorjs.plugins.wifi.classes.results.GetAvailableNetworksResult;
import io.capawesome.capacitorjs.plugins.wifi.classes.results.GetIpAddressResult;
import io.capawesome.capacitorjs.plugins.wifi.classes.results.GetRssiResult;
import io.capawesome.capacitorjs.plugins.wifi.classes.results.GetSsidResult;
import io.capawesome.capacitorjs.plugins.wifi.classes.results.IsEnabledResult;
import io.capawesome.capacitorjs.plugins.wifi.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.wifi.interfaces.NonEmptyCallback;
import io.capawesome.capacitorjs.plugins.wifi.interfaces.Result;

@CapacitorPlugin(
    name = "Wifi",
    permissions = { @Permission(strings = { Manifest.permission.ACCESS_FINE_LOCATION }, alias = WifiPlugin.PERMISSION_LOCATION) }
)
public class WifiPlugin extends Plugin {

    public static final String TAG = "Wifi";
    public static final String ERROR_CONNECT_FAILED = "Failed to connect.";
    public static final String ERROR_DISCONNECT_FAILED = "Failed to disconnect.";
    public static final String ERROR_GET_IP_ADDRESS_FAILED = "Failed to get IP address.";
    public static final String ERROR_GET_RSSI_FAILED = "Failed to get RSSI.";
    public static final String ERROR_GET_SSID_FAILED = "Failed to get SSID.";
    public static final String ERROR_LOCATION_PERMISSION_DENIED = "Location permission denied.";
    public static final String ERROR_SSID_MISSING = "ssid must be provided.";
    public static final String ERROR_START_SCAN_FAILED = "Failed to start scan.";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String EVENT_NETWORKS_SCANNED = "networksScanned";
    public static final String PERMISSION_LOCATION = "location";
    public static final String CALLBACK_LOCATION_PERMISSION = "handleLocationPermissionCallback";

    private Wifi implementation;

    public static void logException(@NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
    }

    @Override
    public void load() {
        try {
            implementation = new Wifi(this);
        } catch (Exception exception) {
            logException(exception);
        }
    }

    @Override
    protected void handleOnPause() {
        try {
            implementation.handleOnPause();
        } catch (Exception exception) {
            logException(exception);
        }
    }

    @Override
    protected void handleOnResume() {
        try {
            implementation.handleOnResume();
        } catch (Exception exception) {
            logException(exception);
        }
    }

    @PluginMethod
    public void connect(PluginCall call) {
        try {
            String ssid = call.getString("ssid");
            if (ssid == null) {
                call.reject(ERROR_SSID_MISSING);
                return;
            }
            String password = call.getString("password");
            boolean isHiddenSsid = call.getBoolean("isHiddenSsid", false);

            ConnectOptions options = new ConnectOptions(ssid, password, isHiddenSsid);
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call, null);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.connect(options, callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void disconnect(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    resolveCall(call, null);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.disconnect(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getAvailableNetworks(PluginCall call) {
        try {
            NonEmptyCallback<GetAvailableNetworksResult> callback = new NonEmptyCallback<>() {
                @Override
                public void success(GetAvailableNetworksResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            if (getPermissionState(PERMISSION_LOCATION) != PermissionState.GRANTED) {
                requestPermissionForAlias(PERMISSION_LOCATION, call, CALLBACK_LOCATION_PERMISSION);
                return;
            }
            implementation.getAvailableNetworks(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getIpAddress(PluginCall call) {
        try {
            NonEmptyCallback<GetIpAddressResult> callback = new NonEmptyCallback<>() {
                @Override
                public void success(GetIpAddressResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            if (getPermissionState(PERMISSION_LOCATION) != PermissionState.GRANTED) {
                requestPermissionForAlias(PERMISSION_LOCATION, call, CALLBACK_LOCATION_PERMISSION);
                return;
            }
            implementation.getIpAddress(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getRssi(PluginCall call) {
        try {
            NonEmptyCallback<GetRssiResult> callback = new NonEmptyCallback<>() {
                @Override
                public void success(GetRssiResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            if (getPermissionState(PERMISSION_LOCATION) != PermissionState.GRANTED) {
                requestPermissionForAlias(PERMISSION_LOCATION, call, CALLBACK_LOCATION_PERMISSION);
                return;
            }
            implementation.getRssi(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void getSsid(PluginCall call) {
        try {
            NonEmptyCallback<GetSsidResult> callback = new NonEmptyCallback<>() {
                @Override
                public void success(GetSsidResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            if (getPermissionState(PERMISSION_LOCATION) != PermissionState.GRANTED) {
                requestPermissionForAlias(PERMISSION_LOCATION, call, CALLBACK_LOCATION_PERMISSION);
                return;
            }
            implementation.getSsid(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        try {
            NonEmptyCallback<IsEnabledResult> callback = new NonEmptyCallback<>() {
                @Override
                public void success(IsEnabledResult result) {
                    resolveCall(call, result);
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            implementation.isEnabled(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PluginMethod
    public void startScan(PluginCall call) {
        try {
            EmptyCallback callback = new EmptyCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    rejectCall(call, exception);
                }
            };

            if (getPermissionState(PERMISSION_LOCATION) != PermissionState.GRANTED) {
                requestPermissionForAlias(PERMISSION_LOCATION, call, CALLBACK_LOCATION_PERMISSION);
                return;
            }
            implementation.startScan(callback);
        } catch (Exception exception) {
            rejectCall(call, exception);
        }
    }

    @PermissionCallback
    private void handleLocationPermissionCallback(PluginCall call) {
        if (getPermissionState(PERMISSION_LOCATION) == PermissionState.GRANTED) {
            if (call.getMethodName().equals("getAvailableNetworks")) {
                getAvailableNetworks(call);
            } else if (call.getMethodName().equals("getIpAddress")) {
                getIpAddress(call);
            } else if (call.getMethodName().equals("getRssi")) {
                getRssi(call);
            } else if (call.getMethodName().equals("getSsid")) {
                getSsid(call);
            } else if (call.getMethodName().equals("startScan")) {
                startScan(call);
            }
        } else {
            rejectCall(call, new Exception(ERROR_LOCATION_PERMISSION_DENIED));
        }
    }

    public void notifyNetworksScannedListeners(@NonNull NetworksScannedEvent event) {
        this.notifyListeners(EVENT_NETWORKS_SCANNED, event, false);
    }

    private void notifyListeners(@NonNull String eventName, @NonNull Result result, boolean retainUntilConsumed) {
        super.notifyListeners(eventName, result.toJSObject(), retainUntilConsumed);
    }

    private void resolveCall(@NonNull PluginCall call, @Nullable Result result) {
        if (result == null) {
            call.resolve();
        } else {
            call.resolve(result.toJSObject());
        }
    }

    private void rejectCall(@NonNull PluginCall call, @NonNull Exception exception) {
        String message = exception.getMessage();
        if (message == null) {
            message = ERROR_UNKNOWN_ERROR;
        }
        Logger.error(TAG, message, exception);
        call.reject(message);
    }
}
