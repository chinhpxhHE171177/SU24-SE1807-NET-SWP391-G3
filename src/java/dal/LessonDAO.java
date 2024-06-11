package dal;

import java.sql.Date;
import java.util.List;
import model.Lessons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.LesMooc;
import model.Mooc;

/**
 *
 * @author Admin
 */
public class LessonDAO extends DBContext {

    /**
     * @param args the command line arguments
     */
    public List<Lessons> getAllLessons() {
        List<Lessons> list = new ArrayList<>();

        try {
            String sql = "SELECT l.*, s.Subject_Name, u.UserName FROM Lessons L\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = l.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Lessons lesson = new Lessons(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10));
                list.add(lesson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LesMooc> getAllLesMooc() {
        List<LesMooc> list = new ArrayList<>();
        try {
            String sql = "SELECT l.*, m.MoocName, s.Subject_Name, u.FullName, s.Image, c.category_name FROM LesMooc L\n"
                    + "LEFT JOIN Mooc m ON m.MoocID = l.MoocID\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryId";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LesMooc e = new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14));
                list.add(e);
            }
        } catch (Exception e) {
            e.getStackTrace();
        }
        return list;
    }

    public List<LesMooc> getAllLesMoocASC() {
        List<LesMooc> list = new ArrayList<>();
        try {
            String sql = "SELECT l.*, m.MoocName, u.FullName FROM LesMooc l\n"
                    + "LEFT JOIN Mooc m ON m.MoocId = l.MoocId\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "ORDER BY l.LessonName ASC";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LesMooc e = new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getString(10),
                        rs.getString(11));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LesMooc> getAllLesMoocDESC() {
        List<LesMooc> list = new ArrayList<>();
        try {
            String sql = "SELECT l.*, m.MoocName, u.FullName FROM LesMooc l\n"
                    + "LEFT JOIN Mooc m ON m.MoocId = l.MoocId\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "ORDER BY l.LessonName DESC";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LesMooc e = new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getString(10),
                        rs.getString(11));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LesMooc> getAllLesMoocByDate(String order) {
        List<LesMooc> list = new ArrayList<>();
        try {
            String sql = "SELECT l.*, m.MoocName, u.FullName FROM LesMooc l "
                    + "LEFT JOIN Mooc m ON m.MoocId = l.MoocId "
                    + "LEFT JOIN Users u ON u.UserID = l.created_by "
                    + "ORDER BY l.CreatedAt " + order;
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LesMooc e = new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getString(10),
                        rs.getString(11));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<LesMooc> getDateASC() {
        return getAllLesMoocByDate("ASC");
    }

    public List<LesMooc> getDateDESC() {
        return getAllLesMoocByDate("DESC");
    }

    public List<Lessons> searchByName(String txtSearch) {
        List<Lessons> list = new ArrayList<>();

        try {
            String sql = "SELECT l.*, s.Subject_Name, u.UserName FROM Lessons L\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = l.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "WHERE L.LessonName LIKE ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + txtSearch + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Lessons lesson = new Lessons(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10));
                list.add(lesson);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<LesMooc> searchForName(String txtSearch) {
        List<LesMooc> list = new ArrayList<>();

        try {
            String sql = "SELECT l.*, m.MoocName, u.FullName FROM LesMooc l \n"
                    + "LEFT JOIN Mooc m ON m.MoocId = l.MoocId\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "WHERE L.LessonName LIKE ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + txtSearch + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LesMooc e = new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getString(10),
                        rs.getString(11));
                list.add(e);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Lessons> getSubjectByStatus(String status) {
        List<Lessons> list = new ArrayList<>();

        try {
            String sql = "SELECT l.*, s.Subject_Name, u.UserName FROM Lessons L\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = l.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "WHERE l.status = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, status);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Lessons lesson = new Lessons(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10));
                list.add(lesson);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<LesMooc> getLessonByStatus(String status) {
        List<LesMooc> list = new ArrayList<>();
        try {
            String sql = "SELECT l.*, m.MoocName, u.FullName FROM LesMooc l \n"
                    + "LEFT JOIN Mooc m ON m.MoocId = l.MoocId\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "WHERE L.status = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, status);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LesMooc e = new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getString(10),
                        rs.getString(11));
                list.add(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Lessons lesson) {

        try {
            String sql = "INSERT INTO [dbo].[Lessons]\n"
                    + "           ([LessonName]\n"
                    + "           ,[VideoLink]\n"
                    + "           ,[CreatedAt]\n"
                    + "           ,[created_by]\n"
                    + "           ,[SubjectID]\n"
                    + "           ,[status]\n"
                    + "           ,[Content])\n"
                    + "     VALUES\n"
                    + "           (?,?,?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, lesson.getName());
            pst.setString(2, lesson.getVideoLink());
            pst.setDate(3, (Date) lesson.getCreatedAt());
            pst.setInt(4, lesson.getCreatedBy());
            pst.setInt(5, lesson.getSubjectId());
            pst.setString(6, lesson.getStatus());
            pst.setString(7, lesson.getContent());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertLes(LesMooc lesson) {
        try {
            String sql = "INSERT INTO [dbo].[LesMooc]\n"
                    + "           ([LessonName]\n"
                    + "           ,[VideoLink]\n"
                    + "           ,[CreatedAt]\n"
                    + "           ,[created_by]\n"
                    + "           ,[MoocID]\n"
                    + "           ,[status]\n"
                    + "           ,[Content])\n"
                    + "     VALUES\n"
                    + "           (?,?,?,?,?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, lesson.getName());
            pst.setString(2, lesson.getVideoLink());
            pst.setDate(3, (Date) lesson.getCreatedAt());
            pst.setInt(4, lesson.getCreatedBy());
            pst.setInt(5, lesson.getMoocId());
            pst.setString(6, lesson.getStatus());
            pst.setString(7, lesson.getContent());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Lessons deleteLesson(int id) {
        String sql = "DELETE FROM Lessons WHERE LessonId = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
        }
        return null;
    }

    public Lessons deleteLesMooc(int id) {
        String sql = "DELETE FROM LesMooc WHERE LesMoocID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Lessons getLessonById(int id) {

        String sql = "SELECT * FROM Lessons WHERE LessonId = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new Lessons(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LesMooc getLesMById(int id) {
        String sql = "SELECT * FROM LesMooc WHERE LesMoocID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LesMooc getLesMoocById(int id) {
        String sql = "Select l.*, m.MoocName, s.Subject_Name, u.FullName, s.Image, c.category_name  from LesMooc l\n"
                + "JOIN Mooc m ON m.MoocID = l.MoocID\n"
                + "LEFT JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryId\n"
                + "WHERE LesMoocID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int totalSubjectEnroll(int id) {
        String sql = "SELECT COUNT(*) AS totalUser\n"
                + "FROM Registrations r\n"
                + "JOIN Subjects s ON r.SubjectID = s.SubjectID\n"
                + "WHERE s.SubjectID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("totalUser");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int numberLesson(int id) {
        String sql = "SELECT COUNT(LessonId) As numberLesson FROM Lessons WHERE SubjectID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("numberLesson");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public int numberLesMooc(int id) {
        String sql = "SELECT COUNT(l.LesMoocID) As numberLesson FROM LesMooc l\n"
                + "LEFT JOIN Mooc m ON m.MoocID = l.MoocID\n"
                + "LEFT JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                + "WHERE s.SubjectID = ? ";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("numberLesson");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<Lessons> getLessonsBySubjectId(int id) {
        List<Lessons> list = new ArrayList<>();
        try {
            String sql = "SELECT l.*, s.Subject_Name, u.UserName, s.Image, c.category_name FROM Lessons L\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = l.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryId\n"
                    + "WHERE l.SubjectId = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Lessons lesson = new Lessons(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12));
                list.add(lesson);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }

    public List<Lessons> getLesMoocsBySubjectId(int id) {
        List<Lessons> list = new ArrayList<>();
        try {
            String sql = "SELECT l.*, s.Subject_Name, u.FullName, s.Image, c.category_name FROM LesMooc L\n"
                    + "LEFT JOIN Mooc m ON m.MoocID = l.MoocID\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryId\n"
                    + "WHERE m.SubjectID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Lessons lesson = new Lessons(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12));
                list.add(lesson);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }

    public List<LesMooc> getLessonsByMoocId(int id) {
        List<LesMooc> list = new ArrayList<>();
        try {
            String sql = "SELECT l.*, m.MoocName, s.Subject_Name, u.FullName, s.Image, c.category_name FROM LesMooc L\n"
                    + "LEFT JOIN Mooc m ON m.MoocID = l.MoocID\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.\n"
                    + "CategoryId\n"
                    + "WHERE m.MoocID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                LesMooc lesson = new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14));
                list.add(lesson);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }

    public Lessons getLessonsBYID(int id) {
        try {
            String sql = "SELECT l.*, s.Subject_Name, u.UserName, s.Image, c.category_name FROM Lessons L\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = l.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryId\n"
                    + "WHERE l.lessonid = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new Lessons(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public LesMooc getLessonsBID(int id) {
        try {
            String sql = "SELECT l.*, s.Subject_Name, u.FullName, s.Image, c.category_name, m.MoocName FROM LesMooc L\n"
                    + "LEFT JOIN Mooc m ON m.MoocId = l.MoocId\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = m.SubjectId\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryId\n"
                    + "WHERE l.lessonid = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public LesMooc getLessonLearning(int userId, int subjectId) {
        LesMooc lesson = null;
        try {
            String sql = "Select l.*, m.MoocName, s.Subject_Name, u.FullName from LesMooc l\n"
                    + "JOIN Mooc m ON m.MoocID = l.MoocID\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "where l.created_by=? and s.SubjectID=?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, subjectId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                int lessonId = rs.getInt("LessonID");
                lesson = new LessonDAO().getLesMoocById(lessonId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                ex.getStackTrace();
            }
        }
        return lesson;
    }

    public Lessons getLessonByFirstSb(int id) {
        try {
            String sql = "SELECT l.*, s.Subject_Name, u.UserName, s.Image, c.category_name FROM Lessons L\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = l.SubjectID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryId\n"
                    + "WHERE l.SubjectID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new Lessons(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12));
            }
        } catch (SQLException e) {
        }
        return null;
    }

    public LesMooc getLessonFirstBySid(int id) {
        try {
            String sql = "SELECT TOP 1 l.*, s.Subject_Name, u.UserName, m.MoocName, s.Image, c.category_name FROM LesMooc L\n"
                    + "LEFT JOIN Mooc m ON m.MoocID = l.MoocID\n"
                    + "LEFT JOIN Subjects s ON s.SubjectID = m.MoocID\n"
                    + "LEFT JOIN Users u ON u.UserID = l.created_by\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryId\n"
                    + "WHERE m.SubjectID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new LesMooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getInt(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getDouble(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14));
            }
        } catch (SQLException e) {
            e.getStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(LessonDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public void update(Lessons lesson) {

        try {
            String sql = "UPDATE [dbo].[Lessons]\n"
                    + "   SET [LessonName] = ?\n"
                    + "      ,[VideoLink] = ?\n"
                    + "      ,[CreatedAt] = ?\n"
                    + "      ,[created_by] = ?\n"
                    + "      ,[SubjectID] = ?\n"
                    + "      ,[status] = ?\n"
                    + "      ,[Content] = ?\n"
                    + " WHERE LessonId = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, lesson.getName());
            pst.setString(2, lesson.getVideoLink());
            pst.setDate(3, (Date) lesson.getCreatedAt());
            pst.setInt(4, lesson.getCreatedBy());
            pst.setInt(5, lesson.getSubjectId());
            pst.setString(6, lesson.getStatus());
            pst.setString(7, lesson.getContent());
            pst.setInt(8, lesson.getId());
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void updateLes(LesMooc lesson) {

        try {
            String sql = "UPDATE [dbo].[LesMooc]\n"
                    + "   SET [LessonName] = ?\n"
                    + "      ,[VideoLink] = ?\n"
                    + "      ,[CreatedAt] = ?\n"
                    + "      ,[created_by] = ?\n"
                    + "      ,[MoocId] = ?\n"
                    + "      ,[status] = ?\n"
                    + "      ,[Content] = ?\n"
                    + " WHERE LesMoocID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, lesson.getName());
            pst.setString(2, lesson.getVideoLink());
            pst.setDate(3, (Date) lesson.getCreatedAt());
            pst.setInt(4, lesson.getCreatedBy());
            pst.setInt(5, lesson.getMoocId());
            pst.setString(6, lesson.getStatus());
            pst.setString(7, lesson.getContent());
            pst.setInt(8, lesson.getId());
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void updateStatus(int id, String status) {

        String sql = "UPDATE Lessons SET Lessons.status = ? WHERE LessonId = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, status);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public void updateNewStatus(int id, String status) {
        String sql = "UPDATE LesMooc SET LesMooc.status = ? WHERE LesMoocID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, status);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public static void main(String args[]) {
        // TODO code application logic here
        LessonDAO ldao = new LessonDAO();
        int lesson = ldao.numberLesMooc(1);
        System.out.println(lesson);
//        List<Lessons> list = ldao.getLessonsBySubjectId(1);
//        for (Lessons lessons : list) {
//            System.out.println(lessons);
//        }
//        List<LesMooc> list = ldao.getLessonsByMoocId(1);
//        for (LesMooc lesMooc : list) {
//            System.out.println(lesMooc);
//        }
    }
}
