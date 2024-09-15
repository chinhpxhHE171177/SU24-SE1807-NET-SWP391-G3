package dal;

import model.UserQuizChoices;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object for UserQuizChoices.
 */
public class UserQuizChoicesDAO extends DBContext {

    /**
     * Adds a user choice to the database.
     *
     * @param choice the UserQuizChoices object containing user choice data
     */
    public void addUserChoice(UserQuizChoices choice) {
        String sql = "INSERT INTO UserQuizChoices (userId, QuizID, questionId, selectedAnswerId, isCorrect, startTime, endTime) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, choice.getUserId());
            stmt.setInt(2, choice.getQuizId());
            stmt.setInt(3, choice.getQuestionId());
            stmt.setInt(4, choice.getSelectedAnswerId());
            stmt.setBoolean(5, choice.getIsCorrect());
            stmt.setTimestamp(6, choice.getStartTime());
            stmt.setTimestamp(7, choice.getEndTime());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertUserChoice(UserQuizChoices choice) {
        String sql = "INSERT INTO [dbo].[UserQuizChoices]\n"
                + "           ([userId]\n"
                + "           ,[QuizID]\n"
                + "           ,[questionId]\n"
                + "           ,[selectedAnswerId]\n"
                + "           ,[isCorrect]\n"
                + "           ,[startTime]\n"
                + "           ,[endTime]\n"
                + "           ,[resultID])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, choice.getUserId());
            stmt.setInt(2, choice.getQuizId());
            stmt.setInt(3, choice.getQuestionId());
            stmt.setInt(4, choice.getSelectedAnswerId());
            stmt.setBoolean(5, choice.getIsCorrect());
            stmt.setTimestamp(6, choice.getStartTime());
            stmt.setTimestamp(7, choice.getEndTime());
            stmt.setInt(8, choice.getResultId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Retrieves user choices for a specific user and quiz from the database.
     *
     * @param userId the ID of the user
     * @param quizId the ID of the quiz
     * @return a list of UserQuizChoices objects
     */
    public List<UserQuizChoices> getUserChoices(int userId, int quizId) {
        List<UserQuizChoices> choices = new ArrayList<>();
        String sql = "SELECT u.*, q.QuestionDetail, a.AnswerDetail, a.option1, a.option2, a.option3, a.option4 \n"
                + "FROM UserQuizChoices u\n"
                + "JOIN Questions q ON q.QuestionID = u.questionId\n"
                + "LEFT JOIN Answers a ON a.QuestionID = q.QuestionID\n"
                + "WHERE u.userId = ? AND u.QuizID = ? \n"
                + "AND CONVERT(VARCHAR, u.endTime, 120) = (\n"
                + "    SELECT MAX(CONVERT(VARCHAR, endTime, 120)) \n"
                + "    FROM UserQuizChoices \n"
                + "    WHERE userId = ? AND QuizID = ?\n"
                + ");";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, quizId);
            // Setting userId and quizId again for the subquery
            stmt.setInt(3, userId);
            stmt.setInt(4, quizId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UserQuizChoices choice = new UserQuizChoices();
                    choice.setUserId(rs.getInt("userId"));
                    choice.setQuizId(rs.getInt("QuizID"));
                    choice.setQuestionId(rs.getInt("questionId"));
                    choice.setSelectedAnswerId(rs.getInt("selectedAnswerId"));
                    choice.setIsCorrect(rs.getBoolean("isCorrect"));
                    choice.setStartTime(rs.getTimestamp("startTime"));
                    choice.setEndTime(rs.getTimestamp("endTime"));
                    choice.setQuestionDetail(rs.getString("questionDetail"));
                    choice.setAnswerDetail(rs.getString("answerDetail"));
                    choice.setOption1(rs.getString("option1"));
                    choice.setOption2(rs.getString("option2"));
                    choice.setOption3(rs.getString("option3"));
                    choice.setOption4(rs.getString("option4"));
                    choices.add(choice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return choices;
    }

    public List<UserQuizChoices> getUserHistoryChoices(int resultId) {
        List<UserQuizChoices> choices = new ArrayList<>();
        String sql = "SELECT u.*, q.QuestionDetail, a.AnswerDetail, a.option1, a.option2, a.option3, a.option4 \n"
                + "FROM UserQuizChoices u\n"
                + "JOIN Questions q ON q.QuestionID = u.questionId\n"
                + "LEFT JOIN Answers a ON a.QuestionID = q.QuestionID\n"
                + "WHERE u.resultID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, resultId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UserQuizChoices choice = new UserQuizChoices();
                    choice.setUserId(rs.getInt("userId"));
                    choice.setQuizId(rs.getInt("QuizID"));
                    choice.setQuestionId(rs.getInt("questionId"));
                    choice.setSelectedAnswerId(rs.getInt("selectedAnswerId"));
                    choice.setIsCorrect(rs.getBoolean("isCorrect"));
                    choice.setStartTime(rs.getTimestamp("startTime"));
                    choice.setEndTime(rs.getTimestamp("endTime"));
                    choice.setQuestionDetail(rs.getString("questionDetail"));
                    choice.setAnswerDetail(rs.getString("answerDetail"));
                    choice.setOption1(rs.getString("option1"));
                    choice.setOption2(rs.getString("option2"));
                    choice.setOption3(rs.getString("option3"));
                    choice.setOption4(rs.getString("option4"));
                    choices.add(choice);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return choices;
    }

    public UserQuizChoices getUserChoice(int userId, int quizId) {
        String sql = "SELECT u.*, q.QuestionDetail, a.AnswerDetail, a.option1, a.option2, a.option3, a.option4 \n"
                + "FROM UserQuizChoices u\n"
                + "JOIN Questions q ON q.QuestionID = u.questionId\n"
                + "LEFT JOIN Answers a ON a.QuestionID = q.QuestionID\n"
                + "WHERE u.userId = ? AND u.QuizID = ?\n"
                + "ORDER BY u.endTime DESC;";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, quizId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UserQuizChoices choice = new UserQuizChoices();
                    choice.setUserId(rs.getInt("userId"));
                    choice.setQuizId(rs.getInt("QuizID"));
                    choice.setQuestionId(rs.getInt("questionId"));
                    choice.setSelectedAnswerId(rs.getInt("selectedAnswerId"));
                    choice.setIsCorrect(rs.getBoolean("isCorrect"));
                    choice.setStartTime(rs.getTimestamp("startTime"));
                    choice.setEndTime(rs.getTimestamp("endTime"));
                    choice.setQuestionDetail(rs.getString("questionDetail"));
                    choice.setAnswerDetail(rs.getString("answerDetail"));
                    choice.setOption1(rs.getString("option1"));
                    choice.setOption2(rs.getString("option2"));
                    choice.setOption3(rs.getString("option3"));
                    choice.setOption4(rs.getString("option4"));
                    return choice;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public UserQuizChoices getUserHistoryChoice(int userId, int quizId) {
        String sql = "SELECT u.*, q.QuestionDetail, a.AnswerDetail, a.option1, a.option2, a.option3, a.option4 \n"
                + "FROM UserQuizChoices u\n"
                + "JOIN Questions q ON q.QuestionID = u.questionId\n"
                + "LEFT JOIN Answers a ON a.QuestionID = q.QuestionID\n"
                + "WHERE u.userId = ? AND u.QuizID = ?\n";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, quizId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    UserQuizChoices choice = new UserQuizChoices();
                    choice.setUserId(rs.getInt("userId"));
                    choice.setQuizId(rs.getInt("QuizID"));
                    choice.setQuestionId(rs.getInt("questionId"));
                    choice.setSelectedAnswerId(rs.getInt("selectedAnswerId"));
                    choice.setIsCorrect(rs.getBoolean("isCorrect"));
                    choice.setStartTime(rs.getTimestamp("startTime"));
                    choice.setEndTime(rs.getTimestamp("endTime"));
                    choice.setQuestionDetail(rs.getString("questionDetail"));
                    choice.setAnswerDetail(rs.getString("answerDetail"));
                    choice.setOption1(rs.getString("option1"));
                    choice.setOption2(rs.getString("option2"));
                    choice.setOption3(rs.getString("option3"));
                    choice.setOption4(rs.getString("option4"));
                    return choice;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        // Sample code to test the DAO methods
        UserQuizChoicesDAO dao = new UserQuizChoicesDAO();
        UserQuizChoices u = dao.getUserChoice(1, 1);
        System.out.println(u);
//
//        // Create a sample UserQuizChoices object
//        UserQuizChoices choice = new UserQuizChoices(1, 1, 1, 1, 3, true, new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()));
//
//        // Add the user choice to the database
//        dao.addUserChoice(choice);

        // Retrieve and print user choices
//        List<UserQuizChoices> choices = dao.getUserChoices(1, 1);
//        for (UserQuizChoices c : choices) {
//            System.out.println(c);
//        }
    }
}
