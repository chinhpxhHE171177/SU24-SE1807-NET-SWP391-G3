package dal;

import model.DowntimeRecord;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    
    public List<String> getDowntimeDataByUser(String userCode) {
        List<String> downtimeList = new ArrayList<>();
        String query = "SELECT pl.LineName, \r\n"
        		+ "SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,\r\n"
        		+ "SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
        		+ "SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
        		+ "COUNT(eh.ErrorID) AS TotalDowntimeCount\r\n"
        		+ "FROM ErrorHistory eh\r\n"
        		+ "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
        		+ "JOIN Stages s ON s.StageID = eh.StageID\r\n"
        		+ "LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID\r\n"
        		+ "JOIN Rooms r ON pl.RoomID = r.RoomID\r\n"
        		+ "JOIN Users u ON r.RoomID = u.RoomID\r\n"
        		+ "WHERE u.UserCode = ?\r\n"
        		+ "GROUP BY pl.LineName";

        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            
            // Thiết lập giá trị cho PreparedStatement trước khi thực hiện truy vấn
            pst.setString(1, userCode);
            
            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    String lineName = resultSet.getString("LineName");
                    double totalDowntime = resultSet.getDouble("TotalDowntime");
                    double shortStop = resultSet.getDouble("ShortStop");
                    double longStop = resultSet.getDouble("LongStop");
                    int downtimeCount = resultSet.getInt("TotalDowntimeCount");

                    // Định dạng: "LineName: downtimeCount: totalDowntime"
                    downtimeList.add(lineName + ": " + downtimeCount + ": " + shortStop + ": " + longStop + ": " + totalDowntime);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return downtimeList;
    }
    
