package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RolePermissionDAO extends DBContext {

	 public static List<Integer> getPermissionsByRole(int roleId) {
	        List<Integer> permissions = new ArrayList<>();
	        String query = "SELECT PermissionID FROM RolePermissions WHERE RoleID = ?";
	        
	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setInt(1, roleId);
	            ResultSet rs = ps.executeQuery();
	            while (rs.next()) {
	                permissions.add(rs.getInt("PermissionID"));
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        return permissions;
	    }

	    public static void clearPermissions(int roleId) {
	        String query = "DELETE FROM RolePermissions WHERE RoleID = ?";
	        
	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setInt(1, roleId);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	    public static void addPermission(int roleId, int permissionId) {
	        String query = "INSERT INTO RolePermissions (RoleID, PermissionID) VALUES (?, ?)";
	        
	        try (Connection con = getConnection();
	             PreparedStatement ps = con.prepareStatement(query)) {
	            ps.setInt(1, roleId);
	            ps.setInt(2, permissionId);
	            ps.executeUpdate();
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }
}
