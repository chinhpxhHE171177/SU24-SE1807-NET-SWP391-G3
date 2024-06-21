package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import model.User;

/**
 *
 * @author Admin
 */
public class UserDAO extends DBContext {

    /**
     * @param args the command line arguments
     */
    public List<User> getAllUser() {
        List<User> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM [Users]";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User user = new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getBoolean(9),
                        rs.getInt(10),
                        rs.getString(11),
                        rs.getDate(12));
                list.add(user);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<User> ManaAccount() {
        List<User> list = new ArrayList<>();
        try {
            String sql = "SELECT u.UserID, u.RoleId, u.FullName, u.UserName, u.DateOfBirth, u.Email, "
                    + "u.Password, u.Phone, u.Address, u.Gender, u.Avatar, u.Create_at, r.role_name "
                    + "FROM Users u "
                    + "INNER JOIN Roles r ON r.RoleID = u.RoleID";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User Acc = new User(
                        rs.getInt("UserID"),
                        rs.getString("FullName"),
                        rs.getString("UserName"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Phone"),
                        rs.getString("Address"),
                        rs.getBoolean("Gender"),
                        rs.getInt("RoleId"),
                        rs.getString("Avatar"),
                        rs.getTimestamp("Create_at"),
                        rs.getString("role_name"));
                list.add(Acc);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void AddAccount(String username, String password, int roleId, String email, Boolean gender, Date createAt) {
        try {
            String sql = "INSERT INTO Users (UserName, Password, RoleID, Email,Gender,Create_at)\n"
                    + "   VALUES (?,?,?,?,?,?);";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, roleId);
            statement.setString(4, email);
            statement.setBoolean(5, gender);
            statement.setDate(6, new java.sql.Date(createAt.getTime()));
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new acc was inserted successfully!");
            }
        } catch (Exception e) {
            System.out.println("cut roi");
        }
    }

    public void deleteAccount(int id) {

        try {
            String sql = "DELETE FROM Users WHERE UserID = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, id);
            stmt.execute();

        } catch (Exception e) {

        }
    }

    public ArrayList<User> getAllRoleNameforUser() {
        ArrayList<User> list = new ArrayList<>();
        try {
            String sql = "select r.role_name from Roles r";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getString(1)));
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<User> getAccountFlowingRole(String Role) {
        ArrayList<User> list = new ArrayList<>();
        try {
            String sql = "SELECT u.UserID, u.RoleId, u.FullName, u.UserName, u.Email, \n" +
"                    u.Password, u.Gender, u.Create_at, r.role_name \n" +
"                    FROM Users u \n" +
"                    INNER JOIN Roles r ON r.RoleID = u.RoleID\n" +
"                    where r.role_name = ?";    
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, Role);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),                
                        rs.getBoolean(6),
                        rs.getInt(7),
                        rs.getDate(8),
                        rs.getString(9)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void main(String args[]) {

        // TODO code application logic here
        UserDAO userDAO = new UserDAO();

        // Test getAccountFlowingRole method
        String roleToTest = "Admin"; // Change this to the role you want to test
        ArrayList<User> users = userDAO.getAccountFlowingRole(roleToTest);

        // Print the result
        for (User user : users) {
            System.out.println(user);
        }
//        List<User> lista = udao.ManaAccount();
//        for (User mana : lista) {
//            System.out.println(mana);
//        }
//
//        try {
//
//            String username = "testuser";
//            String password = "testpassword";
//            int roleId = 1;
//            String email = "testuser@example.com";
//            boolean gender = true;
//            Date createAt = new Date(System.currentTimeMillis());
//            udao.AddAccount(username, password, roleId, email, gender, createAt);
//        } catch (Exception e) {
//            System.out.println("xong");
//        }

    }
}
