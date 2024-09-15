package dal;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import model.Reply;

/**
 *
 * @author Admin
 */
public class ReplyDAO extends DBContext {

    /**
     * @param args the command line arguments
     */
    public List<Reply> getReplyByRatingId(int id) {
        List<Reply> list = new ArrayList<>();
        try {
            String sql = "SELECT r.*, u.FullName, u.Avatar FROM Reply r\n"
                    + "JOIN Users u ON u.UserID = r.UserID\n"
                    + "WHERE RatingID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Reply reply = new Reply(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getTimestamp(5),
                        rs.getBoolean(6),
                        rs.getInt(7),
                        rs.getString(8),
                        rs.getString(9));
                list.add(reply);
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }
        return list;
    }

    public void updateComment(int id, String comment) {
        String sql = "UPDATE Reply SET Content = ? WHERE ReplyID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, comment);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComment(int id) {
        String sql = "DELETE FROM [dbo].[Reply] WHERE ReplyID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ReplyDAO redao = new ReplyDAO();
        List<Reply> list = redao.getReplyByRatingId(1);
        for (Reply reply : list) {
            System.out.println(reply);
        }
    }
}
