"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const Quagga = require('quagga').default; // Common JS (important: default)
Quagga.init({
    inputStream: {
        name: "Video",
        type: "Webcam",
        target: document.querySelector('Video')
    },
    decoder: {
        readers: ["codebar"]
    }
}, function (err) {
    if (err) {
        console.log(err);
        return;
    }
    console.log("Initialization finished. Ready to start");
    Quagga.start();
});
//# sourceMappingURL=Barcode.js.map