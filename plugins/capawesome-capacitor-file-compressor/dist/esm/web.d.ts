import { WebPlugin } from '@capacitor/core';
import type { CompressImageOptions, CompressImageResult, FileCompressorPlugin } from './definitions';
export declare class FileCompressorWeb extends WebPlugin implements FileCompressorPlugin {
    private static readonly ERROR_FILE_MISSING;
    private static readonly ERROR_QUALITY_OUT_OF_RANGE;
    private static readonly ERROR_MIME_TYPE_NOT_SUPPORTED;
    compressImage(options: CompressImageOptions): Promise<CompressImageResult>;
}
