import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';
export class WifiWeb extends WebPlugin {
    async connect(_options) {
        throw this.createUnimplementedException();
    }
    async disconnect() {
        throw this.createUnimplementedException();
    }
    async getAvailableNetworks() {
        throw this.createUnimplementedException();
    }
    async getIpAddress() {
        throw this.createUnimplementedException();
    }
    async getRssi() {
        throw this.createUnimplementedException();
    }
    async getSsid() {
        throw this.createUnimplementedException();
    }
    async isEnabled() {
        throw this.createUnimplementedException();
    }
    async startScan() {
        throw this.createUnimplementedException();
    }
    async checkPermissions() {
        throw this.createUnimplementedException();
    }
    async requestPermissions(_options) {
        throw this.createUnimplementedException();
    }
    createUnimplementedException() {
        return new CapacitorException('Not implemented on web.', ExceptionCode.Unimplemented);
    }
}
//# sourceMappingURL=web.js.map