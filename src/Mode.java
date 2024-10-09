import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.*;

public class Mode extends JPanel {

    private String mode;
    private String text_mode;
    private String username;
    private JLabel coinLabel;
    private int coins;
    private int player_id;
    CoinManager coinManager = new CoinManager();

    ModePage modePage;
    Style style = new Style();

    Mode(String mode, String username, int playerId, ModePage modePage) {
        this.mode = mode;
        this.username = username;
        this.player_id = playerId;
        this.modePage = modePage;
        text_mode = getTextMode(mode);
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));
        setBackground(style.modeBgColor);

        // style
        Font titleFont = new Font(style.font_title, Font.BOLD, style.h1);
        Font levelFont = new Font(style.font_button, Font.BOLD, 18);
        Font btn_info = new Font(style.font_button, Font.BOLD, style.text_font_medium);
        int borderRadius = 10;

        coinLabel = new JLabel("Coins: " + coinManager.getCoin(player_id));
        coinLabel.setBounds(950, 10, style.info_btn_width, style.info_btn_height);
        coinLabel.setFont(btn_info);
        coinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coinLabel.setBackground(Color.WHITE);
        coinLabel.setOpaque(true);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 0));
        centerPanel.setBounds(220, 100, 820, 600); // ubah posisi panel dari sini

        RoundedButton backButton = new RoundedButton("Back");
        backButton.setBounds(10, 10, style.info_btn_width, style.info_btn_height);
        backButton.setFont(btn_info);
        backButton.setBackground(style.backButtonColor);
        backButton.setForeground(style.whiteColor);
        backButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        backButton.setCornerRadius(borderRadius);
        backButton.setBorderColor(Color.WHITE);
        backButton.setBorderThickness(2);

        RoundedButton leaderboard = new RoundedButton("Leaderboard");
        leaderboard.setBounds(1080, 10, 150, style.info_btn_height);
        leaderboard.setFont(btn_info);
        leaderboard.setBackground(style.backButtonColor);
        leaderboard.setForeground(style.whiteColor);
        leaderboard.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        leaderboard.setCornerRadius(borderRadius);
        leaderboard.setBorderColor(Color.WHITE);
        leaderboard.setBorderThickness(2);

        JLabel titleLabel = new JLabel(text_mode + " Level", SwingConstants.CENTER);
        titleLabel.setForeground(style.whiteColor);
        titleLabel.setFont(titleFont);
        titleLabel.setBounds(280, 120, 720, 40);

        Dimension level_button_size = new Dimension(150, 150);

        RoundedButton level1 = new RoundedButton("Level 1");
        RoundedButton level2 = new RoundedButton("Level 2");
        RoundedButton level3 = new RoundedButton("Level 3");
        RoundedButton level4 = new RoundedButton("Level 4");
        RoundedButton level5 = new RoundedButton("Level 5");

        level1.setFont(levelFont);
        level2.setFont(levelFont);
        level3.setFont(levelFont);
        level4.setFont(levelFont);
        level5.setFont(levelFont);

        level1.setPreferredSize(level_button_size);
        level2.setPreferredSize(level_button_size);
        level3.setPreferredSize(level_button_size);
        level4.setPreferredSize(level_button_size);
        level5.setPreferredSize(level_button_size);

        level1.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        level1.setCornerRadius(borderRadius);
        level2.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        level2.setCornerRadius(borderRadius);
        level3.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        level3.setCornerRadius(borderRadius);
        level4.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        level4.setCornerRadius(borderRadius);
        level5.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        level5.setCornerRadius(borderRadius);

        level1.setForeground(style.blackColor);
        level2.setForeground(style.blackColor);
        level3.setForeground(style.blackColor);
        level4.setForeground(style.blackColor);
        level5.setForeground(style.blackColor);

        level1.setBorderColor(getLevelButtonColor(mode));
        level2.setBorderColor(getLevelButtonColor(mode));
        level3.setBorderColor(getLevelButtonColor(mode));
        level4.setBorderColor(getLevelButtonColor(mode));
        level5.setBorderColor(getLevelButtonColor(mode));

        level1.setBackground(getLevelButtonColor(mode));
        level2.setBackground(getLevelButtonColor(mode));
        level3.setBackground(getLevelButtonColor(mode));
        level4.setBackground(getLevelButtonColor(mode));
        level5.setBackground(getLevelButtonColor(mode));

        JPanel levelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));

        levelContainer.add(level1);
        levelContainer.add(level2);
        levelContainer.add(level3);
        levelContainer.add(level4);
        levelContainer.add(level5);

        levelContainer.setOpaque(false);
        centerPanel.setOpaque(false);
        centerPanel.add(titleLabel);
        centerPanel.add(levelContainer);

        add(coinLabel);
        add(backButton);
        add(leaderboard);
        add(centerPanel);

        backButton.addActionListener(new ActionListener() {
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

                container.add(modePage, "modepage");

                cardLayout.show(container, "modepage");

                container.revalidate();
                container.repaint();
            }
        });

        leaderboard.addActionListener(new ActionListener() {
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

        level1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof Level) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                Level level = new Level(mode, mode + "1", username, modePage);

                container.add(level, "level1");

                cardLayout.show(container, "level1");
                level.readyPopup();

                container.revalidate();
                container.repaint();
            }
        });

        level2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof Level) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                Level level = new Level(mode, mode + "2", username, modePage);

                container.add(level, "level2");

                cardLayout.show(container, "level2");
                level.readyPopup();

                container.revalidate();
                container.repaint();
            }
        });
        level3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof Level) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                Level level = new Level(mode, mode + "3", username, modePage);

                container.add(level, "level3");

                cardLayout.show(container, "level3");
                level.readyPopup();

                container.revalidate();
                container.repaint();
            }
        });
        level4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof Level) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                Level level = new Level(mode, mode + "4", username, modePage);

                container.add(level, "level4");

                cardLayout.show(container, "level4");
                level.readyPopup();

                container.revalidate();
                container.repaint();
            }
        });
        level5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof Level) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                Level level = new Level(mode, mode + "5", username, modePage);

                container.add(level, "level5");

                cardLayout.show(container, "level5");
                level.readyPopup();

                container.revalidate();
                container.repaint();
                // updateCoinLabel();
            }
        });
    }

    private String getTextMode(String mode) {
        switch (mode) {
            case "ez":
                return "Easy";
            case "md":
                return "Medium";
            case "hrd":
                return "Hard";
            default:
                return "Mode";
        }
    }

    public void updateCoinLabel() {
        coins = coinManager.getCoin(player_id);
        coinLabel.setText("");
        coinLabel.setText("Coins: " + coins);
    }

    private Color getLevelButtonColor(String mode) {
        switch (mode) {
            case "ez":
                return style.easyColor;
            case "md":
                return style.mediumColor;
            case "hrd":
                return style.hardColor;
            default:
                return style.purpleColor;
        }
    }
}