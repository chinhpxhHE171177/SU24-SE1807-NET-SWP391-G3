package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.LesMooc;
import model.Mooc;
import model.Ratings;

/**
 *
 * @author Admin
 */
public class MoocDAO extends DBContext {

    /**
     * @param args the command line arguments
     */
    public List<Mooc> getAllMooc() {
        List<Mooc> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Mooc";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Mooc mooc = new Mooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4));
                list.add(mooc);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Mooc> getAllMoocs() {
        List<Mooc> list = new ArrayList<>();
        try {
            String sql = "SELECT m.*, s.Subject_Name, COUNT(lm.LesMoocID) AS NumOfLes \n"
                    + "FROM Mooc m \n"
                    + "JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                    + "LEFT JOIN LesMooc lm ON lm.MoocID = m.MoocID\n"
                    + "GROUP BY m.MoocID, m.MoocName, m.SubjectID, m.Status, s.Subject_Name;";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Mooc mooc = new Mooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getString(5),
                        rs.getInt(6));
                list.add(mooc);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Mooc> searchForName(String txtSearch) {
        List<Mooc> list = new ArrayList<>();
        try {
            String sql = "SELECT m.*, s.Subject_Name, COUNT(lm.LesMoocID) AS NumOfLes \n"
                    + "FROM Mooc m \n"
                    + "JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                    + "LEFT JOIN LesMooc lm ON lm.MoocID = m.MoocID\n"
                    + "WHERE m.MoocName LIKE ?\n"
                    + "GROUP BY m.MoocID, m.MoocName, m.SubjectID, m.[Status], s.Subject_Name;";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + txtSearch + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Mooc mooc = new Mooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getString(5),
                        rs.getInt(6));
                list.add(mooc);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void updateNewStatus(int id, boolean status) {
        String sql = "UPDATE Mooc SET Mooc.Status = ? WHERE MoocID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setBoolean(1, status);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public List<Mooc> getMoocsBySubjectId(int sid) {
        List<Mooc> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Mooc WHERE SubjectID = ? AND Status = 1";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, sid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Mooc mooc = new Mooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3));
                list.add(mooc);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Mooc getMoocById(int mid) {
        try {
            String sql = "SELECT * FROM Mooc WHERE MoocID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, mid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new Mooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public List<Mooc> getLesMoocBySubjectId(int sid) {
        List<Mooc> moocList = new ArrayList<>();
        try {
            String moocSql = "SELECT * FROM Mooc WHERE SubjectID = ?";
            PreparedStatement moocPst = connection.prepareStatement(moocSql);
            moocPst.setInt(1, sid);
            ResultSet moocRs = moocPst.executeQuery();

            while (moocRs.next()) {
                int moocId = moocRs.getInt("MoocID");
                String moocName = moocRs.getString("MoocName");
                int subjectId = moocRs.getInt("SubjectID");

                Mooc mooc = new Mooc(moocId, moocName, subjectId);

                // Fetch lessons for each MOOC
                String lessonSql = "SELECT l.*, u.FullName FROM LesMooc l "
                        + "JOIN Mooc m ON m.MoocID = l.MoocID "
                        + "LEFT JOIN Users u ON u.UserID = l.created_by "
                        + "WHERE m.MoocID = ?";
                PreparedStatement lessonPst = connection.prepareStatement(lessonSql);
                lessonPst.setInt(1, moocId);
                ResultSet lessonRs = lessonPst.executeQuery();

                List<LesMooc> lessonList = new ArrayList<>();
                while (lessonRs.next()) {
                    int lessonId = lessonRs.getInt("LesMoocID");
                    String lessonName = lessonRs.getString("LessonName");
                    String videoLink = lessonRs.getString("VideoLink");
                    Date createdAt = lessonRs.getDate("CreatedAt");
                    int createdBy = lessonRs.getInt("created_by");
                    String status = lessonRs.getString("status");
                    String content = lessonRs.getString("Content");
                    int duration = lessonRs.getInt("duration");
                    String fullName = lessonRs.getString("FullName");

                    // Fetch ratings for each lesson
                    String ratingSql = "SELECT * FROM Ratings WHERE LesMoocID = ?";
                    PreparedStatement ratingPst = connection.prepareStatement(ratingSql);
                    ratingPst.setInt(1, lessonId);
                    ResultSet ratingRs = ratingPst.executeQuery();

                    List<Ratings> ratingsList = new ArrayList<>();
                    while (ratingRs.next()) {
                        int ratingId = ratingRs.getInt("RatingID");
                        int userId = ratingRs.getInt("UserID");
                        int lesMoocId = ratingRs.getInt("LesMoocID");
                        int rating = ratingRs.getInt("rating");
                        String comment = ratingRs.getString("comment");
                        Date createdAtRating = ratingRs.getTimestamp("created_at");

                        Ratings ratingObj = new Ratings(ratingId, userId, lesMoocId, rating, comment, (Timestamp) createdAtRating, 0, true, true);
                        ratingsList.add(ratingObj);
                    }

                    LesMooc lesson = new LesMooc(lessonId, lessonName, videoLink, createdAt, createdBy, moocId, status, content, duration, fullName);
                    lessonList.add(lesson);
                }
                mooc.setLessons(lessonList);
                moocList.add(mooc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return moocList;
    }

    public void insert(Mooc mooc) {
        try {
            String sql = "INSERT INTO [dbo].[Mooc]\n"
                    + "           ([MoocName]\n"
                    + "           ,[SubjectID]\n"
                    + "           ,[Status])\n"
                    + "     VALUES\n"
                    + "           (?,?,?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, mooc.getName());
            pst.setInt(2, mooc.getSubjectId());
            pst.setBoolean(3, mooc.isStatus());
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public Mooc delete(int id) {
        String sql = "DELETE FROM Mooc WHERE MoocID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void update(Mooc mooc) {
        String sql = "UPDATE [dbo].[Mooc]\n"
                + "   SET [MoocName] = ?\n"
                + "      ,[SubjectID] = ?\n"
                + "      ,[Status] = ?\n"
                + " WHERE MoocID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, mooc.getName());
            pst.setInt(2, mooc.getSubjectId());
            pst.setBoolean(3, mooc.isStatus());
            pst.setInt(4, mooc.getId());
            pst.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Mooc> getMoocByStatus(int sid) {
        List<Mooc> list = new ArrayList<>();
        try {
            String sql = "SELECT m.*, s.Subject_Name, COUNT(lm.LesMoocID) AS NumOfLes \n"
                    + "FROM Mooc m \n"
                    + "JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                    + "LEFT JOIN LesMooc lm ON lm.MoocID = m.MoocID\n"
                    + "WHERE m.Status = ?\n"
                    + "GROUP BY m.MoocID, m.MoocName, m.SubjectID, m.[Status], s.Subject_Name;";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, sid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Mooc mooc = new Mooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getString(5),
                        rs.getInt(6));
                list.add(mooc);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Mooc> getMoocByCid(int sid) {
        List<Mooc> list = new ArrayList<>();
        try {
            String sql = "SELECT m.*, s.Subject_Name, COUNT(lm.LesMoocID) AS NumOfLes \n"
                    + "FROM Mooc m \n"
                    + "JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                    + "LEFT JOIN LesMooc lm ON lm.MoocID = m.MoocID\n"
                    + "WHERE m.SubjectID = ?\n"
                    + "GROUP BY m.MoocID, m.MoocName, m.SubjectID, m.[Status], s.Subject_Name;";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, sid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Mooc mooc = new Mooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getString(5),
                        rs.getInt(6));
                list.add(mooc);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Mooc> getSubjectsByCourseAndStatus(int cid, int status) {
        List<Mooc> list = new ArrayList<>();
        try {
            String sql = "SELECT m.*, s.Subject_Name, COUNT(lm.LesMoocID) AS NumOfLes \n"
                    + "FROM Mooc m \n"
                    + "JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                    + "LEFT JOIN LesMooc lm ON lm.MoocID = m.MoocID\n"
                    + "WHERE m.SubjectID = ? AND m.Status = ?\n"
                    + "GROUP BY m.MoocID, m.MoocName, m.SubjectID, m.[Status], s.Subject_Name;";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, cid);
            pst.setInt(2, status);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Mooc mooc = new Mooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3),
                        rs.getBoolean(4),
                        rs.getString(5),
                        rs.getInt(6));
                list.add(mooc);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static void main(String args[]) {
        MoocDAO mdao = new MoocDAO();
        List<Mooc> listm = mdao.getMoocByStatus(1);
//        Mooc listm = mdao.getMoocById(2);
        for (Mooc mooc : listm) {
            System.out.println(listm);
        }
    }
}
