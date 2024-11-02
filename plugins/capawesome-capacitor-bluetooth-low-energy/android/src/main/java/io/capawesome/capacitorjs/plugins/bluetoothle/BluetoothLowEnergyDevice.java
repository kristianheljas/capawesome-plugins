package io.capawesome.capacitorjs.plugins.bluetoothle;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.getcapacitor.Logger;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.events.CharacteristicChangedEvent;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.events.DeviceDisconnectedEvent;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.ReadCharacteristicOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.ReadDescriptorOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.RequestConnectionPriorityOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.RequestMtuOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.StartCharacteristicNotificationsOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.StopCharacteristicNotificationsOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.WriteCharacteristicOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.options.WriteDescriptorOptions;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.GetServicesResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.IsBondedResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.ReadCharacteristicResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.ReadDescriptorResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.classes.results.ReadRssiResult;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.EmptyCallback;
import io.capawesome.capacitorjs.plugins.bluetoothle.interfaces.NonEmptyResultCallback;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.UUID;

public class BluetoothLowEnergyDevice {

    @Nullable
    private final BluetoothAdapter bluetoothAdapter;

    @Nullable
    private BluetoothGatt bluetoothGatt;

    @Nullable
    private BroadcastReceiver bondBroadcastReceiver;

    @NonNull
    private final BluetoothLowEnergyService service;

    @NonNull
    private final String deviceId;

    @Nullable
    private EmptyCallback savedConnectCallback;

    @Nullable
    private EmptyCallback savedCreateBondCallback;

    @Nullable
    private EmptyCallback savedDisconnectCallback;

    @Nullable
    private EmptyCallback savedDiscoverServicesCallback;

    @Nullable
    private NonEmptyResultCallback<ReadCharacteristicResult> savedReadCharacteristicCallback;

    @Nullable
    private NonEmptyResultCallback<ReadDescriptorResult> savedReadDescriptorCallback;

    @Nullable
    private EmptyCallback savedRequestMtuCallback;

    @Nullable
    private EmptyCallback savedWriteCharacteristicCallback;

    @Nullable
    private EmptyCallback savedWriteDescriptorCallback;

    @Nullable
    private NonEmptyResultCallback<ReadRssiResult> savedReadRssiCallback;

    public BluetoothLowEnergyDevice(
        @NonNull BluetoothLowEnergyService service,
        @NonNull BluetoothAdapter bluetoothAdapter,
        @NonNull String deviceId
    ) {
        this.service = service;
        this.bluetoothAdapter = bluetoothAdapter;
        this.deviceId = deviceId;
    }

    @SuppressLint("MissingPermission")
    public void connect(@NonNull EmptyCallback callback) {
        savedConnectCallback = callback;
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(deviceId);
        bluetoothGatt = device.connectGatt(service, false, createBluetoothGattCallback());
    }

