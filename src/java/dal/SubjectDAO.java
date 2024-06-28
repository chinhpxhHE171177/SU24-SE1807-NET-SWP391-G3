package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Registration;
import model.Subject;

/**
 *
 * @author Admin
 */
public class SubjectDAO extends DBContext {

    /**
     * @param args the command line arguments
     * @return
     */
    /*----------- 
    Admin 
------------*/
    public List<Subject> getAllSubjects() {
        List<Subject> list = new ArrayList<>();

        try {
            String sql = "SELECT s.*, c.category_name, p.package_name, u.UserName, COUNT(l.LessonId) AS numberOfLessons FROM [Subjects] s\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryID\n"
                    + "LEFT JOIN Packages p ON p.PackageID = s.PackageID\n"
                    + "LEFT JOIN Users u ON u.UserID = s.created_by\n"
                    + "LEFT JOIN Lessons l ON s.SubjectID = l.SubjectID\n"
                    + "GROUP BY\n"
                    + "s.SubjectID,\n"
                    + "s.Subject_Name,\n"
                    + "s.Description,\n"
                    + "s.Image,\n"
                    + "s.Status,\n"
                    + "s.PackageId,\n"
                    + "s.CategoryId,\n"
                    + "s.created_by,\n"
                    + "s.Created_at,\n"
                    + "c.category_name,\n"
                    + "p.package_name,\n"
                    + "u.UserName";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13));

                int totalEnroll = totalSubjectEnroll(subject.getId());
                subject.setTotalEnroll(totalEnroll);

                list.add(subject);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int totalSubjectEnroll(int id) {
        String sql = "SELECT COUNT(UserID) AS totalUser FROM Registrations WHERE SubjectID = ?";
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

    public List<Subject> searchByName(String txtSearch) {
        List<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT s.*, c.category_name, p.package_name, u.UserName, COUNT(l.LessonId) AS numberOfLessons FROM [Subjects] s\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryID\n"
                    + "LEFT JOIN Packages p ON p.PackageID = s.PackageID\n"
                    + "LEFT JOIN Users u ON u.UserID = s.created_by\n"
                    + "LEFT JOIN Lessons l ON s.SubjectID = l.SubjectID\n"
                    + "WHERE Subject_Name LIKE ?\n"
                    + "GROUP BY\n"
                    + "s.SubjectID,\n"
                    + "s.Subject_Name,\n"
                    + "s.Description,\n"
                    + "s.Image,\n"
                    + "s.Status,\n"
                    + "s.PackageId,\n"
                    + "s.CategoryId,\n"
                    + "s.created_by,\n"
                    + "s.Created_at,\n"
                    + "c.category_name,\n"
                    + "p.package_name,\n"
                    + "u.UserName";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + txtSearch + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13));