//    public List<String> getMonthlyDowntimeData(String userCode) {
//        List<String> downtimeList = new ArrayList<>();
//        String query = "SELECT pl.LineName, \r\n"
//        		+ "    SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,\r\n"
//        		+ "    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
//        		+ "    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
//        		+ "    COUNT(eh.ErrorID) AS TotalDowntimeCount\r\n"
//        		+ "FROM ErrorHistory eh\r\n"
//        		+ "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
//        		+ "JOIN Stages s ON s.StageID = eh.StageID\r\n"
//        		+ "LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID\r\n"
//        		+ "JOIN Rooms r ON pl.RoomID = r.RoomID\r\n"
//        		+ "JOIN Users u ON r.RoomID = u.RoomID\r\n"
//        		+ "WHERE u.UserCode = ? AND MONTH(eh.StartTime) = MONTH(GETDATE())  AND YEAR(eh.StartTime) = YEAR(GETDATE()) \r\n"
//        		+ "GROUP BY pl.LineName;";
//
//        try (Connection connection = getConnection();
//             PreparedStatement pst = connection.prepareStatement(query)) {
//            
//            // Thiết lập giá trị cho PreparedStatement trước khi thực hiện truy vấn
//            pst.setString(1, userCode);
//            
//            try (ResultSet resultSet = pst.executeQuery()) {
//                while (resultSet.next()) {
//                    String lineName = resultSet.getString("LineName");
//                    double totalDowntime = resultSet.getDouble("TotalDowntime");
//                    double shortStop = resultSet.getDouble("ShortStop");
//                    double longStop = resultSet.getDouble("LongStop");
//                    int downtimeCount = resultSet.getInt("TotalDowntimeCount");
//
//                    // Định dạng: "LineName: downtimeCount: totalDowntime"
//                    downtimeList.add(lineName + ": " + downtimeCount + ": " + shortStop + ": " + longStop + ": " + totalDowntime);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return downtimeList;
//    }
//    
//    public List<String> getDailyDowntimeData(String userCode) {
//        List<String> downtimeList = new ArrayList<>();
//        String query = "SELECT pl.LineName, \r\n"
//        		+ "    SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,\r\n"
//        		+ "    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
//        		+ "    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
//        		+ "    COUNT(eh.ErrorID) AS TotalDowntimeCount\r\n"
//        		+ "FROM ErrorHistory eh\r\n"
//        		+ "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
//        		+ "JOIN Stages s ON s.StageID = eh.StageID\r\n"
//        		+ "LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID\r\n"
//        		+ "JOIN Rooms r ON pl.RoomID = r.RoomID\r\n"
//        		+ "JOIN Users u ON r.RoomID = u.RoomID\r\n"
//        		+ "WHERE u.UserCode = ? AND CAST(eh.StartTime AS DATE) = CAST(GETDATE() AS DATE) \r\n"
//        		+ "GROUP BY pl.LineName;";
//
//        try (Connection connection = getConnection();
//             PreparedStatement pst = connection.prepareStatement(query)) {
//            
//            // Thiết lập giá trị cho PreparedStatement trước khi thực hiện truy vấn
//            pst.setString(1, userCode);
//            
//            try (ResultSet resultSet = pst.executeQuery()) {
//                while (resultSet.next()) {
//                    String lineName = resultSet.getString("LineName");
//                    double totalDowntime = resultSet.getDouble("TotalDowntime");
//                    double shortStop = resultSet.getDouble("ShortStop");
//                    double longStop = resultSet.getDouble("LongStop");
//                    int downtimeCount = resultSet.getInt("TotalDowntimeCount");
//
//                    // Định dạng: "LineName: downtimeCount: totalDowntime"
//                    downtimeList.add(lineName + ": " + downtimeCount + ": " + shortStop + ": " + longStop + ": " + totalDowntime);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return downtimeList;
//    }
//    
//    public List<Map<String, Object>> getMonthlyDowntimeData(String userCode) {
//        List<Map<String, Object>> downtimeList = new ArrayList<>();
//        String query = "SELECT pl.LineName, " +
//                "SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, " +
//                "SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop, " +
//                "SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, " +
//                "COUNT(eh.ErrorID) AS TotalDowntimeCount " +
//                "FROM ErrorHistory eh " +
//                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID " +
//                "JOIN Stages s ON s.StageID = eh.StageID " +
//                "LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID " +
//                "JOIN Rooms r ON pl.RoomID = r.RoomID " +
//                "JOIN Users u ON r.RoomID = u.RoomID " +
//                "WHERE u.UserCode = ? AND MONTH(eh.StartTime) = MONTH(GETDATE()) " +
//                "AND YEAR(eh.StartTime) = YEAR(GETDATE()) " +
//                "GROUP BY pl.LineName;";
//
//        try (Connection connection = getConnection();
//             PreparedStatement pst = connection.prepareStatement(query)) {
//            pst.setString(1, userCode);
//
//            try (ResultSet resultSet = pst.executeQuery()) {
//                while (resultSet.next()) {
//                    Map<String, Object> downtimeData = new HashMap<>();
//                    downtimeData.put("lineName", resultSet.getString("LineName"));
//                    downtimeData.put("totalDowntime", resultSet.getDouble("TotalDowntime"));
//                    downtimeData.put("shortStop", resultSet.getDouble("ShortStop"));
//                    downtimeData.put("longStop", resultSet.getDouble("LongStop"));
//                    downtimeData.put("downtimeCount", resultSet.getInt("TotalDowntimeCount"));
//
//                    downtimeList.add(downtimeData);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return downtimeList;
//    }
//
//    public List<Map<String, Object>> getDailyDowntimeData(String userCode) {
//        List<Map<String, Object>> downtimeList = new ArrayList<>();
//        String query = "SELECT pl.LineName, " +
//                "SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, " +
//                "SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop, " +
//                "SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, " +
//                "COUNT(eh.ErrorID) AS TotalDowntimeCount " +
//                "FROM ErrorHistory eh " +
//                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID " +
//                "JOIN Stages s ON s.StageID = eh.StageID " +
//                "LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID " +
//                "JOIN Rooms r ON pl.RoomID = r.RoomID " +
//                "JOIN Users u ON r.RoomID = u.RoomID " +
//                "WHERE u.UserCode = ? AND CAST(eh.StartTime AS DATE) = CAST(GETDATE() AS DATE) " +
//                "GROUP BY pl.LineName;";
//
//        try (Connection connection = getConnection();
//             PreparedStatement pst = connection.prepareStatement(query)) {
//            pst.setString(1, userCode);
//
//            try (ResultSet resultSet = pst.executeQuery()) {
//                while (resultSet.next()) {
//                    Map<String, Object> downtimeData = new HashMap<>();
//                    downtimeData.put("lineName", resultSet.getString("LineName"));
//                    downtimeData.put("totalDowntime", resultSet.getDouble("TotalDowntime"));
//                    downtimeData.put("shortStop", resultSet.getDouble("ShortStop"));
//                    downtimeData.put("longStop", resultSet.getDouble("LongStop"));
//                    downtimeData.put("downtimeCount", resultSet.getInt("TotalDowntimeCount"));
//
//                    downtimeList.add(downtimeData);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return downtimeList;
//    }
    
