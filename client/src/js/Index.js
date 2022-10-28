import Barcode from "./Barcode.js";
let start = document.querySelector("#Startv");
let video = document.querySelector("#scanner-container");
let Reader = new Barcode();
let ws = new WebSocket("ws://localhost:8025");
ws.addEventListener('open', (event) => {
    ws.send('Hello Server!');
});
// Listen for messages
ws.addEventListener('message', (event) => {
    console.log('Message from server ', event.data);
});
start.addEventListener('click', ((event) => {
    Reader.live(video);
}));
window.addEventListener('load', function () {
    document.querySelector('input[type="file"]').addEventListener('change', function () {
        if (this.files && this.files[0]) {
            let imgurl = URL.createObjectURL(this.files[0]);
            Reader.static(imgurl);
        }
    });
});
//# sourceMappingURL=Index.js.map