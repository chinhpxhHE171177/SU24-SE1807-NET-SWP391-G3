package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.ProductionLines;
import model.Stages;

public class ProductionLineDAO extends DBContext{

    public List<String> getDowntimeData() {
        List<String> downtimeList = new ArrayList<>();
        String query = "SELECT pl.LineName, " +
                "SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime " +
                "FROM ProductionLines pl " +
                "JOIN Stages s ON pl.LineID = s.LineID " +
                "JOIN Equipment e ON s.StageID = e.StageID " +
                "JOIN ErrorHistory eh ON e.EquipmentID = eh.EquipmentID " +
                "GROUP BY pl.LineName";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String lineName = resultSet.getString("LineName");
                double totalDowntime = resultSet.getDouble("TotalDowntime");
                downtimeList.add(lineName + ": " + totalDowntime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return downtimeList;
    }
    
    
}
