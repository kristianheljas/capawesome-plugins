package io.capawesome.capacitorjs.plugins.wifi;

public class WifiHelper {

    public static String convertIpAddressIntToString(int ipAddress) {
        return ((ipAddress & 0xFF) + "." + ((ipAddress >> 8) & 0xFF) + "." + ((ipAddress >> 16) & 0xFF) + "." + ((ipAddress >> 24) & 0xFF));
    }
}
