const { execSync} = require('child_process')
const fs = require('node:fs')
require('dotenv').config();

if (process.argv.length !== 3) {
    process.stderr.write('Usage: node scripts/copy-plugin.js <package>');
}

const packageName = process.argv[2];
const srcPackageRef = `@capawesome-team/${packageName}@latest`;
const srcPackageDir = `node_modules/@capawesome-team/${packageName}`;
const destPackageRef = `@kristianheljas/capawesome-${packageName}`;
const destPackageDir = `plugins/capawesome-${packageName}`;
const destPackageJsonPath = `${destPackageDir}/package.json`;

console.log(`Installing ${srcPackageRef}`)
execSync(`npm install ${srcPackageRef}`);

console.log(`Copying package to ${destPackageDir}`);
if (fs.existsSync(destPackageDir)) {
    fs.rmSync(destPackageDir, { recursive: true });
}
fs.mkdirSync(destPackageDir, { recursive: true });
fs.cpSync(srcPackageDir, destPackageDir, { recursive: true });

console.log('Updating package.json');
const packageJson = JSON.parse(
    fs.readFileSync(destPackageJsonPath, 'utf8')
);

packageJson['name'] = destPackageRef;
packageJson['repository'] = {
    'type': 'git',
    'url': 'git+https://github.com/kristianheljas/capawesome-plugins.git'
};
packageJson['publishConfig'] = {
    "@kristianheljas:registry": "https://npm.pkg.github.com"
}

delete packageJson['scripts']['prepare'];
delete packageJson['scripts']['prepublish'];
delete packageJson['scripts']['prepublishOnly'];
delete packageJson['scripts']['prepack'];
delete packageJson['scripts']['postpack'];

fs.writeFileSync(
    destPackageJsonPath,
    JSON.stringify(packageJson, null, 2),
)

console.log('Copying .envrc');
fs.cpSync('.npmrc', `${destPackageDir}/.npmrc`);

console.log('Done.')