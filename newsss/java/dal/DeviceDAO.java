package dal;

import model.Devices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DeviceDAO extends DBContext{

    public List<Devices> getAllDevices() {
        List<Devices> list = new ArrayList<>();
        try {
            String sql = "SELECT d.*, p.ProcessName \n" +
                    "FROM DEVICES d\n" +
                    "JOIN Process p ON p.ProccessID = d.ProcessID\n" +
                    "ORDER BY d.DateUse DESC;\n";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Devices device = new Devices(
                        rs.getString("DeviceID"),
                        rs.getString("LineCode"),
                        rs.getString("DeviceName"),
                        rs.getDate("DateUse"),
                        rs.getString("Origin"),
                        rs.getInt("YOM"),
                        rs.getString("QrCode"),
                        rs.getString("Location"),
                        rs.getString("Status"),
                        rs.getInt("ProcessID"),
                        rs.getString("ProcessName"));
                list.add(device);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public Devices getDeviceById(String deviceID) {
        String sql = "SELECT d.*, p.ProcessName FROM DEVICES d " +
                "JOIN Process p ON p.ProcessID = d.ProcessID WHERE d.DeviceID = ?";

        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {

            pst.setString(1, deviceID);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return new Devices(
                            rs.getString("DeviceID"),
                            rs.getString("LineCode"),
                            rs.getString("DeviceName"),
                            rs.getDate("DateUse"),
                            rs.getString("Origin"),
                            rs.getInt("YOM"),
                            rs.getString("QrCode"),
                            rs.getString("Location"),
                            rs.getString("Status"),
                            rs.getInt("ProcessID"),
                            rs.getString("ProcessName")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Devices> getDevices(int pageIndex, int pageSize) {
        List<Devices> devices = new ArrayList<>();
        String sql = "SELECT \n" +
                "    d.DeviceID,\n" +
                "    d.LineCode,\n" +
                "    d.DeviceName,\n" +
                "    p.ProcessName,\n" +
                "    d.DateUse,\n" +
                "    d.Origin,\n" +
                "    d.YOM,\n" +
                "\td.QRCode,\n" +
                "    STRING_AGG(et.EName, ', ') AS ErrorNames,\n" +
                "\td.ProcessID,\n" +
                "    d.Location,\n" +
                "\td.Status\n" +
                "FROM \n" +
                "    DEVICES d\n" +
                "LEFT JOIN \n" +
                "    DeviceErrors de ON d.DeviceID = de.DeviceID\n" +
                "LEFT JOIN \n" +
                "    ERRORTYPE et ON de.ETypeID = et.ETypeID\n" +
                "LEFT JOIN \n" +
                "    Process p ON p.ProccessID = d.ProcessID\n" +
                "GROUP BY \n" +
                "    d.DeviceID, d.LineCode, d.DeviceName, p.ProcessName, d.DateUse, d.Origin, d.YOM, d.QRCode, d.ProcessID, d.Location, d.Status\n" +
                "ORDER BY \n" +
                "    d.DateUse DESC\n" +
                "OFFSET ? ROWS FETCH NEXT ? ROWS ONLY;";
        try (Connection connection = getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, (pageIndex - 1) * pageSize);
            ps.setInt(2, pageSize);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Devices device = new Devices(
                    rs.getString("DeviceID"),
                            rs.getString("LineCode"),
                            rs.getString("DeviceName"),
                            rs.getDate("DateUse"),
                            rs.getString("Origin"),
                            rs.getInt("YOM"),
                            rs.getString("QrCode"),
                            rs.getString("Location"),
                            rs.getString("Status"),
                            rs.getInt("ProcessID"),
                            rs.getString("ProcessName"),
                            rs.getString("ErrorNames"));
                    devices.add(device);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return devices;
    }



    public List<Devices> getAllDevicesPaging(int pageNumber, int pageSize) {
        List<Devices> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM DEVICES ORDER BY DeviceID OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, (pageNumber - 1) * pageSize);
            pst.setInt(2, pageSize);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                Devices device = new Devices(
                        rs.getString("DeviceID"),
                        rs.getString("LineCode"),
                        rs.getString("DeviceName"),
                        rs.getDate("DateUse"),
                        rs.getString("Origin"),
                        rs.getInt("YOM"),
                        rs.getString("QrCode"),
                        rs.getString("Location"),
                        rs.getString("Status"),
                        rs.getInt("ProcessID"),
                        rs.getString("ProcessName"));
                list.add(device);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int getTotalDevicesCount() {
        int count = 0;
        try {
            String sql = "SELECT COUNT(*) FROM DEVICES";
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

    public List<Devices> searchForName(String txtSearch) {
        List<Devices> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM DEVICES WHERE DeviceName LIKE ?";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, "%" + txtSearch + "%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Devices device = new Devices(
                        rs.getString("DeviceID"),
                        rs.getString("LineCode"),
                        rs.getString("DeviceName"),
                        rs.getDate("DateUse"),
                        rs.getString("Origin"),
                        rs.getInt("YOM"),
                        rs.getString("QrCode"),
                        rs.getString("Location"),
                        rs.getString("Status"),
                        rs.getInt("ProcessID"),
                        rs.getString("ProcessName"));
                list.add(device);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Devices> searchForDeviceId(String DeviceID) {
        List<Devices> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM DEVICES WHERE DeviceId = ?";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, DeviceID);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Devices device = new Devices(
                        rs.getString("DeviceID"),
                        rs.getString("LineCode"),
                        rs.getString("DeviceName"),
                        rs.getDate("DateUse"),
                        rs.getString("Origin"),
                        rs.getInt("YOM"),
                        rs.getString("QrCode"),
                        rs.getString("Location"),
                        rs.getString("Status"),
                        rs.getInt("ProcessID"),
                        rs.getString("ProcessName"));
                list.add(device);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Devices> searchForLineCode(String LineCode) {
        List<Devices> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM DEVICES WHERE LineCode = ?";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, LineCode);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Devices device = new Devices(
                        rs.getString("DeviceID"),
                        rs.getString("LineCode"),
                        rs.getString("DeviceName"),
                        rs.getDate("DateUse"),
                        rs.getString("Origin"),
                        rs.getInt("YOM"),
                        rs.getString("QrCode"),
                        rs.getString("Location"),
                        rs.getString("Status"),
                        rs.getInt("ProcessID"),
                        rs.getString("ProcessName"));
                list.add(device);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Devices> searchForOrigin(String Origin) {
        List<Devices> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM DEVICES WHERE Origin = ?";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setString(1, Origin);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Devices device = new Devices(
                        rs.getString("DeviceID"),
                        rs.getString("LineCode"),
                        rs.getString("DeviceName"),
                        rs.getDate("DateUse"),
                        rs.getString("Origin"),
                        rs.getInt("YOM"),
                        rs.getString("QrCode"),
                        rs.getString("Location"),
                        rs.getString("Status"),
                        rs.getInt("ProcessID"),
                        rs.getString("ProcessName"));
                list.add(device);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public List<Devices> searchDevices(String deviceId, String lineCode, Integer processID, String deviceName, String origin) {
        List<Devices> deviceList = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT d.*, p.ProcessName FROM DEVICES d\n" +
                "JOIN Process p ON p.ProccessID = d.ProcessID WHERE 1=1");

        if (deviceId != null && !deviceId.isEmpty()) {
            sql.append(" AND DeviceID = ?");
        }
        if (lineCode != null && !lineCode.isEmpty() && !"-- Select --".equals(lineCode)) {
            sql.append(" AND LineCode = ?");
        }
        if (processID != null) {
            sql.append(" AND ProcessID = ?");
        }
        if (deviceName != null && !deviceName.isEmpty()) {
            sql.append(" AND DeviceName LIKE ?");
        }
        if (origin != null && !origin.isEmpty()) {
            sql.append(" AND Origin = ?");
        }

        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql.toString())) {

            int index = 1;
            if (deviceId != null && !deviceId.isEmpty()) {
                pst.setString(index++, deviceId);
            }
            if (lineCode != null && !lineCode.isEmpty() && !"-- Select --".equals(lineCode)) {
                pst.setString(index++, lineCode);
            }
            if (processID != null) {
                pst.setInt(index++, processID);
            }
            if (deviceName != null && !deviceName.isEmpty()) {
                pst.setString(index++, "%" + deviceName + "%");
            }
            if (origin != null && !origin.isEmpty()) {
                pst.setString(index++, origin);
            }

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Devices device = new Devices(
                        rs.getString("DeviceID"),
                        rs.getString("LineCode"),
                        rs.getString("DeviceName"),
                        rs.getDate("DateUse"),
                        rs.getString("Origin"),
                        rs.getInt("YOM"),
                        rs.getString("QrCode"),
                        rs.getString("Location"),
                        rs.getString("Status"),
                        rs.getInt("ProcessID"),
                        rs.getString("ProcessName"));
                deviceList.add(device);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return deviceList;
    }

    public List<Devices> deviceSearch(String deviceId, String lineCode, Integer processID, String deviceName, String origin, int pageIndex, int pageSize, String sortByDate) throws SQLException {
        List<Devices> devices = new ArrayList<>();
        String query = "SELECT d.*, p.ProcessName FROM DEVICES d\n" +
                "JOIN Process p ON p.ProccessID = d.ProcessID WHERE 1=1";

        if (deviceId != null && !deviceId.isEmpty()) {
            query += " AND DeviceID = ?";
        }
        if (lineCode != null && !lineCode.isEmpty()) {
            query += " AND LineCode = ?";
        }
        if (processID != null) {
            query += " AND ProcessID = ?";
        }
        if (deviceName != null && !deviceName.isEmpty()) {
            query += " AND DeviceName LIKE ?";
        }
        if (origin != null && !origin.isEmpty()) {
            query += " AND Origin = ?";
        }

        // Add sorting by date
        if ("asc".equalsIgnoreCase(sortByDate)) {
            query += " ORDER BY DateUse ASC";
        } else {
            query += " ORDER BY DateUse DESC";
        }

        // Add pagination
        query += " OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection connection = getConnection(); PreparedStatement stmt = connection.prepareStatement(query)) {
            int index = 1;

            // Set parameters dynamically
            if (deviceId != null && !deviceId.isEmpty()) {
                stmt.setString(index++, deviceId);
            }
            if (lineCode != null && !lineCode.isEmpty()) {
                stmt.setString(index++, lineCode);
            }
            if (processID != null) {
                stmt.setInt(index++, processID);
            }
            if (deviceName != null && !deviceName.isEmpty()) {
                stmt.setString(index++, "%" + deviceName + "%");
            }
            if (origin != null && !origin.isEmpty()) {
                stmt.setString(index++, origin);
            }

            stmt.setInt(index++, (pageIndex - 1) * pageSize); // Pagination offset
            stmt.setInt(index++, pageSize); // Pagination limit

            ResultSet rs = stmt.executeQuery();
                while (rs.next()) {
                    Devices device = new Devices(
                            rs.getString("DeviceID"),
                            rs.getString("LineCode"),
                            rs.getString("DeviceName"),
                            rs.getDate("DateUse"),
                            rs.getString("Origin"),
                            rs.getInt("YOM"),
                            rs.getString("QrCode"),
                            rs.getString("Location"),
                            rs.getString("Status"),
                            rs.getInt("ProcessID"),
                            rs.getString("ProcessName"));
                    devices.add(device);
            }
        }

        return devices;
    }


    public void addDevice(Devices device) {
        String sql = "INSERT INTO [dbo].[DEVICES] ([DeviceID],[LineCode],[DeviceName],[DateUse],[Origin],[YOM],[Location],[Status],[ProcessID]) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, device.getDeviceid());
            pst.setString(2, device.getLinecode());
            pst.setString(3, device.getDevicename());
            pst.setDate(4, device.getDateuse());
            pst.setString(5, device.getOrigin());
            pst.setInt(6, device.getYom());
            pst.setString(7, device.getLocation());
            pst.setString(8, device.getStatus());
            pst.setInt(9, device.getProcessId());

            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean deviceExists(String deviceId) {
        String query = "SELECT COUNT(*) FROM DEVICES WHERE DeviceID = ?";
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

    public void deleteDevice(String id) {
        String sql = "DELETE FROM DEVICES WHERE DeviceID = ?";

        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.getStackTrace();
        }
    }

    public void updateDevice(Devices device) throws SQLException {
        String sql = "UPDATE [dbo].[DEVICES]\n" +
                "   SET [LineCode] = ?\n" +
                "      ,[DeviceName] = ?\n" +
                "      ,[DateUse] = ?\n" +
                "      ,[Origin] = ?\n" +
                "      ,[YOM] = ?\n" +
                "      ,[QRCode] = ? \n" +
                "      ,[Location] = ?\n" +
                "      ,[Status] = ?\n" +
                "      ,[ProcessID] = ?\n" +
                " WHERE DeviceID = ?";

        try (Connection connection = getConnection();PreparedStatement stmt = connection.prepareStatement(sql)) {

            stmt.setString(1, device.getLinecode());
            stmt.setString(2, device.getDevicename());
            stmt.setDate(3, device.getDateuse());
            stmt.setString(4, device.getOrigin());
            stmt.setInt(5, device.getYom());
            stmt.setString(6, device.getQrcode());
            stmt.setString(7, device.getLocation());
            stmt.setString(8, device.getStatus());
            stmt.setInt(9, device.getProcessId());
            stmt.setString(10, device.getDeviceid());
            stmt.executeUpdate();
        }
    }

    public static void main(String[] args) {
        DeviceDAO dao = new DeviceDAO();
        List<Devices> list = dao.getDevices(1, 10);
        for (Devices d : list) {
            System.out.println(d);
        }
//         int total = dao.getTotalDevicesCount();
//        System.out.println(total);
    }
}
