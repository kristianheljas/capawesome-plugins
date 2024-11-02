import { WebPlugin } from '@capacitor/core';
import type { UnzipOptions, ZipOptions, ZipPlugin } from './definitions';
export declare class ZipWeb extends WebPlugin implements ZipPlugin {
    zip(_options: ZipOptions): Promise<void>;
    unzip(_options: UnzipOptions): Promise<void>;
    private throwUnimplementedError;
}
