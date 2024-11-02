import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';
export class BluetoothLowEnergyWeb extends WebPlugin {
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
        return new CapacitorException('This plugin method is not available on this platform.', ExceptionCode.Unavailable);
    }
}
//# sourceMappingURL=web.js.map