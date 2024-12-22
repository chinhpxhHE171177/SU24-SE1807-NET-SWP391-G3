package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Code;
import model.Line;
import model.User;

public class UserDAO extends DBContext {

    public User login(String code, String password) {
        String sql = "SELECT * FROM Users WHERE code = ? AND password = ?";
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, code);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setCode(rs.getString("code"));
                user.setName(rs.getString("name"));
                user.setGender(rs.getString("gender"));
                user.setPasword(rs.getString("password"));  
                user.setRoleId(rs.getInt("roleId"));
                user.setCreatedAt(rs.getTimestamp("createdAt"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public List<User> getAlls() {
        List<User> list = new ArrayList<>();
        try {
        	String sql = "SELECT u.*, r.roleName, STRING_AGG(l.lineName, ', ') AS lineNames "
        	           + "FROM Users u "
        	           + "JOIN Role r ON u.roleId = r.id "
        	           + "LEFT JOIN User_Lines ul ON u.id = ul.userId "
        	           + "LEFT JOIN Lines l ON ul.lineId = l.lineId "
        	           + "GROUP BY u.id, u.code, u.name, u.gender, u.password, u.roleId, u.createdAt, r.roleName";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	User  user = new User(
            		    rs.getInt("id"), 
            		    rs.getString("code"),
            		    rs.getString("name"), 
            		    rs.getString("gender"), 
            		    rs.getString("password"), 
            		    rs.getInt("roleId"), 
            		    rs.getString("roleName"),
            		    rs.getString("lineNames"), // This should contain the line names
            		    rs.getTimestamp("createdAt")
            		);

                // Thêm thuộc tính lineIds vào user: danh sách lineId từ bảng User_Lines
                String sqlLines = "SELECT lineId FROM User_Lines WHERE userId = ?";
                PreparedStatement stmtLines = connection.prepareStatement(sqlLines);
                stmtLines.setInt(1, user.getId());
                ResultSet rsLines = stmtLines.executeQuery();
                List<String> lineIds = new ArrayList<>();
                while (rsLines.next()) {
                    lineIds.add(String.valueOf(rsLines.getInt("lineId")));
                }
                rsLines.close();
                stmtLines.close();

                // Gán danh sách lineId dưới dạng chuỗi phân tách bởi dấu phẩy
                user.setLineIds(String.join(",", lineIds)); // Danh sách ID dây chuyền
                user.setLineName(rs.getString("lineNames")); // Danh sách tên dây chuyền
                list.add(user);
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; 
    }
    
       public void delete(int id) {
           String sql = "DELETE FROM Users WHERE id = ?";

           try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
               stmt.setInt(1, id);
               stmt.executeUpdate();
           } catch (SQLException e) {
               e.getStackTrace();
        }
    }
       
       public void add(User user, List<Integer> lineIds) {
    	    String sql = "INSERT INTO Users (code, name, gender, password, roleId, createdAt) VALUES (?, ?, ?, ?, ?, GETDATE())";
    	    String sqlUserLines = "INSERT INTO User_Lines (userId, lineId) VALUES (?, ?)";
    	    
    	    try (Connection connection = getConnection();
    	         PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
    	        
    	        // Thêm người dùng vào bảng Users
    	        stmt.setString(1, user.getCode());
    	        stmt.setString(2, user.getName());
    	        stmt.setString(3, user.getGender());
    	        stmt.setString(4, user.getPasword());
    	        stmt.setInt(5, user.getRoleId());
    	        
    	        int affectedRows = stmt.executeUpdate();
    	        if (affectedRows > 0) {
    	            // Lấy ID người dùng vừa thêm
    	            ResultSet rs = stmt.getGeneratedKeys();
    	            if (rs.next()) {
    	                int userId = rs.getInt(1);
    	                
    	                // Thêm các dòng liên kết với người dùng vào bảng User_Lines
    	                try (PreparedStatement stmtLines = connection.prepareStatement(sqlUserLines)) {
    	                    for (Integer lineId : lineIds) {
    	                        stmtLines.setInt(1, userId);
    	                        stmtLines.setInt(2, lineId);
    	                        stmtLines.addBatch();
    	                    }
    	                    stmtLines.executeBatch();
    	                }
    	            }
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	}

       
       public void update(User user, List<Integer> lineIds) {
    	    String sql = "UPDATE Users SET code = ?, name = ?, gender = ?, roleId = ?, createdAt = GETDATE() WHERE id = ?";
    	    String sqlDeleteLines = "DELETE FROM User_Lines WHERE userId = ?";
    	    String sqlInsertLines = "INSERT INTO User_Lines (userId, lineId) VALUES (?, ?)";
    	    
    	    try (Connection connection = getConnection();
    	         PreparedStatement stmt = connection.prepareStatement(sql)) {
    	        
    	        // Cập nhật thông tin người dùng
    	        stmt.setString(1, user.getCode());
    	        stmt.setString(2, user.getName());
    	        stmt.setString(3, user.getGender());
    	        stmt.setInt(4, user.getRoleId());
    	        stmt.setInt(5, user.getId());
    	        stmt.executeUpdate();
    	        
    	        // Xóa các dòng liên kết cũ trước khi thêm lại
    	        try (PreparedStatement stmtDelete = connection.prepareStatement(sqlDeleteLines)) {
    	            stmtDelete.setInt(1, user.getId());
    	            stmtDelete.executeUpdate();
    	        }
    	        
    	        // Thêm các dòng mới vào User_Lines
    	        try (PreparedStatement stmtInsert = connection.prepareStatement(sqlInsertLines)) {
    	            for (Integer lineId : lineIds) {
    	                stmtInsert.setInt(1, user.getId());
    	                stmtInsert.setInt(2, lineId);
    	                stmtInsert.addBatch();
    	            }
    	            stmtInsert.executeBatch();
    	        }
    	        
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	}

       public boolean isCodeExists(String code, Integer userId) {
    	    String sql = "SELECT COUNT(*) FROM Users WHERE code = ?" + (userId != null ? " AND id != ?" : "");
    	    try (Connection connection = getConnection();
    	         PreparedStatement stmt = connection.prepareStatement(sql)) {
    	        stmt.setString(1, code);
    	        if (userId != null) {
    	            stmt.setInt(2, userId);
    	        }
    	        ResultSet rs = stmt.executeQuery();
    	        if (rs.next()) {
    	            return rs.getInt(1) > 0;
    	        }
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	    }
    	    return false;
    	}
       
       public boolean updatePassword(String code, String newPassword) {
    	    String sql = "UPDATE Users SET password = ? WHERE code = ?";
    	    try (Connection connection = getConnection();
    	         PreparedStatement stmt = connection.prepareStatement(sql)) {
    	        stmt.setString(1, newPassword);
    	        stmt.setString(2, code);
    	        int rowsUpdated = stmt.executeUpdate();
    	        return rowsUpdated > 0; 
    	    } catch (SQLException e) {
    	        e.printStackTrace();
    	        return false;
    	    }
    	}


    
    public static void main (String []args) {
		UserDAO dao = new UserDAO();
		List<User> list = dao.getAlls();
		for(User users: list) {
			System.out.print(users);
		}
	}
}
