package io.capawesome.capacitorjs.plugins.wifi;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;
import static io.capawesome.capacitorjs.plugins.wifi.WifiPlugin.ERROR_CONNECT_FAILED;
import static io.capawesome.capacitorjs.plugins.wifi.WifiPlugin.ERROR_DISCONNECT_FAILED;
import static io.capawesome.capacitorjs.plugins.wifi.WifiPlugin.ERROR_GET_IP_ADDRESS_FAILED;
import static io.capawesome.capacitorjs.plugins.wifi.WifiPlugin.ERROR_GET_RSSI_FAILED;
import static io.capawesome.capacitorjs.plugins.wifi.WifiPlugin.ERROR_GET_SSID_FAILED;
import static io.capawesome.capacitorjs.plugins.wifi.WifiPlugin.ERROR_START_SCAN_FAILED;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.net.wifi.WifiNetworkSpecifier;
import android.os.Build;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import io.capawesome.capacitorjs.plugins.wifi.classes.events.NetworksScannedEvent;
import io.capawesome.capacitorjs.plugins.wifi.classes.options.ConnectOptions;
import io.capawesome.capacitorjs.plugins.wifi.classes.results.GetAvailableNetworksResult;
import io.capawesome.capacitorjs.plugins.wifi.classes.results.GetIpAddressResult;
import io.capawesome.capacitorjs.plugins.wifi.classes.results.GetRssiResult;
import io.capawesome.capacitorjs.plugins.wifi.classes.results.GetSsidResult;
import io.capawesome.capacitorjs.plugins.wifi.classes.results.IsEnabledResult;
import io.capawesome.capacitorjs.plugins.wifi.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.wifi.interfaces.NonEmptyCallback;
import java.util.List;

public class Wifi {

    @Nullable
    private BroadcastReceiver broadcastReceiver;

    @NonNull
    private ConnectivityManager connectivityManager;

    @Nullable
    private ConnectivityManager.NetworkCallback networkCallback;

    @NonNull
    private final WifiPlugin plugin;

    @NonNull
    private final WifiManager wifiManager;

    public Wifi(WifiPlugin plugin) {
        this.connectivityManager = (ConnectivityManager) plugin.getContext().getSystemService(CONNECTIVITY_SERVICE);
        this.plugin = plugin;
        this.wifiManager = (WifiManager) plugin.getContext().getApplicationContext().getSystemService(WIFI_SERVICE);
    }

    public void handleOnPause() {
        unregisterBroadcastReceiver();
    }

    public void handleOnResume() {
        registerBroadcastReceiver();
    }

