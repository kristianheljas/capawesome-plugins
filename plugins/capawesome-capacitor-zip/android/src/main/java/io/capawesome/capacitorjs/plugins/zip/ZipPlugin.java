package io.capawesome.capacitorjs.plugins.zip;

import android.util.Log;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "Zip")
public class ZipPlugin extends Plugin {

    public static final String TAG = "Zip";
    public static final String ERROR_DESTINATION_MISSING = "destination must be provided.";
    public static final String ERROR_SOURCE_MISSING = "source must be provided.";
    public static final String ERROR_PASSWORD_REQUIRED = "password is required for encrypted files.";
    public static final String ERROR_PASSWORD_INCORRECT = "password is incorrect.";

    private Zip implementation;

    @Override
    public void load() {
        implementation = new Zip(this);
    }

    @PluginMethod
    public void unzip(PluginCall call) {
        try {
            String source = call.getString("source");
            if (source == null) {
                call.reject(ERROR_SOURCE_MISSING);
                return;
            }
            String destination = call.getString("destination");
            if (destination == null) {
                call.reject(ERROR_DESTINATION_MISSING);
                return;
            }
            String password = call.getString("password");

            implementation.unzip(source, destination, password);

            call.resolve();
        } catch (Exception exception) {
            String message = exception.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }

    @PluginMethod
    public void zip(PluginCall call) {
        try {
            String source = call.getString("source");
            if (source == null) {
                call.reject(ERROR_SOURCE_MISSING);
                return;
            }
            String destination = call.getString("destination");
            if (destination == null) {
                call.reject(ERROR_DESTINATION_MISSING);
                return;
            }
            String password = call.getString("password");

            implementation.zip(source, destination, password);

            call.resolve();
        } catch (Exception exception) {
            String message = exception.getMessage();
            Log.e(TAG, message);
            call.reject(message);
        }
    }
}
