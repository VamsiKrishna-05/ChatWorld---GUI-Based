package Server;

import java.io.*;
import java.net.*;
import java.util.*;
import Common.Message;

public class ChatServer {
    private static Map<String, ObjectOutputStream> userStreams = new HashMap<>();

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(12345);
        System.out.println("🚀 Chat Server started...");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket socket;
        private String username;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                out = new ObjectOutputStream(socket.getOutputStream());
                in = new ObjectInputStream(socket.getInputStream());

                username = (String) in.readObject();
                synchronized (userStreams) {
                    userStreams.put(username, out);
                }

                System.out.println("🔗 " + username + " connected.");

                Message msg;
                while ((msg = (Message) in.readObject()) != null) {
                    ObjectOutputStream recipientOut = userStreams.get(msg.getTo());
                    if (recipientOut != null) {
                        recipientOut.writeObject(msg);
                    }
                }

            } catch (Exception e) {
                System.out.println("❌ User disconnected: " + username);
            } finally {
                try {
                    socket.close();
                    userStreams.remove(username);
                } catch (IOException e) {
                }
            }
        }
    }
}
