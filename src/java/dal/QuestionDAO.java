/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import model.QuizQuestion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Answer;
import model.AnswerQuestion;
import model.Question;
import model.QuestionQuiz;
import model.QuizDetailVM;
import model.User_Answer;
import model.User_Quiz_Result;

/**
 *
 * @author minh
 */
public class QuestionDAO extends DBContext {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

    public List<QuizQuestion> getAllQuestion() {
        List<QuizQuestion> list = new ArrayList<>();
        String query = "select * from Questions";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new QuizQuestion(rs.getInt(1), rs.getString(2), rs.getInt(3)));

            }
        } catch (Exception e) {
        }

        return list;
    }

    public void insertQuestion(String qdetail, String quizid) {
        String query = "INSERT INTO [dbo].[Questions]\n"
                + "           \n"
                + "     VALUES\n"
                + "           (?,?)";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, qdetail);
            ps.setString(2, quizid);
            ps.executeUpdate();
            System.out.println("add");
        } catch (Exception e) {
        }
    }

    public void deleteQuestion(String id) {
        String query = "DELETE FROM Questions where QuestionID = ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, id);
            ps.executeUpdate();
            System.out.println("delete");
        } catch (Exception e) {
        }
    }

    public int getTotalQuestion() {
        String query = "select count (*) from Questions";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }

        return 0;
    }

    public List<QuizQuestion> pagingQuestion(int index) {
        List<QuizQuestion> list = new ArrayList<>();
        String query = "select * from Questions \n"
                + "order by QuestionID\n"
                + "OFFSET ? Rows fetch next 10 rows only ;";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, (index - 1) * 10);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new QuizQuestion(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<QuizQuestion> searchByQuestionDetail(String txtSearch) {
        List<QuizQuestion> list = new ArrayList<>();
        String query = "select * from Questions\n"
                + "where QuestionDetail like ?";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, "%" + txtSearch + "%");
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new QuizQuestion(rs.getInt(1), rs.getString(2), rs.getInt(3)));
            }

        } catch (Exception e) {
        }

        return list;
    }

    public boolean insertQuestion(String qdetail, int quizid) {
        String query = "INSERT INTO [dbo].[Questions] (QuestionDetail, QuizID, Type)"
                + "     VALUES (?,?, ?)";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1, qdetail);
            ps.setInt(2, quizid);
            ps.setInt(3, 1);

            int affectedRow = ps.executeUpdate();
            return affectedRow > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getIdQuestionWasAdded() {
        try {
            String sql = "SELECT * FROM Questions ORDER BY QuestionID DESC";
            int check = 0;
            conn = new DBContext().connection;
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public QuizDetailVM getAllQuestionById(int quizId) {
        String query = "select * from Questions WHERE QuizID = ? AND Type = 1 ";
        QuizDetailVM quizDetailVM = new QuizDetailVM();
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, quizId);
            rs = ps.executeQuery();
            List<QuestionQuiz> listQuestion = new ArrayList<>();
            while (rs.next()) {
                QuestionQuiz q = new QuestionQuiz();
                q.setQuestionDetail(rs.getString("QuestionDetail"));
                q.setQuestionId(rs.getInt("QuestionID"));
                q.setQuizId(quizId);
                listQuestion.add(q);
            }
            quizDetailVM.setQuizId(quizId);
            quizDetailVM.setListQuestion(listQuestion);
            if (listQuestion != null) {
                for (QuestionQuiz question : listQuestion) {
                    query = "select * from AnswerQuestion WHERE QuestionID = ?";
                    conn = new DBContext().connection;
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, question.getQuestionId());
                    rs = ps.executeQuery();
                    List<AnswerQuestion> listAnswer = new ArrayList<>();
                    while (rs.next()) {
                        AnswerQuestion a = new AnswerQuestion();
                        a.setAnswerID(rs.getInt("AnswerID"));
                        a.setAnswerContent(rs.getString("AnswerContent"));
                        a.setIsCorrect(rs.getBoolean("IsCorrect"));
                        a.setQuestionID(rs.getInt("QuestionId"));
                        listAnswer.add(a);
                    }
                    question.setListAnswer(listAnswer);
                }
            }
            return quizDetailVM;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizDetailVM;
    }

    public QuizDetailVM getAllQuestionsBYID(int quizId) {
        String query = "select * from Questions WHERE QuizID = ? ";
        QuizDetailVM quizDetailVM = new QuizDetailVM();
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setInt(1, quizId);
            rs = ps.executeQuery();
            List<QuestionQuiz> listQuestion = new ArrayList<>();
            while (rs.next()) {
                QuestionQuiz q = new QuestionQuiz();
                q.setQuestionDetail(rs.getString("QuestionDetail"));
                q.setQuestionId(rs.getInt("QuestionID"));
                q.setQuizId(quizId);
                listQuestion.add(q);
            }
            quizDetailVM.setQuizId(quizId);
            quizDetailVM.setListQuestion(listQuestion);
            if (listQuestion != null) {
                for (QuestionQuiz question : listQuestion) {
                    query = "select * from AnswerQuestion WHERE QuestionID = ?";
                    conn = new DBContext().connection;
                    ps = conn.prepareStatement(query);
                    ps.setInt(1, question.getQuestionId());
                    rs = ps.executeQuery();
                    List<AnswerQuestion> listAnswer = new ArrayList<>();
                    while (rs.next()) {
                        AnswerQuestion a = new AnswerQuestion();
                        a.setAnswerID(rs.getInt("AnswerID"));
                        a.setAnswerContent(rs.getString("AnswerContent"));
                        a.setIsCorrect(rs.getBoolean("IsCorrect"));
                        a.setQuestionID(rs.getInt("QuestionId"));
                        listAnswer.add(a);
                    }
                    question.setListAnswer(listAnswer);
                }
            }
            return quizDetailVM;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return quizDetailVM;
    }

    public boolean storeAnswerHistory(User_Answer answer) {
        try {
            String sql = "INSERT INTO User_Answers (UserID, QuestionID, AnswerID, answered_at) VALUES (?, ?, ?, ?)";
            conn = new DBContext().connection;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, answer.getUserID());
            ps.setInt(2, answer.getQuestionID());
            ps.setInt(3, answer.getAnswerID());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setString(4, currentDate);

            int affectedRow = ps.executeUpdate();
            if (rs.next()) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public User_Answer getUserAnswerHistory(User_Answer answer) {
        try {

            String sql = "SELECT * FROM User_Answers WHERE QuestionID = ? AND UserID = ?";
            conn = new DBContext().connection;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, answer.getQuestionID());
            ps.setInt(2, answer.getUserID());
            rs = ps.executeQuery();
            if (rs.next()) {
                User_Answer aw = new User_Answer();
                aw.setAnswerID(rs.getInt("AnswerID"));
                aw.setQuestionID(rs.getInt("QuestionID"));
                aw.setUserAnswerID(rs.getInt("UserID"));
                return aw;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean storeAnswerHistory(User_Quiz_Result result) {
        try {
            String sql = "INSERT INTO User_Quiz_Results (UserID, QuizID, score, completed_at) VALUES (?, ?, ?, ?)";
            conn = new DBContext().connection;
            ps = conn.prepareStatement(sql);
            ps.setInt(1, result.getUserID());
            ps.setInt(2, result.getQuizID());
            ps.setFloat(3, result.getScore());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setString(4, currentDate);

            int affectedRow = ps.executeUpdate();
            if (rs.next()) {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /////////////////////// Chinh /////////////////////////////////////////////
    public List<Question> getQuestionsByQuizId(int quizID) {
        List<Question> questions = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Questions WHERE QuizID = ? AND Type = 2";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, quizID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Question question = new Question();
                question.setQuestionID(resultSet.getInt("QuestionID"));
                question.setQuestionDetail(resultSet.getString("QuestionDetail"));
                question.setQuizID(resultSet.getInt("QuizID"));

                // Get the answers for this question
                question.setAnswers(getAnswersByQuestionId(question.getQuestionID()));
                questions.add(question);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public List<Question> getQuestionsByQuizId(String quizId) {
        List<Question> questions = new ArrayList<>();

        try {
            String query = "SELECT q.questionID, q.questionDetail, a.answerID, a.answerDetail "
                    + "FROM Questions q "
                    + "LEFT JOIN Answers a ON q.questionID = a.questionID "
                    + "WHERE q.quizID = ?";
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setString(1, quizId);
            ResultSet rs = pstmt.executeQuery();

            Question currentQuestion = null;
            while (rs.next()) {
                int questionId = rs.getInt("questionID");
                if (currentQuestion == null || currentQuestion.getQuestionID() != questionId) {
                    currentQuestion = new Question();
                    currentQuestion.setQuestionID(questionId);
                    currentQuestion.setQuestionDetail(rs.getString("questionDetail"));
                    currentQuestion.setAnswers(new ArrayList<>());
                    questions.add(currentQuestion);
                }

                Answer answer = new Answer();
                answer.setAnswerID(rs.getInt("answerID"));
                answer.setAnswerDetail(rs.getString("answerDetail"));
                currentQuestion.getAnswers().add(answer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return questions;
    }

    private List<Answer> getAnswersByQuestionId(int questionID) {
        List<Answer> answers = new ArrayList<>();
        String sql = "SELECT * FROM Answers WHERE QuestionID = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, questionID);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Answer answer = new Answer();
                answer.setAnswerID(resultSet.getInt("AnswerID"));
                answer.setAnswerDetail(resultSet.getString("AnswerDetail"));
                answer.setIsAnswer(resultSet.getInt("IsAnswer"));
                answer.setOption1(resultSet.getString("option1"));
                answer.setOption2(resultSet.getString("option2"));
                answer.setOption3(resultSet.getString("option3"));
                answer.setOption4(resultSet.getString("option4"));

                answers.add(answer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return answers;
    }

    public Map<Integer, Integer> getCorrectAnswersByQuizId(int quizId) {
        Map<Integer, Integer> correctAnswersMap = new HashMap<>();
        try {
            // Assuming 'connection' is your established database connection
            String sql = "SELECT QuestionID, IsAnswer FROM Answers WHERE QuestionID IN (SELECT QuestionID FROM Questions WHERE QuizID = ?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, quizId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int questionID = rs.getInt("QuestionID");
                int correctAnswerID = rs.getInt("IsAnswer");
                correctAnswersMap.put(questionID, correctAnswerID);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return correctAnswersMap;
    }

    public static void main(String[] args) {
        QuestionDAO q = new QuestionDAO();
        q.insertQuestion("hihihih", "5");
    }

}
