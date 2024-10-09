import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

import java.awt.event.*;


public class ModePage extends JPanel{

    Color backgroundColor = new Color(0x3887BE);
    private int coins = 0;
    private String username;
    private int player_id;
    private JLabel coinLabel;
    CoinManager coinManager = new CoinManager();

    Style style = new Style();

    ModePage(String username){
        this.username = username;
        player_id = coinManager.getPlayerID(username);
        coins = coinManager.getCoin(player_id);
        initComponents();
    }

    private void initComponents() {
        setLayout(null);
        setPreferredSize(new Dimension(1280, 720));
        setBackground(style.modeBgColor);

        Font btn_font = new Font(style.font_button, Font.BOLD, 24);
        Font btn_info = new Font(style.font_button, Font.BOLD, style.text_font_medium);

        int borderRadius = 10;

        
        coinLabel = new JLabel("Coins: " + coins);
        coinLabel.setBounds(1100, 10, style.info_btn_width, style.info_btn_height);
        coinLabel.setHorizontalAlignment(SwingConstants.CENTER);
        coinLabel.setBackground(Color.WHITE);
        coinLabel.setFont(btn_info);
        coinLabel.setOpaque(true);
        
        JPanel centerPanel = new JPanel(new GridLayout(1, 3, 10, 10));
        centerPanel.setBounds(280, 160, 720, 400);

        RoundedButton backButton = new RoundedButton("Back");
        backButton.setFont(btn_info);
        backButton.setBackground(style.backButtonColor);
        backButton.setForeground(style.whiteColor);
        backButton.setBounds(10, 10, style.info_btn_width, style.info_btn_height);
        backButton.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        backButton.setCornerRadius(borderRadius);
        backButton.setBorderColor(Color.WHITE); 
        backButton.setBorderThickness(2);

        RoundedButton btn1 = new RoundedButton("Easy Mode");
        btn1.setFont(btn_font);
        btn1.setBackground(new Color(0xFCF03A));
        btn1.setForeground(style.blackColor);
        btn1.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        btn1.setCornerRadius(borderRadius);

        RoundedButton btn2 = new RoundedButton("Medium Mode");
        btn2.setFont(btn_font);
        btn2.setBackground(new Color(0x27C9FF));
        btn2.setForeground(style.blackColor);
        btn2.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        btn2.setCornerRadius(borderRadius);

        RoundedButton btn3 = new RoundedButton("Hard Mode");
        btn3.setFont(btn_font);
        btn3.setBackground(new Color(0xFC3A6C));
        btn3.setForeground(style.blackColor);
        btn3.setBorder(new EmptyBorder(10, 20, 10, 20)); // Padding for text
        btn3.setCornerRadius(borderRadius);

        centerPanel.add(btn1);
        centerPanel.add(btn2);
        centerPanel.add(btn3);
        centerPanel.setOpaque(false);

        JLabel chooseText = new JLabel("Choose Mode:");
        chooseText.setBounds(280, 70, 500, 100);
        chooseText.setFont(new Font(style.font_title, Font.BOLD, style.h1));
        chooseText.setForeground(style.whiteColor);

        // add(coin);
        add(coinLabel);
        add(backButton);
        add(chooseText);
        add(centerPanel);

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e){
                CardLayout cardLayout = (CardLayout) getParent().getLayout();
                cardLayout.show(getParent(), "home");
            }
        });
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof Mode) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                Mode ezMode = new Mode("ez", username, player_id, ModePage.this);

                container.add(ezMode, "easy");

                cardLayout.show(container, "easy");

                container.revalidate();
                container.repaint();
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof Mode) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                Mode mdMode = new Mode("md", username, player_id, ModePage.this);

                container.add(mdMode, "medium");

                cardLayout.show(container, "medium");

                container.revalidate();
                container.repaint();
            }
        });
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Container container = getParent();

                Component[] components = container.getComponents();
                for (Component component : components) {
                    if (component instanceof Mode) {
                        container.remove(component);
                    }
                }

                CardLayout cardLayout = (CardLayout) container.getLayout();

                Mode hdMode = new Mode("hrd", username, player_id, ModePage.this);

                container.add(hdMode, "hard");

                cardLayout.show(container, "hard");

                container.revalidate();
                container.repaint();
            }
        });
    }

    public void updateCoinLabel() {
        coins = coinManager.getCoin(player_id);
        coinLabel.setText("Coins: " + coins);
    }
}
