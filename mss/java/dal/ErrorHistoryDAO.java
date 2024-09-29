package dal;

import model.DowntimeRecord;
import model.ErrorHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    // Method to get downtime records with pagination
    public List<ErrorHistory> getErrorHistory(int pageIndex, int pageSize) {
        List<ErrorHistory> list = new ArrayList<>();

        String query = "SELECT eh.*, e.EquipmentCode, e.EquipmentName, s.StageName, l.LineName\n" +
                "FROM ErrorHistory eh\n" +
                "JOIN Equipment e ON e.EquipmentID = eh.EquipmentID\n" +
                "JOIN Stages s ON s.StageID = eh.StageID\n" +
                "LEFT JOIN ProductionLines l ON l.LineID = s.LineID\n" +
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


    public static void main(String[] args) {
        ErrorHistoryDAO dao = new ErrorHistoryDAO();
        List<ErrorHistory> list = dao.getErrorsByEquipmentId(2);
        for (ErrorHistory line : list) {
            System.out.println(line);
        }
    }

}
