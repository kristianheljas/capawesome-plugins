export interface PrinterPlugin {
    /**
     * Present the printing user interface to print a html document.
     *
     * Only available for Android and iOS.
     *
     * @since 5.0.0
     */
    printHtml(options: PrintHtmlOptions): Promise<void>;
    /**
     * Present the printing user interface to print a pdf document.
     *
     * Only available for Android and iOS.
     *
     * @since 5.1.0
     */
    printPdf(options: PrintPdfOptions): Promise<void>;
    /**
     * Present the printing user interface to print the web view content.
     *
     * @since 5.0.0
     *
     * Only available for Android and iOS.
     */
    printWebView(options?: PrintWebViewOptions): Promise<void>;
}
/**
 * @since 5.0.0
 */
export interface PrintOptions {
    /**
     * The name of the print job.
     *
     * @since 5.0.0
     * @default 'Document'
     */
    name?: string;
}
/**
 * @since 5.1.0
 */
export interface PrintPdfOptions extends PrintOptions {
    /**
     * The path to the pdf document on the device.
     *
     * @since 5.1.0
     */
    path: string;
}
/**
 * @since 5.0.0
 */
export interface PrintHtmlOptions extends PrintOptions {
    /**
     * The HTML content to print.
     *
     * @since 5.0.0
     */
    html: string;
}
/**
 * @since 5.0.0
 */
export declare type PrintWebViewOptions = PrintOptions;