//    public List<Map<String, Object>> getMonthlyDowntimeData(String userCode) {
//        List<Map<String, Object>> downtimeList = new ArrayList<>();
//        String query = "SELECT pl.LineName, " +
//                "FORMAT(eh.StartTime, 'MMMM yyyy') AS Month, " + 
//                "SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, " +
//                "SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop, " +
//                "SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, " +
//                "COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount " +
//                "FROM ErrorHistory eh " +
//                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID " +
//                "JOIN Stages s ON s.StageID = eh.StageID " +
//                "LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID " +
//                "JOIN Rooms r ON pl.RoomID = r.RoomID " +
//                "JOIN Users u ON r.RoomID = u.RoomID " +
//                "WHERE u.UserCode = ? AND MONTH(eh.StartTime) = MONTH(GETDATE()) " +
//                "AND YEAR(eh.StartTime) = YEAR(GETDATE()) " +
//                "GROUP BY pl.LineName, FORMAT(eh.StartTime, 'MMMM yyyy');"; 
//
//        try (Connection connection = getConnection();
//             PreparedStatement pst = connection.prepareStatement(query)) {
//            pst.setString(1, userCode);
//
//            try (ResultSet resultSet = pst.executeQuery()) {
//                while (resultSet.next()) {
//                    Map<String, Object> downtimeData = new HashMap<>();
//                    downtimeData.put("lineName", resultSet.getString("LineName"));
//                    downtimeData.put("month", resultSet.getString("Month")); 
//                    downtimeData.put("totalDowntime", resultSet.getDouble("TotalDowntime"));
//                    downtimeData.put("shortStop", resultSet.getDouble("ShortStop"));
//                    downtimeData.put("longStop", resultSet.getDouble("LongStop"));
//                    downtimeData.put("downtimeCount", resultSet.getInt("TotalDowntimeCount"));
//
//                    downtimeList.add(downtimeData);
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return downtimeList;
//    }
    
    public List<Map<String, Object>> getMonthlyDowntimeData(String userCode) {
        List<Map<String, Object>> downtimeList = new ArrayList<>();
        String query = "SELECT \r\n"
        		+ "    LineName,\r\n"
        		+ "    Months,\r\n"
        		+ "    SUM(TotalDowntime) AS TotalDowntime,\r\n"
        		+ "    SUM(ShortStop) AS ShortStop,\r\n"
        		+ "    SUM(LongStop) AS LongStop,\r\n"
        		+ "    SUM(TotalDowntimeCount) AS TotalDowntimeCount,\r\n"
        		+ "    ROUND(AVG(Efficiency) * 100, 2) AS ShortStopPercentage\r\n"
        		+ "FROM (\r\n"
        		+ "    SELECT \r\n"
        		+ "        e.EquipmentID,\r\n"
        		+ "        pl.LineName,\r\n"
        		+ "        FORMAT(eh.StartTime, 'MM/yyyy') AS Months,\r\n"
        		+ "        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,\r\n"
        		+ "        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
        		+ "        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, \r\n"
        		+ "        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,\r\n"
        		+ "        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / \r\n"
        		+ "        NULLIF(SUM(ss.Duration), 0) AS Efficiency\r\n"
        		+ "    FROM ErrorHistory eh\r\n"
        		+ "    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
        		+ "    JOIN Stages s ON s.StageID = eh.StageID\r\n"
        		+ "    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID\r\n"
        		+ "    JOIN Rooms r ON pl.RoomID = r.RoomID\r\n"
        		+ "    JOIN Users u ON r.RoomID = u.RoomID\r\n"
        		+ "    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID\r\n"
        		+ "	WHERE u.UserCode = ? AND MONTH(eh.StartTime) = MONTH(GETDATE())\r\n"
        		+ "    GROUP BY \r\n"
        		+ "        e.EquipmentID, pl.LineName, FORMAT(eh.StartTime, 'MM/yyyy')\r\n"
        		+ ") AS Summary\r\n"
        		+ "GROUP BY \r\n"
        		+ "    LineName, Months;"; 

        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, userCode);

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> downtimeData = new HashMap<>();
                    downtimeData.put("lineName", resultSet.getString("LineName"));
                    downtimeData.put("month", resultSet.getString("Months")); 
                    downtimeData.put("totalDowntime", resultSet.getDouble("TotalDowntime"));
                    downtimeData.put("shortStop", resultSet.getDouble("ShortStop"));
                    downtimeData.put("longStop", resultSet.getDouble("LongStop"));
                    downtimeData.put("downtimeCount", resultSet.getInt("TotalDowntimeCount"));
                    downtimeData.put("shortStopPercentage", resultSet.getDouble("ShortStopPercentage"));

                    downtimeList.add(downtimeData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return downtimeList;
    }

    public List<Map<String, Object>> getDailyDowntimeData(String userCode) {
        List<Map<String, Object>> downtimeList = new ArrayList<>();
        String query = "SELECT \r\n"
        		+ "    LineName,\r\n"
        		+ "    Date,\r\n"
        		+ "	SUM(TotalDowntime) AS TotalDowntime,\r\n"
        		+ "    SUM(ShortStop) AS ShortStop,\r\n"
        		+ "	SUM(LongStop) AS LongStop,\r\n"
        		+ "    SUM(TotalDowntimeCount) TotalDowntimeCount,\r\n"
        		+ "    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage\r\n"
        		+ "FROM (\r\n"
        		+ "    SELECT \r\n"
        		+ "        e.EquipmentID,\r\n"
        		+ "        pl.LineName,\r\n"
        		+ "        FORMAT(eh.StartTime, 'yyyy-MM-dd') AS Date,\r\n"
        		+ "        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,\r\n"
        		+ "        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
        		+ "        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, \r\n"
        		+ "        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,\r\n"
        		+ "        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency\r\n"
        		+ "    FROM ErrorHistory eh\r\n"
        		+ "    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
        		+ "    JOIN Stages s ON s.StageID = eh.StageID\r\n"
        		+ "    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID\r\n"
        		+ "    JOIN Rooms r ON pl.RoomID = r.RoomID\r\n"
        		+ "    JOIN Users u ON r.RoomID = u.RoomID\r\n"
        		+ "    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID\r\n"
        		+ "    WHERE u.UserCode = ? AND CAST(eh.StartTime AS DATE) = CAST(GETDATE() AS DATE)\r\n"
        		+ "    GROUP BY \r\n"
        		+ "        e.EquipmentID, pl.LineName, FORMAT(eh.StartTime, 'yyyy-MM-dd'), ss.Duration\r\n"
        		+ ") AS Summary\r\n"
        		+ "GROUP BY \r\n"
        		+ "    LineName, Date;"; 

        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, userCode);

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> downtimeData = new HashMap<>();
                    downtimeData.put("lineName", resultSet.getString("LineName"));
                    downtimeData.put("date", resultSet.getString("Date")); // Add date to data
                    downtimeData.put("totalDowntime", resultSet.getDouble("TotalDowntime"));
                    downtimeData.put("shortStop", resultSet.getDouble("ShortStop"));
                    downtimeData.put("longStop", resultSet.getDouble("LongStop"));
                    downtimeData.put("downtimeCount", resultSet.getInt("TotalDowntimeCount"));
                    downtimeData.put("shortStopPercentage", resultSet.getDouble("ShortStopPercentage"));

                    downtimeList.add(downtimeData);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return downtimeList;
    }
    
    public List<Map<String, Object>> getYearlyDowntimeData(String userCode) {
        List<Map<String, Object>> downtimeList = new ArrayList<>();
        String query = "SELECT \r\n"
        		+ "    LineName,\r\n"
        		+ "    Year,\r\n"
        		+ "	SUM(TotalDowntime) AS TotalDowntime,\r\n"
        		+ "    SUM(ShortStop) AS ShortStop,\r\n"
        		+ "	SUM(LongStop) AS LongStop,\r\n"
        		+ "    SUM(TotalDowntimeCount) TotalDowntimeCount,\r\n"
        		+ "    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage\r\n"
        		+ "FROM (\r\n"
        		+ "    SELECT \r\n"
        		+ "        e.EquipmentID,\r\n"
        		+ "        pl.LineName,\r\n"
        		+ "        FORMAT(eh.StartTime, 'yyyy') AS Year,\r\n"
        		+ "        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,\r\n"
        		+ "        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
        		+ "        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, \r\n"
        		+ "        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,\r\n"
        		+ "        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency\r\n"
        		+ "    FROM ErrorHistory eh\r\n"
        		+ "    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
        		+ "    JOIN Stages s ON s.StageID = eh.StageID\r\n"
        		+ "    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID\r\n"
        		+ "    JOIN Rooms r ON pl.RoomID = r.RoomID\r\n"
        		+ "    JOIN Users u ON r.RoomID = u.RoomID\r\n"
        		+ "    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID\r\n"
        		+ "    WHERE u.UserCode = ? AND YEAR(eh.StartTime) = YEAR(GETDATE())\r\n"
        		+ "    GROUP BY \r\n"
        		+ "        e.EquipmentID, pl.LineName, FORMAT(eh.StartTime, 'yyyy'), ss.Duration\r\n"
        		+ ") AS Summary\r\n"
        		+ "GROUP BY \r\n"
        		+ "    LineName, Year;"; 

        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(query)) {
            pst.setString(1, userCode);

            try (ResultSet resultSet = pst.executeQuery()) {
                while (resultSet.next()) {
                    Map<String, Object> downtimeData = new HashMap<>();
                    downtimeData.put("lineName", resultSet.getString("LineName"));
                    downtimeData.put("year", resultSet.getString("Year")); // This is fine, it's just showing the year
                    downtimeData.put("totalDowntime", resultSet.getDouble("TotalDowntime"));
                    downtimeData.put("shortStop", resultSet.getDouble("ShortStop"));
                    downtimeData.put("longStop", resultSet.getDouble("LongStop"));
                    downtimeData.put("downtimeCount", resultSet.getInt("TotalDowntimeCount"));
                    downtimeData.put("shortStopPercentage", resultSet.getDouble("ShortStopPercentage"));

                    downtimeList.add(downtimeData);
                }
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
        String sql = "SELECT TOP 5 pl.LineName, \r\n"
        		+ "SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, \r\n"
        		+ "SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
        		+ "SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
        		+ "COUNT(eh.ErrorID) AS ErrorCount\r\n"
        		+ "FROM ProductionLines pl \r\n"
        		+ "JOIN Stages s ON pl.LineID = s.LineID \r\n"
        		+ "JOIN ErrorHistory eh ON s.StageID = eh.StageID\r\n"
        		+ "GROUP BY pl.LineName\r\n"
        		+ "ORDER BY TotalDowntime DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DowntimeRecord record = new DowntimeRecord();
                record.setLineName(rs.getString("LineName"));
                record.setTotalDowntime(rs.getInt("TotalDowntime"));
                record.setShortTime(rs.getDouble("ShortStop"));
                record.setLongTime(rs.getDouble("LongStop"));
                record.setErrorCount(rs.getInt("ErrorCount")); 
                list.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }


    public List<DowntimeRecord> getTopStages() {
        List<DowntimeRecord> list = new ArrayList<>();
        String sql = "SELECT TOP 5 s.StageName, \r\n"
        		+ "SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, \r\n"
        		+ "SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
        		+ "SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
        		+ "COUNT(eh.ErrorID) AS ErrorCount\r\n"
        		+ "FROM Stages s \r\n"
        		+ "JOIN ErrorHistory eh ON s.StageID = eh.StageID \r\n"
        		+ "GROUP BY s.StageName \r\n"
        		+ "ORDER BY TotalDowntime DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                DowntimeRecord record = new DowntimeRecord();
                record.setStageName(rs.getString("StageName"));
                record.setTotalDowntime(rs.getInt("TotalDowntime"));
                record.setShortTime(rs.getDouble("ShortStop"));
                record.setLongTime(rs.getDouble("LongStop"));
                record.setErrorCount(rs.getInt("ErrorCount")); 
                list.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    
    // Phương thức lấy Top 5 dây chuyền sản xuất theo ngày
    public List<DowntimeRecord> getTopLinesByDate(String startDate, String endDate) {
        List<DowntimeRecord> list = new ArrayList<>();
        String sql = "SELECT TOP 5 pl.LineName, " +
                     "SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, " +
                     "SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop, " +
                     "SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, " +
                     "COUNT(eh.ErrorID) AS ErrorCount " +
                     "FROM ProductionLines pl " +
                     "JOIN Stages s ON pl.LineID = s.LineID " +
                     "JOIN ErrorHistory eh ON s.StageID = eh.StageID " +
                     "WHERE eh.StartTime BETWEEN ? AND ? " + 
                     "GROUP BY pl.LineName " +
                     "ORDER BY TotalDowntime DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DowntimeRecord record = new DowntimeRecord();
                record.setLineName(rs.getString("LineName"));
                record.setTotalDowntime(rs.getInt("TotalDowntime"));
                record.setShortTime(rs.getDouble("ShortStop"));
                record.setLongTime(rs.getDouble("LongStop"));
                record.setErrorCount(rs.getInt("ErrorCount"));
                list.add(record);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    // Phương thức lấy Top 5 giai đoạn theo ngày
    public List<DowntimeRecord> getTopStagesByDate(String startDate, String endDate) {
        List<DowntimeRecord> list = new ArrayList<>();
        String sql = "SELECT TOP 5 s.StageName, " +
                     "SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime, " +
                     "SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop, " +
                     "SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, " +
                     "COUNT(eh.ErrorID) AS ErrorCount " +
                     "FROM Stages s " +
                     "JOIN ErrorHistory eh ON s.StageID = eh.StageID " +
                     "WHERE eh.StartTime BETWEEN ? AND ? " + 
                     "GROUP BY s.StageName " +
                     "ORDER BY TotalDowntime DESC";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                DowntimeRecord record = new DowntimeRecord();
                record.setStageName(rs.getString("StageName"));
                record.setTotalDowntime(rs.getInt("TotalDowntime"));
                record.setShortTime(rs.getDouble("ShortStop"));
                record.setLongTime(rs.getDouble("LongStop"));
                record.setErrorCount(rs.getInt("ErrorCount"));
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
