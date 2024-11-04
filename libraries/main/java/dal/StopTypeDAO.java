package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import model.StopType;

public class StopTypeDAO extends DBContext {
	
	public List<StopType> getAlls() {
        List<StopType> list = new ArrayList<>();

        String query = "SELECT * FROM StopType";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    StopType eh = new StopType();

                    // Extract data from result set
                    eh.setId(resultSet.getInt("TypeID"));
                    eh.setName(resultSet.getString("TypeName"));
                    list.add(eh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
	public int getTypeIDByTypeName(String name) {
        int stageID = -1;
        String sql = "SELECT TypeID FROM StopType WHERE TypeName = ? ";
        
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setString(1, name);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    stageID = rs.getInt("TypeID"); 
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
        return stageID;
    }

	
	public static void main(String[] args) {
        StopTypeDAO dao = new StopTypeDAO();
        Integer id = dao.getTypeIDByTypeName("Dừng ngắn");
       System.out.print(id);
    }

}
