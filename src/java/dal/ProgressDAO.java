package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Date;
import model.Progress;

/**
 *
 * @author Admin
 */
public class ProgressDAO extends DBContext {

    /**
     * @param args the command line arguments
     */
    public void updateProgress(int userId, int quizId, int subjectId, int state) {
        String query = "UPDATE Progress SET State = ? WHERE UserID = ? AND QuizID = ? AND SubjectID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, state);
            ps.setInt(2, userId);
            ps.setInt(3, quizId);
            ps.setInt(4, subjectId);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int getProgressState(int userId, int subjectId) {
        String query = "SELECT State FROM Progress WHERE UserID = ? AND SubjectID = ? AND State = 2";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, subjectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("State");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0; // Default state
    }

    public int countCompletedQuizzes(int userId, int subjectId) {
        String query = "SELECT COUNT(*) FROM Progress WHERE UserID = ? AND SubjectID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, subjectId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void insert(Progress progress) {
        String sql = "INSERT INTO Progress (UserID, QuizID, SubjectID, State, CompletedAt) "
                + "VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, progress.getUserId());
            stmt.setInt(2, progress.getQuizId());
            stmt.setInt(3, progress.getSubjectId());
            stmt.setInt(4, progress.getState());
            stmt.setDate(5, progress.getCompletedAt());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // In ra lỗi ra console để xem lỗi cụ thể
        }
    }

    // Method to insert progress into the database
//    public void insert(int userId, int quizId, int subjectId, int state, Date completedAt) {
//        String sql = "INSERT INTO Progress (UserID, QuizID, SubjectID, State, CreatedAt) "
//                + "VALUES (?, ?, ?, ?, ?)";
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//            stmt.setInt(1, userId);
//            stmt.setInt(2, quizId);
//            stmt.setInt(3, subjectId);
//            stmt.setInt(4, state);
//            stmt.setDate(5, completedAt);
//
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            e.printStackTrace(); // Handle or log the exception as needed
//        }
//    }
    public void addProgress(Progress progress) {
        try {
            String query = "INSERT INTO Progress (UserID, QuizID, SubjectID, State, CreatedAt) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setInt(1, progress.getUserId());
            ps.setInt(2, progress.getQuizId());
            ps.setInt(3, progress.getSubjectId());
            ps.setInt(4, progress.getState());
            ps.setDate(5, progress.getCompletedAt());

            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        ProgressDAO pdao = new ProgressDAO();
        int state = pdao.getProgressState(1, 1);
        System.out.println(state);
    }
}
