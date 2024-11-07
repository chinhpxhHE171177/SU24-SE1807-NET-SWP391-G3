package dal;

import model.Stages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StageDAO extends DBContext{

    public List<Stages> getAlls() {
        List<Stages> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Stages";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Stages p = new Stages(rs.getInt(1),
                        rs.getString(2),
                        rs.getInt(3));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();;
        }
        return list;
    }


    public int getStageIdByName(String stageName) throws SQLException {
        String sql = "SELECT StageID FROM Stages WHERE StageName = ?";
        try (Connection connection = getConnection();
             PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, stageName);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return rs.getInt("StageID");
            } else {
                throw new SQLException("No Stage found with the name: " + stageName);
            }
        }
    }


    public String getProcessNameById(int processID) throws SQLException {
        String processName = null;
        String sql = "SELECT ProcessName FROM Process WHERE ProccessID = ?";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(sql)) {
            pst.setInt(1, processID);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                processName = rs.getString("ProcessName");
            }
        }
        return processName;
    }
    
    public List<Stages> searchByStage(Integer stageId) {
        List<Stages> list = new ArrayList<>();
        try {
            String sql = "SELECT \r\n"
            		+ "    s.StageID, \r\n"
            		+ "    s.StageName, \r\n"
            		+ "    SUM(CASE WHEN eh.Duration > 5 THEN eh.Duration ELSE 0 END) AS LongStop,\r\n"
            		+ "    SUM(CASE WHEN eh.Duration <= 5 THEN eh.Duration ELSE 0 END) AS ShortStop,\r\n"
            		+ "	SUM(eh.Duration) AS TotalDuration,\r\n"
            		+ "	COUNT(eh.ErrorID) AS ErrorCount\r\n"
            		+ "FROM ErrorHistory eh\r\n"
            		+ "JOIN Stages s ON eh.StageID = s.StageID\r\n"
            		+ "WHERE s.StageID = ?\r\n"
            		+ "GROUP BY s.StageID, s.StageName;";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, stageId);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Stages p = new Stages(rs.getInt(1),
                        rs.getString(2),
                        rs.getDouble(3),
                        rs.getDouble(4),
                        rs.getDouble(5),
                        rs.getInt(6));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();;
        }
        return list;
    }
    
    public List<Stages> getStageByLineId(int lineId) {
        List<Stages> lines = new ArrayList<>();
        
        String query = "SELECT s.StageID, s.StageName FROM Stages s "
                     + "JOIN ProductionLines l ON s.LineID = l.LineID "
                     + "WHERE l.LineId = ?";

        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, lineId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Stages line = new Stages();
                line.setId(rs.getInt("StageID"));
                line.setName(rs.getString("StageName"));
                lines.add(line);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return lines;
    }


    public static void main(String[] args) {
        StageDAO dao = new StageDAO();
        List<Stages> list = dao.searchByStage(1);
        for (Stages p : list) {
            System.out.println(p);
        }
    }
}
