package io.capawesome.capacitorjs.plugins.bluetoothle.classes.options;

import androidx.annotation.NonNull;

public class StartForegroundServiceOptions {

    @NonNull
    private String body;

    @NonNull
    private Integer id;

    @NonNull
    private String smallIcon;

    @NonNull
    private String title;

    public StartForegroundServiceOptions(@NonNull String body, @NonNull Integer id, @NonNull String smallIcon, @NonNull String title) {
        this.body = body;
        this.id = id;
        this.smallIcon = smallIcon;
        this.title = title;
    }

    @NonNull
    public String getBody() {
        return body;
    }

    @NonNull
    public Integer getId() {
        return id;
    }

    @NonNull
    public String getSmallIcon() {
        return smallIcon;
    }

    @NonNull
    public String getTitle() {
        return title;
    }
}
