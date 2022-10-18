package com.franckbarbier.websockets.tyrus.java;

// https://eclipse-ee4j.github.io/tyrus-project.github.io/documentation/latest/index/
public class WebSockets_illustration {

    /* Danger: 'My_ServerEndpoint' constructor must be accessed by the WebSockets server. Don't forget 'static'! */
    @javax.websocket.server.ServerEndpoint(value = "/WebSockets_illustration")
    public static class My_ServerEndpoint { // It *MUST* be 'public'!

        @javax.websocket.OnClose
        public void onClose(javax.websocket.Session session, javax.websocket.CloseReason close_reason) {
            System.out.println("onClose: " + close_reason.getReasonPhrase());
        }

        @javax.websocket.OnError
        public void onError(javax.websocket.Session session, Throwable throwable) {
            System.out.println("onError: " + throwable.getMessage());
        }

        @javax.websocket.OnMessage
        public void onMessage(javax.websocket.Session session, String message) {
            System.out.println("Message from client: " + message);
        }

        @javax.websocket.OnOpen
        public void onOpen(javax.websocket.Session session, javax.websocket.EndpointConfig ec) throws java.io.IOException {
            System.out.println("OnOpen... " + ec.getUserProperties().get("Author"));
            session.getBasicRemote().sendText("{\"Handshaking\": \"Yes\"}");
        }
    }

    public static void main(String[] args) {
        java.util.Map<String, Object> user_properties = new java.util.HashMap();
        user_properties.put("Author", "FranckBarbier");

        org.glassfish.tyrus.server.Server server = new org.glassfish.tyrus.server.Server("localhost", 1963, "/FranckBarbier", user_properties /* or 'null' */, My_ServerEndpoint.class);
        try {
            server.start();
            System.out.println("\n*** Please press any key to stop the server... ***\n");
// The Web page (JavaScript client) is launched from Java:
            java.awt.Desktop.getDesktop().browse(java.nio.file.FileSystems.getDefault().getPath("web" + java.io.File.separatorChar + "index.html").toUri());
// The Java 11 client is launched as well:
            Java_11_client client = new Java_11_client(java.util.Optional.of(Java_11_client.class.getSimpleName()));
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            reader.readLine();
            System.out.println("\n@@@\n" + client.get_log() + "@@@\n");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}
