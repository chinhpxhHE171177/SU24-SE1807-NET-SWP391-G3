package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.User;
import java.sql.Date;

/**
 *
 * @author Admin
 */
public class UserDAO extends DBContext {

    Connection conn = null;
    PreparedStatement ps = null;
    ResultSet rs = null;

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

    public List<User> pagingUser(int index) {
        List<User> list = new ArrayList<>();

        String query = "select * from Users \n"
                + "order by UserID\n"
                + "OFFSET ? Rows fetch next 5 rows only ;";
        try {
            PreparedStatement pst = connection.prepareStatement(query);
            pst.setInt(1, (index - 1) * 5);
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

    public int getTotalUser() {
        String query = "select count (*) from Users";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getInt(1);
            }

        } catch (Exception e) {
        }

        return 0;
    }

    public void insertUser(User u) {
        String query = "INSERT INTO [dbo].[Users]\n"
                + "           ([FullName]\n"
                + "           ,[UserName]\n"
                + "           ,[DateOfBirth]\n"
                + "           ,[Email]\n"
                + "           ,[Password]\n"
                + "           ,[Phone]\n"
                + "           ,[Address]\n"
                + "           ,[Gender]\n"
                + "           ,[RoleID]\n"
                + "           ,[Avatar]\n"
                + "           ,[Create_at])\n"
                + "     VALUES\n"
                + "           (?,?,?,?,?,?,?,?,?,?,?)";
        try {
            conn = new DBContext().connection;
            ps = conn.prepareStatement(query);
            ps.setString(1,u.getFullname());
            ps.setString(2,u.getUsername() );
            ps.setDate(3,(Date)u.getDob());
            ps.setString(4,u.getEmail());
            ps.setString(5, u.getPassword());
            ps.setString(6, u.getPhone());
            ps.setString(7, u.getAddress());
            ps.setBoolean(8,u.isGender() );
            ps.setInt(9,u.getRoleId() );
            ps.setString(10, u.getAvatar());
            ps.setDate(11, (Date) u.getCreateAt());
            ps.executeUpdate();
            System.out.println("add");
        } catch (Exception e) {
        }
    }

    public static void main(String args[]) {
        // TODO code application logic here
        UserDAO udao = new UserDAO();
        List<User> listu = udao.getAllUser();
        for (User user : listu) {
            System.out.println(user);
        }
    }
}
