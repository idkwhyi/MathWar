import java.awt.*;

import javax.swing.*;

public class App {
    public static void main(String[] args) throws Exception {

        DatabaseManager databaseManager = new DatabaseManager();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                JFrame main = new JFrame();
                main.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                main.setSize(1280, 720);

                main.setResizable(false);
                main.setTitle("Final Project");
                LoginPage loginPanel = new LoginPage(databaseManager);
                RegisterPage registerPanel =  new RegisterPage(databaseManager);
                
                main.getContentPane().setLayout(new CardLayout());
                main.getContentPane().add(loginPanel, "login");
                main.getContentPane().add(registerPanel, "register");
                
                main.pack();
                main.setLocationRelativeTo(null);
                main.setVisible(true);
            }
        });
    }
}