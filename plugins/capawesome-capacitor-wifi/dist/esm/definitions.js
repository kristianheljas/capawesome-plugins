/**
 * @since 6.1.0
 */
export var NetworkSecurityType;
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
})(NetworkSecurityType || (NetworkSecurityType = {}));
//# sourceMappingURL=definitions.js.map