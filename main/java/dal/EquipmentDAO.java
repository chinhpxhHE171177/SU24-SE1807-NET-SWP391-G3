package dal;

import model.Equipment;
import model.ProductionLines;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO extends DBContext{

    public List<Equipment> getAlls() {
        List<Equipment> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Equipment";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Equipment e = new Equipment(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getDate(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getString(7),
                        rs.getInt(8));
                list.add(e); // Add the line object to the list
            }
            // Close resources
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; // Return the populated list
    }

    public int getECodedByName(String equipmentCode) throws SQLException {
        String query = "SELECT EquipmentID FROM Equipment WHERE TRIM(LOWER(EquipmentCode)) = ?";
        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, equipmentCode.trim().toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("EquipmentID");
            } else {
                throw new SQLException("No Equipment found with the name: " + equipmentCode);
            }
        }
    }
}
