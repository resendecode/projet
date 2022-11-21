
package com.gabi.serveur;

import org.glassfish.tyrus.server.Server;

/**
 *
 * @author gabriel
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */


public class Serveur {


    @javax.websocket.server.ServerEndpoint(value = "/websocket")

    
    public static class EndPoint {
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
            //lorsqu'on reçoit un message du côté client de l'application (un barcode)
            //nous essayons de créer un objet ProduitApi qui va traiter la requete
            //la reponse au message sera une chaine de caracteres retourné par la methode 'build'
            //avec toutes les informations necessaires au côté client pour construire la page de réponse
            System.out.println("Message from client: " + message);

            //Creation du produit avec le code barre du client
            try {
                ProduitApi produit = new ProduitApi(message);
                session.getBasicRemote().sendText(produit.build());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        @javax.websocket.OnOpen
        public void onOpen(javax.websocket.Session session, javax.websocket.EndpointConfig ec) throws java.io.IOException {
            System.out.println("OnOpen... " + ec.getUserProperties().get("Author"));
            session.getBasicRemote().sendText("{\"Handshaking\": \"Yes\"}");
        }
    }

    public static void main(String[] args) {
        //Démarrage du serveur dans le port local
        Server server = new Server ("localhost", 8025, "/BetterFood", null, EndPoint.class);
        try {
            server.start();
            System.out.println("--- server is running");
            System.out.println(java.nio.file.FileSystems.getDefault().getPath("client") );
            System.out.print("Please press a key to stop the server.");
            java.io.BufferedReader reader = new java.io.BufferedReader(new java.io.InputStreamReader(System.in));
            reader.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            server.stop();
        }
    }
}

