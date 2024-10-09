import javax.swing.*;
import java.awt.event.*;

import javax.swing.border.EmptyBorder;

import java.awt.*;

public class HomePage extends JPanel {

    private String username;
    private RoundedButton playButton;
    private RoundedButton leaderboardButton;
    private RoundedButton secretLevelButton;
    private RoundedButton quitButton;
    private int width = 400;
    private int height = 80;

    Style style = new Style();

    Color buttonColor = new Color(0x3887BE);

    public HomePage(String username) {
        this.username = username;
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));
        setBackground(style.backgroundColor);

        // styling
        Font textFont = new Font(style.font_text, Font.PLAIN, style.h3);
        Font buttonFont = new Font(style.font_button, Font.BOLD, style.h3);
        Font logoutFont = new Font(style.font_button, Font.BOLD, style.text_font_medium);
        int borderRadius = 10;

        JLabel usernameText = new JLabel("Welcome back, " + username + "!");
        usernameText.setFont(textFont);
        usernameText.setBounds(10, 5, 500, 40);
        usernameText.setForeground(style.whiteColor);
        add(usernameText);

        RoundedButton logoutButton = new RoundedButton("Log Out");
        logoutButton.setFont(logoutFont);
        logoutButton.setBounds(1140, 650, style.info_btn_width, style.info_btn_height);
        logoutButton.setForeground(style.blackColor);
        logoutButton.setBackground(style.whiteColor);
        logoutButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        logoutButton.setCornerRadius(borderRadius);
        logoutButton.setBorderColor(Color.WHITE);
        logoutButton.setBorderThickness(2);
        add(logoutButton);

        Dimension sizing = new Dimension(width, height);

        // Title and subtitle
        JLabel title = new JLabel("M&M");
        JLabel subTitle = new JLabel("Math Master!");

        title.setHorizontalTextPosition(JLabel.CENTER);
        title.setVerticalTextPosition(JLabel.CENTER);
        subTitle.setHorizontalTextPosition(JLabel.CENTER);
        subTitle.setVerticalTextPosition(JLabel.CENTER);

        title.setHorizontalAlignment(JLabel.CENTER);
        title.setVerticalAlignment(JLabel.CENTER);
        subTitle.setHorizontalAlignment(JLabel.CENTER);
        subTitle.setVerticalAlignment(JLabel.CENTER);

        title.setFont(new Font("Poppins", Font.BOLD, 86));
        subTitle.setFont(new Font("Poppins", Font.BOLD, 54));

        title.setForeground(style.whiteColor);
        title.setOpaque(false);

        subTitle.setForeground(style.whiteColor);
        subTitle.setOpaque(false);

        title.setPreferredSize(sizing);
        subTitle.setPreferredSize(sizing);
        // Title and subtitle

        // Button Components
        playButton = new RoundedButton("Play");
        leaderboardButton = new RoundedButton("Leaderboard");
        secretLevelButton = new RoundedButton("Bonus Level");
        quitButton = new RoundedButton("Quit");

        playButton.setFont(buttonFont);
        playButton.setForeground(style.blackColor);
        // playButton.setBackground(style.nintendoBlueColor);
        playButton.setBackground(style.whiteColor);
        playButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        playButton.setCornerRadius(borderRadius);

        secretLevelButton.setFont(buttonFont);
        secretLevelButton.setForeground(style.blackColor);
        // secretLevelButton.setBackground(style.purpleColor);
        secretLevelButton.setBackground(style.whiteColor);
        secretLevelButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        secretLevelButton.setCornerRadius(borderRadius);

        leaderboardButton.setFont(buttonFont);
        leaderboardButton.setForeground(style.blackColor);
        // leaderboardButton.setBackground(style.funYellowColor);
        leaderboardButton.setBackground(style.whiteColor);
        leaderboardButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        leaderboardButton.setCornerRadius(borderRadius);

        quitButton.setFont(buttonFont);
        quitButton.setForeground(style.blackColor);
        // quitButton.setBackground(style.nintendoRedColor);
        quitButton.setBackground(style.whiteColor);
        quitButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        quitButton.setCornerRadius(borderRadius);

        playButton.setPreferredSize(sizing);
        leaderboardButton.setPreferredSize(sizing);
        secretLevelButton.setPreferredSize(sizing);
        quitButton.setPreferredSize(sizing);

        // Button Components

        // title panel
        JPanel gameTextPanel = new JPanel();
        gameTextPanel.setLayout(new GridLayout(2, 1, 0, 15));
        gameTextPanel.setOpaque(false);
        gameTextPanel.add(title);
        gameTextPanel.add(subTitle);
        // title panel

        // button panel
        // margin top
        JPanel marginTopPanel = new JPanel();
        marginTopPanel.setPreferredSize(new Dimension(0, 50));

        marginTopPanel.setBackground(style.backgroundColor);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(style.backgroundColor);
        buttonPanel.setLayout(new GridLayout(4, 1, 40, 10));
        buttonPanel.setBorder(new EmptyBorder(0, 60, 0, 60));
        buttonPanel.add(playButton);
        buttonPanel.add(secretLevelButton);
        buttonPanel.add(leaderboardButton);
        buttonPanel.add(quitButton);

        JPanel mainButtonPanel = new JPanel();
        mainButtonPanel.setLayout(new BorderLayout());

        mainButtonPanel.add(buttonPanel, BorderLayout.SOUTH);
        // button panel

        // panel container
        JPanel panelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 50));
        panelContainer.setOpaque(false);
        panelContainer.setBounds(340, 30, 600, 700); // Adjust the bounds as needed


        panelContainer.add(gameTextPanel);
        panelContainer.add(mainButtonPanel);

        add(panelContainer);

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof ModePage) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                ModePage modePage = new ModePage(username);

                container.add(modePage, "mode");

                cardLayout.show(container, "mode");

                container.revalidate();
                container.repaint();
            }
        });

        leaderboardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof LeaderboardPage) {
                        container.remove(component);
                    }
                }
                CardLayout cardLayout = (CardLayout) container.getLayout();

                LeaderboardPage leaderboardPage = new LeaderboardPage();

                container.add(leaderboardPage, "leaderboard");

                cardLayout.show(container, "leaderboard");

                container.revalidate();
                container.repaint();
            }
        });

        secretLevelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof BonusPage) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                BonusPage bonusLevel = new BonusPage("bonus", username);

                container.add(bonusLevel, "bonus");

                cardLayout.show(container, "bonus");

                container.revalidate();
                container.repaint();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DatabaseManager databaseManager = new DatabaseManager();
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof LoginPage) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                LoginPage loginPage = new LoginPage(databaseManager);

                container.add(loginPage, "login");

                cardLayout.show(container, "login");

                container.revalidate();
                container.repaint();
            }
        });
    }
}