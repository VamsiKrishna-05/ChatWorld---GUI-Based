package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import Common.Message;

public class ChatClient {
    private JFrame frame;
    private JTextArea chatArea;
    private JTextField inputField, toField;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String username;

    public ChatClient(String username) {
        this.username = username;
        initialize();
        connectToServer();
    }

    private void initialize() {
        frame = new JFrame("ChatWorld - " + username);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);

        inputField = new JTextField();
        toField = new JTextField();
        toField.setPreferredSize(new Dimension(100, 30));
        inputField.setPreferredSize(new Dimension(300, 30));

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(toField, BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);

        frame.add(scrollPane, BorderLayout.CENTER);
        frame.add(inputPanel, BorderLayout.SOUTH);

        inputField.addActionListener(e -> sendMessage());
        frame.setVisible(true);
    }

    private void connectToServer() {
        try {
            Socket socket = new Socket("localhost", 12345);
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(username);

            new Thread(() -> {
                try {
                    Message msg;
                    while ((msg = (Message) in.readObject()) != null) {
                        chatArea.append(msg.getFrom() + ": " + msg.getContent() + "\n");
                    }
                } catch (Exception e) {
                    chatArea.append("Disconnected from server.\n");
                }
            }).start();

        } catch (IOException e) {
            JOptionPane.showMessageDialog(frame, "Unable to connect to server.");
        }
    }

    private void sendMessage() {
        String to = toField.getText().trim();
        String text = inputField.getText().trim();
        if (!to.isEmpty() && !text.isEmpty()) {
            try {
                out.writeObject(new Message(username, to, text));
                inputField.setText("");
            } catch (IOException e) {
                chatArea.append("Failed to send message.\n");
            }
        }
    }

    public static void main(String[] args) {
        String username = JOptionPane.showInputDialog("Enter username:");
        if (username != null && !username.trim().isEmpty()) {
            new ChatClient(username);
        }
    }
}
