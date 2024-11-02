import { registerPlugin } from '@capacitor/core';
import { BluetoothLowEnergyClient } from './client';
const BluetoothLowEnergy = new BluetoothLowEnergyClient(registerPlugin('BluetoothLowEnergy', {
    web: () => import('./web').then(m => new m.BluetoothLowEnergyWeb()),
}));
export * from './definitions';
export { BluetoothLowEnergy };
//# sourceMappingURL=index.js.map