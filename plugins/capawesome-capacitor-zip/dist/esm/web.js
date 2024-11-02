import { WebPlugin } from '@capacitor/core';
export class ZipWeb extends WebPlugin {
    async zip(_options) {
        this.throwUnimplementedError();
    }
    async unzip(_options) {
        this.throwUnimplementedError();
    }
    throwUnimplementedError() {
        throw new Error('Not implemented on web.');
    }
}
//# sourceMappingURL=web.js.map