package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Users;

public class UserDAO extends DBContext{

    public Users login(String code, String password) {
        String sql = "SELECT * FROM USERS WHERE UserCode = ? AND Password = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, code);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("UserID"));
                user.setCode(rs.getString("UserCode"));
                user.setFullName(rs.getString("FullName"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setAddress(rs.getString("Address"));
                user.setDob(rs.getDate("DateOfBirth"));;
                user.setAvatar(rs.getString("Avatar"));
                user.setRoleId(rs.getInt("RoleID"));
                user.setRoomId(rs.getInt("RoomId"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Users> getAllUsers () {
        List<Users> list = new ArrayList<>();
        try {
            String sql = "SELECT u.*, r.RoleName FROM USERS u\n" +
                    "JOIN Roles r ON r.RoleID = u.RoleID";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Users user = new Users();
                user.setId(rs.getInt("UserID"));
                user.setCode(rs.getString("UserCode"));
                user.setFullName(rs.getString("FullName"));
                user.setPassword(rs.getString("Password"));
                user.setEmail(rs.getString("Email"));
                user.setPhone(rs.getString("Phone"));
                user.setAddress(rs.getString("Address"));
                user.setDob(rs.getDate("DateOfBirth"));
                user.setAvatar(rs.getString("Avatar"));
                user.setRoleId(rs.getInt("RoleID"));
                user.setRoomId(rs.getInt("RoomId"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Users> getUsers(int pageIndex, int pageSize) {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT u.*, r.RoleName, d.DepartmentName, rm.RoomName, l.LineName FROM Users u\n" +
                "LEFT JOIN Roles r ON r.RoleID = u.RoleID\n" +
                "JOIN Departments d On d.DepartmentID = u.DepartmentID\n" +
                "JOIN Rooms rm ON  rm.RoomID = u.RoomID\n" +
                "JOIN ProductionLines l ON l.LineID = u.LineID\n" +
                "GROUP BY u.UserID, u.UserCode, u.FullName, u.Gender, u.Password, u.Position,\n" +
                "u.Level, u.DepartmentID, u.RoomID, u.LineID, u.RoleID, u.createDate, u.roleDate, r.RoleName,\n" +
                "d.DepartmentName, rm.RoomName, l.LineName\n" +
                "ORDER BY u.createDate desc\n" +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (pageIndex - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                	 Users user = new Users();
                     user.setId(rs.getInt("UserID"));
                     user.setCode(rs.getString("UserCode"));
                     user.setFullName(rs.getString("FullName"));
                     user.setPassword(rs.getString("Password"));
                     user.setEmail(rs.getString("Email"));
                     user.setPhone(rs.getString("Phone"));
                     user.setAddress(rs.getString("Address"));
                     user.setDob(rs.getDate("DateOfBirth"));
                     user.setAvatar(rs.getString("Avatar"));
                     user.setRoleId(rs.getInt("RoleID"));
                     user.setRoomId(rs.getInt("RoomId"));
                     list.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Users> getUserRole(int pageIndex, int pageSize) {
        List<Users> list = new ArrayList<>();
        String sql = "SELECT u.*, r.RoleName, d.DepartmentName, rm.RoomName, l.LineName FROM Users u\n" +
                "JOIN Roles r ON r.RoleID = u.RoleID\n" +
                "JOIN Departments d On d.DepartmentID = u.DepartmentID\n" +
                "JOIN Rooms rm ON  rm.RoomID = u.RoomID\n" +
                "JOIN ProductionLines l ON l.LineID = u.LineID\n" +
                "GROUP BY u.UserID, u.UserCode, u.FullName, u.Gender, u.Password, u.Position,\n" +
                "u.Level, u.DepartmentID, u.RoomID, u.LineID, u.RoleID, u.createDate, u.roleDate, r.RoleName,\n" +
                "d.DepartmentName, rm.RoomName, l.LineName\n" +
                "ORDER BY u.createDate desc\n" +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (pageIndex - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                	 Users user = new Users();
                     user.setId(rs.getInt("UserID"));
                     user.setCode(rs.getString("UserCode"));
                     user.setFullName(rs.getString("FullName"));
                     user.setPassword(rs.getString("Password"));
                     user.setEmail(rs.getString("Email"));
                     user.setPhone(rs.getString("Phone"));
                     user.setAddress(rs.getString("Address"));
                     user.setDob(rs.getDate("DateOfBirth"));
                     user.setAvatar(rs.getString("Avatar"));
                     user.setRoleId(rs.getInt("RoleID"));
                     user.setRoomId(rs.getInt("RoomId"));
                     list.add(user);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Users getUserByCode(String userCode) {
        String sql = "SELECT * FROM USERS WHERE UserCode = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, userCode);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
            	 Users user = new Users();
                 user.setId(rs.getInt("UserID"));
                 user.setCode(rs.getString("UserCode"));
                 user.setFullName(rs.getString("FullName"));
                 user.setPassword(rs.getString("Password"));
                 user.setEmail(rs.getString("Email"));
                 user.setPhone(rs.getString("Phone"));
                 user.setAddress(rs.getString("Address"));
                 user.setDob(rs.getDate("DateOfBirth"));
                 user.setAvatar(rs.getString("Avatar"));
                 user.setRoleId(rs.getInt("RoleID"));
                 user.setRoomId(rs.getInt("RoomId"));
                 
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public void editUser(Users user) throws SQLException {
//        String sql = "UPDATE [dbo].[Users]\n" +
//                "   SET [FullName] = ?\n" +
//                "      ,[Gender] = ?\n" +
//                "      ,[Position] = ?\n" +
//                "      ,[Level] = ?\n" +
//                "      ,[DepartmentID] = ?\n" +
//                "      ,[RoomID] = ?\n" +
//                "      ,[LineID] = ?\n" +
//                " WHERE UserCode = ?";
//        Connection connection = getConnection();
//        PreparedStatement pst = connection.prepareStatement(sql);
//        pst.setString(1, user.getFullName());
//        pst.setString(2, user.getGender());
//        pst.setString(3, user.getPosition());
//        pst.setString(4, user.getLevel());
//        pst.setInt(5, user.getDepartmentId());
//        pst.setInt(6, user.getRoomId());
//        pst.setInt(7, user.getLineId());
//        pst.setString(8,user.getCode());
//        pst.executeUpdate();
//    }

    public void changePass(Users user) throws SQLException {
        String sql = "UPDATE [dbo].[USERS]\n" +
                "   SET [Password] = ?\n" +
                " WHERE UserCode = ?";
        Connection connection = getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, user.getPassword());
        pst.setString(2, user.getCode());
        pst.executeUpdate();
    }

    public int getTotalUserCount() {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM USERS";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public List<Users> userSearch(String userCode, Integer depId, Integer roomId, Integer lineId, String sortByDate, int pageIndex, int pageSize) throws SQLException {
        List<Users> list = new ArrayList<>();
        String query = "SELECT u.*, r.RoleName, d.DepartmentName, rm.RoomName, l.LineName FROM Users u\n" +
                "LEFT JOIN Roles r ON r.RoleID = u.RoleID\n" +
                "JOIN Departments d On d.DepartmentID = u.DepartmentID\n" +
                "JOIN Rooms rm ON  rm.RoomID = u.RoomID\n" +
                "JOIN ProductionLines l ON l.LineID = u.LineID\n" +
                "WHERE 1=1";

        if (userCode != null && !userCode.isEmpty()) {
            query += " AND u.UserCode = ?";
        }
        if (depId != null) {
            query += " AND u.DepartmentID = ?";
        }
        if (roomId != null) {
            query += " AND u.RoomID = ?";
        }
        if (lineId != null) {
            query += " AND u.LineID = ?";
        }
        if ("asc".equalsIgnoreCase(sortByDate)) {
            query += " ORDER BY u.createDate ASC";
        } else {
            query += " ORDER BY u.createDate DESC";
        }

        query += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            int index = 1;

            // Set parameters dynamically
            if (userCode != null && !userCode.isEmpty()) {
                stmt.setString(index++, userCode);
            }
            if (depId != null) {
                stmt.setInt(index++, depId);
            }
            if (roomId != null) {
                stmt.setInt(index++, roomId);
            }
            if (lineId != null) {
                stmt.setInt(index++, lineId);
            }

            stmt.setInt(index++, (pageIndex - 1) * pageSize);
            stmt.setInt(index++, pageSize);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	 Users user = new Users();
                 user.setId(rs.getInt("UserID"));
                 user.setCode(rs.getString("UserCode"));
                 user.setFullName(rs.getString("FullName"));
                 user.setPassword(rs.getString("Password"));
                 user.setEmail(rs.getString("Email"));
                 user.setPhone(rs.getString("Phone"));
                 user.setAddress(rs.getString("Address"));
                 user.setDob(rs.getDate("DateOfBirth"));
                 user.setAvatar(rs.getString("Avatar"));
                 user.setRoleId(rs.getInt("RoleID"));
                 user.setRoomId(rs.getInt("RoomId"));
                 list.add(user);
            }
        }

        return list;
    }

    public List<Users> userRoleSearch(String userCode, Integer roleId, int pageIndex, int pageSize) throws SQLException {
        List<Users> list = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT u.UserID, u.UserCode, u.FullName, u.RoleID, u.roleDate, r.RoleName FROM Users u ")
                .append("JOIN Roles r ON r.RoleID = u.RoleID ")
                .append("WHERE 1=1 ");

        if (userCode != null && !userCode.isEmpty()) {
            query.append(" AND u.UserCode = ?");
        }
        if (roleId != null) {
            query.append(" AND u.RoleID = ?");
        }

        // Add ORDER BY clause before OFFSET
        query.append(" ORDER BY u.roleDate ")
                .append("OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query.toString())) {
            int index = 1;

            // Set parameters based on userCode
            if (userCode != null && !userCode.isEmpty()) {
                stmt.setString(index++, userCode);
            }
            // Set parameters based on roleId
            if (roleId != null) {
                stmt.setInt(index++, roleId);
            }

            // Set pagination parameters
            stmt.setInt(index++, (pageIndex - 1) * pageSize);
            stmt.setInt(index++, pageSize);

            // Execute the query and process results
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                	 Users user = new Users();
                     user.setId(rs.getInt("UserID"));
                     user.setCode(rs.getString("UserCode"));
                     user.setFullName(rs.getString("FullName"));
                     user.setPassword(rs.getString("Password"));
                     user.setEmail(rs.getString("Email"));
                     user.setPhone(rs.getString("Phone"));
                     user.setAddress(rs.getString("Address"));
                     user.setDob(rs.getDate("DateOfBirth"));
                     user.setAvatar(rs.getString("Avatar"));
                     user.setRoleId(rs.getInt("RoleID"));
                     user.setRoomId(rs.getInt("RoomId"));
                    list.add(user);
                }
            }
        }

        return list;
    }

    public boolean userExists(String userCode) {
        String query = "SELECT COUNT(*) FROM USERS WHERE UserCode = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if it exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Not found
    }

    public boolean userRoleExists(String userCode) {
        String query = "SELECT COUNT(*) FROM USERS WHERE UserCode = ? AND RoleID IN(1,2)";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, userCode);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if it exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Not found
    }

//    public void addUser(Users user) {
//        String sql = "INSERT INTO [dbo].[Users] " +
//                "([UserCode], [FullName], [Gender], [Password], [Position], [Level], [DepartmentID], [RoomID], [LineID], [createDate]) " +
//                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
//
//        try (Connection connection = getConnection();
//             PreparedStatement pst = connection.prepareStatement(sql)) {
//
//            pst.setString(1, user.getCode());
//            pst.setString(2, user.getFullName());
//            pst.setString(3, user.getGender());
//            pst.setString(4, user.getPassword());
//            pst.setString(5, user.getPosition());
//            pst.setString(6, user.getLevel());
//            pst.setInt(7, user.getDepartmentId());
//            pst.setInt(8, user.getRoomId());
//            pst.setInt(9, user.getLineId());
//            pst.setTimestamp(10, user.getCreateDate());
//
//            pst.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace(); // Debugging line
//            // Optionally add a meaningful error message to the response
//        }
//    }

    public void addUserRole(Users user) throws SQLException {
        String sql = "UPDATE Users SET RoleID = ?, roleDate = GETDATE() \n" +
                "WHERE UserCode = ?;";

        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, user.getRoleId());
            stmt.setString(2, user.getCode());
            stmt.executeUpdate();
        }
    }

    public void deleteUser(String id) {
        String sql = "DELETE FROM USERS WHERE UserID = ?";

        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void updateUser(Users user) throws SQLException {
        String sql = "UPDATE [dbo].[Users]\n" +
                "   SET [FullName] = ?\n" +
                "      ,[Gender] = ?\n" +
                "      ,[Password] = ?\n" +
                "      ,[Position] = ?\n" +
                "      ,[Level] = ?\n" +
                "      ,[DepartmentID] = ?\n" +
                "      ,[RoomID] = ?\n" +
                "      ,[LineID] = ?\n" +
                "      ,[RoleID] = ?\n" +
                " WHERE UserCode = ?";

        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, user.getFullName());
            stmt.setString(2, user.getGender());
            stmt.setString(3, user.getPassword());
            stmt.setInt(7, user.getRoomId());
            stmt.setInt(9, user.getRoleId());
            stmt.setString(10, user.getCode());
            stmt.executeUpdate();
        }
    }

    public void deleteUserRole(String userCode) {
        String sql = "Update Users SET RoleID = NULL WHERE UserCode = ?";

        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, userCode);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
//        Users user = dao.login("0099", "04081983");
//        if (user != null) {
//            System.out.println("Login successfully");
//        } else {
//            System.out.println("Login failed");
//        }
         List<Users> listu = dao.getUsers(1, 10);
         for (Users u : listu) {
             System.out.println(u);
         }
//        Users user = dao.getUId("S00012");
//        System.out.println(user);

//        int count = dao.getTotalUserCount();
//        System.out.println(count);
    }
}
