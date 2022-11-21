// Importation du module Barcode qui traite les moyens d'obtenir le barcode
import Barcode from "./Barcode.js";
// Déclaration des variables 
let start = document.querySelector("#Startv");
let video = document.querySelector("#scanner-container");
let upload = document.querySelector('input[type="file"]');
let submit = document.querySelector("#submit");
let Reader = new Barcode();
// Event Listener de l'activation de la caméra
start.addEventListener('click', (() => {
    Reader.live(video);
}));
// Event Listener d'upload de fichier
upload.addEventListener('change', function () {
    if (this.files && this.files[0]) {
        // Création d'un objet URL pour la lecture du code barre
        let imgurl = URL.createObjectURL(this.files[0]);
        Reader.static(imgurl);
    }
});
// Event Listener d'input
submit.addEventListener('click', (() => {
    let value = document.getElementById("number").value;
    Reader.input(value);
}));
//# sourceMappingURL=Index.js.map