    public void connect(@NonNull ConnectOptions options, @NonNull EmptyCallback callback) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            connectViaNetworkRequest(options, callback);
        } else {
            connectViaWifiManager(options, callback);
        }
    }

    public void disconnect(@NonNull EmptyCallback callback) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
            if (networkCallback != null) {
                connectivityManager.unregisterNetworkCallback(networkCallback);
                networkCallback = null;
            }
            connectivityManager.bindProcessToNetwork(null);
            callback.success();
        } else {
            boolean succeeded = wifiManager.disconnect();
            if (succeeded) {
                callback.success();
            } else {
                callback.error(new Exception(ERROR_DISCONNECT_FAILED));
            }
        }
    }

    @SuppressLint("MissingPermission")
    public void getIpAddress(@NonNull NonEmptyCallback<GetIpAddressResult> callback) {
        WifiInfo wifiInfo = getWifiInfo();
        if (wifiInfo == null) {
            callback.error(new Exception(ERROR_GET_IP_ADDRESS_FAILED));
        } else {
            GetIpAddressResult result = new GetIpAddressResult(wifiInfo);
            callback.success(result);
        }
    }

    public void getRssi(@NonNull NonEmptyCallback<GetRssiResult> callback) {
        WifiInfo wifiInfo = getWifiInfo();
        if (wifiInfo == null) {
            callback.error(new Exception(ERROR_GET_RSSI_FAILED));
        } else {
            GetRssiResult result = new GetRssiResult(wifiInfo);
            callback.success(result);
        }
    }

    @SuppressLint("MissingPermission")
    public void getAvailableNetworks(@NonNull NonEmptyCallback<GetAvailableNetworksResult> callback) {
        List<ScanResult> scanResults = wifiManager.getScanResults();
        GetAvailableNetworksResult result = new GetAvailableNetworksResult(scanResults);
        callback.success(result);
    }

    public void getSsid(@NonNull NonEmptyCallback<GetSsidResult> callback) {
        WifiInfo wifiInfo = getWifiInfo();
        if (wifiInfo == null) {
            callback.error(new Exception(ERROR_GET_SSID_FAILED));
        } else {
            GetSsidResult result = new GetSsidResult(wifiInfo);
            callback.success(result);
        }
    }

    public void isEnabled(@NonNull NonEmptyCallback<IsEnabledResult> callback) {
        boolean isEnabled = wifiManager.isWifiEnabled();
        IsEnabledResult result = new IsEnabledResult(isEnabled);
        callback.success(result);
    }

    public void startScan(@NonNull EmptyCallback callback) {
        boolean succeeded = wifiManager.startScan();
        if (succeeded) {
            callback.success();
        } else {
            callback.error(new Exception(ERROR_START_SCAN_FAILED));
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.Q)
    private void connectViaNetworkRequest(@NonNull ConnectOptions options, @NonNull EmptyCallback callback) {
        String ssid = options.getSsid();
        String password = options.getPassword();
        boolean isHiddenSsid = options.isHiddenSsid();

        WifiNetworkSpecifier.Builder builder = new WifiNetworkSpecifier.Builder();
        builder.setSsid(ssid);
        if (password != null && !password.isEmpty()) {
            builder.setWpa2Passphrase(password);
        }
        if (isHiddenSsid) {
            builder.setIsHiddenSsid(true);
        }
        WifiNetworkSpecifier specifier = builder.build();
        NetworkRequest.Builder networkRequestBuilder = new NetworkRequest.Builder();
        networkRequestBuilder.addTransportType(NetworkCapabilities.TRANSPORT_WIFI);
        networkRequestBuilder.setNetworkSpecifier(specifier);
        NetworkRequest networkRequest = networkRequestBuilder.build();
        networkCallback =
            new ConnectivityManager.NetworkCallback() {
                @Override
                public void onAvailable(Network network) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        connectivityManager.bindProcessToNetwork(network);
                    } else {
                        connectivityManager.setProcessDefaultNetwork(network);
                    }
                    callback.success();
                }

                @Override
                public void onLost(@NonNull Network network) {
                    super.onLost(network);
                    connectivityManager.bindProcessToNetwork(null);
                    connectivityManager.unregisterNetworkCallback(networkCallback);
                }

                @Override
                public void onUnavailable() {
                    callback.error(new Exception(ERROR_CONNECT_FAILED));
                }
            };
        connectivityManager.requestNetwork(networkRequest, networkCallback);
    }

    private void connectViaWifiManager(@NonNull ConnectOptions options, @NonNull EmptyCallback callback) {
        String ssid = options.getSsid();
        String password = options.getPassword();
        boolean isHiddenSsid = options.isHiddenSsid();

        // Set up the wifi configuration
        WifiConfiguration wifiConfiguration = new WifiConfiguration();
        wifiConfiguration.SSID = "\"" + ssid + "\"";
        wifiConfiguration.status = WifiConfiguration.Status.ENABLED;
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.RSN);
        wifiConfiguration.allowedProtocols.set(WifiConfiguration.Protocol.WPA);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);
        wifiConfiguration.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP40);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.WEP104);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
        wifiConfiguration.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
        if (password == null || password.isEmpty()) {
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.NONE);
            wifiConfiguration.allowedAuthAlgorithms.clear();
        } else {
            wifiConfiguration.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            wifiConfiguration.preSharedKey = "\"" + password + "\"";
        }
        if (isHiddenSsid) {
            wifiConfiguration.hiddenSSID = true;
        }

        // Connect to the network
        int networkId = wifiManager.addNetwork(wifiConfiguration);
        if (networkId == -1) {
            callback.error(new Exception(ERROR_CONNECT_FAILED));
            return;
        }
        // boolean disconnectSucceeded = wifiManager.disconnect();
        // if (!disconnectSucceeded) {
        //     callback.error(new Exception("Failed to disconnect from current network."));
        //     return;
        // }
        boolean enableNetworkSucceeded = wifiManager.enableNetwork(networkId, true);
        if (!enableNetworkSucceeded) {
            callback.error(new Exception(ERROR_CONNECT_FAILED));
            return;
        }
        boolean reconnectSucceeded = wifiManager.reconnect();
        if (!reconnectSucceeded) {
            callback.error(new Exception(ERROR_CONNECT_FAILED));
            return;
        }
        callback.success();
    }

    private BroadcastReceiver createBroadcastReceiver() {
        return new BroadcastReceiver() {
            @Override
            @SuppressLint("MissingPermission")
            public void onReceive(Context context, Intent intent) {
                try {
                    List<ScanResult> scanResults = wifiManager.getScanResults();
                    NetworksScannedEvent event = new NetworksScannedEvent(scanResults);
                    plugin.notifyNetworksScannedListeners(event);
                } catch (Exception exception) {
                    WifiPlugin.logException(exception);
                }
            }
        };
    }

    @SuppressLint("MissingPermission")
    @Nullable
    private WifiInfo getWifiInfo() {
        /*if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            Network network = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
            if (networkCapabilities == null) {
                return null;
            } else {
                return (WifiInfo) networkCapabilities.getTransportInfo();
            }
        } else {
            return wifiManager.getConnectionInfo();
        }*/
        return wifiManager.getConnectionInfo();
    }

    private void registerBroadcastReceiver() {
        if (broadcastReceiver == null) {
            broadcastReceiver = createBroadcastReceiver();
            plugin.getContext().registerReceiver(broadcastReceiver, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        }
    }

    private void unregisterBroadcastReceiver() {
        if (broadcastReceiver != null) {
            plugin.getContext().unregisterReceiver(broadcastReceiver);
            broadcastReceiver = null;
        }
    }
}
