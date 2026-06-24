package gui;

import controller.CampusManager;

import javax.swing.*;
import java.awt.*;

public class loginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public loginFrame() {

        setTitle("Campus Management Login");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Main Panel
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);

        // Title
        JLabel titleLabel = new JLabel("Campus Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setBounds(70, 20, 300, 30);

        // Username
        JLabel userLabel = new JLabel("Username:");
        userLabel.setBounds(50, 70, 100, 25);

        usernameField = new JTextField();
        usernameField.setBounds(150, 70, 180, 25);

        // Password
        JLabel passLabel = new JLabel("Password:");
        passLabel.setBounds(50, 110, 100, 25);

        passwordField = new JPasswordField();
        passwordField.setBounds(150, 110, 180, 25);

        // Login Button
        JButton loginBtn = new JButton("Login");
        loginBtn.setBounds(140, 160, 100, 30);

        loginBtn.setFocusPainted(false);

        // Button Logic
        loginBtn.addActionListener(e -> login());

        // Add Components
        panel.add(titleLabel);

        panel.add(userLabel);
        panel.add(usernameField);

        panel.add(passLabel);
        panel.add(passwordField);

        panel.add(loginBtn);

        add(panel);
    }

    // Login Method
    private void login() {

        String username = usernameField.getText();
        String password = String.valueOf(passwordField.getPassword());

        // Simple fixed login
        model.Role role = null;

        if (username.equals("admin") && password.equals("1234")) {
            role = model.Role.ADMIN;
        } else if (username.equals("teacher") && password.equals("5678")) {
            role = model.Role.TEACHER;
        } else if (username.equals("student") && password.equals("0000")) {
            role = model.Role.STUDENT;
        }

        if (role != null) {
            JOptionPane.showMessageDialog(this, "Welcome! Logged in as: " + role);
            CampusManager manager = CampusManager.loadSystem("campus.dat");
            MainFrame mf = new MainFrame(manager, role);
            mf.setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this,
                    "Invalid credentials!\n\nHint:\nadmin/1234\nteacher/5678\nstudent/0000",
                    "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}