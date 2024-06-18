package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.Role;
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

    public List<Role> getAllRole() {
        List<Role> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM [Roles]";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Role r = new Role(rs.getInt(1), rs.getString(2), rs.getString(3));

                list.add(r);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public User checkLogin(String name, String pass) {
        DBContext db = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            db = new DBContext();
            String sql = "SELECT * FROM Users WHERE UserName = ? AND Password = ?";
            pst = db.connection.prepareStatement(sql);
            pst.setString(1, name);
            pst.setString(2, pass);
            rs = pst.executeQuery();

            if (rs.next()) {
                User u = new User(rs.getInt("UserID"),
                        rs.getString("FullName"),
                        rs.getString("UserName"),
                        rs.getDate(4),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Phone"),
                        rs.getString("Address"),
                        rs.getBoolean("Gender"),
                        rs.getInt("RoleID"),
                        rs.getString("Avatar"),
                        rs.getDate("Create_at"));

                return u;
            } else {
                System.out.println("No user found with the provided credentials.");
            }
        } catch (Exception e) {
            e.printStackTrace();  // Log the exception for debugging purposes
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pst != null) {
                    pst.close();
                }
                if (db != null && db.connection != null) {
                    db.connection.close();
                }
            } catch (Exception e) {
                e.printStackTrace();  // Log any potential exceptions during resource closing
            }
        }
        return null;
    }

    public static void main(String args[]) {
        // TODO code application logic here
        UserDAO udao = new UserDAO();
//        List<User> listu = udao.getAllUser();
//        for (User user : listu) {
//            System.out.println(user);
//        }

        List<Role> listu = udao.getAllRole();
        for (Role r : listu) {
            System.out.println(r);
        }
    }
}
