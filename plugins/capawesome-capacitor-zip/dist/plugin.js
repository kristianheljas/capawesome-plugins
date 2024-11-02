var capacitorZip = (function (exports, core) {
    'use strict';

    const Zip = core.registerPlugin('Zip', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.ZipWeb()),
    });

    class ZipWeb extends core.WebPlugin {
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

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        ZipWeb: ZipWeb
    });

    exports.Zip = Zip;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
