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


    public static void main(String[] args) {
        StageDAO dao = new StageDAO();
        List<Stages> list = dao.getAlls();
        for (Stages p : list) {
            System.out.println(p);
        }
    }
}
