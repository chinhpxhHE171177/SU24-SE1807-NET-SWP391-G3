package dal;

/**
 *
 * @author Admin
 */
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RatingReactionDAO extends DBContext {

    // Method to check if a user has already reacted to a rating
    public boolean checkRatingReaction(int userId, int ratingId) throws SQLException {
        String sql = "SELECT COUNT(*) FROM RatingReactions WHERE UserID = ? AND RatingID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, ratingId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                } else {
                    return false;
                }
            }
        }
    }

    // Check if the user's reaction is a like
    public boolean isLike(int userId, int ratingId) throws SQLException {
        String query = "SELECT status FROM RatingReactions WHERE userId = ? AND ratingId = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, ratingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("status");
            }
        }
        return false;
    }
    

    // Method to add a like reaction
    public void updateLikeRatingReaction(int userId, int ratingId) throws SQLException {
        String sql = "UPDATE RatingReactions SET Status = 1 WHERE UserID = ? AND RatingID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, ratingId);
            stmt.executeUpdate();
        }
    }

    public void updateUnLikeRatingReaction(int userId, int ratingId) throws SQLException {
        String sql = "UPDATE RatingReactions SET Status = 0 WHERE UserID = ? AND RatingID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, ratingId);
            stmt.executeUpdate();
        }
    }

    // Method to add a new like reaction
    public void addNewLikeRatingReaction(int userId, int ratingId) throws SQLException {
        String sql = "INSERT INTO RatingReactions (UserID, RatingID, Status) VALUES (?, ?, 1)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, ratingId);
            stmt.executeUpdate();
        }
    }

    // Method to subtract a like reaction
    public void subLikeRatingReaction(int userId, int ratingId) throws SQLException {
        String sql = "UPDATE RatingReactions SET Status = 0 WHERE UserID = ? AND RatingID = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            stmt.setInt(2, ratingId);
            stmt.executeUpdate();
        }
    }

    public boolean checkIfUserLiked(int userId, int ratingId) throws SQLException {
        // Implement the logic to check if the user has liked the rating
        String query = "SELECT Status FROM RatingReactions WHERE UserID = ? AND RatingID = ?";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, ratingId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("Status");
            }
        }
        return false;
    }

    public void addOrUpdateLikeRatingReaction(int userId, int ratingId, boolean status) throws SQLException {
        // Implement the logic to add or update the user’s like status
        String query = "MERGE INTO RatingReactions AS target "
                + "USING (SELECT ? AS UserID, ? AS RatingID) AS source "
                + "ON (target.UserID = source.UserID AND target.RatingID = source.RatingID) "
                + "WHEN MATCHED THEN "
                + "UPDATE SET Status = ? "
                + "WHEN NOT MATCHED THEN "
                + "INSERT (UserID, RatingID, Status) VALUES (?, ?, ?);";
        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, userId);
            ps.setInt(2, ratingId);
            ps.setBoolean(3, status);
            ps.setInt(4, userId);
            ps.setInt(5, ratingId);
            ps.setBoolean(6, status);
            ps.executeUpdate();
        }
    }

    public boolean getStatusByUserIdAndRatingId(int userId, int ratingId) throws SQLException {
        String sql = "SELECT status FROM RatingReactions WHERE userId = ? AND ratingId = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, ratingId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getBoolean("status");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {

    }
}
