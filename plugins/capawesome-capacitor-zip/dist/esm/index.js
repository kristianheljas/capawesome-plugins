import { registerPlugin } from '@capacitor/core';
const Zip = registerPlugin('Zip', {
    web: () => import('./web').then(m => new m.ZipWeb()),
});
export * from './definitions';
export { Zip };
//# sourceMappingURL=index.js.map