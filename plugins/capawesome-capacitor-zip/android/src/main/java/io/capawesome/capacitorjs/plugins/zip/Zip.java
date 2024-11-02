package io.capawesome.capacitorjs.plugins.zip;

import androidx.annotation.Nullable;
import java.io.File;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.model.enums.EncryptionMethod;

public class Zip {

    private ZipPlugin plugin;

    Zip(ZipPlugin plugin) {
        this.plugin = plugin;
    }

    public void unzip(String source, String destination, @Nullable String password) throws Exception {
        if (source.contains("file://")) {
            source = source.replace("file://", "");
        }
        if (destination.contains("file://")) {
            destination = destination.replace("file://", "");
        }

        ZipFile zipFile = new ZipFile(source);
        if (zipFile.isEncrypted()) {
            if (password == null || password.isEmpty()) {
                throw new Exception(ZipPlugin.ERROR_PASSWORD_REQUIRED);
            } else {
                zipFile.setPassword(password.toCharArray());
            }
        }
        try {
            zipFile.extractAll(destination);
        } catch (ZipException exception) {
            if (exception.getMessage().equals("Wrong password!")) {
                throw new Exception(ZipPlugin.ERROR_PASSWORD_INCORRECT);
            } else {
                throw exception;
            }
        }
    }

    public void zip(String source, String destination, @Nullable String password) throws Exception {
        if (source.contains("file://")) {
            source = source.replace("file://", "");
        }
        if (destination.contains("file://")) {
            destination = destination.replace("file://", "");
        }

        File sourceFile = new File(source);
        ZipFile zipFile = new ZipFile(destination);
        ZipParameters zipParameters = new ZipParameters();
        if (password != null && !password.isEmpty()) {
            zipFile.setPassword(password.toCharArray());
            zipParameters.setEncryptFiles(true);
            zipParameters.setEncryptionMethod(EncryptionMethod.ZIP_STANDARD);
        }
        if (sourceFile.isDirectory()) {
            zipFile.addFolder(sourceFile, zipParameters);
        } else {
            zipFile.addFile(sourceFile, zipParameters);
        }
    }
}
