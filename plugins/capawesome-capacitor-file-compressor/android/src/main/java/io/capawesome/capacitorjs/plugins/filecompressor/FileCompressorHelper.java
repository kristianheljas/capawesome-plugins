package io.capawesome.capacitorjs.plugins.filecompressor;

import android.graphics.Bitmap;

public class FileCompressorHelper {

    public static Bitmap.CompressFormat mapMimeTypeToBitmapCompressFormat(String mimeType) {
        switch (mimeType) {
            case "image/webp":
                return Bitmap.CompressFormat.WEBP;
            default:
                return Bitmap.CompressFormat.JPEG;
        }
    }

    public static String mapMimeTypeToExtension(String mimeType) {
        switch (mimeType) {
            case "image/webp":
                return "webp";
            default:
                return "jpeg";
        }
    }
}
