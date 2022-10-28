export default class Barcode {
    live(video) {
        Quagga.init({
            inputStream: {
                name: "Live",
                type: "LiveStream",
                // Or '#yourElement' (optional)
                target: video
            },
            decoder: {
                readers: [
                    "code_128_reader",
                    "ean_reader",
                    "ean_8_reader",
                    "code_39_reader",
                    "code_39_vin_reader",
                    "codabar_reader",
                    "upc_reader",
                    "upc_e_reader",
                    "i2of5_reader"
                ],
            }
        }, function (err) {
            if (err) {
                console.log(err);
                return;
            }
            console.log("Initialization finished. Ready to start");
            Quagga.start();
        });
        Quagga.onDetected(function (result) {
            Quagga.stop();
            console.log("Barcode detected and processed : [" + result.codeResult.code + "]", result);
        });
    }
    stop() {
        Quagga.stop();
    }
    static(img) {
        Quagga.decodeSingle({
            decoder: {
                readers: [
                    "code_128_reader",
                    "ean_reader",
                    "ean_8_reader",
                    "code_39_reader",
                    "code_39_vin_reader",
                    "codabar_reader",
                    "upc_reader",
                    "upc_e_reader",
                    "i2of5_reader"
                ], // List of active readers
            },
            locate: true,
            src: img // or 'data:image/jpg;base64,' + data
        }, function (result) {
            if (result.codeResult) {
                console.log("result", result.codeResult.code);
            }
            else {
                console.log("not detected");
            }
        });
    }
}
//# sourceMappingURL=Barcode.js.map