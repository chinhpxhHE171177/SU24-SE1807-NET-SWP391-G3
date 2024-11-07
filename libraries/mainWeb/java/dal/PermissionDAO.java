package dal;

import model.Permission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PermissionDAO extends DBContext {
	
	public List<Permission> getPermissionsByRoleId(int roleId) {
	    List<Permission> list = new ArrayList<>();
	    String sql = "SELECT * FROM Permissions WHERE RoleID = ?";

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	         
	        stmt.setInt(1, roleId);
	        
	        try (ResultSet rs = stmt.executeQuery()) {
	            while (rs.next()) {
	                Permission p = new Permission(
	                    rs.getInt("PermissionID"),
	                    rs.getInt("RoleID"),
	                    rs.getString("PageName"),
	                    rs.getBoolean("CanAccess"),
	                    rs.getBoolean("CanAdd"),
	                    rs.getBoolean("CanEdit"),
	                    rs.getBoolean("CanDelete")
	                );
	                list.add(p);
	            }
	        }
	    } catch (SQLException e) {
	        System.err.println("SQL Exception: " + e.getMessage());
	    }

	    return list;
	}
	
	public void updatePermission(Permission permission) {
	    String sql = "UPDATE Permissions SET CanAccess = ?, CanAdd = ?, CanEdit = ?, CanDelete = ? WHERE RoleID = ? AND PageName = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setBoolean(1, permission.isCanAccess());
	        stmt.setBoolean(2, permission.isCanAdd());
	        stmt.setBoolean(3, permission.isCanEdit());
	        stmt.setBoolean(4, permission.isCanDelete());
	        stmt.setInt(5, permission.getRoleId());
	        stmt.setString(6, permission.getPageName());
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	public void deletePermissionsByRoleId(int roleId) {
	    String sql = "UPDATE Permissions SET CanAccess = 0, CanAdd = 0, CanEdit = 0, CanDelete = 0 WHERE RoleID = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, roleId);
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	public void deletePermission(Permission permission) {
	    String sql = "UPDATE Permissions SET CanAccess = 0, CanAdd = 0, CanEdit = 0, CanDelete = 0 WHERE RoleID = ? AND PageName = ?";
	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	        stmt.setInt(1, permission.getRoleId()); 
	        stmt.setString(2, permission.getPageName()); 
	        
	        stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


}
