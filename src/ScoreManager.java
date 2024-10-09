import java.sql.*;

public class ScoreManager {
    private Connection connection;

    public ScoreManager() {
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

    public int getScore(int playerId, String levelId, String modeId) {
        int score = 0;
        String query = "SELECT score FROM score " +
                "WHERE ID = ? AND id_level = ? AND id_mode = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, playerId);
            preparedStatement.setString(2, levelId);
            preparedStatement.setString(3, modeId);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    score = resultSet.getInt("score");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return score;
    }

    public void updateScore(int playerId, int newScore, String levelId, String modeId) {
        try {
            String checkScoreQuery = "SELECT * FROM score WHERE ID = ? AND id_level = ? AND id_mode = ?";
            try (PreparedStatement checkScoreStatement = connection.prepareStatement(checkScoreQuery)) {
                checkScoreStatement.setInt(1, playerId);
                checkScoreStatement.setString(2, levelId);
                checkScoreStatement.setString(3, modeId);

                try (ResultSet resultSet = checkScoreStatement.executeQuery()) {
                    if (resultSet.next()) {
                        int oldScore = getScore(playerId, levelId, modeId);

                        if(newScore > oldScore){

                            String updateQuery = "UPDATE score SET score = ? WHERE ID = ? AND id_level = ? AND id_mode = ?";
                            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                                updateStatement.setInt(1, newScore);
                                updateStatement.setInt(2, playerId);
                                updateStatement.setString(3, levelId);
                                updateStatement.setString(4, modeId);
                                int rowsUpdated = updateStatement.executeUpdate();
                                if (rowsUpdated > 0) {
                                    System.out.println("Score updated successfully.");
                                } else {
                                    System.out.println("Failed to update score.");
                                }
                            } catch (SQLException e) {
                                e.printStackTrace();
                            }
                        }
                    } else {
                        String insertScoreQuery = "INSERT INTO score (score, ID, id_level, id_mode) VALUES (?, ?, ?, ?)";
                        try (PreparedStatement insertStatement = connection.prepareStatement(insertScoreQuery)) {
                            insertStatement.setInt(1, newScore);
                            insertStatement.setInt(2, playerId);
                            insertStatement.setString(3, levelId);
                            insertStatement.setString(4, modeId);

                            int rowsAffected = insertStatement.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("New score added successfully.");
                            } else {
                                System.out.println("Failed to add new score.");
                            }
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

    // public void updateScore(int playerId, int newScore, String levelId, String
    // modeId) {
    // int oldScore = getScore(playerId, levelId, modeId);

    // if (oldScore == 0) {
    // // No existing score for the player and level, insert a new record
    // String insertQuery = "INSERT INTO score (score, ID, id_level, id_mode) VALUES
    // (?, ?, ?, ?)";
    // try (PreparedStatement insertStatement =
    // connection.prepareStatement(insertQuery)) {
    // insertStatement.setInt(1, newScore);
    // insertStatement.setInt(2, playerId);
    // insertStatement.setString(3, levelId);
    // insertStatement.setString(4, modeId);
    // int rowsInserted = insertStatement.executeUpdate();
    // if (rowsInserted > 0) {
    // System.out.println("New score added successfully.");
    // } else {
    // System.out.println("Failed to add new score.");
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // } else if (newScore > oldScore) {
    // // Update the score if the new score is greater than the old score
    // String updateQuery = "UPDATE score SET score = ? WHERE ID = ? AND id_level =
    // ? AND id_mode = ?";
    // try (PreparedStatement updateStatement =
    // connection.prepareStatement(updateQuery)) {
    // updateStatement.setInt(1, newScore);
    // updateStatement.setInt(2, playerId);
    // updateStatement.setString(3, levelId);
    // updateStatement.setString(4, modeId);
    // int rowsUpdated = updateStatement.executeUpdate();
    // if (rowsUpdated > 0) {
    // System.out.println("Score updated successfully.");
    // } else {
    // System.out.println("Failed to update score.");
    // }
    // } catch (SQLException e) {
    // e.printStackTrace();
    // }
    // } else {
    // System.out.println("New score is not greater than the old score. No update
    // needed.");
    // }
    // }
}
