import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionManager {
    private Connection connection;

    public QuestionManager() {
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

    public String getQuestionID(String mode, String level) {
        String questionId = null;
        String query = "SELECT id_question FROM question " +
                "WHERE id_mode = ? AND id_level = ? " +
                "ORDER BY RAND() LIMIT 1";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, mode);
            preparedStatement.setString(2, level);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    questionId = resultSet.getString("id_question");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questionId;
    }

    public String getQuestion(String questionID) {
        String question = null;
        String query = "SELECT pertanyaan FROM question " +
                "WHERE id_question = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, questionID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    question = resultSet.getString("pertanyaan");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return question;
    }

    public String getAnswer(String questionID ) {
        String jawaban = null;
        String query = "SELECT jawaban FROM question " +
                "WHERE id_question = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, questionID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    jawaban = resultSet.getString("jawaban");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jawaban;
    }

    public List<String> getAnswerChoises(String questionID) {
        List<String> answerChoises = new ArrayList<>();

        String query = "SELECT jawaban FROM pilihan_jwbn " +
                "WHERE id_question = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, questionID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    answerChoises.add(resultSet.getString("jawaban"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answerChoises;
    }
}
