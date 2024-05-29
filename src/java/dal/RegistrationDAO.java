package dal;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.Registrations;

/**
 *
 * @author Admin
 */
public class RegistrationDAO extends DBContext {

    public List<Registrations> getAllRegistrations() {
        List<Registrations> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Registrations";
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Registrations registration = new Registrations(
                        resultSet.getInt("RegisterID"),
                        resultSet.getInt("UserID"),
                        resultSet.getInt("SubjectID"),
                        resultSet.getInt("PackageID"),
                        resultSet.getBigDecimal("total_cost"),
                        resultSet.getInt("status"),
                        resultSet.getDate("valid_from"),
                        resultSet.getDate("valid_to"),
                        resultSet.getTimestamp("created_at")
                );
                list.add(registration);
            }
        } catch (Exception e) {
        }
        return list;

    }

    public void addRegistration(int userID, int subjectID, int packageID, BigDecimal totalCost, int status, Date validFrom, Date validTo, Timestamp createdAt) {
        try {
            String sql = "INSERT INTO Registrations (UserID, SubjectID, PackageID, total_cost, status, valid_from, valid_to, created_at) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
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

        }
    }

    public boolean deleteRegistration(int id) {
        String sql = "DELETE FROM Registrations WHERE RegisterID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (Exception e) {

        }
        return false;
    }

    public boolean updateRegistration(Registrations registration) {
        try {
            String sql = "UPDATE Registrations SET UserID = ?, SubjectID = ?, PackageID = ?, total_cost = ?, status = ? WHERE RegisterID = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, registration.getUserID());
            statement.setInt(2, registration.getSubjectID());
            statement.setInt(3, registration.getPackageID());
            statement.setBigDecimal(4, registration.getTotalCost());
            statement.setInt(5, registration.getStatus());
            statement.setInt(6, registration.getRegisterID());

            int rowsUpdated = statement.executeUpdate();
            return rowsUpdated > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
       

    public List<Registrations> filterRegistration(String property, int value) {
        List<Registrations> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Registrations WHERE " + property + " = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, value);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Registrations registration = new Registrations(
                        resultSet.getInt("RegisterID"),
                        resultSet.getInt("UserID"),
                        resultSet.getInt("SubjectID"),
                        resultSet.getInt("PackageID"),
                        resultSet.getBigDecimal("total_cost"),
                        resultSet.getInt("status"),
                        resultSet.getDate("valid_from"),
                        resultSet.getDate("valid_to"),
                        resultSet.getTimestamp("created_at")
                );
                list.add(registration);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String[] args) {
//        RegistrationDAO registrationsDBContext = new RegistrationDAO();
//        List<Registrations> registrations = registrationsDBContext.getAllRegistrations();
//        for (Registrations registration : registrations) {
//            System.out.println(registration);
//        }
        // Tạo một đối tượng RegistrationsDAO
//        RegistrationsDAO dao = new RegistrationsDAO();
//        
//        // Thêm một bản ghi mới vào bảng registrations
//        dao.addRegistration(1, 2, 4, 300.00, 2, "2024-05-25", "2024-05-31", "2024-05-25 12:32:11.000");
//        
//        // In ra thông báo khi thêm thành công
//        System.out.println("Added successfully!");
//
//        RegistrationDAO dao = new RegistrationDAO();
//
//        // Tạo các thông tin giả định cho việc thêm vào bảng
//        int userID = 1;
//        int subjectID = 2;
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
//        List<Registrations> registrations = dao.getAllRegistrations();
//        for (Registrations registration : registrations) {
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
//        registrations = dao.getAllRegistrations();
//        for (Registrations registration : registrations) {
//            System.out.println(registration);
//        }
        // Tạo một đối tượng RegistrationDAO
//    RegistrationDAO registrationDAO = new RegistrationDAO();
//
//    // Hiển thị thông tin trước khi cập nhật
//    System.out.println("Registrations before update:");
//    List<Registrations> registrationsBeforeUpdate = registrationDAO.getAllRegistrations();
//    for (Registrations registration : registrationsBeforeUpdate) {
//        System.out.println(registration);
//    }
//
//    // Tạo một đối tượng Registrations mới để cập nhật
//    Registrations registrationToUpdate = new Registrations();
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
//    System.out.println("Registrations after update:");
//    List<Registrations> registrationsAfterUpdate = registrationDAO.getAllRegistrations();
//    for (Registrations registration : registrationsAfterUpdate) {
//        System.out.println(registration);
//    }
     RegistrationDAO registrationDAO = new RegistrationDAO();

    // Test lấy tất cả các đăng ký
    List<Registrations> allRegistrations = registrationDAO.getAllRegistrations();
    System.out.println("All Registrations:");
    for (Registrations registration : allRegistrations) {
        System.out.println(registration);
    }

    // Test lọc đăng ký theo thuộc tính
    String propertyToFilter = "UserID"; // Đổi thành thuộc tính bạn muốn lọc (UserID, SubjectID, PackageID, hoặc Status)
    int valueToFilter = 1; // Đổi thành giá trị bạn muốn lọc
    List<Registrations> filteredRegistrations = registrationDAO.filterRegistration(propertyToFilter, valueToFilter);
    System.out.println("\nFiltered Registrations (by " + propertyToFilter + " = " + valueToFilter + "):");
    for (Registrations registration : filteredRegistrations) {
        System.out.println(registration);
    }
    }
}
