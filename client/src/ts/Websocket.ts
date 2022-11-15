// Création du WebSocket
export var ws = new WebSocket("ws://localhost:8025/BetterFood/websocket");

// Au démarrage
ws.onopen = function(e) {
    console.log("Connexion au websocket effectué");
};

// Erreur
ws.onerror = function(error) {
    alert("impossible de se connecter au WebSocket");
}

// Message reçu
ws.onmessage = function(event) {
    alert(`[message] Data received from server: ${event.data}`);
};