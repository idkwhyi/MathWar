import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LevelPurchaseManager {
    private Connection connection;

    public LevelPurchaseManager() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/MathMaster";
            String username = "root";
            String password = "";
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean purchaseLevel(int playerId, String levelId, int requiredCoins) {
        int userCoins = getCoin(playerId);

        if (userCoins >= requiredCoins) {

            deductCoins(playerId, requiredCoins);
            // unlockLevel(playerId, levelId);
            System.out.println("Level purchased successfully!");
            return true;
        } else {
            // User does not have enough coins
            System.out.println("Insufficient coins to purchase the level.");
            return false;
        }
    }

    private int getCoin(int playerId) {
        int coins = 0;
        String query = "SELECT jumlah_koin FROM coin WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, playerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    coins = resultSet.getInt("jumlah_koin");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coins;
    }

    private void deductCoins(int playerId, int coinsToDeduct) {
        String updateQuery = "UPDATE coin SET jumlah_koin = jumlah_koin - ? WHERE ID = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, coinsToDeduct);
            updateStatement.setInt(2, playerId);
            updateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addPlayerTransaction(int playerId, String levelId) {
        String insertQuery = "INSERT INTO levelPurchase (playerID, levelID) VALUES (?, ?)";
        try (PreparedStatement insertStatement = connection.prepareStatement(insertQuery)) {
            insertStatement.setInt(1, playerId);
            insertStatement.setString(2, levelId);
            insertStatement.executeUpdate();
            System.out.println("adding player transaction");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean getPlayerTransaction(int player_id, String level_id){
        boolean playerTransaction = false;

        String query = "SELECT * FROM levelPurchase WHERE playerID = ? AND levelID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, player_id);
            preparedStatement.setString(2, level_id);
    
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                playerTransaction = resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerTransaction;
    }
}
