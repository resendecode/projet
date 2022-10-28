/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.websocket;
import org.glassfish.tyrus.server.Server;

/**
 *
 * @author mickael
 */
public class Websocket {

    public static void main(String[] args) {
        Server server;
        server = new Server ("localhost", 8025, "/websocket", null);
        try {
            server.start();
            System.out.println("--- server is running");
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
