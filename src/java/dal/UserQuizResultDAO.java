package dal;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.UserQuizResult;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import model.Quiz;

/**
 *
 * @author Admin
 */
public class UserQuizResultDAO extends DBContext {

    /**
     * @param args the command line arguments
     */
//    public void addUserQuizResult(UserQuizResult result) {
//        try {
//            String sql = "INSERT INTO User_Quiz_Results (UserID, QuizID, score, completed_at, status) VALUES (?, ?, ?, ?, ?)";
//            PreparedStatement statement = connection.prepareStatement(sql);
//            statement.setInt(1, result.getUserID());
//            statement.setInt(2, result.getQuizID());
//            statement.setDouble(3, result.getScore());
//            statement.setDate(4, result.getCompletedAt());
//            statement.setBoolean(5, result.isStatus());
//            statement.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    public int addUserQuizResult(UserQuizResult result) {
        int resultId = -1;
        String sql = "INSERT INTO User_Quiz_Results (UserID, QuizID, score, completed_at, status) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, result.getUserID());
            ps.setInt(2, result.getQuizID());
            ps.setDouble(3, result.getScore());
            ps.setDate(4, result.getCompletedAt());
            ps.setBoolean(5, result.isStatus());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                resultId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultId;
    }

    public int addUserQuizResultAndReturnId(UserQuizResult result) {
        String sql = "INSERT INTO User_Quiz_Results (UserID, QuizID, score, completed_at, status) VALUES (?, ?, ?, ?, ?)";
        int resultId = -1;

        try (PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, result.getUserID());
            stmt.setInt(2, result.getQuizID());
            stmt.setDouble(3, result.getScore());
            stmt.setDate(4, result.getCompletedAt());
            stmt.setBoolean(5, result.isStatus());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        resultId = generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultId;
    }
