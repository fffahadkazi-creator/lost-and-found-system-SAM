package ui;

import dao.UserDAO;

import javax.swing.*;
import java.awt.*;

public class SignupForm extends JFrame {

    private JTextField nameField, emailField;
    private JPasswordField passwordField;

    public SignupForm() {
        setTitle("Campus Lost & Found - Sign Up");
        setSize(380, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Name:"), gbc);
        gbc.gridx = 1;
        nameField = new JTextField(15);
        add(nameField, gbc);

        gbc.gridy = 1; gbc.gridx = 0;
        add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        emailField = new JTextField(15);
        add(emailField, gbc);

        gbc.gridy = 2; gbc.gridx = 0;
        add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        passwordField = new JPasswordField(15);
        add(passwordField, gbc);

        JButton signupBtn = new JButton("Create Account");
        JButton backBtn = new JButton("Back to Login");

        gbc.gridy = 3; gbc.gridx = 0; gbc.gridwidth = 2;
        add(signupBtn, gbc);
        gbc.gridy = 4;
        add(backBtn, gbc);

        signupBtn.addActionListener(e -> doSignup());
        backBtn.addActionListener(e -> {
            new LoginForm().setVisible(true);
            dispose();
        });
    }

    private void doSignup() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required.");
            return;
        }

        boolean success = new UserDAO().signup(name, email, password);
        if (success) {
            JOptionPane.showMessageDialog(this, "Account created! Please log in.");
            new LoginForm().setVisible(true);
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Signup failed. That email may already be registered.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
