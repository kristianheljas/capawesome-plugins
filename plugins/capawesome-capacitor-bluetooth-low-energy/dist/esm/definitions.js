/**
 * @since 6.0.0
 */
export var ConnectionPriority;
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
})(ConnectionPriority || (ConnectionPriority = {}));
//# sourceMappingURL=definitions.js.map