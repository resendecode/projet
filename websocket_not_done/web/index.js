'use strict';

window.onload = () => {
    // Tested with Tyrus 1.17 WebSockets Java library
    const service = new WebSocket("ws://localhost:1963/FranckBarbier/WebSockets_illustration");
    service.onmessage = (event) => {
        console.log("Message from Java: " + event.data);
    };
    service.onopen = () => {
        console.log("service.onopen...");
        const response = window.confirm(service.url + " just opened... Say 'Hi!'?");
        if (response)
            service.send(JSON.stringify({Response: "Hi!"}));
    };
    service.onclose = (event/*:CloseEvent*/) => {
        console.log("service.onclose... " + event.code);
        window.alert("Bye! See you later...");
// '1011': the server is terminating the connection because it encountered an unexpected condition that prevented it from fulfilling the request.
    };
    service.onerror = () => {
        window.alert("service.onerror...");
    };
};