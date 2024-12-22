package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import model.CodeApi;

public class CodeApiDAO extends DBContext {
	
	public List<CodeApi> getAlls(){
        List<CodeApi> list = new ArrayList<>();
        String query = "SELECT ca.*, c.name, c.lineId, l.lineName FROM CodeApi ca \r\n"
        		+ "JOIN Codes c ON ca.codeId = c.id\r\n"
        		+ "LEFT JOIN Lines l ON c.lineId = l.lineId";
        try {
             Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idApi");
                String code = rs.getString("code");
                String image = rs.getString("image");
                Timestamp createdDate = rs.getTimestamp("createdDate");
                int idCode = rs.getInt("codeId");
                String name = rs.getString("name");
                int lineId = rs.getInt("lineId");
                String lineName = rs.getString("lineName");
                list.add(new CodeApi(id, code, name, lineId, lineName, image, createdDate, idCode));
            }
        } catch (Exception e) {
			e.getStackTrace();
		}
        return list;
    }
	
	
	public CodeApi getByApi(){
        String query = "SELECT ca.*, c.name, c.lineId, l.lineName FROM CodeApi ca \r\n"
        		+ "JOIN Codes c ON ca.codeId = c.id\r\n"
        		+ "LEFT JOIN Lines l ON c.lineId = l.lineId";
        try {
             Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("idApi");
                String code = rs.getString("code");
                String image = rs.getString("image");
                Timestamp createdDate = rs.getTimestamp("createdDate");
                int idCode = rs.getInt("codeId");
                String name = rs.getString("name");
                int lineId = rs.getInt("lineId");
                String lineName = rs.getString("lineName");
                return new CodeApi(id, code, name, lineId, lineName, image, createdDate, idCode);
            }
        } catch (Exception e) {
			e.getStackTrace();
		}
        return null;
    }
	
	
	// Kiểm tra bảng CodeAPI có dữ liệu không
    public boolean isEmpty() {
        String query = "SELECT COUNT(*) AS count FROM CodeAPI";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("count") == 0; 
            }
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        return true; 
    }
    
    public int getIdApi() {
        int id = -1; // Default value to indicate not found
        String sql = "SELECT idApi FROM CodeAPI";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                id = rs.getInt("idApi");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void insertCodeApi(String code, String image, int idCode) {
        String query = "INSERT INTO CodeAPI (code, image, createdDate, codeId) VALUES (?, ?, GETDATE(), ?)";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, code);
            pst.setString(2, image);
            pst.setInt(3, idCode);
            
            int rowsInserted = pst.executeUpdate();
            System.out.println("Rows inserted: " + rowsInserted); // Ghi ra số hàng đã được chèn
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    public void updateCodeApi(int idApi, String code, String image, int idCode) {
//        String query = "UPDATE CodeAPI SET code = ?, image = ?, createdDate = GETDATE(), codeId = ? WHERE idApi = ?";
//
//        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
//            pst.setString(1, image); // Đặt hình ảnh vào tham số đầu tiên
//            pst.setInt(2, idCode);    // Đặt idCode vào tham số thứ hai
//            pst.setString(3, code);   // Đặt code vào tham số thứ ba
//            pst.setString(4, code);   
//
//            pst.executeUpdate();
//            //System.out.println("Rows updated: " + rowsUpdated); // Ghi ra số hàng đã được cập nhật
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//    
    public void updateCodeApi(String code, String image, int idCode) {
        String query = "UPDATE CodeAPI SET code = ?, image = ?, createdDate = GETDATE(), codeId = ?";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, code); // Đặt hình ảnh vào tham số đầu tiên
            pst.setString(2, image);    // Đặt idCode vào tham số thứ hai
            pst.setInt(3, idCode);   // Đặt code vào tham số thứ ba

            pst.executeUpdate();
            //System.out.println("Rows updated: " + rowsUpdated); // Ghi ra số hàng đã được cập nhật
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


	
	public static void main(String[] args) {
	    CodeApiDAO dao = new CodeApiDAO();
	    CodeApi c = dao.getByApi();
	    int idApi = dao.getIdApi();
	    System.out.println(idApi);
//	    List<CodeApi> list = dao.getAlls();
//	    for (CodeApi code : list) {
//	        System.out.println(code);
//	    }
	}
}
