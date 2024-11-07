package dal;

import model.Equipment;
import model.ProductionLines;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EquipmentDAO extends DBContext{

    public List<Equipment> getAlls() {
        List<Equipment> list = new ArrayList<>();
        try {
            String sql = "SELECT e.*, s.StageName\r\n"
            		+ "FROM Equipment e\r\n"
            		+ "JOIN Stages s ON e.StageID = s.StageID ";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet resultSet = stmt.executeQuery();

            while (resultSet.next()) {
            	Equipment device = new Equipment();
                
                // Extract data from result set
                device.setId(resultSet.getInt("EquipmentID"));
                device.setCode(resultSet.getString("EquipmentCode"));
                device.setName(resultSet.getString("EquipmentName"));
                device.setDateUse(resultSet.getDate("DateUse"));
                device.setOrigin(resultSet.getString("Origin"));
                device.setYom(resultSet.getInt("YOM"));
                device.setQrCode(resultSet.getString("QRCode"));
                device.setStageId(resultSet.getInt("StageID")); 
                device.setIssue(resultSet.getString("Issue")); 
                device.setIdCode(resultSet.getString("IdCode"));
                device.setStageName(resultSet.getString("StageName"));
                list.add(device);
            }
            // Close resources
            resultSet.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; // Return the populated list
    }
    
    public List<Equipment> getDevices(int pageIndex, int pageSize) {
        List<Equipment> devices = new ArrayList<>();

        String query = "SELECT e.*, s.StageName, l.LineName " +
                       "FROM Equipment e " +
                       "JOIN Stages s ON e.StageID = s.StageID " +
                       "LEFT JOIN ProductionLines l ON s.LineID = l.LineID " +
                       "ORDER BY e.EquipmentID " + // Change this as needed for ordering
                       "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";

        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(query)) {

            // Set parameters for pagination
            ps.setInt(1, (pageIndex - 1) * pageSize);
            ps.setInt(2, pageSize);

            try (ResultSet resultSet = ps.executeQuery()) {
                while (resultSet.next()) {
                    Equipment device = new Equipment();
                    
                    // Extract data from result set
                    device.setId(resultSet.getInt("EquipmentID"));
                    device.setCode(resultSet.getString("EquipmentCode"));
                    device.setName(resultSet.getString("EquipmentName"));
                    device.setDateUse(resultSet.getDate("DateUse"));
                    device.setOrigin(resultSet.getString("Origin"));
                    device.setYom(resultSet.getInt("YOM"));
                    device.setQrCode(resultSet.getString("QRCode"));
                    device.setStageId(resultSet.getInt("StageID")); 
                    device.setIssue(resultSet.getString("Issue")); 
                    device.setStageName(resultSet.getString("StageName"));
                    device.setLineName(resultSet.getString("LineName"));
                    devices.add(device);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return devices;
    }


    public int getECodedByName(String equipmentCode) throws SQLException {
        String query = "SELECT EquipmentID FROM Equipment WHERE TRIM(LOWER(EquipmentCode)) = ?";
        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, equipmentCode.trim().toLowerCase());
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("EquipmentID");
            } else {
                throw new SQLException("No Equipment found with the name: " + equipmentCode);
            }
        }
    }
    
    public int getTotalDevicesCount() {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM Equipment";
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
    
    
    public List<Equipment> searchDevices(String equipmentCode, Integer lineId, Integer stageId, String equipmentName, String origin, int pageIndex, int pageSize) {
        List<Equipment> equipmentList = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
            "WITH EquipmentWithRowNumber AS ( " +
            "    SELECT e.*, s.StageName, pl.LineName, " +
            "           ROW_NUMBER() OVER (ORDER BY e.EquipmentID) AS RowNum " +
            "    FROM Equipment e " +
            "    JOIN Stages s ON e.StageID = s.StageID " +
            "    JOIN ProductionLines pl ON s.LineID = pl.LineID " +
            "    WHERE 1=1 "
        );

        // Thêm điều kiện tìm kiếm nếu có
        if (equipmentCode != null && !equipmentCode.isEmpty()) {
            sql.append(" AND e.EquipmentCode = ?");
        }
        if (lineId != null) {
            sql.append(" AND pl.LineID = ?");
        }
        if (stageId != null) {
            sql.append(" AND s.StageID = ?");
        }
        if (equipmentName != null && !equipmentName.isEmpty()) {
            sql.append(" AND e.EquipmentName LIKE ?");
        }
        if (origin != null && !origin.isEmpty()) {
            sql.append(" AND e.Origin = ?");
        }

        // Đóng phần WITH và thêm câu truy vấn phân trang
        sql.append(") SELECT * FROM EquipmentWithRowNumber WHERE RowNum BETWEEN ? AND ?");

        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql.toString())) {

            int index = 1;
            // Thiết lập các tham số cho câu lệnh PreparedStatement
            if (equipmentCode != null && !equipmentCode.isEmpty()) {
                pst.setString(index++, equipmentCode);
            }
            if (lineId != null) {
                pst.setInt(index++, lineId);
            }
            if (stageId != null) {
                pst.setInt(index++, stageId);
            }
            if (equipmentName != null && !equipmentName.isEmpty()) {
                pst.setString(index++, "%" + equipmentName + "%");
            }
            if (origin != null && !origin.isEmpty()) {
                pst.setString(index++, origin);
            }

            // Tính toán giá trị cho phân trang
            int startRow = (pageIndex - 1) * pageSize + 1;
            int endRow = pageIndex * pageSize;

            // Thiết lập tham số cho phân trang
            pst.setInt(index++, startRow);
            pst.setInt(index++, endRow);

            // Thực thi truy vấn và xử lý kết quả
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Equipment equipment = new Equipment(
                    rs.getInt("EquipmentID"),
                    rs.getString("EquipmentCode"),
                    rs.getString("EquipmentName"),
                    rs.getDate("DateUse"),
                    rs.getString("Origin"),
                    rs.getInt("YOM"),
                    rs.getString("QRCode"),
                    rs.getInt("StageID"),
                    rs.getString("Issue"),
                    rs.getString("StageName"),
                    rs.getString("LineName")
                );
                equipmentList.add(equipment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipmentList;
    }

    
    public boolean deviceExists(String deviceId) {
        String query = "SELECT COUNT(*) FROM Equipment WHERE EquipmentCode = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, deviceId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Returns true if it exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Not found
    }
    
 // Method to insert a new Equipment into the database
    public void insert(Equipment equipment) throws SQLException {
        String sql = "INSERT INTO Equipment (EquipmentCode, EquipmentName, DateUse, Origin, YOM, StageID, Issue) "
                   + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection(); 
             PreparedStatement pst = conn.prepareStatement(sql)) {
        
            pst.setString(1, equipment.getCode());        
            pst.setString(2, equipment.getName());        
            pst.setDate(3, equipment.getDateUse());       
            pst.setString(4, equipment.getOrigin());      
            pst.setInt(5, equipment.getYom());   
            pst.setInt(6, equipment.getStageId());       
            pst.setString(7, equipment.getIssue());  

            // Execute the INSERT operation
            pst.executeUpdate();
        }
    }
    

    public void delete(int id) {
        String sql = "DELETE FROM Equipment WHERE EquipmentID = ?";

        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }
    
    public int getFilteredEquipmentCount(String equipmentCode, String equipmentName, Integer lineCode, Integer stageId, String origin) {
        int count = 0;
        StringBuilder sql = new StringBuilder("SELECT COUNT(*) FROM Equipment e LEFT JOIN Stages s ON e.StageID = s.StageID LEFT JOIN ProductionLines l ON s.LineID = l.LineID WHERE 1 = 1");

        // Add filtering conditions based on the provided parameters
        if (equipmentCode != null && !equipmentCode.isEmpty()) {
            sql.append(" AND e.EquipmentCode = ?");
        }
        if (equipmentName != null && !equipmentName.isEmpty()) {
            sql.append(" AND e.EquipmentName LIKE ?");
        }
        if (lineCode != null) {
            sql.append(" AND l.LineID = ?");
        }
        if (stageId != null) {
            sql.append(" AND s.StageID = ?");
        }
        if (origin != null && !origin.isEmpty()) {
            sql.append(" AND e.Origin LIKE ?");
        }

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql.toString())) {
            int index = 1;

            if (equipmentCode != null && !equipmentCode.isEmpty()) {
                pst.setString(index++, equipmentCode);
            }
            if (equipmentName != null && !equipmentName.isEmpty()) {
                pst.setString(index++, "%" + equipmentName + "%");
            }
            if (lineCode != null) {
                pst.setInt(index++, lineCode);
            }
            if (stageId != null) {
                pst.setInt(index++, stageId);
            }
            if (origin != null && !origin.isEmpty()) {
                pst.setString(index++, "%" + origin + "%");
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

    
    public List<Equipment> search(String EquipmentCode, Integer lineCode, Integer stageId, String deviceName, String origin, int pageIndex, int pageSize) throws SQLException {
        List<Equipment> list = new ArrayList<>();

        // Base query construction
        String query = "SELECT e.*, s.StageName, l.LineName " +
                       "FROM Equipment e " +
                       "JOIN Stages s ON s.StageID = e.StageID " +
                       "LEFT JOIN ProductionLines l ON l.LineID = s.LineID " +
                       "WHERE 1 = 1";

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
        if (origin != null && !origin.isEmpty()) {
            query += " AND e.Origin = ?";
        }

            query += " ORDER BY e.EquipmentName ASC";

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
            if (origin != null && !origin.isEmpty()) {
                stmt.setString(index++, origin);
            }

            // Setting pagination parameters
            stmt.setInt(index++, (pageIndex - 1) * pageSize);
            stmt.setInt(index++, pageSize);

            // Executing query and processing results
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
            	Equipment equipment = new Equipment(
            		    rs.getInt("EquipmentID"),
            		    rs.getString("EquipmentCode"),
            		    rs.getString("EquipmentName"),
            		    rs.getDate("DateUse"),
            		    rs.getString("Origin"),
            		    rs.getInt("YOM"),
            		    rs.getString("QRCode"),
            		    rs.getInt("StageID"),
            		    rs.getString("Issue"),
            		    rs.getString("StageName"),
            		    rs.getString("LineName")
            		);
            	list.add(equipment);
            }
        }

        return list;
    }
    
    public void addEquipment(String equipmentCode, String equipmentName, java.sql.Date dateUse, String origin, int yom, int stageId, String issue) {
        String sql = "INSERT INTO Equipment (EquipmentCode, EquipmentName, DateUse, Origin, YOM, StageID, Issue) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, equipmentCode);
            pst.setString(2, equipmentName);
            pst.setDate(3, dateUse);
            pst.setString(4, origin);
            pst.setInt(5, yom);
            pst.setInt(6, stageId);
            pst.setString(7, issue);
            
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error adding equipment: " + e.getMessage());
        }
    }

    public void update(Equipment equipment) throws SQLException {
        String sql = "UPDATE Equipment SET EquipmentCode = ?, EquipmentName = ?, DateUse = ?, Origin = ?, YOM = ?, StageID = ?, Issue = ? WHERE EquipmentID = ?";
        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, equipment.getCode());
            stmt.setString(2, equipment.getName());
            stmt.setDate(3, equipment.getDateUse());
            stmt.setString(4, equipment.getOrigin());
            stmt.setInt(5, equipment.getYom());
            stmt.setInt(6, equipment.getStageId());
            stmt.setString(7, equipment.getIssue());
            stmt.setInt(8, equipment.getId()); // Đảm bảo truyền deviceId đúng

            stmt.executeUpdate();
        }
    }
    
    public Equipment getDeviceById(String deviceID) {
        String sql = "SELECT d.*, p.StageName FROM Equipment d "
                + "JOIN Stages p ON p.StageID = d.StageID WHERE d.EquipmentCode = ?";

        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, deviceID);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Equipment(
                    		rs.getInt("EquipmentID"),
                		    rs.getString("EquipmentCode"),
                		    rs.getString("EquipmentName"),
                		    rs.getDate("DateUse"),
                		    rs.getString("Origin"),
                		    rs.getInt("YOM"),
                		    rs.getString("QRCode"),
                		    rs.getInt("StageID"),
                		    rs.getString("Issue"),
                		    rs.getString("StageName")
                    );
                }
            }
        } catch (SQLException e) {
            // Log the SQL exception and return null
            e.printStackTrace();
            return null;
        }
        return null;
    }
    
    public boolean updateQrCode(Equipment device) {
        String sql = "UPDATE Equipment SET QRCode = ?, IdCode = ? WHERE EquipmentCode = ?";
        try (Connection conn = getConnection();
                     PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, device.getQrCode());
            pstmt.setString(2, device.getIdCode());
            pstmt.setString(3, device.getCode());

            return pstmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public static void main(String[] args) {
        EquipmentDAO ldao = new EquipmentDAO();
        List<Equipment> list = ldao.getAlls();
        for (Equipment line : list) {
            System.out.println(line); // This should now print the lines if data exists
        }
    }
}
