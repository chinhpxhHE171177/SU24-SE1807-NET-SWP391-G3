package dal;

import model.Departments;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DepartmentDAO extends DBContext{
    public List<Departments> getAlls() {
        List<Departments> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Departments";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Departments dep = new Departments(rs.getInt(1), rs.getString(2));
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
