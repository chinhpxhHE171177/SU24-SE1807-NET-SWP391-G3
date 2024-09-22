package dal;

import model.Lines;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LineDAO extends DBContext {

    public List<Lines> getLines() {
        List<Lines> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM LINES";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Lines line = new Lines(rs.getString("LineCode"), rs.getString("LineName"));
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

    public static void main(String[] args) {
        LineDAO ldao = new LineDAO();
        List<Lines> list = ldao.getLines();
        for (Lines line : list) {
            System.out.println(line); // This should now print the lines if data exists
        }
    }
}
