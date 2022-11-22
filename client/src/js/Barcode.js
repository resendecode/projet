// Importation du fichier Websocket permettant d'envoyer les informations vers le côté Java
import { ws } from "./Websocket.js";
export default class Barcode {
    // Lecture par la Webcam
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
                    "ean_reader",
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
            // Envoie le résultat au Websocket
            ws.send(result.codeResult.code);
        });
    }
    // Lecture par image static
    static(img) {
        Quagga.decodeSingle({
            decoder: {
                readers: [
                    "ean_reader",
                ], // List of active readers
            },
            locate: true,
            src: img // or 'data:image/jpg;base64,' + data
        }, function (result) {
            if (result.codeResult) {
                console.log("result", result);
                // Envoie le résultat au Websocket
                ws.send(result.codeResult.code);
            }
            else {
                alert("non détecté");
            }
        });
    }
    // Lecture par input
    input(value) {
        // Le code barre doit être composé de numéro avec une taille de 13
        let regex = new RegExp("^[0-9]{13}$");
        regex.test(value) ? ws.send(value) : alert("mauvais format");
    }
}
//# sourceMappingURL=Barcode.js.map