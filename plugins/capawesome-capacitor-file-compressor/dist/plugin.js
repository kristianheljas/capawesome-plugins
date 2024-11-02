var capacitorFileCompressor = (function (exports, core) {
    'use strict';

    const FileCompressor = core.registerPlugin('FileCompressor', {
        web: () => Promise.resolve().then(function () { return web; }).then((m) => new m.FileCompressorWeb()),
    });

    class FileCompressorWeb extends core.WebPlugin {
        async compressImage(options) {
            var _a, _b;
            const blob = options.blob;
            if (!blob) {
                throw new Error(FileCompressorWeb.ERROR_FILE_MISSING);
            }
            const mimeType = (_a = options.mimeType) !== null && _a !== void 0 ? _a : 'image/jpeg';
            if (mimeType !== 'image/jpeg' && mimeType !== 'image/webp') {
                throw new Error(FileCompressorWeb.ERROR_MIME_TYPE_NOT_SUPPORTED);
            }
            const quality = (_b = options.quality) !== null && _b !== void 0 ? _b : 0.6;
            if (quality < 0 || quality > 1) {
                throw new Error(FileCompressorWeb.ERROR_QUALITY_OUT_OF_RANGE);
            }
            return new Promise((resolve, reject) => {
                const reader = new FileReader();
                reader.readAsDataURL(blob);
                reader.onload = () => {
                    const base64data = reader.result;
                    const image = new Image();
                    image.src = base64data;
                    image.onload = () => {
                        const { height, width } = image;
                        const canvasElement = document.createElement('canvas');
                        canvasElement.height = height;
                        canvasElement.width = width;
                        const canvasElementContext = canvasElement.getContext('2d');
                        if (!canvasElementContext) {
                            reject('Could not get canvas context.');
                            return;
                        }
                        canvasElementContext.drawImage(image, 0, 0, width, height);
                        canvasElementContext.canvas.toBlob((blob) => {
                            if (blob) {
                                resolve({ blob });
                            }
                            else {
                                reject('Could not get blob.');
                            }
                            canvasElement.remove();
                        }, mimeType, quality);
                    };
                    image.onerror = (error) => {
                        reject(error);
                    };
                };
                reader.onerror = (error) => {
                    reject(error);
                };
            });
        }
    }
    FileCompressorWeb.ERROR_FILE_MISSING = 'blob must be provided.';
    FileCompressorWeb.ERROR_QUALITY_OUT_OF_RANGE = 'quality must be between 0 and 1.';
    FileCompressorWeb.ERROR_MIME_TYPE_NOT_SUPPORTED = 'mimeType is not supported.';

    var web = /*#__PURE__*/Object.freeze({
        __proto__: null,
        FileCompressorWeb: FileCompressorWeb
    });

    exports.FileCompressor = FileCompressor;

    Object.defineProperty(exports, '__esModule', { value: true });

    return exports;

})({}, capacitorExports);
//# sourceMappingURL=plugin.js.map
