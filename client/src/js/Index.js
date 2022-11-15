import Barcode from "./Barcode.js";
let start = document.querySelector("#Startv");
let video = document.querySelector("#scanner-container");
let upload = document.querySelector('input[type="file"]');
let submit = document.querySelector("#submit");
let Reader = new Barcode();
start.addEventListener('click', (() => {
    Reader.live(video);
}));
upload.addEventListener('change', function () {
    if (this.files && this.files[0]) {
        let imgurl = URL.createObjectURL(this.files[0]);
        Reader.static(imgurl);
    }
});
submit.addEventListener('click', (() => {
    let value = document.getElementById("number").value;
    Reader.input(value);
}));
//# sourceMappingURL=Index.js.map