package dal;

import model.ProductionLines;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineDAO extends DBContext {

    public List<ProductionLines> getLines() {
        List<ProductionLines> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM ProductionLines";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ProductionLines line = new ProductionLines(rs.getInt("LineID"), rs.getString("LineName"), rs.getInt("RoomID"));
                list.add(line); // Add the line object to the list
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

    public int getLineIdByName(String lineName) throws SQLException {
        String sql = "SELECT LineID FROM ProductionLines WHERE LineName = ?";
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, lineName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("LineID");
            } else {
                throw new SQLException("No Line found with the name: " + lineName);
            }
        }
    }
    
    public List<ProductionLines> searchByLine(Integer lineId) {
        List<ProductionLines> list = new ArrayList<>();
        try {
            String sql = "SELECT \r\n"
            		+ "    pl.LineID, \r\n"
            		+ "    pl.LineName, \r\n"
            		+ "    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
            		+ "    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
            		+ "	SUM(eh.Duration) AS TotalDuration,\r\n"
            		+ "	COUNT(eh.ErrorID) AS ErrorCount\r\n"
            		+ "FROM ErrorHistory eh\r\n"
            		+ "JOIN Stages s ON eh.StageID = s.StageID\r\n"
            		+ "JOIN ProductionLines pl ON s.LineID = pl.LineID\r\n"
            		+ "WHERE pl.LineID = ?\r\n"
            		+ "GROUP BY pl.LineID, pl.LineName;";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, lineId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
            	ProductionLines p = new ProductionLines(rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getInt(6));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();;
        }
        return list;
    } 
    
    
    public ProductionLines getLineAndDepartment(String userCode) {
        try {
            String sql = "SELECT l.LineName, d.DepartmentName FROM ProductionLines L\r\n"
            		+ "JOIN Rooms r ON l.RoomID = r.RoomID\r\n"
            		+ "JOIN Users u ON u.RoomID = r.RoomID\r\n"
            		+ "JOIN Departments d ON  r.DepartmentID = d.DepartmentID\r\n"
            		+ "WHERE u.UserCode = ?";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, userCode);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
            	return new ProductionLines(
                        rs.getString(1),
                        rs.getString(2));
            }
        } catch (SQLException e) {
            e.printStackTrace();;
        }
        return null;
    } 

    public static void main(String[] args) {
        LineDAO ldao = new LineDAO();
        List<ProductionLines> list = ldao.searchByLine(1);
        for (ProductionLines line : list) {
            System.out.println(line); // This should now print the lines if data exists
        }
    }
}
