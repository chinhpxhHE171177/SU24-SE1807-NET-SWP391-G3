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
	
	public void updatePageName(Permission permission) {
	    // Sửa đổi SQL để cập nhật PageName theo PermissionID
	    String sql = "UPDATE Permissions SET PageName = ? WHERE PermissionID = ?";

	    try (Connection conn = getConnection();
	         PreparedStatement stmt = conn.prepareStatement(sql)) {
	         
	        // Thiết lập các tham số
	        stmt.setString(1, permission.getPageName()); // Cập nhật PageName
	        stmt.setInt(2, permission.getId()); // Lấy ID permission của đối tượng

	        stmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}


	public Permission getPermissionById(int permissionId)  {
	    String sql = "SELECT * FROM Permissions WHERE PermissionID = ?";
	    try (Connection conn = getConnection();
		         PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setInt(1, permissionId);
	        ResultSet rs = stmt.executeQuery();
	        if (rs.next()) {
	            Permission perm = new Permission();
	            perm.setId(rs.getInt("PermissionID"));
	            perm.setRoleId(rs.getInt("RoleID"));
	            perm.setPageName(rs.getString("PageName"));
	            perm.setCanAccess(rs.getBoolean("CanAccess"));
	            perm.setCanAdd(rs.getBoolean("CanAdd"));
	            perm.setCanEdit(rs.getBoolean("CanEdit"));
	            perm.setCanDelete(rs.getBoolean("CanDelete"));
	            return perm;
	        }
	   } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return null;
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
	
	public void deletePermissionsByPageName(String pageName)  {
	    String sql = "DELETE FROM Permissions WHERE PageName = ?";
	    try (Connection conn = getConnection();
		         PreparedStatement stmt = conn.prepareStatement(sql)) {
	    	stmt.setString(1, pageName);
	    	stmt.executeUpdate();
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}

	
	public List<Permission> getAllPermissions() throws SQLException {
	    String sql = "SELECT RoleID, PageName, CanAccess, CanAdd, CanEdit, CanDelete FROM Permissions";
	    List<Permission> permissions = new ArrayList<>();
	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql);
	         ResultSet rs = ps.executeQuery()) {
	        while (rs.next()) {
	            Permission perm = new Permission();
	            perm.setRoleId(rs.getInt("RoleID"));
	            perm.setPageName(rs.getString("PageName"));
	            perm.setCanAccess(rs.getBoolean("CanAccess"));
	            perm.setCanAdd(rs.getBoolean("CanAdd"));
	            perm.setCanEdit(rs.getBoolean("CanEdit"));
	            perm.setCanDelete(rs.getBoolean("CanDelete"));
	            permissions.add(perm);
	        }
	    }
	    return permissions;
	}

	public void addPermission(Permission permission) throws SQLException {
	    String sql = "INSERT INTO Permissions (RoleID, PageName, CanAccess, CanAdd, CanEdit, CanDelete) VALUES (?, ?, ?, ?, ?, ?)";
	    try (Connection conn = getConnection();
	         PreparedStatement ps = conn.prepareStatement(sql)) {
	        ps.setInt(1, permission.getRoleId());
	        ps.setString(2, permission.getPageName());
	        ps.setBoolean(3, permission.isCanAccess());
	        ps.setBoolean(4, permission.isCanAdd());
	        ps.setBoolean(5, permission.isCanEdit());
	        ps.setBoolean(6, permission.isCanDelete());
	        ps.executeUpdate();
	    }
	}


	public void add(String pageName, boolean canAccess, boolean canAdd, boolean canEdit, boolean canDelete) {
	    // Query to get all RoleIDs from the Roles table
	    String fetchRolesSql = "SELECT id FROM Role";

	    // Query to insert a new permission
	    String insertPermissionSql = "INSERT INTO Permissions (RoleID, PageName, CanAccess, CanAdd, CanEdit, CanDelete) VALUES (?, ?, ?, ?, ?, ?)";

	    try (Connection connection = getConnection();
	         PreparedStatement fetchRolesStmt = connection.prepareStatement(fetchRolesSql);
	         PreparedStatement insertPermissionStmt = connection.prepareStatement(insertPermissionSql)) {

	        // Fetch all RoleIDs
	        ResultSet rs = fetchRolesStmt.executeQuery();

	        // Loop through each RoleID and insert a permission
	        while (rs.next()) {
	            int roleId = rs.getInt("id");

	            insertPermissionStmt.setInt(1, roleId);
	            insertPermissionStmt.setString(2, pageName);
	            insertPermissionStmt.setBoolean(3, canAccess);
	            insertPermissionStmt.setBoolean(4, canAdd);
	            insertPermissionStmt.setBoolean(5, canEdit);
	            insertPermissionStmt.setBoolean(6, canDelete);

	            // Execute the insertion for this RoleID
	            insertPermissionStmt.executeUpdate();
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	        throw new RuntimeException("Error adding permissions: " + e.getMessage());
	    }
	}

}
