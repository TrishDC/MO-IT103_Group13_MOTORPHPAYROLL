
package MotorPH_GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginForm extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    
    
    public LoginForm() {
        initComponents();
    }
    
    private void initComponents() {
        setTitle("MotorPH Employee Login System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());
        setResizable(false);
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        
        // Title
        JLabel titleLabel = new JLabel("MotorPH Employee System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(titleLabel, gbc);
        
        // Username
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Username:"), gbc);
        
        usernameField = new JTextField(20);
        gbc.gridx = 1;
        add(usernameField, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Password:"), gbc);
        
        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        add(passwordField, gbc);
        
        // Login Button
        loginButton = new JButton("Login");
        loginButton.setPreferredSize(new Dimension(100, 30));
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performLogin();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(loginButton, gbc);
       
    }
    
    private void performLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password.",
                "Login Error",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        if (LoginCredentialsGenerator.validateLogin(username, password)) {
            String employeeId = LoginCredentialsGenerator.getEmployeeIdByLogin(username, password);
            JOptionPane.showMessageDialog(this,
                "Login successful! Welcome, Employee ID: " + employeeId,
                "Login Successful",
                JOptionPane.INFORMATION_MESSAGE);
            
            // Close login form and open main application
            dispose();
            
            // Open the main application window
            SwingUtilities.invokeLater(() -> {
                try {
                    EmployeeViewTable mainApp = new EmployeeViewTable();
                    mainApp.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                        "Error opening main application: " + e.getMessage(),
                        "Application Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            });
            
        } else {
            JOptionPane.showMessageDialog(this,
                "Invalid username or password. Please check your credentials and try again.",
                "Login Failed",
                JOptionPane.ERROR_MESSAGE);
            
            // Clear password field for security
            passwordField.setText("");
            usernameField.requestFocus();
        }
    }
    
    
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new LoginForm().setVisible(true);
        });
    }
}
