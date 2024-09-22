package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Process;

public class ProcessDAO extends DBContext{

    public List<Process> getAllProcess() {
        List<Process> list = new ArrayList<>();
        try {
            String sql = "SELECT * FROM Process";
            Connection connection = getConnection();
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Process p = new Process(rs.getInt(1),
                        rs.getString(2));
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();;
        }
        return list;
    }

    public int getProcessIDByName(String processName) {
        int processID = -1;
        String sql = "SELECT ProccessID FROM Process WHERE ProcessName = ?";

        try (Connection conn = DBContext.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, processName);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                processID = rs.getInt("ProccessID");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return processID;
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
        ProcessDAO dao = new ProcessDAO();
        List<Process> list = dao.getAllProcess();
        for (Process p : list) {
            System.out.println(p);
        }
    }
}
