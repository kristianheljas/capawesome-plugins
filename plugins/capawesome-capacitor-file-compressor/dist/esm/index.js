import { registerPlugin } from '@capacitor/core';
const FileCompressor = registerPlugin('FileCompressor', {
    web: () => import('./web').then((m) => new m.FileCompressorWeb()),
});
export * from './definitions';
export { FileCompressor };
//# sourceMappingURL=index.js.map