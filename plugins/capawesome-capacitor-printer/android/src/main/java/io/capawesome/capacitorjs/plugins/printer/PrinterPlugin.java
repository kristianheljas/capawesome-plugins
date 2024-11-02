package io.capawesome.capacitorjs.plugins.printer;

import android.net.Uri;
import com.getcapacitor.Logger;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;
import io.capawesome.capacitorjs.plugins.printer.classes.options.PrintHtmlOptions;
import io.capawesome.capacitorjs.plugins.printer.classes.options.PrintPdfOptions;
import io.capawesome.capacitorjs.plugins.printer.classes.options.PrintWebViewOptions;
import io.capawesome.capacitorjs.plugins.printer.interfaces.EmptyResultCallback;

@CapacitorPlugin(name = "Printer")
public class PrinterPlugin extends Plugin {

    public static final String TAG = "PrinterPlugin";
    public static final String ERROR_HTML_MISSING = "html must be provided.";
    public static final String ERROR_PATH_MISSING = "path must be provided.";
    public static final String ERROR_FILE_NOT_EXIST = "File does not exist.";

    private Printer implementation;

    @Override
    public void load() {
        implementation = new Printer(this);
    }

    @PluginMethod
    public void printPdf(PluginCall call) {
        try {
            String name = call.getString("name", "Document");
            String path = call.getString("path");
            if (path == null) {
                call.reject(ERROR_PATH_MISSING);
                return;
            }

            Uri uri = implementation.getUriByPath(path);
            boolean fileExists = implementation.isFileExists(uri);
            if (!fileExists) {
                call.reject(ERROR_FILE_NOT_EXIST);
                return;
            }

            PrintPdfOptions options = new PrintPdfOptions(name, uri);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.printPdf(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void printHtml(PluginCall call) {
        try {
            String name = call.getString("name", "Document");
            String html = call.getString("html");
            if (html == null) {
                call.reject(ERROR_HTML_MISSING);
                return;
            }

            PrintHtmlOptions options = new PrintHtmlOptions(name, html);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.printHtml(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }

    @PluginMethod
    public void printWebView(PluginCall call) {
        try {
            String name = call.getString("name", "Document");

            PrintWebViewOptions options = new PrintWebViewOptions(name);
            EmptyResultCallback callback = new EmptyResultCallback() {
                @Override
                public void success() {
                    call.resolve();
                }

                @Override
                public void error(Exception exception) {
                    Logger.error(TAG, exception.getMessage(), exception);
                    call.reject(exception.getMessage());
                }
            };

            implementation.printWebView(options, callback);
        } catch (Exception exception) {
            Logger.error(TAG, exception.getMessage(), exception);
            call.reject(exception.getMessage());
        }
    }
}
