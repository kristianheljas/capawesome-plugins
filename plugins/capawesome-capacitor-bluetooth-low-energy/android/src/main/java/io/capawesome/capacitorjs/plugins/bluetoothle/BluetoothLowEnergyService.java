package io.capawesome.capacitorjs.plugins.bluetoothle;

import static android.content.pm.ServiceInfo.FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.le.BluetoothLeScanner;
import android.bluetooth.le.ScanCallback;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.plugin.util.AssetUtil;
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
import java.util.HashMap;
import java.util.List;

public class BluetoothLowEnergyService extends Service {

    interface BluetoothLowEnergyServiceCallback {
        void onCharacteristicChanged(CharacteristicChangedEvent event);
        void onDeviceDisconnected(DeviceDisconnectedEvent event);
        void onDeviceScanned(DeviceScannedEvent event);
    }

    public static final String DEFAULT_NOTIFICATION_CHANNEL_ID = "default";

    @NonNull
    private final IBinder binder = new LocalBinder();

    @NonNull
    private final BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

    @NonNull
    private final BluetoothLeScanner bluetoothLeScanner = bluetoothAdapter.getBluetoothLeScanner();

    @Nullable
    private BluetoothLowEnergyServiceCallback callback;

    @NonNull
    private HashMap<String, BluetoothLowEnergyDevice> connectedDevices = new HashMap<>();

    @NonNull
    private final ScanCallback scanCallback = new ScanCallback() {
        @Override
        public void onScanResult(int callbackType, @NonNull ScanResult result) {
            BluetoothDevice device = result.getDevice();
            int rssi = result.getRssi();
            String deviceId = device.getAddress();
            if (scannedDeviceIds.contains(deviceId)) {
                return;
            }
            scannedDeviceIds.add(deviceId);
            DeviceScannedEvent event = new DeviceScannedEvent(device, rssi);
            notifyDeviceScannedListener(event);
        }
    };

    @NonNull
    private final List<String> scannedDeviceIds = new ArrayList<>();

    public class LocalBinder extends Binder {

        public BluetoothLowEnergyService getService() {
            return BluetoothLowEnergyService.this;
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public void connect(@NonNull ConnectOptions options, @NonNull EmptyCallback callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = new BluetoothLowEnergyDevice(this, bluetoothAdapter, deviceId);
        device.connect(callback);
        connectedDevices.put(deviceId, device);
    }

    public void createBond(@NonNull CreateBondOptions options, @NonNull EmptyCallback callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.createBond(callback);
    }

    public void disconnect(@NonNull DisconnectOptions options, @NonNull EmptyCallback callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.disconnect(callback);
        connectedDevices.remove(deviceId);
    }

    public void discoverServices(@NonNull DiscoverServicesOptions options, @NonNull EmptyCallback callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.discoverServices(callback);
    }

    @SuppressLint("MissingPermission")
    public GetConnectedDevicesResult getConnectedDevices() {
        // BluetoothManager bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        // List<BluetoothDevice> connectedDevices = bluetoothManager.getConnectedDevices(BluetoothProfile.GATT);
        List<BluetoothDevice> connectedDevices = new ArrayList<>();
        for (BluetoothLowEnergyDevice device : this.connectedDevices.values()) {
            BluetoothDevice bluetoothDevice = device.getBluetoothDevice();
            if (bluetoothDevice != null) {
                connectedDevices.add(bluetoothDevice);
            }
        }
        GetConnectedDevicesResult result = new GetConnectedDevicesResult(connectedDevices);
        return result;
    }

    public GetServicesResult getServices(@NonNull GetServicesOptions options) throws Exception {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED);
        }
        return device.getServices();
    }

