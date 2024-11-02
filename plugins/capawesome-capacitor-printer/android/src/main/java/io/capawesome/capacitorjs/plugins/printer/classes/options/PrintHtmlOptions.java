package io.capawesome.capacitorjs.plugins.printer.classes.options;

import androidx.annotation.NonNull;

public class PrintHtmlOptions extends PrintOptions {

    @NonNull
    private String html;

    public PrintHtmlOptions(@NonNull String name, @NonNull String html) {
        super(name);
        this.html = html;
    }

    @NonNull
    public String getHtml() {
        return html;
    }
}
