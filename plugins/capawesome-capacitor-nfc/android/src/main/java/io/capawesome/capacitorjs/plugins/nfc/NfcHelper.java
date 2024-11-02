/**
 * Copyright (c) 2022 Robin Genz
 */
package io.capawesome.capacitorjs.plugins.nfc;

import android.nfc.AvailableNfcAntenna;
import android.nfc.NdefMessage;
import android.nfc.NdefRecord;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.nfc.tech.MifareClassic;
import android.nfc.tech.MifareUltralight;
import android.nfc.tech.Ndef;
import android.nfc.tech.NfcA;
import android.nfc.tech.NfcB;
import android.nfc.tech.NfcBarcode;
import android.nfc.tech.NfcF;
import android.nfc.tech.NfcV;
import android.os.Build;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import com.getcapacitor.JSArray;
import com.getcapacitor.JSObject;
import com.getcapacitor.Logger;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class NfcHelper {

    public static JSONObject createReadingResultForNdefTag(Tag tag, @Nullable Parcelable rawMessage) throws JSONException {
        JSONArray tagId = convertByteArrayToJsonArray(tag.getId());
        JSONObject message = createJsonObjectFromRawNdefMessage(rawMessage);
        JSONArray techTypes = new JSONArray(mapTagTechClassNamesToTypes(tag.getTechList()));

        JSONObject result = new JSONObject();
        if (tagId != null) {
            result.put("id", tagId);
        }
        result.put("message", message);
        result.put("techTypes", techTypes);

        Ndef ndef = Ndef.get(tag);
        if (ndef != null) {
            String type = mapNdefTagTypeToResultType(ndef.getType());

            try {
                result.put("canMakeReadOnly", ndef.canMakeReadOnly());
            } catch (Exception exception) {
                Logger.error(NfcPlugin.TAG, "Error while calling Ndef.canMakeReadOnly()", exception);
            }
            result.put("isWritable", ndef.isWritable());
            result.put("maxSize", ndef.getMaxSize());
            result.put("type", type);
        }

        NfcA nfca = NfcA.get(tag);
        if (nfca != null) {
            JSONArray atqa = convertByteArrayToJsonArray(nfca.getAtqa());
            JSONArray sak = convertByteArrayToJsonArray(convertShortToByteArray(nfca.getSak()));

            if (atqa != null) {
                result.put("atqa", atqa);
            }
            if (sak != null) {
                result.put("sak", sak);
            }
        }

        NfcB nfcb = NfcB.get(tag);
        if (nfcb != null) {
            JSONArray applicationData = convertByteArrayToJsonArray(nfcb.getApplicationData());
            JSONArray protocolInfo = convertByteArrayToJsonArray(nfcb.getProtocolInfo());

            if (applicationData != null) {
                result.put("applicationData", applicationData);
            }
            if (protocolInfo != null) {
                result.put("protocolInfo", protocolInfo);
            }
        }

        NfcF nfcf = NfcF.get(tag);
        if (nfcf != null) {
            JSONArray manufacturer = convertByteArrayToJsonArray(nfcf.getManufacturer());
            JSONArray systemCode = convertByteArrayToJsonArray(nfcf.getManufacturer());

            if (manufacturer != null) {
                result.put("manufacturer", manufacturer);
            }
            if (systemCode != null) {
                result.put("systemCode", systemCode);
            }
        }

        NfcV nfcv = NfcV.get(tag);
        if (nfcv != null) {
            JSONArray dsfId = convertByteArrayToJsonArray(new byte[] { nfcv.getDsfId() });
            JSONArray responseFlags = convertByteArrayToJsonArray(new byte[] { nfcv.getResponseFlags() });

            if (dsfId != null) {
                result.put("dsfId", dsfId);
            }
            if (responseFlags != null) {
                result.put("responseFlags", responseFlags);
            }
        }

        IsoDep isoDep = IsoDep.get(tag);
        if (isoDep != null) {
            JSONArray hiLayerResponse = convertByteArrayToJsonArray(isoDep.getHiLayerResponse());
            JSONArray historicalBytes = convertByteArrayToJsonArray(isoDep.getHistoricalBytes());

            if (hiLayerResponse != null) {
                result.put("hiLayerResponse", hiLayerResponse);
            }
            if (historicalBytes != null) {
                result.put("historicalBytes", historicalBytes);
            }
        }

        MifareClassic mifareClassic = MifareClassic.get(tag);
        if (mifareClassic != null) {
            int blockCount = mifareClassic.getBlockCount();
            int sectorCount = mifareClassic.getSectorCount();
            int size = mifareClassic.getSize();
            String type = mapMifareClassicTagTypeToResultType(mifareClassic.getType());

            result.put("blockCount", blockCount);
            result.put("sectorCount", sectorCount);
            result.put("size", size);
            result.put("type", type);
        }

        MifareUltralight mifareUltralight = MifareUltralight.get(tag);
        if (mifareUltralight != null) {
            String type = mapMifareUltralightTagTypeToResultType(mifareUltralight.getType());

            result.put("type", type);
        }

        NfcBarcode nfcBarcode = NfcBarcode.get(tag);
        if (nfcBarcode != null) {
            JSONArray barcode = convertByteArrayToJsonArray(nfcBarcode.getBarcode());

            if (barcode != null) {
                result.put("barcode", barcode);
            }
        }

        return result;
    }

    public static JSONObject createReadingResultForNdefFormatableTag(Tag tag) throws JSONException {
        JSONArray tagId = convertByteArrayToJsonArray(tag.getId());
        JSONArray techTypes = new JSONArray(mapTagTechClassNamesToTypes(tag.getTechList()));

        JSONObject result = new JSONObject();
        if (tagId != null) {
            result.put("id", tagId);
        }
        result.put("techTypes", techTypes);
        return result;
    }

    public static NdefMessage createNdefMessageFromJsonObject(@NonNull JSONObject jsonMessage) throws JSONException {
        JSONArray jsonRecords = jsonMessage.getJSONArray("records");
        NdefRecord[] records = new NdefRecord[jsonRecords.length()];
        for (int i = 0; i < jsonRecords.length(); i++) {
            JSONObject jsonRecord = (JSONObject) jsonRecords.get(i);
            short tnf = (short) jsonRecord.getInt("tnf");
            byte[] type = convertJsonArrayToByteArray(jsonRecord.getJSONArray("type"));
            byte[] id = convertJsonArrayToByteArray(jsonRecord.getJSONArray("id"));
            byte[] payload = convertJsonArrayToByteArray(jsonRecord.getJSONArray("payload"));
            NdefRecord record = new NdefRecord(tnf, type, id, payload);
            records[i] = record;
        }
        return new NdefMessage(records);
    }

    @Nullable
    public static String getTagTechClassNameByType(String type) {
        return mapTypeToTagTechClassName(type);
    }

    @Nullable
    public static JSONArray convertByteArrayToJsonArray(@Nullable byte[] bytes) {
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

    public static byte[] convertJsonArrayToByteArray(JSONArray json) throws JSONException {
        byte[] bytes = new byte[json.length()];
        for (int i = 0; i < json.length(); i++) {
            bytes[i] = (byte) json.getInt(i);
        }
        return bytes;
    }

    public static NdefRecord createEmptyNdefRecord() {
        return new NdefRecord(NdefRecord.TNF_EMPTY, new byte[0], new byte[0], new byte[0]);
    }

    private static JSONObject createJsonObjectFromRawNdefMessage(@Nullable Parcelable rawMessage) throws JSONException {
        JSONObject jsonObject = new JSONObject();
        if (rawMessage == null) {
            return jsonObject;
        }
        NdefMessage ndefMessage = (NdefMessage) rawMessage;
        NdefRecord[] ndefRecords = ndefMessage.getRecords();
        JSONArray records = createJsonArrayFromNdefRecords(ndefRecords);
        jsonObject.put("records", records);
        return jsonObject;
    }

    private static JSONArray createJsonArrayFromNdefRecords(@NonNull NdefRecord[] ndefRecords) throws JSONException {
        List<JSONObject> list = new ArrayList();
        for (NdefRecord ndefRecord : ndefRecords) {
            list.add(createJsonObjectFromNdefRecord(ndefRecord));
        }
        return new JSONArray(list);
    }

    private static JSONObject createJsonObjectFromNdefRecord(NdefRecord record) throws JSONException {
        JSONObject ret = new JSONObject();
        JSONArray id = convertByteArrayToJsonArray(record.getId());
        JSONArray type = convertByteArrayToJsonArray(record.getType());
        JSONArray payload = convertByteArrayToJsonArray(record.getPayload());

        if (id != null) {
            ret.put("id", id);
        }
        if (type != null) {
            ret.put("type", type);
        }
        if (payload != null) {
            ret.put("payload", payload);
        }
        ret.put("tnf", record.getTnf());
        return ret;
    }

    private static List<String> mapTagTechClassNamesToTypes(String[] techClassNames) {
        List<String> resultTypes = new ArrayList();
        for (String techClassName : techClassNames) {
            String type = mapTagTechClassNameToType(techClassName);
            if (type == null) {
                continue;
            }
            resultTypes.add(type);
        }
        return resultTypes;
    }

    @Nullable
    private static String mapTagTechClassNameToType(String techClassName) {
        String type = null;
        if (techClassName.equals("android.nfc.tech.NfcA")) {
            type = "NFC_A";
        } else if (techClassName.equals("android.nfc.tech.NfcB")) {
            type = "NFC_B";
        } else if (techClassName.equals("android.nfc.tech.NfcF")) {
            type = "NFC_F";
        } else if (techClassName.equals("android.nfc.tech.NfcV")) {
            type = "NFC_V";
        } else if (techClassName.equals("android.nfc.tech.IsoDep")) {
            type = "ISO_DEP";
        } else if (techClassName.equals("android.nfc.tech.Ndef")) {
            type = "NDEF";
        } else if (techClassName.equals("android.nfc.tech.MifareClassic")) {
            type = "MIFARE_CLASSIC";
        } else if (techClassName.equals("android.nfc.tech.MifareUltralight")) {
            type = "MIFARE_ULTRALIGHT";
        } else if (techClassName.equals("android.nfc.tech.NfcBarcode")) {
            type = "NFC_BARCODE";
        } else if (techClassName.equals("android.nfc.tech.NdefFormatable")) {
            type = "NDEF_FORMATABLE";
        }
        return type;
    }

    @Nullable
    private static String mapTypeToTagTechClassName(String type) {
        String techClassName = null;
        if (type.equals("NFC_A")) {
            techClassName = "android.nfc.tech.NfcA";
        } else if (type.equals("NFC_B")) {
            techClassName = "android.nfc.tech.NfcB";
        } else if (type.equals("NFC_F")) {
            techClassName = "android.nfc.tech.NfcF";
        } else if (type.equals("NFC_V")) {
            techClassName = "android.nfc.tech.NfcV";
        } else if (type.equals("ISO_DEP")) {
            techClassName = "android.nfc.tech.IsoDep";
        } else if (type.equals("NDEF")) {
            techClassName = "android.nfc.tech.Ndef";
        } else if (type.equals("MIFARE_CLASSIC")) {
            techClassName = "android.nfc.tech.MifareClassic";
        } else if (type.equals("MIFARE_ULTRALIGHT")) {
            techClassName = "android.nfc.tech.MifareUltralight";
        } else if (type.equals("NFC_BARCODE")) {
            techClassName = "android.nfc.tech.NfcBarcode";
        } else if (type.equals("NDEF_FORMATABLE")) {
            techClassName = "android.nfc.tech.NdefFormatable";
        }
        return techClassName;
    }

    private static String mapNdefTagTypeToResultType(String tagTyp) {
        String resultType = tagTyp;
        if (tagTyp.equals(Ndef.NFC_FORUM_TYPE_1)) {
            resultType = "NFC_FORUM_TYPE_1";
        } else if (tagTyp.equals(Ndef.NFC_FORUM_TYPE_2)) {
            resultType = "NFC_FORUM_TYPE_2";
        } else if (tagTyp.equals(Ndef.NFC_FORUM_TYPE_3)) {
            resultType = "NFC_FORUM_TYPE_3";
        } else if (tagTyp.equals(Ndef.NFC_FORUM_TYPE_4)) {
            resultType = "NFC_FORUM_TYPE_4";
        } else if (tagTyp.equals(Ndef.MIFARE_CLASSIC)) {
            resultType = "MIFARE_CLASSIC";
        }
        return resultType;
    }

    private static String mapMifareClassicTagTypeToResultType(int tagTyp) {
        switch (tagTyp) {
            case MifareClassic.TYPE_PLUS:
                return "MIFARE_PLUS";
            case MifareClassic.TYPE_PRO:
                return "MIFARE_PRO";
            default:
                return "MIFARE_CLASSIC";
        }
    }

    private static String mapMifareUltralightTagTypeToResultType(int tagTyp) {
        switch (tagTyp) {
            case MifareUltralight.TYPE_ULTRALIGHT_C:
                return "MIFARE_ULTRALIGHT_C";
            default:
                return "MIFARE_ULTRALIGHT";
        }
    }

    private static byte[] convertShortToByteArray(short bytes) {
        byte[] byteArray = new byte[2];
        byteArray[0] = (byte) (bytes & 0xff);
        byteArray[1] = (byte) ((bytes >> 8) & 0xff);
        return byteArray;
    }
}
