import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;

public class BonusPage extends JPanel {

    private String mode;
    private String text_mode;
    private String username;
    private int player_id;

    private JLabel coinLabel;
    CoinManager coinManager = new CoinManager();
    ScoreManager scoreManager = new ScoreManager();
    private int coins;

    Style style = new Style();

    private LevelPurchaseManager buyLevel = new LevelPurchaseManager();

    BonusPage(String mode, String username) {
        this.mode = mode;
        this.username = username;
        player_id = scoreManager.getPlayerID(username);
        coins = coinManager.getCoin(player_id);
        text_mode = getTextMode(mode);
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));
        setBackground(style.modeBgColor);


        Font titleFont = new Font(style.font_title, Font.BOLD, style.h1);
        Font levelFont = new Font(style.font_button, Font.BOLD, 18);
        Font btn_info = new Font(style.font_button, Font.BOLD, style.text_font_medium);
        int borderRadius = 10;


        coinLabel = new JLabel("Coins: " + coins);
        coinLabel.setFont(btn_info);
        coinLabel.setBounds(950, 10, style.info_btn_width, style.info_btn_height);
        coinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coinLabel.setBackground(Color.WHITE);
        coinLabel.setOpaque(true);

        JPanel centerPanel = new JPanel(new GridLayout(2, 1, 10, 0));
        centerPanel.setBounds(220, 100, 820, 600); // ubah posisi panel dari sini

        RoundedButton backButton = new RoundedButton("Back");
        backButton.setFont(btn_info);
        backButton.setBounds(10, 10, style.info_btn_width, style.info_btn_height);
        backButton.setBackground(style.backButtonColor);
        backButton.setForeground(style.whiteColor);
        backButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        backButton.setCornerRadius(borderRadius);
        backButton.setBorderColor(Color.WHITE);
        backButton.setBorderThickness(2);

        RoundedButton leaderboard = new RoundedButton("Leaderboard");
        leaderboard.setFont(btn_info);
        leaderboard.setBounds(1080, 10, 150, style.info_btn_height);
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

        level1.setFont(levelFont);
        level2.setFont(levelFont);
        level3.setFont(levelFont);

        level1.setPreferredSize(level_button_size);
        level2.setPreferredSize(level_button_size);
        level3.setPreferredSize(level_button_size);

        level1.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        level1.setCornerRadius(borderRadius);
        level2.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        level2.setCornerRadius(borderRadius);
        level3.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        level3.setCornerRadius(borderRadius);

        level1.setForeground(style.blackColor);
        level2.setForeground(style.blackColor);
        level3.setForeground(style.blackColor);

        level1.setBorderColor(getLevelButtonColor(mode));
        level2.setBorderColor(getLevelButtonColor(mode));
        level3.setBorderColor(getLevelButtonColor(mode));

        level1.setBackground(getLevelButtonColor(mode));
        level2.setBackground(getLevelButtonColor(mode));
        level3.setBackground(getLevelButtonColor(mode));

        JPanel levelContainer = new JPanel(new FlowLayout(FlowLayout.CENTER));

        levelContainer.add(level1);
        levelContainer.add(level2);
        levelContainer.add(level3);

        // levelContainer.setBackground(backgroundColor);
        // centerPanel.setBackground(backgroundColor);
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
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "home");
            }
        });

        leaderboard.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // CardLayout cardLayout = (CardLayout) getParent().getLayout();
                // cardLayout.show(getParent(), "leaderboard");
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
                purchase(mode + "1", 50, 1);
            }
        });

        level2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchase(mode + "2", 60, 2);
            }
        });
        level3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                purchase(mode + "3", 70, 3);
            }
        });
    }

    // salah disini bang 🐗🐗🐗🐗🐗🐗🐗

    public void purchase(String levelId, int coins, int levelNumber) {
        // check player transaction (apakah pernah atau tidak)
        if (buyLevel.getPlayerTransaction(player_id, levelId)) { // cek transaksi (return boolean)
            showLevelPage(mode, levelId, username, BonusPage.this); // masuk ke level
        } else {

            int choice = JOptionPane.showOptionDialog(
                    BonusPage.this,
                    "Are you want to unlock this level?",
                    "Purchase Level",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[] { coins + " Coins", "No thanks" },
                    "Im Ready");

            if (choice == JOptionPane.YES_OPTION) {
                purchaseAction(levelId, coins, levelNumber);
            }
        }
    }

    public void purchaseAction(String levelId, int coins, int levelNumber) {

        boolean status = buyLevel.purchaseLevel(player_id, levelId, coins);

        if (status) {
            buyLevel.addPlayerTransaction(player_id, levelId); // menambahkan transaksi user pada db
            showLevelPage(mode, levelId, username, BonusPage.this);
        }
    }

    private void showLevelPage(String mode, String level, String username, BonusPage bonusPage) {
        Container container = getParent();

        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof Level) {
                container.remove(component);
            }
        }

        CardLayout cardLayout = (CardLayout) container.getLayout();

        BonusLevel bonusLevel = new BonusLevel(mode, level, username, BonusPage.this);

        container.add(bonusLevel, level);

        cardLayout.show(container, level);
        bonusLevel.readyPopup();

        container.revalidate();
        container.repaint();
        updateCoinLabel();
    }

    private String getTextMode(String mode) {
        switch (mode) {
            case "bonus":
                return "Bonus";
            default:
                return "Bonus";
        }
    }

    public void updateCoinLabel() {
        coins = coinManager.getCoin(player_id);
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