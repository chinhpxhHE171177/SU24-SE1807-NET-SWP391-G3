package dal;

import model.ChartData;
import model.DowntimeRecord;
import model.ErrorHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ErrorHistoryDAO extends DBContext{


    // Method to get downtime records with pagination
    public List<DowntimeRecord> getDowntimeRecords(int startRow, int endRow) {
        List<DowntimeRecord> downtimeRecords = new ArrayList<>();

        String query = "SELECT * FROM ( " +
                "    SELECT ROW_NUMBER() OVER (ORDER BY e.EquipmentID) AS RowNum, " +
                "           e.EquipmentID, e.EquipmentCode, e.EquipmentName, " +
                "           e.Origin, e.QRCode, e.YOM, " +
                "           er.ErrorID, er.ErrorDescription, er.StartTime, er.EndTime, " +
                "           DATEDIFF(MINUTE, er.StartTime, er.EndTime) AS Duration, " +
                "           s.StageName, l.LineName " +
                "    FROM Equipment e " +
                "    JOIN ErrorHistory er ON er.EquipmentID = e.EquipmentID " +
                "    JOIN Stages s ON s.StageID = e.StageID " +
                "    LEFT JOIN ProductionLines l ON l.LineID = s.LineID " +
                ") AS Result " +
                "WHERE RowNum BETWEEN ? AND ?";

        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            // Set parameters for pagination (start and end row)
            preparedStatement.setInt(1, startRow);
            preparedStatement.setInt(2, endRow);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    DowntimeRecord record = new DowntimeRecord();

                    // Extract data from result set
                    record.setErrorID(resultSet.getInt("ErrorID"));
                    record.setEquipmentID(resultSet.getInt("EquipmentID"));
                    record.setEquipmentCode(resultSet.getString("EquipmentCode"));
                    record.setEquipmentName(resultSet.getString("EquipmentName"));
                    record.setOrigin(resultSet.getString("Origin"));
                    record.setQrCode(resultSet.getString("QRCode"));
                    record.setYom(resultSet.getInt("YOM"));
                    record.setErrorDescription(resultSet.getString("ErrorDescription"));
                    record.setStartTime(resultSet.getTimestamp("StartTime"));
                    record.setEndTime(resultSet.getTimestamp("EndTime"));
                    record.setDuration(resultSet.getInt("Duration"));
                    record.setStageName(resultSet.getString("StageName"));
                    record.setLineName(resultSet.getString("LineName"));

                    downtimeRecords.add(record);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return downtimeRecords;
    }

    public List<ErrorHistory> getAlls() {
        List<ErrorHistory> list = new ArrayList<>();

        String query = "SELECT eh.*, e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName\n" +
                "FROM ErrorHistory eh\n" +
                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n" +
                "JOIN Stages s ON s.StageID = eh.StageID\n" +
                "LEFT JOIN ProductionLines l ON l.LineID = s.LineID\n" +
                "ORDER BY eh.StartTime desc\n";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    ErrorHistory eh = new ErrorHistory();

                    // Extract data from result set
                    eh.setId(resultSet.getInt("ErrorID"));
                    eh.setEquipmentId(resultSet.getInt("EquipmentID"));
                    eh.setContent(resultSet.getString("ErrorDescription"));
                    eh.setStartDate(resultSet.getTimestamp("StartTime"));
                    eh.setEndDate(resultSet.getTimestamp("EndTime"));
                    eh.setDuration(resultSet.getInt("Duration"));
                    eh.setStageId(resultSet.getInt("StageID"));
                    eh.setEquipmentCode(resultSet.getString("EquipmentCode"));
                    eh.setEquipmentName(resultSet.getString("EquipmentName"));
                    eh.setStageName(resultSet.getString("StageName"));
                    eh.setLineName(resultSet.getString("LineName"));
                    list.add(eh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
   

    public List<ErrorHistory> search(String EquipmentCode, Integer lineCode, Integer stageId, String deviceName, int pageIndex, int pageSize, String sortByDate) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();

        // Base query construction
        String query = "SELECT eh.*, e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName\n" +
                " FROM ErrorHistory eh\n" +
                " JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n" +
                " JOIN Stages s ON s.StageID = eh.StageID\n" +
                " LEFT JOIN ProductionLines l ON l.LineID = s.LineID\n" +
                " WHERE 1 = 1";

        // Adding filters based on input parameters
        if (EquipmentCode != null && !EquipmentCode.isEmpty()) {
            query += " AND e.EquipmentCode = ?";
        }
        if (lineCode != null) {
            query += " AND l.LineID = ?";
        }
        if (stageId != null) {
            query += " AND s.StageID = ?";
        }
        if (deviceName != null && !deviceName.isEmpty()) {
            query += " AND e.EquipmentName LIKE ?";
        }

        // Add sorting by StartTime or EndTime
        if ("asc".equalsIgnoreCase(sortByDate)) {
            query += " ORDER BY eh.StartTime ASC";
        } else {
            query += " ORDER BY eh.EndTime DESC";
        }

        // Pagination logic
        query += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        // Creating connection and statement
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            int index = 1;

            // Setting dynamic parameters for the query
            if (EquipmentCode != null && !EquipmentCode.isEmpty()) {
                stmt.setString(index++, EquipmentCode);
            }
            if (lineCode != null) {
                stmt.setInt(index++, lineCode);
            }
            if (stageId != null) {
                stmt.setInt(index++, stageId);
            }
            if (deviceName != null && !deviceName.isEmpty()) {
                stmt.setString(index++, "%" + deviceName + "%");
            }

            // Setting pagination parameters
            stmt.setInt(index++, (pageIndex - 1) * pageSize);
            stmt.setInt(index++, pageSize);

            // Executing query and processing results
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ErrorHistory eh = new ErrorHistory();

                // Extracting data from result set and populating ErrorHistory object
                eh.setId(rs.getInt("ErrorID"));
                eh.setEquipmentId(rs.getInt("EquipmentID"));
                eh.setContent(rs.getString("ErrorDescription"));
                eh.setStartDate(rs.getTimestamp("StartTime"));
                eh.setEndDate(rs.getTimestamp("EndTime"));
                eh.setDuration(rs.getInt("Duration"));
                eh.setStageId(rs.getInt("StageID"));
                eh.setEquipmentCode(rs.getString("EquipmentCode"));
                eh.setEquipmentName(rs.getString("EquipmentName"));
                eh.setStageName(rs.getString("StageName"));
                eh.setLineName(rs.getString("LineName"));

                // Adding to the list
                list.add(eh);
            }
        }

        return list;
    }
    
    public List<ErrorHistory> getErrorHistory(int pageIndex, int pageSize) {
        List<ErrorHistory> list = new ArrayList<>();

        String query = "SELECT eh.*, e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName, d.DepartmentName\n" +
                "FROM ErrorHistory eh\n" +
                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n" +
        	    "JOIN Stages s ON eh.StageID = s.StageID\n" +
        	    "LEFT JOIN ProductionLines l ON s.LineID = l.LineID\n" +
        	    "LEFT JOIN Rooms r ON l.RoomID = r.RoomID\n" +
        	    "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID\n" +
                "ORDER BY eh.StartTime desc\n" +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Set parameters for pagination (start and end row)
            ps.setInt(1, (pageIndex - 1) * pageSize);
            ps.setInt(2, pageSize);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    ErrorHistory eh = new ErrorHistory();

                    // Extract data from result set
                    eh.setId(resultSet.getInt("ErrorID"));
                    eh.setEquipmentId(resultSet.getInt("EquipmentID"));
                    eh.setContent(resultSet.getString("ErrorDescription"));
                    eh.setStartDate(resultSet.getTimestamp("StartTime"));
                    eh.setEndDate(resultSet.getTimestamp("EndTime"));
                    eh.setDuration(resultSet.getInt("Duration"));
                    eh.setStageId(resultSet.getInt("StageID"));
                    eh.setEquipmentCode(resultSet.getString("EquipmentCode"));
                    eh.setEquipmentName(resultSet.getString("EquipmentName"));
                    eh.setStageName(resultSet.getString("StageName"));
                    eh.setLineName(resultSet.getString("LineName"));
                    eh.setDepartmentName(resultSet.getString("DepartmentName"));
                    list.add(eh);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    public List<ErrorHistory> searchNew(String EquipmentCode, Integer depId, Integer lineCode, Integer stageId, int pageIndex, int pageSize) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();

        // Base query construction
        String query = "SELECT eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.ErrorDescription, eh.Duration,\n" +
                "e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName, d.DepartmentName\n" +
                "FROM ErrorHistory eh\n" +
                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n" +
                "JOIN Stages s ON s.StageID = eh.StageID\n" +
                "LEFT JOIN ProductionLines l ON l.LineID = s.LineID\n" +
                "LEFT JOIN Rooms r ON l.RoomID = r.RoomID\n" +
                "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID\n" +
                "WHERE 1 = 1";

        // Adding filters based on input parameters
        if (EquipmentCode != null && !EquipmentCode.isEmpty()) {
            query += " AND e.EquipmentCode = ?";
        }
        if (depId != null) {
            query += " AND d.DepartmentID = ?";
        }
        if (lineCode != null) {
            query += " AND l.LineID = ?";
        }
        if (stageId != null) {
            query += " AND s.StageID = ?";
        }
        query += " ORDER BY eh.EndTime DESC";

        // Pagination logic
        query += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        // Creating connection and statement
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            int index = 1;

            // Setting dynamic parameters for the query
            if (EquipmentCode != null && !EquipmentCode.isEmpty()) {
                stmt.setString(index++, EquipmentCode);
            }
            if (depId != null) {
                stmt.setInt(index++, depId);
            }
            if (lineCode != null) {
                stmt.setInt(index++, lineCode);
            }
            if (stageId != null) {
                stmt.setInt(index++, stageId);
            }

            // Setting pagination parameters
            stmt.setInt(index++, (pageIndex - 1) * pageSize);
            stmt.setInt(index++, pageSize);

            // Executing query and processing results
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ErrorHistory eh = new ErrorHistory();

                // Extracting data from result set and populating ErrorHistory object
                eh.setContent(rs.getString("ErrorDescription"));
                eh.setStartDate(rs.getTimestamp("StartTime"));
                eh.setEndDate(rs.getTimestamp("EndTime"));
                eh.setDuration(rs.getInt("Duration"));
                eh.setEquipmentCode(rs.getString("EquipmentCode"));
                eh.setEquipmentName(rs.getString("EquipmentName"));
                eh.setStageName(rs.getString("StageName"));
                eh.setLineName(rs.getString("LineName"));
                eh.setDepartmentName(rs.getString("DepartmentName"));
                // Adding to the list
                list.add(eh);
            }
        }

        return list;
    }

    public List<ErrorHistory> searchChartByECode(String EquipmentCode) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();

        // Base query construction
        String query = "SELECT \n" +
                "    e.EquipmentCode,\n" +
                "    eh.ErrorDescription,\n" +
                "    eh.StartTime,\n" +
                "    eh.EndTime,\n" +
                "    DateDiff(MINUTE, eh.StartTime, eh.EndTime) AS Duration,\n" +
                "    YEAR(eh.StartTime) AS ErrorYear,\n" +
                "    MONTH(eh.StartTime) AS ErrorMonth,\n" +
                "    DAY(eh.StartTime) AS ErrorDay\n" +
                "FROM ErrorHistory eh\n" +
                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n" +
                "WHERE e.EquipmentCode = ?\n" +
                "ORDER BY eh.StartTime;\n";

        // Creating connection and statement
        try {
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setString(1, EquipmentCode);
            // Executing query and processing results
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ErrorHistory eh = new ErrorHistory();

                // Extracting data from result set and populating ErrorHistory object
                eh.setEquipmentCode(rs.getString("EquipmentCode"));
                eh.setContent(rs.getString("ErrorDescription"));
                eh.setStartDate(rs.getTimestamp("StartTime"));
                eh.setEndDate(rs.getTimestamp("EndTime"));
                eh.setDuration(rs.getInt("Duration"));
                eh.setYear(rs.getInt("ErrorYear"));
                eh.setMonth(rs.getInt("ErrorMonth"));
                eh.setDay(rs.getInt("ErrorDay"));
                // Adding to the list
                list.add(eh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

//    public List<ErrorHistory> searchChart(String EquipmentCode, Integer depId, Integer lineId, Integer stageId) throws SQLException {
//        List<ErrorHistory> list = new ArrayList<>();
//
//        String query = "SELECT \n" +
//        	    " e.EquipmentCode,\n" +
//        	    " eh.ErrorDescription,\n" +
//        	    " eh.StartTime,\n" +
//        	    " eh.EndTime,\n" +
//        	    " DateDiff(MINUTE, eh.StartTime, eh.EndTime) AS DowntimeMinutes,\n" +
//        	    " YEAR(eh.StartTime) AS ErrorYear,\n" +
//        	    " MONTH(eh.StartTime) AS ErrorMonth,\n" +
//        	    " DAY(eh.StartTime) AS ErrorDay,\n" +
//        	    " COUNT(eh.ErrorID) AS ErrorCount \n" +
//        	    "FROM ErrorHistory eh\n" +
//        	    "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n" +
//        	    "JOIN Stages s ON eh.StageID = s.StageID\n" +
//        	    "LEFT JOIN ProductionLines l ON s.LineID = l.LineID\n" +
//        	    "LEFT JOIN Rooms r ON l.RoomID = r.RoomID\n" +
//        	    "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID\n" +
//        	    "WHERE 1=1";
//
//        	// Adding filters dynamically based on provided parameters
//        	if (EquipmentCode != null && !EquipmentCode.isEmpty()) {
//        	    query += " AND e.EquipmentCode = ?";
//        	}
//        	if (depId != null) {
//        	    query += " AND d.DepartmentID = ?";
//        	}
//        	if (lineId != null) {
//        	    query += " AND l.LineID = ?";
//        	}
//        	if (stageId != null) {
//        	    query += " AND s.StageID = ?";
//        	}
//
//        	// Add GROUP BY clause
//        	query += " GROUP BY e.EquipmentCode, eh.ErrorDescription, eh.StartTime, eh.EndTime, " +
//        	         "YEAR(eh.StartTime), MONTH(eh.StartTime), DAY(eh.StartTime) " +
//        	         "ORDER BY eh.StartTime DESC;";
//
//        try (Connection connection = getConnection();
//             PreparedStatement stmt = connection.prepareStatement(query)) {
//
//            int paramIndex = 1;
//
//            // Set the values for the parameters dynamically
//            if (EquipmentCode != null && !EquipmentCode.isEmpty()) {
//                stmt.setString(paramIndex++, EquipmentCode);
//            }
//            if (depId != null) {
//                stmt.setInt(paramIndex++, depId);
//            }
//            if (lineId != null) {
//                stmt.setInt(paramIndex++, lineId);
//            }
//            if (stageId != null) {
//                stmt.setInt(paramIndex++, stageId);
//            }
//
//            ResultSet rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                ErrorHistory eh = new ErrorHistory();
//
//                // Populate the ErrorHistory object from the result set
//                eh.setEquipmentCode(rs.getString("EquipmentCode"));
//                eh.setContent(rs.getString("ErrorDescription"));
//                eh.setStartDate(rs.getTimestamp("StartTime"));
//                eh.setEndDate(rs.getTimestamp("EndTime"));
//                eh.setDuration(rs.getInt("DowntimeMinutes"));
//                eh.setYear(rs.getInt("ErrorYear"));
//                eh.setMonth(rs.getInt("ErrorMonth"));
//                eh.setDay(rs.getInt("ErrorDay"));
//                eh.setErrorCount(rs.getInt("ErrorCount"));
//
//                list.add(eh);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return list;
//    }
    
    public List<ErrorHistory> searchByDateRange(String deviceCode, Integer depId, Integer lineId, Integer stageId, Date startDate, Date endDate, int pageIndex, int pageSize) throws SQLException {
        List<ErrorHistory> detailedList = new ArrayList<>();
        Map<String, Integer> errorSummaryMap = new HashMap<>();

        // Base SQL query for detailed error history
        String detailQuery = "SELECT eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration, " +
            "e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName, d.DepartmentName " +
            "FROM ErrorHistory eh " +
            "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID " +
            "JOIN Stages s ON eh.StageID = s.StageID " +
            "LEFT JOIN ProductionLines l ON s.LineID = l.LineID " +
            "LEFT JOIN Rooms r ON l.RoomID = r.RoomID " +
            "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID " +
            "WHERE 1=1 ";

        // Adding filters dynamically based on provided parameters
        if (deviceCode != null && !deviceCode.isEmpty()) {
            detailQuery += " AND e.EquipmentCode = ?";
        }
        if (depId != null) {
            detailQuery += " AND d.DepartmentID = ?";
        }
        if (lineId != null) {
            detailQuery += " AND l.LineID = ?";
        }
        if (stageId != null) {
            detailQuery += " AND s.StageID = ?";
        }

        // Date filtering
        if (startDate != null && endDate != null) {
            detailQuery += " AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?";
        }

        // Pagination logic
        detailQuery += " ORDER BY eh.StartTime DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = getConnection(); PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
            int paramIndex = 1;

            // Set the dynamic parameters
            if (deviceCode != null && !deviceCode.isEmpty()) {
                detailStmt.setString(paramIndex++, deviceCode);
            }
            if (depId != null) {
                detailStmt.setInt(paramIndex++, depId);
            }
            if (lineId != null) {
                detailStmt.setInt(paramIndex++, lineId);
            }
            if (stageId != null) {
                detailStmt.setInt(paramIndex++, stageId);
            }

            // Set date parameters if applicable
            if (startDate != null && endDate != null) {
                detailStmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
                detailStmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }

            // Set pagination parameters
            detailStmt.setInt(paramIndex++, (pageIndex - 1) * pageSize); // Offset
            detailStmt.setInt(paramIndex++, pageSize); // Fetch size

            // Execute detailed query and populate the list
            try (ResultSet rs = detailStmt.executeQuery()) {
                while (rs.next()) {
                    ErrorHistory eh = new ErrorHistory();
                    eh.setContent(rs.getString("ErrorDescription"));
                    eh.setStartDate(rs.getTimestamp("StartTime"));
                    eh.setEndDate(rs.getTimestamp("EndTime"));
                    eh.setDuration(rs.getInt("Duration"));
                    eh.setEquipmentCode(rs.getString("EquipmentCode"));
                    eh.setEquipmentName(rs.getString("EquipmentName"));
                    eh.setStageName(rs.getString("StageName"));
                    eh.setLineName(rs.getString("LineName"));
                    eh.setDepartmentName(rs.getString("DepartmentName"));

                    detailedList.add(eh);

                    // Increment the error count in the summary map
                    String equipmentCode = rs.getString("EquipmentCode");
                    errorSummaryMap.put(equipmentCode, errorSummaryMap.getOrDefault(equipmentCode, 0) + 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        // Create summary message
        StringBuilder summaryMessage = new StringBuilder();
        for (Map.Entry<String, Integer> entry : errorSummaryMap.entrySet()) {
            summaryMessage.append(entry.getKey()).append(": ").append(entry.getValue()).append(" error(s), ");
        }

        // Remove the trailing comma and space if necessary
        if (summaryMessage.length() > 0) {
            summaryMessage.setLength(summaryMessage.length() - 2); // Trim last comma and space
        }

        // Print or return the summary if needed
        System.out.println(summaryMessage.toString()); // or return it

        return detailedList;
    }


    public List<ErrorHistory> searchChart(String equipmentCode, Integer depId, Integer lineId, Integer stageId, Date startDate, Date endDate) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();

        // Base SQL query
        String query = "SELECT " +
                       " e.EquipmentCode, " +
                       " eh.ErrorDescription, " +
                       " eh.StartTime, " +
                       " eh.EndTime, " +
                       " DATEDIFF(MINUTE, eh.StartTime, eh.EndTime) AS DowntimeMinutes, " +
                       " YEAR(eh.StartTime) AS ErrorYear, " +
                       " MONTH(eh.StartTime) AS ErrorMonth, " +
                       " DAY(eh.StartTime) AS ErrorDay, " +
                       " COUNT(eh.ErrorID) AS ErrorCount " +
                       " FROM ErrorHistory eh " +
                       " JOIN Equipment e ON e.EquipmentID = eh.EquipmentID " +
                       " JOIN Stages s ON eh.StageID = s.StageID " +
                       " LEFT JOIN ProductionLines l ON s.LineID = l.LineID " +
                       " LEFT JOIN Rooms r ON l.RoomID = r.RoomID " +
                       " LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID " +
                       " WHERE 1=1 ";

        // Adding filters dynamically based on provided parameters
        if (equipmentCode != null && !equipmentCode.isEmpty()) {
            query += " AND e.EquipmentCode = ?";
        }
        if (depId != null) {
            query += " AND d.DepartmentID = ?";
        }
        if (lineId != null) {
            query += " AND l.LineID = ?";
        }
        if (stageId != null) {
            query += " AND s.StageID = ?";
        }
        // Include date filters if provided
        if (startDate != null && endDate != null) {
            query += " AND eh.StartTime >= ? AND eh.EndTime <= ?";
        }

        // Add GROUP BY clause
        query += " GROUP BY e.EquipmentCode, eh.ErrorDescription, eh.StartTime, eh.EndTime, " +
                 " YEAR(eh.StartTime), MONTH(eh.StartTime), DAY(eh.StartTime) " +
                 " ORDER BY eh.StartTime ASC;";

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            int paramIndex = 1;

            // Set the values for the parameters dynamically
            if (equipmentCode != null && !equipmentCode.isEmpty()) {
                stmt.setString(paramIndex++, equipmentCode);
            }
            if (depId != null) {
                stmt.setInt(paramIndex++, depId);
            }
            if (lineId != null) {
                stmt.setInt(paramIndex++, lineId);
            }
            if (stageId != null) {
                stmt.setInt(paramIndex++, stageId);
            }
            // Set the date parameters if provided
            if (startDate != null && endDate != null) {
                stmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
                stmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }

            // Execute the query
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ErrorHistory eh = new ErrorHistory();

                // Populate the ErrorHistory object from the result set
                eh.setEquipmentCode(rs.getString("EquipmentCode"));
                eh.setContent(rs.getString("ErrorDescription"));
                eh.setStartDate(rs.getTimestamp("StartTime"));
                eh.setEndDate(rs.getTimestamp("EndTime"));
                eh.setDuration(rs.getInt("DowntimeMinutes"));
                eh.setYear(rs.getInt("ErrorYear"));
                eh.setMonth(rs.getInt("ErrorMonth"));
                eh.setDay(rs.getInt("ErrorDay"));
                eh.setErrorCount(rs.getInt("ErrorCount"));

                list.add(eh);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // It may be helpful to rethrow the exception after logging
        }

        return list;
    }

    public List<ErrorHistory> searchHourByDate(String deviceCode, Integer depId, Integer lineId, Integer stageId, Date targetDate, int pageIndex, int pageSize) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();

        // Base SQL query for detailed error history
        String detailQuery = "SELECT eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration, " +
            "e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName, d.DepartmentName " +
            "FROM ErrorHistory eh " +
            "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID " +
            "JOIN Stages s ON eh.StageID = s.StageID " +
            "LEFT JOIN ProductionLines l ON s.LineID = l.LineID " +
            "LEFT JOIN Rooms r ON l.RoomID = r.RoomID " +
            "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID " +
            "WHERE 1=1 ";

        // Adding filters dynamically based on provided parameters
        if (deviceCode != null && !deviceCode.isEmpty()) {
            detailQuery += " AND e.EquipmentCode = ?";
        }
        if (depId != null) {
            detailQuery += " AND d.DepartmentID = ?";
        }
        if (lineId != null) {
            detailQuery += " AND l.LineID = ?";
        }
        if (stageId != null) {
            detailQuery += " AND s.StageID = ?";
        }

        // Date filtering
        if (targetDate != null) {
            detailQuery += " AND CAST(eh.StartTime AS DATE)= ?";
        }

        // Pagination logic
        detailQuery += " ORDER BY eh.StartTime DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = getConnection(); PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
            int paramIndex = 1;

            // Set the dynamic parameters
            if (deviceCode != null && !deviceCode.isEmpty()) {
                detailStmt.setString(paramIndex++, deviceCode);
            }
            if (depId != null) {
                detailStmt.setInt(paramIndex++, depId);
            }
            if (lineId != null) {
                detailStmt.setInt(paramIndex++, lineId);
            }
            if (stageId != null) {
                detailStmt.setInt(paramIndex++, stageId);
            }

            // Set date parameters if applicable
            if (targetDate != null) {
                detailStmt.setDate(paramIndex++, new java.sql.Date(targetDate.getTime()));
            }

            // Set pagination parameters
            detailStmt.setInt(paramIndex++, (pageIndex - 1) * pageSize); // Offset
            detailStmt.setInt(paramIndex++, pageSize); // Fetch size

            // Execute detailed query and populate the list
            try (ResultSet rs = detailStmt.executeQuery()) {
                while (rs.next()) {
                    ErrorHistory eh = new ErrorHistory();
                    eh.setContent(rs.getString("ErrorDescription"));
                    eh.setStartDate(rs.getTimestamp("StartTime"));
                    eh.setEndDate(rs.getTimestamp("EndTime"));
                    eh.setDuration(rs.getInt("Duration"));
                    eh.setEquipmentCode(rs.getString("EquipmentCode"));
                    eh.setEquipmentName(rs.getString("EquipmentName"));
                    eh.setStageName(rs.getString("StageName"));
                    eh.setLineName(rs.getString("LineName"));
                    eh.setDepartmentName(rs.getString("DepartmentName"));

                    list.add(eh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return list;
    }

    public List<ErrorHistory> searchByHour(String deviceCode, String depId, String lineId, String stageId, String specificDate) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();
        String query = "SELECT \n" +
        	    " e.EquipmentCode,\n" +
        	    " eh.ErrorDescription,\n" +
        	    " eh.StartTime,\n" +
        	    " eh.EndTime,\n" +
        	    " DateDiff(MINUTE, eh.StartTime, eh.EndTime) AS DowntimeMinutes,\n" +
        	    " YEAR(eh.StartTime) AS ErrorYear,\n" +
        	    " MONTH(eh.StartTime) AS ErrorMonth,\n" +
        	    " DAY(eh.StartTime) AS ErrorDay,\n" +
        	    " COUNT(eh.ErrorID) AS ErrorCount \n" +
        	    "FROM ErrorHistory eh\n" +
        	    "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n" +
        	    "JOIN Stages s ON eh.StageID = s.StageID\n" +
        	    "LEFT JOIN ProductionLines l ON s.LineID = l.LineID\n" +
        	    "LEFT JOIN Rooms r ON l.RoomID = r.RoomID\n" +
        	    "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID WHERE DATE(StartTime) = ?"; // Adjust for your needs

        try (Connection conn = getConnection(); PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, specificDate);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	  ErrorHistory eh = new ErrorHistory();

                  // Populate the ErrorHistory object from the result set
                  eh.setEquipmentCode(rs.getString("EquipmentCode"));
                  eh.setContent(rs.getString("ErrorDescription"));
                  eh.setStartDate(rs.getTimestamp("StartTime"));
                  eh.setEndDate(rs.getTimestamp("EndTime"));
                  eh.setDuration(rs.getInt("DowntimeMinutes"));
                  eh.setYear(rs.getInt("ErrorYear"));
                  eh.setMonth(rs.getInt("ErrorMonth"));
                  eh.setDay(rs.getInt("ErrorDay"));
                  eh.setErrorCount(rs.getInt("ErrorCount"));
                list.add(eh);
            }
        }
        return list;
    }


    public int getFilteredCounts(String equipmentCode, Integer lineCode, Integer stageId, String deviceName) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ErrorHistory eh JOIN Equipment e ON e.EquipmentID = eh.EquipmentID JOIN Stages s ON s.StageID = eh.StageID LEFT JOIN ProductionLines l ON l.LineID = s.LineID WHERE 1 = 1");

        // Add filtering conditions based on the provided parameters
        if (equipmentCode != null && !equipmentCode.isEmpty()) {
            sql.append(" AND e.EquipmentCode = ?");
        }
        if (lineCode != null) {
            sql.append(" AND l.LineID = ?");
        }
        if (stageId != null) {
            sql.append(" AND s.StageID = ?");
        }
        if (deviceName != null && !deviceName.isEmpty()) {
            sql.append(" AND e.EquipmentName LIKE ?");
        }

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (equipmentCode != null && !equipmentCode.isEmpty()) {
                pst.setString(index++, equipmentCode);
            }
            if (lineCode != null) {
                pst.setInt(index++, lineCode);
            }
            if (stageId != null) {
                pst.setInt(index++, stageId);
            }
            if (deviceName != null && !deviceName.isEmpty()) {
                pst.setString(index++, "%" + deviceName + "%");
            }

            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int getFilterCounts(String equipmentCode, Integer depId, Integer lineCode, Integer stageId, Date startDate, Date endDate) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ErrorHistory eh \n" +
                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID \n" +
                "JOIN Stages s ON s.StageID = eh.StageID \n" +
                "LEFT JOIN ProductionLines l ON l.LineID = s.LineID \n" +
                "LEFT JOIN Rooms r ON l.RoomID = r.RoomID \n" +
                "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID WHERE 1 = 1");

        // Add filtering conditions based on the provided parameters
        if (equipmentCode != null && !equipmentCode.isEmpty()) {
            sql.append(" AND e.EquipmentCode = ?");
        }
        if (depId != null) {
            sql.append(" AND d.DepartmentID = ?");
        }
        if (lineCode != null) {
            sql.append(" AND l.LineID = ?");
        }
        if (stageId != null) {
            sql.append(" AND s.StageID = ?");
        }
        
        // Include date filters if provided
        if (startDate != null && endDate != null) {
            sql.append(" AND eh.StartTime >= ? AND eh.EndTime <= ?");
        }

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (equipmentCode != null && !equipmentCode.isEmpty()) {
                pst.setString(index++, equipmentCode);
            }
            if (depId != null) {
                pst.setInt(index++, depId);
            }
            if (lineCode != null) {
                pst.setInt(index++, lineCode);
            }
            if (stageId != null) {
                pst.setInt(index++, stageId);
            }
         // Set the date parameters if provided
            if (startDate != null && endDate != null) {
                pst.setDate(index++, new java.sql.Date(startDate.getTime()));
                pst.setDate(index++, new java.sql.Date(endDate.getTime()));
            }
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    
    public int getFilterCounts(String equipmentCode, Integer depId, Integer lineCode, Integer stageId, Date targetDate) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ErrorHistory eh \n" +
                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID \n" +
                "JOIN Stages s ON s.StageID = eh.StageID \n" +
                "LEFT JOIN ProductionLines l ON l.LineID = s.LineID \n" +
                "LEFT JOIN Rooms r ON l.RoomID = r.RoomID \n" +
                "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID WHERE 1 = 1");

        // Add filtering conditions based on the provided parameters
        if (equipmentCode != null && !equipmentCode.isEmpty()) {
            sql.append(" AND e.EquipmentCode = ?");
        }
        if (depId != null) {
            sql.append(" AND d.DepartmentID = ?");
        }
        if (lineCode != null) {
            sql.append(" AND l.LineID = ?");
        }
        if (stageId != null) {
            sql.append(" AND s.StageID = ?");
        }
        
        // Include date filters if provided
        if (targetDate != null) {
            sql.append(" AND eh.StartTime = ?");
        }

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (equipmentCode != null && !equipmentCode.isEmpty()) {
                pst.setString(index++, equipmentCode);
            }
            if (depId != null) {
                pst.setInt(index++, depId);
            }
            if (lineCode != null) {
                pst.setInt(index++, lineCode);
            }
            if (stageId != null) {
                pst.setInt(index++, stageId);
            }
         // Set the date parameters if provided
            if (targetDate != null) {
                pst.setDate(index++, new java.sql.Date(targetDate.getTime()));
            }
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
    


