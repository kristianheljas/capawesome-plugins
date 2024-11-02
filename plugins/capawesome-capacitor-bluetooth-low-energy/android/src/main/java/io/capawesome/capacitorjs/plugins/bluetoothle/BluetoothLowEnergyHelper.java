package io.capawesome.capacitorjs.plugins.bluetoothle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.json.JSONArray;
import org.json.JSONException;

public class BluetoothLowEnergyHelper {

    public static String convertBytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02X", b));
        }
        return sb.toString().trim();
    }

    @Nullable
    public static JSONArray convertBytesToJsonArray(@Nullable byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        JSONArray ret = new JSONArray();
        for (byte _byte : bytes) {
            // Convert to unsigned byte
            ret.put(_byte & 0xFF);
        }
        return ret;
    }

    @NonNull
    public static byte[] convertJsonArrayToByteArray(JSONArray json) throws JSONException {
        byte[] bytes = new byte[json.length()];
        for (int i = 0; i < json.length(); i++) {
            bytes[i] = (byte) json.getInt(i);
        }
        return bytes;
    }
}
