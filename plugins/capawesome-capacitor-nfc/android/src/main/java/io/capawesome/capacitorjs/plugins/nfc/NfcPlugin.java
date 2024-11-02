/**
 * Copyright (c) 2022 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.nfc;

import android.content.Intent;
import android.nfc.AvailableNfcAntenna;
import android.nfc.NdefMessage;
import android.nfc.NfcAntennaInfo;
import android.os.Build;
import android.util.Log;
import androidx.activity.result.ActivityResult;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.ActivityCallback;
import com.getcapacitor.annotation.CapacitorPlugin;
import com.getcapacitor.annotation.Permission;
import java.util.List;
import org.json.JSONObject;

@CapacitorPlugin(name = "Nfc", permissions = @Permission(strings = {}, alias = "nfc"))
public class NfcPlugin extends Plugin {

    public static final String TAG = "NfcPlugin";

    public static final String NFC_TAG_SCANNED_EVENT = "nfcTagScanned";
    public static final String NFC_SCAN_SESSION_ERROR_EVENT = "scanSessionError";
    public static final String ERROR_UNKNOWN_ERROR = "An unknown error has occurred.";
    public static final String ERROR_NO_TAG_DETECTED = "No NFC tag was detected.";
    public static final String ERROR_NDEF_NOT_SUPPORTED = "The NFC tag does not support NDEF tag technology.";
    public static final String ERROR_NOT_NDEF_FORMATTED = "The NFC tag has not yet been formatted as NDEF.";
    public static final String ERROR_MESSAGE_SIZE_EXCEEDED = "The maximum message size of the NFC tag was exceeded.";
    public static final String ERROR_TAG_READONLY = "The tag is read-only.";
    public static final String ERROR_TAG_READONLY_FAILED = "It is not possible to make this tag read-only.";
    public static final String ERROR_TECH_TYPE_MISSING = "techType must be provided";
    public static final String ERROR_DATA_MISSING = "data must be provided";
    public static final String ERROR_TECH_TYPE_UNSUPPORTED = "The NFC tag does not support the selected techType.";
    public static final String ERROR_TAG_NOT_CONNECTED = "The tag is not connected.";
    public static final String ERROR_ANTENNA_INFO_NOT_AVAILABLE = "The antenna info is not available.";

    private Nfc implementation;

    @Override
    public void load() {
        try {
            implementation = new Nfc(this);
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
        }
    }

    @Override
    protected void handleOnPause() {
        try {
            super.handleOnPause();
            implementation.handleOnPause();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
        }
    }

    @Override
    protected void handleOnResume() {
        try {
            super.handleOnResume();
            implementation.handleOnResume();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
        }
    }

    @Override
    protected void handleOnNewIntent(Intent intent) {
        try {
            super.handleOnNewIntent(intent);
            implementation.handleOnNewIntent(intent);
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
        }
    }

    @PluginMethod
    public void startScanSession(PluginCall call) {
        try {
            List<String> techTypes = null;
            try {
                techTypes = call.getArray("techTypes").toList();
            } catch (Exception ex) {}
            List<String> mimeTypes = null;
            try {
                mimeTypes = call.getArray("mimeTypes").toList();
            } catch (Exception ex) {}

            implementation.startScanSession(techTypes, mimeTypes);
            call.resolve();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void stopScanSession(PluginCall call) {
        try {
            implementation.stopScanSession();
            call.resolve();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void write(PluginCall call) {
        try {
            JSObject jsonMessage = call.getObject("message", new JSObject());

            NdefMessage message = NfcHelper.createNdefMessageFromJsonObject(jsonMessage);
            implementation.write(message);
            call.resolve();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void makeReadOnly(PluginCall call) {
        try {
            implementation.makeReadOnly();
            call.resolve();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void erase(PluginCall call) {
        try {
            implementation.erase();
            call.resolve();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void format(PluginCall call) {
        try {
            implementation.format();
            call.resolve();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void transceive(PluginCall call) {
        try {
            JSArray dataOption = call.getArray("data");
            if (dataOption == null) {
                call.reject(ERROR_DATA_MISSING);
                return;
            }
            byte[] data = NfcHelper.convertJsonArrayToByteArray(dataOption);
            byte[] response = implementation.transceive(data);

            JSObject ret = new JSObject();
            ret.put("response", NfcHelper.convertByteArrayToJsonArray(response));
            call.resolve(ret);
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void connect(PluginCall call) {
        try {
            String techType = call.getString("techType");
            if (techType == null) {
                call.reject(ERROR_TECH_TYPE_MISSING);
                return;
            }
            implementation.connect(techType);
            call.resolve();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void close(PluginCall call) {
        try {
            implementation.close();
            call.resolve();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void isSupported(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            ret.put("isSupported", implementation.isSupported());
            call.resolve(ret);
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void isEnabled(PluginCall call) {
        try {
            JSObject ret = new JSObject();
            ret.put("isEnabled", implementation.isEnabled());
            call.resolve(ret);
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void openSettings(PluginCall call) {
        try {
            implementation.openSettings(call);
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void getAntennaInfo(PluginCall call) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
                NfcAntennaInfo nfcAntennaInfo = implementation.getAntennaInfo();
                if (nfcAntennaInfo == null) {
                    call.reject(ERROR_ANTENNA_INFO_NOT_AVAILABLE);
                    return;
                }

                JSArray availableAntennasResult = new JSArray();
                for (AvailableNfcAntenna antenna : nfcAntennaInfo.getAvailableNfcAntennas()) {
                    JSObject availableAntennaResult = new JSObject();
                    availableAntennaResult.put("locationX", antenna.getLocationX());
                    availableAntennaResult.put("locationY", antenna.getLocationY());
                    availableAntennasResult.put(availableAntennaResult);
                }

                JSObject result = new JSObject();
                result.put("availableAntennas", availableAntennasResult);
                result.put("deviceHeight", nfcAntennaInfo.getDeviceHeight());
                result.put("deviceWidth", nfcAntennaInfo.getDeviceWidth());
                result.put("isDeviceFoldable", nfcAntennaInfo.isDeviceFoldable());
                call.resolve(result);
            } else {
                call.reject(ERROR_ANTENNA_INFO_NOT_AVAILABLE);
            }
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void setAlertMessage(PluginCall call) {
        call.unimplemented();
    }

    @ActivityCallback
    public void openSettingsResult(PluginCall call, ActivityResult result) {
        try {
            if (call == null) {
                Log.d(TAG, "openSettingsResult was called with empty call parameter.");
                return;
            }
            call.resolve();
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
        }
    }

    public void notifyNfcTagScannedListener(JSONObject nfcTag) {
        try {
            JSObject result = new JSObject();
            result.put("nfcTag", nfcTag);
            notifyListeners(NFC_TAG_SCANNED_EVENT, result, true);
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
        }
    }

    public void notifyScanSessionErrorListener(String errorMessage) {
        try {
            JSObject result = new JSObject();
            result.put("message", errorMessage);
            notifyListeners(NFC_SCAN_SESSION_ERROR_EVENT, result);
        } catch (Exception ex) {
            String message = ex.getMessage();
            if (message == null) {
                message = ERROR_UNKNOWN_ERROR;
            }
            Log.e(TAG, message);
        }
    }
}
