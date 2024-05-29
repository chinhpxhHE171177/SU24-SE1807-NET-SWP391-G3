package dal;

import java.sql.Date;
import java.util.List;
import model.Lessons;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

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
        }
        return list;
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

    public List<Lessons> getLessonById(int id) {
        List<Lessons> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Lessons l\n"
                    + "WHERE l.SubjectID = ?";
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
                        rs.getString(8));
                list.add(lesson);
            }
        } catch (Exception e) {
        }
        return list;
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
                Lessons e = new Lessons(
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
                list.add(e);
            }
        } catch (Exception e) {
        }
        return list;
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

    public static void main(String args[]) {
        // TODO code application logic here
        LessonDAO ldao = new LessonDAO();
//        Lessons lesson = ldao.getLessonsBYID(3);
//        System.out.println(lesson);
        List<Lessons> list = ldao.getLessonsBySubjectId(3);
        for (Lessons lessons : list) {
            System.out.println(lessons);
        }
    }
}
