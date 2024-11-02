package io.capawesome.capacitorjs.plugins.printer;

import static android.print.PrintDocumentInfo.CONTENT_TYPE_DOCUMENT;
import static android.print.PrintDocumentInfo.PAGE_COUNT_UNKNOWN;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.os.ParcelFileDescriptor;
import android.print.PageRange;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintDocumentInfo;
import androidx.annotation.NonNull;
import io.capawesome.capacitorjs.plugins.printer.interfaces.EmptyResultCallback;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PrinterAdapter extends PrintDocumentAdapter {

    @NonNull
    private final Context context;

    @NonNull
    private final Uri uri;

    @NonNull
    private final String jobName;

    @NonNull
    private final EmptyResultCallback callback;

    public PrinterAdapter(@NonNull Context context, @NonNull Uri uri, @NonNull String jobName, @NonNull EmptyResultCallback callback) {
        this.context = context;
        this.uri = uri;
        this.jobName = jobName;
        this.callback = callback;
    }

    @Override
    public void onLayout(
        PrintAttributes oldAttributes,
        PrintAttributes newAttributes,
        CancellationSignal cancellationSignal,
        LayoutResultCallback callback,
        Bundle extras
    ) {
        if (cancellationSignal.isCanceled()) return;

        PrintDocumentInfo printDocumentInfo = new PrintDocumentInfo.Builder(jobName)
            .setContentType(CONTENT_TYPE_DOCUMENT)
            .setPageCount(PAGE_COUNT_UNKNOWN)
            .build();

        boolean changed = !newAttributes.equals(oldAttributes);

        callback.onLayoutFinished(printDocumentInfo, changed);
    }

    @Override
    public void onWrite(
        PageRange[] pages,
        ParcelFileDescriptor destination,
        CancellationSignal cancellationSignal,
        WriteResultCallback callback
    ) {
        if (cancellationSignal.isCanceled()) return;

        InputStream input = null;
        OutputStream output = null;
        try {
            input = context.getContentResolver().openInputStream(uri);
            output = new FileOutputStream(destination.getFileDescriptor());
            PrinterHelper.copyInputStreamToOutputStream(input, output);
        } catch (IOException e) {
            callback.onWriteFailed(e.getMessage());
            return;
        } finally {
            PrinterHelper.closeStream(input);
            PrinterHelper.closeStream(output);
        }

        callback.onWriteFinished(new PageRange[] { PageRange.ALL_PAGES });
    }

    @Override
    public void onFinish() {
        super.onFinish();
        callback.success();
    }
}
