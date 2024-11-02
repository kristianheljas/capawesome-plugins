import { WebPlugin } from '@capacitor/core';
export class PrinterWeb extends WebPlugin {
    async printHtml(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
    async printPdf(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
    async printWebView(_options) {
        throw this.unimplemented('Not implemented on web.');
    }
}
//# sourceMappingURL=web.js.map