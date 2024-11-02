import { WebPlugin } from '@capacitor/core';
import type { PrintPdfOptions, PrintHtmlOptions, PrintOptions, PrinterPlugin } from './definitions';
export declare class PrinterWeb extends WebPlugin implements PrinterPlugin {
    printHtml(_options: PrintHtmlOptions): Promise<void>;
    printPdf(_options: PrintPdfOptions): Promise<void>;
    printWebView(_options?: PrintOptions | undefined): Promise<void>;
}
