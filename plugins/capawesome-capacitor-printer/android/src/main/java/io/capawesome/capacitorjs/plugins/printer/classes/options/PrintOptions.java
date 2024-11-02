package io.capawesome.capacitorjs.plugins.printer.classes.options;

import androidx.annotation.NonNull;

public class PrintOptions {

    @NonNull
    private String name;

    public PrintOptions() {}

    public PrintOptions(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getName() {
        return name;
    }
}
