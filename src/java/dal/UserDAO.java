package dal;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Role;
import model.Teacher;
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

    public User checkLogin(String nameOrEmail, String pass) {
        DBContext db = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            db = new DBContext();
            String sql = "SELECT * FROM Users WHERE (UserName = ? AND Password = ?) OR (Email = ? AND Password = ?)";
            pst = db.connection.prepareStatement(sql);
            pst.setString(1, nameOrEmail);  // For UserName
            pst.setString(2, pass);         // For Password
            pst.setString(3, nameOrEmail);  // For Email
            pst.setString(4, pass);         // For Password
            rs = pst.executeQuery();

            if (rs.next()) {
                return new User(rs.getInt("UserID"),
                        rs.getString("FullName"),
                        rs.getString("UserName"),
                        rs.getDate("DateOfBirth"),
                        rs.getString("Email"),
                        rs.getString("Password"),
                        rs.getString("Phone"),
                        rs.getString("Address"),
                        rs.getBoolean("Gender"),
                        rs.getInt("RoleID"),
                        rs.getString("Avatar"),
                        rs.getDate("Create_at"));
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

    public User checkLoginWithGoogle(String email, String id) {
        DBContext db = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        try {
            db = new DBContext();
            String sql = "SELECT * FROM Users WHERE Email = ? AND Password = ?";
            pst = db.connection.prepareStatement(sql);
            pst.setString(1, email);
            pst.setString(2, id);
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

    public User checkEmailExits(String email) {
        String sql = "select * from Users \n"
                + "where Email = ?\n";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getBoolean(9), rs.getInt(10), rs.getString(11), rs.getDate(12));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public User checkAccountExits(String user) {
        String sql = "select * from Users \n"
                + "where UserName = ?\n";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, user);

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getDate(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(8), rs.getBoolean(9), rs.getInt(10), rs.getString(11), rs.getDate(12));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void CreateAccWithGoogle(String email, String id, java.sql.Date createAt) {
        String sql = "INSERT INTO [dbo].[Users]\n"
                + "           ([Email]\n"
                + "           ,[Password]\n"
                + "           ,[RoleID]\n"
                + "           ,[Create_at])\n"
                + "     VALUES\n"
                + "           (?,?,?,?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1, email);
            ps.setString(2, id);
            ps.setInt(3, 2);
            ps.setDate(4, createAt);

            ps.executeUpdate();

        } catch (Exception e) {

        }
    }

    public int insertUserByGoogle(String name, String email, String avatar) {
        int check = 0;

        try {
            final String sql = "INSERT INTO [Users]\n"
                    + "( FullName, Email, Gender, RoleID, Avatar)\n"
                    + "VALUES( ?, ?, 0, 0, ?,1);";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, name);
            st.setString(2, email);
            st.setString(3, avatar);
            check = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }

    public int setPasswordForGoogleAccount(int userId, String password) {
        int check = 0;
        try {
            final String sql = "UPDATE [Users]\n"
                    + "SET  Password=?\n"
                    + "WHERE UserID =?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, password);
            st.setInt(2, userId);
            check = st.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return check;
    }

    public boolean CreateAccount(String fullname, String username, String email, String dob, String password, boolean gender, java.sql.Date create_At) {
        try {
            DBContext db = new DBContext();
            String sql = "INSERT INTO [dbo].[Users]\n"
                    + "           ([FullName]\n"
                    + "           ,[UserName]\n"
                    + "           ,[DateOfBirth]\n"
                    + "           ,[Email]\n"
                    + "           ,[Password]\n"
                    + "           ,[Gender]\n"
                    + "           ,[RoleID]\n"
                    + "           ,[Create_at])\n"
                    + "     VALUES\n"
                    + "           (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = db.connection.prepareStatement(sql);
            pst.setString(1, fullname);
            pst.setString(2, username);
            pst.setString(3, dob);
            pst.setString(4, email);
            pst.setString(5, password);
            pst.setBoolean(6, gender);
            pst.setInt(7, 2); // Default role for normal users
            pst.setDate(8, create_At);
            pst.executeUpdate();
            return true;
        } catch (Exception e) {
            return false;
        }

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
            String sql = "SELECT u.UserID, u.RoleId, u.FullName, u.UserName, u.Email, \n"
                    + "                    u.Password, u.Gender, u.Create_at, r.role_name \n"
                    + "                    FROM Users u \n"
                    + "                    INNER JOIN Roles r ON r.RoleID = u.RoleID\n"
                    + "                    where r.role_name = ?";
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

    public User getByUid(int id) {
        try {
            String sql = "SELECT * FROM Users WHERE UserID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new User(rs.getInt(1),
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
            }
        } catch (SQLException e) {
        }
        return null;
    }

    /*---------------------------------------------------------------------- Teacher ---------------------------------------------------------- */
    public List<Teacher> getAllTeacher() {
        List<Teacher> list = new ArrayList<>();
        try {
            String sql = "SELECT t.*, u.FullName FROM Teacher t\n"
                    + "JOIN Users u ON u.UserID = t.UserID";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Teacher user = new Teacher(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getString(8));
                list.add(user);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public void changeProfile(User user) throws SQLException {
        String sql = "UPDATE Users "
                + "SET FullName = ?, "
                + "    UserName = ?, "
                + "    DateOfBirth = ?, "
                + "    Email = ?, "
                + "    Phone = ?, "
                + "    Address = ?, "
                + "    Gender = ?, "
                + "    RoleID = ?, "
                + "    Avatar = ? "
                + "WHERE UserID = ?";

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {

            pstmt.setString(1, user.getFullname());
            pstmt.setString(2, user.getUsername());
            pstmt.setDate(3, new Date(user.getDob().getTime()));
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getPhone());
            pstmt.setString(6, user.getAddress());
            pstmt.setBoolean(7, user.isGender());
            pstmt.setInt(8, user.getRoleId());
            pstmt.setString(9, user.getAvatar());
            pstmt.setInt(10, user.getId());

            pstmt.executeUpdate();
        } catch (SQLException ex) {
            throw new SQLException("Error updating user profile: " + ex.getMessage());
        }
    }

    public void changePass(User acc) throws SQLException {
        String sql = "UPDATE Users SET Password = ? WHERE UserName = ?";
        PreparedStatement stmt = connection.prepareStatement(sql);
        stmt.setString(1, acc.getPassword());
        stmt.setString(2, acc.getUsername());
        stmt.executeUpdate();
    }

    public boolean checkUsername(String username) {
        String sql = "SELECT * FROM Users WHERE UserName = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setUsername(rs.getString("UserName"));
                user.setFullname(rs.getString("FullName"));
                user.setDob(rs.getDate("DateOfBirth"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setPhone(rs.getString("Phone"));
                user.setAddress(rs.getString("Address"));
                user.setGender(rs.getBoolean("Gender"));
                user.setRoleId(rs.getInt("RoleID"));
                user.setAvatar(rs.getString("Avatar"));
                user.setCreateAt(rs.getDate("Create_at"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkEmail(String email) {
        String sql = "SELECT * FROM Users WHERE Email = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setUsername(rs.getString("UserName"));
                user.setFullname(rs.getString("FullName"));
                user.setDob(rs.getDate("DateOfBirth"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setPhone(rs.getString("Phone"));
                user.setAddress(rs.getString("Address"));
                user.setGender(rs.getBoolean("Gender"));
                user.setRoleId(rs.getInt("RoleID"));
                user.setAvatar(rs.getString("Avatar"));
                user.setCreateAt(rs.getDate("Create_at"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean checkPhone(String phone) {
        String sql = "SELECT * FROM Users WHERE Phone = ?";
        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, phone);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("UserID"));
                user.setUsername(rs.getString("UserName"));
                user.setFullname(rs.getString("FullName"));
                user.setDob(rs.getDate("DateOfBirth"));
                user.setEmail(rs.getString("Email"));
                user.setPassword(rs.getString("Password"));
                user.setPhone(rs.getString("Phone"));
                user.setAddress(rs.getString("Address"));
                user.setGender(rs.getBoolean("Gender"));
                user.setRoleId(rs.getInt("RoleID"));
                user.setAvatar(rs.getString("Avatar"));
                user.setCreateAt(rs.getDate("Create_at"));
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* -------------------------------------------------------------------- Teacher -------------------------------------------------------------- */
    public List<Teacher> getTeacher() {
        List<Teacher> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM [Teacher]";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Teacher t = new Teacher(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7));
                list.add(t);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public Teacher getTeacherById(int userId) {
        try {
            String sql = "SELECT t.*, u.FullName FROM Teacher t\n"
                    + "JOIN Users u ON u.UserID = t.UserID WHERE t.UserID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, userId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return new Teacher(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getDate(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9));
            }
        } catch (Exception e) {
        }
        return null;
    }

    public static void main(String args[]) {
        // TODO code application logic here
        UserDAO udao = new UserDAO();
        List<User> Listu = udao.getAllUser();
        for (User user : Listu) {
            System.out.println(user);
        }
//        User u = udao.checkLogin("phamchinh", "123456");
//        if (u != null) {
//            System.out.println("Login success");
//        } else {
//            System.out.println("Login Failed");
//        }
//        List<User> listu = udao.getAllUser();
//        for (User user : listu) {
//            System.out.println(user);
//        }

//        List<Role> listu = udao.getAllRole();
//        for (Role r : listu) {
//            System.out.println(r);
//        }
    }

}
