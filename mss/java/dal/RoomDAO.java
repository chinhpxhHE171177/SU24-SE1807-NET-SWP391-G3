package dal;

import model.Departments;
import model.Rooms;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDAO extends DBContext{
    public List<Rooms> getAlls() {
        List<Rooms> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Rooms";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Rooms dep = new Rooms(rs.getInt(1), rs.getString(2), rs.getInt(3));
                list.add(dep); // Add the line object to the list
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
}
