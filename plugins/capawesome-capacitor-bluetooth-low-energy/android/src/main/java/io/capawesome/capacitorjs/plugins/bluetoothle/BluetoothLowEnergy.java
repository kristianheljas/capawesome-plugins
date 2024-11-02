package io.capawesome.capacitorjs.plugins.bluetoothle;

import static android.content.Context.POWER_SERVICE;
import static android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
import static android.provider.Settings.ACTION_BLUETOOTH_SETTINGS;
import static android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
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

public class BluetoothLowEnergy {

    @Nullable
    private PowerManager.WakeLock activeWakeLock;

    private final BluetoothLowEnergyPlugin plugin;

    @Nullable
    private BluetoothLowEnergyService service;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            BluetoothLowEnergyService.LocalBinder binder = (BluetoothLowEnergyService.LocalBinder) iBinder;
            service = binder.getService();
            service.setCallback(createBluetoothLowEnergyServiceCallback());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            service = null;
        }
    };

    public BluetoothLowEnergy(BluetoothLowEnergyPlugin plugin) {
        this.plugin = plugin;
        Context context = plugin.getActivity().getApplicationContext();
        this.startAndBindService(context);
    }

    public void connect(@NonNull ConnectOptions options, @NonNull EmptyCallback callback) throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.connect(options, callback);
    }

    public void createBond(@NonNull CreateBondOptions options, @NonNull EmptyCallback callback) throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.createBond(options, callback);
    }

    public void disconnect(@NonNull DisconnectOptions options, @NonNull EmptyCallback callback) throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.disconnect(options, callback);
    }

    public void discoverServices(@NonNull DiscoverServicesOptions options, @NonNull EmptyCallback callback) {
        if (service == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY));
            return;
        }
        service.discoverServices(options, callback);
    }

    public GetConnectedDevicesResult getConnectedDevices() throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        return service.getConnectedDevices();
    }

    public GetServicesResult getServices(@NonNull GetServicesOptions options) throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        return service.getServices(options);
    }

    public IsBondedResult isBonded(@NonNull IsBondedOptions options) throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        return service.isBonded(options);
    }

    public IsEnabledResult isEnabled() throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        return service.isEnabled();
    }

    public void notifyCharacteristicChangedListener(@NonNull CharacteristicChangedEvent result) {
        plugin.notifyCharacteristicChangedListener(result);
    }

    public void notifyDeviceDisconnectedListener(@NonNull DeviceDisconnectedEvent event) {
        plugin.notifyDeviceDisconnectedListener(event);
    }

    public void notifyDeviceScannedListener(@NonNull DeviceScannedEvent event) {
        plugin.notifyDeviceScannedListener(event);
    }

    public void openAppSettings() {
        Intent intent = new Intent(ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + plugin.getContext().getPackageName()));
        plugin.getActivity().startActivity(intent);
    }

    public void openBluetoothSettings() {
        Intent intent = new Intent();
        intent.setAction(ACTION_BLUETOOTH_SETTINGS);
        plugin.getActivity().startActivity(intent);
    }

    public void openLocationSettings() {
        Intent intent = new Intent(ACTION_LOCATION_SOURCE_SETTINGS);
        plugin.getActivity().startActivity(intent);
    }

    public void readCharacteristic(
        @NonNull ReadCharacteristicOptions options,
        @NonNull NonEmptyResultCallback<ReadCharacteristicResult> callback
    ) throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.readCharacteristic(options, callback);
    }

    public void readDescriptor(@NonNull ReadDescriptorOptions options, @NonNull NonEmptyResultCallback<ReadDescriptorResult> callback)
        throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.readDescriptor(options, callback);
    }

    public void readRssi(@NonNull ReadRssiOptions options, @NonNull NonEmptyResultCallback<ReadRssiResult> callback) throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.readRssi(options, callback);
    }

    public void requestConnectionPriority(@NonNull RequestConnectionPriorityOptions options, @NonNull EmptyCallback callback)
        throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.requestConnectionPriority(options, callback);
    }

    public void requestMtu(@NonNull RequestMtuOptions options, @NonNull EmptyCallback callback) throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.requestMtu(options, callback);
    }

    public void startCharacteristicNotifications(@NonNull StartCharacteristicNotificationsOptions options, @NonNull EmptyCallback callback)
        throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.startCharacteristicNotifications(options, callback);
    }

    public void startScan() throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_SCAN);
        }
        service.startScan();
    }

    public void startForegroundService(@NonNull StartForegroundServiceOptions options) throws Exception {
        acquireWakeLock();
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        service.startForegroundService(options);
    }

    public void stopCharacteristicNotifications(@NonNull StopCharacteristicNotificationsOptions options, @NonNull EmptyCallback callback)
        throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.stopCharacteristicNotifications(options, callback);
    }

    public void stopForegroundService() throws Exception {
        releaseWakeLock();
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        service.stopForegroundService();
    }

    public void stopScan() throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_SCAN);
        }
        service.stopScan();
    }

    public void writeCharacteristic(@NonNull WriteCharacteristicOptions options, @NonNull EmptyCallback callback) throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.writeCharacteristic(options, callback);
    }

    public void writeDescriptor(@NonNull WriteDescriptorOptions options, @NonNull EmptyCallback callback) throws Exception {
        if (service == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_READY);
        }
        if (
            ActivityCompat.checkSelfPermission(plugin.getContext(), Manifest.permission.BLUETOOTH_CONNECT) !=
            PackageManager.PERMISSION_GRANTED
        ) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_PERMISSION_NOT_GRANTED_BLUETOOTH_CONNECT);
        }
        service.writeDescriptor(options, callback);
    }

    private void acquireWakeLock() {
        if (activeWakeLock != null) {
            return;
        }
        PowerManager powerManager = (PowerManager) plugin.getContext().getSystemService(POWER_SERVICE);
        PowerManager.WakeLock wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "CapacitorBluetoothLowEnergyService::Wakelock"
        );
        wakeLock.acquire();
        activeWakeLock = wakeLock;
    }

    private void bindService(Context context, Intent intent) {
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
    }

    private BluetoothLowEnergyService.BluetoothLowEnergyServiceCallback createBluetoothLowEnergyServiceCallback() {
        return new BluetoothLowEnergyService.BluetoothLowEnergyServiceCallback() {
            @Override
            public void onCharacteristicChanged(@NonNull CharacteristicChangedEvent event) {
                notifyCharacteristicChangedListener(event);
            }

            @Override
            public void onDeviceDisconnected(@NonNull DeviceDisconnectedEvent event) {
                notifyDeviceDisconnectedListener(event);
            }

            @Override
            public void onDeviceScanned(@NonNull DeviceScannedEvent event) {
                notifyDeviceScannedListener(event);
            }
        };
    }

    private void releaseWakeLock() {
        if (activeWakeLock == null) {
            return;
        }
        activeWakeLock.release();
        activeWakeLock = null;
    }

    private void startService(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent);
        } else {
            context.startService(intent);
        }
    }

    private void startAndBindService(Context context) {
        Intent intent = new Intent(context, BluetoothLowEnergyService.class);
        startService(context, intent);
        bindService(context, intent);
    }

    private void stopAndUnbindService() {
        Context context = plugin.getActivity().getApplicationContext();
        stopService(context);
        unbindService(context);
    }

    private void stopService(Context context) {
        Intent intent = new Intent(context, BluetoothLowEnergyService.class);
        context.stopService(intent);
    }

    private void unbindService(Context context) {
        context.unbindService(serviceConnection);
    }
}