// Add this method to update choices with the resultId

    public void updateChoicesWithResultId(int userId, int quizId, int resultId) {
        String sql = "UPDATE UserQuizChoices SET resultID = ? WHERE userId = ? AND quizId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, resultId);
            ps.setInt(2, userId);
            ps.setInt(3, quizId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<UserQuizResult> getUserQuizResultsByUserId(int userID) {
        List<UserQuizResult> results = new ArrayList<>();
        try {
            String sql = "SELECT * FROM User_Quiz_Results WHERE UserID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UserQuizResult result = new UserQuizResult();
                result.setResultID(resultSet.getInt("ResultID"));
                result.setUserID(resultSet.getInt("UserID"));
                result.setQuizID(resultSet.getInt("QuizID"));
                result.setScore(resultSet.getDouble("score"));
                result.setCompletedAt(resultSet.getDate("completed_at"));
                result.setStatus(resultSet.getBoolean("status"));
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }

    public List<UserQuizResult> getResultsByUserIdAndQuizId(int userID, int quizId) {
        List<UserQuizResult> results = new ArrayList<>();
        try {
            String sql = "SELECT * FROM User_Quiz_Results WHERE UserID = ? AND QuizID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            statement.setInt(2, quizId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UserQuizResult result = new UserQuizResult();
                result.setResultID(resultSet.getInt("ResultID"));
                result.setUserID(resultSet.getInt("UserID"));
                result.setQuizID(resultSet.getInt("QuizID"));
                result.setScore(resultSet.getDouble("score"));
                result.setCompletedAt(resultSet.getDate("completed_at"));
                result.setStatus(resultSet.getBoolean("status"));
                results.add(result);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately (logging, throwing custom exceptions, etc.)
        }
        return results;
    }

    public UserQuizResult getResultsByUserIdAndQuizIdLatest(int userID, int quizId) {
        try {
            String sql = "SELECT TOP 1 ur.* FROM User_Quiz_Results ur WHERE ur.UserID = ? AND ur.QuizID = ? ORDER BY ur.completed_at DESC";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            statement.setInt(2, quizId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UserQuizResult result = new UserQuizResult();
                result.setResultID(resultSet.getInt("ResultID"));
                result.setUserID(resultSet.getInt("UserID"));
                result.setQuizID(resultSet.getInt("QuizID"));
                result.setScore(resultSet.getDouble("score"));
                result.setCompletedAt(resultSet.getDate("completed_at"));
                result.setStatus(resultSet.getBoolean("status"));
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserQuizResult getResultsByUserAndQuiz(int userID, int quizId) {
        try {
            String sql = "SELECT ur.* FROM User_Quiz_Results ur WHERE ur.UserID = ? AND ur.QuizID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            statement.setInt(2, quizId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UserQuizResult result = new UserQuizResult();
                result.setResultID(resultSet.getInt("ResultID"));
                result.setUserID(resultSet.getInt("UserID"));
                result.setQuizID(resultSet.getInt("QuizID"));
                result.setScore(resultSet.getDouble("score"));
                result.setCompletedAt(resultSet.getDate("completed_at"));
                result.setStatus(resultSet.getBoolean("status"));
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserQuizResult getUserQuizResult(int resultId, int userID, int quizId) {
        try {
            String sql = "SELECT * FROM User_Quiz_Results ur \n"
                    + "WHERE ur.ResultID = ? AND ur.UserID = ? AND ur.QuizID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, resultId);
            statement.setInt(2, userID);
            statement.setInt(3, quizId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                UserQuizResult result = new UserQuizResult();
                result.setResultID(resultSet.getInt("ResultID"));
                result.setUserID(resultSet.getInt("UserID"));
                result.setQuizID(resultSet.getInt("QuizID"));
                result.setScore(resultSet.getDouble("score"));
                result.setCompletedAt(resultSet.getDate("completed_at"));
                result.setStatus(resultSet.getBoolean("status"));
                return result;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getTopScore(int userId, int quizId) {
        String sql = "SELECT MAX(score) AS max_score FROM User_Quiz_Results WHERE UserID = ? AND QuizID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setInt(2, quizId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt("max_score");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    // Get the highest score for a user in a specific subject
//    public double getHighestScore(int userId, int subjectId) {
//        String query = "SELECT MAX(score) FROM UserQuizResult ur "
//                + "JOIN Quiz q ON ur.quizId = q.id "
//                + "WHERE ur.userId = ? AND q.subjectId = ?";
//        try ( PreparedStatement ps = connection.prepareStatement(query)) {
//            ps.setInt(1, userId);
//            ps.setInt(2, subjectId);
//            ResultSet rs = ps.executeQuery();
//            if (rs.next()) {
//                return rs.getDouble(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return 0;
//    }
    public double getHighestScore(int userId, int quizId) {
        String query = "SELECT MAX(score) FROM User_Quiz_Results ur \n"
                + "JOIN Quizzes q ON ur.QuizID = q.QuizID\n"
                + "WHERE ur.userId = ? AND q.QuizID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, quizId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public UserQuizResult getLastResult(int quizId) {
        try {
            String sql = "SELECT TOP 1 ur.*, q.title, q.description, q.Level, q.created_by, q.created_at, q.duration\n"
                    + "FROM User_Quiz_Results ur\n"
                    + "JOIN Quizzes q ON q.QuizID = ur.QuizID\n"
                    + "WHERE ur.QuizID = ?\n"
                    + "ORDER BY ur.completed_at DESC;";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, quizId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new UserQuizResult(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getDate(5),
                        rs.getBoolean(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getInt(9),
                        rs.getInt(10),
                        rs.getDate(11),
                        rs.getInt(12));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public UserQuizResult getQuizResult(int resultId) {
        try {
            String sql = "SELECT* FROM User_Quiz_Results ur WHERE ur.ResultID = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, resultId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new UserQuizResult(rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDouble(4),
                        rs.getDate(5),
                        rs.getBoolean(6));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String args[]) {
        // TODO code application logic here
        UserQuizResultDAO udao = new UserQuizResultDAO();
//        UserQuizResult u = udao.getUserQuizResult(17, 2, 1);
        UserQuizResult u = udao.getResultsByUserAndQuiz(1, 1);
        System.out.println(u);
//        List<UserQuizResult> listu = udao.getResultsByUserIdAndQuizId(1, 1);
//        for (UserQuizResult userQuizResult : listu) {
//            System.out.println(userQuizResult);
//        }
//        UserQuizResult user = new UserQuizResult();
//        user.setQuizID(1);
//        user.setUserID(1);
//        user.setScore(100);
//        user.setCompletedAt(Date.valueOf("2024-06-15"));
//        user.setStatus(true);
//        udao.addUserQuizResult(user);
    }
}
