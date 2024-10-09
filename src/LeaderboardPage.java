import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;
import java.util.List;

public class LeaderboardPage extends JPanel {

    LeaderboardManager leaderboardManager = new LeaderboardManager();

    List<Object[]> easyPlayerScoreData;
    List<Object[]> mediumPlayerScoreData;
    List<Object[]> hardPlayerScoreData;

    Style style = new Style();

    LeaderboardPage() {
        easyPlayerScoreData = leaderboardManager.getPlayerScoreData("ez");
        mediumPlayerScoreData = leaderboardManager.getPlayerScoreData("md");
        hardPlayerScoreData = leaderboardManager.getPlayerScoreData("hrd");
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setPreferredSize(new Dimension(1280, 720));
        setBackground(style.leaderboardBgColor);

        // styling
        int borderRadius = 10;
        Font titleFont = new Font(style.font_title, Font.BOLD, style.h1);
        Font labelFont = new Font(style.font_title, Font.BOLD, style.h3);
        Font leaderboardItemsFont = new Font(style.font_text, Font.BOLD, style.text_font_medium);
        Font leaderboardRankFont = new Font(style.font_text, Font.BOLD, style.text_font_big);
        Font btn_info = new Font(style.font_button, Font.BOLD, style.text_font_medium);

        JPanel gap = new JPanel();
        gap.setPreferredSize(new Dimension(0, 25));
        gap.setOpaque(false);
        JPanel gap2 = new JPanel();
        gap2.setPreferredSize(new Dimension(0, 25));
        gap2.setOpaque(false);

        // margin
        JPanel marginLeft = new JPanel();
        marginLeft.setPreferredSize(new Dimension(20, 60));
        marginLeft.setBackground(style.leaderboardBgColor);
        JPanel marginRight = new JPanel();
        marginRight.setPreferredSize(new Dimension(20, 60));
        marginRight.setBackground(style.leaderboardBgColor);
        // margin

        // back button
        JPanel buttonContainer = new JPanel();
        buttonContainer.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        buttonContainer.setPreferredSize(new Dimension(1280, 90));
        buttonContainer.setBackground(style.leaderboardBgColor);

        RoundedButton backButton = new RoundedButton("Back");
        backButton.setPreferredSize(new Dimension(100, 50));
        backButton.setFont(btn_info);
        backButton.setBackground(style.backButtonColor);
        backButton.setForeground(style.whiteColor);
        backButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        backButton.setCornerRadius(borderRadius);
        backButton.setBorderColor(Color.WHITE);
        backButton.setBorderThickness(2);

        JLabel title = new JLabel("Leaderboard");
        title.setPreferredSize(new Dimension(240, 50));
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(titleFont);
        title.setBackground(style.leaderboardBgColor);
        title.setForeground(style.whiteColor);

        buttonContainer.add(marginLeft);
        buttonContainer.add(backButton);
        buttonContainer.add(title);
        // back button

        // main leaderboard
        int easyLength = easyPlayerScoreData.size() + 2;
        int mediumLength = mediumPlayerScoreData.size() + 1;
        int hardLength = hardPlayerScoreData.size() + 1;

        // height
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(style.leaderboardBgColor);
        panel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 15));

        RoundedPanel easyContainer = new RoundedPanel(20);
        easyContainer.setLayout(new BorderLayout());
        RoundedPanel mediumContainer = new RoundedPanel(20);
        mediumContainer.setLayout(new BorderLayout());
        RoundedPanel hardContainer = new RoundedPanel(20);
        hardContainer.setLayout(new BorderLayout());

        easyContainer.setBackground(style.leaderboardLayerColor);
        mediumContainer.setBackground(style.leaderboardLayerColor);
        hardContainer.setBackground(style.leaderboardLayerColor);

        JLabel easyLabel = new JLabel("Easy Mode");
        easyLabel.setFont(labelFont);
        easyLabel.setForeground(style.whiteColor);
        easyLabel.setBorder(BorderFactory.createEmptyBorder(2, 3, 20, 5));
        JLabel mediumLabel = new JLabel("Medium Mode");
        mediumLabel.setFont(labelFont);
        mediumLabel.setForeground(style.whiteColor);
        mediumLabel.setBorder(BorderFactory.createEmptyBorder(2, 3, 20, 5));
        JLabel hardLabel = new JLabel("Hard Mode");
        hardLabel.setFont(labelFont);
        hardLabel.setForeground(style.whiteColor);
        hardLabel.setBorder(BorderFactory.createEmptyBorder(2, 3, 20, 5));

        JPanel easyLeaderboard = new JPanel(new GridLayout(easyLength, 1, 0, 5));
        JPanel mediumLeaderboard = new JPanel(new GridLayout(mediumLength, 1, 0, 5));
        JPanel hardLeaderboard = new JPanel(new GridLayout(hardLength, 1, 0, 5));

        easyLeaderboard.setBackground(style.leaderboardBgColor);
        mediumLeaderboard.setBackground(style.leaderboardBgColor);
        hardLeaderboard.setBackground(style.leaderboardBgColor);

        easyLeaderboard.setOpaque(false);
        mediumLeaderboard.setOpaque(false);
        hardLeaderboard.setOpaque(false);

        JPanel easyItems = new JPanel(new GridLayout(1, 4, 15, 0));
        JPanel mediumItems = new JPanel(new GridLayout(1, 3, 15, 0));
        JPanel hardItems = new JPanel(new GridLayout(1, 3, 15, 0));

        JLabel rank_text_easy = new JLabel("Rank");
        rank_text_easy.setFont(leaderboardItemsFont);
        rank_text_easy.setBackground(style.leaderboardLayerColor);
        rank_text_easy.setForeground(style.whiteColor);
        JLabel username_text_easy = new JLabel("Username");
        username_text_easy.setFont(leaderboardItemsFont);
        username_text_easy.setBackground(style.leaderboardLayerColor);
        username_text_easy.setForeground(style.whiteColor);
        JLabel score_text_easy = new JLabel("Score");
        score_text_easy.setFont(leaderboardItemsFont);
        score_text_easy.setBackground(style.leaderboardLayerColor);
        score_text_easy.setForeground(style.whiteColor);

        JLabel rank_text_medium = new JLabel("Rank");
        rank_text_medium.setFont(leaderboardItemsFont);
        rank_text_medium.setBackground(style.leaderboardLayerColor);
        rank_text_medium.setForeground(style.whiteColor);
        JLabel username_text_medium = new JLabel("Username");
        username_text_medium.setFont(leaderboardItemsFont);
        username_text_medium.setBackground(style.leaderboardLayerColor);
        username_text_medium.setForeground(style.whiteColor);
        JLabel score_text_medium = new JLabel("Score");
        score_text_medium.setFont(leaderboardItemsFont);
        score_text_medium.setBackground(style.leaderboardLayerColor);
        score_text_medium.setForeground(style.whiteColor);

        JLabel rank_text_hard = new JLabel("Rank");
        rank_text_hard.setFont(leaderboardItemsFont);
        rank_text_hard.setBackground(style.leaderboardLayerColor);
        rank_text_hard.setForeground(style.whiteColor);
        JLabel username_text_hard = new JLabel("Username");
        username_text_hard.setFont(leaderboardItemsFont);
        username_text_hard.setBackground(style.leaderboardLayerColor);
        username_text_hard.setForeground(style.whiteColor);
        JLabel score_text_hard = new JLabel("Score");
        score_text_hard.setFont(leaderboardItemsFont);
        score_text_hard.setBackground(style.leaderboardLayerColor);
        score_text_hard.setForeground(style.whiteColor);

        easyItems.setBackground(style.leaderboardLayerColor);
        mediumItems.setBackground(style.leaderboardLayerColor);
        hardItems.setBackground(style.leaderboardLayerColor);

        easyItems.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 5));
        mediumItems.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 5));
        hardItems.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 5));

        easyItems.add(rank_text_easy);
        easyItems.add(username_text_easy);
        easyItems.add(score_text_easy);

        mediumItems.add(rank_text_medium);
        mediumItems.add(username_text_medium);
        mediumItems.add(score_text_medium);

        hardItems.add(rank_text_hard);
        hardItems.add(username_text_hard);
        hardItems.add(score_text_hard);

        easyLeaderboard.add(easyItems);
        mediumLeaderboard.add(mediumItems);
        hardLeaderboard.add(hardItems);

        // Border border = BorderFactory.createMatteBorder(01, 01, 1, 01,
        // style.whiteColor);

        for (int i = 0; i < easyPlayerScoreData.size(); i++) {
            Object[] pair = easyPlayerScoreData.get(i);
            int playerId = (int) pair[0];
            int totalScore = (int) pair[1];

            RoundedPanel entryPanel = new RoundedPanel(10);
            entryPanel.setLayout(new GridLayout(1, 3, 15, 0));
            entryPanel.setBackground(style.whiteColor);
            entryPanel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 5));

            int easy_rank = i + 1;
            JLabel rankLabel = new JLabel(String.valueOf(easy_rank));
            JLabel nameLabel = new JLabel(leaderboardManager.getUsername(playerId));
            JLabel scoreLabel = new JLabel(String.valueOf(totalScore));

            rankLabel.setFont(leaderboardRankFont);
            nameLabel.setFont(leaderboardRankFont);
            scoreLabel.setFont(leaderboardRankFont);

            rankLabel.setForeground(style.blackColor);
            nameLabel.setForeground(style.blackColor);
            scoreLabel.setForeground(style.blackColor);

            rankLabel.setOpaque(false);
            nameLabel.setOpaque(false);
            scoreLabel.setOpaque(false);

            // entryPanel.setBorder(border);
            entryPanel.add(rankLabel);
            entryPanel.add(nameLabel);
            entryPanel.add(scoreLabel);

            easyLeaderboard.add(entryPanel);
        }

        for (int i = 0; i < mediumPlayerScoreData.size(); i++) {
            Object[] pair = mediumPlayerScoreData.get(i);
            int playerId = (int) pair[0];
            int totalScore = (int) pair[1];

            RoundedPanel entryPanel = new RoundedPanel(10);
            entryPanel.setLayout(new GridLayout(1, 3, 15, 0));
            entryPanel.setBackground(style.whiteColor);
            entryPanel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 5));

            int easy_rank = i + 1;
            JLabel rankLabel = new JLabel(String.valueOf(easy_rank));
            JLabel nameLabel = new JLabel(leaderboardManager.getUsername(playerId));
            JLabel scoreLabel = new JLabel(String.valueOf(totalScore));

            rankLabel.setFont(leaderboardRankFont);
            nameLabel.setFont(leaderboardRankFont);
            scoreLabel.setFont(leaderboardRankFont);

            rankLabel.setForeground(style.blackColor);
            nameLabel.setForeground(style.blackColor);
            scoreLabel.setForeground(style.blackColor);

            rankLabel.setOpaque(false);
            nameLabel.setOpaque(false);
            scoreLabel.setOpaque(false);

            entryPanel.add(rankLabel);
            entryPanel.add(nameLabel);
            entryPanel.add(scoreLabel);

            mediumLeaderboard.add(entryPanel);
        }

        for (int i = 0; i < hardPlayerScoreData.size(); i++) {
            Object[] pair = hardPlayerScoreData.get(i);
            int playerId = (int) pair[0];
            int totalScore = (int) pair[1];

            RoundedPanel entryPanel = new RoundedPanel(10);
            entryPanel.setLayout(new GridLayout(1, 3, 15, 0));
            entryPanel.setBackground(style.whiteColor);
            entryPanel.setBorder(BorderFactory.createEmptyBorder(2, 10, 2, 5));

            int easy_rank = i + 1;
            JLabel rankLabel = new JLabel(String.valueOf(easy_rank));
            JLabel nameLabel = new JLabel(leaderboardManager.getUsername(playerId));
            JLabel scoreLabel = new JLabel(String.valueOf(totalScore));

            rankLabel.setFont(leaderboardRankFont);
            nameLabel.setFont(leaderboardRankFont);
            scoreLabel.setFont(leaderboardRankFont);

            rankLabel.setForeground(style.blackColor);
            nameLabel.setForeground(style.blackColor);
            scoreLabel.setForeground(style.blackColor);

            rankLabel.setOpaque(false);
            nameLabel.setOpaque(false);
            scoreLabel.setOpaque(false);

            entryPanel.add(rankLabel);
            entryPanel.add(nameLabel);
            entryPanel.add(scoreLabel);

            hardLeaderboard.add(entryPanel);
        }

        easyContainer.add(easyLabel, BorderLayout.NORTH);
        easyContainer.add(easyLeaderboard, BorderLayout.SOUTH);

        mediumContainer.add(mediumLabel, BorderLayout.NORTH);
        mediumContainer.add(mediumLeaderboard, BorderLayout.CENTER);

        hardContainer.add(hardLabel, BorderLayout.NORTH);
        hardContainer.add(hardLeaderboard, BorderLayout.CENTER);

        easyContainer.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        mediumContainer.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));
        hardContainer.setBorder(BorderFactory.createEmptyBorder(10, 15, 5, 15));

        panel.add(easyContainer);
        panel.add(gap);
        panel.add(mediumContainer);
        panel.add(gap2);
        panel.add(hardContainer);

        JScrollPane scrollPanel = new JScrollPane(panel);
        scrollPanel.setBackground(style.leaderboardBgColor);
        scrollPanel.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
        scrollPanel.setPreferredSize(new Dimension(900, 1000));

        scrollPanel.setBorder(null);

        // main leaderboard
        add(buttonContainer, BorderLayout.NORTH);
        add(marginLeft, BorderLayout.LINE_START);
        add(marginRight, BorderLayout.LINE_END);
        add(scrollPanel, BorderLayout.CENTER);
        // add(centerPanel, BorderLayout.CENTER);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "home");
            }
        });
    }
}