package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Line;

public class LineDAO extends DBContext {

    public List<Line> getLines() {
        List<Line> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Lines";
            Connection connection = getConnection();
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
            	Line line = new Line(rs.getInt("lineId"), rs.getString("lineName"));
                list.add(line); 
            }
            rs.close();
            stmt.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list; 
    }
    
    public Map<String, Integer> getAllLines() {
        Map<String, Integer> linesMap = new HashMap<>();
        String sql = "SELECT lineId, lineName FROM Lines";
        try (Connection connection = getConnection(); PreparedStatement pst = connection.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                int lineId = rs.getInt("lineId");
                String lineName = rs.getString("lineName");
                linesMap.put(lineName, lineId); 
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy danh sách dây chuyền: " + e.getMessage());
        }
        return linesMap;
    }
}