                int numberSearch = getNumberSearch(txtSearch);
                list.add(subject);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public int getNumberSearch(String txtSearch) {
        String sql = "SELECT COUNT(SubjectID) FROM Subjects WHERE Subject_Name LIKE ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + txtSearch + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt("");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public int getTotalSubject() {
        String sql = "SELECT COUNT(*) FROM Subjects";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public List<Subject> getSubjectByCid(int id) {
        List<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT s.*, c.category_name, p.package_name, u.UserName, COUNT(l.LessonId) AS numberOfLessons FROM [Subjects] s\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryID\n"
                    + "LEFT JOIN Packages p ON p.PackageID = s.PackageID\n"
                    + "LEFT JOIN Users u ON u.UserID = s.created_by\n"
                    + "LEFT JOIN Lessons l ON s.SubjectID = l.SubjectID\n"
                    + "WHERE c.CategoryID = ?\n"
                    + "GROUP BY\n"
                    + "s.SubjectID,\n"
                    + "s.Subject_Name,\n"
                    + "s.Description,\n"
                    + "s.Image,\n"
                    + "s.Status,\n"
                    + "s.PackageId,\n"
                    + "s.CategoryId,\n"
                    + "s.created_by,\n"
                    + "s.Created_at,\n"
                    + "c.category_name,\n"
                    + "p.package_name,\n"
                    + "u.UserName";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13));
                list.add(subject);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<Subject> getSubjectByStatus(int id) {
        List<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT s.*, c.category_name, p.package_name, u.UserName, COUNT(l.LessonId) AS numberOfLessons FROM [Subjects] s\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryID\n"
                    + "LEFT JOIN Packages p ON p.PackageID = s.PackageID\n"
                    + "LEFT JOIN Users u ON u.UserID = s.created_by\n"
                    + "LEFT JOIN Lessons l ON s.SubjectID = l.SubjectID\n"
                    + "WHERE s.status = ?\n"
                    + "GROUP BY\n"
                    + "s.SubjectID,\n"
                    + "s.Subject_Name,\n"
                    + "s.Description,\n"
                    + "s.Image,\n"
                    + "s.Status,\n"
                    + "s.PackageId,\n"
                    + "s.CategoryId,\n"
                    + "s.created_by,\n"
                    + "s.Created_at,\n"
                    + "c.category_name,\n"
                    + "p.package_name,\n"
                    + "u.UserName";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13));
                list.add(subject);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public List<Subject> getSubjectsByCategoryAndStatus(int cid, int status) {
        List<Subject> list = new ArrayList<>();

        try {
            String sql = "SELECT s.*, c.category_name, p.package_name, u.UserName, COUNT(l.LessonId) AS numberOfLessons FROM [Subjects] s\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryID\n"
                    + "LEFT JOIN Packages p ON p.PackageID = s.PackageID\n"
                    + "LEFT JOIN Users u ON u.UserID = s.created_by\n"
                    + "LEFT JOIN Lessons l ON s.SubjectID = l.SubjectID\n"
                    + "WHERE c.CategoryID = ? AND s.status = ?\n"
                    + "GROUP BY\n"
                    + "s.SubjectID,\n"
                    + "s.Subject_Name,\n"
                    + "s.Description,\n"
                    + "s.Image,\n"
                    + "s.Status,\n"
                    + "s.PackageId,\n"
                    + "s.CategoryId,\n"
                    + "s.created_by,\n"
                    + "s.Created_at,\n"
                    + "c.category_name,\n"
                    + "p.package_name,\n"
                    + "u.UserName";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, cid);
            pst.setInt(2, status);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13));
                list.add(subject);
            }
        } catch (Exception e) {
        }
        return list;

    }

    public void insert(Subject subject) {

        String sql = "INSERT INTO [dbo].[Subjects]\n"
                + "           ([Subject_Name]\n"
                + "           ,[Description]\n"
                + "           ,[Image]\n"
                + "           ,[Status]\n"
                + "           ,[PackageId]\n"
                + "           ,[CategoryId]\n"
                + "           ,[created_by]\n"
                + "           ,[Created_at])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?)";

        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, subject.getName());
            pst.setString(2, subject.getDescription());
            pst.setString(3, subject.getImage());
            pst.setBoolean(4, subject.isStatus());
            pst.setInt(5, subject.getPackageId());
            pst.setInt(6, subject.getCategoryId());
            pst.setInt(7, subject.getCreated_by());
            pst.setDate(8, (Date) subject.getCreated_at());
            pst.executeUpdate();
        } catch (Exception e) {
        }
    }

    public Subject deleteSubject(int id) {
        String sql = "DELETE FROM [dbo].[Subjects] WHERE SubjectID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Subject with ID " + id + " deleted successfully.");
            } else {
                System.out.println("No subject found with ID " + id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error deleting subject", e);
        }
        return null;
    }

    public Subject getSubjectById(int id) {
        String sql = "SELECT s.*, c.category_name, p.package_name, u.UserName, COUNT(l.LessonId) AS numberOfLessons FROM [Subjects] s\n"
                + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryID\n"
                + "LEFT JOIN Packages p ON p.PackageID = s.PackageID\n"
                + "LEFT JOIN Users u ON u.UserID = s.created_by\n"
                + "LEFT JOIN Lessons l ON s.SubjectID = l.SubjectID\n"
                + "WHERE s.SubjectID = ?\n"
                + "GROUP BY\n"
                + "s.SubjectID,\n"
                + "s.Subject_Name,\n"
                + "s.Description,\n"
                + "s.Image,\n"
                + "s.Status,\n"
                + "s.PackageId,\n"
                + "s.CategoryId,\n"
                + "s.created_by,\n"
                + "s.Created_at,\n"
                + "c.category_name,\n"
                + "p.package_name,\n"
                + "u.UserName";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Subject(
                            rs.getInt("SubjectID"),
                            rs.getString("Subject_Name"),
                            rs.getString("Description"),
                            rs.getString("Image"),
                            rs.getBoolean("Status"),
                            rs.getInt("PackageId"),
                            rs.getInt("CategoryId"),
                            rs.getInt("created_by"),
                            rs.getDate("Created_at"),
                            rs.getString("category_name"),
                            rs.getString("package_name"),
                            rs.getString("UserName"),
                            rs.getInt("numberOfLessons"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // You may want to log the error or throw a custom exception
        }
        return null; // Return null if no subject found

    }

    public void updateSubject(Subject subject) {
        String sql = "UPDATE Subjects SET Subject_Name = ?, Description = ?, Image = ?, Status = ?, PackageId = ?, CategoryId = ?, created_by = ?, Created_at = ? WHERE SubjectID = ?";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, subject.getName());
            pst.setString(2, subject.getDescription());
            pst.setString(3, subject.getImage());
            pst.setBoolean(4, subject.isStatus());
            pst.setInt(5, subject.getPackageId());
            pst.setInt(6, subject.getCategoryId());
            pst.setInt(7, subject.getCreated_by());
            pst.setTimestamp(8, new java.sql.Timestamp(subject.getCreated_at().getTime()));
            pst.setInt(9, subject.getId());

            int affectedRows = pst.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Subject with ID " + subject.getId() + " updated successfully.");
            } else {
                System.out.println("No subject found with ID " + subject.getId());
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Database error: " + e.getMessage());
        }
    }

    public List<Subject> getLastest() {
        List<Subject> list = new ArrayList<>();
        try {
            String sql = "SELECT s.*, c.category_name, p.package_name, u.UserName, COUNT(l.LessonId) AS numberOfLessons FROM [Subjects] s\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryID\n"
                    + "LEFT JOIN Packages p ON p.PackageID = s.PackageID\n"
                    + "LEFT JOIN Users u ON u.UserID = s.created_by\n"
                    + "LEFT JOIN Lessons l ON s.SubjectID = l.SubjectID\n"
                    + "GROUP BY\n"
                    + "s.SubjectID,\n"
                    + "s.Subject_Name,\n"
                    + "s.Description,\n"
                    + "s.Image,\n"
                    + "s.Status,\n"
                    + "s.PackageId,\n"
                    + "s.CategoryId,\n"
                    + "s.created_by,\n"
                    + "s.Created_at,\n"
                    + "c.category_name,\n"
                    + "p.package_name,\n"
                    + "u.UserName\n"
                    + "ORDER BY s.Created_at DESC";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13));
                list.add(subject);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Subject> getTopUserEnroll() {
        List<Subject> list = new ArrayList<>();

        try {
            String sql = "SELECT s.*, c.category_name, p.package_name, u.UserName, sr.totalUser\n"
                    + "FROM Subjects s\n"
                    + "JOIN (\n"
                    + "    SELECT SubjectID, COUNT(UserID) AS totalUser\n"
                    + "    FROM Registrations\n"
                    + "    GROUP BY SubjectID\n"
                    + ") sr ON s.SubjectID = sr.SubjectID\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryID\n"
                    + "LEFT JOIN Packages p ON p.PackageID = s.PackageID\n"
                    + "LEFT JOIN Users u ON u.UserID = s.created_by\n"
                    + "LEFT JOIN Lessons l ON s.SubjectID = l.SubjectID\n"
                    + "WHERE sr.totalUser = (\n"
                    + "    SELECT MAX(totalUser)\n"
                    + "    FROM (\n"
                    + "        SELECT COUNT(UserID) AS totalUser\n"
                    + "        FROM Registrations\n"
                    + "        GROUP BY SubjectID\n"
                    + "    ) AS subquery\n"
                    + ");";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getInt(13)); //totalEnroll

                int totalEnroll = totalSubjectEnroll(subject.getId());
                subject.setTotalEnroll(totalEnroll);

                list.add(subject);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Subject> getSubjectNameASC() {
        List<Subject> list = new ArrayList<>();

        try {
            String sql = "SELECT s.*, c.category_name, p.package_name, u.UserName FROM [Subjects] s\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryID\n"
                    + "LEFT JOIN Packages p ON p.PackageID = s.PackageID\n"
                    + "LEFT JOIN Users u ON u.UserID = s.created_by\n"
                    + "LEFT JOIN Lessons l ON s.SubjectID = l.SubjectID\n"
                    + "ORDER BY s.Subject_Name ASC";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12));
                list.add(subject);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Registration> getAllSubjectforRegistration(){
        ArrayList<Registration> list=new ArrayList<>();
        try {
            String sql="select s.Subject_Name from Subjects as s";
            PreparedStatement ps=connection.prepareStatement(sql);
            ResultSet rs=ps.executeQuery();
            while(rs.next()){
                list.add(new Registration(rs.getString(1)));
            }
        } catch (Exception e) {
        }
        return list;
    }
    public List<Subject> getSubjectNameDESC() {
        List<Subject> list = new ArrayList<>();

        try {
            String sql = "SELECT s.*, c.category_name, p.package_name, u.UserName FROM [Subjects] s\n"
                    + "LEFT JOIN Categories c ON c.CategoryID = s.CategoryID\n"
                    + "LEFT JOIN Packages p ON p.PackageID = s.PackageID\n"
                    + "LEFT JOIN Users u ON u.UserID = s.created_by\n"
                    + "LEFT JOIN Lessons l ON s.SubjectID = l.SubjectID\n"
                    + "ORDER BY s.Subject_Name DESC";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Subject subject = new Subject(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getBoolean(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getDate(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12));
                list.add(subject);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static void main(String args[]) {
        SubjectDAO sdao = new SubjectDAO();
        System.out.println(sdao.getAllSubjectforRegistration());
//        Subject subject = new Subject();
//        subject = (Subject) sdao.getSubjectByCid(10);
//        System.out.println(subject);
//        sdao.deleteSubject(11);
//        List<Subject> list = sdao.getTopUserEnroll();
//        for (Subject subject : list) {
//            System.out.println(subject);
//            ArrayList<Registrations> list=sdao.getAllSubject();
//            for (Registrations registrations : list) {
//                System.out.println(registrations.getSubjectName());
//        }
//        List<Subject> listname = sdao.getSubjectsByCategoryAndStatus(1, 1);
//        for (Subject subject : listname) {
//            System.out.println(subject);
//        }

//        Subject subject = new Subject();
//        subject.setId(13);
//        subject.setName("Physics");
//        subject.setDescription("Physics basics");
//        subject.setImage("image01");
//        subject.setStatus(true);
//        subject.setPackageId(1);
//        subject.setCategoryId(1);
//        subject.setCreated_by(1);
//        subject.setCreated_at(Date.valueOf("2024-05-18"));
//
//        sdao.updateSubject(subject);
//        Subject subject = new Subject();
//        subject.setName("Physics");
//        subject.setDescription("Physics basics");
//        subject.setImage("image01.jpg");
//        subject.setStatus(true);
//        subject.setPackageId(1);
//        subject.setCategoryId(1);
//        subject.setUserId(1);
//        subject.setRatingId(1); // Ensure this RatingID exists in the Ratings table
//        subject.setDate(Date.valueOf("2024-05-16"));
//
//        // Insert the new subject into the database
//        sdao.insert(subject);
//        int count = sdao.getTotalSubject();
//        System.out.println(count);
    }
}
