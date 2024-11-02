import { registerPlugin } from '@capacitor/core';
const Nfc = registerPlugin('Nfc', {
    web: () => import('./web').then((m) => new m.NfcWeb()),
});
export * from './definitions';
export * from './utils';
export { Nfc };
//# sourceMappingURL=index.js.map