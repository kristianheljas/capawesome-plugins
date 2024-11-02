import { CapacitorException } from '@capacitor/core';
import { PromiseQueue } from './queue';
export class BluetoothLowEnergyClient {
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
        return new CapacitorException('Operation timed out');
    }
}
//# sourceMappingURL=client.js.map