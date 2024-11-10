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

        String query = "SELECT eh.*, st.TypeName, e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName\n" +
                "FROM ErrorHistory eh\n" +
        		" JOIN StopType st ON eh.TypeID = st.TypeID\n" +
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
                    eh.setTypeName(resultSet.getString("TypeName"));
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
   

    public List<ErrorHistory> search(String EquipmentCode, Integer lineCode, Integer stageId, String deviceName, Integer typeId, int pageIndex, int pageSize, String sortByDate) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();

        // Base query construction
        String query = "SELECT \r\n"
        		+ "    eh.*, \r\n"
        		+ "    st.TypeName, \r\n"
        		+ "    e.EquipmentCode, \r\n"
        		+ "    e.EquipmentName,\r\n"
        		+ "    s.StageName, \r\n"
        		+ "    l.LineName, \r\n"
        		+ "    d.DepartmentName\r\n"
        		+ "FROM ErrorHistory eh\r\n"
        		+ "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
        		+ "LEFT JOIN Stages s ON eh.StageID = s.StageID\r\n"
        		+ "LEFT JOIN StopType st ON eh.TypeID = st.TypeID\r\n"
        		+ "LEFT JOIN ProductionLines l ON eh.LineID = l.LineID\r\n"
        		+ "LEFT JOIN Rooms r ON l.RoomID = r.RoomID\r\n"
        		+ "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID\r\n"
        		+ "WHERE 1=1";

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
        if (typeId != null) {
            query += " AND st.TypeID = ?";
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
            if (typeId != null) {
                stmt.setInt(index++, typeId);
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
                eh.setTypeName(rs.getString("TypeName"));
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

        String query = "SELECT \r\n"
        		+ "    eh.*, \r\n"
        		+ "    st.TypeName, \r\n"
        		+ "    e.EquipmentCode, \r\n"
        		+ "    e.EquipmentName,\r\n"
        		+ "    s.StageName, \r\n"
        		+ "    l.LineName, \r\n"
        		+ "    d.DepartmentName\r\n"
        		+ "FROM ErrorHistory eh\r\n"
        		+ "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
        		+ "LEFT JOIN Stages s ON eh.StageID = s.StageID\r\n"
        		+ "LEFT JOIN StopType st ON eh.TypeID = st.TypeID\r\n"
        		+ "LEFT JOIN ProductionLines l ON eh.LineID = l.LineID\r\n"
        		+ "LEFT JOIN Rooms r ON l.RoomID = r.RoomID\r\n"
        		+ "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID\r\n"
        		+ "ORDER BY eh.StartTime DESC\r\n"
        		+ "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

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
                    eh.setSlotId(resultSet.getInt("SlotID"));
                    eh.setTypeId(resultSet.getInt("TypeID"));
                    eh.setLineId(resultSet.getInt("LineID"));
                    eh.setTypeName(resultSet.getString("TypeName"));
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

    
//    public List<ErrorHistory> searchByDateRange(String deviceCode, Integer depId, Integer lineId, Integer stageId, Date startDate, Date endDate, int pageIndex, int pageSize) throws SQLException {
//        List<ErrorHistory> detailedList = new ArrayList<>();
//        Map<String, Integer> errorSummaryMap = new HashMap<>();
//
//        // Base SQL query for detailed error history
//        String detailQuery = "SELECT eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration, " +
//            "e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName, d.DepartmentName " +
//            "FROM ErrorHistory eh " +
//            "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID " +
//            "JOIN Stages s ON eh.StageID = s.StageID " +
//            "LEFT JOIN ProductionLines l ON s.LineID = l.LineID " +
//            "LEFT JOIN Rooms r ON l.RoomID = r.RoomID " +
//            "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID " +
//            "WHERE 1=1 ";
//
//        // Adding filters dynamically based on provided parameters
//        if (deviceCode != null && !deviceCode.isEmpty()) {
//            detailQuery += " AND e.EquipmentCode = ?";
//        }
//        if (depId != null) {
//            detailQuery += " AND d.DepartmentID = ?";
//        }
//        if (lineId != null) {
//            detailQuery += " AND l.LineID = ?";
//        }
//        if (stageId != null) {
//            detailQuery += " AND s.StageID = ?";
//        }
//
//        // Date filtering
//        if (startDate != null && endDate != null) {
//            detailQuery += " AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?";
//        }
//
//        // Pagination logic
//        detailQuery += " ORDER BY eh.StartTime DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
//
//        try (Connection connection = getConnection(); PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
//            int paramIndex = 1;
//
//            // Set the dynamic parameters
//            if (deviceCode != null && !deviceCode.isEmpty()) {
//                detailStmt.setString(paramIndex++, deviceCode);
//            }
//            if (depId != null) {
//                detailStmt.setInt(paramIndex++, depId);
//            }
//            if (lineId != null) {
//                detailStmt.setInt(paramIndex++, lineId);
//            }
//            if (stageId != null) {
//                detailStmt.setInt(paramIndex++, stageId);
//            }
//
//            // Set date parameters if applicable
//            if (startDate != null && endDate != null) {
//                detailStmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
//                detailStmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
//            }
//
//            // Set pagination parameters
//            detailStmt.setInt(paramIndex++, (pageIndex - 1) * pageSize); // Offset
//            detailStmt.setInt(paramIndex++, pageSize); // Fetch size
//
//            // Execute detailed query and populate the list
//            try (ResultSet rs = detailStmt.executeQuery()) {
//                while (rs.next()) {
//                    ErrorHistory eh = new ErrorHistory();
//                    eh.setContent(rs.getString("ErrorDescription"));
//                    eh.setStartDate(rs.getTimestamp("StartTime"));
//                    eh.setEndDate(rs.getTimestamp("EndTime"));
//                    eh.setDuration(rs.getInt("Duration"));
//                    eh.setEquipmentCode(rs.getString("EquipmentCode"));
//                    eh.setEquipmentName(rs.getString("EquipmentName"));
//                    eh.setStageName(rs.getString("StageName"));
//                    eh.setLineName(rs.getString("LineName"));
//                    eh.setDepartmentName(rs.getString("DepartmentName"));
//
//                    detailedList.add(eh);
//
//                    // Increment the error count in the summary map
//                    String equipmentCode = rs.getString("EquipmentCode");
//                    errorSummaryMap.put(equipmentCode, errorSummaryMap.getOrDefault(equipmentCode, 0) + 1);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw e;
//        }
//
//        // Create summary message
//        StringBuilder summaryMessage = new StringBuilder();
//        for (Map.Entry<String, Integer> entry : errorSummaryMap.entrySet()) {
//            summaryMessage.append(entry.getKey()).append(": ").append(entry.getValue()).append(" error(s), ");
//        }
//
//        // Remove the trailing comma and space if necessary
//        if (summaryMessage.length() > 0) {
//            summaryMessage.setLength(summaryMessage.length() - 2); // Trim last comma and space
//        }
//
//        // Print or return the summary if needed
//        System.out.println(summaryMessage.toString()); // or return it
//
//        return detailedList;
//    }
    
    
    public List<ErrorHistory> searchByDateRange(Integer lineId, Integer stageId, Date startDate, Date endDate, int pageIndex, int pageSize) throws SQLException {
        List<ErrorHistory> detailedList = new ArrayList<>();
        Map<String, Integer> errorSummaryMap = new HashMap<>();

        // Base SQL query for detailed error history
        String detailQuery = "SELECT eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration, " +
            "e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName, d.DepartmentName, st.TypeName " +
            "FROM ErrorHistory eh " +
            "LEFT JOIN StopType st ON eh.TypeID = st.TypeID " +
            "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID " +
            "LEFT JOIN Stages s ON eh.StageID = s.StageID " +
            "LEFT JOIN ProductionLines l ON eh.LineID = l.LineID " +
            "LEFT JOIN Rooms r ON l.RoomID = r.RoomID " +
            "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID " +
            "WHERE 1=1 ";

        
        if (lineId != null) {
            detailQuery += " AND l.LineID = ?";
        }
        if (stageId != null) {
            detailQuery += " AND eh.StageID = ?";
        }

        // Date filtering
        if (startDate != null && endDate != null) {
            detailQuery += " AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?";
        }

        // Pagination logic
        detailQuery += " ORDER BY eh.StartTime DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = getConnection(); PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
            int paramIndex = 1;

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
                    eh.setTypeName(rs.getString("TypeName"));

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
    
    
    public List<ErrorHistory> searchByShiftDateRange(Integer lineId, Integer stageId, Date startDate, Date endDate, int pageIndex, int pageSize) throws SQLException {
        List<ErrorHistory> detailedList = new ArrayList<>();
        Map<String, Integer> errorSummaryMap = new HashMap<>();

        // Base SQL query for detailed error history
        String detailQuery = "SELECT eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration, \r\n"
        		+ "e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName, d.DepartmentName, sh.ShiftName, st.TypeName\r\n"
        		+ "FROM ErrorHistory eh \r\n"
        		+ "LEFT JOIN StopType st ON eh.TypeID = st.TypeID\r\n"
        		+ "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
        		+ "LEFT JOIN Stages s ON eh.StageID = s.StageID \r\n"
        		+ "LEFT JOIN ProductionLines l ON eh.LineID = l.LineID \r\n"
        		+ "LEFT JOIN Rooms r ON l.RoomID = r.RoomID \r\n"
        		+ "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID \r\n"
        		+ "LEFT JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID \r\n"
        		+ "LEFT JOIN Shifts sh ON ss.ShiftID = sh.ShiftID \r\n"
        		+ "WHERE 1=1 ";

        
        if (lineId != null) {
            detailQuery += " AND l.LineID = ?";
        }
        if (stageId != null) {
            detailQuery += " AND eh.StageID = ?";
        }

        // Date filtering
        if (startDate != null && endDate != null) {
            detailQuery += " AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?";
        }

        // Pagination logic
        detailQuery += " ORDER BY eh.StartTime DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = getConnection(); PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
            int paramIndex = 1;

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
                    eh.setShiftName(rs.getString("ShiftName"));
                    eh.setTypeName(rs.getString("TypeName"));

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
    
    public List<ErrorHistory> searchHourByDate(Integer lineId, Integer stageId, Date targetDate, int pageIndex, int pageSize) throws SQLException {
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
                    eh.setTypeName(rs.getString("TypeName"));

                    list.add(eh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return list;
    }
    
    
    public List<ErrorHistory> searchHourByDateNew(Integer lineId, Integer stageId, Date targetDate, int pageIndex, int pageSize) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();

        // Base SQL query for detailed error history
        String detailQuery = "SELECT eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration, \r\n"
        		+ "e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName, d.DepartmentName, st.TypeName \r\n"
        		+ "FROM ErrorHistory eh \r\n"
        		+ "LEFT JOIN StopType st ON eh.TypeID = st.TypeID \r\n"
        		+ "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID \r\n"
        		+ "LEFT JOIN Stages s ON eh.StageID = s.StageID \r\n"
        		+ "LEFT JOIN ProductionLines l ON eh.LineID = l.LineID\r\n"
        		+ "LEFT JOIN Rooms r ON l.RoomID = r.RoomID\r\n"
        		+ "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID \r\n"
        		+ "WHERE 1=1 ";

        if (lineId != null) {
            detailQuery += " AND l.LineID = ?";
        }
        if (stageId != null) {
            detailQuery += " AND eh.StageID = ?";
        }

        // Date filtering
        if (targetDate != null) {
            detailQuery += " AND CAST(eh.StartTime AS DATE)= ?";
        }

        // Pagination logic
        detailQuery += " ORDER BY eh.StartTime DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = getConnection(); PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
            int paramIndex = 1;

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
                    eh.setDuration(rs.getDouble("Duration"));
                    eh.setEquipmentCode(rs.getString("EquipmentCode"));
                    eh.setEquipmentName(rs.getString("EquipmentName"));
                    eh.setStageName(rs.getString("StageName"));
                    eh.setLineName(rs.getString("LineName"));
                    eh.setDepartmentName(rs.getString("DepartmentName"));
                    eh.setTypeName(rs.getString("TypeName"));

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
    
    public List<ErrorHistory> searchByHour(String lineId, String stageId, String specificDate) throws SQLException {
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


    public int getFilteredCounts(String equipmentCode, Integer lineCode, Integer stageId, String deviceName, Integer typeId) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ErrorHistory eh \r\n"
        		+ "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
        		+ "LEFT JOIN Stages s ON eh.StageID = s.StageID\r\n"
        		+ "LEFT JOIN StopType st ON eh.TypeID = st.TypeID\r\n"
        		+ "LEFT JOIN ProductionLines l ON eh.LineID = l.LineID\r\n"
        		+ "LEFT JOIN Rooms r ON l.RoomID = r.RoomID\r\n"
        		+ "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID\r\n"
        		+ "WHERE 1=1");

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
        if (typeId != null) {
            sql.append(" AND st.TypeID = ?");
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
            if (typeId != null) {
                pst.setInt(index++, typeId);
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
                "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID \n" +
                "LEFT JOIN Stages s ON s.StageID = eh.StageID \n" +
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
    
    public int getFilterCounts(Integer lineCode, Integer stageId, Date startDate, Date endDate) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ErrorHistory eh \n" +
                "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID \n" +
                "LEFT JOIN Stages s ON s.StageID = eh.StageID \n" +
                "LEFT JOIN ProductionLines l ON l.LineID = s.LineID \n" +
                "LEFT JOIN Rooms r ON l.RoomID = r.RoomID \n" +
                "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID WHERE 1 = 1");
        
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
                "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID \n" +
                "LEFT JOIN Stages s ON s.StageID = eh.StageID \n" +
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
    

    public int getFilterCounts(Integer lineCode, Integer stageId, Date targetDate) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM ErrorHistory eh \n" +
                "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID \n" +
                "LEFT JOIN Stages s ON s.StageID = eh.StageID \n" +
                "LEFT JOIN ProductionLines l ON l.LineID = s.LineID \n" +
                "LEFT JOIN Rooms r ON l.RoomID = r.RoomID \n" +
                "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID WHERE 1 = 1");

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

            if (lineCode != null) {
                pst.setInt(index++, lineCode);
            }
            if (stageId != null) {
                pst.setInt(index++, stageId);
            }
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

//    public void add(int equipmentId, String errorDescription, Timestamp startTime, Timestamp endTime, int stageId) {
//        String sql = "INSERT INTO ErrorHistory (EquipmentID, ErrorDescription, StartTime, EndTime, StageID) VALUES (?, ?, ?, ?, ?)";
//        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, equipmentId);
//            stmt.setString(2, errorDescription);
//            stmt.setTimestamp(3, startTime);
//            stmt.setTimestamp(4, endTime);
//            stmt.setInt(5, stageId);  // Đảm bảo stageId được thêm vào đúng cột trong bảng
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
    
    public void add(Integer equipmentId, String errorDescription, Timestamp startTime, Timestamp endTime, Integer stageId, Integer typeId, Integer lineId) {
        StringBuilder sql = new StringBuilder("INSERT INTO ErrorHistory (ErrorDescription, StartTime, EndTime");
        
        if (equipmentId != null) sql.append(", EquipmentID");
        if (stageId != null) sql.append(", StageID");
        if (typeId != null) sql.append(", TypeID");
        if (lineId != null) sql.append(", LineID");
        
        sql.append(") VALUES (?, ?, ?"); // Base values for description, start time, end time
        
        if (equipmentId != null) sql.append(", ?");
        if (stageId != null) sql.append(", ?");
        if (typeId != null) sql.append(", ?");
        if (lineId != null) sql.append(", ?");
        
        sql.append(")");

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql.toString())) {
            // Set the first three mandatory parameters
            stmt.setString(1, errorDescription);
            stmt.setTimestamp(2, startTime);
            stmt.setTimestamp(3, endTime);
            
            int index = 4; // Start setting from the 4th position
            
            if (equipmentId != null) stmt.setInt(index++, equipmentId);
            if (stageId != null) stmt.setInt(index++, stageId);
            if (typeId != null) stmt.setInt(index++, typeId);
            if (lineId != null) stmt.setInt(index++, lineId);

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
//    public void insertErrorHistory(ErrorHistory errorHistory) {
//        Connection connection = null;
//        PreparedStatement preparedStatement = null;
//
//        try {
//            connection = getConnection();
//
//            String sql = "INSERT INTO ErrorHistory (EquipmentID, ErrorDescription, StartTime, EndTime, StageID, LineID, TypeID) VALUES (?, ?, ?, ?, ?, ?, ?)";
//
//            preparedStatement = connection.prepareStatement(sql);
//            preparedStatement.setInt(1, errorHistory.getEquipmentId());
//            preparedStatement.setString(2, errorHistory.getContent());
//            preparedStatement.setTimestamp(3, new Timestamp(errorHistory.getStartDate().getTime())); 
//            preparedStatement.setTimestamp(4, new Timestamp(errorHistory.getEndDate().getTime())); 
//            preparedStatement.setInt(5, errorHistory.getStageId());
//            preparedStatement.setInt(6, errorHistory.getLineId());
//            preparedStatement.setInt(7, errorHistory.getTypeId());
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException e) {
//            e.printStackTrace(); 
//        } finally {
//            try {
//                if (preparedStatement != null) preparedStatement.close();
//                if (connection != null) connection.close();
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//        }
//    }
    
    public void insertErrorHistory(ErrorHistory errorHistory) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            connection = getConnection();

            // Xây dựng truy vấn động
            StringBuilder sql = new StringBuilder("INSERT INTO ErrorHistory (");
            StringBuilder valuesPart = new StringBuilder("VALUES (");
            List<Object> parameters = new ArrayList<>();

            if (errorHistory.getEquipmentId() != null) {
                sql.append("EquipmentID, ");
                valuesPart.append("?, ");
                parameters.add(errorHistory.getEquipmentId());
            }
            if (errorHistory.getContent() != null) {
                sql.append("ErrorDescription, ");
                valuesPart.append("?, ");
                parameters.add(errorHistory.getContent());
            }
            if (errorHistory.getStartDate() != null) {
                sql.append("StartTime, ");
                valuesPart.append("?, ");
                parameters.add(new Timestamp(errorHistory.getStartDate().getTime()));
            }
            if (errorHistory.getEndDate() != null) {
                sql.append("EndTime, ");
                valuesPart.append("?, ");
                parameters.add(new Timestamp(errorHistory.getEndDate().getTime()));
            }
            if (errorHistory.getStageId() != null) {
                sql.append("StageID, ");
                valuesPart.append("?, ");
                parameters.add(errorHistory.getStageId());
            }
            if (errorHistory.getLineId() != null) {
                sql.append("LineID, ");
                valuesPart.append("?, ");
                parameters.add(errorHistory.getLineId());
            }
            if (errorHistory.getTypeId() != null) {
                sql.append("TypeID, ");
                valuesPart.append("?, ");
                parameters.add(errorHistory.getTypeId());
            }

            // Xóa dấu phẩy cuối cùng và đóng câu truy vấn
            sql.setLength(sql.length() - 2); // Xóa dấu ", "
            valuesPart.setLength(valuesPart.length() - 2); // Xóa dấu ", "
            sql.append(") ").append(valuesPart).append(")");

            preparedStatement = connection.prepareStatement(sql.toString());

            // Gán giá trị cho các tham số
            for (int i = 0; i < parameters.size(); i++) {
                preparedStatement.setObject(i + 1, parameters.get(i));
            }

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
    
    public void insertPartialErrorHistory(int lineId, Timestamp startDate, Timestamp endDate, String duration, int typeId) {
        // Implement database insert logic here for partial data
        String sql = "INSERT INTO ErrorHistory (LineID, StartTime, EndTime, Duration, TypeID) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            
            preparedStatement.setInt(1, lineId);
            preparedStatement.setTimestamp(2, startDate);
            preparedStatement.setTimestamp(3, endDate);
            preparedStatement.setString(4, duration);
            preparedStatement.setInt(5, typeId);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    
//    public List<ErrorHistory> searchByLineStageWorkingDay(Integer lineId, Integer stageId, Date startDate, Date endDate) throws SQLException {
//        List<ErrorHistory> list = new ArrayList<>();
//
//        String detailQuery = "SELECT "
//            + "CAST(eh.StartTime AS DATE) AS Day, "
//            + "SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, "
//            + "SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop, "
//            + "SUM(eh.Duration) AS TotalDuration, "
//            + "COUNT(eh.ErrorID) AS ErrorCount "
//            + "FROM ErrorHistory eh "
//            + "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID "
//            + "JOIN Stages s ON eh.StageID = s.StageID "
//            + "LEFT JOIN ProductionLines l ON s.LineID = l.LineID "
//            + "LEFT JOIN Rooms r ON l.RoomID = r.RoomID "
//            + "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID "
//            + "WHERE 1=1 ";
//
//        if (lineId != null) {
//            detailQuery += " AND l.LineID = ?";
//        }
//
//        if (stageId != null) {
//            detailQuery += " AND s.StageID = ?";
//        }
//
//        // Ensure space before AND in the conditional statements
//        if (startDate != null && endDate != null) {
//            detailQuery += " AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?";
//        }
//
//        detailQuery += " GROUP BY CAST(eh.StartTime AS DATE) ORDER BY Day ASC";
//
//        try (Connection connection = getConnection(); 
//             PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
//            
//            int paramIndex = 1;
//
//            if (lineId != null) {
//                detailStmt.setInt(paramIndex++, lineId);
//            }
//
//            if (stageId != null) {
//                detailStmt.setInt(paramIndex++, stageId);
//            }
//
//            if (startDate != null && endDate != null) {
//                detailStmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
//                detailStmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
//            }
//
//            try (ResultSet rs = detailStmt.executeQuery()) {
//                while (rs.next()) {
//                    ErrorHistory eh = new ErrorHistory();
//                    eh.setStartDate(rs.getTimestamp("Day")); // Update here to match the selected day
//                    eh.setLongTime(rs.getDouble("LongStop"));
//                    eh.setShortTime(rs.getDouble("ShortStop"));
//                    eh.setDuration(rs.getDouble("TotalDuration"));
//                    eh.setErrorCount(rs.getInt("ErrorCount"));
//                    list.add(eh);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw e;
//        }
//
//        return list;
//    }
    
    public List<ErrorHistory> searchByLineStageWorkingDay(Integer lineId, Integer stageId, Date startDate, Date endDate) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();

        StringBuilder detailQuery = new StringBuilder();
        detailQuery.append("SELECT \n")
                .append("    Day,\n")
                .append("    SUM(LongStop) AS LongStop,\n")
                .append("    SUM(ShortStop) AS ShortStop,\n")
                .append("    SUM(TotalDowntime) AS TotalDuration,\n")
                .append("    SUM(ShortCount) AS ShortCount,\n")
                .append("    SUM(LongCount) AS LongCount,\n")
                .append("    SUM(TotalDowntimeCount) AS ErrorCount,\n")
                .append("    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage\n")
                .append("FROM (\n")
                .append("    SELECT \n")
                .append("        CAST(eh.StartTime AS DATE) AS Day,\n")
                .append("        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, \n")
                .append("        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\n")
                .append("        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,\n")
                .append("        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS ShortCount,\n")
                .append("        COUNT(CASE WHEN eh.Duration > 5 THEN 1 END) AS LongCount,\n")
                .append("        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,\n")
                .append("        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency\n")
                .append("    FROM ErrorHistory eh\n")
                .append("    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n")
                .append("    JOIN Stages s ON s.StageID = eh.StageID\n")
                .append("    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID\n")
                .append("    JOIN Rooms r ON pl.RoomID = r.RoomID\n")
                .append("    JOIN Users u ON r.RoomID = u.RoomID\n")
                .append("    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID\n")
                .append("    WHERE 1=1\n");

        // Append line ID filter
        if (lineId != null) {
            detailQuery.append(" AND pl.LineID = ?"); // Fixed alias to 'pl'
        }

        // Append stage ID filter
        if (stageId != null) {
            detailQuery.append(" AND s.StageID = ?"); // No change needed here
        }

        // Append date range filter
        if (startDate != null && endDate != null) {
            detailQuery.append(" AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?");
        }

        detailQuery.append(" GROUP BY CAST(eh.StartTime AS DATE), ss.Duration")
                .append(") AS Summary ")
                .append("GROUP BY Day ORDER BY Day ASC");

        try (Connection connection = getConnection();
             PreparedStatement detailStmt = connection.prepareStatement(detailQuery.toString())) {
            
            int paramIndex = 1;

            if (lineId != null) {
                detailStmt.setInt(paramIndex++, lineId);
            }

            if (stageId != null) {
                detailStmt.setInt(paramIndex++, stageId);
            }

            if (startDate != null && endDate != null) {
                detailStmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
                detailStmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }

            try (ResultSet rs = detailStmt.executeQuery()) {
                while (rs.next()) {
                    ErrorHistory eh = new ErrorHistory();
                    eh.setStartDate(rs.getTimestamp("Day"));
                    eh.setLongTime(rs.getDouble("LongStop"));
                    eh.setShortTime(rs.getDouble("ShortStop"));
                    eh.setDuration(rs.getDouble("TotalDuration"));
                    eh.setShortCount(rs.getInt("ShortCount"));
                    eh.setLongCount(rs.getInt("LongCount"));
                    eh.setErrorCount(rs.getInt("ErrorCount"));
                    eh.setShortStopPercentage(rs.getDouble("ShortStopPercentage"));
                    list.add(eh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return list;
    }
    
    
    public List<Map<String, Object>> searchByLineStageWorkingDayNew(Integer lineId, Integer stageId, Date startDate, Date endDate) throws SQLException {
    	List<Map<String, Object>> downtimeList = new ArrayList<>();

        StringBuilder detailQuery = new StringBuilder();
        detailQuery.append("WITH MaxSlot AS (\r\n"
        		+ "    SELECT \r\n"
        		+ "        CAST(eh.StartTime AS DATE) AS Date,\r\n"
        		+ "        l.LineName, \r\n"
        		+ "        MAX(ss.SlotID) AS MaxSlotID\r\n"
        		+ "    FROM ShiftSlots ss\r\n"
        		+ "    JOIN ErrorHistory eh ON ss.SlotID = eh.SlotID\r\n"
        		+ "    JOIN ProductionLines l ON eh.LineID = l.LineID\r\n"
        		+ "    GROUP BY CAST(eh.StartTime AS DATE), l.LineName\r\n"
        		+ ")\r\n"
        		+ "SELECT\r\n"
        		+ "   Day,\r\n"
        		+ "   SUM(ShortStop) AS ShortStop,\r\n"
        		+ "   ROUND(SUM(EShort) / NULLIF(LatestSlot, 0) * 100, 2) AS ShortPercentage,\r\n"
        		+ "   SUM(ShortCount) AS ShortCount,\r\n"
        		+ "   SUM(LongStop) AS LongStop,\r\n"
        		+ "   ROUND(SUM(ELong) / NULLIF(LatestSlot, 0) * 100, 2) AS LongPercentage,\r\n"
        		+ "   SUM(LongCount) AS LongCount,\r\n"
        		+ "   SUM(PPStop) AS PPStop,\r\n"
        		+ "   ROUND(SUM(EPP) / NULLIF(LatestSlot, 0) * 100, 2) AS PPPercentage,\r\n"
        		+ "   SUM(PPCount) AS PPCount,\r\n"
        		+ "   SUM(DCCStop) AS DCCStop,\r\n"
        		+ "   ROUND(SUM(EDCC) / NULLIF(LatestSlot, 0) * 100, 2) AS DccPercentage,\r\n"
        		+ "   SUM(DCCCount) AS DCCCount,\r\n"
        		+ "   SUM(DMTDStop) AS DMTDStop,\r\n"
        		+ "   ROUND(SUM(EDMTD) / NULLIF(LatestSlot, 0) * 100, 2) AS DmtdPercentage,\r\n"
        		+ "   SUM(DMTDCount) AS DMTDCount,\r\n"
        		+ "   SUM(NRStop) AS NRStop,\r\n"
        		+ "   ROUND(SUM(ENR) / NULLIF(LatestSlot, 0) * 100, 2) AS NrPercentage,\r\n"
        		+ "   SUM(NRCount) AS NRCount,\r\n"
        		+ "   LatestSlot,\r\n"
        		+ "   SUM(TotalTime) AS TotalTime,\r\n"
        		+ "   SUM(TotalCounts) AS TotalCounts\r\n"
        		+ "FROM (\r\n"
        		+ "   SELECT \r\n"
        		+ "       CAST(eh.StartTime AS DATE) AS Day,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 1 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 1 THEN 1 END) AS ShortCount,\r\n"
        		+ "       SUM(CASE WHEN eh.TypeID = 2 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 2 THEN 1 END) AS LongCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 3 THEN eh.Duration ELSE 0 END) AS PPStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 3 THEN 1 END) AS PPCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 4 THEN eh.Duration ELSE 0 END) AS DMTDStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 4 THEN 1 END) AS DCCCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 5 THEN eh.Duration ELSE 0 END) AS DCCStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 5 THEN 1 END) AS DMTDCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 6 THEN eh.Duration ELSE 0 END) AS NRStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 6 THEN 1 END) AS NRCount,\r\n"
        		+ "       SUM(eh.Duration) AS TotalTime,\r\n"
        		+ "       COUNT(eh.ErrorID) AS TotalCounts,\r\n"
        		+ "	   ms.MaxSlotID AS LatestSlot,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 1 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EShort,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 2 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS ELong,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 3 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EPP,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 4 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EDMTD,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EDCC,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 6 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS ENR\r\n"
        		+ "    \r\n"
        		+ "   FROM ErrorHistory eh\r\n"
        		+ "   LEFT JOIN StopType st ON eh.TypeID = st.TypeID\r\n"
        		+ "   LEFT JOIN Stages s ON s.StageID = eh.StageID\r\n"
        		+ "   LEFT JOIN ProductionLines l ON eh.LineID = l.LineID\r\n"
        		+ "   LEFT JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID\r\n"
        		+ "   LEFT JOIN MaxSlot ms ON ms.LineName = l.LineName AND ms.Date = CAST(eh.StartTime AS DATE)\r\n"
        		+ "   WHERE 1=1\n");

        // Append line ID filter
        if (lineId != null) {
            detailQuery.append(" AND l.LineID = ?"); // Fixed alias to 'pl'
        }

        // Append stage ID filter
        if (stageId != null) {
            detailQuery.append(" AND eh.StageID = ?"); // No change needed here
        }

        // Append date range filter
        if (startDate != null && endDate != null) {
            detailQuery.append(" AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?");
        }

        detailQuery.append(" GROUP BY CAST(eh.StartTime AS DATE), ss.Duration, ms.MaxSlotID")
                .append(") AS Summary ")
                .append("GROUP BY Day, LatestSlot ORDER BY Day ASC");

        try (Connection connection = getConnection();
             PreparedStatement detailStmt = connection.prepareStatement(detailQuery.toString())) {
            
            int paramIndex = 1;

            if (lineId != null) {
                detailStmt.setInt(paramIndex++, lineId);
            }

            if (stageId != null) {
                detailStmt.setInt(paramIndex++, stageId);
            }

            if (startDate != null && endDate != null) {
                detailStmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
                detailStmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }

            try (ResultSet resultSet = detailStmt.executeQuery()) {
                while (resultSet.next()) {
                	Map<String, Object> downtimeData = new HashMap<>();

                    downtimeData.put("day", resultSet.getString("Day"));
                    downtimeData.put("shortStop", resultSet.getDouble("ShortStop"));
                    downtimeData.put("shortPercentage", resultSet.getDouble("ShortPercentage"));
                    downtimeData.put("shortCount", resultSet.getInt("ShortCount"));
                    downtimeData.put("longStop", resultSet.getDouble("LongStop"));
                    downtimeData.put("longPercentage", resultSet.getDouble("LongPercentage"));
                    downtimeData.put("longCount", resultSet.getInt("LongCount"));
                    downtimeData.put("ppStop", resultSet.getDouble("PPStop"));
                    downtimeData.put("ppPercentage", resultSet.getDouble("PPPercentage"));
                    downtimeData.put("ppCount", resultSet.getInt("PPCount"));
                    downtimeData.put("dccStop", resultSet.getDouble("DCCStop"));
                    downtimeData.put("dccPercentage", resultSet.getDouble("DccPercentage"));
                    downtimeData.put("dccCount", resultSet.getInt("DCCCount"));
                    downtimeData.put("dmtdStop", resultSet.getDouble("DMTDStop"));
                    downtimeData.put("dmtdPercentage", resultSet.getDouble("DmtdPercentage"));
                    downtimeData.put("dmtdCount", resultSet.getInt("DMTDCount"));
                    downtimeData.put("nrStop", resultSet.getDouble("NRStop"));
                    downtimeData.put("nrPercentage", resultSet.getDouble("NrPercentage"));
                    downtimeData.put("nrCount", resultSet.getInt("NRCount"));
                    downtimeData.put("latestSlot", resultSet.getDouble("LatestSlot"));
                    downtimeData.put("totalTime", resultSet.getDouble("TotalTime"));
                    downtimeData.put("totalCount", resultSet.getInt("TotalCounts"));
                    
                    downtimeList.add(downtimeData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return downtimeList;
    }
    
    
//    public List<ErrorHistory> searchByLineStageShift(Integer lineId, Integer stageId, Date startDate, Date endDate) throws SQLException {
//        List<ErrorHistory> list = new ArrayList<>();
//
//        String detailQuery = "SELECT \r\n"
//        		+ "    CAST(eh.StartTime AS DATE) AS Day,\r\n"
//        		+ "    sh.ShiftName, \r\n"
//        		+ "    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, \r\n"
//        		+ "    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop, \r\n"
//        		+ "    SUM(eh.Duration) AS TotalDuration, \r\n"
//        		+ "    COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS ShortCount, \r\n"
//        		+ "    COUNT(CASE WHEN eh.Duration > 5 THEN 1 END) AS LongCount, \r\n"
//        		+ "    COUNT(eh.ErrorID) AS ErrorCount\r\n"
//        		+ "FROM ErrorHistory eh\r\n"
//        		+ "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
//        		+ "JOIN Stages s ON eh.StageID = s.StageID\r\n"
//        		+ "LEFT JOIN ProductionLines l ON s.LineID = l.LineID\r\n"
//        		+ "LEFT JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID\r\n"
//        		+ "LEFT JOIN Shifts sh ON ss.ShiftID = sh.ShiftID\r\n"
//        		+ "WHERE 1=1";
//
//        if (lineId != null) {
//            detailQuery += " AND l.LineID = ?";
//        }
//
//        if (stageId != null) {
//            detailQuery += " AND s.StageID = ?";
//        }
//
//        // Ensure space before AND in the conditional statements
//        if (startDate != null && endDate != null) {
//            detailQuery += " AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?";
//        }
//
//        detailQuery += " GROUP BY CAST(eh.StartTime AS DATE), sh.ShiftName ORDER BY Day ASC";
//
//        try (Connection connection = getConnection(); 
//             PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
//            
//            int paramIndex = 1;
//
//            if (lineId != null) {
//                detailStmt.setInt(paramIndex++, lineId);
//            }
//
//            if (stageId != null) {
//                detailStmt.setInt(paramIndex++, stageId);
//            }
//
//            if (startDate != null && endDate != null) {
//                detailStmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
//                detailStmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
//            }
//
//            try (ResultSet rs = detailStmt.executeQuery()) {
//                while (rs.next()) {
//                    ErrorHistory eh = new ErrorHistory();
//                    eh.setStartDate(rs.getTimestamp("Day")); 
//                    eh.setShiftName(rs.getString("ShiftName")); 
//                    eh.setLongTime(rs.getDouble("LongStop"));
//                    eh.setShortTime(rs.getDouble("ShortStop"));
//                    eh.setDuration(rs.getDouble("TotalDuration"));
//                    eh.setShortCount(rs.getInt("ShortCount"));
//                    eh.setLongCount(rs.getInt("LongCount"));
//                    eh.setErrorCount(rs.getInt("ErrorCount"));
//                    list.add(eh);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw e;
//        }
//
//        return list;
//    }
    
    public List<ErrorHistory> searchByLineStageShift(Integer lineId, Integer stageId, Date startDate, Date endDate) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();

        StringBuilder detailQuery = new StringBuilder();
        detailQuery.append("SELECT \n")
                .append("    Day,\n")
                .append("    ShiftName,\n")
                .append("    SUM(LongStop) AS LongStop,\n")
                .append("    SUM(ShortStop) AS ShortStop,\n")
                .append("    SUM(TotalDowntime) AS TotalDuration,\n")
                .append("    SUM(ShortCount) AS ShortCount,\n")
                .append("    SUM(LongCount) AS LongCount,\n")
                .append("    SUM(TotalDowntimeCount) AS ErrorCount,\n")
                .append("    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage\n")
                .append("FROM (\n")
                .append("    SELECT \n")
                .append("        CAST(eh.StartTime AS DATE) AS Day,\n")
                .append("        sh.ShiftName AS ShiftName,\n")
                .append("        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, \n")
                .append("        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\n")
                .append("        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,\n")
                .append("        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS ShortCount,\n")
                .append("        COUNT(CASE WHEN eh.Duration > 5 THEN 1 END) AS LongCount,\n")
                .append("        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,\n")
                .append("        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency\n")
                .append("    FROM ErrorHistory eh\n")
                .append("    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n")
                .append("    JOIN Stages s ON s.StageID = eh.StageID\n")
                .append("    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID\n")
                .append("    JOIN Rooms r ON pl.RoomID = r.RoomID\n")
                .append("    JOIN Users u ON r.RoomID = u.RoomID\n")
                .append("    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID\n")
                .append("    LEFT JOIN Shifts sh ON ss.ShiftID = sh.ShiftID\n")
                .append("    WHERE 1=1\n");

        // Append line ID filter
        if (lineId != null) {
            detailQuery.append(" AND pl.LineID = ?"); // Fixed alias to 'pl'
        }

        // Append stage ID filter
        if (stageId != null) {
            detailQuery.append(" AND s.StageID = ?"); // No change needed here
        }

        // Append date range filter
        if (startDate != null && endDate != null) {
            detailQuery.append(" AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?");
        }

        detailQuery.append(" GROUP BY CAST(eh.StartTime AS DATE), sh.ShiftName, ss.Duration")
                .append(") AS Summary ")
                .append("GROUP BY Day, ShiftName ORDER BY Day ASC");

        try (Connection connection = getConnection(); 
             PreparedStatement detailStmt = connection.prepareStatement(detailQuery.toString())) {
            
            int paramIndex = 1;

            if (lineId != null) {
                detailStmt.setInt(paramIndex++, lineId);
            }

            if (stageId != null) {
                detailStmt.setInt(paramIndex++, stageId);
            }

            if (startDate != null && endDate != null) {
                detailStmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
                detailStmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }

            try (ResultSet rs = detailStmt.executeQuery()) {
                while (rs.next()) {
                    ErrorHistory eh = new ErrorHistory();
                    eh.setStartDate(rs.getTimestamp("Day")); 
                    eh.setShiftName(rs.getString("ShiftName")); 
                    eh.setLongTime(rs.getDouble("LongStop"));
                    eh.setShortTime(rs.getDouble("ShortStop"));
                    eh.setDuration(rs.getDouble("TotalDuration"));
                    eh.setShortCount(rs.getInt("ShortCount"));
                    eh.setLongCount(rs.getInt("LongCount"));
                    eh.setErrorCount(rs.getInt("ErrorCount"));
                    eh.setShortStopPercentage(rs.getDouble("ShortStopPercentage"));
                    list.add(eh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return list;
    }
    
    
    public List<Map<String, Object>> searchByLineStageShiftNew(Integer lineId, Integer stageId, Date startDate, Date endDate) throws SQLException {
    	 List<Map<String, Object>> downtimeList = new ArrayList<>();

        StringBuilder detailQuery = new StringBuilder();
        detailQuery.append("WITH MaxSlot AS (\r\n"
        		+ "    SELECT \r\n"
        		+ "        CAST(eh.StartTime AS DATE) AS Date,\r\n"
        		+ "        l.LineName, \r\n"
        		+ "        MAX(ss.SlotID) AS MaxSlotID\r\n"
        		+ "    FROM ShiftSlots ss\r\n"
        		+ "    JOIN ErrorHistory eh ON ss.SlotID = eh.SlotID\r\n"
        		+ "    JOIN ProductionLines l ON eh.LineID = l.LineID\r\n"
        		+ "    GROUP BY CAST(eh.StartTime AS DATE), l.LineName\r\n"
        		+ ")\r\n"
        		+ "SELECT\r\n"
        		+ "   Day,\r\n"
        		+ "   ShiftName,\r\n"
        		+ "   SUM(ShortStop) AS ShortStop,\r\n"
        		+ "   ROUND(SUM(EShort) / NULLIF(LatestSlot, 0) * 100, 2) AS ShortPercentage,\r\n"
        		+ "   SUM(ShortCount) AS ShortCount,\r\n"
        		+ "   SUM(LongStop) AS LongStop,\r\n"
        		+ "   ROUND(SUM(ELong) / NULLIF(LatestSlot, 0) * 100, 2) AS LongPercentage,\r\n"
        		+ "   SUM(LongCount) AS LongCount,\r\n"
        		+ "   SUM(PPStop) AS PPStop,\r\n"
        		+ "   ROUND(SUM(EPP) / NULLIF(LatestSlot, 0) * 100, 2) AS PPPercentage,\r\n"
        		+ "   SUM(PPCount) AS PPCount,\r\n"
        		+ "   SUM(DCCStop) AS DCCStop,\r\n"
        		+ "   ROUND(SUM(EDCC) / NULLIF(LatestSlot, 0) * 100, 2) AS DccPercentage,\r\n"
        		+ "   SUM(DCCCount) AS DCCCount,\r\n"
        		+ "   SUM(DMTDStop) AS DMTDStop,\r\n"
        		+ "   ROUND(SUM(EDMTD) / NULLIF(LatestSlot, 0) * 100, 2) AS DmtdPercentage,\r\n"
        		+ "   SUM(DMTDCount) AS DMTDCount,\r\n"
        		+ "   SUM(NRStop) AS NRStop,\r\n"
        		+ "   ROUND(SUM(ENR) / NULLIF(LatestSlot, 0) * 100, 2) AS NrPercentage,\r\n"
        		+ "   SUM(NRCount) AS NRCount,\r\n"
        		+ "   LatestSlot,\r\n"
        		+ "   SUM(TotalTime) AS TotalTime,\r\n"
        		+ "   SUM(TotalCounts) AS TotalCounts\r\n"
        		+ "FROM (\r\n"
        		+ "   SELECT \r\n"
        		+ "       CAST(eh.StartTime AS DATE) AS Day,\r\n"
        		+ "	   sh.ShiftName AS ShiftName,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 1 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 1 THEN 1 END) AS ShortCount,\r\n"
        		+ "       SUM(CASE WHEN eh.TypeID = 2 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 2 THEN 1 END) AS LongCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 3 THEN eh.Duration ELSE 0 END) AS PPStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 3 THEN 1 END) AS PPCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 4 THEN eh.Duration ELSE 0 END) AS DMTDStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 4 THEN 1 END) AS DCCCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 5 THEN eh.Duration ELSE 0 END) AS DCCStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 5 THEN 1 END) AS DMTDCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 6 THEN eh.Duration ELSE 0 END) AS NRStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 6 THEN 1 END) AS NRCount,\r\n"
        		+ "       SUM(eh.Duration) AS TotalTime,\r\n"
        		+ "       COUNT(eh.ErrorID) AS TotalCounts,\r\n"
        		+ "	   ms.MaxSlotID AS LatestSlot,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 1 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EShort,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 2 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS ELong,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 3 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EPP,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 4 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EDMTD,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EDCC,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 6 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS ENR\r\n"
        		+ "    \r\n"
        		+ "   FROM ErrorHistory eh\r\n"
        		+ "   LEFT JOIN StopType st ON eh.TypeID = st.TypeID\r\n"
        		+ "   LEFT JOIN Stages s ON s.StageID = eh.StageID\r\n"
        		+ "   LEFT JOIN ProductionLines l ON eh.LineID = l.LineID\r\n"
        		+ "   LEFT JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID\r\n"
        		+ "   LEFT JOIN Shifts sh ON ss.ShiftID = sh.ShiftID\r\n"
        		+ "   JOIN MaxSlot ms ON ms.LineName = l.LineName AND ms.Date = CAST(eh.StartTime AS DATE)\r\n"
        		+ "   WHERE 1=1\n");

        // Append line ID filter
        if (lineId != null) {
            detailQuery.append(" AND l.LineID = ?"); 
        }

        // Append stage ID filter
        if (stageId != null) {
            detailQuery.append(" AND eh.StageID = ?"); 
        }

        // Append date range filter
        if (startDate != null && endDate != null) {
            detailQuery.append(" AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?");
        }

        detailQuery.append(" GROUP BY CAST(eh.StartTime AS DATE), ss.Duration, ms.MaxSlotID, sh.ShiftName")
                .append(") AS Summary ")
                .append("GROUP BY Day, LatestSlot, ShiftName ORDER BY Day ASC");

        try (Connection connection = getConnection(); 
             PreparedStatement detailStmt = connection.prepareStatement(detailQuery.toString())) {
            
            int paramIndex = 1;

            if (lineId != null) {
                detailStmt.setInt(paramIndex++, lineId);
            }

            if (stageId != null) {
                detailStmt.setInt(paramIndex++, stageId);
            }

            if (startDate != null && endDate != null) {
                detailStmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
                detailStmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
            }

            try (ResultSet resultSet = detailStmt.executeQuery()) {
                while (resultSet.next()) {
                	Map<String, Object> downtimeData = new HashMap<>();
                	downtimeData.put("day", resultSet.getString("Day"));
                	downtimeData.put("shiftName", resultSet.getString("ShiftName"));
                    downtimeData.put("shortStop", resultSet.getDouble("ShortStop"));
                    downtimeData.put("shortPercentage", resultSet.getDouble("ShortPercentage"));
                    downtimeData.put("shortCount", resultSet.getInt("ShortCount"));
                    downtimeData.put("longStop", resultSet.getDouble("LongStop"));
                    downtimeData.put("longPercentage", resultSet.getDouble("LongPercentage"));
                    downtimeData.put("longCount", resultSet.getInt("LongCount"));
                    downtimeData.put("ppStop", resultSet.getDouble("PPStop"));
                    downtimeData.put("ppPercentage", resultSet.getDouble("PPPercentage"));
                    downtimeData.put("ppCount", resultSet.getInt("PPCount"));
                    downtimeData.put("dccStop", resultSet.getDouble("DCCStop"));
                    downtimeData.put("dccPercentage", resultSet.getDouble("DccPercentage"));
                    downtimeData.put("dccCount", resultSet.getInt("DCCCount"));
                    downtimeData.put("dmtdStop", resultSet.getDouble("DMTDStop"));
                    downtimeData.put("dmtdPercentage", resultSet.getDouble("DmtdPercentage"));
                    downtimeData.put("dmtdCount", resultSet.getInt("DMTDCount"));
                    downtimeData.put("nrStop", resultSet.getDouble("NRStop"));
                    downtimeData.put("nrPercentage", resultSet.getDouble("NrPercentage"));
                    downtimeData.put("nrCount", resultSet.getInt("NRCount"));
                    downtimeData.put("latestSlot", resultSet.getDouble("LatestSlot"));
                    downtimeData.put("totalTime", resultSet.getDouble("TotalTime"));
                    downtimeData.put("totalCount", resultSet.getInt("TotalCounts"));
                    
                    downtimeList.add(downtimeData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return downtimeList;
    }

    
//    public List<ErrorHistory> searchLineStageHourByDate(Integer lineId, Integer stageId, Date targetDate) throws SQLException {
//        List<ErrorHistory> list = new ArrayList<>();
//
//        String detailQuery = "SELECT \r\n"
//        		+ "    CONVERT(VARCHAR(16), eh.StartTime, 120) AS StartTime,\r\n"
//        		+ "    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
//        		+ "    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
//        		+ "    SUM(eh.Duration) AS TotalDuration,\r\n"
//        		+ "    COUNT(eh.ErrorID) AS ErrorCount\r\n"
//        		+ "FROM ErrorHistory eh\r\n"
//        		+ "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
//        		+ "JOIN Stages s ON eh.StageID = s.StageID\r\n"
//        		+ "JOIN ProductionLines l ON s.LineID = l.LineID\r\n"
//        		+ "JOIN Rooms r ON l.RoomID = r.RoomID\r\n"
//        		+ "JOIN Departments d ON r.DepartmentID = d.DepartmentID\r\n"
//        		+ "WHERE 1=1";
//
//        if (lineId != null) {
//            detailQuery += " AND l.LineID = ?";
//        }
//
//        if (stageId != null) {
//            detailQuery += " AND s.StageID = ?";
//        }
//
//        if (targetDate != null) {
//            detailQuery += " AND CAST(eh.StartTime AS DATE) = ?";
//        }
//
//        detailQuery += " GROUP BY CONVERT(VARCHAR(16), eh.StartTime, 120) "
//                     + " ORDER BY CONVERT(VARCHAR(16), eh.StartTime, 120) ASC";
//
//        try (Connection connection = getConnection();
//             PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
//
//            int paramIndex = 1;
//
//            if (lineId != null) {
//                detailStmt.setInt(paramIndex++, lineId);
//            }
//
//            if (stageId != null) {
//                detailStmt.setInt(paramIndex++, stageId);
//            }
//
//            if (targetDate != null) {
//                detailStmt.setDate(paramIndex++, new java.sql.Date(targetDate.getTime()));
//            }
//
//            try (ResultSet rs = detailStmt.executeQuery()) {
//                while (rs.next()) {
//                    ErrorHistory eh = new ErrorHistory();
//                    eh.setStartTime(rs.getString("StartTime"));  
//                    eh.setLongTime(rs.getDouble("LongStop"));
//                    eh.setShortTime(rs.getDouble("ShortStop"));
//                    eh.setDuration(rs.getDouble("TotalDuration"));
//                    eh.setErrorCount(rs.getInt("ErrorCount"));
//                    list.add(eh);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw e; // Log meaningful error
//        }
//
//        return list;
//    }
    
//    public List<ErrorHistory> searchLineStageHourByDate(Integer lineId, Integer stageId, Date targetDate) throws SQLException {
//        List<ErrorHistory> list = new ArrayList<>();
//
//        String detailQuery = "SELECT \r\n"
//        		+ "CONVERT(VARCHAR(16), eh.StartTime, 120) AS StartTime,\r\n"
//        		+ "SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
//        		+ "SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
//        		+ "SUM(eh.Duration) AS TotalDuration,\r\n"
//        		+ "COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS ShortCount,\r\n"
//        		+ "COUNT(CASE WHEN eh.Duration > 5 THEN 1 END) AS LongCount,\r\n"
//        		+ "COUNT(eh.ErrorID) AS ErrorCount\r\n"
//        		+ "FROM ErrorHistory eh\r\n"
//        		+ "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\r\n"
//        		+ "JOIN Stages s ON eh.StageID = s.StageID\r\n"
//        		+ "JOIN ProductionLines l ON s.LineID = l.LineID\r\n"
//        		+ "WHERE 1=1";
//
//        if (lineId != null) {
//            detailQuery += " AND l.LineID = ?";
//        }
//
//        if (stageId != null) {
//            detailQuery += " AND s.StageID = ?";
//        }
//
//        if (targetDate != null) {
//            detailQuery += " AND CAST(eh.StartTime AS DATE) = ?";
//        }
//
//        detailQuery += " GROUP BY CONVERT(VARCHAR(16), eh.StartTime, 120) "
//                     + " ORDER BY CONVERT(VARCHAR(16), eh.StartTime, 120) ASC";
//
//        try (Connection connection = getConnection();
//             PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
//
//            int paramIndex = 1;
//
//            if (lineId != null) {
//                detailStmt.setInt(paramIndex++, lineId);
//            }
//
//            if (stageId != null) {
//                detailStmt.setInt(paramIndex++, stageId);
//            }
//
//            if (targetDate != null) {
//                detailStmt.setDate(paramIndex++, new java.sql.Date(targetDate.getTime()));
//            }
//
//            try (ResultSet rs = detailStmt.executeQuery()) {
//                while (rs.next()) {
//                    ErrorHistory eh = new ErrorHistory();
//                    eh.setStartTime(rs.getString("StartTime"));  
//                    eh.setLongTime(rs.getDouble("LongStop"));
//                    eh.setShortTime(rs.getDouble("ShortStop"));
//                    eh.setDuration(rs.getDouble("TotalDuration"));
//                    eh.setShortCount(rs.getInt("ShortCount"));
//                    eh.setLongCount(rs.getInt("LongCount"));
//                    eh.setErrorCount(rs.getInt("ErrorCount"));
//                    list.add(eh);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw e; // Log meaningful error
//        }
//
//        return list;
//    }
    
    public List<ErrorHistory> searchLineStageHourByDate(Integer lineId, Integer stageId, Date targetDate) throws SQLException {
        List<ErrorHistory> list = new ArrayList<>();

        StringBuilder detailQuery = new StringBuilder();
        detailQuery.append("SELECT \n")
                .append("    StartTime,\n")
                .append("    SUM(LongStop) AS LongStop,\n")
                .append("    SUM(ShortStop) AS ShortStop,\n")
                .append("    SUM(TotalDowntime) AS TotalDuration,\n")
                .append("    SUM(ShortCount) AS ShortCount,\n")
                .append("    SUM(LongCount) AS LongCount,\n")
                .append("    SUM(TotalDowntimeCount) AS ErrorCount,\n")
                .append("    ROUND(SUM(Efficiency) * 100, 2) AS ShortStopPercentage\n")
                .append("FROM (\n")
                .append("    SELECT \n")
                .append("        CONVERT(VARCHAR(16), eh.StartTime, 120) AS StartTime,\n")
                .append("        SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop, \n")
                .append("        SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\n")
                .append("        SUM(DATEDIFF(MINUTE, eh.StartTime, eh.EndTime)) AS TotalDowntime,\n")
                .append("        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS ShortCount,\n")
                .append("        COUNT(CASE WHEN eh.Duration > 5 THEN 1 END) AS LongCount,\n")
                .append("        COUNT(CASE WHEN eh.Duration <= 5 THEN 1 END) AS TotalDowntimeCount,\n")
                .append("        CAST(SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / ss.Duration AS Efficiency\n")
                .append("    FROM ErrorHistory eh\n")
                .append("    JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n")
                .append("    JOIN Stages s ON s.StageID = eh.StageID\n")
                .append("    LEFT JOIN ProductionLines pl ON pl.LineID = s.LineID\n")
                .append("    JOIN Rooms r ON pl.RoomID = r.RoomID\n")
                .append("    JOIN Users u ON r.RoomID = u.RoomID\n")
                .append("    JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID\n")
                .append("    WHERE 1=1\n");

        // Append line ID filter
        if (lineId != null) {
            detailQuery.append(" AND pl.LineID = ?"); // Fixed alias to 'pl'
        }

        // Append stage ID filter
        if (stageId != null) {
            detailQuery.append(" AND s.StageID = ?"); // No change needed here
        }

        // Append date range filter
        if (targetDate != null) {
            detailQuery.append(" AND CAST(eh.StartTime AS DATE) = ?");
        }

        detailQuery.append(" GROUP BY CONVERT(VARCHAR(16), eh.StartTime, 120), ss.Duration")
                .append(") AS Summary ")
                .append("GROUP BY StartTime ORDER BY StartTime ASC");

        try (Connection connection = getConnection();
             PreparedStatement detailStmt = connection.prepareStatement(detailQuery.toString())) {

            int paramIndex = 1;

            if (lineId != null) {
                detailStmt.setInt(paramIndex++, lineId);
            }

            if (stageId != null) {
                detailStmt.setInt(paramIndex++, stageId);
            }

            if (targetDate != null) {
                detailStmt.setDate(paramIndex++, new java.sql.Date(targetDate.getTime()));
            }

            try (ResultSet rs = detailStmt.executeQuery()) {
                while (rs.next()) {
                    ErrorHistory eh = new ErrorHistory();
                    eh.setStartTime(rs.getString("StartTime"));  
                    eh.setLongTime(rs.getDouble("LongStop"));
                    eh.setShortTime(rs.getDouble("ShortStop"));
                    eh.setDuration(rs.getDouble("TotalDuration"));
                    eh.setShortCount(rs.getInt("ShortCount"));
                    eh.setLongCount(rs.getInt("LongCount"));
                    eh.setErrorCount(rs.getInt("ErrorCount"));
                    eh.setShortStopPercentage(rs.getDouble("ShortStopPercentage"));
                    list.add(eh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Log meaningful error
        }

        return list;
    }
    
    
    public List<Map<String, Object>> searchLineStageHourByDateNew(Integer lineId, Integer stageId, Date targetDate) throws SQLException {
    	 List<Map<String, Object>> downtimeList = new ArrayList<>();

        StringBuilder detailQuery = new StringBuilder();
        detailQuery.append("WITH MaxSlot AS (\r\n"
        		+ "    SELECT \r\n"
        		+ "        CONVERT(VARCHAR(16), eh.StartTime, 120) AS StartTime,\r\n"
        		+ "        l.LineName, \r\n"
        		+ "        MAX(ss.SlotID) AS MaxSlotID\r\n"
        		+ "    FROM ShiftSlots ss\r\n"
        		+ "    JOIN ErrorHistory eh ON ss.SlotID = eh.SlotID\r\n"
        		+ "    JOIN ProductionLines l ON eh.LineID = l.LineID\r\n"
        		+ "    GROUP BY CONVERT(VARCHAR(16), eh.StartTime, 120), l.LineName\r\n"
        		+ ")\r\n"
        		+ "SELECT\r\n"
        		+ "   StartTime,\r\n"
        		+ "   SUM(ShortStop) AS ShortStop,\r\n"
        		+ "   ROUND(SUM(EShort) / NULLIF(LatestSlot, 0) * 100, 2) AS ShortPercentage,\r\n"
        		+ "   SUM(ShortCount) AS ShortCount,\r\n"
        		+ "   SUM(LongStop) AS LongStop,\r\n"
        		+ "   ROUND(SUM(ELong) / NULLIF(LatestSlot, 0) * 100, 2) AS LongPercentage,\r\n"
        		+ "   SUM(LongCount) AS LongCount,\r\n"
        		+ "   SUM(PPStop) AS PPStop,\r\n"
        		+ "   ROUND(SUM(EPP) / NULLIF(LatestSlot, 0) * 100, 2) AS PPPercentage,\r\n"
        		+ "   SUM(PPCount) AS PPCount,\r\n"
        		+ "   SUM(DCCStop) AS DCCStop,\r\n"
        		+ "   ROUND(SUM(EDCC) / NULLIF(LatestSlot, 0) * 100, 2) AS DccPercentage,\r\n"
        		+ "   SUM(DCCCount) AS DCCCount,\r\n"
        		+ "   SUM(DMTDStop) AS DMTDStop,\r\n"
        		+ "   ROUND(SUM(EDMTD) / NULLIF(LatestSlot, 0) * 100, 2) AS DmtdPercentage,\r\n"
        		+ "   SUM(DMTDCount) AS DMTDCount,\r\n"
        		+ "   SUM(NRStop) AS NRStop,\r\n"
        		+ "   ROUND(SUM(ENR) / NULLIF(LatestSlot, 0) * 100, 2) AS NrPercentage,\r\n"
        		+ "   SUM(NRCount) AS NRCount,\r\n"
        		+ "   LatestSlot,\r\n"
        		+ "   SUM(TotalTime) AS TotalTime,\r\n"
        		+ "   SUM(TotalCounts) AS TotalCounts\r\n"
        		+ "FROM (\r\n"
        		+ "   SELECT \r\n"
        		+ "       CONVERT(VARCHAR(16), eh.StartTime, 120) AS StartTime,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 1 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 1 THEN 1 END) AS ShortCount,\r\n"
        		+ "       SUM(CASE WHEN eh.TypeID = 2 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 2 THEN 1 END) AS LongCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 3 THEN eh.Duration ELSE 0 END) AS PPStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 3 THEN 1 END) AS PPCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 4 THEN eh.Duration ELSE 0 END) AS DMTDStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 4 THEN 1 END) AS DCCCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 5 THEN eh.Duration ELSE 0 END) AS DCCStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 5 THEN 1 END) AS DMTDCount,\r\n"
        		+ "	   SUM(CASE WHEN eh.TypeID = 6 THEN eh.Duration ELSE 0 END) AS NRStop,\r\n"
        		+ "       COUNT(CASE WHEN eh.TypeID = 6 THEN 1 END) AS NRCount,\r\n"
        		+ "       SUM(eh.Duration) AS TotalTime,\r\n"
        		+ "       COUNT(eh.ErrorID) AS TotalCounts,\r\n"
        		+ "	   ms.MaxSlotID AS LatestSlot,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 1 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EShort,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 2 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS ELong,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 3 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EPP,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 4 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EDMTD,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 5 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS EDCC,\r\n"
        		+ "       CAST(SUM(CASE WHEN eh.TypeID = 6 THEN eh.Duration ELSE 0 END) AS FLOAT) / NULLIF(ss.Duration, 0) AS ENR\r\n"
        		+ "   FROM ErrorHistory eh\r\n"
        		+ "   LEFT JOIN StopType st ON eh.TypeID = st.TypeID\r\n"
        		+ "   LEFT JOIN Stages s ON s.StageID = eh.StageID\r\n"
        		+ "   LEFT JOIN ProductionLines l ON eh.LineID = l.LineID\r\n"
        		+ "   LEFT JOIN ShiftSlots ss ON eh.SlotID = ss.SlotID\r\n"
        		+ "   LEFT JOIN MaxSlot ms ON ms.LineName = l.LineName AND ms.StartTime = CONVERT(VARCHAR(16), eh.StartTime, 120)\r\n"
        		+ "   WHERE 1=1\n");

        // Append line ID filter
        if (lineId != null) {
            detailQuery.append(" AND l.LineID = ?"); // Fixed alias to 'pl'
        }

        // Append stage ID filter
        if (stageId != null) {
            detailQuery.append(" AND eh.StageID = ?"); // No change needed here
        }

        // Append date range filter
        if (targetDate != null) {
            detailQuery.append(" AND CAST(eh.StartTime AS DATE) = ?");
        }

        detailQuery.append(" GROUP BY ss.Duration, ms.MaxSlotID, CONVERT(VARCHAR(16), eh.StartTime, 120)")
                .append(") AS Summary ")
                .append("GROUP BY StartTime, LatestSlot ORDER BY StartTime ASC");

        try (Connection connection = getConnection();
             PreparedStatement detailStmt = connection.prepareStatement(detailQuery.toString())) {

            int paramIndex = 1;

            if (lineId != null) {
                detailStmt.setInt(paramIndex++, lineId);
            }

            if (stageId != null) {
                detailStmt.setInt(paramIndex++, stageId);
            }

            if (targetDate != null) {
                detailStmt.setDate(paramIndex++, new java.sql.Date(targetDate.getTime()));
            }

            try (ResultSet resultSet = detailStmt.executeQuery()) {
                while (resultSet.next()) {
                	Map<String, Object> downtimeData = new HashMap<>();

                    downtimeData.put("startTime", resultSet.getString("StartTime"));
                    downtimeData.put("shortStop", resultSet.getDouble("ShortStop"));
                    downtimeData.put("shortPercentage", resultSet.getDouble("ShortPercentage"));
                    downtimeData.put("shortCount", resultSet.getInt("ShortCount"));
                    downtimeData.put("longStop", resultSet.getDouble("LongStop"));
                    downtimeData.put("longPercentage", resultSet.getDouble("LongPercentage"));
                    downtimeData.put("longCount", resultSet.getInt("LongCount"));
                    downtimeData.put("ppStop", resultSet.getDouble("PPStop"));
                    downtimeData.put("ppPercentage", resultSet.getDouble("PPPercentage"));
                    downtimeData.put("ppCount", resultSet.getInt("PPCount"));
                    downtimeData.put("dccStop", resultSet.getDouble("DCCStop"));
                    downtimeData.put("dccPercentage", resultSet.getDouble("DccPercentage"));
                    downtimeData.put("dccCount", resultSet.getInt("DCCCount"));
                    downtimeData.put("dmtdStop", resultSet.getDouble("DMTDStop"));
                    downtimeData.put("dmtdPercentage", resultSet.getDouble("DmtdPercentage"));
                    downtimeData.put("dmtdCount", resultSet.getInt("DMTDCount"));
                    downtimeData.put("nrStop", resultSet.getDouble("NRStop"));
                    downtimeData.put("nrPercentage", resultSet.getDouble("NrPercentage"));
                    downtimeData.put("nrCount", resultSet.getInt("NRCount"));
                    downtimeData.put("latestSlot", resultSet.getDouble("LatestSlot"));
                    downtimeData.put("totalTime", resultSet.getDouble("TotalTime"));
                    downtimeData.put("totalCount", resultSet.getInt("TotalCounts"));
                    
                    downtimeList.add(downtimeData);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }

        return downtimeList;
    }
    
//    public List<ErrorHistory> searchByDateRange(String deviceCode, Integer lineId, Integer stageId, Date startDate, Date endDate, int pageIndex, int pageSize) throws SQLException {
//        List<ErrorHistory> detailedList = new ArrayList<>();
//        Map<String, Integer> errorSummaryMap = new HashMap<>();
//
//        // Base SQL query for detailed error history
//        String detailQuery = "SELECT eh.ErrorDescription, eh.StartTime, eh.EndTime, eh.Duration, " +
//            "e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName, d.DepartmentName " +
//            "FROM ErrorHistory eh " +
//            "LEFT JOIN StopType st ON eh.TypeID = st.TypeID " +
//            "LEFT JOIN Equipment e ON e.EquipmentID = eh.EquipmentID " +
//            "LEFT JOIN Stages s ON eh.StageID = s.StageID " +
//            "LEFT JOIN ProductionLines l ON s.LineID = l.LineID " +
//            "LEFT JOIN Rooms r ON l.RoomID = r.RoomID " +
//            "LEFT JOIN Departments d ON r.DepartmentID = d.DepartmentID " +
//            "WHERE 1=1 ";
//
//        // Adding filters dynamically based on provided parameters
//        if (deviceCode != null && !deviceCode.isEmpty()) {
//            detailQuery += " AND e.EquipmentCode = ?";
//        }
//        if (lineId != null) {
//            detailQuery += " AND l.LineID = ?";
//        }
//        if (stageId != null) {
//            detailQuery += " AND s.StageID = ?";
//        }
//
//        // Date filtering
//        if (startDate != null && endDate != null) {
//            detailQuery += " AND CAST(eh.StartTime AS DATE) >= ? AND CAST(eh.EndTime AS DATE) <= ?";
//        }
//
//        // Pagination logic
//        detailQuery += " ORDER BY eh.StartTime DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
//
//        try (Connection connection = getConnection(); PreparedStatement detailStmt = connection.prepareStatement(detailQuery)) {
//            int paramIndex = 1;
//
//            // Set the dynamic parameters
//            if (deviceCode != null && !deviceCode.isEmpty()) {
//                detailStmt.setString(paramIndex++, deviceCode);
//            }
//            if (lineId != null) {
//                detailStmt.setInt(paramIndex++, lineId);
//            }
//            if (stageId != null) {
//                detailStmt.setInt(paramIndex++, stageId);
//            }
//
//            // Set date parameters if applicable
//            if (startDate != null && endDate != null) {
//                detailStmt.setDate(paramIndex++, new java.sql.Date(startDate.getTime()));
//                detailStmt.setDate(paramIndex++, new java.sql.Date(endDate.getTime()));
//            }
//
//            // Set pagination parameters
//            detailStmt.setInt(paramIndex++, (pageIndex - 1) * pageSize); // Offset
//            detailStmt.setInt(paramIndex++, pageSize); // Fetch size
//
//            // Execute detailed query and populate the list
//            try (ResultSet rs = detailStmt.executeQuery()) {
//                while (rs.next()) {
//                    ErrorHistory eh = new ErrorHistory();
//                    eh.setContent(rs.getString("ErrorDescription"));
//                    eh.setStartDate(rs.getTimestamp("StartTime"));
//                    eh.setEndDate(rs.getTimestamp("EndTime"));
//                    eh.setDuration(rs.getDouble("Duration"));
//                    eh.setEquipmentCode(rs.getString("EquipmentCode"));
//                    eh.setEquipmentName(rs.getString("EquipmentName"));
//                    eh.setStageName(rs.getString("StageName"));
//                    eh.setLineName(rs.getString("LineName"));
//                    eh.setDepartmentName(rs.getString("DepartmentName"));
//                    eh.setTypeName(rs.getString("TypeName"));
//
//                    detailedList.add(eh);
//
//                    // Increment the error count in the summary map
//                    String equipmentCode = rs.getString("EquipmentCode");
//                    errorSummaryMap.put(equipmentCode, errorSummaryMap.getOrDefault(equipmentCode, 0) + 1);
//                }
//            }
//        } catch (SQLException e) {
//            e.printStackTrace();
//            throw e;
//        }
//
//        // Create summary message
//        StringBuilder summaryMessage = new StringBuilder();
//        for (Map.Entry<String, Integer> entry : errorSummaryMap.entrySet()) {
//            summaryMessage.append(entry.getKey()).append(": ").append(entry.getValue()).append(" error(s), ");
//        }
//
//        // Remove the trailing comma and space if necessary
//        if (summaryMessage.length() > 0) {
//            summaryMessage.setLength(summaryMessage.length() - 2); // Trim last comma and space
//        }
//
//        // Print or return the summary if needed
//        System.out.println(summaryMessage.toString()); // or return it
//
//        return detailedList;
//    }


    
    public static void main(String[] args) {
        ErrorHistoryDAO dao = new ErrorHistoryDAO();
        List<ErrorHistory> list = dao.getAlls();
        for (ErrorHistory line : list) {
            System.out.println(line);
        }
//        int id = dao.getStageIDByStageName("Preparation Stage", "EQ003");
//       System.out.print(id);
    }

}
