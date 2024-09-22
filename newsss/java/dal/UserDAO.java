package dal;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Users;

public class UserDAO extends DBContext{

    public Users login(String code, String password) {
        String sql = "SELECT * FROM USERS WHERE UserID = ? AND Password = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, code);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Users user = new Users();
                user.setUserid(rs.getString("UserID"));
                user.setDisplayname(rs.getString("DisplayName"));
                user.setGender(rs.getBoolean("Gender"));
                user.setDatework(rs.getDate("DateWork"));
                user.setDob(rs.getDate("DOB"));
                user.setPassword(rs.getString("Password"));
                user.setTitle(rs.getString("Title"));
                user.setLevels(rs.getString("Levels"));
                user.setDepartment(rs.getString("Department"));
                user.setRoom(rs.getString("Room"));
                user.setGroup(rs.getString("Group"));
                user.setPodline(rs.getString("ProductionLine"));
                user.setStatus(rs.getString("Status"));
                user.setLocation(rs.getString("Location"));
                user.setPhonenumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setRole(rs.getInt("Role"));
                user.setAvatar(rs.getString("Avatar"));
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
            String sql = "SELECT * FROM USERS";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Users user = new Users();
                user.setUserid(rs.getString("UserID"));
                user.setDisplayname(rs.getString("DisplayName"));
                user.setGender(rs.getBoolean("Gender"));
                user.setDatework(rs.getDate("DateWork"));
                user.setDob(rs.getDate("DOB"));
                user.setPassword(rs.getString("Password"));
                user.setTitle(rs.getString("Title"));
                user.setLevels(rs.getString("Levels"));
                user.setDepartment(rs.getString("Department"));
                user.setRoom(rs.getString("Room"));
                user.setGroup(rs.getString("Group"));
                user.setPodline(rs.getString("ProductionLine"));
                user.setStatus(rs.getString("Status"));
                user.setLocation(rs.getString("Location"));
                user.setPhonenumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setRole(rs.getInt("Role"));
                user.setAvatar(rs.getString("Avatar"));
                list.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Users getUserByID(String userid) {
        String sql = "SELECT * FROM USERS WHERE UserID = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, userid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Users user = new Users();
                user.setUserid(rs.getString("UserID"));
                user.setDisplayname(rs.getString("DisplayName"));
                user.setGender(rs.getBoolean("Gender"));
                user.setDatework(rs.getDate("DateWork"));
                user.setDob(rs.getDate("DOB"));
                user.setPassword(rs.getString("Password"));
                user.setTitle(rs.getString("Title"));
                user.setLevels(rs.getString("Levels"));
                user.setDepartment(rs.getString("Department"));
                user.setRoom(rs.getString("Room"));
                user.setGroup(rs.getString("Group"));
                user.setPodline(rs.getString("ProductionLine"));
                user.setStatus(rs.getString("Status"));
                user.setLocation(rs.getString("Location"));
                user.setPhonenumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setRole(rs.getInt("Role"));
                user.setAvatar(rs.getString("Avatar"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return null;
    }

    public boolean checkEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Users user = new Users();
                user.setUserid(rs.getString("UserID"));
                user.setDisplayname(rs.getString("DisplayName"));
                user.setGender(rs.getBoolean("Gender"));
                user.setDatework(rs.getDate("DateWork"));
                user.setDob(rs.getDate("DOB"));
                user.setPassword(rs.getString("Password"));
                user.setTitle(rs.getString("Title"));
                user.setLevels(rs.getString("Levels"));
                user.setDepartment(rs.getString("Department"));
                user.setRoom(rs.getString("Room"));
                user.setGroup(rs.getString("Group"));
                user.setPodline(rs.getString("ProductionLine"));
                user.setStatus(rs.getString("Status"));
                user.setLocation(rs.getString("Location"));
                user.setPhonenumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setRole(rs.getInt("Role"));
                user.setAvatar(rs.getString("Avatar"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPhone(String phone) {
        String sql = "SELECT * FROM Users WHERE PhoneNumber = ?";
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                Users user = new Users();
                user.setUserid(rs.getString("UserID"));
                user.setDisplayname(rs.getString("DisplayName"));
                user.setGender(rs.getBoolean("Gender"));
                user.setDatework(rs.getDate("DateWork"));
                user.setDob(rs.getDate("DOB"));
                user.setPassword(rs.getString("Password"));
                user.setTitle(rs.getString("Title"));
                user.setLevels(rs.getString("Levels"));
                user.setDepartment(rs.getString("Department"));
                user.setRoom(rs.getString("Room"));
                user.setGroup(rs.getString("Group"));
                user.setPodline(rs.getString("ProductionLine"));
                user.setStatus(rs.getString("Status"));
                user.setLocation(rs.getString("Location"));
                user.setPhonenumber(rs.getString("PhoneNumber"));
                user.setEmail(rs.getString("Email"));
                user.setRole(rs.getInt("Role"));
                user.setAvatar(rs.getString("Avatar"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public void changeProfile(Users user) throws SQLException {
        String sql = "UPDATE [dbo].[USERS]\n" +
                "   SET [DisplayName] =?\n" +
                "      ,[Gender] = ?\n" +
                "      ,[DOB] = ?\n" +
                "      ,[Password] = ?\n" +
                "      ,[Location] = ?\n" +
                "      ,[PhoneNumber] = ?\n" +
                "      ,[Email] = ?\n" +
                "      ,[Avatar] = ?\n" +
                " WHERE UserID = ?";
        Connection connection = getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, user.getDisplayname());
        pst.setBoolean(2, user.isGender());
        pst.setDate(3, user.getDob());
        pst.setString(4, user.getPassword());
        pst.setString(5, user.getLocation());
        pst.setString(6, user.getPhonenumber());
        pst.setString(7, user.getEmail());
        pst.setString(8, user.getAvatar());
        pst.setString(9, user.getUserid());
        pst.executeUpdate();
    }

    public void changePass(Users user) throws SQLException {
        String sql = "UPDATE [dbo].[USERS]\n" +
                "   SET [Password] = ?\n" +
                " WHERE UserID = ?";
        Connection connection = getConnection();
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, user.getPassword());
        pst.setString(2, user.getUserid());
        pst.executeUpdate();
    }

    public static void main(String[] args) {
        UserDAO dao = new UserDAO();
//        Users user = dao.login("0099", "04081983");
//        if (user != null) {
//            System.out.println("Login successfully");
//        } else {
//            System.out.println("Login failed");
//        }
         List<Users> listu = dao.getAllUsers();
         for (Users u : listu) {
             System.out.println(u);
         }
//        Users user = dao.getUserByID("S00012");
//        System.out.println(user);
    }
}
