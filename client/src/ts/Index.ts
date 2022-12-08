// Importation du module Barcode qui traite les moyens d'obtenir le barcode
import Barcode from "./Barcode.js";

// Déclaration des variables 
let start  = <HTMLButtonElement> document.querySelector("#Startv");
let video = <HTMLVideoElement> document.querySelector("#scanner-container");
let upload = <HTMLInputElement> document.querySelector('input[type="file"]')
let submit = <HTMLButtonElement> document.querySelector("#submit");
let Reader : Barcode = new Barcode();

// Event Listener de l'activation de la caméra
start.addEventListener('click', (() => {
    Reader.live(video);
}) as EventListener);

// Event Listener d'upload de fichier
upload.addEventListener('change', function(this : any) {
    if (this.files && this.files[0]) {
        // Création d'un objet URL pour la lecture du code barre
        let imgurl = URL.createObjectURL(this.files[0]);
        Reader.static(imgurl);
    }
});    

// Event Listener d'input
submit.addEventListener('click', (() => {
    let value  = (<HTMLInputElement> document.getElementById("number")).value;
    Reader.input(value);
})as EventListener);

