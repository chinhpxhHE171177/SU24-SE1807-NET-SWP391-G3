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

    public static void main(String[] args) {
        LineDAO ldao = new LineDAO();
        List<ProductionLines> list = ldao.getLines();
        for (ProductionLines line : list) {
            System.out.println(line); // This should now print the lines if data exists
        }
    }
}
