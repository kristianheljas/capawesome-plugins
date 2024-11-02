var capacitorWifi = (function (exports, core) {
    'use strict';

    /**
     * @since 6.1.0
     */
    exports.NetworkSecurityType = void 0;
    (function (NetworkSecurityType) {
        /**
         * Unknown security type.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["UNKNOWN"] = -1] = "UNKNOWN";
        /**
         * Open network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["OPEN"] = 0] = "OPEN";
        /**
         * WEP network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["WEP"] = 1] = "WEP";
        /**
         * PSK network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["PSK"] = 2] = "PSK";
        /**
         * EAP network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["EAP"] = 3] = "EAP";
        /**
         * SAE network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["SAE"] = 4] = "SAE";
        /**
         * WPA3-Enterprise in 192-bit security network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["EAP_WPA3_ENTERPRISE_192_BIT"] = 5] = "EAP_WPA3_ENTERPRISE_192_BIT";
        /**
         * OWE network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["OWE"] = 6] = "OWE";
        /**
         * WAPI PSK network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["WAPI_PSK"] = 7] = "WAPI_PSK";
        /**
         * WAPI Certificate network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["WAPI_CERT"] = 8] = "WAPI_CERT";
        /**
         * WPA3-Enterprise network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["WPA3_ENTERPRISE"] = 9] = "WPA3_ENTERPRISE";
        /**
         * OSEN network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["OSEN"] = 10] = "OSEN";
        /**
         * Passpoint R1/R2 network, where TKIP and WEP are not allowed.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["PASSPOINT_R1_R2"] = 11] = "PASSPOINT_R1_R2";
        /**
         * Passpoint R3 network, where TKIP and WEP are not allowed, and PMF must be set to Required.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["PASSPOINT_R3"] = 12] = "PASSPOINT_R3";
        /**
         * Easy Connect (DPP) network.
         *
         * @since 6.1.0
         */
        NetworkSecurityType[NetworkSecurityType["DPP"] = 13] = "DPP";
    })(exports.NetworkSecurityType || (exports.NetworkSecurityType = {}));

    const Wifi = core.registerPlugin('Wifi', {
        web: () => Promise.resolve().then(function () { return web; }).then(m => new m.WifiWeb()),
    });

    class WifiWeb extends core.WebPlugin {
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
            return new core.CapacitorException('Not implemented on web.', core.ExceptionCode.Unimplemented);
        }
    }

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        WifiWeb: WifiWeb
    });

    exports.Wifi = Wifi;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
