package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Mooc;

/**
 *
 * @author Admin
 */
public class MoocDAO extends DBContext {

    /**
     * @param args the command line arguments
     */
    public List<Mooc> getAllMooc() {
        List<Mooc> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Mooc";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Mooc mooc = new Mooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3));
                list.add(mooc);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Mooc> getMoocsBySubjectId(int sid) {
        List<Mooc> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Mooc WHERE SubjectID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, sid);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Mooc mooc = new Mooc(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3));
                list.add(mooc);
            }
        } catch (SQLException e) {
        }
        return list;
    }

    public static void main(String args[]) {
        MoocDAO mdao = new MoocDAO();
        List<Mooc> listm = mdao.getMoocsBySubjectId(1);
        for (Mooc mooc : listm) {
            System.out.println(mooc);
        }
    }
}