//    public int getFilterCounts(String equipmentCode, Integer depId, Integer lineCode, Integer stageId) {
//        int count = 0;
//        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ErrorHistory eh \n" +
//                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID \n" +
//                "JOIN Stages s ON s.StageID = eh.StageID \n" +
//                "LEFT JOIN ProductionLines l ON l.LineID = s.LineID \n" +
//                "LEFT JOIN Rooms r ON l.RoomID = r.RoomID \n" +
//                "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID WHERE 1 = 1");
//
//        // Add filtering conditions based on the provided parameters
//        if (equipmentCode != null && !equipmentCode.isEmpty()) {
//            sql.append(" AND e.EquipmentCode = ?");
//        }
//        if (depId != null) {
//            sql.append(" AND d.DepartmentID = ?");
//        }
//        if (lineCode != null) {
//            sql.append(" AND l.LineID = ?");
//        }
//        if (stageId != null) {
//            sql.append(" AND s.StageID = ?");
//        }
//
//        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql.toString())) {
//            int index = 1;
//
//            if (equipmentCode != null && !equipmentCode.isEmpty()) {
//                pst.setString(index++, equipmentCode);
//            }
//            if (depId != null) {
//                pst.setInt(index++, depId);
//            }
//            if (lineCode != null) {
//                pst.setInt(index++, lineCode);
//            }
//            if (stageId != null) {
//                pst.setInt(index++, stageId);
//            }
//            ResultSet rs = pst.executeQuery();
//            if (rs.next()) {
//                count = rs.getInt(1);
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return count;
//    }

    public List<ErrorHistory> getErrorsByEquipmentId(int equipmentId) {
        List<ErrorHistory> errors = new ArrayList<>();

        String query = "SELECT ErrorID, ErrorDescription FROM ErrorHistory WHERE EquipmentID = ?";

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, equipmentId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ErrorHistory error = new ErrorHistory();
                error.setId(rs.getInt("ErrorID"));
                error.setContent(rs.getString("ErrorDescription"));
                errors.add(error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return errors;
    }

    public int getCounts() {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM ErrorHistory";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                count = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public void delete(int id) {
        String sql = "DELETE FROM ErrorHistory WHERE ErrorID = ?";

        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void add(int equipmentId, String errorDescription, Timestamp startTime, Timestamp endTime, int stageId) {
        String sql = "INSERT INTO ErrorHistory (EquipmentID, ErrorDescription, StartTime, EndTime, StageID) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, equipmentId);
            stmt.setString(2, errorDescription);
            stmt.setTimestamp(3, startTime);
            stmt.setTimestamp(4, endTime);
            stmt.setInt(5, stageId);  // Đảm bảo stageId được thêm vào đúng cột trong bảng
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addImport(ErrorHistory eh) {
        String sql = "INSERT INTO ErrorHistory (EquipmentID, ErrorDescription, StartTime, EndTime, StageID) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, eh.getEquipmentId());
            stmt.setString(2, eh.getContent());
            stmt.setTimestamp(3, eh.getStartDate());
            stmt.setTimestamp(4, eh.getEndDate());
            stmt.setInt(5, eh.getStageId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void update(ErrorHistory eh) throws SQLException {
        String sql = "UPDATE [dbo].[ErrorHistory]\n" +
                "   SET [EquipmentID] = ?\n" +
                "      ,[ErrorDescription] = ?\n" +
                "      ,[StartTime] = ?\n" +
                "      ,[EndTime] = ?\n" +
                "      ,[StageID] = ?\n" +
                " WHERE ErrorID = ?";

        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setInt(1, eh.getEquipmentId());
            stmt.setString(2, eh.getContent());
            stmt.setTimestamp(3, eh.getStartDate());
            stmt.setTimestamp(4, eh.getEndDate());
            stmt.setInt(5, eh.getStageId());
            stmt.setInt(6, eh.getId());
            stmt.executeUpdate();
        }
    }
    
    
    public List<ChartData> fetchDailyData(Date startDate, Date endDate) {
        List<ChartData> chartDataList = new ArrayList<>();
        String query = "SELECT CAST(StartTime AS DATE) AS date, SUM(Duration) AS totalDuration " +
                       "FROM ErrorHistory " +
                       "WHERE StartTime BETWEEN ? AND ? " +
                       "GROUP BY CAST(StartTime AS DATE)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(startDate.getTime()));
            stmt.setDate(2, new java.sql.Date(endDate.getTime()));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChartData data = new ChartData();
                    data.setDate(rs.getDate("date").toString()); // Format as required
                    data.setTotalDuration(rs.getInt("totalDuration"));
                    chartDataList.add(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chartDataList;
    }

    public List<ChartData> fetchHourlyData(Date specificDate) {
        List<ChartData> chartDataList = new ArrayList<>();
        String query = "SELECT DATEPART(HOUR, StartTime) AS hour, SUM(Duration) AS totalDuration " +
                       "FROM ErrorHistory " +
                       "WHERE CAST(StartTime AS DATE) = ? " +
                       "GROUP BY DATEPART(HOUR, StartTime)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setDate(1, new java.sql.Date(specificDate.getTime()));
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChartData data = new ChartData();
                    data.setHour(rs.getInt("hour"));
                    data.setTotalDuration(rs.getInt("totalDuration"));
                    chartDataList.add(data);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chartDataList;
    }

    
 // Get EquipmentID by EquipmentCode
    public int getEquipmentIDByCode(String code) {
        int equipmentId = -1;
        String sql = "SELECT e.EquipmentID FROM Equipment e  WHERE e.EquipmentCode = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setString(1, code);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    equipmentId = rs.getInt("EquipmentID"); 
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
        return equipmentId;
    }
    
    public int getStageIDByStageName(String name, String code) {
        int stageID = -1;
        String sql = "SELECT s.StageID FROM Stages s\r\n"
        		+ "JOIN Equipment e ON s.StageID = e.StageID\r\n"
        		+ "WHERE s.StageName = ? AND e.EquipmentCode = ?";
        
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            
            pst.setString(1, name);
            pst.setString(2, code);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    stageID = rs.getInt("StageID"); 
                }
            }
            
        } catch (Exception e) {
            e.printStackTrace(); 
        }
        
        return stageID;
    }
    
    
 // Insert ErrorHistory record into the database
    public void insertErrorHistory(ErrorHistory errorHistory) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // Establish the database connection
            connection = getConnection(); // Replace this with your actual method to get the DB connection

            // Prepare the SQL query
            String sql = "INSERT INTO ErrorHistory (EquipmentID, ErrorDescription, StartTime, EndTime, StageID) VALUES (?, ?, ?, ?, ?)";

            // Create the PreparedStatement
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, errorHistory.getEquipmentId());
            preparedStatement.setString(2, errorHistory.getContent());
            preparedStatement.setTimestamp(3, new Timestamp(errorHistory.getStartDate().getTime())); 
            preparedStatement.setTimestamp(4, new Timestamp(errorHistory.getEndDate().getTime())); 
            preparedStatement.setInt(5, errorHistory.getStageId());

            // Execute the insert statement
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace(); 
        } finally {
            try {
                if (preparedStatement != null) preparedStatement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    
    public static void main(String[] args) {
        ErrorHistoryDAO dao = new ErrorHistoryDAO();
//        List<ErrorHistory> list = dao.getErrorHistory(1, 10);
//        for (ErrorHistory line : list) {
//            System.out.println(line);
//        }
        int id = dao.getStageIDByStageName("Preparation Stage", "EQ003");
       System.out.print(id);
    }

}
