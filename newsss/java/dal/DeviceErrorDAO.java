package dal;

import model.DeviceError;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DeviceErrorDAO extends DBContext{

    public List<DeviceError> getAlls() {
        List<DeviceError> list = new ArrayList<>();
        try {
            String sql = "SELECT de.*, et.EName FROM DeviceErrors de \n" +
                    "JOIN ERRORTYPE et ON et.ETypeID = de.ETypeID";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                DeviceError de = new DeviceError(
                        rs.getString("DeviceID"),
                        rs.getInt("ETypeID"),
                        rs.getString("EName")
                );
                list.add(de);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
