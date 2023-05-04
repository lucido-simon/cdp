const fs = require('fs');
const path = require('path');
const { exec } = require('child_process');

const secretsDir = 'secrets';

const args = process.argv.slice(2); // Remove the first two elements (node and script path)

let apply = false;
let applyAll = false;

args.forEach((arg) => {
  if (arg === '--apply') {
    apply = true;
  }
  if (arg === '--apply-all') {
    applyAll = true;
  }
});

fs.readdir(secretsDir, (err, files) => {
  if (err) {
    console.error(`Error reading directory: ${err.message}`);
    return;
  }

  files.forEach((file) => {
    const isGlobal = file.includes('global');
    const filePath = path.join(secretsDir, file);
    const outputFilePath = `sealed/${file.replace(/\.yaml/g, '')}-sealed.yaml`.replace(
      /global/g,
      '',
    );

    const command = `kubeseal -f ${filePath} -w ${outputFilePath} -n polystore -o yaml`;

    exec(command, (error, stdout, stderr) => {
      if (error) {
        console.error(`Error running command: ${error.message}`);
        return;
      }
      if (stderr) {
        console.error(`Error: ${stderr}`);
        return;
      }

      console.log(`Sealed secret saved to: ${outputFilePath}`);
    });

    if ((apply && isGlobal) || applyAll) {
      const applyCommand = `kubectl apply -f ${outputFilePath}`;

      exec(applyCommand, (error, stdout, stderr) => {
        if (error) {
          console.error(`Error running command: ${error.message}`);
          return;
        }
        if (stderr) {
          console.error(`Error: ${stderr}`);
          return;
        }

        console.log(`Applied sealed secret: ${outputFilePath}`);
      });
    }
  });
});
