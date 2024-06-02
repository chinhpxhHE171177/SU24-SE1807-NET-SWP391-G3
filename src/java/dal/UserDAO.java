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
    
    public static void main(String args[]) {
        // TODO code application logic here
        UserDAO udao = new UserDAO();
        List<User> listu = udao.getAllUser();
        for (User user : listu) {
            System.out.println(user);
        }
    }
}
