package controller;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@ServerEndpoint("/ws")
public class CodeWebSocket {
    private static Set<Session> clients = new HashSet<>();

    @OnOpen
    public void onOpen(Session session) {
        clients.add(session);
        System.out.println("Client connected: " + session.getId());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        // Log incoming messages if needed
        System.out.println("Message from client: " + message);
    }

    @OnClose
    public void onClose(Session session) {
        clients.remove(session);
        System.out.println("Client disconnected: " + session.getId());
    }

    public static void broadcast(String code, String image) {
        // Broadcast message to all connected clients
        for (Session client : clients) {
            try {
                String jsonResponse = "{ \"code\": \"" + code + "\", \"image\": \"" + image + "\" }";
                client.getBasicRemote().sendText(jsonResponse);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
