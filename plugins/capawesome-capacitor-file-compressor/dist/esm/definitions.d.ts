export interface FileCompressorPlugin {
    /**
     * Compress an image.
     *
     * Only png, jpeg, and webp images are supported.
     *
     * **Attention**: The exif data of the image is lost during compression.
     *
     * @since 5.0.0
     */
    compressImage(options: CompressImageOptions): Promise<CompressImageResult>;
}
/**
 * @since 5.0.0
 */
export interface CompressImageOptions {
    /**
     * The blob of the file to compress.
     *
     * Only available on Web.
     *
     * @since 5.0.0
     */
    blob?: Blob;
    /**
     * The mime type of the compressed file.
     *
     * On Android, only `image/jpeg` and `image/webp` are supported.
     * On iOS, only `image/jpeg` is supported.
     * On Web, only `image/jpeg` and `image/webp` are supported.
     *
     * @since 5.0.0
     * @example 'image/jpeg'
     * @default 'image/jpeg'
     */
    mimeType?: string;
    /**
     * The path of the file to compress.
     *
     * Only available on Android and iOS.
     *
     * @since 5.0.0
     * @example 'content://com.android.providers.downloads.documents/document/msf%3A1000000485'
     */
    path?: string;
    /**
     * The quality of the resulting image, expressed as a value from `0.0` to `1.0`.
     *
     * The value `0.0` represents the maximum compression (or lowest quality)
     * while the value `1.0` represents the least compression (or best quality).
     *
     * @since 5.0.0
     * @default 0.6
     * @example 0.7
     */
    quality?: number;
}
/**
 * @since 5.0.0
 */
export interface CompressImageResult {
    /**
     * The path of the compressed file.
     *
     * Only available on Android and iOS.
     *
     * @since 5.0.0
     * @example 'content://com.android.providers.downloads.documents/document/msf%3A1000000353'
     */
    path?: string;
    /**
     * The blob of the compressed file.
     *
     * Only available on Web.
     *
     * @since 5.0.0
     */
    blob?: Blob;
}
