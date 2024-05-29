package dal;

import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.Ratings;

/**
 *
 * @author Admin
 */
public class RatingDAO extends DBContext {

    /**
     * @param args the command line arguments
     */
    public List<Ratings> getAllRating() {
        List<Ratings> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Ratings";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Ratings r = new Ratings(rs.getInt(1),
                        rs.getInt(2),
                        rs.getString(3),
                        rs.getString(4));
                list.add(r);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public static void main(String args[]) {
        // TODO code application logic here
        RatingDAO rdao = new RatingDAO();
        List<Ratings> lists = rdao.getAllRating();
        for (Ratings list : lists) {
            System.out.println(list);
        }
    }
}
