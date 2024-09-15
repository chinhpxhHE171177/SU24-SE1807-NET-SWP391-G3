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
        String sql = "SELECT * FROM [Quizzes] q Where q.type = 1 ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
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
                quiz.setStatus(rs.getBoolean("Status"));

                listQuiz.add(quiz);
            }
            return listQuiz;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Quiz> getListExamPage(int index) {
        String sql = "SELECT * FROM [Quizzes] q Where q.type = 2 ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
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
                quiz.setStatus(rs.getBoolean("Status"));
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
            } else if (categoryId != 0) {
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

    public Quiz getQuizByID(int id) {
        String sql = "SELECT * FROM Quizzes WHERE QuizID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setTitle(rs.getString("title"));
                quiz.setImage(rs.getString("Image"));
                quiz.setDescription(rs.getString("description"));
                quiz.setLevel(rs.getInt("Level"));
                quiz.setSubjectID(rs.getInt("SubjectID"));
                quiz.setCategoryID(rs.getInt("CategoryID"));
                quiz.setCreateById(rs.getInt("created_by"));
                quiz.setCreatedAt(rs.getDate("created_at"));
                quiz.setDuration(rs.getInt("duration"));
                return quiz;
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return null;
    }

    public boolean addNewQuiz(Quiz quiz, Part image) throws IOException {
        String sql = "INSERT INTO Quizzes (title, Image,  description, Level, SubjectID, CategoryID, created_by, created_at, type, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
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
            ps.setInt(9, 1);
            ps.setBoolean(10, false);
            int affectedRow = ps.executeUpdate();
            if (affectedRow > 0) {
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean addNewExam(Quiz quiz, Part image) throws IOException {
        String sql = "INSERT INTO Quizzes (title, Image,  description, SubjectID, CategoryID, created_by, created_at, type, Status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, quiz.getTitle());
            InputStream fileContent = image.getInputStream();
            ps.setBinaryStream(2, fileContent, (int) image.getSize());
            ps.setString(3, quiz.getDescription());
            ps.setInt(4, quiz.getSubjectID());
            ps.setInt(5, quiz.getCategoryID());

            ps.setInt(6, quiz.getCreateById());
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = new Date();
            String currentDate = dateFormat.format(date);
            ps.setObject(7, currentDate);
            ps.setInt(8, 2);
            ps.setBoolean(9, false);
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

    public boolean updateExam(Quiz quiz, Part image) throws IOException {
        String sql = "UPDATE  Quizzes SET  title = ? , Image = ?, description = ?, Level = ?, SubjectID = ?, CategoryID = ?, "
                + "created_by = ?, created_at = ? WHERE QuizID = ?";
        try {
            ps = connection.prepareStatement(sql);
            ps.setString(1, quiz.getTitle());
            InputStream fileContent = image.getInputStream();
            ps.setBinaryStream(2, fileContent, (int) image.getSize());
            ps.setString(3, quiz.getDescription());
            ps.setInt(4, 1);
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

    public boolean deleteExam(int quizId) {
        boolean result = false;
        String sql = "DELETE FROM [Quizzes] WHERE QuizID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, quizId);
            int rowsAffected = ps.executeUpdate();
            result = rowsAffected > 0;
            System.out.println("Rows affected: " + rowsAffected); // Debug line
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
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
        String sql = "SELECT * FROM [Quizzes] q WHERE SubjectID = ? AND Type = 1 ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 9 ROWS ONLY";
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
        String sql = "SELECT * FROM [Quizzes] q WHERE SubjectID = ? AND q.type = 1 AND q.Status = 1 ORDER BY QuizID DESC OFFSET ? ROWS FETCH NEXT 5 ROWS ONLY";
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

    public Quiz getSubjectQuiz(int sid) {
        try {
            String sql = "SELECT * FROM Quizzes WHERE SubjectID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, sid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(rs.getInt("QuizID"));
                quiz.setTitle(rs.getString("title"));
                quiz.setImage(rs.getString("Image"));
                quiz.setDescription(rs.getString("description"));
                quiz.setLevel(rs.getInt("Level"));
                quiz.setSubjectID(rs.getInt("SubjectID"));
                quiz.setCategoryID(rs.getInt("CategoryID"));
                quiz.setCreateById(rs.getInt("created_by"));
                quiz.setCreatedAt(rs.getDate("created_at"));
                quiz.setDuration(rs.getInt("duration"));
                return quiz;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public List<Quiz> getListQuizByUserId(int userId) {
        String sql = "SELECT * FROM [Quizzes] q WHERE created_by = ?";
        List<Quiz> listQuiz = new ArrayList();
        try {
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);

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

    public int GetQuizHistoryTotal(int userId) {
        String sql = "SELECT COUNT(*) FROM [Quizzes] q JOIN [User_Quiz_Results] hq ON q.QuizID = hq.QuizID WHERE hq.UserID = ?";
        try {
            List<QuizHistoryVM> listHistory = new ArrayList();
            CategoryDAO cateDAO = new CategoryDAO();
            SubjectDAO subjectDAO = new SubjectDAO();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<QuizHistoryVM> GetQuizHistory(int userId, int index) {
        String sql = "SELECT * FROM [Quizzes] q JOIN [User_Quiz_Results] hq ON q.QuizID = hq.QuizID WHERE hq.UserID = ? ORDER BY hq.completed_at DESC "
                + "OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
        try {
            List<QuizHistoryVM> listHistory = new ArrayList();
            CategoryDAO cateDAO = new CategoryDAO();
            SubjectDAO subjectDAO = new SubjectDAO();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setInt(2, (index - 1) * 6);
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
                hq.setCreateAt(rs.getString("created_at").toString());
                hq.setCompleteAt(rs.getString("completed_at").toString());
                hq.setScore(rs.getInt("score"));
                listHistory.add(hq);
            }
            return listHistory;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public int GetQuizHistorySearchTotal(int userId, String search) {
        String sql = "SELECT COUNT(*) FROM [Quizzes] q JOIN [User_Quiz_Results] hq ON q.QuizID = hq.QuizID WHERE hq.UserID = ? AND q.title LIKE ? ";
        try {
            List<QuizHistoryVM> listHistory = new ArrayList();
            CategoryDAO cateDAO = new CategoryDAO();
            SubjectDAO subjectDAO = new SubjectDAO();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, "%" + search + "%");

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<QuizHistoryVM> GetQuizHistorySearch(int userId, int index, String search) {
        String sql = "SELECT * FROM [Quizzes] q JOIN [User_Quiz_Results] hq ON q.QuizID = hq.QuizID WHERE hq.UserID = ?  AND q.title LIKE ? ORDER BY hq.completed_at DESC "
                + "OFFSET ? ROWS FETCH NEXT 6 ROWS ONLY";
        try {
            List<QuizHistoryVM> listHistory = new ArrayList();
            CategoryDAO cateDAO = new CategoryDAO();
            SubjectDAO subjectDAO = new SubjectDAO();
            ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ps.setString(2, "%" + search + "%");
            ps.setInt(2, (index - 1) * 6);
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
                hq.setCreateAt(rs.getString("created_at").toString());
                hq.setCompleteAt(rs.getString("completed_at").toString());
                hq.setScore(rs.getInt("score"));
                listHistory.add(hq);
            }
            return listHistory;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    // -- Chinh --
    public List<Quiz> getQuizBySubjectID(int sid) {
        List<Quiz> quizzes = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Quizzes WHERE SubjectID = ? AND type = 2 AND Status = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, sid);
            ResultSet resultSet = pst.executeQuery();
            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setQuizID(resultSet.getInt("QuizID"));
                quiz.setTitle(resultSet.getString("title"));
                quiz.setImage(resultSet.getString("Image"));
                quiz.setDescription(resultSet.getString("description"));
                quiz.setLevel(resultSet.getInt("Level"));
                quiz.setSubjectID(resultSet.getInt("SubjectID"));
                quiz.setCategoryID(resultSet.getInt("CategoryID"));
                quiz.setCreateById(resultSet.getInt("created_by"));
                quiz.setCreatedAt(resultSet.getDate("created_at"));
                quiz.setDuration(resultSet.getInt("duration"));
                quizzes.add(quiz);
            }
        } catch (Exception e) {
        }
        return quizzes;
    }

    public boolean checkIfQuizAttempted(int userId, int quizId) {
        String sql = "SELECT * FROM User_Quiz_Results WHERE UserID = ? AND QuizID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, userId);
            pst.setInt(2, quizId);
            ResultSet rs = pst.executeQuery();
            return rs.next(); // Returns true if any results found
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle exceptions appropriately (logging, throwing custom exceptions, etc.)
        }
        return false; // Return false by default
    }

    public int totalQuestionByQuiz(int qid) {

        String sql = "SELECT COUNT(*) AS totalQuestion FROM Questions q\n"
                + "JOIN Quizzes qs ON qs.QuizID = q.QuizID\n"
                + "WHERE qs.QuizID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, qid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt("totalQuestion");
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return 0;
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
                question.setQuestionID(rs.getInt("QuestionID"));
                question.setQuestionDetail(rs.getString("QuestionDetail"));
                question.setQuizID(rs.getInt("QuizID"));
                question.setType(rs.getInt("Type"));

                listQuestion.add(question);
            }
            return listQuestion;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<Quiz> searchForName(String txtSearch) {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT q.*, m.Subject_Name, u.FullName FROM Quizzes q\n"
                    + "LEFT JOIN Subjects m ON m.SubjectID = q.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = q.created_by\n"
                    + "WHERE q.title LIKE ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + txtSearch + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz e = new Quiz(
                        rs.getInt("QuizID"),
                        rs.getString("title"),
                        rs.getString("Image"),
                        rs.getString("description"),
                        rs.getInt("Level"),
                        rs.getInt("CategoryID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("created_by"),
                        rs.getInt("duration"),
                        rs.getDate("created_at"),
                        rs.getInt("type"),
                        rs.getBoolean("Status"),
                        rs.getString("Subject_Name"),
                        rs.getString("FullName"));
                list.add(e);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Quiz> getAllQuiz() {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT q.QuizID, q.title, q.Image, q.description, q.Level, q.CategoryID, q.SubjectID, q.created_by, q.duration, q.created_at, q.type, q.Status, m.Subject_Name, u.FullName\n"
                    + "FROM Quizzes q\n"
                    + "LEFT JOIN Subjects m ON m.SubjectID = q.SubjectID \n"
                    + "LEFT JOIN Users u ON u.UserID = q.created_by\n"
                    + "WHERE q.type = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz e = new Quiz(
                        rs.getInt("QuizID"),
                        rs.getString("title"),
                        rs.getString("Image"),
                        rs.getString("description"),
                        rs.getInt("Level"),
                        rs.getInt("CategoryID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("created_by"),
                        rs.getInt("duration"),
                        rs.getDate("created_at"),
                        rs.getInt("type"),
                        rs.getBoolean("Status"),
                        rs.getString("Subject_Name"),
                        rs.getString("FullName")
                );
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
        return list;
    }

    public List<Quiz> getAllListQuiz() {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT q.QuizID, q.title, q.Image, q.description, q.Level, q.CategoryID, q.SubjectID, q.created_by, q.duration, q.created_at, q.type, q.Status, m.Subject_Name, u.FullName\n"
                    + "FROM Quizzes q\n"
                    + "LEFT JOIN Subjects m ON m.SubjectID = q.SubjectID \n"
                    + "LEFT JOIN Users u ON u.UserID = q.created_by\n"
                    + "WHERE q.type = 2";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz e = new Quiz(
                        rs.getInt("QuizID"),
                        rs.getString("title"),
                        rs.getString("Image"),
                        rs.getString("description"),
                        rs.getInt("Level"),
                        rs.getInt("CategoryID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("created_by"),
                        rs.getInt("duration"),
                        rs.getDate("created_at"),
                        rs.getInt("type"),
                        rs.getBoolean("Status"),
                        rs.getString("Subject_Name"),
                        rs.getString("FullName")
                );
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Print stack trace for debugging
        }
        return list;
    }

    public void updateNewStatus(int id, Boolean status) {
        String sql = "UPDATE Quizzes SET Quizzes.Status = ? WHERE QuizID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setBoolean(1, status);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Quiz> getQuizByStatus(int sid) {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT q.QuizID, q.title, q.description, q.Level, q.CategoryID, q.SubjectID, q.created_by, q.duration, q.created_at, q.type, q.Status, m.Subject_Name, u.FullName\n"
                    + "FROM Quizzes q\n"
                    + "LEFT JOIN Subjects m ON m.SubjectID = q.SubjectID \n"
                    + "LEFT JOIN Users u ON u.UserID = q.created_by\n"
                    + "WHERE q.type = 1 AND q.Status = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, sid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz e = new Quiz(
                        rs.getInt("QuizID"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("Level"),
                        rs.getInt("CategoryID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("created_by"),
                        rs.getInt("duration"),
                        rs.getDate("created_at"),
                        rs.getInt("type"),
                        rs.getBoolean("Status"),
                        rs.getString("Subject_Name"),
                        rs.getString("FullName")
                );
                list.add(e);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Quiz> getExamByStatus(int sid) {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT q.QuizID, q.title, q.description, q.Level, q.CategoryID, q.SubjectID, q.created_by, q.duration, q.created_at, q.type, q.Status, m.Subject_Name, u.FullName\n"
                    + "FROM Quizzes q\n"
                    + "LEFT JOIN Subjects m ON m.SubjectID = q.SubjectID \n"
                    + "LEFT JOIN Users u ON u.UserID = q.created_by\n"
                    + "WHERE q.type = 2 AND q.Status = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, sid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz e = new Quiz(
                        rs.getInt("QuizID"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("Level"),
                        rs.getInt("CategoryID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("created_by"),
                        rs.getInt("duration"),
                        rs.getDate("created_at"),
                        rs.getInt("type"),
                        rs.getBoolean("Status"),
                        rs.getString("Subject_Name"),
                        rs.getString("FullName")
                );
                list.add(e);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Quiz> getQuizBySubjectId(int sid) {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT q.QuizID, q.title, q.description, q.Level, q.CategoryID, q.SubjectID, q.created_by, q.duration, q.created_at, q.type, q.Status, m.Subject_Name, u.FullName\n"
                    + "FROM Quizzes q\n"
                    + "LEFT JOIN Subjects m ON m.SubjectID = q.SubjectID \n"
                    + "LEFT JOIN Users u ON u.UserID = q.created_by\n"
                    + "WHERE q.type = 1 AND q.SubjectID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, sid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz e = new Quiz(
                        rs.getInt("QuizID"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("Level"),
                        rs.getInt("CategoryID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("created_by"),
                        rs.getInt("duration"),
                        rs.getDate("created_at"),
                        rs.getInt("type"),
                        rs.getBoolean("Status"),
                        rs.getString("Subject_Name"),
                        rs.getString("FullName"));
                list.add(e);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Quiz> getExamByCoursetId(int sid) {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT q.QuizID, q.title, q.description, q.Level, q.CategoryID, q.SubjectID, q.created_by, q.duration, q.created_at, q.type, q.Status, m.Subject_Name, u.FullName\n"
                    + "FROM Quizzes q\n"
                    + "LEFT JOIN Subjects m ON m.SubjectID = q.SubjectID \n"
                    + "LEFT JOIN Users u ON u.UserID = q.created_by\n"
                    + "WHERE q.type = 2 AND q.SubjectID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, sid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz e = new Quiz(
                        rs.getInt("QuizID"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("Level"),
                        rs.getInt("CategoryID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("created_by"),
                        rs.getInt("duration"),
                        rs.getDate("created_at"),
                        rs.getInt("type"),
                        rs.getBoolean("Status"),
                        rs.getString("Subject_Name"),
                        rs.getString("FullName"));
                list.add(e);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Quiz> getQuizBySubjectAndStatus(int cid, int status) {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT q.QuizID, q.title, q.description, q.Level, q.CategoryID, q.SubjectID, q.created_by, q.duration, q.created_at, q.type, q.Status, m.Subject_Name, u.FullName\n"
                    + "FROM Quizzes q\n"
                    + "LEFT JOIN Subjects m ON m.SubjectID = q.SubjectID \n"
                    + "LEFT JOIN Users u ON u.UserID = q.created_by\n"
                    + "WHERE q.type = 1 AND q.Status = ? AND q.SubjectID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, cid);
            pst.setInt(2, status);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz e = new Quiz(
                        rs.getInt("QuizID"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("Level"),
                        rs.getInt("CategoryID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("created_by"),
                        rs.getInt("duration"),
                        rs.getDate("created_at"),
                        rs.getInt("type"),
                        rs.getBoolean("Status"),
                        rs.getString("Subject_Name"),
                        rs.getString("FullName"));
                list.add(e);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Quiz> getExamBySubjectAndStatus(int cid, int status) {
        List<Quiz> list = new ArrayList<>();
        try {
            String sql = "SELECT q.QuizID, q.title, q.description, q.Level, q.CategoryID, q.SubjectID, q.created_by, q.duration, q.created_at, q.type, q.Status, m.Subject_Name, u.FullName\n"
                    + "FROM Quizzes q\n"
                    + "LEFT JOIN Subjects m ON m.SubjectID = q.SubjectID \n"
                    + "LEFT JOIN Users u ON u.UserID = q.created_by\n"
                    + "WHERE q.type = 2 AND q.Status = ? AND q.SubjectID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, cid);
            pst.setInt(2, status);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Quiz e = new Quiz(
                        rs.getInt("QuizID"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getInt("Level"),
                        rs.getInt("CategoryID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("created_by"),
                        rs.getInt("duration"),
                        rs.getDate("created_at"),
                        rs.getInt("type"),
                        rs.getBoolean("Status"),
                        rs.getString("Subject_Name"),
                        rs.getString("FullName"));
                list.add(e);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static void main(String args[]) {
        QuizDAO sdao = new QuizDAO();
        List<Quiz> listq = sdao.getAllQuiz();
        for (Quiz quiz : listq) {
            System.out.println(quiz);
        }
//        Quiz quiz = sdao.getQuizByID(1);
//        System.out.println(quiz);
//        List<Quiz> q = new ArrayList<>();
//        q = (List<Quiz>) sdao.searchForName("u");
//        for (Quiz quiz : q) {
//            System.out.println(quiz);
//        }
    }
}
