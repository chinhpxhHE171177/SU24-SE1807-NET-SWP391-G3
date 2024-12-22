package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import model.Code;

public class CodeDAO extends DBContext {
	
	public List<Code> getAlls(){
        List<Code> roles = new ArrayList<>();
        String query = "SELECT c.*, l.lineName FROM Codes c JOIN Lines l ON l.lineId = c.lineId";
        try {
             Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                String code = rs.getString("code");
                String name = rs.getString("name");
                int lineId = rs.getInt("lineId");
                String image = rs.getString("image");
                Timestamp createdDate = rs.getTimestamp("createdDate");
                String lineName = rs.getString("lineName");
                roles.add(new Code(id, code, name, lineId, lineName, image, createdDate));
            }
        } catch (Exception e) {
			e.getStackTrace();
		}
        return roles;
    }
	
	 public void delete(int id) {
	        String sql = "DELETE FROM Codes WHERE id = ?";

	        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
	            stmt.setInt(1, id);
	            stmt.executeUpdate();
	        } catch (SQLException e) {
	            e.getStackTrace();
	     }
	 }
	 
	 public void add(String code, String name, int lineId, String image) {
	        String sql = "INSERT INTO Codes (code, name, lineId, image, createdDate) VALUES (?, ?, ?, ?, GETDATE())";

	        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {
	            pst.setString(1, code);
	            pst.setString(2, name);
	            pst.setInt(3, lineId);
	            pst.setString(4, image);
	            
	            pst.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Lỗi khi thêm mã: " + e.getMessage());
	        }
	    }
	 
	 public void addNew(String code, String name, Integer lineId, String image) {
	        String sql = "INSERT INTO Codes (code, name, lineId, image, createdDate) VALUES (?, ?, ?, ?, GETDATE())";

	        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {
	            pst.setString(1, code);
	            pst.setString(2, name);
	            pst.setInt(3, lineId);
	            pst.setString(4, image);
	            
	            pst.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	            throw new RuntimeException("Lỗi khi thêm mã: " + e.getMessage());
	        }
	    }
	 
	 public void update(int id, String code, String name, int lineId, String image) {
		    String sql = "UPDATE Codes SET code = ?, name = ?, lineId = ?, image = ?, createdDate = GETDATE() WHERE id = ?";

		    try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {
		        pst.setString(1, code);
		        pst.setString(2, name);
		        pst.setInt(3, lineId);
		        pst.setString(4, image);
		        pst.setInt(5, id);

		        pst.executeUpdate();
		    } catch (SQLException e) {
		        e.printStackTrace();
		        throw new RuntimeException("Lỗi khi cập nhật mã: " + e.getMessage());
		    }
		}

	 // New method to fetch image URL by code
	    public String getImageByCode(String code) {
	        String imageUrl = null;
	        String sql = "SELECT image FROM Codes WHERE code = ?";
	        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {
	            pst.setString(1, code);
	            ResultSet rs = pst.executeQuery();
	            if (rs.next()) {
	                imageUrl = rs.getString("image");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return imageUrl;
	    }
	    
	    public int getIdCodeByCode(String code) {
	        int id = -1; // Default value to indicate not found
	        String sql = "SELECT id FROM Codes WHERE code = ?";
	        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {
	            pst.setString(1, code);
	            ResultSet rs = pst.executeQuery();
	            if (rs.next()) {
	                id = rs.getInt("id");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return id;
	    }
	   
	    public Code getCodeByCode(String code) {
	        String sql = "SELECT * FROM Codes WHERE code = ?";
	        try (Connection conn = getConnection();
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, code);
	            ResultSet rs = pstmt.executeQuery();
	            if (rs.next()) {
	                return new Code(
	                    rs.getInt("id"),
	                    rs.getString("code"),
	                    rs.getString("name"),
	                    rs.getInt("lineId"),
	                    rs.getString("image"),
	                    rs.getTimestamp("createdDate")
	                );
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return null; // Nếu không tìm thấy
	    }
    
	public static void main (String []args) {
		CodeDAO dao = new CodeDAO();
		List<Code> list = dao.getAlls();
		for(Code codes: list) {
			System.out.print(codes);
		}
	}

}
