import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Level extends JPanel {
    QuestionManager questionManager = new QuestionManager();
    private String mode;
    private String level;
    private String username;
    private int player_id;

    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private int totalScore;

    private JLabel questionLabel;
    private RoundedButton[] answerButtons;
    private RoundedButton pauseButton;

    private Timer timer;
    JProgressBar timerBar;
    private int constTime;

    private int timerTime = constTime;
    private int coins;

    ModePage modePage;
    Style style = new Style();

    public Level(String mode, String level, String username, ModePage modePage) {
        this.mode = mode;
        this.level = level;
        this.username = username;
        this.modePage = modePage;

        UIManager.put("OptionPane.noIcon", Boolean.TRUE);
        // UIManager.put("OptionPaneUI", NoIconOptionPaneUI.class.getName());
        UIManager.put("OptionPane.messageFont", new Font(style.font_text, Font.PLAIN, style.text_font_medium));
        UIManager.put("OptionPane.messageForeground", style.whiteColor);
        UIManager.put("OptionPane.buttonFont", new Font(style.font_text, Font.PLAIN, style.text_font_small));
        UIManager.put("OptionPane.background", style.darkGreyColor);
        UIManager.put("Panel.background", style.darkGreyColor);

        int borderRadius = 10;
        Font textFont = new Font(style.font_text, Font.BOLD, style.text_font_big);
        Font answerButtonFont = new Font(style.font_button, Font.BOLD, style.text_font_medium);
        Font btn_info = new Font(style.font_button, Font.BOLD, style.text_font_medium);

        constTime = getTime(mode);

        questions = new ArrayList<>();
        initializeQuestions(this.mode, this.level);

        setLayout(null);
        setBackground(style.pureBlack);
        setPreferredSize(new Dimension(1280, 720));

        JPanel mainContainer = new JPanel();
        mainContainer.setBackground(style.pureBlack);
        mainContainer.setLayout(new GridLayout(3, 1, 0, 10));
        mainContainer.setBounds(220, 60, 820, 1000); // ubah posisi panel dari sini

        questionLabel = new JLabel("", SwingConstants.CENTER);
        questionLabel.setVisible(false);
        questionLabel.setFont(textFont);
        questionLabel.setBackground(style.greyColor);
        questionLabel.setForeground(style.whiteColor);
        mainContainer.add(questionLabel);

        JPanel answerPanel = new JPanel(new GridLayout(2, 2, 5, 5));
        answerPanel.setOpaque(false);
        answerButtons = new RoundedButton[4];

        for (int i = 0; i < answerButtons.length; i++) {
            answerButtons[i] = new RoundedButton("");
            answerButtons[i].setFont(answerButtonFont);
            answerButtons[i].setPreferredSize(new Dimension(400, 140));
            answerButtons[i].setBackground(getAnswerColor(i));
            answerButtons[i].setForeground(style.blackColor);
            answerButtons[i].setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
            answerButtons[i].setCornerRadius(borderRadius);
            answerButtons[i].addActionListener(new AnswerButtonListener());
            answerPanel.add(answerButtons[i]);
        }

        timerBar = new JProgressBar(0, constTime);
        timerBar.setValue(constTime);
        timerBar.setPreferredSize(new Dimension(750, 20));
        timerBar.setForeground(Color.RED);
        timerBar.setBackground(Color.BLACK);

        JPanel container = new JPanel();
        container.setLayout(new FlowLayout());
        container.setPreferredSize(new Dimension(600, 500));
        container.add(timerBar, BorderLayout.NORTH);
        container.add(answerPanel, BorderLayout.CENTER);
        container.setOpaque(false);

        mainContainer.add(container);

        pauseButton = new RoundedButton("Pause");
        pauseButton.setBounds(1160, 10, 100, 50);
        pauseButton.setFont(btn_info);
        pauseButton.setBackground(style.maroonColor);
        pauseButton.setForeground(style.whiteColor);
        pauseButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        pauseButton.setCornerRadius(borderRadius);
        // pauseButton.setBorderColor(Color.WHITE);
        // pauseButton.setBorderThickness(2);
        pauseButton.addActionListener(new PauseButtonListener());
        add(pauseButton);

        add(mainContainer);
        startLevel();
    }

    private void initializeQuestions(String mode, String level) {
        questions.clear();

        String id = questionManager.getQuestionID(mode, level);
        String question = questionManager.getQuestion(id);
        String answer = questionManager.getAnswer(id);

        questions.add(new Question(question, answer, id));
    }

    private void startLevel() {
        currentQuestionIndex = 0;
        displayQuestion();
    }

    private void displayQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            questionLabel.setText(currentQuestion.getQuestionText());

            List<String> answerOptions = new ArrayList<>(currentQuestion.getAnswerOptions());

            Collections.shuffle(answerOptions);

            for (int i = 0; i < answerButtons.length; i++) {
                answerButtons[i].setText(answerOptions.get(i));
            }
        } else {
            showLevelCompletedPopup();
        }
    }

    private void startTimer() {
        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timerTime--;
                timerBar.setValue(timerTime);
                if (timerTime == 0) {
                    timer.stop();
                    timesUp();
                }
            }
        });
        timer.start();
    }

    public void readyPopup() {
        int choice = JOptionPane.showOptionDialog(
                Level.this,
                "Are you ready to start the level?",
                "Ready to Start",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[] { "Im Ready", "Back To Mode" },
                "Im Ready");

        if (choice == JOptionPane.YES_OPTION) {
            questionLabel.setVisible(true);
            timerTime = constTime;
            startTimer();
        } else if (choice == JOptionPane.NO_OPTION) {
            modePage.revalidate();
            modePage.repaint();
            modePage.updateCoinLabel();
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "mode");
        } else {
            modePage.revalidate();
            modePage.repaint();
            modePage.updateCoinLabel();
            CardLayout cardLayout = (CardLayout) getParent().getLayout();
            cardLayout.show(getParent(), "mode");
        }
    }

    private void showLevelCompletedPopup() {
        timer.stop();
        totalScore = score * timerTime * 5;
        ScoreManager scoreManager = new ScoreManager();
        player_id = scoreManager.getPlayerID(username);
        scoreManager.updateScore(player_id, totalScore, level, mode);

        CoinManager coinManager = new CoinManager();

        String choiceText = "";


        if (totalScore == 0) {
            // coins = 0;
            // System.out.println("jumlah koin didapat: " + coins);
            // coinManager.updateCoin(player_id, coins);
            choiceText = "Your answer is wrong! ";
        } else {
            coins = getCoins(mode);
            // System.out.println("jumlah koin didapat: " + coins);
            coinManager.updateCoin(player_id, coins);
            choiceText = "Congratulations! You completed the level. ";
        }
        int choice = JOptionPane.showOptionDialog(
                Level.this,
                choiceText + "You Scored " + totalScore + "! Your high score: "
                        + scoreManager.getScore(player_id, level, mode) + " You got " + coins + " coins",
                "Level Completed",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[] { "Try Again", "Go To Level Page" },
                "Try Again");

        if (choice == JOptionPane.YES_OPTION) {
            // try again
            restartLevel();
        } else if (choice == JOptionPane.NO_OPTION) {
            timer.stop();
            goToLevelPage(mode, username, player_id);
        } else {
            timer.stop();
            goToLevelPage(mode, username, player_id);
        }
    }

    private void timesUp() {
        ScoreManager scoreManager = new ScoreManager();
        player_id = scoreManager.getPlayerID(username);
        scoreManager.updateScore(player_id, totalScore, level, mode);
        int choice = JOptionPane.showOptionDialog(
                Level.this,
                "Times up! You have run out of time",
                "Times up",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                new Object[] { "Try Again", "Go To Level Page" },
                "Try Again");

        if (choice == JOptionPane.YES_OPTION) {
            restartLevel();
        } else if (choice == JOptionPane.NO_OPTION) {
            timer.stop();
            goToLevelPage(mode, username, player_id);
        } else {
            timer.stop();
            goToLevelPage(mode, username, player_id);

        }
    }

    private class AnswerButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            RoundedButton clickedButton = (RoundedButton) e.getSource();
            String userAnswer = clickedButton.getText();

            Question currentQuestion = questions.get(currentQuestionIndex);
            if (currentQuestion.isCorrect(userAnswer)) {
                score++;
                currentQuestionIndex++;
                displayQuestion();
            } else {
                currentQuestionIndex++;
                displayQuestion();
            }
        }
    }

    private class PauseButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // timer.stop();
            int choice = JOptionPane.showOptionDialog(
                    Level.this,
                    "Game Paused",
                    "Pause Menu",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    new Object[] { "Resume", "Back To Level Page" },
                    "Resume");

            if (choice == JOptionPane.YES_OPTION) {
                //resumt
                // timer.start();
            } else if (choice == JOptionPane.NO_OPTION) {
                //back to level
                timer.stop();
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "mode");
            } else {
                timer.stop();
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "mode");
            }
        }
    }

    private void restartLevel() {
        currentQuestionIndex = 0;
        score = 0;
        coins = 0;
        timerTime = constTime;
        timerBar.setValue(timerTime);
        timer.start();
        initializeQuestions(mode, level);
        displayQuestion();
    }

    static class Question {
        private String questionText;
        private String correctAnswer;
        private String questionId;
        private QuestionManager qManager = new QuestionManager();

        public Question(String questionText, String correctAnswer, String questionId) {
            this.questionText = questionText;
            this.correctAnswer = correctAnswer;
            this.questionId = questionId;
        }

        public String getQuestionText() {
            return questionText;
        }

        public List<String> getAnswerOptions() {
            List<String> answerOptions = new ArrayList<>();
            List<String> choises = qManager.getAnswerChoises(questionId);

            answerOptions.add(correctAnswer);
            for (String answer : choises) {
                answerOptions.add(answer);
            }
            return answerOptions;
        }

        public boolean isCorrect(String userAnswer) {
            return correctAnswer.equals(userAnswer);
        }
    }

    private int getTime(String mode) {
        int time;
        switch (mode) {
            case "ez":
                time = 30;
                break;
            case "md":
                time = 20;
                break;
            case "hrd":
                time = 15;
                break;
            default:
                time = 30;
                coins = 75;
        }
        ;
        return time;
    }

    private int getCoins(String mode) {
        int coin;
        switch (mode) {
            case "ez":
                coin = 5;
                break;
            case "md":
                coin = 5;
                break;
            case "hrd":
                coin = 5;
                break;
            default:
                coin = 5;
                break;
        }
        ;
        return coin;
    }

    private Color getAnswerColor(int i) {
        switch (i) {
            case 0:
                return style.funYellowColor;
            case 1:
                return style.mediumColor;
            case 2:
                return style.easyColor;
            case 3:
                return style.hardColor;
            default:
                return style.whiteColor;
        }
    }

    private void goToLevelPage(String mode, String username, int playerID) {
        Container container = getParent();
        String fullmode = getFullMode(mode);

        Component[] components = container.getComponents();
        for (Component component : components) {
            if (component instanceof Mode) {
                container.remove(component);
            }
        }

        CardLayout cardLayout = (CardLayout) container.getLayout();

        Mode modePage = new Mode(mode, username, playerID, this.modePage);

        container.add(modePage, fullmode);

        cardLayout.show(container, fullmode);

        container.revalidate();
        container.repaint();
    }

    private String getFullMode(String mode) {
        switch (mode) {
            case "ez":
                return "easy";
            case "md":
                return "medium";
            case "hrd":
                return "hard";
            default:
                return "modeMode";
        }
    }
}