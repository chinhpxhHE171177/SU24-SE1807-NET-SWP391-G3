package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Line;
import model.Role;
import model.User;

public class RoleDAO extends DBContext {

    public List<Role> getAlls() {
        List<Role> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Role";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	Role role = new Role(rs.getInt("id"), rs.getString("rolename"), rs.getString("description"), rs.getTimestamp("createdAt"));
                list.add(role); 
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
        String sql = "DELETE FROM Role WHERE id = ?";

        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
     }
    }
    
    public boolean isNameExists(String name, Integer roleId) {
	    String sql = "SELECT COUNT(*) FROM Role WHERE name = ?" + (roleId != null ? " AND id != ?" : "");
	    try (Connection connection = getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql)) {
	        stmt.setString(1, name);
	        if (roleId != null) {
	            stmt.setInt(2, roleId);
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
    
    public void add(Role role) {
	    String sql = "INSERT INTO Role (rolename, description, createdAt) VALUES (?, ?, GETDATE())";
	    
	    try (Connection connection = getConnection();
	         PreparedStatement stmt = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
	        
	        stmt.setString(1, role.getName());
	        stmt.setString(2, role.getDescription());
	        stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi thêm vai trò: " + e.getMessage());
        }
	}
    
    
    public void update(Role role) {
	    String sql = "UPDATE Role SET rolename = ?, description = ?, createdAt = GETDATE() WHERE id = ?";

	    try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {
	    	
	        pst.setString(1, role.getName());
	        pst.setString(2, role.getDescription());
	        pst.setInt(3, role.getId());
	        pst.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Lỗi khi cập nhật vai trò: " + e.getMessage());
	    }
	}   
    
    public int addNew(String roleName, String roleDescription) throws SQLException {
        String sql = "INSERT INTO Role (rolename, description, createdAt, GETDATE()) VALUES (?, ?); SELECT SCOPE_IDENTITY();";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, roleName);
            pst.setString(2, roleDescription);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt(1); 
            }
        }
        throw new SQLException("Failed to retrieve RoleID.");
    }
}