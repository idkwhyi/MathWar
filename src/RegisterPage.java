import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.awt.*;

public class RegisterPage extends JPanel {

    private DatabaseManager databaseManager;

    Style style = new Style();

    RegisterPage(DatabaseManager databaseManager) {
        this.databaseManager = databaseManager;
        initComponents();
    }

    RoundedButton backButton;
    RoundedButton createAccountButton;

    public void initComponents() {
        // setLayout(new FlowLayout(FlowLayout.CENTER, 0, 50));
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));
        setBackground(style.backgroundColor);

        // styling
        Font titleFont = new Font(style.font_title, Font.BOLD, style.h1);
        Font labelFont = new Font(style.font_text, Font.BOLD, style.text_font_big);
        Font inputFont = new Font(style.font_text, Font.PLAIN, style.text_font_big);
        Font buttonFont = new Font(style.font_button, Font.BOLD, style.text_font_medium);
        Font backFont = new Font(style.font_button, Font.PLAIN, style.text_font_small);

        int top = 1;
        int right = 7;
        int bottom = 1;
        int left = 7;
        int borderRadius = 10;


        JPanel marginTop = new JPanel();
        marginTop.setBackground(style.backgroundColor);
        marginTop.setPreferredSize(new Dimension(60, 0));

        JPanel mainPanel = new JPanel();
        mainPanel.setBounds(430, 0, 420, 600);
        mainPanel.setLayout(new GridLayout(6, 1, 20, 15));
        mainPanel.setBackground(style.backgroundColor);

        JLabel registerTitle = new JLabel("Register Here!");
        registerTitle.setFont(titleFont);
        registerTitle.setHorizontalAlignment(JLabel.CENTER);
        registerTitle.setVerticalAlignment(JLabel.CENTER);
        registerTitle.setForeground(style.whiteColor);
        registerTitle.setOpaque(false);

        // username
        JPanel usernamePanel = new JPanel();
        usernamePanel.setLayout(new GridLayout(2, 1, 0, 5));
        usernamePanel.setPreferredSize(new Dimension(200, 100));
        usernamePanel.setBackground(style.backgroundColor);

        JLabel usernameLabel = new JLabel("New Username");
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

        createAccountButton = new RoundedButton("Create Account");
        createAccountButton.setFont(buttonFont);
        createAccountButton.setPreferredSize(new Dimension(200, 75));
        createAccountButton.setForeground(style.whiteColor);
        createAccountButton.setBackground(style.purpleColor);
        createAccountButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        createAccountButton.setCornerRadius(borderRadius);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(style.backgroundColor);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(backButton);
        buttonPanel.add(createAccountButton);

        mainPanel.add(marginTop);
        mainPanel.add(registerTitle);
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

        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newUsername = usernameField.getText();
                String newPassword = passwordField.getText();
                String newEmail = emailField.getText();

                if (newUsername.isEmpty() || newPassword.isEmpty() || newEmail.isEmpty()) {
                    JOptionPane.showMessageDialog(RegisterPage.this,
                            "Cannot be empty. Please fill in all fields.", "Input Error",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean registrationSuccess = databaseManager.registerUser(newUsername, newPassword, newEmail);

                if (registrationSuccess) {
                    JOptionPane.showMessageDialog(RegisterPage.this,
                            "New Account has been Created Successfully", "New Account Created",
                            JOptionPane.INFORMATION_MESSAGE);
                    CardLayout cardLayout = (CardLayout) getParent().getLayout();
                    cardLayout.show(getParent(), "login");
                } else {
                    JOptionPane.showMessageDialog(RegisterPage.this,
                            "Username already exists. Please choose a different username.", "Registration Error",
                            JOptionPane.WARNING_MESSAGE);

                }
            }
        });
    }

}
