export interface ZipPlugin {
    /**
     * Unzip a file.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    unzip(options: UnzipOptions): Promise<void>;
    /**
     * Zip a file or directory.
     *
     * Only available on Android and iOS.
     *
     * @since 6.0.0
     */
    zip(options: ZipOptions): Promise<void>;
}
/**
 * @since 6.0.0
 */
export interface UnzipOptions {
    /**
     * The destination directory.
     *
     * @since 6.0.0
     * @example "file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/1714900095398"
     * @example "file:///var/mobile/Containers/Data/Application/0283533F-3254-40EF-881E-8F68E3AF1207/Library/Caches/1714901909011"
     */
    destination: string;
    /**
     * The password to decrypt the zip file.
     *
     * @since 6.1.0
     * @example "secret"
     */
    password?: string;
    /**
     * The source file to unzip.
     *
     * @since 6.0.0
     * @example "file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/1714900095398.zip"
     * @example "file:///var/mobile/Containers/Data/Application/0283533F-3254-40EF-881E-8F68E3AF1207/Library/Caches/1714901909011.zip"
     */
    source: string;
}
/**
 * @since 6.0.0
 */
export interface ZipOptions {
    /**
     * The destination file.
     *
     * @since 6.0.0
     * @example "file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/1714900095398.zip"
     * @example "file:///var/mobile/Containers/Data/Application/0283533F-3254-40EF-881E-8F68E3AF1207/Library/Caches/1714901909011.zip"
     */
    destination: string;
    /**
     * The password to encrypt the zip file.
     *
     * @since 6.1.0
     * @example "secret"
     */
    password?: string;
    /**
     * The source file or directory to zip.
     *
     * @since 6.0.0
     * @example "file:///data/user/0/dev.robingenz.capacitor.plugindemo/cache/1714900095398"
     * @example "file:///var/mobile/Containers/Data/Application/0283533F-3254-40EF-881E-8F68E3AF1207/Library/Caches/1714901909011"
     */
    source: string;
}
