/**
 * Copyright (c) 2022 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.nfc;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentFilter;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.NfcAdapter;
import android.nfc.NfcAntennaInfo;
import android.nfc.Tag;
import android.nfc.tech.Ndef;
import android.nfc.tech.NdefFormatable;
import android.nfc.tech.TagTechnology;
import android.os.Build;
import android.os.Parcelable;
import android.provider.Settings;
import android.util.Log;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.getcapacitor.JSArray;
import com.getcapacitor.PluginCall;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONException;
import org.json.JSONObject;

public class Nfc {

    private final String TAG = "Nfc";
    private final NfcPlugin plugin;

    @Nullable
    private final NfcAdapter nfcAdapter;

    private final PendingIntent pendingIntent;
    private final ArrayList<IntentFilter> intentFilterList = new ArrayList<>();
    private final ArrayList<String[]> techTypesList = new ArrayList<>();

    @Nullable
    private Intent lastIntent = null;

    @Nullable
    private Class<?> tagTechnologyClass = null;

    @Nullable
    private TagTechnology tagTechnology = null;

    public Nfc(NfcPlugin plugin) {
        this.plugin = plugin;
        this.nfcAdapter = NfcAdapter.getDefaultAdapter(plugin.getActivity());
        this.pendingIntent = createPendingIntent();
    }

    public void handleOnPause() {
        disableForegroundDispatch();
    }

    public void handleOnResume() {
        enableForegroundDispatch();
    }

    public void handleOnNewIntent(Intent intent) {
        String action = intent.getAction();
        if (action == null || action.trim().isEmpty()) {
            return;
        }
        this.lastIntent = intent;
        if (action.equals(NfcAdapter.ACTION_NDEF_DISCOVERED)) {
            this.handleNdefDiscoveredIntent(intent);
        } else if (action.equals(NfcAdapter.ACTION_TECH_DISCOVERED)) {
            this.handleTechDiscoveredIntent(intent);
        } else if (action.equals(NfcAdapter.ACTION_TAG_DISCOVERED)) {
            this.handleTagDiscoveredIntent(intent);
        }
    }

    public void startScanSession(@Nullable List<String> techTypes, @Nullable List<String> mimeTypes)
        throws IntentFilter.MalformedMimeTypeException {
        if (techTypes != null && techTypes.size() > 0) {
            intentFilterList.add(new IntentFilter(NfcAdapter.ACTION_TECH_DISCOVERED));
            for (String techType : techTypes) {
                techTypesList.add(new String[] { techType });
            }
        }
        if (mimeTypes != null) {
            for (String mimeType : mimeTypes) {
                IntentFilter intentFilter = createIntentFilter(mimeType);
                intentFilterList.add(intentFilter);
            }
        }
        disableAndEnableForegroundDispatch();
    }

    public void stopScanSession() {
        intentFilterList.clear();
        techTypesList.clear();
        disableForegroundDispatch();
    }

    public void write(NdefMessage message) throws Exception {
        if (lastIntent == null) {
            throw new Exception(NfcPlugin.ERROR_NO_TAG_DETECTED);
        }
        Tag tag = lastIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            throw new Exception(NfcPlugin.ERROR_NO_TAG_DETECTED);
        }
        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            NdefFormatable ndefFormatable = NdefFormatable.get(tag);
            if (ndefFormatable == null) {
                throw new Exception(NfcPlugin.ERROR_NDEF_NOT_SUPPORTED);
            } else {
                throw new Exception(NfcPlugin.ERROR_NOT_NDEF_FORMATTED);
            }
        } else {
            ndef.connect();
            if (ndef.isWritable()) {
                int messageSize = message.toByteArray().length;
                int maxMessageSize = ndef.getMaxSize();
                if (maxMessageSize < messageSize) {
                    throw new Exception(NfcPlugin.ERROR_MESSAGE_SIZE_EXCEEDED);
                } else {
                    ndef.writeNdefMessage(message);
                }
            } else {
                throw new Exception(NfcPlugin.ERROR_TAG_READONLY);
            }
            ndef.close();
        }
    }

    public void makeReadOnly() throws Exception {
        if (lastIntent == null) {
            throw new Exception(NfcPlugin.ERROR_NO_TAG_DETECTED);
        }
        Tag tag = lastIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            throw new Exception(NfcPlugin.ERROR_NO_TAG_DETECTED);
        }
        Ndef ndef = Ndef.get(tag);
        if (ndef == null) {
            throw new Exception(NfcPlugin.ERROR_NDEF_NOT_SUPPORTED);
        }

        ndef.connect();
        if (ndef.canMakeReadOnly()) {
            boolean successful = ndef.makeReadOnly();
            if (successful == false) {
                throw new Exception(NfcPlugin.ERROR_TAG_READONLY_FAILED);
            }
        } else {
            throw new Exception(NfcPlugin.ERROR_TAG_READONLY_FAILED);
        }
        ndef.close();
    }

    public void erase() throws Exception {
        NdefRecord record = NfcHelper.createEmptyNdefRecord();
        NdefMessage ndefMessage = new NdefMessage(record);
        this.write(ndefMessage);
    }

    public void format() throws Exception {
        if (lastIntent == null) {
            throw new Exception(NfcPlugin.ERROR_NO_TAG_DETECTED);
        }
        Tag tag = lastIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            throw new Exception(NfcPlugin.ERROR_NO_TAG_DETECTED);
        }

        NdefRecord ndefRecord = NfcHelper.createEmptyNdefRecord();
        NdefMessage ndefMessage = new NdefMessage(ndefRecord);

        NdefFormatable ndefFormatable = NdefFormatable.get(tag);
        if (ndefFormatable == null) {
            this.write(ndefMessage);
        } else {
            ndefFormatable.connect();
            ndefFormatable.format(ndefMessage);
            ndefFormatable.close();
        }
    }

    public byte[] transceive(byte[] data) throws Exception {
        if (tagTechnology == null || !tagTechnology.isConnected() || tagTechnologyClass == null) {
            throw new Exception(NfcPlugin.ERROR_TAG_NOT_CONNECTED);
        }
        Method transceiveMethod = tagTechnologyClass.getMethod("transceive", byte[].class);
        byte[] response = (byte[]) transceiveMethod.invoke(tagTechnology, data);
        return response;
    }

    public void connect(String techType) throws Exception {
        if (lastIntent == null) {
            throw new Exception(NfcPlugin.ERROR_NO_TAG_DETECTED);
        }
        Tag tag = lastIntent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            throw new Exception(NfcPlugin.ERROR_NO_TAG_DETECTED);
        }
        String tagTechClassName = NfcHelper.getTagTechClassNameByType(techType);
        if (tagTechClassName == null) {
            throw new Exception(NfcPlugin.ERROR_TECH_TYPE_UNSUPPORTED);
        }
        List<String> techList = Arrays.asList(tag.getTechList());
        if (techList.contains(tagTechClassName)) {
            tagTechnologyClass = Class.forName(tagTechClassName);
            Method getMethod = tagTechnologyClass.getMethod("get", Tag.class);
            tagTechnology = (TagTechnology) getMethod.invoke(null, tag);
            tagTechnology.connect();
        } else {
            throw new Exception(NfcPlugin.ERROR_TECH_TYPE_UNSUPPORTED);
        }
    }

    public void close() throws Exception {
        if (tagTechnology != null) {
            if (tagTechnology.isConnected()) {
                tagTechnology.close();
            }
        }
        tagTechnology = null;
        tagTechnologyClass = null;
    }

    public boolean isSupported() {
        if (nfcAdapter == null) {
            return false;
        }
        return true;
    }

    public boolean isEnabled() {
        if (nfcAdapter == null) {
            return false;
        }
        return nfcAdapter.isEnabled();
    }

    public void openSettings(PluginCall call) {
        Intent intent;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            intent = new Intent(Settings.ACTION_NFC_SETTINGS);
        } else {
            intent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
        }
        plugin.startActivityForResult(call, intent, "openSettingsResult");
    }

    @RequiresApi(api = Build.VERSION_CODES.UPSIDE_DOWN_CAKE)
    public NfcAntennaInfo getAntennaInfo() {
        if (nfcAdapter == null) {
            return null;
        }
        return nfcAdapter.getNfcAntennaInfo();
    }

    private void enableForegroundDispatch() {
        if (nfcAdapter == null) {
            return;
        }
        IntentFilter[] intentFilters = getIntentFilters();
        String[][] techLists = getTechLists();
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                try {
                    nfcAdapter.enableForegroundDispatch(plugin.getActivity(), pendingIntent, intentFilters, techLists);
                } catch (Exception exception) {
                    Log.e(TAG, "enableForegroundDispatch failed.", exception);
                }
            });
    }

    private void disableForegroundDispatch() {
        if (nfcAdapter == null) {
            return;
        }
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                try {
                    nfcAdapter.disableForegroundDispatch(plugin.getActivity());
                } catch (Exception exception) {
                    Log.e(TAG, "disableForegroundDispatch failed.", exception);
                }
            });
    }

    private void disableAndEnableForegroundDispatch() {
        disableForegroundDispatch();
        enableForegroundDispatch();
    }

    private IntentFilter createIntentFilter(String mimeType) throws IntentFilter.MalformedMimeTypeException {
        IntentFilter intentFilter = new IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED);
        intentFilter.addDataType(mimeType);
        return intentFilter;
    }

    private IntentFilter[] getIntentFilters() {
        return intentFilterList.toArray(new IntentFilter[intentFilterList.size()]);
    }

    private String[][] getTechLists() {
        return techTypesList.toArray(new String[0][0]);
    }

    private PendingIntent createPendingIntent() {
        Activity activity = plugin.getActivity();
        Intent intent = new Intent(activity, activity.getClass());
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            return PendingIntent.getActivity(activity, 0, intent, PendingIntent.FLAG_MUTABLE);
        } else {
            return PendingIntent.getActivity(activity, 0, intent, 0);
        }
    }

    private void handleNdefDiscoveredIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            return;
        }
        Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);

        try {
            JSONObject result = NfcHelper.createReadingResultForNdefTag(tag, messages[0]);
            this.plugin.notifyNfcTagScannedListener(result);
        } catch (JSONException exception) {
            exception.printStackTrace();
            this.plugin.notifyScanSessionErrorListener(exception.getMessage());
        }
    }

    private void handleTechDiscoveredIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            return;
        }
        Parcelable[] messages = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES);
        List tagTechList = Arrays.asList(tag.getTechList());

        try {
            if (tagTechList.contains(Ndef.class.getName())) {
                JSONObject result = NfcHelper.createReadingResultForNdefTag(tag, messages[0]);
                this.plugin.notifyNfcTagScannedListener(result);
            } else {
                JSONObject result = NfcHelper.createReadingResultForNdefFormatableTag(tag);
                this.plugin.notifyNfcTagScannedListener(result);
            }
        } catch (JSONException exception) {
            exception.printStackTrace();
            this.plugin.notifyScanSessionErrorListener(exception.getMessage());
        }
    }

    private void handleTagDiscoveredIntent(Intent intent) {
        Tag tag = intent.getParcelableExtra(NfcAdapter.EXTRA_TAG);
        if (tag == null) {
            return;
        }

        try {
            JSONObject result = NfcHelper.createReadingResultForNdefFormatableTag(tag);
            this.plugin.notifyNfcTagScannedListener(result);
        } catch (JSONException exception) {
            exception.printStackTrace();
            this.plugin.notifyScanSessionErrorListener(exception.getMessage());
        }
    }
}
