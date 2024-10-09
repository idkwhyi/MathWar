import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LeaderboardManager {
    private Connection connection;

    public LeaderboardManager() {
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

    // get PLAYER ID
    public List<Integer> getAllPlayerIds() {
        List<Integer> playerIds = new ArrayList<>();

        String query = "SELECT ID FROM player";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int playerId = resultSet.getInt("ID");
                playerIds.add(playerId);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playerIds;
    }

    // function to get object of player data
    public List<Object[]> getPlayerScoreData(String mode) {
        List<Integer> playerIds = getAllPlayerIds();
        List<Object[]> idTotalScorePairs = new ArrayList<>();

        for (int playerId : playerIds) {
            int totalScore = getTotalScoreForPlayerAndMode(playerId, mode);
            Object[] pair = { playerId, totalScore };
            idTotalScorePairs.add(pair);
        }

        Collections.sort(idTotalScorePairs, Comparator.comparing((Object[] pair) -> (int) pair[1]).reversed());

        return idTotalScorePairs;
    }

    private int getTotalScoreForPlayerAndMode(int playerId, String mode) {
        int totalScore = 0;
        String getScoreQuery = "SELECT score FROM score WHERE ID = ? AND id_mode = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(getScoreQuery)) {
            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, mode);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    int score = resultSet.getInt("score");
                    totalScore += score;
                }
                // totalScore = totalScore;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return totalScore;
    }

    // function to get username
    public String getUsername(int id) {
        String username = null;
        String query = "SELECT username FROM player WHERE ID = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    username = resultSet.getString("username");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username;
    }

}
