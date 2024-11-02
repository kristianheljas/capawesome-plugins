package io.capawesome.capacitorjs.plugins.filecompressor;

import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.documentfile.provider.DocumentFile;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileCompressor {

    private FileCompressorPlugin plugin;

    FileCompressor(FileCompressorPlugin plugin) {
        this.plugin = plugin;
    }

    public File compressImage(@NonNull Uri uri, @NonNull String mimeType, double quality) throws IOException {
        Bitmap bitmap = BitmapFactory.decodeStream(plugin.getContext().getContentResolver().openInputStream(uri));
        String outputFileName = System.currentTimeMillis() + "." + FileCompressorHelper.mapMimeTypeToExtension(mimeType);
        File outputFile = new File(plugin.getContext().getCacheDir(), outputFileName);
        FileOutputStream fileOutputStream = new FileOutputStream(outputFile);
        Bitmap.CompressFormat format = FileCompressorHelper.mapMimeTypeToBitmapCompressFormat(mimeType);
        bitmap.compress(format, (int) (quality * 100), fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
        return outputFile;
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
}
