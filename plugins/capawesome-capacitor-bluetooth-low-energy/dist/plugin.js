var capacitorBluetoothLowEnergy = (function (exports, core) {
    'use strict';

    class PromiseQueue {
        constructor() {
            this.queue = new Map();
            this.working = new Map();
        }
        enqueue(key, promise) {
            return new Promise((resolve, reject) => {
                var _a;
                const item = { promise, resolve, reject };
                if (this.queue.has(key)) {
                    (_a = this.queue.get(key)) === null || _a === void 0 ? void 0 : _a.push(item);
                }
                else {
                    this.queue.set(key, [item]);
                }
                this.dequeue(key);
            });
        }
        dequeue(key) {
            var _a;
            if (this.working.get(key)) {
                return;
            }
            const item = (_a = this.queue.get(key)) === null || _a === void 0 ? void 0 : _a.shift();
            if (!item) {
                return;
            }
            try {
                this.working.set(key, true);
                item
                    .promise()
                    .then(value => item.resolve(value))
                    .catch(err => item.reject(err))
                    .finally(() => {
                    this.working.set(key, false);
                    this.dequeue(key);
                });
            }
            catch (error) {
                item.reject(error);
                this.working.set(key, false);
                this.dequeue(key);
            }
        }
    }

    class BluetoothLowEnergyClient {
        constructor(plugin) {
            this.queue = new PromiseQueue();
            this.plugin = plugin;
        }
        async connect(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 10000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.connect(options), timeout));
        }
        async createBond(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 10000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.createBond(options), timeout));
        }
        async disconnect(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.disconnect(options), timeout));
        }
        async discoverServices(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 20000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.discoverServices(options), timeout));
        }
        async getConnectedDevices() {
            return this.plugin.getConnectedDevices();
        }
        async getServices(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.getServices(options), timeout));
        }
        async initialize() {
            return this.plugin.initialize();
        }
        async isBonded(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.isBonded(options), timeout));
        }
        async isEnabled() {
            return this.plugin.isEnabled();
        }
        async openAppSettings() {
            return this.plugin.openAppSettings();
        }
        async openBluetoothSettings() {
            return this.plugin.openBluetoothSettings();
        }
        async openLocationSettings() {
            return this.plugin.openLocationSettings();
        }
        async readCharacteristic(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.readCharacteristic(options), timeout));
        }
        async readDescriptor(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.readDescriptor(options), timeout));
        }
        async readRssi(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.readRssi(options), timeout));
        }
        async requestConnectionPriority(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.requestConnectionPriority(options), timeout));
        }
        async requestMtu(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.requestMtu(options), timeout));
        }
        async startCharacteristicNotifications(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.startCharacteristicNotifications(options), timeout));
        }
        async startScan(options) {
            return this.plugin.startScan(options);
        }
        async startForegroundService(options) {
            return this.plugin.startForegroundService(options);
        }
        async stopCharacteristicNotifications(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.stopCharacteristicNotifications(options), timeout));
        }
        async stopForegroundService() {
            return this.plugin.stopForegroundService();
        }
        async stopScan() {
            return this.plugin.stopScan();
        }
        async writeCharacteristic(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.writeCharacteristic(options), timeout));
        }
        async writeDescriptor(options) {
            var _a;
            const timeout = (_a = options.timeout) !== null && _a !== void 0 ? _a : 5000;
            return this.queue.enqueue(options.deviceId, () => this.timeout(this.plugin.writeDescriptor(options), timeout));
        }
        async checkPermissions() {
            return this.plugin.checkPermissions();
        }
        async requestPermissions() {
            return this.plugin.requestPermissions();
        }
        async addListener(eventName, listenerFunc) {
            return this.plugin.addListener(eventName, listenerFunc);
        }
        async removeAllListeners() {
            return this.plugin.removeAllListeners();
        }
        async timeout(promise, time) {
            return Promise.race([
                promise,
                new Promise((_, reject) => setTimeout(() => {
                    reject(this.createTimeoutException());
                }, time)),
            ]);
        }
        createTimeoutException() {
            return new core.CapacitorException('Operation timed out');
        }
    }

    /**
     * @since 6.0.0
     */
    exports.ConnectionPriority = void 0;
    (function (ConnectionPriority) {
        /**
         * Balanced connection priority.
         *
         * @since 6.0.0
         * @see https://developer.android.com/reference/android/bluetooth/BluetoothGatt#CONNECTION_PRIORITY_BALANCED
         */
        ConnectionPriority[ConnectionPriority["BALANCED"] = 0] = "BALANCED";
        /**
         * High connection priority.
         *
         * @since 6.0.0
         * @see https://developer.android.com/reference/android/bluetooth/BluetoothGatt#CONNECTION_PRIORITY_HIGH
         */
        ConnectionPriority[ConnectionPriority["HIGH"] = 1] = "HIGH";
        /**
         * Low power connection priority.
         *
         * @since 6.0.0
         * @see https://developer.android.com/reference/android/bluetooth/BluetoothGatt#CONNECTION_PRIORITY_LOW_POWER
         */
        ConnectionPriority[ConnectionPriority["LOW_POWER"] = 2] = "LOW_POWER";
        /**
         * Digital Car Key connection priority.
         *
         * @since 6.0.0
         * @see https://developer.android.com/reference/android/bluetooth/BluetoothGatt#CONNECTION_PRIORITY_DCK
         */
        ConnectionPriority[ConnectionPriority["PRIORITY_DCK"] = 3] = "PRIORITY_DCK";
    })(exports.ConnectionPriority || (exports.ConnectionPriority = {}));

    const BluetoothLowEnergy = new BluetoothLowEnergyClient(core.registerPlugin('BluetoothLowEnergy', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.BluetoothLowEnergyWeb()),
    }));

    class BluetoothLowEnergyWeb extends core.WebPlugin {
        async connect(_options) {
            throw this.createUnavailableException();
        }
        async createBond(_options) {
            throw this.createUnavailableException();
        }
        async disconnect() {
            throw this.createUnavailableException();
        }
        async discoverServices() {
            throw this.createUnavailableException();
        }
        async getConnectedDevices() {
            throw this.createUnavailableException();
        }
        async getServices(_options) {
            throw this.createUnavailableException();
        }
        async isBonded(_options) {
            throw this.createUnavailableException();
        }
        async isEnabled() {
            throw this.createUnavailableException();
        }
        async initialize() {
            throw this.createUnavailableException();
        }
        async openAppSettings() {
            throw this.createUnavailableException();
        }
        async openBluetoothSettings() {
            throw this.createUnavailableException();
        }
        async openLocationSettings() {
            throw this.createUnavailableException();
        }
        async readCharacteristic(_options) {
            throw this.createUnavailableException();
        }
        async readDescriptor(_options) {
            throw this.createUnavailableException();
        }
        async readRssi(_options) {
            throw this.createUnavailableException();
        }
        async requestConnectionPriority(_options) {
            throw this.createUnavailableException();
        }
        async requestMtu(_options) {
            throw this.createUnavailableException();
        }
        async startCharacteristicNotifications(_options) {
            throw this.createUnavailableException();
        }
        async startScan() {
            throw this.createUnavailableException();
        }
        async startForegroundService(_options) {
            throw this.createUnavailableException();
        }
        async stopCharacteristicNotifications(_options) {
            throw this.createUnavailableException();
        }
        async stopForegroundService() {
            throw this.createUnavailableException();
        }
        async stopScan() {
            throw this.createUnavailableException();
        }
        async writeCharacteristic(_options) {
            throw this.createUnavailableException();
        }
        async writeDescriptor(_options) {
            throw this.createUnavailableException();
        }
        async checkPermissions() {
            throw this.createUnavailableException();
        }
        async requestPermissions() {
            throw this.createUnavailableException();
        }
        createUnavailableException() {
            return new core.CapacitorException('This plugin method is not available on this platform.', core.ExceptionCode.Unavailable);
        }
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        BluetoothLowEnergyWeb: BluetoothLowEnergyWeb
    });

    exports.BluetoothLowEnergy = BluetoothLowEnergy;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
