import java.sql.*;

public class CoinManager {
    private Connection connection;

    public CoinManager() {
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

    public int getPlayerID(String username) {
        int player_id = 0;
        String query = "SELECT ID FROM player " +
                "WHERE username = ? ";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, username);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    player_id = resultSet.getInt("ID");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return player_id;
    }

    public int getCoin(int playerId) {
        int coin = 0;
        String query = "SELECT jumlah_koin FROM coin " +
                "WHERE ID = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, playerId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    coin = resultSet.getInt("jumlah_koin");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return coin;
    }

    public void updateCoin(int playerId, int coins) {
        // int defaultCoin = 0;
        System.out.println(playerId);
        System.out.println(coins);
        // String query;

        try {
            // Check if the user already has a score
            String checkScoreQuery = "SELECT * FROM coin WHERE ID = ?";
            try (PreparedStatement checkScoreStatement = connection.prepareStatement(checkScoreQuery)) {
                checkScoreStatement.setInt(1, playerId);

                try (ResultSet resultSet = checkScoreStatement.executeQuery()) {
                    if (resultSet.next()) {
                        updateNewCoin(playerId, coins);
                    } else {
                        String insertCoinQuery = "INSERT INTO coin (ID, jumlah_koin) VALUES (?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertCoinQuery)) {
                            insertStatement.setInt(1, playerId);
                            insertStatement.setInt(2, 0);

                            int rowsAffected = insertStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("New coin added successfully.");
                            } else {
                                System.out.println("Failed to add new coin.");
                            }

                            updateNewCoin(playerId, coins);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    void updateNewCoin(int playerId, int coins) {
        int coinBefore = getCoin(playerId);
        int coinAfter = coinBefore + coins;

        String updateQuery = "UPDATE coin SET jumlah_koin = ? WHERE ID = ?";
        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
            updateStatement.setInt(1, coinAfter);
            updateStatement.setInt(2, playerId);

            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Coin updated successfully.");
            } else {
                System.out.println("Failed to update coin.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
