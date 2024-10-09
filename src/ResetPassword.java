import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.awt.*;

public class ResetPassword extends JPanel {

    private DatabaseManager databaseManager = new DatabaseManager();
    private String enteredUsername;

    Style style = new Style();

    ResetPassword() {
        initComponents();
    }

    // JButton submitButton;
    RoundedButton backButton;
    RoundedButton resetPasswordButton;

    public void initComponents() {
        // setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));
        setBackground(style.backgroundColor);

        // styling
        Font titleFont = new Font(style.font_title, Font.BOLD, style.h1);
        Font labelFont = new Font(style.font_text, Font.BOLD, style.h3);
        Font inputFont = new Font(style.font_text, Font.PLAIN, style.text_font_big);
        Font buttonFont = new Font(style.font_button, Font.BOLD, style.text_font_medium);
        Font backFont = new Font(style.font_button, Font.PLAIN, style.text_font_small);


        int top = 1;
        int right = 7;
        int bottom = 1;
        int left = 7;
        int borderRadius = 10;
        // styling

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(430, 85, 420, 720);
        mainPanel.setLayout(new GridLayout(7, 1, 15, 20));
        mainPanel.setBackground(style.backgroundColor);

        JLabel loginTitle = new JLabel("Reset Password");
        loginTitle.setFont(titleFont);
        loginTitle.setHorizontalAlignment(JLabel.CENTER);
        loginTitle.setVerticalAlignment(JLabel.CENTER);
        loginTitle.setForeground(style.whiteColor);
        loginTitle.setOpaque(false);

        // username
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new GridLayout(2, 1, 0, 5));
        usernamePanel.setPreferredSize(new Dimension(200, 100));
        usernamePanel.setBackground(style.backgroundColor);

        JLabel usernameLabel = new JLabel("Your Username");
        usernameLabel.setFont(labelFont);
        usernameLabel.setForeground(style.whiteColor);
        usernameLabel.setOpaque(false);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(250, 40));
        usernameField.setFont(inputFont);
        usernameField.setBorder(new EmptyBorder(top, left, bottom, right));

        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);

        // password
        JPanel passwordPanel = new JPanel();
        passwordPanel.setLayout(new GridLayout(2, 1, 0, 5));
        passwordPanel.setPreferredSize(new Dimension(300, 100));
        passwordPanel.setBackground(style.backgroundColor);

        JLabel passwordLabel = new JLabel("New Password");
        passwordLabel.setFont(labelFont);
        passwordLabel.setForeground(style.whiteColor);
        passwordLabel.setOpaque(false);

        JTextField passwordField = new JTextField();
        passwordField.setPreferredSize(new Dimension(250, 40));
        passwordField.setFont(inputFont);
        passwordField.setBorder(new EmptyBorder(top, left, bottom, right));

        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        // email
        JPanel emailPanel = new JPanel();
        emailPanel.setLayout(new GridLayout(2, 1, 0, 5));
        emailPanel.setPreferredSize(new Dimension(300, 100));
        emailPanel.setBackground(style.backgroundColor);

        JLabel emaiLabel = new JLabel("Your Email");
        emaiLabel.setFont(labelFont);
        emaiLabel.setForeground(style.whiteColor);
        emaiLabel.setOpaque(false);

        JTextField emailField = new JTextField();
        emailField.setPreferredSize(new Dimension(250, 40));
        emailField.setFont(inputFont);
        emailField.setBorder(new EmptyBorder(top, left, bottom, right));

        emailPanel.add(emaiLabel);
        emailPanel.add(emailField);

        // buttons
        backButton = new RoundedButton("Back");
        backButton.setFont(backFont);
        backButton.setPreferredSize(new Dimension(200, 75));
        backButton.setForeground(style.whiteColor);
        backButton.setBackground(style.backgroundColor);
        backButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        backButton.setCornerRadius(borderRadius);
        backButton.setBorderColor(Color.WHITE);
        backButton.setBorderThickness(2);

        resetPasswordButton = new RoundedButton("Reset Password");
        resetPasswordButton.setFont(buttonFont);
        resetPasswordButton.setPreferredSize(new Dimension(200, 75));
        resetPasswordButton.setForeground(style.greyColor);
        resetPasswordButton.setBackground(new Color(0xffffff));
        resetPasswordButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        resetPasswordButton.setCornerRadius(borderRadius);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(style.backgroundColor);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(resetPasswordButton);

        mainPanel.add(loginTitle);
        mainPanel.add(usernamePanel);
        mainPanel.add(passwordPanel);
        mainPanel.add(emailPanel);
        mainPanel.add(buttonPanel);

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
        setAlignmentY(Component.CENTER_ALIGNMENT);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "login");
            }
        });

        resetPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                enteredUsername = usernameField.getText();
                String enteredPassword = passwordField.getText();
                String enteredEmail = emailField.getText();

                boolean resetStatus = databaseManager.resetPassword(enteredUsername, enteredPassword, enteredEmail);

                if (resetStatus) {
                    JOptionPane.showMessageDialog(ResetPassword.this,
                            "Your password has been successfully reset", "Password Reset Complete",
                            JOptionPane.INFORMATION_MESSAGE);
                    System.out.println("success resetpass");
                    CardLayout cardLayout = (CardLayout) getParent().getLayout();
                    cardLayout.show(getParent(), "login");
    
                } else {
                    JOptionPane.showMessageDialog(ResetPassword.this,
                            "The data you entered is incorrect. Please check again!", "Login Error",
                            JOptionPane.WARNING_MESSAGE);
                }

                // i want a popup appear after password reset before back to login page
            }
        });
    }
}