    public IsBondedResult isBonded(@NonNull IsBondedOptions options) throws Exception {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED);
        }
        return device.isBonded();
    }

    public IsEnabledResult isEnabled() {
        boolean isEnabled = bluetoothAdapter.isEnabled();
        IsEnabledResult result = new IsEnabledResult(isEnabled);
        return result;
    }

    public void notifyCharacteristicChangedListener(@NonNull CharacteristicChangedEvent event) {
        if (callback != null) {
            callback.onCharacteristicChanged(event);
        }
    }

    public void notifyDeviceDisconnectedListener(@NonNull DeviceDisconnectedEvent event) {
        if (callback != null) {
            callback.onDeviceDisconnected(event);
        }
        // Remove device from connectedDevices map
        String deviceId = event.getDevice().getAddress();
        connectedDevices.remove(deviceId);
    }

    public void notifyDeviceScannedListener(@NonNull DeviceScannedEvent event) {
        if (callback != null) {
            callback.onDeviceScanned(event);
        }
    }

    public void readCharacteristic(
        @NonNull ReadCharacteristicOptions options,
        @NonNull NonEmptyResultCallback<ReadCharacteristicResult> callback
    ) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.readCharacteristic(options, callback);
    }

    public void readDescriptor(@NonNull ReadDescriptorOptions options, @NonNull NonEmptyResultCallback<ReadDescriptorResult> callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.readDescriptor(options, callback);
    }

    public void readRssi(@NonNull ReadRssiOptions options, @NonNull NonEmptyResultCallback<ReadRssiResult> callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.readRssi(callback);
    }

    public void requestConnectionPriority(@NonNull RequestConnectionPriorityOptions options, @NonNull EmptyCallback callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.requestConnectionPriority(options, callback);
    }

    public void requestMtu(@NonNull RequestMtuOptions options, @NonNull EmptyCallback callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.requestMtu(options, callback);
    }

    public void setCallback(@NonNull BluetoothLowEnergyServiceCallback callback) {
        this.callback = callback;
    }

    public void startCharacteristicNotifications(
        @NonNull StartCharacteristicNotificationsOptions options,
        @NonNull EmptyCallback callback
    ) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.startCharacteristicNotifications(options, callback);
    }

    public void stopCharacteristicNotifications(@NonNull StopCharacteristicNotificationsOptions options, @NonNull EmptyCallback callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.stopCharacteristicNotifications(options, callback);
    }

    public void startForegroundService(@NonNull StartForegroundServiceOptions options) {
        String title = options.getTitle();
        String body = options.getBody();
        String smallIcon = options.getSmallIcon();
        int id = options.getId();

        int iconResourceId = AssetUtil.getResourceID(getApplicationContext(), AssetUtil.getResourceBaseName(smallIcon), "drawable");

        PendingIntent contentIntent = buildContentIntent(id);
        Notification.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new Notification.Builder(getApplicationContext(), DEFAULT_NOTIFICATION_CHANNEL_ID);
        } else {
            builder = new Notification.Builder(getApplicationContext());
        }
        builder
            .setContentTitle(title)
            .setContentText(body)
            .setContentIntent(contentIntent)
            .setOngoing(true)
            .setSmallIcon(iconResourceId)
            .setPriority(Notification.PRIORITY_HIGH);
        Notification notification = builder.build();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            startForeground(id, notification, FOREGROUND_SERVICE_TYPE_CONNECTED_DEVICE);
        } else {
            startForeground(id, notification);
        }
    }

    @SuppressLint("MissingPermission")
    public void startScan() {
        scannedDeviceIds.clear();
        bluetoothLeScanner.startScan(scanCallback);
    }

    public void stopForegroundService() {
        stopForeground(true);
    }

    @SuppressLint("MissingPermission")
    public void stopScan() {
        bluetoothLeScanner.stopScan(scanCallback);
    }

    public void writeCharacteristic(@NonNull WriteCharacteristicOptions options, @NonNull EmptyCallback callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.writeCharacteristic(options, callback);
    }

    public void writeDescriptor(@NonNull WriteDescriptorOptions options, @NonNull EmptyCallback callback) {
        String deviceId = options.getDeviceId();

        BluetoothLowEnergyDevice device = connectedDevices.get(deviceId);
        if (device == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
            return;
        }
        device.writeDescriptor(options, callback);
    }

    private PendingIntent buildContentIntent(int id) {
        String packageName = getApplicationContext().getPackageName();
        Intent intent = getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
        int pendingIntentFlags;
        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            pendingIntentFlags = PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE;
        } else {
            pendingIntentFlags = PendingIntent.FLAG_CANCEL_CURRENT;
        }
        return PendingIntent.getActivity(getApplicationContext(), id, intent, pendingIntentFlags);
    }
}
