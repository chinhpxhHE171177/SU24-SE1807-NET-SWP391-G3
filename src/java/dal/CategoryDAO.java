package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Category;

/**
 *
 * @author Admin
 */
public class CategoryDAO extends DBContext {

    /**
     * @param args the command line arguments
     */
    
    public List<Category> getAllCategory() {
        List<Category> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Categories";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Category category = new Category(rs.getInt(1), rs.getString(2));
                list.add(category);
            }
        } catch (SQLException e) {
        }
        return list;
    }
    
    

    public static void main(String args[]) {
        // TODO code application logic here
        CategoryDAO cdao = new CategoryDAO();
        List<Category> listca = cdao.getAllCategory();
        for (Category category : listca) {
            System.out.println(category);
        }
    }
}