    @SuppressLint("MissingPermission")
    public void createBond(@NonNull EmptyCallback callback) {
        BluetoothDevice device = bluetoothGatt.getDevice();
        // Check if the device is already bonded
        boolean isBonded = device.getBondState() == BluetoothDevice.BOND_BONDED;
        if (isBonded) {
            callback.success();
            return;
        }
        savedCreateBondCallback = callback;
        // Register a broadcast receiver to listen for bond state changes
        registerBondBroadcastReceiver();
        // Initiate the bonding process
        boolean result = device.createBond();
        if (!result) {
            savedCreateBondCallback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_BOND_FAILED));
        }
    }

    @SuppressLint("MissingPermission")
    public void disconnect(@NonNull EmptyCallback callback) {
        if (bluetoothGatt != null) {
            savedDisconnectCallback = callback;
            bluetoothGatt.disconnect();
        }
    }

    @SuppressLint("MissingPermission")
    public void discoverServices(@NonNull EmptyCallback callback) {
        savedDiscoverServicesCallback = callback;
        bluetoothGatt.discoverServices();
    }

    @Nullable
    public BluetoothDevice getBluetoothDevice() {
        return bluetoothGatt.getDevice();
    }

    public GetServicesResult getServices() throws Exception {
        if (bluetoothGatt == null) {
            throw new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED);
        }
        List<BluetoothGattService> services = bluetoothGatt.getServices();
        GetServicesResult result = new GetServicesResult(services);
        return result;
    }

    @SuppressLint("MissingPermission")
    public IsBondedResult isBonded() {
        BluetoothDevice device = bluetoothGatt.getDevice();
        boolean isBonded = device.getBondState() == BluetoothDevice.BOND_BONDED;
        IsBondedResult result = new IsBondedResult(isBonded);
        return result;
    }

    @SuppressLint("MissingPermission")
    public void readCharacteristic(
        @NonNull ReadCharacteristicOptions options,
        @NonNull NonEmptyResultCallback<ReadCharacteristicResult> callback
    ) {
        String characteristicId = options.getCharacteristicId();
        String serviceId = options.getServiceId();

        if (bluetoothGatt == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
        }
        BluetoothGattService service = getService(serviceId);
        if (service == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_FOUND));
        }
        BluetoothGattCharacteristic characteristic = getCharacteristic(service, characteristicId);
        if (characteristic == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_CHARACTERISTIC_NOT_FOUND));
        }
        savedReadCharacteristicCallback = callback;
        bluetoothGatt.readCharacteristic(characteristic);
    }

    @SuppressLint("MissingPermission")
    public void readDescriptor(@NonNull ReadDescriptorOptions options, @NonNull NonEmptyResultCallback<ReadDescriptorResult> callback) {
        String characteristicId = options.getCharacteristicId();
        String descriptorId = options.getDescriptorId();
        String serviceId = options.getServiceId();

        if (bluetoothGatt == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
        }
        BluetoothGattService service = getService(serviceId);
        if (service == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_FOUND));
        }
        BluetoothGattCharacteristic characteristic = getCharacteristic(service, characteristicId);
        if (characteristic == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_CHARACTERISTIC_NOT_FOUND));
        }
        BluetoothGattDescriptor descriptor = getDescriptor(characteristic, descriptorId);
        if (descriptor == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DESCRIPTOR_NOT_FOUND));
        }
        savedReadDescriptorCallback = callback;
        bluetoothGatt.readDescriptor(descriptor);
    }

    @SuppressLint("MissingPermission")
    public void readRssi(@NonNull NonEmptyResultCallback<ReadRssiResult> callback) {
        if (bluetoothGatt == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
        }
        savedReadRssiCallback = callback;
        bluetoothGatt.readRemoteRssi();
    }

    @SuppressLint("MissingPermission")
    public void requestConnectionPriority(@NonNull RequestConnectionPriorityOptions options, @NonNull EmptyCallback callback) {
        String deviceId = options.getDeviceId();
        int connectionPriority = options.getConnectionPriority();

        if (bluetoothGatt == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
        }
        boolean result = bluetoothGatt.requestConnectionPriority(connectionPriority);
        if (result) {
            callback.success();
        } else {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_REQUEST_CONNECTION_PRIORITY_FAILED));
        }
    }

    @SuppressLint("MissingPermission")
    public void requestMtu(@NonNull RequestMtuOptions options, @NonNull EmptyCallback callback) {
        int mtu = options.getMtu();

        if (bluetoothGatt == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DEVICE_NOT_CONNECTED));
        }
        savedRequestMtuCallback = callback;
        boolean result = bluetoothGatt.requestMtu(mtu);
        if (!result) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_REQUEST_MTU_FAILED));
        }
    }

    public void startCharacteristicNotifications(
        @NonNull StartCharacteristicNotificationsOptions options,
        @NonNull EmptyCallback callback
    ) {
        String characteristicId = options.getCharacteristicId();
        String serviceId = options.getServiceId();

        BluetoothGattService service = getService(serviceId);
        if (service == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_FOUND));
        }
        BluetoothGattCharacteristic characteristic = getCharacteristic(service, characteristicId);
        if (characteristic == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_CHARACTERISTIC_NOT_FOUND));
        }
        setCharacteristicNotification(characteristic, true);
        callback.success();
    }

    public void stopCharacteristicNotifications(@NonNull StopCharacteristicNotificationsOptions options, @NonNull EmptyCallback callback) {
        String characteristicId = options.getCharacteristicId();
        String serviceId = options.getServiceId();

        BluetoothGattService service = getService(serviceId);
        if (service == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_FOUND));
        }
        BluetoothGattCharacteristic characteristic = getCharacteristic(service, characteristicId);
        if (characteristic == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_CHARACTERISTIC_NOT_FOUND));
        }
        setCharacteristicNotification(characteristic, false);
        callback.success();
    }

    @SuppressLint("MissingPermission")
    public void writeCharacteristic(@NonNull WriteCharacteristicOptions options, @NonNull EmptyCallback callback) {
        String characteristicId = options.getCharacteristicId();
        String serviceId = options.getServiceId();
        int type = options.getType();
        byte[] value = options.getValue();

        BluetoothGattService service = getService(serviceId);
        if (service == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_FOUND));
        }
        BluetoothGattCharacteristic characteristic = getCharacteristic(service, characteristicId);
        if (characteristic == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_CHARACTERISTIC_NOT_FOUND));
        }
        characteristic.setValue(value);
        characteristic.setWriteType(type);
        bluetoothGatt.writeCharacteristic(characteristic);
    }

    @SuppressLint("MissingPermission")
    public void writeDescriptor(@NonNull WriteDescriptorOptions options, @NonNull EmptyCallback callback) {
        String characteristicId = options.getCharacteristicId();
        String descriptorId = options.getDescriptorId();
        String serviceId = options.getServiceId();
        byte[] value = options.getValue();

        BluetoothGattService service = getService(serviceId);
        if (service == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_SERVICE_NOT_FOUND));
        }
        BluetoothGattCharacteristic characteristic = getCharacteristic(service, characteristicId);
        if (characteristic == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_CHARACTERISTIC_NOT_FOUND));
        }
        BluetoothGattDescriptor descriptor = getDescriptor(characteristic, descriptorId);
        if (descriptor == null) {
            callback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_DESCRIPTOR_NOT_FOUND));
        }
        descriptor.setValue(value);
        bluetoothGatt.writeDescriptor(descriptor);
    }

    private BluetoothGattCallback createBluetoothGattCallback() {
        return new BluetoothGattCallback() {
            @SuppressLint("MissingPermission")
            @Override
            public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
                if (newState == BluetoothProfile.STATE_CONNECTED) {
                    if (savedConnectCallback != null) {
                        savedConnectCallback.success();
                        savedConnectCallback = null;
                    }
                } else if (newState == BluetoothProfile.STATE_DISCONNECTED) {
                    bluetoothGatt.close();
                    bluetoothGatt = null;
                    if (savedDisconnectCallback != null) {
                        savedDisconnectCallback.success();
                        savedDisconnectCallback = null;
                    }
                    notifyDeviceDisconnectedListener(gatt);
                }
            }

            @Override
            public void onCharacteristicChanged(
                @NonNull BluetoothGatt gatt,
                @NonNull BluetoothGattCharacteristic characteristic,
                @NonNull byte[] value
            ) {
                invokeHeadlessTaskMethod(
                    getHeadlessTaskMethod("onCharacteristicChanged", BluetoothGatt.class, BluetoothGattCharacteristic.class, byte[].class),
                    getHeadlessTaskObject(),
                    gatt,
                    characteristic,
                    value
                );
                notifyCharacteristicChangedListener(characteristic);
            }

            @Override
            public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                if (savedReadCharacteristicCallback != null) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        ReadCharacteristicResult result = new ReadCharacteristicResult(characteristic);
                        savedReadCharacteristicCallback.success(result);
                    } else {
                        savedReadCharacteristicCallback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_READ_CHARACTERISTIC_FAILED));
                    }
                    savedReadCharacteristicCallback = null;
                }
            }

            @Override
            public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
                if (savedWriteCharacteristicCallback != null) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        savedWriteCharacteristicCallback.success();
                    } else {
                        savedWriteCharacteristicCallback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_WRITE_CHARACTERISTIC_FAILED));
                    }
                    savedWriteCharacteristicCallback = null;
                }
            }

            @Override
            public void onDescriptorRead(
                @NonNull BluetoothGatt gatt,
                @NonNull BluetoothGattDescriptor descriptor,
                int status,
                @NonNull byte[] value
            ) {
                if (savedReadDescriptorCallback != null) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        ReadDescriptorResult result = new ReadDescriptorResult(descriptor);
                        savedReadDescriptorCallback.success(result);
                    } else {
                        savedReadDescriptorCallback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_READ_DESCRIPTOR_FAILED));
                    }
                    savedReadDescriptorCallback = null;
                }
            }

            @Override
            public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
                if (savedWriteDescriptorCallback != null) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        savedWriteDescriptorCallback.success();
                    } else {
                        savedWriteDescriptorCallback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_WRITE_DESCRIPTOR_FAILED));
                    }
                    savedWriteDescriptorCallback = null;
                }
            }

            @Override
            public void onMtuChanged(BluetoothGatt gatt, int mtu, int status) {
                if (savedRequestMtuCallback != null) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        savedRequestMtuCallback.success();
                    } else {
                        savedRequestMtuCallback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_REQUEST_MTU_FAILED));
                    }
                    savedRequestMtuCallback = null;
                }
            }

            @Override
            public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
                if (savedReadRssiCallback != null) {
                    if (status == BluetoothGatt.GATT_SUCCESS) {
                        ReadRssiResult result = new ReadRssiResult(rssi);
                        savedReadRssiCallback.success(result);
                    } else {
                        savedReadRssiCallback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_READ_RSSI_FAILED));
                    }
                    savedReadRssiCallback = null;
                }
            }

            @SuppressLint("MissingPermission")
            @Override
            public void onServiceChanged(BluetoothGatt gatt) {
                if (bluetoothGatt != null) {
                    bluetoothGatt.discoverServices();
                }
            }

            @Override
            public void onServicesDiscovered(BluetoothGatt gatt, int status) {
                if (savedDiscoverServicesCallback != null) {
                    savedDiscoverServicesCallback.success();
                    savedDiscoverServicesCallback = null;
                }
            }
        };
    }

    @NonNull
    private void registerBondBroadcastReceiver() {
        if (bondBroadcastReceiver == null) {
            bondBroadcastReceiver =
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String action = intent.getAction();
                        if (BluetoothDevice.ACTION_BOND_STATE_CHANGED.equals(action)) {
                            BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                            int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, BluetoothDevice.ERROR);
                            if (device != null && device.getAddress().equals(deviceId)) {
                                if (bondState == BluetoothDevice.BOND_BONDED) {
                                    savedCreateBondCallback.success();
                                } else if (bondState == BluetoothDevice.BOND_NONE) {
                                    savedCreateBondCallback.error(new Exception(BluetoothLowEnergyPlugin.ERROR_BOND_FAILED));
                                }
                            }
                        }
                    }
                };
            IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
            service.registerReceiver(bondBroadcastReceiver, filter);
        }
    }

    @Nullable
    private BluetoothGattCharacteristic getCharacteristic(BluetoothGattService service, String characteristicId) {
        return service.getCharacteristic(UUID.fromString(characteristicId));
    }

    @Nullable
    private BluetoothGattDescriptor getDescriptor(BluetoothGattCharacteristic characteristic, String descriptorId) {
        return characteristic.getDescriptor(UUID.fromString(descriptorId));
    }

    private Class<?> getHeadlessTaskClass() {
        try {
            String packageName = service.getApplicationContext().getPackageName();
            return Class.forName(packageName + ".BluetoothLowEnergyHeadlessTask");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Nullable
    private Method getHeadlessTaskMethod(String name, Class<?>... parameterTypes) {
        try {
            Class<?> headlessTaskClass = getHeadlessTaskClass();
            return headlessTaskClass.getMethod(name, parameterTypes);
        } catch (Exception exception) {
            Logger.error(BluetoothLowEnergyPlugin.TAG, "Failed to get headless task method.", exception);
            return null;
        }
    }

    @Nullable
    private Object getHeadlessTaskObject() {
        try {
            Class<?> headlessTaskClass = getHeadlessTaskClass();
            Constructor<?> constructor = headlessTaskClass.getConstructor();
            return constructor.newInstance();
        } catch (Exception exception) {
            Logger.error(BluetoothLowEnergyPlugin.TAG, "Failed to create headless task object.", exception);
            return null;
        }
    }

    @Nullable
    private BluetoothGattService getService(String serviceId) {
        return bluetoothGatt.getService(UUID.fromString(serviceId));
    }

    private void invokeHeadlessTaskMethod(Method method, Object object, Object... args) {
        try {
            method.invoke(object, args);
        } catch (Exception exception) {
            Logger.error(BluetoothLowEnergyPlugin.TAG, "Failed to invoke headless task method.", exception);
        }
    }

    private void notifyCharacteristicChangedListener(BluetoothGattCharacteristic characteristic) {
        CharacteristicChangedEvent event = new CharacteristicChangedEvent(characteristic, deviceId);
        service.notifyCharacteristicChangedListener(event);
    }

    private void notifyDeviceDisconnectedListener(BluetoothGatt gatt) {
        DeviceDisconnectedEvent event = new DeviceDisconnectedEvent(gatt.getDevice());
        service.notifyDeviceDisconnectedListener(event);
    }

    @SuppressLint("MissingPermission")
    private void setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        bluetoothGatt.setCharacteristicNotification(characteristic, enabled);
        // See https://gist.github.com/sam016/4abe921b5a9ee27f67b3686910293026#file-allgattdescriptors-java-L12
        String clientCharacteristicConfig = "00002902-0000-1000-8000-00805f9b34fb";
        BluetoothGattDescriptor descriptor = characteristic.getDescriptor(UUID.fromString(clientCharacteristicConfig));
        if (enabled) {
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
        } else {
            descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
        }
        bluetoothGatt.writeDescriptor(descriptor);
    }
}
