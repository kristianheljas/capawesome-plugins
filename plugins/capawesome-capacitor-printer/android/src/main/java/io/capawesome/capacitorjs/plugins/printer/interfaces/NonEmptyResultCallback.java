package io.capawesome.capacitorjs.plugins.printer.interfaces;

import androidx.annotation.NonNull;

public interface NonEmptyResultCallback<T extends Result> extends ResultCallback {
    void success(@NonNull T result);
}
