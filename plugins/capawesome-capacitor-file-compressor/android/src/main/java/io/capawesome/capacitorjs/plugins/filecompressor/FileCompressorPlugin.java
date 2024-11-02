package io.capawesome.capacitorjs.plugins.filecompressor;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import java.io.File;

@CapacitorPlugin(name = "FileCompressor")
public class FileCompressorPlugin extends Plugin {

    public static final String TAG = "FileCompressorPlugin";

    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_MIME_TYPE_NOT_SUPPORTED = "mimeType is not supported.";
    public static final String ERROR_FILE_NOT_EXIST = "File does not exist.";

    private FileCompressor implementation;

    @Override
    public void load() {
        implementation = new FileCompressor(this);
    }

    @PluginMethod
    public void compressImage(PluginCall call) {
        try {
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }
            String mimeType = call.getString("mimeType", "image/jpeg");
            if (!mimeType.equals("image/jpeg") && !mimeType.equals("image/webp")) {
                call.reject(ERROR_MIME_TYPE_NOT_SUPPORTED);
                return;
            }
            double quality = call.getDouble("quality", 0.6);

            Uri uri = implementation.getUriByPath(path);
            boolean fileExists = implementation.isFileExists(uri);
            if (!fileExists) {
                call.reject(ERROR_FILE_NOT_EXIST);
                return;
            }

            File file = implementation.compressImage(uri, mimeType, quality);

            JSObject result = new JSObject();
            result.put("path", file.getAbsolutePath());
            call.resolve(result);
        } catch (Exception exception) {
            String message = exception.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }
}
