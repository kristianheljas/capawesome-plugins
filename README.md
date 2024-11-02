# Copy of capacitor plugins

Personal copy of capawesome plugins

## Using these packages

```shell
# Configure npm to install from github
npm config set @kristianheljas:registry https://npm.pkg.github.com
npm config set //npm.pkg.github.com/:_authToken <github-token>

# Install packages
npm install @kristianheljas/capawesome-capacitor-bluetooth-low-energy
npm install @kristianheljas/capawesome-capacitor-file-compressor
npm install @kristianheljas/capawesome-capacitor-nfc
npm install @kristianheljas/capawesome-capacitor-printer
npm install @kristianheljas/capawesome-capacitor-wifi
npm install @kristianheljas/capawesome-capacitor-zip
```

## Copying a plugin

```shell
export POLAR_LICENSE_KEY=POLAR-XXXX
export PLUGIN_NAME=capacitor-nfc
export GITHUB_TOKEN=xxx

node scripts/copy-plugin.js $PLUGIN_NAME
cd plugins/$PLUGIN_NAME
npm publish
```