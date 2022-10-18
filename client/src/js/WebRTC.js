"use strict";
// @ts-nocheck
var video = document.querySelector("Video");
if (navigator.mediaDevices.getUserMedia) {
    navigator.mediaDevices.getUserMedia({ video: true })
        .then(function (stream) {
        video.srcObject = stream;
    })
        .catch(function () {
        window.alert("Something went wrong");
    });
}
//# sourceMappingURL=WebRTC.js.map