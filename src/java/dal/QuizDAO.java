/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import model.Question;
import model.Quiz;
import model.QuizHistoryVM;

/**
 *
 * @author ADMIN
 */
public class QuizDAO extends DBContext {

    private Connection con;
    PreparedStatement ps;
    ResultSet rs;

    public int getTotalList() {
        String sql = "SELECT COUNT(*) FROM [Quizzes]";
        try {
            ps = connection.prepareStatement(sql);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Quiz> getListQuizPage(int index) {
        String sql = "SELECT * FROM [Quizzes] q ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
        List<Quiz> listQuiz = new ArrayList();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, (index - 1) * 6);
            rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                byte[] imgData = rs.getBytes("Image");
                String base64Image = Base64.getEncoder().encodeToString(imgData);
                quiz.setImage((base64Image));
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setLevel(rs.getInt("Level"));
                quiz.setCategoryID(rs.getInt("CategoryID"));
                quiz.setSubjectID(rs.getInt("SubjectID"));

                quiz.setCreateAt(rs.getDate("created_at").toString());
                listQuiz.add(quiz);
            }
            return listQuiz;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Quiz> searchQuiz(int index, String search, int categoryId) {
        try {
            List<Quiz> listQuiz = new ArrayList();
            String sql = "";
            if (!(search.equals("")) && categoryId != 0) {
                sql = "SELECT * FROM [Quizzes] WHERE title LIKE ? AND CategoryID = ?  ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
                ps = connection.prepareStatement(sql);
                ps.setString(1, "%" + search + "%");      
                ps.setInt(2, categoryId);
                ps.setInt(2, (index - 1) * 6);
            } else if (search.equals("")) {
                sql = "SELECT * FROM [Quizzes] WHERE title LIKE ? ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
                ps = connection.prepareStatement(sql);
                ps.setString(1, "%" + search + "%");
                ps.setInt(2, (index - 1) * 6);
            }else  if (categoryId != 0) {
                sql = "SELECT * FROM [Quizzes] WHERE CategoryID = ? ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, categoryId);
                ps.setInt(2, (index - 1) * 6);
            } else {
                 sql = "SELECT * FROM [Quizzes] ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
                ps = connection.prepareStatement(sql);
                ps.setInt(1, (index - 1) * 6);
            }
            rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setLevel(rs.getInt("Level"));
                quiz.setCategoryID(rs.getInt("CategoryID"));
                quiz.setSubjectID(rs.getInt("SubjectID"));
                byte[] imgData = rs.getBytes("Image");
                String base64Image = Base64.getEncoder().encodeToString(imgData);
                quiz.setImage((base64Image));
                quiz.setCreateAt(rs.getDate("created_at").toString());
                listQuiz.add(quiz);
            }
            return listQuiz;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Quiz> filterQuiz(int index, int filter) {
        String sql = "SELECT * FROM [Quizzes] WHERE Level = ? ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
        List<Quiz> listQuiz = new ArrayList();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, filter);
            ps.setInt(2, (index - 1) * 6);

            rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setLevel(rs.getInt("Level"));
                quiz.setCategoryID(rs.getInt("CategoryID"));
                quiz.setSubjectID(rs.getInt("SubjectID"));
                byte[] imgData = rs.getBytes("Image");
                String base64Image = Base64.getEncoder().encodeToString(imgData);
                quiz.setImage((base64Image));
                quiz.setCreateAt(rs.getDate("created_at").toString());
                listQuiz.add(quiz);
            }
            return listQuiz;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getTotalfilterQuizByCategory(int cateId) {
        String sql = "SELECT * FROM [Quizzes] WHERE CategoryID = ? ";
        List<Quiz> listQuiz = new ArrayList();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cateId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Quiz> filterQuizByCategory(int index, int cateId) {
        String sql = "SELECT * FROM [Quizzes] WHERE CategoryID = ? ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
        List<Quiz> listQuiz = new ArrayList();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, cateId);
            ps.setInt(2, (index - 1) * 6);

            rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setLevel(rs.getInt("Level"));
                quiz.setCategoryID(rs.getInt("CategoryID"));
                quiz.setSubjectID(rs.getInt("SubjectID"));
                byte[] imgData = rs.getBytes("Image");
                String base64Image = Base64.getEncoder().encodeToString(imgData);
                quiz.setImage((base64Image));
                quiz.setCreateAt(rs.getDate("created_at").toString());
                listQuiz.add(quiz);
            }
            return listQuiz;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getTotalListFilter(int level) {
        String sql = "SELECT COUNT(*) FROM [Quizzes] WHERE Level = ? ";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, level);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int getTotalListSearch(String search) {
        String sql = "SELECT COUNT(*) FROM [Quizzes]  WHERE title LIKE ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, "%" + search + "%");
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Quiz getQuizById(int Id) {
        String sql = "SELECT * FROM [Quizzes] WHERE QuizID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, Id);
            rs = ps.executeQuery();
            if (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setLevel(rs.getInt("Level"));
                quiz.setCategoryID(rs.getInt("CategoryID"));
                quiz.setSubjectID(rs.getInt("SubjectID"));
                byte[] imgData = rs.getBytes("Image");
                String base64Image = Base64.getEncoder().encodeToString(imgData);
                quiz.setImage((base64Image));
                quiz.setCreateAt(rs.getDate("created_at").toString());
                return quiz;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean addNewQuiz(Quiz quiz, Part image) throws IOException {
        String sql = "INSERT INTO Quizzes (title, Image,  description, Level, SubjectID, CategoryID, created_by, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, quiz.getTitle());
            InputStream fileContent = image.getInputStream();
            ps.setBinaryStream(2, fileContent, (int) image.getSize());
            ps.setString(3, quiz.getDescription());
            ps.setInt(4, quiz.getLevel());
            ps.setInt(5, quiz.getSubjectID());
            ps.setInt(6, quiz.getCategoryID());

            ps.setInt(7, quiz.getCreateById());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setObject(8, currentDate);
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateQuiz(Quiz quiz, Part image) throws IOException {
        String sql = "UPDATE  Quizzes SET  title = ? , Image = ?, description = ?, Level = ?, SubjectID = ?, CategoryID = ?, "
                + "created_by = ?, created_at = ? WHERE QuizID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, quiz.getTitle());
            InputStream fileContent = image.getInputStream();
            ps.setBinaryStream(2, fileContent, (int) image.getSize());
            ps.setString(3, quiz.getDescription());
            ps.setInt(4, quiz.getLevel());
            ps.setInt(5, quiz.getSubjectID());
            ps.setInt(6, quiz.getCategoryID());

            ps.setInt(7, quiz.getCreateById());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setObject(8, currentDate);
            ps.setObject(9, quiz.getQuizID());

            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<QuizHistoryVM> GetQuizHistory(int userId) {
        String sql = "SELECT * FROM [Quizzes] q JOIN [User_Quiz_Results] hq ON q.QuizID = hq.QuizID WHERE hq.UserID = ?";
        try {
            List<QuizHistoryVM> listHistory = new ArrayList();
            CategoryDAO cateDAO = new CategoryDAO();
            SubjectDAO subjectDAO = new SubjectDAO();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            while (rs.next()) {
                QuizHistoryVM hq = new QuizHistoryVM();
                hq.setQuizID(rs.getInt("QuizID"));
                hq.setTitle(rs.getString("title"));
                hq.setDescription(rs.getString("description"));
                hq.setLevel(rs.getInt("Level"));
                String cateName = cateDAO.GetById(rs.getInt("CategoryID"));
                String subjectName = subjectDAO.GetById(rs.getInt("SubjectID"));
                hq.setCategory(cateName);
                hq.setSubject(subjectName);
                byte[] imgData = rs.getBytes("Image");
                String base64Image = Base64.getEncoder().encodeToString(imgData);
                hq.setImage((base64Image));
                hq.setCreateAt(rs.getDate("created_at").toString());
                hq.setCompleteAt(rs.getDate("completed_at").toString());
                hq.setScore(rs.getInt("score"));
                listHistory.add(hq);
            }
            return listHistory;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int getTotalQuizBySubjectId(int SubjectId) {
        String sql = "SELECT COUNT(*) FROM [Quizzes] q WHERE SubjectID = ?";
        List<Quiz> listQuiz = new ArrayList();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, SubjectId);

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Quiz> getListQuizBySubjectId(int SubjectId, int index) {
        String sql = "SELECT * FROM [Quizzes] q WHERE SubjectID = ? ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 9 ROWS ONLY";
        List<Quiz> listQuiz = new ArrayList();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, SubjectId);
            ps.setInt(2, (index - 1) * 9);

            rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                byte[] imgData = rs.getBytes("Image");
                String base64Image = Base64.getEncoder().encodeToString(imgData);
                quiz.setImage((base64Image));
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setLevel(rs.getInt("Level"));
                quiz.setCategoryID(rs.getInt("CategoryID"));
                quiz.setSubjectID(rs.getInt("SubjectID"));

                quiz.setCreateAt(rs.getDate("created_at").toString());
                listQuiz.add(quiz);
            }
            return listQuiz;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Quiz> getListQuizHomePageBySubjectId(int SubjectId) {
        String sql = "SELECT * FROM [Quizzes] q WHERE SubjectID = ? ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";
        List<Quiz> listQuiz = new ArrayList();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, SubjectId);
            ps.setInt(2, 0);

            rs = ps.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                byte[] imgData = rs.getBytes("Image");
                String base64Image = Base64.getEncoder().encodeToString(imgData);
                quiz.setImage((base64Image));
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setTitle(rs.getString("title"));
                quiz.setDescription(rs.getString("description"));
                quiz.setLevel(rs.getInt("Level"));
                quiz.setCategoryID(rs.getInt("CategoryID"));
                quiz.setSubjectID(rs.getInt("SubjectID"));

                quiz.setCreateAt(rs.getDate("created_at").toString());
                listQuiz.add(quiz);
            }
            return listQuiz;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Question> GetListQuestionByQuizId(int quizId) {
//         String sql = "SELECT * FROM [Question] q WHERE QuizID = ? ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";     
        String sql = "SELECT * FROM [Questions] WHERE QuizID = ? ";

        List<Question> listQuestion = new ArrayList();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, quizId);

            rs = ps.executeQuery();
            while (rs.next()) {
                Question question = new Question();
                question.setQuestionId(rs.getInt("QuestionID"));
                question.setQuestionDetail(rs.getString("QuestionDetail"));
                question.setQuizId(rs.getInt("QuizID"));
                listQuestion.add(question);
            }
            return listQuestion;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
