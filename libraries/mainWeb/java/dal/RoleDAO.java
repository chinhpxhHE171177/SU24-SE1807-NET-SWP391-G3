package dal;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import model.Roles;

public class RoleDAO extends DBContext {

    public List<Roles> getAllRoles(){
        List<Roles> roles = new ArrayList<>();
        String query = "SELECT RoleID, RoleName FROM Roles";
        try {
             Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("RoleID");
                String name = rs.getString("RoleName");
                roles.add(new Roles(id, name));
            }
        } catch (Exception e) {
			e.getStackTrace();
		}
        return roles;
    }
    
    public void assignRoleToUser(int userID, int roleID) throws SQLException {
        String query = "UPDATE Users SET RoleID = ? WHERE UserID = ?";
        try ( Connection connection = getConnection(); PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, roleID);
            pstmt.setInt(2, userID);
            pstmt.executeUpdate();
        }
    }
}
