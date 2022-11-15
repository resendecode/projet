export var ws = new WebSocket("ws://localhost:8025/BetterFood/websocket");
ws.onopen = function (e) {
    console.log("Connexion au websocket effectué");
};
ws.onerror = function (error) {
    alert("impossible de se connecter au WebSocket");
};
ws.onmessage = function (event) {
    alert(`[message] Data received from server: ${event.data}`);
};
//# sourceMappingURL=Websocket.js.map