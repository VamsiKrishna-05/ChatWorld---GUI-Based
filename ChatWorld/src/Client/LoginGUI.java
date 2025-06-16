package Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.List;
import java.util.ArrayList;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LoginGUI {
    private JFrame frame;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginBtn, registerBtn;

    public LoginGUI() {
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
        }

        frame = new JFrame("ChatWorld - Login");
        frame.setSize(400, 250);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        JLabel userLabel = new JLabel("Username:");
        JLabel passLabel = new JLabel("Password:");
        usernameField = new JTextField(15);
        passwordField = new JPasswordField(15);
        loginBtn = new JButton("Login");
        registerBtn = new JButton("Register");

        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        frame.add(userLabel, gbc);
        gbc.gridx = 1;
        frame.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        frame.add(passLabel, gbc);
        gbc.gridx = 1;
        frame.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        frame.add(loginBtn, gbc);
        gbc.gridx = 1;
        frame.add(registerBtn, gbc);

        loginBtn.addActionListener(e -> login());
        registerBtn.addActionListener(e -> register());

        frame.setVisible(true);
    }

    private void login() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        if (user.isEmpty() || pass.isEmpty()) {
            showError("Fields cannot be empty");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("users.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(":");
                if (parts.length == 2 && parts[0].equals(user) && parts[1].equals(pass)) {
                    frame.dispose();
                    new ChatClient(user);
                    return;
                }
            }
            showError("Invalid username or password");
        } catch (IOException ex) {
            showError("Unable to read users.txt");
        }
    }

    private void register() {
        String user = usernameField.getText().trim();
        String pass = new String(passwordField.getPassword()).trim();
        if (user.isEmpty() || pass.isEmpty()) {
            showError("Fields cannot be empty");
            return;
        }

        try {
            File file = new File("users.txt");
            file.createNewFile(); // if it doesn't exist
            List<String> lines = Files.readAllLines(file.toPath());
            for (String line : lines) {
                if (line.startsWith(user + ":")) {
                    showError("Username already exists");
                    return;
                }
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(user + ":" + pass);
                writer.newLine();
            }

            JOptionPane.showMessageDialog(frame, "Registration successful! Please login.");
        } catch (IOException ex) {
            showError("Unable to write to users.txt");
        }
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(frame, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        new LoginGUI();
    }
}
