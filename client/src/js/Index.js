import Barcode from "./Barcode.js";
let start = document.querySelector("#Startv");
let video = document.querySelector("#scanner-container");
let Reader = new Barcode();
let ws = new WebSocket("ws://localhost:8025/BetterFood/websocket");
ws.onopen = function (e) {
    alert("[open] Connection established");
    alert("Sending to server");
    ws.send("5411188103387");
    setTimeout(function () {
        console.log("pause pour debug");
    }, 100000);
};
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