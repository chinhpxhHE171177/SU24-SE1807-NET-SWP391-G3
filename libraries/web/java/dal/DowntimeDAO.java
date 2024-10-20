package dal;

import model.DowntimeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DowntimeDAO extends DBContext {

    public List<String> getDowntimeData() {
        List<String> downtimeList = new ArrayList<>();
        String query = " SELECT pl.LineName, SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,\n" +
                " COUNT(eh.ErrorID) AS TotalDowntimeCount\n" +
                " FROM ErrorHistory eh\n" +
                " JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n" +
                " JOIN Stages s ON s.StageID = eh.StageID\n" +
                " LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID\n" +
                " GROUP BY pl.LineName";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String lineName = resultSet.getString("LineName");
                int downtimeCount = resultSet.getInt("TotalDowntimeCount");
                double totalDowntime = resultSet.getDouble("TotalDowntime");

                // Format: "LineName: downtimeCount: totalDowntime"
                downtimeList.add(lineName + ": " + downtimeCount + ": " + totalDowntime);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return downtimeList;
    }

    public List<DowntimeRecord> listDownTime(String deviceCode, String roomId, String lineId, String stageId) {
        List<DowntimeRecord> list = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT e.EquipmentID, e.EquipmentCode, e.EquipmentName, " +
                    "pl.LineName, s.StageName, eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration " +
                    "FROM Equipment e " +
                    "JOIN ErrorHistory eh ON e.EquipmentID = eh.EquipmentID " +
                    "JOIN Stages s ON e.StageID = s.StageID " +
                    "JOIN ProductionLines pl ON s.LineID = pl.LineID " +
                    "JOIN Rooms r ON pl.RoomID = r.RoomID " +
                    "WHERE 1=1 ");  // Dummy condition to simplify appending clauses

            // Check if parameters are provided and append conditions
            if (deviceCode != null && !deviceCode.isEmpty()) {
                sql.append("AND e.EquipmentCode = ? ");
            }
            if (roomId != null && !roomId.isEmpty()) {
                sql.append("AND r.RoomID = ? ");
            }
            if (lineId != null && !lineId.isEmpty()) {
                sql.append("AND pl.LineID = ? ");
            }
            if (stageId != null && !stageId.isEmpty()) {
                sql.append("AND s.StageID = ? ");
            }

            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            // Set parameters dynamically
            int paramIndex = 1;
            if (deviceCode != null && !deviceCode.isEmpty()) {
                pstmt.setString(paramIndex++, deviceCode);
            }
            if (roomId != null && !roomId.isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(roomId));
            }
            if (lineId != null && !lineId.isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(lineId));
            }
            if (stageId != null && !stageId.isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(stageId));
            }

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                DowntimeRecord device = new DowntimeRecord();
                device.setEquipmentID(rs.getInt("EquipmentID"));
                device.setEquipmentCode(rs.getString("EquipmentCode"));
                device.setEquipmentName(rs.getString("EquipmentName"));
                device.setLineName(rs.getString("LineName"));
                device.setStageName(rs.getString("StageName"));
                device.setErrorDescription(rs.getString("ErrorDescription"));
                device.setStartTime(rs.getTimestamp("StartTime"));
                device.setEndTime(rs.getTimestamp("EndTime"));
                device.setDuration(rs.getInt("Duration"));
                list.add(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Example SQL Query to get downtime and error count
    public List<DowntimeRecord> listNewDownTime(String deviceCode, String roomId, String lineId, String stageId) {
        List<DowntimeRecord> list = new ArrayList<>();
        try {
            StringBuilder sql = new StringBuilder("SELECT e.EquipmentID, e.EquipmentCode, e.EquipmentName,\n" +
                    "pl.LineName, s.StageName, eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration,\n" +
                    "SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, COUNT(eh.ErrorID) AS TotalError\n" +
                    "FROM Equipment e\n" +
                    "JOIN ErrorHistory eh ON e.EquipmentID = eh.EquipmentID\n" +
                    "JOIN Stages s ON e.StageID = s.StageID\n" +
                    "JOIN ProductionLines pl ON s.LineID = pl.LineID\n" +
                    "JOIN Rooms r ON pl.RoomID = r.RoomID\n" +
                    "WHERE 1 = 1");

            // Append conditions
            if (deviceCode != null && !deviceCode.isEmpty()) {
                sql.append("AND e.EquipmentCode = ? ");
            }
            if (roomId != null && !roomId.isEmpty()) {
                sql.append("AND r.RoomID = ? ");
            }
            if (lineId != null && !lineId.isEmpty()) {
                sql.append("AND pl.LineID = ? ");
            }
            if (stageId != null && !stageId.isEmpty()) {
                sql.append("AND s.StageID = ? ");
            }

            sql.append("GROUP BY e.EquipmentID, e.EquipmentCode, e.EquipmentName,\n" +
                    "pl.LineName, s.StageName, eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration");

            Connection conn = getConnection();
            PreparedStatement pstmt = conn.prepareStatement(sql.toString());

            // Set parameters dynamically
            int paramIndex = 1;
            if (deviceCode != null && !deviceCode.isEmpty()) {
                pstmt.setString(paramIndex++, deviceCode);
            }
            if (roomId != null && !roomId.isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(roomId));
            }
            if (lineId != null && !lineId.isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(lineId));
            }
            if (stageId != null && !stageId.isEmpty()) {
                pstmt.setInt(paramIndex++, Integer.parseInt(stageId));
            }

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                DowntimeRecord device = new DowntimeRecord();
                device.setEquipmentID(rs.getInt("EquipmentID"));
                device.setEquipmentCode(rs.getString("EquipmentCode"));
                device.setEquipmentName(rs.getString("EquipmentName"));
                device.setLineName(rs.getString("LineName"));
                device.setStageName(rs.getString("StageName"));
                device.setErrorDescription(rs.getString("ErrorDescription"));
                device.setStartTime(rs.getTimestamp("StartTime"));
                device.setEndTime(rs.getTimestamp("EndTime"));
                device.setDuration(rs.getInt("Duration"));
                device.setTotalDowntime(rs.getInt("TotalDowntime"));
                device.setErrorCount(rs.getInt("TotalError"));
                list.add(device);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<DowntimeRecord> getTopLines() {
        List<DowntimeRecord> list = new ArrayList<>();
        String sql = "SELECT TOP 5 pl.LineName, SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, COUNT(eh.ErrorID) AS ErrorCount " +
                "FROM ProductionLines pl " +
                "JOIN Stages s ON pl.LineID = s.LineID " +
                "JOIN ErrorHistory eh ON s.StageID = eh.StageID " +
                "GROUP BY pl.LineName " +
                "ORDER BY TotalDowntime DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DowntimeRecord record = new DowntimeRecord();
                record.setLineName(rs.getString("LineName"));
                record.setTotalDowntime(rs.getInt("TotalDowntime"));
                record.setErrorCount(rs.getInt("ErrorCount")); // Lấy số lần xuất hiện
                list.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<DowntimeRecord> getTopStages() {
        List<DowntimeRecord> list = new ArrayList<>();
        String sql = "SELECT TOP 5 s.StageName, SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, COUNT(eh.ErrorID) AS ErrorCount " +
                "FROM Stages s " +
                "JOIN ErrorHistory eh ON s.StageID = eh.StageID " +
                "GROUP BY s.StageName " +
                "ORDER BY TotalDowntime DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DowntimeRecord record = new DowntimeRecord();
                record.setStageName(rs.getString("StageName"));
                record.setTotalDowntime(rs.getInt("TotalDowntime"));
                record.setErrorCount(rs.getInt("ErrorCount")); // Lấy số lần xuất hiện
                list.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }




    public static void main(String[] args) {
        DowntimeDAO dao = new DowntimeDAO();
        List<DowntimeRecord> list = dao.getTopLines();
        for (DowntimeRecord line : list) {
            System.out.println(line);
        }
    }
}
