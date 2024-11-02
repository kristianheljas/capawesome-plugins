package io.capawesome.capacitorjs.plugins.printer.classes.options;

import android.net.Uri;
import androidx.annotation.NonNull;

public class PrintPdfOptions extends PrintOptions {

    @NonNull
    private Uri uri;

    public PrintPdfOptions(@NonNull String name, @NonNull Uri uri) {
        super(name);
        this.uri = uri;
    }

    @NonNull
    public Uri getUri() {
        return uri;
    }
}
