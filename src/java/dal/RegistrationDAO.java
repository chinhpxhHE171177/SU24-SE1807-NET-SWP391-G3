package dal;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Registration;

/**
 *
 * @author Admin
 */
public class RegistrationDAO extends DBContext {

    public ArrayList<Registration> getRegistrationFlowingSubjectName(String subjectName) {
        ArrayList<Registration> list = new ArrayList<>();
        try {
            String sql = "select u.UserID,u.UserName,s.Subject_Name,p.package_name,p.listPrice,r.status,r.valid_from,r.valid_to,r.created_at from Registration as r\n"
                    + "inner join  Users as u on r.UserID=u.UserID\n"
                    + "inner join Subjects as s on r.SubjectID=s.SubjectID\n"
                    + "inner join Packages as p on p.PackageID=r.PackageID\n"
                    + "where s.Subject_Name=?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, subjectName);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Registration(rs.getInt(1),
                        rs.getInt(6),
                        rs.getDate(7),
                        rs.getDate(8),
                        rs.getTimestamp(9),
                        rs.getString(2),
                        rs.getString(4), rs.getString(3), rs.getFloat(5)));
            }
        } catch (Exception e) {
            System.out.println("Error get Subject");
        }
        return list;
    }

    public List<Registration> getRegistration(int pageNumber, int rowsPerPage) {
        List<Registration> list = new ArrayList<>();
        String sql = "SELECT r.*, u.FullName, p.package_name, p.listPrice, s.Subject_Name "
                + "FROM Registration r "
                + "JOIN Users u ON u.UserID = r.UserID "
                + "JOIN Packages p ON p.PackageID = r.PackageID "
                + "JOIN Subjects s ON s.SubjectID = r.SubjectID "
                + "ORDER BY r.RegisterID "
                + "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, (pageNumber - 1) * rowsPerPage);
            pst.setInt(2, rowsPerPage);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Registration registration = new Registration(
                        rs.getInt("RegisterID"),
                        rs.getInt("UserID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("PackageID"),
                        rs.getBigDecimal("total_cost"),
                        rs.getInt("status"),
                        rs.getDate("valid_from"),
                        rs.getDate("valid_to"),
                        rs.getTimestamp("created_at"),
                        rs.getString("FullName"),
                        rs.getString("package_name"),
                        rs.getString("Subject_Name"),
                        rs.getFloat("listPrice")
                );
                list.add(registration);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Registration> getRegistration() {
        List<Registration> list = new ArrayList<>();
        String sql = "SELECT r.*, u.FullName, p.package_name, p.listPrice, s.Subject_Name "
                + "FROM Registration r "
                + "JOIN Users u ON u.UserID = r.UserID "
                + "JOIN Packages p ON p.PackageID = r.PackageID "
                + "JOIN Subjects s ON s.SubjectID = r.SubjectID";

        try (PreparedStatement statement = connection.prepareStatement(sql); ResultSet rs = statement.executeQuery()) {
            while (rs.next()) {
                Registration registration = new Registration(
                        rs.getInt("RegisterID"),
                        rs.getInt("UserID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("PackageID"),
                        rs.getBigDecimal("total_cost"),
                        rs.getInt("status"),
                        rs.getDate("valid_from"),
                        rs.getDate("valid_to"),
                        rs.getTimestamp("created_at"),
                        rs.getString("FullName"),
                        rs.getString("package_name"),
                        rs.getString("Subject_Name"),
                        rs.getFloat("listPrice")
                );
                list.add(registration);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalRecords() {
        String sql = "SELECT COUNT(*) FROM Registration";
        try (PreparedStatement pst = connection.prepareStatement(sql); ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void addRegistration(int userID, int subjectID, int packageID, BigDecimal totalCost, int status, Date validFrom, Date validTo, Timestamp createdAt) {
        try {
            String sql = "INSERT INTO Registration (UserID, SubjectID, PackageID, total_cost, status, valid_from, valid_to, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, userID);
            statement.setInt(2, subjectID);
            statement.setInt(3, packageID);
            statement.setBigDecimal(4, totalCost);
            statement.setInt(5, status);
            statement.setDate(6, new java.sql.Date(validFrom.getTime()));
            statement.setDate(7, new java.sql.Date(validTo.getTime()));
            statement.setTimestamp(8, createdAt);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new registration was inserted successfully!");
            }
        } catch (Exception e) {
            System.out.println("Error Add Registration");
        }
    }

    public void deleteRegistration(int id) {

        try {
            String sql = "DELETE FROM Registration WHERE RegisterID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();

        } catch (Exception e) {
            System.out.println("Error Delete Registration");
        }
    }

    public List<Registration> getRegistrations() {
        List<Registration> list = new ArrayList<>();
        try {
            String sql = "SELECT r.*, u.FullName, p.package_name, p.listPrice, s.Subject_Name "
                    + "FROM Registration r "
                    + "JOIN Users u ON u.UserID = r.UserID "
                    + "JOIN Packages p ON p.PackageID = r.PackageID "
                    + "JOIN Subjects s ON s.SubjectID = r.SubjectID";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Registration registration = new Registration(
                        rs.getInt("RegisterID"),
                        rs.getInt("UserID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("PackageID"),
                        rs.getBigDecimal("total_cost"),
                        rs.getInt("status"),
                        rs.getDate("valid_from"),
                        rs.getDate("valid_to"),
                        rs.getTimestamp("created_at"),
                        rs.getString("FullName"),
                        rs.getString("package_name"),
                        rs.getString("Subject_Name"),
                        rs.getFloat("listPrice")
                );
                list.add(registration);
            }
        } catch (Exception e) {
            System.out.println("Error get Registration: " + e.getMessage());
        }
        return list;
    }

    public Registration getRegistrationById(int registerId) {
        Registration registration = null;
        try {
            String sql = "SELECT r.*, u.FullName, p.package_name, p.listPrice, s.Subject_Name "
                    + "FROM Registration r "
                    + "JOIN Users u ON u.UserID = r.UserID "
                    + "JOIN Packages p ON p.PackageID = r.PackageID "
                    + "JOIN Subjects s ON s.SubjectID = r.SubjectID "
                    + "WHERE r.RegisterID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, registerId);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                registration = new Registration(
                        rs.getInt("RegisterID"),
                        rs.getInt("UserID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("PackageID"),
                        rs.getBigDecimal("total_cost"),
                        rs.getInt("status"),
                        rs.getDate("valid_from"),
                        rs.getDate("valid_to"),
                        rs.getTimestamp("created_at"),
                        rs.getString("FullName"),
                        rs.getString("package_name"),
                        rs.getString("Subject_Name"),
                        rs.getFloat("listPrice")
                );
            }
        } catch (Exception e) {
            System.out.println("Error get Registration by ID: " + e.getMessage());
        }
        return registration;
    }

    public boolean updateRegistration(Registration registration) {
        try {
            String sql = "UPDATE Registration SET total_cost = ?, status = ?, valid_from = ?, valid_to = ?, PackageID = ? "
                    + "WHERE RegisterID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBigDecimal(1, registration.getTotalCost());
            statement.setInt(2, registration.getStatus());
            statement.setDate(3, new java.sql.Date(registration.getValidFrom().getTime()));
            statement.setDate(4, new java.sql.Date(registration.getValidTo().getTime()));
            statement.setInt(5, registration.getPackageID());
            statement.setInt(6, registration.getRegisterID());
            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            System.out.println("Error update Registration: " + e.getMessage());
            return false;
        }
    }

    public List<Registration> filterRegistration(String property, int value) {
        List<Registration> list = new ArrayList<>();
        try {
            String sql = "Select r.*, u.FullName, p.package_name,p.listPrice ,s.Subject_Name from Registration as r "
                    + "   join Users u on u.UserID = r.UserID\n"
                    + "   join Packages p on p.PackageID = r.PackageID\n"
                    + "   join Subjects s on s.SubjectID = r.SubjectID\n"
                    + " WHERE " + property + " = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, value);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                Registration rg = new Registration(
                        rs.getInt("RegisterID"),
                        rs.getInt("UserID"),
                        rs.getInt("SubjectID"),
                        rs.getInt("PackageID"),
                        rs.getBigDecimal("total_cost"),
                        rs.getInt("status"),
                        rs.getDate("valid_from"),
                        rs.getDate("valid_to"),
                        rs.getTimestamp("created_at"),
                        rs.getString("FullName"),
                        rs.getString("package_name"),
                        rs.getString("Subject_Name"),
                        rs.getFloat("listPrice")
                );
                list.add(rg);
            }
        } catch (Exception e) {
            System.out.println("Error Filter Registration");
        }
        return list;
    }

    public static void main(String[] args) {
//        RegistrationDAO dao = new RegistrationDAO();
//        List<Registration> registrations = registrationsDBContext.getRegistrationFlowingSubjectName("Calculus");
//        for (Registration registration : registrations) {
//            System.out.println(registration.getSubjectName());
//        }
//          <td>${o.FullName}</td>
//          <td>${o.SubjectName}</td>                    
//          <td>${o.PackageName}</td>
        // Tạo một đối tượng RegistrationDAO
//        RegistrationDAO dao = new RegistrationDAO();

        // Thêm một bản ghi mới vào bảng registrations
//            int userID = 1;
//            int subjectID = 1;
//            int packageID = 1;
//            BigDecimal totalCost = new BigDecimal("100.00");
//            int status = 1;
//            Date validFrom = new Date(System.currentTimeMillis());
//            Date validTo = new Date(System.currentTimeMillis() + 86400000L); // +1 day
//            Timestamp createdAt = new Timestamp(System.currentTimeMillis());
//
//            dao.addRegistration(userID, subjectID, packageID, totalCost, status, validFrom, validTo, createdAt);
//            System.out.println("ok");
//       
//      
//
//        RegistrationDAO dao = new RegistrationDAO();
//
//        // Tạo các thông tin giả định cho việc thêm vào bảng
//        int userID = 4;
//        int subjectID = 3;
//        int packageID = 4;
//        BigDecimal totalCost = new BigDecimal("300.00");
//        int status = 2;
//        Date validFrom = new Date();
//        Date validTo = new Date();
//        Timestamp createdAt = new Timestamp(System.currentTimeMillis());
//
//        // Thêm một bản ghi mới vào bảng registrations
//        dao.addRegistration(userID, subjectID, packageID, totalCost, status, validFrom, validTo, createdAt);
//
//        // In ra thông báo khi thêm thành công
//        System.out.println("Added successfully!");
//        RegistrationDAO dao = new RegistrationDAO();
//
//        // Hiển thị tất cả các bản ghi trước khi xóa
//        System.out.println("Danh sách trước khi xóa:");
//        List<Registration> registrations = dao.getAllRegistration();
//        for (Registration registration : registrations) {
//            System.out.println(registration);
//        }
//
//        // Xóa bản ghi với ID cụ thể
//        int idToDelete = 13; // Thay bằng ID bạn muốn xóa
//        boolean isDeleted = dao.deleteRegistration(idToDelete);
//        if (isDeleted) {
//            System.out.println("Xóa thành công bản ghi với ID: " + idToDelete);
//        } else {
//            System.out.println("Không thể xóa bản ghi với ID: " + idToDelete);
//        }
//
//        // Hiển thị tất cả các bản ghi sau khi xóa
//        System.out.println("Danh sách sau khi xóa:");
//        registrations = dao.getAllRegistration();
//        for (Registration registration : registrations) {
//            System.out.println(registration);
//        }
        // Tạo một đối tượng RegistrationDAO
//    RegistrationDAO registrationDAO = new RegistrationDAO();
//
//    // Hiển thị thông tin trước khi cập nhật
//    System.out.println("Registration before update:");
//    List<Registration> registrationsBeforeUpdate = registrationDAO.getAllRegistration();
//    for (Registration registration : registrationsBeforeUpdate) {
//        System.out.println(registration);
//    }
//
//    // Tạo một đối tượng Registration mới để cập nhật
//    Registration registrationToUpdate = new Registration();
//    registrationToUpdate.setRegisterID(6); // Đặt ID của bản ghi cần cập nhật
//    registrationToUpdate.setUserID(2); // Đặt UserID mới
//    registrationToUpdate.setSubjectID(2); // Đặt SubjectID mới
//    registrationToUpdate.setPackageID(3); // Đặt PackageID mới
//    registrationToUpdate.setTotalCost(new BigDecimal("400.00")); // Đặt totalCost mới
//    registrationToUpdate.setStatus(1); // Đặt status mới
//
//    // Thực hiện cập nhật và kiểm tra kết quả
//    boolean isUpdated = registrationDAO.updateRegistration(registrationToUpdate);
//    if (isUpdated) {
//        System.out.println("Registration updated successfully.");
//    } else {
//        System.out.println("Failed to update registration.");
//    }
//
//    // Hiển thị thông tin sau khi cập nhật
//    System.out.println("Registration after update:");
//    List<Registration> registrationsAfterUpdate = registrationDAO.getAllRegistration();
//    for (Registration registration : registrationsAfterUpdate) {
//        System.out.println(registration);
////    }
//        RegistrationDAO registrationDAO = new RegistrationDAO();
////
////        // Test lấy tất cả các đăng ký
//        List<Registration> allRegistration = registrationDAO.getAllRegistration();
//        System.out.println("All Registration:");
//        for (Registration registration : allRegistration) {
//            System.out.println(registration);
//        }
//         Test lọc đăng ký theo thuộc tính
//        String propertyToFilter = "UserID"; // Đổi thành thuộc tính bạn muốn lọc (UserID, SubjectID, PackageID, hoặc Status)
//        int valueToFilter = 1; // Đổi thành giá trị bạn muốn lọc
//        List<Registration> filtered = RegistrationDAO.filterRegistration(propertyToFilter, valueToFilter);
//        System.out.println("\nFiltered Registration (by " + propertyToFilter + " = " + valueToFilter + "):");
//        for (Registration registration : filtered) {
//            System.out.println(registration);
//        }
//        
//        String propertyToFilter = "PackageID";
//        // Lọc danh sách đăng ký dựa trên property và value
//        List<Registration> registrationList = registrationDAO.filterRegistration(propertyToFilter, 2);
//
//        // In ra thông tin của các đăng ký
//        for (Registration registration : registrationList) {
//            System.out.println(registration);
////        }
//        RegistrationDAO dao = new RegistrationDAO();
//
//        try {
//
//            
//        } catch (Exception e) {
//            System.out.println("loi roi");
//        }
    }
}
