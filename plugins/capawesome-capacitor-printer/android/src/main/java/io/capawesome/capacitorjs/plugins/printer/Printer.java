package io.capawesome.capacitorjs.plugins.printer;

import android.content.ContentResolver;
import android.content.Context;
import android.net.Uri;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;
import io.capawesome.capacitorjs.plugins.printer.classes.options.PrintHtmlOptions;
import io.capawesome.capacitorjs.plugins.printer.classes.options.PrintPdfOptions;
import io.capawesome.capacitorjs.plugins.printer.classes.options.PrintWebViewOptions;
import io.capawesome.capacitorjs.plugins.printer.interfaces.EmptyResultCallback;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class Printer {

    private PrinterPlugin plugin;

    Printer(PrinterPlugin plugin) {
        this.plugin = plugin;
    }

    public void printPdf(@NonNull PrintPdfOptions options, @NonNull EmptyResultCallback callback) throws FileNotFoundException {
        String name = options.getName();
        Uri uri = options.getUri();

        EmptyResultCallback adapterCallback = new EmptyResultCallback() {
            @Override
            public void success() {
                callback.success();
            }

            @Override
            public void error(Exception exception) {
                callback.error(exception);
            }
        };
        PrinterAdapter adapter = new PrinterAdapter(plugin.getContext(), uri, name, adapterCallback);
        printAdapter(adapter, options.getName());
    }

    public void printHtml(@NonNull PrintHtmlOptions options, @NonNull EmptyResultCallback callback) {
        String name = options.getName();
        String html = options.getHtml();

        plugin
            .getActivity()
            .runOnUiThread(() -> {
                try {
                    WebView webView = this.createWebView();

                    webView.setWebViewClient(
                        new WebViewClient() {
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                return false;
                            }

                            @Override
                            public void onPageFinished(WebView view, String url) {
                                printWebView(view, name);
                                callback.success();
                            }
                        }
                    );

                    webView.loadDataWithBaseURL(null, html, "text/HTML", "UTF-8", null);
                } catch (Exception exception) {
                    callback.error(exception);
                }
            });
    }

    public void printWebView(@NonNull PrintWebViewOptions options, @NonNull EmptyResultCallback callback) {
        String name = options.getName();

        WebView webView = plugin.getBridge().getWebView();
        plugin
            .getActivity()
            .runOnUiThread(() -> {
                try {
                    printWebView(webView, name);
                    callback.success();
                } catch (Exception exception) {
                    callback.error(exception);
                }
            });
    }

    public Uri getUriByPath(@NonNull String path) {
        Uri uri = Uri.parse(path);
        if (uri.getScheme() != null && uri.getScheme().equals(ContentResolver.SCHEME_CONTENT)) {
            return uri;
        } else if (uri.getScheme() == null || uri.getScheme().equals(ContentResolver.SCHEME_FILE)) {
            return FileProvider.getUriForFile(
                plugin.getActivity(),
                plugin.getContext().getPackageName() + ".fileprovider",
                new File(uri.getPath())
            );
        } else {
            return FileProvider.getUriForFile(plugin.getActivity(), plugin.getContext().getPackageName() + ".fileprovider", new File(path));
        }
    }

    public boolean isFileExists(@NonNull Uri uri) {
        DocumentFile document = DocumentFile.fromSingleUri(plugin.getContext(), uri);
        return document.exists();
    }

    @NonNull
    private WebView createWebView() {
        Context context = plugin.getContext();
        WebView webView = new WebView(context);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setGeolocationEnabled(true);
        settings.setDatabaseEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        // spec.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        return webView;
    }

    private void printWebView(@NonNull WebView webView, @NonNull String jobName) {
        PrintDocumentAdapter printAdapter = webView.createPrintDocumentAdapter(jobName);
        printAdapter(printAdapter, jobName);
    }

    private void printAdapter(@NonNull PrintDocumentAdapter adapter, @NonNull String jobName) {
        PrintManager printManager = (PrintManager) plugin.getActivity().getSystemService(Context.PRINT_SERVICE);
        printManager.print(jobName, adapter, new PrintAttributes.Builder().build());
    }
}
