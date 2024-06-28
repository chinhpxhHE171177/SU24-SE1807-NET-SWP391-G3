package dal;

import java.sql.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Ratings;

public class RatingDAO extends DBContext {

    public List<Ratings> getAllRatings() {
        List<Ratings> list = new ArrayList<>();

        try {
            String sql = "SELECT * FROM Ratings";
            PreparedStatement pst = connection.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Ratings rating = new Ratings(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getBoolean(8));
                list.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Ratings> getAllRatingByLID(int id) {
        List<Ratings> list = new ArrayList<>();
        try {
            String sql = "SELECT r.*, u.FullName, u.Avatar FROM Ratings r\n"
                    + "LEFT JOIN [Users] u ON u.UserId = r.UserID\n"
                    + "LEFT JOIN [LesMooc] l ON l.LesMoocID = r.LesMoocID\n"
                    + "LEFT JOIN [Mooc] m ON m.MoocID = l.MoocID\n"
                    + "LEFT JOIN [Subjects] s ON s.SubjectID = m.SubjectID\n"
                    + "WHERE m.SubjectID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Ratings rating = new Ratings(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getBoolean(8),
                        rs.getString(9),
                        rs.getString(10));
                list.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Ratings> getAllRatingByLesMocID(int id) {
        List<Ratings> list = new ArrayList<>();
        try {
            String sql = "SELECT r.*, u.FullName, u.Avatar FROM Ratings r\n"
                    + "LEFT JOIN [Users] u ON u.UserId = r.UserID\n"
                    + "LEFT JOIN [LesMooc] l ON l.LesMoocID = r.LesMoocID\n"
                    + "LEFT JOIN [Mooc] m ON m.MoocID = l.MoocID\n"
                    + "LEFT JOIN [Subjects] s ON s.SubjectID = m.SubjectID\n"
                    + "WHERE l.LesMoocID = ?";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                Ratings rating = new Ratings(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getInt(4),
                        rs.getString(5),
                        rs.getTimestamp(6),
                        rs.getInt(7),
                        rs.getBoolean(8),
                        rs.getString(9),
                        rs.getString(10));
                list.add(rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void insert(Ratings rating) {
        try {
            String sql = "INSERT INTO [dbo].[Ratings] ([UserID], [LessonID], [rating], [comment], [created_at]) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, rating.getUserId());
            pst.setInt(2, rating.getLessonId());
            pst.setInt(3, rating.getRating());
            pst.setString(4, rating.getComment());
            pst.setTimestamp(5, rating.getCreatedAt());
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertRating(int userId, int lessonId, int rating, String comment, int like, boolean isReply, boolean status) {
        String sql = "INSERT INTO Ratings (UserID, LesMoocID, rating, comment, created_at, [Like], IsReply, [Status]) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ps.setInt(2, lessonId);
            ps.setInt(3, rating);
            ps.setString(4, comment);
            ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            ps.setString(6, String.valueOf(like));
            ps.setBoolean(7, isReply);
            ps.setBoolean(8, status);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int replyToComment(String userId, String parentId, String comment) {
        int newCommentId = -1;
        String sql = "INSERT INTO Reply (RatingID, UserID, Content, DateReply) VALUES (?, ?, ?, GETDATE())";
        try (PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, Integer.parseInt(parentId));
            ps.setInt(2, Integer.parseInt(userId));
            ps.setString(3, comment);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                newCommentId = rs.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return newCommentId;
    }

    // Method to get the text of a comment by its ID
    public String getCommentText(int commentId) {
        String commentText = null;
        String sql = "SELECT Content FROM Reply WHERE ReplyID = ?";
        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, commentId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                commentText = rs.getString("Content");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return commentText;
    }

    public String getAverageRatingByLessonID(int lessonId) { // tinh tong so rating theo subject id 
        double averageRating = 0.0;
        String sql = "SELECT AVG(CAST(rating AS FLOAT)) as avgRating FROM Ratings r\n"
                + "JOIN LesMooc lm ON lm.LesMoocID = r.LesMoocID\n"
                + "LEFT JOIN Mooc m ON m.MoocID = lm.MoocID\n"
                + "LEFT JOIN Subjects s ON s.SubjectID = m.SubjectID\n"
                + "WHERE lm.LesMoocID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, lessonId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                averageRating = rs.getDouble("avgRating");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        // Format the average rating to one decimal place
        DecimalFormat df = new DecimalFormat("#.#");
        return df.format(averageRating);
    }

    public int countReviews(int id) {
        String sql = "SELECT COUNT(*) AS numOfReviews FROM Ratings r \n"
                + "LEFT JOIN [Users] u ON u.UserId = r.UserID\n"
                + "LEFT JOIN [LesMooc] l ON l.LesMoocID = r.LesMoocID\n"
                + "LEFT JOIN [Mooc] m ON m.MoocID = l.MoocID\n"
                + "LEFT JOIN [Subjects] s ON s.SubjectID = m.SubjectID\n"
                + "WHERE m.SubjectID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt("numOfReviews");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public double getAverageRatingBySubject(int subjectId) {
        String sql = "SELECT AVG(CAST(r.rating AS FLOAT)) AS averageRating "
                + "FROM Ratings r "
                + "JOIN LesMooc l ON r.LesMoocID = l.LesMoocID "
                + "JOIN Mooc m ON l.MoocID = m.MoocID "
                + "JOIN Subjects s ON m.SubjectID = s.SubjectID "
                + "WHERE s.SubjectID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, subjectId);
            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("averageRating");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0.0;
    }

    public void updateComment(int id, String comment) {
        String sql = "UPDATE Ratings SET comment = ? WHERE RatingID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setString(1, comment);
            pst.setInt(2, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteComment(int id) {
        String sql = "DELETE FROM [dbo].[Ratings] WHERE RatingID = ?";
        try (PreparedStatement pst = connection.prepareStatement(sql)) {
            pst.setInt(1, id);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public int numOfComment(int id) {
        String sql = "SELECT COUNT(*) AS numOfComment FROM Ratings WHERE LesMoocID = ?";
        try {
            PreparedStatement pst = connection.prepareStatement(sql);
            pst.setInt(1, id);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                return rs.getInt("numOfComment");
            }
        } catch (Exception e) {
        }
        return 0;
    }

    public int addLikeByRatingId(int ratingId) {
        try {
            String sql = "UPDATE [Ratings] SET [Like] = [Like] + 1 WHERE RatingID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ratingId);

            int rs = st.executeUpdate();
            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return 0;
    }

    public int subLikeByRatingId(int ratingId) {
        try {
            String sql = "UPDATE Ratings SET [Like] = CASE WHEN [Like] > 0 THEN [Like] - 1 ELSE 0 END WHERE RatingID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ratingId);

            int rs = st.executeUpdate();
            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return 0;
    }

    public int updateStatusLike(int userId, int lessonId) {
        try {
            String sql = "UPDATE Ratings SET Status = 1 WHERE UserID = ? AND LesMoocID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, lessonId);

            int rs = st.executeUpdate();
            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return 0;
    }

    public int updateStatusUnLike(int userId, int lessonId) {
        try {
            String sql = "UPDATE Ratings SET Status = 0 WHERE UserID = ? AND LesMoocID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, lessonId);

            int rs = st.executeUpdate();
            return rs;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return 0;
    }

    public int getLikeCountByRatingId(int ratingId) {
        int likeCount = 0;
        try {
            String sql = "SELECT [Like] FROM [Ratings] WHERE RatingID = ?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, ratingId);
            ResultSet rs = st.executeQuery();

            if (rs.next()) {
                likeCount = rs.getInt("Like");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(RatingDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return likeCount;
    }

    // Method to get like count by status
    public int getLikeCountByStatus() {
        int likeCount = 0;
        String sql = "SELECT COUNT([Status]) AS LikeCount FROM [Ratings] WHERE [Status] = 1";

        try (PreparedStatement st = connection.prepareStatement(sql); ResultSet rs = st.executeQuery()) {
            if (rs.next()) {
                likeCount = rs.getInt("LikeCount");
            }
        } catch (SQLException e) {
            e.getStackTrace();
        }

        return likeCount;
    }

    // Check if the user has liked the rating
    public boolean checkLikeStatus(int userId, int ratingId) {
        String sql = "SELECT [Status] FROM [Ratings] WHERE [UserID] = ? AND [RatingID] = ?";
        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, userId);
            st.setInt(2, ratingId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    return rs.getBoolean("Status");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to get like count by status
    public int getLikeCountByStatusAndLesson(int lessonId, int ratingId) {
        int likeCount = 0;
        String sql = "SELECT COUNT( [Status]) AS LikeCount  FROM [Ratings] WHERE [Status] = 1 AND LesMoocID = ? AND RatingID = ?";

        try (PreparedStatement st = connection.prepareStatement(sql)) {
            st.setInt(1, lessonId);
            st.setInt(2, ratingId);
            try (ResultSet rs = st.executeQuery()) {
                if (rs.next()) {
                    likeCount = rs.getInt("LikeCount");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return likeCount;
    }

    public boolean checkUserReation(int userId, int commentId) {

        try {
            String sql = "SELECT * FROM Ratings WHERE UserID=? and RatingID=?";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, userId);
            st.setInt(2, commentId);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public int AddLikeUserReaction(int uuserId, int commentId) {
        int rs = 0;
        try {
            String sql = "UPDATE UserReaction\n"
                    + "SET isLike=1\n"
                    + "WHERE UserID=? AND CommentID=?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, uuserId);
            st.setInt(2, commentId);
            rs = st.executeUpdate();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public int RemoveLikeUserReaction(int uuserId, int commentId) {
        int rs = 0;
        try {
            String sql = "UPDATE UserReaction\n"
                    + "SET isLike=0\n"
                    + "WHERE UserID=? AND CommentID=?;";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, uuserId);
            st.setInt(2, commentId);
            rs = st.executeUpdate();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return rs;
    }

    public int AddNewLikeUserReaction(int uuserId, int commentId) {
        try {
            String sql = "INSERT INTO [dbo].[UserReaction]\n"
                    + "           ([UserID]\n"
                    + "           ,[RatingID]\n"
                    + "           ,[isLike])\n"
                    + "     VALUES\n"
                    + "           (?,?,1)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, uuserId);
            st.setInt(2, commentId);
            int rs = st.executeUpdate();
            return rs;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                connection.close();
            } catch (SQLException ex) {
                Logger.getLogger(UserDAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return 0;
    }

    public int addLikeByRatingId(int userId, int ratingId) throws SQLException {
        String sqlInsertLike = "INSERT INTO UserReaction (UserID, RatingID, isLike) VALUES (?, ?, 1)";
        String sqlUpdateRating = "UPDATE Ratings SET [Like] = [Like] + 1 WHERE RatingID = ?";

        try (PreparedStatement stInsert = connection.prepareStatement(sqlInsertLike); PreparedStatement stUpdate = connection.prepareStatement(sqlUpdateRating)) {
            stInsert.setInt(1, userId);
            stInsert.setInt(2, ratingId);
            stInsert.executeUpdate();

            stUpdate.setInt(1, ratingId);
            return stUpdate.executeUpdate();
        }
    }

    public int subLikeByRatingId(int userId, int ratingId) throws SQLException {
        String sqlDeleteLike = "DELETE FROM UserReaction WHERE UserID = ? AND RatingID = ?";
        String sqlUpdateRating = "UPDATE Ratings SET [Like] = [Like] - 1 WHERE RatingID = ? AND [Like] > 0";

        try (PreparedStatement stDelete = connection.prepareStatement(sqlDeleteLike); PreparedStatement stUpdate = connection.prepareStatement(sqlUpdateRating)) {
            stDelete.setInt(1, userId);
            stDelete.setInt(2, ratingId);
            stDelete.executeUpdate();

            stUpdate.setInt(1, ratingId);
            return stUpdate.executeUpdate();
        }
    }

    public boolean checkUserReaction(int userId, int ratingId) throws SQLException {
        String sqlCheck = "SELECT COUNT(*) FROM UserReaction WHERE UserID = ? AND RatingID = ?";
        try (PreparedStatement stCheck = connection.prepareStatement(sqlCheck)) {
            stCheck.setInt(1, userId);
            stCheck.setInt(2, ratingId);
            try (ResultSet rs = stCheck.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        }
        return false;
    }

//    public boolean checkUserReaction(int userId, int ratingId) throws SQLException {
//        String sqlCheck = "SELECT COUNT(*) FROM UserReaction WHERE UserID = ? AND RatingID = ?";
//        try (PreparedStatement stCheck = connection.prepareStatement(sqlCheck)) {
//            stCheck.setInt(1, userId);
//            stCheck.setInt(2, ratingId);
//            try (ResultSet rs = stCheck.executeQuery()) {
//                if (rs.next()) {
//                    return rs.getInt(1) > 0;
//                }
//            }
//        }
//        return false;
//    }
//
//    public int addLikeByRatingId(int userId, int ratingId) throws SQLException {
//        String sqlInsertLike = "INSERT INTO UserReaction (UserID, RatingID, isLike) VALUES (?, ?, 1)";
//        String sqlUpdateRating = "UPDATE Ratings SET [Like] = [Like] + 1 WHERE RatingID = ?";
//
//        try (PreparedStatement stInsert = connection.prepareStatement(sqlInsertLike); PreparedStatement stUpdate = connection.prepareStatement(sqlUpdateRating)) {
//            stInsert.setInt(1, userId);
//            stInsert.setInt(2, ratingId);
//            stInsert.executeUpdate();
//
//            stUpdate.setInt(1, ratingId);
//            return stUpdate.executeUpdate();
//        }
//    }
//
//    public int subLikeByRatingId(int userId, int ratingId) throws SQLException {
//        String sqlDeleteLike = "DELETE FROM UserReaction WHERE UserID = ? AND RatingID = ?";
//        String sqlUpdateRating = "UPDATE Ratings SET [Like] = [Like] - 1 WHERE RatingID = ? AND [Like] > 0";
//
//        try (PreparedStatement stDelete = connection.prepareStatement(sqlDeleteLike); PreparedStatement stUpdate = connection.prepareStatement(sqlUpdateRating)) {
//            stDelete.setInt(1, userId);
//            stDelete.setInt(2, ratingId);
//            stDelete.executeUpdate();
//
//            stUpdate.setInt(1, ratingId);
//            return stUpdate.executeUpdate();
//        }
//    }
    public static void main(String args[]) {
        RatingDAO rdao = new RatingDAO();
//        rdao.updateComment(5, "Good!");
//        System.out.println("Update successful: ");
        //int num = rdao.replyToComment("2", "1", "hello");
        int num = rdao.getLikeCountByStatusAndLesson(1, 1);
        System.out.println(num);
        //System.out.println(numOfReviews);
        //rdao.insertRating(1, 1, 3, "Not Good But Not Bad");
//        List<Ratings> lists = rdao.getAllRatingByLesMocID(1);
//        for (Ratings list : lists) {
//            System.out.println(list);
//        }
//        String averageRating = rdao.getAverageRatingByLessonID(2);
//        System.out.println("Average Rating for Lesson 1: " + averageRating);
    }
}
